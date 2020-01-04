package cn.antido.node.service.test;

public class EchoTest {
	public static void main(String[] args) {
		String data = "00-**313";
		String info[] = data.split("-");
		String nodeName = info[0];
		
		//获取传感器距离值
		int numIndex = 0;
		while(info[1].charAt(numIndex) == '*') {
			numIndex++;
		}
		String echoState = info[1].substring(numIndex, info[1].length());
		int echoTime = Integer.parseInt(echoState);
		
		//判断接近状态
		boolean isClose = false;
		if(echoTime < 8000) {
			isClose = true;
		}
		
		System.out.println(nodeName);
		System.out.println(echoTime);
		System.out.println(isClose);
	}
}
