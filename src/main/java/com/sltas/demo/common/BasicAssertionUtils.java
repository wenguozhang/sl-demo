package com.sltas.demo.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


public class BasicAssertionUtils {

	

	
	/**
	 * <p>
	 * Title: validNumber
	 * </p>
	 * <p>
	 * Description: 检测字段是否符合有效数字类型  >= 0
	 * </p>
	 * @param @param object
	 * @param @param result 
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void validNumber(@Nullable Object object, String msg) throws BasicBusinessException {
		if(object == null || !validNumber(object)) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>
	 * Title: validNumber
	 * </p>
	 * <p>
	 * Description: 检测字段是否符合有效数字类型  >= 0
	 * </p>
	 * @param @param object
	 * @param @param result 
	 * @return void
	 * @throws
	 */
	public static boolean validNumber(@Nullable Object object) {
		if(object instanceof Integer) {
			if(((Integer) object).intValue() >= 0) {
				return true;
			}
		}
		if(object instanceof Long) {
			if(((Long) object).longValue() >= 0) {
				return true;
			}
		}
		return false;
	}
	

	
	/**
	 * <p>
	 * Title: validGtNumber
	 * </p>
	 * <p>
	 * Description: 检测字段是否符合有效数字类型  > 0
	 * </p>
	 * @param @param object
	 * @param @param result 
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void validGtNumber(@Nullable Object object, String msg) throws BasicBusinessException {
		if(object == null || !validGtNumber(object)) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>
	 * Title: validGtNumber
	 * </p>
	 * <p>
	 * Description: 检测字段是否符合有效数字类型  > 0
	 * </p>
	 * @param @param object
	 * @param @param result 
	 * @return void
	 * @throws
	 */
	public static boolean validGtNumber(@Nullable Object object) {
		if(object instanceof Integer) {
			if(((Integer) object).intValue() > 0) {
				return true;
			}
		}
		if(object instanceof Long) {
			if(((Long) object).longValue() > 0) {
				return true;
			}
		}
		if(object instanceof BigDecimal ) {
			if(((BigDecimal) object).compareTo(BigDecimal.ZERO) > 0 ) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * <p>
	 * Title: hasMoney
	 * </p>
	 * <p>
	 * Description: 检测字段是否符合钱类型，保持金额后两位为正确
	 * </p>
	 * @param @param money
	 * @param @param result 
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void validMoney(@Nullable BigDecimal money) throws BasicBusinessException {
		validMoney(money,null);
	}
	
	/**
	 * <p>
	 * Title: hasMoney
	 * </p>
	 * <p>
	 * Description: 检测字段是否符合钱类型，保持金额后两位为正确
	 * </p>
	 * @param @param money
	 * @param @param result 
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void validMoney(@Nullable BigDecimal money, String msg) throws BasicBusinessException {
		if(money == null || !validMoneyTo2(money)) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>
	 * Title: hasMoney
	 * </p>
	 * <p>
	 * Description: 检测字段是否符合钱类型，保持金额后两位为正确
	 * </p>
	 * @param @param money
	 * @param @param result 
	 * @return void
	 * @throws
	 */
	private static boolean validMoneyTo2(@Nullable BigDecimal money) {
		if(money.setScale(2,RoundingMode.HALF_UP).compareTo(money) == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * <p>
	 * Title: notNull
	 * </p>
	 * <p>
	 * Description: 检测字段是否为空
	 * </p>
	 * @param @param object		检测对象是否为空
	 * @param @param result 	BasicEnum 结果信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void notNull(@Nullable Object object) throws BasicBusinessException {
		notNull(object,null);
	}
	
	/**
	 * <p>
	 * Title: notNull
	 * </p>
	 * <p>
	 * Description: 检测字段是否为空
	 * </p>
	 * @param @param object		检测对象是否为空
	 * @param @param result 	BasicEnum 结果信息
	 * @param @param msg 		BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void notNull(@Nullable Object object, String msg) throws BasicBusinessException {
		if(object == null|| org.apache.commons.lang3.StringUtils.isBlank((object.toString()))) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	
	public static void notNullToObject(@Nullable Object object,Object enumObject, String msg) throws BasicBusinessException {
		if(object == null|| org.apache.commons.lang3.StringUtils.isBlank((object.toString()))) {
			if(enumObject == null){
				throw new BasicBusinessException("999","999",msg);
			}else{
				throw new BasicBusinessException(enumObject);
			}
		}
	}
	
	
	/**
	 * <p>
	 * Title: hasLength
	 * </p>
	 * <p>
	 * Description: 检测字段是否为空，长度是否符合规范
	 * </p>
	 * @param @param text		检测字段是否为空串，是否出了空格外仍有有效参数
	 * @param @param size		检测字段长度是否符合标准
	 * @param @param result 	BasicEnum 结果信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void hasLength(@Nullable String text,int size) throws BasicBusinessException {
		hasLength(text,size,null);
	}
	
	/**
	 * <p>
	 * Title: hasLength
	 * </p>
	 * <p>
	 * Description: 检测字段是否为空，长度是否符合规范
	 * </p>
	 * @param @param text		检测字段是否为空串，是否出了空格外仍有有效参数
	 * @param @param size		检测字段长度是否符合标准
	 * @param @param result 	BasicEnum 结果信息
	 * @param @param msg 		BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void hasLength(@Nullable String text,int size,String msg) throws BasicBusinessException {
		if (!StringUtils.hasText(text) || text.length() > size) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>
	 * Title: hasLength
	 * </p>
	 * <p>
	 * Description: 检测字段是否为空，长度是否符合规范
	 * </p>
	 * @param @param text		检测字段是否为空串，是否出了空格外仍有有效参数
	 * @param @param size		检测字段长度是否符合标准
	 * @param @param result 	BasicEnum 结果信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void hasLength(@Nullable String text) throws BasicBusinessException {
		hasLength(text,null);
	}
	
	/**
	 * <p>
	 * Title: hasLength
	 * </p>
	 * <p>
	 * Description: 检测字段是否为空，长度是否符合规范
	 * </p>
	 * @param @param text		检测字段是否为空串，是否出了空格外仍有有效参数
	 * @param @param size		检测字段长度是否符合标准
	 * @param @param result 	BasicEnum 结果信息
	 * @param @param msg 		BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void hasLength(@Nullable String text,String msg) throws BasicBusinessException {
		if (!StringUtils.hasText(text)) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>
	 * Title: hasLength
	 * </p>
	 * <p>
	 * Description: 检测字段是否超过限定长度
	 * </p>
	 * @param @param text		检测字段是否超过限定长度
	 * @param @param size		检测字段是否超过限定长度
	 * @param @param result 	BasicEnum 结果信息
	 * @param @param msg 		BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void lengthCheck(@Nullable String text,int length,String msg) throws BasicBusinessException {
		if (text.length()>length) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	
	/**
	 * <p>
	 * Title: notEmpty
	 * </p>
	 * <p>
	 * Description: 检测map中是否存在该key值
	 * </p>
	 * @param @param map		检测map是否为空
	 * @param @param key		验证key信息的value是否存在
	 * @param @param result 	BasicEnum 结果信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void notEmpty(@Nullable Map<?, ?> map,Object key) throws BasicBusinessException {
		notEmpty(map,key,null);
	}
	
	/**
	 * <p>
	 * Title: notEmpty
	 * </p>
	 * <p>
	 * Description: 检测map中是否存在该key值
	 * </p>
	 * @param @param map		检测map是否为空
	 * @param @param key		验证key信息的value是否存在
	 * @param @param result 	BasicEnum 结果信息
	 * @param @param msg 		BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void notEmpty(@Nullable Map<?, ?> map,Object key,String msg) throws BasicBusinessException {
		if (CollectionUtils.isEmpty(map) || !map.containsKey(key)) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>
	 * Title: notEmpty
	 * </p>
	 * <p>
	 * Description: 检测 array中是否为空
	 * </p>
	 * @param @param array		检测 arr 是否为空
	 * @param @param result 	BasicEnum 结果信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void notEmpty(@Nullable Object[] array) throws BasicBusinessException {
		notEmpty(array,null);
	}
	
	/**
	 * <p>
	 * Title: notEmpty
	 * </p>
	 * <p>
	 * Description: 检测 array中是否为空
	 * </p>
	 * @param @param array		检测 arr 是否为空
	 * @param @param result 	BasicEnum 结果信息
	 * @param @param msg 		BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void notEmpty(@Nullable Object[] array,String msg) throws BasicBusinessException {
		if (ObjectUtils.isEmpty(array)) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>
	 * Title: notEmpty
	 * </p>
	 * <p>
	 * Description: 检测 collection中是否为空
	 * </p>
	 * @param @param collection		检测 collection 是否为空
	 * @param @param result 		BasicEnum 结果信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void notEmpty(@Nullable Collection<?> collection) throws BasicBusinessException {
		notEmpty(collection,null);
	}
	
	/**
	 * <p>
	 * Title: notEmpty
	 * </p>
	 * <p>
	 * Description: TODO(describe the file) 
	 * </p>
	 * @param @param collection		检测 collection 是否为空
	 * @param @param result 		BasicEnum 结果信息
	 * @param @param msg 			BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void notEmpty(@Nullable Collection<?> collection,String msg) throws BasicBusinessException {
		if (ObjectUtils.isEmpty(collection)) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>Title: notEmpty</p>
	 * <p>Description: 检测 collection 是否为空</p>
	 * @param collection
	 * @param msg
	 * @throws BasicBusinessException
	 */
	public static void empty(@Nullable Collection<?> collection, String msg) throws BasicBusinessException {
		if (!ObjectUtils.isEmpty(collection)) {
			throw new BasicBusinessException("999","999", msg);
		}
	}
	
	/**
	 * <p>
	 * Title: isTrue
	 * </p>
	 * <p>
	 * Description: 判定表达式是否为true
	 * </p>
	 * @param @param expression		判定表达式是否为true
	 * @param @param result 		BasicEnum 结果信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 * @author 李兆河 
	 * @date 2019年1月16日 下午5:06:20 
	 */
	public static void isTrue(@Nullable boolean expression) throws BasicBusinessException {
		isTrue(expression, null);
	}
	
	/**
	 * <p>
	 * Title: isTrue
	 * </p>
	 * <p>
	 * Description: 判定表达式是否为true
	 * </p>
	 * @param @param expression		判定表达式是否为true
	 * @param @param result 		BasicEnum 结果信息
	 * @param @param msg 			BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void isTrue(@Nullable boolean expression,String msg) throws BasicBusinessException {
		if (!expression) {
			throw new BasicBusinessException("999","999",msg);
		}
	}
	
	/**
	 * <p>
	 * Title: hasText
	 * </p>
	 * <p>
	 * Description: 用来字符串是否有效
	 * </p>
	 * @param @param text		字符串是否为空
	 * @param @param result 	BasicEnum 结果信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 */
	public static void hasText(@Nullable String text) throws BasicBusinessException {
		hasText(text, null); 
	}
	
	/**
	 * <p>
	 * Title: hasText
	 * </p>
	 * <p>
	 * Description: 用来字符串是否有效
	 * </p>
	 * @param @param text		字符串是否为空
	 * @param @param result 	BasicEnum 结果信息
	 * @param @param msg 		BasicEnum 信息中{0} 的额外填充信息
	 * @return void
	 * @throws BasicBusinessException 
	 * @throws
	 * @author 李兆河 
	 * @date 2019年2月20日 下午2:02:17 
	 */
	public static void hasText(@Nullable String text,String msg) throws BasicBusinessException {
		if (!StringUtils.hasText(text)) {
			throw new BasicBusinessException("999","999",msg);
		}
	}

	
	/**
	 * <p>
	 * Title: check
	 * </p>
	 * <p>
	 * Description: 验证表达式信息，检查是否执行内部验证方案
	 * </p>
	 * @param @param expression
	 * @param @param t
	 * @param @param consumer 
	 * @return void
	 * @throws
	 */
	public static <T> void check(@Nullable boolean expression,T t,Consumer<T> consumer) throws BasicBusinessException{
		if (expression) {
			consumer.accept(t);
		}
	}
}
