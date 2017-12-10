package com.helmet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.helmet.dao.ContactDetailDao;
import com.helmet.entity.ContactDetail;

@Service("contactDetailService")
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class ContactDetailServiceImpl implements ContactDetailService{
	
	@Autowired
	ContactDetailDao contactDetailDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addContactDetails(ContactDetail contactDetail) {
	
		contactDetailDao.addContactDetails(contactDetail);
	}

	@Override
	@Transactional
	public List<ContactDetail> listContactDetails() {
		
		return (List<ContactDetail>)contactDetailDao.listContactDetails();
	}

	@Override
	@Transactional
	public Integer updateContactDetail(ContactDetail contactDetail) {

		return contactDetailDao.updateContactDetail(contactDetail);
	
	}

	@Override
	@Transactional
	public void deleteContactDetail(ContactDetail contactList) {
	
		contactDetailDao.deleteContactDetail(contactList);
	}

	@Override
	public ContactDetail getContactDetail(int userId_fk) {
	
		return contactDetailDao.getContactDetail(userId_fk);
	}
	
}
