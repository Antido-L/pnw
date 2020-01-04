package cn.antido.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description 加密工具类
 * @author Antido
 * @date 2018年6月13日 下午3:33:29
 */
public class CodeUtils {
	
	/**
	 * 获取与节点控制端之间通讯的校验码
	 * @param key
	 * @return
	 * @throws InterruptedException 
	 */
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println(getConnCode("a")[0]);
		Thread.sleep(1000);
		System.out.println(getConnCode("a")[0]);
		Thread.sleep(1000);
		System.out.println(getConnCode("a")[0]);
		Thread.sleep(1000);
		System.out.println(getConnCode("a")[0]);
		Thread.sleep(1000);
		System.out.println(getConnCode("a")[0]);
		Thread.sleep(1000);
		System.out.println(getConnCode("a")[0]);
		Thread.sleep(1000);
		System.out.println(getConnCode("a")[0]);
		Thread.sleep(1000);
		System.out.println(getConnCode("a")[0]);
	}
	
	/**
	 * 根据密钥获取连接校验码<br>
	 * 校验码最多有两个
	 * @param key
	 * @return
	 */
	public static String[] getConnCode(String key) {
		if(key == null || "".equals(key)) {
			throw new RuntimeException("getConnCode 必须传入有效的密钥 , 不能为null或者空值!");
		}
		
		//获取时间戳 取最小单位为10s
		long time = System.currentTimeMillis();
		time /= 10000L;
		
		String[] res = new String[2];
		
		//对时间戳MD5一次 将结果与密钥相加再MD5一次
		//当接收方解密时有可能时间戳的值相差1
		if(time % 2 == 0) {
			
			String timeMD5 = DigestUtils.md5Hex(time + "");
			res[0] = DigestUtils.md5Hex(timeMD5 + key);
		} else {
			//当时间戳是奇数时分别向下和向上加一 分别加密结果
			String timeMD5 = DigestUtils.md5Hex((time+1)+"");
			res[0] = DigestUtils.md5Hex(key + timeMD5);
			timeMD5 = DigestUtils.md5Hex((time-1)+"");
			res[1] = DigestUtils.md5Hex(key + timeMD5);
		}
		
		return res;
	}
	
}
