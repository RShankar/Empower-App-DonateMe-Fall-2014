package edu.fau.group4.donateme;

public class RequestObject {

	String orgName;
	String orgType;
	String requestType;
	String whatFor;
	String description;
	String website;
	String goal;
	String objectId;
	public RequestObject(String orgName, String orgType, String requestType, String whatFor, String description, String website, String goal, String objectId){
		super();
		this.orgName = orgName;
		this.orgType = orgType;
		this.requestType = requestType;
		this.whatFor = whatFor;
		this.description = description;
		this.website = website;
		this.goal = goal;
		this.objectId = objectId;
		
	}
	public String getOrgName(){
		return orgName;
	}
	public String getOrgType(){
		return orgType;
	}
	public String getRequestType(){
		return requestType;		
	}
	public String getWhatFor(){
		return whatFor;
	}
	public String getDescription(){
		return description;
	}
	public String getWebsite(){
		return website;
	}
	public String getGoal(){
		return goal;
	}
	public String getObjectId(){
		return objectId;
	}
	
	public void setOrgName(String orgName){
		this.orgName = orgName;
	}
	public void setOrgType(String orgType){
		this.orgType = orgType;
	}
	public void setRequestType(String requestType){
		this.requestType = requestType;		
	}
	public void setWhatFor(String whatFor){
		this.whatFor = whatFor;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setWebsite(String website){
		this.website = website;
	}
	public void setGoal(String goal){
		this.goal = goal;
	}
	public void setObjectId(String objectId){
		this.objectId = objectId;
	}
	
}
