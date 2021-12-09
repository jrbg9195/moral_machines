package ethicalengine;

import java.util.*;
/**
 * COMP90041, Sem2, 2020: Final Project
 * Represents a scenario to decide on
 * @author: John Robert Garcia, 695559, garcia1@student.unimelb.edu.au
 * A scenario would contain a list of the passengers and pedestrians
 * It would indicate whether the user is a passenger or pedestrian
 * It would indicate whether pedestrians are crossing legally
 */
public class Scenario {
	
	private final Persona[] DEFAULT_PASSENGERS = {new Human(),new Animal()}; 
	private final Persona[] DEFAULT_PEDESTRIANS = {new Human(),new Animal()}; 
	private final boolean DEFAULT_CROSSING = false;
	private final String NEWLINE = "\n";
	
	private ArrayList<Persona> passengers = new ArrayList<Persona>();
	private ArrayList<Persona> pedestrians = new ArrayList<Persona>();
	private boolean isLegalCrossing;
	
	/**
	 * Creates a scenario with using a default passengers and pedestrians array and a default
	 * legal crossing value
	 */    
	public Scenario() {
		for(Persona passenger: DEFAULT_PASSENGERS) {
			passengers.add(passenger);
		}
		for(Persona pedestrian: DEFAULT_PEDESTRIANS) {
			pedestrians.add(pedestrian);
		}
		setLegalCrossing(DEFAULT_CROSSING);
	}
	
	/**
	 * Creates a scenario using the input parameters provided
	 * @param passengers is an array of Persona[] objects that have charateristics who are
	 * the passengers in the scenario
	 * @param pedestrians is an array of Persona[] objects that have charateristics who are
	 * the pedestrians in the scenario
	 * @param isLegalCrossing is a boolean value which dictates whether the light is green (true)
	 * or red (false)
	 */    
	public Scenario(Persona[] passengers, Persona[] pedestrians, boolean isLegalCrossing) {
		for(Persona passenger: passengers) {
			this.passengers.add(passenger);
		}
		for(Persona pedestrian: pedestrians) {
			this.pedestrians.add(pedestrian);
		}
		setLegalCrossing(isLegalCrossing);
	}
	
	/**
	 * Gets the passengers of a scenario
	 * @return Arraylist of Persona objects which are the passengers in the scenario
	 */
	public ArrayList<Persona> getPassengers() {
		return new ArrayList<Persona>(passengers);
	}
	
	/**
	 * Gets the pedestrians of a scenario
	 * @return Arraylist of Persona objects which are the pedestrians in the scenario
	 */
	public ArrayList<Persona> getPedestrians() {
		return new ArrayList<Persona>(pedestrians);
	}
	
	/**
	 * Get the number of passengers in the scenario
	 * @return int number of passenger personas in the scenario
	 */
	public int getPassengerCount() {
		return passengers.size();
	}
	
	/**
	 * Get the number of pedestrians in the scenario
	 * @return int number of pedestrian personas in the scenario
	 */
	public int getPedestrianCount() {
		return pedestrians.size();
	}
	
	/**
	 * Get the legality of the street crossing
	 * @return boolean value which dictates the legality of the street crossing
	 */
	public boolean isLegalCrossing() {
		return isLegalCrossing;
	}
	
	/**
	 * Sets the legality of the street crossing based on the input
	 * @param isLegalCrossing is a boolean value which dictates the legality of the street crossing
	 */
	public void setLegalCrossing(boolean isLegalCrossing) {
		this.isLegalCrossing = isLegalCrossing;
	}
	
	/**
	 * Check whether the user is a passenger
	 * @return boolean value which dictates whether the user is in the car
	 */
	public boolean hasYouInCar() {
		for(Persona passenger: passengers) {
			//passenger is Human
			if(passenger instanceof Human) {
				return (((Human)passenger).isYou());
			}
		}
		return false;
	}
	
	/**
	 * Check whether the user is a pedestrian
	 * @return boolean value which dictates whether the user is in the street
	 */
	public boolean hasYouInLane() {
		for(Persona pedestrian: pedestrians) {
			//passenger is Human
			if(pedestrian instanceof Human) {
				return (((Human)pedestrian).isYou());
			}
		}
		return false;
	}
	
	/**
	 * Displays all the passenger and pedestrian personas and their characteristics
	 * as well as the crossing legality when called by the print method
	 * @return String value which shows the scenario's legality and the participants characteristics
	 */
	@Override
	public String toString() {
		
		String equals = "======================================";
		String header = equals + NEWLINE + "# Scenario" + NEWLINE + equals + NEWLINE;
		String crossing = "Legal Crossing: " + (isLegalCrossing()? "yes\n":"no\n");
		String pass = "Passengers " + "(" + getPassengerCount() + ")";
		String pedest = NEWLINE + "Pedestrians " + "(" + getPedestrianCount() + ")";
		
		for (Persona passenger: passengers) {
			pass = pass + NEWLINE + "- " + passenger.toString();
		}
		
		for(Persona pedestrian: pedestrians) {
			pedest = pedest + NEWLINE + "- " + pedestrian.toString();
		}
		
		return(header + crossing + pass + pedest);
	}
	
	/**
	 * Compare an object to the scenario that invoked the method
	 * @return boolean dictates whether the object and the scenario that invoked the method are identical
	 */
	public boolean equals(Object otherObject) {
		if(otherObject == null) {
			return false;
		}
		else if(getClass() != otherObject.getClass()) {
			return false;
		}
		else {
			Scenario otherScenario = (Scenario) otherObject;
			int truecount = 0;
			
			for(Persona passenger:getPassengers()) {
				for(Persona otherPassenger:otherScenario.getPassengers()) {
					if(passenger.equals(otherPassenger))
						truecount += 1;
				}
			}
			
			for(Persona pedestrian:getPedestrians()) {
				for(Persona otherPedestrian:otherScenario.getPedestrians()) {
					if(pedestrian.equals(otherPedestrian))
						truecount += 1;
				}
			}
			
			return(isLegalCrossing() == otherScenario.isLegalCrossing()
				&& truecount == (getPassengerCount() + getPedestrianCount())
				&& (getPassengerCount() + getPedestrianCount()) 
				== (otherScenario.getPassengerCount() + otherScenario.getPedestrianCount()));
		}
	}
}