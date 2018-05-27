package com.helmet.bean;

public class PositionBean {

	private String userId;
	private String usermobileno;
	private String longitude;
	private String latitude;
	private String clientTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsermobileno() {
		return usermobileno;
	}

	public void setUsermobileno(String usermobileno) {
		this.usermobileno = usermobileno;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getClientTime() {
		return clientTime;
	}

	public void setClientTime(String clientTime) {
		this.clientTime = clientTime;
	}

	@Override
	public String toString() {
		return "PositionBean [userId=" + userId + ", usermobileno=" + usermobileno + ", longitude=" + longitude + ", latitude=" + latitude + ", clientTime=" + clientTime + "]";
	}

}
