package Automation.POM.PageObjects;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.service.util.ExceptionUtil;

import Automation.POM.framework.Utilities.ExecutionReport;
import Automation.POM.framework.Utilities.ExecutionStatus;
import Automation.POM.framework.core.BaseClass;

public class SignUp_page extends BaseClass{

		
	public SignUp_page(WebDriver driver) {
		BaseClass.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	
	
	
	/*********************************************************************************/
	@FindBy(name="firstName")
	WebElement firstNameField;
	
	@FindBy(name="lastName")
	WebElement lastNameField;
	
	@FindBy(name="phone")
	WebElement phoneField;
	
	@FindBy(name="userName")
	WebElement emailField;
	
	@FindBy(name="address1")
	WebElement AddressFld;
	
	@FindBy(name="city")
	WebElement CityFld;
	
	@FindBy(name="state")
	WebElement stateFld;
	
	@FindBy(name="postalCode")
	WebElement postalCodeFld;
	
	@FindBy(name="country")
	WebElement countryFld;
	
	@FindBy(name="email")
	WebElement UserNameFld;
	
	@FindBy(name="password")
	WebElement passwordFld;
	
	@FindBy(name="confirmPassword")
	WebElement confirmPasswordFld;
	
	@FindBy(name="submit")
	WebElement submit;
	
	/*************************************************************************************/
	
	
	
	
	/**
	 * EnterSignUpDetails
	 * Enter the user sign up details 
	 * @param report
	 * @throws Exception
	 */
	public void EnterSignUpDetails(Hashtable<String, String> testData, ExtentTest report) throws Exception {
		try {

			actions.sendText(firstNameField, testData.get("FirstName"));
			actions.sendText(lastNameField, testData.get("LastName"));
			actions.sendText(phoneField, testData.get("Phone"));
			actions.sendText(emailField, testData.get("Email"));
			actions.sendText(AddressFld, testData.get("Address"));
			actions.sendText(CityFld, testData.get("City"));
			actions.sendText(stateFld, testData.get("State"));
			actions.sendText(postalCodeFld, testData.get("P-code"));
			actions.sendText(UserNameFld, testData.get("UserName"));

			/* Select drop down value from the list */
			Select objSelect = new Select(countryFld);
			objSelect.selectByVisibleText(testData.get("Country"));

			actions.sendText(passwordFld, this.decodeEncryptedData(testData.get("Password")));
			actions.sendText(confirmPasswordFld, this.decodeEncryptedData(testData.get("Password")));
			actions.Wait(03);

			ExecutionReport.loggMessage(report, "Sign-Up data form fill", "Sign-UP User data entered successfully",
					ExecutionStatus.PASS);

			actions.click(submit);
			actions.implicitWait(03);
			ExecutionReport.loggMessage(report, "Sign-Up button Click", "Sign-UP Button Clicked successfully",
					ExecutionStatus.PASS);

		} catch (Exception e) {
			e.printStackTrace();
			ExecutionReport.loggMessage(report, "SignUp detail enter step fail",
					"failed to enter user detail for signup" + ExceptionUtil.getStackTrace(e), ExecutionStatus.FAIL);
		}
	}

}
