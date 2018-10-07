package calculations;

/**
 * Clase Vector
 * @version 1.0 
 */

public class Vector {
	//Declaración de los componentes del vector
	private double i;
	private double j;
	private double k;
	int ID;
	

	//Constructor cuando creas un vector sin parametros
	public Vector() {
		//Se le asiga "0" a todas las componentes
		this.i = 0;
		this.j = 0;
		this.k = 0;
	
	}
	
	//Setter del ID del vector
	public void setID(int VectorID) {
		this.ID=VectorID;
	}
	
	/**
	 * Constructor para que el usuario nos dé valores, el valor de cada vector se guarda en su posición 
	 * @param i Valor del vector
	 * @param j Valor del vector
	 * @param k Valor del vector
	 * @param tipo Tipo del vector
	 */
	public Vector(double i, double j, double k) {
		setI(i);
		setJ(j);
		setK(k);
	}


	//Creación de getter's y setter's
	
	/**
	 * Método que obtiene la componente I
	 * @return la componente I
	 */
	public double getI() {
		return i;
	}
	
	/**
	 * Método que modifique la componente I del Vector
	 * @param i Valor con que el que se sumple la componente I del vector
	 */
	public void setI(double i) {
		this.i = i;
	}
	
	/**
	 * Método que obtiene la componente J
	 * @return la componente J
	 */
	public double getJ() {
		return j;
	}
	
	/**
	 * Método que modifique la componente J del Vector
	 * @param j Valor con que el que se sumple la componente J del vector
	 */
	public void setJ(double j) {
		this.j = j;
	}

	/**
	 * Método que obtiene la componente K
	 * @return la componente K
	 */
	public double getK() {
		return k;
	}
	
	/**
	 * Método que modifique la componente K de algún Vector
	 * @param k Valor con que el que se sumple la componente J del vector
	 */
	public void setK(double k) {
		this.k = k;
	}
	
	
	
	/*
	 * Método para clonar un vector
	 * @return el nuevo vector clonado
	 */
	@Override
	public Vector clone() {
		return new Vector(this.i, this.j, this.k);
	}

	/*
	 * @Método que imprime los 3 tipos de Vector
	 */
	@Override
	public String toString() {
		if(i != 0) {
			return  String.format("Vector: <%.2f i, %.2f j, %.2f k> ",i,j,k );
		}
		else {
			return "Vector Neutro: <"+ i + " i, " + j + " j, " + k + " k" + ">" ;
		}
	}
}//fin de la clase

