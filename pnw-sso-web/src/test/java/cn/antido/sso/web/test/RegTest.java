package cn.antido.sso.web.test;

import java.util.regex.Pattern;

public class RegTest {
	public static void main(String[] args) {
		String isOpCode = "[0-9]{6}";
		boolean isValid = Pattern.matches(isOpCode, "01244");
		
		System.out.println(isValid);
	}
}
