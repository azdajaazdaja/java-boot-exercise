package com.exercise;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.exercise.service.SensorService;

@Component
public class Scheduler {
	
	@Autowired
	private SensorService sensorService;
	
	private final int TIME_PERIOD = 3600000;
	
	@Scheduled(fixedRate = TIME_PERIOD)
	public void calculateMedianNoise() {
		Timestamp endTimestamp = new Timestamp((new Date()).getTime());
		Timestamp startTimestamp = new Timestamp(endTimestamp.getTime() - TIME_PERIOD);
		sensorService.calculateAndAddMedianNoise(startTimestamp, endTimestamp);
	}
}
