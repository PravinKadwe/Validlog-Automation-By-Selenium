package pagerepository;

import java.io.File;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import helpers.FileOperations;

public class KycUpdatePage 
{
	
	@FindBy(how = How.XPATH, using="//p[@class='desktop-menu']/span/i[text()='menu']")
	public WebElement ClickMenu;
	
	@FindBy(how = How.XPATH, using="//ul[@id='mySidebar']/li[6]/span[2]")
	public WebElement ClickKYCOption;
	
	@FindBy(how = How.CSS, using="div#add-odc-right-pane div div h4")
	public WebElement CheckFormHeading;
	
	@FindBy(how = How.CSS, using="input[placeholder='Enter Document Type']")
	public WebElement EnterDocumentType;
	
	@FindBy(how = How.CSS, using="input[placeholder='Enter Key']")
	public WebElement EnterKeyValue;
	
	@FindBy(how = How.CSS, using="input[placeholder='Enter Value']")
	public WebElement EnterValue;
	
	@FindBy(how = How.XPATH, using="//div[@id='add-odc-right-pane']/div/div/div/div[3]/span[text()='add_circle']")
	public WebElement ClickToSelect;
	
	@FindBy(how = How.ID, using="image")
	public WebElement UploadImageDoc;
	
	
	@FindBy(how = How.XPATH, using="//*[@id=\"add-odc-right-pane\"]/div/div/div[3]/div[2]/span")
	public WebElement ClickAddFileButton;
	
	@FindBy(how = How.CSS, using="input[name='searchIssuer']")
	public WebElement EnterIssuerEmail;
	
	@FindBy(how = How.CSS, using="button[name='Upload']")
	public WebElement ClickToUpload;
	
	
	@FindBy(how = How.CSS, using="input[placeholder='Enter Passphrase']")
	public WebElement EnterPassphrase;
	
	@FindBy(how = How.CSS, using="textarea[placeholder='Enter Private Key']")
	public WebElement EnterPrivateKey;
	
	@FindBy(how = How.NAME, using="Sign")
	public WebElement ClickSignButton;
	
	WebDriver driver;
	FileOperations FileOperationsObj = new FileOperations();
	public KycUpdatePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public void OpenSideManu()
	{
		ClickMenu.click();
	}
	
	public void OpenKYCOption()
	{
		ClickKYCOption.click();
	}
	
	public void CheckFormHeading() throws Exception
	{
		String actualFormHeading = CheckFormHeading.getText();
		
		Assert.assertEquals(actualFormHeading, FileOperationsObj.ReadPropertyFile("KycFormHeading"));
	}
	
	public void DocumentType() throws Exception
	{
		EnterDocumentType.sendKeys(FileOperationsObj.ReadPropertyFile("DocumentType"));
	}
	
	public void KeyValue() throws Exception
	{
		EnterKeyValue.sendKeys(FileOperationsObj.ReadPropertyFile("KeyValue"));
		
		EnterValue.sendKeys(FileOperationsObj.ReadPropertyFile("ValueNumber"));
		
		ClickToSelect.click();
	}
	
	public void SelectImageFile() throws Exception
	{
		
		UploadImageDoc.sendKeys(FileOperationsObj.ReadPropertyFile("UploadFile"));
	
		ClickAddFileButton.click();
	}
	
	public void SendToIssuer() throws Exception
	{
		EnterIssuerEmail.sendKeys(FileOperationsObj.ReadPropertyFile("IssuerEmail"));
	}
	
	public void UploadForm()
	{
		ClickToUpload.click();
	}
	
	public void OTPVerification() throws Exception
	{
		String OriginalPage = driver.getWindowHandle();
		
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
	
	public void SignDocument() throws Exception
	{
		String UserEmail = FileOperationsObj.ReadPropertyFile("Email");
		
		String UserName[] = UserEmail.split("@");
		
		Scanner PassphraseText = FileOperationsObj.PassphraseKey(UserName[0]);
		
		while(PassphraseText.hasNext())
		{
			EnterPassphrase.sendKeys(PassphraseText.nextLine());
		}
		
		Scanner PrivateKeyText = FileOperationsObj.PrivateKey(UserName[0]);
		
		while(PrivateKeyText.hasNext())
		{
			EnterPrivateKey.sendKeys(PrivateKeyText.nextLine()+"\n");
		}
				
	
		ClickSignButton.click();
	}
	
}
