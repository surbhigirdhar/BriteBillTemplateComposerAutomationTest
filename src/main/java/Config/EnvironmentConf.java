package Config;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class EnvironmentConf {

	public static String ENVIRONMENT_NAME;
	
	public static String URL;

	public static LinkedHashMap<String, HashMap<String, String>> envKeyMaps =  new LinkedHashMap<String, HashMap<String, String>>();
	
	public static LinkedHashMap<String, HashMap<String, String>> environmentDetails=  new LinkedHashMap<String, HashMap<String, String>>();
	
	public static void SetEnvironmentDetails(String envName) throws  SQLException
	{
		if(environmentDetails.containsKey(envName))
		{
			HashMap<String, String> envDetails = environmentDetails.get(envName);
			
			EnvironmentConf.URL =envDetails.get("URL");

			System.out.println("Environment is set to :" + envName);
			
		}
	
	}
	
	public static String getEnvKeyMap(String keyVal)
	{
		String val="";
		
		for (Entry<String, String> envKeys : envKeyMaps.get(EnvironmentConf.ENVIRONMENT_NAME).entrySet())
		{
				if(keyVal.contains(envKeys.getKey()))
				{
					val= envKeys.getValue();
					break;
				}
		}
		
		return val;
	}
}
