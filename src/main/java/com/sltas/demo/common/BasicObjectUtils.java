package com.sltas.demo.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

/**
 * @作者 曹媛媛  
 * @说明:Objet 工具类
 */
public class BasicObjectUtils extends  cn.hutool.core.util.ObjectUtil{

	 /**
	  * @说明: 字符串、集合、map、数组: 为空或者 empty
	  * @param
	  * @return
	  */
	public static boolean isNullOrEmpty(Object obj) {  
		if (obj == null)  
			return true;  
	  
		if (obj instanceof CharSequence)  
			return ((CharSequence) obj).length() == 0;  
	  
		if (obj instanceof Collection)  
			return ((Collection<?>) obj).isEmpty();  
	  
		if (obj instanceof Map)  
			return ((Map<?,?>) obj).isEmpty();  
	  
		if (obj instanceof Object[]) {  
			Object[] object = (Object[]) obj;  
			if (object.length == 0) {  
				return true;  
			}  
			boolean empty = true;  
			for (int i = 0; i < object.length; i++) {  
				if (!isNullOrEmpty(object[i])) {  
					empty = false;  
					break;  
				}  
			}  
			return empty;  
		}  
	          
		return false;  
	}  
	
	/**
	 * @Title: copyProperties
	 * @Description: 对象属性拷贝(将源对象的属性值拷贝到目标对象)
	 * @author liuqinglin
	 * @date 2021年12月2日
	 * @param @param source 源对象
	 * @param @param target 目标对象
	 */
	public static void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}

	/**
	 * 根据条件过滤数据
	 * @param filterField 参数过滤的字段
	 * @param queryVo 过滤字段的对象
	 * @param list list数据
	 * @param <T> 数据泛型
	 * @return List<T>
	 */
	public static <T> List<T> getFilterLis(String[] filterField, Object queryVo, List<T> list){
		try{
			for(int i=0; i<filterField.length; i++) {
				String str = filterField[i];
				Object fieldValue = getFieldValue(queryVo,str);
				if(!BasicObjectUtils.isNullOrEmpty(fieldValue)) {
					String fieldStr = String.valueOf(fieldValue);
					List<String> filterList = Arrays.asList(fieldStr.split(","));
					list = list.stream().filter(vo -> {
						Object voFieldValue = getFieldValue(vo,str);
						if(BasicObjectUtils.isNullOrEmpty(voFieldValue)){
							return false;
						} else {
							return  filterList.contains(voFieldValue);
						}

					}).collect(Collectors.toList());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取对象的属性值
	 * @param t   对象
	 * @param arrtibute  属性
	 * @return
	 */
	public static Object getFieldValue(Object t,String arrtibute){
		if(null == arrtibute){
			return "";
		}
		// 将属性的首字符大写，方便构造get，set方法;
		String fieldName = arrtibute.substring(0, 1).toUpperCase() + arrtibute.substring(1);
		try {
			Method m = t.getClass().getMethod("get" + fieldName);
			// 调用getter方法获取属性值
			return m.invoke(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 开始分页
	 * @param list
	 * @param pageNum 页码
	 * @param pageSize 每页多少条数据
	 * @return
	 */
	public static List startPage(List list, Integer pageNum,
								 Integer pageSize) {
		if (list.size() == 0) {
			return list;
		}

		Integer count = list.size(); // 记录总数
		Integer pageCount = 0; // 页数
		if (count % pageSize == 0) {
			pageCount = count / pageSize;
		} else {
			pageCount = count / pageSize + 1;
		}

		int fromIndex = 0; // 开始索引
		int toIndex = 0; // 结束索引

		if (pageNum > pageCount) {
			list.clear();
			return list;
		}

		if (!pageNum.equals(pageCount)) {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = fromIndex + pageSize;
		} else {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = count;
		}

		List pageList = list.subList(fromIndex, toIndex);

		return pageList;
	}

}
