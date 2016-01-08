package com.ok8.common.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;

/**
 * Jackson 工具类
 */
public class JacksonUtils {
	
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		SerializationConfig config = mapper.getSerializationConfig();
//		config.setSerializationInclusion(Inclusion.NON_NULL); // 只针对对象，对List、Map不起作用
		config.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 将Java对象转换成JSON格式字符串
	 * @param obj Java对象
	 * @return JSON格式字符串
	 */
	public static String writeValueAsString(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将JSON格式字符串转换成HashMap集合
	 * @param json JSON字符串
	 * @return HashMap集合
	 * @throws Exception 
	 */
	public static HashMap<String, Object> readValueAsHashMap(String json)
			throws Exception {
		if (json == null || json.isEmpty()) {
			return null;
		}
		return mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});
	}

	/**
	 * 将JSON格式字符串转换成HashMap集合
	 * @param json JSON字符串
	 * @return HashMap集合
	 * @throws Exception 
	 */
	public static HashMap<String, Object> readValueAsHashMapQuietly(String json) {
		if (json == null || json.isEmpty()) {
			return null;
		}
		try {
			return mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});
		} catch (Exception e) {
			return null;
		}
	}

	// 工具类禁止new实例
	private JacksonUtils() {
	}

}
