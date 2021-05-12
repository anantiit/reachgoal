package com.reachgoal.assignments.logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class LoggerDriver {
	public static String LOG_CONFIG_FILE = "src/main/resources/logger_config.xml";
	private static String TIME_FORMAT = "timeFormat";
	private String LOG_LEVEL = "logLevel";
	private String LOGGER_TYPE = "loggerType";
	private String BUFFERE_SIZE = "bufferSize";
	private String SINK_TYPE = "sinkType";

	private LoggerDriver() {

	}

	public static void init() {
		try (BufferedReader reader = new BufferedReader(new FileReader(LOG_CONFIG_FILE))) {
			/*
			 * Sample Config: ts_format:dd-mm-yyyy-hh-mm-ss log_level:INFO logger_type:ASYNC
			 * buffer_size:25 sink_type:STDOUT
			 * 
			 **/
			String line = null;
			List<LoggerConfig> loggerConfigs = new ArrayList<LoggerConfig>();
			while (line = reader.readLine() != null) {
				String[] configValues = line.split(" ");
				if(!configValues.isEmpty()) {
				for(int i=0;i<  configValues.length();i++) {
					LoggerConfig loggerConfig = new LoggerConfig();
				switch(configValues[i]) {
				configValue = configValues[i+1];
				case TIME_FORMAT: 
					loggerConfig.setTimeFormat();
				case LOG_LEVEL:
			    
				}
				}
				loggerConfigs.add(loggerConfig);
				}
				
			}
		} catch(final FileNotFoundException e) {
			System.out.println(LOG_CONFIG_FILE + " config file not found. Hence exiting..");
		} catch (final IOException e) {
			System.out.println(LOG_CONFIG_FILE + " config could not open file, Hence exiting..");
		}

	}

}
