package com.sltas.demo.common;

/**
 * 异常处理类
 * 
 */
public class BasicExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils{
	
	
	/**
	 * 
	 * throwException:传入枚举抛出对应枚举值异常信息
	 * 				     枚举需要规范  统一统一规范定义异常三要素
	 *                异常三要素{returnType returnCode returnMsg}
	 * 
	 * @author 大河 
	 * Date:2020年12月18日下午3:17:26 
	 * @param object
	 * @throws BasicBusinessException 
	 * @since JDK 1.8
	 */
	public static void throwException(Object object) throws BasicBusinessException{
		
		throw new BasicBusinessException(object);

	}

	
	
	
	
	
}