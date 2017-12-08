package application;

import java.io.IOException;
import java.util.logging.*;

import application.controller.AdminRootLayoutController;
import application.controller.LoginController;
import application.controller.RootLayoutController;
import application.controller.TicketController;
import application.controller.UserController;
import application.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Manages control flow for logins */
public class LoginManager {
//	private Scene scene;
//	private Stage stage;
//	private BorderPane rootLayout;
//
//	public LoginManager(Scene scene, Stage stage) {
//		this.scene = scene;
//		this.stage = stage;
//	}
//
//	/**
//	 * Callback method invoked to notify that a user has been authenticated. Will
//	 * show the main application screen.
//	 */
//	public void authenticated(User user, String sessionID) {
//		showMainView(user, sessionID);
//	}
//
//	/**
//	 * Callback method invoked to notify that a user has logged out of the main
//	 * application. Will show the login application screen.
//	 */
//	public void logout() {
//		showLoginScreen();
//	}
//
//	public void showLoginScreen() {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(LoginManager.class.getResource("view/Login.fxml"));
//			scene.setRoot((Parent) loader.load());
//			LoginController controller = loader.<LoginController>getController();
//			controller.initManager(this);
//		} catch (IOException ex) {
//			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}
//
//	private void showMainView(User user, String sessionID) {
//		try {
//
//			FXMLLoader loader = new FXMLLoader();
//			
//			if (user.getRole().equals("admin")) {
//				loader.setLocation(LoginManager.class.getResource("view/AdminRootLayout.fxml"));
//				rootLayout = (BorderPane) loader.load();
//				AdminRootLayoutController controller = loader.<AdminRootLayoutController>getController();
//				controller.initSessionID(this, sessionID);
//			} else {
//				loader.setLocation(LoginManager.class.getResource("view/RootLayout.fxml"));
//				rootLayout = (BorderPane) loader.load();
//				RootLayoutController controller = loader.<RootLayoutController>getController();
//				controller.initSessionID(this, sessionID);
//			}
//			// scene.setRoot((Parent) loader.load());
//		
//			scene.setRoot(rootLayout);
//			stage.sizeToScene();
//			stage.centerOnScreen();
//			stage.setUserData(user);
//
//
//		} catch (IOException ex) {
//			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}
//
//	/**
//	 * Shows the person overview inside the root layout.
//	 */
//	public void showTicketView() {
//		try {
//			// Load person overview.
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(LoginManager.class.getResource("view/TicketView.fxml"));
//			AnchorPane ticketView = (AnchorPane) loader.load();
//
//			// Set person overview into the center of root layout.
//			rootLayout.setCenter(ticketView);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Shows the person overview inside the root layout.
//	 */
//	public void showUserView() {
//		try {
//			// Load person overview.
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(LoginManager.class.getResource("view/UserView.fxml"));
//			AnchorPane userView = (AnchorPane) loader.load();
// 
//			// Set person overview into the center of root layout.
//			rootLayout.setCenter(userView);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}