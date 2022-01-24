package com.sltas.demo.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @说明:String 工具类
 */
public class BasicStringUtils extends org.apache.commons.lang3.StringUtils{

	/**
	 * 下划线
	 */
	public static final String UNDER_LINE = "_";
    
	/**
	 * 中线
	 */
    public static final String CENTER_LINE = "-";
    
    /**
	 * 换行
	 */
	public static final String LINE_SEPARATOR_UNIX = "\r\n";
	
	/**
	 * 分隔符
	 */
	public static final String LINE_SEPARATOR_SPLITE = "\n";
	
	
	/**
     * 截取指定长度的字符串
     * @param str 原字符串
     * @param len 长度
     * @return 如果str为null，则返回null；如果str长度小于len，则返回str；如果str的长度大于len，则返回截取后的字符串
     */

    public static String subStrByStrAndLen(String str, int len) {
        return StringUtils.isNotBlank(str)? str.substring(0, str.length() > len ? len : str.length()) : "";
    }
    
    /**
     * @说明:  字符串编码转换
     * @param str
     * @return
     */
    public static String urlEncodeUTF8(String str){
  		if(StringUtils.isBlank(str)){
  			return "";
  		}
  		try {
  			str = URLEncoder.encode(str, "utf-8");
  		} catch (UnsupportedEncodingException e) {
  			throw new RuntimeException(e);
  		}
  		return str;
  	}  
    
    /**
     * @说明:  去除字符串中的空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest.replaceAll("[\\pZ]", "");
        }
        return dest;
    }
    
    /**
     * @说明  	将下划线字符转驼峰，首字母大写
 	 * @param 	要转换的字符串，格式："user_info"
 	 * @return	转换后的字符串，格式："UserInfo"
 	 */
     public static String toHumpUpperCase(String str){
         StringBuffer sb = new StringBuffer();
         sb.append(str);
         int count = sb.indexOf(UNDER_LINE);
         while(count!=0){
             int num = sb.indexOf(UNDER_LINE,count);
             count = num + 1;
             if(num != -1){
                 char ss = sb.charAt(count);
                 if(Character.isLowerCase(ss)) {
                 	ss = (char) (ss - 32);
                 }
                 sb.replace(count , count + 1,ss + "");
             }
         }
         String result = sb.toString().replaceAll(UNDER_LINE,"");
         return StringUtils.capitalize(result);
     }
     
    /**
     * @说明  	将下划线字符转驼峰，首字母小写
 	 * @param 	要转换的字符串，格式："user_info"
 	 * @return	转换后的字符串，格式："userInfo"
 	 */
     public static String toHumpLowerCase(String str){
         return lowerFirst(toHumpUpperCase(str));
     }
 	
    /**
 	 * @param 	要转换的字符串，格式："UserInfo"
 	 * @return	转换后的字符串，格式："userInfo"
 	 * @说明  	将字符串首字母小写
 	 * @throws	 
 	 */
 	public static String lowerFirst(String oldStr){

 		char[]chars = oldStr.toCharArray();

 		if(Character.isUpperCase(chars[0])) {
 			chars[0] += 32;
         }

 		return String.valueOf(chars);

 	}
 	
 	/**
 	 * @说明  	将字符串首字母大写
 	 * @param 	要转换的字符串，格式："userInfo"
 	 * @return	转换后的字符串，格式："UserInfo"
 	 */
 	public static String upperFirst(String oldStr){

 		char[]chars = oldStr.toCharArray();

 		if(Character.isLowerCase(chars[0])) {
 			chars[0] = (char) (chars[0] - 32);
         }

 		return String.valueOf(chars);

 	}
 	
 	/**
      * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
      *
      * <pre>
      * StringUtils.isNotBlank(null)      = false
      * StringUtils.isNotBlank("")        = false
      * StringUtils.isNotBlank(" ")       = false
      * StringUtils.isNotBlank("null")    = false
      * StringUtils.isNotBlank("NULL")    = false
      * StringUtils.isNotBlank("bob")     = true
      * StringUtils.isNotBlank("  bob  ") = true
      * </pre>
      *
      * @param cs  the CharSequence to check, may be null
      * @return {@code true} if the CharSequence is
      *  not empty and not null and not whitespace
      * @since 2.0
      * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
      */
     public static boolean isNotBlank(final Object obj) {
     	final CharSequence cs = String.valueOf(obj);
     	if(!isBlank(cs)) {
     		if("null".contentEquals(cs) || "NULL".contentEquals(cs)) {
     			return false;
     		}
     	}
         return !isBlank(cs);
     }

	/**
	 * Author: x1ao贱
	 * Description: 字符串拼接
	 * Date: 2021/10/20 17:20
	 */
	public static String concat(String... strArray){
     	StringBuffer sb = new StringBuffer();
     	for (int i = 0; i < strArray.length; i++) {
			if(isNotBlank(strArray[i])) {
				sb = sb.append(strArray[i]);
			}
     	}
     	return sb.toString();
	 }
}
