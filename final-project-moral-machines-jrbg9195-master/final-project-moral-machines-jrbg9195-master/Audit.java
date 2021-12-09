import ethicalengine.*;
import ethicalengine.Human.AgeCategory;
import ethicalengine.Human.Profession;
import ethicalengine.Persona.BodyType;
import ethicalengine.Persona.Gender;

import java.util.*;

import java.io.*;

/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * Summarizes the results for each characteristic based on the statistic of projected survival
 * @author: garcia1@student.unimelb.edu.au, John Robert Garcia, 695559
 */
public class Audit {
	
	private final String NEWLINE = "\n";
	private final int SURVIVORS = 0;
	private final int TOTAL = 1;
	private final String DEFAULT_AUDIT_TYPE = "Unspecified";
	
	private int N;
	private String AuditType;
	private ScenarioGenerator scenariogenerator;
	
	public int[] pedestrianStat = new int[2];
	private int[] passengerStat = new int[2];
	
	private int[] humanTypeStat = new int[2];
	private int[] animalTypeStat = new int[2];
	
	private int[] redlightStat = new int[2];
	private int[] greenlightStat = new int[2];
	
	private int[] pregnancyStat = new int[2];
	private int[] youStat = new int[2];
	private int[] petStat = new int[2];
	private int[] age = new int[2];
	
	private GenericPurpose<String, Integer> specie;
	private String specieName;
	private ArrayList<GenericPurpose<String,Integer>> specielist = 
													new ArrayList<GenericPurpose<String,Integer>>();
	
	public ArrayList<Scenario> scenariolist = new ArrayList<Scenario>();
	
	/**
	 * Creates an audit with an empty scenario list and sets the audit type to default
	 */
	public Audit() {
		setAuditType(DEFAULT_AUDIT_TYPE);
	}
	
	/**
	 * Creates an audit with scenarios based on the input parameter
	 * @param scenarios is an array of scenarios to be audited
	 */
	public Audit(Scenario[] scenarios) {
		setScenario(scenarios);
		this.scenariolist = new ArrayList<Scenario>(Arrays.asList(scenarios));
		setAuditType(DEFAULT_AUDIT_TYPE);
	}
	
	/**
	 * Runs an audit for each scenario that is imported from a csv file
	 * Updates and prints statistic to console-line
	 * This uses the audit constructor Audit(Scenario[] scenarios) so the array of scenarios 
	 * imported from the csv file must be inputed
	 */
	public void run() {
		this.N = scenariolist.size();
		
		for(Scenario scenario:scenariolist) {
			EthicalEngine.Decision decision = EthicalEngine.decide(scenario);
			updateAllStatistic(decision, scenario);
		}
	}
 	/**
	 * Create N number of scenarios and runs them through the EthicalEngine's decide method.
	 * Each scenario's outcome is added to the statistic
	 * @param runs is number of scenarios that is run for the audit
	 * This uses the empty constructor Audit() as the scenarios are generated randomly
	 */
	public void run(int runs) {
		this.N = this.N + runs;
		
		//for each run
		for(int i = 0; i < runs; i++) {
			
			//create and generate a random scenario
			scenariogenerator = new ScenarioGenerator();
			Scenario random_scenario = scenariogenerator.generate();
			
			//use decision method to decide who survives
			EthicalEngine.Decision decision = EthicalEngine.decide(random_scenario);
			
			updateAllStatistic(decision, random_scenario);
		}
	}
	
	/**
	 * Set the name of audit type
	 * @param name is a string which is used to set the name of audit type
	 */
	public void setAuditType(String name) {
		this.AuditType = name;
	}
	
	/**
	 * Return name of audit with default name "Unspecified"
	 * @return String, name of audit
	 */
	public String getAuditType() {
		return this.AuditType;
	}
	
	/**
	 * Print summary returned by toString() to the command-line.
	 */
	public void printStatistic() {
		System.out.println(toString());
	}
	
