package com.naidu.java.designpatterns.observer;

import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotifyMeObserver implements Observer {
	OutOfStockNotificationRepository osnr;

	@Override
	public void update(NotificationEvent notificationEvent) {
		Set<String> emails = osnr.getNotifierEmails(notificationEvent.getItemId());
		if (emails == null || emails.isEmpty()) {
			return;
		}
		for (String email : emails) {
			System.out.println("Item with id is in stock now. Email sent to :" + email);
		}
	}

}
