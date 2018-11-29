package application;

import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import calculations.Vector;
import calculations.VectorControl;
import calculations.NullElementException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VectorsGUI extends Application {
	public ResourceBundle rb;
	public VectorsGUI(ResourceBundle rb){
		this.rb=rb;
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
		Menu menuLoad = new Menu(rb.getString("v_load"));//cargar
		MenuItem addVector = new MenuItem(rb.getString("v_addv"));//nuevo vector
		MenuItem randomVector = new MenuItem(rb.getString("v_rndmv"));//vector aleatorio
		menuLoad.getItems().addAll(addVector, randomVector);

		// Menu de operaciones bÃ¡sicas
		Menu menuBasicOps = new Menu(rb.getString("v_basic"));//operaciones basicas
		MenuItem suma = new MenuItem(rb.getString("v_sum"));//suma de vectores
		MenuItem resta = new MenuItem(rb.getString("v_subs"));//resta de vectores
		MenuItem multEsc = new MenuItem(rb.getString("v_multe"));//multiplicacion por escalar
		MenuItem multiplyElements = new MenuItem(rb.getString("v_mult"));//multiplicacion de vectores
		menuBasicOps.getItems().addAll(suma, resta, multEsc, multiplyElements);

		// MenÃº de operaciones especiales
		Menu menuSpecOps = new Menu(rb.getString("v_specops"));//operaciones especiales
		MenuItem magnitud = new MenuItem(rb.getString("v_mag"));//magnitud de un vector
		MenuItem toUnitary = new MenuItem(rb.getString("v_unit"));//convertir vector a unitario
		MenuItem prodPunto = new MenuItem(rb.getString("v_pp"));//producto punto de vectores
		MenuItem prodCruz = new MenuItem(rb.getString("v_cp"));//producto cruz de vectores
		MenuItem angulo = new MenuItem(rb.getString("v_angle"));//angulo entre vectores
		MenuItem proyeccion = new MenuItem(rb.getString("v_proj"));//proyeccion de vectores
		MenuItem gramSch = new MenuItem(rb.getString("v_gram"));//metodo gram-schmidt
		MenuItem areaPar = new MenuItem(rb.getString("v_area"));//area de paralelogramo
		menuSpecOps.getItems().addAll(magnitud, toUnitary, prodPunto, prodCruz, angulo, proyeccion, gramSch, areaPar);

		// Menu de regresar al menu principal
		Menu change = new Menu(rb.getString("v_change"));//cambiar pestaña
		MenuItem backButton = new MenuItem(rb.getString("v_back"));//menu principal
		MenuItem infoButton = new MenuItem(rb.getString("v_info"));//informacion de operaciones
		change.getItems().addAll(backButton, infoButton);

		// AÃ±adir todo al menu
		menu.getMenus().addAll(menuLoad, menuBasicOps, menuSpecOps, change);

		// CreaciÃ³n de la ventana raÃ­z y resultados
		VBox root = new VBox();
		FlowPane mainResults = new FlowPane();

		// Espacio para el historial de vectores
		VBox mainTable = new VBox();
		mainTable.setStyle("-fx-min-height:10px; -fx-min-width:10px; -fx-padding:5px; -fx-padding-top:10px;");
		Label historyL = new Label(rb.getString("v_usablev"));//vectores utilizables
		TextArea vectorHistory = new TextArea();
		vectorHistory.setStyle("-fx-min-height:200px");
		HBox clear = new HBox();
		Button reset = new Button(rb.getString("v_clean"));//limpiar
		clear.setSpacing(5);
		clear.setPadding(new Insets(10, 0, 0, 10));
		clear.getChildren().addAll(reset);
		mainTable.setPadding(new Insets(10, 0, 0, 10));
		mainTable.setSpacing(5);
		mainTable.getChildren().addAll(historyL, vectorHistory, clear);

		// Espacio para los resultados
		VBox results = new VBox();
		results.setStyle("-fx-min-height:100px; -fx-min-width:100px; -fx-padding:5px;");
		Label resl1 = new Label(rb.getString("v_result"));//resultado
		TextArea resTxtArea = new TextArea();
		results.getChildren().addAll(resl1, resTxtArea);
		results.setSpacing(5);

		// AÃ±adir historial y resultados a la pantalla
		mainResults.setPadding(new Insets(10, 0, 0, 10));
		mainResults.getChildren().addAll(mainTable, results);
//		root.setSpacing(5d);
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
		
		// Mostrar información
				infoButton.setOnAction((event) -> {

					Image infoImg = new Image("\\application\\vectorsInfo.png");
					ImageView imgv = new ImageView();
					MenuBar menuInfo = new MenuBar();
					Menu opcMenu = new Menu(rb.getString("v_opt"));//opciones
					MenuItem backBtn = new MenuItem(rb.getString("v_prev"));//regresar
					opcMenu.getItems().add(backBtn);
					menuInfo.getMenus().addAll(opcMenu);
					imgv.setImage(infoImg);
					VBox vBox = new VBox();
					vBox.getChildren().addAll(menuInfo, imgv);
					backBtn.setOnAction((event2) -> {
						VectorsGUI main = new VectorsGUI(this.rb);
						try {
							main.start(stage);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					Group newRoot = new Group();
					Scene scene = new Scene(newRoot);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					newRoot.getChildren().addAll(vBox);
					stage.setResizable(true);
					stage.centerOnScreen();
					stage.setHeight(infoImg.getHeight());
					stage.setWidth(infoImg.getWidth() + 20);
					stage.setScene(scene);
					stage.show();
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
				alert.setTitle(rb.getString("v_inserr"));//error en la insercion
				alert.setHeaderText(rb.getString("v_dataerr"));//datos incorrectos
				alert.setContentText(rb.getString("v_try"));//vuelva a intentar e ingrese numeros sin errores
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
				alert.setTitle(rb.getString("v_inserr"));
				alert.setHeaderText(rb.getString("v_dataerr"));
				alert.setContentText(rb.getString("v_try"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("v_sum_A")));//Ingrese el nÃºmero del vector A por sumar
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("v_sum_B")));//Ingrese el nÃºmero del vector B por sumar
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerVector.add(VectorControl.sumaVector(A, B));
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("v_cerr"));//error en el calculo
					alert.setHeaderText(rb.getString("v_vio"));//violacion de propiedades
					alert.setContentText(rb.getString("v_2vecerr"));//Debe de haber al menos dos vectores para efectuar la operaciÃ³n.
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("v_sub_A")));//Ingrese el nÃºmero del vector A por restar
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("v_sub_B")));//Ingrese el nÃºmero del vector B por restar
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerVector.add(VectorControl.restaVector(A, B));
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_2vecerr"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("v_multe_A")));//Ingrese el nÃºmero del vector A por restar
				double j = Double.parseDouble(JOptionPane
						.showInputDialog(rb.getString("v_multe_E")));//Ingrese el nÃºmero escalar por el cual serÃ¡ multiplicado el vector A
				Vector A = history.get(i);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.escalar(A, j));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_1vecerr"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("v_mult_A")));//Ingrese el nÃºmero del vector A por multiplicar
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("v_mult_B")));//Ingrese el nÃºmero del vector B por multiplicar
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.multiplicacionVector(A, B));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_2vecerr"));
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
						JOptionPane.showInputDialog(rb.getString("v_mag_A")));//Ingrese el nÃºmero del vector A para obtener su magnitud
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
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_1vecerr"));//Debe de haber al menos un vector para efectuar la operaciÃ³n.
					alert.showAndWait();
					return;
				}

			} catch (NumberFormatException e) {
			}
		});

		// Convertir vector a unitario

		toUnitary.setOnAction((event) -> {
			try {
				int i = Integer
						.parseInt(JOptionPane.showInputDialog(rb.getString("v_unit_A")));//Ingrese el nÃºmero del vector A a convertir a unitario
				Vector A = history.get(i);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.toUnitario(A));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_1vecerr"));
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
						JOptionPane.showInputDialog(rb.getString("v_pp_A")));//Ingrese el nÃºmero del vector A para obtener su producto punto
				int j = Integer.parseInt(
						JOptionPane.showInputDialog(rb.getString("v_pp_B")));//Ingrese el nÃºmero del vector B para obtener su producto punto
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
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_2vecerr"));
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
						JOptionPane.showInputDialog(rb.getString("v_cp_A")));//Ingrese el nÃºmero del vector A para obtener su producto cruz
				int j = Integer.parseInt(
						JOptionPane.showInputDialog(rb.getString("v_cp_B")));//Ingrese el nÃºmero del vector B para obtener su producto cruz
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.productoCruz(A, B));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_2vecerr"));
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
				int i = Integer
						.parseInt(JOptionPane.showInputDialog(rb.getString("v_angle_A")));//Ingrese el nÃºmero del vector A para obtener el Ã¡ngulo
				int j = Integer
						.parseInt(JOptionPane.showInputDialog(rb.getString("v_angle_B")));//Ingrese el nÃºmero del vector B para obtener el Ã¡ngulo
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
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_2vecerr"));
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
						JOptionPane.showInputDialog(rb.getString("v_proj_A")));//Ingrese el nÃºmero del vector A para obtener la proyecciÃ³n
				int j = Integer.parseInt(
						JOptionPane.showInputDialog(rb.getString("v_proj_B")));//Ingrese el nÃºmero del vector B para obtener la proyecciÃ³n
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				try {
					answerVector.add(VectorControl.proyeccion(A, B));
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_2vecerr"));
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
						.showInputDialog(rb.getString("v_gram_A")));//Ingrese el nÃºmero del vector A para obtener el mÃ©todo Gram-Schmidt
				int j = Integer.parseInt(JOptionPane
						.showInputDialog(rb.getString("v_gram_B")));//Ingrese el nÃºmero del vector B para obtener el mÃ©todo Gram-Schmidt
				Vector A = history.get(i);
				Vector B = history.get(j);
				prepareAnswerField(resTxtArea);
				try {
					answerVector = VectorControl.listGramSchmidtproccess(A, B);
				} catch (NullElementException e) {
				}
				if (answerVector.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_2vecerr"));
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
						.parseInt(JOptionPane.showInputDialog(rb.getString("v_area_A")));//Ingrese el nÃºmero del vector A para obtener el Ã¡rea
				int j = Integer
						.parseInt(JOptionPane.showInputDialog(rb.getString("v_area_B")));//Ingrese el nÃºmero del vector B para obtener el Ã¡rea
				int k = Integer
						.parseInt(JOptionPane.showInputDialog(rb.getString("v_area_C")));//Ingrese el nÃºmero del vector C para obtener el Ã¡rea
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
					alert.setTitle(rb.getString("v_cerr"));
					alert.setHeaderText(rb.getString("v_vio"));
					alert.setContentText(rb.getString("v_3vecerr"));
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
		stage.setHeight(600);
		stage.setWidth(515);
		stage.setResizable(false);
		stage.setTitle(rb.getString("v_vecops"));//operaciones vectoriales
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
