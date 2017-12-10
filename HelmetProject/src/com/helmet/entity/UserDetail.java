package com.helmet.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Anant
 *
 */

@Entity
@Table(name="user_detail")
public class UserDetail implements Serializable{

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@TableGenerator(name = "userprofile_TABLE_GEN", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "userprofile_TABLE_GEN")
	@Column(name = "userid")
	private Integer userId;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "user_name")
	private String userName;

	@Column(name = "mobile_no")
	private String mobileNo;
	
	@Column(name = "password")
	private String password;

	@Column(name="user_email_id")
	private String userEmailId;
	
	@Column(name = "otp")
	private String otp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable = false, length = 19)
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on", nullable = false, length = 19)
	private Date updatedOn;
	
	@Column(name = "active_status")
	private String activeStatus;

	public Integer getUserId() {
		
		return userId;
	}

	public void setUserId(Integer userId) {
		
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

	public Date getCreatedOn() {
		
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		
		this.updatedOn = updatedOn;
	}

	public String getActiveStatus() {
		
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {

		return "UserDetail [userId=" + userId + ", fullName=" + fullName + ", userName=" + userName + ", mobileNo="
				+ mobileNo + ", password=" + password + ", userEmailId=" + userEmailId + ", otp=" + otp + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", activeStatus=" + activeStatus + "]";
	}
	
	
}
