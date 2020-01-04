package cn.antido.admin.web.test;

import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.web.util.HtmlUtils;

public class TestValid {
	@Test
	public void testValid() {
		Integer num = null;
		Integer num1 = 123123123;
		Integer num2 = 123;
		System.out.println(num == null || num > 2313);
		
	}
	
	@Test
	public void testXSS(){
		String content = "<script src='${pageContext.request.contextPath}/js/antido.js'></script>";
		System.out.println(content.length());
		String str = HtmlUtils.htmlEscape(content);
		System.out.println(str.length());
		System.out.println(str);
	} 
	
	@Test
	public void testIP(){
		String content = "1.1.1.1";
		String pattern = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)($|(?!\\.$)\\.)){4}$";
		boolean isMatch = Pattern.matches(pattern, content);
		System.out.println(isMatch);
	} 
}	
