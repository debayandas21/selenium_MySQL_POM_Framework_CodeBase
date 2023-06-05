package Automation.POM.framework.Action;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import Automation.POM.framework.core.BaseClass;
import Automation.POM.framework.interfaces.Elements;

public class action_keywrods extends BaseClass implements Elements {
	
	JavascriptExecutor js=(JavascriptExecutor)driver;
	/**
	 * click Perform click operation
	 */
	public void click(WebElement locator)
			throws NoSuchElementException, ElementClickInterceptedException, ElementNotInteractableException {
		// TODO Auto-generated method stub
		try {
			if (driver == null) {
				throw new UnreachableBrowserException("Browser is not launched  or driver is failed to initialize");
			}

			else if (locator != null) {
				if (locator.isDisplayed()) {
					locator.click();
				}
			} else {
				throw new NoSuchElementException("Element is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wait Customized Wait command for static wait time
	 *
	 */
	@Override
	public long Wait(int timeUnit) throws Exception {
		if (driver != null) {
			logger.info("driver is not null");
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeUnit));
				logger.info("wait for " + timeUnit + " seconds");
				System.out.println("wait for " + timeUnit + " seconds");
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return timeUnit;

	}

	/**
	 * implicitWait Implicitly wait
	 */
	@Override
	public long implicitWait(int timeUnit) throws Exception {

		if (driver != null) {
			logger.info("driver is not null");

			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeUnit));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return timeUnit;
	}

	@Override
	public void sendText(WebElement locator, String inputValue)
			throws NoSuchElementException, UnreachableBrowserException {

		try {
			if (driver == null) {
				throw new UnreachableBrowserException("Browser is not launched  or driver is failed to initialize");
			} else if (locator != null) {
				if (locator.isDisplayed()) {
					locator.sendKeys(inputValue);
					logger.info("Send key method worked successfully");
				}
			} else {

				throw new NoSuchElementException("Element is not present");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getText(WebElement locator) throws NoSuchElementException, WebDriverException {

		String getValue = null;
		try {
			if (driver == null) {
				throw new UnreachableBrowserException("Browser is not launched  or driver is failed to initialize");
			}

			else if (locator != null) {
				if (locator.isDisplayed() && locator.isEnabled()) {
					logger.info("locator is displayed: " + locator);
					getValue = locator.getText();
				}

			} else {

				throw new NoSuchElementException("Element is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getValue;

	}

	@Override
	public boolean checkElementExist(WebElement locator, int timeUnit)
			throws NoSuchElementException, ElementNotInteractableException {
		
		boolean flag=false;
		try {
			actions.Wait(timeUnit);
			if(driver==null) {
				throw new UnreachableBrowserException("Browser is not launched  or driver is failed to initialize");
			}
			else if(locator!=null) {
				if(locator.isDisplayed() && locator.isEnabled()) {
					flag=true;
					
					logger.info("Element is displayed on the page and enabled");
				}
			} else {
				throw new NoSuchElementException("Element is not present on thee page");
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			
		}return flag;
	}

}
