package com.think.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public final class DateUtil {

	/**
	 * 取指定日期所在月的第一天
	 * @param date
	 * @return
	 */
	public static Date firstDay(Date date){
		validateParam(date);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	/**
	 * 取指定日期所在月的最后一天
	 * @param date
	 * @return
	 */
	public static Date lastDay(Date date){
		validateParam(date);
		Calendar cal = Calendar.getInstance();
		final int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	private static void validateParam(Date date) {
		if(null == date){
			throw new IllegalArgumentException("日期为空!");
		}
	}
	/**
	 * 指定日期的前一天
	 * @param date
	 * @return
	 */
	public static Date preDay(Date date){
		validateParam(date);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	/**
	 * 指定日期的后一天
	 * @param date
	 * @return
	 */
	public static Date nextDay(Date date){
		validateParam(date);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date,String pattern){
		validateParam(date);
		if(StringUtils.isBlank(pattern)){
			throw new IllegalArgumentException("日期格式化模式为空!");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		
		return dateFormat.format(date);
	}
	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException 
	 */
	public static Date parse(String date,String pattern) throws ParseException{
		validateParam(date);
		if(StringUtils.isBlank(pattern)){
			throw new IllegalArgumentException("日期格式化模式为空!");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		
		return dateFormat.parse(date);
	}
	private static void validateParam(String date) {
		if(StringUtils.isBlank(date)){
			throw new IllegalArgumentException("日期为空!");
		}
	}
}
