package com.think.common.utils;

import java.math.BigDecimal;

public final class MathUtil {

	/**
	 * 加法操作，如果参数包含null值，则抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal add(BigDecimal ...values)throws IllegalArgumentException{
		validateNullParams(values);
		BigDecimal sum = new BigDecimal(0);
		
		for (BigDecimal value : values) {
			sum = sum.add(value);
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
			validateNullParams(values);
		}
		BigDecimal sum = new BigDecimal(0);
		
		for (BigDecimal value : values) {
			if(null != value){
				sum = sum.add(value);
			}
		}
		return sum;
	}
	/**
	 * 减法操作，如果参数包含null值，则抛异常
	 * @param values
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigDecimal subtract(BigDecimal ...values)throws IllegalArgumentException{
		validateNullParams(values);
		BigDecimal sum = new BigDecimal(0);
		
		for (BigDecimal value : values) {
			sum = sum.subtract(value);
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
	
	public static void main(String[] args) {
		System.out.println(add(null,null));
	}
}
