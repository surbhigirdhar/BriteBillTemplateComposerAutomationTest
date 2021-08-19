package Utils;

import com.opencsv.bean.CsvBindByName;

public class CommonDB 
{
	@CsvBindByName(column = "ID")
    private String ID;

    @CsvBindByName(column = "Query")
    private String Query;

    @CsvBindByName(column = "EXP_Result")
    private String EXP_Result;
    
    @CsvBindByName(column = "SAVE_PARAM_NAME")
    private String SAVE_PARAM_NAME;

	public String getID() {
		return ID;
	}

	public String getQuery() {
		return Query;
	}

	public String getEXP_Result() {
		return EXP_Result;
	}

	public String getSAVE_PARAM_NAME() {
		return SAVE_PARAM_NAME;
	}

}
