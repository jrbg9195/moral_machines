/**
 * COMP90041, Sem2, 2020: Final Project: Moral Machines
 * @author John Robert Garcia, 695559, garcia1@student.unimelb.edu.au
 * This interface lays out the structure of the enum classes Profession, AgeCategory, Gender, BodyType
 * The methods that must be implemented are used to update the survivor and total count for a 
 * specific characteristic and is used specifically for the audit.
 */

package ethicalengine;

public interface EnumStructureforAudit {
	
	//getters
	public int getSurvivor();
	public int getTotal();
	
	//setters
	public void setSurvivor(int survivor);
	public void setTotal(int total);
	
	//update survivor, total count
	public void addSurvivor();
	public void addTotal();
}