public class MapContext<KEYIN,VALUEIN,KEYOUT,VALUEOUT> 
  extends TaskInputOutputContext<KEYIN,VALUEIN,KEYOUT,VALUEOUT> {
  private RecordReader<KEYIN,VALUEIN> reader;
  private InputSplit split;
  public MapContext(Configuration conf, TaskAttemptID taskid,
                    RecordReader<KEYIN,VALUEIN> reader,
                    RecordWriter<KEYOUT,VALUEOUT> writer,
                    OutputCommitter committer,
                    StatusReporter reporter,
                    InputSplit split) {
    super(conf, taskid, writer, committer, reporter);
    this.reader = reader;
    this.split = split;
  }
  public InputSplit getInputSplit() {
    return split;
  }
  @Override
  public KEYIN getCurrentKey() throws IOException, InterruptedException {
    return reader.getCurrentKey();
  }
  @Override
  public VALUEIN getCurrentValue() throws IOException, InterruptedException {
    return reader.getCurrentValue();
  }
  @Override
  public boolean nextKeyValue() throws IOException, InterruptedException {
    return reader.nextKeyValue();
  }
}
