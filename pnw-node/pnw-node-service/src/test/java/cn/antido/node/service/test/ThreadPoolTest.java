package cn.antido.node.service.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) {
		//新建与服务端发送消息线程池
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
			5, 											//corePoolSize线程池中核心线程数
			10, 										//maximumPoolSize 线程池中最大线程数 
			1, 								//线程池中线程的最大空闲时间
			TimeUnit.HOURS, 							//时间单位  
			new LinkedBlockingQueue<Runnable>(10), 		//阻塞队列
			new ThreadPoolExecutor.DiscardOldestPolicy()//丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
		);
		for(int i=0;i<20;i++){
			Thread1 t = new Thread1();
			System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
		             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
			executor.execute(t);
		}
		executor.shutdown();
	}
}

class Thread1 extends Thread {
	@Override
	public void run() {
		try {
			System.out.println(currentThread().getName()+"is working!");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

