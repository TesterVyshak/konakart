package com.konakart.testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.konakart.pages.AdvanceSearchPage;
import com.konakart.pages.CustomerDetails;
import com.konakart.pages.HomePage;
import com.konakart.pages.MyAccount;
import com.konakart.pages.OrderConfirmation;
import com.konakart.pages.ProductDetailsPage;
import com.konakart.pages.SearchResultPage;

public class Purchase_Game extends BaseClass {

	HomePage homepage;
	AdvanceSearchPage searchPage;
	SearchResultPage searchResult;
	ProductDetailsPage pdtDetailsPage;
	CustomerDetails customerDetails;
	OrderConfirmation orderConfirmation;
	MyAccount myAccount;

	@BeforeTest

	public void steup() throws Throwable {

		try {

			// reports
			startReport();
			test = extentReports.createTest("Game Purchase");

			// launch the browser
			driver = lauchBrowser();

			// validate user in homepage, using Title / Logo
			homepage = new HomePage(driver);
			homepage.checkHomePageVisible();
			Report_log("User verified that konkart site loaded successfully", "PASS");

		} catch (Exception e) {
			e.printStackTrace();
			Report_log("konkart site not loaded successfully", "FAIL");
		}

	}

	@Test
	public void test() throws Throwable {

		try {

			// user clicked on AdvanceSearch link

			homepage.clickAdvanceSearch();
			Report_log("User clicks on the AdvanceSearch link", "INFO");
			wait(2);

			searchPage = new AdvanceSearchPage(driver);

			searchPage.isUserRedirectsToAdvanceSearchPage();
			Report_log("User verified  Header and breadcrumb details of the AdvanceSearch page", "PASS");

			// Applying Search criteria

			searchPage.selectCategory("Games");
			Report_log("User selects Games from the category dropdown", "INFO");

			searchPage.enterPriceFrom("39");
			Report_log("User enters starting price as 39", "INFO");

			searchPage.enterPriceTo("80");
			Report_log("User enters starting price as 80", "INFO");

			searchPage.clickSearchButton();
			Report_log("User cliks on SEARCH button", "INFO");

			// Adding product to the cart

			searchResult = new SearchResultPage(driver);
			searchResult.checkUserInSearchResultsPage();
			Report_log("User verified that Result set showing as expected", "PASS");

			searchResult.checkIetmInStock("last");
			Report_log("User verified that Item in Stock", "PASS");

			searchResult.selectItemToCart("last");
			Report_log("User selects last item to the cart", "INFO");

			// Verify cart details

			homepage.checkItemInCart();
			Report_log("User verified that added item in the Cart", "PASS");

			// Redirects to the Product details screen and adding pdt to cart

			searchResult.selectItemsDetailsSCreen("first");
			Report_log("User clicked on the first item and redirects to the details screen", "INFO");

			pdtDetailsPage = new ProductDetailsPage(driver);

			pdtDetailsPage.isUserInDetailScreen();
			Report_log("User verified the details from Summary screen", "PASS");

			pdtDetailsPage.addProductInToCart(2);
			Report_log("User added the product into the cart with quantity as 2", "INFO");

			homepage.checkItemInCart();
			Report_log("User verified that added item in the Cart", "PASS");

			// Product checkout

			homepage.checkoutTheProducts();
			Report_log("User verified that cart details and click on CHECK OUT ", "PASS");

			// Checkout as Guest user

			customerDetails = new CustomerDetails(driver);
			customerDetails.clickWithoutCustomerDetails();
			Report_log("User verified that without mandatory details error labels showing properly ", "PASS");

			// Customer details entering to UI

			customerDetails.fillCustomerDetails();
			Report_log("User enters all the fields successfully ", "INFO");

			customerDetails.clickContinueButton();
			Report_log("User clicked CONTINUE  button and redirects to the Order Confirmation Page", "PASS");

			// Order confirmation

			orderConfirmation = new OrderConfirmation(driver);

			orderConfirmation.checkDetailsInConfirmationScreen();
			Report_log("User verified the order details in OrderConfirmation screen", "PASS");

			orderConfirmation.confirmOrder();
			Report_log("User clicked CONFIRM ORDER button and verified the order STATUS", "PASS");

			orderConfirmation.redirectToMyAccount();
			Report_log("User clicked CONTINUE and redirected to the My Account Information page", "INFO");

			// MyAccount details

			myAccount = new MyAccount(driver);
			myAccount.verifyOrderDetails();
			Report_log("User verified the Order details and corresponding status as Pending", "PASS");

		} catch (Exception e) {

			e.printStackTrace();
			Report_log("Product purchase FAILED", "FAIL");

		}

	}

	@AfterTest

	public void afterTest() {
		endReport();
		driver.quit();

	}

}
