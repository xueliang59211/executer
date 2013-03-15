package com.test;

import com.Job;

public class MyJob extends Job {

	@Override
	public User execute() {
		//模拟业务需要处理1秒.
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		System.out.println("running thread id = "+Thread.currentThread().getId());
		return new User();
	}

}
