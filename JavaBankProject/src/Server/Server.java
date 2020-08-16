package Server;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{

	
	static int port = 9876;
    private static ServerSocket serverSocket;
    
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
    	
    	serverSocket = new ServerSocket(port);
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
                ObjectInputStream dis = new ObjectInputStream(socket.getInputStream()); 
                ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());  
  
                // create a new thread object 
                Thread t = new ClientHandler(socket, dis, dos); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e){ 
            	socket.close(); 
                e.printStackTrace(); 
            } 
        }
    	
    }

}
