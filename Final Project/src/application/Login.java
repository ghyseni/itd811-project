package application;

import application.LoginManager;
import application.controller.AdminRootLayoutController;
import application.controller.LoginController;
import application.controller.RootLayoutController;
import application.model.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/** Main application class for the login demo application */
public class Login extends Application {
	private Scene scene;
	private Stage stage;
	private BorderPane rootLayout;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(new StackPane());
		this.stage = stage;
		showLoginView();

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Callback method invoked to notify that a user has been authenticated. Will
	 * show the main application screen.
	 */
	public void authenticate(User user, String sessionID) {
		showMainView(user, sessionID);
	}
	
	/**
	 * Callback method invoked to notify that a user has logged out of the main
	 * application. Will show the login application screen.
	 */
	public void logout() {
		showLoginView();
	}
	
	public void showLoginView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LoginManager.class.getResource("view/Login.fxml"));
			scene.setRoot((Parent) loader.load());
			LoginController controller = loader.<LoginController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void showMainView(User user, String sessionID) {
		try {

			FXMLLoader loader = new FXMLLoader();
			
			if (user.getRole().equals("admin")) {
				loader.setLocation(LoginManager.class.getResource("view/AdminRootLayout.fxml"));
				rootLayout = (BorderPane) loader.load();
				AdminRootLayoutController controller = loader.<AdminRootLayoutController>getController();
				controller.initSessionID(this, sessionID);
			} else {
				loader.setLocation(LoginManager.class.getResource("view/RootLayout.fxml"));
				rootLayout = (BorderPane) loader.load();
				RootLayoutController controller = loader.<RootLayoutController>getController();
				controller.initSessionID(this, sessionID);
			}
			// scene.setRoot((Parent) loader.load());
		
			scene.setRoot(rootLayout);
			stage.sizeToScene();
			stage.centerOnScreen();
			stage.setUserData(user);


		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showTicketView() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LoginManager.class.getResource("view/TicketView.fxml"));
			AnchorPane ticketView = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(ticketView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showUserView() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LoginManager.class.getResource("view/UserView.fxml"));
			AnchorPane userView = (AnchorPane) loader.load();
 
			// Set person overview into the center of root layout.
			rootLayout.setCenter(userView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}