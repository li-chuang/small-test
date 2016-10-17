package com.lichuang.Oct16;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class NIOUtils {

	public static void main(String[] args) {
		// copyFile(new File("F:\\testData"),new File("E:\\testData"));
		// copyFiles(new File("F:\\war3"),new File("E:\\war3"));
		// readContent(new File("E:\\wen.txt"));  //E:\\logFile_2.txt"
		readChinaContent(new File("E:\\wen.txt"));
	}
	
	/**
	 * 1.用NIO复制文件
	 * @param source
	 * @param destination
	 */
	public static void copyFile(File source,File destination){
		try {
			if(!source.exists()){
				throw new Exception("File not exists");
			}
			//得到一个源文件对应的输入通道
			FileChannel fic = new FileInputStream(source).getChannel();
			//得到一个目标文件对应的输出通道  
			FileChannel foc = new FileOutputStream(destination).getChannel();
			//生成一个1024字节的ByteBuffer实例 
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			
			while(fic.read(byteBuffer) != -1){
				byteBuffer.flip();  //准备写  
				foc.write(byteBuffer);
				byteBuffer.clear(); //准备读
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.用NIO复制文件夹
	 */
	public static void copyFiles(File source,File destination){
		try {
			if(!source.exists()){
				throw new Exception("File Not Found!!");
			}
			if(!destination.exists()){
				destination.mkdirs();
			}
			if(source.isDirectory()){
				String[] srcList = source.list();
				for(int i=0;i<srcList.length;i++){
					String src = source.getPath() + "\\" + srcList[i];
					String dest = destination.getPath() + "\\" + srcList[i];
					File subSrc = new File(src);
					if(subSrc.isDirectory()){
						copyFiles(subSrc,new File(dest));
					}else{
						copyFile(subSrc,new File(dest));
					}
					
				}
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * 3.读取文件内容(英文)
	 *   
	 */
	public static void readContent(File file){
		try {
			//第一步：从FileInputStream获取通道
			FileChannel fc = new FileInputStream(file).getChannel();
			//第二步：创建缓冲区
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			//第三步：将数据从通道读到缓冲区中
			fc.read(byteBuffer);
			
			byteBuffer.flip(); // 位置设置为0，在开始的位置做好输出的准备 
			while(byteBuffer.hasRemaining()){
				System.out.print((char)byteBuffer.get());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 4.读取文件内容(汉字)
	 */
	public static void readChinaContent(File file){
		try {
			FileChannel fc = new FileInputStream(file).getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			fc.read(byteBuffer);
			Charset charset = Charset.forName("GBK");
			CharsetDecoder decoder = charset.newDecoder();
			CharBuffer charBuffer = decoder.decode(byteBuffer);
			System.out.println(charBuffer); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

