package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDAO {

	// *******************************
	// SELECT an User by ID
	// *******************************
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

	// *******************************
	// SELECT an User by ID
	// *******************************
	public static User searchUserByUsernamePassword(String username, String password)
			throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		String selectStmt = "SELECT * FROM users WHERE username='" + username + "' and password='" + password + "'";

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsUser = DBUtil.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getUserFromResultSet method and get user object
			User user = getUserFromResultSet(rsUser);

			// Return user object
			return user;
		} catch (SQLException e) {
			System.out.println("While searching an user with '" + username + "' username, an error occurred: " + e);
			// Return exception
			throw e;
		}
	}

	// Use ResultSet from DB as parameter and set User Object's attributes and
	// return user object.
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

	// *******************************
	// SELECT Users
	// *******************************
	public static ObservableList<User> searchUsers() throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		String selectStmt = "SELECT * FROM users";

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsUsers = DBUtil.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getUserList method and get user object
			ObservableList<User> userList = getUserList(rsUsers);

			// Return user object
			return userList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has been failed: " + e);
			// Return exception
			throw e;
		}
	}

	// Select * from users operation
	private static ObservableList<User> getUserList(ResultSet rs) throws SQLException, ClassNotFoundException {
		// Declare a observable List which comprises of User objects
		ObservableList<User> userList = FXCollections.observableArrayList();

		while (rs.next()) {
			User user = new User();
			user.setUserId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setRole(rs.getString("role"));
			// Add user to the ObservableList
			userList.add(user);
		}
		// return userList (ObservableList of Users)
		return userList;
	}

	// *************************************
	// UPDATE a user
	// *************************************
	public static void updateUser(int userId, String username, String password, String firstName, String lastName,
			String role) throws SQLException, ClassNotFoundException {
		// Declare a UPDATE statement
		String updateStmt = "UPDATE users SET username = '" + username + "', password='" + password + "', first_name='"
				+ firstName + "', last_name='" + lastName + "', role='" + role + "' WHERE user_id = " + userId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while UPDATE Operation: " + e);
			throw e;
		}
	}

	// *************************************
	// DELETE a user
	// *************************************
	public static void deleteUserWithId(String userId) throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String updateStmt = "DELETE FROM users WHERE user_id =" + userId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while DELETE Operation: " + e);
			throw e;
		}
	}

	// *************************************
	// INSERT a user
	// *************************************
	public static void insertUser(String username, String password, String firstName, String lastName, String role)
			throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String updateStmt = "INSERT INTO users" + "(username, password, first_name, last_name, role) " + "VALUES "
				+ "('" + username + "','" + password + "','" + firstName + "','" + lastName + "','" + role + "')";

		// Execute DELETE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while Insert Operation: " + e);
			throw e;
		}
	}

}
