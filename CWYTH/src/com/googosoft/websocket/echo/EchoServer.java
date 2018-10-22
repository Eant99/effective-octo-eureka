package com.googosoft.websocket.echo;

import java.io.IOException;
import java.util.List;

import javax.websocket.DecodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class EchoServer {
	private String fromUser;
	private Session session;
	@OnOpen
	public void initSession(Session session) {
		List<String> params = session.getRequestParameterMap().get("fromUser");
		if(params != null && params.size() > 0){
			this.fromUser = params.get(0);
			this.session = session;
			System.err.println("websocket::::::"+this.fromUser);
			EchoUtil.echoMap.put(this.fromUser, this);
			SessionMap.sessions.put(this.fromUser, session);
		}
	}
	@OnMessage
	public void printMessage(String message) {
		System.out.println("message:"+message);
		System.out.println("当前在线人数："+EchoUtil.echoMap.size());
	}
	@OnError  
	public void handleError(Throwable thw) {
        if (thw instanceof DecodeException) {
           System.out.println("Error decoding incoming message: " + ((DecodeException)thw).getText());
       } else {
           System.out.println("Server WebSocket error: " + thw.getMessage());
           thw.printStackTrace();
       }
    }
	
	@OnClose
	public void processClose(Session session){
		EchoUtil.echoMap.remove(this.fromUser);
	}
	
	public void sendMessage() {
		try {
			this.session.getBasicRemote().sendText("{\"size\":1,\"content\":\"hello world\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMessage(String str) {
		try {
			this.session.getBasicRemote().sendText(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
