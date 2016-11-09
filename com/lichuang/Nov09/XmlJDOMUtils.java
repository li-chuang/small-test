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
		
		//addXmlElement(doc);
		//updateXmlElement(doc);
		deleteXmlElement(doc);
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
	
	// 新增节点
	public static void addXmlElement(Document doc) throws Exception{
		//1.获取根元素
		Element root = doc.getRootElement();
		
		//2.每个节点都可以new出来，有属性都可以直接设定
		Element student = new Element("student");
		student.setAttribute("idcard", "07152259");
		
		//3.一般节点也可以设置text值，之后附加到主节点上就可以了
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
		
		//4.不要忘了附加到根节点上
		root.addContent(student);
		
		//5.输出，有乱码就记得转码
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getCompactFormat().setEncoding("GBK"));
		out.output(doc, new FileWriter("E:/students.xml"));
	}

	// 修改节点
	public static void updateXmlElement(Document doc) throws Exception{
		//1.获得根目录
		Element root = doc.getRootElement();
		//2.找到你需要修改的地方
		for(Element e :root.getChildren("student")){
			if("07152225".equals(e.getAttribute("idcard").getValue())){
				Element name = e.getChild("name");
				//3.按照自己的需求进行修改
				name.setText("li_ch");
			}
		}
		//4.输出，记得不要输出到原文件，这里似乎是不可以覆盖原文件的
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getCompactFormat().setEncoding("GBK"));
		out.output(doc, new FileWriter("F:/students.xml"));
	}
	
	// 删除节点和属性
	public static void deleteXmlElement(Document doc) throws Exception{
		Element root = doc.getRootElement();
		
		List<Element> list = root.getChildren("student");
		
		for(Element student : list){
			if("07152225".equals(student.getAttribute("idcard").getValue())){
				student.removeAttribute("idcard");//删除属性
				student.removeChild("grade");//删除子节点
			}
		}
		
		for(Element student : list){
			if(student.getAttribute("idcard")!=null && "07152247".equals(student.getAttribute("idcard").getValue())){
				root.removeContent(student);//删除指定的节点，
				// removeChild(String)也可以删除子节点，但毕竟是用标签删除，无法区分各个标签的名字，
				// root的所有子节点都叫做student
			}
		}
		
		XMLOutputter out = new XMLOutputter();
        out.setFormat(Format.getCompactFormat().setEncoding("GBK"));//设置UTF-8编码,理论上来说应该不会有乱码，但是出现了乱码,故设置为GBK
        out.output(doc, new FileWriter("F:/students.xml")); //写文件
	}
	
	

	
}
