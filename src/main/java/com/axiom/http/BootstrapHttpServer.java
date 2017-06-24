package com.axiom.http;

import com.axiom.http.server.CoreHttpServer;
import com.axiom.http.server.impl.CoreHttpServerImpl;

public class BootstrapHttpServer {

	
	public void bootServer() {
		CoreHttpServer server = new CoreHttpServerImpl();
		server.init(null);
		server.startServer();
	}
	
}