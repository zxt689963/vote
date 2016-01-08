package com.ok8.common.global;

/**
 * 操作结果信息，用于Service
 * @author caiwl
 */
public final class Message {
	
	/** 操作成功  */
	public static final String SUCCESS = "操作成功";
	/** 登录成功  */
	public static final String SUCCESS_LOGIN = "登录成功";
	/** 读取操作成功  */
	public static final String SUCCESS_READ = "读取成功";
	/** 新增操作成功  */
	public static final String SUCCESS_CREATE = "新增成功";
	/** 修改操作成功  */
	public static final String SUCCESS_UPDATE = "修改成功";
	/** 删除操作成功  */
	public static final String SUCCESS_DELETE = "删除成功";
	/** 保存草稿成功  */
	public static final String SUCCESS_SAVE = "保存草稿成功";
	/** 提交成功  */
	public static final String SUCCESS_SUBMIT = "提交成功";
	
	/** 操作失败  */
	public static final String ERROR = "操作失败";
	/** 登录失败  */
	public static final String ERROR_LOGIN = "登录失败";
	/** 读取操作失败  */
	public static final String ERROR_READ = "读取失败";
	/** 新增操作失败  */
	public static final String ERROR_CREATE = "新增失败";
	/** 修改操作失败  */
	public static final String ERROR_UPDATE = "修改失败";
	/** 删除操作失败  */
	public static final String ERROR_DELETE = "删除失败";
	/** 保存草稿失败  */
	public static final String ERROR_SAVE = "保存草稿失败";
	/** 提交失败  */
	public static final String ERROR_SUBMIT = "提交失败";
	
	/** 未找到匹配的Action */
	public static final String ERROR_NO_ACTION = "未找到匹配的Action，请检查Action名称是否错误！";
	/** 发生Java异常  */
	public static final String ERROR_JAVA = "JAVA程序异常，请联系系统管理员！";
	/** 发生数据库异常  */
	public static final String ERROR_DATABASE = "数据库操作异常，请联系系统管理员！";
	/** 重复新增  */
	public static final String ERROR_SAME = "新增内容已存在";
	/** 单号重复  */
	public static final String ERROR_SAME_ID = "单号重复";
	/** 编码重复  */
	public static final String ERROR_SAME_CODE = "编码重复";
	/** 名称重复  */
	public static final String ERROR_SAME_NAME = "名称重复";
	/** 部分串号已经被使用  */
	public static final String ERROR_BIND_SAME_SERIAL_NUM = "部分串号已经被使用，请重新选择！";
	/** 删除父类  */
	public static final String ERROR_EXIST_CHILDREN = "删除项中的某些项存在子项，无法直接删除！";
	/** 参数传递异常  */
	public static final String ERROR_PARAMS = "参数传递异常";
	/** JSON参数格式异常，用于所有Servlet解析JSON异常时返回前台信息 */
	public static final String ERROR_JSON = "{\"success\":false,\"message\":\"JSON参数格式不正确！\"}";
	
	/** 回收订单指派回收员时，推送消息给回收员微信企业账号 */
	public static final String WXQY_TO_COLLECTOR = "您有新指派的回收订单，请及时处理！";

}
