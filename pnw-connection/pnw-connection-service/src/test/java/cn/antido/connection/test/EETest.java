package cn.antido.connection.test;

import cn.antido.connection.pojo.ConfirmTarget;

public class EETest {
	public static void main(String[] args) {
		/*ConfirmTarget t = new ConfirmTarget();
		
		System.out.println(t.getConfirmTimes());
		
		t.setConfirmTimes(t.getConfirmTimes()+1);
		System.out.println(t.getConfirmTimes());
		
		t.setConfirmTimes(t.getConfirmTimes()+1);
		System.out.println(t.getConfirmTimes());
		
		t.setConfirmTimes(t.getConfirmTimes()+1);
		System.out.println(t.getConfirmTimes());*/
		
		new Thread(new T1()).start();
		
	}
	
}

class T1 implements Runnable {
	

	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("T1 is run");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int a = 1/0;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void print() {
		System.out.println(Thread.currentThread() + "T1");
	}
	
}

class T2 implements Runnable {

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(2000);
				T1 t1 = new T1();
				t1.print();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void print() {
		System.out.println(Thread.currentThread() + "T2");
	}
	
}