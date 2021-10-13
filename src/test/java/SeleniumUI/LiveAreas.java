package SeleniumUI;

import Config.MainConfig;
import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class LiveAreas extends BaseClass {

    public String liveAreasXpath = "//*[contains(text(),'Live areas')]";
    public String newLiveAreaXpath = "//button[@id = 'bb-te-live-areas-new-button']";
    public String searchLiveAreaXpath = "//div[@id = 'livearea-list']/parent::*//input[@name= 'text']";
    public String liveAreaName;
    public String goButtonXpath = "//div[@id = 'livearea-list']/parent::*//*[contains(text(),'Go ')]";
    public String addRuleXpath = "//button[@class ='add bb-btn-secondary ng-binding ng-scope']";
    public String decisionXpath ="//input[@id = 'addRuleModalInputDecision']";
    public String decisionName;
    public String messageXpath ="//input[@id = 'addRuleModalInputMessage']";
    public String messageName;
    public String calendarXpath ="//input[@id = 'bb-te-add-rule-modal-from-date-input']";
    public String dateXpath ="//button[@class= 'btn btn-default btn-sm uib-title']";
    public String yearXpath = "//button[@class = 'btn btn-default btn-sm uib-title']";
    public String previousYearsXpath = "//button[@class = 'btn btn-default btn-sm pull-left uib-left']";
    public String selectYearXpath = "//button[@class= 'btn btn-default']/parent::*//*[contains(text(),'2015')]";
    public String selectMonthXpath = "//*[contains(text(),'Jan')]";
    public String selectDateXpath = "//button[@class= 'btn btn-default btn-sm active']/parent::*//*[contains(text(),'01')]";
    public String setPriorityXpath = "//input[@id = 'bb-te-add-rule-modal-priority-input']";
    public String okButtonXpath = "//button[@class = 'add-rule-modal-footer-add ng-binding']";
    public String closeButtonXpath = "//*[contains(text(),'Close')]";
    public String levelValue;
    public String deleteRule= "//td[@class='icon-action bb-grid-cell-0-6 bb-grid-cell-callback']/parent::*//i[@class='delete']";
    public String deleteConfirm = "//div[@class='modal-footer bb-btn-group ng-scope']//*[contains(text(),'Delete')]";
    public String levelXpath = "//div[@class='type form-group ng-scope']//select";
    public String levelType;
    public String levelTypeXpath = "//select[@id = 'bb-te-live-area-editor-options-select']";

    public void LiveArea() throws InterruptedException {

        try {
            driver.findElement(By.xpath(liveAreasXpath)).click();
            Thread.sleep(3000);
            reporter.reportLogPassWithScreenshot("Live Areas Page is Displayed");
            SearchLiveArea();
            AddRule();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void NewLiveArea() throws InterruptedException {

        driver.findElement(By.xpath(newLiveAreaXpath)).click();
        Thread.sleep(2000);
        reporter.reportLogPassWithScreenshot("New Live Area is Created");

    }
    public void SearchLiveArea() throws InterruptedException {

        if(keepRefer.get("INVOICE").equalsIgnoreCase("Paper"))
        {
            liveAreaName = keepRefer.get("LIVE_AREA_PAPER");
        }
        else
        {
            liveAreaName = keepRefer.get("LIVE_AREA_DIGITAL");
        }

        driver.findElement(By.xpath(searchLiveAreaXpath)).sendKeys(liveAreaName);
        Thread.sleep(2000);

        driver.findElement(By.xpath(goButtonXpath)).click();
        Thread.sleep(2000);
        reporter.reportLogPassWithScreenshot("Selected Live Area is Displayed");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[contains(text(),'"+ liveAreaName +"')]")).click();
        Thread.sleep(1000);

    }
    public void AddRule() throws InterruptedException {
        //delete Rule if exists
       /* Boolean deleteButtonExists = driver.findElements(By.xpath(deleteRule)).size() != 0;
        if (deleteButtonExists.equals(true)) {
            driver.findElement(By.xpath(deleteRule)).click();
            driver.findElement(By.xpath(deleteConfirm)).click();
            Thread.sleep(2000);
        }*/
        WebDriverWait wait = new WebDriverWait(driver, 15);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addRuleXpath)));

        reporter.reportLogPassWithScreenshot("Selected Live Area is Ready to Edit");
        Thread.sleep(1000);
        levelType = keepRefer.get("LEVEL_TYPE");
        //    Select type = new Select(driver.findElement(By.xpath(levelTypeXpath)));
        //   type.selectByVisibleText(levelType);
        Thread.sleep(1000);



//        levelValue = keepRefer.get("FACT_LEVEL");
//        WebElement factLevel = driver.findElement(By.xpath(levelXpath));
//        Select level = new Select(factLevel);
//        level.selectByVisibleText(levelValue);
//                Thread.sleep(1000);


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scroll(0,800)");
        Thread.sleep(1000);
        // Add Rule
        driver.findElement(By.xpath(addRuleXpath)).click();
        Thread.sleep(1000);

        decisionName = keepRefer.get("MESSAGE_NAME");

        WebElement select = driver.findElement(By.xpath(decisionXpath));
        select.sendKeys(decisionName);
        Thread.sleep(2000);
        driver.findElement(By.xpath(decisionXpath)).click();
        driver.findElement(By.xpath("//input[@id = 'addRuleModalInputDecision']//..//li")).click();

        Thread.sleep(1000);

        messageName = keepRefer.get("MESSAGE_NAME");
        WebElement select1 = driver.findElement(By.xpath(messageXpath));
        select1.sendKeys(messageName);
        Thread.sleep(2000);

        driver.findElement(By.xpath(messageXpath)).click();
        driver.findElement(By.xpath("//input[@id = 'addRuleModalInputMessage']//..//li")).click();

        Thread.sleep(1000);

//Set Active From Date
        driver.findElement(By.xpath(calendarXpath)).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath(dateXpath)).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(yearXpath)).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(previousYearsXpath)).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(selectYearXpath)).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(selectMonthXpath)).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(selectDateXpath)).click();
        Thread.sleep(1000);
//Set Priority

        WebElement priority = driver.findElement(By.xpath(setPriorityXpath));

        priority.sendKeys(Keys.NUMPAD1);
        priority.sendKeys(Keys.TAB);

        Thread.sleep(1000);

        reporter.reportLogPassWithScreenshot("Add Rule Page is Displayed");

        driver.findElement(By.xpath(okButtonXpath)).click();
        Thread.sleep(1000);

        JavascriptExecutor jsDown = (JavascriptExecutor) driver;
        jsDown.executeScript("scroll(0,800)");
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("New Rule added successfully to Live Area");

        JavascriptExecutor jsUp = (JavascriptExecutor) driver;
        jsUp.executeScript("scroll(0,-800)");
        Thread.sleep(3000);
     //   if (levelValue.equalsIgnoreCase("Account")) {
       //     driver.findElement(By.xpath("//button[@id='bb-te-live-area-editor-top-save-button']")).click();
       // }
        //else {
        driver.findElement(By.xpath(closeButtonXpath)).click();
        Thread.sleep(2000);
          //   }
    }
}