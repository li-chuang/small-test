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
import org.w3c.dom.NamedNodeMap;
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
	public static void parseXmlToTree(String path) throws Exception{
		//获得DocumentBuilder
		DocumentBuilder builder = getDocumentBuilder();
		//获得文档对象
		Document doc = builder.parse(path);
		//获得根元素 
		Element element = doc.getDocumentElement();
		//用方法遍历递归打印根元素下面所有的ElementNode(包括属性,TextNode非空的值),用空格分层次显示.  
        listAllChildNodes(element, 0);// 参数0表示设定根节点层次为0,它的前面不打印空格. 
	 		
	}
     // 递归遍历并打印所有的ElementNode(包括节点的属性和文本节点的有效内容),按一般的xml样式展示出来(空格来表示层次) 
    private static void listAllChildNodes(Node node, int level) {  
    	// 只处理ElementNode类型的节点,感觉这种类型的节点(还有有效的文本节点)才是真正有用的数据,其他注释节点,空白节点等都用不上.  
        if (node.getNodeType() == Node.ELEMENT_NODE) {  
            boolean hasTextChild = false;// 变量表示该节点的第一个子节点是否就是一个有有效内容的文本节点)  
            // Ⅰ❶【打印 - 空格】空格的长度 - level(n级ElementNode有n个长度的空格在前面)  
            String levelSpace = "";  
            for (int i = 0; i < level; i++) {  
                levelSpace += "    ";  
            }  
            // Ⅱ❷【打印 - 开始标签】先打印ElementNode的开始标签(有属性的话也要打印)  
            System.out.print(levelSpace + "<" + node.getNodeName()  
                    + (node.hasAttributes() ? " " : ">"));// 有属性的话节点的开始标签后面的尖括号">"就留待属性打印完再打印  
            // Ⅲ❸【打印 - 属性】遍历打印节点的所有属性  
            if (node.hasAttributes()) {  
                NamedNodeMap nnmap = node.getAttributes();  
                for (int i = 0; i < nnmap.getLength(); i++) {  
                    System.out.print(nnmap.item(i).getNodeName()  
                            + "=\""// 字符串里含双引号要用到转义字符\  
                            + nnmap.item(i).getNodeValue() + "\""  
                            + (i == (nnmap.getLength() - 1) ? "" : " "));// 不是最后一个属性的话属性之间要留空隙  
                }  
                System.out.print(">");// 开始标签里的属性全部打印完加上尖括号">"  
            }  
            // Ⅳ❹【打印 - 子节点】该ElementNode包含子节点时候的处理  
            if (node.hasChildNodes()) {  
                level++;// 有下一级子节点,层次加1,新的层次表示的是这个子节点的层次(递归调用时传给了它)  
                // 获得所有的子节点列表  
                NodeList nodelist = node.getChildNodes();  
                // 循环遍历取到所有的子节点  
                for (int i = 0; i < nodelist.getLength(); i++) {  
                    // Ⅳ❹❶【有效文本子节点】子节点为TextNode类型,并且包含的文本内容有效  
                    if (nodelist.item(i).getNodeType() == Node.TEXT_NODE  
                            && (!nodelist.item(i).getTextContent()  
                                    .matches("\\s+"))) {// 用正则选取内容包含非空格的有效字符的文本节点  
                        hasTextChild = true;// 该ElementNode的一级子节点是存在有效字符的文本节点  
                        System.out.print(nodelist.item(i).getTextContent());// 在开始标签后面添加文本内容  
                        // Ⅳ❹❷【ElementNode子节点】子节点是正常的ElementNode的处理  
                    } else if (nodelist.item(i).getNodeType() == Node.ELEMENT_NODE) {  
                        System.out.println();  
                        // 递归调用方法 - 以遍历该节点下面所有的子节点  
                        listAllChildNodes(nodelist.item(i), level);// level表示该节点处于第几个层次(相应空格)  
                    }  
                }  
                level--;// 遍历完所有的子节点,层次变量随子节点的层数,依次递减,回归到该节点本身的层次  
                // level++ 和 level--对于该节点的子节点影响的是子节点的初值  
            }  
            // Ⅴ❺【打印 - 结束标签】打印元素的结束标签.如果它的第一个一级子节点是有效文本的话,文本和结束标签添加到开始标签后面,  
            // 层次什么的就作废用不上了,否则,才按层次打印结束标签.  
            System.out.print(((hasTextChild) ? "" : "\n" + levelSpace) + "</"  
                    + node.getNodeName() + ">");  
        }  
    }  
 	
	//向一个XML文件中添加节点
	public static void addNodeToXml(Student student){
		
	}
	
	//向一个XML文件中删除节点
	public static void deleteNodeFromXml(){
		
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
		
		/*List<Student> list = parseXmlToBean("D:/students.xml");
		System.out.println(list);*/
		
		//parseXmlToTree("D:/students.xml");
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
