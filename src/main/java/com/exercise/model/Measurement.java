package com.exercise.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Measurement {

	private Long sensorId;
	private BigDecimal value;
	private Timestamp timestamp;
	
	public Measurement() {
		
	}
	
	public Measurement(Long sensorId, BigDecimal value, Timestamp timestamp) {
		this.sensorId = sensorId;
		this.value = value;
		this.timestamp = timestamp;
	}

	public Long getSensorId() {
		return sensorId;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
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
