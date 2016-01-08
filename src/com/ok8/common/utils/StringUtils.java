package com.ok8.common.utils;

/**
 * 字符串 工具类
 */
public class StringUtils {
	
	/**
	 * 验证字符串是否为NULL，或空字符串，或由空白符组成
	 * @param str 需要验证的字符串
	 * @return 验证成功，返回true；验证失败，返回false
	 * @author caiwl
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	/**
	 * 验证字符串是否为NULL，或空字符串
	 * @param str 需要验证的字符串
	 * @return 验证成功，返回true；验证失败，返回false
	 * @author caiwl
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}
	
	/**
	 * 验证字符串是否不为NULL，且不为空字符串，且不是由空白符组成
	 * @param str 需要验证的参数
	 * @return 验证成功，返回true；验证失败，返回false
	 * @author caiwl
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	/**
	 * 验证字符串是否不为NULL，且不为空字符串
	 * @param str 需要验证的字符串
	 * @return 验证成功，返回true；验证失败，返回false
	 * @author caiwl
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

}
