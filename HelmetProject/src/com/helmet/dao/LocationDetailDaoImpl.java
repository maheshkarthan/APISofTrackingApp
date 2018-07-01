package com.helmet.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.helmet.entity.LocationDetail;

@Repository("locationDetailDao")
public class LocationDetailDaoImpl implements LocationDetailDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void addLocationDetails(LocationDetail locationDetail) {

		System.out.println("add Locaion details");

		sessionFactory.getCurrentSession().save(locationDetail);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationDetail> listLocationDetail() {

		System.out.println("list Locaion details");

		return ((List<LocationDetail>) sessionFactory.getCurrentSession().createCriteria(LocationDetail.class).list());

	}

	@Override
	public boolean updateLocationDetail(LocationDetail locationDetail) {

		System.out.println("update Locaion details");

		boolean isUpdated = false;

		try {

			sessionFactory.getCurrentSession().saveOrUpdate(locationDetail);

			isUpdated = true;

		} catch (Exception e) {

			isUpdated = true;
		}

		return isUpdated;
	}

	@Override
	public void deleteLocationDetail(LocationDetail locationDetail) {

		System.out.println("delete Locaion details");

	}

	@Override
	public LocationDetail getUserLocation(String mobileNo) {

		Query query = sessionFactory.getCurrentSession()
				.createQuery("select tm FROM LocationDetail ld WHERE lm.mobileNo =:mobileNo");

		return (LocationDetail) query.setString("mobileNo", mobileNo).uniqueResult();

	}
	
	
	@Override
	public List<LocationDetail> listLocationDetailByDate(Date tripDate , String mobNo){
		
		List<LocationDetail> list =  new ArrayList<LocationDetail>();
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LocationDetail.class).add(Restrictions.eq("mobileNo", mobNo)).add(Restrictions.between("clientTime", tripDate, tripDate)).addOrder(Order.asc("clientTime"));

		list = criteria.list();
		
		
		return list;
	}
	

}
