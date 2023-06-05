package Automation.POM.framework.Utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import Automation.POM.framework.core.BaseClass;



public class ExecutionReport extends BaseClass {

	public static ExtentTest test;
	public static boolean failedscreenshot = true;
	public static boolean passscreenshot = true;

	public ExecutionReport(WebDriver driver) {
		BaseClass.driver = driver;
	}


	public static void loggMessage(ExtentTest report, String StepPerform_Message, String actualMessage,
			ExecutionStatus logType) throws Exception {
		String screenshotPath = "";
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[2];
		String ClassName = element.getClassName();
		String MethodName = element.getMethodName();
		try {

			if (logType.equals(ExecutionStatus.PASS)) {
				if (passscreenshot)
					screenshotPath = takeExeCutionScreenshot(driver);
				
				report.createNode("Step Performed: " + StepPerform_Message).pass(actualMessage)
						.addScreenCaptureFromPath(screenshotPath);
				
				logger.info("class Name: " + ClassName + "||" + " Function Name: " + MethodName + "||"
						+ " Step Performed: " + StepPerform_Message + "||" + " Message: " + actualMessage);
			}
			
			else if (logType.equals(ExecutionStatus.FAIL)) {
				if (failedscreenshot)
					screenshotPath = takeExeCutionScreenshot(driver);
				logger.info("class Name: " + ClassName + "||" + " Function Name: " + MethodName + "||"
						+ " Step Performed: " + StepPerform_Message + "||" + " Message: " + actualMessage);
				report.createNode("Step Performed: " + StepPerform_Message).fail(actualMessage)
						.addScreenCaptureFromPath(screenshotPath);
			} 
			
			else if (logType.equals(ExecutionStatus.INFO)) {
				if (failedscreenshot)
					
				logger.info("class Name: " + ClassName + "||" + " Function Name: " + MethodName + "||"
						+ " Step Performed: " + StepPerform_Message + "||" + " Message: " + actualMessage);
				report.createNode("Step Performed: " + StepPerform_Message).info(actualMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed to logg reports");
		}
	}
	
	
	/**
	 * exeCutionScreenshot
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public static String takeExeCutionScreenshot(WebDriver driver) throws Exception
	{
		String screenshotFileName=null;
		try {
			String reportFolderName= System.getProperty("reportFolderName");
			Date date= new Date();
			SimpleDateFormat dateFormat=new SimpleDateFormat("YYYYmmddHHmmss");
			Random ran= new Random();
			int RandomNo= ran.nextInt(9999)+1;
			screenshotFileName= dateFormat.format(date)+""+RandomNo+".png";
			
			File newScreenshot= new File("./Reports/"+reportFolderName+"/Screenshots/"+screenshotFileName);
			File scrnshtFile= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			FileUtils.copyFile(scrnshtFile, newScreenshot);
		
	}catch(Exception e) {
		e.printStackTrace();
	}
		return "Screenshots/"+screenshotFileName;
	}
}
