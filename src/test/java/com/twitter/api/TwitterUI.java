package com.twitter.api;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TwitterUI {

	
	WebDriver driver;
	@Test(priority=1)
	public void tc001_GetHighestRetweet() 
	{
		
		System.setProperty("webdriver.chrome.driver", "D:\\Projects\\Twitter\\src\\test\\resource\\chromedriver.exe");
		 
		// Initialize browser
		driver=new ChromeDriver();
		 
		// Open facebook
		driver.get("https://twitter.com/");
		 
		// Maximize browser
		 
		driver.manage().window().maximize();
		
		driver.get("https://twitter.com/stepin_forum");
		
		List<WebElement> tweetList = driver.findElements(By.xpath("//ol[@id='stream-items-id'][1]/li"));
		List<Integer> retweetCountList=new ArrayList<Integer>();
		List<Integer> likeCountList=new ArrayList<Integer>();
		HashMap<String,Integer> map = new HashMap<String, Integer>(); 
		List hashTags = new ArrayList<String>();
		for (int i=0; i<tweetList.size();i++){
			retweetCountList.add(Integer.parseInt(tweetList.get(i).findElement(By.xpath("//div[@class='ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt']/descendant::span[@class='ProfileTweet-actionCount'][1]")).getText()));
			likeCountList.add(Integer.parseInt(tweetList.get(i).findElement(By.xpath("//div[@class='ProfileTweet-action ProfileTweet-action--favorite js-toggleState']/descendant::span[@class='ProfileTweet-actionCount'][1]")).getText()));
			List<WebElement> hashTagList = tweetList.get(i).findElements(By.xpath("//a[@data-query-source='hashtag_click']"));
			for(WebElement we:hashTagList) {
				String str=we.findElement(By.tagName("b")).getText();
				 map=TwitterUtil.put(str, map);
				
			}
		}
		
		LinkedHashMap<String, Integer> sortedMap = TwitterUtil.convertMapToReverseSortedMap(map);
		 
		 
		 int j = 0; 
		  Set<String> set = sortedMap.keySet();
		  for(Object str:set) {
			  j++;
			  hashTags.add((String)str);
			  if (j == 10) 
				  break;
		  }
		
		 assertTrue(hashTags.size()!=0);

		
	}
}
