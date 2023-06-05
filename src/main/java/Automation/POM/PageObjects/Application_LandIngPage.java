package Automation.POM.PageObjects;

import java.time.Duration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.service.util.ExceptionUtil;

import Automation.POM.framework.Action.action_keywrods;
import Automation.POM.framework.Utilities.ExecutionReport;
import Automation.POM.framework.Utilities.ExecutionStatus;
import Automation.POM.framework.Utilities.globalConfig;
import Automation.POM.framework.core.BaseClass;

public class Application_LandIngPage extends BaseClass {
	
	action_keywrods actions;
	
	
	
	public Application_LandIngPage(WebDriver driver) {
		driver= BaseClass.driver;
		actions= new action_keywrods();
		PageFactory.initElements(driver, this);
		

	}

	JavascriptExecutor js=(JavascriptExecutor)driver;
	
	@FindBy(xpath="//*[text()='REGISTER']")
	WebElement RegisterLink; 
	
	@FindBy(name="userName")
	WebElement UserNameField;
	
	@FindBy(name="password")
	WebElement passwordFld;

	@FindBy(name="submit")
	WebElement submit;
	
	@FindBy(xpath="//*[text()='Login Successfully']")
	WebElement Signin_confirmation;
	
	/**
	 * launchApllication
	 * @param report
	 * @throws Exception
	 */
	public void launchApllication(ExtentTest report) throws Exception {
		try {
			driver.get(globalConfig.globalParameter.get("ApplicationUrl"));

			System.out.println(globalConfig.globalParameter.get("ApplicationUrl"));

			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

			String pageTitle = driver.getTitle();
			System.out.println(pageTitle);
			actions.implicitWait(3);
			
			ExecutionReport.loggMessage(report, "Capture Page Title", "Page Title Captured Successfully", ExecutionStatus.PASS);

		} catch (Exception e) {
			e.printStackTrace();
			ExecutionReport.loggMessage(report, "Application launch Method Fail", "Unble to launch the application url"+ExceptionUtil.getStackTrace(e), ExecutionStatus.FAIL);
		}
	}
	
	
	/**
	 * Perform_SignUP
	 * Click on the SignUp Link 
	 * @throws Exception
	 */
	public void Perform_SignUP_LinkCLick(ExtentTest report) throws Exception {
		try {		

			if (actions.checkElementExist(RegisterLink, 03)) {
				js.executeScript("arguments[0].scrollIntoView(true);", RegisterLink);
				actions.click(RegisterLink);
				ExecutionReport.loggMessage(report, "Click on Sign-UP Link", "Sign-Up link clicked successfully",
						ExecutionStatus.PASS);
			} else {
				ExecutionReport.loggMessage(report, "Unable to click Sign-UP Link",
						"Sign-Up link is not displayed or not enabled", ExecutionStatus.FAIL);
				throw new NoSuchElementException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExecutionReport.loggMessage(report, "Click on Sign-UP Link-Fail",
					"Failed to click on Sign-UP link" + ExceptionUtil.getStackTrace(e), ExecutionStatus.FAIL);
		}
	}
	
	/**
	 * perform_signIn
	 * @param report
	 * @throws Exception
	 */
	public void perform_signIn(Hashtable<String, String> db_testData,ExtentTest report) throws Exception
	{
		try {
			
			actions.sendText(UserNameField, db_testData.get("login_userName"));
			actions.sendText(passwordFld, db_testData.get("loginPass"));
			
			actions.Wait(03);
			ExecutionReport.loggMessage(report, "Sign-in Data enter", "Sign In data entered successfully", ExecutionStatus.PASS);
			actions.click(submit);
			actions.implicitWait(04);
			
			if(actions.checkElementExist(Signin_confirmation, 02)) {
				ExecutionReport.loggMessage(report, "Sign-in Complete", "Signed IN successfully-", ExecutionStatus.PASS);
			} else {
				ExecutionReport.loggMessage(report, "Perform signin-Fail",
						"Failed to perform signin" , ExecutionStatus.FAIL);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			ExecutionReport.loggMessage(report, "Perform signin-Fail",
					"Failed to perform signin" + ExceptionUtil.getStackTrace(e), ExecutionStatus.FAIL);
		}
	}
}
