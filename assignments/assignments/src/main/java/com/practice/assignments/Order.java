package com.practice.assignments;

/*
 * 
e.g. The following input (format:<order-id> <time> <stock> <buy/sell> <price> <qty>):

#1 09:45 BAC sell 240.12 100
#2 09:46 BAC sell 237.45  90 
#3 09:47 BAC buy  238.10 110 
#4 09:48 BAC buy  237.80  10
#5 09:49 BAC buy  237.80  40
#6 09:50 BAC sell 236.00  50
 */
public class Order {
	private int orderNumber;
	private int epochTime;
	private String stockId;
	private boolean isBuyOrder;
	private Double orderedPrice;
	private int orderedQuantity;
	private int executedQuantity;
	private OrderStatus orderStatus;

	public int getOrderNumber() {
		return orderNumber;
	}

	public Order(int orderNumber, int epochTime, String stockId, boolean isBuyOrder, Double orderedPrice,
			int orderedQuantity, OrderStatus orderStatus) {
		super();
		this.orderNumber = orderNumber;
		this.epochTime = epochTime;
		this.stockId = stockId;
		this.isBuyOrder = isBuyOrder;
		this.orderedPrice = orderedPrice;
		this.orderedQuantity = orderedQuantity;
		this.executedQuantity = 0;
		this.orderStatus = orderStatus;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getEpochTime() {
		return epochTime;
	}

	public void setEpochTime(int epochTime) {
		this.epochTime = epochTime;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public boolean isBuyOrder() {
		return isBuyOrder;
	}

	public void setBuyOrder(boolean isBuyOrder) {
		this.isBuyOrder = isBuyOrder;
	}

	public Double getOrderedPrice() {
		return orderedPrice;
	}

	public void setOrderedPrice(Double orderedPrice) {
		this.orderedPrice = orderedPrice;
	}

	public int getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public int getExecutedQuantity() {
		return executedQuantity;
	}

	public void setExecutedQuantity(int executedQuantity) {
		this.executedQuantity = executedQuantity;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}
