package com.lichuang.sep22;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;

public class FileTools {

	/**
	 * formatPath 转义文件目录
	 * 
	 * @param path
	 * @return
	 */
	public static String formatPath(String path) {
		return path.replaceAll("\\\\", "/");
	}

	/**
	 * combainPath文件路径合并
	 * 
	 * @param eins
	 * @param zwei
	 * @return
	 */
	private static String combainPath(String eins, String zwei) {
		String dori = "";
		eins = null == eins ? "" : formatPath(eins);
		zwei = null == zwei ? "" : formatPath(zwei);
		if (!eins.endsWith("/") && zwei.indexOf("/") != 0) {
			dori = eins + "/" + zwei;
		} else {
			dori = (eins + zwei).replaceAll("//", "/");
		}
		return dori;
	}

	/**
	 * du 获取文件实际大小
	 * 
	 * @param filePath
	 * @param fileName
	 * @param loop
	 * @return
	 */
	public static long du(String filePath, String fileName, boolean loop) {
		long size = 0;
		try {
			String fullPath = combainPath(filePath, fileName);
			File file = new File(fullPath);
			size = du(file, loop);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return size;
	}

	/**
	 * du 获取文件实际大小
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static long du(String filePath, String fileName) {
		long size = 0;
		try {
			String fullPath = combainPath(filePath, fileName);
			File file = new File(fullPath);
			size = du(file, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return size;
	}

	/**
	 * du 获取文件实际大小
	 * 
	 * @param fullPath
	 * @return
	 */
	public static long du(String fullPath) {
		long size = 0;
		try {
			File file = new File(fullPath);
			size = du(file, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return size;
	}

	/**
	 * du 获取文件实际大小
	 * 
	 * @param file
	 * @return
	 */
	public static long du(File file) {
		long size = 0;
		try {
			size = du(file, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return size;
	}

	/**
	 * du 获取文件实际大小
	 * 
	 * @param fullPath
	 * @param loop
	 * @return
	 */
	public static long du(String fullPath, boolean loop) {
		long size = 0;
		try {
			File file = new File(fullPath);
			size = du(file, loop);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return size;
	}

	/**
	 * du 获取文件实际大小
	 *    真正有效获取文件大小的就只有这个方法，其他的都是对这个方法的引用和包装
	 * @param file
	 * @param loop
	 * @return
	 */
	public static long du(File file, boolean loop) {
		//使用FileChannel的理由是此类可以将整个文件都缓存掉
		//除此之外，使用File下的length()方法也是可行的
		//获取文件大小时注意一个隐藏的bug,InputStream未进行read操作时，available()貌似也可以，
		//不过available()返回值为int，最大表示出2G的大小，再大一些就完蛋了
		//size()和length()都返回long，不会有这个顾虑
		FileChannel fileChannel = null; 
		long size = 0;
		try {
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			if (file.isFile()) {
				FileInputStream fis = new FileInputStream(file);
				fileChannel = fis.getChannel();
				size = fileChannel.size();
				//size = file.length();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				int length = files.length;
				for (int i = 0; i < length && loop; i++) {
					if (files[i].isDirectory()) {
						du(files[i], loop);
					} else {
						size += du(files[i], false);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (null != fileChannel) {
				try {
					fileChannel.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return size;
	}
	
	public static void main(String[] args) {
		System.out.println(du("E:\\lich.rar",true)/(1024.0*1024*1024));
	}

}


