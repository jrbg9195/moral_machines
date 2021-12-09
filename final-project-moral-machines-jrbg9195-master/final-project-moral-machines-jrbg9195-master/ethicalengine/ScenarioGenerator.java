package ethicalengine;

import java.util.ArrayList;
import java.util.Random;

import ethicalengine.Human.Profession;
import ethicalengine.Persona.BodyType;
import ethicalengine.Persona.Gender;

/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * Generate various scenarios by applying pseudorandomnesss 
 * Number and characteristics of personas are randomised
 * Scenario characteristics such as user whereabouts (car/street) and legality of crossing also randomised
 * @author John Robert Garcia, 695559, garcia1@student.unimelb.edu.au
 */
public class ScenarioGenerator {
	private final int MAX = 5;
	private final int MIN = 1;
	
	private Random random;
	private int passengerCountMinimum;
	private int passengerCountMaximum;
	private int pedestrianCountMinimum;
	private int pedestrianCountMaximum;
	
	/**
	 * Empty constructor
	 * Create a scenario generator which utilises a random object with randomised seed
	 * Implicitly sets the minimum and maximum counts of passengers and pedestrians to 1 and 5
	 */
	public ScenarioGenerator() {
		random = new Random();
		setPassengerCountMin(MIN);
		setPassengerCountMax(MAX);
		setPedestrianCountMin(MIN);
		setPedestrianCountMax(MAX);
	}
	
	/**
	 * Create a scenario generator which utilises a random object with a predefined seed value
	 * Implicitly sets the minimum and maximum counts of passengers and pedestrians to 1 and 5
	 * @param seed is a long value used in the mathematical algorithm to compute the pseudo-randomness of
	 * the random object 
	 */
	public ScenarioGenerator(long seed) {
		random = new Random(seed);
		setPassengerCountMin(MIN);
		setPassengerCountMax(MAX);
		setPedestrianCountMin(MIN);
		setPedestrianCountMax(MAX);
	}
	
