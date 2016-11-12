package com.lichuang.Nov012;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlDom4JUtil {
	public static void main(String[] args) throws Exception {
		parseXml("E:/students.xml");
		//addXmlElement("D:/students.xml","E:/students.xml");
		//updateXmlElement("D:/students.xml","E:/students.xml");
	}

	//解析xml文件
	public static void parseXml(String path) throws Exception{
		//获得解析器
		SAXReader reader = new SAXReader();
		//获得文本文件
		Document doc = reader.read(path);
		//获得根文件
		Element root = doc.getRootElement();
		List<Element> students = root.elements();
		for(Element student : students){
			//节点下获取有关属性的集合
			List<Attribute> attributes = student.attributes();
			for(Attribute attr: attributes){
				// name和value是比较简单而且有优势的地方
				String name = attr.getName();
				String value = attr.getValue();
				System.out.println(name +" = "+value);
			}
			//节点下获取有关子节点的集合
			List<Element> elements = student.elements();
			for(Element elem : elements){
				//而对于节点，获取name和text非常简单
				String name = elem.getName();
				String text = elem.getText();
				System.out.println(name +" = "+text);
				
			}
		}
		
	}
	
	// 新增节点
	public static void addXmlElement(String path,String dest) throws Exception{
		SAXReader reader = new SAXReader();
		Document doc = reader.read(path);
		
		Element root = doc.getRootElement();
		
		Element student = root.addElement("student");
		student.addAttribute("idcard", "005");
		student.addElement("name").setText("FangJR");
		student.addElement("examid").setText("A127");
		student.addElement("location").setText("GuangZhou");
		student.addElement("grade").setText("100");
		
		OutputFormat format = OutputFormat.createPrettyPrint();  
        // 设置编码  
        format.setEncoding("UTF-8");  
        // 创建XMLWriter对象,指定了写出文件及编码格式   
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(  
                new FileOutputStream(new File(dest)), "UTF-8"), format);  
        // 写入  
        writer.write(doc);  
        // 立即写入  
        writer.flush();  
        // 关闭操作  
        writer.close();  
	}
	
	// 修改节点
	public static void updateXmlElement(String path,String dest) throws Exception{
		SAXReader reader = new SAXReader();
		Document doc = reader.read(path);
			
		Element root = doc.getRootElement();
		List<Element> students =  root.elements();
		for(Element student:students){
			if("005".equals(student.attributeValue("idcard"))){
				root.remove(student);
			}
		}
		
		OutputFormat format = OutputFormat.createPrettyPrint();  
        // 设置编码  
        format.setEncoding("UTF-8");  
        // 创建XMLWriter对象,指定了写出文件及编码格式   
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(  
                new FileOutputStream(new File(dest)), "UTF-8"), format);  
        // 写入  
        writer.write(doc);  
        // 立即写入  
        writer.flush();  
        // 关闭操作  
        writer.close();  
	}
}
