package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketDAO {

	// *******************************
	// SELECT an Ticket
	// *******************************
	public static Ticket searchTicket(String ticketId) throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement

		String selectStmt = "SELECT * FROM tickets WHERE ticket_id=" + ticketId;
		System.out.println(selectStmt);

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsTicket = DBUtil.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getTicketFromResultSet method and get ticket object
			Ticket ticket = getTicketFromResultSet(rsTicket);

			// Return ticket object
			return ticket;
		} catch (SQLException e) {
			System.out.println("While searching an ticket with " + ticketId + " id, an error occurred: " + e);
			// Return exception
			throw e;
		}
	}

	// Use ResultSet from DB as parameter and set Ticket Object's attributes and
	// return ticket object.
	private static Ticket getTicketFromResultSet(ResultSet rs) throws SQLException {
		Ticket ticket = null;
		if (rs.next()) {
			ticket = new Ticket();
			ticket.setTicketId(rs.getInt("ticket_id"));
			ticket.setName(rs.getString("name"));
			ticket.setDescription(rs.getString("description"));
			ticket.setDepartment(rs.getString("department"));
			ticket.setIssuer(rs.getString("issuer"));
			ticket.setUserId(rs.getInt("user_id"));
			ticket.setIssuer(rs.getString("status"));
		}
		return ticket;
	}

	// *******************************
	// SELECT Tickets
	// *******************************
	public static ObservableList<Ticket> searchTickets(String keyword) throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		String selectStmt = "";
		if (keyword.isEmpty()) {
			selectStmt = "SELECT * FROM tickets";
		} else {
			selectStmt = "SELECT * FROM tickets where name LIKE '%" + keyword + "%' OR description LIKE '%" + keyword
					+ "%' OR status LIKE '%" + keyword + "%'";
		}

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsTickets = DBUtil.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getTicketList method and get ticket object
			ObservableList<Ticket> ticketList = getTicketList(rsTickets);

			// Return ticket object
			return ticketList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has been failed: " + e);
			// Return exception
			throw e;
		}
	}

	// *******************************
	// SELECT Tickets by userId
	// *******************************
	public static ObservableList<Ticket> searchTickets(String keyword, int userId)
			throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement
		String selectStmt = "";
		if (keyword.isEmpty()) {
			selectStmt = "SELECT * FROM tickets where user_id=" + userId;
		} else {
			selectStmt = "SELECT * FROM tickets where name LIKE '%" + keyword + "%' OR description LIKE '%" + keyword
					+ "%' OR status LIKE '%" + keyword + "% AND user_id=" + userId;
		}

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsTickets = DBUtil.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getTicketList method and get ticket object
			ObservableList<Ticket> ticketList = getTicketList(rsTickets);

			// Return ticket object
			return ticketList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has been failed: " + e);
			// Return exception
			throw e;
		}
	}

	// Select * from tickets
	private static ObservableList<Ticket> getTicketList(ResultSet rs) throws SQLException, ClassNotFoundException {
		// Declare a observable List which comprises of Ticket objects
		ObservableList<Ticket> userList = FXCollections.observableArrayList();

		while (rs.next()) {
			Ticket ticket = new Ticket();
			ticket.setTicketId(rs.getInt("ticket_id"));
			ticket.setName(rs.getString("name"));
			ticket.setDescription(rs.getString("description"));
			ticket.setDepartment(rs.getString("department"));
			ticket.setUserId(rs.getInt("user_id"));
			ticket.setIssuer(rs.getString("issuer"));
			ticket.setIssuer(rs.getString("status"));
			// Add ticket to the ObservableList
			userList.add(ticket);
		}
		// return userList (ObservableList of Tickets)
		return userList;
	}

	// *************************************
	// UPDATE a ticket name
	// *************************************
	public static void updateTicket(String ticketId, String ticketName, String ticketDescription,
			String ticketDepartment, String issuer, int userId, String status)
			throws SQLException, ClassNotFoundException {
		// Declare a UPDATE statement
		String updateStmt = "UPDATE tickets SET name = '" + ticketName + "', description = '" + ticketDescription
				+ "', department = '" + ticketDepartment + "', issuer = '" + issuer + "', user_id = " + userId
				+ ", status='" + status + "' WHERE ticket_id = " + ticketId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while UPDATE ticket name: " + e);
			throw e;
		}
	}

	// *************************************
	// UPDATE a ticket description
	// *************************************
	public static void updateTicketDescription(String ticketId, String ticketDescription)
			throws SQLException, ClassNotFoundException {
		// Declare a UPDATE statement
		String updateStmt = "UPDATE tickets SET description = '" + ticketDescription + "' WHERE ticket_id = "
				+ ticketId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while UPDATE ticket description: " + e);
			throw e;
		}
	}

	// *************************************
	// DELETE a ticket
	// *************************************
	public static void deleteTicketWithId(String ticketId) throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String updateStmt = "DELETE FROM tickets WHERE ticket_id =" + ticketId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while DELETE ticket: " + e);
			throw e;
		}
	}

	// *************************************
	// INSERT a ticket
	// *************************************
	public static void insertTicket(String name, String description, String department, String issuer, int userId,
			String status) throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String updateStmt = "INSERT INTO tickets " + "(name, description, department, issuer, user_id, status) "
				+ "VALUES " + "('" + name + "','" + description + "','" + department + "','" + issuer + "'," + userId
				+ ",'" + status + "')";
		System.out.println(updateStmt);
		// Execute DELETE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while insert Ticket: " + e);
			throw e;
		}
	}

}