	//TODO: need to sort in descending order, numerical then alphabetical,	
	/**
	 * Returns summary of simulation, includes the characteristics available in the simulation and
	 * the survival rate for that specific characteristic
	 * sorted in descending order numerically, then alphabetically
	 */
	@Override
	public String toString() {
		
		//from config file, N is not update in run() since number of lines is not known
		//until the whole csv file is converted to a scenario array
		
		if(N == 0 && scenariolist.size() == 0) {
			return "No Audit Available";
		}
		
		else {
			String equals = "======================================";
			String header = equals + NEWLINE + "# " + getAuditType() + " Audit" + NEWLINE + equals +
					NEWLINE  + "- % SAVED AFTER " + N + " RUNS" + NEWLINE;
			
			String content = "";
			
			if(pedestrianStat[TOTAL]!=0) {
				content += String.format("pedestrians: %.2f",
							survivalPercentage(pedestrianStat[SURVIVORS],pedestrianStat[TOTAL])) + NEWLINE;
			}
			
			if(passengerStat[TOTAL]!=0) {
				content += String.format("passengers: %.2f",
						survivalPercentage(passengerStat[SURVIVORS],passengerStat[TOTAL])) + NEWLINE;
			}
			
			if(humanTypeStat[TOTAL]!=0) {
				content += String.format("human: %.2f", 
						survivalPercentage(humanTypeStat[SURVIVORS],humanTypeStat[TOTAL])) + NEWLINE;
			}
			
			if(animalTypeStat[TOTAL]!=0) {
				content += String.format("animal: %.2f", 
						survivalPercentage(animalTypeStat[SURVIVORS],animalTypeStat[TOTAL])) + NEWLINE;
			}
			
			if(redlightStat[TOTAL]!=0) {
				content += String.format("red: %.2f", 
						survivalPercentage(redlightStat[SURVIVORS],redlightStat[TOTAL])) + NEWLINE;
			}
			
			if(greenlightStat[TOTAL]!=0) {
				content += String.format("green: %.2f", 
						survivalPercentage(greenlightStat[SURVIVORS],greenlightStat[TOTAL])) + NEWLINE;
			}
			
			if(pregnancyStat[TOTAL]!=0) {
				content += String.format("pregnant: %.2f", 
						survivalPercentage(pregnancyStat[SURVIVORS],pregnancyStat[TOTAL])) + NEWLINE;
			}
			
			if(youStat[TOTAL]!=0) {
				content += String.format("you: %.2f", 
						survivalPercentage(youStat[SURVIVORS],youStat[TOTAL])) + NEWLINE;
			}
			
			if(petStat[TOTAL]!=0) {
				content += String.format("pet: %.2f", 
						survivalPercentage(petStat[SURVIVORS],petStat[TOTAL])) + NEWLINE;
			}	

			for(int i = 0; i<BodyType.values().length; i++) {
				if(BodyType.values()[i].getTotal()>0 && BodyType.values()[i]!=BodyType.UNSPECIFIED) {
					content += String.format("%s: %.2f", BodyType.values()[i].toString().toLowerCase(), 
					survivalPercentage(BodyType.values()[i].getSurvivor(),BodyType.values()[i].getTotal()))
					+ NEWLINE;
				}
			}
			
			for(int i = 0; i< Gender.values().length; i++) {
				if(Gender.values()[i].getTotal()>0 && Gender.values()[i]!=Gender.UNKNOWN) {
					content += String.format("%s: %.2f", Gender.values()[i].toString().toLowerCase(), 
					survivalPercentage(Gender.values()[i].getSurvivor(),Gender.values()[i].getTotal()))
					+ NEWLINE;
				}
			}
			
			for(int i = 0; i< Profession.values().length; i++) {
				if(Profession.values()[i].getTotal()>0 && Profession.values()[i]!=Profession.NONE) {
					content += String.format("%s: %.2f", Profession.values()[i].toString().toLowerCase(), 
					survivalPercentage(Profession.values()[i].getSurvivor(),
					Profession.values()[i].getTotal())) + NEWLINE;
				}
			}
			
			for(int i = 0; i< AgeCategory.values().length; i++) {
				if(AgeCategory.values()[i].getTotal()>0) {
					content += String.format("%s: %.2f", AgeCategory.values()[i].toString().toLowerCase(), 
					survivalPercentage(AgeCategory.values()[i].getSurvivor(),
					AgeCategory.values()[i].getTotal())) + NEWLINE;
				}
			}
			
			for(int i = 0; i< specielist.size(); i++) {
				if(specielist.get(i).getValue()[TOTAL]>0) {
					content += String.format("%s: %.2f", specielist.get(i).getKey(),
							survivalPercentage(specielist.get(i).getValue()[SURVIVORS],
											   specielist.get(i).getValue()[TOTAL])) + NEWLINE;
				}
			}
			
			content += "--" + NEWLINE + String.format("average age: %.2f", 
						survivalPercentage(age[SURVIVORS],age[TOTAL]));
			
			return header + content;
		}
	}
	
