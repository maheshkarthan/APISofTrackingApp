package com.helmet.restcontroller;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.helmet.entity.LocationDetail;
import com.helmet.entity.TrackingMe;
import com.helmet.entity.UserDetail;
import com.helmet.service.LocationDetailService;
import com.helmet.service.TrackingMeService;
import com.helmet.service.UserDetailService;
import com.helmet.util.Constants;

/**
 * @author Anant
 *
 */
@RestController
@RequestMapping("helmetuser")
public class TrackingRestController {

	@Autowired
	TrackingMeService trackingMeService;

	@Autowired
	UserDetailService userDetailService;

	@Autowired
	LocationDetailService locationDetailService;

	@RequestMapping(value = "requestTracking", method = RequestMethod.POST)
	public @ResponseBody String requesttracking(
			@RequestHeader(value = "myMobileNumber", required = true) String myMobileNumber,
			@RequestHeader(value = "friendMobileNumber", required = true) String friendMobileNumber) {

		System.out.println("RequestTracking: \n myMobileNumber: " + myMobileNumber
				+ "\nfriendMobileNumber: " + friendMobileNumber);

		TrackingMe trackingMe = new TrackingMe();

		JSONObject jsonObject = new JSONObject();

		UserDetail userDetail = userDetailService.getUserByMobNo(myMobileNumber);

		boolean isFriendExist = userDetailService.isUserExist(friendMobileNumber);

		if (!isFriendExist) {

			try {

				trackingMe.setFriendsMobileNo(friendMobileNumber);

				trackingMe.setMyMobileNo(myMobileNumber);

				trackingMe.setUpdatedOn(new Date());

				trackingMe.setTrackingStatus(Constants.Status_Requestd);

				trackingMe.setCreatedOn(new Date());

				trackingMe.setUserIdFk(userDetail);

				trackingMeService.addTrackMeDetails(trackingMe);

				jsonObject.put(Constants.Status, Constants.Status_Success);

				jsonObject.put(Constants.StatusCode, Constants.Status_OK);

			} catch (Exception e) {

				try {

					jsonObject.put(Constants.Status, Constants.Status_Fail);

					jsonObject.put(Constants.StatusCode, "203");

				} catch (Exception e1) {

					e1.printStackTrace();
				}

				e.printStackTrace();
			}

		}

		return jsonObject.toString().trim();
	}


	
	@RequestMapping(value = "acceptTracking", method = RequestMethod.POST)
	public @ResponseBody String acceptTracking(
			@RequestHeader(value = "myMobileNumber", required = true) String myMobileNumber,
			@RequestHeader(value = "friendMobileNumber", required = true) String friendMobileNumber) {

		System.out.println("acceptTracking: \nmyMobileNumber: " + myMobileNumber
				+ "\nfriendMobileNumber: " + friendMobileNumber);

		TrackingMe trackingMe = new TrackingMe();

		JSONObject jsonObject = new JSONObject();

		UserDetail userDetail = userDetailService.getUserByMobNo(myMobileNumber);

		boolean isFriendExist = userDetailService.isUserExist(friendMobileNumber);

		if (!isFriendExist) {

			try {
				
				trackingMe = trackingMeService.getFriendTrackingDetail(friendMobileNumber);
				
				if(trackingMe!=null){
					
					trackingMe.setTrackingStatus(Constants.Status_Accepted);
					
					trackingMe.setUpdatedOn(new Date());
					
					trackingMe.setUserIdFk(userDetail);
					
					trackingMeService.addTrackMeDetails(trackingMe);
				}
				

				jsonObject.put(Constants.Status, Constants.Status_Success);

				jsonObject.put(Constants.StatusCode, Constants.Status_OK);

			} catch (Exception e) {

				try {

					jsonObject.put(Constants.Status, Constants.Status_Fail);

					jsonObject.put(Constants.StatusCode, "203");

				} catch (Exception e1) {

					e1.printStackTrace();
				}

				e.printStackTrace();
			}

		}

		return jsonObject.toString().trim();
	}
	
	
	
	
	/**
	 * method return the List of user you are tracking
	 * 
	 * @param userId
	 * 
	 * @return List of user
	 */
	@RequestMapping(value = "friendsTrackingYou", method = RequestMethod.POST)
	public @ResponseBody String friendsTrackingYou(@RequestHeader(value = "userId") String userId) {
		
		JSONArray jsonArray = new JSONArray();

		JSONObject jsonObject = new JSONObject();

		int userIdInt = Integer.parseInt(userId);

		List<TrackingMe> trackersList = trackingMeService.getTrackingFriendList(userIdInt);

		UserDetail userDetail;

		try {

			jsonObject.put(Constants.Status, Constants.Status_Success);

			jsonObject.put(Constants.StatusCode, Constants.Status_OK);

			for (int i = 0; i < trackersList.size(); i++) {

				userDetail = userDetailService.getUserByMobNo(trackersList.get(i).getFriendsMobileNo().toString());

				if (userDetail != null) {

					JSONObject jsonObject1 = new JSONObject();

					jsonObject1.put("userId", userDetail.getUserId());

					jsonObject1.put("fullName", userDetail.getFullName());

					jsonObject1.put("userName", userDetail.getUserName());

					jsonObject1.put("mobileNo", userDetail.getMobileNo());

					jsonObject1.put("userEmailId", userDetail.getUserEmailId());
					
					jsonObject1.put("status", userDetail.getUserEmailId());

					jsonArray.put(jsonObject1);
				}
			}

			jsonObject.put("List", jsonArray.toString());

		} catch (JSONException ex) {

			ex.printStackTrace();

			try {

				jsonObject.put(Constants.Status_Exception, "Exception");

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return jsonObject.toString().trim();
	}

	/**
	 * List of user whome I am tracking
	 * 
	 * @param mobileNo
	 * 
	 * @return List of user who are tracking my number
	 */
	@RequestMapping(value = "friendsYouAreRequested", method = RequestMethod.POST)
	public @ResponseBody String friendsYouAreRequested(
			@RequestHeader(value = "mobileNo") String mobileNo) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			List<TrackingMe> trackersList = trackingMeService.getFriendsYouAreTrackingList(mobileNo);
			if (!trackersList.isEmpty()) {
				for (TrackingMe trackingMe : trackersList) {
					jsonArray.put(trackingMe.getFriendsMobileNo());
				}
				jsonObject.put(Constants.Status, Constants.Status_Success);
				jsonObject.put("requestedContactsList", jsonArray);
				jsonObject.put(Constants.StatusCode, Constants.Status_OK);
			} else {
				jsonObject.put(Constants.Status, Constants.Status_Fail);
				jsonObject.put("requestedContactsList", jsonArray);
				jsonObject.put(Constants.StatusCode, "201");
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
			try {
				jsonObject.put(Constants.Status, Constants.Status_Exception);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject.toString().trim();
	}
	
	@RequestMapping(value = "requestsRecieved", method = RequestMethod.POST)
	public @ResponseBody String requestsRecieved(
			@RequestHeader(value = "mobileNo") String mobileNo) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			List<TrackingMe> trackersList = trackingMeService.getRequestsRecievedList(mobileNo);
			if (!trackersList.isEmpty()) {
				for (TrackingMe trackingMe : trackersList) {
					jsonArray.put(trackingMe.getMyMobileNo());
				}
				jsonObject.put(Constants.Status, Constants.Status_Success);
				jsonObject.put("requestsRecievedList", jsonArray);
				jsonObject.put(Constants.StatusCode, Constants.Status_OK);
			} else {
				jsonObject.put(Constants.Status, Constants.Status_Fail);
				jsonObject.put("requestsRecievedList", jsonArray);
				jsonObject.put(Constants.StatusCode, "201");
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
			try {
				jsonObject.put(Constants.Status, Constants.Status_Exception);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject.toString().trim();
	}
	
	@RequestMapping(value = "requestAcceptedFriends", method = RequestMethod.POST)
	public @ResponseBody String requestAcceptedFriends(
			@RequestHeader(value = "myMobileNumber") String myMobileNumber) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			List<TrackingMe> trackersList = trackingMeService.getRequestAcceptedbyMeList(myMobileNumber);
			if (!trackersList.isEmpty()) {
				for (TrackingMe trackingMe : trackersList) {
					jsonArray.put(trackingMe.getFriendsMobileNo());
				}
				jsonObject.put(Constants.Status, Constants.Status_Success);
				jsonObject.put("requestAcceptedFriendsList", jsonArray);
				jsonObject.put(Constants.StatusCode, Constants.Status_OK);
			} else {
				jsonObject.put(Constants.Status, Constants.Status_Fail);
				jsonObject.put("requestAcceptedFriendsList", jsonArray);
				jsonObject.put(Constants.StatusCode, "201");
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
			try {
				jsonObject.put(Constants.Status, Constants.Status_Exception);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject.toString().trim();
	}
	
	@RequestMapping(value = "requestFriendsTrackingMe", method = RequestMethod.POST)
	public @ResponseBody String requestFriendsTrackingMe(
			@RequestHeader(value = "myMobileNumber") String myMobileNumber) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			List<TrackingMe> trackersList = trackingMeService.getRequestAcceptedFriendsList(myMobileNumber);
			if (!trackersList.isEmpty()) {
				for (TrackingMe trackingMe : trackersList) {
					jsonArray.put(trackingMe.getMyMobileNo());
				}
				jsonObject.put(Constants.Status, Constants.Status_Success);
				jsonObject.put("friendsTrackingMeList", jsonArray);
				jsonObject.put(Constants.StatusCode, Constants.Status_OK);
			} else {
				jsonObject.put(Constants.Status, Constants.Status_Fail);
				jsonObject.put("friendsTrackingMeList", jsonArray);
				jsonObject.put(Constants.StatusCode, "201");
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
			try {
				jsonObject.put(Constants.Status, Constants.Status_Exception);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject.toString().trim();
	}
	
}
