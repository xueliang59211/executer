package com.test;

import com.Job;

public class MyJob extends Job {

	@Override
	public User execute() {
		//ģ��ҵ����Ҫ����1��.
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		System.out.println("running thread id = "+Thread.currentThread().getId());
		return new User();
	}

}
