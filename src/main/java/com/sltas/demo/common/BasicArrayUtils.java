package com.sltas.demo.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @说明:array 工具类
 */
public class BasicArrayUtils extends org.apache.commons.lang3.ArrayUtils{
	
	private static Logger logger = LoggerFactory.getLogger(BasicArrayUtils.class);
	
	/**
	 * @Title: BasicArrayUtils.java
	 * @Description: 切割List集合
	 * @param <T>
	 * @param source
	 * @param sizeWeight
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, double sizeWeight) {
		double totalSize = source.size();
        double ceil = Math.ceil(totalSize / sizeWeight);
        int size = (int) ceil; // 切分点

        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % size;  //(先计算出余数)
        int number = source.size() / size;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < size; i++) {
            List<T> value;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
	}
	
	
	/**
	 * list<Object>转化到另一个List<Object>
	 * tList--源list
	 * cls2--目标Object
	 * return -- 目标的List
	 */
	public static <T> List<T> listToList(List<?> tList, Class<T> cls2) {
		List<T> list = new ArrayList<T>();
		try {
			for(Object t  : tList){
				T t2 = cls2.newInstance();
				BeanUtils.copyProperties(t, t2);
				list.add(t2);
			}
		}catch(Exception e) {
			logger.error("list转换异常---"+e.getMessage(),e);
		}
        return list;
    }
	
}
