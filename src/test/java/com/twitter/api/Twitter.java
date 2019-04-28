package com.twitter.api;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Twitter extends TestCasesBase{

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

	
	
    
	
	@Test
	public void Test_HighestRetweetCount() {
		JsonPath js = TwitterUtil.returnResponse("/statuses/user_timeline.json");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 48; i++) {
			list.add((Integer) js.get("[" + i + "].retweet_count"));
		}
		Collections.sort(list);
		retweetCount = list.get(list.size() - 1);
		assertTrue(retweetCount != 0);
	}

	@Test(dependsOnMethods={"Test_Top_10_Hashtag", "Test_HighestRetweetCount","Test_HighestLikeCount","Test_Top_3_Followers"})
	public void Test_JSONSample() {
		mainJSON = new JSONObject();
		mainJSON.put("top_retweet_count", retweetCount);
		mainJSON.put("top_like_count", likeCount);
		JSONArray hashtagArray = new JSONArray();
		for (String hashTag : hashTags) {
			hashtagArray.put(hashTag);
		}
		mainJSON.put("top_10_hashtags", hashtagArray);
		JSONArray bioGraphyArray = new JSONArray();
		for (Biography bio : lst) {
			JSONObject main = new JSONObject();
			main.put("name", bio.name);
			main.put("screen_name", bio.screen_name);
			main.put("followers_count", bio.followers_count);
			main.put("friends_count", bio.friends_count);
			bioGraphyArray.put(main);
		}
		mainJSON.put("biography", bioGraphyArray);
		assertTrue(mainJSON!=null);
	}

	@Test
	public void Test_Top_10_Hashtag() {
		hashTags = new ArrayList<String>();
		JsonPath jsonPath = TwitterUtil.returnResponse("/statuses/user_timeline.json");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		 for(int i=0;i<50;i++) {
			 for(int j=0;j<10;j++) {
				String str=jsonPath.get("["+i+"].entities.hashtags["+j+"].text");
				if(str!=null) 
					 map=TwitterUtil.put(str, map);
				else 
					 break;
			 }
		 }
		  
		 LinkedHashMap<String, Integer> sortedMap = TwitterUtil.convertMapToReverseSortedMap(map);
		 
		 
		 int j = 0; 
		  Set set = sortedMap.keySet();
		  for(Object str:set) {
			  j++;
			  hashTags.add((String)str);
			  if (j == 10) 
				  break;
		  }
		
		 assertTrue(hashTags.size()!=0);

	}
	

	@Test(priority = 2)
	public void Test_HighestLikeCount() {

		JsonPath js = TwitterUtil.returnResponse("/statuses/user_timeline.json");

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 50; i++) {
			list.add((Integer) js.get("[" + i + "].favorite_count"));
		}

		Collections.sort(list);
		likeCount = list.get(list.size() - 1);

		assertTrue(likeCount != 0);
	}

	@Test
	public void Test_Top_3_Followers() {
		JsonPath js = TwitterUtil.returnResponse("/followers/list.json");
		lst=new ArrayList<Biography>();
		for (int i = 0; i < 3; i++) {
			Biography bio=new Biography();
			bio.name=js.get("users[" + i + "].name");
			bio.screen_name=js.get("users[" + i + "].screen_name");
			bio.followers_count=js.get("users[" + i + "].followers_count");
			bio.friends_count=js.get("users[" + i + "].friends_count");
			lst.add(bio);
		}
		assertTrue(lst!=null);


	}
	
	@Test(dependsOnMethods={"Test_JSONSample"})
	
	public void Test_PostRequest() {
		RestAssured.baseURI = "https://cgi-lib.berkeley.edu";
		Response res = given().body(mainJSON).when().post("/ex/fup.html").then().extract().response();
		assertTrue(res.statusCode()==200);

	}

}
	
