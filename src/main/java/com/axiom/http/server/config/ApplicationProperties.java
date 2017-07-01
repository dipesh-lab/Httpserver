package com.axiom.http.server.config;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationProperties {
	
	private final static Logger logger = Logger.getLogger(ApplicationProperties.class);

	private static ApplicationProperties APPLICATION_PROPERTIES = new ApplicationProperties();
	
	private Properties properties = new Properties();
	
	private ApplicationProperties() {
		readConfig();
	}
	
	public static ApplicationProperties getInstance() {
		return APPLICATION_PROPERTIES;
	}
	
	private void readConfig() {
		try (InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
			properties = new Properties();
			properties.load(inStream);
			logger.debug("Application Config Read Complete");
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public String getProperty(final String key) {
		return properties.getProperty(key);
	}
	
}
