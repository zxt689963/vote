package com.ok8.common.utils;

import java.io.File;

/**
 * 批量删除SVN目录 工具类
 */
public class DeleteSVN {

	public static String deleteFileName = ".svn";
	public static String deleteDirectory = "E:\\svn项目版本备份\\recycle";

	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				deleteFile(f);
			}
		}
//		System.out.println("delete file: " + file.getPath() + "/"
//				+ file.getName());
		file.delete();
	}

	public static void deleteDestFile(File file) {
		if (file.isDirectory()) {
			if (file.getName().equals(deleteFileName)) {
				deleteFile(file);
			} else {
				File[] files = file.listFiles();
				for (File f : files) {
					deleteDestFile(f);
				}
			}
		}
	}

	public static void main(String[] args) {
		File file = new File(deleteDirectory);
		deleteDestFile(file);
	}
}
