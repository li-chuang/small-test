package com.lichuang.Sep25;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

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
		// System.out.println(fileSize("F:\\war3") / (1024 * 1024.0 * 1024));
		// copyFile(new File("F:\\jfsky_yingbi.rar"),new
		// File("E:\\jfsky_yingbi.rar"));
		// copyFiles(new File("F:\\hello"), new File("E:\\hello"));
		// copyFiles("F:\\hello", "E:\\hello");
		// removeFile(new File("F:\\ed4c355a11ef197d0a9e519f3dd65325.jpg"));
		// removeFolder(new File("F:\\www - 副本"));
		// rename(new File("F:\\logFile_1.txt"),new File("F:\\logFile_2.txt"));
		// moveFile(new File("F:\\logFile_2_副本.txt"),new File("E:\\logFile_2.txt"));
		// extensions(new File("F:\\logFile_2.txt"));
		// System.out.println(searchFolder(new File("F:\\war3"),0));
		System.out.println(readFile(new File("F:\\logFile_2.txt"),null));
	}

	/**
	 * 1.获取文件实际大小 对目录，则循环向下获取，取总值
	 */
	public static long fileSize(File file) {
		FileChannel fileChannel = null;
		long size = 0L;
		try {
			if (!file.exists()) {
				throw new Exception("此文件不存在！！");
			}
			if (file.isFile()) {
				FileInputStream fis = new FileInputStream(file);
				fileChannel = fis.getChannel();
				size = fileChannel.size();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				int length = files.length;
				for (int i = 0; i < length; i++) {
					if (files[i].isDirectory()) {
						size += fileSize(files[i]);
					} else {
						size += fileSize(files[i]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileChannel != null) {
				try {
					fileChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return size;
	}

	public static long fileSize(String fileName) {
		return fileSize(new File(fileName));
	}

	/**
	 * 2.文件复制 注意NIO的使用
	 */
	public static File copyFile(File source, File destination) {
		FileChannel inc = null;
		FileChannel outc = null;
		try {
			if (!source.exists() || source.isDirectory()) {
				throw new Exception("文件不存在！");
			}
			if (source.getPath().equals(destination.getPath())) {
				return source;
			}
			if (!destination.exists()) {
				destination.createNewFile();
			}
			// 得到对应源文件的输入通道
			inc = new FileInputStream(source).getChannel();
			// 得到对应目标文件的输出通道
			outc = new FileOutputStream(destination).getChannel();
			// 生成1024字节的ByteBuffer实例
			ByteBuffer buf = ByteBuffer.allocate(1024);
			while (inc.read(buf) != -1) { // 其实，即是不用NIO，完文件复制也是读与写的问题
				buf.flip(); // 准备写
				outc.write(buf);
				buf.clear(); // 准备读
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inc != null) {
					inc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (outc != null) {
					try {
						outc.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public static File copyFile(String source, String destination) {
		File src = new File(source);
		File dest = new File(destination);
		dest = copyFile(src, dest);
		return dest;
	}

	/**
	 * 3.文件夹复制
	 */
	public static void copyFiles(File source, File destination) {
		try {
			if (!source.exists()) {
				throw new Exception("文件夹不存在！");
			}
			// 注意，让目的地址先建立起来很重要，不然会发生找不到路径的错误
			if (!destination.exists()) {
				destination.mkdirs();
			}
			if (source.isDirectory()) {
				String[] files = source.list();
				int length = files.length;
				for (int i = 0; i < length; i++) {
					String src = source.getPath() + "\\" + files[i];
					String dest = destination.getPath() + "\\" + files[i];
					System.out.println(src + "\n" + dest);
					File subFile = new File(src);
					if (subFile.isDirectory()) {
						copyFiles(subFile, new File(dest));
					} else {
						copyFile(subFile, new File(dest));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyFiles(String source, String destination) {
		File src = new File(source);
		File dest = new File(destination);
		copyFiles(src, dest);
	}

	/**
	 * 4.文件删除
	 * 
	 * @throws Exception
	 */
	public static boolean removeFile(File fileName) {
		boolean result = false;
		try {
			if (!fileName.exists()) {
				throw new Exception("文件不存在！");
			}
			if (fileName.isFile()) {
				result = fileName.delete();
			}
			if (result = Boolean.TRUE) {
				System.out.println("删除成功！");
			} else {
				System.out.println("删除失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 5.文件夹删除 
	 * 注意：delete()可以删除文件和文件夹，也就是文件和目录，但假如文件夹中还有文件，则删除不了,需要逐层删除。
	 */
	public static void removeFolder(File filesPath) {
		try {
			if (!filesPath.exists()) {
				throw new Exception("文件不存在！");
			}
			if(filesPath.isDirectory()){
				File[] files = filesPath.listFiles();
				for(int i = 0;i<files.length;i++){
					if(files[i].isFile()){
						files[i].delete();
					}else if(files[i].isDirectory()){
						removeFolder(files[i]);
					}
				}
			}else {
				filesPath.delete();
			}
			filesPath.delete();
		} catch (Exception e) {
			System.out.println("删除失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 6.文件重命名
	 * 注意：1.新名字和旧名字不能一样；2新名字对应的文件要不存在，要不然就会同名
	 */
	public static void rename(File oldPath, File newPath){
		String oldName = oldPath.getPath();
		String newName = newPath.getPath();
		if(!oldName.equals(newName)){
			if(!newPath.exists()){
				oldPath.renameTo(newPath);
			}
		}
	}
	
	/**
	 * 7.文件移动
	 * 注意：文件移动，如果路径一致，相当于重开一个副本；如果路径不一致，直接重命名就可以了
	 * 重命名不仅仅是对文件的名字起作用，对文件的路径等都是有效的
	 */
	public static void moveFile(File source,File destination){
			try {
				if(!source.exists()){
					throw new Exception("文件不存在！");
				}
				StringBuilder fileName =new StringBuilder(source.getName());
				if(source.getPath().equals(destination.getPath())){
					if(fileName.indexOf(".") > 0){
						fileName.insert(fileName.lastIndexOf("."), "_副本");
					} else {
						fileName.append("_副本");
					}
					copyFile(source, new File(source.getParent() +"\\"+ fileName));
				} else {
					source.renameTo(destination);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 8.获取文件扩展名信息
	 */
	public static String[] extensions(File file){
		String[] extensions = new String[3];
		if(file.isFile()){
			String fileName = file.getName();
			if(fileName.lastIndexOf(".") > 0){
				int lastIndex = fileName.lastIndexOf(".");
				extensions[0] = fileName.substring(0, lastIndex); //文件名
				extensions[1] = fileName.substring(lastIndex+1);  //扩展名
				extensions[2] = String.valueOf(lastIndex); //扩展名的“.”的索引
				
			}
		}
		System.out.println(extensions[0]+" , "+extensions[1]+" , "+extensions[2]);
		return extensions;
	}
	
	/**
	 * 9.遍历文件
	 *   打印出文件的树状目录结构
	 * @throws Exception 
	 */
	public static StringBuilder searchFolder(File file,int level){
		StringBuilder sb = new StringBuilder();
		try {
			if(!file.exists()){
					throw new Exception("文件不存在！");
			}
			for(int i=0;i<level;i++){
				sb.append("--");
			}
			sb.append(file.getName()+"\n");
			if(file.isDirectory()){
				File[] files = file.listFiles();
				level++;
				for(int i=0;i<files.length;i++){
					sb.append(searchFolder(files[i],level));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}
				
	/**
	 * 10.文件排序 
	 */
	
	/**
	 * 11.文件合并 
	 */
	
	/**
	 * 12.读取文本文件
	 */
	public static StringBuilder readFile(File file,String charset){
		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader =null;
		if(null == charset || "".equals(charset)){
			charset = "UTF-8";
		}
		try {
			if(!file.exists()||file.isDirectory()){
				throw new FileNotFoundException();
			}
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),charset));
			String temp ;
			while((temp=bufferedReader.readLine()) != null){
				sb.append(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(null != bufferedReader){
						bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
		
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
