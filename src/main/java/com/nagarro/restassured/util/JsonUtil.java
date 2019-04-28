package com.nagarro.restassured.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
	public static JSONArray getJSONArray(String key, String value) throws Exception {
		JSONObject stopsParams;
		JSONArray stopArr=new JSONArray();
		String[] keyArr=key.split(",");
		String[] valueArr=value.split(",");
		for (int i=0; i < keyArr.length; i++)		
		{
			stopsParams = new JSONObject();
			stopsParams.put(keyArr[i], valueArr[i]);
			stopArr.put(stopsParams);
		}		
	    return stopArr;		
	}	
	
	public static JSONObject getJSONObject(String key,String value) throws Exception {			
		JSONObject stopsParams = new JSONObject();
		stopsParams.put(key,value);	
		return stopsParams;
    }

}
