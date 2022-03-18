package com.konakart.pages;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.konakart.Util.Customer;
import com.konakart.testcases.BaseClass;

public class MyAccount extends BaseCommon {

	public MyAccount(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	CustomerDetails customerDetails = new CustomerDetails(driver);

	@FindBy(xpath = "//h1[@id='page-title']")
	public static WebElement title_MyAccount;

	@FindBy(xpath = "//div[contains(text(),'Pending')]")
	public static WebElement status_Pending;

	String pdt_Qunatity = "//tr/td/a[contains(text(),'dynamic')]/following::td[1]";
	String pdt_Price = "//tr/td/a[contains(text(),'dynamic')]/following::td[2]";

	public void verifyOrderDetails() throws Throwable {

		try {

			Assert.assertTrue(isWebElementVisible(status_Pending));

			Assert.assertTrue(verifyValueInTheScreen(Customer.getFirstName()));
			Assert.assertTrue(verifyValueInTheScreen(Customer.getPrimaryPhone()));
			Assert.assertTrue(verifyValueInTheScreen(Customer.getEMail()));
			Assert.assertTrue(verifyValueInTheScreen(Customer.getStreetAddress()));

			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("addtocart_Item2").toString())));
			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("addtocart_Item1").toString())));

			String name_LastItem = BaseClass.reference.get("addtocart_Item2").toString();
			Assert.assertEquals(Integer.parseInt(BaseClass.reference.get("second_PdtQuantity").toString()),
					Integer.parseInt(extractDigitFromString(driver
							.findElement(
									By.xpath("//tr/td/a[contains(text(),'" + name_LastItem + "')]/following::td[1]"))
							.getText())));

			String name_FirstItem = BaseClass.reference.get("addtocart_Item1").toString();
			Assert.assertEquals(Integer.parseInt(BaseClass.reference.get("first_PdtQuantity").toString()),
					Integer.parseInt(extractDigitFromString(driver
							.findElement(
									By.xpath("//tr/td/a[contains(text(),'" + name_FirstItem + "')]/following::td[1]"))
							.getText())));

			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("second_PdtPrice").toString())));
			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("first_PdtPrice").toString())));
			Assert.assertTrue((verifyValueInTheScreen(BaseClass.reference.get("totalPrice").toString())));

		} catch (Throwable e) {
			e.printStackTrace();
			Report_log("Details missing in the Order details screen", "FAIL");
		}

	}

}
