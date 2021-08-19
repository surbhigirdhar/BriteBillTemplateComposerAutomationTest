package Utils;




import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import Config.EnvironmentConf;
import Config.MainConfig;




public class CSVHandler
{

	public LinkedHashMap readResourceCalendar(String calendarName)
	{

		LinkedHashMap<String, HashMap<String, String>> allCommandsList=  new LinkedHashMap<String, HashMap<String, String>>();

		try
		{
			CSVReader reader = new CSVReader(new FileReader(calendarName));

			String[] headerLine, nextLine;

			headerLine = reader.readNext();


			while ((nextLine = reader.readNext()) != null)
			{
				HashMap<String,String> actionList = new HashMap<String, String>();

				if (nextLine != null && !nextLine[1].equalsIgnoreCase("x") )
				{
					for(int i=0; i<nextLine.length;i++)
					{
						actionList.put(headerLine[i],nextLine[i]);
					}
					allCommandsList.put(nextLine[0],actionList);
				}

			}



		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allCommandsList;

	}
	
	public LinkedHashMap<String, HashMap<String, String>> readScenario(String calendarName) throws FileNotFoundException
	{
		String fileName = calendarName;
		
		LinkedHashMap<String, HashMap<String, String>> allCommandsList=  new LinkedHashMap<String, HashMap<String, String>>();
		
		CsvToBean<TestCases> csvToBean = new CsvToBeanBuilder<TestCases>(new FileReader(fileName))
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                .withType(TestCases.class)
                .build();

        List<TestCases> beans = csvToBean.parse();
        
        for(int i=0;i<beans.size();i++)
        {
	        HashMap<String,String> actionList = new HashMap<String, String>();
			
	        TestCases testcase = beans.get(i);
	        
	        if(testcase.getSkip().equalsIgnoreCase("No"))
	        {
				actionList.put("ID",testcase.getID());
				actionList.put("TestID",testcase.getTestID());
				actionList.put("Type",testcase.getType());
				actionList.put("Path",testcase.getPath());
				actionList.put("Method",testcase.getMethod());
				actionList.put("APIStatus",testcase.getAPIStatus());
				actionList.put("Request",testcase.getRequest());
				actionList.put("Header",testcase.getHeader());
				actionList.put("Wait",testcase.getWait());
				actionList.put("Retry",testcase.getRetry());
				actionList.put("Configure",testcase.getConfig());
				actionList.put("ReadResponse",testcase.getReadResponse());
				actionList.put("Assert",testcase.getAssert());
				actionList.put("PreRequest",testcase.getPreRequest());
					
				allCommandsList.put(testcase.getID(),actionList);
	        }
        }
        
       return allCommandsList;
		
	}
	
	public void ReadEnvironmentCalendar(String envName) throws FileNotFoundException
	{
		//String fileName = "/Rogers/Automation/Environments.csv";
		
		String fileName = System.getProperty("user.dir") + MainConfig.properties.getProperty("ENVIRONMENT_FILE");
				
		CsvToBean<Environment> csvToBean = new CsvToBeanBuilder<Environment>(new FileReader(fileName))
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                .withType(Environment.class)
                .build();

        List<Environment> beans = csvToBean.parse();
        
        for(int i=0;i<beans.size();i++)
        {
        	HashMap<String,String> envDet = new HashMap<String, String>();
			
	        Environment env = beans.get(i);
	        
	        if(env.getEnvName().contentEquals(envName))
	        {
	        	envDet.put("EnvName", env.getEnvName());
				envDet.put("URL", env.getURL());

			
				EnvironmentConf.environmentDetails.put(env.getEnvName(),envDet);
	        }
        }
		
	}
	
	public HashMap<String, String> readKeepRefer(String calendarName, String testCaseID)
	{
	//	String keepReferFile = System.getProperty("user.dir") + MainConfig.properties.getProperty("APP_PATH")
	//							+ "/"+ release +"/Test/" + MainConfig.properties.getProperty("KEEP_REFER_FILE");
		
		HashMap<String, String> keepRefer=  new HashMap<String, String>();
		 
		 try 
		 {
			CSVReader reader = new CSVReader(new FileReader(calendarName));
			
			String[] headerLine, nextLine;
			
			headerLine = reader.readNext();
			
			
			while ((nextLine = reader.readNext()) != null)
			{
				if (nextLine != null && !nextLine[1].equalsIgnoreCase("x") && nextLine[2].contentEquals(testCaseID))
				{
					for(int i=0; i<nextLine.length;i++)
					{
						keepRefer.put(headerLine[i],nextLine[i]);
					}
					break;
				}
				
			}
		
		 } 
		 catch (Exception e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return keepRefer;
		 
	}
	
	
	public static String ReadCalendarDB(String id, HashMap<String, String> keepRefer) 
	{
		
		try
		{
		
			String commonDBFile =  System.getProperty("user.dir") + MainConfig.properties.getProperty("COMMON_DB_PATH");
	
			CsvToBean<CommonDB> csvToBean = new CsvToBeanBuilder<CommonDB>(new FileReader(commonDBFile))
	                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
	                .withType(CommonDB.class)
	                .build();
	
	        List<CommonDB> beans = csvToBean.parse();
	        
	        for(int i=0;i<beans.size();i++)
	        {
	        	CommonDB db = beans.get(i);
	        	if(db.getID().contentEquals(id))
	        	{
					if(db.getSAVE_PARAM_NAME() != null)
					{
						keepRefer.put("SAVE_PARAM_NAME", db.getSAVE_PARAM_NAME());
					}
					
					if(db.getEXP_Result() != null)
					{
						keepRefer.put("EXP_RESULT", db.getEXP_Result());
					}
					else
					{
						keepRefer.put("EXP_RESULT", "");
					}
					
					return db.getQuery();
	        	}
	        }
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
        return null;
	}
	
	public static void createResultReport(HashMap<String, String> keepRefer)
	{
		String fileName = "keepRefer_"+keepRefer.get("TestCaseID")+"_.csv";
		
		String resultPath = System.getProperty("user.dir") + MainConfig.properties.getProperty("APP_PATH") 
							+ "/"+ MainConfig.properties.getProperty("RELEASE") + MainConfig.properties.getProperty("RESULT_PATH");
				
		try 
		{
			FileManager file = new FileManager();

			Path tempFilepath = Paths.get(file.CreateNewInputFile(fileName, resultPath));

			 for(int i=0;i<keepRefer.size();i++)
			 {
				 String entry = (keepRefer.entrySet().toArray())[i].toString();
				 
				 String[] eArr = entry.split("=");
				 String key = eArr[0];
				 String val = eArr.length>1?eArr[1]:"";
				 
				 Files.write(tempFilepath, (key + "," +val +"\n").getBytes() , StandardOpenOption.APPEND);
				 
			 }

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}



}
