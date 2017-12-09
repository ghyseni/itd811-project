package application.controller;

import application.Login;
import application.Ticket;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class DialogRootLayoutController {

	@FXML
	private BorderPane bp;

	@FXML
	private Menu closeMenu;

	@FXML
	private Label sessionLabel;

	public void initialize(User user) {


		closeMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		});

	}

}
