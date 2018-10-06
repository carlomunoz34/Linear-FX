package calculations;

import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javafx.scene.control.TextArea;

public class Matrixes {

	/**Base matrix element. Contains ID,rows,columns and the matrix itself.*/
	public static class Matrix{
		int ID;
		int rows;
		int cols;
		double[][] content;
		public Matrix(int rows,int cols,double[][] content){
			this.rows=rows;
			this.cols=cols;
			this.content=content;
		}

		public void setID(int MatrixID) {
			this.ID=MatrixID;
		}
	}



	/**Returns true if the matrix rows are equal to its columns
	 * @param m The Matrix to be tested*/
	public static boolean isSquared(Matrix m) {
		return m.rows==m.cols;
	}

	///////////////////////////MATRIX GENERATION//////////////////////////////////////////

	/**Method that adds a new matrix to the history in order to be operated.*/
	public static boolean addMatrix(LinkedList<Matrix> history,TextArea area){
		try{
			try {
				int rows=Integer.parseInt(JOptionPane.showInputDialog("Ingrese las filas"));
				int cols=Integer.parseInt(JOptionPane.showInputDialog("Ingrese las columnas"));
				double[][]content= new double[rows][cols];
				for(int i=0; i<rows; i++) {
					for(int j=0; j<cols; j++) {
						content[i][j]=Double.parseDouble(JOptionPane.showInputDialog("Ingrese el dato Matriz:"+(i+1)+(j+1)));
					}
				}
				Matrix m=new Matrix(rows, cols, content);
				history.add(m);
				area.setText(area.getText()+"\n"+m.ID+":\n"+printMatrix(m));
			}catch(NullPointerException e) {
				return false;
			}
		}catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}

