package SeleniumUI;


import Config.MainConfig;
import base.BaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;


public class SeleniumConfig extends BaseClass {


	private static final ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
	private static final ThreadLocal<Actions> actionsThreadLocal = new ThreadLocal<>();

//	private WebDriver driver;

/*	public static WebDriver getChormeDriver()
	{

		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/Jars/chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
		return driver;
		
	}

 */

	public void chromeInit() throws Exception {

		WebDriverManager.chromedriver().setup();

		HashMap<String,Object> chromePrefs = new HashMap<>();
		chromePrefs.put("plugins.always_open_pdf_externally",true);
		chromePrefs.put("download.default_directory",MainConfig.properties.getProperty("PDF_PATH"));
		chromePrefs.put("download.directory_upgrade",true);

		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--start-maximized");
		options.setExperimentalOption("prefs",chromePrefs);

		options.addArguments("disable-infobars");

		setDriver(new ChromeDriver(options));
	}

	public void setDriver(WebDriver driver1) {
		driver = driver1;
	}

	public WebDriver getDriver()
	{
		return driver;
	}

}
