package application.controller;

import java.sql.SQLException;

import application.Login;
import application.model.User;
import application.model.UserDAO;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controls the login screen */
public class LoginController {
	
	private static int sessionID = 0;
	private static User user;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
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
					sessionID=generateSessionID();
					login.authenticate(user,sessionID);
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
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		try {
			// Get User information
			this.user = UserDAO.searchUserByUsernamePassword(username, password);
			
//			System.out.println(user.toString());
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
		}
	}	

	private String generateSessionID() {
		sessionID++;
		return "" + sessionID;
	}
}