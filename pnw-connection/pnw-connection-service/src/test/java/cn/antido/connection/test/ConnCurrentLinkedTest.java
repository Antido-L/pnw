package cn.antido.connection.test;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnCurrentLinkedTest {
	public static void main(String[] args) {
		ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
		
		Thread t1 = new Thread(new T1(queue));
		Thread t2 = new Thread(new T2(queue));
		Thread t3 = new Thread(new T3(queue));
		
		t1.start();
		t2.start();
		t3.start();
		
		while(true) {
			System.out.println("**************************************");
			for (Integer i : queue) {
				System.out.println(i);
			}
		}
		
		
		
	}
	
	static class T1 implements Runnable{
		private ConcurrentLinkedQueue<Integer> queue;

		public T1(ConcurrentLinkedQueue<Integer> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true) {
				queue.add(1);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	static class T2 implements Runnable{
		private ConcurrentLinkedQueue<Integer> queue;

		public T2(ConcurrentLinkedQueue<Integer> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true) {
				queue.add(2);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	static class T3 implements Runnable{
		private ConcurrentLinkedQueue<Integer> queue;

		public T3(ConcurrentLinkedQueue<Integer> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true) {
				queue.add(3);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
