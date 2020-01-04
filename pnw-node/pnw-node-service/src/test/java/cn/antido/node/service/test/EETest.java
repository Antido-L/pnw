package cn.antido.node.service.test;

public class EETest {
	
	private static String num = "abc";
	
	public static void main(String[] args) {
		//String num = "abc";
		add(num);
		System.out.println(num);
	}
	
	public static void add(String n) {
		num += "d";
		//return ++n;
	}
}
