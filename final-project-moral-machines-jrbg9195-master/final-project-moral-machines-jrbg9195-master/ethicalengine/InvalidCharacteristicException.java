package ethicalengine;
/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * This class is derived from the base Exception class.
 * It is used for checking invalid characteristics of the personas in the CSV config file
 * which are enum values such as profession, bodytype, and gender
 */
public class InvalidCharacteristicException extends Exception{
	
	public InvalidCharacteristicException() {
		super("WARNING: invalid data format in config file.");
	}
	
	public InvalidCharacteristicException(String message) {
		super(message);
	}
}