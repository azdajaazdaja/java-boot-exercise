package com.exercise.service;

import static com.exercise.generated.public_.tables.Measurement.MEASUREMENT;
import static com.exercise.generated.public_.tables.MedianNoise.MEDIAN_NOISE;
import static com.exercise.generated.public_.tables.Sensor.SENSOR;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.generated.public_.tables.records.MeasurementRecord;
import com.exercise.generated.public_.tables.records.MedianNoiseRecord;
import com.exercise.generated.public_.tables.records.SensorRecord;
import com.exercise.model.Measurement;
import com.exercise.model.Sensor;;

@Service
public class SensorService {

    @Autowired
    private DSLContext create;

    public Collection<Sensor> getSensors() {
        return create.selectFrom(SENSOR).fetch(record -> new Sensor(record.getSensorPublicId()));
    }

    @Transactional
    public void addSensor(Sensor sensor) {
        SensorRecord sensorRecord = create.newRecord(SENSOR);
        sensorRecord.setSensorPublicId(sensor.getSensorId());
        sensorRecord.store();
    }
    
    public Collection<Measurement> getMeasurements(){
    	return create.selectFrom(MEASUREMENT).fetch(record -> new Measurement(record.getSensorId(), record.getValue(), record.getTimestamp()));
    }
    
    @Transactional
    public void addMeasurement(Measurement measurement) {
    	MeasurementRecord measurementRecord = create.newRecord(MEASUREMENT);
    	measurementRecord.setSensorId(measurement.getSensorId());
    	measurementRecord.setValue(measurement.getValue());
    	measurementRecord.setTimestamp(measurement.getTimestamp());
    	measurementRecord.store();
    }
    
    @Transactional
    public void calculateAndAddMedianNoise(Timestamp startTime, Timestamp endTime) {    	
    	Result<Record2<Long, BigDecimal>>  medianValues =
    	create.select(MEASUREMENT.SENSOR_ID,MEASUREMENT.VALUE.avg())
    	      .from(MEASUREMENT)
    	      .where(MEASUREMENT.VALUE.between(BigDecimal.valueOf(startTime.getTime())).and(BigDecimal.valueOf(endTime.getTime())))
    	      .groupBy(MEASUREMENT.SENSOR_ID).fetch();
    	medianValues.stream().forEach(record -> {
    		MedianNoiseRecord medianNoiseRecord = create.newRecord(MEDIAN_NOISE);
    		medianNoiseRecord.setSensorId(Long.parseLong(record.get("SENSOR_ID").toString()));
    		medianNoiseRecord.setValue(new BigDecimal(record.get("avg").toString()));
    		medianNoiseRecord.setStartTimestamp(startTime);
    		medianNoiseRecord.setEndTimestamp(endTime);
    		medianNoiseRecord.store();
    	});
    }
    
}
