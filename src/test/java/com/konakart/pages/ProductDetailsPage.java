package com.konakart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.konakart.testcases.BaseClass;

public class ProductDetailsPage extends BaseCommon {

	public ProductDetailsPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[@id='page-title']")
	public static WebElement pageTitle;

	@FindBy(xpath = "(//select[@id='prodQuantityId'])[2]")
	public static WebElement dropdown_PdtQuantity;

	@FindBy(xpath = "(//div/a[contains(text(),'Add to Cart')])[2]")
	public static WebElement button_AddToCart;

	@FindBy(xpath = "//span[contains(@class,'shopping-cart-title')]")
	public static WebElement basketCount;

	@FindBy(xpath = "(//div[@id='product-price']/span)[2]")
	public static WebElement pdt_Price;

	String count_ScreenShot = "//div[@id='gallery_nav']/a";

	public void isUserInDetailScreen() throws Throwable {
		try {
			isWebElementVisible(pageTitle);
			Assert.assertEquals(pageTitle.getText(), BaseClass.reference.get("addtocart_Item2"));
			Assert.assertEquals(4, driver.findElements(By.xpath(count_ScreenShot)).size());
		} catch (Throwable e) {
			e.printStackTrace();
			Report_log("Details screen validation FAILED ", "FAIL");
		}
	}

	public void addProductInToCart(int quantity) throws Throwable {

		try {
			dropdown_PdtQuantity.click();
			wait(1);
			Select select = new Select(dropdown_PdtQuantity);
			select.selectByVisibleText(String.valueOf(quantity));
			BaseClass.reference.put("beofre_CartCount", extractDigitFromString(basketCount.getText()));
			button_AddToCart.click();

			String value = pdt_Price.getText();
			Float price = Float.parseFloat(extractPriceFromString(value));
			Float pdtTotalPrice = getPdtTotalAmount(price, quantity);
			BaseClass.reference.put("second_PdtPrice", pdtTotalPrice);
			BaseClass.reference.put("second_PdtQuantity", quantity);

		} catch (Throwable e) {
			e.printStackTrace();
			Report_log("Product add to cart process FAILED", "FAIL");
		}

	}
}