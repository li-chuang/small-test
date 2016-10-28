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
		createJson();
	}
	
	public static void jsonToString(JSONObject json){
		
	}

	public static void stringToJson(String str){
		
	}
	
	public static void createJson(){
		JSONObject json = new JSONObject();
		json.put("name", "lichuang");
		json.put("age", "26");
		
		JSONArray array = new JSONArray();
		array.put("football");
		array.put("swimming");
		
		json.put("favorite", array);
		
		System.out.println(json.toString());
	}
}
