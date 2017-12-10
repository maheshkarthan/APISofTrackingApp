package com.helmet.bean;

import java.util.Date;

public class UserDetailBean {
	
	private String userId;
	
	private String fullName;
	
	private String userName;

	private String userEmailId;
	
	private String mobileNo;
	
	private String password;
	
	private String otp;
	
	private Date created_on;
	
	

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	public String getFullName() {
		
		return fullName;
	}

	public void setFullName(String fullName) {
		
		this.fullName = fullName;
	}

	public String getUserName() {
		
		return userName;
	}

	public void setUserName(String userName) {
		
		this.userName = userName;
	}
	
	
	public String getUserEmailId() {

		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		
		this.userEmailId = userEmailId;
	}

	public String getMobileNo() {
		
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		
		this.mobileNo = mobileNo;
	}

	public String getPassword() {
		
		return password;
	}

	public void setPassword(String password) {
		
		this.password = password;
	}

	public String getOtp() {
		
		return otp;
	}

	public void setOtp(String otp) {
		
		this.otp = otp;
	}

	public Date getCreated_on() {
		
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		
		this.created_on = created_on;
	}

}
