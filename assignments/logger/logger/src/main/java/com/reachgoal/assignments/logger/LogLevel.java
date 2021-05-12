package com.reachgoal.assignments.logger;

public enum LogLevel {

	DEBUG(0), INFO(1), WARN(2), ERROR(3), FATAL(4);
	private int priority;

	LogLevel(int priority) {
		this.priority = priority;
	}
}
