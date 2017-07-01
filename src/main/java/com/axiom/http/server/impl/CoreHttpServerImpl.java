package com.axiom.http.server.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.axiom.http.server.CoreHttpServer;

public class CoreHttpServerImpl implements CoreHttpServer {

	private final static Logger logger = Logger.getLogger(CoreHttpServerImpl.class);
	
	private boolean listen = false;
	
	private ServerSocketChannel serverSocket = null;
	
	private Selector selector = null;
	
	public CoreHttpServerImpl() {}
	
	@Override
	public void startServer() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		logger.debug("Server listening started");
		while(listen) {
			try {
				int count = selector.select();
	            if(count > 0) {
	                for(Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext();) {
	                    SelectionKey key = i.next();
	                    if(key.isAcceptable()) {
	                    	
	                    }
	                    if(key.isReadable()) {
	                    
	                    	i.remove();
	                    }
	                    if(key.isWritable()) {
	                    
	                    	i.remove();
	                    }
	                }
	            }
			}catch(IOException e) {}
		}
	}
	
	@Override
	public void initServer(int serverPort) {
		try {
			selector = Selector.open();
			serverSocket = ServerSocketChannel.open();
			serverSocket.configureBlocking(false);
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverPort);
			serverSocket.socket().bind(inetSocketAddress);
			serverSocket.register(selector, SelectionKey.OP_ACCEPT);
			listen = true;
			logger.debug("Server initialised sucessfully on port " + serverPort);
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}