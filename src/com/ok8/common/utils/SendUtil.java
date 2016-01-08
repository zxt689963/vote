package com.ok8.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.ok8.common.global.Constants;

public class SendUtil {

	public SendUtil() {
	}

	public static String send(String url) {
		String str = "";
		StringBuffer result = new StringBuffer();
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = http(url);
			inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
//			byte[] b = new byte[1024];
//			int length = -1;
//			while (-1 != (length = inputStream.read(b))) {
//				str = new String(b, 0, length);
//				// System.out.println(str);
//			}
			inputStreamReader.close();
			bufferedReader.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		str = result.toString();
		return str;
	}

	public static InputStream http(String url) {
		URL u = null;
		HttpURLConnection con = null;
		InputStream inputStream = null;
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "binary/octet-stream");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		// 读取返回内容
		try {
			// 读取返回内容
			inputStream = con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static String createMenu(String params,String accessToken) {//微信服务号菜单创建方法
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(
					"https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
							+ accessToken);
			InputStream inputStream = null;
			OutputStream outputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			outputStream = connection.getOutputStream();
			outputStream.write(params.getBytes());
			outputStream.flush();
			outputStream.close();
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
//			System.out.println(result);
			inputStreamReader.close();
			bufferedReader.close();
			inputStream.close();
			if (connection != null) {
				// 关闭连接
				connection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public static String createMenuQiye(String params,String accessToken) {//微信企业号菜单创建方法
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(
					"https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken + "&agentid=1");
			InputStream inputStream = null;
			OutputStream outputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			outputStream = connection.getOutputStream();
			outputStream.write(params.getBytes());
			outputStream.flush();
			outputStream.close();
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
//			System.out.println(result);
			inputStreamReader.close();
			bufferedReader.close();
			inputStream.close();
			if (connection != null) {
				// 关闭连接
				connection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * 发送普通微信消息
	 * @param params 发送数据包
	 * @param accessToken 微信接口所需参数
	 * @return
	 */
	public static String sendMessage(String params,String accessToken) {
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(
					"https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="
							+ accessToken);
			InputStream inputStream = null;
			OutputStream outputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			outputStream = connection.getOutputStream();
			outputStream.write(params.getBytes("utf-8"));
			outputStream.flush();
			outputStream.close();
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
//			System.out.println(result);
			inputStreamReader.close();
			bufferedReader.close();
			inputStream.close();
			if (connection != null) {
				// 关闭连接
				connection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * 发送模板微信消息
	 * @param params 发送数据包
	 * @param accessToken 微信接口所需参数
	 * @return
	 */
	//该方法可与上面的方法合并  后续完善...
	public static String sendTemplateMessage(String params,String accessToken) {
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(
					"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
							+ accessToken);
			InputStream inputStream = null;
			OutputStream outputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			outputStream = connection.getOutputStream();
			outputStream.write(params.getBytes("utf-8"));
			outputStream.flush();
			outputStream.close();
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
//			System.out.println(result);
			inputStreamReader.close();
			bufferedReader.close();
			inputStream.close();
			if (connection != null) {
				// 关闭连接
				connection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static String getInputStream(InputStream inputStream){
		String str = "";
		byte[] b = new byte[1024];
		int length = -1;
		try {
			while (-1 != (length = inputStream.read(b))) {
				str = new String(b, 0, length);
//				System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 订单状态改变给用户推送消息(微信)
	 * @param openid 用户关注本微信服务号唯一标识符
	 * @param orderNum 订单号
	 * @param stateName 订单状态(名称)
	 * @return 微信服务器返回的json格式字符串 可判别推送成功与否
	 */
	public static String sendMessage2User(String openid, String orderNum, String stateName){//String openid,String message
		String access_token = Constants.access_token;
		if(access_token==null){
//			System.out.println("重新获取了access_token");
			String getAccessTokenResult = send("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbeb224599be6f280&secret=15d2f5080353c7ce033fe2551c8fca08");
			HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(getAccessTokenResult);
			access_token = (String)map.get("access_token");
			Constants.access_token = access_token;
		}else{
//			System.out.println("没有重新获取access_token");
		}
		String params = "{" + 
							"\"touser\":\"" + openid + "\"," + //openid
							"\"template_id\":\"1EjBEsGywCOQp6DSUBshPuBfYoqxD6XGymHyla4whtw\"," + //订单更新模板id
							"\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=myorder&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"," + 
							"\"topcolor\":\"#FF0000\"," + 
							"\"data\":{" + 
								"\"first\": {" + 
									"\"value\":\"尊敬的闲宝网用户：您的订单状态有变动，请及时查看！\"," + 
									"\"color\":\"#173177\"" + 
								"}," + 
								"\"OrderSn\":{" + 
									"\"value\":\"" + orderNum + "\"," + 
									"\"color\":\"#173177\"" + 
								"}," + 
								"\"OrderStatus\":{" + 
									"\"value\":\"" + stateName + "\"," + 
									"\"color\":\"#173177\"" + 
								"}," + 
								"\"remark\":{" + 
									"\"value\":\"点击查看详情\"," + 
									"\"color\":\"#173177\"" + 
								"}" + 
							"}" + 
						"}";
		String result = sendTemplateMessage(params,access_token);//{"errcode":0,"errmsg":"ok","msgid":213345536} {"errcode":47001,"errmsg":"data format error"} 
		HashMap<String, Object> resultMap = ObjToHashMapUtil.parseJSON2HashMap(result);
		int errcode = -2;
		if(resultMap!=null){
			errcode = (int)(resultMap.get("errcode")==null?-2:resultMap.get("errcode"));
		}
		if(errcode==0){
//			System.out.println("推送消息给用户成功");
		}else if(errcode==-2){
			System.out.println("推送消息给用户失败，原因：微信没有将错误代码返回给本服务器");
		}else if(errcode==-1){
			System.out.println("推送消息给用户失败，原因：微信系统繁忙");
		}else if(errcode==40001){
			System.out.println("推送消息给用户失败，原因：参数验证失败");
		}else if(errcode==40002){
			System.out.println("推送消息给用户失败，原因：不合法的凭证类型");
		}else if(errcode==40003){
			System.out.println("推送消息给用户失败，原因：不合法的OpenID");
		}else if(errcode==40008){
			System.out.println("推送消息给用户失败，原因：不合法的消息类型");
		}else if(errcode==40013){
			System.out.println("推送消息给用户失败，原因：不合法的APPID");
		}else if(errcode==41001){
			System.out.println("推送消息给用户失败，原因：缺少access_token参数");
			Constants.access_token = null;
		}else if(errcode==41002){
			System.out.println("推送消息给用户失败，原因：缺少appid参数");
		}else if(errcode==41004){
			System.out.println("推送消息给用户失败，原因：缺少secret参数");
		}else if(errcode==41006){
			System.out.println("推送消息给用户失败，原因：access_token超时");//此时应该要重新获取access_token参数并将消息重新推送。
			String getAccessTokenResult = send("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbeb224599be6f280&secret=15d2f5080353c7ce033fe2551c8fca08");
			HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(getAccessTokenResult);
			access_token = (String)map.get("access_token");
			Constants.access_token = access_token;
			result = sendTemplateMessage(params,access_token);
			System.out.println("重新推送消息结果：" + result);
		}else if(errcode==45009){
			System.out.println("推送消息给用户失败，原因：接口调用超过限制");
		}else if(errcode==47001){
			System.out.println("推送消息给用户失败，原因：解析JSON/XML内容错误");
		}else{
			System.out.println("错误原因不在罗列范围内，具体原因：" + result);
		}
		return result;
	}
	
	/**
	 * 微信企业号推送消息，给回收员分配订单后，将给回收员推送一条提醒消息
	 * @param staffNum 关注企业号后管理员给公司员工分配的帐号
	 * @param message 发送给用户的信息内容
	 * @return 消息推送成功返回true，否则false
	 */
	public static boolean sendMessage2Staff(String staffNum,String message){
		boolean flag = false;
		String result = SendUtil.send("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx73e3fa40505de847&corpsecret=nZUX_T4GC9XchZ7XmU04DS0z6jQtpz-tMTlO0f4YIweoHk0_wDul5Jz6o0CMnr62");
		HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(result);
		String access_token = (String)map.get("access_token");//该access_token是企业号所需的，可能与服务号不一样，也可能不能共用，后续再调查
		System.out.println(access_token);
		String params = "{" + 
							"\"touser\":\"" + staffNum + "\"," + //" + staffNum + "
							"\"toparty\":\"\"," + 
							"\"totag\":\"\"," + 
							"\"msgtype\":\"text\"," + 
							"\"agentid\":\"1\"," + 
							"\"text\":{\"content\":\"" + message + "\"}," + 
							"\"safe\":\"0\"" + 
						"}";
		String result1 = sendMessage(params,access_token);
		HashMap<String, Object> resultMap = ObjToHashMapUtil.parseJSON2HashMap(result1);
		int errcode = -2;
		if(resultMap!=null){
			errcode = (int)(resultMap.get("errcode")==null?-2:resultMap.get("errcode"));
		}
		if(errcode==0){
			flag = true;
		}else if(errcode==-2){
			System.out.println("微信没有将错误代码返回给本服务器，推送消息可能失败");
		}
		return flag;
	}
	
	/**
	 * 微信企业号推送消息，客户提交订单后，将给客服部门推送一条提醒消息
	 * @param partId 企业号管理员创建部门时生成的部门id
	 * @param message 发送给部门所有员工的信息内容
	 * @return 消息推送成功返回true，否则false
	 */
	public static boolean sendMessage2Part(String partId,String message){
		boolean flag = false;
		String result = SendUtil.send("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx73e3fa40505de847&corpsecret=nZUX_T4GC9XchZ7XmU04DS0z6jQtpz-tMTlO0f4YIweoHk0_wDul5Jz6o0CMnr62");
		HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(result);
		String access_token = (String)map.get("access_token");//该access_token是企业号所需的，可能与服务号不一样，也可能不能共用，后续再调查
//		System.out.println(access_token);
		String params = "{" + 
							"\"touser\":\"\"," + //" + staffNum + "
							"\"toparty\":\"" + partId + "\"," + 
							"\"totag\":\"\"," + 
							"\"msgtype\":\"text\"," + 
							"\"agentid\":\"1\"," + 
							"\"text\":{\"content\":\"" + message + "\"}," + 
							"\"safe\":\"0\"" + 
						"}";
		String result1 = sendMessage(params,access_token);
		HashMap<String, Object> resultMap = ObjToHashMapUtil.parseJSON2HashMap(result1);
		int errcode = -2;
		if(resultMap!=null){
			errcode = (int)(resultMap.get("errcode")==null?-2:resultMap.get("errcode"));
		}
		if(errcode==0){
			flag = true;
		}else if(errcode==-2){
			System.out.println("微信没有将错误代码返回给本服务器，推送消息可能失败");
		}
		return flag;
	}
	
	public static void main2(String[] args) {//微信服务号菜单生成
		try {
			InputStream inputStream1 = http("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbeb224599be6f280&secret=15d2f5080353c7ce033fe2551c8fca08");
			String result = getInputStream(inputStream1);
			System.out.println(result);
			HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(result);
			String params = "{\"button\":[{\"name\":\"旧机回收\",\"sub_button\":[" +
														"{\"type\":\"view\",\"name\":\"IPHONE回收\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=index&mx=11&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
//														"{\"type\":\"view\",\"name\":\"查看我的openid\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=myopenid&mx=12&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
														"{\"type\":\"view\",\"name\":\"保修期查询\",\"url\":\"http://sn.appvv.com/\"}]}," +
										"{\"name\":\"最新活动\",\"type\":\"click\",\"key\":\"active\"}," +
										"{\"name\":\"自助服务\",\"sub_button\":[" +
														"{\"type\":\"view\",\"name\":\"账号绑定\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=bangdingPhone&mx=31&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
														"{\"type\":\"view\",\"name\":\"订单查询\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=myorder&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
														"{\"type\":\"click\",\"name\":\"关于我们\",\"key\":\"about\"}," +
														"{\"type\":\"click\",\"name\":\"帮助中心\",\"key\":\"help\"}," +
														"{\"type\":\"view\",\"name\":\"代理商登录\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=agent&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}" +
														"]}]}";
			String accessToken = (String)map.get("access_token");
			System.out.println(accessToken);
			String result1 = createMenu(params, accessToken);
			System.out.println(result1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main4(String[] args) {//微信企业号菜单生成
		try {
			InputStream inputStream1 = http("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx73e3fa40505de847&corpsecret=nZUX_T4GC9XchZ7XmU04DS0z6jQtpz-tMTlO0f4YIweoHk0_wDul5Jz6o0CMnr62");
			String result = getInputStream(inputStream1);
			System.out.println(result);
			HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(result);
//			String params = "{\"button\":[{\"name\":\"旧机回收\",\"sub_button\":[" +
//														"{\"type\":\"view\",\"name\":\"IPHONE回收\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=index&mx=11&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
//														"{\"type\":\"view\",\"name\":\"保修期查询\",\"url\":\"http://sn.appvv.com/\"}]}," +
//										"{\"name\":\"最新活动\",\"type\":\"click\",\"key\":\"active\"}," +
//										"{\"name\":\"自助服务\",\"sub_button\":[" +
//														"{\"type\":\"view\",\"name\":\"账号绑定\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=bangdingPhone&mx=31&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
//														"{\"type\":\"view\",\"name\":\"订单查询\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb224599be6f280&redirect_uri=http://www.ok8.com/toWeixin?direct=myorder&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
//														"{\"type\":\"click\",\"name\":\"关于我们\",\"key\":\"about\"}," +
//														"{\"type\":\"click\",\"name\":\"帮助中心\",\"key\":\"help\"}]}]}";
			String params1 = "{\"button\":[{\"name\":\"代理商登录\",\"type\":\"view\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx73e3fa40505de847&redirect_uri=http://www.ok8.com/toWeixinQiye?direct=agent&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}]}";
			String accessToken = (String)map.get("access_token");
			System.out.println(accessToken);
			String result1 = createMenuQiye(params1, accessToken);
			System.out.println(result1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main1(String[] args) {
		try {
			InputStream inputStream1 = http("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx470cf821c89ab13e&secret=8661d3dfa9554241e01fa2bf53a8bf09");
			String result = getInputStream(inputStream1);
//			System.out.println(result);
			HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(result);
			String params = "{\"button\":[{\"name\":\"以旧换新\",\"sub_button\":[" +
														"{\"type\":\"view\",\"name\":\"以旧换新活动详情\",\"url\":\"http://121.40.52.239/oldChangeNew/webFrontPage/poster_detail.html\"}," +
														"{\"type\":\"view\",\"name\":\"旧机价格在线评估\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx470cf821c89ab13e&redirect_uri=http://121.40.52.239/oldChangeNew/webFrontPage/evaluation.jsp?mx=12&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
														"{\"type\":\"view\",\"name\":\"IPHONE保修状态\",\"url\":\"http://sn.appvv.com/\"}]}," +
										"{\"name\":\"掌上网厅\",\"sub_button\":[" +
														"{\"type\":\"view\",\"name\":\"线上预约\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx470cf821c89ab13e&redirect_uri=http://121.40.52.239/oldChangeNew/toyuyue.do?mx=21&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
														"{\"type\":\"view\",\"name\":\"个人预约订单\",\"url\":\"http://121.40.52.239/view.html\"}," +
														"{\"type\":\"view\",\"name\":\"杭州移动优惠\",\"url\":\"http://121.40.52.239/view.html\"}," +
														"{\"type\":\"view\",\"name\":\"分期付款推荐\",\"url\":\"http://121.40.52.239/view.html\"}]}," +
										"{\"name\":\"身边爱通\",\"sub_button\":[" +
														"{\"type\":\"view\",\"name\":\"查找门店\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx470cf821c89ab13e&redirect_uri=http://121.40.52.239/oldChangeNew/shop.action?servletName=list&province_id=1&city_id=1?mx=31&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect\"}," +
														"{\"type\":\"view\",\"name\":\"联系VIP客户经理\",\"url\":\"http://121.40.52.239/view.html\"}," +
														"{\"type\":\"view\",\"name\":\"咨询/售后/投诉\",\"url\":\"http://121.40.52.239/view.html\"}," +
														"{\"type\":\"view\",\"name\":\"关于我们\",\"url\":\"http://121.40.52.239/view.html\"}]}]}";
			String accessToken = (String)map.get("access_token");
			System.out.println(accessToken);
			createMenu(params, accessToken);
//			System.out.println(getInputStream(inputStream));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		boolean flag = sendMessage2Staff("yfzx002", "测试一下！");
		boolean flag = sendMessage2Part("5", "我是胡班，这是使用程序单独发消息测试，不是客户下单，也不用回复！");
		if(flag){
			System.out.println("响应结果正确！");
		}
//		String result = SendUtil.send("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx73e3fa40505de847&corpsecret=nZUX_T4GC9XchZ7XmU04DS0z6jQtpz-tMTlO0f4YIweoHk0_wDul5Jz6o0CMnr62");
//		HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(result);
//		String access_token = (String)map.get("access_token");
//		System.out.println(access_token);
//		String param = "{" + 
//							"\"touser\":\"yfzx002\"," + 
//							"\"toparty\":\"\"," + 
//							"\"totag\":\"\"," + 
//							"\"msgtype\":\"text\"," + 
//							"\"agentid\":\"0\"," + 
//							"\"text\":{\"content\":\"胡班！快来看看我们的网站(http://www.ok8.com)\"}," + 
//							"\"safe\":\"0\"" + 
//						"}";
//		String result1 = sendMessage(param,access_token);
//		System.out.println(result1);
//		String userInfo = SendUtil.send("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + access_token);
	}
	
	public static void main3(String[] args) {
		String htmlStr = send("http://www.baidu.com");
		System.out.println(htmlStr);
	}

}
