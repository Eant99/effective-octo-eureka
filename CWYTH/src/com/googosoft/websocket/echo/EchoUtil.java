package com.googosoft.websocket.echo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.Message;
import com.googosoft.websocket.message.DshMessage;
import com.googosoft.websocket.message.MessageType;

public class EchoUtil {

	public static Map<String, EchoServer> echoMap = new HashMap<String, EchoServer>();
	private static Gson gson = new Gson();
	/**
	 * 获取socket连接对象
	 * @param key 用户id
	 * @return
	 */
	public static EchoServer getEchoServer(String key) {
		return echoMap.get(key);
	}
	/**
	 * 连接是否包含key
	 * @param key
	 * @return
	 */
	public static boolean contains(String key) {
		return echoMap.containsKey(key);
	}
	/**
	 * 发送消息
	 * @param key 接受者ryid
	 * @param msg 发送的消息
	 */
	public static void sendMessage(String key,String msg) {
		if(contains(key)) {
			echoMap.get(key).sendMessage(msg);
		}
	}
	/**
	 * 发送消息
	 * @param message
	 */
	public static void sendMessage(Message message) {
		String key = message.getReceiver();
		sendMessage(key,gson.toJson(message));
	}
	/**
	 * 批量发送待审核消息
	 * @param msgMap
	 */
	public static void batchSendDshxxMsg(DshInfoMap msgMap) {
		for(String key : msgMap.keySet()) {
			sendMessage(new DshMessage(key,MessageType.DSHXX, msgMap.get(key)));
		}
	}
}
