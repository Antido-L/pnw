package cn.antido.node.service.test;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.antido.node.service.thread.sender.MsgSender;
import cn.antido.node.service.thread.sender.MsgSenderFactory;

public class MsgSenderTest {
	public static void main(String[] args) throws InterruptedException {
		//新建与服务端发送消息线程池
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
			5, 											//corePoolSize线程池中核心线程数
			10, 										//maximumPoolSize 线程池中最大线程数 
			1, 								//线程池中线程的最大空闲时间
			TimeUnit.HOURS, 							//时间单位  
			new LinkedBlockingQueue<Runnable>(10), 		//阻塞队列
			new ThreadPoolExecutor.DiscardOldestPolicy()//丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
		);
		
		MsgSenderFactory factory = new MsgSenderFactory("a","b");
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()) {
			System.out.println(sc.nextLine());
			MsgSender sender = factory.onlineMsgSender("999");
			System.out.println("sender:"+sender);
			executor.execute(sender);
			System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
		             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
		}
		//whiell(executor, factory);
		//executor.shutdown();
	}

	private static void whiell(ThreadPoolExecutor executor, MsgSenderFactory factory) throws InterruptedException {
		while(true) {
				Thread.sleep(3000);
				MsgSender sender = factory.closeMsgSender("123456789");
				executor.execute(sender);
				
				System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
			             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
			
				Thread.sleep(3000);
				MsgSender sender1 = factory.leftMsgSender("123456789");
				executor.execute(sender1);
				
				System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
			             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
			
				Thread.sleep(3000);
				MsgSender sender2 = factory.onlineMsgSender("123456789");
				executor.execute(sender2);
				
				System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
			             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
			
				Thread.sleep(3000);
				MsgSender sender3 = factory.newNodeMsgSender("123456789","wokao");
				executor.execute(sender3);
				
				System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
			             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
			
				Thread.sleep(3000);
				MsgSender sender4 = factory.outlineMsgSender("123456789");
				executor.execute(sender4);
				
				System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
			             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
				
				
			
		}
	}
}
