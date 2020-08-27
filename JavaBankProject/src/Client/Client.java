package Client;
import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ClientServerCommunication.ActionResult;
import ClientServerCommunication.ClientActions;
import ClientServerCommunication.ClientServerObject;
import ClientServerCommunication.Transaction;


public class Client { // Client object is in charge of the communication with the server

	private static Client clientInstance = null; // Instance of Client (Using Singleton)
	private InetAddress host;
	private Socket socket;
	private ObjectOutputStream  output;
	private ObjectInputStream input;
	private ClientServerObject clientServerObject; // Client-Server translator
	private User user;
	
	private Client() {
		try {
			this.host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.socket = null;
        this.output = null;
        this.input = null;	
        this.clientServerObject = ClientServerObject.getInstance();

	}
	
	public static Client getInstance() // get the instance of Client
    { 
        if (clientInstance == null) 
        	clientInstance = new Client(); 
        return clientInstance; 
    } 
	
	// try to login to the system by checking the userID, accountID and password
	@SuppressWarnings("unchecked")
	public void tryToConnect(String userID, String accountID, String password) throws IOException,
	ClassNotFoundException, InterruptedException{
		
		this.clientServerObject.setClientAction(ClientActions.LOGIN);
		this.clientServerObject.clearData();
		this.clientServerObject.addDataToSend(userID, accountID, password);
		
        this.socket = new Socket(host.getHostName(), 9876);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
        
        output.writeObject(this.clientServerObject);
        System.out.println("Trying to login");
        //output.flush();
        
        try{
			clientServerObject = (ClientServerObject) input.readObject();
			if(clientServerObject.getActionResult()==ActionResult.YES) {
			this.user = new User((String) clientServerObject.getDataToSend().get(0),
					(String) clientServerObject.getDataToSend().get(1),
					(String) clientServerObject.getDataToSend().get(2),
					(String) clientServerObject.getDataToSend().get(3),
					(String) clientServerObject.getDataToSend().get(4),
					(String) clientServerObject.getDataToSend().get(5),
					(String) clientServerObject.getDataToSend().get(6),
					(Integer)clientServerObject.getDataToSend().get(7),
					(ArrayList<Transaction>)clientServerObject.getDataToSend().get(8));
			Main.getMainMenuController().setUp();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("ERROR: Couldn't login");
		}catch (IOException e) {
			System.out.println("ERROR: Couldn't login");
		}
        
	}
	
	
	
	public void trnasfer(int amount, String toAccountID) { // transfer money to another account
		clientServerObject.clear();
		clientServerObject.setClientAction(ClientActions.TRANSFER);
		clientServerObject.addDataToSend(user.getAccountID() ,new Integer(amount), toAccountID);
		clientServerObject.setActionResult(ActionResult.DEAFULT);
		try {
			output.writeObject(clientServerObject);
			
		} catch (IOException e) {
			System.out.println("ERROR: Couldn't transfer money");
		}
		
		
		try{
			clientServerObject = (ClientServerObject) input.readObject();
			Main.getTransferController().setTransferResult(clientServerObject.getActionResult());
			if(clientServerObject.getActionResult()==ActionResult.YES) {
				this.user.setAmount(((Integer) clientServerObject.getDataToSend().get(0)).intValue());
				System.out.println("New amount: " + user.getAmount());
			}
		}catch (IOException e) {
			System.out.println("ERROR: Couldn't transfer");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: Couldn't transfer");
		}
		
		clientServerObject.clear();
	}
	
	public void exit() { // Exit from system
		closeConnection();
		System.exit(0); 
	}
	

	
	public void closeConnection() { // close connection with server
		try {
			clientServerObject.setClientAction(ClientActions.EXIT);
			output.writeObject(clientServerObject);
			this.output.close();
			this.input.close();
			this.socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// ************** Getters and Setters *************** //
	
	public ActionResult getActionResult() {
		return this.clientServerObject.getActionResult();
	}
	
	public User getUser() {
		return this.user;
	}
}
