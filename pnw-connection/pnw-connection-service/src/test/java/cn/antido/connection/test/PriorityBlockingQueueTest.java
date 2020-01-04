package cn.antido.connection.test;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueTest {
	public static void main(String[] args) throws InterruptedException {
		PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<Integer>();
		T1 t1 = new T1(queue);
		new Thread(t1).start();;
		new Thread(new T2(queue)).start();
		
		while(true) {
			Integer i = queue.take();
			Thread.sleep(30);
			System.out.println(i);
		}
		
		
	}
	
	static class T1 implements Runnable {
		
		private PriorityBlockingQueue<Integer> queue;

		public T1(PriorityBlockingQueue<Integer> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true)
			for(int i = 0 ; i < 3 ; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				queue.add(i);
			}
		}
		
	}

	static class T2 implements Runnable {
		
		private PriorityBlockingQueue<Integer> queue;

		public T2(PriorityBlockingQueue<Integer> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true)
			for(int i = 1 ; i < 5 ; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				queue.add(i);
			}
		}
		
	}

	
}

