package com.exercise.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exercise.model.Measurement;
import com.exercise.model.Sensor;
import com.exercise.service.SensorService;

@RequestMapping("/sensors")
@Controller
public class SensorResource {

	@Autowired
	private SensorService sensorService;

	@RequestMapping
	ResponseEntity getSensors() {
		Collection<Sensor> sensors = sensorService.getSensors();
		return ResponseEntity.ok(sensors);
	}

	@PostMapping
	ResponseEntity addSensor(@RequestBody Sensor sensor) {
		sensorService.addSensor(sensor);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping("/measurements")
	ResponseEntity getMeasurement() {
		Collection<Measurement> measurements = sensorService.getMeasurements();
		return ResponseEntity.ok(measurements);
	}
	
	@PostMapping("/measurement")
	ResponseEntity addMeasurement(@RequestBody Measurement measurement) {
		sensorService.addMeasurement(measurement);
		return ResponseEntity.ok().build();
	}
}
