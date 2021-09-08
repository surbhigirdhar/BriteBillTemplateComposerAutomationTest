package Master;

import Config.EnvironmentConf;
import Config.MainConfig;
import SeleniumUI.*;
import Utils.CSVHandler;
import Utils.CommonUtils;
import Utils.FileManager;
import base.BaseClass;
import extentreport.ExtentTestManager;
import freemarker.template.Template;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class BBMaster extends BaseClass {

    CSVHandler csvHandler = new CSVHandler();
    BBLaunch bblaunch = new BBLaunch();
    TemplateComposer tc = new TemplateComposer();
    Messages msg = new Messages();
    Decisions dc = new Decisions();
    LiveAreas la = new LiveAreas();
    Preview pw = new Preview();
    PreviewDigital dig = new PreviewDigital();


    @Parameters({"Env", "CalendarName", "TestCaseID"})
    @Test
    public void executeTest(String env, String calendarName, String testCaseID) throws Exception {
        calendarName = System.getProperty("user.dir") + "/resources/" + calendarName;
        EnvironmentConf.ENVIRONMENT_NAME = env;


        keepRefer = csvHandler.readKeepRefer(calendarName, testCaseID);
        keepRefer.put("TestCaseID", testCaseID);
        csvHandler.ReadEnvironmentCalendar(env);
        EnvironmentConf.SetEnvironmentDetails(env);
        String status = "Pass";

        String action = keepRefer.get("ACTION");

        switch (action) {
            case "validateMessage":
                status = validateMessage();
                break;
        }

        if (status.contentEquals("Pass")) {
            reporter.reportLogPass("Test Passed");
        } else
            reporter.reportLogFail("Test Failed");

    }

    private String validateMessage() throws InterruptedException, IOException {
        String status = "Pass";

        bblaunch.launchBriteBill();
        tc.AppSelection();
        tc.Projects();
        tc.SearchProject();

        String Message_name_gen = keepRefer.get("MESSAGE_NAME_GEN");
        String invoiceType = keepRefer.get("INVOICE_TYPE");
        keepRefer.put("INVOICE", invoiceType);
        keepRefer.put("MESSAGE_STYLE", keepRefer.get("MSG_STYLE_" + invoiceType.toUpperCase()));

        if (Message_name_gen.isEmpty()) {
            msg.Message();
            keepRefer.put("MESSAGE_NAME", keepRefer.get("MESSAGE_ID") + "" + keepRefer.get("INVOICE") + CommonUtils.generateRandomDigits(4));

            if (!keepRefer.get("MESSAGE_TEXT_EN").isEmpty()) {
                keepRefer.put("MESSAGE_TEXT", keepRefer.get("MESSAGE_TEXT_EN"));
                keepRefer.put("LANGUAGE", "English");

                msg.NewMessage();
            }

            if (!keepRefer.get("MESSAGE_TEXT_FR").isEmpty()) {
                keepRefer.put("MESSAGE_TEXT", keepRefer.get("MESSAGE_TEXT_FR"));
                keepRefer.put("LANGUAGE", "French");
                msg.NewMessage();
            }

            dc.Decision();
            la.LiveArea();

        }

            pw.launchPreview();
        if (keepRefer.get("INVOICE_TYPE").equalsIgnoreCase("Paper")) {
            if (!keepRefer.get("CUSTOMER_SAMPLE_EN").isEmpty()) {
                pw.previewPDF(keepRefer.get("CUSTOMER_SAMPLE_EN"), keepRefer.get("DATA_SAMPLE_EN"), "EN");// this will download pdf
            }
            if (!keepRefer.get("CUSTOMER_SAMPLE_FR").isEmpty()) {
                pw.previewPDF(keepRefer.get("CUSTOMER_SAMPLE_FR"), keepRefer.get("DATA_SAMPLE_FR"), "FR");
            }
            if (!keepRefer.get("DATA_SAMPLE_NEG").isEmpty()) {
                pw.previewPDF(keepRefer.get("CUSTOMER_SAMPLE_EN"), keepRefer.get("DATA_SAMPLE_NEG"), "NEG");
            }


            if (!keepRefer.get("MESSAGE_TEXT_EN").isEmpty()) {
                status = pw.validatePDF(keepRefer.get("MESSAGE_TEXT_EN"), "EN");
            }

            if (!keepRefer.get("MESSAGE_TEXT_FR").isEmpty()) {
                status = pw.validatePDF(keepRefer.get("MESSAGE_TEXT_FR"), "FR");
            }
            if (!keepRefer.get("MESSAGE_TEXT_EN").isEmpty()) {
                status = pw.validatePDF(keepRefer.get("MESSAGE_TEXT_EN"), "NEG");
            }

        }

        if (keepRefer.get("INVOICE_TYPE").equalsIgnoreCase("Digital")) {
            if (!keepRefer.get("CUSTOMER_SAMPLE_EN").isEmpty()) {
                dig.previewDigitalBill(keepRefer.get("CUSTOMER_SAMPLE_EN"), keepRefer.get("DATA_SAMPLE_EN"), "EN");// this will download pdf
                status = dig.validateDigitalBill(keepRefer.get("MESSAGE_TEXT_EN"), "EN");
               // status = "Pass";
            }
            if (!keepRefer.get("CUSTOMER_SAMPLE_FR").isEmpty()) {
                dig.previewDigitalBill(keepRefer.get("CUSTOMER_SAMPLE_FR"), keepRefer.get("DATA_SAMPLE_FR"), "FR");
                status = dig.validateDigitalBill(keepRefer.get("MESSAGE_TEXT_FR"), "FR");
            //    status = "Pass";
            }
            if (!keepRefer.get("DATA_SAMPLE_NEG").isEmpty()) {
                dig.previewDigitalBill(keepRefer.get("CUSTOMER_SAMPLE_EN"), keepRefer.get("DATA_SAMPLE_NEG"), "NEG");
                status = dig.validateDigitalBill(keepRefer.get("MESSAGE_TEXT_EN"), "NEG");
              //  status = "Pass";
            }

        }

        return status;
    }

}





