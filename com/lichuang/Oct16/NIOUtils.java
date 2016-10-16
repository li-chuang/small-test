package com.lichuang.Oct16;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOUtils {

	public static void main(String[] args) {
		// copyFile(new File("F:\\testData"),new File("E:\\testData"));
		copyFiles(new File("F:\\war3"),new File("E:\\war3"));
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
	
	
}


