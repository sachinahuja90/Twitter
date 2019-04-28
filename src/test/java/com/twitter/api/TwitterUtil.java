package com.twitter.api;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.nagarro.restassured.util.PropertyReader;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;


public class TwitterUtil{

	String id;

	int likeCount, retweetCount;
	ArrayList<String> hashTags;
	List<Biography> lst;
	
	JSONObject mainJSON;


    protected String baseUri;
    String curDir = System.getProperty("user.dir");
    
    Logger logger = Logger.getRootLogger();
    protected static RequestSpecification httpRequest = null;
    
    public static HashMap<String, String> propertyMap;

	
	
		
	static LinkedHashMap<String, Integer> convertMapToReverseSortedMap(HashMap<String,Integer> map) {
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		  
		 map.entrySet()
		     .stream()
		     .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		     .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		 
		 return sortedMap;
		
	}

	static HashMap<String, Integer> put(String str, HashMap<String, Integer> map) {
		if (str == null) {
			return map;
		}
		if (map.containsKey(str)) {
			map.put(str, map.get(str) + 1);

		} else
			map.put(str, 1);
		return map;
	}

	static JsonPath returnResponse(String resource) {
		RestAssured.baseURI = PropertyReader.propertyMap.get("TwitterHost");
		Response res=given().
						auth().
						oauth(PropertyReader.propertyMap.get("ConsumerKey"), PropertyReader.propertyMap.get("ConsumerSecret"), PropertyReader.propertyMap.get("Token"), PropertyReader.propertyMap.get("TokenSecret")).
						queryParam("count", "50").
						queryParam("screen_name", "stepin_forum").
					when().
						get(resource).
					then().
						extract().response();
				
		String response = res.asString();

		JsonPath js = new JsonPath(response);

		return js;
	}

	
}
	
