package cn.antido.common.utils;

import java.util.UUID;
/**
 * @Description UUID工具类
 * @author Antido 
 * @date 2017年12月14日  下午8:13:17
 */
public class UUIDUtils {
	/**
	 * 获取32位UUID 
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
