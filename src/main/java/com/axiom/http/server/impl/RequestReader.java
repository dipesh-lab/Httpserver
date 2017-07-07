package com.axiom.http.server.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.axiom.http.constants.AppConstants;
import com.axiom.http.exception.InvalidRequestException;
import com.axiom.http.server.RequestProcessor;
import com.axiom.http.server.config.ApplicationProperties;

public class RequestReader extends AbstractRequestProcessor implements RequestProcessor {

	private static final Logger log = Logger.getLogger(RequestReader.class);
	
	private static final AtomicInteger COUNTER = new AtomicInteger(1);
	
	private final Charset charset;
	
	private static final int bufferLimit = 256;
	
	private ApplicationProperties props = ApplicationProperties.getInstance();
	
	RequestReader(Selector sel) {
		super(sel, SelectionKey.OP_WRITE);
		String charStr =  props.getProperty(AppConstants.CHARSET_KEY);
		charset = Charset.forName(charStr);
	}
	
	@Override
	public SelectableChannel process(SelectionKey key) {
		SocketChannel channel = null;
		try {
			channel = (SocketChannel) key.channel();
			byte[] data = readHttpMessage(channel);
			if(data.length == 1) throw new InvalidRequestException();
			log.debug("Request " + COUNTER.getAndIncrement() + " Data\n"+ new String(data, charset));
		} catch(IOException | InvalidRequestException e) {
			closeRequest(key,  channel);
			if(e.getClass().equals(IOException.class))
				log.error(e.getMessage(), e);
		}
		return channel;
	}
	
	private byte[] readHttpMessage(SocketChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(bufferLimit);
		int state;
		byte[] data = {1};
		int position = 0;
		while( (state = channel.read(buffer)) > 0 ) {
			buffer.flip();
			byte[] d = Arrays.copyOf(buffer.array(), state);
			data = Arrays.copyOf(data, position + state);
			System.arraycopy(d, 0, data, position, state);
			position = position + state;
			buffer.clear();
		}
		return data;
	}

}