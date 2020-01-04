package cn.antido.admin;

import java.util.Date;

import org.junit.Test;

import com.sun.tools.internal.xjc.model.SymbolSpace;

public class TestEE {
	
	@Test
	public void testFinal() {
		String s = "10-";
		String[] v = s.split("-");
		System.out.println(v.length);
	}
	
	
	@Test
	public void test2() {
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date();
		long time = date.getTime();
		System.out.println(currentTimeMillis);
		System.out.println(time);
		
	}
	
	
	@Test
	public void test1() {
		String a = "XXX002100";
		System.out.println(Integer.parseInt(a.substring(3, 3+3)));
		System.out.println(Integer.parseInt(a.substring(3+3)));
		StringBuffer sb = new StringBuffer();
		sb.append((char)97);
		char[] charArray = a.toCharArray();
	}
	
	public static void main(String[] args) {
		String parkId = "XXXXXX";
		Integer row = 3;
		Integer col = 3;
		String info = "1,2,3,0,2,0,0,1,2";
		String[] split = info.split(",");
		int currentRow = 0;
		int currentCol = 0;
		StringBuffer sb = new StringBuffer();
		String id = "";
		for (int i = 0; i < split.length; i++) {
			if(col.equals(currentCol)) { //超过当前行的最后一个元素
				currentCol = 0;
				currentRow++;
			}
			if(!"0".equals(split[i])) { //入库
				//把整型数据高位补0
				sb.append(currentRow);
				while (sb.length() < 3) {
					sb.insert(0, "0");
				}
				sb.append(currentCol);
				while (sb.length() < 6) {
					sb.insert(3, "0");
				}
				id = parkId + sb.toString();
				sb.delete(0, sb.length());
				System.out.println(new Byte(split[i]));
			}
			currentCol++;
			System.out.println(id);
		}
	}
}

class Cat extends Animal{
	
	private String age;

	Cat() {
		super();
		System.out.println("Cat 构造");
	}

	Cat(String age) {
		super();
		this.age = age;
	}
	
}

class Animal {
	static {
		System.out.println("static 代码块");
	}
	
	Animal() {
		super();
		System.out.println("animal super");
	}
	
}