package com.sltas.demo.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @作者 曹媛媛  
 * @日期 2020年12月18日 上午10:21:42 
 * @说明:金额 工具类
 */
public class BasicMoneyUtils{
	
	/**
	 * @说明:   金额相乘保留两位小数(四舍五入)
	 * @param  priceOne 金额  
	 *         priceTwo 金额
	 * @return BigDecimal
	 */
	public static BigDecimal multiply(BigDecimal moneyOne,BigDecimal moneyTwo){
		return moneyOne.multiply(moneyTwo).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * @说明:   金额相处保留两位小数(相除四舍五入保留两位小数,除不尽四舍五入)
	 * @param  priceOne 金额  
	 *         priceTwo 金额
	 * @return BigDecimal
	 */
	public static BigDecimal divide(BigDecimal moneyOne,BigDecimal moneyTwo){
		return  moneyOne.divide(moneyTwo,2,RoundingMode.HALF_UP);
	}
	
	/**
	 * @说明:   金额相加
	 * @param  priceOne 金额  
	 *         priceTwo 金额
	 * @return BigDecimal
	 */
	public static BigDecimal add(BigDecimal moneyOne,BigDecimal moneyTwo){
		return moneyOne.add(moneyTwo);
	}
	
	/**
	 * @说明:   金额相减
	 * @param  priceOne 金额  
	 *         priceTwo 金额
	 * @return BigDecimal
	 */
	public static BigDecimal sub(BigDecimal moneyOne,BigDecimal moneyTwo){
		return moneyOne.subtract(moneyTwo);
	}
	
	
	/**
	 * @说明:   金额相乘保留四位小数(四舍五入)
	 * @param  priceOne 金额  
	 *         priceTwo 金额
	 * @return BigDecimal
	 */
	public static BigDecimal multiplyFourPlaces(BigDecimal moneyOne,BigDecimal moneyTwo){
		return moneyOne.multiply(moneyTwo).setScale(4, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * @说明:   金额相除保留四位小数(相除四舍五入保留四位小数,除不尽四舍五入)
	 * @param  priceOne 金额  
	 *         priceTwo 金额
	 * @return BigDecimal
	 */
	public static BigDecimal divideRoundFourPlaces(BigDecimal moneyOne,BigDecimal moneyTwo){
		return  moneyOne.divide(moneyTwo,4,RoundingMode.HALF_UP);
	}
	
	
    /**
     * 待补充 除不尽报异常的	
     */
	
	/**
	 * @说明:   金额千分位
	 * @param  priceOne 金额  
	 *         priceTwo 金额
	 * @return BigDecimal
	 */
	public static String moneyFormatThousands(BigDecimal money){
		return formatBigDecimal(money, "###,###,###,###,###.##");
	}
	
	
	public static String moneyFormatYuan(BigDecimal money){
		return "￥"+formatBigDecimal(money, "###,###,###,###,###.##");
	}
	
	private static String formatBigDecimal(BigDecimal money,String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(money);
	}
	
	/**
	 * @说明:   金额转大写   
	 * @param  value 金额
	 * @return String
	 */
	public static String priceToCapital(BigDecimal value) {
		String neg = value.compareTo(BigDecimal.ZERO) == -1 ? "负" : "";
		value = value.abs();
		if (value.compareTo(BigDecimal.ZERO) == 0) {
			return "零圆整";
		}
		char[] hunit = { '拾', '佰', '仟' };// 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = multiply(value, new BigDecimal("100")).longValue(); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串
		String head = "";
		String rail = "";
		if (valStr.length() > 2) {
			head = valStr.substring(0, valStr.length() - 2); // 取整数部分
			rail = valStr.substring(valStr.length() - 2); // 取小数部分
		} else if (valStr.length() == 2) {
			head = "";
			rail = valStr;
		} else {
			head = "";
			rail = "0" + valStr;
		}
		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果

		if (valStr.length() > 17) {
			return "数值过大！";// 解决问题1,超过千亿的问题。
		}

		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0' && idx != 0) { // 标志 ,连续零，仅读一次零，
					zero = digit[0]; // 解决问题2,当一个零位于第0位时，不输出“零”，仅输出“段名”.
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}

			// 取到该位对应数组第几位。
			int position = chDig[i] - '0';
			if (position == 1 && i == 0 && idx == 1)// 解决问题3 ,即处理10读"拾",而不读"壹拾"

			{
			} else {
				prefix += digit[position]; // 转化该数字表示
			}

			if (idx > 0) // 段内位置表示的值
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) { // 段名表示的值
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return neg + prefix + suffix; // 返回正确表示
	}

}
