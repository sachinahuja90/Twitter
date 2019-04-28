package com.nagarro.restassured.util;

import java.io.*;
import java.util.*;

public class PropertyReader {
	 
    String str, key;
    private static Properties prop;
    
    public static HashMap<String, String> propertyMap;

     
    private void loadProps(String propertyFile) throws IOException {
           File cfgfile = new File(propertyFile);
           if (cfgfile.exists()) {
                  FileInputStream propin = new FileInputStream(cfgfile);
                  prop.load(propin);
           }
    }
    
    
    
    
    
    public String readProperty(String propkey) {           
           return prop.getProperty(propkey);
    }
    
	public  HashMap<String, String> getPropertyMap() throws IOException {
		if(propertyMap==null) {
			prop=new Properties();  
			propertyMap = new HashMap<String, String>();
			String curDir = System.getProperty("user.dir");
 	   		loadProps(curDir + "\\src\\test\\resource\\config.properties");
           	Set<Object> keys = prop.keySet();
    	   	for(Object k:keys){
    	   		String key = (String)k;
    	   		propertyMap.put(key, (String)prop.getProperty(key));
    	   	}
		}
    	return propertyMap;
    }
    
	
} 



