package SeleniumUI;

import base.BaseClass;
import io.cucumber.java.eo.Se;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class Messages extends BaseClass {

    public String messagesXpath = "//*[contains(text(),'Messages')]";
    public String newMessageXpath = "//button[@id = 'bb-te-messages-list-new-upload-button']";
    public String messageNameXpath = "//input[@id = 'bb-te-message-editor-name-input']";
    public String messageName;
    public String languageXpath ="//select[@id = 'bb-te-message-editor-locale-new-select']";
    public String messageProperty;
    public String listType;
    public String hyperlinkText;
    public String boldXpath = "//button[@title= 'Bold']";
    public String italicXpath = "//button[@title= 'Italic']";
    public String messageTextXpath = "//body[@id ='tinymce']";
    public String messageText;
    public String factLevelXpath = "//*[contains(text(),'Fact Levels')]";
    public String messageVariable;
    public String messageVariableXpath = "//*[contains(text(),'Message variables')]";
    public String searchVariableXpath = "//input[@class = 'tox-textfield']";
    public String insertButtonXpath = "//button[@title = 'Insert']";
    public String saveButtonXpath = "//button[@class = 'save ng-binding ng-scope']";
    public String fontXpath = "//*[contains(text(),'Paragraph')]";
    public String fontType;

    public String searchTxtBox = "//div[@id='message-list']//input";
    public String goButton = "//div[@id='message-list']//button[contains(text(),'Go')]";

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

    public boolean SearchMessage() throws InterruptedException
    {
        Thread.sleep(2000);

        if(driver.findElement(By.xpath(searchTxtBox)).isDisplayed())
        {
            driver.findElement(By.xpath(searchTxtBox)).sendKeys(keepRefer.get("MESSAGE_NAME"));
            driver.findElement(By.xpath(goButton)).click();
            Thread.sleep(2000);

            List<WebElement> searchResult = driver.findElements(By.xpath("//span[contains(text(),'"+keepRefer.get("MESSAGE_NAME")+"')]"));

            if(searchResult.size()>0) {

                reporter.reportLogPassWithScreenshot("Message already created");
                return true;
            }
        }
        return false;
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

        driver.findElement(By.xpath(factLevelXpath)).click();
        Thread.sleep(1000);
        String factLevelValue = keepRefer.get("FACT_LEVEL");
        driver.findElement(By.xpath("//div[@title='"+ factLevelValue +"']")).click();



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
        WebElement text = driver.findElement(By.xpath(messageTextXpath));
        text.sendKeys(messageText);
        Thread.sleep(1000);

        //Hyperlinks
        driver.switchTo().parentFrame();
        hyperlinkText = keepRefer.get("HYPERLINK");

        if(!hyperlinkText.isEmpty())
        {
            driver.findElement(By.xpath("//button[@title='Insert/edit link']")).click();
            WebElement url = driver.findElement(By.xpath("//label[contains(text(),'URL')]/..//input[@type='text']"));
            url.click();
            url.sendKeys(hyperlinkText);
            Thread.sleep(1000);
            WebElement title = driver.findElement(By.xpath("//label[contains(text(),'Title')]/..//input[@type='text']"));
            title.click();
            title.sendKeys(hyperlinkText);
            Thread.sleep(1000);
            WebElement target = driver.findElement(By.xpath("//button[@title='Target']"));
            target.click();
            //Select targetName = new Select(target);
            //targetName.selectByVisibleText("New window");
            driver.findElement(By.xpath("//div[contains(text(),'New window')]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@class='tox-dialog__footer']//button[contains(text(),'Save')]")).click();

        }

        driver.switchTo().frame(0);

        text.sendKeys(Keys.CONTROL, "a");
        Thread.sleep(2000);

        if(messageProperty.equals("UNDERLINE"))

        {
            text.sendKeys(Keys.CONTROL, "u");
            Thread.sleep(1000);
        }


   /*    //Inserting the message variable

            driver.switchTo().parentFrame();
            messageVariable = keepRefer.get("MESSAGE_VARIABLE");
            driver.findElement(By.xpath(messageVariableXpath)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(searchVariableXpath)).sendKeys(messageVariable);
            Thread.sleep(2000);
            reporter.reportLogPassWithScreenshot("Message Variable Search Page is Displayed");
            driver.findElement(By.xpath(insertButtonXpath)).click();
            Thread.sleep(2000);
    */
        driver.switchTo().parentFrame();

        listType = keepRefer.get("LIST_TYPE");

        if(listType.equals("BULLETS"))
        {
            driver.findElement(By.xpath("//button[@title= 'Bullet list']")).click();
        }

        fontType = keepRefer.get("MESSAGE_STYLE");

        driver.findElement(By.xpath(fontXpath)).click();
        driver.findElement(By.xpath("//*[contains(text(),'"+fontType+"')]")).click();
        Thread.sleep(2000);
        reporter.reportLogPassWithScreenshot("Message details before saving");
        driver.findElement(By.xpath(saveButtonXpath)).click();
        reporter.reportLogPassWithScreenshot("New Message is Created successfully");
        Thread.sleep(4000);

    }
}

