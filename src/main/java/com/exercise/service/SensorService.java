package com.exercise.service;

import static com.exercise.generated.public_.tables.Measurement.MEASUREMENT;
import static com.exercise.generated.public_.tables.Sensor.SENSOR;

import java.util.Collection;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.generated.public_.tables.records.MeasurementRecord;
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
    

}
