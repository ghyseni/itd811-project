package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import application.model.UserDAO;
import application.util.Util;
import application.Login;
import application.model.User;
import application.model.User;
import application.model.UserDAO;

public class UserController {

	// AnchorPane
	@FXML
	private AnchorPane ap;

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

	// Buttons
	@FXML
	private Button searchUsersBtn;
	@FXML
	private Button searchUserBtn;
	@FXML
	private Button updateUserBtn;
	@FXML
	private Button addUserBtn;
	@FXML
	private Button deleteUserBtn;

	@FXML
	private TableView<User> userTable;
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
			System.out.println("Error occurred while getting user information from DB.\n" + e);
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

	// Called after FXML load
	@FXML
	public void initialize() {

	}

	// Initializing controller class.
	public void init(final Login login, User user) {
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

		// Add action on table selection
		userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			fillUserFormInputs(newSelection);
			updateUserBtn.setVisible(true);
		});

	}

	// Fill User Form Inputs For Update
	@FXML
	private void fillUserFormInputs(User user) {
		// Set each input field value
		if (user != null) {
			userIdText.setText(Integer.toString(user.getUserId()));
			userNameText.setText(user.getUsername());
			firstNameText.setText(user.getFirstName());
			lastNameText.setText(user.getLastName());
			roleCombo.setValue(user.getRole());
		}
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

	// Fill User for TableView and Display User on TextArea
	@FXML
	private void fillAndShowUser(User user) throws ClassNotFoundException {
		if (user != null) {
			fillUser(user);
		} else {
			System.out.println("This user does not exist!\n");
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
	private void updateUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

		try {

			String plainPassword = newPasswordText.getText();
			String password = Util.hashPassword(plainPassword);

			UserDAO.updateUser(Integer.parseInt(userIdText.getText()), userNameText.getText(), password,
					firstNameText.getText(), lastNameText.getText(), roleCombo.getValue().toString());

			searchUsersBtn.fire();

			System.out.println("User has been updated for, user id: " + userIdText.getText() + "\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem occurred while updating user: " + e);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem occurred while hashing passowrd: " + e);
		}
	}

	// Insert an user to the DB
	@FXML
	private void insertUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {

			String newPassword = Util.hashPassword(newPasswordText.getText());

			UserDAO.insertUser(userNameText.getText(), newPassword, firstNameText.getText(), lastNameText.getText(),
					roleCombo.getValue().toString());
			System.out.println("User inserted! \n");
		} catch (SQLException e) {
			System.out.println("Problem occurred while inserting user " + e);
			throw e;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Problem occurred while hashing password " + e);
		}
	}

	// Delete an user with a given user Id from DB
	@FXML
	private void deleteUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			UserDAO.deleteUserWithId(userIdText.getText());
			System.out.println("User deleted! User id: " + userIdText.getText() + "\n");
		} catch (SQLException e) {
			System.out.println("Problem occurred while deleting user " + e);
			throw e;
		}
	}

}