package com.konakart.testcases;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.build.Plugin.Factory.UsingReflection.Priority;

public class TestOne extends SeleniumAbstractTest{

	
	WebDriver driver;
	
	@BeforeTest
	
	public void setup() throws InterruptedException
	{
		
		WebDriverManager.chromedriver().setup();
		
		driver=new ChromeDriver();
		
		driver.manage().window().maximize();
		
	}
	
	
	@Test
	
	public void performAction() throws InterruptedException
	{
		
		driver.get("https://www.browserstack.com/guide/handling-tabs-in-selenium");
		
		Actions actions=new Actions(driver);
		
		
		
		
		Thread.sleep(5000);
		
		driver.switchTo().newWindow(WindowType.TAB);
		
		driver.get("https://www.browserstack.com/guide/handling-tabs-in-selenium");
		
		Thread.sleep(5000);
		
		String currentWindow=driver.getWindowHandle();
		
		Set<String> allWindows=driver.getWindowHandles();
		
		System.out.println(currentWindow);
		
		System.out.println(allWindows);
		
		
		for(String name:allWindows)
		{
			if(!name.equalsIgnoreCase(currentWindow))
			{
				driver.close();
			}
		}
		
		Thread.sleep(5000);
		
	}
		
		
		@AfterTest
		
		public void closig()
		{
			driver.quit();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
}
