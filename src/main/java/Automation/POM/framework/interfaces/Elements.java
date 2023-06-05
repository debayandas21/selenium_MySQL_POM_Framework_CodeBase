package Automation.POM.framework.interfaces;

import java.util.NoSuchElementException;



import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;



public interface Elements {

	
	public void click (WebElement locator) throws NoSuchElementException, ElementClickInterceptedException, ElementNotInteractableException;
	
	public long Wait(int timeUnit) throws Exception;
	
	public long implicitWait(int timeUnti) throws Exception;
	
	public void sendText(WebElement locator, String inputValue) throws NoSuchElementException, UnreachableBrowserException;
	
	public String getText(WebElement locator) throws NoSuchElementException, WebDriverException;
	
	public boolean checkElementExist(WebElement locator, int timeUnit) throws NoSuchElementException, ElementNotInteractableException;
	
	
	
}
