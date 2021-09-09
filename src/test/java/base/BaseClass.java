package base;


import Config.EnvironmentConf;
import SeleniumUI.SeleniumConfig;
import Utils.FileManager;
import extentreport.ExtentManager;
import extentreport.ExtentTestManager;
import Utils.CSVHandler;
import Utils.CommonUtils;
import Utils.Reporter;
import com.relevantcodes.extentreports.ExtentTest;

import Config.MainConfig;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BaseClass {

	// list of variables
	public ExtentTest test;
	protected FileWriter fileWriter;
	protected static Reporter reporter;
	public static HashMap<Long, PrintStream> printStreams = new HashMap<Long, PrintStream>();
	protected static HashMap<Long, String> logFiles = new HashMap<Long, String>();

	public LinkedHashMap<String, HashMap<String, String>> ScenarioList=  new LinkedHashMap<String, HashMap<String, String>>();
	public LinkedHashMap<String, HashMap<String, String>> allCommandsList=  new LinkedHashMap<String, HashMap<String, String>>();
	
	public static HashMap<String, String> keepRefer;
	
	public CommonUtils commonUtils;
	public static WebDriver driver;
	public SeleniumConfig seleniumConfig;

	@BeforeMethod (alwaysRun=true)
	public void beforeTest() throws IOException {
		new MainConfig();

		MainConfig.properties.setProperty("PDF_PATH",System.getProperty("user.dir")+MainConfig.properties.getProperty("PDF_PATH"));
		MainConfig.properties.setProperty("Bill_PATH",System.getProperty("user.dir") + "//Bills//");

		reporter = new ExtentTestManager();
		commonUtils = new CommonUtils();

		keepRefer=  new HashMap<String, String>();

		seleniumConfig = new SeleniumConfig();
		keepRefer.clear();

		try {
			seleniumConfig.chromeInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver = seleniumConfig.getDriver();
		reporter.setDriver(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void afterTest() 
	{
		driver.close();
		driver.quit();

		String  resultsDir= ExtentManager.absolutePathToReport;
		try {
			FileManager.copyFile( MainConfig.properties.getProperty("Bill_PATH"),resultsDir,keepRefer.get("TestCaseID")+"_EN.pdf");
			FileManager.copyFile( MainConfig.properties.getProperty("Bill_PATH"),resultsDir,keepRefer.get("TestCaseID")+"_FR.pdf");
			FileManager.copyFile( MainConfig.properties.getProperty("Bill_PATH"),resultsDir,keepRefer.get("TestCaseID")+"_NEG.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
}