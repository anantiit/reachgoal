package com.razorpay.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.razorpay.entities.Column;
import com.razorpay.entities.Row;
import com.razorpay.entities.Table;
import com.razorpay.model.DataType;

public class DataBaseOperationManager {

	public static void createTable(String tableName, String[] columns, DataType[] dataTypes) {
		if (columns.length != dataTypes.length) {
			System.out.println("Given inputs are wrong");
		}
		int columnLength = columns.length;
		List<Column<Comparable>> columnlist = new ArrayList<Column<Comparable>>();
		for (int i = 0; i < columnLength; i++) {
			String columnName = columns[i];
			DataType dt = dataTypes[i];
			if (DataType.Integer == dt) {
				Column<Integer> column = new Column<Integer>();
			} else if (DataType.String == dt) {
				Column<String> column = new Column<String>();
			}
			columnlist.add(column);
		}
		Row row = new Row(columnlist);
		Set<Row> rows = new HashSet<Row>();
		rows.add(row);
		Table table = new Table(tableName, rows);
	}

	public static void insert(String tableName, String[] columns, String[] values) {
		if (columns.length != values.length) {
			System.out.println("Given inputs are wrong");
		}
		int columnLength = columns.length;
		Table tableSchema = getTableSchema(tableName);
		List<Column<String>> columnlist = tableSchema.getSchemaRow();
		for (int i = 0; i < columnLength; i++) {
			String columnName = columns[i];
			String dt = values[i];
			if (DataType.Integer == dt) {
				Column<Integer> column = new Column<Integer>();
			} else if (DataType.String == dt) {
				Column<String> column = new Column<String>();
			}
			columnlist.add(column);
		}
		Row row = new Row(columnlist);
		Set<Row> rows = new HashSet<Row>();
		rows.add(row);
		Table table = new Table(tableName, rows);

	}

}
