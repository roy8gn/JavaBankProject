package Client;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController {

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

    @FXML
    private Button createNewAcoountBtn;
    
    
    @FXML
    void TryToConnect(ActionEvent event) {
    	try {
			Client.getInstance().tryToConnect(userIDText.getText().toString(),
					accountIDText.getText().toString(), passwordText.getText().toString());
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
}

