package com.lichuang.Sep20;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 格式化
 * @author ASUS
 *
 */
public class NumberFormatTest {

	public static void main(String[] args){
		System.out.println(standardDecimalFormat(12345.6789,",###.000%"));
	}
	
	// NumberFormat类的使用，实际用途与SimpleDateFormat相似，但也有不同。
	public static void numberFormat(int i){
        // 得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(true);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(4);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(1);
        // 输出测试语句
        System.out.println(nf.format(i));
	}
	
	// String也有自己格式化的方法，它的方法与C语言中的语法类似
	public static void stringFormat(int youNumber){
	    // 0 代表前面补充0   
	    // 4 代表长度为4   
	    // d 代表参数为正数型   
	    String str = String.format("%04d", youNumber);   
	    System.out.println(str); // 0001   
	}
	
	//流水号加1后返回，流水号长度为4
    private static final String STR_FORMAT = "0000"; 
 
    public static String decimalFormat(String liuShuiHao){
        Integer intHao = Integer.parseInt(liuShuiHao);
        intHao++;
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(intHao);
    }
    
    public static String standardDecimalFormat(double number,String pattern){
    	NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
    	DecimalFormat df = (DecimalFormat) nf;
    	df.applyPattern(pattern);
    	return df.format(number);
    }
	
}


