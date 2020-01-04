package cn.antido.node.service.test;

import java.util.concurrent.ConcurrentHashMap;

public class HashTest {

	public static void main(String[] args) {
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String,Integer>();
		
		map.put("a", 1);
		map.put("b", 1);
		map.put("c", 1);
		map.put("d", 1);
		map.put("e", 1);
		map.put("a", 2);
		map.put("b", 3);
		
		String a = new String("abcdefgh");
		System.err.println(a.hashCode());
		String a1 = "abcdefgh";
		System.err.println(a1.hashCode());
		
		
		for (String s : map.keySet()) {
			System.out.println(s+":"+map.get(s));
		}
		
		
	}
}
