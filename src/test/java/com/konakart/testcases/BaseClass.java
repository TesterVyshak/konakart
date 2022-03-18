package com.konakart.testcases;

import java.io.File;
import java.io.FileInputStream;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {

	public static WebDriver driver;
	public static Properties prop;
	public static HashMap<String, Object> reference = new HashMap<String, Object>();
	
	public  ExtentHtmlReporter htmlReporter;
	public  ExtentTest test;
	public  ExtentReports extentReports;
	String imagePath="";

	public BaseClass() {
		try {

			prop = new Properties();
			String config = System.getProperty("user.dir") + ("\\src\\test\\resource\\Configuration.properties");
			FileInputStream fis = new FileInputStream(config);
			prop.load(fis);			
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	
	public WebDriver lauchBrowser() throws Throwable {

		String browser = (String) prop.get("browser");

		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.navigate().to((String) prop.get("url"));
			
		}else if(browser.equals("mozilla"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.navigate().to((String) prop.get("url"));
		}

		return driver;

	}
	
	public void startReport()
	{
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd_MM_yy_hh_mm");
		String reportPath="./Reports/konakart"+dateFormat.format(date)+".html";
		
		htmlReporter=new ExtentHtmlReporter(reportPath);
		
		//initialize extent report
		extentReports=new ExtentReports();
		extentReports.attachReporter(htmlReporter);
		extentReports.setSystemInfo("System", System.getProperty("os.name"));
		extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
		extentReports.setSystemInfo("Host Name", System.getenv("COMPUTERNAME"));
		
		htmlReporter.config().setDocumentTitle("KonaKart Automation");
		htmlReporter.config().setReportName("KonaKart Automation Report");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extentReports.attachReporter(htmlReporter);
		
		
		
		
	}
	
	
	public void Report_log(String stepName,String stepStatus) throws Throwable
	{
		try {
		String screenName=stepStatus.toUpperCase()+"_"+stepName;
		takeScreenShot(screenName);
		
		switch(stepStatus.toUpperCase().trim())
		{
		
		case "PASS":
		{
			test.log(Status.PASS, stepName,MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
			break;
		}
		
		case "FAIL":
		{
			test.log(Status.FAIL, stepName,MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
			break;
		}
		
		case "INFO":
		{
			test.log(Status.INFO, stepName,MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
			break;
		}
			
		}
	}catch(Exception e)
		{
		 e.printStackTrace();
		 test.log(Status.FAIL, stepName);
		}
	}
	

	
	
	public void takeScreenShot(String screenShotName) throws Throwable
	{
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("HHmmss");
		String screenShot=screenShotName+dateFormat.format(date);
		String targetFileName="./ScreenShots/"+screenShot+".png";
		File desFile=new File(targetFileName);
		imagePath=desFile.getAbsolutePath();
		
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(srcFile, desFile);
		
		
	}
	
	public static String getEMailAddress()
	{
		
		String email=RandomStringUtils.randomAlphabetic(5)+(String) prop.get("emailExtension");
		return email;
		
	}
	
	public static String getUserName()
	{
			
		return "UN_"+RandomStringUtils.randomAlphabetic(5);
		
	}
	
	

	public void endReport()
	{
		extentReports.flush();
	}
	
	public void wait(int seconds) throws InterruptedException
	{
		int value=seconds*1000;
		Thread.sleep(value);
	}
}
