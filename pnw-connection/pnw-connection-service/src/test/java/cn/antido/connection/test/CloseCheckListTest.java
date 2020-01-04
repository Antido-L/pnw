package cn.antido.connection.test;

import java.util.Iterator;

import cn.antido.connection.service.list.CheckAndRemoveList;

public class CloseCheckListTest {
	public static void main(String[] args) throws Exception {
		CheckAndRemoveList<Integer> list = new CheckAndRemoveList<Integer>();
		
		Thread t0 = new Thread(new T0(list));
		Thread t1 = new Thread(new T1(list));
		Thread t2 = new Thread(new T2(list));
		
		t0.start();
		t1.start();
		t2.start();
		while(true){
			for (int i = 0; i < 3; i++) {
				Thread.sleep(100);
				Iterator<Integer> it = list.iterator();
				System.out.println("get iterator");
				int count = 0;
				while(it.hasNext()) {
					Integer next = it.next();
					if(next.equals(i)) {
						it.remove();
						count++;
					}
				}
				System.out.println("remove "+ i +"共" + count);
			}
		}
		
	}
	
	static class T0 implements Runnable {

		private CheckAndRemoveList<Integer> list;
		
		public T0(CheckAndRemoveList<Integer> list) {
			this.list = list;
		}
		
		@Override
		public void run() {
			int count = 0;
			while(true) {
				if(count++ == 1000) {
					count = 0;
					System.out.println("T0 添加了1000次!");
				}
				list.add(new Integer(0));
			}
			
		}
		
	}
	
	static class T1 implements Runnable {

		private CheckAndRemoveList<Integer> list;
		
		public T1(CheckAndRemoveList<Integer> list) {
			this.list = list;
		}
		
		@Override
		public void run() {
			int count = 0;
			while(true) {
				if(count++ == 1000) {
					count = 0;
					System.out.println("T1 添加了1000次!");
				}
				list.add(new Integer(1));
				
			}
			
		}
		
	}
	
	static class T2 implements Runnable {

		private CheckAndRemoveList<Integer> list;
		
		public T2(CheckAndRemoveList<Integer> list) {
			this.list = list;
		}
		
		@Override
		public void run() {
			int count = 0;
			while(true) {
				if(count++ == 1000) {
					count = 0;
					System.out.println("T2 添加了1000次!");
				}
				list.add(new Integer(2));
				
			}
			
		}
		
	}
	
}
