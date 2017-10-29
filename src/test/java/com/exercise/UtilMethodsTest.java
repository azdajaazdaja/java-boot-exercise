package com.exercise;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class UtilMethodsTest {

	@Test
	public void test_median_foOddEnteredList() {
		// when
		UtilMethods utilMethods = new UtilMethods();
		List<BigDecimal> values = Arrays.asList(new BigDecimal(10.5),
												new BigDecimal(20.5),
												new BigDecimal(10.5),
												new BigDecimal(30.8),
												new BigDecimal(40.9)
												);
		
		// do
		BigDecimal result = utilMethods.median(values);
		
		// then
		Assert.assertEquals("Expected median value is 20.5",  new BigDecimal(20.5), result);
	}
	
	@Test
	public void test_median_foEvenEnteredList() {
		// when
		UtilMethods utilMethods = new UtilMethods();
		List<BigDecimal> values = Arrays.asList(new BigDecimal(10.5),
												new BigDecimal(20.5),
												new BigDecimal(10.5),
												new BigDecimal(30.0),
												new BigDecimal(40.5),
												new BigDecimal(50.5)
												);
		
		// do
		BigDecimal result = utilMethods.median(values);
		
		// then
		Assert.assertEquals("Expected median value is 25.25",  new BigDecimal(25.25), result);
		
	}
}
