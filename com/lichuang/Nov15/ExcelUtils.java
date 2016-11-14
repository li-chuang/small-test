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
		createExcel();
	}

	//生成一个Excel文件
	public static void createExcel(){
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet("第一个sheet");
		
		HSSFRow row = sheet.createRow(0);
		
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("序号");
		
		cell = row.createCell(1);
		cell.setCellValue("姓名");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            wb.write(os);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        byte[] content = os.toByteArray();
        
        File file = new File("F:/text.xls");//Excel文件生成后存储的位置。
        
        OutputStream fos  = null;
        
        try
        {
            fos = new FileOutputStream(file);
            
            fos.write(content);

            os.close();

            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }     
	}
}

