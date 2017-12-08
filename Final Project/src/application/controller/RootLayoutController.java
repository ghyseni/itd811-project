package application.controller;

import application.Login;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class RootLayoutController {

	@FXML
	private BorderPane bp;

	@FXML
	private MenuItem logoutMenuItem;
	@FXML
	private MenuItem exitMenuItem;
	@FXML
	private MenuItem profileMenuItem;
	@FXML
	private MenuItem ticketsMenuItem;
	@FXML
	private MenuItem usersMenuItem;

	@FXML
	private Label sessionLabel;

	public void initialize(final Login login, User user, String sessionID) {

		login.showProfile(user);

		System.out.println("Session started. SessionID:" + sessionID);
		System.out.println("User:" + user.getUsername());

		logoutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				login.logout();
			}
		});
		exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				login.logout();
				System.exit(0);
			}
		});

		profileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				login.showProfile(user);
			}
		});
		ticketsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				login.showTicketView(user);
			}
		});

		// Modify view/events based on user role
		if (user.getRole().equals("admin")) {
			usersMenuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					login.showUserView(user);
				}
			});
		}

	}

	// Help Menu button behavior
	public void handleHelp(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Program Information");
		alert.setHeaderText("Trouble Ticket System!");
		alert.setContentText("You can search, delete, update, insert a new employee with this program.");
		alert.show();
	}

}
