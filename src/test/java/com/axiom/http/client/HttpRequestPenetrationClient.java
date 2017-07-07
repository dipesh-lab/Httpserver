package com.axiom.http.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

public class HttpRequestPenetrationClient {
	
	private static Logger log = Logger.getLogger(HttpRequestPenetrationClient.class);
	
	public static void main(String[] args) throws Exception {
        for(int i = 0; i < 20; i++) {
        	execute();
	    }
    }
	
	public static void execute() {
		Socket clientSocket = null;
    	DataOutputStream outToServer = null;
    	BufferedReader inFromServer=null;
    	try {
    		clientSocket = new Socket("localhost", 9000);
        	outToServer = new DataOutputStream(clientSocket.getOutputStream());
	        outToServer.writeBytes("Http Request asl djasld jasdlaskj daslkjd aslk djaslkdjaslkd jaslkdjasld kjasld jasdljasd lajs dlasjdlasjdlasj daslkjd aslkd jasldjasld jasld jasld jasld kjasd lkasjd lkasjd lasjdlasjdlasjdlkasj dlasjdlasjd lasj ljas dlasj dlkasjd lkasjd lkasjd lkasjdlkasjd lkasjdlkasjd lasjdlasj lasj lasjd lkasjd lkasjdlk asjd lkasjd lkajsdlk jasldk j asld jasl lajs dlasjdlasjdal sdj asljd asljdaslkjdasljdalsjdlasjdalsjdsaljd lasjd lasjdlasjd lasjd lj alks jdalksdj aslksd jasldk jas ldkjas aslkd jalkd jasl asjd lasjdalskj ended");
	        
	        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        String sentence = inFromServer.readLine();
	        log.debug("Message Received " + sentence);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	        	inFromServer.close();
	    		outToServer.flush();
		        outToServer.close();
		        clientSocket.close();
	        }catch(Exception e){e.printStackTrace();}
	    }
	}
	
}