	/**
	 * 	/**
	 * Create a scenario generator which utilises a random object with predefined seed value
	 * Min, max counts of pedestrians and passengers are defined.
	 * @param seed is a long value used in the mathematical algorithm to compute the pseudo-randomness of
	 * the random object  
	 * @param passengerCountMinimum is the minimum number of passengers
	 * @param passengerCountMaximum is the maximum number of passengers
	 * @param pedestrianCountMinimum is the minimum number of pedestrians
	 * @param pedestrianCountMaximum is the maximum number of pedetrianss
	 */
	public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum,
			int pedestrianCountMinimum, int pedestrianCountMaximum) {
		random = new Random(seed);
		
		//max passenger count must be greater than min passenger count
		if(passengerCountMinimum > passengerCountMaximum) {
			setPassengerCountMin(MIN);
		}
		else {
			setPassengerCountMin(passengerCountMinimum) ;
		}
		setPassengerCountMax(passengerCountMaximum);
		setPedestrianCountMin(pedestrianCountMinimum);
		setPedestrianCountMax(pedestrianCountMaximum);
	}
	
	//setters
	/**
	 * Sets the minimum number of car passengers for a scenario
	 * @param min is the minimum number of passengers for the scenario
	 */
	public void setPassengerCountMin(int min) {
		this.passengerCountMinimum = min;
	}
	
	/**
	 * Sets the maximum number of car passengers for a scenario
	 * @param max is the maximum number of passengers for the scenario
	 */
	public void setPassengerCountMax(int max) {
		this.passengerCountMaximum = max;
	}
	
	/**
	 * Sets the minimum number of pedestrians for a scenario
	 * @param min is the minimum number of pedestrians for the scenario
	 */
	public void setPedestrianCountMin(int min) {
		this.pedestrianCountMinimum = min;
	}
	
	/**
	 * Sets the maximum number of pedestrians for a scenario
	 * @param max is the maximum number of pedestrians for the scenario
	 */
	public void setPedestrianCountMax(int max) {
		this.pedestrianCountMaximum = max;
	}
	
	//getters
	/**
	 * getter for max passenger count
	 * @return maximum number of passengers
	 */
	public int getPassengerCountMax() {
		return passengerCountMaximum;
	}
	
	/**
	 * getter for min passenger count
	 * @return minimum number of passengers
	 */
	public int getPassengerCountMin() {
		return passengerCountMinimum;
	}
	
	/**
	 * getter for max pedestrian count
	 * @return max pedestrian count
	 */
	public int getPedestrianCountMax() {
		return pedestrianCountMaximum;
	}
	
	/**
	 * getter for min pedestrian count
	 * @return min pedestrian count
	 */
	public int getPedestrianCountMin() {
		return pedestrianCountMinimum;
	}
	
	/**
	 * Using the random object with either a truly random or predefined seed,
	 * Create Human with random Age, BodyType, Gender, Profession, Pregnancy status
	 * @return Human with random features
	 */
	public Human getRandomHuman() {
		Human human = new Human();
		final int AGECAP = 100;
		
		//Randomise characteristics of human including age, bodytype, gender, 
		//pregnancy state and profession
		int random_age = random.nextInt(AGECAP + 1);
		human.setAge(random_age);
		
		int random_bodytype_index = random.nextInt(BodyType.values().length);
		human.setBodyType(BodyType.values()[random_bodytype_index]);
		
		int random_gender_index = random.nextInt(Gender.values().length);
		human.setGender(Gender.values()[random_gender_index]);
		
		//setPregnant() method takes care of invalid pregnancy states for non-adult females
		human.setPregnant(random.nextBoolean());
		 
		int random_profession_index = random.nextInt(Profession.values().length);
		
		human.setProfession(Profession.values()[random_profession_index]);
		
		
		return human;
	}
	
	/**
	 * Using the random object with either a truly random or predefined seed,
	 * Create an animal of random specie and pet status
	 * @return Animal with random features
	 */
	public Animal getRandomAnimal() {
		
		String[] specieList = {"dog","cat","parrot","goldfish","horse","rabbit","llama"};
		Animal animal = new Animal();
		final int AGECAP = 20;
		
		///Randomise characteristics of animal including age, bodytype, gender, specie, and pet status
		int random_age = random.nextInt(AGECAP + 1);
		animal.setAge(random_age);
		
		int random_bodytype_index = random.nextInt(BodyType.values().length);
		animal.setBodyType(BodyType.values()[random_bodytype_index]);
		
		int random_gender_index = random.nextInt(Gender.values().length);
		animal.setGender(Gender.values()[random_gender_index]);
		
		boolean random_isPet = random.nextBoolean();
		animal.setPet(random_isPet);	
		
		int random_specie_index = random.nextInt(specieList.length);
		animal.setSpecies(specieList[random_specie_index]);
		
		return animal;
	}
	
	/**
	 * Generate a scenario containing random number of passengers and pedestrians 
	 * that abides by the default or provided min/max counts
	 * Personas in the scenario are generated with random characteristics
	 * Street Crossing status and whereabouts of user (car or street) are also randomised
	 * @return scenario that is randomised
	 */
	public Scenario generate() {
		
		//randomising total passengers and pedestrians
		int totalPassengers = random.nextInt(getPassengerCountMax()-getPassengerCountMin() + 1) 
							+ getPassengerCountMin();
		
		int totalPedestrians = random.nextInt(getPedestrianCountMax()-getPedestrianCountMin() + 1) 
				+ getPedestrianCountMin();
		
		int totalHumanPedestrians = 0;
		
		//list containing passengers and pedestrians
		Persona[] passengerList = new Persona[totalPassengers];
		Persona[] pedestrianList = new Persona[totalPedestrians];
		
		//add random persona to passengers
		for(int i = 0; i < totalPassengers; i++) {
			
			//ensure that at least 1 passenger is a Human who drives the car
			if(i==0) {
				passengerList[i] = getRandomHuman();
			}
			
			//other passengers can be either human or animal
			else {
				boolean isHuman = random.nextBoolean();
				
				if(isHuman) {
					passengerList[i] = getRandomHuman(); 
				}
				else {
					passengerList[i] = getRandomAnimal();
				}
			}
		}
		
		//add random persona to pedestrians
		for(int i = 0; i < totalPedestrians; i++) {
			
			boolean isHuman = random.nextBoolean();
			
			if(isHuman) {
				pedestrianList[i] = getRandomHuman(); 
				totalHumanPedestrians ++;
			}
			else {
				pedestrianList[i] = getRandomAnimal();
			}
		}
		
		boolean youInCar;
		
		//if 0 human pedestrians, user must be in the car since
		//there will always be at least 1 human in the car who drives it
		if(totalHumanPedestrians == 0) {
			youInCar = true;
		}
		//if there are human pedestrians, user COULD be a pedestrian
		//randomise whether user is a passenger or pedestrian
		else {
			 youInCar = random.nextBoolean();
		}
		
		//user is a passenger
		if(youInCar) {
			//create a list of humans that are inside the car
			ArrayList<Human> humansInCar = new ArrayList<Human>();
			for(Persona passenger: passengerList) {
				if(passenger instanceof Human) {
					humansInCar.add((Human)passenger);
				}
			}
			//out of all human passengers, randomise who the user is
			int your_passenger_index = random.nextInt(humansInCar.size());
			humansInCar.get(your_passenger_index).setAsYou(true);
		}
		
		//user is a pedestrian
		else {
			//create list of human pedestrians
			ArrayList<Human> humanPedestrians = new ArrayList<Human>();
			for(Persona pedestrian: pedestrianList) {
				if(pedestrian instanceof Human) {
					humanPedestrians.add((Human)pedestrian);
				}
			}
			//out of all human passengers, randomise who the user is
			int your_pedestrian_index = random.nextInt(humanPedestrians.size());
			humanPedestrians.get(your_pedestrian_index).setAsYou(true);
		}
		
		//randomise legality of crossing
		boolean legalCrossing = random.nextBoolean();
		Scenario scenario = new Scenario(passengerList,pedestrianList,legalCrossing);
		
		return scenario;
	}
}

