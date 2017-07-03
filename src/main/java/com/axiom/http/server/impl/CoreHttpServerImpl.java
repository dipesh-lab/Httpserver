package com.axiom.http.server.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.axiom.http.server.CoreHttpServer;
import com.axiom.http.server.RequestProcessor;

public class CoreHttpServerImpl implements CoreHttpServer {

	private final static Logger logger = Logger.getLogger(CoreHttpServerImpl.class);
	
	private boolean listen = false;
	
	private ServerSocketChannel serverSocket = null;
	
	private Selector selector = null;
	
	private Map<Integer, RequestProcessor> processors = new ConcurrentHashMap<>(3, 1.0f);
	
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
	                    i.remove();
	                    if(key.isAcceptable()) {
	                    	RequestProcessor processor = processors.get(SelectionKey.OP_ACCEPT);
	                    	processor.handleRequest(key);
	                    }
	                    if(key.isReadable()) {
	                    	RequestProcessor processor = processors.get(SelectionKey.OP_READ);
	                    	processor.handleRequest(key);
	                    }
	                    if(key.isWritable()) {
	                    	RequestProcessor processor = processors.get(SelectionKey.OP_WRITE);
	                    	processor.handleRequest(key);
	                    }
	                }
	            }
			}catch(Exception e) {}
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
			
			processors.put(SelectionKey.OP_ACCEPT, new RequestAcceptor(selector));
			processors.put(SelectionKey.OP_READ, new RequestReader(selector));
			processors.put(SelectionKey.OP_WRITE, new RequestWriter(selector));
			logger.debug("Server initialised sucessfully on port " + serverPort);
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}