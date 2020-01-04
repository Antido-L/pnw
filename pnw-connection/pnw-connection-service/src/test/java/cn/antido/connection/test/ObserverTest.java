package cn.antido.connection.test;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class ObserverTest {
	
	/**
	 * 被观察者
	 * 父类维护着一个Vector()
	 */
	static class Obser extends Observable implements Runnable {
		
		public String msg = "observer";
		
		/**
		 * 新增观察者
		 */
		@Override
		public synchronized void addObserver(Observer o) {
			super.addObserver(o);
		}
		
		/**
		 * 移除观察者
		 */
		@Override
		public synchronized void deleteObserver(Observer o) {
			super.deleteObserver(o);
		}

		@Override
		public void run() {
			while(true) {
				try {
					System.out.println("observice is running");
					Thread.sleep(3000);
					int a = 1/0;
					System.out.println("before error");
					
				} catch (Exception e) {
					System.out.println("observer in catch");
					e.printStackTrace();
					doBusiness();
					break; //结束当前线程
				}
			}
			
		}
		
		private void doBusiness() {
			setChanged();
			notifyObservers();
		}
		
	}
 	
	
	/**
	 * 观察者
	 */
	static class listener implements Observer {
		
		private String name;
		
		public listener(String name) {
			this.name = name;
		}

		@Override
		public void update(Observable o, Object arg) {
			 System.out.println("listener is running");
			 Obser obser = new Obser();
			 obser.addObserver(this);
			 new Thread(obser).start();
		}
		
	}
	
	/**
	 * 测试方法
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Obser obser = new Obser();
		
		//新增三个观察者
		obser.addObserver(new listener("l1"));
		//obser.addObserver(new listener("l2"));
		//obser.addObserver(new listener("l3"));
		
		//启动测试线程
		Thread thread = new Thread(obser);
		thread.start();
		while(true) {
			Thread.sleep(5000);
			System.out.println(thread.isAlive());
		}
		
	}
	
	
	
}
