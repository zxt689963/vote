package com.ok8.common.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;

public class ObjToHashMapUtil {

	public static HashMap<String, Object> parseJSON2HashMap(String json) {
		HashMap<String, Object> map = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(json,
					new TypeReference<HashMap<String, Object>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static String parseObject2JSON(Object obj) {
		String json = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			SerializationConfig config = mapper.getSerializationConfig();
			config.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			json = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

}
