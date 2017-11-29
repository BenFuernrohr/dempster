package dempster;

import java.util.ArrayList;
import java.util.List;

public class DempsterHandler{
	
	private int size;
	private List<Measure> measures = new ArrayList<Measure>();
	
	public DempsterHandler (int size) {
		this.size = size;
	}
	
	public Measure addMeasure() {
		Measure newMeasure = new Measure(size);
		this.measures.add(newMeasure);
		return newMeasure;
	}

	public void accumulateAllMeasures() {
		if (measures.size() < 2) {
			//no measures or just 1 left. Abort
			return;
		}
		else {
			//take 2 measures, remove them from list
			Measure measure1 = measures.get(0);
			Measure measure2 = measures.get(1);
			this.measures.remove(measure1);
			this.measures.remove(measure2);
			//accumulate measures, add to list
			this.measures.add(this.accumulateMeasures(measure1, measure2));
			this.accumulateAllMeasures();
		}
		
	}
	
	public Measure getFirstMeasure() {
		return this.measures.get(0);
	}

	private Measure accumulateMeasures(Measure measure1, Measure measure2) {
		
		Measure retMeasure = new Measure(this.size);
		double conflict = getConflict(measure1,measure2);

		if (conflict <= 0.99d)
		{
		    double correction = 1.0d/(1.0d-conflict);
		    List<MeasureEntry> entries1 = measure1.getMeasureEntrys();
		    List<MeasureEntry> entries2 = measure2.getMeasureEntrys();
	
		    for (MeasureEntry entry1 : entries1){
		    	for (MeasureEntry entry2 : entries2){
		    		List<Integer> intersection = getIntersection(entry1, entry2);
		    		double value = entry1.getPropability() * entry2.getPropability() * correction;
		    		if (value > 0.0d && !entryIsEmpty(intersection) &&!isOmegaEntry(intersection)) {
		    			retMeasure.addEntry(intersection, value);
		    		}
		    	}
		    }
		}
		return retMeasure;
	}

	private boolean isOmegaEntry(List<Integer> intersection) {
		for (Integer entry : intersection) {
			if (entry != 1) {
				return false;
			}
		}
		return true;
	}

	private boolean entryIsEmpty(List<Integer> intersection) {
		for (Integer entry : intersection) {
			if (entry != 0) {
				return false;
			}
		}
		return true;
	}

	private List<Integer> getIntersection(MeasureEntry entry1, MeasureEntry entry2) {
		List<Integer> retList = new ArrayList<Integer>();
		for(int i = 0; i<this.size; i++) {
			int i1 = entry1.getValues().get(i);
			int i2 = entry2.getValues().get(i);
			if ((i1 + i2) == 2) {
				retList.add(1);
			}
			else {
				retList.add(0);
			}
		}
		return retList;
	}

	private double getConflict(Measure measure1, Measure measure2) {
		double conflict = 0.0d;
		for (MeasureEntry me1: measure1.getMeasureEntrys())
		{
			for (MeasureEntry me2 : measure2.getMeasureEntrys()) {
				if (this.entryIsEmpty(this.getIntersection(me1, me2))) {
					conflict = conflict + (me1.getPropability() * me2.getPropability());
				}
			}
		}
		return conflict;
	}

	public void printAccumulatedMeasure() {
		//There should be only 1 measure left. Print it.
		if (!this.measures.isEmpty()){
			System.out.println(this.measures.get(0).toString());
		}
	}			
}
