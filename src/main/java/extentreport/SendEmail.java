package extentreport;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendEmail {

	/**
	 * This method will sent the email after the test execution suites completes
	 * @param strMessageBody Email body content
	 * @param strSuiteName Name of the TestNG Suite - ISuite.getName()
	 * @throws IOException exception
	 * @throws MessagingException exception
	 * @author Mirza.Kamran
	 */
    public  static void sendEmail(String strSuiteName, String strMessageBody) throws IOException, MessagingException {


    	Properties props = new Properties();
        props.put("mail.smtp.user", "automation.service@RogersDigitalQaAutomation.ca");//"automation_service@outlook.com"); //automation.service.rogers@gmail.com
        props.put("mail.smtp.host", "10.18.97.209");//"smtp-mail.outlook.com"); smtp.office365.com //smtp.gmail.com
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "false");
        props.put("mail.smtp.socketFactory.port", "587");       
        props.put("mail.smtp.socketFactory.fallback", "true");

        try
        {
        Authenticator auth = new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("automation.service@RogersDigitalQaAutomation.ca", "rogers123");  //"sbgrpzghtinvrvdx"
            }
          };
          
          String msgBody = composeEmailBody(strMessageBody);
          MimeBodyPart mimeBodyPart=new MimeBodyPart();
          mimeBodyPart.setContent(msgBody,"text/html");
          MimeMultipart multipart=new MimeMultipart();
          multipart.addBodyPart(mimeBodyPart);  
          Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);
        msg.setContent(multipart);       
        msg.setSubject("Automation Testing Result - " + strSuiteName);
        msg.setFrom(new InternetAddress("automation.service@RogersDigitalQaAutomation.ca"));
        //msg.addRecipients(Message.RecipientType.TO, new InternetAddress(getEmailReceipient()));
        msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(getEmailReceipient(strSuiteName)));
        msg.setText(msgBody,"UTF-8","html");
        Transport.send(msg);

        }catch (MessagingException mex) {
        	mex.printStackTrace();
           System.out.println("It seems the email report link wasn't successfully sent to user : "+System.getProperty("user.name")+", alternatively you can find the html link here: ");
           System.out.println("http://10.18.97.209/"+strMessageBody);
        }catch(Exception e){
        	System.out.println("Sorry something went wrong with the automated email !");
        	e.printStackTrace();
		}
    }
    
    /**
     * This method will create the email body
     * @param strReportLinkAddress string containing the Extent report link
     * @return string having the email body content
     * @author Mirza.Kamran
     */
    public static String composeEmailBody(String strReportLinkAddress) {
    	 String body = "<i> Hey There!! </i><br><br>";
    	 body += "<i> The Latest Test execution report link is below</i><br><br>";         
         body +=	 "<a >http://10.18.97.209/"+strReportLinkAddress+"</a><br><br>";
         body += "<font color=red>Thank you</font>";
         
         return body;
    }
    
   
    /**
     * Gets the email distribution list
     * @return string email distribution list
     * @author Mirza.Kamran
     */
    public static String getEmailReceipient() {  
    				Collection<String> receipientList = new LinkedList<String>() ;	
    	    		receipientList.add("mirza.kamran"); 
    	    		receipientList.add("chinnarao.vattam"); 
    	    		receipientList.add("saurav.goyal");
    	    		receipientList.add("aditya.dhingra");
    	    		receipientList.add("ning.xue");
    	    		receipientList.add("rajesh.varalli1");
    	    		receipientList.add("karthic.hasan");
    	    		receipientList.add("vedachalam.vasudevan");    	 
    	    		receipientList.add("rohit.kumar5");   
    	    		receipientList.add("naina.agarwal");
					receipientList.add("subha.tirumoorthy");
		if(receipientList.contains(System.getProperty("user.name").toLowerCase()))
    	{
    		return System.getProperty("user.name")+"@rci.rogers.com";
    	}
    	else {
    		
    		return "aditya.dhingra@rci.rogers.com,ning.xue@rci.rogers.com,rajesh.varalli1@rci.rogers.com,"
    				+ "mirza.kamran@rci.rogers.com,chinnarao.vattam@rci.rogers.com,saurav.goyal@rci.rogers.com,"
    				+ "karthic.hasan@rci.rogers.com,vedachalam.vasudevan@rci.rogers.com,"
    				+ "rohit.kumar5@rci.rogers.com,naina.agarwal@rci.rogers.com,subha.tirumoorthy@rci.rogers.com";
    	}
		
    	    	
    }
    
   
    public static String getEmailReceipient(String strSuiteName) {
    	strSuiteName = strSuiteName.toLowerCase().trim();
    	Collection<String> receipientList = new LinkedList<String>() ;	
		receipientList.add("mirza.kamran"); 
		receipientList.add("chinnarao.vattam"); 
		receipientList.add("saurav.goyal");
		receipientList.add("ning.xue");
		receipientList.add("rajesh.varalli1");
		receipientList.add("karthic.hasan");
		receipientList.add("vedachalam.vasudevan");
		receipientList.add("rohit.kumar5");
		receipientList.add("naina.agarwal");
		receipientList.add("rohit.n");
		receipientList.add("dharani.up");
		receipientList.add("sidhartha.vadrevu");
		receipientList.add("shilpi.bhatnagar");
		receipientList.add("praveen.kumar7");
		receipientList.add("subha.tirumoorthy");
		if(strSuiteName.contains("mbh") || strSuiteName.contains("mybusinesshub")) {
    		return "rajesh.varalli1@rci.rogers.com,vedachalam.vasudevan@rci.rogers.com";
    	} else if(strSuiteName.contains("bfa") || strSuiteName.contains("buyflows")) {
    		return "shilpi.bhatnagar@rci.rogers.com,sidhartha.vadrevu@rci.rogers.com,praveen.kumar7@rci.rogers.com";
		} else if(strSuiteName.contains("cbs") || strSuiteName.contains("search")) {
			return "rajesh.varalli1@rci.rogers.com,saurav.goyal@rci.rogers.com,naina.agarwal@rci.rogers.com";
    	} else if(strSuiteName.contains("ss") || strSuiteName.contains("selfserve") || strSuiteName.toUpperCase().trim().endsWith("SS")
    			|| strSuiteName.toUpperCase().trim().endsWith("SS]")) {
    		return "ning.xue@rci.rogers.com,mirza.kamran@rci.rogers.com,subha.tirumoorthy@rci.rogers.com";
    	} else if(strSuiteName.contains("redesign") || strSuiteName.contains("browse")) {
    		return "ray.azeez@rci.rogers.com,nimmy.george@rci.rogers.com,karthic.hasan@rci.rogers.com";
    	} else if(strSuiteName.contains("ch") || strSuiteName.contains("solaris") || strSuiteName.contains("connectedhome")) {
    		return "aditya.dhingra@rci.rogers.com,chinnarao.vattam@rci.rogers.com,dharani.up@rci.rogers.com,rohit.n@rci.rogers.com";
    	} else {
    		return System.getProperty("user.name")+"@rci.rogers.com";
    	}
    }
    
    
    
}