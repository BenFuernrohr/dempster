package dempster;

import java.util.ArrayList;
import java.util.List;

public class Measure{
	
	private int size;
	private List<MeasureEntry> entries = new ArrayList<MeasureEntry>();
	private MeasureEntry omegaEntry;
	
	public Measure(int size) {
		this.size = size;
		List<Integer> omegaEntryList = new ArrayList<Integer>();
		for (int i = 0 ; i < size; i++) {
			omegaEntryList.add(1);			
		}
		
		this.omegaEntry = new MeasureEntry(this.size, omegaEntryList, 1.0);
		this.entries.add(omegaEntry);
	}
	
	public void addEntry(List<Integer> entry, double propability) {
		//check if entry already exists
		for (MeasureEntry me : entries) {
			if (isSameEntry(me, entry)) {
				me.setPropability(me.getPropability() + propability);
				this.omegaEntry.setPropability(this.omegaEntry.getPropability()-propability);
				return;
			}			
		}
		//entry does not exist yet?
		entries.add(new MeasureEntry(size, entry, propability));
		this.omegaEntry.setPropability(this.omegaEntry.getPropability()-propability);
	}

	private boolean isSameEntry(MeasureEntry me, List<Integer> entry) {
		for (int i = 0; i < this.size; i++) {
			if (me.getValues().get(i) != entry.get(i)) {
				return false;				
			}
		}
		return true;
	}

	public String toString() {
		String retString = "";
		for (MeasureEntry entry: entries) {
			retString = retString + entry.toString();
		}
		return retString;
	}

	public List<MeasureEntry> getMeasureEntrys() {
		return entries;
	}
	
}