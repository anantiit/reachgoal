package com.naidu.java.designpatterns.observer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Item {
	private String id;
	private String name;
	private int stockCount;
}
