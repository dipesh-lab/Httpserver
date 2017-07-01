package com.axiom.http.server;

public interface CoreHttpServer extends Runnable {
	
	public void initServer(int port);
	
	public void startServer();
	
}
