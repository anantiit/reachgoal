package com.naidu.java.designpatterns.observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class OutOfStockNotificationRepository {
	Map<String, HashSet<String>> itemNotifierEmailMap = new HashMap<String, HashSet<String>>();

	public void addNotification(String itemId, String emailId) {
		// TODO Auto-generated method stub
		itemNotifierEmailMap.computeIfAbsent(itemId, k -> new HashSet<String>()).add(emailId);
		itemNotifierEmailMap.compute(itemId, (k, v) -> v).add(emailId);
	}

	public Set<String> getNotifierEmails(String itemId) {
		return itemNotifierEmailMap.get(itemId);
	}

}
