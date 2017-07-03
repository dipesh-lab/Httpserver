package com.axiom.http.server.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.axiom.http.constants.AppConstants;
import com.axiom.http.exception.InvalidRequestException;
import com.axiom.http.server.RequestProcessor;
import com.axiom.http.server.config.ApplicationProperties;

public class RequestReader extends AbstractRequestProcessor implements RequestProcessor {

	private static final Logger log = Logger.getLogger(RequestReader.class);
	
	private static final AtomicInteger COUNTER = new AtomicInteger(1);
	
	private final String charset;
	
	private ApplicationProperties props = ApplicationProperties.getInstance();
	
	RequestReader(Selector sel) {
		super(sel, SelectionKey.OP_WRITE);
		charset =  props.getProperty(AppConstants.CHARSET_KEY);
	}
	
	@Override
	public SelectableChannel process(SelectionKey key) {
		SocketChannel channel = null;
		try {
			channel = (SocketChannel) key.channel();
			String data = readHttpMessage(channel);
			log.debug("Request " + COUNTER.getAndIncrement() + " Data\n" + data);
		} catch(IOException | InvalidRequestException e) {
			closeRequest(key,  channel);
			if(e.getClass().equals(IOException.class))
				log.error(e.getMessage(), e);
		}
		return channel;
	}
	
	private String readHttpMessage(SocketChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(128);
		if(channel.read(buffer) !=-1) {
			buffer.flip();
			return new String(buffer.array(), charset);
		}
		throw new InvalidRequestException();
	}

}