package edu.fau.group4.donateme;

import com.parse.ParseGeoPoint;

public class RequestObject {

	String orgName;
	String orgType;
	String requestType;
	String whatFor;
	String description;
	String website;
	String goal;
	String objectId;
	ParseGeoPoint geo;
	double distance;
	String howToHelp;
	byte[] imageArray;
	public RequestObject(String orgName, String orgType, String requestType, String whatFor, String description, String website, String goal, String objectId, ParseGeoPoint geo,double distance,String howToHelp, byte[] imageArray){
		super();
		this.orgName = orgName;
		this.orgType = orgType;
		this.requestType = requestType;
		this.whatFor = whatFor;
		this.description = description;
		this.website = website;
		this.goal = goal;
		this.objectId = objectId;
		this.geo = geo;
		this.distance = distance;
		this.howToHelp = howToHelp;
		this.imageArray = imageArray;
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
	public ParseGeoPoint getGeo(){
		return geo;
	}
	public double getDistance(){
		return distance;
	}	
	public String getHowToHelp(){
		return howToHelp;
	}
	public byte[] getImageArray(){
		return imageArray;
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
	public void setGeo(ParseGeoPoint geo){
		this.geo = geo;
	}
	public void setDistance(double distance){
		this.distance = distance;
	}
	public void setHowToHelp(String howToHelp){
		this.howToHelp = howToHelp;
	}
	public void setImageArray(byte[] imageArray){
		this.imageArray = imageArray;
	}
}