	/**
	 * Save the results of an audit to a file.
	 * If file already exists, append the results.
	 * If file doesn't exist, it will be created.
	 * If directory specified by filepath variable does not exist, an error message is displayed.
	 * @param filepath is a String value that includes the target directory and filename
	 */
	public void printToFile(String filepath) {
		//filepath = directory + file name
		//ie. 'logs/results.log' = logs/ + results.log
		final boolean append = true; 
		PrintWriter outputStreamName = null;
		
		//if filename already exists, append
		//if file doesn't exist, program should create it
		//if directory specified by filepath variable does not exist, print error
		
		try {
			outputStreamName = new PrintWriter(new FileOutputStream(filepath,append));
		}
		catch (FileNotFoundException e) {
			System.out.println("ERROR: could not print results. Target directory does not exist.");
			System.exit(0);
		}
		
		outputStreamName.println(toString());
		outputStreamName.close();
	}
	
	/**
	 * Set the number of runs based on parameter
	 * @param N is an int value of the number of scenarios run
	 */
	public void setN(int N) {
		this.N = N;
	}
	
	/**
	 * Updates the scenariolist to be audited
	 * The size of the scenario list corresponds to the number of runs undertaken for the audit
	 * @param scenarios
	 */
	public void setScenario(Scenario[] scenarios) {
		this.scenariolist = new ArrayList<Scenario>(Arrays.asList(scenarios));
		this.N = this.scenariolist.size();
	}
	
	//helper methods

