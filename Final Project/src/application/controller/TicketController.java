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
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

import application.Login;
import application.model.Ticket;
import application.model.TicketDAO;
import application.model.User;

public class TicketController {

	private User user;

	// AnchorPane
	@FXML
	private AnchorPane ap;

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
	private TextField ticketIssuerText;
	@FXML
	private TextField ticketKeywordText;
	@FXML
	private ComboBox<String> searchStatusCombo;
	@FXML
	private ComboBox<String> statusCombo;

	// Buttons
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
	@FXML
	private Button closeTicketBtn;

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
	private TableColumn<Ticket, String> ticketIssuerColumn;
	@FXML
	private TableColumn<Ticket, String> ticketStatusColumn;
	@FXML
	private TableColumn<Ticket, String> ticketCreatedAtColumn;
	@FXML
	private TableColumn<Ticket, String> ticketUpdatedAtColumn;
	
	

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
			System.out.println("Error occurred while getting ticket information from DB.\n" + e);
			throw e;
		}
	}

	// Search all tickets
	@FXML
	private void searchTickets(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// Get all Tickets information
			ObservableList<Ticket> ticketData = null;
			String keyword = ticketKeywordText.getText();
			String status = searchStatusCombo.getValue();
			if (user.getRole().equals("admin")) {
				ticketData = TicketDAO.searchTickets(keyword,status);
			} else {
				ticketData = TicketDAO.searchTickets(keyword,status, user.getUserId());
			}
			// Fill Tickets on TableView
			fillTickets(ticketData);
		} catch (SQLException e) {
			System.out.println("Error occurred while getting tickets information from DB.\n" + e);
			throw e;
		}
	}

	// Called after fxml load
	@FXML
	public void initialize() {

	}

	// Initializing controller class.
	public void init(final Login login, User user) {

		this.user = user;

		ticketIdColumn.setCellValueFactory(cellData -> cellData.getValue().ticketIdProperty().asObject());
		ticketNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		ticketDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		ticketDepartmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
		ticketUserIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
		ticketIssuerColumn.setCellValueFactory(cellData -> cellData.getValue().issuerProperty());
		ticketStatusColumn.setCellValueFactory(cellData -> cellData.getValue().issuerProperty());
		ticketCreatedAtColumn.setCellValueFactory(cellData -> cellData.getValue().issuerProperty());
		ticketUpdatedAtColumn.setCellValueFactory(cellData -> cellData.getValue().issuerProperty());

		// fill department combo box with item choices.
		ObservableList<String> departments = FXCollections.observableArrayList();
		departments.add("Production");
		departments.add("IT");
		departmentCombo.setItems(departments);

		// fill status combo box with item choices.
		ObservableList<String> statuses = FXCollections.observableArrayList();
		
		statuses.add("Open");
		statuses.add("Processing");
		statuses.add("Closed");
		statusCombo.setItems(statuses);
		statuses.add("");
		searchStatusCombo.setItems(statuses);

		// Add action on table selection
		ticketTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			fillTicketFormInputs(newSelection);
			updateTicketBtn.setVisible(true);
		});
	}

	// Fill Ticket Form Inputs For Update
	private void fillTicketFormInputs(Ticket ticket) {
		// Set each input field value
		if (ticket != null) {
			ticketIdText.setText(Integer.toString(ticket.getTicketId()));
			ticketNameText.setText(ticket.getName());
			ticketDescriptionTextArea.setText(ticket.getDescription());
			departmentCombo.setValue(ticket.getDepartment());
			ticketIssuerText.setText(ticket.getIssuer());
		}
	}

	// Fill Ticket
	private void fillTicket(Ticket ticket) throws ClassNotFoundException {
		// Declare and ObservableList for table view
		ObservableList<Ticket> ticketData = FXCollections.observableArrayList();
		// Add ticket to the ObservableList
		ticketData.add(ticket);
		// Set items to the ticketTable
		ticketTable.setItems(ticketData);
	}

	// Fill Ticket for TableView and Display Ticket on TextArea
	private void fillAndShowTicket(Ticket ticket) throws ClassNotFoundException {
		if (ticket != null) {
			fillTicket(ticket);
		} else {
			System.out.println("This ticket does not exist!\n");
		}
	}

	// Fill Tickets for TableView
	private void fillTickets(ObservableList<Ticket> ticketData) throws ClassNotFoundException {
		// Set items to the ticketTable
		ticketTable.setItems(ticketData);
	}

	// Update ticket's email with the email which is written on newEmailText field
	@FXML
	private void updateTicket(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TicketDAO.updateTicket(ticketIdText.getText(), ticketNameText.getText(),
					ticketDescriptionTextArea.getText(), departmentCombo.getValue(), ticketIssuerText.getText(),
					user.getUserId(), statusCombo.getValue().toString());

			searchTicketsBtn.fire();

			System.out.println("Ticket has been updated for, ticket id: " + ticketIdText.getText() + "\n");
		} catch (SQLException e) {
			System.out.println("Problem occurred while updating ticket name: " + e);
		}
	}

	// Insert an ticket to the DB
	@FXML
	private void insertTicket(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TicketDAO.insertTicket(ticketNameText.getText(), ticketDescriptionTextArea.getText(),
					departmentCombo.getValue().toString(), ticketIssuerText.getText(), user.getUserId(),
					statusCombo.getValue().toString());
			System.out.println("Ticket inserted! \n");
		} catch (SQLException e) {
			System.out.println("Problem occurred while inserting ticket " + e);
			throw e;
		}
	}

	// Delete an ticket with a given ticket Id from DB
	@FXML
	private void deleteTicket(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			TicketDAO.deleteTicketWithId(ticketIdText.getText());
			System.out.println("Ticket deleted! Ticket id: " + ticketIdText.getText() + "\n");
		} catch (SQLException e) {
			System.out.println("Problem occurred while deleting ticket " + e);
			throw e;
		}
	}
}