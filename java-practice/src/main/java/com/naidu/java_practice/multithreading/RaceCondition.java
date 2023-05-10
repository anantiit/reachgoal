package com.naidu.java_practice.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class RaceCondition {

	public static void main(String[] args) {
		Counter c = new Counter();
		// Both threads share the same counter
		Thread thread1 = new Thread(getRunnable(c), "Thread1");
		Thread thread2 = new Thread(getRunnable(c), "Thread2");
		thread1.start();
		thread2.start();

	}

	public static Runnable getRunnable(Counter counter) {
		return () -> {
			// As we are counting one million times atleast one thread finally should give 2
			// million. but that wont happen
			for (int i = 0; i < 1000000; i++) {
				counter.incrementAtomicCounter();
			}
			for (int i = 0; i < 1000000; i++) {
				counter.increment();
			}
			System.out
					.println(Thread.currentThread().getName() + " counted atomic counter to " + counter.atomicCounter);
			System.out.println(Thread.currentThread().getName() + " counted to " + counter.counter);
		};
	}
}

class Counter {
	// atomic integer counter works properly other counter gives race condition
	AtomicInteger atomicCounter;
	int counter;

	Counter() {
		this.atomicCounter = new AtomicInteger();
	}

	public void incrementAtomicCounter() {
		atomicCounter.incrementAndGet();
	}

	public void increment() {
		// synchronized (this) {
			counter++; // critical section, so should be put synchronized block else race condition
						// will occur.
		// }
	}


}

