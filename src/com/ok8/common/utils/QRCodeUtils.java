package com.ok8.common.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ok8.common.global.Constants;

public class QRCodeUtils {
	
	/**
	 * 推广码（服务号）
	 * @param agentCode 代理商代码
	 * @return 返回生成的二维码路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String encodeAgentQRCode(String agentCode) throws Exception {
		// 请求URL中带上代理商编码，便于微信扫一扫时，抓取代理商发展的用户
		String contents = CreateErWeiMaUtils.getQRCodeUrl(agentCode);
		int qrCodeSize = 430; // 二维码宽高
		int logoSize = 98; // 中间LOGO宽高
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 修正容量高
		hints.put(EncodeHintType.MARGIN, 2); // 边框留白
		// 代理商二维码存放路径
		String qrPath = Constants.PIC_AGENT + agentCode + "/" + agentCode + ".jpg";
		// 生成二维码图片
		BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		// 给二维码图片添加LOGO，LOGO图片是经过UI设计师处理的，边框，圆角，留白等（这样不需要程序处理）
		BufferedImage logo = ImageIO.read(new File(Constants.PIC_WX_LOGO));
		Graphics2D graph = image.createGraphics();
		int x = (qrCodeSize - logoSize) / 2;
		int y = (qrCodeSize - logoSize) / 2;
		graph.drawImage(logo, x - 1, y - 1, logoSize + 1, logoSize + 1, null);
		graph.drawRoundRect(x, y, logoSize, logoSize, 22, 22);
		graph.dispose();
		File file = new File(qrPath);
		File directory = file.getParentFile();
		if (!directory.exists()) {
			directory.mkdirs();
		}
		ImageIO.write(image, "jpg", file);
		return agentCode + "/" + agentCode + ".jpg";
	}
	
	/**
	 * 推广码（首页）
	 * @param agentCode 代理商编码
	 * @param contents 二维码内容
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String encodeAgentQRCode1(String agentCode, String contents) throws Exception {
		// 请求URL中带上代理商编码，便于微信扫一扫时，抓取代理商发展的用户
		int qrCodeSize = 430; // 二维码宽高
		int logoSize = 98; // 中间LOGO宽高
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 修正容量高
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
		// 边框留白
		hints.put(EncodeHintType.MARGIN, 2); // 边框留白
		// 代理商二维码存放路径
		String qrPath = Constants.PIC_AGENT + agentCode + "/" + agentCode + "_1.jpg";
		// 生成二维码图片
		BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		// 给二维码图片添加LOGO
		Graphics2D graph = image.createGraphics();
		int x = (qrCodeSize - logoSize) / 2;
		int y = (qrCodeSize - logoSize) / 2;
		BufferedImage logo = ImageIO.read(new File(Constants.PIC_WX_LOGO));
		graph.drawImage(logo, x - 1, y - 1, logoSize + 1, logoSize + 1, null);
		graph.drawRoundRect(x, y, logoSize, logoSize, 22, 22);
		graph.dispose();
		File file = new File(qrPath);
		File directory = file.getParentFile();
		if (!directory.exists()) {
			directory.mkdirs();
		}
		ImageIO.write(image, "jpg", file);
		return agentCode + "/" + agentCode + "_1.jpg";
	}
	
	/**
	 * 下单码（首页）
	 * @param agentCode 代理商编码
	 * @param contents 二维码内容
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String encodeAgentQRCode2(String agentCode, String contents) throws Exception {
		// 请求URL中带上代理商编码，便于微信扫一扫时，抓取代理商发展的用户
		int qrCodeSize = 430; // 二维码宽高
		int logoSize = 98; // 中间LOGO宽高
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 修正容量高
		hints.put(EncodeHintType.MARGIN, 2); // 边框留白
		// 代理商二维码存放路径
		String qrPath = Constants.PIC_AGENT + agentCode + "/" + agentCode + "_2.jpg";
		// 生成二维码图片
		BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		// 给二维码图片添加LOGO
		Graphics2D graph = image.createGraphics();
		int x = (qrCodeSize - logoSize) / 2;
		int y = (qrCodeSize - logoSize) / 2;
		BufferedImage logo = ImageIO.read(new File(Constants.PIC_WX_LOGO));
		graph.drawImage(logo, x - 1, y - 1, logoSize + 1, logoSize + 1, null);
		graph.drawRoundRect(x, y, logoSize, logoSize, 22, 22);
		graph.dispose();
		File file = new File(qrPath);
		File directory = file.getParentFile();
		if (!directory.exists()) {
			directory.mkdirs();
		}
		ImageIO.write(image, "jpg", file);
		return agentCode + "/" + agentCode + "_2.jpg";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void decodeAgentQRCode(String filePath) {
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			File input = new File(filePath);
			BufferedImage image = ImageIO.read(input);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);
			System.out.println("解析结果 = " + result.toString());
			System.out.println("二维码格式类型 = " + result.getBarcodeFormat());
			System.out.println("二维码文本内容 = " + result.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
