package application;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Start extends Application {
	Button complexNumBtn;
	Button vectorsBtn;
	Button matrixBtn;
	Button ltBtn;
	Button chatBtn;

	public static ResourceBundle rb;

	public void updateValues() {
		complexNumBtn.setText(rb.getString("complex_title"));
		vectorsBtn.setText(rb.getString("vectors_title"));
		matrixBtn.setText(rb.getString("matrix_title"));
		ltBtn.setText(rb.getString("linear_transformations"));
		chatBtn.setText(rb.getString("chat_title"));
	}

	@Override
	public void start(Stage stage) throws Exception {

		HBox navbar=new HBox();
		ComboBox<String> language= new ComboBox<String>();
		language.getItems().addAll("Español","English");
		language.setValue(rb.getString("select_cbox"));
		navbar.getChildren().add(language);

		VBox main = new VBox();
		complexNumBtn = new Button(rb.getString("complex_title"));
		complexNumBtn.setPrefWidth(280d);
		complexNumBtn.setPrefHeight(20d);
		complexNumBtn.setOnAction((event) -> {
			ComplexNumGUI complexNumGUI = new ComplexNumGUI();
			try {
				complexNumGUI.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		vectorsBtn = new Button(rb.getString("vectors_title"));
		vectorsBtn.setPrefWidth(280d);
		vectorsBtn.setPrefHeight(20d);
		vectorsBtn.setOnAction((event) -> {
			VectorsGUI vectorsGUI = new VectorsGUI();
			try {
				vectorsGUI.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		matrixBtn = new Button(rb.getString("matrix_title"));
		matrixBtn.setPrefWidth(280d);
		matrixBtn.setPrefHeight(20d);
		matrixBtn.setOnAction((event) -> {
			MatrixGUI matrixGUI = new MatrixGUI(rb);
			try {
				matrixGUI.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		ltBtn = new Button(rb.getString("linear_transformations"));
		ltBtn.setPrefWidth(280d);
		ltBtn.setPrefHeight(20d);
		ltBtn.setOnAction((event) -> {
			LinearTransformationGUI linearTransformGUI = new LinearTransformationGUI();
			try {
				linearTransformGUI.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		chatBtn = new Button(rb.getString("chat_title"));
		chatBtn.setPrefWidth(280d);
		chatBtn.setPrefHeight(20d);
		chatBtn.setOnAction((event) -> {
			ChatClient chat=new ChatClient(rb);
			try {
				chat.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		language.setOnAction((event) -> {
			String resourcesPath = "resources.Bundle";
			switch(language.getValue().toLowerCase()) {
			case "español":
				rb = ResourceBundle.getBundle(resourcesPath, new Locale("es", "mx"));
				updateValues();
				System.out.println(rb.getLocale());					
				break;
			case "english":
				rb = ResourceBundle.getBundle(resourcesPath, new Locale("en", "us"));
				updateValues();
				System.out.println(rb.getLocale());
				break;
			default:
				break;
			}
		});

		main.setSpacing(5d);
		main.setPadding(new Insets(10, 0, 0, 10));
		main.getChildren().addAll(navbar,complexNumBtn, vectorsBtn, matrixBtn, ltBtn,chatBtn);

		Scene scene = new Scene(main, 300, 150);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setWidth(305);
		stage.setHeight(250);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle("Linear FX");
		stage.show();


	}


	public static void main(String[] args) {
		Locale locale = new Locale("es", "mx");
		String resourcesPath = "resources.Bundle";
		rb = ResourceBundle.getBundle(resourcesPath,locale);
		launch(args);
	}

}
