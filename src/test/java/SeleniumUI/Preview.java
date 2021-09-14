package SeleniumUI;

import Config.EnvironmentConf;
import Config.MainConfig;
import Utils.FileManager;
import base.BaseClass;
import com.testautomationguru.utility.PDFUtil;
import javafx.scene.control.RadioButton;

import jdk.nashorn.internal.runtime.Context;
import net.bytebuddy.implementation.Implementation;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tools.ant.util.Base64Converter;
import org.codehaus.groovy.util.URLStreams;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Preview extends BaseClass
{
    public void launchPreview() throws InterruptedException {
        //click preview button

        driver.findElement(By.xpath("//*[contains(text(),'Preview')]")).click();
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("Preview Page is Displayed");

    }

    public void previewPDF(String customerSample, String dataSample,String lang) throws IOException, InterruptedException {
        FileManager.deleteDirectory(MainConfig.properties.getProperty("PDF_PATH"));

        Select documentType = new Select(driver.findElement(By.xpath("//*[@ng-model='vm.form.documentType']")));
        documentType.selectByValue("string:bill");
        Thread.sleep(2000);
        if (keepRefer.get("INVOICE_TYPE").equalsIgnoreCase("Paper"))
        {
            driver.findElement(By.id("previewModalRadioPrint")).click();

            //select Template
            driver.findElement(By.id("previewModalInputTemplate")).clear();
            driver.findElement(By.id("previewModalInputTemplate")).sendKeys("root ");
            Thread.sleep(1000);
            driver.findElement(By.id("previewModalInputTemplate")).click();
            driver.findElement(By.xpath("//*[@id='previewModalInputTemplate']//..//li[2]")).click();
        }
        else if(keepRefer.get("INVOICE").equalsIgnoreCase("Digital"))
        {
            driver.findElement(By.id("previewModalRadioWeb")).click();
        }

        Thread.sleep(1000);
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
            Thread.sleep(2000);
            driver.findElement(By.id("previewModalInputCustomer")).click();
            driver.findElement(By.xpath("//*[@id='previewModalInputCustomer']//..//li")).click();

        Thread.sleep(2000);

        WebElement PDFButton = driver.findElement(By.xpath("//button[contains(text(),'Generate PDF')]"));

        if (PDFButton.isDisplayed())
        {
            PDFButton.click();
            reporter.reportLogPassWithScreenshot("Generate PDF button clicked.");
        }
        else
        {
            reporter.reportLogFailWithScreenshot("Generate PDF button not displayed");
        }
        Thread.sleep(5000);

        FileManager.copyAllFiles(MainConfig.properties.getProperty("PDF_PATH"), MainConfig.properties.getProperty("Bill_PATH"),keepRefer.get("TestCaseID")+"_"+lang+".pdf");


    }

    public String validatePDF(String message,String lang) throws InterruptedException, IOException
    {
        String status = "Fail";
        PDFUtil pdfUtil = new PDFUtil();

        Thread.sleep(3000);
       File pdfFileName = new File(MainConfig.properties.getProperty("Bill_PATH") + keepRefer.get("TestCaseID")+"_"+lang+".pdf");

        HashMap<String,Object> chromePrefs = new HashMap<>();
        chromePrefs.put("plugins.always_open_pdf_externally",false);

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("prefs",chromePrefs);
        options.setAcceptInsecureCerts(true);
        options.addArguments("--start-maximized");

        driver.quit();
        driver = new ChromeDriver(options);
        reporter.setDriver(driver);

       driver.get("file://"+pdfFileName.getAbsolutePath());

       Thread.sleep(5000);

      int totalPages = pdfUtil.getPageCount(pdfFileName.getAbsolutePath());

            try {
                String content = pdfUtil.getText(pdfFileName.getAbsolutePath());
                String newmsg = message;
                if (newmsg.contains("{{")) {

                    String[] variableArr = StringUtils.substringsBetween(message, "{{", "}}");

                    for (String var1 : variableArr) {
                        newmsg = newmsg.replace("{{" + var1 + "}}", "");
                    }
                }

                if(lang.contains("NEG"))
                {
                    if (!content.contains(newmsg)) {
                        reporter.reportLogPassWithScreenshot("Message not present in PDF Bill");
                        status = "Pass";
                    } else {
                        reporter.reportLogFailWithScreenshot("Message Present in PDF bill");
                        status = "Fail";
                    }
                }
                else {
                    if (content.contains(newmsg)) {
                        reporter.reportLogPassWithScreenshot("Message present in PDF Bill");
                        status = "Pass";
                    } else {
                        reporter.reportLogFailWithScreenshot("Message Not Present in PDF bill");
                        status = "Fail";
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        return status;

    }
}
