package com.konakart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class AdvanceSearchPage extends BaseCommon {

	public AdvanceSearchPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[contains(text(),'Advanced Search')]")
	public static WebElement header_AdvanceSearch;

	@FindBy(xpath = "//select[@name='categoryId']")
	public static WebElement dropdown_Categories;

	@FindBy(xpath = "//input[@id='priceFromStr']")
	public static WebElement text_PriceFrom;

	@FindBy(xpath = "//input[@id='priceToStr']")
	public static WebElement text_PriceTo;

	@FindBy(xpath = "//span[contains(text(),'Search')]")
	public static WebElement button_Search;

	@FindBy(xpath = "//div[@id='breadcrumbs']")
	public static WebElement label_Breadcrumb;

	String categoryName = "//option[contains(text(),'dynamic')]";

	public void isUserRedirectsToAdvanceSearchPage() throws Throwable {

		try {
			Assert.assertTrue(isWebElementVisible(header_AdvanceSearch));
			Assert.assertEquals("Home Advanced Search", label_Breadcrumb.getText());
		} catch (Throwable e) {

			e.printStackTrace();
			Report_log("Advance Search validation FAILED", "FAIL");
		}
	}

	public void selectCategory(String category) throws Throwable {

		dropdown_Categories.click();
		Select select = new Select(dropdown_Categories);
		select.selectByVisibleText(category);

	}

	public void enterPriceFrom(String price) {

		text_PriceFrom.sendKeys(price);

	}

	public void enterPriceTo(String price) {

		text_PriceTo.sendKeys(price);

	}

	public void clickSearchButton() {

		button_Search.click();

	}

}
