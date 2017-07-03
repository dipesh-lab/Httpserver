package com.axiom.http.server.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.axiom.http.constants.AppConstants;
import com.axiom.http.server.RequestProcessor;
import com.axiom.http.server.config.ApplicationProperties;

public class RequestWriter extends AbstractRequestProcessor implements RequestProcessor {

	private static final Logger log = Logger.getLogger(RequestWriter.class);
	
	private String response;
	
	private ApplicationProperties props = ApplicationProperties.getInstance();
	
	RequestWriter(Selector sel) {
		super(sel, -1);
		response = props.getProperty(AppConstants.HTTP_RESPONSE);
	}
	
	@Override
	public SelectableChannel process(SelectionKey key) {
		SocketChannel channel = null;
		try {
			channel = (SocketChannel) key.channel();
			ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
			channel.write(buffer);
		} catch(IOException ioe) {
			log.error(ioe.getMessage(), ioe);
		} finally {
			closeRequest(key, channel);
		}
		return null;
	}

}