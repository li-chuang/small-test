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
		System.out.println(matchPhoneNumber("我的电话号码是：13545216097"));
	}
	
	/**
	 * 1.判断电话号码，并进行必要的隐藏处理
	 *   matches(),lookingAt(),find()
	 */
	public static String matchPhoneNumber(String str){
		// compile()方法对正则表达式进行编译，获得匹配模式
		Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[34578]\\d{9}))(?!\\d)");
		// 生成与匹配模式对应的匹配器
		Matcher matcher = pattern.matcher(str);
		StringBuffer bf = new StringBuffer(64);
		// 匹配器查找，如果存在则进入下面的处理
		// 除了find(),还有matches()对整个字符串进行匹配,lookingAt()只有匹配到的字符串在最前面才返回true
		if(matcher.find()){
			// matcher.group()获取字符串中匹配的项，此例中为电话号码。
			str = str.replace(matcher.group(), matcher.group().substring(0,3)+"********");
		}
		return str;
	}
	
	/**
	 * 2.精细化匹配
	 *   start()返回匹配到的子字符串在字符串中的索引位置,
	 *   end()返回匹配到的子字符串的最后一个字符在字符串中的索引位置,
	 *   group()返回匹配到的子字符串
	 *   str="我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com"
	 */
	public static void matchInfomation(String str){
		Pattern p=Pattern.compile("\\d+"); //匹配数字[0-9]
		Matcher m=p.matcher(str); 
		while(m.find()) { //注意：只有匹配成功了，才有所谓start，end以及匹配值，不然会报错。
		     System.out.println(m.group()); 
		     System.out.print("start:"+m.start()); 
		     System.out.println(" end:"+m.end()); 
		} 

 
	}
	
	/**
	 * 2.(?=x)y 零宽度正预测先行断言,它断言自身出现的位置的后面能匹配表达式exp.
	 * 比如\b\w+(?=ing\b),匹配以ing结尾的单词的前面部分(除了ing以外的部分),
	 * 如查找I'm singing while you're dancing.时,它会匹配sing和danc.
	 */
	
	/**
	 * 
	 */

}


