package com.axiom.http;

import org.apache.log4j.Logger;

import com.axiom.http.server.CoreHttpServer;
import com.axiom.http.server.config.ApplicationProperties;
import com.axiom.http.server.impl.CoreHttpServerImpl;

public class BootstrapHttpServer {
	
	private final static Logger logger = Logger.getLogger(BootstrapHttpServer.class);
	
	public void bootServer() {
		ApplicationProperties APP_PROPS = ApplicationProperties.getInstance();
		logger.debug("Server Configuration loaded");
		int port = Integer.parseInt(APP_PROPS.getProperty("server.port").trim());
		
		CoreHttpServer server = new CoreHttpServerImpl();
		server.initServer(port);
		server.startServer();
	}
	
}