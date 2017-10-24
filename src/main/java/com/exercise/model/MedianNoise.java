package com.exercise.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MedianNoise {

	private Long sensorId;
	private BigDecimal value;
	private Timestamp startTimestamp;
	private Timestamp endTimestamp;
	
	public MedianNoise() {}
	
	public MedianNoise(Long sensorId, BigDecimal value) {
		this.sensorId = sensorId;
		this.value = value;
	}
	
	public MedianNoise(Long sensorId, BigDecimal value, Timestamp startTimestamp, Timestamp endTimestamp) {
		this.sensorId = sensorId;
		this.value = value;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
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

	public Timestamp getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(Timestamp startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public Timestamp getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Timestamp endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
	
}
