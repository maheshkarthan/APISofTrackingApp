package com.helmet.dao;

import java.util.List;

import com.helmet.entity.ContactDetail;

public interface ContactDetailDao {
	
	public void addContactDetails(ContactDetail contactDetail);
	
	public List<ContactDetail> listContactDetails();
	
	public Integer updateContactDetail(ContactDetail contactDetail);
	
	public void deleteContactDetail(ContactDetail contactList);

	public ContactDetail getContactDetail(int userId_fk);

}
