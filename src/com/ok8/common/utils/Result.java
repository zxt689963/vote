package com.ok8.common.utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Servlet与Service交互对象
 * @author caiwl
 */
public class Result {
	
	/**
	 * 读取表格（不分页）
	 * @param rows
	 * @return
	 */
	public static HashMap<String, Object> get(ArrayList<HashMap<String, Object>> rows) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if (CollectionUtils.isEmpty(rows)) {
			rows = new ArrayList<HashMap<String, Object>>();
		}
		result.put("rows", rows);
		return result;
	}
	
	@Deprecated
	public static HashMap<String, Object> get(boolean success, String message) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		return result;
	}
	
	/**
	 * 新增、修改、作废、删除、提交、串号扫描
	 * @param success
	 * @param errorCode
	 * @return
	 */
	public static HashMap<String, Object> get(boolean success, int errorCode) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("errorCode", errorCode);
		return result;
	}
	
	@Deprecated
	public static HashMap<String, Object> get(boolean success, String message, Object data) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		result.put("data", data);
		return result;
	}
	
	/**
	 * 读取表格（分页）
	 * @param total
	 * @param rows
	 * @return
	 */
	public static HashMap<String, Object> get(int total, ArrayList<HashMap<String, Object>> rows) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		if (CollectionUtils.isEmpty(rows)) {
			rows = new ArrayList<HashMap<String, Object>>();
		}
		result.put("rows", rows);
		return result;
	}
	
	/**
	 * 读取表格（分页、状态）
	 * @param total
	 * @param rows
	 * @param states
	 * @return
	 */
	public static HashMap<String, Object> get(int total, ArrayList<HashMap<String, Object>> rows,
			ArrayList<HashMap<String, Object>> states) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		if (CollectionUtils.isEmpty(rows)) {
			rows = new ArrayList<HashMap<String, Object>>();
		}
		result.put("rows", rows);
		if (CollectionUtils.isEmpty(states)) {
			states = new ArrayList<HashMap<String, Object>>();
		}
		result.put("states", states);
		return result;
	}
	
	/**
	 * 读取详情（表单、表格）
	 * @param form
	 * @param rows
	 * @return
	 */
	public static HashMap<String, Object> get(HashMap<String, Object> form,
			ArrayList<HashMap<String, Object>> rows) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if (CollectionUtils.isEmpty(form)) {
			form = new HashMap<String, Object>();
		}
		if (CollectionUtils.isEmpty(rows)) {
			rows = new ArrayList<HashMap<String, Object>>();
		}
		result.put("form", form);
		result.put("rows", rows);
		return result;
	}
	
	/**
	 * 分分页查询
	 * @param rows 当前页记录
	 * @param pageNumber 当前页页码
	 * @param pageSize 分页大小
	 * @param totalPage 总页数
	 * @param total 总记录数
	 * @return
	 */
	public static HashMap<String, Object> getPage(ArrayList<HashMap<String, Object>> rows,
			int pageNumber, int pageSize, int totalPage, int total) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if (CollectionUtils.isEmpty(rows)) {
			rows = new ArrayList<HashMap<String, Object>>(0);
		}
		result.put("rows", rows);
		result.put("pageNumber", pageNumber);
		result.put("pageSize", pageSize);
		result.put("totalPage", totalPage);
		result.put("total", total);
		return result;
	}

}
