package com.think.demo.thread.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 阻塞队列
 * @author diaoyouwei
 *
 */
public class BlockingQueueDemo {

	/**
	 * 阻塞队列：
	 * 1、生产者放数据时，队列满则等待。
	 * 2、消费者消费时，即从队列中取数时，队列为空，可以等待也可以直接返回。
	 * 
	 * 一下程序，验证此逻辑
	 * @throws InterruptedException 
	 */
	
	public static void main(String[] args) throws InterruptedException {
		//一下队列最多放5个元素
		BlockingQueue<String> queue = new LinkedBlockingDeque<String>(5);
		//验证如果空间不足，则等待
		for (int i=0;i<6;i++) {
			new Thread(new Producer(queue)).start();
		}
		//睡眠
		Thread.sleep(5000);
		//消费者启动
		new Thread(new Consumer(queue)).start();
	}
	
	
}

class Producer implements Runnable{

	private BlockingQueue<String> queue = null;
	
	public Producer(BlockingQueue<String> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		if(queue != null){
			try {
				Thread thread = Thread.currentThread();
				queue.put(thread.getName());
				System.out.println("线程："+thread.getName()+"已入队!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class Consumer implements Runnable{
	private BlockingQueue<String> queue = null;
	
	public Consumer(BlockingQueue<String> queue) {
		super();
		this.queue = queue;
	}
	@Override
	public void run() {
		if(queue!=null){
			queue.poll();
			System.out.println("已消费，队列大小："+queue.size());
		}
	}
	
}