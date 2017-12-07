package dempster;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent an entry within a measure. Contains a List of values (0 or 1), a fixed size and the probability of the entry.
 */
public class MeasureEntry{
	
	/** size of the entry, should match that of the holding {@link Measure} */
	private int size;
	
	/** List of values, containing 0s and 1s */
	private List<Integer> values = new ArrayList<Integer>();
	
	/** probability of a single measure */
	private double probability;
	
	public MeasureEntry(int size, List<Integer> values, double probability) {
		this.size = size;
		this.values = values;
		this.probability = probability;
	}

	/** returns the List of values in the entry */
	public List<Integer> getValues() {
		return values;
	}

	/** returns the probability of the entry */
	public double getprobability() {
		return probability;
	}

	/** sets the List of values in the entry */
	public void setValues(List<Integer> values) {
		this.values = values;
	}

	/** sets the probability of the entry */
	public void setprobability(double probability) {
		this.probability = probability;
	}

	public String toString() {
		String retString = "Set: ";
		for (int i = 0; i < size; i++) {
			retString = retString + (values.get(i)+ " ");
		}
		retString = retString + ("probability: " + probability + "\n");	
		return retString;
	}
}