/*author sandeepgarg
 *
 */
package com.twitter.api;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.nagarro.restassured.util.PropertyReader;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;


class TestCasesBase {
	 
    private PropertyReader pr;
    protected String baseUri;
    String curDir = System.getProperty("user.dir");
    Logger logger = Logger.getRootLogger();
    protected static RequestSpecification httpRequest;
    public static HashMap<String, String> propertyMap;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    ExtentTest test;
    
    @BeforeSuite
	void BeforeSuite() throws Exception {
		try {
			pr = new PropertyReader();
			propertyMap=pr.getPropertyMap();
			logger.setLevel(Level.ERROR);
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/testReport.html");
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        htmlReporter.config().setChartVisibilityOnOpen(true);
	        htmlReporter.config().setDocumentTitle("Extent Report Demo");
	        htmlReporter.config().setReportName("Test Report");
	        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
			
		}
		catch (FileNotFoundException e) {
			logger.error("FileNotFoundException : ", e);
		}
	}
    
	@BeforeMethod
	void initializeRestClient(Method method) throws Exception {
		test = extent.createTest(method.getName());
		RestAssured.baseURI = propertyMap.get("TwitterHost");		
		httpRequest = RestAssured.given();
		test.log(Status.INFO, "Base URL : "+propertyMap.get("TwitterHost")+" intialized");
		httpRequest.header("Content-Type", "application/json");
	}
	
	
	@AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
        }
        else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
    }
	
	 @AfterSuite
	    public void tearDown() {
	    	//to write or update test information to reporter
	        extent.flush();
	    }
	
   
} 


