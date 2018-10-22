package com.googosoft.util;

import java.util.UUID;

public class CoderHelper {
	
	public static  String generateId(){
		return UUID.randomUUID().toString().replace("-", "");
		 
	}
}
