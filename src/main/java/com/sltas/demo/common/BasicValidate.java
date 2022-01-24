package com.sltas.demo.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.commons.nullanalysis.NotNull;

/**
 * @说明:正则验证
 */
public class BasicValidate {

	/**
	 * @说明: 手机号正则校验(1开头的11位数字)
	 * @param
	 * @return
	 */
	public static void mobileValidate(String mobile,String msg) throws BasicBusinessException{
		if(BasicStringUtils.isNotBlank(mobile)){
			BasicAssertionUtils.isTrue(Pattern.matches(REGEX_MOBILE,mobile), msg+"格式不正确");
		}
	}
	
	/**
	 * @说明: 邮箱正则校验
	 * @param
	 * @return
	 */
	public static void emailValidate(String email,String msg) throws BasicBusinessException{
		if(BasicStringUtils.isNotBlank(email)){
			BasicAssertionUtils.isTrue(Pattern.matches(REGEX_EMAIL,email), msg+"格式不正确");
		}
	}
	
	/**
	 * @说明: 证件号验证  cardType{@link BasicCommonConstant}}
	 * @param
	 * @return
	 */
	public static boolean isCard(@NotNull String cardType,@NotNull String cardValue){
		 if (StringUtils.isBlank(cardType) || StringUtils.isBlank(cardValue)) {
	            return false;
	        }
			if("1".equals(cardType)){
				return isIDCard(cardValue) ;
			}else{
				return isOtherCard(cardValue);
			}
    }
	
	
	/**
	 * @说明: 中文校验(字符串中不能包含中文)
	 * @param
	 * @return
	 */
	public static void chineseValidate(String param,String msg) throws BasicBusinessException{
		if(BasicStringUtils.isNotBlank(param)){
			BasicAssertionUtils.isTrue(Pattern.matches(REGEX_CARD,param), msg+"不能包含中文");
		}
	}
	
	/**
	 * @说明: 金额校验(最多两位小数)
	 * @param
	 * @return
	 */
	public static void moneyValidate(String param,String msg) throws BasicBusinessException{
		if(BasicStringUtils.isNotBlank(param)){
			BasicAssertionUtils.isTrue(Pattern.matches(MONEY,param), msg+"格式不正确");
		}
	}
	
	/**
	 * @说明: 金额校验(最多四位小数)
	 * @param
	 * @return
	 */
	public static void moneyFourDecimalPlacesValidate(String param,String msg) throws BasicBusinessException{
		if(BasicStringUtils.isNotBlank(param)){
			BasicAssertionUtils.isTrue(Pattern.matches(FOUR_DECIMAL_MONEY,param), msg+"格式不正确");
		}
	}
	
	/**
	 * @说明: 排序码校验校验数字且不能超过11位
	 * @param
	 * @return
	 */
	public static void sortCodeValidate(String param,String msg) throws BasicBusinessException{
		if(BasicStringUtils.isNotBlank(param)){
			BasicAssertionUtils.isTrue(Pattern.matches(NUMBER_VALIDATE,param), msg+"格式不正确");
			BasicAssertionUtils.lengthCheck(param,11, msg+"不能超过"+11+"位");
		}
	}
	
	/**
	 * @说明: 排序码校验
	 * @param
	 * @return
	 */
	public static void sortCodeLengthValidate(String param,String msg,Integer length) throws BasicBusinessException{
		if(BasicStringUtils.isNotBlank(param)){
			BasicAssertionUtils.isTrue(Pattern.matches(NUMBER_VALIDATE,param), msg+"格式不正确");
			BasicAssertionUtils.lengthCheck(param, length, msg+"不能超过"+length+"位");
		}
	}
	
	/**
	 * @说明: 日期格式校验（一般用作excel导入三方对接等）
	 * @param
	 * @return
	 */
	public static void dateFormatValidate(String param,String format,String msg) throws BasicBusinessException{
		if(BasicStringUtils.isNotBlank(param)){
			try {
				BasicDateUtils.parse(param,format);
			} catch (Exception e) {
				throw new BasicBusinessException("999","999",msg+"格式不正确");
			}
			//日期本身校验不严格需要根据长度判断
			//BasicAssertionUtil.isTrue(param.length()==format.length(),msg+"格式不正确");
		}
	}
	
	/**
	 * @说明: 拼接字符串校验(id-名称)
	 * @param
	 * @return
	 */
	public static String[] spliceInfoValidate(String param,String msg){
		try {
			String[] spliceInfoArr = param.split(BasicStringUtils.CENTER_LINE);
			Integer.parseInt(spliceInfoArr[0]);
			String info = BasicStringUtils.substringAfter(param,spliceInfoArr[0]+BasicStringUtils.CENTER_LINE);
			String[] returnInfoArr = {spliceInfoArr[0],info};
			return returnInfoArr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * isIDNumber:
	 *      假设18位身份证号码:41000119910101123X  410001 19910101 123X
		    ^开头
		    [1-9] 第一位1-9中的一个      4
		    \\d{5} 五位数字           10001（前六位省市县地区）
		    (18|19|20)                19（现阶段可能取值范围18xx-20xx年）
		    \\d{2}                    91（年份）
		    ((0[1-9])|(10|11|12))     01（月份）
		    (([0-2][1-9])|10|20|30|31)01（日期）
		    \\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
		    [0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
		    $结尾
		
		          假设15位身份证号码:410001910101123  410001 910101 123
		    ^开头
		    [1-9] 第一位1-9中的一个      4
		    \\d{5} 五位数字           10001（前六位省市县地区）
		    \\d{2}                    91（年份）
		    ((0[1-9])|(10|11|12))     01（月份）
		    (([0-2][1-9])|10|20|30|31)01（日期）
		    \\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
		    $结尾
	 * 
	 * @author 大河 
	 * Date:2019年12月31日上午10:31:53 
	 * @param IDNumber
	 * @return 
	 * @since JDK 1.8
	 */

	public static boolean isIDCard(String IDNumber) {
       
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        boolean matches = IDNumber.matches(REGEX_ID_CARD);

        //判断第18位校验值
        if (matches) {
            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() + 
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    return false;
                }
            }
        }
        return matches;
    }
	
	/**
	 * 
	 * isOtherCard:其他证件
	 * 
	 * @author 大河 
	 * Date:2020年3月9日上午11:24:25 
	 * @param cardValue
	 * @return 
	 * @since JDK 1.8
	 */
	public static boolean isOtherCard(@NotNull String cardValue){
		
        boolean matches = isContainChinese(cardValue) && cardValue.length()<21;
        
		return matches;
		
	}
	
	
    /**
     * 字符串是否包含中文
     * @param str 待校验字符串
     * @return false 包含中文字符 true 不包含中文字符
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile(REGEX_CARD);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return false;
        }
        return true;
    }
    
	/**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	
	/**
	 * 
	 * 手机正则
	 */
	public static final String REGEX_MOBILE = "^1[0-9]{10}$";
	
	
    /**
     * 不能包含中文的正則
     */
    public static final String REGEX_CARD = "[\u4E00-\u9FA5|\\！|\\，|\\。|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]" ;

    /**
     * 正则表达式  ：金额校验
     */
    public static final String MONEY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
    
    /**
     * 正则表达式  ：金额校验
     */
    public static final String FOUR_DECIMAL_MONEY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,4})?$";
    
    /**
     * 正则表达式  ：数字校验
     */
    public static final String  NUMBER_VALIDATE = "^[0-9]\\d*$";
    
    /**
     *  正则表达式  ：身份证校验
     */
    public static final String REGEX_ID_CARD ="(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
}
