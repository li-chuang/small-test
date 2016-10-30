package com.lichuang.Oct29;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * json包似乎功能太弱了，好多想法无法实现，所以想换一种功能更强大的jar包
 * Json的使用
 * 1.将字符串解析为Json对象
 * 2.将Json解析为字符串
 * 3.创建Json格式的数据
 *
 */
public class JsonUtils {
	
	public static void main(String[] args) {
		createJson(new Person("lich","100", new String[]{"football","swimming"}));
	}
	
	// 将Json对象以String的形式输出
	public static void jsonToString(JSONObject json){
		
	}

	// 将String类型的信息转换为Json对象
	public static void stringToJson(String str){
		
	}
	
	// 生成简单的Json对象
	public static void createJsonTest(){
		JSONObject json = new JSONObject();
		json.put("name", "lichuang");
		json.put("age", "26");
		
		JSONArray array = new JSONArray();
		array.put("football");
		array.put("swimming");
		
		json.put("favorite", array);
		
		System.out.println(json.toString());
	}
	
	// 将对象信息转为Json格式，
	public static void createJson(Person person){
		JSONObject json = new JSONObject();
		json.put("name", person.getName());
		json.put("age", person.getAge());
		
		JSONArray array = new JSONArray();
		for(String fav : person.getFavorite()){
			array.put(fav);
		}		
		json.put("favorite", array);		
		System.out.println(json.toString());
	}
	
	// 将任意对象信息转为Json格式，
	public static void createJson(Object obj){
		
	}
	
	// 将String格式的信息转换为实体对象
	public static void stringToBean(String str){
		//JSONObject()
	}
}

// 实体类
class Person{
	private String name;
	private String age;
	private String[] favorite;
	
	public Person(){
		
	}
	public Person(String name,String age,String[] favorite){
		this.name = name;
		this.age = age;
		this.favorite = favorite;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String[] getFavorite() {
		return favorite;
	}
	public void setFavorite(String[] favorite) {
		this.favorite = favorite;
	}
	
	
}
