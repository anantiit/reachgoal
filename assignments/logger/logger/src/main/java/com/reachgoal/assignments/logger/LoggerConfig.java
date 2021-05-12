package com.reachgoal.assignments.logger;

public class LoggerConfig {
	String timeFormat;
	LogLevel logLevel;
	LoggerType loggerType;
	int bufferSize;
	SinkType sinkType;

	public LoggerConfig() {

	}

	public LoggerConfig(String timeFormat, LogLevel logLevel, LoggerType loggerType, int bufferSize,
			SinkType sinkType) {
		super();
		this.timeFormat = timeFormat;
		this.logLevel = logLevel;
		this.loggerType = loggerType;
		this.bufferSize = bufferSize;
		this.sinkType = sinkType;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public LoggerType getLoggerType() {
		return loggerType;
	}

	public void setLoggerType(LoggerType loggerType) {
		this.loggerType = loggerType;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public SinkType getSinkType() {
		return sinkType;
	}

	public void setSinkType(SinkType sinkType) {
		this.sinkType = sinkType;
	}

}
