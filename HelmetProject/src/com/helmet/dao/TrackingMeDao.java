package com.helmet.dao;

import java.util.List;

import com.helmet.entity.TrackingMe;

public interface TrackingMeDao {

	public void addTrackMeDetails(TrackingMe trackingMe);
	
	public List<TrackingMe> listTrackMeDetails();
	
	public void updateTrackMeDetail(TrackingMe trackingMe);
	
	public void deleteTrackMeDetail(TrackingMe trackingMe);

	public List<TrackingMe> getTrackingFriendList(int userIdInt);

	public List<TrackingMe> getFriendsYouAreTrackingList(String mobileNo);
	
	public List<TrackingMe> getRequestsRecievedList(String mobileNo);
	
	public List<TrackingMe> getRequestAcceptedFriendsList(String mobileNo);
	
	public TrackingMe getFriendTrackingDetail(String mobileNo);

}
