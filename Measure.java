package dempster;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a single measure within a Dempster-Schaefer calculation.
 * Contains multiple entries and allows the calculation of plausability, believe and doubt for alternatives
 */
public class Measure{
	
	/** size of the measure-entries */
	private int size;
	
	/** List holding the various measure-entries */
	private List<MeasureEntry> entries = new ArrayList<MeasureEntry>();
	
	/** reference to the entry in the entry-list, thast represents the omega-remainder */
	private MeasureEntry omegaEntry;
	
	public Measure(int size) {
		this.size = size;
		//set the omega-Entry to all 1
		List<Integer> omegaEntryList = new ArrayList<Integer>();
		for (int i = 0 ; i < size; i++) {
			omegaEntryList.add(1);			
		}
		
		this.omegaEntry = new MeasureEntry(this.size, omegaEntryList, 1.0);
		this.entries.add(omegaEntry);
	}
	
	/**
	 * Add an entry for the Measure by submitting the entry-values and the propability of the entry
	 * @param List of entrys. Should be the same size of the Measure
	 * @param propability Propability of the entry
	 */
	public void addEntry(List<Integer> entry, double propability) {
		//check if entry already exists
		for (MeasureEntry me : entries) {
			if (isSameEntry(me.getValues(), entry)) {
				me.setPropability(me.getPropability() + propability);
				this.omegaEntry.setPropability(this.omegaEntry.getPropability()-propability);
				return;
			}			
		}
		//entry does not exist yet?
		entries.add(new MeasureEntry(size, entry, propability));
		this.omegaEntry.setPropability(this.omegaEntry.getPropability()-propability);
	}

	/**
	 * Returns all Measure-Entries
	 * 
	 * @return a List of all Measure-Entries
	 */
	public List<MeasureEntry> getMeasureEntrys() {
		return entries;
	}
	
	/**
	 * Calculates the belief for a given index	
	 * @param index the index 
	 * @return the belief for the index
	 */
	public double calculateBelief(int index) {
		double belief = 0.0;
		for (MeasureEntry entry : this.entries) {
			List<Integer> valueList = entry.getValues();
			if (valueList.get(index) == 1) {
				//for belief of a single entry, all other positions must be "0"
				boolean isBeliefMeasureEntry = true;
				for (int i=0; i<valueList.size(); i++) {
					if (i != index && valueList.get(i) != 0) {
						isBeliefMeasureEntry = false;
					}
				}
				if (isBeliefMeasureEntry)
					belief = belief + entry.getPropability();
			}
		}
		return belief;
	}
	
	/**
	 * Calculates the plausability for a given index	
	 * @param index the index 
	 * @return the plausability for the index
	 */
	public double calculatePlausability(int index) {
		double plausability = 0.0;
		for (MeasureEntry entry : this.entries) {
			// sum up all the MeasuerEntrys that have a "1" at the index's position
			if (entry.getValues().get(index) == 1) {
				plausability = plausability + entry.getPropability();
			}
		}
		return plausability;
	}
	/**
	 * Calculates the doubt for a given index	
	 * @param index the index 
	 * @return the doubt for the index
	 */
	public double calculateDoubt(int index) {
		return 1 - this.calculatePlausability(index);
	}
	
	public String toString() {
		String retString = "";
		for (MeasureEntry entry: entries) {
			retString = retString + entry.toString();
		}
		return retString;
	}	

	/**
	 * Checks whether 2 Lists contain the same entries.
	 * @param list1 first list
	 * @param list2 second list
	 * @return {@link true} is lists contain same entries
	 */
	private boolean isSameEntry(List<Integer> list1, List<Integer> list2) {
		for (int i = 0; i < this.size; i++) {
			if (list1.get(i) != list2.get(i)) {
				return false;				
			}
		}
		return true;
	}
}