package com.nagarro.restassured.util;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utilities {
   public static XmlPath rawToXML(Response r) {
      String respon = r.asString();
      //System.out.println(respon);
      XmlPath x = new XmlPath(respon);
      return x;
   }

   public static JsonPath rawToJson(Response r) {
      String respon = r.asString();
      JsonPath x = new JsonPath(respon);
      return x;
   }
   

   public static String xmlToString(String path) throws IOException {
      return new String(Files.readAllBytes(Paths.get(path)));
   }
   
	   
   
}