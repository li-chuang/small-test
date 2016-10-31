package com.lichuang.Oct31;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Gson的使用 
 * 1.用String类型的json数据生成Json对象； 
 * 2.用Object实体数据生成对应的Json对象；
 * 3.用String类型的json数据生成Object对象；
 * 4.用Json对象生成生成对应的Object对象；
 * 5.用Object对象生成String类型的json数据；
 * 6.用Json对象生成对应的String类型的json数据；
 * 
 */
public class GsonUtils {

	public static void main(String[] args) {
		//3 stringToObject("{\"age\":\"100\",\"name\":\"lich\",\"favorite\":\"football\"}");
		//5 objectToString(new Person("lich", "100", new String[] { "football","swimming" }));
	}

	// 1.将String类型的信息转换为Json对象
	public static void stringToJson(String str) {

	}

	// 2.用Object实体数据生成对应的Json对象
	public static void objectToJson(Object obj) {
		Gson gson = new Gson();
		
	}

	// 3.用String类型的json数据生成Object对象
	public static void stringToObject(String str) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> map = gson.fromJson(str, type);//这里的Type很麻烦，类型复杂了解析不了
        for (String key : map.keySet()) {
            System.out.println("map.get = " + map.get(key));
        }
	}

	// 4.用Json对象生成生成对应的Object对象
	public static void jsonToObject(Gson json) {

	}

	// 5.用Object对象生成String类型的json数据
	public static void objectToString(Object obj) {
		Person person = (Person) obj;
		Gson gson = new Gson();
		String gsonStr = gson.toJson(person);// 注意，toJson的参数Object范围极广，集合类完全没有问题
		System.out.println(gsonStr);
	}

	// 6.用Json对象生成对应的String类型的json数据
	public static void jsonToString(Gson json) {

	}
}

// 实体类
class Person {
	private String name;
	private String age;
	private String[] favorite;

	public Person() {

	}

	public Person(String name, String age, String[] favorite) {
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
