package cn.antido.node.service.servial;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * @Description 串口事件监听器
 * @author Antido
 * @date 2018年7月11日 上午9:50:56
 */
public abstract class ServialListenerHandle implements SerialPortEventListener {

	@Override
	public void serialEvent(SerialPortEvent serialPortEvent) {
		switch (serialPortEvent.getEventType()) {

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
        	dataArriveHandle();
            break;
		}
		
	}

	abstract void dataArriveHandle();

}
