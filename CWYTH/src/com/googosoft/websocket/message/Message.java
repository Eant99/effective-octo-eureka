package com.googosoft.websocket.message;

public abstract class Message{

	private String receiver;//接受者
	private MessageType msgType;//消息类型
	private Object msg;//消息
	
	public Message() {
		super();
	}

	public Message(String receiver, MessageType msgType, Object msg) {
		super();
		this.receiver = receiver;
		this.msgType = msgType;
		this.msg = msg;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	
	
}
