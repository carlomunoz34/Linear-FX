package calculations;
/**
 * NullElementException se maneja cuando no hay números en las componenentes del vector
 * @version 1.0
 */
@SuppressWarnings("serial")
public class NullElementException extends Exception{

	public NullElementException(String mensaje) {
		super(mensaje);
	}
}
