package com.think.common.util;

import java.math.BigDecimal;

public final class MathUtil {

	/**
	 * 加法操作，如果参数包含null值，则抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	private static BigDecimal add(BigDecimal ...values)throws IllegalArgumentException{
		validateNullParams(values);
		BigDecimal sum = new BigDecimal(0);
		
		for (BigDecimal value : values) {
			sum = sum.add(value);
		}
		return sum;
	}
	/**
	 * 加法操作，如果参数包含null值，则抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal add(double ...values)throws IllegalArgumentException{
		BigDecimal sum = new BigDecimal(0);
		
		for (double value : values) {
			sum = sum.add(new BigDecimal(String.valueOf(value)));
		}
		return sum;
	}
	/**
	 * 加法操作，忽略null值
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	private static BigDecimal addIgnoreNull(BigDecimal... values) {
		BigDecimal sum = new BigDecimal(0);
		
		for (BigDecimal value : values) {
			if(null != value)	sum = sum.add(value);
		}
		return sum;
	}
	/**
	 * 加法操作
	 * @param ignoreNull 是否忽略null值  例如：true:忽略null值;false:参数为null，抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal add(boolean ignoreNull,BigDecimal ...values)throws IllegalArgumentException{
		if(!ignoreNull){
			return add(values);
		}
		return addIgnoreNull(values);
	}
	/**
	 * @param ignoreNull 是否忽略null值  例如：true:忽略null值;false:参数为null，抛异常
	 * @param precision 保留小数位数
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal addRoundDown(boolean ignoreNull,int precision,BigDecimal ...values)throws IllegalArgumentException{
		BigDecimal result = BigDecimal.ZERO;
		if(!ignoreNull){
			result = add(values);
		}else{
			result = addIgnoreNull(values);
		}
		result = result.setScale(precision,BigDecimal.ROUND_DOWN);
		return result;
	}
	/** 加法操作
	 * @param precision 保留小数位数
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal addRoundDown(int precision,double ...values)throws IllegalArgumentException{
		return add(precision,BigDecimal.ROUND_DOWN,values);
	}
	/**
	 *  加法操作
	 * @param ignoreNull ignoreNull 是否忽略null值  例如：true:忽略null值;false:参数为null，抛异常
	 * @param precision 保留小数位数
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal addRoundUp(boolean ignoreNull,int precision,BigDecimal ...values)throws IllegalArgumentException{
		BigDecimal result = BigDecimal.ZERO;
		if(!ignoreNull){
			result = add(values);
		}else{
			result = addIgnoreNull(values);
		}
		result = result.setScale(precision, BigDecimal.ROUND_UP);
		return result;
	}
	/**
	 * 加法操作
	 * @param precision 保留小数位数
	 * @param roundmode 进位模式
	 * @param values
	 * @return
	 */
	private static BigDecimal add(int precision, int roundmode,double... values) {
		BigDecimal result = add(values);
		result = result.setScale(precision, roundmode);
		return result;
	}
	/**
	 * 加法操作
	 * @param ignoreNull 是否忽略null值  例如：true:忽略null值;false:参数为null，抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal addRoundUp(int precision,double ...values)throws IllegalArgumentException{
		return add(precision,BigDecimal.ROUND_UP,values);
	}
	/**
	 * 加法操作
	 * @param ignoreNull 是否忽略null值  例如：true:忽略null值;false:参数为null，抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal addRound(int precision,double ...values)throws IllegalArgumentException{
		return add(precision,BigDecimal.ROUND_HALF_UP,values);
	}
	/**
	 *  加法操作
	 * @param ignoreNull ignoreNull 是否忽略null值  例如：true:忽略null值;false:参数为null，抛异常
	 * @param precision 保留小数位数
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal addRound(boolean ignoreNull,int precision,BigDecimal ...values)throws IllegalArgumentException{
		BigDecimal result = add(ignoreNull,values);
		result = result.setScale(precision, BigDecimal.ROUND_HALF_UP);
		return result;
	}
	/**
	 * 减法操作，如果参数包含null值，则抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal subtract(BigDecimal sum,BigDecimal ...values)throws IllegalArgumentException{
		validateNullParams(values);
		BigDecimal result = new BigDecimal(0);
		
		for (BigDecimal value : values) {
			result = sum.subtract(value);
		}
		return result;
	}
	/**
	 * 减法操作
	 * @param ignoreNull,是否忽略null值，如果为false，则有null值，会抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal subtract(boolean ignoreNull,BigDecimal sum,BigDecimal ...values)throws IllegalArgumentException{
		if(!ignoreNull){
			validateNullParams(values);
		}
		
		for (BigDecimal value : values) {
			if(value!=null){
				sum = sum.subtract(value);
			}
		}
		return sum;
	}
	/**
	 * 验证参数是否包含null值
	 * @param params
	 */
	private static void validateNullParams(BigDecimal ... params) {
		for (int i = 0; i < params.length; i++) {
			if(params[i] == null){
				throw new IllegalArgumentException("第["+(i+1)+"]个参数为null");
			}
		}
	}
}
