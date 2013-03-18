package com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 并行框架
 * @author Administrator
 *
 */
public class Executer {
	//存储任务的执行结果
	private List<Future<Object>> futres = new ArrayList<Future<Object>>(); 
	//条件队列锁,以及线程计数器
	public final Lock lock = new Lock();
	//线程池
	private ExecutorService pool = null;
	public Executer() {
		this(1);
	}
	public Executer(int threadPoolSize) {
		pool = Executors.newFixedThreadPool(threadPoolSize);
	}
	/**
	 * 任务派发
	 * @param job
	 */
	public void fork(Job job){
		//设置同步锁
		job.setLock(lock);
		//将任务派发给线程池去执行
		futres.add(pool.submit(job));
		//增加线程数
		synchronized (lock) {
			lock.thread_count++;
		}
	}
	/**
	 * 统计任务结果
	 */
	public List<Object> join(){
		synchronized (lock) {
			while(lock.thread_count > 0){//检查线程数，如果为0，则表示所有任务处理完成
//				System.out.println("threadCount: "+THREAD_COUNT);
				try {
					lock.wait();//如果任务没有全部完成，则挂起。等待完成的任务给予通知
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		List<Object> list = new ArrayList<Object>();
		//取出每个任务的处理结果，汇总后返回
		for (Future<Object> future : futres) {
			try {
				Object result = future.get();//因为任务都已经完成，这里直接get
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
	 * 关闭所有线程资源
	 * Definition: 
	 * author: HanDeGang
	 * Created date: Jul 26, 2012
	 */
	public void shutdown(){
		pool.shutdown();
	}
}
