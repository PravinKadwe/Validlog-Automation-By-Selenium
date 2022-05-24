package pagerepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import helpers.FileOperations;

public class LoginPage 
{
	
	@FindBy(how = How.XPATH, using="//div[@class='form-style login-form']/h4")
	public WebElement LoginpageHeading;
	
	@FindBy(how = How.CSS, using="input[name='email']")
	public WebElement EnterUserEmail;
	
	@FindBy(how = How.CSS, using="input[name='password']")
	public WebElement EnterUserPassword;
	
	@FindBy(how = How.CSS, using="button[name='Login']")
	public WebElement LoginButton;
	

	WebDriver driver;
	FileOperations fileOperationsObj = new FileOperations();
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public void LoginPageHeading() throws Exception
	{
		String actualHeading = LoginpageHeading.getText();
		Assert.assertEquals(actualHeading, fileOperationsObj.ReadPropertyFile("loginpageHeading"));
	}
	
	public void EnterEmail() throws Exception
	{
		EnterUserEmail.sendKeys(fileOperationsObj.ReadPropertyFile("Email"));
	}
	
	public void EnterPassword() throws Exception
	{
		EnterUserPassword.sendKeys(fileOperationsObj.ReadPropertyFile("password"));
	}
	
	public void SolveReCAPTCHA() throws Exception
	{
		WebElement reCaptchaElement = driver.findElement(By.cssSelector("iframe[title='reCAPTCHA']"));
		
		driver.switchTo().frame(reCaptchaElement);
		
		driver.findElement(By.id("recaptcha-anchor")).click();
		
		Thread.sleep(35000);
		
		String OriginalPage = driver.getWindowHandle();
		
		driver.switchTo().window(OriginalPage);
	}
	
	
	public void ClickLoginButton()
	{
		LoginButton.click();
	}
	
	
	public void CheckDashboardPage() throws Exception
	{
		String dashbordHeading = driver.findElement(By.xpath("//h5[@class='navbar-heading']")).getText();
		
		Assert.assertEquals(dashbordHeading, fileOperationsObj.ReadPropertyFile("DashboardHeadinh"));
		
	}
	
	
	
	
	
}
