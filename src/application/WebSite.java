package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WebSite extends Application{
	ResourceBundle rb;
	public WebSite(ResourceBundle rb) {
		this.rb=rb;
	}
	
	public WebSite() {}
	
	public void start(Stage stage) throws Exception {
		URL fxmlResource = getClass().getResource("WebSite.fxml");
		VBox root=FXMLLoader.load(fxmlResource);
		Scene scene=new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Tutorial WebViewer");
		stage.setResizable(true);
		stage.show();
	}

}
