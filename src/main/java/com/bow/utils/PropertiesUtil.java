package com.bow.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wwxiang
 * @since 2017/12/28.
 */
public class PropertiesUtil {

	public static Properties parse(String content) throws IOException {
		StringReader reader = new StringReader(content);
		Properties properties = new Properties();
		properties.load(reader);
		return properties;
	}

	public static Map<String, String> format(Properties props) {
		Map<String, String> result = new ConcurrentHashMap<>();
		for (Object key : props.keySet()) {
			String keyStr = (String) key;
			result.put(keyStr, props.getProperty(keyStr));
		}
		return result;
	}
}
