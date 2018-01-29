package bpi2015.model;

public class Activity {
	
	private Long caseId;
	private String name;
	private Long resource;
	private Long timestamp;
	private String actionCode;
	private String activityNameEN;
	private String[] kindsOfPermit;
	
	
	
	public Activity(Long caseId, String name, Long resource, Long timestamp, String actionCode, String activityNameEN) {
		this.setCaseId(caseId);
		this.name = name;
		this.resource = resource;
		this.timestamp = timestamp;
		this.actionCode = actionCode;
		this.activityNameEN = activityNameEN;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the resource
	 */
	public Long getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(Long resource) {
		this.resource = resource;
	}
	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the actionCode
	 */
	public String getActionCode() {
		return actionCode;
	}
	/**
	 * @param actionCode the actionCode to set
	 */
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	/**
	 * @return the activityNameEN
	 */
	public String getActivityNameEN() {
		return activityNameEN;
	}
	/**
	 * @param activityNameEN the activityNameEN to set
	 */
	public void setActivityNameEN(String activityNameEN) {
		this.activityNameEN = activityNameEN;
	}
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
	 * @return the kindsOfPermit
	 */
	public String[] getKindsOfPermit() {
		return kindsOfPermit;
	}
	/**
	 * @param kindsOfPermit the kindsOfPermit to set
	 */
	public void setKindsOfPermit(String[] kindsOfPermit) {
		this.kindsOfPermit = kindsOfPermit;
	}
	
	public String getKindsOfPermitString() {
		String res = "";
		for (int i = 0;i<this.kindsOfPermit.length;i++){
			res+=(this.kindsOfPermit[i]+"#");
		}
		return res;
	}
	
	

}
