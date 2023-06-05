package Automation.POM.framework.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Automation.POM.framework.Action.action_keywrods;
import Automation.POM.framework.Utilities.globalConfig;


public class BaseClass {

	public static WebDriver driver;
	public static Logger logger = LogManager.getLogger(BaseClass.class);
	public static ExtentReports Extent;
	public static action_keywrods actions= new action_keywrods();
	public static ExtentTest report;

	public static globalConfig globalParam;
	
	

	public void initializeDriver(Hashtable<String, String> testData) throws Exception {

		try {
			if (testData.get("Medium").equalsIgnoreCase("Web")) {
				if (testData.get("Browser").equals("Edge")) {
					logger.info("Browser medium is: " + testData.get("Browser"));

					try {
						EdgeOptions opt = new EdgeOptions();

						opt.addArguments("start-maximized");
						// opt.addArguments("user-data-dir=/path/to/your/custom/profile");
						opt.addArguments("--no-sandbox");
						opt.addArguments("--disable-infobars");
						opt.addArguments("--disable-extensions");
						opt.addArguments("--disable-gpu");
						opt.addArguments("--remote-allow-origins=*");
						
						driver = new EdgeDriver(opt);
						driver.manage().deleteAllCookies();
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
						logger.info("Edge browser is launched successfully");
						
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Failed to load the Edge Browser");
					}
				}

				else if (testData.get("Browser").equals("Chrome")) {

					try {
						ChromeOptions opt = new ChromeOptions();
						String dflt_DownloadPath = System.getProperty("user.dir") + "\\Downloads";
						HashMap<String, Object> chromeperfs = new HashMap<String, Object>();
						chromeperfs.put("download.default_directory", dflt_DownloadPath);
						opt.setExperimentalOption("perfs", chromeperfs);

						opt.addArguments("start-maximized");
						opt.addArguments("user-data-dir=/path/to/your/custom/profile");
						opt.addArguments("no-sandbox");
						opt.addArguments("--disable-dev-shm-usage");
						opt.addArguments("--disable-infobars");
						opt.addArguments("--disable-extensions");
						opt.addArguments("--disable-gpu");
						opt.addArguments("--remote-allow-origins=*");
					
						driver = new ChromeDriver(opt);
						driver.manage().deleteAllCookies();
						driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
						logger.info("Chrome browser loaded successfully");
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("unable to load the Chrome browser");
					}

				}

			} else {
				logger.error("Execution medium is not WEB");
				throw new InvalidArgumentException("Execution medium is not WEB");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * initializeConfig
	 * 
	 * @throws Exception
	 */
	
	public void initializeConfig() throws Exception {

		try {
			globalParam = new globalConfig();
			String filePath = System.getProperty("user.dir") + "\\GlobalConfig\\Global_configFile.xlsx";
			globalParam.getGlobalConfig(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to capture the global config file path");
		} finally {
			try {
				Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");
				Runtime.getRuntime().exec("taskkill /F /IM winword.exe");
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Unable to kill the existing open process and browsers");
			}
		}
	}

	/**
	 * quit_process
	 * 
	 * @throws Exception
	 */
	public void quit_process(ExtentTest report) throws Exception {

		try {
			driver.quit();
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to kill the driver instance");
		}
	}

	/**
	 * CreateReportFolder
	 * 
	 * @throws Exception
	 */
	public void CreateReportFolder() throws Exception {
		try {

			if (logger.isDebugEnabled()) {
				logger.info("CreateReportFolder()- start");
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyddmmHHmmss");
			String folderName = sdf.format(date);
			File directry1 = new File("./Reports/" + folderName);
			directry1.mkdir();

			File directry2 = new File("./Reports/" + folderName + "/Screenshots/");
			directry2.mkdir();

			System.setProperty("reportFolderName", folderName);
			logger.info("Reporting folder created successfully");

			if (logger.isDebugEnabled()) {
				logger.info("CreateReportFolder()- Ends");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Failed to create a folder for reports");
		}
	}

	@BeforeSuite
	public void configureReportingStructure() throws Exception {
		try {
			CreateReportFolder();
			
			Extent= new ExtentReports();
			String Reportingpath = System.getProperty("user.dir") + "\\Reports\\"
					+ System.getProperty("reportFolderName") + "\\Execution_Result.html";

			ExtentSparkReporter reporter = new ExtentSparkReporter(Reportingpath);

			reporter.config().setReportName("Automation Execution Report");
			
			Extent.attachReporter(reporter);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failer to configure the Reporting html structure");
		} return;
	}

	@AfterClass
	public void generateReport() throws Exception {
		Extent.flush();
	}
	
	
	/**
	 * String decryption
	 * @param encodeData
	 * @return
	 * @throws Exception
	 */
	public String decodeEncryptedData(String encodeData) throws Exception
	{
		byte[] decodedData=null;
		try {
			//Convert string to Byte	
			decodedData= Base64.decodeBase64(encodeData);
		}catch(Exception e) {
			e.printStackTrace();
		}
		 return (new String(decodedData));
	}
}



