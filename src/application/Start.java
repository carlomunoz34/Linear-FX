package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Start extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		VBox main = new VBox();
		Button complexNumBtn = new Button("Números Complejos");
		complexNumBtn.setPrefWidth(280d);
		complexNumBtn.setPrefHeight(20d);
		complexNumBtn.setOnAction((event) -> {
			ComplexNumGUI complexNumGUI = new ComplexNumGUI();
			try {
				complexNumGUI.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Button vectorsBtn = new Button("Vectores");
		vectorsBtn.setPrefWidth(280d);
		vectorsBtn.setPrefHeight(20d);
		vectorsBtn.setOnAction((event) -> {
			VectorsGUI vectorsGUI = new VectorsGUI();
			try {
				vectorsGUI.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Button matrixBtn = new Button("Matrices");
		matrixBtn.setPrefWidth(280d);
		matrixBtn.setPrefHeight(20d);
		matrixBtn.setOnAction((event) -> {
			MatrixGUI matrixGUI = new MatrixGUI();
			try {
				matrixGUI.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Button ltBtn = new Button("Transformaciones Lineales");
		ltBtn.setPrefWidth(280d);
		ltBtn.setPrefHeight(20d);
		ltBtn.setOnAction((event) -> {
			LinearTransformationGUI linearTransformGUI = new LinearTransformationGUI();
			try {
				linearTransformGUI.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		main.setSpacing(5d);
		main.setPadding(new Insets(10, 0, 0, 10));
		main.getChildren().addAll(complexNumBtn, vectorsBtn, matrixBtn, ltBtn);

		Scene scene = new Scene(main, 300, 150);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle("LineAlgebra GUI");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
