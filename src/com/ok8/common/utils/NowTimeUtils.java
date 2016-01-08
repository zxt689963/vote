package com.ok8.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NowTimeUtils {
	
	public static String getNowTime(){
//		Date date = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义时间格式
		String nowTime = sdf.format(new Date());//时间格式化
		return nowTime;
	}

}
