package com.naidu.java.designpatterns.observer;

public interface Observable {

	public boolean add(Observer o1);

	public boolean remove(Observer o1);

	public void notifyObservers(NotificationEvent notificationEvent);

}
