package cn.antido.common.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @Description 通用JSON工具类,使用Jackson
 * @author Antido
 * @date 2018年6月6日 上午9:46:32
 */
public class JsonUtils {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public JsonUtils() {
		
	}
	
	/**
	 * 对象转json 
	 * @param obj
	 */
	public static String obj2Json(Object obj) {
		try {
			String json = mapper.writeValueAsString(obj);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * json转Object
	 */
	public static <T> T json2Obj(String json, Class<T> clazz) {
		try {
			T t = mapper.readValue(json, clazz);
			return t;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
