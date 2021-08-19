package extentreport;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.MessagingException;

import org.apache.commons.net.ftp.FTPClient;


/**
 * This File upload class contains extent reports upload method to upload the generated extent reports to the FTP server
 * This method copy the reports from the absolute path of the test executed  mechine to the FTP server with relative path of the corresponding projects 
 *
 * @author chinnarao.vattam
 */
public  class FileUpload {
	
	
	/**
	 * Capture the reports from the test executed  machine and copy it to the FTP server with the corresponding projects folder.
	 * @throws MessagingException Email Message Exception
	 * @return String, remoteDirectoryPath+strFileName, path + file name
	 */
    public static String extentReportsUpload() throws MessagingException  {
    	
    	 FTPClient client = new FTPClient();
         FileInputStream fis = null;
         String strFileName="";
         String remoteDirectoryPath="";
         String srcFolderPath;
        try {
	        client.connect(System.getenv("FTP_SERVER"));
	        client.login(System.getenv("FTP_USERNAME"), System.getenv("FTP_PASSWORD"));
	        // Change the format and folder as per your project needs             
	        client.setFileType(FTPClient.BINARY_FILE_TYPE);       
	        
           remoteDirectoryPath= ExtentManager.relativePathToReport;  	   
    	   srcFolderPath = ExtentManager.absolutePathToReport;
    	   
    	   File folder = new File(srcFolderPath);  
    	   if (folder.isDirectory()) {
        	   client.makeDirectory(remoteDirectoryPath);
        	   client.changeWorkingDirectory(remoteDirectoryPath);
    	     for (File file : folder.listFiles()) {
    		   fis = new FileInputStream(srcFolderPath+"/"+ file.getName()); 
    		   if(file.getName().contains(".html"))
    		   {
    			   strFileName = file.getName();
    		   }
    		   client.storeFile(file.getName(), fis);
               }
    	   client.changeToParentDirectory();
    	        }
    	   else {
        	   InputStream fisf = folder.toURI().toURL().openStream();
                    client.storeFile(folder.getName(), fisf);
                 }
    	   client.logout();
    	       	             
    	   //SendEmail.sendEmail(remoteDirectoryPath+strFileName);
    	   
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (remoteDirectoryPath+strFileName);
    }

    
 
      
}

