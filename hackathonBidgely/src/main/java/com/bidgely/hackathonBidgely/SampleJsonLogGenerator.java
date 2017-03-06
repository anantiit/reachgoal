package com.bidgely.hackathonBidgely;

/**
 * Hello world!
 *
 */
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;



public class SampleJsonLogGenerator {

static Logger jsonLogger = LogManager.getLogger("jsonLogger");
static Logger debugLogger = LogManager.getLogger("debugLogger");


public static void main(String[] args) {
	
	while(true){
		try{
	raiseException(0);
	Thread.sleep(2000);
	}
		catch(InterruptedException ie){
			jsonLogger.error("failed",ie);
		}
	}
	
}


private static void raiseException(int i) {
	try{
	debugLogger.error("col1 col2 "+ i+" "+" col4 col5 "+debugLogger.getName()+" "+System.currentTimeMillis());
		if(i>10){
			throw new IllegalArgumentException("i should not be more than 10");
		}
		else{
		raiseException(i+1);
		}
   }
	catch(IllegalArgumentException iae){
		jsonLogger.error("col1 col2 "+i+" "+" col4 col5 "+System.currentTimeMillis(),iae);
		debugLogger.error("col1 col2 "+i+" "+" col4 col5 "+System.currentTimeMillis(),iae);
	}
}

}