package com.ok8.common.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML 操作工具类
 */
public class XmlUtils {

	/**
	 * 将XML字符串解析为JSON字符串
	 * @param xml XML字符串
	 * @return JSON字符串
	 */
//	public static String parseXmlToJson(String xml) {
//		String json = null;
//		StringReader reader = null;
//		try {
//			StringBuffer jsonBuffer = new StringBuffer();
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			reader = new StringReader(xml);
//			Document document = builder.parse(new InputSource(reader));
//			Element element = document.getDocumentElement();
//			NodeList nodes = element.getChildNodes();
//			for (int i = 0, len = nodes.getLength(); i < len; i++) {
//				Node node = nodes.item(i);
//				String nodeValue = node.getNodeName();
//				Node child = node.getFirstChild();
//				if (child != null) {
//					nodeValue = node.getFirstChild().getNodeValue();
//				}
//				jsonBuffer.append(",\"" + node.getNodeName() + "\":\""
//						+ nodeValue + "\"");
////				Node node = nodes.item(i);
////				/*System.out.println(node==null);
////				System.out.println(node.getFirstChild()==null);*/
////				jsonBuffer.append(",\"" + node.getNodeName() + "\":\""
////						+ node.getNodeValue() + "\"");
//			}
//			json = "{" + jsonBuffer.substring(1) + "}";
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			reader.close();
//		}
////		System.out.println`(json);
//		return json;
//	}
	
	/**
	 * 将XML字符串解析为JSON字符串
	 * @param xml XML字符串
	 * @return JSON字符串
	 */
	public static String parseXmlToJson(String xml) {
		String json = null;
		StringReader reader = null;
		try {
			StringBuffer jsonBuffer = new StringBuffer();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			reader = new StringReader(xml);
			Document document = builder.parse(new InputSource(reader));
			Element element = document.getDocumentElement();
			NodeList nodes = element.getChildNodes();
			for (int i = 0, len = nodes.getLength(); i < len; i++) {
				Node node = nodes.item(i);
				jsonBuffer.append(",\"" + node.getNodeName() + "\":\""
						+ node.getTextContent() + "\"");
			}
			json = "{" + jsonBuffer.substring(1) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return json;
	}

	/**
	 * 将XML字符串解析为HashMap集合
	 * @param xml XML字符串
	 * @return HashMap集合
	 */
	public static HashMap<String, String> parseXmlToHashMap(String xml) throws SAXException, IOException, ParserConfigurationException, DOMException{
		HashMap<String, String> map = new HashMap<String, String>();
		StringReader reader = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();//throws ParserConfigurationException
			reader = new StringReader(xml);
			Document document = builder.parse(new InputSource(reader));//throws SAXException, IOException
			Element element = document.getDocumentElement();
			NodeList nodes = element.getChildNodes();
			for (int i = 0, len = nodes.getLength(); i < len; i++) {
				Node node = nodes.item(i);
				map.put(node.getNodeName(), node.getTextContent());//throws DOMException
			}
		} finally {
			reader.close();
		}
		return map;
	}
	
	public static void main(String[] args) {
		String xml = ""
				+ "<xml>"
					+ "<ToUserName><![CDATA[gh_12141be9000e]]></ToUserName>"
					+ "<FromUserName><![CDATA[o9MOUt4m2L4vD7VQfXlP1bZuUUv4]]></FromUserName>"
					+ "<CreateTime>1421472618</CreateTime>"
					+ "<MsgType><![CDATA[event]]></MsgType>"
					+ "<Event><![CDATA[VIEW]]></Event>"
					+ "<EventKey><![CDATA[http://at.zj10010.com/view.html]]></EventKey>"
				+ "</xml>";
		System.out.println(parseXmlToJson(xml));
//		System.out.println(parseXmlToHashMap(xml));
	}

}
