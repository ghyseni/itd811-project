package application.util;

import java.sql.*;
 
/**
 * Created by ONUR BASKIRT on 22.02.2016.
 */
public class DBUtil {
    //JDBC Driver
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
 
    //Connection fields
    private static Connection conn = null;
    static final String DATABASE_NAME = "tickets";
    static final String DB_URL = "jdbc:mysql://localhost/" + DATABASE_NAME;
	static final String USER = "root";
	static final String PASS = "";
 
 
    //Connect to DB
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        // JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Problem with JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
 
        //Establish connection
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
        Statement stmt = null;
        ResultSet rs = null;
        try {
            dbConnect();
            System.out.println("Select statement: " + queryStmt);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred while executing query. " + e);
            throw e;
        } 
        return rs;
    }
 
    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
            System.out.println("Update statement: " + sqlStmt);
        } catch (SQLException e) {
        	 System.out.println("Problem occurred while executing query. " + e);
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }
}