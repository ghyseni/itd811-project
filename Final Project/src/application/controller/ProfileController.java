package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class ProfileController {

	private User user;

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
	private Button updateUserBtn;
	@FXML
	private Button ticketsBtn;

	// Label
	@FXML
	private Label usernameLbl;
	@FXML
	private Label resultLbl;
	@FXML
	private Label userIdLbl;

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

	// Called after FXML load
	@FXML
	public void initialize() {

	}

	// Initializing controller class.
	public void init(final Login login, User user) {
		this.user = user;
		fillUserFormInputs(user);
		if (user != null) {
			usernameLbl.setText(user.getFirstName() + user.getLastName() + "");
		}
	}

	// Fill User Form Inputs For Update
	private void fillUserFormInputs(User user) {
		
		// Set each input field value
		if (user != null) {
			userNameText.setText(user.getUsername());
			firstNameText.setText(user.getFirstName());
			lastNameText.setText(user.getLastName());
		}
	}

	// Update user's details
	@FXML
	private void updateUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

		try {

			String plainPassword = newPasswordText.getText();
			String password = Util.hashPassword(plainPassword);

			UserDAO.updateUser(Integer.parseInt(userIdText.getText()), userNameText.getText(), password,
					firstNameText.getText(), lastNameText.getText(), roleCombo.getValue().toString());

			System.out.println("User has been updated for, user id: " + userIdText.getText() + "\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem occurred while updating user: " + e);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem occurred while hashing passowrd: " + e);
		}
	}

}