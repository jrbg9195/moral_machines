/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * @author John Robert Garcia, 695559, garcia1@student.unimelb.edu.au
 * This is an abstract class from which all persona types inherit.
 * It has attributes such as Gender, BodyType and Age where the latter 2 are of enum type
 * This is the base class for Human and Animal class where the abstract method toString() must be
 * implemented.
 * This is a part of the ethicalengine package
 */

package ethicalengine;

public abstract class Persona {
	
	
	/**
	 * @author John Robert Garcia, 695559, garcia1@student.unimelb.edu.au
	 * This enum class contains the gender as well as values that represent the survivor and total count
	 * for that specific characteristic.
	 * This structure was used specifically for the audit part.
	 */
	public enum Gender implements EnumStructureforAudit {
		UNKNOWN(0,0),
		MALE(0,0),
		FEMALE(0,0);
		
		private int[] value = new int[2];
		private final int SURVIVOR = 0;
		private final int TOTAL = 1;
		
		//constructor
		private Gender(int survivor, int total) {
			value[SURVIVOR] = survivor;
			value[TOTAL] = total;
		}
		
		//getters
		public int getSurvivor() {
			return value[SURVIVOR];
		}
		
		public int getTotal() {
			return value[TOTAL];
		}
		
		//setters
		public void setSurvivor(int survivor) {
			value[SURVIVOR] = survivor;
		}
		
		public void setTotal(int total) {
			value[TOTAL] = total;
		}
		
		//update survivor
		public void addSurvivor() {
			setSurvivor(getSurvivor()+1);
		}
		
		//update total
		public void addTotal() {
			setTotal(getTotal()+1);
		}
	}
	
	/**
	 * @author John Robert Garcia, 695559, garcia1@student.unimelb.edu.au
	 * This enum class contains the body type as well as values that represent the survivor and total count
	 * for that specific characteristic.
	 * This structure was used specifically for the audit part.
	 */
	public enum BodyType implements EnumStructureforAudit{
		UNSPECIFIED(0,0),
		AVERAGE(0,0),
		ATHLETIC(0,0),
		OVERWEIGHT(0,0);
		
		private int[] value = new int[2];
		private final int SURVIVOR = 0;
		private final int TOTAL = 1;
		
		//constructor
		private BodyType(int survivor, int total) {
			value[SURVIVOR] = survivor;
			value[TOTAL] = total;
		}
		
		//getters
		public int getSurvivor() {
			return value[SURVIVOR];
		}
		
		public int getTotal() {
			return value[TOTAL];
		}
		
		//setters
		public void setSurvivor(int survivor) {
			value[SURVIVOR] = survivor;
		}
		
		public void setTotal(int total) {
			value[TOTAL] = total;
		}
		
		public void addSurvivor() {
			setSurvivor(getSurvivor()+1);
		}
		
		public void addTotal() {
			setTotal(getTotal()+1);
		}
	}
	
	private final int DEFAULT_AGE = 20;
	private final Gender DEFAULT_GENDER = Gender.UNKNOWN;
	private final BodyType DEFAULT_BODYTYPE = BodyType.UNSPECIFIED;
	
	private int age;
	private Gender gender;
	private BodyType bodyType;

	/**
	 * Creates a persona using the default values for age, gender, and bodytype
	 * However, since the persona class is abstract, this method cannot be invoked.
	 */
	public Persona() {
		setAge(DEFAULT_AGE);
		setGender(DEFAULT_GENDER);
		setBodyType(DEFAULT_BODYTYPE);
	}
	
	/**
	 * Creates a persona using the age, gender, and bodytype values inputted.
	 * @param age is the age of the persona. int type
	 * @param gender is the gender of the persona. Gender type
	 * @param bodyType is the bodytype of the persona. BodyType type
	 * Since persona class is abstract, this method cannot be invoked.
	 */
	public Persona(int age, Gender gender, BodyType bodyType) {
		setAge(age);
		setGender(gender);
		setBodyType(bodyType);
	}
	
	//copy constructor
	//question for copy constructor, is it better to use null pointer exception when copying null object
	//or is if-else sufficient
	/**
	 * Copy constructor. This creates a new persona with the same characteristics as the persona copied.
	 * @param otherPersona the persona to be copied. Persona type
	 * This method cannot be invoked since persona class is abstract.
	 */
	public Persona(Persona otherPersona) {
    	if(otherPersona == null) {
    		System.out.println("Error, cannot copy null Persona");
    		System.exit(0);
    	}
    	else {
    		setAge(otherPersona.getAge());
    		setGender(otherPersona.getGender());
    		setBodyType(otherPersona.getBodyType());
    	}
	}
	
	
	
	/**
	 * Sets the age of the persona to the corresponding input.
	 * @param age is an int input to which the persona's age is updated to
	 * If the age input is negative, the persona's age is set to a default value
	 */
	public void setAge (int age) {
		if(age>0) {
			this.age = age;
		}
		else {
			this.age = DEFAULT_AGE;
		}
	}
	
	/**
	 * Sets the gender of the persona to the corresponding input.
	 * @param gender is an Gender type input to which the persona's Gender is updated to
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	/**
	 * Sets the body type of the persona to the corresponding input.
	 * @param bodytype is a BodyType type input to which the persona's BodyType is updated to
	 */
	public void setBodyType(BodyType bodyType) {//throws InvalidCharacteristicException {
		this.bodyType = bodyType;
	}
	
	/**
	 * Gets the persona's age.
	 * @return age which is an int.
	 */
	public int getAge() {
		return this.age;
	}
	
	/**
	 * Gets the persona's gender.
	 * @return gender which is of Gender type.
	 */
	public Gender getGender() {
		return this.gender;
	}
	
	/**
	 * Gets the persona's bodyType.
	 * @return bodyType which is of BodyType type.
	 */
	public BodyType getBodyType() {
		return this.bodyType;
	}
	
	/**
	 * This will be implemented in the derived class
	 */
	public abstract String toString(); 
	
	/**
	 * Compares an object to the persona that invoked the method.
	 * @return boolean value which indicates whether the object being compared to the persona which
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
			Persona persona = (Persona) otherObject;
			return((getAge() == persona.getAge())
				 &&(getGender() == persona.getGender())
		    	 &&(getBodyType() == persona.getBodyType()));
		}
	}
}