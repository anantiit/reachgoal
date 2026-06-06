package com.naidu.java_practice.multithreading;

//What is theissue with this code?
public class SoftEngineFactory implements SessionFactory {

	@Override
	public TransactionProcessSoft getTransactionProcessor() {
		// synchronized (SoftEngineFactory.class) {
		// It is highly discouraged to use non final immutable objects as synchronized
		// objects as they can/will be pointed new object on which lock is not there.
		synchronized (ezSessionCount) {
			if (ezSessionCount < sessionLimit) {
				ezSessionCount++;
				return new TransactionProcessSoft();
			}
		}
		return null;
	}

	@Override
	public void returnTransactionProcessor() {
		// decrement count
		return;
	}

	public static final int sessionLimit = 5;
	public static Integer ezSessionCount = new Integer(0);
}
