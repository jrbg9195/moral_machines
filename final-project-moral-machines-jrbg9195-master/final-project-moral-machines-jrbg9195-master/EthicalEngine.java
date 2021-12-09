import java.util.*;
import java.io.*;

import ethicalengine.*;
import ethicalengine.Human.AgeCategory;
import ethicalengine.Human.Profession;
import ethicalengine.Persona.BodyType;
import ethicalengine.Persona.Gender;

/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * @author: garcia1@student.unimelb.edu.au, John Robert Garcia, 695559
 * This class holds the main method and manages program execution.
 * It takes care of program parameters and user input.
 * It also houses the decision algorithm which decides on whether to save Passengers or Pedestrians
 */
public class EthicalEngine {
		
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private final String COLON = ":";
	private final String RED = "red";
	private final String GREEN = "green";
	private final String PASSENGER = "passenger";
	
	private final Profession DEFAULT_PROFESSION = Profession.NONE;
	private final int DEFAULT_AGE = 20;
	private final Gender DEFAULT_GENDER = Gender.UNKNOWN;
	private final BodyType DEFAULT_BODYTYPE = BodyType.UNSPECIFIED;
	
	private final int NEWSCENARIO = 0;
	private final int LEGALITY = 1;
	
	private final int CLASS = 0;
	private final int GENDER = 1;
	private final int AGE = 2;
	private final int BODYTYPE = 3;
	
	private final int PROFESSION = 4;
	private final int PREGNANT = 5;
	private final int ISYOU = 6;
	private final int SPECIES = 4;
	private final int ISPET = 5;
	private final int ROLE_HUMAN = 7;
	private final int ROLE_ANIMAL = 6;
	
	private static final String DEFAULT_RESULTS_FILENAME = "results.log";
	
	private final String CONFIG_FLAG1 = "-c";
	private final String CONFIG_FLAG2 = "--config";
	
	private final String RESULTS_FLAG1 = "-r";
	private final String RESULTS_FLAG2 = "--results";
	
	private final String HELP_FLAG1 = "--help";
	private final String HELP_FLAG2 = "-h";
	
	private final String INTERACTIVE_FLAG1 = "-i";
	private final String INTERACTIVE_FLAG2 = "--interactive";
	
    public enum Decision {PASSENGERS, PEDESTRIANS};
    private ArrayList<Scenario> scenariolist = new ArrayList<Scenario>();
    private ArrayList<Scenario> scenariolist_config = new ArrayList<Scenario>();
    private ArrayList<Decision> decisionlist = new ArrayList<Decision>();
    private String output_filepath;
    private String input_filepath;
    private Audit audit = new Audit();
    private Scenario[] scenarioarray;
    private Decision[] decisionarray;
    private int interactive_count = 0;
    
    /**
     * Creates an EthicalEngine object with a default output filepath where the statistics are saved to. 
     */
    public EthicalEngine() {
    	output_filepath = DEFAULT_RESULTS_FILENAME;
    }
    
    /**
     * Creates an ethicalengine object with a specified output filepath where the statistics are saved to.
     * @param filepath is a String and includes both the directory i.e. (logs/)
     * and the filename i.e. (results1.log). 
     */
    public EthicalEngine(String filepath) {
    	output_filepath = filepath;
    }
    
    /**
     * Sets the input filepath of the ethical engine object
     * @param input_filepath is a String value which corresponds to the path where a csv file is.
     */
    public void setInputFilePath(String input_filepath) {
    	this.input_filepath = input_filepath;
    }
    
    /**
     * Sets the output filepath of the ethical engine object
     * @param output_filepath is a String value which corresponds to the path where the statistics
     * are saved to.
     */
    public void setOutputFilePath(String output_filepath) {
    	this.output_filepath = output_filepath;
    }
    
    /**
     * Get the input filepath
     * @return input_filepath which is a String value.
     */
    public String getInputFilePath() {
    	return this.input_filepath;
    }
    
