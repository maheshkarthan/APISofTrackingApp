package com.helmet.restcontroller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.helmet.entity.UserDetail;
import com.helmet.service.UserDetailService;
import com.helmet.util.Constants;
import com.helmet.util.OTPGenerator;

@RestController
public class UserDetailRestController {

	@Autowired
	UserDetailService userDetailService;

	@RequestMapping(value = "helmetuser/registerUser", method = RequestMethod.POST)
	public @ResponseBody String registerUser(@RequestHeader(value = "userName", required = true) String userName,
			@RequestHeader(value = "fullName", required = true) String fullName,
			@RequestHeader(value = "passWord", required = true) String passWord,
			@RequestHeader(value = "mobileNo", required = true) String mobileNo,
			@RequestHeader(value = "userEmail", required = true) String userEmail) {

		JSONObject jsonObject = new JSONObject();

		boolean isMobileExist = false;

		boolean isUserNameExist = false;

		try {

			if (!isNullOrEmpty(fullName) && !isNullOrEmpty(userName) && !isNullOrEmpty(passWord)
					&& !isNullOrEmpty(mobileNo)) {

				isMobileExist = userDetailService.isMobileNumberExist(mobileNo);

				isUserNameExist = userDetailService.isUserExist(userName);

				String newOTP =		OTPGenerator.generateOTP(4);
				if (!isUserNameExist) {

					if (!isMobileExist) {

						passWord = encodeString(passWord);

						UserDetail detail = new UserDetail();

						detail.setFullName(fullName);

						detail.setPassword(passWord);

						detail.setMobileNo(mobileNo);

						detail.setUserName(userName.toLowerCase());

						detail.setUserEmailId(userEmail);

						detail.setCreatedOn(new Date());

						detail.setActiveStatus(Constants.User_Active);

						detail.setUpdatedOn(new Date());

						
						if(newOTP!=null && newOTP!= ""){
						
							System.out.println("new OTP generated");
						
							detail.setOtp(newOTP);
						}
						
						detail.setOtp(Constants.Dummy_OTP); //comment this line when we pass OTP to user in realtime , purposefully dummy otp kept for testing

						detail = userDetailService.addUserDetails(detail);

						System.out.println(detail.getUserId() + "" + detail.toString());

						jsonObject.put(Constants.Status, Constants.Status_Success);

						jsonObject.put(Constants.StatusCode, Constants.Status_OK);

						jsonObject.put("userId", detail.getUserId().toString());

					} else {

						jsonObject.put(Constants.Status, "Fail - Mobile number already exist");

						jsonObject.put(Constants.StatusCode, Constants.Status_OK);

					}

				} else {

					jsonObject.put(Constants.Status, "Fail - User Name exist, chose anther name");

					jsonObject.put(Constants.StatusCode, Constants.Status_OK);
				}

			} else {

				jsonObject.put(Constants.Status, "improper data, data may be null or empty");

				jsonObject.put(Constants.StatusCode, "203");
			}

		} catch (JSONException exception) {

			try {

				jsonObject.put(Constants.Status, "improper data, data may be null or empty");

				jsonObject.put(Constants.StatusCode, "203");

				exception.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return jsonObject.toString();

	}

	@RequestMapping(value = "helmetuser/updateUserProfile", method = RequestMethod.POST)
	public @ResponseBody String updateUserProfile(@RequestHeader(value = "userId", required = true) String userId,
			@RequestHeader(value = "userName", required = true) String userName,
			@RequestHeader(value = "fullName", required = true) String fullName,
			@RequestHeader(value = "passWord", required = true) String passWord,
			@RequestHeader(value = "mobileNo", required = true) String mobileNo,
			@RequestHeader(value = "userEmail", required = true) String userEmail) {

		JSONObject jsonObject = new JSONObject();

		try {

			if (!isNullOrEmpty(userId)) {

				int userIdInt = 0;

				if (!isNullOrEmpty(fullName) && !isNullOrEmpty(userName) && !isNullOrEmpty(passWord)
						&& !isNullOrEmpty(mobileNo) && !isNullOrEmpty(userEmail)) {

					try {

						userIdInt = Integer.parseInt(userId);

						UserDetail userDetail = userDetailService.getUserById(userIdInt);

						if (userDetail != null) {

							passWord = encodeString(passWord);

							userDetail.setFullName(fullName);

							userDetail.setUserName(userName);

							userDetail.setMobileNo(mobileNo);

							userDetail.setPassword(passWord);

							userDetail.setUpdatedOn(new Date());

							userDetail.setUserEmailId(userEmail);

							userDetailService.addUserDetails(userDetail);

							jsonObject.put(Constants.Status, "Success");

							jsonObject.put(Constants.StatusCode, "200");

						} else {

							jsonObject.put(Constants.Status, "Fail - User does not exist");

							jsonObject.put(Constants.StatusCode, "203");

						}

					} catch (Exception e) {

						jsonObject.put(Constants.Status, "fail - improper UserId");

						jsonObject.put(Constants.StatusCode, "203");

						e.printStackTrace();
					}
				}

			} else {

				jsonObject.put(Constants.Status, "Fail - UserId may be null or empty");

				jsonObject.put(Constants.StatusCode, "203");
			}

		} catch (JSONException exception) {

			try {

				jsonObject.put(Constants.Status, "improper data, data may be null or empty");

				jsonObject.put(Constants.StatusCode, "203");

				exception.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return jsonObject.toString();

	}

	@RequestMapping(value = "helmetuser/userlogin", method = RequestMethod.POST)
	public @ResponseBody String loginUser(@RequestHeader(value = "userId", required = true) String userId,
			@RequestHeader(value = "userName", required = true) String userName,
			@RequestHeader(value = "passWord", required = true) String passWord) {

		JSONObject jsonObject = new JSONObject();

		System.out.println(userId + "" + userName + " attempted to log in");

		try {

			int userIdVal = 0;

			try {

				userIdVal = Integer.parseInt(userId.trim());

				UserDetail userDetail = userDetailService.getUserById(userIdVal);

				if (userDetail != null) {

					String decodedData = decodeString(userDetail.getPassword());
					String encodedData = encodeString(passWord);

					System.out.println(userDetail.toString() + "-->" + decodedData + " condition: "
							+ (userDetail.getUserName().equalsIgnoreCase(userName)
									&& (userDetail.getPassword().equals(encodedData))));

					if (userDetail.getUserName().equalsIgnoreCase(userName)
							&& userDetail.getPassword().equals(encodedData)) {

						jsonObject.put("userid", userDetail.getUserId());

						jsonObject.put(Constants.Status, Constants.Status_Success);

						jsonObject.put(Constants.StatusCode, "200");

					} else {

						jsonObject.put(Constants.Status, "Invalid credentials");

						jsonObject.put(Constants.StatusCode, "203");
					}

				} else {

					jsonObject.put(Constants.Status, "User not found");

					jsonObject.put(Constants.StatusCode, "203");
				}

			} catch (Exception ex) {

				try {

					jsonObject.put(Constants.Status, Constants.Status_Fail);

					jsonObject.put(Constants.StatusCode, "203");

				} catch (Exception e1) {

				}
				ex.printStackTrace();

			}

		} catch (Exception e1) {

			e1.printStackTrace();

		}

		return jsonObject.toString().trim();

	}

	@RequestMapping(value = "helmetuser/deleteuser", method = RequestMethod.POST)
	public @ResponseBody String deleteUser(@RequestHeader(value = "userId", required = true) String userId) {

		JSONObject jsonObject = new JSONObject();

		System.out.println(userId + " attempted to delete");

		try {

			int userIdVal = 0;

			try {

				userIdVal = Integer.parseInt(userId.trim());

				UserDetail userDetail = userDetailService.getUserById(userIdVal);

				if (userDetail != null) {

					jsonObject.put("userid", userDetail.getUserId());

					jsonObject.put(Constants.Status, Constants.Status_Success);

					jsonObject.put(Constants.StatusCode, "200");

					jsonObject.put("message", "user Deleted");
				}

			} catch (Exception ex) {

				try {

					jsonObject.put(Constants.Status, Constants.Status_Fail);

					jsonObject.put(Constants.StatusCode, "203");

				} catch (Exception e1) {

				}
				ex.printStackTrace();

			}

		} catch (Exception e1) {

			e1.printStackTrace();

		}

		return jsonObject.toString().trim();

	}

	@RequestMapping(value = "helmetuser/validateotp", method = RequestMethod.POST)
	public @ResponseBody String validateOtp(@RequestHeader(value = "userId", required = true) String userId,@RequestHeader(value = "otp", required = true) String otp) {

		JSONObject jsonObject = new JSONObject();

		System.out.println(otp + " otp, userId: "+userId);

			int userIdVal = 0;

				try {

					userIdVal = Integer.parseInt(userId.trim());

					UserDetail userDetail = userDetailService.getUserById(userIdVal);
					
					if (userDetail != null) {

						try {
		
							if(userDetail.getOtp().equalsIgnoreCase(otp)){

								jsonObject.put("userid", userDetail.getUserId());
						
								jsonObject.put(Constants.Status, Constants.Status_Success);

								jsonObject.put(Constants.StatusCode, "200");
						
								jsonObject.put("message","valid OTP");

							}else{
							
								jsonObject.put(Constants.Status, Constants.Status_Fail);
							
								jsonObject.put(Constants.StatusCode, "203");
								
								jsonObject.put("messsage", "invalid OTP");
						}

					}
					

					 catch (Exception e1) {

						e1.printStackTrace();
					}

				}


		} catch (Exception e1) {
			
			try {

				jsonObject.put(Constants.Status, Constants.Status_Fail);

				jsonObject.put(Constants.StatusCode, "203");
				
				jsonObject.put("message", "improper json / userid does not exist");

			} catch (Exception e2) {

			}

			e1.printStackTrace();

		}
	
		return jsonObject.toString().trim();

	}

	private static boolean isNullOrEmpty(String data) {

		boolean isTrue = false;

		try {

			if (data.equalsIgnoreCase(null)) {

				isTrue = true;

			} else {

				if (data.trim().equalsIgnoreCase("")) {

					isTrue = true;

				}
			}

		} catch (Exception e) {

			isTrue = true;
		}

		return isTrue;
	}

	private static String encodeString(String input) {

		System.out.println("----- b4 encoding -----");

		System.out.println("data: " + input);

		Base64.Encoder encoder = Base64.getEncoder();

		try {

			input = encoder.encodeToString(input.getBytes("utf-8"));

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

			System.out.println("----- Exception is raised, cant encode  -----" + e.toString());
		}

		System.out.println("----- after encoding1 -----");

		System.out.println("data: " + input);

		return input;
	}

	private static String decodeString(String input) {

		System.out.println("----- b4 decoding -----");

		System.out.println("data: " + input);

		Base64.Decoder decoder = Base64.getDecoder();

		try {

			input = new String(decoder.decode(input));

		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("----- Exception is raised, cant decode  -----" + e.toString());
		}

		System.out.println("----- after decoding -----");

		System.out.println("data: " + input);

		return input;
	}

}
