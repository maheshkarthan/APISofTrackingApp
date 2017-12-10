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

	@RequestMapping(value = "RequestTracking", method = RequestMethod.POST)
	public @ResponseBody String requesttracking(@RequestHeader(value = "userId", required = true) String userId,
			@RequestHeader(value = "myMobileNumber", required = true) String myMobileNumber,
			@RequestHeader(value = "friendMobileNumber", required = true) String friendMobileNumber) {

		System.out.println("RequestTracking: \nuserId: " + userId + " \nmyMobileNumber: " + myMobileNumber
				+ "\nfriendMobileNumber: " + friendMobileNumber);

		TrackingMe trackingMe = new TrackingMe();

		JSONObject jsonObject = new JSONObject();

		UserDetail userDetail = userDetailService.getUserById(Integer.parseInt(userId));

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
	public @ResponseBody String acceptTracking(@RequestHeader(value = "userId", required = true) String userId,
			@RequestHeader(value = "myMobileNumber", required = true) String myMobileNumber,
			@RequestHeader(value = "friendMobileNumber", required = true) String friendMobileNumber) {

		System.out.println("acceptTracking: \nuserId: " + userId + " \nmyMobileNumber: " + myMobileNumber
				+ "\nfriendMobileNumber: " + friendMobileNumber);

		TrackingMe trackingMe = new TrackingMe();

		JSONObject jsonObject = new JSONObject();

		UserDetail userDetail = userDetailService.getUserById(Integer.parseInt(userId));

		boolean isFriendExist = userDetailService.isUserExist(friendMobileNumber);

		if (!isFriendExist) {

			try {
				
				trackingMe = trackingMeService.getFriendTrackingDetail(friendMobileNumber);
				
				if(trackingMe!=null){
					
					trackingMe.setTrackingStatus(Constants.Status_Accepted);
					
					trackingMe.setCreatedOn(new Date());
					
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
	@RequestMapping(value = "friendsYouAreTracking", method = RequestMethod.POST)
	public @ResponseBody String friendsYouAreTracking(@RequestHeader(value = "mobileNo") String mobileNo) {

		JSONArray jsonArray = new JSONArray();

		JSONObject jsonObject = new JSONObject();

		UserDetail userDetail;

		List<TrackingMe> trackersList = trackingMeService.getFriendsYouAreTrackingList(mobileNo);

		try {

			jsonObject.put(Constants.Status, Constants.Status_Success);

			jsonObject.put(Constants.StatusCode, Constants.Status_OK);

			for (int i = 0; i < trackersList.size(); i++) {

				userDetail = userDetailService.getUserByMobNo(trackersList.get(i).getMyMobileNo().toString());

				if (userDetail != null) {

					JSONObject jsonObject1 = new JSONObject();

					jsonObject1.put("userId", userDetail.getUserId());

					jsonObject1.put("fullName", userDetail.getFullName());

					jsonObject1.put("userName", userDetail.getUserName());

					jsonObject1.put("mobileNo", userDetail.getMobileNo());

					jsonObject1.put("userEmailId", userDetail.getUserEmailId());

					try {

						LocationDetail locdt = locationDetailService.getUserLocation(userDetail.getMobileNo());

						jsonObject1.put("lattitude", locdt.getLattitude());

						jsonObject1.put("longitude", locdt.getLongitude());

						jsonObject1.put("lattitude", locdt.getUpdatedOn());

					} catch (Exception e1) {

						e1.printStackTrace();

					}

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
}
