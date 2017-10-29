package com.exercise.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasToString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SensorResourceIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void should_return_preconfigured_sensors() {
        when().
                get("/sensors").
        then().
                statusCode(OK.value()).
                body("sensorId", hasItems("sensor-longstreet-45", "sensor-bertastreet-21", "sensor-werdstreet-2"));
    }

    @Test
    public void should_add_sensor() {
        // add sensor
        given().
                contentType(JSON).
                body("{ \"sensorId\": \"sensor-teststreet-1\" }").
        when().
                post("/sensors").

        then().
                statusCode(OK.value());

        // get sensors
        when().
                get("/sensors").
        then().
                statusCode(OK.value()).
                body("sensorId", hasItems("sensor-teststreet-1"));
    }
    
    @Test
    public void shoud_return_preconfigured_measurements() {
    	when().
    			get("/sensors/measurements").
    	then().
    			statusCode(OK.value()).
    			body("sensorId", hasItems(1, 2, 3)).
    			body("value", hasItems(30.1f, 27.6f, 55.4f, 32.7f, 47.2f, 34.4f)).
    			body("timestamp", hasItems(hasToString(Long.toString(Timestamp.valueOf("2017-09-17 18:45:00.000").getTime())),
    					                   hasToString(Long.toString(Timestamp.valueOf("2017-09-17 18:50:00.000").getTime()))
    					                   ));
    }

    @Test
    public void should_add_measurement() {
    	// add measurement
    	given().
    			contentType(JSON).
    			body("{ \"sensorId\": \"1\", \"value\": \"55.7\", \"timestamp\": \"2017-09-18\" }").
    	when().
    			post("/sensors/measurement").
    	then().
    			statusCode(OK.value());
    	
    	// get measurement
        when().
        		get("/sensors/measurements").
        then().
        		statusCode(OK.value()).
        		body("sensorId", hasItems(hasToString("1"))).
        		body("value", hasItems(hasToString("55.7"))).
        		body("timestamp", hasItems(hasToString(Long.toString(Timestamp.valueOf("2017-09-18 02:00:00.000").getTime()))));	
    }
    
    @Test
    public void should_return_preconfigured_get_sensor_median_values() {
    	given().
    		get("/sensors/medianvalue/1/2017-09-17 18:00:00.000/2017-09-17 19:59:59.999").
    	then().
    		statusCode(OK.value()).
    		body("value", hasItems(30.5f, 40.5f)).
    	    body("timestamp", hasItems(Timestamp.valueOf("2017-09-17 18:00:00.000").getTime(),
    	    		                   Timestamp.valueOf("2017-09-17 19:00:00.000").getTime()
    	    	                      )
    	    	);
    	    
    }
    
    
    
}
