package com.ok8.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ok8.common.global.Constants;

public class SendVerificationCode1 {

	public SendVerificationCode1() {
	}
	
	private static String url = "http://42.121.122.61:18002/send.do";//发送验证码接口地址
	private static String ua = "ok8";//企业编号
	private static String pw = "259280";//用户名
	
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
		
		String params = "ua=" + ua + "&" +
				  "pw=" + pw + "&" +
				  "mb=" + mobile + "&" +
				  "ms=" + MessageContent + "【闲宝网】";
		
		try {
			URL realUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			outputStream = connection.getOutputStream();
			outputStream.write(params.getBytes("gbk"));
			outputStream.flush();
			outputStream.close();
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,"gbk");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				backCode += line;
			}
			System.out.println(backCode);
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
			System.out.println("成功将信息\"" + MessageContent + "\"发送到手机号为\"" + mobile + "\"的手机上");
		}else if(backCode.startsWith("result=32")){
			result = "{\"result\":\"moreTimes\"}";
			System.out.println("手机号码为：" + mobile + "发送次数太多！");
		}else if(backCode.startsWith("result=10")){
			System.out.println("黑名单用户！");
		}else if(backCode.startsWith("result=11")){
			System.out.println("获取验证码提交速度太快！");
		}else if(backCode.startsWith("result=16")){
			System.out.println("发送短信超出发送上限！");
		}else if(backCode.startsWith("result=17")){
			System.out.println("余额不足！");
		}else if(backCode.startsWith("result=18")){
			System.out.println("扣费不成功！");
		}else if(backCode.startsWith("result=20")){
			System.out.println("系统错误！");
		}else if(backCode.startsWith("result=24")){
			System.out.println("账户状态不正常！");
		}else if(backCode.startsWith("result=25")){
			System.out.println("账户权限不足！");
		}
		return result;
	}
	
	public static void main(String[] args) {
		sendPost("18156099891","闲宝网");
	}
	
}
