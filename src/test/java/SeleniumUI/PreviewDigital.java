package SeleniumUI;

import Config.MainConfig;
import Utils.FileManager;
import base.BaseClass;
import com.testautomationguru.utility.PDFUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PreviewDigital extends BaseClass {


    public void previewDigitalBill(String customerSample, String dataSample, String lang) throws IOException, InterruptedException {
        Thread.sleep(2000);
        Select documentType = new Select(driver.findElement(By.xpath("//*[@ng-model='vm.form.documentType']")));
        documentType.selectByValue("string:bill");
        Thread.sleep(2000);

        driver.findElement(By.id("previewModalRadioWeb")).click();

        Thread.sleep(2000);
        //select data sample
        driver.findElement(By.id("previewModalInputDocument")).clear();
        driver.findElement(By.id("previewModalInputDocument")).sendKeys(dataSample);
        Thread.sleep(1000);
        driver.findElement(By.id("previewModalInputDocument")).click();
        driver.findElement(By.xpath("//*[@id='previewModalInputDocument']//..//li")).click();
        Thread.sleep(1000);

        //select Customer Sample
        driver.findElement(By.id("previewModalInputCustomer")).clear();
        driver.findElement(By.id("previewModalInputCustomer")).sendKeys(customerSample);
        Thread.sleep(1000);
        driver.findElement(By.id("previewModalInputCustomer")).click();
        driver.findElement(By.xpath("//*[@id='previewModalInputCustomer']//..//li")).click();
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("Preview Before Generate");
        driver.findElement(By.xpath("//button[@ng-click='vm.getHTML()']")).click();
        Thread.sleep(3000);

    }

    public String validateDigitalBill(String message,String lang) throws InterruptedException, IOException
    {

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        Thread.sleep(1000);
        WebDriver bill = driver.switchTo().window(tabs.get(1));
        Thread.sleep(1000);
        String newmsg = message;

        String status = "Fail";
        if (newmsg.contains("{{")) {

            String[] variableArr = StringUtils.substringsBetween(message, "{{", "}}");

            for (String var1 : variableArr) {
                newmsg = newmsg.replace("{{" + var1 + "}}", "#");
            }
        }

        /* To validate Digital Live Area- "digital-911-fee" */
        driver.switchTo().frame(0);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scroll(0,800)");
        Thread.sleep(1000);

        WebElement viewMessage = null;

        if (keepRefer.get("BUSINESS_UNIT").contentEquals("cbu")) {

            viewMessage = driver.findElement(By.xpath("//span[@class='messages-text text-link view-messages ng-binding']"));
        }
        else
        {
            viewMessage = driver.findElement(By.xpath("(//a[@class='details-link ng-binding ng-isolate-scope'])[1]"));
        }

        if (viewMessage.isDisplayed()) {
            viewMessage.click();
            Thread.sleep(2000);
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            driver.switchTo().frame(0);
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(2000);
            String[] msgArr = newmsg.split("'");

            for (String msg : msgArr) {
                String[] varArr = msg.split("#");
                for (String validatemsg : varArr) {
                    List<WebElement> list = null;

                    if (keepRefer.get("BUSINESS_UNIT").contentEquals("cbu")) {
                        list = driver.findElements(By.xpath("//div[@class='margin-bottom-15']/..//*[contains(text(),'" + validatemsg.trim() + "')]"));
                    }
                    else {
                        list = driver.findElements(By.xpath("//section[@ng-bind-html='message']//*[contains(text(),'" + validatemsg.trim() + "')]"));
                    }
                    if (lang.contains("NEG")) {

                        if (list.size()==0) {

                            reporter.reportLogPassWithScreenshot("Message Not present in Digital Bill");
                            status = "Pass";
                        } else {
                            reporter.reportLogFailWithScreenshot("Message Present in Digital bill");
                            status = "Fail";
                            break;
                        }
                    } else {

                        if (list.size()>0) {

                            reporter.reportLogPassWithScreenshot("Message present in Digital Bill");
                            status = "Pass";
                        } else {
                            reporter.reportLogFailWithScreenshot("Message Not Present in Digital bill");
                            status = "Fail";
                            break;
                        }
                    }
                }
            }
        }
        else if (lang.contains("NEG")) {
            reporter.reportLogPassWithScreenshot("Message Not present in Digital Bill");
            status = "Pass";
        }
        else
            {
                reporter.reportLogFailWithScreenshot("View Message is not available");
                status = "Fail";
            }


        /* For other Digital Live Area validations
         driver.switchTo().frame(0);
       String[] msgArr =  newmsg.split("'");
       for (String msg : msgArr) {
           List<WebElement> list = driver.findElements(By.xpath("//p[contains(text(),'" + msg + "')]"));


           Thread.sleep(1000);
           if (lang.contains("NEG")) {
               //if (!content.contains(newmsg))
               if (list.size() == 0) {
                   driver.switchTo().frame(0);
                   JavascriptExecutor js = (JavascriptExecutor) driver;
                   js.executeScript("scroll(0,800)");
                   Thread.sleep(1000);
                   reporter.reportLogPassWithScreenshot("Message not present in Digital Bill1");
                   status = "Pass";
               } else {
                   reporter.reportLogFailWithScreenshot("Message Present in Digital bill");
                   status = "Fail";
                   break;
               }
           } else {

               //if (content.contains(newmsg))
               if (list.size() > 0) {

                   driver.switchTo().frame(0);
                   JavascriptExecutor js1 = (JavascriptExecutor) driver;
                   js1.executeScript("scroll(0,800)");
                   Thread.sleep(1000);
                   reporter.reportLogPassWithScreenshot("Message present in Digital Bill1");
                   status = "Pass";
               } else {
                   reporter.reportLogFailWithScreenshot("Message Not Present in Digital bill");
                   status = "Fail";
                   break;
               }
           }
       }
        */

                bill.close();
                driver.switchTo().window(tabs.get(0));


        return status;

    }

}
