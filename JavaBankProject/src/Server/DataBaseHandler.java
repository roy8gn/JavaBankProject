package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import ClientServerCommunication.ActionResult;
import ClientServerCommunication.ClientServerObject;
import ClientServerCommunication.Transaction;

public class DataBaseHandler {

	private static DataBaseHandler dataBaseInstance = null; // Instance of Client (Using Singleton)
	private Connection conn;
	
	private DataBaseHandler() {
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 System.out.println("Driver definition failed");
        	 }
        
        try 
        {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javabankdb?serverTimezone=IST","root","Aa123456");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
            System.out.println("SQL connection succeed");
            //createTableCourses(conn);
            //printCourses(conn);
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
        
      
	}
	
	public static DataBaseHandler getInstance() // get the instance of the Data Base Handler
    { 
        if (dataBaseInstance == null) 
        	dataBaseInstance = new DataBaseHandler(); 
        return dataBaseInstance; 
    }
	
	public void transfer(ClientServerObject cso) {
		String accoutFrom;
		int amount;
		String accountTo;
		
		accoutFrom = (String) cso.getDataToSend().get(0);
		amount = ((Integer) cso.getDataToSend().get(1)).intValue();
		accountTo = (String) cso.getDataToSend().get(2);
		
		if(checkIfAccountExists(accountTo)) {
			updateAccountAmount(accoutFrom, amount*(-1));
			updateAccountAmount(accountTo, amount);
			updateTransactionsTable(accoutFrom, amount*(-1), accountTo);
			updateTransactionsTable(accountTo, amount, accoutFrom);
			cso.clear();
			cso.setActionResult(ActionResult.YES);
			cso.addDataToSend(checkCurrentAmount(accoutFrom));
		}
		else {
			cso.setActionResult(ActionResult.NO);
		}
	}
	
	public void updateAccountAmount(String accountID, int amount) { // UPDATE the amount of an account
		int accountAmount = checkCurrentAmount(accountID);
		accountAmount = accountAmount + amount;
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("update javabankdb.bankaccouts set bankaccouts.amount = ? where bankaccouts.accountID = ?;");
			pstmt.setInt(1, accountAmount);
			pstmt.setString(2, accountID);
			pstmt.executeUpdate();
			
			//pstmt.close();
		} catch (SQLException e) {
			System.out.println("ERROR: UPDATE QUERY");
		}
		
	}
	
	public int checkCurrentAmount(String accountID) { // check the current amount of an account
		int currentAmount=0;
		PreparedStatement pstmt;
		ResultSet rs;
		
		try {
			pstmt = conn.prepareStatement("select bankaccouts.amount\r\n" + 
					"from javabankdb.bankaccouts\r\n" + 
					"where bankaccouts.accountID=?;");
			pstmt.setString(1, accountID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				currentAmount = rs.getInt(1);
			}
			return currentAmount;
		} catch (SQLException e) {}
		return -999999999;
	}
	
	// update the transactions table
	public void updateTransactionsTable(String accontFrom, int amount, String accountTo) {
		
		PreparedStatement pstmt;
		String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		try {
			pstmt = conn.prepareStatement("insert into javabankdb.transactions (date1, fromAccount, amount1, toAccount) "
					+ "values (?, ?, ?, ?)");
			pstmt.setString(1, currentDate);
			pstmt.setString(2, accontFrom);
			pstmt.setInt(3, amount);
			pstmt.setString(4, accountTo);
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println("ERROR: INSERT QUERY");
		}
	}
	
	public boolean checkIfAccountExists(String accountID) { // check if the account exists in the data base
		PreparedStatement pstmt;
		ResultSet rs;
		
		try {
			pstmt = conn.prepareStatement("select bankaccouts.accountID\r\n" + 
					"from javabankdb.bankaccouts\r\n" + 
					"where bankaccouts.accountID=?;");
			pstmt.setString(1, accountID);
			rs = pstmt.executeQuery();
			if(rs.next()) return true;
		} catch (SQLException e) {
			return false;
		}
		
		return false;
	}
	
	public void connectToBank(ClientServerObject cso) {	
		try 
		{
			PreparedStatement pstmt;
			ResultSet rs;
			pstmt = conn.prepareStatement("select users.userID, bankaccouts.accountID, bankaccouts.password1, users.firstName, users.lastName, users.birthDate, users.email1, bankaccouts.amount\r\n" + 
					"from javabankdb.users, javabankdb.bankaccouts\r\n" + 
					"where bankaccouts.userID=? and users.userID=? and bankaccouts.accountID=? and bankaccouts.password1=?;");
			pstmt.setString(1, (String) cso.getDataToSend().get(0));
			pstmt.setString(2, (String) cso.getDataToSend().get(0));
			pstmt.setString(3, (String) cso.getDataToSend().get(1));
			pstmt.setString(4, (String) cso.getDataToSend().get(2));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cso.clearData();
				cso.addDataToSend(rs.getString(1)); // userID
				cso.addDataToSend(rs.getString(2)); // accountID
				cso.addDataToSend(rs.getString(3)); // password1
				cso.addDataToSend(rs.getString(4)); // firstName
				cso.addDataToSend(rs.getString(5)); // lastName
				cso.addDataToSend(rs.getString(6)); // birthDate
				cso.addDataToSend(rs.getString(7)); // email1
				cso.addDataToSend(rs.getInt(8)); // amount
				getAllTransactions(cso);
				cso.setActionResult(ActionResult.YES);
			}
			else {
				cso.clearData();
				cso.setActionResult(ActionResult.NO);
			}
			
		}catch (SQLException e) {e.printStackTrace();
		}
	}
	
	public void getAllTransactions(ClientServerObject cso) {	
		ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = conn.prepareStatement("select date1, fromAccount, amount1, toAccount from  javabankdb.transactions"
					+ " where fromAccount=?;");
			pstmt.setString(1, (String) cso.getDataToSend().get(1)); // accountID (after getting data from Data Base for login)
			rs = pstmt.executeQuery();
			while(rs.next()) {
				transactionsList.add(new Transaction(rs.getString(1), // date1
						rs.getString(2), // fromAccount
						rs.getInt(3), // amount1
						rs.getString(4))); // toAccount
			}
			cso.addDataToSend(transactionsList);
			//System.out.println(transactionsList);
		} catch (SQLException e) {
			System.out.println("ERROR: CAN'T GET BANK ACCOUNT'S TRANSACTIONS!!!");
		}
		
		
		
	}
	
}
