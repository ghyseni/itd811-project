package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author gresehyseni
 * 
 *         Final Project - 12/06/2017
 * 
 *         Provides the interaction with the database directly.
 */
public class UserDAO {

	/**
	 * SELECT User by ID
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static User searchUser(String userId) throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		String selectStmt = "SELECT * FROM users WHERE user_id=" + userId;

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getUserFromResultSet method and get user object
			User user = getUserFromResultSet(rs);

			// Return user object
			return user;
		} catch (SQLException e) {
			System.out.println("While searching an user with " + userId + " id, an error occurred: " + e);
			// Return exception
			throw e;
		}
	}

	/**
	 * SELECT User by username and password. Used for authentication.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static User searchUserByUsernamePassword(String username, String password)
			throws SQLException, ClassNotFoundException {

		String selectStmt = "SELECT * FROM users WHERE username='" + username + "' and password='" + password + "'";

		// Execute SELECT statement
		try {
			ResultSet rsUser = DBUtil.dbExecuteQuery(selectStmt);
			User user = getUserFromResultSet(rsUser);
			return user;
		} catch (SQLException e) {
			System.out.println("While searching an user with '" + username + "' username, an error occurred: " + e);
			throw e;
		}
	}

	/**
	 * Set User Object's attributes from DB ResultSet.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static User getUserFromResultSet(ResultSet rs) throws SQLException {
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setUserId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setRole(rs.getString("role"));
		}
		return user;
	}

	/**
	 * SELECT Users
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ObservableList<User> searchUsers() throws SQLException, ClassNotFoundException {

		String selectStmt = "SELECT * FROM users";

		// Execute SELECT statement
		try {
			ResultSet rsUsers = DBUtil.dbExecuteQuery(selectStmt);
			ObservableList<User> userList = getUserList(rsUsers);
			return userList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has been failed: " + e);
			throw e;
		}
	}

	/**
	 * Set User Objects' attributes from DB ResultSet.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static ObservableList<User> getUserList(ResultSet rs) throws SQLException, ClassNotFoundException {
		ObservableList<User> userList = FXCollections.observableArrayList();

		while (rs.next()) {
			User user = new User();
			user.setUserId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setRole(rs.getString("role"));

			userList.add(user);
		}
		return userList;
	}

	/**
	 * Update user
	 * 
	 * @param userId
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param role
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void updateUser(int userId, String username, String password, String firstName, String lastName,
			String role) throws SQLException, ClassNotFoundException {

		String updateStmt = "UPDATE users SET username = '" + username + "', password='" + password + "', first_name='"
				+ firstName + "', last_name='" + lastName + "', role='" + role + "' WHERE user_id = " + userId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error while UPDATE Operation: " + e);
			throw e;
		}
	}

	/**
	 * DELETE User by user id
	 * 
	 * @param userId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void deleteUserWithId(String userId) throws SQLException, ClassNotFoundException {

		String updateStmt = "DELETE FROM users WHERE user_id =" + userId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error while DELETE Operation: " + e);
			throw e;
		}
	}

	/**
	 * Insert user
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param role
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void insertUser(String username, String password, String firstName, String lastName, String role)
			throws SQLException, ClassNotFoundException {

		String updateStmt = "INSERT INTO users" + "(username, password, first_name, last_name, role) " + "VALUES "
				+ "('" + username + "','" + password + "','" + firstName + "','" + lastName + "','" + role + "')";

		// Execute DELETE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error while Insert Operation: " + e);
			throw e;
		}
	}

}
