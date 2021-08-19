package Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;

public class MainConfig {

	
	
	public static Properties properties = new Properties();
	
	public MainConfig()
	{
		FileInputStream fis;
		
		try 
		{
			fis = new FileInputStream(Paths.get("").toAbsolutePath().toString()+ "/config.properties");
			properties.load(fis);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
