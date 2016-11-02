package com.lichuang.Nov02;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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

	//将内存中的文档树保存为.xml文档
	private static void extractXML(Document doc,String path) {	
		Transformer transformer;
		try {
			//得到转换器
			transformer = TransformerFactory.newInstance().newTransformer();
			//设置换行
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//写入文件
			transformer.transform(new DOMSource(doc), new StreamResult(new File(path)));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	// 创建一个XML文件
	public static void createXml(){
		//创建一个文件根节点doc
		Document doc = getDocument();
		//创建一大堆的元素/节点，然后按照自己的意愿安顿
		Element students = doc.createElement("students");		
		Element student = doc.createElement("student");
		Element name = doc.createElement("name");
		Node attrName = doc.createTextNode("Lich");//末尾的称为节点，其他的都是元素
		
		//添加子节点
		doc.appendChild(students);
		students.appendChild(student);
		student.appendChild(name);
		student.setAttribute("age", "99");//这里添加的就是属性值
		name.appendChild(attrName);
		//System.out.println(doc);
		// 生成.xml文档
		extractXML(doc,"D:/students.xml");
	}

	
	
	// 用DOM创建一个XML文件
	
	public static void main(String[] args) {
		//System.out.println(getDocument());
		createXml();
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
