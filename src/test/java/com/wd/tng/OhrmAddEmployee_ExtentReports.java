package com.wd.tng;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class OhrmAddEmployee_ExtentReports {
	/*
	 * download extent jars 
	 * goto http://extentreports.com/community/
	 * click on 2.41.2
	 * it will navigate to google drive
	 * login there
	 * click on download symbol
	 * extract zip file
	 * add all jars from all folders to buildpath
	 */
	
	WebDriver driver;
	ExtentReports xReport;
	ExtentTest test;
	
	@BeforeClass
	public void OpenApplication() {
		xReport = new ExtentReports("src/../Reports/ohrmAddemp1.html",true);
		test = xReport.startTest("OhrmAddEmp");
		
		try{
		// configure browser driver
		System.setProperty("webdriver.chrome.driver",
				"F:\\SeleniumSoftware\\BrowserDrivers\\chromedriver.exe");

		// open new edge window
		driver = new ChromeDriver();

		// specify page load time
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		// element wait time
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// maximize window
		// driver.manage().window().maximize();

		driver.get("http://localhost:81/orangehrm/symfony/web/index.php");
		
		//verify title of the opened application
		
		/*if(driver.getTitle().equalsIgnoreCase("OrangeHRM1")!=true){
			throw new AssertionError();
		}*/
				
		assertEquals(driver.getTitle(), "OrangeHRM");
		test.log(LogStatus.PASS, "Application Opened");
		}catch(AssertionError ase){
			test.log(LogStatus.FAIL, "Failed to open Application");
			xReport.flush();
			Assert.fail("Failed to open Application");
		}

	}

	@Test
	public void Login() throws IOException {

		try{
			
		
		// enter text on username
		driver.findElement(By.xpath("//*[@id='txtUsername']"))
				.sendKeys("admin");
		test.log(LogStatus.PASS, "text enetered in USername");
		
		// enter password
		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys(
				"sudhakar");
		test.log(LogStatus.PASS,"text enetered password");
		// click on login button
		driver.findElement(By.cssSelector("#btnLogin1")).click();
		test.log(LogStatus.PASS,"Clicked on login Button");
		}
		catch(NoSuchElementException nse){
			File sFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File dFile = new File("src/../Reports/ohrmAddemp.jpg");
			FileUtils.copyFile(sFile, dFile);
			test.log(LogStatus.FAIL,"Failed to Find an Element "+test.addScreenCapture(dFile.getAbsolutePath()));
			nse.printStackTrace();			
			Assert.fail("Failed to Find an Element ");
			
		}
	}

	@Test(dependsOnMethods = "Login")
	public void AddEmployee() {
		driver.findElement(By.linkText("PIM")).click();
		// click on AddEmployee link
		driver.findElement(By.partialLinkText("Add Employee")).click();

		// enter first name
		driver.findElement(By.id("firstName")).sendKeys("selenium");

		// enter last name
		driver.findElement(By.id("lastName")).sendKeys("selenium");

		driver.findElement(By.id("btnSave")).click();

	}

	@AfterClass
	public void CloseApplication() {
		// close the browser
		driver.quit();
		xReport.endTest(test);
		xReport.flush();
	}
}





