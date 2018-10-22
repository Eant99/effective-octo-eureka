package com.googosoft.constant;

import org.springframework.stereotype.Component;

@Component("systemSet")
public class SystemSet {
	
	public static String XTBZ = "01"; //固定资产系统
	public static String oaBz="OA_";//资产表前缀标志
	
	/**
	 * 管理员账号
	 */
    public static String AdminRybh(){
         return "000000"; 
    }
    /**
     * 顶级单位的上级编号
     */
    public static String TopDwFlag(){
        return "000000";
    }
    /**
     * 顶级单位编号
     */
    public static String Dwbh(){
		return "000001";
    }
    
    public static String sysBz="ZC_SYS_";//系统表前缀标志
    public static String zcBz="ZC_";//资产表前缀标志
    public static String gxBz="GX_SYS_";//gx表前缀标志
    public static String address="http://localhost:8081";
    
    
}

