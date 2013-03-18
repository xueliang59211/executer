package com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ���п��
 * @author Administrator
 *
 */
public class Executer {
	//�洢�����ִ�н��
	private List<Future<Object>> futres = new ArrayList<Future<Object>>(); 
	//����������,�Լ��̼߳�����
	public final Lock lock = new Lock();
	//�̳߳�
	private ExecutorService pool = null;
	public Executer() {
		this(1);
	}
	public Executer(int threadPoolSize) {
		pool = Executors.newFixedThreadPool(threadPoolSize);
	}
	/**
	 * �����ɷ�
	 * @param job
	 */
	public void fork(Job job){
		//����ͬ����
		job.setLock(lock);
		//�������ɷ����̳߳�ȥִ��
		futres.add(pool.submit(job));
		//�����߳���
		synchronized (lock) {
			lock.thread_count++;
		}
	}
	/**
	 * ͳ��������
	 */
	public List<Object> join(){
		synchronized (lock) {
			while(lock.thread_count > 0){//����߳��������Ϊ0�����ʾ�������������
//				System.out.println("threadCount: "+THREAD_COUNT);
				try {
					lock.wait();//�������û��ȫ����ɣ�����𡣵ȴ���ɵ��������֪ͨ
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		List<Object> list = new ArrayList<Object>();
		//ȡ��ÿ������Ĵ����������ܺ󷵻�
		for (Future<Object> future : futres) {
			try {
				Object result = future.get();//��Ϊ�����Ѿ���ɣ�����ֱ��get
				if(result != null){
					if(result instanceof List)
						list.addAll((List)result);
					else
						list.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return list;
	}
	/**
	 * �ر������߳���Դ
	 * Definition: 
	 * author: HanDeGang
	 * Created date: Jul 26, 2012
	 */
	public void shutdown(){
		pool.shutdown();
	}
}
