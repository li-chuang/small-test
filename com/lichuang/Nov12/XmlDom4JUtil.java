package com.lichuang.Nov012;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlDom4JUtil {
	public static void main(String[] args) throws Exception {
		//parseXml("E:/students.xml");
		//addXmlElement("D:/students.xml","E:/students.xml");
		//updateXmlElement("D:/students.xml","E:/students.xml");
		
		//deleteXmlElement("D:/students.xml","E:/students.xml");
		
		//parseXMLToBean("D:/students.xml");
		
		List<Student> list = new ArrayList<Student>();
		list.add(new Student("006", "A123", "LiSi", "BeiJing", "52"));
		list.add(new Student("007", "A124", "ZhangSan", "HeFei", "91"));
		list.add(new Student("008", "A125", "LiuMing", "NanJing", "17"));
		list.add(new Student("009", "A126", "MaLu", "ShangHai", "85"));
		buildXMLByBean(list,"F:/students.xml");
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
	
	// 删除节点和属性
	public static void deleteXmlElement(String path,String dest) throws Exception{
		SAXReader reader = new SAXReader(); 
		Document doc = reader.read(path);
		
		Element students = doc.getRootElement();
		List<Element> list = students.elements();
		for(Element stuElement :list){
			if("005".equals(stuElement.attributeValue("idcard"))){
				students.remove(stuElement);
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
	
	//将XML文件解析为对象
	public static void parseXMLToBean(String path) throws Exception{
		SAXReader reader = new SAXReader();
		Document doc = reader.read(path);
		
		Element students = doc.getRootElement();
		List<Element> stuElement = students.elements();
		List<Student> list = new ArrayList<Student>();
		for(Element element :stuElement){
			Student student = new Student();
			student.setIdcard(element.attributeValue("idcard"));
			student.setName(element.elementText("name"));
			student.setLocation(element.elementText("location"));
			student.setExamid(element.elementText("examid"));
			student.setGrade(element.elementText("grade"));
			list.add(student);
		}
		System.out.println(list);
	}
	
	//将实例对象构建成XML文件
	public static void buildXMLByBean(List<Student> students,String dest) throws Exception{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("students");
		for(int i=0;i<students.size();i++){
			Student student = students.get(i);
			Element stuElement = root.addElement("student");
			stuElement.addAttribute("idcard", student.getIdcard());
			stuElement.addElement("name").setText(student.getName());
			stuElement.addElement("location").setText(student.getLocation());
			stuElement.addElement("examid").setText(student.getExamid());
			stuElement.addElement("grade").setText(student.getGrade());
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

class Student {
	private String idcard;  
    private String examid;  
    private String name;  
    private String location;  
    private String grade;
        
	public Student() {
	}

	public Student(String idcard, String examid, String name, String location,String grade) {
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Student [idcard=" + idcard + ", examid=" + examid + ", name="
				+ name + ", location=" + location + ", grade=" + grade + "]";
	}
}
