package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class sampleTestRun {
	
	
	public Scanner readFileText() throws FileNotFoundException
	{

		File fsPath = new File("WalletUser/DilipKulkarni14/Private_Key.txt");	
		
		Scanner ScanValueOf = new Scanner(fsPath);		
		
		return ScanValueOf;
	}
	

	public static void main(String[] args) throws FileNotFoundException  
	{
		// TODO Auto-generated method stub
		
		sampleTestRun sampleTestRunObj = new sampleTestRun();
		
		Scanner getText = sampleTestRunObj.readFileText();
		
		System.out.println("String is printed \n now this should be on next line.");
		
	}

}
