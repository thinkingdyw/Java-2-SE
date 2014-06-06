private <INKEY,INVALUE,OUTKEY,OUTVALUE>
  void runNewMapper(final JobConf job,
                    final TaskSplitIndex splitIndex,
                    final TaskUmbilicalProtocol umbilical,
                    TaskReporter reporter
                    ) throws IOException, ClassNotFoundException,
                             InterruptedException {
    // make a task context so we can get the classes
    org.apache.hadoop.mapreduce.TaskAttemptContext taskContext =
      new org.apache.hadoop.mapreduce.TaskAttemptContext(job, getTaskID());
    // make a mapper
    org.apache.hadoop.mapreduce.Mapper<INKEY,INVALUE,OUTKEY,OUTVALUE> mapper =
      (org.apache.hadoop.mapreduce.Mapper<INKEY,INVALUE,OUTKEY,OUTVALUE>)
        ReflectionUtils.newInstance(taskContext.getMapperClass(), job);
    // make the input format
    org.apache.hadoop.mapreduce.InputFormat<INKEY,INVALUE> inputFormat =
      (org.apache.hadoop.mapreduce.InputFormat<INKEY,INVALUE>)
        ReflectionUtils.newInstance(taskContext.getInputFormatClass(), job);
    // rebuild the input split
    org.apache.hadoop.mapreduce.InputSplit split = null;
    split = getSplitDetails(new Path(splitIndex.getSplitLocation()),
        splitIndex.getStartOffset());

    org.apache.hadoop.mapreduce.RecordReader<INKEY,INVALUE> input =
      new NewTrackingRecordReader<INKEY,INVALUE>
          (inputFormat.createRecordReader(split, taskContext), reporter);
    
    job.setBoolean("mapred.skip.on", isSkipping());
    org.apache.hadoop.mapreduce.RecordWriter output = null;
    org.apache.hadoop.mapreduce.Mapper<INKEY,INVALUE,OUTKEY,OUTVALUE>.Context 
         mapperContext = null;
    try {
      Constructor<org.apache.hadoop.mapreduce.Mapper.Context> contextConstructor =
        org.apache.hadoop.mapreduce.Mapper.Context.class.getConstructor
        (new Class[]{org.apache.hadoop.mapreduce.Mapper.class,
                     Configuration.class,
                     org.apache.hadoop.mapreduce.TaskAttemptID.class,
                     org.apache.hadoop.mapreduce.RecordReader.class,
                     org.apache.hadoop.mapreduce.RecordWriter.class,
                     org.apache.hadoop.mapreduce.OutputCommitter.class,
                     org.apache.hadoop.mapreduce.StatusReporter.class,
                     org.apache.hadoop.mapreduce.InputSplit.class});

      // get an output object
      if (job.getNumReduceTasks() == 0) {
         output =
           new NewDirectOutputCollector(taskContext, job, umbilical, reporter);
      } else {
        output = new NewOutputCollector(taskContext, job, umbilical, reporter);
      }

      mapperContext = contextConstructor.newInstance(mapper, job, getTaskID(),
                                                     input, output, committer,
                                                     reporter, split);

      input.initialize(split, mapperContext);
      mapper.run(mapperContext);
      input.close();
      output.close(mapperContext);
    } catch (NoSuchMethodException e) {
      throw new IOException("Can't find Context constructor", e);
    } catch (InstantiationException e) {
      throw new IOException("Can't create Context", e);
    } catch (InvocationTargetException e) {
      throw new IOException("Can't invoke Context constructor", e);
    } catch (IllegalAccessException e) {
      throw new IOException("Can't invoke Context constructor", e);
    }