	/** Generate matrix with random elements.*/
	public static boolean random (LinkedList<Matrix> history) {
		try{
			try {
				int rows=Integer.parseInt(JOptionPane.showInputDialog("Ingrese las filas"));
				int cols=Integer.parseInt(JOptionPane.showInputDialog("Ingrese las columnas"));
				double[][] x = new double[rows][cols];
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						x[i][j] = Math.random()*10;
					}
				}
				Matrix m = new Matrix(rows,cols,x);
				history.add(m);
			}catch(NullPointerException e) {
				return false;
			}
		}catch(NumberFormatException ex) {
			return false;
		}return true;
	}

	/**Generates a matrix with 1s in the diagonal and all remaining numbers 0s.
	 * @return     A matrix with identity characteristics.*/
	public static boolean identity (LinkedList<Matrix> history) {
		try{
			try{
				int rows=Integer.parseInt(JOptionPane.showInputDialog("Ingrese las filas"));
				int cols=Integer.parseInt(JOptionPane.showInputDialog("Ingrese las columnas"));
				double[][] x = new double[rows][cols];
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						x[i][j] = (i == j ? 1.0 : 0.0);
					}
				}
				Matrix m = new Matrix(rows,cols,x);
				history.add(m);
			}catch(NullPointerException e) {
				return false;
			}
		}catch(NumberFormatException ex) {
			return false;
		}return true;
	}

	///////////////////////////////MATRIX OPERATIONS///////////////////////////////


	/**Method that fills a text area with all available matrixes registeres throughout the program execution.
	 * @param area The text area where the user will see the matrixes.*/
	public static void updateHistory(LinkedList<Matrix> history,TextArea area) {
		area.setText("");
		for(Matrix m: history) {
			area.setText(area.getText()+"\n"+m.ID+":\n"+Matrixes.printMatrix(m));
		}
	}

	/**Method that prints a Matrix object.
	 * @param matrix The matrix to be printed
	 * @return String with the corresponding matrix*/
	public static String printMatrix(Matrix matrix) {
		String mat="";
		for(int i=0; i<matrix.rows; i++) {
			for(int j=0; j<matrix.cols; j++) {
				mat+=String.format("%s\t",new DecimalFormat("00.00").format(matrix.content[i][j]));
			}
			mat+="\n";
		}
		return mat;
	}

	/**Method that makes the dotProduct multiplication and adds the resulting element to the history list.
	 * @param constant The multiplier
	 * @param matrix The matrix to be multiplied.
	 * @return Boolean that proves if given matrix is squared.*/
	public static Matrix dotProduct(double constant,Matrix matrix){
		double[][] matrix2=new double[matrix.rows][matrix.cols];
		for(int i=0; i<matrix.rows; i++) {
			for(int j=0; j<matrix.cols; j++) {
				matrix2[i][j]=matrix.content[i][j]*constant;
			}
		}
		Matrix m=new Matrix(matrix.rows,matrix.cols,matrix2);
		return m;
	}

	/** Element by element multiplication within 2 same sized matrixes.
	   @param A First matrix
	   @param B    Another matrix
	   @return     Matrix or null if properties are violated*/
	public static Matrix multiply2Matrixes (Matrix A, Matrix B) {
		if(!(A.rows==B.rows && A.cols==B.cols))
			return null;
		double[][] C = new double[A.rows][A.cols];
		for (int i = 0; i < A.rows; i++) {
			for (int j = 0; j < A.cols; j++) {
				C[i][j] = A.content[i][j] * B.content[i][j];
			}
		}
		Matrix m = new Matrix(A.rows,A.cols,C);
		return m;
	}

	/**Multiplication within 2 matrixes through cross product. Properties needed are A.rows==B.cols.
	 * @param A First matrix
	 * @param B    Another matrix
	 * @return      Matrix or null if properties are violated*/
	public static Matrix matrixProduct (Matrix A,Matrix B) {
		if (B.cols != A.rows) {
			return null;
		}
		double[][] C = new double[A.rows][B.cols];
		for (int x=0; x < C.length; x++) {
			for (int y=0; y < C[x].length; y++) {
				for (int z=0; z<B.rows; z++) {
					C[x][y] += A.content[x][z]*B.content[z][y]; 
				}
			}
		}
		Matrix m = new Matrix(A.rows,B.cols,C);
		return m;
	}


	/**Method that gets the determinant of a given matrix. This method assumes valid squared matrix is given.
	 * @param matrix The matrix to operate.
	 * @param size The length of either the rows or columns (have to be the same).
	 * @return Double containing the matrix determinant.*/
	public static double determinantCalculator(Matrix matrix) {
		return determinantOfMatrix(matrix.content, matrix.rows, matrix.cols);    	
	}

	/**Inverts the matrix elements to get a trasposed model.
	 * @param matrix The matrix to be trasposed.
	 * @return The resulting trasposed matrix.*/
	public static Matrix trasposeMatrix(Matrix matrix){
		int m = matrix.rows;
		int n = matrix.cols;

		double[][] trasposedMatrix = new double[n][m];

		for(int x = 0; x < n; x++){
			for(int y = 0; y < m; y++){
				trasposedMatrix[x][y] = matrix.content[y][x];
			}
		}
		Matrix trasposed = new Matrix(n,m,trasposedMatrix); 
		return trasposed;
	}

	/** Element by element sum within 2 same sized matrixes.
	   @param A First matrix
	   @param B    Another matrix
	   @return      Matrix or null if properties are violated*/
	public static Matrix sum(Matrix A,Matrix B) {
		if(!(A.rows==B.rows && A.cols==B.cols))
			return null;
		double[][] C = new double[A.rows][A.cols];
		for (int i = 0; i < A.rows; i++) {
			for (int j = 0; j < A.cols; j++) {
				C[i][j] = A.content[i][j] + B.content[i][j];
			}
		}
		Matrix m = new Matrix(A.rows,A.cols,C);
		return m;
	}

	/** Element by element substraction within 2 same sized matrixes.
	   @param A First matrix
	   @param B Another matrix
	   @return  Matrix or null if properties are violated*/
	public static Matrix substract(Matrix A,Matrix B) {
		if(!(A.rows==B.rows && A.cols==B.cols))
			return null;
		double[][] C = new double[A.rows][A.cols];
		for (int i = 0; i < A.rows; i++) {
			for (int j = 0; j < A.cols; j++) {
				C[i][j] = A.content[i][j] - B.content[i][j];
			}
		}
		Matrix m = new Matrix(A.rows,A.cols,C);
		return m;
	}

	/**Method that divides a matrix by a scalar and adds the resulting element to the result list.
	 * @param constant The divisor
	 * @param matrix The matrix to be divided.
	 * @return  Matrix or null if properties are violated*/
	public static Matrix divide(double constant,Matrix matrix){
		double[][] matrix2=new double[matrix.rows][matrix.cols];
		for(int i=0; i<matrix.rows; i++) {
			for(int j=0; j<matrix.cols; j++) {
				matrix2[i][j]=matrix.content[i][j]/constant;
			}
		}
		Matrix m=new Matrix(matrix.rows,matrix.cols,matrix2);
		return m;// Matrixes.isSquared(m);
	}




	/**Method that calculates the inverse of a matrix.
	 * Procedure consists of calculating determinant, then adjoint matrix and then divides adjoint by determinant.
	 * @return  Matrix or null if properties are violated*/
	public static Matrix inverse(Matrix matrix){ 
		double det= determinantCalculator(matrix);
		// Find determinant of A[][] 
		if (det == 0){ 
			return null; 
		} 	    
		// Find adjoint 
		Matrix adj=adjoint(matrix); 
		// Find Inverse using formula "inverse(A) = adj(A)/det(A)" 
		return divide(det, adj);
	} 	

	/**Method that consists of calculating the adjoint as the trasposed cofactor matrix.
	 * @return Adjoint matrix to be implemented in other methods.*/
	public static Matrix adjoint(Matrix matrix) {
		Matrix m= trasposeMatrix(cofactorMatrix(matrix));
		return m;
	}

	/**Method that calculates cofactor matrix through elimination of row/column of each element.
	 * @return Cofactor matrix to be implemented in other methods.*/
	public static Matrix cofactorMatrix(Matrix matrix) {
		double[][]cofactorMatrix=new double[matrix.rows][matrix.cols];
		for(int i=0; i<matrix.rows;i++) {
			for(int j=0; j<matrix.cols;j++) {
				double noRownoCol[][]=new double[matrix.rows-1][matrix.cols-1];
				getCofactor(matrix.content,noRownoCol,i,j,matrix.rows);
				int sign = ((i+j)%2==0)? 1: -1; 
				cofactorMatrix[i][j]=sign*determinantCalculator(new Matrix(matrix.rows-1,matrix.cols-1,noRownoCol));
			}
		}
		Matrix m=new Matrix(matrix.rows,matrix.cols,cofactorMatrix);
		return m;
	}

	//////////////////////////////////////////////HELPER METHODS/////////////////////////////////////////////////////

	// Function to get cofactor of  
	// mat[p][q] in temp[][]. n is  
	// current dimension of mat[][] 
	private static void getCofactor(double mat[][],  
			double temp[][], int p, int q, int n){ 
		int i = 0, j = 0; 

		// Looping for each element of  
		// the matrix 
		for (int row = 0; row < n; row++) { 
			for (int col = 0; col < n; col++){ 
				// Copying into temporary matrix  
				// only those element which are  
				// not in given row and column 
				if (row != p && col != q){ 
					temp[i][j++] = mat[row][col]; 
					// Row is filled, so increase  
					// row index and reset col  
					//index 
					if (j == n - 1){ 
						j = 0; 
						i++; 
					} 
				} 
			} 
		} 
	} 

	/* Recursive function for finding determinant 
    of matrix. n is current dimension of mat[][]. */
	private static double determinantOfMatrix(double mat[][], int n,int N){ 
		double D = 0; // Initialize result 
		// Base case : if matrix contains single 
		// element 
		if (n == 1d) 
			return mat[0][0]; 
		// To store cofactors 
		double temp[][] = new double[N][N];  
		// To store sign multiplier 
		int sign = 1;  
		// Iterate for each element of first row 
		for (int f = 0; f < n; f++){ 
			// Getting Cofactor of mat[0][f] 
			getCofactor(mat, temp, 0, f, n); 
			D += sign * mat[0][f]  
					* determinantOfMatrix(temp, n - 1, N); 
			// terms are to be added with  
			// alternate sign 
			sign = -sign; 
		} 
		return D; 
	} 



}
