package com.twitter.api;

import static io.restassured.RestAssured.given;

import com.nagarro.restassured.util.PropertyReader;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class sample {
	
	static JsonPath returnResponse(String resource) {
		RestAssured.baseURI = "TwitterHostURL";
		Response res=given().
						auth().
						oauth("ConsumerKey","ConsumerSecret","Token","TokenSecret").
						param("count", "50").
						header("screen_name", "sachinahuja90").
						body("Request Body").
					when().
						post(resource).
					then().
						extract().response();
				
		String response = res.asString();

		JsonPath js = new JsonPath(response);

		return js;
	}

}
