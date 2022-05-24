package pagerepository;

//import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import helpers.FileOperations;

public class RegistrationPage 
{
	
	@FindBy(how = How.CSS, using="button[name=\"Register Here\"]")
	public WebElement RegisterNowButton;
	
	@FindBy(how = How.CSS, using="div[class*='register-form'] div h4")
	public WebElement pageHeading;
	
	@FindBy(how = How.CSS, using="div.agree-check input[type='checkbox']")
	public WebElement CheckBox;
	
	@FindBy(how = How.NAME, using="email")
	public WebElement EnterEmail;
	
	@FindBy(how = How.NAME, using="Send OTP")
	public WebElement SendOTPButton;
	
	@FindBy(how = How.XPATH, using="//div[@id='update']/button[text()='Continuously check']")
	public WebElement ClickToUpdate;
	
	@FindBy(how = How.CSS, using="input[placeholder='Enter Password']")
	public WebElement EnterPassword;
	
	@FindBy(how = How.CSS, using="input[placeholder='Confirm Password']")
	public WebElement ReEnterPassword;
	
	@FindBy(how = How.NAME, using="Register")
	public WebElement RegisterButton;
	
	@FindBy(how = How.XPATH, using="//h5")
	public WebElement getHeading;
	
	@FindBy(how = How.CSS, using="div.user-type-item span")
	public WebElement getUserType;
	
	@FindBy(how = How.XPATH, using="//input[@name='recipient']")
	public WebElement selectCheckBox;
	
	WebDriver driver;
	FileOperations FileOperationsObj = new FileOperations();
	public RegistrationPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public void clickToRegistration()
	{
		RegisterNowButton.click();
	}
	
	public void CheckPageHeading() throws Exception
	{
		String h4 = pageHeading.getText(); 
		Assert.assertEquals(h4, FileOperationsObj.ReadPropertyFile("heading"));
	}

	public void AcceptTermsAndConditions()
	{
		CheckBox.click();
	}
	
	public void ProvideEmail() throws Exception
	{
		EnterEmail.sendKeys(FileOperationsObj.ReadPropertyFile("Email"));
	}

	public void ProvideValidOTP() throws Exception
	{
		String OriginalPage = driver.getWindowHandle();
		
		SendOTPButton.click();
		
		driver.switchTo().newWindow(WindowType.TAB);
		
		String UserEmail = FileOperationsObj.ReadPropertyFile("Email");
		
		String UserName[] = UserEmail.split("@");
		
		System.out.println(UserName[0]);
		
		driver.get("https://www.dispostable.com/inbox/"+UserName[0]+"/");
		
		String EmailPage = driver.getWindowHandle();
		
		driver.switchTo().window(EmailPage);
		
		System.out.println(driver.getTitle());
		
		driver.navigate().refresh();
		
		WebElement FirstIframe = driver.findElement(By.xpath("//div[@id='update']/iframe[1]"));
		
		driver.switchTo().frame(FirstIframe);
		
		driver.findElement(By.xpath("//div[@id='update']/button[@class='button']")).click();
		
		Thread.sleep(3000);
		
					// The below code is of reCAPTCHA if appear just uncomment it or Comment it if not required.
		
		WebElement reCaptchaElement = driver.findElement(By.cssSelector("iframe[title='reCAPTCHA']"));
		
		driver.switchTo().frame(reCaptchaElement);
		
		driver.findElement(By.id("recaptcha-anchor")).click();
		
		Thread.sleep(15000);
		
		driver.switchTo().window(EmailPage);
		
			// if reCAPTCHA code is commented, comment below line if not just uncomment it.
		
		driver.findElement(By.cssSelector("input[value='View the email message']")).click();
		
		String validnumber = driver.findElement(By.xpath("//table/tbody/tr/td/div/div/table/tbody/tr/td/div")).getText();
		
		System.out.println(validnumber);
		
		driver.close();
		
		Thread.sleep(1000);
		
		driver.switchTo().window(OriginalPage);
		
		driver.findElement(By.cssSelector("input[placeholder='Enter OTP']")).sendKeys(validnumber);
		
		driver.findElement(By.name("Verify OTP")).click();
		
		
	}
	
