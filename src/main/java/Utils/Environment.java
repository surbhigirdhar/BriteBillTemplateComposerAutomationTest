package Utils;

import com.opencsv.bean.CsvBindByName;

public class Environment {
	
	@CsvBindByName(column = "EnvName")
    private String EnvName;

    @CsvBindByName(column = "URL")
    private String URL;


	public String getEnvName() {
		return EnvName;
	}

	public String getURL() {
		return URL;
	}



}
