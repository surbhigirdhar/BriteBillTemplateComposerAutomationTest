package Utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import Config.EnvironmentConf;
import Config.MainConfig;

public class ValidateTest{
	
	public static HashMap<String, String> compareResult = new HashMap<String, String>();
	
	CommonUtils commonUtils = new CommonUtils();
	
	public  String[] ValidateResult(String result, String[] validationList, HashMap<String, String> keepRefer) throws NumberFormatException, SQLException
	{
		String[] status= {"Pass",""};
		String res ="";

		int k=0;
		
		Pattern p = Pattern.compile("[>=<!]", Pattern.CASE_INSENSITIVE);
		String validVal ="";
		
		while(k<validationList.length)
		{
			validVal = validationList[k];
			
			if(validVal.contains("#"))
			{
				String query = CSVHandler.ReadCalendarDB( validVal.replace("#", ""), keepRefer );
				validVal=commonUtils.ReplaceTag(query, keepRefer);
				
//				res = DBManager.RunDBQuery(EnvironmentConf.V21Database, validVal);
				
			//	if(rs.next())
				{
					status[0] = Integer.parseInt(res) > 0 ? "Pass" : "Fail";
					if(status[0].contentEquals("Fail"))
					{
						status[1]="Query : "+validVal +" Failed";
					}
				}
				
			}
			
			else if(validationList[k].contains("<<"))
			{
				validVal = commonUtils.ReplaceTag(validationList[k], keepRefer);
			}
			
			else if(p.matcher(validVal).find())
			 {
				 String s =result + validVal;
				 
				 if(Boolean.parseBoolean(s))
				 {
					 status[0]="Pass";
				 }
				 else
				 {
					 status[0]="Fail";
					 status[1]="Validation " + validVal + " Failed";
				 }
			 }					
			else if(validVal.contains(result))
			{
				status[0]="Pass";
				break;				
			}
			else
			 {
				 status[0]="Fail";
				 status[1]="Validation " + validVal + " Failed";
			 }
			
			 k++;
		}
		//LoggerMaster.writeIntoHTMLLog("Validation : " + validVal +"; "+ status[1] , status[0] , "DB" );
		
		return status;
		
	}
	
	/*
	 * public static String[] validateResultOnAdminDB(String[] validationList)
	 * throws NumberFormatException, SQLException, FilloException {
	 * 
	 * String[] status= {"Pass",""};
	 * 
	 * EnvironmentConf.SetEnvironmentDetails(EnvironmentConf.ENVIRONMENT_NAME,
	 * EnvironmentConf.ADMIN_ID);
	 * 
	 * for (String validVal : validationList ) { status = validateDB(validVal);
	 * LoggerMaster.writeIntoHTMLLog("Validation : " + validVal +"; "+ status[1] ,
	 * status[0] ); }
	 * 
	 * return status;
	 * 
	 * }
	 * 
	 * public static String[] validateResultOnCustomerDB(String[] validationList)
	 * throws NumberFormatException, SQLException, FilloException { String[] status=
	 * {"Pass",""};
	 * 
	 * 
	 * for (String validVal : validationList ) { System.out.println(""); for(String
	 * slaveEnv : EnvironmentConf.SLAVE_ID.keySet()) {
	 * EnvironmentConf.SetEnvironmentDetails(EnvironmentConf.ENVIRONMENT_NAME,
	 * slaveEnv); status = validateDB(validVal);
	 * 
	 * if(status[0].contentEquals("Pass")) {
	 * //LoggerMaster.writeIntoHTMLLog("Validation : " + validVal +"; "+ status[0] ,
	 * status[0] ); break; } else { LoggerMaster.writeIntoHTMLLog("Validation : " +
	 * validVal +"; Failed on slave id :"+ slaveEnv + " : " + status[1] , status[0]
	 * ); } }
	 * 
	 * 
	 * 
	 * } return status; }
	 */
	
