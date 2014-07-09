package com.think.demo.thread.countdown.forkjoin;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

public class Fork implements Action{
	private Logger LOG = Logger.getLogger(getClass());
	/**
	 * 收集worker抛出的异常，最终抛出第一个
	 */
	private ConcurrentLinkedQueue<Exception> exceptions = new ConcurrentLinkedQueue<Exception>();
	List<Worker> workers;
	private CountDownLatch synLock;
	
	public Fork(List<Worker> workers) {
		this.workers = workers;
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}

	public CountDownLatch getSynLock() {
		return synLock;
	}

	public void setSynLock(CountDownLatch synLock) {
		this.synLock = synLock;
	}

	public void execute() throws Exception {
		LOG.debug("---------fork start------------");
		for (Worker worker : workers) {
			worker.setSynLock(synLock);
			worker.setExceptions(exceptions);
			new Thread(worker).start();
		}
		synLock.await();
		if(!exceptions.isEmpty()){
			//发生异常
			throw exceptions.poll();
		}
		LOG.debug("---------fork end--------------");
	}
}