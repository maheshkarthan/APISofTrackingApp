package com.helmet.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Anant
 *
 */
@Entity
@Table(name = "location_detail")
public class LocationDetail {

	@Id
	@TableGenerator(name = "locationdetail_TABLE_GEN", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "locationdetail_TABLE_GEN")
	@Column(name = "locid")
	private Integer locId;
	
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId_fk",referencedColumnName="userid") 
	private UserDetail userIdFk;
	
	@Column(name="mobile_no")
	private String mobileNo;
	
	@Column(name="lattitude")
	private String lattitude;
	
	@Column(name="longitude")
	private String longitude;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable = true, length = 19)
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on", nullable = true, length = 19)
	private Date updatedOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "client_time", nullable= true, length = 19)
	private Date clientTime;

	
	public UserDetail getUserId_fk() {

		return userIdFk;
	}

	public void setUserId_fk(UserDetail userIdFk) {
	
		this.userIdFk = userIdFk;
	}
	
	public String getMobileNo() {
		
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		
		this.mobileNo = mobileNo;
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

	public Integer getLocId() {
		
		return locId;
	}

	public void setLocId(Integer locId) {
		
		this.locId = locId;
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

	public Date getClientTime() {
		
		return clientTime;
	}

	public void setClientTime(Date clientTime) {
		
		this.clientTime = clientTime;
	}

	@Override
	public String toString() {
		
		return "LocationDetail [locId=" + locId + ", userId_fk=" + userIdFk + ", mobileNo=" + mobileNo + ", lattitude="
				+ lattitude + ", longitude=" + longitude + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", clientTime=" + clientTime + "]";
	}
	
}
