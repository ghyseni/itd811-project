package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

import application.Login;
import application.model.Task;
import application.model.TaskDAO;
import application.model.Ticket;
import application.model.User;

public class TasksController {

	private User user;
	private Ticket ticket;

	// AnchorPane
	@FXML
	private AnchorPane ap;

	// Inputs
	@FXML
	private TextField taskIdText;
	@FXML
	private TextField taskNameText;
	@FXML
	private TextArea taskDescriptionTextArea;
	@FXML
	private TextField assignedToText;
	@FXML
	private TextField taskKeywordText;
	@FXML
	private ComboBox<String> searchStatusCombo;
	@FXML
	private ComboBox<String> statusCombo;

	// Buttons
	@FXML
	private Button searchTasksBtn;
	@FXML
	private Button searchTaskBtn;
	@FXML
	private Button updateTaskBtn;
	@FXML
	private Button addTaskBtn;
	@FXML
	private Button deleteTaskBtn;
	@FXML
	private Button closeTaskBtn;

	// Table
	@FXML
	private TableView<Task> taskTable;
	@FXML
	private TableColumn<Task, Integer> taskIdColumn;
	@FXML
	private TableColumn<Task, String> taskNameColumn;
	@FXML
	private TableColumn<Task, String> taskDescriptionColumn;
	@FXML
	private TableColumn<Task, String> taskAssignedToColumn;
	@FXML
	private TableColumn<Task, String> taskStatusColumn;
	@FXML
	private TableColumn<Task, String> taskCreatedAtColumn;
	@FXML
	private TableColumn<Task, String> taskUpdatedAtColumn;

	// Search an task
	@FXML
	private void searchTask(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		System.out.println(actionEvent);
		try {
			// Get Task information
			Task task = TaskDAO.searchTask(taskIdText.getText());
			// Fill Task on TableView and Display on TextArea
			fillAndShowTask(task);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error occurred while getting task information from DB.\n" + e);
			throw e;
		}
	}

	// Search all tasks
	@FXML
	private void searchTasks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// Get all Tasks information
			ObservableList<Task> taskData = null;
			String keyword = taskKeywordText.getText();
			String status = searchStatusCombo.getValue();
			taskData = TaskDAO.searchTasks(keyword, status, ticket.getTicketId());
			// Fill Tasks on TableView
			fillTasks(taskData);
		} catch (SQLException e) {
			System.out.println("Error occurred while getting tasks information from DB.\n" + e);
			throw e;
		}
	}

	// Called after fxml load
	@FXML
	public void initialize() {

	}

	// Initializing controller class.
	public void init(final Login login, User user, Ticket ticket) {

		this.user = user;
		this.ticket = ticket;

		taskIdColumn.setCellValueFactory(cellData -> cellData.getValue().taskIdProperty().asObject());
		taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		taskDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		taskStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		taskAssignedToColumn.setCellValueFactory(cellData -> cellData.getValue().assignedToProperty());
		taskCreatedAtColumn.setCellValueFactory(cellData -> cellData.getValue().createdAtProperty());
		taskUpdatedAtColumn.setCellValueFactory(cellData -> cellData.getValue().updatedAtProperty());

		// fill status combo box with item choices.
		ObservableList<String> statuses = FXCollections.observableArrayList();
		statuses.add("Open");
		statuses.add("Processing");
		statuses.add("Done");
		statusCombo.setItems(statuses);
		statuses.add("");
		searchStatusCombo.setItems(statuses);

		// Add action on table selection
		taskTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			fillTaskFormInputs(newSelection);
			updateTaskBtn.setVisible(true);
		});
	}

	// Fill Task Form Inputs For Update
	private void fillTaskFormInputs(Task task) {
		// Set each input field value
		if (task != null) {
			taskIdText.setText(Integer.toString(task.getTaskId()));
			taskNameText.setText(task.getName());
			taskDescriptionTextArea.setText(task.getDescription());
			statusCombo.setValue(task.getStatus());
			assignedToText.setText(task.getAssignedTo());
		}
	}

	// Fill Task
	private void fillTask(Task task) throws ClassNotFoundException {
		// Declare and ObservableList for table view
		ObservableList<Task> taskData = FXCollections.observableArrayList();
		// Add task to the ObservableList
		taskData.add(task);
		// Set items to the taskTable
		taskTable.setItems(taskData);
	}

	// Fill Task for TableView and Display Task on TextArea
	private void fillAndShowTask(Task task) throws ClassNotFoundException {
		if (task != null) {
			fillTask(task);
		} else {
			System.out.println("This task does not exist!\n");
		}
	}

	// Fill Tasks for TableView
	private void fillTasks(ObservableList<Task> taskData) throws ClassNotFoundException {
		// Set items to the taskTable
		taskTable.setItems(taskData);
	}

	// Update task's email with the email which is written on newEmailText field
	@FXML
	private void updateTask(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TaskDAO.updateTask(taskIdText.getText(), taskNameText.getText(), taskDescriptionTextArea.getText(),
					assignedToText.getText(), statusCombo.getValue().toString());

			searchTasksBtn.fire();

			System.out.println("Task has been updated for, task id: " + taskIdText.getText() + "\n");
		} catch (SQLException e) {
			System.out.println("Problem occurred while updating task name: " + e);
		}
	}

	// Insert an task to the DB
	@FXML
	private void insertTask(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TaskDAO.insertTask(taskNameText.getText(), taskDescriptionTextArea.getText(), assignedToText.getText(),
					statusCombo.getValue().toString(),ticket.getTicketId());
			System.out.println("Task inserted! \n");
		} catch (SQLException e) {
			System.out.println("Problem occurred while inserting task " + e);
			throw e;
		}
	}

	// Delete an task with a given task Id from DB
	@FXML
	private void deleteTask(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TaskDAO.deleteTaskWithId(taskIdText.getText());
			System.out.println("Task deleted! Task id: " + taskIdText.getText() + "\n");
		} catch (SQLException e) {
			System.out.println("Problem occurred while deleting task " + e);
			throw e;
		}
	}

}