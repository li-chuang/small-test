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
		// System.out.println(matchPhoneNumber("我的电话号码是：13545216097"));
		// matchInfomation("我的QQ是:456456 ,我的电话是:0532214 ,我的邮箱是:aaa123@aaa.com");
		// replaceName("你好，我是Kevin，今天是我的生日！");
		// groupPhoneNumber("我家的座机号码是021-85853335");
		// lookAhead("I'm singing while you're dancing.");
		lookBehind("republic of China");
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
		if(matcher.find()){ // find()挨个寻找，像迭代器
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
	 * 3.替换
	 * replaceAll()/replaceFirst()全部替换、替换首个
	 * appendReplacement(StringBuffer sb, String replacement)将前面的和替换的部分都加入到sb对象中 
	 * appendTail(StringBuffer sb) 将匹配后的尾部加入到sb中。
	 * 
	 * str="你好，我是Kevin，今天是我的生日！"
	 */
	public static void replaceName(String str){
		Pattern pattern = Pattern.compile("Kevin");
		Matcher matcher= pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcher.find();
		int i=0;
		while(result){
			i++;
			matcher.appendReplacement(sb, "007"); //你好，我是007
			System.out.println("第"+i+"次匹配后sb的内容是："+sb); 
			result = matcher.find();
		}
		matcher.appendTail(sb); //你好，我是007，今天是我的生日！
		System.out.println("调用m.appendTail(sb)后sb的最终内容是:"+ sb.toString()); 
	}
	
	/**
	 * 4.分组
	 * group()返回整个匹配
	 * group(int group)返回确定的某个匹配
	 * groupCount()返回组个数
	 * group是指里用括号括起来的，能被后面的表达式调用的正则表达式，也就是组
	 * 如：A(B(C))D，
	 * 里面有三个group：group 0是ABCD， group 1是BC，group 2是C
	 * 
	 * str = "我家的座机号码是021-85853335"
	 */
	public static void groupPhoneNumber(String str){
		Pattern pattern = Pattern.compile("(\\d{3})-(\\d{8})");
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			System.out.println("该次查找获得匹配组的数量为："+matcher.groupCount()); // 2
			System.out.println("该次查找获得匹配组第一分组为："+matcher.group(1)); // 021 
			System.out.println("该次查找获得匹配组第二分组为："+matcher.group(2)); // 85853335
		}
	}
	
	
	/**
	 * 5.(?=x) 零宽度正预测先行断言,它断言自身出现的位置的前面能匹配表达式exp.
	 * 比如\b\w+(?=ing\b),匹配以ing结尾的单词的前面部分(除了ing以外的部分),
	 * 如查找I'm singing while you're dancing.时,它会匹配sing和danc.
	 * \b 单词边界
	 * \w 单词字符：[a-zA-Z_0-9] 
	 * 
	 * (?=X) ，\\d{3}(?=a) 连续三个数字的后面出现的字符是a的匹配
	 * (?!X) ，\\d{3}(?!a) 连续三个数字的后面出现的字符不是a的匹配
	 * (?<=X) 
	 * (?<!X)，
	 * 
	 * str = "I'm singing while you're dancing."
	 */
	public static void lookAhead(String str){
		Pattern pattern = Pattern.compile("\\b\\w+(?=ing\\b)");
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			System.out.println(matcher.group());
		}
	}
	
	/**
	 * str = "republic of China"
	 */
	public static void lookBehind(String str){
		Pattern pattern = Pattern.compile("(?<=re)\\w+\\b");
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			System.out.println(matcher.group());
		}
	}
	//"(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[34578]\\d{9}))(?!\\d)"
	
	/**
	 * 
	 */

}


