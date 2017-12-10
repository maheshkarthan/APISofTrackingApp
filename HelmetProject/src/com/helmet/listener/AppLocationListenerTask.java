package com.helmet.listener;

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

		System.out.println("Got message from MQTT" + paramMessage);

		try {

			if (paramMessage instanceof ActiveMQMessage) {

				ActiveMQMessage mqMessage = (ActiveMQMessage) paramMessage;

				String origMessage = new String(mqMessage.getContent().getData());
				
				origMessage = "{\"userId\"=\"1\",\"mobileNo\":\"9738415229\",\"longitude\":\"17.55534\",\"lattitude\":\"17.45343\",\"clientTime\":\""+new Date()+"\"}";
				
				System.out.println("----- Message Received for Location update -----\n");

				System.out.println("Message: " + origMessage + "\n");

				System.out.println("------------------------------------------------");

				PositionBean bean = null;
				try {

					bean = new Gson().fromJson(origMessage, PositionBean.class);

					if (origMessage != "") {

						int userId = 0;

						boolean isValidUser = false;

						try {

							userId = Integer.parseInt(bean.getUserId());
							isValidUser = true;
						} catch (Exception e) {

							e.printStackTrace();

							System.out.println("improper userId");
						}

						if (isValidUser && bean.getLattitude()!=null && bean.getLongitude()!=null) {

							UserDetail userDetail = userDetailService.getUserById(userId);

							LocationDetail locdt = new LocationDetail();

							locdt.setLattitude(bean.getLattitude());

							locdt.setLongitude(bean.getLongitude());

							locdt.setMobileNo(bean.getMobileNo());

							locdt.setUserId_fk(userDetail);

							locdt.setUpdatedOn(new Date());

							locdt.setClientTime(new Date());

							locationDetailService.addLocationDetails(locdt);

							System.out.println("Updated Location Detail");
						
						}else{
						
							System.out.println("could not update the location : "+bean);
						}

					}
				} catch (Exception e) {
					
					System.out.println(" Improper location details: "+e.toString());
				}

			}
		} catch (Exception e) {
			
			System.out.println(" corrupt data : "+e.toString());
		
		}

	}

	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

}
