package Utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
/**
 * This class contains methods to fill forms with random data example DOB,License Number ,Passport Number etc.
 * @author chinnarao.vattam
 *
 */
public class FormFiller {

	/**
	 * This Method generates the a random DOB
	 * @return the random DOB 
	 * @author Chinnarao.Vattam
	 */
	
	public static String generateDOBYear() {
		Random randomGenerator = new Random();
		int dOByear = 1960 + randomGenerator.nextInt(40);
		return Integer.toString(dOByear);
	}

	/**
	 * This Method generates random expiry year 
	 * @return the random expiry year 
	 * @author Chinnarao.Vattam
	 */
	public static String generateExpiryYear() {
		Calendar calendar = Calendar.getInstance();
		Random randomGenerator = new Random();
		int year = calendar.get(Calendar.YEAR);
		int expiryYear = year + randomGenerator.nextInt(5) + 1;
		return Integer.toString(expiryYear);
	}

	/**
	 * This method generates the random Month of the year
	 * @return the random Month of the year
	 * @author Chinnarao.Vattam
	 */
	public static String generateMonth() {
		Random randomGenerator = new Random();
		int month = 1 + randomGenerator.nextInt(12);
		return Integer.toString(month);
	}

	/**
	 * This Method generates the random month name from the calendar year
	 * @return random month name from the calendar year
	 * @author Chinnarao.Vattam
	 */
	public static String generateNameOfMonth() {
		String [] strMonth = {"January", "February", "March", "April", "May", "June", "July" , "August", "September", "October", "November", "December"};
		Random randomGenerator = new Random();
		int value = randomGenerator.nextInt(strMonth.length);
		String intialLetters = strMonth [value];
		String strMonthName = intialLetters ;
		return strMonthName;
	}
	
	/**
	 * This method generates the calendar day of the Month
	 * @return the calendar day of the Month
	 * @author Chinnarao.Vattam
	 */
	public static String generateCalendarDay() {
		Random randomGenerator = new Random();
		int day = 1 + randomGenerator.nextInt(28);
		return Integer.toString(day);
	}

	/**
	 * This method generates the License Number
	 * @return the License Number
	 * @author Chinnarao.Vattam
	 */
	public static String generateLicenseNumber() {
		return FormFiller.generateRandomNumber(7);
	}
	
	public static String generateLicenseNumber(String province) {
		String dlnumber = null;
		
		switch (province.toUpperCase()) {
		
		case "AB"://123456-789
		case "ALBERTA":
			dlnumber = generateRandomNumber(9);
			break;
		
		case "BC": //1234567
		case "BRITISH COLUMBIA":
		case "NB":
		case "NEW BRUNSWICK":
			dlnumber = generateRandomNumber(7);
			break;
		
		case "MB": //AA-AA-AA-A123AA
		case "MANITOBA":
			dlnumber = generateRandomName().substring(0, 7) + generateRandomNumber(3) + "MB";
			break;
			
		case "NL": //A123456789
		case "NEWFOUNDLAND AND LABRADOR":
			dlnumber = "N" + generateRandomNumber(7);
			break;
		
		case "NS": //SAMPL123456789
		case "NOVA SCOTIA":
			dlnumber = generateRandomName().substring(0, 5) + generateRandomNumber(9);
			break;
		
		case "ON": //A1234-56789-12345
		case "ONTARIO":
			dlnumber = "O" + generateRandomNumber(7) + generateRandomNumber(7);
			break;
		
		case "PE": //567845
		case "PRINCE EDWARD ISLAND":
		case "NU":
		case "NUNAVUT":
		case "NT":
		case "NORTHWEST TERRITORIES":
			dlnumber = generateRandomNumber(6);
			break;
		
		case "QC": //A1234-567898-76
		case "QUEBEC":
			dlnumber = "Q" + generateRandomNumber(6) + generateRandomNumber(6);
			break;
		
		case "SK": //56784532
		case "SASKATCHEWAN":
			dlnumber = generateRandomNumber(8);
			break;
		
		case "YT": //1234567890
		case "YUKON":
			dlnumber = generateRandomNumber(10);
			break;

		default:
			break;
		}
		
		return dlnumber;
	}

	/**
	 * This the random passport number for the credit evaluation 
	 * @return the passport number 
	 * @author Chinnarao.Vattam
	 */
	public static String generatePassportNumber() {
		String [] intialList = {"CH", "FI", "RO", "CA", "GA", "FB", "GG" , "CR", "AD", "KA", "DT", "BC", "BT"};
		Random randomGenerator = new Random();
		int intial = randomGenerator.nextInt(intialList.length);
		String intialLetters = intialList [intial];
		String passportNumber = FormFiller.generateRandomNumber(6);
		String strPassportNumber = intialLetters +  passportNumber;
		return strPassportNumber;
	}
	
	/**
	 * This method generates the random CVV for the credit card validation
	 * @return the random CVV
	 * @author Chinnarao.Vattam
	 */
	public static String generateCVVNumber() {
		return FormFiller.generateRandomNumber(3);
	}
	
	/**
	 * This method generates the random phone number
	 * @return the random phone number
	 * @author Chinnarao.Vattam
	 */
	public static String generatePhoneNumber() {
		String strPhone = FormFiller.generateRandomNumber(4);
		String strPhoneNumber= "100000" + strPhone;
		return strPhoneNumber;
	}
	
	/**
	 * To generate SIM card number for update SIM card. get random number for last 5 digit. 
	 * @return strSimNumber, string of 15 digit SIM card number (first 5 digit are already there at input area.) 
	 */
	public static String generateSIMNumber() {
		String strSim = FormFiller.generateRandomNumber(5);
		String strSimNumber= "3701020003" + strSim;
		return strSimNumber;
	}
	
	/**
	 * This method generates the random name
	 * @return the random name
	 * @author Chinnarao.Vattam
	 */
    public static String generateRandomName (){
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 3;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int count = 0; count < targetStringLength; count++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedName = buffer.toString();
	 
	    return generatedName;
    }

    /**
     * Generate random number of certain length
     * @param intNumDigit, Int, the length of the number
     * @return String of specific digits number
     * @author ning.xue
     */
	public static String generateRandomNumber(int intNumDigit) {
		int base = (int) Math.pow(10, intNumDigit - 1);
		Random randomGenerator = new Random(); 
		int intNumber = base + randomGenerator.nextInt(9 * base);		
		return  Integer.toString(intNumber);
	}  
    
	/**
	 * This method generates the random email
	 * @return the random email
	 * @author Chinnarao.Vattam
	 */
	public static String generateEmail() {
		Random randomGenerator = new Random();  
		int randomInt = randomGenerator.nextInt(10000);
		String strEmail ="auto." + generateRandomName() + randomInt +"@mailinator.com";
		return strEmail;
	} 
	
	/**
	 * This method generates the date time in the YYYY-MM-dd-HH-mm-ss format
	 * @return the date time in the YYYY-MM-dd-HH-mm-ss format
	 * @author Chinnarao.Vattam
	 */
	public static String generatelocalDateTime() {
	    String strlocalDateTime = ZonedDateTime.now(ZoneId.of("America/Montreal")).format(DateTimeFormatter.ofPattern("YYYY-MM-dd-HH-mm-ss"));
		return strlocalDateTime;
	} 
	
	/**
	 * this method converts the currency with "." to support French  currency 
	 * @param strCurrency in French format
	 * @return Currency in French format
	 * @author Chinnarao.Vattam
	 */
	public static String currencyConverter(String strCurrency) {
		String strStandardCurrency=strCurrency.replaceAll(",","."); 
		return strStandardCurrency;
	}
	}
