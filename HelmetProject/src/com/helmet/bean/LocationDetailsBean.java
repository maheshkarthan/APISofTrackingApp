package com.helmet.bean;

import java.io.Serializable;
import java.util.Date;

public class LocationDetailsBean implements Serializable{

	private static final long serialVersionUID = -723583058586873479L;

	private String userId_fk;
	
	private String lattitude;
	
	private String longitude;
	
	private Date timeStamp;

	public String getUserId_fk() {
		
		return userId_fk;
	}

	public void setUserId_fk(String userId_fk) {
		
		this.userId_fk = userId_fk;
	}

	public String getLattitude() {
		
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		
		this.lattitude = lattitude;
	}

	public String getLongitude() {
		
		return longitude;
	}

	public void setLongitude(String longitude) {
		
		this.longitude = longitude;
	}

	public Date getTimeStamp() {
		
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		
		this.timeStamp = timeStamp;
	}

}
