package com.lichuang.Oct29;

import org.json.JSONArray;
import org.json.JSONObject;

/**
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
	
	public static void jsonToString(JSONObject json){
		
	}

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
