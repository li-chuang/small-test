package com.lichuang.Oct12;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(matchPhoneNumber("13545216097"));
	}
	
	public static String matchPhoneNumber(String str){
		Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[34578]\\d{9}))(?!\\d)");
		Matcher matcher = pattern.matcher(str);
		StringBuffer bf = new StringBuffer(64);
		if(matcher.find()){
			str = str.replace(matcher.group(), matcher.group().substring(0,3)+"********");
		}
		return str;
	}

}

