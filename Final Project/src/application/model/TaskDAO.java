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
public class TaskDAO {

	/**
	 * SELECT Task by task id
	 * 
	 * @param taskId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Task searchTask(String taskId) throws SQLException, ClassNotFoundException {

		String selectStmt = "SELECT * FROM tasks WHERE task_id=" + taskId;
		System.out.println(selectStmt);

		// Execute SELECT statement
		try {
			ResultSet rsTask = DBUtil.dbExecuteQuery(selectStmt);
			Task task = getTaskFromResultSet(rsTask);
			return task;
		} catch (SQLException e) {
			System.out.println("While searching an task with " + taskId + " id, an error occurred: " + e);
			throw e;
		}
	}

	/**
	 * Set Task Object's attributes from DB ResultSet.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static Task getTaskFromResultSet(ResultSet rs) throws SQLException {
		Task task = null;
		if (rs.next()) {
			task = new Task();
			task.setTaskId(rs.getInt("task_id"));
			task.setName(rs.getString("name"));
			task.setDescription(rs.getString("description"));
			task.setAssignedTo(rs.getString("assigned_to"));
			task.setStatus(rs.getString("status"));
			task.setTicketId(rs.getInt("ticket_id"));
			task.setCreatedAt(rs.getTimestamp("created_at").toString());
			task.setUpdatedAt(rs.getTimestamp("updated_at").toString());
		}
		return task;
	}

	/**
	 * SELECT Tasks by keyword, status, ticketId
	 * 
	 * @param keyword
	 * @param status
	 * @param ticketId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ObservableList<Task> searchTasks(String keyword, String status, int ticketId)
			throws SQLException, ClassNotFoundException {

		if (status == null || status.isEmpty()) {
			status = "%";
		}
		if (keyword.isEmpty()) {
			keyword = "%";
		} else {
			keyword = "%" + keyword + "%";
		}

		String selectStmt = "SELECT * FROM tasks WHERE (name LIKE '" + keyword + "' OR description LIKE '" + keyword
				+ "') AND status LIKE '" + status + "' AND ticket_id=" + ticketId;

		// Execute SELECT statement
		try {

			ResultSet rsTasks = DBUtil.dbExecuteQuery(selectStmt);
			ObservableList<Task> taskList = getTaskList(rsTasks);
			return taskList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has failed: " + e);
			throw e;
		}
	}

	/**
	 * Select * from tasks. Set Task Object's attributes from DB ResultSet.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static ObservableList<Task> getTaskList(ResultSet rs) throws SQLException, ClassNotFoundException {

		ObservableList<Task> tasksList = FXCollections.observableArrayList();

		while (rs.next()) {
			Task task = new Task();
			task.setTaskId(rs.getInt("task_id"));
			task.setName(rs.getString("name"));
			task.setDescription(rs.getString("description"));
			task.setAssignedTo(rs.getString("assigned_to"));
			task.setStatus(rs.getString("status"));
			task.setTicketId(rs.getInt("ticket_id"));
			task.setCreatedAt(rs.getTimestamp("created_at").toString());
			task.setUpdatedAt(rs.getTimestamp("updated_at").toString());
			tasksList.add(task);
		}

		return tasksList;
	}

	/**
	 * UPDATE task
	 * 
	 * @param taskId
	 * @param taskName
	 * @param taskDescription
	 * @param assignedTo
	 * @param status
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void updateTask(String taskId, String taskName, String taskDescription, String assignedTo,
			String status) throws SQLException, ClassNotFoundException {

		String updateStmt = "UPDATE tasks SET name = '" + taskName + "', description = '" + taskDescription
				+ "', assigned_to = '" + assignedTo + "', status='" + status + "' WHERE task_id = " + taskId;

		// Execute operation UPDATE
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error while UPDATE task name: " + e);
			throw e;
		}
	}

	/**
	 * DELETE task
	 * 
	 * @param taskId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void deleteTaskWithId(String taskId) throws SQLException, ClassNotFoundException {

		String updateStmt = "DELETE FROM tasks WHERE task_id =" + taskId;

		// Execute operation UPDATE
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error while DELETE task: " + e);
			throw e;
		}
	}

	/**
	 * INSERT task
	 * 
	 * @param name
	 * @param description
	 * @param assignedTo
	 * @param status
	 * @param ticketId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void insertTask(String name, String description, String assignedTo, String status, int ticketId)
			throws SQLException, ClassNotFoundException {
		String updateStmt = "INSERT INTO tasks " + "(name, description, assigned_to, status, ticket_id) " + "VALUES "
				+ "('" + name + "','" + description + "','" + assignedTo + "','" + status + "'," + ticketId + ")";
		System.out.println(updateStmt);
		// Execute operation DELETE
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error while insert Task: " + e);
			throw e;
		}
	}

}
