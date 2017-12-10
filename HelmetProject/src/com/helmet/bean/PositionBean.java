package com.helmet.bean;

public class PositionBean {

	private String userId;
	
	private String  mobileNo;
	
	private String longitude;
	
	private String lattitude;
	
	private String clientTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobileNo() {
		
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		
		this.mobileNo = mobileNo;
	}

	public String getLongitude() {
		
		return longitude;
	}

	public void setLongitude(String longitude) {
		
		this.longitude = longitude;
	}

	public String getLattitude() {
		
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		
		this.lattitude = lattitude;
	}

	public String getClientTime() {
		
		return clientTime;
	}

	public void setClientTime(String clientTime) {
		
		this.clientTime = clientTime;
	}

	@Override
	public String toString() {
		return "PositionBean [userId=" + userId + ", mobileNo=" + mobileNo + ", longitude=" + longitude + ", lattitude="
				+ lattitude + ", clientTime=" + clientTime + "]";
	}
}
