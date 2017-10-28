package com.exercise;

import java.math.BigDecimal;
import java.util.List;

public class UtilMethods {

	protected BigDecimal median(List<BigDecimal> values) {
		BigDecimal median; 
		values.sort((v1 ,v2) -> v1.compareTo(v2));
		if(values.size()/2==0) {
			median = ((values.get(values.size()/2)).add(values.get(values.size()/2 -1))).divide(new BigDecimal(2));
		}else {
			median = values.get(values.size()/2);
		}
		
		return median;
	}
}
