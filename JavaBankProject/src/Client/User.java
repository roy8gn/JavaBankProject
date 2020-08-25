package Client;

import java.util.Date;

//import java.sql.Date;


public class User {

	private String userID;
	private String accountID;
	private String password;
	private String firstName;
	private String lastName;
	private String birthDate;
	private String email1;
	private int amount;
	
	public User(String userID, String accountID, String password, String firstName, 
			String lastName, String date, String email1, int amount) {
		this.userID = userID;
		this.accountID = accountID;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = date;
		this.email1 = email1;
		this.amount = amount;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getAccountID() {
		return accountID;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail1() {
		return email1;
	}	
}
