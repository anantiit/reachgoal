package com.naidu.java_practice.multithreading;

import lombok.NonNull;

public class PrintOddAndEvenUsingThreadsInOrder implements Runnable {
	// The shared state need not always static. We can make it instance variable and
	// create one state object and pass the same to different instance constructors
	// so that the same object is shared across different instances
	static State sharedState = new State(PrintType.ODD);
	PrintType printType;
	PrintType nextPrintType;
	static int counter = 0;
	static int max = 50;
	static int step = 1;

	PrintOddAndEvenUsingThreadsInOrder(PrintType printType, PrintType nextPrintType) {
		this.printType = printType;
		this.nextPrintType = nextPrintType;
	}

	public static void main(String[] args) {
		PrintOddAndEvenUsingThreadsInOrder printOddThread = new PrintOddAndEvenUsingThreadsInOrder(PrintType.ODD,
				PrintType.EVEN);
		PrintOddAndEvenUsingThreadsInOrder printEvenThread = new PrintOddAndEvenUsingThreadsInOrder(PrintType.EVEN,
				PrintType.ODD);
		Thread oddThread = new Thread(printOddThread);
		oddThread.start();
		Thread evenThread = new Thread(printEvenThread);
		evenThread.start();
		Example e = new Example(null);

	}

	@Override
	public void run() {

		while (counter < max - 1) {
			synchronized (sharedState) {
				if (this.printType != sharedState.nextPrintType) {
					try {
						sharedState.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				System.out.println(counter = (counter + step));
				sharedState.nextPrintType = this.nextPrintType;
				sharedState.notify();

			}
		}
	}

}

class State {
	public State(PrintType nextPrintType) {
		this.nextPrintType = nextPrintType;
	}

	PrintType nextPrintType;
}

enum PrintType {
	ODD, EVEN;
}

class Example {
	private String name;

	public Example(@NonNull String name) {
		System.out.println("Initialized:" + name);
		this.name = name;
	}

	public void setName(@NonNull String name) {
		this.name = name;
	}
}
