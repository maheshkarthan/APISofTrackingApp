package com.helmet.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.helmet.entity.UserDetail;

@Repository("userDetailDao")
public class UserDetailDaoImpl implements UserDetailDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public UserDetail addUserDetails(UserDetail userDetail) {

		System.out.println("add user detail");

		try {

			sessionFactory.getCurrentSession().saveOrUpdate(userDetail);

		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println(userDetail.toString());

		return userDetail;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetail> listUserDetails() {

		System.out.println("list user detail");

		return (List<UserDetail>) sessionFactory.getCurrentSession().createCriteria(UserDetail.class).list();
	}

	@Override
	public boolean updateUserDetail(UserDetail userDetail) {

		boolean status = false;

		System.out.println("update user detail");

		try {

			sessionFactory.getCurrentSession().saveOrUpdate(userDetail);

			status = true;

		} catch (Exception e) {

			status = false;

		}

		return status;
	}

	@Override
	public Integer deleteUserProfile(UserDetail userDetail) {

		System.out.println("delete user detail");

		int status = 0;
		
		Query qry = sessionFactory.getCurrentSession().createQuery("update UserDetail set "
				+ "activeStatus='DELETED' where userId=:userId");
		
		qry.setParameter("userId", userDetail.getUserId());
		
				
		status = qry.executeUpdate();

		return status;
	}

	@Override
	public boolean isUserExist(String mobileNo) {

		System.out.println("user detail exist");

		boolean status = false;

		try {

			Query query = sessionFactory.getCurrentSession().createQuery("FROM UserDetail WHERE mobileNo =:mobileNo");

			query.setParameter("mobileNo", mobileNo);

			UserDetail userDetail = (UserDetail) query.uniqueResult();

			if (userDetail != null) {

				status = true;

			} else {

				status = false;
			}

			System.out.println("user detail exist" + userDetail);

		} catch (Exception e) {

			status = false;
		}

		return status;
	}

	@Override
	public String getUserId(String mobileNo) {

		System.out.println("get user detail");

		String userId = (String) sessionFactory.getCurrentSession()
				.createQuery("select userid from UserDetail where mobileNo=:mobileNo")
				.setParameter("mobileNO", mobileNo).uniqueResult();

		System.out.println("fetched userId: " + userId);

		return userId;
	}

	@Override
	public UserDetail getUserById(Integer userId) {

		UserDetail userDetail = (UserDetail) sessionFactory.getCurrentSession()
				.createQuery(" from UserDetail where userId=:userId").setParameter("userId", userId).uniqueResult();

		System.out.println("fetched userDetail: " + userDetail);

		return userDetail;
	}

	@Override
	public UserDetail getUserByMobNo(String mobileNo) {

		UserDetail userDetail = (UserDetail) sessionFactory.getCurrentSession()
				.createQuery("select u from UserDetail u where u.mobileNo=:mobileNo").setString("mobileNo", mobileNo)
				.uniqueResult();

		System.out.println("fetched userDetail: " + userDetail);

		return userDetail;
	}

	@Override
	public boolean isMobileNumberExist(String mobileNo) {

		boolean isExist = false;

		Long count = (Long) sessionFactory.getCurrentSession().createCriteria(UserDetail.class)
				.add(Restrictions.eq("mobileNo", mobileNo)).setProjection(Projections.rowCount()).uniqueResult();

		if (count != 0) {

			isExist = true;

		}

		return isExist;
	}

	@Override
	public boolean isUserNameExist(String userName) {

		boolean isExist = false;

		int count = (int) sessionFactory.getCurrentSession().createCriteria(UserDetail.class)
				.add(Restrictions.eq("userName", userName.toLowerCase())).setProjection(Projections.rowCount())
				.uniqueResult();

		if (count != 0) {

			isExist = true;

		}

		return isExist;
	}
}
