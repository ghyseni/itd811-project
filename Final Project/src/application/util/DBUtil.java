package application.util;

import java.sql.*;
 
/**
 * Created by ONUR BASKIRT on 22.02.2016.
 */
public class DBUtil {
    //Declare JDBC Driver
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
 
    //Connection
    private static Connection conn = null;
 
    //Connection String
    static final String DATABASE_NAME = "tickets";
    static final String DB_URL = "jdbc:mysql://localhost/" + DATABASE_NAME;
	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
 
 
    //Connect to DB
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        //Setting Oracle JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Problem with JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
 
        System.out.println("Oracle JDBC Driver Registered!");
 
        //Establish the Oracle Connection using Connection String
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection Failed!" + e);
            e.printStackTrace();
            throw e;
        }
    }
 
    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
           throw e;
        }
    }
 
    //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");
 
            //Create statement
            stmt = conn.createStatement();
 
            //Execute select (query) 
            rs = stmt.executeQuery(queryStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery. " + e);
            throw e;
        } 
        //Return CachedRowSet
        return rs;
    }
 
    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate  with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate. " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
}