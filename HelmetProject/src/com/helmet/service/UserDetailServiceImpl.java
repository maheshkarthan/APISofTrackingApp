package com.helmet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.helmet.dao.UserDetailDao;
import com.helmet.entity.UserDetail;

@Service("userDetailService")
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class UserDetailServiceImpl implements UserDetailService {
	
	@Autowired
	UserDetailDao userDetailDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserDetail addUserDetails(UserDetail userDetail) {
	
		return userDetailDao.addUserDetails(userDetail);
	}

	@Override
	@Transactional
	public List<UserDetail> listUserDetails() {
	
		return userDetailDao.listUserDetails();
	
	}

	@Override
	@Transactional
	public boolean updateUserDetail(UserDetail userDetail) {
	
		return userDetailDao.updateUserDetail(userDetail);
	
	}

	@Override
	@Transactional
	public Integer deleteUserProfile(UserDetail userDetail) {
		
		return userDetailDao.deleteUserProfile(userDetail);
	
	}

	@Override
	@Transactional
	public String getUserId(String mobileNo) {
	
		return userDetailDao.getUserId(mobileNo);
	}

	@Override
	@Transactional
	public UserDetail getUserById(Integer userId) {
		
		return userDetailDao.getUserById(userId);
	}

	@Override
	@Transactional
	public boolean isUserExist(String mobileNo) {
	
		return userDetailDao.isUserExist(mobileNo);
	}

	@Override
	@Transactional
	public UserDetail getUserByMobNo(String mobileNo) {
	
		return userDetailDao.getUserByMobNo(mobileNo);
	
	}

	@Override
	@Transactional
	public boolean isMobileNumberExist(String mobileNo) {
		
		return userDetailDao.isMobileNumberExist(mobileNo);

	}

	@Override
	@Transactional
	public boolean isUserNameExist(String userName) {
		
		return userDetailDao.isUserNameExist(userName);
	
	}

}
