package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class BrowserOperation 
{
	WebDriver driver;
	public WebDriver LaunchApplication(String browserName) throws Exception 
	{
		switch(browserName)
		{
		case "Chrome":
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\pravi\\Downloads\\Softwares\\downloads\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver(); 
			break;
		
		case "Edge":
			System.setProperty("webdriver.edge.driver", "C:\\Users\\pravi\\Downloads\\Softwares\\downloads\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		}
		
		FileOperations FileOperationsObj = new FileOperations();
		
		driver.get(FileOperationsObj.ReadPropertyFile("url"));
		return driver;
	}
	
	public String GetTitleOfBrowser()
	{
		String title = driver.getTitle();
		
		return title;
	}
}
