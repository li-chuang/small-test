package com.lichuang.Nov06;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSAXUtils {
	
	public static void main(String[] args) throws Exception {
		//parseXML("D:/students.xml", new XmlSAXHandler01());
		parseXML("D:/students.xml", new XmlSAXHandler02());
	}
	
	// 使用SAX的方式解析XML，传入XML文件路径和解析的处理类
	public static void parseXML(String path,DefaultHandler handler) throws Exception{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(path,handler);
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
