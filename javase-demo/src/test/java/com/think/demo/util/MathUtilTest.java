package com.think.demo.util;

import java.math.BigDecimal;

import org.junit.Test;

import com.think.common.util.MathUtil;

public class MathUtilTest {

	@Test
	public void add(){
		System.out.println(MathUtil.addRound(2,123.12,123.840));
		System.out.println(BigDecimal.valueOf(12132.1253).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
}
