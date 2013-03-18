package com;

import java.util.concurrent.Callable;

/**
 * ��������
 * @author Administrator
 *
 */
public abstract class Job implements Callable<Object> {

	//��
	private Lock lock = null;

	void setLock(Lock lock) {
		this.lock = lock;
	}

	public Object call() throws Exception {
		Object result = null;
		try{
			result = this.execute();//ִ�������������
		}catch(Exception e){
			e.printStackTrace();
		}
		synchronized (lock) {
			//������ҵ�������������ݼ��߳�����ͬʱ�������߳�
			lock.thread_count--;
			lock.notifyAll();
		}
		return result;
	}
	/**
	 * ҵ������
	 */
	public abstract Object execute();
	
}
