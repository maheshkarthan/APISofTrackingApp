package com.helmet.dao;

import java.util.List;

import com.helmet.entity.UserDetail;

public interface UserDetailDao {

	public UserDetail addUserDetails(UserDetail userDetail);
	
	public List<UserDetail> listUserDetails();
	
	public boolean updateUserDetail(UserDetail userDetail);
	
	public Integer deleteUserProfile(UserDetail userDetail);

	public String getUserId(String mobileNo);
	
	public UserDetail getUserById(Integer userId);

	public boolean isUserExist(String mobileNo);

	public UserDetail getUserByMobNo(String mobileNo);

	public boolean isMobileNumberExist(String mobileNo);

	public boolean isUserNameExist(String userName);
}
