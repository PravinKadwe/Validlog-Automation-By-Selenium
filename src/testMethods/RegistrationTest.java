package testMethods;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import helpers.BrowserOperation;
import helpers.FileOperations;
import pagerepository.RegistrationPage;

public class RegistrationTest 
{
	WebDriver driver;
	@Test
	public void RegistrationTestMethod() throws Exception
	{
		
		
		BrowserOperation BrowserOperationObj = new BrowserOperation();
		FileOperations FileOperationsObj = new FileOperations();
		driver = BrowserOperationObj.LaunchApplication(FileOperationsObj.ReadPropertyFile("browser"));
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String actualTitle = BrowserOperationObj.GetTitleOfBrowser();
		Assert.assertEquals(actualTitle, FileOperationsObj.ReadPropertyFile("pagetitle"));
		
		
		RegistrationPage RegistrationPageObj = new RegistrationPage(driver);
		
		RegistrationPageObj.clickToRegistration();
		RegistrationPageObj.AcceptTermsAndConditions();
		RegistrationPageObj.ProvideEmail();
		RegistrationPageObj.ProvideValidOTP();
		RegistrationPageObj.SetPassword();
//		RegistrationPageObj.SetUserType();
		RegistrationPageObj.EnterUserDetails();
		RegistrationPageObj.AppectLicensing();
		RegistrationPageObj.OTPVerification();
		RegistrationPageObj.GenertePassCode();
		
	}
	
	
	@AfterMethod
	public void CaptureScreenchot(ITestResult result) throws IOException
	{
		if(result.getStatus() == ITestResult.FAILURE)
		{
			TakesScreenshot sc = (TakesScreenshot)driver;
			File screenshot = sc.getScreenshotAs(OutputType.FILE);
			
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss");
			   LocalDateTime now = LocalDateTime.now();
			
			FileHandler.copy(screenshot, new File(".//Screenshots//Error"+dtf.format(now)+".png"));
		}
	}
	
}
