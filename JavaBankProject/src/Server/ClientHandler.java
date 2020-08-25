package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import ClientServerCommunication.ActionResult;
import ClientServerCommunication.ClientServerObject;

class ClientHandler extends Thread  
{ 
    
    final ObjectInputStream input; 
    final ObjectOutputStream output; 
    final Socket socket; 
    private DataBaseHandler dataBaseInstance;
    private ClientServerObject clientServerObject;  
    private boolean running;
    // Constructor 
    public ClientHandler(Socket socket, ObjectInputStream input, ObjectOutputStream output)  
    { 
        this.socket = socket; 
        this.input = input; 
        this.output = output;
        this.dataBaseInstance = Server.getDataBaseInstance();
        running = true;
       
    } 
  
    @Override
    public void run(){  
        while (running)  { 
        	try{
				clientServerObject = (ClientServerObject) input.readObject();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            switch(clientServerObject.getClientAction()){
            case LOGIN:
            	dataBaseInstance.connectToBank(clientServerObject);
            	try {
					output.writeObject(this.clientServerObject);
				} catch (IOException e) { // In case of any exception, assume login has failed
					clientServerObject.clearData();
					clientServerObject.setActionResult(ActionResult.NO);
				}
            	break;
            case TRANSFER:
            	dataBaseInstance.transfer(clientServerObject);
            	try {
            		//clientServerObject.clearData();
					output.writeObject(this.clientServerObject);
				} catch (IOException e) { // In case of any exception, assume transfer has failed
					clientServerObject.clearData();
					clientServerObject.setActionResult(ActionResult.NO);
				}
            	break;
            case EXIT:
            	closeConnection();
            	running = false;
            	break;
			default:
				break;
            }   
        } 
    }
    
    @SuppressWarnings("deprecation")
	public void closeConnection(){
    	try
        { 
            // closing resources 
            this.input.close(); 
            this.output.close(); 
            System.out.println("Client "+socket + "has disconnected.");
            this.socket.close();  
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    }
}