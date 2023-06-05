package Automation.POM.Test;

import java.util.Hashtable;

import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import Automation.POM.PageObjects.Application_LandIngPage;
import Automation.POM.PageObjects.SignUp_page;
import Automation.POM.framework.Utilities.DataRead;
import Automation.POM.framework.Utilities.ExecutionReport;
import Automation.POM.framework.Utilities.ExecutionStatus;
import Automation.POM.framework.core.BaseClass;

public class TC01_Launch_Application_SignUpUser_Test extends BaseClass {

	ExtentTest report;

	@DataProvider(name = "testDataProvider")
	public Object[][] testDataProvider() throws Exception {
		return DataRead.getConsolidatedtestDataArray(this.getClass().getSimpleName().toString(), "TestData_Sheet1");
	}

	@BeforeTest
	public void setUpConfig() throws Exception {
		initializeConfig();
	

	}

	@Test(dataProvider = "testDataProvider")
	public void Launch_Application(Hashtable<String, String> testData, ITestContext context) throws Exception {
		try {
			report = Extent.createTest(this.getClass().getSimpleName());
			initializeDriver(testData);

			Application_LandIngPage homeP = new Application_LandIngPage(driver);
			
			SignUp_page signP = new SignUp_page(driver);

			/* launch the application */
			homeP.launchApllication(report);

			/* Click on SignUP link */
			homeP.Perform_SignUP_LinkCLick(report);

			/* Enter SignUp Details */
			signP.EnterSignUpDetails(testData, report);
			
			
			

		} catch (Exception e) {
			e.printStackTrace();

		}

		finally {
			super.quit_process(report);
			ExecutionReport.loggMessage(report, "Kill Browser Instance", "Browser instance killed successfully",
					ExecutionStatus.INFO);

		}
	}

}
