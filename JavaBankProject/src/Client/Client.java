package Client;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ClientServerCommunication.ClientActions;
import ClientServerCommunication.ClientServerObject;


public class Client { // Client object is in charge of the communication with the server

	private static Client clientInstance = null; // Instance of Client (Using Singleton)
	private InetAddress host;
	private Socket socket;
	private ObjectOutputStream  output;
	private ObjectInputStream input;
	private ClientServerObject clientServerObject; // Client-Server translator
	
	private boolean isConnected = false;
	
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
        this.clientServerObject = new ClientServerObject();
	}
	
	public static Client getInstance() // get the instance of Client
    { 
        if (clientInstance == null) 
        	clientInstance = new Client(); 
  
        return clientInstance; 
    } 
	
	// try to login to the system by checking the userID, accountID and password
	public void tryToConnect(String userID, String accountID, String password) throws IOException,
	ClassNotFoundException, InterruptedException{
		
		this.clientServerObject.setClientAction(ClientActions.LOGIN);
		this.clientServerObject.clearData();
		this.clientServerObject.addDataToSend(userID, accountID, password);
		
        
        this.socket = new Socket(host.getHostName(), 9876);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        
        output.writeObject(this.clientServerObject);
        System.out.println("Object was sent to server.");
        
	}
}
