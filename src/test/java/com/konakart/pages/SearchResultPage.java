package com.konakart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.konakart.testcases.BaseClass;

public class SearchResultPage extends BaseCommon {

	public SearchResultPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	BaseClass baseClass = new BaseClass();

	WebDriverWait wait = new WebDriverWait(driver, 120);
	Actions actions = new Actions(driver);

	@FindBy(xpath = "//div[@id='breadcrumbs']")
	public static WebElement label_Breadcrumbs;

	@FindBy(xpath = "//div[@class='item']")
	public static WebElement count_SearchResult;

	@FindBy(xpath = "//span[contains(@class,'shopping-cart-title')]")
	public static WebElement basketCount;

	String ietmCount = "//div[@class='items']/ul/li";

	String selectItem = "(//div[@class='item'])[dynamic]";

	String button_AddToCart = "(//a[contains(text(),'Add to Cart')])[dynamic]";

	String icon_Instock = "//a[contains(text(),'dynamic')]/ancestor::div[1]/div[1]/div";

	int count = 0;

	String itemName = "(.//*[@class='item-title'])[dynamic]";

	String itemPrice = "(.//*[@class='item-title'])[]/following::div[@class='price']";

	String pdtName = "";

	Float pdtPrice, pdtTotalPrice;

	public void checkUserInSearchResultsPage() throws Throwable {

		try {
		wait.until(ExpectedConditions.visibilityOf(count_SearchResult));
		Assert.assertEquals(label_Breadcrumbs.getText(), "Home Search Results");
		Assert.assertNotEquals(0, driver.findElements(By.xpath("//div[@class='item']")).size());

	}catch (Throwable e) {
		e.printStackTrace();
		Report_log("SearchResult page validation FAILED", "FAIL");
	}
	}
	

	public void selectItemToCart(String item) throws Throwable {

		if (item.contains("last")) {

			button_AddToCart = button_AddToCart.replaceAll("dynamic", String.valueOf(getSearchResultCount(ietmCount)));
			itemName = itemName.replaceAll("dynamic", String.valueOf(getSearchResultCount(ietmCount)));
			String value = driver
					.findElement(By.xpath("(.//*[@class='item-title'])["
							+ String.valueOf(getSearchResultCount(ietmCount) + "]/following::div[@class='price']")))
					.getText();
			pdtPrice = (Float) Float.parseFloat(extractPriceFromString(value));
			pdtTotalPrice = getPdtTotalAmount(pdtPrice, 1);

		} else if (item.contains("first")) {
			button_AddToCart = button_AddToCart.replaceAll("dynamic", "1");
			itemName = itemName.replaceAll("dynamic", "1");
			String value = driver
					.findElement(By.xpath(".//*[@class='item-title'])["
							+ String.valueOf(getSearchResultCount(ietmCount) + "]/following::div[@class='price']")))
					.getText();
			pdtPrice = (Float) Float.parseFloat(extractPriceFromString(value));
			pdtTotalPrice = getPdtTotalAmount(pdtPrice, 1);
		}

		isElementVisible(button_AddToCart);
		BaseClass.reference.put("beofre_CartCount", extractDigitFromString(basketCount.getText()));
		driver.findElement(By.xpath(button_AddToCart)).click();
		BaseClass.reference.put("addtocart_Item1", driver.findElement(By.xpath(itemName)).getText());
		BaseClass.reference.put("first_PdtPrice", pdtTotalPrice);
		BaseClass.reference.put("first_PdtQuantity", 1);

		wait(2);

	}

	public void checkIetmInStock(String item) throws Throwable {

		try {

			if (item.contains("last")) {
				selectItem = selectItem.replaceAll("dynamic", String.valueOf(getSearchResultCount(ietmCount)));

				itemName = itemName.replaceAll("dynamic", String.valueOf(getSearchResultCount(ietmCount)));

			} else if (item.contains("first")) {
				selectItem = selectItem.replaceAll("dynamic", "1");

				itemName = itemName.replaceAll("dynamic", "1");
			}

			WebElement element = driver.findElement(By.xpath(selectItem));
			actions.moveToElement(element).perform();
			wait(2);

			pdtName = driver.findElement(By.xpath(itemName)).getText();

			icon_Instock = icon_Instock.replaceAll("dynamic", pdtName);
			Assert.assertTrue(isElementVisible(icon_Instock));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
			Report_log("Item out of stock", "FAIL");
		}

	}

	public int getSearchResultCount(String xpath) {
		int count = driver.findElements(By.xpath(xpath)).size();
		return count;

	}

	public void selectItemsDetailsSCreen(String item) throws Throwable {

		if (item.contains("last")) {

			itemName = "(.//*[@class='item-title'])[" + String.valueOf(getSearchResultCount(ietmCount)) + "]";
		} else if (item.contains("first")) {
			itemName = "(.//*[@class='item-title'])[1]";

		}

		pdtName = driver.findElement(By.xpath(itemName)).getText();

		BaseClass.reference.put("addtocart_Item2", pdtName);
		driver.findElement(By.xpath(itemName)).click();
		wait(2);
	}

}
