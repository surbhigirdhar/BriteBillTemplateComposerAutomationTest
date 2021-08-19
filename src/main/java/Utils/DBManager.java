package Utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Reporter;

import Config.EnvironmentConf;
import java.sql.*;  

public class DBManager {

	static Connection connection = null;
	
	public static String RunDBQuery(String dbType, String sql) throws SQLException
	{
		ResultSet rs=null;
		Statement stmt = null;
		String result ="";
	
		try { 
			if(dbType.contentEquals("Oracle"))
			{
				ConnectOracle();
			}
			
			stmt = connection.createStatement();
			
			if(sql.toLowerCase().startsWith("update"))
			{
				//int result = stmt.executeUpdate(sql);
			}
			else
			{
				rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					result = rs.getString(1);
				}
				else
				{
					result="Fail";
					
				}
			}
						
			connection.close();
		}
		catch (Exception e) 
		{
	/*		if (e.getMessage().toLowerCase().contains("warning"))
				LoggerMaster.writeIntoHTMLLog(e.getMessage(),"Warning", "DB");
			else
				LoggerMaster.writeIntoHTMLLog(e.getMessage(),"Fail", "DB");
			e.printStackTrace();
	*/		
			connection.close();
		}
		
		return result;
	}
	
	public static void ConnectOracle() 
	{
	
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
	//		connection=DriverManager.getConnection("jdbc:oracle:thin:@"+EnvironmentConf.V21DBHostName+":"+EnvironmentConf.V21DBPort +":"+ EnvironmentConf.V21DBSID, EnvironmentConf.V21DBUserName,EnvironmentConf.V21DBPassword);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	/*
	 * public static void runWithSQLReader(String dbType, String sqlFile, String
	 * createFileOnPath) throws SQLException { ArrayList<String> queries_01;
	 * SQLReader sqlread = new SQLReader(); queries_01 =
	 * sqlread.createQueries(sqlFile); Statement stmt = null;
	 * 
	 * String fileName="";
	 * 
	 * try { GetSession(); if (dbType.contentEquals("PostgreSQL")) {
	 * LoggerMaster.writeIntoHTMLLog("Connecting to PostgreSQL DB ", "Info");
	 * ConnectPostgres(); } else if (dbType.contentEquals("Sybase")) {
	 * LoggerMaster.writeIntoHTMLLog("Connecting to Sybase DB", "Info");
	 * ConnectSybase(); }
	 * 
	 * int lineNo = 0; String fileContent = "";
	 * 
	 * try { stmt = connection.createStatement();
	 * 
	 * boolean isRS = stmt.execute(queries_01.get(0));
	 * 
	 * while (isRS || (stmt.getUpdateCount() != -1)) { if (!isRS) { isRS =
	 * stmt.getMoreResults(); continue; } ResultSet rs = stmt.getResultSet();
	 * ResultSetMetaData meta = rs.getMetaData();
	 * 
	 * // get column names; int colCount = meta.getColumnCount(); String[] columns =
	 * new String[colCount + 1];
	 * 
	 * for (int c = 1; c < colCount + 1; c++) columns[c] = meta.getColumnLabel(c);
	 * 
	 * while (rs.next()) { for (int c = 1; c < colCount + 1; c++) { Object val =
	 * rs.getObject(c); if (val == null) continue;
	 * 
	 * if (columns[c].contentEquals("lignes_fichier")) { if(lineNo == 0) fileName =
	 * rs.getObject(c).toString(); else fileContent = fileContent + rs.getObject(c)
	 * + "\n";
	 * 
	 * lineNo++; }
	 * 
	 * } }
	 * 
	 * rs.close(); isRS = stmt.getMoreResults(); } stmt.close();
	 * 
	 * OFRConfig.properties.setProperty("FILE_NAME", fileName);
	 * CommonUtils.createNewInputFile(createFileOnPath);
	 * 
	 * FileManager fileManager = new FileManager();
	 * fileManager.WriteIntoFile(OFRConfig.properties.getProperty(
	 * "INPUT_FILE_FULL_PATH"), fileContent);
	 * 
	 * System.out.println("File created at :"+
	 * OFRConfig.properties.getProperty("INPUT_FILE_FULL_PATH"));
	 * 
	 * } catch (Exception ex) { System.out.println(ex.getMessage()); } } catch
	 * (Exception e) { if (e.getMessage().contains("warning"))
	 * LoggerMaster.writeIntoHTMLLog(e.getMessage(), "Warning"); else
	 * LoggerMaster.writeIntoHTMLLog(e.getMessage(), "Fail"); e.printStackTrace();
	 * 
	 * connection.close(); session.disconnect(); }
	 * 
	 * }
	 */
	
	
	

	
}
