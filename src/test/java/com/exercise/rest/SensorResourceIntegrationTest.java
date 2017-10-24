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
    public void should_add_new_sensor() {
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
    public void should_add_Measurement() {
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
    
}
