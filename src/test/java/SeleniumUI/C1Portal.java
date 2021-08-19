package SeleniumUI;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class C1Portal extends BaseClass {

    public int date= 30;
    public String url = "https://authoring-qa01.npe.c1d1.aws.rogers.com/catalog-one-ui/";
    public String username      = "qa01_qateamuser1";
    public String password      =  username;
    public String usernameXpath = "//*[contains(@id ,'username')]";
    public String passwordXpath = "//*[contains(@id ,'password')]";
    public String loginXpath    = "//*[contains(@id ,'kc-login')]";
    public String createBRXpath = "//*[contains(text(),'Create Business Request')]";
    public String dateIconXpath = "//*[@alt='calendar icon']";
    public String dayXpath      = "//*[text()="+date+" and @aria-disabled='false']";
    public String dateBtnXpath  = "//label[text()='Apply']";
    public String approvalFlowXpath = "//span[text()='Select Value']";
    public String selectValueXpath = "//span[text()='No Approval Required']";
    public String createBRbtnXpath = "//label[text()='Create & Open']";
    public String openSearchXpath = "//*[text()='Open Search']";
    public String searchXpath = "//*[@placeholder='Search for anything']";
    public String searchItemValue = "TEST_Plans (Nation-Wide Talk, Text & Data) to Add Ons";
    public String searchIconXpath = "//*[@data-test-id ='search-button']";
    public String verticalDotXpath = "(//*[@class='submenu-toggle'])[1]/*";
    public String duplicateXpath   = "//*[text()='Duplicate']";
    public String openInANewTabXpath = "//*[text()='Open in a new tab']";
    public String displayNameXpath = "//*[@data-test-id='productofferingbundle-name']/div/input";
    public String codeXpath        = "//*[@data-test-id='productoffering-code']/div/input";
    public String createbtnXpath = "//*[text()='Create']";
    public String catalogItemTypeFilterXpath = "//*[text()='Catalog Item Type']";
    public String categorySelectionXpath = "//*[text()='Commercial Relation']";
    public String horizontalDotsXpath = "//*[@class='business-request-actions-menu-button']/*";
    public String validatePendingIssuesXpath = "//*[text()='Validate Pending Issues']";
    public String publishToProductionXpath = "//*[text()='Publish to Production']";
    public String publishAnywayXpath = "//*[text()='Publish Anyway']";
    public String publishToProduction = "//*[text()='Publish to Production']";
    public String createBRNameXpath = "//*[@data-test-id='create_br_name']/div/input";
    public String effectiveBtnXpath ="//*[text() ='Effective']";
    public String searchCatalogXpath = "//*[text()='Search Catalog']";
    public String displayHeaderXpath ="//*[@class ='entity-name']/div/div";
    public String publishXpath = "//*[@class ='business-request-status-title']/div/div";

    //ChromeDriver driver = (ChromeDriver)SeleniumConfig.getChormeDriver();

    public WebDriver driver;

    public C1Portal()
    {
        SeleniumConfig seleniumConfig = new SeleniumConfig();
        try {

            seleniumConfig.chromeInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver = seleniumConfig.getDriver();
        reporter.setDriver(driver);
    }

    public void C1DaliySanity(){
        try{
            //reporter.setDriver(driver);
            C1PortalLogin();
            Thread.sleep(1000);
            driver.findElement(By.xpath(searchCatalogXpath)).click();
            searchAPlan();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void markPlanToEffective(){
        //reporter.setDriver(driver);
        C1PortalLogin();
        createBR();
        serachAndOfferToWorkspace();
        publishBR();
    }
    public void C1PortalLogin(){

        driver.get(url);

        try{

            reporter.reportLogPassWithScreenshot("C1Portal Login page");
            Thread.sleep(1000);
            driver.findElement(By.xpath(usernameXpath)).sendKeys(username);
            driver.findElement(By.xpath(passwordXpath)).sendKeys(password);
            reporter.reportLogWithScreenshot("Username Password Entered");

            driver.findElement(By.xpath(loginXpath)).click();

            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void createBR(){

        try {
            reporter.reportLogPassWithScreenshot("C1Portal after login");
            Thread.sleep(1000);
            driver.findElement(By.xpath(createBRXpath)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(createBRNameXpath)).sendKeys("New BR");
            driver.findElement(By.xpath(dateIconXpath)).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath(dayXpath)).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath(dateBtnXpath)).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath(approvalFlowXpath)).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath(selectValueXpath)).click();
            reporter.reportLogPassWithScreenshot("Entered BR detail");
            Thread.sleep(2000);
            driver.findElement(By.xpath(createBRbtnXpath)).click();

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void duplicatingAOffer() {
        try {
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("Workspace");
            Thread.sleep(1000);
            driver.findElement(By.xpath(openSearchXpath)).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath(searchXpath)).sendKeys(searchItemValue);
            Thread.sleep(1000);
            driver.findElement(By.xpath(catalogItemTypeFilterXpath)).click();
            driver.findElement(By.xpath(categorySelectionXpath)).click();
            reporter.reportLogPassWithScreenshot("searching offer");
            Thread.sleep(1000);
            driver.findElement(By.xpath(searchIconXpath)).click();
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("Search Result");
            Thread.sleep(1000);
            driver.findElement(By.xpath(verticalDotXpath)).click();
            reporter.reportLogPassWithScreenshot("Duplicating a Plan");
            driver.findElement(By.xpath(duplicateXpath)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(displayNameXpath)).sendKeys("QATEST_"+searchItemValue);
            reporter.reportLogPassWithScreenshot("Entering duplicate offer detail");
            Thread.sleep(1000);
            driver.findElement(By.xpath(createbtnXpath)).click();


        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void publishBR() {
        try {
            Thread.sleep(20000);
            reporter.reportLogPassWithScreenshot("adding duplicate offer to workspace");
            Thread.sleep(1000);
            driver.findElement(By.xpath(horizontalDotsXpath)).click();
            reporter.reportLogPassWithScreenshot("Clicking on validate pending issues");
            Thread.sleep(2000);
            driver.findElement(By.xpath(validatePendingIssuesXpath)).click();
            Thread.sleep(10000);
            driver.findElement(By.xpath(horizontalDotsXpath)).click();
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("Clicking on publish To Production");
            Thread.sleep(2000);
            driver.findElement(By.xpath(publishToProductionXpath)).click();
            Thread.sleep(3000);
            reporter.reportLogPassWithScreenshot("Clicking on publish To Production");
            driver.findElement(By.xpath(publishToProduction)).click();
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("After clicking on publish To Production");
            Thread.sleep(30000);
            Assert.assertEquals(driver.findElement(By.xpath(publishXpath)).getText(), "Publish");
            reporter.reportLogPassWithScreenshot("Clicking on publish To Production");

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void serachAndOfferToWorkspace()
    {
        try{
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("Workspace");
            Thread.sleep(1000);
            driver.findElement(By.xpath(openSearchXpath)).click();
            Thread.sleep(1000);
            searchAPlan();
            driver.findElement(By.xpath(effectiveBtnXpath)).click();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void searchAPlan(){
        try{
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("Workspace");
            Thread.sleep(1000);
            driver.findElement(By.xpath(searchXpath)).sendKeys(searchItemValue);
            Thread.sleep(1000);
            driver.findElement(By.xpath(catalogItemTypeFilterXpath)).click();
            driver.findElement(By.xpath(categorySelectionXpath)).click();
            reporter.reportLogPassWithScreenshot("searching offer");
            Thread.sleep(1000);
            driver.findElement(By.xpath(searchIconXpath)).click();
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("Search Result");
            Thread.sleep(1000);
            driver.findElement(By.xpath(verticalDotXpath)).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath(openInANewTabXpath)).click();
            reporter.reportLogPassWithScreenshot("Plan is added to workspace");
            Thread.sleep(3000);
            // Assert.assertEquals(driver.findElement(By.xpath(displayHeaderXpath)).getText(), searchItemValue);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}


