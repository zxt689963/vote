package com.ok8.common.utils;

import java.util.List;
import java.util.Map;

/**
 * 集合 工具类
 */
public class CollectionUtils {

	/**
	 * 验证ArrayList集合是否为空，或集合内容为空
	 * @param list 需要验证的ArrayList集合
	 * @return 验证成功，返回true；验证失败，返回false
	 * @author caiwl
	 */
	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	/**
	 * 验证HashMap集合是否为NULL，或集合内容为空
	 * @param map 需要验证的HashMap集合
	 * @return 验证成功，返回true；验证失败，返回false
	 * @author caiwl
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 验证ArrayList集合是否不为空，且集合内容不为空
	 * @param list 需要验证的ArrayList集合
	 * @return 验证成功，返回true；验证失败，返回false
	 * @author caiwl
	 */
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	/**
	 * 验证HashMap集合是否不为NULL，且集合内容不为空
	 * @param map 需要验证的HashMap集合
	 * @return 验证成功，返回true；验证失败，返回false
	 * @author caiwl
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
	
	/**
	 * map循环put值公共方法
	 * @param str
	 * @param rs
	 * @return
	 */
//	public static HashMap<String, Object> map(String[] str,ResultSet rs){
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		int j = str.length;
//		for (int i = 0; i < j; i++) {
//			try {
//				Object obj = rs.getObject(i+1);
//				map.put(str[i], obj == null ? "" : obj.toString());
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return map;
//	}
//	
//	public static void prepStmt(PreparedStatement pstmt,HashMap<String, Object> map,String[] cols){
//		PreparedStatement prep = pstmt;
//		int j = cols.length;
//		for (int i = 0; i < j; i++) {
//			Object obj = map.get(cols[i]);
//			try {
//				if(obj == null || obj.equals("")){//如果前台未传该参数或者前台传的参数为空字符串
//					prep.setObject(i+1, null);
//				}else{
//					prep.setObject(i+1, obj);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