	public  String validateDB(String validVal, HashMap<String, String> keepRefer) throws NumberFormatException, SQLException 
	{
		String result="";
		String query = CSVHandler.ReadCalendarDB(validVal.replace("#", ""), keepRefer);


		if(query!=null)
		{
			validVal=commonUtils.ReplaceTag(query, keepRefer);
			validVal = validVal.replace("*","");
			System.out.println("Query :" + validVal);
//			result = DBManager.RunDBQuery(EnvironmentConf.V21Database, validVal);
		}
	
		return result;
	}

	
	
	
	public String ValidateExpectedResult(String result, HashMap<String, String> keepRefer) throws  SQLException 
	{
		String valueToValidate="";
		String status = "Pass";
		
		if(keepRefer.get("EXP_RESULT")!="")
		{
			valueToValidate = keepRefer.get("EXP_RESULT");
			valueToValidate = commonUtils.ReplaceTag(valueToValidate, keepRefer);
			
			commonUtils.writeResultsInKeepRefer(valueToValidate, result, keepRefer);
		}
		
		if(valueToValidate!="")
		{
			Pattern p = Pattern.compile("[>=<!]", Pattern.CASE_INSENSITIVE);
			 if(p.matcher(valueToValidate).find())
			 {
				 Pattern chkNum = Pattern.compile("-?\\d+(\\.\\d+)?");
				 if(!chkNum.matcher(result).matches())
				 {
					 result="'" + result +"'";
				 }
				 String s =result + valueToValidate;
			
				 try {
						 ScriptEngineManager manager = new ScriptEngineManager();
						 ScriptEngine engine = manager.getEngineByName("js");
						 Object r;
						 r = engine.eval(s);
						 
						 if(Boolean.TRUE.equals(r))
						 {
							 status="Pass";
						 }
						 else
						 {
							 status="Fail";
						 }
					} catch (ScriptException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   
				 
			 }		
		}
		else
		{
			status = !result.isEmpty() ? "Pass" : "Fail";
		}
		
		return status;
		
	}

//	public static void getResultFromDBSingleRecord(String[] validationList, String result_colName, int chkNo) throws FilloException, NumberFormatException, SQLException 
//	{
//		
//		int k=0;
//		String finalQuery, status;
//		
//		while(k<validationList.length)
//		{
//			String validVal = validationList[k];
//			
//			if(validVal.contains("CHECK"))
//			{
//				String query = ExcelUtil.ReadCalendarDB(EnvironmentConf.CALENDAR_NAME, validVal.replace("#", ""));
//				finalQuery=CommonUtils.ReplaceTag(query);
//				
//				ResultSet rs = DBManager.RunDBQuery(EnvironmentConf.DATABASE, finalQuery);
//				
//				if(rs.next())
//				{
//					String result = rs.getString(1);
//					String dbID = validVal.replace("#", "");
//					
//					if(chkNo==1)
//					{
//						compareResult.put(dbID, result);
//						//ExcelUtil.insertIntoCalendar(EnvironmentConf.CALENDAR_NAME, "Compare_Result", "ID,DateOfExecution,Scenario,DB_ID," + result_colName , val	);
//					}
//					else if(chkNo==2)
//					{
//						//String resultEnv1 = ExcelUtil.readSingleRecordFromCalendar(EnvironmentConf.CALENDAR_NAME, "Compare_Result", "Result_ENV1", "ID="+ MainConfig.properties.getProperty("SCENARIO_ID") +" And DB_ID = '"+validVal.replace("#", "")+"'");
//						
//						if(compareResult.get(dbID).contentEquals(result))
//						{
//							status = "Pass";
//														
//						//	ExcelUtil.writeCalendar(EnvironmentConf.CALENDAR_NAME, "Compare_Result", result_colName, MainConfig.properties.getProperty("SCENARIO_ID") +" And DB_ID = '"+validVal.replace("#", "")+"'", result);
//						}
//						else
//						{
//							status = "Fail";
//							MainConfig.properties.setProperty("SCENARIO_COMPARE_RESULT", "Fail");
//							
//						}
//						
//						String val = "'" + MainConfig.properties.getProperty("SCENARIO_ID") + "','" + MainConfig.keepRefer.get("TODAY_DATE") + "','" 
//								+ MainConfig.properties.getProperty("SCENARIO_NAME") + "','" + dbID + "','" + compareResult.get(dbID) + "','" + result + "','"+status+"'";
//						
//						ExcelUtil.insertIntoCalendar(EnvironmentConf.CALENDAR_NAME, "Compare_Result", "ID,DateOfExecution,Scenario,DB_ID,Result_ENV1,Resut_ENV2,Status" , val	);
//					}
//						
//				}
//				
//			}
//			
//			k++;
//		}
//		
//	}
	

}
