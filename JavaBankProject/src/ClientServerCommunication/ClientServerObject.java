package ClientServerCommunication;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * An object that will allow a common way to communicate and pass
 * data between the Client and the Server
 */
public class ClientServerObject implements Serializable{

	private ClientActions clientAction;
	private ArrayList<Object> dataToSend;
	
	
	public ClientServerObject() {
		dataToSend = new ArrayList<>();
	}
	
	
	public ClientActions getClientAction() {
		return clientAction;
	}
	public void setClientAction(ClientActions clientAction) {
		this.clientAction = clientAction;
	}
	public ArrayList<Object> getDataToSend() {
		return dataToSend;
	}
	public void addDataToSend(Object... objects) {
		for(Object object:objects)
		this.dataToSend.add(object);
	}
	
	public void clearData() { // Clear the data of the ArrayList
		if(!dataToSend.isEmpty()) {
			dataToSend.clear();
		}
	}
	
	
}
