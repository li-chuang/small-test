package com.lichuang.sep27;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类中是有关JSON解析的方法， JSON解析有现成的方式，这里将它们再解析一次，弄懂它们的原理。
 * 
 * @author ASUS
 * 
 */
public class JsonParse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseJson("{'singer':{'id':01,'name':'tom','gender':'男'}}");
	}

	// 普通JSON数据解析
	
	//分隔这个思路似乎不对，实际上不好分隔，还不如使用堆栈，遇到对应的则弹出
	public static Map parseJson(String strJson) {
		String result = "";
		Map jsonMap = new HashMap();
		if(strJson ==null || "".equals(strJson)){
			return jsonMap;
		}
		int startIndex =strJson.indexOf("{");
		int endIndex = strJson.lastIndexOf("}");
		if(startIndex >= 0 && endIndex >=0){
			result = strJson.substring(startIndex+1, endIndex);
		}
		System.out.println(result);
		
		String[] results = result.split(":");
		String key ="";
		if(results[0].startsWith("'")){
			key = results[0].substring(1, results[0].length()-1);
			System.out.println(key);
		}
		String value ;
		if(results[1].startsWith("[")){
			List list = new ArrayList();
			value = results[1].substring(1, results[0].length()-1);
			Collections.addAll(list, value.split(","));
			jsonMap.put(key, list);
		} else if(results[1].startsWith("'")){
			value = results[1].substring(1, results[0].length()-1);
			jsonMap.put(key, value);
		} else if (results[1].startsWith("{")){
			Map jsonSub =  parseJson(results[1]);
		} else {
			value = results[1];
			jsonMap.put(key, value);
		}
		return jsonMap;
	}
	
	// 这里是一种新的思路，用堆栈解决
	public static Map<String,Object> parseStrToJson(String jsonStr){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		
		return jsonMap;
	}
	
	// 自定义堆栈
	class MyStack{
		private int index = 0;
		private char[] data = new char[10];
		public void push(char c){
			data[index] = c;
		}
		public char pop(){
			index--;
			return data[index];			
		}
		public int getIndex(){
			return index;
		}
	}

}
