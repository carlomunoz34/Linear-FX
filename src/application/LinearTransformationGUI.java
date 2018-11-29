package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import calculations.IllegalDimensionException;
import calculations.LinearTransformation;
import calculations.LinearTransformationControl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LinearTransformationGUI extends Application {
	public ResourceBundle rb;
	public LinearTransformationGUI(ResourceBundle rb){
		this.rb=rb;
	}
	private static final double TEXT_AREA_HEIGHT = 120.0, TEXT_AREA_WIDTH = 100.0;
	private static final double BUTTON_WIDTH = 70.0;
	private LinearTransformationControl control;

	@Override
	public void start(Stage primaryStage) throws Exception {
		control = new LinearTransformationControl();

		// Root layout
		VBox root = new VBox();

		/*
		 * Menu bar of the view
		 */
		MenuBar menuBar = new MenuBar();

		// Menus of the bar
		// The saved lt will be displayed here
		Menu favorites = new Menu("Favoritos");
		// Change window
		Menu changeWindow = new Menu("Cambiar Pestaña");
		// Help displays how to use this section
		Menu help = new Menu("Ayuda");

		// Items of the menu

		//Delete favorites
		MenuItem deleteFavorites = new MenuItem("Borrar favoritos");
		deleteFavorites.setOnAction(event -> {
			control.deleteFavorites();
			favorites.getItems().clear();
			
			MenuItem favoritesEmpty = new MenuItem("(vacio)");
			favorites.getItems().add(favoritesEmpty);
		});
		favorites.getItems().add(deleteFavorites);
		
		// Preferences items
		// Go home gives the option to go back to home
		MenuItem goHome = new MenuItem("Menu Principal");
		goHome.setOnAction((value) -> {
			Start goBack = new Start();
			try {
				goBack.start(primaryStage);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, "Ha ocurrido un error", ButtonType.OK);
				alert.show();
				e.printStackTrace();
			}
		});
		// Add items to the change window section
		changeWindow.getItems().addAll(goHome);

		// Add items to the help section

		MenuItem aboutLT = new MenuItem("Acerca de las TL");
		aboutLT.setOnAction(event -> {
			try {
				Desktop.getDesktop().browse(new URI("https://es.wikipedia.org/wiki/Aplicaci%C3%B3n_lineal"));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		});
		help.getItems().addAll(aboutLT);

		// Add menus to the bar
		menuBar.getMenus().addAll(favorites, changeWindow, help);
		root.getChildren().add(menuBar);

		/*
		 * Calculator part
		 */
		GridPane gridPane = new GridPane();

		//In this part you can modify or create the lt
		Label transformationLbl = new Label("Transformación");
		GridPane.setConstraints(transformationLbl, 0, 0);

		// The purpose of this text area is to show the equations of the lt per line
		TextArea equationTA = new TextArea();
		equationTA.setPrefHeight(TEXT_AREA_HEIGHT);
		equationTA.setPrefWidth(TEXT_AREA_WIDTH);
		GridPane.setConstraints(equationTA, 0, 1);

		gridPane.getChildren().addAll(transformationLbl, equationTA);

		// In this one, you can modify the matrix of the lt
		Label matrixLbl = new Label("Matriz");
		GridPane.setConstraints(matrixLbl, 1, 0);

		TextArea matrixTA = new TextArea();
		matrixTA.setPrefHeight(TEXT_AREA_HEIGHT);
		matrixTA.setPrefWidth(TEXT_AREA_WIDTH);
		GridPane.setConstraints(matrixTA, 1, 1);

		gridPane.getChildren().addAll(matrixLbl, matrixTA);

		// And the buttons to save or modify the lt
		VBox ltButtons = new VBox();

		Button createBtn = new Button("Crear");
		createBtn.setPrefWidth(BUTTON_WIDTH);

		Button modifyBtn = new Button("Modificar");
		modifyBtn.setPrefWidth(BUTTON_WIDTH);

		Button saveBtn = new Button("Guardar");
		saveBtn.setPrefWidth(BUTTON_WIDTH);

		Button clearBtn = new Button("Limpiar");
		clearBtn.setPrefWidth(BUTTON_WIDTH);

		ltButtons.getChildren().addAll(createBtn, modifyBtn, saveBtn, clearBtn);
		GridPane.setConstraints(ltButtons, 2, 1);
		gridPane.getChildren().add(ltButtons);

		// Here is the part where the user will apply the lt to a vector
		Label vectorLbl = new Label("Vector");
		GridPane.setConstraints(vectorLbl, 0, 2);

		TextArea vectorTA = new TextArea();
		vectorTA.setPrefHeight(TEXT_AREA_HEIGHT);
		vectorTA.setPrefWidth(TEXT_AREA_WIDTH);

		GridPane.setConstraints(vectorTA, 0, 3);
		gridPane.getChildren().addAll(vectorLbl, vectorTA);

		// Button to generate the converted vector
		Button convertBtn = new Button("Convertir");
		convertBtn.setPrefWidth(BUTTON_WIDTH);

		GridPane.setConstraints(convertBtn, 1, 3);
		gridPane.getChildren().add(convertBtn);

		// The generated vector
		Label resultLbl = new Label("Resultado:");

		GridPane.setConstraints(resultLbl, 2, 2);

		TextArea resultTA = new TextArea();
		resultTA.setPrefHeight(TEXT_AREA_HEIGHT);
		resultTA.setPrefWidth(TEXT_AREA_WIDTH);
		resultTA.setDisable(true);
		GridPane.setConstraints(resultTA, 2, 3);
		gridPane.getChildren().addAll(resultLbl, resultTA);

		// Add favorites items
		// Favorites items
		if (control.loadFavorites()) {
			for (LinearTransformation lt : control.getFavorites()) {
				MenuItem current = new MenuItem(lt.getName());
				current.setOnAction(event -> {
					equationTA.setText(lt.getEquation());
					matrixTA.setText(lt.getMatrix().toString());
					control.setCurrentLT(lt);
				});
				favorites.getItems().add(current);
			}
		} else {
			MenuItem favoritesEmpty = new MenuItem("(vacio)");
			favorites.getItems().clear();
			favorites.getItems().add(favoritesEmpty);
		}

		createBtn.setOnAction(event -> {
			if (matrixTA.getText() != "") {
				equationTA.setText(control.update(matrixTA.getText()));
			}
		});

		modifyBtn.setOnAction(event -> equationTA.setText(control.setCurrentLT(matrixTA.getText())));

		convertBtn.setOnAction(event -> {
			try {
				resultTA.setText(control.applyTL(vectorTA.getText()));
			} catch (IllegalDimensionException e) {
				Alert alert = new Alert(AlertType.WARNING, "Dimensiones del vector o matriz no validas", ButtonType.OK);
				alert.show();
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				Alert alert = new Alert(AlertType.ERROR, "Transformaci�n lineal no creada", ButtonType.OK);
				alert.show();
				e.printStackTrace();
			}
		});

		saveBtn.setOnAction(event -> {
			try {
				if (control.getFavorites().size() == 0) {
					favorites.getItems().clear();
					
					MenuItem delete = new MenuItem("Borrar favoritos");
					deleteFavorites.setOnAction(e -> {
						control.deleteFavorites();
						favorites.getItems().clear();
						
						MenuItem favoritesEmpty = new MenuItem("(vacio)");
						favorites.getItems().add(favoritesEmpty);
					});
					favorites.getItems().add(deleteFavorites);
				}

				String name = JOptionPane.showInputDialog("Dame el nombre de la transformaci�n");
				control.getCurrentLT().setName(name);

				try {
					control.addFavorites();
				} catch (IOException e1) {
					Alert alert = new Alert(AlertType.ERROR, "Ha ocurrido un error", ButtonType.OK);
					alert.show();
					e1.printStackTrace();
				}

				MenuItem current = new MenuItem(control.getCurrentLT().getName());
				current.setOnAction(e -> {
					String currentName = current.getText();
					for (LinearTransformation lt : control.getFavorites()) {
						if (lt.getName().equals(currentName)) {
							equationTA.setText(lt.getEquation());
							matrixTA.setText(lt.getMatrix().toString());
							control.setCurrentLT(lt);
						}
					}
				});

				favorites.getItems().add(current);

			} catch (IllegalArgumentException e) {
				Alert alert = new Alert(AlertType.ERROR, "Ha ocurrido un problema", ButtonType.OK);
				alert.show();
				e.printStackTrace();
			}
		});

		clearBtn.setOnAction(event -> {
			equationTA.setText("");
			matrixTA.setText("");
			vectorTA.setText("");
			resultTA.setText("");
			control.reset();
		});

		gridPane.setHgap(15.0);
		gridPane.setVgap(10.0);
		gridPane.setPadding(new Insets(20));

		// Add the gridPane to the root
		root.getChildren().add(gridPane);

		// Add all to the stage
		Scene scene = new Scene(root, 400, 500);
		primaryStage.setScene(scene);
		primaryStage.setHeight(440);
		primaryStage.setWidth(420);
		primaryStage.show();
	}
}
