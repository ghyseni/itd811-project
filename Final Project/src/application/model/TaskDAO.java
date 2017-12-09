package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskDAO {

	// *******************************
	// SELECT an Task
	// *******************************
	public static Task searchTask(String taskId) throws SQLException, ClassNotFoundException {
		// Declare a SELECT statement

		String selectStmt = "SELECT * FROM tasks WHERE task_id=" + taskId;
		System.out.println(selectStmt);

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsTask = DBUtil.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getTaskFromResultSet method and get task object
			Task task = getTaskFromResultSet(rsTask);

			// Return task object
			return task;
		} catch (SQLException e) {
			System.out.println("While searching an task with " + taskId + " id, an error occurred: " + e);
			// Return exception
			throw e;
		}
	}

	// Use ResultSet from DB as parameter and set Task Object's attributes and
	// return task object.
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

	// *******************************
	// SELECT Tasks by userId
	// *******************************
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
		// Declare a SELECT statement
		String selectStmt = "SELECT * FROM tasks WHERE (name LIKE '" + keyword + "' OR description LIKE '" + keyword
				+ "') AND status LIKE '" + status + "' AND ticket_id=" + ticketId;

		// Execute SELECT statement
		try {
			// Get ResultSet from dbExecuteQuery method
			ResultSet rsTasks = DBUtil.dbExecuteQuery(selectStmt);

			// Send ResultSet to the getTaskList method and get task object
			ObservableList<Task> taskList = getTaskList(rsTasks);

			// Return task object
			return taskList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has been failed: " + e);
			// Return exception
			throw e;
		}
	}

	// Select * from tasks
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

	// *************************************
	// UPDATE a task
	// *************************************
	public static void updateTask(String taskId, String taskName, String taskDescription, String assignedTo,
			String status) throws SQLException, ClassNotFoundException {
		// Declare a UPDATE statement
		String updateStmt = "UPDATE tasks SET name = '" + taskName + "', description = '" + taskDescription
				+ "', assigned_to = '" + assignedTo + "', status='" + status + "' WHERE task_id = " + taskId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while UPDATE task name: " + e);
			throw e;
		}
	}

	// *************************************
	// DELETE a task
	// *************************************
	public static void deleteTaskWithId(String taskId) throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String updateStmt = "DELETE FROM tasks WHERE task_id =" + taskId;

		// Execute UPDATE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while DELETE task: " + e);
			throw e;
		}
	}

	// *************************************
	// INSERT a task
	// *************************************
	public static void insertTask(String name, String description, String assignedTo, String status, int ticketId)
			throws SQLException, ClassNotFoundException {
		// Declare a DELETE statement
		String updateStmt = "INSERT INTO tasks " + "(name, description, assigned_to, status, ticket_id) " + "VALUES "
				+ "('" + name + "','" + description + "','" + assignedTo + "','" + status + "'," + ticketId
				+ ")";
		System.out.println(updateStmt);
		// Execute DELETE operation
		try {
			DBUtil.dbExecuteUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.print("Error occurred while insert Task: " + e);
			throw e;
		}
	}

}
