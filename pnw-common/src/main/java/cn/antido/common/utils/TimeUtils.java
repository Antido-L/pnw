package cn.antido.common.utils;

/**
 * @Description 时间处理工具类
 * @author Antido
 * @date 2017年12月28日 下午4:52:12
 */
public class TimeUtils {
	
	/**
	 * 将毫秒值转换为日期字符串 
	 */
	public static String timeCalculate(Long minusTime) {
		if(minusTime == null || minusTime == 0L) {
			return null;
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		
		long sec = minusTime / 1000; //一共多少秒
		
		if(sec % 60 != 0) {
			stringBuffer.insert(0, sec % 60 +"秒");
		}
		if(sec > 60) {
			long min = sec / 60;  //一共多少分
			if(min % 60 != 0) {
				stringBuffer.insert(0, min % 60 +"分钟");
			}
			if(min > 60) {
				long hour = min / 60; //一共多少小时
				if(hour % 24 != 0) {
					stringBuffer.insert(0, hour % 24 +"小时");
				}
				if(hour > 24) {
					long day = hour / 24;
					stringBuffer.insert(0, day+"天");
				}
			}
		} 
		return stringBuffer.toString();
	}
	
}
