package com.googosoft.websocket.echo;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

public class SessionMap {
	public static Map<String, Session> sessions = new HashMap<String, Session>();
}
