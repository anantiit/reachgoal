package com.razorpay.entities;

import com.razorpay.model.DataType;

public class TableSchema {
	String tableName;
	int columnSize;
	String[] coumnNames;
	DataType[] dataTypes;

	public TableSchema(String tableName, int columnSize, String[] coumnNames, DataType[] dataTypes) {
		super();
		this.columnSize = columnSize;
		this.coumnNames = coumnNames;
		this.dataTypes = dataTypes;
		this.tableName = tableName;
	}

}
