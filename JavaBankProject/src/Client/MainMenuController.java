package Client;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientServerCommunication.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainMenuController implements Initializable{

	

	 	@FXML
	    private Button transferBtn;

	    @FXML
	    private Label amountLabel;

	    @FXML
	    private TableView<Transaction> transactionsTable;

	    @FXML
	    private Button exitBtn;

	    @FXML
	    private TableColumn<Transaction, Integer> amount1;

	    @FXML
	    private TableColumn<Transaction, String> accountFrom;

	    @FXML
	    private TableColumn<Transaction, String> date1;

	    @FXML
	    private TableColumn<Transaction, String> accountTo;

	    @FXML
	    private Label helloLabel;
	    
	    private ObservableList<Transaction> data;
	    
	    private Client clientInstance;

	    private boolean firstTableInitialize = false; // indicate if there is a need to initialize the transactions table
	    
	@FXML
    public void setUp() {
		helloLabel.setText(Client.getInstance().getUser().getFirstName() +" "
    + Client.getInstance().getUser().getLastName() + " | " +
    Client.getInstance().getUser().getAccountID() + " | " + Client.getInstance().getUser().getEmail1() +
    " | " + Client.getInstance().getUser().getBirthDate());
		
		
		amountLabel.setText(""+Client.getInstance().getUser().getAmount()+"¤");
		if(Client.getInstance().getUser().getAmount()<0){
			 amountLabel.setStyle("-fx-text-fill: red;");
		 }
		 else {
			 amountLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
		 }
		
		if(firstTableInitialize==false) {
			ArrayList<Transaction> transactionsList = Client.getInstance().getUser().getTransactionsList();
			data = FXCollections.observableArrayList();
			
			date1.setCellValueFactory(new PropertyValueFactory<Transaction, String>("date"));
			accountFrom.setCellValueFactory(new PropertyValueFactory<Transaction, String>("accountFrom"));
			amount1.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("amount"));
			accountTo.setCellValueFactory(new PropertyValueFactory<Transaction, String>("accountTo"));
			
			for(Transaction transaction : transactionsList) {
				data.add(transaction);
			}
			
			
			transactionsTable.setItems(data);
			
			firstTableInitialize=true;
		}
		
	}
	
	@FXML
    public void addNewTransactionToTheTable(Transaction newTransaction) {
		transactionsTable.getItems().add(newTransaction);
	}
    
    @FXML
    public void tranferMoney(ActionEvent event) {
    	Main.getTransferController().setUp();
    	Main.sceneController.showScene("Transfer");
    }

    @FXML
    void exit(ActionEvent event) {
    	Client.getInstance().exit();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		clientInstance = Client.getInstance();
	}
    

}

	



