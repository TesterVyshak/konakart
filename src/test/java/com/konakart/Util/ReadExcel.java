package com.konakart.Util;

import java.util.ArrayList;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ReadExcel {
	
	public void getCustomerDetails() throws FilloException
	{
		
		String query=null;
		
		String sheetName="CustomerDetails";
		
		String sheetPath="./testData/TestDataSheet.xlsx";
		
		query=String.format("SELECT * FROM CustomerDetails", sheetName);
		
		Fillo fillo=new Fillo();
		
		Connection connection=null;
		
		Recordset recordset=null;
		
		connection=fillo.getConnection(sheetPath);
		
		recordset=connection.executeQuery(query);
		
		while (recordset.next()) {
			
			
			
			Customer.setGender(recordset.getField("Gender"));
			Customer.setFirstName(recordset.getField("FirstName"));
			Customer.setLastName(recordset.getField("LastName"));
			Customer.setDOB(recordset.getField("DOB"));
			Customer.setEMail(recordset.getField("EMail"));
			Customer.setUsername(recordset.getField("Username"));
			Customer.setCompanyName(recordset.getField("CompanyName"));
			Customer.setStreetAddress(recordset.getField("StreetAddress"));
			Customer.setSuburb(recordset.getField("Suburb"));
			Customer.setPostCode(recordset.getField("PostCode"));
			Customer.setCity(recordset.getField("City"));
			Customer.setState(recordset.getField("State"));
			Customer.setCountry(recordset.getField("Country"));
			Customer.setPrimaryPhone(recordset.getField("PrimaryPhone"));
			Customer.setOtherPhone(recordset.getField("OtherPhone"));
			Customer.setNewsletter(recordset.getField("Newsletter"));
			Customer.setPassword(recordset.getField("Password"));
			Customer.setConfirmPassword(recordset.getField("ConfirmPassword"));

			
		}
		
		connection.close();
		recordset.close();
		
	}

}
