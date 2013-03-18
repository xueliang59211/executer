package com.test;

import java.util.List;

import com.Executer;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//��ʼ�������
		Executer exe = new Executer(5);
		//��ʼ������
		long time = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			MyJob job = new MyJob();
			exe.fork(job);//�ɷ�����
		}
		//����������
		List<Object> list = exe.join();
		System.out.println("ResultSize: "+list.size());
		System.out.println("time: "+(System.currentTimeMillis() - time));
	}

}
