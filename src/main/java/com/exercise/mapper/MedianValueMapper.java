package com.exercise.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MedianValueMapper {
	
	private BigDecimal value;
	private Timestamp timestamp;
	
	public MedianValueMapper() {
		
	}
	
	public MedianValueMapper(BigDecimal value, Timestamp timestamp) {
		this.value = value;
		this.timestamp = timestamp;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
