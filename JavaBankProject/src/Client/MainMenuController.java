package Client;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainMenuController {

	

    @FXML
    private Button transferBtn;

    @FXML
    private TableColumn<?, ?> amountAfter;

    @FXML
    private TableView<?> transactionsTable;

    @FXML
    private Button exitBtn;

    @FXML
    private TableColumn<?, ?> amount1;

    @FXML
    private Label firstNameText;
    
    @FXML
    private Label amountText;

    @FXML
    private TableColumn<?, ?> toOrFrom;

    @FXML
    private TableColumn<?, ?> date1;

    @FXML
    private TableColumn<?, ?> type1;

	    
	@FXML
    public void setUp() {
		firstNameText.setText("Hello "+Client.getInstance().getUser().getFirstName()+",");
		amountText.setText(""+Client.getInstance().getUser().getAmount()+"¤");
		//System.out.println(Client.getInstance().getUser().getEmail1());
		 if(Client.getInstance().getUser().getAmount()<0){
			 amountText.setStyle("-fx-text-fill: red;");
		 }
		 else {
			 amountText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
		 }
	}
    
    @FXML
    void tranferMoney(ActionEvent event) {
    	Main.getTransferController().setUp();
    	Main.sceneController.showScene("Transfer");
    }


}

	



