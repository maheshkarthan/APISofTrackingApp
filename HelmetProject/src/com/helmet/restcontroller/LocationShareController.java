package com.helmet.restcontroller;

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
	UserDetailService SuserDetailService;
	
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
}
