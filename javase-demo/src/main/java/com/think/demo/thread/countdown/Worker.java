package com.think.demo.thread.countdown;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

public class Worker implements Runnable{
	private Logger LOG = Logger.getLogger(getClass());
	private CountDownLatch synLock;
	private ConcurrentLinkedQueue<Exception> exceptions;
	private String name;
	
	public Worker(String name) {
		this.name = name;
	}


	public void run() {
		try {
			doWork();
		} catch (Exception e) {
			synLock.countDown();
			//异常处理
			exceptions.add(e);
		}
	}

	@SuppressWarnings("static-access")
	private void doWork() throws Exception {
		LOG.debug(name+" is do working...");
		Thread.currentThread().sleep(3000);
		//TODO 测试异常处理
		/*if(!"最后一个操作!".equalsIgnoreCase(name)){
			throw new RuntimeException("测试forkJoin异常处理");
		}*/
		LOG.debug(name+" done!");
		synLock.countDown();
	}


	public CountDownLatch getSynLock() {
		return synLock;
	}

	public void setSynLock(CountDownLatch synLock) {
		this.synLock = synLock;
	}


	public ConcurrentLinkedQueue<? extends Exception> getExceptions() {
		return exceptions;
	}

	public void setExceptions(ConcurrentLinkedQueue<Exception> exceptions) {
		this.exceptions = exceptions;
	}
}