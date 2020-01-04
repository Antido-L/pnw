package cn.antido.common.utils;

/**
 * @Description ip地址处理工具类
 * @author Antido
 * @date 2018年1月2日 下午4:42:17
 */
public class IPUtils {
	/** 
	 * 把字符串IP转换成long 
	 */  
	public static long ip2Long(String ipStr) {  
	    String[] ip = ipStr.split("\\.");  
        return (Long.valueOf(ip[0]) << 24) + (Long.valueOf(ip[1]) << 16)  
	                + (Long.valueOf(ip[2]) << 8) + Long.valueOf(ip[3]);  
    }  
	  
	/** 
	 * 把IP的long值转换成字符串 
	 */  
	public static String long2Ip(long ipLong) {  
	    StringBuilder ip = new StringBuilder();  
	    ip.append(ipLong >>> 24).append(".");  
	    ip.append((ipLong >>> 16) & 0xFF).append(".");  
	    ip.append((ipLong >>> 8) & 0xFF).append(".");  
        ip.append(ipLong & 0xFF);  
        return ip.toString();  
    }  
	  
    public static void main(String[] args) {  
        System.out.println(ip2Long("192.168.0.1"));  
	    System.out.println(long2Ip(3232235521L));  
	    System.out.println(ip2Long("10.0.0.1"));  
	}  
}
