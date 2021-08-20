package SeleniumUI;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.security.Key;

public class Decisions extends BaseClass {
    public String decisionsXpath = "//*[text()='Decisions']";
    public String newButtonXpath = "//button[@id = 'bb-te-decision-list-new-button']";
    public String nameXpath = "//*[@id='bb-te-decision-editor-name-input']";
    public String decisionName;
    public String trueIfXpath = "//div[@class='ng-scope']/parent::*//textarea[@id='bb-te-decision-editor-description-textarea']";
    public String levelXpath = "//select[@id='bb-te-decision-editor-level-select']";
    public String aggregationModeXpath = "//select[@id='bb-te-decision-editor-having-aggregation-rules-select']";
    public String addRuleXpath = "//*[text()=' Rule                                   ']";
    public String queryBuilderXpath1 = "(//ul[@class='rules-list']//..//input[@type='text'])[1]//parent::*";
    public String queryBuilderXpath2 = "(//ul[@class='rules-list']//..//input[@type='text'])[2]//parent::*";
    public String popUpFactsXpath = "//*[text()='Facts']";
    public String popUpSearchXpath = "//input[@placeholder='Search']";
    public String popUpOkXpath = "//*[text()='OK']";
    public String queryOperatorXpath = "//*[@name='qb_83666_rule_0_operator']";
    public String queryOperatorValueXpath = "//*[text()='equals']";
    public String operatorValueXpath = "//*[@name='qb_83666_rule_0_value_0']";
    public String saveXpath = "//*[text()='Save']";
    public String equalsTrueXpath = "//*[text()=' True']";
    public String equalsFalseXpath = "//*[text()=' False']";
    public String trueIfTextbox;
    public String levelValue;
    public String aggregationModeValue;
    public String decision;
    public String decisionOperation;
    public String decisionValue;

    public String ruleOperator = "//*[text()=' equals ']";


    public String decisionValueXpath = "//select[@class='form-control ']/parent::*//..//*[@class='rule-value-container']/input";


    public void Decision() {
        try {
            driver.findElement(By.xpath(decisionsXpath)).click();
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("Decisions Page is Displayed");
            NewDecision();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void NewDecision() throws InterruptedException {

        driver.findElement(By.xpath(newButtonXpath)).click();
        Thread.sleep(1000);
        //  decisionName = keepRefer.get("MESSAGE_NAME");
        decisionName ="Test123";
        driver.findElement(By.xpath(nameXpath)).sendKeys(decisionName);
        Thread.sleep(1000);
        trueIfTextbox = keepRefer.get("TRUE_IF");
        driver.findElement(By.xpath(trueIfXpath)).sendKeys(trueIfTextbox);
        Thread.sleep(1000);
        levelValue = keepRefer.get("LEVEL");
        Select level = new Select(driver.findElement(By.xpath(levelXpath)));
        level.selectByVisibleText(levelValue);
        Thread.sleep(1000);
        aggregationModeValue = keepRefer.get("AGGREGATION_MODE");
        Select aggregationMode = new Select(driver.findElement(By.xpath(aggregationModeXpath)));
        aggregationMode.selectByVisibleText(aggregationModeValue);

//Adding New Decision Rule

        if(!keepRefer.get("DECISION1").isEmpty())
        {
            keepRefer.put("DECISION", keepRefer.get("DECISION1"));
            keepRefer.put("DECISION_OPERATION", keepRefer.get("DECISION_OPERATION1"));
            keepRefer.put("DECISION_VALUE", keepRefer.get("DECISION_VALUE1"));
            driver.findElement(By.xpath(queryBuilderXpath1)).click();
            Thread.sleep(1000);
            addRule();
        }
//Adding Multiple Rules
        if(!keepRefer.get("DECISION2").isEmpty())
        {
            keepRefer.put("DECISION", keepRefer.get("DECISION2"));
            keepRefer.put("DECISION_OPERATION", keepRefer.get("DECISION_OPERATION2"));
            keepRefer.put("DECISION_VALUE", keepRefer.get("DECISION_VALUE2"));
            driver.findElement(By.xpath(addRuleXpath)).click();
            Thread.sleep(1000);

            if (!keepRefer.get("DECISION_OPERATOR").equals("OR"))
            {
                driver.findElement(By.xpath("//*[text()=' AND                                     ']")).click();
            }
            else
            {
                driver.findElement(By.xpath("//*[text()=' OR                                     ']")).click();
            }

            driver.findElement(By.xpath(queryBuilderXpath2)).click();
            Thread.sleep(1000);
            addRule();
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scroll(0,800)");
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("New Decision details before saving");
        driver.findElement(By.xpath(saveXpath)).click();
        Thread.sleep(2000);
        reporter.reportLogPassWithScreenshot("New Decision Created Successfully");

    }//NewDecision finish


    public void addRule() throws InterruptedException {

        decision = keepRefer.get("DECISION");
        decisionValue = keepRefer.get("DECISION_VALUE");
        decisionOperation = keepRefer.get("DECISION_OPERATION");

        WebElement select = driver.findElement(By.xpath(popUpSearchXpath));
        select.sendKeys(decision);
        Thread.sleep(1000);
        driver.findElement(By.xpath(popUpSearchXpath)).click();
        driver.findElement(By.xpath("//input[@placeholder='Search']//..//li")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(popUpOkXpath)).click();
        Thread.sleep(1000);

        Boolean exists = driver.findElements(By.xpath(ruleOperator)).size() != 0;
        if (exists.equals(true))
        {
            if (decisionValue.equals("TRUE"))
            {
                driver.findElement(By.xpath(equalsTrueXpath)).click();
            } else
            {
                driver.findElement(By.xpath(equalsFalseXpath)).click();

            }
        }
        else
        {
            WebElement element = driver.findElement(By.xpath("//select[@class='form-control ']"));
            Select selectOperation = new Select(element);
            selectOperation.selectByVisibleText(decisionOperation);

            driver.findElement(By.xpath(decisionValueXpath)).sendKeys(decisionValue);
            driver.findElement(By.xpath(decisionValueXpath)).sendKeys(Keys.TAB);

        }

        Thread.sleep(2000);
        reporter.reportLogPassWithScreenshot("New Decision Rule added successfully");
    }


}
