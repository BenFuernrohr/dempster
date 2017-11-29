package dempster;

import java.util.ArrayList;
import java.util.List;

public class MeasureEntry{
	
	private int size;
	private List<Integer> values = new ArrayList<Integer>();
	private double propability;
	
	public MeasureEntry(int size, List<Integer> values, double propability) {
		this.size = size;
		this.values = values;
		this.propability = propability;
	}

	public int getSize() {
		return size;
	}

	public List<Integer> getValues() {
		return values;
	}

	public double getPropability() {
		return propability;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}

	public void setPropability(double propability) {
		this.propability = propability;
	}

	public String toString() {
		String retString = "Set: ";
		for (int i = 0; i < size; i++) {
			retString = retString + (values.get(i)+ " ");
		}
		retString = retString + ("Propability: " + propability + "\n");	
		return retString;
	}
}