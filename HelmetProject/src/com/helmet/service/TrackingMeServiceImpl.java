package com.helmet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.helmet.dao.TrackingMeDao;
import com.helmet.entity.TrackingMe;

@Service("trackingMeService")
public class TrackingMeServiceImpl implements TrackingMeService {

	@Autowired
	TrackingMeDao trackingMeDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTrackMeDetails(TrackingMe trackingMe) {
		
		trackingMeDao.addTrackMeDetails(trackingMe);
	
	}

	@Override
	@Transactional
	public List<TrackingMe> listTrackMeDetails() {
	
		return trackingMeDao.listTrackMeDetails();
	}

	@Override
	@Transactional
	public void updateTrackMeDetail(TrackingMe trackingMe) {
		
		trackingMeDao.updateTrackMeDetail(trackingMe);
	}

	@Override
	@Transactional
	public void deleteTrackMeDetail(TrackingMe trackingMe) {
		
		System.out.println("delete track");
	}

	@Override
	@Transactional
	public List<TrackingMe> getTrackingFriendList(int userIdInt) {
		
		return trackingMeDao.getTrackingFriendList(userIdInt);
	}

	@Override
	@Transactional
	public List<TrackingMe> getFriendsYouAreTrackingList(String mobileNo) {
		return trackingMeDao.getFriendsYouAreTrackingList(mobileNo);
	}
	
	@Override
	@Transactional
	public List<TrackingMe> getRequestsRecievedList(String mobileNo) {
		return trackingMeDao.getRequestsRecievedList(mobileNo);
	}

	@Override
	@Transactional
	public List<TrackingMe> getRequestAcceptedFriendsList(String mobileNo) {
		return trackingMeDao.getRequestAcceptedFriendsList(mobileNo);
	}
	
	@Override
	@Transactional
	public List<TrackingMe> getRequestAcceptedbyMeList(String mobileNo) {
		return trackingMeDao.getRequestAcceptedbyMeList(mobileNo);
	}
	
	@Override
	@Transactional
	public TrackingMe getFriendTrackingDetail(String mobileNo) {
		
		return trackingMeDao.getFriendTrackingDetail(mobileNo);
	}

}
