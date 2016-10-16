package com.lichuang.Oct16;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOUtils {

	public static void main(String[] args) {
		copyFile(new File("F:\\3.jpg"),new File("E:\\3.jpg"));
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
			FileChannel fic = new FileInputStream(source).getChannel();
			FileChannel foc = new FileOutputStream(destination).getChannel();
			
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			
			while(fic.read(byteBuffer) != -1){
				byteBuffer.flip();
				foc.write(byteBuffer);
				byteBuffer.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

