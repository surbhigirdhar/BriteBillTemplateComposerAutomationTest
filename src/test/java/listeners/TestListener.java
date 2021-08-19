package listeners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.HashMap;

import SeleniumUI.SeleniumConfig;
import jdk.nashorn.internal.runtime.Context;
import org.apache.commons.io.output.WriterOutputStream;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import com.relevantcodes.extentreports.LogStatus;
import base.BaseClass;

import extentreport.ExtentManager;
import extentreport.ExtentTestManager;
import org.testng.internal.TestResult;
import org.testng.reporters.jq.Model;


public class TestListener extends BaseClass implements ITestListener, IInvokedMethodListener, ISuiteListener {

    private Logger logger = LogManager.getLogger(TestListener.class);
    private PrintStream logStream = IoBuilder.forLogger(logger).buildPrintStream();

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    	//return iTestResult.getTestName();
    }

    //Before starting all tests, below method runs.
    @Override
    public synchronized void onStart(ITestContext iTestContext) {
        System.out.println(" in onStart method " + iTestContext.getName());
        
     }

    //After ending all tests, below method runs.
    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println(" in onFinish method " + iTestContext.getName());
        //Do tier down operations for extentreports reporting!
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {
        int dataIteratorIndex = ((TestResult) iTestResult).getParameterIndex();
        Object[] dataParam = iTestResult.getParameters();
        System.out.println(" in onTestStart method " +  getTestMethodName(iTestResult) + "-" + dataIteratorIndex + " start");
        //Start operation for extentreports.
        String fullTestClassName[] = iTestResult.getMethod().getTestClass().getName().split("\\.");
     //   String testClassName = fullTestClassName[fullTestClassName.length-1];
//        if(dataIteratorIndex!=0) {
//            testClassName = testClassName + "_DataSet-" + (dataIteratorIndex+1);
//        }
        String testClassName =  iTestResult.getTestContext().getName();
        ExtentTestManager.startTest(testClassName,iTestResult.getTestContext().getName());
   //     ExtentTestManager.startTest(iTestResult.getTestContext().getName(),testClassName);

        Object testClass = iTestResult.getInstance();

       if(dataParam.length!=0) {
           ExtentTestManager.getTest().log(LogStatus.INFO,"Environment", dataParam[0].toString());
            ExtentTestManager.getTest().log(LogStatus.INFO,"Calendar", dataParam[1].toString());
       //     ExtentTestManager.getTest().log(LogStatus.INFO,"Test Data", ((HashMap) dataParam[0]).values().toString());
        }

        try {
            setup(testClassName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    //    setUpRestAssured();
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        System.out.println(" in onTestFailure method " +  getTestMethodName(iTestResult) + " failed");

        //Extentreports log and screenshot operations for failed tests.
        if(ExtentTestManager.getFailFlag()) {
            ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed due to the above logged errors");
        }

        if(iTestResult.getThrowable() != null){
            ExtentTestManager.getTest().log(LogStatus.FAIL,
                    "Test Failed : " + iTestResult.getThrowable().getLocalizedMessage().split("Build")[0].replace("<", "&lt;"));
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult iTestResult) {
        System.out.println(" in onTestSkipped method "+  getTestMethodName(iTestResult) + " skipped");
        //Extentreports log operation for skipped tests.
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    @Override
    public synchronized void onTestSuccess(ITestResult iTestResult) {
        // TODO Auto-generated method stub
        System.out.println(" in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed");
        //Extentreports log operation for passed tests.
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
    }

    /**
     * This listener method is invoked before a method is invoked by TestNG
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // TODO Auto-generated method stub

    }

    /**
     * This listener method is invoked after a method is invoked by TestNG
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(testResult.getMethod().isTest()) {
            if(testResult.getStatus() == ITestResult.SUCCESS) {
                if(ExtentTestManager.getFailFlag()) {
                    Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
                    Reporter.getCurrentTestResult().setThrowable(new AssertionError("Assertion Failed"));
                }
            }
        }
    }

    public synchronized void setup(String testClassName) throws IOException {
        File file = new File(ExtentManager.absolutePathToReport + "/" + testClassName + ".txt");
        file.getParentFile().mkdirs();
        logFiles.put(Thread.currentThread().getId(),testClassName + ".txt");
        fileWriter = new FileWriter(file);
        PrintStream printStream = new PrintStream(new WriterOutputStream(fileWriter,Charset.defaultCharset()), true);
        printStreams.put(Thread.currentThread().getId(),printStream);
    }

}