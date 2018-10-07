package calculations;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import javafx.scene.control.TextArea;


/**
 * Funciones de Vector
 * @version 1.0 
 */

public class vectorControl extends Vector {

	/**
	 * Método para calcular la magnitud de un vector
	 * @param v1 Vector a obtener sus componentes
	 * @return raiz la magnitud del Vector
	 * @throws NullElementException  En caso que los atributos de v1 (i,j,k) sean iguales a 0
	 */
	public static double magnitud(Vector v1) throws NullElementException {
		if(v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		double raiz =  Math.sqrt((Math.pow(v1.getI(), 2) + Math.pow(v1.getJ(), 2) + Math.pow(v1.getK(), 2)));
		return raiz;
	}


	/**
	 * Método para hacer que un vector sea unitario
	 * @param v1 Vector a obtener sus componentes
	 * @return vectorAux el vector siendo unitario
	 * @throws NullElementException En caso que los atributos de v1 (i,j,k) sean iguales a 0
	 */
	public static Vector toUnitario(Vector v1) throws NullElementException {
		if(v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		Vector vectorAux = new Vector();
		vectorAux.setI(v1.getI() / vectorControl.magnitud(v1));
		vectorAux.setJ(v1.getJ() / vectorControl.magnitud(v1));
		vectorAux.setK(v1.getK() / vectorControl.magnitud(v1));
		
		return vectorAux;
	}

	/**
	 * Método para calcular la suma de dos vectores	 
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @return la suma de los dos vectores
	 */
	public static Vector sumaVector(Vector v1, Vector v2 ) {
		Vector aux = new Vector();
		aux.setI(v1.getI() + v2.getI());
		aux.setJ(v1.getJ() + v2.getJ());
		aux.setK(v1.getK() + v2.getK());
		
		return aux;
	}


	/**
	 * Método para restar dos vectores
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @return aux la resta de los vectores
	 */
	public static Vector restaVector(Vector v1, Vector v2 ) {
		Vector aux = new Vector();
		aux.setI(v1.getI() - v2.getI());
		aux.setJ(v1.getJ() - v2.getJ());
		aux.setK(v1.getK() - v2.getK());
		
		return aux;
	}


	/**
	 * Método para multiplicar un escalar por un vector
	 * @param v1 Vector a modificar sus componentes
	 * @param escalar atributo a obtener del numero
	 * @return la multiplicación del escalar por el vector
	 * @throws NullElementException En caso que los atributos de v1 (i,j,k) sean iguales a 0 
	 */
	public static Vector escalar(Vector v1, double escalar) throws NullElementException {
		if(v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		Vector vectorAux = new Vector();
		vectorAux.setI(v1.getI() * escalar);
		vectorAux.setJ(v1.getJ() * escalar);
		vectorAux.setK(v1.getK() * escalar);
		
		return vectorAux;
	}


	/**
	 * Método para multiplicar dos vectores
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @return vectorAux el resultado de la multiplicación de los dos vectores 
	 * @throws NullElementException En caso que los atributos de v1 o v2 (i,j,k) sean iguales a 0 
	 */
	public static Vector multiplicacionVector(Vector v1, Vector v2) throws NullElementException {
		if( (v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) ||  (v2.getI() == 0 && v2.getJ() == 0 && v2.getK() == 0) ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		Vector vectorAux = new Vector();
		vectorAux.setI(v1.getI() * v2.getI());
		vectorAux.setJ(v1.getJ() * v2.getJ());
		vectorAux.setK(v1.getK() * v2.getK());
		
		return vectorAux;
	}

	/**
	 * Método para realizar el producto punto entre dos vectores
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @return resultado un escalar
	 * @throws NullElementException En caso que los atributos de v1 o v2 (i,j,k) sean iguales a 0 
	 */
	public  static double productoPunto(Vector v1, Vector v2) throws NullElementException {
		if( (v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) ||  (v2.getI() == 0 && v2.getJ() == 0 && v2.getK() == 0) ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		double resultado;
		resultado = ( v1.getI() * v2.getI()) + ( v1.getJ() * v2.getJ()) + ( v1.getK() * v2.getK()) ;
		return resultado;
	}


	/**
	 *  Método para realizar el producto cruz entre dos vectores
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @return vectorAux  vector del producto cruz de los vectores v1, v2 
	 * @throws NullElementException En caso que los atributos de v1 o v2 (i,j,k) sean iguales a 0
	 */
	public static Vector productoCruz(Vector v1, Vector v2) throws NullElementException {
		if( (v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) ||  (v2.getI() == 0 && v2.getJ() == 0 && v2.getK() == 0) ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		Vector vectorAux = new Vector();
		vectorAux.setI(( v1.getJ()* v2.getK() - v2.getJ()* v1.getK()) );
		vectorAux.setJ(( v1.getI()* v2.getK() - v1.getK()* v2.getI()) *(-1));
		vectorAux.setK(( v1.getI()* v2.getJ() - v1.getJ()* v2.getI()) );
		
		return vectorAux;
	}


	/**
	 * Método para calcular el angulo entre dos vectores
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @return  aux el angulo entre los vectores v1, v2
	 * @throws NullElementException En caso que los atributos de v1 o v2 (i,j,k) sean iguales a 0 
	 */
	public static double anguloEntreVectores(Vector v1, Vector v2) throws NullElementException {
		double angulo = 0, resultado = 0, aux;
		if( (v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) ||  (v2.getI() == 0 && v2.getJ() == 0 && v2.getK() == 0) ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		angulo = vectorControl.productoPunto(v1, v2) / ( Math.abs(vectorControl.magnitud(v1)) * Math.abs(vectorControl.magnitud(v2)) ); 
		resultado = Math.acos(angulo);
		aux = Math.toDegrees(resultado);
		return aux;
	}


	/**
	 * Método para calcular la proyección de dos vectores
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @return vectorAux la proyección de los vectores v1,v2
	 * @throws NullElementException En caso que los atributos de v1 o v2 (i,j,k) sean iguales a 0
	 */ 
	public static Vector proyeccion(Vector v1, Vector v2) throws NullElementException {
		if( (v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) ||  (v2.getI() == 0 && v2.getJ() == 0 && v2.getK() == 0) ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		Vector vectorAux = new Vector();
		double producPunt, magnitud, resultadoDeDiv;
		producPunt = vectorControl.productoPunto(v1, v2);
		magnitud = Math.abs(vectorControl.magnitud(v2));
		resultadoDeDiv = producPunt / (Math.pow(magnitud, 2));
		vectorAux.setI(v2.getI()*resultadoDeDiv);
		vectorAux.setJ(v2.getJ()*resultadoDeDiv);
		vectorAux.setK(v2.getK()*resultadoDeDiv);
		
		return vectorAux;
	}

	/**
	 * Método para obtener el primer Vector del proceso de Gram-Schmidt
	 * @param v1 Vector a obtener sus componentes
	 * @return vectorAux el resultado del proceso de Graham
	 * @throws NullElementException En caso que los atributos de v1 (i,j,k) sean iguales a 0
	 */ 
	private static Vector gramSchObtenerU1(Vector v1) throws NullElementException {
		if(v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0 ) {
			throw new NullElementException("The vector must have numbers in the components");
		}
		Vector vectorAux = new Vector();
		double magnitud;
		magnitud = vectorControl.magnitud(v1);
		vectorAux.setI(v1.getK() / magnitud);
		vectorAux.setJ(v1.getJ() / magnitud);
		vectorAux.setK(v1.getI() / magnitud);
	
		return vectorAux;
	}


	/**
	 * Método para obtener el segundo Vector del proceso de Gram-Schmidt
	 * @param v2 Vector a obtener sus componentes
	 * @param u1 Vector a obtener sus componentes
	 * @return vectorAux3 el resultado del proceso V  de Graham
	 * @throws NullElementException En caso que los atributos de v1 o v2 (i,j,k) sean iguales a 0 
	 */
	@SuppressWarnings("unused")
	private static Vector gramSchObtenerV(Vector v2, Vector u1 ) throws NullElementException  {
		if( (v2.getI() == 0 && v2.getJ() == 0 && v2.getK() == 0 ) ||  (u1.getI() == 0 && u1.getJ() == 0 && u1.getK() == 0) ) {
			throw new NullElementException("The vector to have numbers in the components");
		}
		//Vector para utilizar lo componentes del primer proceso
		Vector graham = new Vector();
		//Vectores auxiliares
		Vector vectorAux = new Vector();
		Vector vectorAux2 = new Vector();
		Vector vectorAux3 = new Vector();
		double productoPunt, magnitud;
		//Asignamos a este vector el proceso
		graham=  gramSchObtenerU1(u1);
		productoPunt = productoPunto(v2,graham);

		vectorAux.setI(productoPunt * graham.getI());
		vectorAux.setJ(productoPunt * graham.getJ());
		vectorAux.setK(productoPunt * graham.getK());

		vectorAux2 = restaVector(v2, vectorAux);

		magnitud = vectorControl.magnitud(vectorAux2);
		vectorAux3.setK(vectorAux2.getI() / magnitud);
		vectorAux3.setJ(vectorAux2.getJ() / magnitud);
		vectorAux3.setI(vectorAux2.getK() / magnitud);

		return vectorAux3;
	}


	/**
	 * Método del proceso Vectorial de Gram-Schmidt
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @return ListAux que contiene los vectores ordenados con sus procesos
	 * @throws NullElementException En caso que los atributos de v1 o v2 (i,j,k) sean iguales a 0
	 */
	public static LinkedList<Vector> listGramSchmidtproccess(Vector v1, Vector v2) throws NullElementException{
		if( (v2.getI() == 0 && v2.getJ() == 0 && v2.getK() == 0 ) ||  (v1.getI() == 0 && v1.getJ() == 0 && v1.getK() == 0) ) {
			throw new NullElementException("The vector to have numbers in the components");
		}
		LinkedList<Vector> listAux = new LinkedList<>();
		Vector vecAux = vectorControl.gramSchObtenerU1(v1);
		Vector vecAux2 = vectorControl.gramSchObtenerV(v2, vecAux);	
		listAux.add(vecAux);
		listAux.add(vecAux2);
		return listAux;
	}	

	/**
	 * Método para calcular el área de un paralelogramo formado por los vectores
	 * @param v1 Vector a obtener sus componentes
	 * @param v2 Vector a obtener sus componentes
	 * @param v3 Vector a obtener sus componentes
	 * @return resultado, el area del paralegramo
	 * @throws NullElementException En caso que los atributos de v1 o v2 (i,j,k) sean iguales a 0
	 */
	public static double areaParalelogramo(Vector v1, Vector v2, Vector v3) throws NullElementException{
		Vector vectorAux = new Vector();
		Vector vectorAux2 = new Vector();
		Vector vectorAux3 = new Vector();
		double resultado = 0; 
		vectorAux = restaVector(v2, v1);
		vectorAux2 = restaVector(v3, v2);
		vectorAux3 = productoCruz(vectorAux, vectorAux2);
		resultado = vectorControl.magnitud(vectorAux3);
		return resultado;
	}

	/**
	 * Método para indicar si un vector es igual a otro
	 * @param v1 Vector a obtener sus componentes
	 * @return verdadero o falso según sea la comparación
	 */
	public boolean equals (Vector v1) {
		if(v1 instanceof Vector) {
			Vector aux = (Vector) v1;
			return this.getI() == aux.getI() && this.getJ() == aux.getJ() && this.getK() == aux.getK();
		}
		return false;
	}
	
	/**Method that adds a new vector to the history in order to be operated.*/
	public static boolean addVector(LinkedList<Vector> history,TextArea area){
		try{
			try {
				double i=Double.parseDouble(JOptionPane.showInputDialog("Ingrese el componente i del vector."));
				double j=Double.parseDouble(JOptionPane.showInputDialog("Ingrese el componente j del vector."));
				double k=Double.parseDouble(JOptionPane.showInputDialog("Ingrese el componente k del vector."));
				Vector v=new Vector(i, j, k);
				history.add(v);
				area.setText(area.getText()+"\n"+v.ID+":\n"+v.toString());
			}catch(NullPointerException e) {
				return false;
			}
		}catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}

	/** Generate matrix with random elements.*/
	public static boolean random (LinkedList<Vector> history) {
		try{
			try {
				
				double i = Math.random()*10;
				double j = Math.random()*10;
				double k = Math.random()*10;
				Vector v = new Vector(i,j,k);
				history.add(v);
			}catch(NullPointerException e) {
				return false;
			}
		}catch(NumberFormatException ex) {
			return false;
		}return true;
	}
	
	/**Method that fills a text area with all available vector registers throughout the program execution.
	 * @param area The text area where the user will see the vectors.*/
	public static void updateHistory(LinkedList<Vector> history,TextArea area) {
		area.setText("");
		for(Vector v: history) {
			area.setText(area.getText()+"\n"+v.ID+":\n"+v.toString());
		}
	}


}

