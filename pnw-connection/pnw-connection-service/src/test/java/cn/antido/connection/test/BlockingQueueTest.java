package cn.antido.connection.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueTest {
	
	
	
	public static void main(String[] args) {
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
		
		T1 t1 = new T1(queue);
		T2 t2 = new T2(queue);
		
		t1.start();
		t2.start();
		
		while(true) {
			try {
				//System.out.println(queue.peek());
				System.out.println(queue.poll(1,TimeUnit.SECONDS));
				//queue.take();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	static class T1 extends Thread {
		private LinkedBlockingQueue<Integer> queue;
		
		public T1(LinkedBlockingQueue<Integer> queue) {
			this.queue = queue;
		
		}
		
		@Override
		public void run() {
			//super.run();
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				queue.add(new Integer(1));
				/*try {
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}
		}
		
	}
	
	static class T2 extends Thread {
		private LinkedBlockingQueue<Integer> queue;
		
		public T2(LinkedBlockingQueue<Integer> queue) {
			this.queue = queue;
		
		}
		
		@Override
		public void run() {
			//super.run();
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				queue.add(new Integer(2));
				try {
					queue.put(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
