package lld.discount_ecom;

/*
 * Offer : offerProviderType, discountType, priority, ApplicableProductTypes, NotApplicablePRoductTypes, couponCode, bydefaultApplied, maxDiscount
 * offerProviderType: Vendor, Seller, Platform, Bank
 * Order : List<item>, List<price>, List<priceAfterDiscount>
 * Cart : List<Items>
 * Item: Price,MaxThresholdDiscount, defaultDescount
 * we can use chainOfResponsibilityPattern
 */
public class EcomDiscountApplication {

	// DiscountStrategy interface
	public interface DiscountStrategy {
		double calculateDiscount(double totalPrice);

		int getPriority();
	}

	// PercentageDiscountStrategy class
	public class PercentageDiscountStrategy implements DiscountStrategy {
		private double percentage;
		private int priority;

		public PercentageDiscountStrategy(double percentage, int priority) {
			this.percentage = percentage;
			this.priority = priority;
		}

		@Override
		public double calculateDiscount(double totalPrice) {
			return totalPrice * (percentage / 100);
		}

		@Override
		public int getPriority() {
			return priority;
		}
	}

	// FixedAmountDiscountStrategy class
	public class FixedAmountDiscountStrategy implements DiscountStrategy {
		private double amount;
		private int priority;

		public FixedAmountDiscountStrategy(double amount, int priority) {
			this.amount = amount;
			this.priority = priority;
		}

		@Override
		public double calculateDiscount(double totalPrice) {
			return Math.min(totalPrice, amount);
		}

		@Override
		public int getPriority() {
			return priority;
		}
	}

	// CompositeDiscountStrategy class
	public class CompositeDiscountStrategy implements DiscountStrategy {
		private List<DiscountStrategy> strategies;
		private int priority;

		public CompositeDiscountStrategy(List<DiscountStrategy> strategies, int priority) {
			this.strategies = strategies;
			this.priority = priority;
		}

		@Override
		public double calculateDiscount(double totalPrice) {
			double totalDiscount = 0.0;
			for (DiscountStrategy strategy : strategies) {
				totalDiscount += strategy.calculateDiscount(totalPrice);
			}
			return totalDiscount;
		}

		@Override
		public int getPriority() {
			return priority;
		}
	}

	// Discount class (Subject in Observer pattern)
	public class Discount {
		private List<DiscountObserver> observers = new ArrayList<>();
		private List<DiscountStrategy> strategies;

		public void setStrategies(List<DiscountStrategy> strategies) {
			this.strategies = strategies;
			notifyObservers();
		}

		public double calculateDiscount(double totalPrice) {
			if (strategies == null || strategies.isEmpty()) {
				return 0;
			}

			// Sort strategies based on priority (highest priority first)
			strategies.sort(Comparator.comparingInt(DiscountStrategy::getPriority).reversed());

			// Calculate total discount until the maximum threshold is reached
			double totalDiscount = 0.0;
			double remainingTotal = totalPrice;
			for (DiscountStrategy strategy : strategies) {
				double discount = strategy.calculateDiscount(remainingTotal);
				if (totalDiscount + discount > totalPrice) {
					// Discount exceeds the maximum threshold, skip it
					continue;
				}
				totalDiscount += discount;
				remainingTotal -= discount;
			}
			return totalDiscount;
		}

		public void addObserver(DiscountObserver observer) {
			observers.add(observer);
		}

		public void removeObserver(DiscountObserver observer) {
			observers.remove(observer);
		}

		private void notifyObservers() {
			for (DiscountObserver observer : observers) {
				observer.onDiscountChanged();
			}
		}
	}

	// Usage
	public class Main {
		public static void main(String[] args) {
	        // Create a discount
	        Discount discount = new Discount();

	        // Create cart
	        Cart cart = new Cart();
	        cart.setDiscount(discount);

	        // Create multiple discount strategies with priority
	        List<DiscountStrategy> strategies = new ArrayList<>();
	        strategies.add(new PercentageDiscountStrategy(10.0, 2));
	        strategies.add(new FixedAmountDiscountStrategy(20.0, 1));
	        strategies.add(new FixedAmountDiscountStrategy(30.
	    }
}
