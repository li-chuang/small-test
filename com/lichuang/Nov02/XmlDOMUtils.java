package com.lichuang.Nov02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 利用JAXP进行DOM方式解析
 * 缺点：必须读取整个XML文档，才能构建DOM模型，如果XML文档过大，造成资源的浪费。
 * 优点：适合对XML中的数据进行操作（CRUD）
 *
 */
public class XmlDOMUtils {
	
	private static DocumentBuilder builder;
	//得到dom解析器的工厂实例
	public static DocumentBuilder getDocumentBuilder(){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			if(builder == null){				
				builder = factory.newDocumentBuilder();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return builder;
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
		DocumentBuilder builder = getDocumentBuilder();
		Document doc = builder.newDocument();
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

	// XML文件的解析
	// 这里有一个大坑，DOM解析的时候，空格都算一个元素，导致有很多无效的子元素，在这里需要对节点的类型进行进行判断
	public static void parseXml(String path){
		DocumentBuilder builder = getDocumentBuilder();
		try {
			Document doc = builder.parse(path);
			NodeList students = doc.getChildNodes();
			for(int i=0;i<students.getLength();i++){
				if(students.item(i).getNodeType() == Node.ELEMENT_NODE){					
					Node student = students.item(i);
					NodeList studenInfo = student.getChildNodes();
					// 这里可能有5个节点
					for(int j=0;j<studenInfo.getLength();j++){
						if(studenInfo.item(j).getNodeType() == Node.ELEMENT_NODE){							
							Node node = studenInfo.item(j);
							NodeList studentMeta = node.getChildNodes();
							// 解析的时候空白也算进去了，所以可能有5个子节点
							for(int k=0;k<studentMeta.getLength();k++){
								if(studentMeta.item(k).getNodeType() == Node.ELEMENT_NODE){									
									System.out.println(studentMeta.item(k).getNodeName()+" : "+studentMeta.item(k).getTextContent());
								}
							}
						}
					}
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//用Bean实例创建XML文件
	//注意树形结构，只要理清了树的结构，DOM可以随时插入节点还是很不错的。
	public static void createXMLByBean(List<Student> list){
		DocumentBuilder builder = getDocumentBuilder();
		Document doc = builder.newDocument();
		Element students = doc.createElement("students");
		doc.appendChild(students);//DOM的好处就是树形结构可以随时插入
		for(Student stu :list){
			Element student = doc.createElement("student");
			student.setAttribute("idcard", stu.getIdcard());//属性可以直接设置
			
			Element name = doc.createElement("name");//节点与文本节点
			name.appendChild(doc.createTextNode(stu.getName()));//doc.createTextNode(stu.getName())
			student.appendChild(name);
			
			Element location = doc.createElement("location");
			location.appendChild(doc.createTextNode(stu.getLocation()));//注意，这里的是文本节点
			student.appendChild(location);
			
			Element examid = doc.createElement("examid");
			examid.appendChild(doc.createTextNode(stu.getExamid()));
			student.appendChild(examid);
			
			Element grade = doc.createElement("grade");
			grade.appendChild(doc.createTextNode(stu.getGrade()));
			student.appendChild(grade);
			
			students.appendChild(student);//归并到树中
		}
		
		// 生成.xml文档
		extractXML(doc,"D:/students.xml");
	}
	
	//解析一个XMl文件到实体类中
	public static List<Student> parseXmlToBean(String path) throws Exception, IOException{
		DocumentBuilder builder = getDocumentBuilder();
		Document doc = builder.parse(path);
		Element students = doc.getDocumentElement();
		NodeList studentList = students.getElementsByTagName("student");//获取带student标签的树分支
		List<Student> list = new ArrayList<Student>(); 
		for(int i=0;i<studentList.getLength();i++){//此处以下就是具体到每棵小树
			Element stuElement = (Element) studentList.item(i);
			Student student = new Student();
			student.setIdcard(stuElement.getAttribute("idcard"));//属性可以直接获取
			
			NodeList stuAttribute = stuElement.getChildNodes();//其他的属性还在子节点中
			for(int j=0;j<stuAttribute.getLength();j++){//此处以下具体到每个属性节点中
				if(stuAttribute.item(j).getNodeType() == Node.ELEMENT_NODE){
					if("name".equals(stuAttribute.item(j).getNodeName())){
						//注意不要忘了getFirstChild(),文字部分也属于节点，是属性节点的下级，获得第一个子节点后才可以获取值
						student.setName(stuAttribute.item(j).getFirstChild().getNodeValue());
					}else if("location".equals(stuAttribute.item(j).getNodeName())){
						student.setLocation(stuAttribute.item(j).getFirstChild().getNodeValue());
					}else if("examid".equals(stuAttribute.item(j).getNodeName())){
						student.setExamid(stuAttribute.item(j).getFirstChild().getNodeValue());
					}else if("grade".equals(stuAttribute.item(j).getNodeName())){
						student.setGrade(stuAttribute.item(j).getFirstChild().getNodeValue());
					}
				}
			}
			list.add(student);
		}
		return list;
		
	}
	
	//解析一个XML文件到控制台以树形结构呈现
	public static void parseXmlToTree(){
		
	}
	
	
	// 用DOM创建一个XML文件	
	public static void main(String[] args) throws Exception{
		//System.out.println(getDocument());
		// createXml();
		//parseXml("D:/students.xml");
		
		/*List<Student> list = new ArrayList<Student>();
		list.add(new Student("07152225","1010","Lich","ShangHai","98"));
		list.add(new Student("07163336","1011","ZhaoLiu","Beijing","100"));
		createXMLByBean(list);*/
		
		List<Student> list = parseXmlToBean("D:/students.xml");
		System.out.println(list);
	}

}

class Student{
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
