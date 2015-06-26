package com.oil.datamodel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务栈模型 待完善
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class MessionTask<T extends Thread> {
	private List<T> threadList;
	int taskNumber = 1;
	ExecutorService pool;

	public MessionTask(List<T> threadList, int taskNumber) {
		this.threadList = threadList;
		this.taskNumber = taskNumber;
		pool = Executors.newFixedThreadPool(taskNumber);
	};

	public List<T> getThreadList() {
		return threadList;
	}

	public void setThreadList(List<T> threadList) {
		this.threadList = threadList;
	}

	public void start() {
		for (int i = 0; i < threadList.size(); i++) {
			pool.execute(threadList.get(i));
		}
	};

}
