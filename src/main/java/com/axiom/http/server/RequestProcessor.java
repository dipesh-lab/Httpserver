package com.axiom.http.server;

import java.nio.channels.SelectionKey;

public interface RequestProcessor {

	public void handle(SelectionKey key) throws Exception;
	
}
