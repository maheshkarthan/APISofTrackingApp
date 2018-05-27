package com.helmet.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;

import com.google.gson.Gson;
import com.helmet.bean.PositionBean;
import com.helmet.entity.LocationDetail;
import com.helmet.entity.UserDetail;
import com.helmet.service.LocationDetailService;
import com.helmet.service.UserDetailService;

public class AppLocationListenerTask implements MessageListener {

	private MessageConverter messageConverter;

	@Autowired
	LocationDetailService locationDetailService;

	@Autowired
	UserDetailService userDetailService;

	@Override
	public void onMessage(Message paramMessage) {

		//System.out.println("Got message from MQTT" + paramMessage);

		try {
			if (paramMessage instanceof ActiveMQMessage) {
				ActiveMQMessage mqMessage = (ActiveMQMessage) paramMessage;
				String origMessage = new String(mqMessage.getContent().getData());
				//origMessage = "{\"userId\"=\"1\",\"mobileNo\":\"9738415229\",\"longitude\":\"17.55534\",\"lattitude\":\"17.45343\",\"clientTime\":\""+new Date()+"\"}";
				//System.out.println("----- Message Received for Location update -----\n");
				System.out.println("Message: " + origMessage + "\n");
				//System.out.println("------------------------------------------------");

				PositionBean bean = null;
				bean = new Gson().fromJson(origMessage, PositionBean.class);
				if (origMessage != "") {
					String userMobileNumber = null;
					userMobileNumber = bean.getUsermobileno();
					
					if ((userMobileNumber.length() == 10) && bean.getLatitude() != null && bean.getLongitude() != null) {
						UserDetail userDetail = userDetailService.getUserByMobNo(userMobileNumber);
						if (userDetail != null) {
							LocationDetail locdt = new LocationDetail();
							locdt.setLattitude(bean.getLatitude());
							locdt.setLongitude(bean.getLongitude());
							locdt.setMobileNo(bean.getUsermobileno());
							locdt.setUserId_fk(userDetail);
							locdt.setCreatedOn(new Date());
							locdt.setUpdatedOn(new Date());
							SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							locdt.setClientTime(dt.parse(bean.getClientTime()));
							locationDetailService.addLocationDetails(locdt);
							System.out.println("Updated Location Details");
						} else {
							System.out.println("User Mobile Not Registered");
						}
					} else{
						System.out.println(" Improper MobileNo/location details: "+bean);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" corrupted data : " + e);
		}
	}

	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

}
