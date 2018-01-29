package bpi2015;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import bpi2015.model.Case;

public class DataSet {

	private int number;
	private Map<Long, Case> cases = new HashMap<>();

	public DataSet(int i) {
		this.setNumber(i);
	}
	
	public void addCase(Case c){
		if(c.getCaseId()!=null)
			this.cases.put(c.getCaseId(), c);
	}
	
	public Long getMeanDuration(){
		Long counter=0L;
		for (Entry<Long,Case> e : this.cases.entrySet()){
			counter+=e.getValue().getDuration();
		}
		return counter/this.cases.size();
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	

	/**
	 * @return the cases
	 */
	public Map<Long, Case> getCases() {
		return cases;
	}

	/**
	 * @param cases the cases to set
	 */
	public void setCases(Map<Long, Case> cases) {
		this.cases = cases;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataSet [number=" + number + ", cases="+this.cases.size()+ "]";
	}
	
	

}
