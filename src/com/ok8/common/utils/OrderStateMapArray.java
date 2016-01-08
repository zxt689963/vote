package com.ok8.common.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderStateMapArray {

	public HashMap<String, Object> map1 = new HashMap<String, Object>(),
			map2 = new HashMap<String, Object>(),
			map3 = new HashMap<String, Object>(),
			map4 = new HashMap<String, Object>(),
			map5 = new HashMap<String, Object>(),
			map6 = new HashMap<String, Object>(),
			map7 = new HashMap<String, Object>(),
			map8 = new HashMap<String, Object>(),
			map9 = new HashMap<String, Object>();
	
	public ArrayList<HashMap<String, Object>> roadList1 = new ArrayList<HashMap<String, Object>>(),
											  roadList2 = new ArrayList<HashMap<String, Object>>(),
											  roadList3 = new ArrayList<HashMap<String, Object>>(),
											  roadList4 = new ArrayList<HashMap<String, Object>>(),
											  roadList5 = new ArrayList<HashMap<String, Object>>(); 
	
	public OrderStateMapArray(){
		map1.put("key", 1);
		map1.put("name", "提交订单");
		map1.put("flag", 0);
		map2.put("key", 2);
		map2.put("name", "客服确认");
		map2.put("flag", 0);
		map3.put("key", 3);
		map3.put("name", "指派回收员");
		map3.put("flag", 0);
		map4.put("key", 4);
		map4.put("name", "回收员认领");
		map4.put("flag", 0);
		map5.put("key", 5);
		map5.put("name", "客户发货");
		map5.put("flag", 0);
		map6.put("key", 6);
		map6.put("name", "收货检验");
		map6.put("flag", 0);
		map7.put("key", 8);
		map7.put("name", "闲宝网放款");
		map7.put("flag", 0);
		map8.put("key", 9);
		map8.put("name", "交易完成");
		map8.put("flag", 0);
		map9.put("key", 0);
		map9.put("name", "订单关闭");
		map9.put("flag", 0);
		roadList1.add(map1);
//		roadList1.add(map2);
		roadList1.add(map8);
		roadList2.add(map1);
		roadList2.add(map2);
		roadList2.add(map3);
		roadList2.add(map4);
		roadList2.add(map8);
		roadList3.add(map1);
//		roadList3.add(map2);
//		roadList3.add(map6);
		roadList3.add(map7);
		roadList3.add(map8);
		roadList4.add(map1);
		roadList4.add(map2);
		roadList4.add(map3);
		roadList4.add(map4);
//		roadList4.add(map6);
//		roadList4.add(map7);
		roadList4.add(map8);
		roadList5.add(map1);
//		roadList5.add(map2);
		roadList5.add(map5);
		roadList5.add(map6);
		roadList5.add(map7);
		roadList5.add(map8);
	}
	
	/**
	 * @author huban
	 * @param payment 收款方式 1-现金  2-网银  3-支付宝
	 * @param transactionMode 交易方式 1-线下门店  2-地铁口  3-公交站  4-快递  9-其他
	 * @param list 订单跟踪记录
	 * @return
	 */
	public static ArrayList<HashMap<String, Object>> getRoad(String payment, String transactionMode, ArrayList<HashMap<String, Object>> list){
		ArrayList<HashMap<String, Object>> roadList = new ArrayList<HashMap<String, Object>>();
		OrderStateMapArray orderStateMapArray = new OrderStateMapArray();
		if(payment!=null && transactionMode!=null && !"".equals(payment) && !"".equals(transactionMode)){
			if(payment.equals("1") && transactionMode.equals("1")){
				roadList = orderStateMapArray.roadList1;
			}else if(payment.equals("1") && (transactionMode.equals("2")||transactionMode.equals("3")||transactionMode.equals("9"))){
				roadList = orderStateMapArray.roadList2;
			}else if(payment.equals("2") && transactionMode.equals("1")){
				roadList = orderStateMapArray.roadList3;
			}else if(payment.equals("2") && (transactionMode.equals("2")||transactionMode.equals("3")||transactionMode.equals("9"))){
				roadList = orderStateMapArray.roadList4;
			}else if(payment.equals("2") && transactionMode.equals("4")){
				roadList = orderStateMapArray.roadList5;
			}else if(payment.equals("3") && transactionMode.equals("1")){
				roadList = orderStateMapArray.roadList3;
			}else if(payment.equals("3") && (transactionMode.equals("2")||transactionMode.equals("3")||transactionMode.equals("9"))){
				roadList = orderStateMapArray.roadList4;
			}else if(payment.equals("3") && transactionMode.equals("4")){
				roadList = orderStateMapArray.roadList5;
			}
		}
		for (HashMap<String, Object> hashMap : roadList) {
			for (HashMap<String, Object> trackMap : list) {
				if((String.valueOf(hashMap.get("key"))).equals(String.valueOf(trackMap.get("stateCode")))){
					hashMap.put("operateTime", trackMap.get("operateTime"));//operateTime 操作时间
					hashMap.put("orderId", trackMap.get("orderId"));
					hashMap.put("stateCode", trackMap.get("stateCode"));
					hashMap.put("stateName", trackMap.get("stateName"));
					hashMap.put("stateRemark", trackMap.get("stateRemark"));
					hashMap.put("operateRemark", trackMap.get("operateRemark"));
					hashMap.put("staffName", trackMap.get("staffName"));
					break;
				}
			}
			if(String.valueOf(list.get(list.size()-1).get("stateCode")).equals(String.valueOf(hashMap.get("key")))){
				String stateRemark = (String) hashMap.get("stateRemark");
				if(stateRemark!=null && stateRemark.equals("否")){
					hashMap.put("flag", "0");
				}else{
					hashMap.put("flag", "1");
				}
			}
		}
		return roadList;
	}
	
//	//订单详情所需共通方法，暂时放置 -----author：huban
//	public void commonMethod(HashMap<String, Object> detailh,ArrayList<HashMap<String, Object>> roadList){
//		for (HashMap<String, Object> hashMap : roadList) {
//			if(String.valueOf(detailh.get("state")).equals(String.valueOf(hashMap.get("key")))){
//				hashMap.put("flag", 1);
//			}
//		}
//	}
	
	public static void main(String[] args) {
		OrderStateMapArray orderStateMapArray = new OrderStateMapArray();
		ArrayList<HashMap<String, Object>> list = orderStateMapArray.roadList2;
		for (HashMap<String, Object> hashMap : list) {
			System.out.println(hashMap.get("key") + "：" + hashMap.get("name"));
		}
	}

}
