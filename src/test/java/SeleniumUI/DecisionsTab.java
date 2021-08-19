package SeleniumUI;

import base.BaseClass;
import org.openqa.selenium.WebDriver;

public class DecisionsTab extends BaseClass {
    public String decisionsXpath = "//*[text()='Decisions']";
    public String newXpath = "//*[@id='bb-te-decision-list-new-button']";
    public String nameXpath = "//*[@id='bb-te-decision-editor-name-input']";
    public String trueIfXpath = "bb-te-decision-editor-description-textarea";
    public String levelXpath = "QATest-Maestro";
    public String queryBuilderXpath = "//*[@name='qb_1054_rule_0_filter']";
    public String popUpFactsXpath = "//*[text()='Facts']";
    public String popUpSearchXpath = "//*[@class='bb-autocomplete-input form-control ng-empty']";
    public String popUpOkXpath = "//*[text()='OK']";
    public String queryOperatorXpath = "//*[@name='qb_83666_rule_0_operator']";
    public String queryOperatorValueXpath = "//*[text()='equals']";
    public String operatorValueXpath = "//*[@name='qb_83666_rule_0_value_0']";
    public String saveXpath = "//*[text()='Save']";


    public String name,trueIf,level,aggressionMode,queryBuilderValue,operator , operatorValue;
    DecisionsTab(WebDriver driver, String name, String trueIf, String level, String aggressionMode, String queryBuilderValue, String operator , String operatorValue){
        this.driver= driver;
        this.name=name ;
        this.trueIf=trueIf;
        this.level = level;
        this.aggressionMode = aggressionMode;
        this.queryBuilderValue = queryBuilderValue;
        this.operator = operator;
        this.operatorValue = operatorValue;
    }



}
