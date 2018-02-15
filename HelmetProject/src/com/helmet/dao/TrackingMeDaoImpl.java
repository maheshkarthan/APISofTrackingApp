package com.helmet.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.helmet.entity.TrackingMe;

@Repository("trackingMeDao")
public class TrackingMeDaoImpl implements TrackingMeDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void addTrackMeDetails(TrackingMe trackingMe) {

		System.out.println("add track details");
		
		sessionFactory.getCurrentSession().saveOrUpdate(trackingMe);
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingMe> listTrackMeDetails() {
	
		System.out.println("list track details");
		
		return (List<TrackingMe>)sessionFactory.getCurrentSession().createCriteria(TrackingMe.class).list();
	
	}

	@Override
	public void updateTrackMeDetail(TrackingMe trackingMe) {
	
		System.out.println("update track details");
		
		sessionFactory.getCurrentSession().saveOrUpdate(trackingMe);
	}

	@Override
	public void deleteTrackMeDetail(TrackingMe trackingMe) {
	
		System.out.println("delete track details");
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingMe> getTrackingFriendList(int userIdInt) {
	
		Query query = sessionFactory.getCurrentSession().createQuery("select tm FROM TrackingMe tm WHERE tm.userIdFk =:userId");
		
		return query.setInteger("userId", userIdInt).list();
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingMe> getFriendsYouAreTrackingList(String mobileNo) {
		try {			
			Query query = sessionFactory.getCurrentSession().createQuery("FROM TrackingMe tm WHERE tm.myMobileNo =:myMobileNo and tm.trackingStatus =:Requested");
			query.setParameter("myMobileNo", mobileNo);
			query.setParameter("Requested", "Requested");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingMe> getRequestsRecievedList(String mobileNo) {
		try {			
			Query query = sessionFactory.getCurrentSession().createQuery("FROM TrackingMe tm WHERE tm.friendsMobileNo =:myMobileNo and tm.trackingStatus =:Requested");
			query.setParameter("myMobileNo", mobileNo);
			query.setParameter("Requested", "Requested");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingMe> getRequestAcceptedFriendsList(String mobileNo) {
		try {			
			Query query = sessionFactory.getCurrentSession().createQuery("FROM TrackingMe tm WHERE tm.friendsMobileNo =:myMobileNo and tm.trackingStatus =:Accepted");
			query.setParameter("myMobileNo", mobileNo);
			query.setParameter("Accepted", "Accepted");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TrackingMe getFriendTrackingDetail(String mobileNo) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("select tm FROM TrackingMe tm WHERE tm.myMobileNo =:myMobileNo");
		
		return(TrackingMe)query.setString("myMobileNo", mobileNo).uniqueResult();
	}


}
