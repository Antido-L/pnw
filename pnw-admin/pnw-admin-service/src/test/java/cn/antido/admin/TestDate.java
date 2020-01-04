package cn.antido.admin;

import java.util.Date;

import org.junit.Test;

import com.alibaba.dubbo.rpc.protocol.dubbo.telnet.CurrentTelnetHandler;

public class TestDate {
	public static void main(String[] args) {
		String s = "辽宁省沈阳市经济技术开发区沈辽西路<span style='color:red'>111</span>号，沈阳工业大学中央校区，东门进入右转信息学院门";
		s = s.replaceAll("[^0-9|A-Za-z|\u4e00-\u9fa5|_]","");
		System.out.println(s);
	}
	
	@Test
	public void testDate() throws InterruptedException {
		Date date1 = new Date();
		Thread.sleep(3000);
		long currentTimeMillis = System.currentTimeMillis();
		Date date2 = new Date();
		System.out.println(date1.getTime()-currentTimeMillis);
	}
}
