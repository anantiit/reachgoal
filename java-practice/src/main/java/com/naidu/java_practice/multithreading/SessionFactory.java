package com.naidu.java_practice.multithreading;

public interface SessionFactory {

	public TransactionProcessSoft getTransactionProcessor();

	void returnTransactionProcessor();

}
