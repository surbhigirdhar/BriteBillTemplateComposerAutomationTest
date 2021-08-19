package SeleniumUI;

import Config.EnvironmentConf;
import Config.MainConfig;
import Utils.FileManager;
import base.BaseClass;
import com.testautomationguru.utility.PDFUtil;
import javafx.scene.control.RadioButton;

import jdk.nashorn.internal.runtime.Context;
import net.bytebuddy.implementation.Implementation;
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
    public String previewCBU() throws InterruptedException, IOException
    {
        String status = "Fail";
        PDFUtil pdfUtil = new PDFUtil();


        //click preview button
        driver.findElement(By.xpath("//*[contains(text(),'Preview')]")).click();
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("Preview Page is Displayed");

        Select documentType = new Select(driver.findElement(By.xpath("//*[@ng-model='vm.form.documentType']")));
        documentType.selectByValue("string:bill");

        if (keepRefer.get("INVOICE_TYPE").equalsIgnoreCase("Paper"))
        {
            driver.findElement(By.id("previewModalRadioPrint")).click();

            //select Template
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
        driver.findElement(By.id("previewModalInputDocument")).sendKeys("MaestroSample");
        Thread.sleep(1000);
        driver.findElement(By.id("previewModalInputDocument")).click();
        driver.findElement(By.xpath("//*[@id='previewModalInputDocument']//..//li")).click();

        Thread.sleep(1000);

        //select Customer Sample
        driver.findElement(By.id("previewModalInputCustomer")).sendKeys("Customer3_CBU_Maestro");
        Thread.sleep(1000);
        driver.findElement(By.id("previewModalInputCustomer")).click();
        driver.findElement(By.xpath("//*[@id='previewModalInputCustomer']//..//li")).click();

        Thread.sleep(1000);

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

       File pdfFileName = FileManager.getPDFFile();

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

            if(content.contains(keepRefer.get("MESSAGE_TEXT_EN")))
            {
                reporter.reportLogPassWithScreenshot("Message present in PDF Bill");
                status = "Pass";
            }
            else
            {
                reporter.reportLogFailWithScreenshot("Message Not Present in PDF bill");
                status = "Fail";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return status;

    }
}
