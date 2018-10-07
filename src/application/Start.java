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
		Button complexNum = new Button("Números complejos");
		complexNum.setPrefWidth(280d);
		complexNum.setPrefHeight(20d);
		complexNum.setOnAction((event) -> {
			ComplexNumGUI complexnumgui = new ComplexNumGUI();
			try {
				complexnumgui.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		Button vectors = new Button("Vectores");
		vectors.setPrefWidth(280d);
		vectors.setPrefHeight(20d);
		vectors.setOnAction((event) -> {
			VectorsGUI vectorsGui = new VectorsGUI();
			try {
				vectorsGui.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		Button matrix = new Button("Matrices");
		matrix.setPrefWidth(280d);
		matrix.setPrefHeight(20d);
		matrix.setOnAction((event) -> {
			MatrixGUI matrixgui = new MatrixGUI();
			matrixgui.start(stage);
		});
		
		//Create the Linear transformation button
		Button ltBtn = new Button("Transformación Lineal");
		//Set their dimmens
		ltBtn.setPrefWidth(280d);
		ltBtn.setPrefHeight(20d);
		//Set the onClick
		ltBtn.setOnAction((event)-> {
			LinearTransformationGUI linearTransform = new LinearTransformationGUI();
			try {
				linearTransform.start(stage);
			} catch (Exception e) {
				// TODO Complete this block of code
				e.printStackTrace();
			}
		});

		main.setSpacing(5d);
		main.setPadding(new Insets(10, 0, 0, 10));
		main.getChildren().addAll(complexNum, vectors, matrix, ltBtn);

		Scene scene = new Scene(main, 300, 500);
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
