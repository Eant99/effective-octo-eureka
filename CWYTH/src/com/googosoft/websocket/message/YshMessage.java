package com.googosoft.websocket.message;

public class YshMessage extends Message{

	public YshMessage() {
		super();
	}
	/**
	 * 
	 * @param receiver 接受者ryid
	 * @param msgType 消息类型
	 * @param msg 消息集合
	 */
	public YshMessage(String receiver, MessageType msgType, String[] msg) {
		super(receiver, msgType, msg);
	}
}
