package Server;



import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server{

	
	static int port = 9876;
    private static ServerSocket serverSocket;
    public static DataBaseHandler dataBase;
    public static void main(String args[]) throws IOException, ClassNotFoundException{
    	
    	serverSocket = new ServerSocket(port);
    	dataBase = DataBaseHandler.getInstance();
    	System.out.println("Server is on.");
    	// running infinite loop for getting 
        // client request 
        while (true)  
        { 
            Socket socket = null; 
              
            try 
            { 
                // socket object to receive incoming client requests 
            	socket = serverSocket.accept(); 
                  
                System.out.println("A new client is connected : " + socket); 
                  
                // obtaining input and out streams 
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream()); 
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
  
                // create a new thread object 
                Thread t = new ClientHandler(socket, input, output); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e){ 
            	socket.close(); 
                e.printStackTrace(); 
            } 
        }
    	
    }
    
    public static DataBaseHandler getDataBaseInstance() {
    	return dataBase;
    }

}
