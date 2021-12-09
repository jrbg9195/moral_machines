/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * @author John Robert Garcia, 695559, garcia1@student.unimelb.edu.au
 * This human class is a type of persona therefore it has an age, gender, and body type
 * Attributes unique to this class are Profession, Pregnancy status and is You status
 * This is derived from the persona class and implements the toString() method
 * This class is one of the persona types that can exist in a scenario
 * This is a part of the ethicalengine package
 */
package ethicalengine;

public class Human extends Persona {

	public enum Profession implements EnumStructureforAudit{
		NONE(0,0),
		DOCTOR(0,0),
		CEO(0,0),
		ENGINEER(0,0),
		POLICE(0,0),
		FIREFIGHTER(0,0),
		CRIMINAL(0,0),
		HOMELESS(0,0),
		UNEMPLOYED(0,0);
		
		private int[] value = new int[2];
		private final int SURVIVOR = 0;
		private final int TOTAL = 1;
		
		private Profession(int survivor, int total) {
			value[SURVIVOR] = survivor;
			value[TOTAL] = total;
		}
		
		public int getSurvivor() {
			return value[SURVIVOR];
		}
		
		public int getTotal() {
			return value[TOTAL];
		}
		
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

	public enum AgeCategory implements EnumStructureforAudit{
		BABY(0,0),
		CHILD(0,0),
		ADULT(0,0),
		SENIOR(0,0);
		
		private int[] value = new int[2];
		private final int SURVIVOR = 0;
		private final int TOTAL = 1;
		
		private AgeCategory(int survivor, int total) {
			value[SURVIVOR] = survivor;
			value[TOTAL] = total;
		}
		
		public int getSurvivor() {
			return value[SURVIVOR];
		}
		
		public int getTotal() {
			return value[TOTAL];
		}
		
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

	private final Profession DEFAULT_PROFESSION = Profession.NONE;
	private final boolean DEFAULT_PREGNANT = false;
	public final String NEWLINE = "\n";
	public final String SPACE = " ";

	private Profession profession;
	private boolean isPregnant;
	private boolean isYou;
	
	/**
	 * Creates a human with default profession and pregnancy status
	 */
	public Human() {
		super();
		setProfession(DEFAULT_PROFESSION);
		setPregnant(DEFAULT_PREGNANT);
	}
	
	/**
	 * Creates human with given parameters
	 * @param age is an int and defines the age of the human
	 * @param gender is a Gender type and defines the gender of the human
	 * @param bodyType is a BodyType type and defines the bodytype of the human
	 */
	public Human(int age, Gender gender, BodyType bodyType) {
		super(age,gender,bodyType);	
		setProfession(DEFAULT_PROFESSION);
		setPregnant(DEFAULT_PREGNANT);
	}
	
	/**
	 * Creates a human with the given parameters
	 * @param age is an int and defines the age of the human
	 * @param gender is a Gender type and defines the gender of the human
	 * @param bodyType is a BodyType type and defines the bodytype of the human
	 * @param profession is a Profession type and defines the profession of the human
	 * @param isPregnant is a boolean and defines the pregnancy status of the human
	 */
	public Human(int age, Profession profession, Gender gender, BodyType bodyType, boolean isPregnant) {
		super(age,gender,bodyType);
		setProfession(profession);
		setPregnant(isPregnant);
	}
	
	/**
	 * Creates a new human based on the characteristics of the input parameter otherHuman
	 * @param otherHuman Human type object which has the properties of a human.
	 * Copies the age, gender, bodytype, profession, and pregnancy status
	 */
	public Human(Human otherHuman) {
		super(otherHuman);
		setProfession(otherHuman.getProfession());
		setPregnant(otherHuman.isPregnant());
	}
	
	/**
	 * Sets the profession of the human based on the input parameter
	 * @param profession is a Profession type and defines the human's profession
	 * Only adults can have profession, otherwise a default profession of NONE is assigned 
	 */
	public void setProfession(Profession profession) {
		if(getAgeCategory() == AgeCategory.ADULT) {
			this.profession = profession;
		}
		else {
			this.profession = DEFAULT_PROFESSION;
		}
	}
	
	/**
	 * Set Pregnancy status of Human
	 * Prevents non-female and humans from being pregnant
	 * @param isPregnant is boolean and sets the pregnancy status of Human given that it is a valid case
	 */
	public void setPregnant(boolean isPregnant) {
		
		//can't be pregnant unless female, always false
		if(getGender() != Gender.FEMALE) {
			this.isPregnant = false;
		}
		//if human is a female, she can be pregnant
		else {
			this.isPregnant = isPregnant;
		}
	}
	
	/**
	 * Sets the is You status of the human based on input parameter.
	 * @param isYou is a boolean value which dictates if the human is the user.
	 */
	public void setAsYou(boolean isYou) {
		this.isYou = isYou;
	}
	
	/**
	 * Gets the pregnancy status of the human
	 * @return isPregnant boolean pregnancy status of the human
	 */
	public boolean isPregnant() {
		return this.isPregnant;
	}
	
	/**
	 * Gets the profession of the human
	 * @return profession which is a Profession type enum value 
	 */
	public Profession getProfession() {
		if(getAgeCategory().equals(AgeCategory.ADULT) ) {
			return this.profession;
		}
		else {
			return Profession.NONE;
		}
	}
	
	/**
	 * Gets the is You status of the human
	 * @return isYou boolean. if true, human is the user otherwise no
	 */
	public boolean isYou() {
		return this.isYou;
	}
	
	/**
	 * Get the human's age category based on his age
	 * @return AgeCategory enum type
	 */
	public AgeCategory getAgeCategory() {
		//age is always>0 since the setAge() method in persona forces negative age parameters 
		//to a default value of 20
		if(getAge()>=0 && getAge()<=4) {
			return AgeCategory.BABY;
		}
		else if(getAge()>=5 && getAge()<=16) {
			return AgeCategory.CHILD;
		}
		else if(getAge()>=17 && getAge()<=68) {
			return AgeCategory.ADULT;
		}
		else {
			return AgeCategory.SENIOR;
		}
	}

	/**
	 * This method displays the human's characteristics when called by the print method
	 * @return String value which shows the human's characteristics
	 */
	public String toString() {

		String profession;
		String you;
		String pregnant;
		String gender = SPACE + getGender().toString().toLowerCase();
		String bodytype = getBodyType().toString().toLowerCase();
		String agecategory = SPACE + getAgeCategory().toString().toLowerCase();
		
		if(getAgeCategory() == AgeCategory.ADULT) {
			profession = SPACE + getProfession().toString().toLowerCase();
		}
		else {
			profession = "";
		}
		
		if(isPregnant()) {
			pregnant = SPACE + "pregnant";
		}
		else {
			pregnant = "";
		}
		
		if(isYou()) {
			you = "you ";
		}
		else {
			you = "";
		}
		
		return (you + bodytype + agecategory + profession + gender + pregnant); 
	}
	
	/**
	 * Compares an object to the human that invoked the method.
	 * @return boolean value which indicates whether the object being compared to the human which
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
			Human otherHuman = (Human) otherObject;
			return(super.equals(otherHuman)
			    &&(getProfession() == otherHuman.getProfession())
			    &&(isPregnant() == otherHuman.isPregnant()));
		}
	}
}