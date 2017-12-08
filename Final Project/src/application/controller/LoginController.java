package application.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import application.Login;
import application.model.User;
import application.model.UserDAO;
import application.util.Util;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controls the login screen */
public class LoginController {

	private static int sessionID = 0;
	private User user;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Button loginButton;

	public void initialize() {
	}

	public void initManager(final Login login) {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String sessionID = null;
				User user = authorize();
				if (user != null) {
					sessionID = generateSessionID();
					login.authenticate(user, sessionID);
				}
			}
		});
	}

	/**
	 * Check authorization credentials.
	 * 
	 * If accepted, return a sessionID for the authorized session otherwise, return
	 * null.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private User authorize() {

		try {
			// Get User information
			String username = usernameTextField.getText();
			String password = Util.hashPassword(passwordTextField.getText());
			this.user = UserDAO.searchUserByUsernamePassword(username, password);

			// System.out.println(user.toString());
			return user != null ? user : null;

			// Populate User on TableView and Display on TextArea
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error occurred while getting user information from DB.\n" + e);
			return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error while converting password to hash.\n" + e);
			return null;
		}
	}

	private String generateSessionID() {
		sessionID++;
		return "" + sessionID;
	}
}