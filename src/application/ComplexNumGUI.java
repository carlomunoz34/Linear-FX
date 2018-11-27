package application;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import calculations.ComplexNumber;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComplexNumGUI extends Application {

	public static LinkedList<ComplexNumber> history = new LinkedList<>();
	public static LinkedList<ComplexNumber> answerComplexNumber = new LinkedList<>();
	static int complexNumberID = 0;

	@Override
	public void start(Stage stage) throws Exception {
		// Generar menú
		// Menu
		MenuBar menu = new MenuBar();

		// Menu de cargar
		Menu menuLoad = new Menu("Cargar");
		MenuItem addComplexNumber = new MenuItem("Nuevo número complejo");
		MenuItem randomComplexNumber = new MenuItem("Número complejo aleatorio");
		menuLoad.getItems().addAll(addComplexNumber, randomComplexNumber);

		// Menu de operaciones básicas
		Menu menuBasicOps = new Menu("Operaciones básicas");
		MenuItem addElements = new MenuItem("Suma de números complejos");
		MenuItem subtractElement = new MenuItem("Resta de números complejos");
		MenuItem multiplyElements = new MenuItem("Multiplicación de números complejos");
		MenuItem divideElements = new MenuItem("División de números complejos");
		menuBasicOps.getItems().addAll(addElements, subtractElement, multiplyElements, divideElements);

		// Menú de operaciones especiales
		Menu menuSpecOps = new Menu("Operaciones especiales");
		MenuItem conjugateElement = new MenuItem("Conjugar número complejo");
		MenuItem magnitudeElement = new MenuItem("Magnitud de un número complejo");
		MenuItem squareElement = new MenuItem("Cuadrado de un número complejo");
		MenuItem expElement = new MenuItem("Exponencial de un número complejo");
		MenuItem powElement = new MenuItem("Número complejo elevado a X");
		MenuItem sinElement = new MenuItem("Seno de un número complejo");
		MenuItem cosElement = new MenuItem("Coseno de un número complejo");
		MenuItem tanElement = new MenuItem("Tangente de un número complejo");
		MenuItem cotElement = new MenuItem("Cotangente de un número complejo");
		MenuItem secElement = new MenuItem("Secante de un número complejo");
		MenuItem cosecElement = new MenuItem("Cosecante de un número complejo");
		MenuItem invElement = new MenuItem("Inverso de un número complejo");
		menuSpecOps.getItems().addAll(conjugateElement, magnitudeElement, squareElement, expElement, powElement,
				sinElement, cosElement, tanElement, cotElement, secElement, cosecElement, invElement);

		// Menú de regresar al menu principal
		Menu change = new Menu("Cambiar Pestaña");
		MenuItem backButton = new MenuItem("Menú Principal");
		MenuItem infoButton = new MenuItem("Información de números complejos");
		change.getItems().addAll(backButton, infoButton);

		// Añadir todo al menú
		menu.getMenus().addAll(menuLoad, menuBasicOps, menuSpecOps, change);

		// Creación de la ventana raíz y resultados
		VBox root = new VBox();
		FlowPane mainResults = new FlowPane();

		// Espacio para el historial de números complejos
		VBox mainTable = new VBox();
		mainTable.setStyle("-fx-min-height:300px; -fx-min-width:100px; -fx-padding:5px; -fx-padding-top:10px;");
		Label historyL = new Label("Números complejos utilizables:");
		TextArea complexNumberHistory = new TextArea();
		complexNumberHistory.setStyle("-fx-min-height:20px");
		HBox clear = new HBox();
		Button reset = new Button("Limpiar");
		clear.setSpacing(5);
		clear.setPadding(new Insets(10, 0, 0, 10));
		clear.getChildren().addAll(reset);
		mainTable.setPadding(new Insets(10, 0, 0, 10));
		mainTable.setSpacing(5);
		mainTable.getChildren().addAll(historyL, complexNumberHistory, clear);

		// Espacio para los resultados
		VBox results = new VBox();
		results.setStyle("-fx-min-height:50px; -fx-min-width:50px; -fx-padding:5px;");
		Label resl1 = new Label("Resultado:");
		TextArea resTxtArea = new TextArea();
		results.getChildren().addAll(resl1, resTxtArea);
		results.setSpacing(5);

		// Añadir historial y resultados a la pantalla
		mainResults.setPadding(new Insets(10, 0, 0, 10));
		mainResults.getChildren().addAll(mainTable, results);
		root.setSpacing(5d);
		root.getChildren().addAll(menu, mainResults);

		// Eventos
		// Regresar al menu principal		
		backButton.setOnAction((event) -> {
			Start main = new Start();
			try {
				main.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Mostrar información
		infoButton.setOnAction((event) -> {
			
			Image infoImg = new Image("\\application\\complexNumberInfo.png");
			ImageView imgv = new ImageView();
			MenuBar menuInfo = new MenuBar();
			Menu opcMenu = new Menu("Opciones");
			MenuItem backBtn = new MenuItem("Regresar");
			opcMenu.getItems().add(backBtn);
			menuInfo.getMenus().addAll(opcMenu);
			imgv.setImage(infoImg);
			VBox vBox = new VBox();
			vBox.getChildren().addAll(menuInfo, imgv);
			backBtn.setOnAction((event2) -> {
				ComplexNumGUI main = new ComplexNumGUI();
				try {
					main.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Group newRoot = new Group();
			Scene scene = new Scene(newRoot);
			newRoot.getChildren().addAll(vBox);
			stage.setResizable(true);
			stage.centerOnScreen();
			stage.setHeight(infoImg.getHeight());
			stage.setWidth(infoImg.getWidth());
			stage.setScene(scene);
			stage.show();			
		});

		// Limpiar resultados e historial

		reset.setOnAction((event) -> {
			try {
				prepareAnswerField(resTxtArea);
				cleanHistoryField(complexNumberHistory);
			} catch (NumberFormatException e) {
			}
		});

		// Añadir un número complejo
		addComplexNumber.setOnAction((event) -> {
			if (!(ComplexNumber.addComplexNumber(history, complexNumberHistory))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en la inserción");
				alert.setHeaderText("Datos incorrectos");
				alert.setContentText("Vuelva a intentar e ingrese números sin errores.");
				alert.showAndWait();
				return;
			}
			history.getLast().setID(complexNumberID++);
			ComplexNumber.updateHistory(history, complexNumberHistory);
		});

		// Añadir un número complejo aleatorio
		randomComplexNumber.setOnAction((event) -> {
			if (!(ComplexNumber.random(history))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en la inserción");
				alert.setHeaderText("Datos incorrectos");
				alert.setContentText("Vuelva a intentar e ingrese números sin errores.");
				alert.showAndWait();
				return;
			}
			history.getLast().setID(complexNumberID++);
			ComplexNumber.updateHistory(history, complexNumberHistory);
		});

		// Operaciones básicas
		// Suma
		addElements.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del número complejo A por sumar"));
				int j = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del número complejo B por sumar"));
				ComplexNumber A = history.get(i);
				ComplexNumber B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.add(A, B));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos dos números complejos para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Resta
		subtractElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del vector A por restar"));
				int j = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del vector B por restar"));
				ComplexNumber A = history.get(i);
				ComplexNumber B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.subtract(A, B));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos dos números complejos para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Multiplicación de números complejos
		multiplyElements.setOnAction((event) -> {
			try {
				int i = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el número del ID complejo A por multiplicar"));
				int j = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el número del ID complejo B por multiplicar"));
				ComplexNumber A = history.get(i);
				ComplexNumber B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.multiply(A, B));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos dos número complejos para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// División de números complejos
		divideElements.setOnAction((event) -> {
			try {
				int i = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID del número complejo A por dividir"));
				int j = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID del número complejo B por dividir"));
				ComplexNumber A = history.get(i);
				ComplexNumber B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.divide(A, B));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos dos número complejos para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Operaciones especiales
		// Conjugado
		conjugateElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el ID del número complejo para obtener su conjugado"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(A.conjugate());
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Magnitud
		magnitudeElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el ID del número complejo para obtener su magnitud"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(new ComplexNumber(A.mod(), 0));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Double.toString(answerComplexNumber.getLast().getRe()));
			} catch (NumberFormatException e) {
			}
		});

		// Cuadrado
		squareElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el ID del número complejo para obtener su cuadrado"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(A.square());
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Exponencial
		expElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane
						.showInputDialog("Ingrese el ID del número complejo para obtener el exponencial e^(a+bi)"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.exp(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Potencia
		powElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del número complejo"));
				int power = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la potencia a la cual elevar"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.pow(A, power));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Seno
		sinElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el ID del número complejo al cual calcular el seno"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.sin(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Coseno
		cosElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el ID del número complejo al cual calcular el coseno"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.cos(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Tangente
		tanElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el ID del número complejo al cual calcular la tangente"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.tan(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Cotangente
		cotElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane
						.showInputDialog("Ingrese el ID del número complejo al cual calcular la cotangente"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.cot(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Secante
		secElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el ID del número complejo al cual calcular la secante"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.sec(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Cosecante
		cosecElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el ID del número complejo al cual calcular la cosecante"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.cot(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Inverso
		invElement.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del número complejo al cual calcular su inverso"));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(A.inverse());
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cálculo");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Debe de haber al menos un número complejo para efectuar la operación.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerComplexNumber.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Settings del stage
		Scene scene = new Scene(root, 1000, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setHeight(600);
		stage.setWidth(515);
		stage.setTitle("Números Complejos");
		stage.show();
		stage.centerOnScreen();

	}

	// Reset para cada resultado
	public static void prepareAnswerField(TextArea resTxtArea) {
		answerComplexNumber.clear();
		resTxtArea.setText("");
	}

	public static void cleanHistoryField(TextArea resTxtArea) {
		history.clear();
		resTxtArea.setText("");
		complexNumberID = 0;
	}

}
