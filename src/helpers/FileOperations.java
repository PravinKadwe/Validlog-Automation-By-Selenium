package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

public class FileOperations 
{
	public String ReadPropertyFile(String key) throws Exception 
	{
		File file = new File(".//config.properties");
		FileInputStream fs = new FileInputStream(file);
		
		Properties prop = new Properties();
		prop.load(fs);
		String keyValue = prop.getProperty(key);
		return keyValue;
	}
	
	public void WritePropertyFile(String data, String fileName, String userName) throws Exception
	{
		File FilePath = new File(".//WalletUser/"+userName);
		
		FilePath.mkdir();
		
		FileOutputStream insertData = new FileOutputStream(FilePath+"/"+fileName+".txt");
		
		PrintWriter pw = new PrintWriter(insertData);
		
		pw.println(data);
		
		pw.close();
	}
	
	
	
	public Scanner PassphraseKey(String UserName) throws FileNotFoundException 
	{
		File fsPath = new File("WalletUser/"+UserName+"/Passphrase.txt");
		
		Scanner PassphrasePath = new Scanner(fsPath);
		
		return PassphrasePath;
	}
	
	public Scanner PrivateKey(String UserName) throws FileNotFoundException 
	{
		File filePath = new File("WalletUser/"+UserName+"/Private_Key.txt");
		
		Scanner PrivateKeyPath = new Scanner(filePath);
		
		return PrivateKeyPath;
	}
	
}
