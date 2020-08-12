package Client;

public class User {

	private int userID;
	private String firstName;
	private String lastName;
	private String phone1;
	private String email1;
	
	public User(int userID, String firstName, String lastName, String phone1, String email1) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone1 = phone1;
		this.email1 = email1;
	}

	public int getUserID() {
		return userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone1() {
		return phone1;
	}

	public String getEmail1() {
		return email1;
	}
	
	
}
