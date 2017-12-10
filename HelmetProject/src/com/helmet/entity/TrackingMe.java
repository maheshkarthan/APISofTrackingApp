package com.helmet.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author Anant
 *
 */
@Entity
@Table(name="tracking_me")
public class TrackingMe implements Serializable{

	private static final long serialVersionUID = -723583058586873479L;
	

	@Id
	@TableGenerator(name = "trackingme_TABLE_GEN", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "trackingme_TABLE_GEN")
	@Column(name="id")
	private Integer id;
	
	@Column(name="my_mobile_no")
	private String myMobileNo;
	
	@Column(name="friends_mobile_no")
	private String friendsMobileNo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId_fk",referencedColumnName="userid") 
	private UserDetail userIdFk;
	
	@Column(name="tracking_status")
	private String trackingStatus;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {
		
		this.id = id;
	}

	public String getMyMobileNo() {
		
		return myMobileNo;
	}

	public void setMyMobileNo(String myMobileNo) {
		
		this.myMobileNo = myMobileNo;
	}

	public String getFriendsMobileNo() {
		
		return friendsMobileNo;
	}

	public void setFriendsMobileNo(String friendsMobileNo) {
		
		this.friendsMobileNo = friendsMobileNo;
	}

	public UserDetail getUserIdFk() {
		
		return userIdFk;
	}

	public void setUserIdFk(UserDetail userIdFk) {
		
		this.userIdFk = userIdFk;
	}

	public String getTrackingStatus() {
		
		return trackingStatus;
	}

	public void setTrackingStatus(String trackingStatus) {
		
		this.trackingStatus = trackingStatus;
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

	@Override
	public String toString() {
		
		return "TrackingMe [id=" + id + ", myMobileNo=" + myMobileNo + ", friendsMobileNo=" + friendsMobileNo
				+ ", userIdFk=" + userIdFk + ", trackingStatus=" + trackingStatus + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + "]";
	}
	
}
