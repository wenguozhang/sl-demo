package com.sltas.demo.common;

import java.util.Calendar;
import java.util.Date;

/**
 * @说明:日期基本工具类
 */
public class BasicDateUtils extends cn.hutool.core.date.DateUtil{
	
	/**
	 * {@link BasicUtilsTestDemo}使用案例
	 */

	/**
	 * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
	 */
	public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 标准日期格式：yyyy-MM-dd
	 */
	public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 标准日期格式：yyyy年MM月dd日 HH时mm分ss秒
	 */
	public static final String CHINESE_DATE_TIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";

	/**
	 * 标准日期格式：yyyy年MM月dd日
	 */
	public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

	/**
	 * 标准日期格式：yyyyMMdd
	 */
	public static final String PURE_DATE_PATTERN = "yyyyMMdd";

	/**
	 * 标准日期格式：yyyyMMddHHmmss
	 */
	public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";
	
	/**
     * 为应用系统传递的参数查询条件的开始时间追加上  00:00:00
     */
    public final static String START_TIME_ADD = " 00:00:00";
    
    /**
     * 为应用系统传递的参数查询条件的结束时间追加上  23:59:59
     */
    public final static String END_TIME_ADD = " 23:59:59";
    
    /**
     * @说明:比较两个日期时间的大小(dateOne>=dateTwo返回true否则返回false)
     * @param dateOne
     * @param dateTwo
     * @return boolean
     */
    public boolean compareDate(Date dateOne, Date dateTwo) {  
        Calendar calendarOne = Calendar.getInstance();  
        Calendar calendarTwo = Calendar.getInstance();  
        calendarOne.setTime(dateOne);  
        calendarTwo.setTime(dateTwo);  
      
        int result = calendarOne.compareTo(calendarTwo);  
        if(result >= 0){
            return true; 	
        }else {
            return false;  
        } 
    }  
    
    
	/**
	 * @说明取得指定单位之后的日期
	 * @param date
	 * @param timeUnit: Calendar.SECOND(秒)Calendar. DAY_OF_YEAR(天) Calendar.MONTH(月)Calendar.YEAR(年){@link Calendar}
	 * @param times
	 * @return Date
	 */
	public static Date getDateAfterSeconds(Date date,int timeUnit,int times)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(timeUnit, times);
		return calendar.getTime();
	}
	
	
	/**
     * 获取某年第一天日期
     * 
     * @param year
     *            年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * 
     * @param year
     *            年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

}
