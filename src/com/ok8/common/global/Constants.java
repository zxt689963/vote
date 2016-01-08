package com.ok8.common.global;

/**
 * 系统常量
 * @author caiwl
 */
public class Constants {
	
	/** EasyUI DataGrid's pageSize */
	public static final int PAGE_SIZE = 15;
	
	public static final String SUCCESS = "{\"result\":\"success\"}";
	public static final String ERROR   = "{\"result\":\"failure\"}";
	
	//用于微信发送请求时所需的参数
	public static String access_token = null;
	public static String access_tokenQiye = null;
	public static String ticket = null;
	
	//闲宝网首页点击率
	public static int clickRate = 0;
	//闲宝网访问量
	public static int visitQuantity = 0;
	//投票
	public static int number = 0;
	
	//阿里云服务器网卡MAC地址：00-16-3E-00-04-BF
	public static final String serverMac = "00-16-3E-00-04-BF";
	
	//本地网卡MAC地址
	public static String localhostMac = null;
	
	/** 闲宝网微信公众号（服务号）LOGO存放路径 */
	public static final String PIC_WX_LOGO = "C:/xianbao/picture/agent/agent.war/weixin_logo.jpg";
	/** 代理商二维码存放路径 */
	public static final String PIC_AGENT = "C:/xianbao/picture/agent/agent.war/";
	/** 商品分类图片、商品图片存放路径 */
	public static final String PIC_GOODS = "C:/xianbao/picture/goods/goods.war/";
	/** 员工头像存放路径 */
	public static final String PIC_STAFF = "C:/xianbao/picture/staff/staff.war/";

}
