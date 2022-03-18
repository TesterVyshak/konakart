package com.konakart.pages;


import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import com.konakart.Util.Customer;
import com.konakart.Util.ReadExcel;
import com.konakart.testcases.BaseClass;


public class CustomerDetails extends BaseCommon {

	public CustomerDetails(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	ReadExcel excelRead = new ReadExcel();

	OrderConfirmation orderConfirmation = new OrderConfirmation(driver);

	@FindBy(xpath = "//span[contains(text(),'Register an account')]")
	public static WebElement button_Register;

	@FindBy(xpath = "//input[@name='gender' and @value='m']")
	public static WebElement gender_Male;

	@FindBy(xpath = "//input[@name='gender' and @value='f']")
	public static WebElement gender_Female;

	@FindBy(xpath = "//input[@id='firstName']")
	public static WebElement text_FirstName;

	@FindBy(xpath = "//input[@id='lastName']")
	public static WebElement text_LasttName;

	@FindBy(xpath = "//input[@id='datepicker']")
	public static WebElement datepicker_DOB;

	@FindBy(xpath = "//input[@id='emailAddr']")
	public static WebElement text_EmailAddress;

	@FindBy(xpath = "//input[@id='username']")
	public static WebElement text_UserName;

	@FindBy(xpath = "//input[@id='company']")
	public static WebElement text_CompanyName;

	@FindBy(xpath = "//input[@id='streetAddress']")
	public static WebElement text_StreetAddresse;

	@FindBy(xpath = "//input[@id='suburb']")
	public static WebElement text_Suburb;

	@FindBy(xpath = "//input[@id='postcode']")
	public static WebElement text_Postcode;

	@FindBy(xpath = "//input[@id='city']")
	public static WebElement text_City;

	@FindBy(xpath = "//select[@id='state']")
	public static WebElement dropdown_State;

	@FindBy(xpath = "//select[@id='countryId']")
	public static WebElement dropdown_Country;

	@FindBy(xpath = "//input[@id='telephoneNumber']")
	public static WebElement text_PrimaryPhoneNUmber;

	@FindBy(xpath = "//input[@id='telephoneNumber1']")
	public static WebElement text_OtherPhoneNUmber;

	@FindBy(xpath = "//input[@id='newsletterBool']")
	public static WebElement checkbox_Newsletter;

	@FindBy(xpath = "//input[@id='password']")
	public static WebElement text_Password;

	@FindBy(xpath = "//input[@id='passwordConfirmation']")
	public static WebElement text_PasswordConfirmation;

	@FindBy(xpath = "//a[@id='continue-button']")
	public static WebElement button_Continue;

	public void fillCustomerDetails() throws Throwable {

		excelRead.getCustomerDetails();

		if (Customer.getGender().equals("M")) {
			gender_Male.click();
		} else {
			gender_Female.click();
		}

		text_FirstName.sendKeys(Customer.getFirstName());
		text_LasttName.sendKeys(Customer.getLastName());
		datepicker_DOB.sendKeys(Customer.getDOB());

		if (Customer.getEMail().equals("auto")) {
			Customer.setEMail(BaseClass.getEMailAddress());
		}
		text_EmailAddress.sendKeys(Customer.getEMail());

		if (Customer.getUsername().equals("auto")) {
			Customer.setUsername(BaseClass.getUserName());
		}
		text_UserName.sendKeys(Customer.getUsername());
		text_CompanyName.sendKeys(Customer.getCompanyName());
		text_StreetAddresse.sendKeys(Customer.getStreetAddress());
		text_Suburb.sendKeys(Customer.getSuburb());
		text_Postcode.sendKeys(Customer.getPostCode());
		text_City.sendKeys(Customer.getCity());

		selectValueFromDropDown(dropdown_State, Customer.getState());
		selectValueFromDropDown(dropdown_Country, Customer.getCountry());

		text_PrimaryPhoneNUmber.sendKeys(Customer.getPrimaryPhone());
		text_OtherPhoneNUmber.sendKeys(Customer.getOtherPhone());
		text_Password.sendKeys(Customer.getPassword());
		text_PasswordConfirmation.sendKeys(Customer.getConfirmPassword());

	}

	public void clickWithoutCustomerDetails() throws Throwable {
		
		try {
		button_Register.click();
		isElementClickable(button_Continue);
		button_Continue.click();
		wait(1);
		Assert.assertEquals(12, driver.findElements(By.xpath("//label[@class='error']")).size());
		wait(2);

	}catch(Throwable e)
		{
		e.printStackTrace();
		Report_log("Customer details entry FAILED", "FAIL");
		}
		
		}

	public void clickContinueButton() throws InterruptedException {
		button_Continue.click();
		wait(2);
		Assert.assertTrue(orderConfirmation.isHeaderVisible());

	}

}
