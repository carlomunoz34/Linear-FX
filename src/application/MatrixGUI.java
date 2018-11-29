package application;

import calculations.Matrixes;
import calculations.Matrixes.Matrix;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MatrixGUI extends Application {
	public ResourceBundle rb;
	public static LinkedList<Matrix> history = new LinkedList<>();
	public Matrixes matrixContainer = new Matrixes();
	public static LinkedList<Matrix> answerMatrix = new LinkedList<>();
	static int MatrixID = 0;

	public static final int SUMA = 0;
	public static final int RESTA = 0;
	public static final int MULTIPLICACION = 0;
	public static final int DIVISION = 0;

	public MatrixGUI(ResourceBundle rb) {
		this.rb = rb;
	}

	@Override
	public void start(Stage stage) {
		MenuBar menu = new MenuBar();
		Menu menuLoad = new Menu(rb.getString("m_load"));
		menuLoad.getStyleClass().add("menu-item");
		MenuItem addMatrix = new MenuItem(rb.getString("m_new"));
		MenuItem randomMatrix = new MenuItem(rb.getString("m_rand"));
		MenuItem identityMatrix = new MenuItem(rb.getString("m_identity"));
		menuLoad.getItems().addAll(addMatrix, randomMatrix, identityMatrix);
		Menu menuMultiply = new Menu(rb.getString("m_mult"));
		MenuItem dotProduct = new MenuItem(rb.getString("m_dot"));
		MenuItem multiplyMatrix = new MenuItem(rb.getString("m_cross"));
		MenuItem multiplyElements = new MenuItem(rb.getString("m_1_by_1"));
		menuMultiply.getItems().addAll(dotProduct, multiplyMatrix, multiplyElements);
		Menu menuOps = new Menu(rb.getString("m_basic"));
		MenuItem sum = new MenuItem(rb.getString("m_add"));
		MenuItem substract = new MenuItem(rb.getString("m_subb"));
		MenuItem divide = new MenuItem(rb.getString("m_div"));
		menuOps.getItems().addAll(sum, substract, divide);
		Menu special = new Menu(rb.getString("m_special"));
		MenuItem inverse = new MenuItem(rb.getString("m_inverse"));
		MenuItem adjoint = new MenuItem(rb.getString("m_adj"));
		MenuItem cofactor = new MenuItem(rb.getString("m_cof"));
		special.getItems().addAll(inverse, adjoint, cofactor);
		Menu change = new Menu(rb.getString("m_back"));
		MenuItem backButton = new MenuItem(rb.getString("m_home"));
		change.getItems().addAll(backButton);
		menu.getMenus().addAll(menuLoad, menuMultiply, menuOps, special, change);

		VBox root = new VBox();

		FlowPane mainResults = new FlowPane();

		VBox mainTable = new VBox();
		mainTable.setStyle("-fx-min-height:500px; -fx-min-width:100px; -fx-padding:5px; -fx-padding-top:10px;");
		Label historyL = new Label(rb.getString("m_history"));
		TextArea matrixHistory = new TextArea();
		matrixHistory.setEditable(false);
		matrixHistory.setStyle("-fx-min-height:400px");
		HBox operandOptions = new HBox();
		Button description = new Button(rb.getString("m_calc"));
		operandOptions.setSpacing(5);
		operandOptions.setPadding(new Insets(10, 0, 0, 5));
		operandOptions.getChildren().addAll(description);

		mainTable.setPadding(new Insets(10, 0, 0, 10));
		mainTable.setSpacing(5);
		mainTable.getChildren().addAll(historyL, matrixHistory, operandOptions);

		VBox results = new VBox();
		results.setStyle("-fx-min-height:500px; -fx-min-width:100px; -fx-padding:5px;");
		Label resl1 = new Label(rb.getString("m_res"));
		TextArea resTxtArea = new TextArea();
		resTxtArea.setEditable(false);
		Button resButton = new Button(rb.getString("m_to_hist"));
		Label resl2 = new Label(rb.getString("m_det"));
		TextField det = new TextField();
		Label resl3 = new Label(rb.getString("m_trans"));
		Button resButton2 = new Button(rb.getString("m_to_hist"));
		TextArea resTxtArea2 = new TextArea();
		resTxtArea2.setEditable(false);
		results.getChildren().addAll(resl1, resTxtArea, resButton, resl2, det, resl3, resTxtArea2, resButton2);
		results.setSpacing(5);

		mainResults.setPadding(new Insets(10, 0, 0, 10));
		mainResults.getChildren().addAll(mainTable, results);

		// TUTORIALS:
		HBox tutorialOptions = new HBox();
		tutorialOptions.setStyle("-fx-min-width:100px;");
		tutorialOptions.setPadding(new Insets(-35, 0, 0, 19));

		Button dotPTutorial = new Button(rb.getString("m_tutorials"));

		tutorialOptions.getChildren().addAll(dotPTutorial);
//		dotPTutorial.setOnAction((event) -> {
//			WebSite w = new WebSite();
//			try {
//				w.start(stage);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});

		root.setSpacing(5d);
		root.getChildren().addAll(menu, mainResults, tutorialOptions);

		// EVENTOS
		backButton.setOnAction((event) -> {
			Start main = new Start();
			try {
				main.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		resButton.setOnAction((event) -> {
			if (!answerMatrix.isEmpty()) {
				history.add(answerMatrix.get(0));
				history.getLast().setID(MatrixID++);
				Matrixes.updateHistory(history, matrixHistory);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(rb.getString("m_alert_expt_t"));
				alert.setHeaderText(rb.getString("m_alert_expt_h"));
				alert.setContentText(rb.getString("m_alert_expt_c"));
				alert.showAndWait();
			}
		});

		resButton2.setOnAction((event) -> {
			if (!answerMatrix.isEmpty()) {
				history.add(answerMatrix.get(1));
				history.getLast().setID(MatrixID++);
				Matrixes.updateHistory(history, matrixHistory);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(rb.getString("m_alert_expt_t"));
				alert.setHeaderText(rb.getString("m_alert_expt_h"));
				alert.setContentText(rb.getString("m_alert_expt_c"));
				alert.showAndWait();
			}
		});

		addMatrix.setOnAction((event) -> {
			if (!(Matrixes.addMatrix(history, matrixHistory))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(rb.getString("m_alert_insert_t"));
				alert.setHeaderText(rb.getString("m_alert_insert_h"));
				alert.setContentText(rb.getString("m_alert_insert_c"));
				alert.showAndWait();
				return;
			}
			history.getLast().setID(MatrixID++);
			Matrixes.updateHistory(history, matrixHistory);
		});
		randomMatrix.setOnAction((event) -> {
			if (!(Matrixes.random(history))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(rb.getString("m_alert_insert_t"));
				alert.setHeaderText(rb.getString("m_alert_insert_h"));
				alert.setContentText(rb.getString("m_alert_insert_c"));
				alert.showAndWait();
				return;
			}
			history.getLast().setID(MatrixID++);
			Matrixes.updateHistory(history, matrixHistory);
		});
		identityMatrix.setOnAction((event) -> {
			if (!(Matrixes.identity(history))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(rb.getString("m_alert_insert_t"));
				alert.setHeaderText(rb.getString("m_alert_insert_h"));
				alert.setContentText(rb.getString("m_alert_insert_c"));
				alert.showAndWait();
				return;
			}
			history.getLast().setID(MatrixID++);
			Matrixes.updateHistory(history, matrixHistory);
		});

		// Obtiene el producto punto entre matrices y calcula sus propiedades.
		dotProduct.setOnAction((event) -> {
			try {
				double multiplier = Double.parseDouble(JOptionPane.showInputDialog(rb.getString("m_multiplier")));
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_select_mult")));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.dotProduct(multiplier, history.getLast()));
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(history.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				answerMatrix.add(Matrixes.trasposeMatrix(history.get(i)));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		// Obtiene la division entre matrices y calcula sus propiedades.
		divide.setOnAction((event) -> {
			try {
				double dividend = Double.parseDouble(JOptionPane.showInputDialog(rb.getString("m_dividend")));
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_select_div")));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.divide(dividend, history.get(i)));
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(history.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				resTxtArea.setText(Matrixes.printMatrix(history.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(Matrixes.trasposeMatrix(history.getLast())));
			} catch (NumberFormatException e) {
			}
			Matrixes.updateHistory(history, matrixHistory);
		});

		// Obtiene las propiedades de una matriz
		description.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_select_desc")));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				resTxtArea.setText(Matrixes.printMatrix(history.get(i)));
				if (Matrixes.isSquared(history.get(i)))
					det.setText(Double.toString(Matrixes.determinantCalculator(history.get(i))));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				answerMatrix.add(null);
				answerMatrix.add(Matrixes.trasposeMatrix(history.get(i)));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		// Obtiene la multiplicacion entre matrices y calcula sus propiedades.
		multiplyMatrix.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_a_mult")));
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_b_mult")));
				Matrix A = history.get(i);
				Matrix B = history.get(j);
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.matrixProduct(A, B));
				if (answerMatrix.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("m_alert_matrix_t"));
					alert.setHeaderText(rb.getString("m_alert_matrix_h"));
					alert.setContentText(rb.getString("m_alert_matrix_c"));
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		// Obtiene la multiplicacion elemento por elemento y calcula sus propiedades.
		multiplyElements.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_a_mult")));
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_b_mult")));
				Matrix A = history.get(i);
				Matrix B = history.get(j);
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.multiply2Matrixes(A, B));
				if (answerMatrix.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("m_alert_matrix_t"));
					alert.setHeaderText(rb.getString("m_alert_matrix_h"));
					alert.setContentText(rb.getString("m_alert_matrix_c"));
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		// Obtiene la suma y calcula sus propiedades.
		sum.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_a_add")));
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_b_add")));
				Matrix A = history.get(i);
				Matrix B = history.get(j);
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.sum(A, B));
				if (answerMatrix.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("m_alert_matrix_t"));
					alert.setHeaderText(rb.getString("m_alert_matrix_h"));
					alert.setContentText(rb.getString("m_alert_matrix_c"));
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		// Obtiene la resta y calcula sus propiedades.
		substract.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_a_subb")));
				int j = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_b_subb")));
				Matrix A = history.get(i);
				Matrix B = history.get(j);
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.substract(A, B));
				if (answerMatrix.getLast() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("m_alert_matrix_t"));
					alert.setHeaderText(rb.getString("m_alert_matrix_h"));
					alert.setContentText(rb.getString("m_alert_matrix_c"));
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		// Invierte la matriz y muestra las propiedades de dicho resultado
		inverse.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_sel_inv")));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				if (!Matrixes.isSquared(history.get(i))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("m_alert_matrix_t"));
					alert.setHeaderText(rb.getString("m_alert_matrix_h"));
					alert.setContentText(rb.getString("m_alert_matrix_sq_c"));
					alert.showAndWait();
					return;
				}
				answerMatrix.add(Matrixes.inverse(history.get(i)));
				if (answerMatrix.getLast() != null)
					resTxtArea.setText(Matrixes.printMatrix((answerMatrix.getLast())));
				else {
					resTxtArea.setText(rb.getString("m_no_inv"));
					return;
				}
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		//
		adjoint.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_sel_adj")));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				if (!Matrixes.isSquared(history.get(i))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("m_alert_matrix_t"));
					alert.setHeaderText(rb.getString("m_alert_matrix_h"));
					alert.setContentText(rb.getString("m_alert_matrix_sq_c"));
					alert.showAndWait();
					return;
				}
				answerMatrix.add(Matrixes.adjoint(history.get(i)));
				resTxtArea.setText(Matrixes.printMatrix((answerMatrix.getLast())));
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		// Calcula la matriz de cofactores y muestra sus propiedades.
		cofactor.setOnAction((event) -> {
			try {
				int i = Integer.parseInt(JOptionPane.showInputDialog(rb.getString("m_matrix_sel_cof")));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				if (!Matrixes.isSquared(history.get(i))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(rb.getString("m_alert_matrix_t"));
					alert.setHeaderText(rb.getString("m_alert_matrix_h"));
					alert.setContentText(rb.getString("m_alert_matrix_sq_c"));
					alert.showAndWait();
					return;
				}
				answerMatrix.add(Matrixes.cofactorMatrix(history.get(i)));
				resTxtArea.setText(Matrixes.printMatrix((answerMatrix.getLast())));
				if (Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText(rb.getString("m_matrix_not_squared"));
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			} catch (NumberFormatException e) {
			}
		});

		// DECLARACIÓN FINAL
		Scene scene = new Scene(root, 1000, 700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setWidth(1000d);
		stage.setHeight(650d);
		stage.setResizable(false);
		stage.setTitle(rb.getString("m_title"));
		stage.show();
		stage.centerOnScreen();
	}

	// Métodos necesarios

	public static void prepareAnswerFields(TextArea resTxtArea, TextArea resTxtArea2) {
		answerMatrix.clear();
		resTxtArea.setText("");
		resTxtArea2.setText("");
	}
}
