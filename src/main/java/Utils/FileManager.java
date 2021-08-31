package Utils;
import java.io.File;
import Config.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringUtils;


public class FileManager 
{

	public static void deleteDirectory(String pathDir) throws IOException
	{
		File dir = new File(pathDir);
		File[] allFiles = dir.listFiles();

		for(File filename : allFiles)
		{
			filename.delete();
		}

	}

	public static void copyFile(String pathDir, String TestCaseID) throws IOException
	{
		File dir = new File(pathDir);
		File[] allFiles = dir.listFiles();

		for(File filename : allFiles)
		{
			//filename.renameTo(System.getProperty("user.dir") + "//Bills//" + TestCaseID + ".pdf");
			Path srcPath = Paths.get(filename.toURI());
			Path destPath = Paths.get(MainConfig.properties.getProperty("Bill_PATH") + TestCaseID + ".pdf");
			Files.deleteIfExists(destPath);
			Files.copy(srcPath,destPath);
			Files.deleteIfExists(srcPath);

		}

	}

		public static File  getPDFFile()
	{
		File dir = new File (MainConfig.properties.getProperty("PDF_PATH"));
		File[] allFile = dir.listFiles();
		File pdfFile =null;

		for(File filename : allFile)
		{
			if(filename.toString().endsWith(".pdf")) {
				pdfFile = filename;
				break;
			}

		}
		return pdfFile;
	}
	public String CreateNewInputFile(String fileName) throws IOException
	{
		Path path = Paths.get( MainConfig.properties.getProperty("INPUT_FILE_PATH"), fileName);
		Files.deleteIfExists(path);
		Files.createFile(path);
		return path.toString();
	}
	
	public String CreateNewInputFile(String fileName, String folderPath) throws IOException
	{
		Path dir = Paths.get(folderPath);
		if(!Files.isDirectory(dir))
		{
			Files.createDirectory(dir);
		}

		Path path = Paths.get( folderPath, fileName);
		Files.deleteIfExists(path);
		Files.createFile(path);
		return path.toString();
	}

	public String CreateNewTempFile(String fileName, String folderPath) throws IOException
	{
		Path path = Paths.get( folderPath);
		path = Files.createTempFile(path,fileName, new SimpleDateFormat("YYYYMMddhhmmss").format(new Date()));
		return path.toString();
	}

	public void WriteIntoFile(String pathStr, String content)
	{
		try 
		{
			Path path = Paths.get(pathStr);
			Files.write(path, content.getBytes(), StandardOpenOption.APPEND);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void WriteIntoFileReplaceAll(String pathStr, String content)
	{
		try 
		{
			Path path = Paths.get(pathStr);
			Files.write(path, content.getBytes(), StandardOpenOption.WRITE);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean checkEndOfLog(String pathStr)
	{
		try 
		{
			 String data = ""; 
			 data = new String(Files.readAllBytes(Paths.get(pathStr))); 
			 
			 if(data.contains("</scenarios>"))
			 {
				 return true;
			 }
		
		} catch (IOException e) {
			return false;
			
		}
		return false;
	}
	
//	guid=<<RANDOM:5>>
	public String replaceTagInJSONFile(String originalFile, HashMap<String, String> keepRefer) 
	{
	//	String tempFile = originalFile.split("\\.")[0]+ "_" + MainConfig.properties.getProperty("TIMESTAMP") +".json";
	
		String data = ""; 
		
		try 
		{
			//create temp file
			
	//		String tempFilePath = System.getProperty("user.dir") + MainConfig.properties.getProperty("APP_PATH")
	//		+ "/"+ MainConfig.properties.getProperty("RELEASE") +MainConfig.properties.getProperty("JSON_TEMPORARY_FILES_PATH");
			
	//		Path tempFilepath = Paths.get(tempFilePath , tempFile);

		//	Files.deleteIfExists(tempFilepath);
		//	Files.createFile(tempFilepath);
			
			//replace tag
		
			String templateFilePath = System.getProperty("user.dir") + MainConfig.properties.getProperty("APP_PATH") 
			+ "/"+ MainConfig.properties.getProperty("RELEASE") + MainConfig.properties.getProperty("JSON_TEMPLATE_PATH");
			
			data = new String(Files.readAllBytes(Paths.get(templateFilePath ,originalFile))); 
			
			String[] tagArr = StringUtils.substringsBetween(data, "<<", ">>");

			if(tagArr != null) {
				for (String params : tagArr) {
					String keepReferVal = keepRefer.get(params);

					/*
					 * if(params.contains("=")) { params="#"+keepReferVal.split("=")[0]+"#";
					 * //<<guid>> keepReferVal = keepReferVal.split("=")[1]; //42342 }
					 */
					if (keepReferVal == null) {
						keepReferVal = "";
					}
					if (data.contains(params)) {
						data = data.replaceAll("<<" + params + ">>", keepReferVal);

					}

				}

			}
		//	 Files.write(tempFilepath, data.getBytes(), StandardOpenOption.WRITE);
		
		} catch (Exception e) 
		{
		}
		
		return data;
		
	}
	
	public String replaceTagInXMLFile(String originalFile, HashMap<String, String> keepRefer) 
	{
	//	String tempFile = originalFile.split("\\.")[0]+ "_" +( new SimpleDateFormat("YYYYMMddhhmmss").format(new Date())).toString() +"_"+ CommonUtils.generateRandomDigits(4) +".xml";
		//replace tag
		String data = ""; 
		try 
		{ 
	/*		String tempFilePath = System.getProperty("user.dir") + MainConfig.properties.getProperty("APP_PATH")
			+ "/"+ MainConfig.properties.getProperty("RELEASE") +MainConfig.properties.getProperty("SOAP_REQUEST_TEMPORARY_FILES_PATH");
			//create temp file
			Path tempFilepath = Paths.get(tempFilePath , tempFile);

			Files.deleteIfExists(tempFilepath);
			
			Files.createFile(tempFilepath);
	*/
			String templateFilePath = System.getProperty("user.dir") + MainConfig.properties.getProperty("APP_PATH") 
			+ "/"+ MainConfig.properties.getProperty("RELEASE") + MainConfig.properties.getProperty("SOAP_REQUEST_TEMPLATE_PATH");
			
			data = new String(Files.readAllBytes(Paths.get(templateFilePath ,originalFile))); 
			 
			String[] tagArr = StringUtils.substringsBetween(data, "#", "#");
			
			if(tagArr == null)
			{
		//		Files.write(tempFilepath, data.getBytes(), StandardOpenOption.WRITE);
			}
			else
			{
				for(String params : tagArr)
				{
					String keepReferVal = keepRefer.get(params);
					
					/*
					 * if(params.contains("=")) { params="#"+keepReferVal.split("=")[0]+"#";
					 * //<<guid>> keepReferVal = keepReferVal.split("=")[1]; //42342 }
					 */
					 if(data.contains(params))
					 {
						 data = data.replaceAll("#"+params+"#", keepReferVal);
				//		 Files.write(tempFilepath, data.getBytes(), StandardOpenOption.WRITE);
					 }
				}
			}
		
		} catch (Exception e) 
		{
			System.out.println(e);
		}
		
		return data;
		
	}
	
	public static boolean ifFileExists(String filePath)
	{
		Path path = Paths.get(filePath);
			
		return Files.exists(path);
			
		
	}
    

}
