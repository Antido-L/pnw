package cn.antido.connection.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadTest {
	public static void main(String[] args) throws InterruptedException {
		
		Stack<Integer> stack = new Stack<Integer>();
		/*
		Th1 th1 = new Th1(stack);
		Th2 th2 = new Th2(stack);
		
		new Thread(th1).start();
		new Thread(th2).start();
		
		while(true) {
			Thread.sleep(100000);
		}*/
		/*System.out.println("main:" + Thread.currentThread());
		
		Th3 t = new Th3(stack,"TH3");
		t.start();
		while(true) {
			Thread.sleep(3000);
			System.out.println(stack.pop());
		}*/
		
		ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<Integer>();
		q.offer(1);
		q.offer(2);
		q.offer(3);
		while(!q.isEmpty()) {
			System.out.println(q.poll());
		}
		
	}
}


class Th1 implements Runnable {
	private Stack<Integer> stack;

	public Th1(Stack<Integer> stack) {
		this.stack = stack;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(3000);
				//stack.push(0);
				Date date = new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
				String format = simpleDateFormat.format(date);
				System.out.println("3s:" + format);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}


class Th2 implements Runnable {
	
	private Stack<Integer> stack;

	public Th2(Stack<Integer> stack) {
		this.stack = stack;
	}

	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(5000);
				Date date = new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
				String format = simpleDateFormat.format(date);
				System.out.println("5s:" + format);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}

class Th3 extends Thread {
	
	private Stack<Integer> stack;
	private String threadName;
	private Thread t;
	
	
	public Th3(Stack<Integer> stack, String threadName) {
		this.stack = stack;
		this.threadName = threadName;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + "TH3");
				stack.push(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//super.run();
	}
	
	@Override
	public synchronized void start() {
		if(t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
}
