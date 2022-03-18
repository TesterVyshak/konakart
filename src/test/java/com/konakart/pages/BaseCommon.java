package com.konakart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.konakart.testcases.BaseClass;

public class BaseCommon extends BaseClass {
	@SuppressWarnings("deprecation")
	WebDriverWait wait = new WebDriverWait(driver, 120);

	

	public boolean isWebElementVisible(WebElement element) {

		try {

			wait.until(ExpectedConditions.visibilityOf(element));
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean isElementVisible(String xpath) {

		try {
			driver.findElement(By.xpath(xpath)).isDisplayed();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("deprecation")
	public boolean isElementClickable(WebElement element) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean isElementDisplayed(String xpath) {

		if (driver.findElements(By.xpath(xpath)).size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String extractDigitFromString(String text) {

		String convertedValue = text.replaceAll("[^0-9]", "");
		return convertedValue;
	}

	public String extractPriceFromString(String text) {

		String convertedValue = text.replace("$", "");
		return convertedValue;
	}

	public boolean verifyValueInTheScreen(String value) {

		if (driver.getPageSource().contains(value)) {
			return true;
		} else {
			return false;
		}
	}

	public Float getPdtTotalAmount(Float pdtPrice, int quantity) {

		return (float) (pdtPrice * quantity);

	}

	public void selectValueFromDropDown(WebElement e, String value) throws Throwable {
		e.click();
		wait(1);
		Select select = new Select(e);
		select.selectByVisibleText(value);

	}

}
