package cn.antido.node.service.test;

import java.util.HashMap;
import java.util.Scanner;

public class ThreadTest {
	public static void main(String[] args) {
		T1 t1 = new T1();
		Thread th1 = new Thread(t1);
		Thread th2 = new Thread(t1);
		Thread th3 = new Thread(t1);
		th1.start();
		th2.start();
		th3.start();
	}
	
	
	
	
	
	

	private static void hash() {
		HashMap<Integer, String> map = new HashMap<Integer,String>();
		map.put(1, "a");
		map.put(2, "b");
		map.put(3, "c");
		map.put(3, "d");
		PrintThread t1 = new PrintThread(map);
		Thread thread = new Thread(t1);
		thread.start();
		System.out.println("线程名:"+thread.getName()+"已经启动");
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()) {
			String str = sc.nextLine();
			String[] split = str.split("-");
			map.put(Integer.parseInt(split[0]), split[1]);
		}
	}
}

class T1 implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(Thread.currentThread().getName());
				System.out.println("T1 is working");
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class PrintThread implements Runnable {
	
	private HashMap<Integer,String> map;
	
	public PrintThread(HashMap<Integer,String> map) {
		this.map = map;
	}
	
	@Override
	public void run() {
		while(true) {
			Thread th=Thread.currentThread();
			System.out.println(th+"正在运行");
			for (Integer i : map.keySet()) {
				System.out.println(i+":"+map.get(i));
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}