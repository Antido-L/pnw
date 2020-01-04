package cn.antido.search.service.test;

public class TestEE {
	public static void main(String[] args) {
		int a = 0;
		
		try {
			a++;
			int b = 1/0;
			a++;
		} catch (Exception e) {
			//a++;
		}
		
		System.out.println(a);
		
	}
}
