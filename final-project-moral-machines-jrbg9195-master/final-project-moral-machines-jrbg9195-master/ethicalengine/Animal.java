/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * @author John Robert Garcia, 695559, garcia1@student.unimelb.edu.au
 * This animal class is a type of persona therefore it has an age, gender, and body type
 * Attributes unique to this class are Species and isPet status
 * This is derived from the persona class and implements the toString() method
 * This class is one of the persona types that can exist in a scenario
 * This is a part of the ethicalengine package.
 */

package ethicalengine;

public class Animal extends Persona{
	
	private final String DEFAULT_SPECIES = "DOG";
	private final Boolean DEFAULT_ISPET = false;
	private final String SPACE = " ";
	
	private String species;
	private Boolean isPet;
	
	/**
	 * Creates an animal with default species, pet status and age, gender, bodytype
	 */
	public Animal() {
		super();
		setSpecies(DEFAULT_SPECIES);
		setPet(DEFAULT_ISPET);
	}
	
	/**
	 * Creates an animal of a certain specie based on input parameter
	 * @param species String which denotes the animal's specie
	 */
	public Animal(String species) {
		super();
		setSpecies(species);
		setPet(DEFAULT_ISPET);
	}
	
	/**
	 * Creates an animal of a certain specie and pet status based on input parameters
	 * @param species String which denotes the animal's specie
	 * @param isPet boolean value which denotes animal's pet status
	 */
	public Animal(String species, Boolean isPet) {
		super();
		setSpecies(species);
		setPet(isPet);
	}
	
	/**
	 * Creates an animal of a certain specie and pet status based on input parameters
	 * @param age is an int and defines the age of the animal
	 * @param gender is a Gender type and defines the gender of the animal
	 * @param bodyType is a BodyType type and defines the bodytype of the animal
	 * @param species String which denotes the animal's specie
	 * @param isPet boolean value which denotes animal's pet status
	 */
	public Animal(int age, Gender gender, BodyType bodyType, String species) {
		super(age,gender,bodyType);
		setSpecies(species);
		setPet(DEFAULT_ISPET);
	}
	
	/**
	 * Creates a new animal based on the characteristics of the input parameter otherAnimal
	 * @param otherAnimal ANimal type object which has the properties of an animal.
	 * Copies the age, gender, bodytype, specie, and pet status
	 */
	public Animal(Animal otherAnimal) {
		super(otherAnimal);
		setSpecies(otherAnimal.getSpecies());
		setPet(otherAnimal.isPet());
	}
	
	/**
	 * Sets the specie of the animal based on the input parameter
	 * @param species is a String type and defines the animal's specie
	 */
	public void setSpecies(String species) {
		this.species = species;
	}
	
	/**
	 * Sets the pet status of the animal based on the input parameter
	 * @param isPet is a boolean type and dictates whether the animal is a pet or not
	 */
	public void setPet(Boolean isPet) {
		this.isPet = isPet;
	}
	
	/**
	 * Gets the animal's species
	 * @return species which is a string value
	 */
	public String getSpecies() {
		return this.species;
	}
	
	/**
	 * Get's the animal's pet status
	 * @return isPet which is a boolean value
	 */
	public Boolean isPet() {
		return this.isPet;
	}
	
	/**
	 * This method displays the animal's characteristics when called by the print method
	 * The characteristics displayed are only limited to its specie and pet status however
	 * @return String value which shows the animal's characteristics
	 */
	public String toString() {
		
		String species = getSpecies().toString().toLowerCase();
		String ispet;
		
		if(isPet()) {
			ispet = SPACE + "is pet";
		}
		else {
			ispet = "";
		}
		
		return species + ispet;
	}
	
	/**
	 * Compares an object to the animal that invoked the method.
	 * @return boolean value which indicates whether the object being compared to the animal which
	 * invoked the method has identical characteristics.
	 */
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject == null) {
			return false;
		}
		else if(getClass() != otherObject.getClass()) {
			return false;
		}
		else {
			Animal otherAnimal = (Animal) otherObject;
			return(super.equals(otherAnimal)
			    &&(getSpecies() == otherAnimal.getSpecies())
				&&(isPet() == otherAnimal.isPet()));
		}
	}
}