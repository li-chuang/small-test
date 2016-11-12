package com.lichuang.Nov012;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlDom4JUtil {
	public static void main(String[] args) throws Exception {
		parseXml();
	}

	//解析xml文件
	public static void parseXml() throws Exception{
		SAXReader reader = new SAXReader();
		Document doc = reader.read("D:/students.xml");
		
		Element root = doc.getRootElement();
		List<Element> students = root.elements();
		for(Element student : students){
			List<Attribute> attributes = student.attributes();
			for(Attribute attr: attributes){
				String name = attr.getName();
				String value = attr.getValue();
				System.out.println(name +" = "+value);
			}
			List<Element> elements = student.elements();
			for(Element elem : elements){
				String name = elem.getName();
				String text = elem.getText();
				System.out.println(name +" = "+text);
				
			}
		}
		
	}
}
