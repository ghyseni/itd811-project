package application.controller;

import application.Login;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class RootLayoutController {

	@FXML
	private MenuItem logoutMenuItem;
	@FXML
	private MenuItem exitMenuItem;
	@FXML
	private MenuItem tickets;

	@FXML
	private Label sessionLabel;

	public void initialize() {
	}

	// Help Menu button behavior
	public void handleHelp(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Program Information");
		alert.setHeaderText("Trouble Ticket System!");
		alert.setContentText("You can search, delete, update, insert a new employee with this program.");
		alert.show();
	}

	public void initSessionID(final Login login, String sessionID) {
		System.out.println("sessionID:" + sessionID);
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
		tickets.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				login.showTicketView();
			}
		});
	}
}
