package com.reachgoal.core_utils.date;

import java.io.Serializable;

public class TimeTime implements Serializable {
	private static final long serialVersionUID = -3544377498665801527L;

	public TimeTime() {
	}

	public TimeTime(int first, int last) {
		this.first = first;
		this.last = last;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + first;
		result = prime * result + last;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TimeTime other = (TimeTime) obj;
		if (first != other.first) {
			return false;
		}
		if (last != other.last) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TimeTime [first=" + first + ", last=" + last + "]";
	}

	int first;
	int last;
}
