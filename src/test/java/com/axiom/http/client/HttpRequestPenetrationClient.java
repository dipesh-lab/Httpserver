package com.axiom.http.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

public class HttpRequestPenetrationClient {
	
	private static Logger log = Logger.getLogger(HttpRequestPenetrationClient.class);
	
	public static void main(String[] args) throws Exception {
        for(int i = 0; i < 300; i++) {
        	execute();
	    }
    }
	
	public static void execute() {
		Socket clientSocket = null;
    	DataOutputStream outToServer = null;
    	BufferedReader inFromServer=null;
    	try {
    		clientSocket = new Socket("localhost", 7070);
        	outToServer = new DataOutputStream(clientSocket.getOutputStream());
	        outToServer.writeBytes("Hello Buddy");
	        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        String sentence = inFromServer.readLine();
	        log.debug("Message Received " + sentence);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		clientSocket.close();
	        	inFromServer.close();
	        	outToServer.flush();
	        	outToServer.close();
	        }catch(Exception e){e.printStackTrace();}
	    }
	}
	
}