package cn.antido.connection.test;

import java.util.LinkedList;

public class SynTest {
	public static void main(String[] args) throws InterruptedException {
		LinkedList<Integer> list = new LinkedList<Integer>();
		P1 p1 = new P1();
		Thread t1 = new Thread(new T1(p1));
		Thread t2 = new Thread(new T2(p1));
		Thread t3 = new Thread(new T3(p1));
		
		t1.start();
		t1.join();
		
		t2.start();
		t3.start();
		
		t2.join();
		t3.join();
		
		int count = 0;
		for (Integer i : list) {
			count++;
			if(count == 100) {
				System.out.println();
				count = 0;
			}
			System.out.printf("%d ",i);
		}
		
	}
	
	static class T1 implements Runnable {
		
		private P1 p;

		public T1(P1 p) {
			this.p = p;
		}
		
		@Override
		public void run() {
			synchronized (p) {
				while(true) {
					System.out.println("第一层");
					synchronized (p) {
						System.out.println("第2层");
					}
				}
				
			}
		}
	}
	
	static class T2 implements Runnable {

		private P1 p;

		public T2(P1 p) {
			this.p = p;
		}
		
		@Override
		public void run() {
			for (;;) {
				p.print(p + "T2");
			}
			
		}
		
	}
	
	static class T3 implements Runnable {
		
		private P1 p;

		public T3(P1 p) {
			this.p = p;
		}
		
		@Override
		public void run() {
			for (;;) {
				p.print(p + "T3");
			}
			
		}
	}
	
	static class P1 {
		
		public void print(String msg) {
			System.out.println(msg);
		}
		
	}

}

