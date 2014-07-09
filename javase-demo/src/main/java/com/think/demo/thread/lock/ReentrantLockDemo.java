package com.think.demo.thread.lock;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 是互斥锁
 * @author diaoyouwei
 *
 */
public class ReentrantLockDemo {

	public static void main(String[] args) {
		Cache cache = new Cache();
		/**
		 * 如果生产者先执行，那么生产者会睡眠10秒，在此期间，消费者会阻塞10秒，当生产者释放锁后，消费者接着执行
		 */
		new Thread(new Producer(cache)).start();
		new Thread(new Consumer(cache)).start();
	}
}
class Producer implements Runnable{
	private Cache cache;

	public Producer(Cache cache) {
		super();
		this.cache = cache;
	}

	@Override
	public void run() {
		cache.push(Thread.currentThread().getName());
	}
	
}
class Consumer implements Runnable{
	private Cache cache;
	public Consumer(Cache cache) {
		super();
		this.cache = cache;
	}
	@Override
	public void run() {
		System.out.println("消费："+cache.poll());
	}
}
class Cache{
	private ReentrantLock lock = new ReentrantLock();
	private Queue<String> cache = new LinkedList<String>();
	
	@SuppressWarnings("static-access")
	public void push(String value){
		lock.lock();
		try{
			try {
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cache.add(value);
		}finally{
			lock.unlock();
		}
	}
	public String poll(){
		lock.lock();
		try{
			return cache.poll();
		}finally{
			lock.unlock();
		}
	}
}