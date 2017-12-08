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

import java.sql.Date;
import java.sql.SQLException;

import application.model.Ticket;
import application.model.TicketDAO;

/**
 * Created by ONUR BASKIRT on 23.02.2016.
 */
public class TicketController {
	// Inputs
	@FXML
	private TextField ticketIdText;
	@FXML
	private TextField ticketNameText;
	@FXML
	private TextArea ticketDescriptionTextArea;
	@FXML
	private ComboBox<String> departmentCombo;
	@FXML
	private TextField ticketUserIdText;
	@FXML
	private TextField ticketIssuerIdText;

	// not used
	@FXML
	private Button searchTicketsBtn;
	@FXML
	private Button searchTicketBtn;
	@FXML
	private Button updateTicketBtn;
	@FXML
	private Button addTicketBtn;
	@FXML
	private Button deleteTicketBtn;

	// Table
	@FXML
	private TableView<Ticket> ticketTable;
	@FXML
	private TableColumn<Ticket, Integer> ticketIdColumn;
	@FXML
	private TableColumn<Ticket, String> ticketNameColumn;
	@FXML
	private TableColumn<Ticket, String> ticketDescriptionColumn;
	@FXML
	private TableColumn<Ticket, String> ticketDepartmentColumn;
	@FXML
	private TableColumn<Ticket, Integer> ticketUserIdColumn;
	@FXML
	private TableColumn<Ticket, Integer> ticketIssuerIdColumn;
	@FXML
	private TextArea resultArea;

	// Search an ticket
	@FXML
	private void searchTicket(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		System.out.println(actionEvent);
		try {
			// Get Ticket information
			Ticket ticket = TicketDAO.searchTicket(ticketIdText.getText());
			// Fill Ticket on TableView and Display on TextArea
			fillAndShowTicket(ticket);
		} catch (SQLException e) {
			e.printStackTrace();
			resultArea.setText("Error occurred while getting ticket information from DB.\n" + e);
			throw e;
		}
	}

	// Search all tickets
	@FXML
	private void searchTickets(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// Get all Tickets information
			ObservableList<Ticket> ticketData = TicketDAO.searchTickets();
			// Fill Tickets on TableView
			fillTickets(ticketData);
		} catch (SQLException e) {
			System.out.println("Error occurred while getting tickets information from DB.\n" + e);
			throw e;
		}
	}

	// Initializing the controller class.
	// This method is automatically called after the fxml file has been loaded.
	@FXML
	private void initialize() {
		/*
		 * The setCellValueFactory(...) that we set on the table columns are used to
		 * determine which field inside the Ticket objects should be used for the
		 * particular column. The arrow -> indicates that we're using a Java 8 feature
		 * called Lambdas. (Another option would be to use a PropertyValueFactory, but
		 * this is not type-safe
		 * 
		 * We're only using StringProperty values for our table columns in this example.
		 * When you want to use IntegerProperty or DoubleProperty, the
		 * setCellValueFactory(...) must have an additional asObject():
		 */
		ticketIdColumn.setCellValueFactory(cellData -> cellData.getValue().ticketIdProperty().asObject());
		ticketNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		ticketDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		ticketDepartmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
		ticketUserIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
		ticketIssuerIdColumn.setCellValueFactory(cellData -> cellData.getValue().issuerIdProperty().asObject());

		// fill user department combo box with item choices.
		ObservableList<String> userDepartments = FXCollections.observableArrayList();
		userDepartments.add("Production");
		userDepartments.add("IT");
		departmentCombo.setItems(userDepartments);

		ticketTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			fillTicketFormInputs(newSelection);
			updateTicketBtn.setVisible(true);
		});

	}

	// Fill Ticket Form Inputs For Update
	@FXML
	private void fillTicketFormInputs(Ticket ticket) {
		// Set each input field value
		if (ticket != null) {
			ticketIdText.setText(Integer.toString(ticket.getTicketId()));
			ticketNameText.setText(ticket.getName());
			ticketDescriptionTextArea.setText(ticket.getDescription());
			departmentCombo.setValue(ticket.getDepartment());
			ticketIssuerIdText.setText(Integer.toString(ticket.getIssuerId()));
			ticketUserIdText.setText(Integer.toString(ticket.getUserId()));
		}
	}

	// Fill Ticket
	@FXML
	private void fillTicket(Ticket ticket) throws ClassNotFoundException {
		// Declare and ObservableList for table view
		ObservableList<Ticket> ticketData = FXCollections.observableArrayList();
		// Add ticket to the ObservableList
		ticketData.add(ticket);
		// Set items to the ticketTable
		ticketTable.setItems(ticketData);
	}

	// Set Ticket information to Text Area
	@FXML
	private void setTicketInfoToTextArea(Ticket ticket) {
		resultArea.setText("Name: " + ticket.getName() + "\n" + "Description: " + ticket.getDescription());
	}

	// Fill Ticket for TableView and Display Ticket on TextArea
	@FXML
	private void fillAndShowTicket(Ticket ticket) throws ClassNotFoundException {
		if (ticket != null) {
			fillTicket(ticket);
			setTicketInfoToTextArea(ticket);
		} else {
			resultArea.setText("This ticket does not exist!\n");
		}
	}

	// Fill Tickets for TableView
	@FXML
	private void fillTickets(ObservableList<Ticket> ticketData) throws ClassNotFoundException {
		// Set items to the ticketTable
		ticketTable.setItems(ticketData);
	}

	// Update ticket's email with the email which is written on newEmailText field
	@FXML
	private void updateTicket(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TicketDAO.updateTicket(ticketIdText.getText(), ticketNameText.getText(),
					ticketDescriptionTextArea.getText(), departmentCombo.getValue(), ticketIssuerIdText.getText(),
					ticketUserIdText.getText());

			searchTicketsBtn.fire();

			resultArea.setText("Ticket has been updated for, ticket id: " + ticketIdText.getText() + "\n");
		} catch (SQLException e) {
			resultArea.setText("Problem occurred while updating ticket name: " + e);
		}
	}

	// Insert an ticket to the DB
	@FXML
	private void insertTicket(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TicketDAO.insertTicket(ticketNameText.getText(), ticketDescriptionTextArea.getText(),
					departmentCombo.getValue().toString(), Integer.parseInt(ticketIssuerIdText.getText()),
					Integer.parseInt(ticketUserIdText.getText()));
			resultArea.setText("Ticket inserted! \n");
		} catch (SQLException e) {
			resultArea.setText("Problem occurred while inserting ticket " + e);
			throw e;
		}
	}

	// Delete an ticket with a given ticket Id from DB
	@FXML
	private void deleteTicket(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TicketDAO.deleteTicketWithId(ticketIdText.getText());
			resultArea.setText("Ticket deleted! Ticket id: " + ticketIdText.getText() + "\n");
		} catch (SQLException e) {
			resultArea.setText("Problem occurred while deleting ticket " + e);
			throw e;
		}
	}
}