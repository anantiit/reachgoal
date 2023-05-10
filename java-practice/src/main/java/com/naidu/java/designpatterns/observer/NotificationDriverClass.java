package com.naidu.java.designpatterns.observer;

import java.util.HashSet;
import java.util.Set;

public class NotificationDriverClass {

	public static void main(String[] args) {
		OutOfStockNotificationRepository osnr = new OutOfStockNotificationRepository();
		Set<Observer> observers = new HashSet<Observer>();
		observers.add(new NotifyMeObserver(osnr));
		InventoryService is = new InventoryService(new InventoryRepository(), osnr, new ItemStockObservable(observers));
		is.addNotifyMe("iphone12", "abc1@gmail.com");
		is.addNotifyMe("sansuiTV", "abc2@gmail.com");
		is.addStock(new Item("iphone12", "iphone", 2));
		is.addStock(new Item("sansuiTV", "sansuiTV", 1));
		is.printStock();
		is.addNotifyMe("FridgeSamSung190L", "abc1@gmail.com");
		is.addNotifyMe("ShirtRaymondsL", "abc1@gmail.com");
		is.addNotifyMe("FanUsha", "abc3@gmail.com");
		is.addStock(new Item("FanUsha", "FanUsha", 1));
		is.printStock();

	}
}
