package bpi2015.model;

/**
 * An activity
 * @author 
 *
 */
public class Activity {
	
	private Long caseId;
	private String name;
	private Long resource;
	private Long monitoring;
	private Long responsible;
	private Long timestamp;
	private String actionCode;
	private String activityNameEN;
	private String[] kindsOfPermit;
	private String status;
	
	
	
	public Activity(Long caseId, String name, Long resource, Long timestamp, String actionCode, String activityNameEN) {
		this.setCaseId(caseId);
		this.name = name;
		this.resource = resource;
		this.timestamp = timestamp;
		this.actionCode = actionCode;
		this.activityNameEN = activityNameEN;
	}
	
	
	public Activity(Long caseId, String name, Long resource, Long monitoring, Long responsible, Long timestamp,
			String actionCode, String activityNameEN, String[] kindsOfPermit, String status) {
		super();
		this.caseId = caseId;
		this.name = name;
		this.resource = resource;
		this.monitoring = monitoring;
		this.responsible = responsible;
		this.timestamp = timestamp;
		this.actionCode = actionCode;
		this.activityNameEN = activityNameEN;
		this.kindsOfPermit = kindsOfPermit;
		this.status = status;
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
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the monitoring
	 */
	public Long getMonitoring() {
		return monitoring;
	}


	/**
	 * @param monitoring the monitoring to set
	 */
	public void setMonitoring(Long monitoring) {
		this.monitoring = monitoring;
	}


	/**
	 * @return the responsible
	 */
	public Long getResponsible() {
		return responsible;
	}


	/**
	 * @param responsible the responsible to set
	 */
	public void setResponsible(Long responsible) {
		this.responsible = responsible;
	}
	
	
	
	
	
	
	
	
	

}
