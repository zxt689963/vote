package com.ok8.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ValidTimeUtils {
	
	public static String getValidTime(Date nowTime){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowTime);
		calendar.add(Calendar.DATE, 3);// 把日期往后增加*天.整数往后推,负数往前移动
//		calendar.add(Calendar.HOUR, 3);// 把小时往后增加*小时.整数往后推,负数往前移动
		Date afterTime = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String str_date = sdf.format(afterTime);
		return str_date;
	}
	
	public static ArrayList<HashMap<String, Object>> monthStartAndEndDate(){
		ArrayList<HashMap<String, Object>> returnDate = new ArrayList<HashMap<String, Object>>();
		for(int i = -3; i < 1; i++){
			HashMap<String, Object> dataformatMap = new HashMap<String, Object>();
			String theMonth="",firstDay = "",lastDay = "";
			SimpleDateFormat formatTheMonth = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat formatFirstDay = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Calendar cal_1=Calendar.getInstance();//获取当前日期 
	        cal_1.add(Calendar.MONTH, i);
	        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	        theMonth = formatTheMonth.format(cal_1.getTime());
	        firstDay = formatFirstDay.format(cal_1.getTime());
//	        System.out.println("-----1------theMonth:"+theMonth);
//	        System.out.println("-----2------firstDay:"+firstDay);
	        //获取前月的最后一天
	        SimpleDateFormat formatLastDay = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
	        Calendar cale = Calendar.getInstance();
	        cale.add(Calendar.MONTH,i+1);
	        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
	        lastDay = formatLastDay.format(cale.getTime());
//	        System.out.println("-----3------lastDay:"+lastDay);
	        dataformatMap.put("theMonth", theMonth);
	        dataformatMap.put("firstDay", firstDay);
	        dataformatMap.put("lastDay", lastDay);
	        dataformatMap.put("order", i);
	        returnDate.add(dataformatMap);
		}
		return returnDate;
	}
	
	public static void main(String[] args) {
		System.out.println(getValidTime(new Date()));
	}
	
}
