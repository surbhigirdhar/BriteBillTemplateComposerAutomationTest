package SeleniumUI;

import Config.EnvironmentConf;
import Utils.Environment;
import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class BBLaunch extends BaseClass {



      public void launchBriteBill() throws InterruptedException
      {

        driver.get(EnvironmentConf.URL);

        driver.navigate().refresh();
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("Login to BriteBill Successful");
    }


}

