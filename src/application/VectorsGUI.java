package application;

import java.util.LinkedList;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import calculations.Vector;
import calculations.VectorControl;
import calculations.NullElementException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VectorsGUI extends Application {
	public ResourceBundle rb;

	public VectorsGUI(ResourceBundle rb) {
		this.rb = rb;
	}

	public static LinkedList<Vector> history = new LinkedList<>();
	public static LinkedList<Vector> answerVector = new LinkedList<>();
	static int VectorID = 0;

	@Override
	public void start(Stage stage) throws Exception {

		// Generar menu
		// Menu
		MenuBar menu = new MenuBar();

		// Menu de cargar
		Menu menuLoad = new Menu("Cargar");
		MenuItem addVector = new MenuItem("Nuevo vector");
		MenuItem randomVector = new MenuItem("Vector aleatorio");
		menuLoad.getItems().addAll(addVector, randomVector);

		// Menu de operaciones bÃ¡sicas
		Menu menuBasicOps = new Menu("Operaciones bÃ¡sicas");
		MenuItem suma = new MenuItem("Suma de vectores");
		MenuItem resta = new MenuItem("Resta de vectores");
		MenuItem multEsc = new MenuItem("MultiplicaciÃ³n por escalar");
		MenuItem multiplyElements = new MenuItem("MultiplicaciÃ³n de vectores");
		menuBasicOps.getItems().addAll(suma, resta, multEsc, multiplyElements);

		// MenÃº de operaciones especiales
		Menu menuSpecOps = new Menu("Operaciones especiales");
		MenuItem magnitud = new MenuItem("Magnitud de un vector");
		MenuItem toUnitary = new MenuItem("Convertir vector a unitario");
		MenuItem prodPunto = new MenuItem("Producto punto de vectores");
		MenuItem prodCruz = new MenuItem("Producto cruz de vectores");
		MenuItem angulo = new MenuItem("Ã�ngulo entre vectores");
		MenuItem proyeccion = new MenuItem("ProyecciÃ³n de vectores");
		MenuItem gramSch = new MenuItem("MÃ©todo Gram-Schmidt");
		MenuItem areaPar = new MenuItem("Ã�rea de paralelogramo");
		menuSpecOps.getItems().addAll(magnitud, toUnitary, prodPunto, prodCruz, angulo, proyeccion, gramSch, areaPar);

		// Menu de regresar al menu principal
		Menu change = new Menu("Cambiar PestaÃ±a");
		MenuItem backButton = new MenuItem("MenÃº Principal");
		change.getItems().addAll(backButton);

		// AÃ±adir todo al menu
		menu.getMenus().addAll(menuLoad, menuBasicOps, menuSpecOps, change);

		// CreaciÃ³n de la ventana raÃ­z y resultados
		VBox root = new VBox();
		FlowPane mainResults = new FlowPane();

		// Espacio para el historial de vectores
		VBox mainTable = new VBox();
		mainTable.setStyle("-fx-min-height:500px; -fx-min-width:100px; -fx-padding:5px; -fx-padding-top:10px;");
		Label historyL = new Label("Vectores utilizables:");
		TextArea vectorHistory = new TextArea();
		vectorHistory.setStyle("-fx-min-height:400px");
		HBox clear = new HBox();
		Button reset = new Button("Limpiar");
		clear.setSpacing(5);
		clear.setPadding(new Insets(10, 0, 0, 10));
		clear.getChildren().addAll(reset);
		mainTable.setPadding(new Insets(10, 0, 0, 10));
		mainTable.setSpacing(5);
		mainTable.getChildren().addAll(historyL, vectorHistory, clear);

		// Espacio para los resultados
		VBox results = new VBox();
		results.setStyle("-fx-min-height:500px; -fx-min-width:100px; -fx-padding:5px;");
		Label resl1 = new Label("Resultado:");
		TextArea resTxtArea = new TextArea();
		results.getChildren().addAll(resl1, resTxtArea);
		results.setSpacing(5);

		// AÃ±adir historial y resultados a la pantalla
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Limpiar resultados e historial

		reset.setOnAction((event) -> {
			try {
				prepareAnswerField(resTxtArea);
				cleanHistoryField(vectorHistory);
			} catch (NumberFormatException e) {
			}
		});

		// AÃ±adir un vector
		addVector.setOnAction((event) -> {
			if (!(VectorControl.addVector(history, vectorHistory))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en la inserciÃ³n");
				alert.setHeaderText("Datos incorrectos");
				alert.setContentText("Vuelva a intentar e ingrese nÃºmeros sin errores.");
				alert.showAndWait();
				return;
			}
			history.getLast().setID(VectorID++);
			VectorControl.updateHistory(history, vectorHistory);
		});

		// AÃ±adir un vector aleatorio
		randomVector.setOnAction((event) -> {
			if (!(VectorControl.random(history))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en la inserciÃ³n");
				alert.setHeaderText("Datos incorrectos");
				alert.setContentText("Vuelva a intentar e ingrese nÃºmeros sin errores.");
				alert.showAndWait();
				return;
			}
			history.getLast().setID(VectorID++);
			VectorControl.updateHistory(history, vectorHistory);
		});

		// Operaciones bÃ¡sicas
		// Suma
		suma.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A por sumar"));
				int j = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector B por sumar"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerVector.add(VectorControl.sumaVector(A, B));
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerVector.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Resta
		resta.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A por restar"));
				int j = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector B por restar"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerVector.add(VectorControl.restaVector(A, B));
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerVector.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// MultiplicaciÃ³n por escalar

		multEsc.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A por restar"));
				double j = Double.parseDouble(JOptionPane
						.showInputDialog("Ingrese el nÃºmero escalar por el cual serÃ¡ multiplicado el vector A"));
				Vector A = history.get(i);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.escalar(A, j));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerVector.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// MultiplicaciÃ³n de vectores

		multiplyElements.setOnAction((event) -> {
			try {
				int i = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A por multiplicar"));
				int j = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector B por multiplicar"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.multiplicacionVector(A, B));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerVector.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Operaciones especiales
		// Magnitud de un vector

		magnitud.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A para obtener su magnitud"));
				Vector A = history.get(i);
				prepareAnswerField(resTxtArea);
				double x = 0;
				try {
					x = VectorControl.magnitud(A);
					resTxtArea.setText(String.format("%.2f", x));
				} catch (NullElementException e) {
				}
				if (x == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos un vector para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}

			} catch (NumberFormatException e) {
			}
		});

		// Convertir vector a unitario

		toUnitary.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A a convertir a unitario"));
				Vector A = history.get(i);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.toUnitario(A));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos un vector para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerVector.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Producto punto

		prodPunto.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A para obtener su producto punto"));
				int j = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector B para obtener su producto punto"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				double x = 0;
				try {
					x = VectorControl.productoPunto(A, B);
					resTxtArea.setText(String.format("%.2f", x));
				} catch (NullElementException e) {
				}
				if (x == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}

			} catch (NumberFormatException e) {
			}
		});

		// Producto cruz

		prodCruz.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A para obtener su producto cruz"));
				int j = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector B para obtener su producto cruz"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.productoCruz(A, B));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerVector.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Ã�ngulo entre vectores

		angulo.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A para obtener el Ã¡ngulo"));
				int j = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector B para obtener el Ã¡ngulo"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				double x = 0;
				try {
					x = VectorControl.anguloEntreVectores(A, B);
					resTxtArea.setText(String.format("%.2f", x));
				} catch (NullElementException e) {
				}
				if (x == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}

			} catch (NumberFormatException e) {
			}
		});

		// ProyecciÃ³n

		proyeccion.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A para obtener la proyecciÃ³n"));
				int j = Integer.parseInt(
						JOptionPane.showInputDialog("Ingrese el nÃºmero del vector B para obtener la proyecciÃ³n"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.proyeccion(A, B));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(answerVector.getLast().toString());
			} catch (NumberFormatException e) {
			}
		});

		// Gram-Schmidt

		gramSch.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane
						.showInputDialog("Ingrese el nÃºmero del vector A para obtener el mÃ©todo Gram-Schmidt"));
				int j = Integer.parseInt(JOptionPane
						.showInputDialog("Ingrese el nÃºmero del vector B para obtener el mÃ©todo Gram-Schmidt"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				try {
					answerVector = VectorControl.listGramSchmidtproccess(A, B);
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos dos vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}
				String s = "U: " + answerVector.getFirst().toString() + ", V: " + answerVector.getLast().toString();
				resTxtArea.setText(s);
			} catch (NumberFormatException e) {
			}
		});

		// Ã�rea de paralelogramo formado por 3 vectores

		areaPar.setOnAction((event) -> {
			try {
				int i = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector A para obtener el Ã¡rea"));
				int j = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector B para obtener el Ã¡rea"));
				int k = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el nÃºmero del vector C para obtener el Ã¡rea"));
				Vector A = history.get(i);
				Vector B = history.get(j);
				Vector C = history.get(k);
				prepareAnswerField(resTxtArea);
				double x = 0;
				try {
					x = VectorControl.areaParalelogramo(A, B, C);
					resTxtArea.setText(String.format("%.2f", x));
				} catch (NullElementException e) {
				}
				if (x == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en el cÃ¡lculo");
					alert.setHeaderText("ViolaciÃ³n de propiedades:");
					alert.setContentText("Debe de haber al menos tres vectores para efectuar la operaciÃ³n.");
					alert.showAndWait();
					return;
				}

			} catch (NumberFormatException e) {
			}
		});

		// Settings del stage
		Scene scene = new Scene(root, 1000, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setHeight(800);
		stage.setWidth(515);
		stage.setResizable(false);
		stage.setTitle("Operaciones vectoriales");
		stage.show();
		stage.centerOnScreen();

	}

	// Reset para cada resultado
	public static void prepareAnswerField(TextArea resTxtArea) {
		answerVector.clear();
		resTxtArea.setText("");
	}

	public static void cleanHistoryField(TextArea resTxtArea) {
		history.clear();
		resTxtArea.setText("");
		VectorID = 0;
	}

}