	//updates statistic of all characteristics that have been seen in the scenarios run
	public void updateAllStatistic(EthicalEngine.Decision decision, Scenario scenario) {	
		//update total passenger and pedestrian statistic
		passengerStat[TOTAL] += scenario.getPassengerCount();
		pedestrianStat[TOTAL] += scenario.getPedestrianCount();
		
		//update total for red and green light
		if(scenario.isLegalCrossing()) {
			greenlightStat[TOTAL] += scenario.getPassengerCount() 
								  +  scenario.getPedestrianCount();
		}
		else {
			redlightStat[TOTAL] += scenario.getPassengerCount() 
								+  scenario.getPedestrianCount();
		}

		//PASSENGERS SURVIVE
		if(decision == EthicalEngine.Decision.PASSENGERS) {
			
			//update passenger survivor statistic
			passengerStat[SURVIVORS] += scenario.getPassengerCount();
			
			//update statistic for survivors of red and green light
			if(scenario.isLegalCrossing()) {
				greenlightStat[SURVIVORS] += scenario.getPassengerCount();
			}
			else {
				redlightStat[SURVIVORS] += scenario.getPassengerCount();
			}
			
			//for each passenger in the scenario who survived
			for(Persona passenger:scenario.getPassengers()) {
				if(passenger instanceof Human) {
					
					//update human survival and total statistic
					humanTypeStat[SURVIVORS] ++;
					humanTypeStat[TOTAL] ++;
					
					//update age statistic
					age[SURVIVORS] += passenger.getAge();
					age[TOTAL] ++;
					
					//add 1 count to the particular characteristics of human passenger that survived
					//characteristics for Human include age category, gender, body type, profession
					//pregnancy status, and if it is you
					
					//add to survivor count
					passenger.getBodyType().addSurvivor();
					passenger.getGender().addSurvivor();
					((Human) passenger).getAgeCategory().addSurvivor();
					((Human) passenger).getProfession().addSurvivor();
					
					//add to total count
					passenger.getBodyType().addTotal();
					passenger.getGender().addTotal();
					((Human) passenger).getAgeCategory().addTotal();
					((Human) passenger).getProfession().addTotal();
					
					//
					if(((Human) passenger).isYou()) {
						youStat[SURVIVORS] ++;
						youStat[TOTAL] ++; 
					}
					if(((Human) passenger).isPregnant()) {
						pregnancyStat[SURVIVORS] ++;
						pregnancyStat[TOTAL] ++;
					}
				}
				//passenger is animal and it survives
				else {
					//update animal survival and total statistic
					animalTypeStat[SURVIVORS] ++;
					animalTypeStat[TOTAL] ++;
					
					//add 1 count to particular characteristic of animal passenger that survived
					//characteristics for Animal include specie and pet status
					if(((Animal) passenger).isPet()) {
						petStat[SURVIVORS] ++;
						petStat[TOTAL] ++;
					}
					
					//initialise specie stat for each animal passenger
					Integer[] specieStat = new Integer[2];

					//get specie of animal passenger, increase survivor and total count by 1.
					specieName = ((Animal) passenger).getSpecies();
					specieStat[SURVIVORS] = 1; 
					specieStat[TOTAL] = 1;
					
					//create object which bundles the specie name and stat.
					specie = new GenericPurpose<String,Integer>(specieName,specieStat);
						
					//update specie stat
					updateSpecieList(specie);
				}
			}
			
			//for each pedestrian who didn't survive
			for(Persona pedestrian:scenario.getPedestrians()) {
				//pedestrian who didn't survive is human
				if(pedestrian instanceof Human) {
					
					//update human total statistic
					humanTypeStat[TOTAL] ++;
					
					//add 1 count to particular human characteristic's total
					//characteristics for Human include age category, gender, body type, profession
					//pregnancy status, and if it is you
					pedestrian.getBodyType().addTotal();
					pedestrian.getGender().addTotal();
					((Human) pedestrian).getAgeCategory().addTotal();
					((Human) pedestrian).getProfession().addTotal();
					humanTypeStat[TOTAL] ++;
					
					//
					if(((Human) pedestrian).isYou()) {
						youStat[TOTAL] ++; 
					}
					if(((Human) pedestrian).isPregnant()) {
						pregnancyStat[TOTAL] ++;
					}
				}
				
				//pedestrian is animal and it doesn't survive
				else {
					//update animal total statistic
					animalTypeStat[TOTAL] ++;
					
					//add 1 count to particular animal characteristic's total
					//characteristics for Animal include specie and pet status
					if(((Animal) pedestrian).isPet()) {
						petStat[TOTAL] ++;
					}
					
					//initialise specie stat for animal pedestrian
					Integer[] specieStat = new Integer[2];
					
					//get specie of animal passenger
					//it doesn't survive so only increase total count.
					specieName = ((Animal) pedestrian).getSpecies();
					specieStat[SURVIVORS] = 0; 
					specieStat[TOTAL] = 1;
					
					//create object which bundles the specie name and stat.
					specie = new GenericPurpose<String,Integer>(specieName,specieStat);
					
					//update specie stat
					updateSpecieList(specie);
				}
			}
		}
		
		//PEDESTRIANS SURVIVE
		else {
			//update pedestrian survivor statistic
			pedestrianStat[SURVIVORS] += scenario.getPedestrianCount();
			
			//update statistic for survivors of red and green light
			if(scenario.isLegalCrossing()) {
				greenlightStat[SURVIVORS] += scenario.getPedestrianCount();
			}
			else {
				redlightStat[SURVIVORS] += scenario.getPedestrianCount();
			}
			
			//for each pedestrian in the scenario who survived
			for(Persona pedestrian:scenario.getPedestrians()) {
				if(pedestrian instanceof Human) {
					
					//update human survival and total statistic
					humanTypeStat[SURVIVORS] ++;
					humanTypeStat[TOTAL] ++;
					
					//update age statistic
					age[SURVIVORS] += pedestrian.getAge();
					age[TOTAL] ++;

					//add 1 count to the particular characteristics of human passenger that survived
					//characteristics for Human include age category, gender, body type, profession
					//pregnancy status, and if it is you
					
					//add to survivor count
					pedestrian.getBodyType().addSurvivor();
					pedestrian.getGender().addSurvivor();
					((Human) pedestrian).getAgeCategory().addSurvivor();
					((Human) pedestrian).getProfession().addSurvivor();
					
					//add to total count
					pedestrian.getBodyType().addTotal();
					pedestrian.getGender().addTotal();
					((Human) pedestrian).getAgeCategory().addTotal();
					((Human) pedestrian).getProfession().addTotal();
					
					//
					if(((Human) pedestrian).isYou()) {
						youStat[SURVIVORS] ++;
						youStat[TOTAL] ++; 
					}
					if(((Human) pedestrian).isPregnant()) {
						pregnancyStat[SURVIVORS] ++;
						pregnancyStat[TOTAL] ++;
					}
				}
				//pedestrian who survived is animal
				else {
					//update animal survival and total statistic
					animalTypeStat[SURVIVORS] ++;
					animalTypeStat[TOTAL] ++;
					
					//add 1 count to particular characteristic of animal pedestrian that survived
					//characteristics for Animal include specie and pet status
					if(((Animal) pedestrian).isPet()) {
						petStat[SURVIVORS] ++;
						petStat[TOTAL] ++;
					}
					
					//initialise specie stat for each animal passenger
					Integer[] specieStat = new Integer[2];
					
					//get specie of animal pedestrian, increase survivor and total count by 1.
					specieName = ((Animal) pedestrian).getSpecies();
					specieStat[SURVIVORS] = 1; 
					specieStat[TOTAL] = 1;
					
					//create object which bundles the specie name and stat together
					specie = new GenericPurpose<String,Integer>(specieName,specieStat);
					
					//update specie stat
					updateSpecieList(specie);
				}
			}
			
			//for each passenger who didn't survive
			for(Persona passenger:scenario.getPassengers()) {
				//pedestrian who didn't survive is human
				if(passenger instanceof Human) {
					
					//update human total statistic
					humanTypeStat[TOTAL] ++;
					
					//add 1 count to particular human characteristic's total
					//characteristics for Human include age category, gender, body type, profession
					//pregnancy status, and if it is you
					passenger.getBodyType().addTotal();
					passenger.getGender().addTotal();
					((Human) passenger).getAgeCategory().addTotal();
					((Human) passenger).getProfession().addTotal();
					humanTypeStat[TOTAL] ++;
					
					//
					if(((Human) passenger).isYou()) {
						youStat[TOTAL] ++; 
					}
					if(((Human) passenger).isPregnant()) {
						pregnancyStat[TOTAL] ++;
					}
				}
				//passenger who didn't survive is animal
				else {
					//update animal total statistic
					animalTypeStat[TOTAL] ++;
					
					//add 1 count to particular animal characteristic's total
					//characteristics for Animal include specie and pet status
					if(((Animal) passenger).isPet()) {
						petStat[TOTAL] ++;
					}

					//initialise specie stat for each animal passenger
					Integer[] specieStat = new Integer[2];
					
					//get specie name of animal passenger
					//it doesnt survive so only increase total count.
					specieName = ((Animal) passenger).getSpecies();
					specieStat[SURVIVORS] = 0; 
					specieStat[TOTAL] = 1;
					
					//create object that bundles specie name and stat together
					specie = new GenericPurpose<String,Integer>(specieName,specieStat);
					
					//update specie stat
					updateSpecieList(specie);
				}
			}
		}
	}
	
