package com.ok8.common.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64是网络上最常见的用于传输8Bit字节代码的编码方式之一，可用于在HTTP环境下传递较长的标识信息。
 * 在其他应用程序中，也常常需要把二进制数据编码为适合放在URL（包括隐藏表单域）中的形式。
 * Apache Commons Codec: provides implementations of common encoders and decoders such as Base64, Hex, Phonetic and URLs.
 *               Base64: Provides Base64 encoding and decoding as defined by RFC 2045
 * @author caiwl
 */
public class Base64Utils {
	
	/**
	 * Base64 加密
	 * @param s 加密字符串
	 * @return
	 */
	public static String encode(String s) {
		try {
			return new String(Base64.encodeBase64(s.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Base64 解密
	 * @param s 解密字符串
	 * @return
	 */
	public static String decode(String s) {
		try {
			return new String(Base64.decodeBase64(s.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(encode("abc"));
		System.out.println(decode(encode("abc")));
	}

}
