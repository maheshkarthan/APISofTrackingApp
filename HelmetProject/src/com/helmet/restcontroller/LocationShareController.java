package com.helmet.restcontroller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.helmet.entity.LocationDetail;
import com.helmet.service.ContactDetailService;
import com.helmet.service.LocationDetailService;
import com.helmet.service.UserDetailService;
import com.helmet.util.Constants;

@RestController
public class LocationShareController {

	@Autowired
	LocationDetailService locationDetailService;
	
	@Autowired
	ContactDetailService contactDetailService;

	@Autowired
	UserDetailService userDetailService;
	
	@RequestMapping(value = "helmetuser/sharelocation", method = RequestMethod.POST)
	public @ResponseBody String sharelocationDetail(@RequestHeader(value = "userid", required = true) String userId,
			@RequestHeader(value = "mobileno", required = true) String mobileNo,
			@RequestHeader(value = "friendMobileNo", required = true) String friendMobileNo) {
		
		JSONObject jsonObject = new JSONObject();
		
		try {
		
			LocationDetail locationDetail = locationDetailService.getUserLocation(friendMobileNo);

			if(locationDetail!= null){
				
				jsonObject.put("longitude", locationDetail.getLongitude());
				
				jsonObject.put("lattitude", locationDetail.getLattitude());
				
				jsonObject.put("lastUpdatedTime", locationDetail.getUpdatedOn());

				
				jsonObject.put(Constants.Status, Constants.Status_Success);

				jsonObject.put(Constants.StatusCode, "200");

			}else{
				
				jsonObject.put(Constants.Status, Constants.Status_Fail);
				
				jsonObject.put(Constants.StatusCode, "203");
			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}

		
		return jsonObject.toString();
	}
	
	
	@RequestMapping(value = "helmetuser/travelpoints", method = RequestMethod.POST)
	public @ResponseBody String travelPoints(
			@RequestHeader(value = "mobileno", required = true) String mobileNo,
			@RequestHeader(value = "date", required = true) String date) {
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		try {
			Date tripDate = new Date();
			DateFormat dtFormat = new SimpleDateFormat("dd-MM-yyyy");

			try {

				tripDate = new Date(date);

				String formattedTripDate = dtFormat.format(date);

			} catch (Exception e) {
				throw e;
			}
			
			List<LocationDetail> locationDetaillist = locationDetailService.listLocationDetailByDate(tripDate,mobileNo);
			LocationDetail locationDetail;
			
			if(locationDetaillist.size() > 0) {
				
				for (int i = 0; i < locationDetaillist.size(); i++) {
					
					locationDetail = locationDetaillist.get(i);
					
					jsonObject = new JSONObject();
					
					if(locationDetail!= null){
						
						jsonObject.put("longitude", locationDetail.getLongitude());
						
						jsonObject.put("lattitude", locationDetail.getLattitude());
						
						jsonObject.put("lastUpdatedTime", locationDetail.getClientTime());
						
						jsonArray.put(jsonObject.toString());
						
					}
				}
				
				jsonObject = new JSONObject();
				
				jsonObject.put(Constants.Status, Constants.Status_Success);
				jsonObject.put("mobilenumber", mobileNo);
				jsonObject.put("locationpoints", jsonArray.toString());
				jsonObject.put(Constants.StatusCode, Constants.Status_OK);
			
			}else {
				jsonObject.put(Constants.Status, Constants.Status_Success);
				jsonObject.put("mobilenumber", mobileNo);
				jsonObject.put("locationpoints", "No points available");
				jsonObject.put(Constants.StatusCode, Constants.Status_OK);
				
			}
			


		} catch (Exception e1) {
			try {

				jsonObject.put(Constants.Status, Constants.Status_Fail);
				jsonObject.put("mobilenumber", mobileNo);
				jsonObject.put("locationpoints", "Currupt Date Format");
				jsonObject.put(Constants.StatusCode, Constants.Status_Exception);
				
			}catch (Exception e) {
				e.printStackTrace();
			}

			e1.printStackTrace();
		}

		
		return jsonObject.toString();
	}
	
}
