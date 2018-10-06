package application;

import calculations.Matrixes;
import calculations.Matrixes.Matrix;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
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

	public static LinkedList<Matrix> history=new LinkedList<>();
	public Matrixes matrixContainer=new Matrixes();
	public static LinkedList<Matrix> answerMatrix=new LinkedList<>();
	static int MatrixID=0;

	public static final int SUMA=0;
	public static final int RESTA=0;
	public static final int MULTIPLICACION=0;
	public static final int DIVISION=0;

	@Override
	public void start(Stage stage) {
		HBox options = new HBox();
		Label matrixesL=new Label("Matrices:");
		TextField matrixes=new TextField();
		Label rowL=new Label("Filas:");
		TextField rows=new TextField();
		Label colL=new Label("Columnas:");
		TextField cols=new TextField();
		Button generate=new Button("Cargar");
		options.setSpacing(5);
		options.setPadding(new Insets(10, 0, 0, 10));
		options.getChildren().addAll(matrixesL,matrixes,rowL,rows,colL,cols,generate);


		MenuBar menu=new MenuBar();
		Menu menuLoad=new Menu("Cargar");
		MenuItem addMatrix=new MenuItem("Nueva matriz");
		MenuItem randomMatrix = new MenuItem("Matriz aleatoria");
		MenuItem identityMatrix = new MenuItem("Matriz identidad");
		menuLoad.getItems().addAll(addMatrix,randomMatrix,identityMatrix);
		Menu menuMultiply=new Menu("Multiplicaciones");
		MenuItem dotProduct = new MenuItem("Producto punto");
		MenuItem multiplyMatrix = new MenuItem("Producto matricial");
		MenuItem multiplyElements = new MenuItem("Elemento por elemento");
		menuMultiply.getItems().addAll(dotProduct,multiplyMatrix,multiplyElements);
		Menu menuOps=new Menu("Operaciones básicas");
		MenuItem sum=new MenuItem("Sumar");
		MenuItem substract=new MenuItem("Restar");
		MenuItem divide=new MenuItem("Dividir");
		menuOps.getItems().addAll(sum,substract,divide);
		Menu special = new Menu("Funciones Especiales");
		MenuItem inverse = new MenuItem("Matriz inversa");
		MenuItem adjoint = new MenuItem("Matriz adjunta");
		MenuItem cofactor = new MenuItem("Matriz de cofactores");
		special.getItems().addAll(inverse,adjoint,cofactor);
		Menu change=new Menu("Cambiar Pestaña");
		MenuItem backButton= new MenuItem("Menu Principal");
		change.getItems().addAll(backButton);
		menu.getMenus().addAll(menuLoad,menuMultiply,menuOps,special,change);
		

		VBox root = new VBox();


		FlowPane mainResults = new FlowPane();


		VBox mainTable = new VBox();
		mainTable.setStyle("-fx-min-height:500px; -fx-min-width:100px; -fx-padding:5px; -fx-padding-top:10px;");
		Label historyL=new Label("Matrices utilizables:");
		TextArea matrixHistory=new TextArea();
		matrixHistory.setStyle("-fx-min-height:400px");
		HBox operandOptions=new HBox();
		Button description= new Button("Calcular propiedades");
		operandOptions.setSpacing(5);
		operandOptions.setPadding(new Insets(10, 0, 0, 10));
		operandOptions.getChildren().addAll(description);

		mainTable.setPadding(new Insets(10, 0, 0, 10));
		mainTable.setSpacing(5);
		mainTable.getChildren().addAll(historyL,matrixHistory,operandOptions);

		VBox results=new VBox();
		results.setStyle("-fx-min-height:500px; -fx-min-width:100px; -fx-padding:5px;");
		Label resl1=new Label("Resultado:");
		TextArea resTxtArea=new TextArea();
		Button resButton=new Button("Agregar a historial");
		Label resl2=new Label("Determinante:");
		TextField det=new TextField();
		Label resl3=new Label("Transpuesta:");
		Button resButton2=new Button("Agregar a historial");
		TextArea resTxtArea2=new TextArea();
		results.getChildren().addAll(resl1,resTxtArea,resButton,resl2,det,resl3,resTxtArea2,resButton2);
		results.setSpacing(5);


		mainResults.setPadding(new Insets(10, 0, 0, 10));
		mainResults.getChildren().addAll(mainTable,results);

		root.setSpacing(5d);
		root.getChildren().addAll(menu,mainResults);


		//EVENTOS
		backButton.setOnAction((event)->{
			Start main= new Start();
			try {
				main.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		resButton.setOnAction((event)->{
			if(!answerMatrix.isEmpty()) {
				history.add(answerMatrix.get(0));
				history.getLast().setID(MatrixID++);
				Matrixes.updateHistory(history,matrixHistory);
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en las exportación");
				alert.setHeaderText("No se encontró el resultado");
				alert.setContentText("No hay ningún resultado para exportar");
				alert.showAndWait();
			}
		});
		
		resButton2.setOnAction((event)->{
			if(!answerMatrix.isEmpty()) {
				history.add(answerMatrix.get(1));
				history.getLast().setID(MatrixID++);
				Matrixes.updateHistory(history,matrixHistory);
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en las exportación");
				alert.setHeaderText("No se encontró el resultado");
				alert.setContentText("No hay ningún resultado para exportar");
				alert.showAndWait();
			}
		});
		
		addMatrix.setOnAction((event)->{
			if(!(Matrixes.addMatrix(history,matrixHistory))){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en la inserción");
				alert.setHeaderText("Datos incorrectos");
				alert.setContentText("Vuelva a intentar e ingrese números sin errores.");
				alert.showAndWait();
				return;
			}	
			history.getLast().setID(MatrixID++);
			Matrixes.updateHistory(history,matrixHistory);
		});
		randomMatrix.setOnAction((event)->{
			if(!(Matrixes.random(history))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en la inserción");
				alert.setHeaderText("Datos incorrectos");
				alert.setContentText("Vuelva a intentar e ingrese números sin errores.");
				alert.showAndWait();
				return;
			}
			history.getLast().setID(MatrixID++);
			Matrixes.updateHistory(history,matrixHistory);
		});
		identityMatrix.setOnAction((event)->{
			if(!(Matrixes.identity(history))) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error en la inserción");
				alert.setHeaderText("Datos incorrectos");
				alert.setContentText("Vuelva a intentar e ingrese números sin errores.");
				alert.showAndWait();
				return;
			}
			history.getLast().setID(MatrixID++);
			Matrixes.updateHistory(history,matrixHistory);
		});


		//Obtiene el producto punto entre matrices y calcula sus propiedades.
		dotProduct.setOnAction((event)->{
			try{
				double multiplier=Double.parseDouble(JOptionPane.showInputDialog("Introduce el multiplicador"));
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz a multiplicar"));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.dotProduct(multiplier,history.getLast()));
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(history.getLast())));
				else
					det.setText("Esta matriz no tiene determinante por no ser cuadrada.");
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				answerMatrix.add(Matrixes.trasposeMatrix(history.get(i)));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});
		
		//Obtiene la division entre matrices y calcula sus propiedades.
		divide.setOnAction((event)->{
			try{
				double dividend=Double.parseDouble(JOptionPane.showInputDialog("Introduce el divisor:"));
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz a dividir:"));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.divide(dividend,history.get(i)));
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(history.getLast())));
				else
					det.setText("Esta matriz no tiene determinante por no ser cuadrada.");
				resTxtArea.setText(Matrixes.printMatrix(history.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(Matrixes.trasposeMatrix(history.getLast())));
			}catch(NumberFormatException e) {}	
			Matrixes.updateHistory(history,matrixHistory);
		});
		
		//Obtiene las propiedades de una matriz
		description.setOnAction((event)->{
			try{
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz por describir"));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				resTxtArea.setText(Matrixes.printMatrix(history.get(i)));
				if(Matrixes.isSquared(history.get(i)))
					det.setText(Double.toString(Matrixes.determinantCalculator(history.get(i))));
				else
					det.setText("Esta matriz no tiene determinante por no ser cuadrada.");
				answerMatrix.add(null);
				answerMatrix.add(Matrixes.trasposeMatrix(history.get(i)));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});

		//Obtiene la multiplicacion entre matrices y calcula sus propiedades.
		multiplyMatrix.setOnAction((event)->{
			try {
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz A por multiplicar"));
				int j=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz B por multiplicar"));
				Matrix A=history.get(i);
				Matrix B=history.get(j);
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.matrixProduct(A, B));
				if(answerMatrix.getLast()==null){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en las matrices");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Las matrices deben cumplir que las filas de A sean las columnas de B");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText("Esta matriz no tiene determinante por no ser cuadrada.");
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});
		
		//Obtiene la multiplicacion elemento por elemento y calcula sus propiedades.
		multiplyElements.setOnAction((event)->{
			try {
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz A por multiplicar"));
				int j=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz B por multiplicar"));
				Matrix A=history.get(i);
				Matrix B=history.get(j);
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.multiply2Matrixes(A, B));
				if(answerMatrix.getLast()==null){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en las matrices");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Las matrices deben cumplir que las filas de A sean las columnas de B");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText("Esta matriz no tiene determinante por no ser cuadrada.");
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});
		
		//Obtiene la suma y calcula sus propiedades.
		sum.setOnAction((event)->{
			try {
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz A por sumar"));
				int j=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz B por sumar"));
				Matrix A=history.get(i);
				Matrix B=history.get(j);
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.sum(A, B));
				if(answerMatrix.getLast()==null){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en las matrices");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Las matrices deben cumplir que las filas de A sean las columnas de B");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText("Esta matriz no tiene determinante por no ser cuadrada.");
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});
		
		//Obtiene la resta y calcula sus propiedades.
		substract.setOnAction((event)->{
			try {
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz A por restar"));
				int j=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz B por restar"));
				Matrix A=history.get(i);
				Matrix B=history.get(j);
				prepareAnswerFields(resTxtArea, resTxtArea2);
				answerMatrix.add(Matrixes.substract(A, B));
				if(answerMatrix.getLast()==null){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en las matrices");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("Las matrices deben cumplir que las filas de A sean las columnas de B");
					alert.showAndWait();
					return;
				}
				resTxtArea.setText(Matrixes.printMatrix(answerMatrix.getLast()));
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText("Esta matriz no tiene determinante por no ser cuadrada.");
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});

		//Invierte la matriz y muestra las propiedades de dicho resultado
		inverse.setOnAction((event)->{
			try{
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz por invertir"));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				if(!Matrixes.isSquared(history.get(i))){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en las matrices");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("La matriz debe ser cuadrada");
					alert.showAndWait();
					return;
				}
				answerMatrix.add(Matrixes.inverse(history.get(i)));
				if(answerMatrix.getLast()!=null)
					resTxtArea.setText(Matrixes.printMatrix((answerMatrix.getLast())));
				else {
						resTxtArea.setText("La matriz no tiene inversa");
						return;
				}
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText("Esta matriz no tiene determinante porque no es cuadrada.");
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});
		
		//
		adjoint.setOnAction((event)->{
			try{
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz a obtener la adjunta"));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				if(!Matrixes.isSquared(history.get(i))){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en las matrices");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("La matriz debe ser cuadrada");
					alert.showAndWait();
					return;
				}
				answerMatrix.add(Matrixes.adjoint(history.get(i)));
				resTxtArea.setText(Matrixes.printMatrix((answerMatrix.getLast())));
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText("Esta matriz no tiene determinante porque no es cuadrada.");
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});
		
		//Calcula la matriz de cofactores y muestra sus propiedades.
		cofactor.setOnAction((event)->{
			try{
				int i=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la matriz a obtener los cofactores"));
				prepareAnswerFields(resTxtArea, resTxtArea2);
				if(!Matrixes.isSquared(history.get(i))){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error en las matrices");
					alert.setHeaderText("Violación de propiedades:");
					alert.setContentText("La matriz debe ser cuadrada");
					alert.showAndWait();
					return;
				}
				answerMatrix.add(Matrixes.cofactorMatrix(history.get(i)));
				resTxtArea.setText(Matrixes.printMatrix((answerMatrix.getLast())));
				if(Matrixes.isSquared(answerMatrix.getLast()))
					det.setText(Double.toString(Matrixes.determinantCalculator(answerMatrix.getLast())));
				else
					det.setText("Esta matriz no tiene determinante porque no es cuadrada.");
				answerMatrix.add(Matrixes.trasposeMatrix(answerMatrix.getLast()));
				resTxtArea2.setText(Matrixes.printMatrix(answerMatrix.getLast()));
			}catch(NumberFormatException e) {}
		});




		//DECLARACIÓN FINAL
		Scene scene = new Scene(root,1000,700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setHeight(primaryScreenBounds.getHeight());
		stage.setResizable(false);
		stage.setTitle("Operaciones matriciales");
		stage.show();
		stage.centerOnScreen();
	}


	//Métodos necesarios


	public static void prepareAnswerFields(TextArea resTxtArea,TextArea resTxtArea2) {
		answerMatrix.clear();
		resTxtArea.setText("");
		resTxtArea2.setText("");
	}
}
