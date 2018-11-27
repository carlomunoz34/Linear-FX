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
	public LinearTransformation(Matrix matrix, String name) {
		this.matrix = matrix;
		this.name = name;
		equation = "";
	}

	/**
	 * Apply the linear transformation to a vector
	 * 
	 * @param vector is the vector which the operation will be applied
	 * @return the result of the transformation
	 */
	public Matrix apply(Matrix vector) throws IllegalArgumentException {
		return matrix.crossProduct(vector);
	}

	/**
	 * Get the equation of the LT
	 * 
	 * @return a string which contains the equation
	 */
	public String getEquation() {
		if (equation != "") {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < matrix.getColumns(); i++) {
				for (int j = 0; j < matrix.getRows(); j++) {
					sb.append(String.format("%d%s", matrix.get(i, j), (char) (('x' - 'a' + j) % ('z' - 'a') + 'a')));
					if (j < matrix.getRows() - 1)
						sb.append(" + ");
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
		this.matrix = matrix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public class Matrix {
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
				if (rows > 1 && matrix[0] != null)
					columns = matrix[0].length;
			}
		}

		public int getColumns() {
			return columns;
		}

		public int getRows() {
			return rows;
		}

		public Matrix crossProduct(Matrix other) throws IllegalArgumentException {
			if (this.columns != other.rows)
				throw new IllegalArgumentException();

			double[][] result = new double[this.rows][other.columns];

			for (int i = 0; i < columns; i++) {
				for (int j = 0; j < rows; j++) {
					for (int k = 0; k < columns; k++) {
						result[i][j] += this.matrix[i][k] * other.matrix[k][j];
					}
				}
			}

			return new Matrix(result);
		}

	}
}
