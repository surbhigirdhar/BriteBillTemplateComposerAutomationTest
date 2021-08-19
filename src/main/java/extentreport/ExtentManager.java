package extentreport;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.relevantcodes.extentreports.ExtentReports;

import Config.MainConfig;
import Utils.FormFiller;



//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.

public class ExtentManager {

  private static ExtentReports extent;  
  public static String pathToReport;
  public static String absolutePathToReport;
  public static String relativePathToReport;
  public static String localTime;
   
  
  
  
  public synchronized static ExtentReports getReporter(){
      /**
       * The if block will get executed if the test run is triggered from local machine or any machine
       * where BUILD_TIMESTAMP is not setup. BUILD_TIMESTAMP env variable is set by Jenkins job.
       */
      if(extent == null){
          if((System.getenv("BUILD_TIMESTAMP")==null||System.getenv("BUILD_TIMESTAMP").equals(""))) {
              //Set HTML reporting file location
              Calendar cal = Calendar.getInstance();
              localTime = ZonedDateTime.now(ZoneId.of("America/Montreal")).format(DateTimeFormatter.ofPattern("KK-mm a")).replace(" ", "");
              relativePathToReport =  "/" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + localTime.replace(".", "").toUpperCase() + "/";
              absolutePathToReport= System.getProperty("user.dir") + "/report/" + relativePathToReport;
              pathToReport = absolutePathToReport +  "Results_" + FormFiller.generatelocalDateTime() + ".html";
          }
          else {
              String jobName = System.getenv("JOB_NAME");
              String buildTimeStamp = System.getenv("BUILD_TIMESTAMP");
              String testProfile = System.getenv("TestProfile");
              String dynamicReportFolder = jobName.replaceAll("\\s+", "").replace("digital-qe/","") + "/" +testProfile+"/"+ buildTimeStamp.replaceAll("\\s+", "")+"/";
              String currentWorkingDirectory = System.getProperty("user.dir");
              absolutePathToReport = currentWorkingDirectory.replace("\\", "/") + "/report/" +dynamicReportFolder;
              pathToReport = absolutePathToReport + "results.html";
          }
          extent = new ExtentReports(pathToReport, true);
      }
      return extent;
  }
  
  
}
