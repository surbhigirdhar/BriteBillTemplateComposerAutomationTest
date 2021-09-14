package SeleniumUI;

import Utils.CommonUtils;
import Utils.FileManager;
import Utils.FormFiller;
import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class DataSample extends BaseClass
{


    String searchTxtBox = "//div[(@ui-view='document')]//..//input[(@placeholder='Name')]";
    String buttonGo = "//div[(@ui-view='document')]//..//button[contains(text(),'Go')]";
    String saveButton = "//button[contains(text(),'Save')]";
    String dataSampleTxtarea = "//*[(@class='ace_text-input')]";

    public void OpenDataSamplePage() throws InterruptedException
    {
        driver.findElement(By.xpath("//*[contains(text(),'Data samples')]")).click();
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("Data Sample Page is Displayed");
    }
    public void CreateNewDataSample()
    {

    }

    public void SearchDataSample() throws InterruptedException
    {
        runForDataSample(keepRefer.get("DATA_SAMPLE_EN"),"ENG");
        Thread.sleep(1000);
        runForDataSample(keepRefer.get("DATA_SAMPLE_FR"),"FR");
        Thread.sleep(2000);

    }

    private void runForDataSample(String dsName, String lang) throws InterruptedException
    {
        String factsTags = "";
        String dataSampleData = "";

        driver.findElement(By.xpath(searchTxtBox)).clear();
        driver.findElement(By.xpath(searchTxtBox)).sendKeys(dsName);
        Thread.sleep(1000);
        driver.findElement(By.xpath(buttonGo)).click();
        Thread.sleep(1000);
        WebElement searchResult = driver.findElement(By.xpath("//span[contains(text(),'"+dsName+"')]"));

        if(searchResult.isDisplayed()) {
            searchResult.click();
            reporter.reportLogPassWithScreenshot("Click on search data sample successfully");
        }
        else
        {
            reporter.reportLogFailWithScreenshot("Search Data sample not found");
        }
        Thread.sleep(1000);

        //if page load
        if(driver.findElement(By.xpath(saveButton)).isDisplayed())
        {
            //clear all text in text area
            driver.findElement(By.xpath(dataSampleTxtarea)).sendKeys(Keys.CONTROL+"a");
            driver.findElement(By.xpath(dataSampleTxtarea)).sendKeys(Keys.DELETE);

            //append facts tags
            for (int i = 1; i < 5; i++)
            {
                if (keepRefer.containsKey("DECISION" + i) ) {
                    if (!keepRefer.get("DECISION" + i).isEmpty()) {
                        //String decisionValue = getDecisionValue(keepRefer.get("DATASAMPLE_DECISION_VALUE" + i), keepRefer.get("DECISION_OPERATION"+i));
                        String decisionValue = keepRefer.get("DATASAMPLE_DECISION_VALUE" + i);
                        factsTags = factsTags + "<FactValue>\n" +
                                "      <name>" + keepRefer.get("DECISION" + i) + "</name>\n" +
                                "      <valueString>" + decisionValue + "</valueString>\n" +
                                "    </FactValue>";

                    }
                }
            }
            //make template file name
            String fileName = "DS_" + keepRefer.get("FACT_LEVEL") + "_" + keepRefer.get("BUSINESS_UNIT").toUpperCase() + "_"+lang;
            dataSampleData = FileManager.editDataSample(fileName, factsTags);

            StringSelection st = new StringSelection(dataSampleData);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(st,null);


            WebElement txtarea =  driver.findElement(By.xpath(dataSampleTxtarea));

            txtarea.sendKeys(Keys.CONTROL+"v");
            Thread.sleep(5000);
            driver.findElement(By.xpath(saveButton)).click();

            reporter.reportLogPassWithScreenshot("Data Sample updated");
        }

        else {
            reporter.reportLogFailWithScreenshot("Data Sample page not loaded");
            return;
        }
    }

    private String getDecisionValue(String decisionValue, String operator)
    {
        String dVal = "";

        switch(operator)
        {
            case ">":
                dVal = String.valueOf(Integer.parseInt(decisionValue)+1);
                break;
            case "<":
                dVal = String.valueOf(Integer.parseInt(decisionValue)-1);
                break;
            case "not equal to":
                dVal = decisionValue + FormFiller.generateRandomName();
                break;
            default :
                dVal = decisionValue;
        }

        return dVal;
    }

    public void updateDataSample()
    {

    }
}