	public void SetPassword() throws Exception
	{
		EnterPassword.sendKeys(FileOperationsObj.ReadPropertyFile("password"));
		
		ReEnterPassword.sendKeys(FileOperationsObj.ReadPropertyFile("password"));
		
		WebElement reCaptchaElement = driver.findElement(By.cssSelector("iframe[title='reCAPTCHA']"));
		
		driver.switchTo().frame(reCaptchaElement);
		
		driver.findElement(By.id("recaptcha-anchor")).click();
		
		Thread.sleep(15000);
		
		String OriginalPage = driver.getWindowHandle();
		
		driver.switchTo().window(OriginalPage);
		
		RegisterButton.click();
		
		String actualHeading = driver.findElement(By.xpath("//div[@class='form-style user-type']/h5")).getText();
		
		Assert.assertEquals(actualHeading, FileOperationsObj.ReadPropertyFile("expectedHeading"));
		
		String actualUserType = driver.findElement(By.cssSelector("div.user-type-item span")).getText();
		
		Assert.assertEquals(actualUserType, FileOperationsObj.ReadPropertyFile("SelectUserType"));
		
		driver.findElement(By.xpath("//input[@name='recipient']")).click();
		
		driver.findElement(By.xpath("//button[@name='Select Type']")).click();
		
	}
	
	
	public void EnterUserDetails() throws Exception
	{
		
		String PageUserHeading = driver.findElement(By.xpath("//div[@class='form-style upload-kyc-form']/h4")).getText();
		
		Assert.assertEquals(PageUserHeading, FileOperationsObj.ReadPropertyFile("userDetailPageHeading"));
		
		driver.findElement(By.id("root_firstName")).sendKeys(FileOperationsObj.ReadPropertyFile("FirstName"));
		
		driver.findElement(By.id("root_middleName")).sendKeys(FileOperationsObj.ReadPropertyFile("MiddleName"));
		
		driver.findElement(By.id("root_lastName")).sendKeys(FileOperationsObj.ReadPropertyFile("LastName"));
		
		driver.findElement(By.id("root_address")).sendKeys(FileOperationsObj.ReadPropertyFile("StreetAddress"));
		
		driver.findElement(By.id("root_city")).sendKeys(FileOperationsObj.ReadPropertyFile("City"));
		
		WebElement SelectState = driver.findElement(By.id("root_state"));
		
		Select StateObj = new Select(SelectState);
		
		StateObj.selectByVisibleText(FileOperationsObj.ReadPropertyFile("State"));
		
		driver.findElement(By.id("root_pincode")).sendKeys(FileOperationsObj.ReadPropertyFile("PinCode"));
		
		String getCountry = driver.findElement(By.id("root_country")).getAttribute("value");
		
		Assert.assertEquals(getCountry, FileOperationsObj.ReadPropertyFile("Country"));
		
		driver.findElement(By.xpath("//input[@id='root_I accept the terms and conditions']")).click();
		
		String getModelTitle = driver.findElement(By.xpath("//div[@class='modal-title h4']")).getText();
		
		Assert.assertEquals(getModelTitle, FileOperationsObj.ReadPropertyFile("modelTitle"));
		
		driver.findElement(By.cssSelector("button.close")).click();
		
		driver.findElement(By.xpath("//button[text()='Submit']")).click();
		
		
	}
	
	public void AppectLicensing() throws Exception
	{
		String actualpasscodeHeading = driver.findElement(By.xpath("//div[@class='form-style register-form']/h4")).getText();
		
		Assert.assertEquals(actualpasscodeHeading, FileOperationsObj.ReadPropertyFile("passcodePageTitle"));
		
		driver.findElement(By.xpath("//div[@class='agree-check']/input[@type='checkbox']")).click();
		
		String checkEmail = driver.findElement(By.xpath("//input[@name='email']")).getAttribute("value");
		
		Assert.assertEquals(checkEmail, FileOperationsObj.ReadPropertyFile("Email"));
        
		
	}
	
	public void OTPVerification() throws Exception
	{
		String OriginalPage = driver.getWindowHandle();
		
		
		driver.findElement(By.cssSelector("button[name='Send OTP']")).click();
		
		
		driver.switchTo().newWindow(WindowType.TAB);
		
		String UserEmail = FileOperationsObj.ReadPropertyFile("Email");
		
		String UserName[] = UserEmail.split("@");
		
		System.out.println(UserName[0]);
		
		driver.get("https://www.dispostable.com/inbox/"+UserName[0]+"/");
		
		String EmailPage = driver.getWindowHandle();
		
		driver.switchTo().window(EmailPage);
		
		System.out.println(driver.getTitle());
		
		driver.navigate().refresh();
		
		WebElement FirstIframe = driver.findElement(By.xpath("//div[@id='update']/iframe[1]"));
		
		driver.switchTo().frame(FirstIframe);
		
		driver.findElement(By.xpath("//div[@id='update']/button[@class='button']")).click();
		
		Thread.sleep(3000);
			
		driver.switchTo().window(EmailPage);
		
		String validnumber = driver.findElement(By.xpath("//table/tbody/tr/td/div/div/table/tbody/tr/td/div")).getText();
		
		System.out.println(validnumber);
		
		driver.close();
		
		Thread.sleep(1000);
		
		driver.switchTo().window(OriginalPage);
		
		driver.findElement(By.cssSelector("input[placeholder='Enter OTP']")).sendKeys(validnumber);
		
		driver.findElement(By.name("Verify OTP")).click();
		
	}
	
	public void GenertePassCode() throws Exception
	{
		driver.findElement(By.name("Generate Passcode")).click();
		
		String PassphraseKey = driver.findElement(By.xpath("//div[@class='row']/div/input")).getAttribute("value");
		
		String UserEmail = FileOperationsObj.ReadPropertyFile("Email");
		
		String UserName[] = UserEmail.split("@");
		
		FileOperationsObj.WritePropertyFile(PassphraseKey, "Passphrase", UserName[0]);
		
		String PrivateKey = driver.findElement(By.id("myInput")).getText();
		
		FileOperationsObj.WritePropertyFile(PrivateKey, "Private_Key", UserName[0]);
		
		driver.findElement(By.cssSelector("button[name='Go To Dashboard']")).click();
	}

}
