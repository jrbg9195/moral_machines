package ethicalengine;

/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * This class is derived from the base Exception class.
 * It is used for checking invalid data for each line in the CSV config file
 * This checks whether human type object have fields that they shouldn't have 
 * like ispet or specie
 * For animals, it check whether they have profession, pregnant and isyou fields.
 */

public class InvalidDataFormatException extends Exception{
	
	public InvalidDataFormatException() {
		super("WARNING: invalid data format in config file.");
	}
	
	public InvalidDataFormatException(String message) {
		super(message);
	}
}