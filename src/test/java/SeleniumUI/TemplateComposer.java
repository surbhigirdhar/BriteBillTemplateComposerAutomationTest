package SeleniumUI;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;


public class TemplateComposer extends BaseClass {

    public String projectsXpath = "//*[text()='Work on a new component set.']";
    public String newButtonXpath = "//button[@class='new ng-binding ng-scope']";
    public String nameFieldXpath = "//input[@id='bb-te-project-editor-name-input']";
    public String projectName;
    public String descriptionXpath = "//textarea[@ng-model='vmEditor.data.narrative']";
    public String saveButtonXpath = "//button[@class='save ng-binding ng-scope']";
    public String searchProjectXpath = "//input[@id='bb-filter-form-filter']";
    public String goButtonXpath = "//*[contains(text(),'Go ')]";
    public String cbuXpath = "//*[contains(text(),'cbu')]";
    public String ebuXpath = "//*[contains(text(),'ebu')]";
    public String BusinessUnit;


    public void AppSelection() throws InterruptedException {

        Select selectApp = new Select(driver.findElement(By.xpath("//select[@ng-model = 'selectedApp']")));
        selectApp.selectByVisibleText("Template Composer");
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("Template Composer App is Displayed successfully");

   }

   public void Projects() throws InterruptedException {

        driver.findElement(By.xpath(projectsXpath)).click();
       Thread.sleep(1000);
       reporter.reportLogPassWithScreenshot("Projects Page Displayed");
    }
   public void NewProject() {
    try {
        projectName = keepRefer.get("PROJECT_NAME");
        driver.findElement(By.xpath(newButtonXpath)).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath(nameFieldXpath)).sendKeys(projectName);
        Thread.sleep(1000);

        driver.findElement(By.xpath(descriptionXpath)).sendKeys("Automation Tests");
        Thread.sleep(1000);

        if (driver.findElement(By.xpath(saveButtonXpath)).isEnabled())
        {
            driver.findElement(By.xpath(saveButtonXpath)).click();
        }
        else {
            reporter.reportLogFailWithScreenshot("Save Button disabled");
            System.out.println("Save Button disabled");

        }
        Thread.sleep(1000);
      reporter.reportLogPassWithScreenshot("New Project Created successfully");

         }
    catch (InterruptedException e) {
        e.printStackTrace();
         }

    }

    public void SearchProject() {
        try {
            projectName = keepRefer.get("PROJECT_NAME");
            driver.findElement(By.xpath(searchProjectXpath)).sendKeys(projectName);
            Thread.sleep(2000);

            driver.findElement(By.xpath(goButtonXpath)).click();
            Thread.sleep(1000);
             reporter.reportLogPassWithScreenshot("Selected Project is Displayed ");

            driver.findElement(By.xpath("//*[contains(text(),'"+ projectName +"')]")).click();
            Thread.sleep(1000);
             reporter.reportLogPassWithScreenshot("Folders Page is Displayed");

            BusinessUnit   = keepRefer.get("BUSINESS_UNIT");
            if(BusinessUnit.equals("cbu"))
        //To select cbu Template
            {
                driver.findElement(By.xpath(cbuXpath)).click();
            }
            else if(BusinessUnit.equals("ebu"))
            {
                driver.findElement(By.xpath(ebuXpath)).click();
            }
            Thread.sleep(1000);
             reporter.reportLogPassWithScreenshot("Templates Page is Displayed");

            }
        catch (InterruptedException e) {
            e.printStackTrace();
         }
    }

}

