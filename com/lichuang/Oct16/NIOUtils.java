package com.lichuang.Oct16;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

public class NIOUtils {

	public static void main(String[] args) {
		// copyFile(new File("F:\\testData"),new File("E:\\testData"));
		// copyFiles(new File("F:\\war3"),new File("E:\\war3"));
		// readContent(new File("E:\\wen.txt")); //E:\\logFile_2.txt"

		// readChinaContent(new File("E:\\wen.txt"));

		// getCharset();

		// writeToFile(new File("E:\\lichuang.txt"),"你好，Java");
		// addContentToEnd(new File("E:\\lichuang.txt"), "我很好");
		
		/*List<String> result = largeMapFile(new File("E:\\lichuang.txt"), "我很好");
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}*/
		
		castByBuffer(new byte[] {0, 1, 2, 3, 4, 5, 'a', 'b', 'c'});
	}

	/**
	 * 1.用NIO复制文件
	 * 
	 * @param source
	 * @param destination
	 */
	public static void copyFile(File source, File destination) {
		try {
			if (!source.exists()) {
				throw new Exception("File not exists");
			}
			// 得到一个源文件对应的输入通道
			FileChannel fic = new FileInputStream(source).getChannel();
			// 得到一个目标文件对应的输出通道
			FileChannel foc = new FileOutputStream(destination).getChannel();
			// 生成一个1024字节的ByteBuffer实例
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

			while (fic.read(byteBuffer) != -1) {
				byteBuffer.flip(); // 准备写
				foc.write(byteBuffer);
				byteBuffer.clear(); // 准备读
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 2.用NIO复制文件夹
	 */
	public static void copyFiles(File source, File destination) {
		try {
			if (!source.exists()) {
				throw new Exception("File Not Found!!");
			}
			if (!destination.exists()) {
				destination.mkdirs();
			}
			if (source.isDirectory()) {
				String[] srcList = source.list();
				for (int i = 0; i < srcList.length; i++) {
					String src = source.getPath() + "\\" + srcList[i];
					String dest = destination.getPath() + "\\" + srcList[i];
					File subSrc = new File(src);
					if (subSrc.isDirectory()) {
						copyFiles(subSrc, new File(dest));
					} else {
						copyFile(subSrc, new File(dest));
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
	public static void readContent(File file) {
		try {
			// 第一步：从FileInputStream获取通道
			FileChannel fc = new FileInputStream(file).getChannel();
			// 第二步：创建缓冲区
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			// 第三步：将数据从通道读到缓冲区中
			fc.read(byteBuffer);

			byteBuffer.flip(); // 位置设置为0，在开始的位置做好输出的准备
			while (byteBuffer.hasRemaining()) {
				System.out.print((char) byteBuffer.get());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 4.读取文件内容(汉字) 
	 * 读取汉字的最大要点是要注意汉字的编码格式，byte[]数据在不同编码格式下， new String(byte[],
	 * Charset) 后的字符串是不一样的，有可能产生乱码。
	 */
	public static void readChinaContent(File file) {
		try {
			FileChannel fc = new FileInputStream(file).getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			fc.read(byteBuffer);
			Charset charset = Charset.forName("GBK");
			// CharsetDecoder decoder = charset.newDecoder();
			System.out.println(new String(byteBuffer.array(), charset));
			// CharBuffer charBuffer = decoder.decode(byteBuffer);
			// System.out.println("It's "+charBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 5.获取Java支持的全部字符集
	 */
	public static void getCharset() {
		SortedMap<String, Charset> map = Charset.availableCharsets();
		for (String charname : map.keySet()) {
			System.out.println(charname + " : " + map.get(charname));
		}
	}

	/**
	 * 6.向一个文件中写入文本
	 */
	public static void writeToFile(File destination, String str) {
		try {
			FileChannel fc = new FileOutputStream(destination).getChannel();
			fc.write(ByteBuffer.wrap(str.getBytes()));
			fc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 7.以读写方式打开文件，并在尾部追加内容
	 * 
	 * RandomAccessFile可以任意位置读写文件，向1G大小的文件末尾添加一句话，读取文件然后字符串拼接就可以，
	 * 但面对超过内存的超大文件，如给20G大小的文件末尾添加一句话，IO就无能为力了，内存会溢出。
	 * 于是这个时候RandomAccessFile的作用就凸显出来了，它可以实现零内存添加。其实这就是支持任意位置读写类的强大之处。
	 * 
	 * 如果我们只希望访问文件的部分内容，而不是把文件从头读到尾，使用RandomAccessFile将会带来更简洁的代码以及更好的性能。
	 */
	public static void addContentToEnd(File destination, String str) {
		try {
			FileChannel fc = new RandomAccessFile(destination, "rw")
					.getChannel();
			fc.position(fc.size()); // 通过position方法设置FileChannel的当前位置，此处是设定到了末尾
			// 其实RandomAccessFile的seek()方法也可以移动指针位置。指针在哪里，就从哪里添加
			fc.write(ByteBuffer.wrap(str.getBytes())); // wrap()
														// 方法将一个数组包装为缓冲区，从而直接使用
			fc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 8.利用RandomAccessFile实现文件的多线程下载 即多线程下载一个文件时，将文件分成几块，每块用不同的线程进行下载
	 * 
	 * 由于这是IO的知识点，具体的代码转移到IOUtils.java中
	 * 
	 */

	/**
	 * 9.将一个大文件映射到内存并查找指定的文本内容是否在该文件中 
	 *   注意MappedByteBuffer的使用是重点
	 * 
	 */
	public static List<String> largeMapFile(File file, String text) {
		// 把检索结果放到一个list中
		List<String> result = new ArrayList<String>();
		try {
			// 将整个文件映射到内存 ，映射多少可以自己决定，此处是全部映射
			MappedByteBuffer dst = new RandomAccessFile(file, "rw")
					.getChannel().map(FileChannel.MapMode.READ_WRITE, 0,
							file.length());
			// 对字符进行解码，UTF-8解码汉字为乱码，用GBK解码汉字就是对的
			String str = Charset.forName("GBK").decode(dst).toString();
			// 准备进行读
			dst.flip();
			// 此处只判断有没有，检索结果还可以更复杂，都可以放在list中传递出去
			if (str.indexOf(text) != -1) {
				result.add(file.getName());
			}
			// 准备写
			dst.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 10.数据类型的转换
	 *   NIO中可以利用ByteBuffer的asXBuffer方法实现数据类型的转换
	 */
	public static void castByBuffer(byte[] bytes){
		ByteBuffer bBuf = ByteBuffer.wrap(bytes);
		System.out.println(Arrays.toString(bBuf.array()));
		//转换成IntBuffer  
        IntBuffer iBuf = ((ByteBuffer)bBuf.rewind()).asIntBuffer();  
        while(iBuf.hasRemaining()) {  
            System.out.print(iBuf.get()+",");  
        }  
        //转换成FloatBuffer  
        FloatBuffer fBuf = ((ByteBuffer)bBuf.rewind()).asFloatBuffer();  
        while(fBuf.hasRemaining()) {  
            System.out.print(fBuf.get()+",");  
        }  
	}	

}












