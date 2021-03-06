package Client;

import java.text.SimpleDateFormat;
import java.util.Date;

import ClientServerCommunication.ActionResult;
import ClientServerCommunication.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TransferController {

    @FXML
    private TextField accountIDText;

    @FXML
    private TextField amountText;

    @FXML
    private Button backBtn;

    @FXML
    private Button TransferBtn;

    @FXML
    private Label resultLabel;
    
    @FXML
    private Label amountLabel;

    private int currentAmount;
    
    
    private ActionResult transferResult;
    
    @FXML
    void transfer(ActionEvent event) {
    	Alert alert;
    	int toTransfer = Integer.parseInt(amountText.getText().toString());
    	String toAccountID = accountIDText.getText().toString();
    	currentAmount = Client.getInstance().getUser().getAmount();
    	if(currentAmount<toTransfer || currentAmount<=0) {
    		alert= new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setContentText("You can't tranfer that amount of money.");
    		alert.show();
    	}
    	else {
    		Client.getInstance().trnasfer(toTransfer, toAccountID);
    		
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		if(transferResult==ActionResult.YES) {
    			resultLabel.setText("Transferd " + toTransfer +"� to account number " + toAccountID +".");
    			resultLabel.setStyle("-fx-text-fill: green;");
    			
    		}
    		else {
    			resultLabel.setText("ERROR! Couldn't transfer money.");
    			resultLabel.setStyle("-fx-text-fill: red;");
    		}
    		TransferBtn.setDisable(true);
			accountIDText.setDisable(true);
			amountText.setDisable(true);
			amountLabel.setText(""+Client.getInstance().getUser().getAmount()+"�");
			
			String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			Main.getMainMenuController().addNewTransactionToTheTable(new Transaction(currentDate,
					Client.getInstance().getUser().getAccountID(),
					Integer.parseInt(amountText.getText().toString())*(-1),
					accountIDText.getText().toString()));
    	}
    }
    
    @FXML
    public void setUp() {
    	amountLabel.setText(""+Client.getInstance().getUser().getAmount()+"�");
    	if(Client.getInstance().getUser().getAmount()<0){
    		amountLabel.setStyle("-fx-text-fill: red;");
		 }
		 else {
			 amountLabel.setStyle("-fx-text-fill: green;");
		 }
    	accountIDText.clear();
    	amountText.clear();
    }
    
    @FXML
    void back(ActionEvent event) {
    	clear();
    	Main.getMainMenuController().setUp();
    	Main.sceneController.showScene("MainMenu");
    }
    
    @FXML
    void clear() {
    	accountIDText.clear();
    	resultLabel.setText("");
    	amountText.clear();
    	amountLabel.setStyle("-fx-text-fill: black;");
    	TransferBtn.setDisable(false);
		accountIDText.setDisable(false);
		amountText.setDisable(false);
    }
    

	public void setTransferResult(ActionResult actionResult) {
		transferResult = actionResult;
	}
}