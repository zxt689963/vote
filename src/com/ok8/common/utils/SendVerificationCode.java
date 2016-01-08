package com.ok8.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ok8.common.global.Constants;

public class SendVerificationCode {
	
	private static String url = "http://ums.zj165.com:8888/sms/Api/Send.do";//发送验证码接口地址
	private static String SpCode = "214628";//企业编号
	private static String LoginName = "zj_at";//用户名
	private static String Password = "@yixintong0924";//密码
	private static int num = 100;
	
	/**
	 * @param mobile 接收信息的手机号码
	 * @param MessageContent 信息内容
	 * @return 返回{\"result\":\"success\"}表示信息发送成功，否则失败。
	 */
	public static String sendPost(String mobile, String MessageContent) {
		OutputStream outputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		InputStream inputStream = null;
		String backCode = "";//初始化接口返回代码
		
		String params = "SpCode=" + SpCode + "&" +
				  "LoginName=" + LoginName + "&" +
				  "Password=" + Password + "&" +
				  "MessageContent=" + MessageContent + "&" +
				  "UserNumber=" + mobile + "&" +
				  "SerialNumber=" + createOrderNum() + "&" +//调用生成订单号工具类来生成流水号
				  "ScheduleTime=&" +
				  "ExtendAccessNum=&" +
				  "f=1";
		
		try {
//			System.out.println("调用获取验证码接口...");
			URL realUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			outputStream = connection.getOutputStream();
			outputStream.write(params.getBytes("GB2312"));
			outputStream.flush();
			outputStream.close();
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,"GB2312");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				backCode += line;
			}
//			inputStream.close();
			if (connection != null) {
				// 关闭连接
				connection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
				if(inputStreamReader != null){
					inputStreamReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		String result = Constants.ERROR;
		if(backCode.startsWith("result=0")){
			result = Constants.SUCCESS;
//			System.out.println("成功将信息\"" + MessageContent + "\"发送到手机号为\"" + mobile + "\"的手机上");
		}else if(backCode.startsWith("result=32")){
			result = "{\"result\":\"moreTimes\"}";
			System.out.println("手机号码为：" + mobile + "发送次数太多！");
		}else if(backCode.startsWith("result=2")){
			System.out.println("账号无效或权限不足！");
		}else if(backCode.startsWith("result=3")){
			System.out.println("账号密码错误！");
		}else if(backCode.startsWith("result=8")){
			System.out.println("内容长度超过上限，最大402字符！");
		}else if(backCode.startsWith("result=10")){
			System.out.println("黑名单用户！");
		}else if(backCode.startsWith("result=11")){
			System.out.println("获取验证码提交速度太快！");
		}else if(backCode.startsWith("result=12")){
			System.out.println("您尚未订购[普通短信业务]，暂不能发送该类信息！");
		}else if(backCode.startsWith("result=13")){
			System.out.println("您的[普通短信业务]剩余数量发送不足，暂不能发送该类信息！");
		}else if(backCode.startsWith("result=16")){
			System.out.println("发送短信超出发送上限！");
		}else if(backCode.startsWith("result=17")){
			System.out.println("余额不足！");
		}else if(backCode.startsWith("result=18")){
			System.out.println("扣费不成功！");
		}else if(backCode.startsWith("result=20")){
			System.out.println("系统错误！");
		}else if(backCode.startsWith("result=21")){
			System.out.println("密码错误次数达到5次！");
		}else if(backCode.startsWith("result=24")){
			System.out.println("账户状态不正常！");
		}else if(backCode.startsWith("result=25")){
			System.out.println("账户权限不足！");
		}else if(backCode.startsWith("result=28")){
			System.out.println("发送内容与模板不符！");
		}else{
			System.out.println("短信验证码接口返回结果：" + backCode);
		}
		return result;
	}
	
	public synchronized static String createOrderNum(){
		if(num==1000){
			num = 100;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = format.format(new Date()) + num++;
		return str;
	}

}
