package com.ok8.common.utils;

import com.ok8.common.global.Constants;

/**
 * 用于统计点击率和访问量工具类
 * @author zxt
 *
 */
public class ClickRateUtils {

	/**
	 * 用于统计网站点击率
	 * @param type type为0，每小时把点击率置为零|有人访问首页是，即type为1时，点击率加1
	 * @author zxt
	 */
	public static synchronized void countClickRate(int type) {
		switch (type){
		case 1:
			Constants.clickRate++;
			break;
		case 0:
			Constants.clickRate = 0;
			break;
//		case -1:
//			Constants.clickRate--;
//			break;
		}
	}
	
	/**
	 * 用于统计网站访问量
	 * @param type type为0，每小时把访问量置零|有人访问网站时，即type为1时，点击率加1
	 * @author zxt
	 */
	public static synchronized void countVisitQuantity(int type) {
		switch (type){
		case 1:
			Constants.visitQuantity++;
			break;
		case 0:
			Constants.visitQuantity = 0;
			break;
		}
	}
}
