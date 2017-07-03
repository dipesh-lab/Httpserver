package com.axiom.http;

import org.apache.log4j.Logger;

import com.axiom.http.server.CoreHttpServer;
import com.axiom.http.server.config.ApplicationProperties;
import com.axiom.http.server.impl.CoreHttpServerImpl;

public class BootstrapHttpServer {
	
	private final static Logger log = Logger.getLogger(BootstrapHttpServer.class);
	
	public void bootServer() {
		ApplicationProperties PROPS = ApplicationProperties.getInstance();
		log.debug("Server Configuration loaded");
		int port = Integer.parseInt(PROPS.getProperty("server.port").trim());
		
		CoreHttpServer server = new CoreHttpServerImpl();
		server.initServer(port);
		server.startServer();
	}
	
}