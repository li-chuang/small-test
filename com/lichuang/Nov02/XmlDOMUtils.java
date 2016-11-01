package com.lichuang.Nov02;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

/**
 * 利用JAXP进行DOM方式解析
 * 缺点：必须读取整个XML文档，才能构建DOM模型，如果XML文档过大，造成资源的浪费。
 * 优点：适合对XML中的数据进行操作（CRUD）
 *
 */
public class XmlDOMUtils {
	
	private static Document document;
	//得到dom解析器的工厂实例
	public static Document getDocument(){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			if(document == null){				
				document = builder.newDocument();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	// 用DOM创建一个XML文件
	
	public static void main(String[] args) {
		System.out.println(getDocument());
	}

}

class Student{
	private String idcard;  
    private String examid;  
    private String name;  
    private String location;  
    private float grade;
        
	public Student() {
	}

	public Student(String idcard, String examid, String name, String location,float grade) {
		this.idcard = idcard;
		this.examid = examid;
		this.name = name;
		this.location = location;
		this.grade = grade;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getExamid() {
		return examid;
	}

	public void setExamid(String examid) {
		this.examid = examid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
    
    
}
