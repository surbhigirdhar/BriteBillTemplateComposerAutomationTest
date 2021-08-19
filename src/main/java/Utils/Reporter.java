package Utils;

import org.openqa.selenium.WebDriver;

/**
 * An interface to report test artifacts on a standard reporting style
 * @author rajesh.varalli1
 *
 */
public interface Reporter {
	
	/**
	 * Log a test step to the report
	 * @param stepName Test step name or description
	 * @author rajesh.varalli1
	 */
	public void reportLog(String stepName);
	
	/**
	 * Log a test step along with the browser screenshot to the report
	 * @param stepName Test step name or description
	 * @author rajesh.varalli1
	 */
	public void reportLogWithScreenshot(String stepName);
	
	/**
	 * Log a test step as PASS to the report
	 * @param stepName Test step name or description
	 * @author rajesh.varalli1
	 */
	public void reportLogPass(String stepName);
	
	/**
	 * Log a test step as PASS along with the browser screenshot to the report
	 * @param stepName Test step name or description
	 * @author rajesh.varalli1
	 */
	public void reportLogPassWithScreenshot(String stepName);

	/**
	 * Log a test step as FAIL to the report
	 * @param stepName Test step name or description
	 * @author rajesh.varalli1
	 */
	public void reportLogFail(String stepName);
	
	/**
	 * Log a test step as PASS along with the browser screenshot to the report
	 * @param stepName Test step name or description
	 * @author rajesh.varalli1
	 */
	public void reportLogFailWithScreenshot(String stepName);

	/**
	 * Evaluates a boolean condition, not throwing an exception in case the condition fails, enabling the execution to still continue further.
	 * Also based on the boolean value logs either a PASS or FAIL test step to the report.
	 * @param condition that needs to be evaluated returning a boolean value
	 * @param passMsg Message to be logged if the evaluated condition is true
	 * @param failMsg Message to be logged if the evaluated condition is false
	 * @author rajesh.varalli1
	 */
	public void softAssert(boolean condition, String passMsg, String failMsg);
	
	/**
	 * Evaluates a boolean condition, throwing an exception in case the condition fails, thereby halting the current test execution.
	 * Also based on the boolean value logs either a PASS or FAIL test step to the report.
	 * @param condition that needs to be evaluated returning a boolean value
	 * @param passMsg Message to be logged if the evaluated condition is true
	 * @param failMsg Message to be logged if the evaluated condition is false
	 * @author rajesh.varalli1
	 */
	public void hardAssert(boolean condition, String passMsg, String failMsg);

	/**
	 * Compares two strings for assertion, not throwing exception on failure enabling the execution to still continue further.
	 * Also based on the boolean value comparison logs either a PASS or FAIL test step to the report.
	 * @param actual String value captured during runtime
	 * @param expected String value that is expected
	 * @param passMsg Message to be logged if the evaluated condition is true
	 * @param failMsg Message to be logged if the evaluated condition is false
	 * @author rajesh.varalli1
	 */
	public void softAssert(String actual, String expected, String passMsg, String failMsg);
	
	public void setDriver(WebDriver driver);
}