package cn.antido.common.utils;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @Description PNW项目价格计算工具类
 * @author Antido
 * @date 2017年12月28日 下午6:47:56
 */
public class PriceUtils {
	
	/**
	 * 计算车位当前时间计费信息,返回字符串对象 
	 */
	public static String currentSubtotalStr(Date start, Integer price, Integer freeTime) {
		// 创建时间 - 当前时间  = 使用的时间
		long timeUsed = (System.currentTimeMillis() - start.getTime()) / 1000; //秒
		// 使用的时间 - 免费时间 = 计费时间
		int time =(int)timeUsed - freeTime; //秒
		if(time <= 0) {
			return "未满"+ freeTime/60 +"分钟不计费";
		} else {
			//不足1小时 按照1小时收费
			double subtotal = (((time / 3600) + 1) * price)/100;
			//转化为字符串对象
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			String format = decimalFormat.format(subtotal);
			return format+"元";
		}
		
	}
	
	/**
	 * 计算车位计费信息,返回字符串对象 
	 */
	public static String subtotalStr(Date start, Date end, Integer price, Integer freeTime) {
		// 创建时间 - 当前时间  = 使用的时间
		long timeUsed = System.currentTimeMillis() - start.getTime(); //毫秒
		// 使用的时间 - 免费时间 = 计费时间
		int time =(int) timeUsed / 1000 - freeTime; //秒
		if(time <= 0) {
			return "未满"+ freeTime/60 +"分钟不计费";
		} else {
			//不足1小时 按照1小时收费
			double subtotal = (((time / 3600) + 1) * price)/100;
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			String format = decimalFormat.format(subtotal);
			return format+"元";
		}
		
	}
	
	/**
	 * 计算车位小计 返回Integer对象 
	 */
	public static Integer subtotal(Date start, Date end, Integer price, Integer freeTime) {
		// 创建时间 - 当前时间  = 使用的时间
		long timeUsed = System.currentTimeMillis() - start.getTime(); //毫秒
		// 使用的时间 - 免费时间 = 计费时间
		int time =(int) timeUsed / 1000 - freeTime; //秒
		if(time <= 0) {
			return 0;
		} else {
			//不足1小时 按照1小时收费
			int subtotal = (((time / 3600) + 1) * price);
			return subtotal;
		}
		
	}
	
}
