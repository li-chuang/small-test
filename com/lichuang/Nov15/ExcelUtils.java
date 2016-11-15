package com.lichuang.Nov15;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {
	public static void main(String[] args) {
		createExcel(new Student(),"F:/text.xls");
	}

	// 生成一个Excel文件
	public static void createExcel(Student student,String path) {
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet("第一个sheet");

		HSSFRow row = sheet.createRow(0);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("序号");

		cell = row.createCell(1);
		cell.setCellValue("姓名");
		
		cell = row.createCell(2);
		cell.setCellValue("年龄");
		
		cell = row.createCell(3);
		cell.setCellValue("地址");

		exportFile(wb, path);
	}

	// 将excel文件导出
	private static void exportFile(HSSFWorkbook wb, String path) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		File file = new File(path);// Excel文件生成后存储的位置。
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(content);
			os.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Student{
	private String id;
	private String name;
	private String age;
	private String address;
	
	public Student(){
		
	}	
	public Student(String id, String name, String age, String address) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
