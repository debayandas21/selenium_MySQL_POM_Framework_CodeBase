package Automation.POM.framework.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import Automation.POM.framework.core.BaseClass;

public class Jdbc_databse_connectivity extends BaseClass {

	public Connection con;
	public Statement stmnt;

	/**
	 * This method is to create JDBC connection
	 * 
	 * @throws Exception, SQLException
	 */


	public void create_jdBC_connection(ExtentTest report) throws Exception, SQLException {
		try {

			String db_connectionPath = globalConfig.globalParameter.get("Databse_connection_path") + "profile_database";
			String userName = globalConfig.globalParameter.get("DB_UserName");
			String password = decodeEncryptedData(globalConfig.globalParameter.get("DB_Password"));

			con = DriverManager.getConnection(db_connectionPath, userName, password);

			logger.info("JDBC connection has been established");

		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("failed to establish the DB connection" + e.getMessage());

		}

	}

	/**
	 * get data from DB
	 * 
	 * @param testData
	 * @param tableName
	 * @throws SQLException
	 * @throws Exception
	 */

	
	public void get_data_fromDB(Hashtable<String, String> db_testData, String tableName, int personID)
			throws SQLException, Exception {
		try {

			String getdata_query = "Select username, password from " + tableName + " where personID=" + personID;
			stmnt = con.createStatement();

			
			ResultSet rs = stmnt.executeQuery(getdata_query); // get data from DB by executing then given query

			while (rs.next()) {

				String userName = rs.getString("username");
				String password = rs.getNString("password");

				db_testData.put("login_userName", userName);
				db_testData.put("loginPass", password);

			}

			con.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
