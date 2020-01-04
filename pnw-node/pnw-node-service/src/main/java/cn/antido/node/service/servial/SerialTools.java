package cn.antido.node.service.servial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import org.springframework.aop.ThrowsAdvice;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
/**
 * @Description 串口通讯工具类<br>
 * 通过该对象实现接受串口数据和和向串口发送数据的功能
 * @author Antido
 * @date 2018年3月13日 下午7:09:42
 */
public class SerialTools {
	
	private int DATABITS = SerialPort.DATABITS_8; //载波位（默认8）
	private int STOPBITS = SerialPort.STOPBITS_1; //停止位（默认1）
	private int PARITY = SerialPort.PARITY_NONE; //parity校验位（默认0无校验）
	
	private SerialPort serialPort; //串口对象
	private String portName; //端口名
	private int baudrate; //波特率
	
	
	/**
	 * 默认连接第一个可以使用的端口<br>
	 * 默认波特率:38400<br>
	 * 当未检测到可用端口时抛出异常
	 * @param portName 
	 * @param baudrate
	 */
	public SerialTools() {
		ArrayList<String> portList = getPortList();
		try {
			String portName = portList.get(0); //当未检测到可用端口时List为null
			this.portName = portName;
			this.baudrate = 38400;
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException("未找到可用端口，实例化失败!"); 
		}
	}
	
	/**
	 * 通过给定参数创建SerialUtils
	 * @param portName
	 * @param baudrate
	 */
	public SerialTools(String portName, int baudrate) {
		this.portName = portName;
		this.baudrate = baudrate;
	}
	
	/**
	 * 手动指定所有参数创建SerialUtils
	 * @param portName
	 * @param baudrate
	 * @param dataBits
	 * @param stopBits
	 * @param parity
	 */
	public SerialTools(String portName, int baudrate, int dataBits, int stopBits, int parity) {
		this.portName = portName;
		this.baudrate = baudrate;
		this.DATABITS = dataBits;
		this.STOPBITS = stopBits;
		this.PARITY = parity;
	}
	
	/**
	 * 串口初始化
	 * @throws NoSuchPortException 
	 * @throws UnsupportedCommOperationException 
	 * @throws PortInUseException 
	 */
	public void Init() throws PortInUseException, UnsupportedCommOperationException, NoSuchPortException {
		SerialPort port = openPort(portName, baudrate); //根据端口名和波特率打开串口
		this.setSerialPort(port);
		/*try {
		} catch (PortInUseException | UnsupportedCommOperationException | NoSuchPortException e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 通过端口名和波特率打开端口
	 * @throws NoSuchPortException 
	 * @throws PortInUseException 
	 * @throws UnsupportedCommOperationException 
	 */
	private SerialPort openPort(String portName, int baudrate) throws PortInUseException, UnsupportedCommOperationException, NoSuchPortException {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		//打开端口，并给端口名字和一个timeout（打开操作的超时时间）
		CommPort commPort = portIdentifier.open(portName, 3000);
		if(commPort instanceof SerialPort) { //判断是否是串口
			SerialPort serialPort = (SerialPort)commPort;
			//设置一下串口的波特率等参数
			serialPort.setSerialPortParams(baudrate, DATABITS, STOPBITS, PARITY);
			System.out.println(portName+":打开成功!");
			return serialPort;
		} else { //不是串口抛出异常
			throw new RuntimeException(portName+"不是RS232串口");
		}
		//return null;
	}
	
	
	/**
     * 关闭串口
     */
    public void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }
    
    /**
	 * 获取所有可以使用的端口名
	 */
	public ArrayList<String> getPortList() {
		 @SuppressWarnings("unchecked")
		 Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();//获得所有端口
		 ArrayList<String> portNames = new ArrayList<String>();
		 while(ports.hasMoreElements()) {
			 CommPortIdentifier port = ports.nextElement();
			 portNames.add(port.getName());
		 }
		 
		 return portNames;
	}
	
	/**
	 * 添加监听器<br>
	 * 需要输入一个监听器实现SerialPortEventListener接口<br>
	 * 一个串口同一时间只能有一个监听器,否则抛出TooManyListenersException
	 * @throws TooManyListenersException 
	 */
	public void addListener(SerialPortEventListener listener) throws TooManyListenersException {
		addListener(getSerialPort(),listener);
	}
	
	/**
     * 添加监听器
     * @param port     串口对象
     * @param listener 串口监听器
	 * @throws TooManyListenersException 
     */
    public void addListener(SerialPort port, SerialPortEventListener listener) throws TooManyListenersException  {
        //给串口添加监听器
    	System.out.println(port);
    	port.addEventListener(listener);
        //设置当有数据到达时唤醒监听接收线程
        port.notifyOnDataAvailable(true);
        //设置当通信中断时唤醒中断线程
        port.notifyOnBreakInterrupt(true);
    }
    
    class MySerialListener extends SerialListener{
    	
    }
    
    
    /**
     * @Description 串口监听器
     * @author Antido
     * @date 2018年3月12日 下午4:59:03
     */
    class SerialListener implements SerialPortEventListener {
    	
    	/**
    	 * 串口监听事件
    	 */
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
            	
                break;
			}
		}
    }
    
    /**
     * 读取串口数据
     * @param serialPort
     * @throws IOException
     */
    public void readComm(SerialPort serialPort) throws IOException {
    	InputStream in = serialPort.getInputStream();
		StringBuilder sb = new StringBuilder();
		byte buff[] =  new byte[255];
		int len ;
		while((len = in.read(buff)) > 0) {
			sb.append(new String(buff,0,len));
		}
		System.out.println(sb.length());
		System.out.println(sb.toString().trim());
    }
    
    /**
     * 向串口发送数据
     * @param msg
     * @throws IOException 
     */
    public void sendMsg(String msg) throws IOException {
    	sendMsg(this.serialPort,msg);
    }
    
    /**
     * 向串口发送信息
     * @param serialPort
     * @param msg
     * @throws IOException 
     */
    private void sendMsg(SerialPort serialPort, String msg) throws IOException {
        OutputStream out = null;
        out = serialPort.getOutputStream();
        out.write(msg.getBytes());
        out.flush();
    }
   
    public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public int getBaudrate() {
		return baudrate;
	}

	public void setBaudrate(int baudrate) {
		this.baudrate = baudrate;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}
	
    
}
