package com.naidu.java.designpatterns.observer;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemStockObservable implements Observable {
	Set<Observer> observers = new HashSet<Observer>();

	@Override
	public boolean add(Observer o1) {
		return observers.add(o1);
	}

	@Override
	public boolean remove(Observer o1) {
		return observers.remove(o1);
	}

	@Override
	public void notifyObservers(NotificationEvent notificationEvent) {
		if (observers == null || observers.isEmpty()) {
			return;
		}
		for (Observer o : observers) {
			o.update(notificationEvent);
		}

	}

}
