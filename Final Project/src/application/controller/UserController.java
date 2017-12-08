package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;

import application.model.User;
import application.model.UserDAO;

/**
 * Created by ONUR BASKIRT on 23.02.2016.
 */
public class UserController {

	@FXML
	private TextField userIdText;
	@FXML
	private TextArea resultArea;
	@FXML
	private TextField newPasswordText;
	@FXML
	private TextField userNameText;
	@FXML
	private TextField firstNameText;
	@FXML
	private TextField lastNameText;
	@FXML
	private ComboBox<String> roleCombo;


	@FXML
	private TableView userTable;
	@FXML
	private TableColumn<User, Integer> userIdColumn;
	@FXML
	private TableColumn<User, String> usernameColumn;
	@FXML
	private TableColumn<User, String> userFirstNameColumn;
	@FXML
	private TableColumn<User, String> userLastNameColumn;
	@FXML
	private TableColumn<User, String> userPasswordColumn;
	@FXML
	private TableColumn<User, String> userRoleColumn;

	// Search an user
	@FXML
	private void searchUser(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		try {
			// Get User information
			User user = UserDAO.searchUser(userIdText.getText());
			// Fill User on TableView and Display on TextArea
			fillAndShowUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			resultArea.setText("Error occurred while getting user information from DB.\n" + e);
			throw e;
		}
	}

	// Search all users
	@FXML
	private void searchUsers(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// Get all Users information
			ObservableList<User> userData = UserDAO.searchUsers();
			// Fill Users on TableView
			fillUsers(userData);
		} catch (SQLException e) {
			System.out.println("Error occurred while getting users information from DB.\n" + e);
			throw e;
		}
	}

	// Initializing the controller class.
	// This method is automatically called after the fxml file has been loaded.
	@FXML
	private void initialize() {
		/*
		 * The setCellValueFactory(...) that we set on the table columns are used to
		 * determine which field inside the User objects should be used for the
		 * particular column. The arrow -> indicates that we're using a Java 8 feature
		 * called Lambdas. (Another option would be to use a PropertyValueFactory, but
		 * this is not type-safe
		 * 
		 * We're only using StringProperty values for our table columns in this example.
		 * When you want to use IntegerProperty or DoubleProperty, the
		 * setCellValueFactory(...) must have an additional asObject():
		 */
		userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
		usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
		userPasswordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
		userFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		userLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		userRoleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

		// fill user role combo box with item choices.
		ObservableList<String> userRoles = FXCollections.observableArrayList();
		userRoles.add("Admin");
		userRoles.add("Employee");
		roleCombo.setItems(userRoles);

	}

	// Fill User
	@FXML
	private void fillUser(User user) throws ClassNotFoundException {
		// Declare and ObservableList for table view
		ObservableList<User> userData = FXCollections.observableArrayList();
		// Add user to the ObservableList
		userData.add(user);
		// Set items to the userTable
		userTable.setItems(userData);
	}

	// Set User information to Text Area
	@FXML
	private void setUserInfoToTextArea(User user) {
		resultArea.setText("First Name: " + user.getFirstName() + "\n" + "Last Name: " + user.getLastName());
	}

	// Fill User for TableView and Display User on TextArea
	@FXML
	private void fillAndShowUser(User user) throws ClassNotFoundException {
		if (user != null) {
			fillUser(user);
			setUserInfoToTextArea(user);
		} else {
			resultArea.setText("This user does not exist!\n");
		}
	}

	// Fill Users for TableView
	@FXML
	private void fillUsers(ObservableList<User> userData) throws ClassNotFoundException {
		// Set items to the userTable
		userTable.setItems(userData);
	}

	// Update user's email with the email which is written on newEmailText field
	@FXML
	private void updateUsername(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

		try {
			UserDAO.updateUser(Integer.parseInt(userIdText.getText()), userNameText.getText(), firstNameText.getText(),
					lastNameText.getText(), roleCombo.getValue().toString());
			resultArea.setText("Email has been updated for, user id: " + userIdText.getText() + "\n");
		} catch (SQLException e) {
			resultArea.setText("Problem occurred while updating email: " + e);
		}
	}

	// Insert an user to the DB
	@FXML
	private void insertUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			UserDAO.insertUser(userNameText.getText(), newPasswordText.getText(), firstNameText.getText(),
					lastNameText.getText(), roleCombo.getValue().toString());
			resultArea.setText("User inserted! \n");
		} catch (SQLException e) {
			resultArea.setText("Problem occurred while inserting user " + e);
			throw e;
		}
	}

	// Delete an user with a given user Id from DB
	@FXML
	private void deleteUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			UserDAO.deleteUserWithId(userIdText.getText());
			resultArea.setText("User deleted! User id: " + userIdText.getText() + "\n");
		} catch (SQLException e) {
			resultArea.setText("Problem occurred while deleting user " + e);
			throw e;
		}
	}
}