package com.lichuang.Nov06;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSAXUtils {
	
	public static void main(String[] args) throws Exception {
		parseXML("D:/students.xml", new XmlSAXHandler01());
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
 *
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
