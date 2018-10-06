package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ComplexNumGUI extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root=new BorderPane();
		Scene scene=new Scene(root,400,500);
		stage.setScene(scene);
		stage.show();
		
	}

}
