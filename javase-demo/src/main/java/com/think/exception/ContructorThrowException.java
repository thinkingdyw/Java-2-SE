package com.think.exception;

import org.apache.log4j.Logger;

/**
 * 在实例化对象时，如果参数错误，则抛出异常，实例化对象失败
 * @author diaoyouwei
 *
 */
public class ContructorThrowException {

	private static Logger log = Logger.getLogger(ContructorThrowException.class);
	public ContructorThrowException(String arg) {
		validateArgument(arg);
	}

	private void validateArgument(String arg) {
		if(arg == null || arg.length() == 0 || "".equals(arg.trim())){
			throw new IllegalArgumentException("参数：arg 不可为空");
		}
	}
	
	public static void main(String[] args) {
		new ContructorThrowException(null);
		log.info("该输出语句不会打印输出.....");
	}
}
