package Client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client { // Client object is in charge of the communication with the server

	private static Client clientInstance = null; // Instance of Client (Using Singleton)
	private InetAddress host;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	
	private Client() {}
	
	public static Client getInstance() // get the instance of Client
    { 
        if (clientInstance == null) 
        	clientInstance = new Client(); 
  
        return clientInstance; 
    } 
	
	// try to login to the system by checking the userID, accountID and password
	public void tryToConnect(String userID, String accountID, String password) throws UnknownHostException,
	IOException, ClassNotFoundException, InterruptedException{
		
		this.host = InetAddress.getLocalHost();
        this.socket = null;
        this.oos = null;
        this.ois = null;
        
        this.socket = new Socket(host.getHostName(), 9876);
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Sending request to Socket Server");
        
        this.ois = new ObjectInputStream(socket.getInputStream());
        String message = (String) ois.readObject();
        System.out.println("Message: " + message);
        //close resources
        this.ois.close();
        this.oos.close();
        
	}
}
