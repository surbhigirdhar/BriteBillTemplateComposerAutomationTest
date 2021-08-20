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


    @Parameters({"Env", "CalendarName","TestCaseID" })
    @Test
    public void executeTest(String env, String calendarName, String testCaseID) throws Exception
    {
        calendarName = System.getProperty("user.dir") + "/resources/" +  calendarName;
        EnvironmentConf.ENVIRONMENT_NAME =env;

        keepRefer = csvHandler.readKeepRefer(calendarName,testCaseID);
        csvHandler.ReadEnvironmentCalendar(env);
        EnvironmentConf.SetEnvironmentDetails(env);
        String status = "Pass";

        String action = keepRefer.get("ACTION");

        switch (action)
        {
            case "validateMessage" :
               status = validateMessage();
                break;
        }

        if(status.contentEquals("Pass"))
        {
            reporter.reportLogPass("Test Passed");
        }
        else
            reporter.reportLogFail("Test Failed");

    }

    private String validateMessage() throws InterruptedException, IOException {
        String status = "Pass";
        try {
            FileManager.deleteDirectory(MainConfig.properties.getProperty("PDF_PATH"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //To launch BriteBill Application
        bblaunch.launchBriteBill();
        //Selection of App in BriteBill Application
        tc.AppSelection();
        //Go to Projects Folder
        tc.Projects();
        //To Create a New Project
        // tc.NewProject();
        //To Search a Project


        tc.SearchProject();

        // Go to Messages Folder

        msg.Message();

       String[] invoiceTypeArr = keepRefer.get("INVOICE_TYPE").split(";"); //Paper or Digital

        for(String invoiceType : invoiceTypeArr)
        {
            keepRefer.put("INVOICE",invoiceType);
            keepRefer.put("MESSAGE_NAME",keepRefer.get("MESSAGE_ID")+""+keepRefer.get("INVOICE")+ CommonUtils.generateRandomDigits(4));
            keepRefer.put("MESSAGE_STYLE",keepRefer.get("MSG_STYLE_"+invoiceType.toUpperCase()));

            //Create new Message

            if(!keepRefer.get("MESSAGE_TEXT_EN").isEmpty())
            {
                keepRefer.put("MESSAGE_TEXT", keepRefer.get("MESSAGE_TEXT_EN"));
                keepRefer.put("LANGUAGE", "English");
                msg.NewMessage();
            }

            if(!keepRefer.get("MESSAGE_TEXT_FR").isEmpty())
            {
                keepRefer.put("MESSAGE_TEXT", keepRefer.get("MESSAGE_TEXT_FR"));
                keepRefer.put("LANGUAGE", "French");
                msg.NewMessage();
            }

       //Create new Decision
            dc.Decision();
  // Go to Live Areas Folder
            la.LiveArea();
 // Go to Preview
            pw.previewCBU();
        }

            status =  pw.previewCBU();

            return status;
   }


}
