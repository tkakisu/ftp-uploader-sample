package jp.okamatake.ftpsample.stepdefs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;

public class WebConnector {

	private WebDriver driver;

	@After
	public void destroy() {
		if (driver != null) {
			driver.close();
		}
	}

	public WebDriver getDriver() {
		if (driver == null) {
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		}
		return driver;
	}

	public void open(String url) {
		getDriver().get(url);
	}

	public void inputText(String name, String text) {
		WebElement element = getDriver().findElement(By.name(name));
		element.sendKeys(text);
	}

	public void click(String selector) {
		WebElement element = getDriver().findElement(By.cssSelector(selector));
		element.click();
	}

	public String getCurrentUrl() {
		return getDriver().getCurrentUrl();
	}

	public boolean existsXpath(String xpathExpression) {
		WebElement element = getDriver().findElement(By.xpath(xpathExpression));
		return element != null;
	}
}
