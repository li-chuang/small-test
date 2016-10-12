package com.lichuang.Oct12;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pattern:匹配模式，可以定制，如匹配电话号码，匹配邮箱地址等。
 * Matcher:匹配器，依据匹配模式对字符串进行匹配检查。
 * @author ASUS
 *
 */
public class PatternUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(matchPhoneNumber("13545216097"));
	}
	
	/**
	 * 1.判断电话号码，并进行必要的隐藏处理
	 * @param str
	 * @return
	 */
	public static String matchPhoneNumber(String str){
		// compile()方法对正则表达式进行编译，获得匹配模式
		Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[34578]\\d{9}))(?!\\d)");
		// 生成与匹配模式对应的匹配器
		Matcher matcher = pattern.matcher(str);
		StringBuffer bf = new StringBuffer(64);
		// 匹配器查找，如果存在则进入下面的处理
		if(matcher.find()){
			// matcher.group()获取字符串中匹配的项，此例中为电话号码。
			str = str.replace(matcher.group(), matcher.group().substring(0,3)+"********");
		}
		return str;
	}

}

