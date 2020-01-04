package cn.antido.connection.test;

import java.util.Iterator;

import cn.antido.connection.service.list.CheckAndRemoveList;

public class QueueTest {
	public static void main(String[] args) {
		CheckAndRemoveList<Integer> list = new CheckAndRemoveList<Integer>();
		/*list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);*/
		//memoryTest(list);
		//addTest(list);
		//iteratorTest(list);
		Integer a = new Integer(1);
		Integer b = new Integer(2);
		
	}

	private static void memoryTest(CheckAndRemoveList<Integer> list) {
		while(true) {
			for (int i = 0; i < 100000; i++) {
				for (int j = 0; j < 3; j++) {
					list.add(new Integer(j));
				}
			}
			
			Iterator<Integer> it0 = list.iterator();
			while(it0.hasNext()) {
				Integer next = it0.next();
				if(next.equals(0)) {
					it0.remove();
				}
			}
			
			Iterator<Integer> it1 = list.iterator();
			while(it1.hasNext()) {
				Integer next = it1.next();
				if(next.equals(1)) {
					it1.remove();
				}
			}
			
			Iterator<Integer> it2 = list.iterator();
			while(it2.hasNext()) {
				Integer next = it2.next();
				if(next.equals(2)) {
					it2.remove();
				}
			}
			
			int count = 0;
			for (Integer integer : list) {
				//System.out.println(integer);
				count++;
			}
			System.out.println(count);
		}
	}

	private static void iteratorTest(CheckAndRemoveList<Integer> list) {
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()) {
			Integer i = it.next();
			if(i == 2)
				it.remove();
			System.out.println(i);
		}
		System.out.println("*****************");
		
		Iterator<Integer> it1 = list.iterator();
		System.out.println(it);
		System.out.println(it1);
		while(it1.hasNext()) {
			Integer i = it1.next();
			if(i == 2)
				it1.remove();
			System.out.println(i);
			
		}
		System.out.println("*****************");
		
		Iterator<Integer> it2 = list.iterator();
		while(it2.hasNext()) {
			Integer i = it2.next();
			if(i == 1)
				it2.remove();
			System.out.println(i);
			
		}
		System.out.println("*****************");
		
		Iterator<Integer> it3 = list.iterator();
		while(it3.hasNext()) {
			Integer i = it3.next();
			if(i == 2)
				it3.remove();
			System.out.println(i);
			
		}
		System.out.println("*****************");
		
		for (Integer integer : list) {
			System.out.println(integer);
		}
	}

	private static void addTest(CheckAndRemoveList<Integer> list) {
		/*System.out.println(list.popHead());
		System.out.println(list.popTail());
		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		
		System.out.println(list.popTail());
		list.add(5);
		System.out.println(list.popHead());
		System.out.println(list.popHead());
		list.add(5);
		System.out.println(list.popHead());
		list.add(5);
		System.out.println(list.popHead());
		System.out.println(list.popHead());
		System.out.println(list.popHead());
		System.out.println(list.popHead());
		System.out.println(list.popTail());*/
	}
}
