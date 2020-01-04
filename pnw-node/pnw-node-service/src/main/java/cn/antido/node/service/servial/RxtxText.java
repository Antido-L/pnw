package cn.antido.node.service.servial;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class RxtxText {
	public static void main(String[] args) throws TooManyListenersException, IOException, PortInUseException, UnsupportedCommOperationException, NoSuchPortException {
		final SerialTools serialUtils = new SerialTools();
		serialUtils.Init();
		serialUtils.addListener(new SerialPortEventListener() {
			
			@Override
			public void serialEvent(SerialPortEvent event) {
				switch (event.getEventType()) {

	            case SerialPortEvent.BI: // 10 通讯中断
	                
	            case SerialPortEvent.OE: // 7 溢位（溢出）错误

	            case SerialPortEvent.FE: // 9 帧错误

	            case SerialPortEvent.PE: // 8 奇偶校验错误

	            case SerialPortEvent.CD: // 6 载波检测

	            case SerialPortEvent.CTS: // 3 清除待发送数据

	            case SerialPortEvent.DSR: // 4 待发送数据准备好了

	            case SerialPortEvent.RI: // 5 振铃指示

	            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
	                break;
	            
	            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
	            	InputStream in;
	            	//获取串口数据
					try {
						in = serialUtils.getSerialPort().getInputStream(); 
						StringBuilder sb = new StringBuilder();
						byte buff[] =  new byte[255];
						int len ;
						while((len = in.read(buff)) > 0) {
							sb.append(new String(buff,0,len));
						}
						//处理一条串口发来的字符串数据
						if(sb.toString().trim().length() == 8) {
							portDataHandle(sb.toString().trim());
						}
						sb.delete(0, sb.length()); //清空缓冲区
					} catch (IOException e) {
						e.printStackTrace();
					}
	            	break;
				}
			}

			private void portDataHandle(String trim) {
				System.out.println(trim);
				
			}

		});
		/*serialUtils.addListener(new SerialPortEventListener() {
			
			@Override
			public void serialEvent(SerialPortEvent event) {
				try {
					InputStream in = port.getInputStream();
					StringBuilder sb = new StringBuilder();
					byte buff[] =  new byte[255];
					int len ;
					while((len = in.read(buff)) > 0) {
						sb.append(new String(buff,0,len));
					}
					System.out.println(sb.length());
					System.out.println(sb.toString().trim());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});*/
		
		
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()) {
			serialUtils.sendMsg(sc.nextLine());
		}
		sc.close();
	}
}
