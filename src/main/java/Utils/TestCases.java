package Utils;

import com.opencsv.bean.CsvBindByName;

public class TestCases {
	
	@CsvBindByName(column = "ID")
    private String ID;

    @CsvBindByName(column = "Skip")
    private String Skip;

    @CsvBindByName(column = "Status")
    private String Status;
    
    @CsvBindByName(column = "TestID")
    private String TestID;
    
    @CsvBindByName(column = "Type")
    private String Type;
    
    @CsvBindByName(column = "Path")
    private String Path;
    
    @CsvBindByName(column = "Method")
    private String Method;
    
    @CsvBindByName(column = "APIStatus")
    private String APIStatus;
    
    @CsvBindByName(column = "Request")
    private String Request;

    @CsvBindByName(column = "Header")
    private String Header;
    
    @CsvBindByName(column = "Wait")
    private String Wait;
    
    @CsvBindByName(column = "Retry")
    private String Retry;
    
    @CsvBindByName(column = "Configure")
    private String Configure;
    
    @CsvBindByName(column = "ReadResponse")
    private String ReadResponse;
    
    @CsvBindByName(column = "Assert")
    private String Assert;
    
    @CsvBindByName(column = "PreRequest")
    private String PreRequest;
    
    @CsvBindByName
    private String TestCase;

    public String getID()
    {
    	return ID;
    }
    
    public String getSkip()
    {
    	return Skip;
    }
    
    public String getStatus()
    {
    	return Status;
    }
    
    public void setStatus(String status)
    {
    	Status = status;
    }
    
    public String getTestID()
    {
    	return TestID;
    }
    
    public String getType()
    {
    	return Type;
    }
    
    public String getPath()
    {
    	return Path;
    }
    
    public String getMethod()
    {
    	return Method;
    }
    
    public String getAPIStatus()
    {
    	return APIStatus;
    }
    
    public String getRequest()
    {
    	return Request;
    }
    
    public String getHeader()
    {
    	return Header;
    }
    
    public String getWait()
    {
    	return Wait;
    }
    
    public String getRetry()
    {
    	return Retry;
    }
    
    public String getConfig()
    {
    	return Configure;
    }
    
    public String getReadResponse()
    {
    	return ReadResponse;
    }
    
    public String getAssert()
    {
    	return Assert;
    }
    
    public String getPreRequest()
    {
    	return PreRequest;
    }
}
