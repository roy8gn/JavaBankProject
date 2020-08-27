package ClientServerCommunication;

import java.io.Serializable;

public class Transaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String accountFrom;
	private int amount;
	private String accountTo;
	
	public Transaction(String date, String accountFrom, int amount, String accountTo) {
		this.date = date;
		this.accountFrom = accountFrom;
		this.amount = amount;
		this.accountTo = accountTo;
	}
	
	public String getDate() {
		return date;
	}
	public String getAccountFrom() {
		return accountFrom;
	}
	public int getAmount() {
		return amount;
	}
	public String getAccountTo() {
		return accountTo;
	}
	
	
}