	//returns the survival rate. 1.00 indicates 100% survival rate. 0.00 indicates 0% survival
	public double survivalPercentage(int survivors, int total) {
		double percentage = survivors/(double)total;
		return percentage;
	}
	
	//updates the specie list of animals in the simulation
	//the specie list has GenericPurpose objects which contains the specie string name and the
	//associated survival and total count for that specific animal
	public void updateSpecieList(GenericPurpose<String,Integer> specie) {
		String specieName = specie.getKey();
		Integer[] specieStat = specie.getValue();
		//specie in the list already
		if(specieInList(specie)) {
			for(GenericPurpose<String,Integer> currentspecie: specielist) {			
				//find specie in the list and update its stats
				if(currentspecie.getKey().equalsIgnoreCase(specieName)) {
					specieStat[SURVIVORS] = currentspecie.getValue()[SURVIVORS] 
							              + specieStat[SURVIVORS];
					specieStat[TOTAL] = currentspecie.getValue()[TOTAL] 
									  + specieStat[TOTAL];
					currentspecie.setValue(specieStat);
				}
			}
		}
		
		//specie not in list, add that specie
		else {
			specielist.add(specie);			
		}
	}
	
	//check whether the a specific specie name is in the list already
	public boolean specieInList(GenericPurpose<String,Integer> specie) {
		
		boolean isInList = false;
		
		//list is empty
		if(specielist.size()==0) {
			return false;
		}
		
		//list is not empty
		for(int i = 0;i<specielist.size();i++) {
			if(specie.getKey().equals(specielist.get(i).getKey())) {
				isInList = true;
				break;
			}
			else {
				isInList = false;
			}
		}
		return isInList;
	}
}