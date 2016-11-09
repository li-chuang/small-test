package com.lichuang.Nov08;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlJDOMUtils {
	
	public static void main(String[] args) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		//获取文件节点
		Document doc = builder.build("D:/students.xml");
		
		//parseXML(doc);
		
		addXmlElement(doc);
	}
	
	//解析XML文件
	public static void parseXML(Document doc){
		Element root = doc.getRootElement();
		List<Element> list = root.getChildren("student");
		for(int i=0;i<list.size();i++){
			System.out.println("---获取第 "+(i+1)+" 个的节点信息------");
			Element student = list.get(i);
			String idcard = student.getAttributeValue("idcard");
			System.out.println("idcard = " + idcard);
			for(Element stuAttr :student.getChildren()){
				String attrName = stuAttr.getName();
				String attrValue = stuAttr.getText();
				System.out.println(attrName + " = " + attrValue);
			}
		}
	}
	
	public static void addXmlElement(Document doc) throws Exception{
		Element root = doc.getRootElement();
		
		Element student = new Element("student");
		student.setAttribute("idcard", "07152259");
		
		Element name = new Element("name");
		name.setText("ZhangSanFeng");
		student.addContent(name);
		
		Element location = new Element("location");
		location.setText("ChengDu");
		student.addContent(location);
		
		Element examid = new Element("examid");
		examid.setText("1013");
		student.addContent(examid);
		
		Element grade = new Element("grade");
		grade.setText("75");
		student.addContent(grade);
		
		root.addContent(student);
		
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getCompactFormat().setEncoding("GBK"));
		out.output(doc, new FileWriter("E:/students.xml"));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
