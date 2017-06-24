package com.axiom.http.server;

import java.util.Properties;

public interface CoreHttpServer {

	public void init(Properties props);
	
	public void startServer();
	
}
