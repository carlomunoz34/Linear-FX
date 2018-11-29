package calculations;

/**
 * 
 * @author Carlo Muñoz
 *
 *         This class is created with the purpose to perform the operations in
 *         the part of Linear Transformations
 */
public class LinearTransformation {
	private Matrix matrix;
	private String equation;
	private String name;

	/**
	 * Principal constructor.
	 * 
	 * @param matrix is the matrix of the linear transformations
	 */
	public LinearTransformation(Matrix matrix) {
		this.matrix = matrix;
		equation = "";
	}

	/**
	 * Apply the linear transformation to a vector
	 * 
	 * @param vector is the vector which the operation will be applied
	 * @return the result of the transformation
	 */
	public Matrix apply(Matrix vector) throws IllegalDimensionException {
		return matrix.crossProduct(vector);
	}

	/**
	 * Get the equation of the LT
	 * 
	 * @return a string which contains the equation
	 */
	public String getEquation() {
		if (equation == "") {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < matrix.getRows(); i++) {
				boolean impresed = false;
				for (int j = 0; j < matrix.getColumns(); j++) {
					if (matrix.get(i, j) != 0) {
						if (impresed && matrix.get(i, j) > 0)
							sb.append(" + ");
						else if (matrix.get(i, j) < 0)
							sb.append(" - ");

						if (Math.abs(matrix.get(i, j)) > 1) {
							sb.append(String.format("%.2f%c", Math.abs(matrix.get(i, j)),
									(char) (('x' - 'a' + j) % ('z' - 'a' + 1) + 'a')));
							impresed = true;
						}
						else {
							sb.append(String.format("%c", (char) (('x' - 'a' + j) % ('z' - 'a' + 1) + 'a')));
							impresed = true;
						}
					}
				}

				sb.append('\n');
			}
			equation = sb.toString();
		}

		return equation;
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		equation = "";
		this.matrix = matrix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class Matrix {
		private double[][] matrix;
		private int columns, rows;

		public Matrix(double[][] matrix) {
			setMatrix(matrix);
		}

		public double get(int i, int j) {
			return matrix[i][j];
		}

		public double[][] getMatrix() {
			return matrix;
		}

		public void setMatrix(double[][] matrix) {
			this.matrix = matrix;

			if (matrix != null) {
				rows = matrix.length;
				if (rows >= 1 && matrix[0] != null)
					columns = matrix[0].length;
			}
		}

		public int getColumns() {
			return columns;
		}

		public int getRows() {
			return rows;
		}

		public Matrix crossProduct(Matrix other) throws IllegalDimensionException {
			if (this.columns != other.rows)
				throw new IllegalDimensionException();

			double[][] result = new double[this.rows][other.columns];

			for (int i = 0; i < rows; i++)
				for (int j = 0; j < other.columns; j++)
					for (int k = 0; k < columns; k++)
						result[i][j] += this.matrix[i][k] * other.matrix[k][j];

			return new Matrix(result);
		}

		public void printMat() {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++)
					System.out.print(matrix[i][j] + " ");
				System.out.println();
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sc = new StringBuilder();
			for(int i = 0; i < matrix.length; i++) {
				for(int j = 0; j < matrix[i].length; j++) {
					sc.append(matrix[i][j]);
					sc.append(" ");
				}
				sc.append("\n");
			}
			
			return sc.toString();
		}

	}
}
