package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LinearTransformationGUI extends Application {
	private static final double TEXT_AREA_HEIGHT = 120.0, TEXT_AREA_WIDTH = 100.0;
	private static final double BUTTON_WIDTH = 70.0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Root layout
		VBox root = new VBox();

		/*
		 * Menu bar of the view
		 */
		MenuBar menuBar = new MenuBar();

		// Menus of the bar
		// The history of the most recent lt (linear transformation) used
		Menu history = new Menu("History");
		// The saved lt will be displayed here
		Menu favorites = new Menu("Favorites");
		// Generate the lt for some vector rotations
		Menu rotations = new Menu("Rotation");
		// Change style preference an help
		Menu preferences = new Menu("Preferences");

		// Items of the menu
		// If the menu have no items, this items must be displayed in their own section:
		MenuItem historyEmpty = new MenuItem("(empty)");
		MenuItem favoritesEmpty = new MenuItem("(empty)");

		// History items
		// TODO it should be an item for each lt in the history
		// Add the items to the section
		history.getItems().add(historyEmpty);

		// Favorites items
		// TODO it should be an item for each lt in favorites
		// Add the items to the section
		favorites.getItems().add(favoritesEmpty);

		// Rotation items
		// The degrees of the rotations are according with the clock
		MenuItem rotate90  = new MenuItem("rotate 90\" to the right");
		MenuItem rotate180 = new MenuItem("rotate 180\"");
		MenuItem rotate270 = new MenuItem("rotate 90\" to the left");
		// Add the items to rotation section
		rotations.getItems().addAll(rotate90, rotate180, rotate270);

		// Preferences items
		// Go home gives the option to go back to home
		MenuItem goHome = new MenuItem("Go home");
		goHome.setOnAction((value) -> {
			Start goBack = new Start();
			try {
				goBack.start(primaryStage);
			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		// Help displays how to use this section
		MenuItem help = new MenuItem("Help");
		// Add items to the help section
		preferences.getItems().addAll(goHome, help);

		// Add menus to the bar
		menuBar.getMenus().addAll(history, favorites, rotations, preferences);
		root.getChildren().add(menuBar);

		
		/*
		 * Calculator part
		 */
		GridPane gridPane = new GridPane();
		
		//In this part you can modify or create the lt
		Label transformationLbl = new Label("Transformation");
		GridPane.setConstraints(transformationLbl, 0, 0);

		//The purpose of this text area is to introduce the equations of the lt per line
		TextArea equationTA = new TextArea();
		equationTA.setPrefHeight(TEXT_AREA_HEIGHT);
		equationTA.setPrefWidth(TEXT_AREA_WIDTH);
		GridPane.setConstraints(equationTA, 0, 1);
		
		gridPane.getChildren().addAll(transformationLbl, equationTA);
		
		//In this one, you can modify the matrix of the lt
		Label matrixLbl = new Label("Matrix");
		GridPane.setConstraints(matrixLbl, 1, 0);
		
		TextArea matrixTA = new TextArea();
		matrixTA.setPrefHeight(TEXT_AREA_HEIGHT);
		matrixTA.setPrefWidth(TEXT_AREA_WIDTH);
		GridPane.setConstraints(matrixTA, 1, 1);
		
		gridPane.getChildren().addAll(matrixLbl, matrixTA);
		
		//And the buttons to save or modify the lt
		VBox ltButtons 	 = new VBox();
		
		Button createBtn = new Button("Create");
		createBtn.setPrefWidth(BUTTON_WIDTH);
		
		Button modifyBtn = new Button("Modify");
		modifyBtn.setPrefWidth(BUTTON_WIDTH);
		
		Button saveBtn 	 = new Button("Save");
		saveBtn.setPrefWidth(BUTTON_WIDTH);
		
		ltButtons.getChildren().addAll(createBtn, modifyBtn, saveBtn);
		GridPane.setConstraints(ltButtons, 2, 1);
		gridPane.getChildren().add(ltButtons);
		
		//Here is the part where the user will apply the lt to a vector
		Label vectorLbl = new Label("Vector");
		GridPane.setConstraints(vectorLbl, 0, 2);
		
		
		TextArea vectorTA = new TextArea();
		vectorTA.setPrefHeight(TEXT_AREA_HEIGHT);
		vectorTA.setPrefWidth(TEXT_AREA_WIDTH);
		
		GridPane.setConstraints(vectorTA, 0, 3);
		gridPane.getChildren().addAll(vectorLbl, vectorTA);
		
		//Button to generate the converted vector
		Button convertBtn = new Button("Convert");
		convertBtn.setPrefWidth(BUTTON_WIDTH);
		
		GridPane.setConstraints(convertBtn, 1, 3);
		gridPane.getChildren().add(convertBtn);
		
		//The generated vector
		Label resultLbl = new Label("Result");
	
		GridPane.setConstraints(resultLbl, 2, 2);
		
		TextArea resultTA = new TextArea();
		resultTA.setPrefHeight(TEXT_AREA_HEIGHT);
		resultTA.setPrefWidth(TEXT_AREA_WIDTH);
		resultTA.setDisable(true);
		GridPane.setConstraints(resultTA, 2, 3);
		gridPane.getChildren().addAll(resultLbl, resultTA);
		
		gridPane.setHgap(15.0);
		gridPane.setVgap(10.0);
		gridPane.setPadding(new Insets(20));
		
		//Add the gridPane to the root
		root.getChildren().add(gridPane);

		// Add all to the stage
		Scene scene = new Scene(root, 400, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
