package com.helmet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.helmet.dao.LocationDetailDao;
import com.helmet.entity.LocationDetail;

@Service("locationDetailService")
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class LocationDetailServiceImpl implements LocationDetailService {
	
	@Autowired
	LocationDetailDao locationDetaildao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addLocationDetails(LocationDetail locationDetail) {
		
		System.out.println("add loc");
		
		locationDetaildao.addLocationDetails(locationDetail);
	
	}

	@Override
	@Transactional
	public List<LocationDetail> listLocationDetail() {
	
		System.out.println("list loc");
		
		return (List<LocationDetail>)locationDetaildao.listLocationDetail();
	
	}

	@Override
	@Transactional
	public boolean updateLocationDetail(LocationDetail locationDetail) {
	
		System.out.println("update loc");
		
		return locationDetaildao.updateLocationDetail(locationDetail);
	}

	@Override
	@Transactional
	public void deleteLocationDetail(LocationDetail locationDetail) {
		
		System.out.println("delete loc");
	}

	@Override
	@Transactional
	public LocationDetail getUserLocation(String mobileNo) {
		
		return locationDetaildao.getUserLocation(mobileNo);
	}

}
