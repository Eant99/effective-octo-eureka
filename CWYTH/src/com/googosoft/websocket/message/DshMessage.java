package com.googosoft.websocket.message;

import java.util.List;

import com.googosoft.websocket.info.DshInfo;

/**
 * 待审核消息
 * @author Administrator
 *
 */
public class DshMessage extends Message{

	
	public DshMessage() {
		super();
	}
	/**
	 * 
	 * @param receiver 接受者ryid
	 * @param msgType 消息类型
	 * @param msg 消息集合
	 */
	public DshMessage(String receiver, MessageType msgType, List<DshInfo> msg) {
		super(receiver, msgType, msg);
	}
	
	
}
