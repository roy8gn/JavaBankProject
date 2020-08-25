package Client;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import ClientServerCommunication.ActionResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController implements Initializable {

	@FXML
    private TextField userIDText;

    @FXML
    private Button connectBtn;

    @FXML
    private TextField accountIDText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button ForgotMyPasswordBtn;

    
    
    Alert a = new Alert(AlertType.NONE); 
    
    
    @FXML
    private Label mainLabel;

    
    @FXML
    void TryToConnect(ActionEvent event) {
    	try {
			Client.getInstance().tryToConnect(userIDText.getText().toString(),
					accountIDText.getText().toString(), passwordText.getText().toString());
			
			
			if(Client.getInstance().getActionResult()==ActionResult.YES) {
				/*a.setAlertType(AlertType.INFORMATION);
				a.setContentText("Welcome to JBank Digital!");
				a.show();*/
				Thread.sleep(500);
				Main.getMainMenuController().setUp();
				Main.sceneController.showScene("MainMenu");
				//Main.screenController.activate("Main Menu");
			}
			else if(Client.getInstance().getActionResult()==ActionResult.NO) {
				 a.setAlertType(AlertType.ERROR);
				 a.setContentText("At least one of the deatils is wrong.");
				 a.show();
				// e -> 
				 //Client.getInstance().closeConnection();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("There was a problem while connecting.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("There was a problem while connecting.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("There was a problem while connecting.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("There was a problem while connecting.");
		}
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
    
    
    
}

