package com.axiom.http.server.impl;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.axiom.http.server.RequestProcessor;

public class RequestAcceptor extends AbstractRequestProcessor implements RequestProcessor {

	private static Logger log= Logger.getLogger(RequestAcceptor.class);
	
	RequestAcceptor(Selector sel) {
		super(sel, SelectionKey.OP_READ);
	}
	
	@Override
	public SelectableChannel process(SelectionKey key) {
		SocketChannel channel = null;
		try {
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
			channel = serverSocketChannel.accept();
			if(Objects.nonNull(channel)) {
				channel.configureBlocking(false);
			}
		}catch(IOException ioe) {
			log.error(ioe.getMessage(), ioe);
		}
		return channel;
	}

}