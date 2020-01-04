package cn.antido.connection.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
	
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date(new Date().getTime() + 60 * 1000));
		System.out.println(time);
		
	}
}
