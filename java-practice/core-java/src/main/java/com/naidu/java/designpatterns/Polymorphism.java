package com.naidu.java.designpatterns;

public class Polymorphism {
	public static void main(String args[]) {
		A a = new A();
		A b = new B();
		A c = new C();
		a.getName();
		b.getName();
		c.getName();
		a.whoAmI();
		b.whoAmI();
		c.whoAmI();
	}
}

class A {
	public void getName() {
		System.out.println("I am " + this.getClass().getName());
	}

	public void whoAmI() {
		System.out.println("I am A");
	}
}

class B extends A {
	@Override
	public void getName() {
		System.out.println("I am " + this.getClass().getName());
	}

	@Override
	public void whoAmI() {
		System.out.println("I am B");
	}
}

class C extends B {
	@Override
	public void getName() {
		System.out.println("I am " + this.getClass().getName());
	}

//	public void whoAmI() {
//		System.out.println("I am C");
//	}
}