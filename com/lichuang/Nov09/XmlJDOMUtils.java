package com.lichuang.Nov08;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class XmlJDOMUtils {
	
	public static void main(String[] args) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		//获取文件节点
		Document doc = builder.build("D:/students.xml");
		
		parseXML(doc);
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
				String studentName = stuAttr.getName();
				String name = stuAttr.getText();
				System.out.println(studentName + " = " + name);
			}
		}
	}

}
