package com.lichuang.Oct29;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Json的使用
 * 1.用String类型的json数据生成Json对象；
 * 2.用Object实体数据生成对应的Json对象；
 * 3.用String类型的json数据生成Object对象；
 * 4.用Json对象生成生成对应的Object对象；
 * 5.用Object对象生成String类型的json数据；
 * 6.用Json对象生成对应的String类型的json数据；
 *
 */
public class JsonUtils {
	
	public static void main(String[] args) {
		createJson(new Person("lich","100", new String[]{"football","swimming"}));
	}
	
	// 1.将String类型的信息转换为Json对象
	public static void stringToJson(String str){
		
	}
	
	// 2.用Object实体数据生成对应的Json对象
	public static void objectToJson(Object obj){
		
	}
	
	// 3.用String类型的json数据生成Object对象
	public static void stringToBean(String str){
		
	} 
	
	// 4.用Json对象生成生成对应的Object对象
	public static void jsonToObject(JSONObject json){
		
	}
	
	// 5.用Object对象生成String类型的json数据
	public static void objectToString(Object obj){
		
	}
	
	// 6.用Json对象生成对应的String类型的json数据
	public static void jsonToString(JSONObject json){
		
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
