package com.razorpay.entities;

import java.util.Set;

public class Table {
	String tableName;
	TableSchema tableSchema;
	Set<Row> rows;

	public Table(String tableName, Set<Row> rows) {
		super();
		this.tableName = tableName;
		this.rows = rows;
	}

}