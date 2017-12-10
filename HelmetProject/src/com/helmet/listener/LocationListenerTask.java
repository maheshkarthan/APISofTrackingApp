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

public class LocationListenerTask implements MessageListener {

	private MessageConverter messageConverter;

	@Autowired
	LocationDetailService locationDetailService;

	@Autowired
	UserDetailService userDetailService;

	@Override
	public void onMessage(Message message) {

		System.out.println("Got message from MQTT" + message);

		try {

			if (message instanceof ActiveMQMessage) {

				ActiveMQMessage mqMessage = (ActiveMQMessage) message;

				String origMessage = new String(mqMessage.getContent().getData());

				System.out.println("----- Message Received for Location update -----\n");

				System.out.println("Message: " + origMessage + "\n");

				System.out.println("------------------------------------------------");

				PositionBean bean = new Gson().fromJson(origMessage, PositionBean.class);

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

					if (isValidUser) {

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
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the messageConverter
	 */
	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	/**
	 * @param messageConverter
	 *            the messageConverter to set
	 */
	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	/**
	 * Sets the message sender.
	 * 
	 * @param messageSender_p
	 *            the new message sender
	 */

}
