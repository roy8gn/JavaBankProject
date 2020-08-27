package Client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class Main extends Application{
	FXMLLoader loader;
	
	//FXMLLoader transferLoader, mainMenuLoader, f3;
	Parent root;
	@FXML static Stage window;
	@FXML static Scene logInScene, mainMenuScene, transferScene;
	public static SceneController sceneController;
	@FXML private static LogInController logInController;
	@FXML private static MainMenuController mainMenuController;
	@FXML private static TransferController transferController;
	
    @Override
    public void start(Stage primaryStage) throws IOException {
        
		this.window = primaryStage;
		sceneController = new SceneController(window);
		
		loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        mainMenuScene = new Scene(root, 898, 699);
        mainMenuScene.getStylesheets().add("Client/Style.css");
        sceneController.add("MainMenu", mainMenuScene);
        mainMenuController = (MainMenuController) loader.getController();
		
		loader = new FXMLLoader(getClass().getResource("TransferForm.fxml"));
        root = loader.load();
        transferScene = new Scene(root, 468, 380);
        transferScene.getStylesheets().add("Client/Style.css");
        sceneController.add("Transfer", transferScene);
        transferController = (TransferController) loader.getController();
        
    	loader = new FXMLLoader(getClass().getResource("LogInForm.fxml"));
        root = loader.load();
        logInScene = new Scene(root, 550, 600);
        logInScene.getStylesheets().add("Client/Style.css");
        sceneController.add("LogIn", logInScene);
        logInController = (LogInController) loader.getController();
        
        window.setScene(logInScene);
        window.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

	public static LogInController getLogInController() {
		return logInController;
	}

	public static MainMenuController getMainMenuController() {
		return mainMenuController;
	}

	public static TransferController getTransferController() {
		return transferController;
	}
    
    
    
}
