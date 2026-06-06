package com.naidu.java.designpatterns.observer;

public class InventoryService {
	InventoryRepository ir;
	OutOfStockNotificationRepository osnr;
	ItemStockObservable iso;

	public InventoryService(InventoryRepository ir, OutOfStockNotificationRepository osnr, ItemStockObservable iso) {
		super();
		this.ir = ir;
		this.osnr = osnr;
		this.iso = iso;
	}

	public boolean isOutOfStock(String itemId) {
		Item item = ir.getItem(itemId);
		if (item == null) {
			return true;
		}
		if (item.getStockCount() > 0) {
			return false;
		}
		return true;
	}

	public void addStock(Item item) {
		ir.addItem(item);
		// TODO should not send notifications always when you add inventory we need to
		// check previous stockcount and if it is < 0 then we should notify.
		iso.notifyObservers(new NotificationEvent(item.getId()));
	}

	public void printStock() {
		System.out.println(ir.inventory);
	}

	public void removeStock(Item item) {
		ir.removeItem(item);
	}

	public void addNotifyMe(String itemId, String emailId) {
		osnr.addNotification(itemId, emailId);
	}
}
