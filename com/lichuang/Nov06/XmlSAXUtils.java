package com.lichuang.Nov06;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSAXUtils {
	
	public static List<Node> nodeList = null;
	public static Node node = null;
	
	public static List<Student> studentList = null;
	public static Student student = null;
	
	public static void main(String[] args) throws Exception {
		//parseXML("D:/students.xml", new XmlSAXHandler01());
		//parseXML("D:/students.xml", new XmlSAXHandler02());
		parseXml03();
	}
	
	// 使用SAX的方式解析XML，传入XML文件路径和解析的处理类
	public static void parseXML(String path,DefaultHandler handler) throws Exception{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(path,handler);
	}
	
	// 将解析的XML信息注入对象中保留
	public static void parseXml03() throws Exception{
        String xmlPath = "D:/students.xml";  
		//获取SAX分析器的工厂实例，专门负责创建SAXParser分析器
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		//获取SAXParser分析器的实例
		SAXParser saxParser = saxParserFactory.newSAXParser();
        InputStream inputStream = new FileInputStream(new File(xmlPath));  			
		//解析xml文档
		saxParser.parse(inputStream, new XmlSAXHandler03());
			
		//迭代list
		if(XmlSAXUtils.nodeList.size() > 0){
			for (Node node : XmlSAXUtils.nodeList) {
				System.out.println("-----------------------------------------");
				System.out.println("【节点】" + node.getName() + "：" + node.getText());
				List<Attribute> attributeList = node.getAttributeList();
				if (attributeList != null) {
					for (Attribute attribute : attributeList) {
						System.out.println("【属性】" + attribute.getName() + "：" + attribute.getValue());							
					}
				}
			}
		}			
	}
	
	public void parseXml04() throws Exception {
        String xmlPath = "D:/students.xml";  
			//获取SAX分析器的工厂实例，专门负责创建SAXParser分析器
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			//获取SAXParser分析器的实例
			SAXParser saxParser = saxParserFactory.newSAXParser();
            InputStream inputStream = new FileInputStream(new File(xmlPath));  
			
			//解析xml文档
			saxParser.parse(inputStream, new XmlSAXHandler04());
			
			//迭代list
			if(XmlSAXUtils.studentList.size() > 0){
				for (Student student : XmlSAXUtils.studentList) {
					System.out.println("-----------------------------------------");
					System.out.println("【Id】" + student.getIdcard());
					System.out.println("【姓名】" + student.getName());
					System.out.println("【年龄】" + student.getLocation());
					System.out.println("【爱好】" + student.getGrade());
					System.out.println("【爱好】" + student.getExamid());
				}
			}
	}

}

/**
 * 解析SAX的处理者01
 * 入门级：简单的了解下SAX解析中的事务驱动原理
 */
class XmlSAXHandler01 extends DefaultHandler{

	@Override
	public void startDocument() throws SAXException {
		System.out.println("---->startDocument() is invoked...");
        super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("---->endDocument() is invoked...");
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		System.out.println("-------->startElement() is invoked...");
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("-------->endElement() is invoked...");
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		System.out.println("------------>characters() is invoked...");
		super.characters(ch, start, length);
	}
	
}

/**
 * 解析SAX的处理者02
 * 深入级：不仅仅知道每个动作的背后发生了什么，还要借此机会获取节点内的内容
 *
 */
class XmlSAXHandler02 extends DefaultHandler {  
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("---->startDocument() is invoked...");
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("---->endDocument() is invoked...");
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("-------->startElement() is invoked...");
		System.out.println("uri的属性值：" + uri);
		System.out.println("localName的属性值：" + localName);
		System.out.println("qName的属性值：" + qName);
		if(attributes.getLength()>0){
			System.out.println("element属性值-->" + attributes.getQName(0) + "：" + attributes.getValue(0)); //根据下标获取属性name和value，也可以直接传递属性name获取属性值：attributes.getValue("id")
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("-------->endElement() is invoked...");
		System.out.println("uri的属性值：" + uri);
		System.out.println("localName的属性值：" + localName);
		System.out.println("qName的属性值：" + qName);
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		System.out.println("------------>characters() is invoked...");
		System.out.println("节点元素文本内容：" + new String(ch, start, length));
	}
}

/**
 * 解析SAX的处理者03
 *
 */
class XmlSAXHandler03 extends DefaultHandler {  
	
	@Override
	public void startDocument() throws SAXException {
		XmlSAXUtils.nodeList = new ArrayList<Node>();
	}
	
	@Override
	public void endDocument() throws SAXException {
		
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		XmlSAXUtils.node = new Node();
		XmlSAXUtils.node.setId(null);
		XmlSAXUtils.node.setName(qName);
		//封装当前节点的属性
		List<Attribute> attributeList = new ArrayList<Attribute>();
		if(attributes.getLength()>0){
			for (int i = 0; i < attributes.getLength(); i++) {
				Attribute attribute = new Attribute();
				attribute.setName(attributes.getQName(i));
				attribute.setValue(attributes.getValue(i));
				attributeList.add(attribute);
			}
		}
		XmlSAXUtils.node.setAttributeList(attributeList);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(XmlSAXUtils.node != null){
			XmlSAXUtils.node.setText(new String(ch, start, length));
			//因为startElement()在characters()的前面调用，所以必须放在后面才能把文本添加进去
			XmlSAXUtils.nodeList.add(XmlSAXUtils.node);
			XmlSAXUtils.node = null;
			//在这里之所以重新置为null是在解析标签的时候节点与节点之间的回车符、Tab符或者空格以及普通文本等等这些字符串也算一个节点
			//如果不这样，那么解析的时候会把之前添加成功的节点内的文本给替换掉。
		}
	}
}

/**
 * 解析SAX的处理者04
 * @author Administrator
 *
 */
class XmlSAXHandler04 extends DefaultHandler {  
	
	private String currentQName;  //因为startElement()才能获取到标签名称，但是标签的内容在characters()获取，而且他们的执行顺序一直是顺序的，所以可以使用currentQName来过渡一下获取上一次的标签名
	
	@Override
	public void startDocument() throws SAXException {
		XmlSAXUtils.studentList = new ArrayList<Student>();
	}
	
	@Override
	public void endDocument() throws SAXException {
		
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("student")){
			XmlSAXUtils.student = new Student(); //每次解析到user标签了才会创建student对象的实例
			//添加student标签中的id属性
			if(attributes.getLength() > 0){
				XmlSAXUtils.student.setIdcard(attributes.getValue("idcard"));
			}
		}
		this.currentQName = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//需要说明的是，因为每一个非空标签都有characters(),那么无法知道user子标签循环完了
		//但是可以这样，如果不考虑子标签顺序可以判断是否解析到了最后一个子标签来判断
		//或者直接在user标签的endElement()中添加即可。
		if(qName.equals("student")){  
			XmlSAXUtils.studentList.add(XmlSAXUtils.student);
			XmlSAXUtils.student = null;
		}
		this.currentQName = null;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String content = new String(ch, start, length);
		//System.out.println(currentQName + "：" + content);
		if(XmlSAXUtils.student != null && currentQName != null){
			if(currentQName.equals("name")){
				XmlSAXUtils.student.setName(content);
			}else if(currentQName.equals("examid")){
				XmlSAXUtils.student.setExamid(content);
			}else if(currentQName.equals("location")){
				XmlSAXUtils.student.setLocation(content);
			}else if (currentQName.equals("grade")){
				XmlSAXUtils.student.setGrade(content);
			}
		}
	}
}
