package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class WebSiteFXMLController {
	@FXML private VBox container;
	@FXML private HBox menuContainer;	
	@FXML private Label URLlabel;	
	@FXML private TextField URLField;
	@FXML private Button GoButton;
	@FXML private Button BackButton;
	@FXML private WebView viewer;
	
	WebEngine webEngine;
	
	@FXML
	private void initialize() {
		webEngine=viewer.getEngine();
		webEngine.locationProperty().addListener((ov,oldVal,newVal)->{URLField.setText(newVal);});		
		webEngine.load("https://www.youtube.com/watch?v=u7v8uBNMNaI");
		}
	
	
	public void loadPage(){
		webEngine.load(URLField.getText());
	}
	
	public void backToSource() {
		Start main= new Start();
		try {
			main.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


}
