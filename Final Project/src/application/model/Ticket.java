package application.model;

import javafx.beans.property.*;

public class Ticket {
	private IntegerProperty ticketId;
	private StringProperty name;
	private StringProperty description;
	private StringProperty department;
	private IntegerProperty userId;
	private StringProperty issuer;

	// Constructor
	public Ticket() {
		this.ticketId = new SimpleIntegerProperty();
		this.name = new SimpleStringProperty();
		this.description = new SimpleStringProperty();
		this.department = new SimpleStringProperty();
		this.issuer = new SimpleStringProperty();
		this.userId = new SimpleIntegerProperty();
	}

	// ticketId
	public int getTicketId() {
		return ticketId.get();
	}

	public void setTicketId(int ticketId) {
		this.ticketId.set(ticketId);
	}

	public IntegerProperty ticketIdProperty() {
		return ticketId;
	}

	// name
	public String getName() {
		return name.get();
	}

	public void setName(String ticketname) {
		this.name.set(ticketname);
	}

	public StringProperty nameProperty() {
		return name;
	}

	// description
	public String getDescription() {
		return description.get();
	}

	public void setDescription(String firstName) {
		this.description.set(firstName);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	// department
	public String getDepartment() {
		return department.get();
	}

	public void setDepartment(String departmentId) {
		this.department.set(departmentId);
	}

	public StringProperty departmentProperty() {
		return department;
	}

	// userid
	public int getUserId() {
		return userId.get();
	}

	public void setUserId(int userId) {
		this.userId.set(userId);
	}

	public IntegerProperty userIdProperty() {
		return userId;
	}

	// issuerid
	public String getIssuer() {
		return issuer.get();
	}

	public void setIssuer(String issuer) {
		this.issuer.set(issuer);
	}

	public StringProperty issuerProperty() {
		return issuer;
	}

}
