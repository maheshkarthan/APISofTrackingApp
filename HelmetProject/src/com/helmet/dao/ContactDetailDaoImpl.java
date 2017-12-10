package com.helmet.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.helmet.entity.ContactDetail;

@Repository("contactDetailDao")
public class ContactDetailDaoImpl implements ContactDetailDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addContactDetails(ContactDetail contactDetail) {
	
		System.out.println("add Contact detail");
		
		sessionFactory.getCurrentSession().saveOrUpdate(contactDetail);
		
		System.out.println("contactDetails:"+ contactDetail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactDetail> listContactDetails() {
		
		System.out.println("list Contact detail");
		
		return (List<ContactDetail>)sessionFactory.getCurrentSession().createCriteria(ContactDetail.class).list();
	}

	@Override
	public Integer updateContactDetail(ContactDetail contactDetail) {
		
		int status = 0;
		
		System.out.println("update Contact detail");
		
		/*Query qry = session.createQuery("update Product p set p.proName=?
				where p.productId=111");
					        qry.setParameter(0,"updated..");
					        int res = qry.executeUpdate();
		*/
		
		Query qry = sessionFactory.getCurrentSession().createQuery("update ContactDetail set emergency_contact1 =:emergency_contact1, "
				+ "emergency_contact2=:emergency_contact2 where cid=:cid");
		
		qry.setParameter("emergency_contact1", contactDetail.getEmergency_contact1());
		
		qry.setParameter("emergency_contact2", contactDetail.getEmergency_contact2());
		
		qry.setParameter("cid", contactDetail.getcId());
		
		status = qry.executeUpdate();
		
		/*sessionFactory.getCurrentSession().saveOrUpdate(contactDetail);*/
	
		return status;
	}

	@Override
	public void deleteContactDetail(ContactDetail contactList) {
	
		System.out.println("delete Contact detail");
	}

	@Override
	public ContactDetail getContactDetail(int userId_fk) {
		
		try {
		
			Query query = sessionFactory.getCurrentSession().createQuery("FROM ContactDetail cd WHERE cd.userId =:userId");
			
			query.setParameter("userId", userId_fk);
			
			return (ContactDetail) query.uniqueResult();
		
		} catch(Exception e) {
		
			e.printStackTrace();
		}
		
		return null;
	}

}
