package Utils;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import extentreport.ExtentManager;

/**
 * This class contains methods related to the Screen capture utilities like screenshot images, screencast videos
 * @author rajesh.varalli1
 *
 */
public class ScreenCapture {

	/**
	 * Takes the screenshot and saves it to a file in 'png' format
	 * @param driver web driver
	 * @return the path of the screenshot as String
	 */
	public static String getScreenShot(WebDriver driver,String TestName,String StepName)
	{
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		String  resultsDir=ExtentManager.absolutePathToReport;
		String strLocalTime = ZonedDateTime.now(ZoneId.of("America/Montreal")).format(DateTimeFormatter.ofPattern("YYYY-MM-dd-HH-mm-ss"));
      
		String fileName = "ScreenShot_" + StepName + "_" + strLocalTime +"_"+Thread.currentThread().getId()+ ".png";
      
		String screenShotFilePath = resultsDir + "/" + TestName+"/" + fileName ;
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(screenShotFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "./" +TestName + "/"+fileName;
	}

}
