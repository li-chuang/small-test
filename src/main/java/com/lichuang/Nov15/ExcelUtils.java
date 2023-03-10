package com.lichuang.Nov15;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * 这里对excel的处理还是比较简单的，只完成了最基本的生成和解析，如果要使代码具有通用性，可以使用反射进行赋值。
 * 这算是一个点吧，以后如果有时间再改进，现在就到这了，用的地方也不多，和前端js结合起来一起用好像常见一些。
 *
 */
public class ExcelUtils {
	public static void main(String[] args) throws Exception {
//		List<Student> list = new ArrayList<Student>();
//		list.add(new Student("001", "zhangsan", "100", "beijing"));
//		list.add(new Student("002", "lisi", "77", "shanghai"));
//		list.add(new Student("003", "wangwu", "85", "nanjing"));
//		list.add(new Student("004", "zhaoliu", "60", "wuhan"));
//		list.add(new Student("005", "qianqi", "98", "chongqin"));
//		createExcel(list,"F:/text.xls");
		
		parseExcel("F:/text.xls");
	}

	// 生成一个Excel文件
	public static void createExcel(List<Student> list,String path) {
//		HSSFWorkbook wb = new HSSFWorkbook();
//
//		HSSFSheet sheet = wb.createSheet("第一个sheet");
//
//		HSSFRow row = sheet.createRow(0);
//
//		HSSFCell cell = row.createCell(0);
//		cell.setCellValue("序号");
//
//		cell = row.createCell(1);
//		cell.setCellValue("姓名");
//
//		cell = row.createCell(2);
//		cell.setCellValue("年龄");
//
//		cell = row.createCell(3);
//		cell.setCellValue("地址");
//
//		for(int i=0;i<list.size();i++){
//			Student student = list.get(i);
//			HSSFRow hssfRow = sheet.createRow(i+1);
//
//			HSSFCell hssfCell = hssfRow.createCell(0);
//			hssfCell.setCellValue(student.getId());
//
//			hssfCell = hssfRow.createCell(1);
//			hssfCell.setCellValue(student.getName());
//
//			hssfCell = hssfRow.createCell(2);
//			hssfCell.setCellValue(student.getAge());
//
//			hssfCell = hssfRow.createCell(3);
//			hssfCell.setCellValue(student.getAddress());
//		}
//
//		exportFile(wb, path);
	}
	
	// 解析excel文件
	public static void parseExcel(String path) throws Exception {
//		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
//		HSSFSheet sheet = workbook.getSheetAt(0);
//		HSSFRow row = sheet.getRow(0);
//		HSSFCell cell = row.getCell(0);
//		//System.out.println(cell.getStringCellValue());
//		int rowNum = sheet.getLastRowNum();//获得行数
//		//int colNum = row.getPhysicalNumberOfCells();//获得列数
//		List<Student> list = new ArrayList<Student>();
//		for(int i=0;i<rowNum;i++){
//			row = sheet.getRow(i+1);
//			Student student = new Student();
//
//			cell = row.getCell(0);
//			student.setId(cell.getStringCellValue());
//
//			cell = row.getCell(1);
//			student.setName(cell.getStringCellValue());
//
//			cell = row.getCell(2);
//			student.setAge(cell.getStringCellValue());
//
//			cell = row.getCell(3);
//			student.setAddress(cell.getStringCellValue());
//
//			list.add(student);
//		}
//		System.out.println(list);
//	}
//
//	// 将excel文件导出
//	private static void exportFile(HSSFWorkbook wb, String path) {
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		try {
//			wb.write(os);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		byte[] content = os.toByteArray();
//		File file = new File(path);// Excel文件生成后存储的位置。
//		OutputStream fos = null;
//		try {
//			fos = new FileOutputStream(file);
//			fos.write(content);
//			os.close();
//			fos.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age
				+ ", address=" + address + "]";
	}
	
}
