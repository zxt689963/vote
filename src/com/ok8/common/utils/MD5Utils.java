package com.ok8.common.utils;

import java.security.MessageDigest;

/**
 * MD5即Message-Digest Algorithm 5（信息-摘要算法5），用于确保信息传输完整一致。
 * MD5的作用是让大容量信息在用数字签名软件签署私人密钥前被"压缩"成一种保密的格式（就是把一个任意长度的字节串变换成一定长的十六进制数字串）。
 * @author caiwl
 */
public class MD5Utils {

	/**
	 * MD5 加密
	 * @param s 加密字符串
	 * @return
	 */
	public static String getMD5(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			byte[] digest = md.digest();
			StringBuffer result = new StringBuffer();
			for (int i = 0; i < digest.length; i++) {
				String hex = Integer.toHexString(digest[i] & 0xFF);
				if (hex.length() == 1) {
					result.append(0);
				}
				result.append(hex);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(getMD5("abc"));
	}

}
