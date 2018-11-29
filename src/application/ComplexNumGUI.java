package application;

import java.util.LinkedList;
import java.util.ResourceBundle;
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
	public ResourceBundle rb;

	public ComplexNumGUI(ResourceBundle rb) {
		this.rb = rb;
	}

	public static LinkedList<ComplexNumber> history = new LinkedList<>();
	public static LinkedList<ComplexNumber> answerComplexNumber = new LinkedList<>();
	static int complexNumberID = 0;

	@Override
	public void start(Stage stage) throws Exception {
		// Generar menú
		// Menu
		MenuBar menu = new MenuBar();

		// Menu de cargar
		Menu menuLoad = new Menu(rb.getString("cn_load"));
		MenuItem addComplexNumber = new MenuItem(rb.getString("cn_add_new"));
		MenuItem randomComplexNumber = new MenuItem(rb.getString("cn_add_rand"));
		menuLoad.getItems().addAll(addComplexNumber, randomComplexNumber);

		// Menu de operaciones básicas
		Menu menuBasicOps = new Menu(rb.getString("cn_menu_basic_ops"));
		MenuItem addElements = new MenuItem(rb.getString("cn_add"));
		MenuItem subtractElement = new MenuItem(rb.getString("cn_sub"));
		MenuItem multiplyElements = new MenuItem(rb.getString("cn_mult"));
		MenuItem divideElements = new MenuItem(rb.getString("cn_div"));
		menuBasicOps.getItems().addAll(addElements, subtractElement, multiplyElements, divideElements);

		// Menú de operaciones especiales
		Menu menuSpecOps = new Menu(rb.getString("cn_menu_spec_ops"));
		MenuItem conjugateElement = new MenuItem(rb.getString("cn_conj"));
		MenuItem magnitudeElement = new MenuItem(rb.getString("cn_mag"));
		MenuItem squareElement = new MenuItem(rb.getString("cn_sqr"));
		MenuItem expElement = new MenuItem(rb.getString("cn_exp"));
		MenuItem powElement = new MenuItem(rb.getString("cn_pow"));
		MenuItem sinElement = new MenuItem(rb.getString("cn_sin"));
		MenuItem cosElement = new MenuItem(rb.getString("cn_cos"));
		MenuItem tanElement = new MenuItem(rb.getString("cn_tan"));
		MenuItem cotElement = new MenuItem(rb.getString("cn_cot"));
		MenuItem secElement = new MenuItem(rb.getString("cn_sec"));
		MenuItem cosecElement = new MenuItem(rb.getString("cn_cosec"));
		MenuItem invElement = new MenuItem(rb.getString("cn_inv"));
		menuSpecOps.getItems().addAll(conjugateElement, magnitudeElement, squareElement, expElement, powElement,
				sinElement, cosElement, tanElement, cotElement, secElement, cosecElement, invElement);

		// Menú de regresar al menu principal
		Menu change = new Menu(rb.getString("cn_change_tab"));
		MenuItem backButton = new MenuItem(rb.getString("cn_back"));
		MenuItem infoButton = new MenuItem(rb.getString("cn_info"));
		change.getItems().addAll(backButton, infoButton);

		// Añadir todo al menú
		menu.getMenus().addAll(menuLoad, menuBasicOps, menuSpecOps, change);

		// Creación de la ventana raÃ­z y resultados
		VBox root = new VBox();
		FlowPane mainResults = new FlowPane();

		// Espacio para el historial de números complejos
		VBox mainTable = new VBox();
		mainTable.setStyle("-fx-min-height:300px; -fx-min-width:100px; -fx-padding:5px; -fx-padding-top:10px;");
		Label historyL = new Label(rb.getString("cn_history"));
		TextArea complexNumberHistory = new TextArea();
		complexNumberHistory.setStyle("-fx-min-height:20px");
		HBox clear = new HBox();
		Button reset = new Button(rb.getString("cn_reset"));
		clear.setSpacing(5);
		clear.setPadding(new Insets(10, 0, 0, 10));
		clear.getChildren().addAll(reset);
		mainTable.setPadding(new Insets(10, 0, 0, 10));
		mainTable.setSpacing(5);
		mainTable.getChildren().addAll(historyL, complexNumberHistory, clear);

		// Espacio para los resultados
		VBox results = new VBox();
		results.setStyle("-fx-min-height:50px; -fx-min-width:50px; -fx-padding:5px;");
		Label resl1 = new Label(rb.getString("cn_result"));
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
			Menu opcMenu = new Menu(rb.getString("cn_opc_menu"));
			MenuItem backBtn = new MenuItem(rb.getString("cn_info_back"));
			opcMenu.getItems().add(backBtn);
			menuInfo.getMenus().addAll(opcMenu);
			imgv.setImage(infoImg);
			VBox vBox = new VBox();
			vBox.getChildren().addAll(menuInfo, imgv);
			backBtn.setOnAction((event2) -> {
				ComplexNumGUI main = new ComplexNumGUI(this.rb);
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
				cleanHistoryField(complexNumberHistory);
			} catch (NumberFormatException e) {
			}
		});

		// Añadir un número complejo
		addComplexNumber.setOnAction((event) -> {
			if (!(ComplexNumber.addComplexNumber(history, complexNumberHistory))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(rb.getString("cn_alert_ins_err"));
				alert.setHeaderText(rb.getString("cn_alert_wrg_data"));
				alert.setContentText(rb.getString("cn_alert_try"));
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
				alert.setTitle(rb.getString("cn_alert_ins_err"));
				alert.setHeaderText(rb.getString("cn_alert_wrg_data"));
				alert.setContentText(rb.getString("cn_alert_try"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_addA_msg")));
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_addB_msg")));
				ComplexNumber A = history.get(i);
				ComplexNumber B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.add(A, B));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_two_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_subA_msg")));
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_subB_msg")));
				ComplexNumber A = history.get(i);
				ComplexNumber B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.subtract(A, B));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_two_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_multA_msg")));
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_multB_msg")));
				ComplexNumber A = history.get(i);
				ComplexNumber B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.multiply(A, B));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_two_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_divA_msg")));
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_divB_msg")));
				ComplexNumber A = history.get(i);
				ComplexNumber B = history.get(j);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.divide(A, B));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_two_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_conj_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(A.conjugate());
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_magn_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(new ComplexNumber(A.mod(), 0));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_sqr_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(A.square());
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_exp_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.exp(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_pow_msg1")));
				int power = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_pow_msg2")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.pow(A, power));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_sin_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.sin(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_cos_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.cos(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_tan_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.tan(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_cot_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.cot(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_sec_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.sec(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_cosec_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(ComplexNumber.cot(A));
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("cn_inv_msg")));
				ComplexNumber A = history.get(i);
				prepareAnswerField(resTxtArea);
				answerComplexNumber.add(A.inverse());
				if (answerComplexNumber.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("cn_calc_err"));
					alert.setHeaderText(rb.getString("cn_prop_vio_err"));
					alert.setContentText(rb.getString("cn_one_num_alert"));
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
		stage.setTitle(rb.getString("cn_title"));
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
