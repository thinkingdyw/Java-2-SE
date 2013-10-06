package com.think.demo.thread.countdown;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * CountDownLatch:线程同步工具类
 * 
 * 	该Demo模拟的场景：当所有的子线程都工作完毕后，主线程才往下执行，类似于：fork/join
 * 
 * @author diaoyouwei
 *
 */
public class CountDownLatchDemo {

	public static void main(String[] args) {
		
		new Thread(new CountDownLatchDemo.MainThread()).start();
	}
	
	/**
	 * 主线程
	 * @author diaoyouwei
	 *
	 */
	static class MainThread implements Runnable{
		private Logger LOG = Logger.getLogger(getClass());
		/**
		 * 说明有几个子线程需要同步
		 */
		CountDownLatch synLock = new CountDownLatch(3);
		public void run() {
			try {
				doWork();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		private void doWork() throws Exception {
			LOG.info("主线程启动，开始工作...");
			
			SubThread subThread_1 = new SubThread("张三",synLock);
			SubThread subThread_2 = new SubThread("李四",synLock);
			SubThread subThread_3 = new SubThread("王五",synLock);
			
			new Thread(subThread_1).start();
			new Thread(subThread_2).start();
			new Thread(subThread_3).start();
			//等待子线程全部工作完毕
			synLock.await();
			LOG.info("主线程结束，所有任务都已完成!");
		}
	}
	/**
	 * 子线程
	 * @author diaoyouwei
	 *
	 */
	static class SubThread implements Runnable{
		private Logger LOG = Logger.getLogger(getClass());

		private CountDownLatch synLock;
		private String name;
		
		public SubThread(String name, CountDownLatch synLock) {
			this.name = name;
			this.synLock = synLock;
		}

		public void run() {
			doWork();
		}

		@SuppressWarnings("static-access")
		private void doWork() {
			LOG.info(name+":正在工作...");
			try {
				Thread.currentThread().sleep(1000);
				LOG.info(name+":工作完毕!");
				synLock.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
