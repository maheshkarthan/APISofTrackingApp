package com.helmet.restcontroller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.helmet.entity.ContactDetail;
import com.helmet.entity.UserDetail;
import com.helmet.service.ContactDetailService;
import com.helmet.service.UserDetailService;
import com.helmet.util.Constants;

@RestController
public class ContactDetailRestController {

	@Autowired
	ContactDetailService contactDetailService;

	@Autowired
	UserDetailService userDetailService;

	@RequestMapping(value = { "helmetuser/addContact", "helmetuser/updateContact" }, method = RequestMethod.POST)
	public @ResponseBody String addContactDetail(@RequestHeader(value = "userid", required = true) String userId,
			@RequestHeader(value = "contact1", required = true) String contact1,
			@RequestHeader(value = "contact2", required = true) String contact2) {

		JSONObject jsonObject = new JSONObject();

		try {

			System.out.println(userId + "" + contact1 + " " + contact2);

			int userId_fk = Integer.parseInt(userId.trim().toString());

			UserDetail userDetail;

			try {

				userDetail = userDetailService.getUserById(userId_fk);

			} catch (Exception exe) {

				exe.printStackTrace();

				jsonObject.put("status", "Failure");

				jsonObject.put("statusCode", "204");

				jsonObject.put("message", "UserNotFound");

				return jsonObject.toString().trim();
			}

			ContactDetail contactDetail = contactDetailService.getContactDetail(userId_fk);

			if (contactDetail == null) {

				contactDetail = new ContactDetail();

				System.out.println("new User" + contactDetail);

			} else {

				System.out.println("Existing User" + contactDetail);

			}

			contactDetail.setEmergency_contact1(contact1);

			contactDetail.setEmergency_contact2(contact2);

			contactDetail.setUserId_fk(userDetail);

			try {

				contactDetailService.addContactDetails(contactDetail);

				jsonObject.put(Constants.Status, Constants.Status_Success);

				jsonObject.put(Constants.StatusCode, Constants.Status_OK);

			} catch (Exception exception) {

				exception.printStackTrace();

				jsonObject.put(Constants.Status, Constants.Status_Exception);

				jsonObject.put(Constants.StatusCode, "202");

			}

		} catch (Exception e) {
			try {

				jsonObject.put(Constants.Status, Constants.Status_Fail);

				jsonObject.put(Constants.StatusCode, "203");

			} catch (JSONException e1) {

				e1.printStackTrace();
			}

		}

		return jsonObject.toString().trim();
	}

}