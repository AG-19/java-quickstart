package com.blogspot.adapttoaem;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumApp {

	public static void main( String[] ag ) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		driver.get("https://github.com/ASSAABLOYAMERICAS/americas-onecms/actions");

		WebElement userName = new WebDriverWait(driver, java.time.Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.id("login_field")));
		userName.sendKeys("anudeep-garnepudi");
		WebElement password = new WebDriverWait(driver, java.time.Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		password.sendKeys("loginGit123$");

		WebElement submit = new WebDriverWait(driver, java.time.Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.name("commit")));
		submit.click();
		
		String wfName = "Java CI with Maven";

		while (true) {

			WebElement aEle = new WebDriverWait(driver, java.time.Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(wfName)));
			WebElement nagarroSyncRoot = aEle.findElement(By.xpath("parent::*")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::*"));

			WebElement textRight = nagarroSyncRoot.findElement(By.cssSelector(".text-right"));
			textRight.click();

			WebElement deleteWorkflow = new WebDriverWait(driver, java.time.Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(textRight, By.cssSelector(".dropdown-menu li details"))).get(0);
			deleteWorkflow.click();

			new WebDriverWait(driver, java.time.Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(deleteWorkflow, By.cssSelector("form button"))).get(0).click();
		}

		//driver.quit();

	}

}
