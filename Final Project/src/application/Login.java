package application;

import application.controller.LoginController;
import application.controller.ProfileController;
import application.controller.RootLayoutController;
import application.controller.TicketController;
import application.controller.UserController;
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
			loader.setLocation(Login.class.getResource("view/Login.fxml"));
			scene.setRoot((Parent) loader.load());
			LoginController controller = loader.<LoginController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void showMainView(User user, String sessionID) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Login.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			RootLayoutController controller = loader.<RootLayoutController>getController();
			controller.initialize(this, user, sessionID);

			scene.setRoot(rootLayout);
			stage.sizeToScene();
			stage.centerOnScreen();

		} catch (IOException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showTicketView(User user) {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Login.class.getResource("view/TicketView.fxml"));
			AnchorPane ticketView = (AnchorPane) loader.load();
			rootLayout.setCenter(ticketView);
			TicketController controller = loader.<TicketController>getController();
			controller.init(this, user);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showProfile(User user) {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Login.class.getResource("view/ProfileView.fxml"));
			AnchorPane profileView = (AnchorPane) loader.load();
			rootLayout.setCenter(profileView);
			ProfileController controller = loader.<ProfileController>getController();
			controller.init(this, user);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showUserView(User user) {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Login.class.getResource("view/UserView.fxml"));
			AnchorPane userView = (AnchorPane) loader.load();
			rootLayout.setCenter(userView);
			UserController controller = loader.<UserController>getController();
			controller.init(this, user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}