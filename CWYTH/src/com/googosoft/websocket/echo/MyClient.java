package com.googosoft.websocket.echo;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class MyClient {
    @OnOpen
    public void onOpen(Session session) {}
    
    @OnClose
	public void processClose(Session session){}
   
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}