package Utils;


import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import Config.EnvironmentConf;
import Config.MainConfig;

public class CommonUtils {
	
	/*
	 * Method Name : ReplaceTag Parameter : command to be replaced Description :
	 * Replace all tags of command with KeepRefer value Author : Saloni Gupta
	 */

	public String ReplaceTag(String command, HashMap<String, String> keepRefer ) throws SQLException 
	{
		if (command != null && command.contains("<<")) 
		{
			String[] id = StringUtils.substringsBetween(command, "<<", ">>");

			for (String idName : id) {
				String lookupVal = "";
 
				if (keepRefer.containsKey(idName)) {
					lookupVal = keepRefer.get(idName);
				}

				if (MainConfig.properties.containsKey(idName)) {
					lookupVal = MainConfig.properties.getProperty(idName);
				}

				//<<DATE(variableName;currentFormat;newFormat)>>
				if (idName.contains("DATE(")) 
				{
					String[] dt = StringUtils.substringsBetween(idName, "(", ")");
					
					String dtVar = dt[0].split(";")[0];
					String currFormat = dt[0].split(";")[1];
					String newFormat = dt[0].split(";")[2];
					
					try {
						lookupVal = (new SimpleDateFormat(newFormat).format(new SimpleDateFormat(currFormat).parse(keepRefer.get(dtVar))));
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}



				if (idName.contains("RANDOM:")) {
					String randNo = idName.split(":")[1];

					lookupVal = generateRandomDigits(Integer.parseInt(randNo)) + "";
				}

				command = command.replace("<<" + idName + ">>", lookupVal);
			}

		}

	//	command = ReplaceKeyMap(command);

		return command;
	}
	
	// Generates a random with n digits
		public static String generateRandomDigits(int n) {
			float m = (float) Math.pow(10, n);
			return Math.round(Math.random() * m)+"";
		}

		public static String ReplaceKeyMap(String command) 
		{
			if (command != null && command.contains("$")) {
				for (Entry<String, String> envKeys : EnvironmentConf.envKeyMaps.get(EnvironmentConf.ENVIRONMENT_NAME)
						.entrySet()) {
					if (command.contains(envKeys.getKey())) {
						command = command.replace("$" + envKeys.getKey(), envKeys.getValue());
					}
				}
			}
			return command;
		}

		
		public HashMap<String, String> writeResultsInKeepRefer(String id, String value, HashMap<String, String> keepRefer) 
		{

			id = id.replace("&", "");

			if (!keepRefer.containsKey(id)) 
			{
				keepRefer.put(id, value);
			} 
			else 
			{
				keepRefer.replace(id, value);

			}

			return keepRefer;
		}
		
		public HashMap<String, String> writeAllResultsInKeepRefer(String outputStr, MainConfig config, HashMap<String, String> keepRefer)
		{

			String val;

			outputStr = outputStr.replace("&", "");

			String[] outputList = outputStr.split(";");

			for (String output : outputList) 
			{
				val = MainConfig.properties.getProperty(output);
				keepRefer.putIfAbsent(output, val);
			}
			
			return keepRefer;

		}
		
		public static String removeStartEndSquareBracket(String str)
		{
			if(str.startsWith("["))
				str = str.substring(1);
			
			if(str.endsWith("]"))
				str = str.substring(0, str.length()-1);
						
			return str;
			
		}
		
		public static String removeStartEndCurlyBracket(String str)
		{
			if(str.startsWith("{"))
				str = str.substring(1);
			
			if(str.endsWith("}"))
				str = str.substring(0, str.length()-1);
						
			return str;
			
		}
		
		public static String replaceQuotes(String str)
		{
			if(str.startsWith("\""))
				str = str.substring(1);
			
			if(str.endsWith("\""))
				str = str.substring(0, str.length()-1);
						
			return str;
			
		}
		
		public static String evalEquals(String expression) 
		{
			String status = "Fail";
			try 
			{
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("js");
				Object r;
				r = engine.eval(expression);

				status =  (Boolean.TRUE.equals(r)) ?"Pass": "Fail";
			} 
			catch (ScriptException e)
			{
				e.printStackTrace();
			}

			return status;
		}

		public static Object evalExpression(String expression) 
		{
			Object r = null;
			try 
			{
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("js");
				
				r = engine.eval(expression);

			} 
			catch (ScriptException e)
			{
				e.printStackTrace();
			}

			return r;
		}

}
