package com.axiom.http.server.impl;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.axiom.http.server.CoreHttpServer;

public class CoreHttpServerImpl implements CoreHttpServer {

	private final static Logger logger = Logger.getLogger(CoreHttpServerImpl.class);
	
	@Override
	public void init(Properties props) {
		// TODO Auto-generated method stub
		logger.debug("Init Server with properties");
	}

	@Override
	public void startServer() {
		// TODO Auto-generated method stub
		logger.debug("Starting Server...");
	}

	
}
