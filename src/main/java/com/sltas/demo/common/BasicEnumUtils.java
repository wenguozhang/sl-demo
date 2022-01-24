package com.sltas.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class BasicEnumUtils {
	
	private static Logger logger = LoggerFactory.getLogger(BasicEnumUtils.class);
	
	/**
     * 获取枚举类中某个常量的名称 默认是两个长度的
     * @param enumClass 枚举类的class
     * @param paramClass 参数类型的class
     * @param code -- 枚举类的第一位表示唯一的
     * @param <E, T>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
	public static <T, E> String getValue(Class<E> enumClass, Class<T> paramClass,String code) {
		try {
			List<T> list = getValues(enumClass, paramClass);
			if(list.contains(code)) {
				return list.get(list.indexOf(code)+1).toString();
			}
		} catch (Exception e) {
			logger.error("获取枚举类的值异常-----"+e.getMessage(),e);
		}
		return "";
	}

	/**
     * 获取枚举类中某个常量的名称 
     * @param enumClass 枚举类的class
     * @param paramClass 参数类型的class
     * @param code -- 枚举类的第一位表示唯一的
     * @param size --  根据code获取第几位的值,从第一位开始计算
     * @param <E, T>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
	public static <T, E> String getValueSize(Class<E> enumClass, Class<T> paramClass,String code,int size) {
		try {
	        List<Field> fieldList = getField(enumClass, paramClass);
	        if(fieldList.size() < size) {
	        	return "";
	        }
	        E[] values = values(enumClass);
	        List<T> resultList = new ArrayList<>();
	        //因为枚举中字段的可能设置为private，要通过Field.get()方法获取Field的值需要开启忽略获取访问符限制
			for (E e : values) {
				for(Field field:fieldList) {
					field.setAccessible(true);
					T t = (T)field.get(e);
		            resultList.add(t);
				}
	        }
			if(resultList.contains(code)) {
				int index = -1;
				for (int i = 0; i < resultList.size(); i++) {
					if(BasicStringUtils.equals(String.valueOf(resultList.get(i)),code) &&  (i % fieldList.size()) == 0){
						index = i;
						break;
					}
				}
				if(index == -1){
					return "";
				}
				return resultList.get(index+(size-1)).toString();
			}
		} catch (Exception e) {
			logger.error("获取枚举类的值异常-----"+e.getMessage(),e);
		}
		return "";
	}
	
	
	
	/**
     * 获取枚举类中变量的列表
     * @param enumClass 枚举类的class
     * @param paramClass 参数类型的class
     * @param <E, T>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <E, T> List<T> getValues(Class<E> enumClass, Class<T> paramClass)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        E[] values = values(enumClass);
        List<T> resultList = new ArrayList<>();
        List<Field> fieldList = getField(enumClass, paramClass);
        //因为枚举中字段的可能设置为private，要通过Field.get()方法获取Field的值需要开启忽略获取访问符限制
		for (E e : values) {
			for(Field field:fieldList) {
				field.setAccessible(true);
				T t = (T)field.get(e);
	            resultList.add(t);
			}
        }
        return resultList;
    }
 
    /**
     * 通过反射调用枚举类的values获取所有枚举类
     * @param enumClass
     * @param <E>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static <E> E[] values(Class<E> enumClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method valuesMethod = enumClass.getMethod("values");
        Object valuesObj = valuesMethod.invoke(enumClass);
        E[] values = (E[]) valuesObj;
        return values;
    }
 
    /**
     * 通过field的类型获取field
     * 说明：
     *      因为枚举类中只有Enum类型的Field,所有可以通过指定Field的类型来获取Field
     * @param <E>
     * @param enumClass
     * @param paramClass
     * @param <E>
     * @param <T>
     * @param <T>
     * @return
     */
    @SuppressWarnings("rawtypes")
	private static <E, T> List<Field> getField(Class<E> enumClass, Class<T> paramClass) throws IllegalAccessException {
        //如果是包装类获取包装类的基本类型
        Class basicClass = getBasicClass(paramClass);
        //获取类型相同的Field(类型相同或与其基本类型相同)
        List<Field> fieldList = Arrays.stream(enumClass.getDeclaredFields())
                .filter(f -> f.getType() == paramClass || f.getType() == basicClass).collect(Collectors.toList());
        
		/*
		 * if (fieldList.size() != 1) { //抛出异常，只支持一个属性 throw new
		 * IllegalArgumentException(paramClass + "类型属性数量异常。"); }
		 */
        return fieldList;
    }
 
    /**
     * 获取class的基本类型的class
     * 说明：
     *      存在基本类型将返回其基本类型，否则返回null
     *      如传入Integer.class将返回int.class,传入String.class返回null
     * @param paramClass
     * @return
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
	private static Class getBasicClass(Class paramClass) throws IllegalAccessException {
        Field typeField = null;
        try {
            //尝试获取包装类的TYPE
            typeField = paramClass.getField("TYPE");
        } catch (NoSuchFieldException e) {
            return null;
        }
        //获取包装类TYPE成功，获取TYPE属性值（因为类型为static，所以传入null）
        return (Class) typeField.get(null);
    }
}
