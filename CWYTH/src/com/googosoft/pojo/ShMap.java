package com.googosoft.pojo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShMap {
	public static ConcurrentHashMap<String,Map> shmaps = new ConcurrentHashMap<String,Map>();
}
