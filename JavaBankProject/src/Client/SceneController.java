package Client;

import java.util.HashMap;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage window;
	//private Parent root;
	private static SceneController instance;
	HashMap<String, Scene> scenes;
	
	
	public SceneController(Stage window) {
		this.window = window;
		this.scenes = new HashMap<String, Scene>();
	}
	
	public void add(String name, Scene scene) {
		scenes.put(name, scene);
	}
	
	public Scene getScene(String name) {
		return scenes.get(name);
	}
	
	public void showScene(String name) {
		window.setScene(getScene(name));
	}
	
}
