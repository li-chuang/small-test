package com.lichuang.Sep25;

import java.io.File;
import java.io.IOException;

/**
 * 文件辅助类 : 1.获取文件实际大小 ; 2.文件复制 ; 3.文件夹复制; 4.文件删除 ; 5.文件夹删除 ; 6.文件重命名 ; 7.文件移动;
 * 8.获取文件扩展名信息; 9.遍历文件 ; 10.文件排序 ; 11.文件合并 ; 12.读取文本文件; 13.写入指定文件（追加或覆盖）;
 * 14.创建目录 ; 15.创建文件;
 * 
 * @author ASUS
 * 
 */
public class FileUtils {
	public static void main(String[] args) {
		// createFile(new File("hello.txt"));
		// createFile("world.txt");
		// createFile("E:\\", "lichuang.txt");
		// createDirectory(new File("hello"));
		// createDirectory("world");
		createDirectory("E:\\", "lichuang\\hello");
	}

	/**
	 * 创建目录
	 */
	public static void createDirectory(File file){
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public static void createDirectory(String filePath){
		createDirectory(new File(filePath));
	}
	
	public static void createDirectory(String destName, String fileName){
		createDirectory(new File(destName + fileName));
	}
	
	
	/**
	 * 15.创建文件 如果文件不存在，则创建
	 */
	public static void createFile(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createFile(String filePath) {
		createFile(new File(filePath));
	}

	// 将文件创建到指定的路径下
	public static void createFile(String destName, String fileName) {
		createFile(new File(destName + fileName));
	}
}
