package com.practice.assignments.coffeemachine.data;

import com.google.gson.annotations.SerializedName;

public class OutLets {
	@SerializedName("count_n")
	private Integer outLetCount;

	public OutLets() {
	}

	public Integer getOutLetCount() {
		return outLetCount;
	}

	public void setOutLetCount(Integer outLetCount) {
		this.outLetCount = outLetCount;
	}

}
