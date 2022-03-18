package com.konakart.pages;

import org.testng.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.konakart.Util.Customer;
import com.konakart.testcases.BaseClass;

public class OrderConfirmation extends BaseCommon {

	public OrderConfirmation(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[contains(text(),'Order Confirmation')]")
	public static WebElement title_OrderConfirmation;

	@FindBy(xpath = "//h1[@id='page-title']")
	public static WebElement title_OrderStatus;

	@FindBy(xpath = "//div[@id='breadcrumbs']")
	public static WebElement label_Breadcrumb;

	@FindBy(xpath = "//span[contains(text(),'Confirm Order')]")
	public static WebElement button_ConfirmOrder;

	@FindBy(xpath = "//select[@id='shippingQuotes']")
	public static WebElement dropdown_ShippmentMethod;

	@FindBy(xpath = "//span[contains(text(),'Continue')]")
	public static WebElement button_Continue;

	@FindBy(xpath = "//h1[@id='page-title']")
	public static WebElement title_MyAccount;

	@FindBy(xpath = "//tr[@class='shopping-cart-total']/td[3]")
	public static WebElement total_Price;

	public boolean isHeaderVisible() {
		try {
			isWebElementVisible(title_OrderConfirmation);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void confirmOrder() throws Throwable {

		button_ConfirmOrder.click();
		wait(2);

		Assert.assertEquals("Home Checkout Success", label_Breadcrumb.getText());
		Assert.assertEquals("Your Order Has Been Processed", title_OrderStatus.getText());

	}

	public void checkDetailsInConfirmationScreen() throws Throwable {
		
		try {
		float totalPrice = 0;
		dropdown_ShippmentMethod.click();
		wait(2);
		Assert.assertTrue(verifyValueInTheScreen(Customer.getFirstName()));
		Assert.assertTrue(verifyValueInTheScreen(Customer.getStreetAddress()));

		Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("addtocart_Item1").toString())));
		Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("addtocart_Item2").toString())));

		Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("second_PdtPrice").toString())));
		Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("first_PdtPrice").toString())));
		Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("subTotal").toString())));

		totalPrice = Float.parseFloat(extractPriceFromString(total_Price.getText()));
		System.out.println(totalPrice);
		totalPrice = (float) (Math.round(totalPrice * 100.0) / 100.0);
		BaseClass.reference.put("totalPrice", totalPrice);

	}catch(Throwable e)
		{
		e.printStackTrace();
		Report_log("Product details missing in the Confirmation screen", "FAIL");
		}
	}
	

	public void redirectToMyAccount() {

		button_Continue.click();
		Assert.assertTrue(isWebElementVisible(title_MyAccount));

	}

}
