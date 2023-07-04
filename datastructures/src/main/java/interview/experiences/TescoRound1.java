package interview.experiences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
/*

    
    Tesco has a fleet of vehicles to deliver orders to the customer. Assigning the right set of orders to different sized vehicles
     is crucial for efficient delivery of orders.
     Different vehicle can fit different container sizes. Given c containers, along with their volumes [l,b,h],
      catalogue of product with its volume requirement (l,b,h) and an order with p products and its quantity.
Example:
Containers:
SMALL -> id=1, length=10, breadth=20, height=30 
MEDIUM -> id=2, length=50, breadth=60, height=70
LARGE -> id=3, length=100, breadth=200, height=300
Product:
productId=1, length=2, breadth=4, height=10
productId=2, length=10, breadth=30, height=4
productId=3, length=5, breadth=6, height=7
Order:
productId=1, quantity=3
productId=3, quantity=7

Determine if that order fits in any of the given c containers and return the ID of the container that can be used.
For the above sample of example SMALL container with id=1 should be returned.
*/

public class TescoRound1 {

	public static void main(String[] args) {
		Map<Product, Integer> productMap = new HashMap<Product, Integer>();
		Order order = new Order(productMap);
		Product product1 = new Product(1, 2, 4, 10);
		Product product2 = new Product(2, 10, 30, 4);
		Product product3 = new Product(3, 5, 6, 7);
		productMap.put(product1, 1000);
		productMap.put(product2, 7);
		Set<Vehicle> vehicles = new TreeSet<Vehicle>((a, b) -> a.volume - b.volume);
		vehicles.addAll(List.of(new Vehicle(1, 10, 20, 30, "SMALL"), new Vehicle(2, 50, 60, 70, "MEDIUM"),
				new Vehicle(3, 100, 200, 300, "LARGE")));
		System.out.println(findMatchingContainer(order, vehicles));
		/*
		 * one of the dimension of the product is greater than all the dimentions (l, b,
		 * h) of vehicle that should not fit in Happy flows 1. order fits in one of the
		 * vehicle 2. order fits in small vehicle 3. order fits in medium vehicle
		 * 
		 * Failure cases: Order does not fit with vehicle because individual product
		 * count is more Order does not fit with vehicle because list of products are
		 * more Order does not fit with vehicle because count is more
		 */
	}

	public static Vehicle findMatchingContainer(Order order, Set<Vehicle> vehicles) {
		Map<Product, Integer> productMap = order.productMap;
		Set<Product> productSet = productMap.keySet();
		int totalProductVolume = 0;
		for (Product product : productSet) {
			int productCount = productMap.get(product);
			int productVolume = productCount * product.volume;
			totalProductVolume = totalProductVolume + productVolume;
		}
		for (Vehicle vehicle : vehicles) {
			if (vehicle.volume >= totalProductVolume) {
				return vehicle;
			}
		}
		return null;
	}

}

class Product {
	int id;
	int length;
	int breadth;
	int height;
	int volume;

	public Product(int id, int length, int breadth, int height) {
		super();
		this.id = id;
		this.length = length;
		this.breadth = breadth;
		this.height = height;
		this.volume = length * breadth * height;
	}

}

class Order {
	Map<Product, Integer> productMap;

	public Order(Map<Product, Integer> productMap) {
		super();
		if (productMap == null) {
			this.productMap = new HashMap<Product, Integer>();
		}
		this.productMap = productMap;
	}
}

class Vehicle {
	public Vehicle(int id, int length, int breadth, int height, String vehicleType) {
		this.id = id;
		this.length = length;
		this.breadth = breadth;
		this.height = height;
		this.volume = length * breadth * height;
		this.vehicletype = vehicleType;
	}

	int id;
	int length;
	int breadth;
	int height;
	int volume;
	String vehicletype;

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", length=" + length + ", breadth=" + breadth + ", height=" + height + ", volume="
				+ volume + ", vehicletype=" + vehicletype + "]";
	}

}
