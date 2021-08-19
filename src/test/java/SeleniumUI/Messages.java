package SeleniumUI;

import base.BaseClass;
import io.cucumber.java.eo.Se;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class Messages extends BaseClass {

    public String messagesXpath = "//*[contains(text(),'Messages')]";
    public String newMessageXpath = "//button[@id = 'bb-te-messages-list-new-upload-button']";
    public String messageNameXpath = "//input[@id = 'bb-te-message-editor-name-input']";
    public String messageName;
    public String languageXpath ="//select[@id = 'bb-te-message-editor-locale-new-select']";
    public String messageProperty;
    public String boldXpath = "//button[@title= 'Bold']";
    public String italicXpath = "//button[@title= 'Italic']";
    public String messageTextXpath = "//body[@id ='tinymce']";
    public String messageText;
    public String messageVariable;
    public String messageVariableXpath = "//*[contains(text(),'Message variables')]";
    public String searchVariableXpath = "//input[@class = 'tox-textfield']";
    public String insertButtonXpath = "//button[@title = 'Insert']";
    public String saveButtonXpath = "//button[@class = 'save ng-binding ng-scope']";
    public String fontXpath = "//*[contains(text(),'Paragraph')]";
    public String fontType;

    public void Message() {
        try {
            driver.findElement(By.xpath(messagesXpath)).click();
            Thread.sleep(1000);
             reporter.reportLogPassWithScreenshot("Messages Page is Displayed");
             }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void NewMessage() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(newMessageXpath)).click();
        Thread.sleep(1000);

        //Insert Name of the Message

        messageName = keepRefer.get("MESSAGE_NAME");
        driver.findElement(By.xpath(messageNameXpath)).sendKeys(messageName);
        Thread.sleep(1000);

//Select Language

               WebElement element = driver.findElement(By.xpath(languageXpath));
               Select lang = new Select(element);
               lang.selectByVisibleText(keepRefer.get("LANGUAGE"));
                Thread.sleep(1000);

        driver.switchTo().frame(0);
        driver.findElement(By.xpath(messageTextXpath)).clear();
        driver.switchTo().parentFrame();

//Select message property (Bold/Italic)
        messageProperty = keepRefer.get("MESSAGE_PROPERTY");
        if(messageProperty.equals("BOLD"))

        {
            driver.findElement(By.xpath(boldXpath)).click();
            Thread.sleep(2000);
        }
        else if(messageProperty.equals("ITALIC"))

        {
            driver.findElement(By.xpath(italicXpath)).click();
            Thread.sleep(2000);
        }

//Type Message in the Textbox
        messageText = keepRefer.get("MESSAGE_TEXT");
        Thread.sleep(1000);
        driver.switchTo().frame(0);
        driver.findElement(By.xpath(messageTextXpath)).sendKeys(messageText);
        Thread.sleep(5000);

//Inserting the message variable
        driver.switchTo().parentFrame();
        messageVariable = keepRefer.get("MESSAGE_VARIABLE");
        driver.findElement(By.xpath(messageVariableXpath)).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(searchVariableXpath)).sendKeys(messageVariable);
        Thread.sleep(2000);
        reporter.reportLogPassWithScreenshot("Message Variable Search Page is Displayed");
        driver.findElement(By.xpath(insertButtonXpath)).click();
        Thread.sleep(2000);


        fontType = keepRefer.get("MESSAGE_STYLE");

        driver.findElement(By.xpath(fontXpath)).click();
        driver.findElement(By.xpath("//*[contains(text(),'"+fontType+"')]")).click();
        Thread.sleep(2000);
        reporter.reportLogPassWithScreenshot("Message details before saving");
        driver.findElement(By.xpath(saveButtonXpath)).click();
        reporter.reportLogPassWithScreenshot("New Message is Created successfully");
        Thread.sleep(2000);

    }
}

