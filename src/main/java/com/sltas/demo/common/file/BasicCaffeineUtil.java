package com.sltas.demo.common.file;

import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @作者 曹媛媛  
 * @日期 2021年6月2日 下午4:02:24 
 * @说明:缓存公共类
 */
public class BasicCaffeineUtil {

	
	public static volatile LoadingCache<String,String> testCache;
}
