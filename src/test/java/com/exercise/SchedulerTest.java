package com.exercise;

import static org.mockito.Mockito.times;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.exercise.service.SensorService;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerTest {
	
	@InjectMocks
	private Scheduler scheduler;
	
	@Mock
	private SensorService sensorService;
	
	@Test
	public void test_calculateMedianNoise() {
		scheduler.calculateMedianNoise();
		Mockito.verify(sensorService, times(1)).calculateAndAddMedianNoise(Mockito.any(Timestamp.class), Mockito.any(Timestamp.class));
	}
}
