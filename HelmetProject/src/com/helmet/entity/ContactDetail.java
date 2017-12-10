package com.helmet.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "contact_list")
public class ContactDetail implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@TableGenerator(name = "contactdetaillist_TABLE_GEN", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "contactdetaillist_TABLE_GEN")
	@Column(name = "cid")
	private Integer cId;

	@OneToOne(targetEntity = UserDetail.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId_fk", referencedColumnName = "userid")
	private UserDetail userId_fk;

	@Column(name = "emergency_contact1")
	private String emergency_contact1;

	@Column(name = "emergency_contact2")
	private String emergency_contact2;


	
	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public UserDetail getUserId_fk() {

		return userId_fk;

	}

	public void setUserId_fk(UserDetail userId_fk) {

		this.userId_fk = userId_fk;

	}

	public String getEmergency_contact1() {

		return emergency_contact1;
	}

	public void setEmergency_contact1(String emergency_contact1) {

		this.emergency_contact1 = emergency_contact1;
	}

	public String getEmergency_contact2() {

		return emergency_contact2;
	}

	public void setEmergency_contact2(String emergency_contact2) {

		this.emergency_contact2 = emergency_contact2;
	}

	@Override
	public String toString() {

		return "ContactDetail [cId=" + cId + ", userId_fk=" + userId_fk + ", emergency_contact1=" + emergency_contact1
				+ ", emergency_contact2=" + emergency_contact2 + "]";
	}

}
