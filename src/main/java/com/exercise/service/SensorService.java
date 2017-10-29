package com.exercise.service;

import static com.exercise.generated.public_.tables.Measurement.MEASUREMENT;
import static com.exercise.generated.public_.tables.MedianNoise.MEDIAN_NOISE;
import static com.exercise.generated.public_.tables.Sensor.SENSOR;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.UtilMethods;
import com.exercise.generated.public_.tables.records.MeasurementRecord;
import com.exercise.generated.public_.tables.records.MedianNoiseRecord;
import com.exercise.generated.public_.tables.records.SensorRecord;
import com.exercise.mapper.MedianValueMapper;
import com.exercise.model.Measurement;
import com.exercise.model.Sensor;;

@Service
public class SensorService extends UtilMethods{

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
    	create.select()
		      .from(SENSOR)
		      .fetch()
		      .getValues(SENSOR.ID)
    		  .forEach(
    				  sensorID -> {
    					  List<BigDecimal> sensorsValues =
    							  create.select()
    				    	      	.from(MEASUREMENT)
    				    	      	.where(MEASUREMENT.TIMESTAMP.between(startTime).and(endTime))
    				    	      	.and(MEASUREMENT.ID.eq(sensorID))
    				    	      	.fetch().getValues(MEASUREMENT.VALUE);
    					  if(!sensorsValues.isEmpty()) {
    						  MedianNoiseRecord medianNoiseRecord = create.newRecord(MEDIAN_NOISE);
    						  medianNoiseRecord.setSensorId(sensorID);
    						  medianNoiseRecord.setValue(new BigDecimal(median(sensorsValues).toString()));
    						  medianNoiseRecord.setStartTimestamp(startTime);
    						  medianNoiseRecord.setEndTimestamp(endTime);
    						  medianNoiseRecord.store();
    					  }
    				  }
    		);
    }
    
    @Transactional
    public List<MedianValueMapper> getMedianValues(Long id, Timestamp start, Timestamp end) {
    	Result<Record3<Long, BigDecimal, Timestamp>> medianValues =
    			create.select(MEDIAN_NOISE.SENSOR_ID, MEDIAN_NOISE.VALUE, MEDIAN_NOISE.START_TIMESTAMP)
    				  .from(MEDIAN_NOISE)
    				  .where(MEDIAN_NOISE.START_TIMESTAMP.between(start).and(end))
    				  .and(MEDIAN_NOISE.SENSOR_ID.equal(id)).fetch();
    	return medianValues.stream()
    					   .map(record -> new MedianValueMapper (new BigDecimal(record.get("VALUE").toString()),
		                                   					     (Timestamp)(record.get("START_TIMESTAMP"))
		                                   				        )
    						    )
    					   .collect(Collectors.toList());
    }
}
