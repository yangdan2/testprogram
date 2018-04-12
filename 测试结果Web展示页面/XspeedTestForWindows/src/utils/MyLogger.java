package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MyLogger {
	private static MyLogger config;
    private static Logger logger;
    private MyLogger(){
    	String file="log4j.properties";
    	logger = Logger.getLogger("log");
    	PropertyConfigurator.configure("log4j.properties");
    }
    public static MyLogger getInstance(){
    	if(config==null){
    		config = new MyLogger();
    	}
		return config;
    }
    public Logger getlogger(){
    	return this.logger;
    }
}