    /**
     * Get the output filepath
     * @return output_filepath which is a String value.
     */
    public String getOutputFilePath() {
    	return this.output_filepath;
    }
    
 
    /**
     * Main function executes the program
     * @param args is the user's input to the console. They may contain a flag value and flag argument
     * after which the specific command would be run
     * Flags available are config, help, results, and interactive.
     * Config sets the input to an audit from a csv file.
     * Help displays the available flags are the functions to the user.
     * Results sets the output filepath where the statistics are saved.
     * Interactive runs an interactive mode where the user can choose the decision for specific 
     * scenarios presented to him.
     * If no flag value and argument is entered, an audit on 100 randomly generated scenarios is
     * conducted, displaying it and writing it to the default filepath results.log
     */
    public static void main(String[] args) {
    	final int FLAG = 0;
    	final int FLAG_ARGUMENT1 = 1;
    	final int FLAG_ARGUMENT2 = 2;
    	    	
    	Scanner keyboard = new Scanner(System.in);
    	
    	EthicalEngine ethicalengine = new EthicalEngine(DEFAULT_RESULTS_FILENAME);
    	
    	//variables needed for the flag inputs
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(args));
		String unchoppedinput;
    	boolean operate = true;

    	while(operate) {
    		
    		//no flag entered
	    	if(input.size() == 0) {
	    		operate = ethicalengine.noflag(keyboard);
	    		
	    		//if false, exit while loop
	    		if(!operate) {
	    			break;
	    		}
	    		
	    	    unchoppedinput = keyboard.nextLine();
	    	    input = ethicalengine.delimSeparatedLine(unchoppedinput,SPACE);
			}
	    	
	    	//there is flag input but no argument
	    	//for help
	    	else if(input.size() == 1) {
	    		String flag = input.get(FLAG); 
	    		
	    		operate = ethicalengine.yesflag(flag, keyboard);
	    		//if operate false, exit while loop
	    		if(!operate) {
	    			break;
	    		}
	    		
	    	    unchoppedinput = keyboard.nextLine();
	    	    input = ethicalengine.delimSeparatedLine(unchoppedinput,SPACE);

	    	}
	    	//there if flag input and argument
	    	//results, config
	    	else if(input.size() == 2) {
	    		
	    		String flag = input.get(FLAG); 
	    		String flag_argument = input.get(FLAG_ARGUMENT1); 
	    		
	        	operate = ethicalengine.yesflag(flag, flag_argument, keyboard);
	        	
	        	//if false, exit while loop
	    		if(!operate) {
	    			break;
	    		}
	    		
	    	    unchoppedinput = keyboard.nextLine();
	    	    input = ethicalengine.delimSeparatedLine(unchoppedinput,SPACE);
	    	}	
	    	
	    	//this only occurs for (interactive flag) (config flag) (config file path)
	    	//this launches interactive mode with a config file
	    	else if(input.size() == 3) {
	    		
	    		String flag = input.get(FLAG); 
	    		String flag2 = input.get(FLAG_ARGUMENT1); 
	    		String flag_argument = input.get(FLAG_ARGUMENT2);
	    		
	        	operate = ethicalengine.yesflag(flag, flag2, flag_argument, keyboard);
	        	
	        	//if false, exit while loop
	    		if(!operate) {
	    			break;
	    		}
	    		
	    	    unchoppedinput = keyboard.nextLine();
	    	    input = ethicalengine.delimSeparatedLine(unchoppedinput,SPACE);
	    	}	
    	}
    }
    
    //Displays the welcome screen when interactive mode is chosen
    public void welcome(String input_filepath, Scanner keyboard) {
    	try {
    		keyboard = new Scanner(new FileInputStream(input_filepath));
    	}
    	catch (FileNotFoundException e) {
			System.out.println("ERROR: could not find config file.");
			System.exit(0);
		}
    	
    	while(keyboard.hasNext()) {
    		System.out.println(keyboard.nextLine());
    	}
    }
    
    //No command name, no config file specified
  	//Default behaviour, run algorithm audit with 100 truly randomly generated scenarios,
  	//Writing to default results.log
    public boolean noflag(Scanner keyboard) {
		audit.run(100);
		audit.printStatistic();
		audit.printToFile(DEFAULT_RESULTS_FILENAME);
		keyboard.close();
		return false;
    }
    
    //Executes interactive mode.
    //User's consent will be taken whether his results can be saved and written to a user.log
    //If no config flag and config file is presented in this mode, the user will be presented with 
    //3 randomly generated scenarios and will be asked to decide on the outcome. After entering 
    //decisions for each scenario, the statistic will be displayed and the user will be prompted 
    //whether to continue. As long as he/she chooses to continue, 3 randomly generated scenarios will be
    //presented again for him/her to decide. Once he/she chooses no, the results will be written
    //to a user log if consent was given, otherwise it won't be. Program is then exited.    
    //If a config flag and filepath was provided, the only difference is that the scenarios presented
    //are not randomly generated and will come from a csv file. 3 scenarios will be provided everytime
    //before asking for his permission to continue until all the scenarios in the csv config file
    //have been ran through
    public boolean interactive(Scanner keyboard, String scenariopresented) {
    	final String WELCOME = "welcome.ascii";
    	final String YES = "yes";
    	final String NO = "no";
    	final String USER_LOG = "user.log";
    	final String USER = "User";
    	final int TOTAL_SCENARIOS_INTERACTIVE = 3;
    	final String RANDOM = "random";
    	final String CONFIG = "config";
    	    	
    	boolean operate = true;
    	
    	welcome(WELCOME, keyboard);

    	System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
    	
    	String consent = keyboard.nextLine();
    	
    	String cont = YES;
    	
    	//continue as long as the user wants to
    	while(cont.equals(YES)) {

	    	if(consent.equals(YES)) {
	    		//use user.log
	    		consent = YES;
	    		setOutputFilePath(USER_LOG);
	    	}
	    	else if(consent.equals(NO)) {
	    		//don't use user.log
	    		consent = NO;
	    	}
	    	else {
	    		//throw exception if neither yes nor no are entered
		    	while(!(consent.equals(YES) || consent.equals(NO))) {
			    	try {
				    		throw new InvalidInputException();
				    	}
			    	
					catch (InvalidInputException e) {
						System.out.println(e.getMessage());
						consent = keyboard.nextLine();
					}
				}
	    	}
	    		    	
			//create scenarios from the generator if there is no config flag
	    	if(scenariopresented.equals(RANDOM)) {
				
	    		//create scenariogenerator
				ScenarioGenerator scenariogenerator = new ScenarioGenerator();
				
				//generate 3 random scenario, display them and ask for user input 
				for(int i = 0; i< TOTAL_SCENARIOS_INTERACTIVE; i++) {
					scenariolist.add(scenariogenerator.generate());
					System.out.println(scenariolist.get(i + interactive_count).toString());
					System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
					
					
					String user_decision = keyboard.nextLine();
					decisionlist.add(interactive_decision(user_decision));
				}
	    	}
	    	//config flag and filepath provided
	    	else if(scenariopresented.equals(CONFIG)) {
	    		//take scenarios from config file
	    		scenariolist = new ArrayList<Scenario>(Arrays.asList(config(input_filepath, keyboard)));
	    		//display 3 scenario or until all scenarios from csv are shown
	    		//and ask for user's decision
				for(int i = 0; i < TOTAL_SCENARIOS_INTERACTIVE && 						
						i + interactive_count < scenariolist.size(); i++) {

						System.out.println(scenariolist.get(i + interactive_count).toString());
						System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
						
						String user_decision = keyboard.nextLine();
						decisionlist.add(interactive_decision(user_decision));
				}
	    	}
	    	
	    	//Converting scenario and decision list to array
	    	scenarioarray = new Scenario[scenariolist.size()];
	    	decisionarray = new Decision[decisionlist.size()];
			scenarioarray = scenariolist.toArray(scenarioarray);
			decisionarray = decisionlist.toArray(decisionarray);
			
			//set the audit type and scenario for the audit
			audit.setScenario(scenarioarray);
			audit.setAuditType(USER);
			
			int lastcount = 0;
			//update the statistics in the audit
			for(int i = 0; (i<TOTAL_SCENARIOS_INTERACTIVE && i + interactive_count < scenariolist.size()); 
																						i++) {
				audit.updateAllStatistic(decisionarray[i + interactive_count], 
										 scenarioarray[i + interactive_count]);
				lastcount = i + 1;
			}
			
			//update total scenarios that user has decided on so far
			interactive_count = interactive_count + lastcount;
			
			//set number of runs
			audit.setN(interactive_count);		
			
			//display statistic
			audit.printStatistic();
			
			//only append user.log if user consents
			if(consent.equals(YES)) {
				audit.printToFile(getOutputFilePath());
			}
			
			//only ask to continue if there are still remaining scenarios in a config run interactive mode 
			//or if 3 scenarios have been presented already whichever comes first
			if((interactive_count < scenariolist.size() && scenariopresented.equals(CONFIG))
			  ||scenariopresented.equals(RANDOM)) {
				System.out.println("Would you like to continue? (yes/no)");
				cont = keyboard.nextLine();
			}
			else {
				cont = NO;
			}
    	}
    	
    	//user doesn't want to continue anymore, while loop exited
    	System.out.println("That's all. Press Enter to quit.");
    	keyboard.nextLine();
    	
    	operate = false;
    	
    	return operate;
    }
    
    //returns the user's decision in the form of enum Decision during interactive mode
    public Decision interactive_decision(String user_decision) {
    	final String PASSENGER1 = "passenger";
    	final String PASSENGER2 = "passengers";
    	final String PASSENGER3 = "1";
    	final String PEDESTRIAN1 = "pedestrian";
    	final String PEDESTRIAN2 = "pedestrians";
    	final String PEDESTRIAN3 = "2";
   
    	Decision decision = Decision.PASSENGERS;
    	
    	switch(user_decision) {
    	case PASSENGER1:
    	case PASSENGER2:
    	case PASSENGER3:
    		//save passengers
    		decision = Decision.PASSENGERS;
    		break;
    	case PEDESTRIAN1:
    	case PEDESTRIAN2:
    	case PEDESTRIAN3:
    		//save pedestrians
    		decision = Decision.PEDESTRIANS;
    		break;
    	default:
    		decision = Decision.PASSENGERS;
    		break;
    	}
    	return decision;
    }
    
    //theres a flag but no flag argument
    // help, config, interactive with no argument
    public boolean yesflag(String flag, Scanner keyboard) {
    	boolean operate = true;
    	String scenariopresented = "random";
    	
    	switch(flag) {	
    	case CONFIG_FLAG1:
    	case CONFIG_FLAG2:
    		operate = help(); 
    		break;
    	case HELP_FLAG1:
    	case HELP_FLAG2:
    		operate = help();
    		break;
    	case INTERACTIVE_FLAG1:
    	case INTERACTIVE_FLAG2: 
    		operate = interactive(keyboard,scenariopresented);		
    		break;
    	default:
    		operate = false;
    		break;
	   	}	
    	return operate;
    }
    
    //theres a flag with argument
    public boolean yesflag(String flag, String flag_argument, Scanner keyboard) {
		boolean operate = true;

    	//different flag types
    	switch(flag) {
    	
	    	case CONFIG_FLAG1:
	    	case CONFIG_FLAG2:
	    		scenarioarray = config(flag_argument, keyboard);
	    		//audits the scenarios from the config file
	    		audit = new Audit(scenarioarray);
	    		audit.run();
	    		audit.printStatistic();
	    		audit.printToFile(getOutputFilePath());

	    		keyboard.close();
	    		operate = false;
	    		break;
	    		
	    	case RESULTS_FLAG1:
	    	case RESULTS_FLAG2:	
	    		operate = results(flag_argument);
	    		break;
	    		
	    	case HELP_FLAG1:
	    	case HELP_FLAG2:
	    		operate = help();
	    		break;
	    	default:
	    		operate = false;
	    		break;
    	}
    	return operate;
    }
    
    //interactive and config together
    public boolean yesflag(String flag, String flag_argument1, String flag_argument2, Scanner keyboard) {
		boolean operate = true;
		String scenariopresented = "config";
		
    	//different flag types
    	switch(flag) {
    	
    	case INTERACTIVE_FLAG1:
    	case INTERACTIVE_FLAG2:
				setInputFilePath(flag_argument2);
    			operate = interactive(keyboard,scenariopresented);
	    		break;
	    	default:
	    		operate = false;
	    		break;
    	}
    	return operate;
    }
    
    public boolean help() {
    	System.out.println("EthicalEngine - COMP90041 - Final Project");
    	System.out.println("Usage: java EthicalEngine [arguments]\n");
    	System.out.println("Arguments:");
    	System.out.println("-c or --config\tOptional: path to config file");
    	System.out.println("-h or --help\tPrint Help (this message) and exit");
    	System.out.println("-r or --results\tOptional: path to results log file");
    	System.out.println("-i or --interactive\tOptional: launches interactive mode");
    	return true;
    }
    
    //specify the target filepath where the statistics are printed
    public boolean results(String output_filepath) {
		setOutputFilePath(output_filepath);
		return true;
    }
    
    //takes an input config file, creates a persona using the characteristics defined in each line,
    //and creates scenarios composed of the personas then outputs an array of scenarios.
    public Scenario[] config(String input_filepath, Scanner keyboard) {
		try {
			keyboard = new Scanner(new FileInputStream(input_filepath));
		}
		
		catch (FileNotFoundException e) {
			System.out.println("ERROR: could not find config file.");
			System.exit(0);
		}
		
		//each index of the arraylist contains 1 line from the csv file
		ArrayList<String> lines = new ArrayList<String>();
		int count = 0;
		
		while(keyboard.hasNextLine()) {
			lines.add(keyboard.nextLine());
			
			//to monitor what is read each line in the csv
			count++;
		}
		
		int lastlinenumber = count - 1; 
		int linenumber = 0;
		
		//parameters to a scenario
		Boolean legality = false;
		ArrayList<Persona> passengerlist = new ArrayList<Persona>();
		ArrayList<Persona> pedestrianlist = new ArrayList<Persona>();
		scenariolist_config = new ArrayList<Scenario>();
		
		for(String line:lines) {	
			//each index of this arraylist contains the contents of the cell of a new line
			ArrayList<String> separatedLine = delimSeparatedLine(line, COMMA);

			try {
				switch(separatedLine.get(CLASS).toLowerCase()) {
					//first cell of the line is human
					case "human":
						
						//humans don't have specie and isPet so only 8 fields should have data
						if(separatedLine.size()!=8) {
							throw new InvalidDataFormatException("WARNING: invalid data format in "
									+ "config file in line " + linenumber);
						}
						//if correct number of values in line for a human persona, create a human
						Human human = new Human();
						
						String profession = separatedLine.get(PROFESSION).toUpperCase();
						String bodytype = separatedLine.get(BODYTYPE).toUpperCase();
						String gender = separatedLine.get(GENDER).toUpperCase();
						String age_string = separatedLine.get(AGE);
						int age = DEFAULT_AGE;
						
						boolean validprof = true;
						boolean validgender = true;
						boolean validbody = true;
						boolean isPregnant = Boolean.parseBoolean(separatedLine.get(PREGNANT));
						boolean isYou = Boolean.parseBoolean(separatedLine.get(ISYOU));
	
						//protect code against invalid values for profession, body type and gender
						try {
							human.setAsYou(isYou);
							human.setPregnant(isPregnant);	
							validprof = checkProf(profession);
							validgender = checkGender(gender);
							validbody = checkBody(bodytype);
							
							if(!validprof || !validgender || !validbody) {	
								throw new InvalidCharacteristicException("WARNING: invalid "
										+ "characteristic in config file in line " + linenumber);
							}
							human.setProfession(Profession.valueOf(profession));
							human.setBodyType(BodyType.valueOf(bodytype));
							human.setGender(Gender.valueOf(gender));
						}
							
						catch(InvalidCharacteristicException e) {
							System.out.println(e.getMessage());
							
							if(validprof) {
								human.setProfession(Profession.valueOf(profession));
							}
							else {
								human.setProfession(DEFAULT_PROFESSION);
							}	
							
							if(validgender) {
								human.setGender(Gender.valueOf(gender));
							}
							else {
								human.setGender(DEFAULT_GENDER);
							}	
							
							if(validbody) {
								human.setBodyType(BodyType.valueOf(bodytype));
							}
							else {
								human.setBodyType(DEFAULT_BODYTYPE);
							}	
						}
						
						//protect code against number format exception
						try {
							age = Integer.parseInt(age_string);
							human.setAge(age);
						}
						
						catch(NumberFormatException e) {
							System.out.println("WARNING: invalid number format in config file in line "
									+ linenumber);
							human.setAge(DEFAULT_AGE);
						}

						
						//human is a passenger, add to passenger list
						if(separatedLine.get(ROLE_HUMAN).equals(PASSENGER)) {
							passengerlist.add(human);
						}
						
						//human is pedestrian, add to pedestrian list
						else {
							pedestrianlist.add(human);
						}
						
						if(linenumber == lastlinenumber) {
							//create final scenario using data from accumulated from passengers, 
							//pedestrians legality of crossing, then add to list
							createScenariofromList(passengerlist, pedestrianlist, legality);
						}
						break;
						
					//first cell of line is animal
					case "animal":
						//animals don't have profession, isPregnant and isYou so only 7 fields should
						//have data
						if(separatedLine.size()!=7) {
							throw new InvalidDataFormatException("WARNING: invalid data format in config "
									+ "file in line " + linenumber);
						}
						
						//if correct number of values for an animal persona, create an animal
						Animal animal = new Animal();
						
						//a stands for animal
						String a_bodytype = separatedLine.get(BODYTYPE).toUpperCase();
						String a_gender = separatedLine.get(GENDER).toUpperCase();
						String a_age_string = separatedLine.get(AGE);
						int a_age = DEFAULT_AGE;

						boolean a_validgender = true;
						boolean a_validbody = true;
						
						//protect code against invalid values for body type and gender
						try {		
							animal.setPet(Boolean.valueOf(separatedLine.get(ISPET)));
							animal.setSpecies(separatedLine.get(SPECIES));
							
							validgender = checkGender(a_gender);
							validbody = checkBody(a_bodytype);
							
							if(!validgender || !validbody) {	
								throw new InvalidCharacteristicException("WARNING: invalid "
										+ "characteristic in config file in line " + linenumber);
							}
							animal.setBodyType(BodyType.valueOf(a_bodytype));
							animal.setGender(Gender.valueOf(a_gender));
							
						}						
						catch(InvalidCharacteristicException e) {
							if(a_validgender) {
								animal.setGender(Gender.valueOf(a_gender));
							}
							else {
								animal.setGender(DEFAULT_GENDER);
							}	
							
							if(a_validbody) {
								animal.setBodyType(BodyType.valueOf(a_bodytype));
							}
							else {
								animal.setBodyType(DEFAULT_BODYTYPE);
							}	
						}
						//ensure age is integer
						try {
							a_age = Integer.parseInt(a_age_string);
							animal.setAge(a_age);
						}
						
						catch(NumberFormatException e) {
							System.out.println("WARNING: invalid number format in config file in line "
									+ linenumber);
							animal.setAge(DEFAULT_AGE);
						}
					
						//animal is passenger
						if(separatedLine.get(ROLE_ANIMAL).equals(PASSENGER)) {
							passengerlist.add(animal);
						}
						//animal is pedestrian
						else {
							pedestrianlist.add(animal);
						}
						
						if(linenumber == lastlinenumber) {
							//create final scenario using data from accumulated passengers, pedestrians
							//and legality of crossing , then add it to scenariolist
							createScenariofromList(passengerlist, pedestrianlist, legality);
						}
						break;
						
					//heading or new scenario line
					default: 
						if (linenumber == 1) {
							//Very first scenario, can only get legality data
							
							//update legality
							legality = legalCrossing(separatedLine);
						}
						else if (linenumber != 0) {
							//New scenario
							//Create a scenario object for the past scenario using the 
							//legality, passenger list and pedestrian list data accumulated
							
							createScenariofromList(passengerlist, pedestrianlist, legality);
														
							//update legality
							legality = legalCrossing(separatedLine);
	
							
							//clear passenger and pedestrian list for new scenario
							passengerlist.clear();
							pedestrianlist.clear();	
						}
						else {
							//first line, it's the heading
							continue;
						}
						break;
				}
			}
			
			catch (InvalidDataFormatException e) {
				System.out.println(e.getMessage());
			}
			finally {
				linenumber++;
			}
		}
		//after all the scenarios from the csv are created and put to a list, it is converted to an
		//array then returned to the where the function is called
		
		scenarioarray = new Scenario[scenariolist_config.size()];
		scenarioarray = scenariolist_config.toArray(scenarioarray);
		return scenarioarray;
    }
    
	//Create a scenario object for the past scenario using the 
	//legality, passenger list and pedestrian list data accumulated
    //Also adds the created scenario to the list
    public void createScenariofromList(ArrayList<Persona> passengerlist, 
    			ArrayList<Persona> pedestrianlist, boolean legality) {
    	//Converting list to array
		Persona [] passengerarray = new Persona[passengerlist.size()];
		passengerarray = passengerlist.toArray(passengerarray);
		
		Persona [] pedestrianarray = new Persona[pedestrianlist.size()];
		pedestrianarray = pedestrianlist.toArray(pedestrianarray);
		
		//create scenario
		Scenario scenario = new Scenario(passengerarray, pedestrianarray, legality);
		
		//add to scenario list for config
		scenariolist_config.add(scenario);
    }
    
    //checks if the profession of a persona read from csv is valid
    //return true if valid, false if not
    public boolean checkProf(String input) { 
    	boolean exists = false;
    	
    	for(Profession profession: Profession.values()) {
			if(profession.name().equalsIgnoreCase(input)) {
				exists = true;
				break;
			}
			exists = false;
		}
    	return exists;
    }
    
    //checks if the gender of a persona read from csv is valid
    //return true if valid, false if not
    public boolean checkGender(String input) {
    	boolean exists = false;
    	
    	for(Gender gender: Gender.values()) {
			if(gender.name().equalsIgnoreCase(input)) {
				exists = true;
				break;
			}
			exists = false;
		}
    	return exists;
    }
    
    //checks if the body type of a persona read from csv is valid
    //return true if valid, false if not
    public boolean checkBody(String input) {
    	boolean exists = false;
    	
    	for(BodyType bodyType: BodyType.values()) {
			if(bodyType.name().equalsIgnoreCase(input)) {
				exists = true;
				break;
			}
			exists = false;
		}
    	return exists;
    }
   
    public ArrayList<String> delimSeparatedLine(String line, String delim) {

    	StringTokenizer needtoseparateLine = new StringTokenizer(line, delim);
    	
    	ArrayList<String> delimSeparated = new ArrayList<String>();
    	    	
    	while(needtoseparateLine.hasMoreTokens()) {
    		delimSeparated.add(needtoseparateLine.nextToken());
    	} 	
    	return delimSeparated;	
    }
    
    //checks whether the scenario given in the csv is green (legal) or red(illegal)
    public boolean legalCrossing(ArrayList<String> separatedLine) {
    	
    	boolean legality = false;
    	
    	if(delimSeparatedLine(separatedLine.get(NEWSCENARIO),COLON)
    										.get(LEGALITY).equals(GREEN)) {
    		legality = true; 
		}
    	
    	else if (delimSeparatedLine(separatedLine.get(NEWSCENARIO),COLON)
    										.get(LEGALITY).equals(RED)) {
    		legality = false;
    	}
    	
    	return legality;
    }
 
    
    /**
     * Decides whether to save the passengers or the pedestrians
     * Algorithm is based on a point system.
     * The higher the points, the higher the priority is to save a group of people
     * Moral points are based off: persona type(human/animal), human pregnancy status, 
     * human age category, profession, user whereabouts (whether you are passenger/pedestrian), pet status
     * If the total points for passengers and pedestrians in a scenario are equal, 
     * legality of crossing will be the deciding factor. 
     * If crossing is legal, save pedestrians, else save passengers
     * @param Scenario scenario: the ethical dilemma
     * @return Decision: which group to save
     */
    public static Decision decide(Scenario scenario) {
    	
    	//index of array containing points for both passengers and pedestrians
    	final int PAS_INDEX = 0;
    	final int PED_INDEX = 1;
    	
    	//this list contains the morality points for the passengers and pedestrians.
    	int[] moralityPoints = {0,0};
    	
    	for(int passenger=0; passenger<scenario.getPassengerCount(); passenger++) {
    		moralityPoints[PAS_INDEX] += personaPoints(scenario.getPassengers().get(passenger));
    	}
    	
    	for(int pedestrian=0; pedestrian<scenario.getPedestrianCount(); pedestrian++) {
    		moralityPoints[PED_INDEX] += personaPoints(scenario.getPedestrians().get(pedestrian));
    	}
    	
        //if morality points for passenger is higher, save them
        if (moralityPoints[PAS_INDEX] > moralityPoints[PED_INDEX]) {
            return Decision.PASSENGERS;
        }
        //if morality point for pedestrian is higher, save them
        else if (moralityPoints[PAS_INDEX] < moralityPoints[PED_INDEX]) {
        	return Decision.PEDESTRIANS;
        }
    	//if total points for passengers and pedestrians are equal in a scenario,
    	//deciding factor will be legality of crossing. save pedestrians if crossing is legal
        //else save passengers
        else {
        	if(scenario.isLegalCrossing()) {
        		return Decision.PEDESTRIANS;
        	}
        	else {
        		return Decision.PASSENGERS;
        	}
        } 
    }
    
    /**
     * Quantify the moral points assigned to a persona
     * The higher the moral points, the higher the priority is to save to that persona
     * Points are based on: persona type(human/animal), human pregnancy status, 
     * human age category, profession, user whereabouts (whether you are passenger/pedestrian), pet status
     * @param persona who is a passenger/pedestrian with certain attributes
     * @return moral points assigned to the persona
     */
    private static double personaPoints(Persona persona) {
    	double moralpoints = 0;
    	
    	//moral points based on persona type, 10 points if Human
    	if(persona instanceof Human) {
    		moralpoints += 10;
    		
    		if(((Human) persona).isYou()) {
    			moralpoints += 10;
    		}
    		//moralpoints based on AgeCategory
    		//8 points if human is senior
    		if(((Human) persona).getAgeCategory() == AgeCategory.SENIOR) {
    			moralpoints += 8;
    		}
    		//7 points if human is a child or baby
    		else if(((Human) persona).getAgeCategory() == AgeCategory.BABY
    			  ||((Human) persona).getAgeCategory() == AgeCategory.CHILD) {
    			moralpoints += 7;
    		}
    		//6 points if human is an adult
    		else {
    			moralpoints += 6;
    		}
    		//moralpoints based on pregnancy state, 8 points if pregnant
    		if(((Human) persona).isPregnant()) {
    			moralpoints += 8;
    		}
    		//-1 moralpoints if criminal
    		if(((Human) persona).getProfession() == Profession.CRIMINAL) {
    			moralpoints -= 1;
    		}
    	}
    	
    	//moralpoints based on persona type, 1 point if animal
    	if(persona instanceof Animal) {
    		moralpoints += 0.5;
    		
    		//moralpoints based on pet status, 1 point if pet
    		if(((Animal) persona).isPet()) {
    			moralpoints += 0.5;
    		}
    	}
    	return moralpoints;
    }
}