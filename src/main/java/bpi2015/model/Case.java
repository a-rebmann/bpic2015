package bpi2015.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A case
 * @author 
 *
 */
public class Case {
	
	private Long caseId;
	
	private List<Activity> activities = new ArrayList<>();
	
	public void addActivity(Activity a){
		this.activities.add(a);
	}
	
	

	public Case(Long caseId) {
		this.caseId = caseId;
	}



	public Case(Long caseId, List<Activity> activities) {
		this.caseId = caseId;
		this.activities = activities;
	}



	public Case() {}



	/**
	 * @return the caseId
	 */
	public Long getCaseId() {
		return caseId;
	}

	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	/**
	 * @return the activities
	 */
	public List<Activity> getActivities() {
		return activities;
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public Long getDuration(){
		Long min = Long.MAX_VALUE;
		Long max = Long.MIN_VALUE;
		for(Activity a : this.activities){
			if(a.getTimestamp()<min)
				min=a.getTimestamp();
			if(a.getTimestamp()>max)
				max=a.getTimestamp();
		}
		return max-min;
	}
	
	public Long getStart(){
		Long min = Long.MAX_VALUE;
		for(Activity a : this.activities){
			if(a.getTimestamp()<min)
				min=a.getTimestamp();
		}
		return min;
	}
	
	public Long getEnd(){
		Long max = Long.MIN_VALUE;
		for(Activity a : this.activities){
			if(a.getTimestamp()>max)
				max=a.getTimestamp();
		}
		return max;
	}
	
	
	

}
