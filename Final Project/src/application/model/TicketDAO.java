package application.model;

import java.io.Console;
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
			ticket.setUsername(rs.getString("username"));
			ticket.setStatus(rs.getString("status"));
			ticket.setCreatedAt(rs.getTimestamp("created_at").toString());
			ticket.setUpdatedAt(rs.getTimestamp("updated_at").toString());
		}
		return ticket;
	}

	// *******************************
	// SELECT Tickets
	// *******************************
	public static ObservableList<Ticket> searchTickets(String keyword, String status)
			throws SQLException, ClassNotFoundException {

		if (status==null || status.isEmpty()) {
			status = "%";
		}
		if (keyword.isEmpty()) {
			keyword = "%";
		}else {
			keyword = "%"+keyword+"%";
		}
		// Declare a SELECT statement
		String selectStmt = "SELECT * FROM tickets INNER JOIN users ON tickets.user_id=users.user_id WHERE (name LIKE '" + keyword + "' OR description LIKE '" + keyword
				+ "') AND status LIKE '" + status + "'";

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
	public static ObservableList<Ticket> searchTickets(String keyword, String status, int userId)
			throws SQLException, ClassNotFoundException {

		if (status.isEmpty()) {
			status = "Open";
		}
		if (keyword.isEmpty()) {
			keyword = "%";
		}else {
			keyword = "%"+keyword+"%";
		}
		// Declare a SELECT statement
		String selectStmt = "SELECT * FROM tickets WHERE (name LIKE '" + keyword + "' OR description LIKE '" + keyword
				+ "') AND status='" + status + "' AND user_id=" + userId;

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

		ObservableList<Ticket> userList = FXCollections.observableArrayList();

		while (rs.next()) {
			Ticket ticket = new Ticket();
			ticket.setTicketId(rs.getInt("ticket_id"));
			ticket.setName(rs.getString("name"));
			ticket.setDescription(rs.getString("description"));
			ticket.setDepartment(rs.getString("department"));
			ticket.setUserId(rs.getInt("user_id"));
			ticket.setUsername(rs.getString("username"));
			ticket.setIssuer(rs.getString("issuer"));
			ticket.setStatus(rs.getString("status"));
			ticket.setCreatedAt(rs.getTimestamp("created_at").toString());
			ticket.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			
			userList.add(ticket);
		}
		return userList;
	}

	// *************************************
	// UPDATE a ticket
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
