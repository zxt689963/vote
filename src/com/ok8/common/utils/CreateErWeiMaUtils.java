package com.ok8.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CreateErWeiMaUtils {

	public static String send(String url) {
		String str = "";
		try {
			InputStream inputStream = http(url);
			byte[] b = new byte[1024];
			int length = -1;
			while (-1 != (length = inputStream.read(b))) {
				str = new String(b, 0, length);
				// System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	/**
	 * 以post发送生成推广码的请求
	 * @param params 请求参数
	 * @param accessToken
	 * @return 返回请求返回值json字符串
	 */
	public static String createErWeiMa(String params,String accessToken) {
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(
					"https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
							+ accessToken);
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
			InputStream inputStream = null;
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
//			System.out.println(result);
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
				str += new String(b, 0, length);
//				System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获得代理商二维码存放地址
	 * @param agentCode 代理商代码
	 * @return
	 */
	public static String getQRCodeUrl(String agentCode) {
		try {
			InputStream inputStream = http("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbeb224599be6f280&secret=15d2f5080353c7ce033fe2551c8fca08");
			String result = getInputStream(inputStream);
//			System.out.println(result);
			HashMap<String, Object> map = ObjToHashMapUtil.parseJSON2HashMap(result);
			String params = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + agentCode + "\"}}}";
			String accessToken = (String)map.get("access_token");
//			System.out.println(accessToken);
			String json = createErWeiMa(params, accessToken);
//			String json = getInputStream(inputStream);
//			System.out.println(getInputStream(inputStream));
			HashMap<String, Object> map1 = ObjToHashMapUtil.parseJSON2HashMap(json);
			inputStream.close();
			return (String) map1.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
