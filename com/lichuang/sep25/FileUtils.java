package com.lichuang.Sep25;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

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
		// createDirectory("E:\\", "lichuang\\hello");
		// System.out.println(fileSize(new File("F:\\jfsky_yingbi.rar"))/1024);
		// System.out.println(fileSize(new File("F:\\war3"))/(1024*1024.0*1024));
		// System.out.println(fileSize("F:\\war3")/(1024*1024.0*1024));
		copyFile(new File("F:\\jfsky_yingbi.rar"),new File("E:\\jfsky_yingbi.rar"));
	}

	/**
	 * 1.获取文件实际大小
	 *   对目录，则循环向下获取，取总值
	 */
	public static long fileSize(File file) {
		FileChannel fileChannel = null;
		long size = 0L;
		try {
			if (!file.exists()) {
				throw new Exception("此文件不存在！！");
			}
			if(file.isFile()){
				FileInputStream fis = new FileInputStream(file);
				fileChannel = fis.getChannel();
				size = fileChannel.size();
			} else if(file.isDirectory()){
				File[] files = file.listFiles();
				int length = files.length;
				for(int i=0;i<length;i++){
					if(files[i].isDirectory()){
						size += fileSize(files[i]);
					} else {
						size += fileSize(files[i]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(fileChannel !=null){
				try {
					fileChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return size;
	}
	
	public static long fileSize(String fileName){
		return fileSize(new File(fileName));
	}

	/**
	 * 2.文件复制
	 *   注意NIO的使用
	 */
	public static File copyFile(File source, File destination) {
		FileChannel inc = null;
		FileChannel outc = null;
		try {
			if (!source.exists() || source.isDirectory()) {
				throw new Exception("文件不存在！");
			}
			if(source.getPath().equals(destination.getPath())){
				return source;
			}
			if(!destination.exists()){
				destination.createNewFile();
			}
			// 得到对应源文件的输入通道
			inc = new FileInputStream(source).getChannel();
			// 得到对应目标文件的输出通道
			outc = new FileOutputStream(destination).getChannel();
			// 生成1024字节的ByteBuffer实例
			ByteBuffer buf = ByteBuffer.allocate(1024);
			while(inc.read(buf)!=-1){	// 其实，即是不用NIO，完文件复制也是读与写的问题
				buf.flip(); // 准备写
				outc.write(buf); 
				buf.clear(); //准备读
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File copyFile(String source,String destination){
		File src = new File(source);
		File dest = new File(destination);
		dest = copyFile(src,dest);
		return dest;
	}
	
	/**
	 * 14.创建目录
	 */
	public static void createDirectory(File file) {
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void createDirectory(String filePath) {
		createDirectory(new File(filePath));
	}

	// 在指定的路径下创造目录
	public static void createDirectory(String destName, String fileName) {
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
