package com.naidu.java_practice.functionalprogramming;

@FunctionalInterface
interface FunctionalInterfaceSample {
	double bookCab(String source, String Destination);// by default any method in interface is public abstract
}

//class UberX implements FunctionalInterfaceSample {
//	@Override
//	public void bookCab() {
//		System.out.println("Uberx booked,be ready for pickup..");
//	}
//}

interface Calc {
	int add(int num1, int num2);
}

class Calculator {
	int addSomething(int num1, int num2) {
		return num1 + num2;
	}

	static int letsAdd(int num1, int num2) {
		return num1 + num2;
	}
}

interface Messenger {
	Message getMessage(String msg);
}

class Message {
	String msg;
	Message(String msg) {
		this.msg = msg;
	}
}
public class LmbdaEXpressionUsage {
	int instanceVar;
	static int staticVar = 100;

	public LmbdaEXpressionUsage(int instanceVar) {
		super();
		this.instanceVar = instanceVar;
	}

	public static void main(String[] args) {

	// Using Anonymas class
	FunctionalInterfaceSample cab  = new FunctionalInterfaceSample(){
		@Override
		public double bookCab(String source, String destination) {
			System.out.println("Anonymas booked,be ready for pickup..");
			return 50;
		}
	};

//	FunctionalInterfaceSample cab1 = new UberX();
	cab.bookCab("src", "dest");
//	cab1.bookCab();
	
	int localVar = 100;
	LmbdaEXpressionUsage le = new LmbdaEXpressionUsage(200);
	// We can use local, instance and static variables in lambda expressions
	FunctionalInterfaceSample cab2 = (src, dest) -> {
		System.out.println(
				"Lambda cab booked,be ready for pickup.." + src + " to " + dest
						+ ". As it is functional interface only one method is there so lambda does not need the method name");
		System.out.println("localVar" + localVar);
		System.out.println("staticVar" + staticVar);
		System.out.println("instanceVar" + le.instanceVar);

		return 50;
	};

	double fare = cab2.bookCab("marathalli", "silk board");
	System.out.println("fare shall be " + fare);

	/*
	 * Method references
	 */
	// Instance method reference
	Calculator calculator = new Calculator();
	Calc calc = calculator::addSomething;
	System.out.println(calc.add(100, 200));
	// Static method reference
	Calc calc1 = Calculator::letsAdd;
	System.out.println(calc1.add(200, 300));
	// Constructor method reference
	Messenger msg = Message::new;
	Message msg1 = msg.getMessage("Hello");
	System.out.println(msg1.msg);

}
}
