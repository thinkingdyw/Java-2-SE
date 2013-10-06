package com.think.demo.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.think.demo.thread.countdown.Fork;
import com.think.demo.thread.countdown.ForkJoin;
import com.think.demo.thread.countdown.Join;
import com.think.demo.thread.countdown.Worker;

public class ForkJoinTest {
	private Logger LOG = Logger.getLogger(getClass());
	
	@Test
	public void forkJoin(){
		LOG.debug("添加操作开始....");
		Worker insertOp1 = new Worker("插入操作1");
		Worker insertOp2 = new Worker("插入操作2");
		List<Worker> workers = new ArrayList<Worker>();
		workers.add(insertOp1);
		workers.add(insertOp2);
		Fork fork = new Fork(workers);
		Join join = new Join(new Worker("最后一个操作!"));
		
		ForkJoin forkJoin = new ForkJoin(fork, join);
		try {
			forkJoin.execute();
			LOG.debug("添加操作完毕!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
