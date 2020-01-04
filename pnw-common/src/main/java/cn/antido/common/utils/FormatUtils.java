package cn.antido.common.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 数据格式转换工具类
 * @author Antido
 * @date 2018年1月6日 下午7:20:57
 */
public class FormatUtils {
	
	/**
	 * yyyy年MM月dd日
	 */
	public static String dateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * yyyy年MM月dd日 HH:MM:SS
	 */
	public static String dateTimeFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 分转换为元,精确到小数点后2位 
	 */
	public static String priceFormat(Integer price) {
		double priceDouble = (double) price / 100;
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return 	decimalFormat.format(priceDouble);
	}
	
	/**
	 * 字符串形式以元为单位的数 转换为以分为单位的整数
	 */
	public static Integer priceFormat(String price) {
		Double priceDouble = Double.parseDouble(price);
		priceDouble = priceDouble * 100;
		return 	priceDouble.intValue();
	}
	
	/**
	 * 分转换为元,精确到小数点后2位 
	 */
	public static String priceFormat(Long price) {
		double priceDouble = (double) price / 100;
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return 	decimalFormat.format(priceDouble);
	}
	
	/**
	 * 经纬度转化,精确到小数点后6位 
	 */
	public static String eastAndNorthFormat(Integer num) {
		DecimalFormat decimalFormat = new DecimalFormat("0.000000");
		double numDouble = (double) num / 1000000;
		return decimalFormat.format(numDouble);
	}
	
	/**
	 * 经纬度转化,精确到小数点后6位 
	 */
	public static Double eastAndNorth2Double(Integer num) {
		return (double) num / 1000000;
	}
	
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
	
	/**
	 * 将int型的经纬度数据转化为精确到小数点后6位的正确经纬度格式
	 */
	/*public static String GPSFormat(int num) {
		
		return null;
	}*/
	
	/**
	 * 将字符串格式的经纬度数据转换为int型,去除小数点
	 */
	public static int GPS2Int(String str) {
		Double parseDouble = Double.parseDouble(str);
		return GPS2Int(parseDouble);
	}
	
	/**
	 * 将Double格式的经纬度转换为Int方便数据库储存
	 */
	public static int GPS2Int(Double dou) {
		return (int) (dou * 1000000);
	}
	
}
