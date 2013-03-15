package com.test;

import java.util.List;

import com.Executer;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//初始化任务池
		Executer exe = new Executer(5);
		//初始化任务
		long time = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			MyJob job = new MyJob();
			exe.fork(job);//派发任务
		}
		//汇总任务结果
		List<Object> list = exe.join();
		System.out.println("ResultSize: "+list.size());
		System.out.println("time: "+(System.currentTimeMillis() - time));
	}

}
