package com.konakart.pages;

import org.testng.Assert;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.konakart.testcases.BaseClass;

public class HomePage extends BaseCommon {

	public HomePage(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	Actions action = new Actions(driver);
	String before_CartCount = "";
	String after_CartCount = "";

	@FindBy(xpath = "//img[@alt='KonaKart Logo']")
	public static WebElement img_Logo;

	@FindBy(xpath = "(//a[@href='AdvancedSearch.action'])[2]")
	public static WebElement advancedSearch;

	@FindBy(xpath = "//span[contains(text(),'Shopping Cart')]")
	public static WebElement shoppingCart;

	@FindBy(xpath = "//span[contains(@class,'shopping-cart-title')]")
	public static WebElement basketCount;

	@FindBy(xpath = "//span[contains(text(),'Checkout')]")
	public static WebElement button_Checkout;

	@FindBy(xpath = "//tr[3]/td[contains(@class,'cost-overview-amounts ')]")
	public static WebElement total_Price;

	public void checkHomePageVisible() {
		Assert.assertEquals("KonaKart", driver.getTitle());
		Assert.assertTrue(isWebElementVisible(img_Logo));

	}

	public void clickAdvanceSearch() throws Throwable {
		Assert.assertTrue(isWebElementVisible(advancedSearch));
		advancedSearch.click();
		wait(2);
	}

	public void checkItemInCart() throws Throwable {

		try {

			action.moveToElement(shoppingCart).perform();
			wait(2);
			after_CartCount = extractDigitFromString(basketCount.getText());
			before_CartCount = BaseClass.reference.get("beofre_CartCount").toString();
			Assert.assertNotEquals(0, Integer.parseInt(after_CartCount));
		} catch (Throwable e) {
			e.printStackTrace();
			Report_log("Product not in the cart", "FAIL");
		}

	}

	public void checkoutTheProducts() throws Throwable {
		float subTotal = 0;
		try {
			shoppingCart.click();
			wait(1);

			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("addtocart_Item1").toString())));
			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("addtocart_Item2").toString())));

			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("second_PdtPrice").toString())));
			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("first_PdtPrice").toString())));

			subTotal = (float) BaseClass.reference.get("second_PdtPrice")
					+ (float) BaseClass.reference.get("first_PdtPrice");
			subTotal = (float) (Math.round(subTotal * 100.0) / 100.0);

			wait(2);
			Assert.assertTrue(verifyValueInTheScreen(String.valueOf(subTotal)));
			BaseClass.reference.put("subTotal", subTotal);

			wait(1);

			button_Checkout.click();
			wait(2);

		} catch (Throwable e) {
			e.printStackTrace();
			Report_log("Product details missing in the cart", "FAIL");

		}

	}

}
