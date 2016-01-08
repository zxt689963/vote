package com.ok8.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrderNumUtils {

	public static int num = 100;
	
	public synchronized static String createOrderNum(){
		if(num==1000){
			num = 100;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = format.format(new Date()) + num++;
		return str;
	}
	
}
