package cn.antido.node.service.test;

public class DatahandleTest {
	public static void main(String[] args) {
		String data = "00-P23-1";
		
		System.out.println(data.substring(0, 2));
		System.out.println(data.substring(data.length()-1, data.length()));
	}
}
