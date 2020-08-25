package ClientServerCommunication;

import java.io.Serializable;
import java.util.ArrayList;


/*
 * An object that will allow a common way to communicate and pass
 * data between the Client and the Server
 */
public class ClientServerObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ClientServerObject instance = null; // Instance of ClientServerObject (Using Singleton)
	private ClientActions clientAction;
	private ArrayList<Object> dataToSend;
	private ActionResult actionResult;

	
	private ClientServerObject() {
		this.clientAction = ClientActions.DEAFULT;
		dataToSend = new ArrayList<>();
		this.actionResult = ActionResult.DEAFULT;
	}
	
	public static ClientServerObject getInstance() // get the instance of ClientServerObject
    { 
        if (instance == null) 
        	instance = new ClientServerObject(); 
        return instance; 
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

	public ActionResult getActionResult() {
		return actionResult;
	}

	public void setActionResult(ActionResult actionResult) {
		this.actionResult = actionResult;
	}

	public void clear() {
		clientAction = ClientActions.DEAFULT;
		dataToSend.clear();
		actionResult = ActionResult.DEAFULT;
	}
	
	
	
	
}
