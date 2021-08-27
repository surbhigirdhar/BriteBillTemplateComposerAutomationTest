package extentreport;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import Utils.Reporter;
import Utils.ScreenCapture;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
 
public class ExtentTestManager implements Reporter{
    @SuppressWarnings("rawtypes")
	static Map extentTestMap = new HashMap();
    static ExtentReports extent = ExtentManager.getReporter();
    private static final ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
    public static HashMap<Long, Boolean> failFlags = new HashMap<Long, Boolean>();
    
    /**
     * Constructor for the class ExtentTestManager
     */
    public ExtentTestManager() {
    	
    }
    
    /**
     * Constructor for the class ExtentTestManager for assigning the WebDriver instance
     * @param driver WebDriver
     */
    public ExtentTestManager(WebDriver driver) {
        webDriverThreadLocal.set(driver);
    }
 
    @Override
    public void setDriver(WebDriver driver)
    {
    	webDriverThreadLocal.set(driver);
    }
    
    public static synchronized ExtentTest getTest() {
        return (ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }
 
    public static synchronized void endTest() {    	
        extent.endTest((ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }
 
    @SuppressWarnings("unchecked")
	public static synchronized ExtentTest startTest(String testName, String desc) {    	
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        failFlags.put(Thread.currentThread().getId(), false);
        return test;
    }
    
    @Override
    public void reportLog(String stepName) {
    	getTest().log(LogStatus.INFO, stepName, "");
    }
    
    @Override
    public void reportLogWithScreenshot(String stepName) {
    	try {
            String testname = getTest().getTest().getName();
    		getTest().log(LogStatus.INFO, stepName, getTest().addScreenCapture(ScreenCapture.getScreenShot(getDriver(),testname,stepName)));
    	}catch (UnhandledAlertException  alertExcpt) {
    		getTest().log(LogStatus.INFO, alertExcpt.toString());
    		alertExcpt.printStackTrace();
    	}	
    }
    
    @Override
    public void reportLogPass(String stepName) {
    	getTest().log(LogStatus.PASS, stepName, "");
        failFlags.replace(Thread.currentThread().getId(), false);
    }
    
	@Override
    public void reportLogPassWithScreenshot(String stepName) {
        String testname = getTest().getTest().getName();
    	getTest().log(LogStatus.PASS, stepName, getTest().addScreenCapture(ScreenCapture.getScreenShot(getDriver(),testname,stepName)));
    }

    @Override
    public void reportLogFail(String stepName) {
    	getTest().log(LogStatus.ERROR, stepName, "");
    	failFlags.replace(Thread.currentThread().getId(), true);
    }
    
	@Override
    public void reportLogFailWithScreenshot(String stepName) {
        String testname = getTest().getTest().getName();
    	getTest().log(LogStatus.ERROR, stepName, getTest().addScreenCapture(ScreenCapture.getScreenShot(getDriver(),testname,stepName)));
    	failFlags.replace(Thread.currentThread().getId(), true);
    }

    @Override
    public void softAssert(boolean condition, String passMsg, String failMsg) {
		if(condition) {
			reportLogPass(passMsg);
		} else {
			reportLogFailWithScreenshot("Soft assert failed due to exception: - "+failMsg);
		}
	}
    
    @Override
    public void hardAssert(boolean condition, String passMsg, String failMsg) {
		if(condition) {
			reportLogPass(passMsg);
		} else {
			throw new WebDriverException("Hard assert failed due to exception: -"+failMsg);
		}
	}

    @Override
    public void softAssert(String actual, String expected, String passMsg, String failMsg) {
        if (actual.replaceAll(" ", "").equalsIgnoreCase(expected.replaceAll(" ", ""))) {
            reportLogPass(passMsg);
        } else {
            reportLogFail("Soft assert failed due to exception: - " + failMsg);
        }
    }

    /**
     * Returns the value of the flag set for indicating soft asserts
     * @return true or false
     * @author rajesh.varalli1
     */
    public static boolean getFailFlag() {
    	return failFlags.get(Thread.currentThread().getId());
    }

    /**
     * This method returns the WebDriver instance from the ThreadLocal
     */
    private WebDriver getDriver(){
        return webDriverThreadLocal.get();
    }
}