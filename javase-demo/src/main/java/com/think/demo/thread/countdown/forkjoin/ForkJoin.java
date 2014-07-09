package com.think.demo.thread.countdown.forkjoin;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ForkJoin {
	private Fork fork;
	private Join join;
	
	private CountDownLatch synLockFork;
	private CountDownLatch synLockJoin = new CountDownLatch(1);

	public ForkJoin(Fork fork, Join join) {
		this.fork = fork;
		this.join = join;
	}
	
	public void execute() throws Exception{
		validateForksAndJoin();
		synLockFork = new CountDownLatch(fork.getWorkers().size());
		fork();
		join();
	}

	private void join() throws Exception {
		join.setSynLock(synLockJoin);
		join.execute();
	}

	private void fork() throws Exception {
		fork.setSynLock(synLockFork);
		fork.execute();
	}

	private void validateForksAndJoin() {
		List<Worker> forks = fork.getWorkers();
		if(fork == null 
				|| forks == null 
				|| forks.isEmpty()){
			throw new IllegalArgumentException("forks不可为空");
		}
		if(join == null || join.getWorker() == null){
			throw new IllegalArgumentException("join不可为空");
		}
	}
}
