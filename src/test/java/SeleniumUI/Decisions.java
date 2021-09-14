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

    public String popUpFactsXpath = "//*[text()='Facts']";
    public String popUpSearchXpath = "//input[@placeholder='Search']";
    public String popUpOkXpath = "//*[text()='OK']";
    public String queryOperatorXpath = "//*[@name='qb_83666_rule_0_operator']";
    public String queryOperatorValueXpath = "//*[text()='equals']";
    public String operatorValueXpath = "//*[@name='qb_83666_rule_0_value_0']";
    public String saveXpath = "//*[text()='Save']";
    public String trueIfTextbox;
    public String levelValue;
    public String aggregationModeValue;

    public void Decision() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath(decisionsXpath)).click();
            Thread.sleep(1000);
            reporter.reportLogPassWithScreenshot("Decisions Page is Displayed");
            NewDecision();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void NewDecision() throws InterruptedException {

        driver.findElement(By.xpath(newButtonXpath)).click();
        Thread.sleep(1000);
        decisionName = keepRefer.get("MESSAGE_NAME");
        //decisionName = "Test";
        driver.findElement(By.xpath(nameXpath)).sendKeys(decisionName);
        Thread.sleep(1000);
        trueIfTextbox = keepRefer.get("TRUE_IF");
        driver.findElement(By.xpath(trueIfXpath)).sendKeys(trueIfTextbox);
        Thread.sleep(1000);
        levelValue = keepRefer.get("FACT_LEVEL");
        Select level = new Select(driver.findElement(By.xpath(levelXpath)));
        level.selectByVisibleText(levelValue);
        Thread.sleep(1000);
        aggregationModeValue = keepRefer.get("AGGREGATION_MODE");
        Select aggregationMode = new Select(driver.findElement(By.xpath(aggregationModeXpath)));
        aggregationMode.selectByVisibleText(aggregationModeValue);

//Adding New Decision Rule

        for (int i = 1; i < 5; i++)
        {
            String queryBuilderXpath = "(//ul[@class='rules-list']//..//div[@class='rule-filter-container'])["+i+"]//input[@class='form-control']//parent::*";

            if (keepRefer.containsKey("DECISION" + i)) {
                if (!keepRefer.get("DECISION" + i).isEmpty())
                {
                    keepRefer.put("DECISION", keepRefer.get("DECISION"+i));
                    keepRefer.put("DECISION_OPERATION", keepRefer.get("DECISION_OPERATION"+i));
                    keepRefer.put("DECISION_VALUE", keepRefer.get("DECISION_VALUE"+i));

                    if(i>1)
                    {
                        driver.findElement(By.xpath(addRuleXpath)).click();
                        Thread.sleep(1000);

                        if (!keepRefer.get("DECISION_OPERATOR").equals("OR")) {
                            driver.findElement(By.xpath("//*[text()=' AND                                     ']")).click();
                        } else {
                            driver.findElement(By.xpath("//*[text()=' OR                                     ']")).click();
                        }
                    }

                    driver.findElement(By.xpath(queryBuilderXpath)).click();
                    Thread.sleep(1000);
                    addRule(i);
                }
            }
        }


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scroll(0,800)");
        Thread.sleep(1000);
        reporter.reportLogPassWithScreenshot("New Decision details before saving");
        driver.findElement(By.xpath(saveXpath)).click();
        Thread.sleep(2000);
        reporter.reportLogPassWithScreenshot("New Decision Created Successfully");

    }

    public void addRule(int i) throws InterruptedException {
        String equalsTrueXpath = "(//ul[@class='rules-list']//..//input[@value='true'])[" + i + "]//parent::*";
        String equalsFalseXpath = "(//ul[@class='rules-list']//..//input[@value='false'])[" + i + "]//parent::*";
        String ruleOperatorValue = "(//ul[@class='rules-list']//..//div[@class='rule-operator-container'])[" + i + "]//select[@class='form-control ']";
        String decisionValueXpath = "(//ul[@class='rules-list']//..//div[@class='rule-value-container'])[" + i + "]//input[@class='form-control']";
        String ruleOperatorExist = "(//ul[@class='rules-list']//..//div[@class='rule-operator-container'])[" + i + "]//select[@class='form-control hide']";
        String collectionDecisionValue = "//div[@class='bb-in-collection']//textarea";
        String noValuesAddedXpath= "(//ul[@class='rules-list']//..//div[@class='rule-value-container'])[" + i + "]//div[@class='bb-in-collection']";

        WebElement select = driver.findElement(By.xpath(popUpSearchXpath));
        select.sendKeys(keepRefer.get("DECISION"));
        Thread.sleep(1000);
        driver.findElement(By.xpath(popUpSearchXpath)).click();
        driver.findElement(By.xpath("//input[@placeholder='Search']//..//li")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(popUpOkXpath)).click();
        Thread.sleep(1000);

        if (driver.findElements(By.xpath(ruleOperatorExist)).size() != 0) {
            if (keepRefer.get("DECISION_VALUE").contentEquals("TRUE"))
                driver.findElement(By.xpath(equalsTrueXpath)).click();
            else
                driver.findElement(By.xpath(equalsFalseXpath)).click();

        } else {
            WebElement element = driver.findElement(By.xpath(ruleOperatorValue));
            Select selectOperation = new Select(element);
            selectOperation.selectByVisibleText(keepRefer.get("DECISION_OPERATION"));

            if (keepRefer.get("DECISION_OPERATION").contains("collection")) {

            WebElement collection = driver.findElement(By.xpath(noValuesAddedXpath));

                collection.click();
                Thread.sleep(2000);
                driver.findElement(By.xpath(collectionDecisionValue)).click();
                driver.findElement(By.xpath(collectionDecisionValue)).sendKeys(keepRefer.get("DECISION_VALUE"));
                Thread.sleep(1000);

                driver.findElement(By.xpath(collectionDecisionValue)).sendKeys(Keys.TAB);
            } else {
                driver.findElement(By.xpath(decisionValueXpath)).sendKeys(keepRefer.get("DECISION_VALUE"));
                driver.findElement(By.xpath(decisionValueXpath)).sendKeys(Keys.TAB);

            }
            Thread.sleep(2000);
            reporter.reportLogPassWithScreenshot("New Decision Rule added successfully");
        }

    }
}

