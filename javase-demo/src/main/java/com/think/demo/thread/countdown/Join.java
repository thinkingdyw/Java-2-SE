package com.think.demo.thread.countdown;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

public class Join implements Action{
	private Logger LOG = Logger.getLogger(getClass());
	
	Worker worker;
	private CountDownLatch synLock;
	private ConcurrentLinkedQueue<Exception> exceptions = new ConcurrentLinkedQueue<Exception>();
	
	public Join(Worker worker) {
		super();
		this.worker = worker;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public CountDownLatch getSynLock() {
		return synLock;
	}

	public void setSynLock(CountDownLatch synLock) {
		this.synLock = synLock;
	}

	public void execute() throws Exception{
		LOG.debug("---------join start--------------");
		worker.setSynLock(synLock);
		worker.setExceptions(exceptions);
		new Thread(worker).start();
		synLock.await();
		if(!exceptions.isEmpty()){
			throw exceptions.remove();
		}
		LOG.debug("---------join end----------------");
	}
}