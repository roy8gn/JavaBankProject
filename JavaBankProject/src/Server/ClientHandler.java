package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import ClientServerCommunication.ClientServerObject;

class ClientHandler extends Thread  
{ 
    
    final ObjectInputStream input; 
    final ObjectOutputStream output; 
    final Socket socket; 
    private ClientServerObject clientServerObject;  
  
    // Constructor 
    public ClientHandler(Socket socket, ObjectInputStream input, ObjectOutputStream output)  
    { 
        this.socket = socket; 
        this.input = input; 
        this.output = output;
    } 
  
    @Override
    public void run()  
    {  
        while (true)  
        { 
        	try {
				clientServerObject = (ClientServerObject) input.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            switch(clientServerObject.getClientAction()) {
            case LOGIN:
            	System.out.println(clientServerObject.getDataToSend().get(0));
            	break;
            case EXIT:
            	closeConnection();
            	break;
			default:
				break;
            }   
        } 
    }
    
    public void closeConnection() {
    	try
        { 
            // closing resources 
            this.input.close(); 
            this.output.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    }
}