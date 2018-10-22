package com.googosoft.constant;
/**
 * 通用表设计类
 * @author Administrator
 *
 */
public class TnameU{
	//资产管理系统 
	public static String[][] xtbzs = {
                 {SystemSet.XTBZ,"资产管理系统",SystemSet.sysBz+"MKB",SystemSet.sysBz+"CZQXB",SystemSet.sysBz+"JSQXB",SystemSet.sysBz+"JSB",SystemSet.sysBz+"JSRYB",SystemSet.sysBz+"GLQXB",SystemSet.sysBz+"RYDWQXB"},
            };
	
	/**
	 * 获取通用表名
	 * @param xtbz 系统标识
	 * @param lx  xtname-系统名 ， mkb-模块表，czqx-操作权限 表，jsczqx-角色操作权限表，jsb-角色表，jsryb-角色人员表 ，glqx-管理权限表，rydwqx-人员单位权限 
	 * @return
	 */
    public static String getTname(String lx)
    {
    	for(int i=0;i<xtbzs.length;i++)
    	{
    			if(lx.equalsIgnoreCase("xtname"))//系统名称
    			{
    				return xtbzs[i][1];
    			}
    		    else if(lx.equalsIgnoreCase("mkb"))//模块表
		    	{
		    		return xtbzs[i][2];
		    	}
		    	else if(lx.equalsIgnoreCase("czqx"))//操作权限
		    	{
		                 return xtbzs[i][3];
		    	}
		    	else if(lx.equalsIgnoreCase("jsczqx"))//角色操作权限表
		    	{
			    		return xtbzs[i][4];
		    	}
		    	else if(lx.equalsIgnoreCase("jsb"))//角色表
		    	{
		    		return xtbzs[i][5];
		    	}
		    	else if(lx.equalsIgnoreCase("jsryb"))//人员角色表
		    	{
		    		return xtbzs[i][6];
		    	}
		    	else if(lx.equalsIgnoreCase("glqx"))//管理权限表
		    	{
		    		return xtbzs[i][7];
		    	}
		    	else if(lx.equalsIgnoreCase("rydwqx"))//人员单位权限
		    	{
		    		return xtbzs[i][8];
		    	}
    	}
    	return "";
    }
    
    /**
     * 管理权限表名
     */
    public static String GLQXB=TnameU.getTname("glqx");
    /**
     * 模块表名
     */
    public static String MKB=TnameU.getTname("mkb");
    /**
     * 操作权限表名
     */
    public static String CZQXB=TnameU.getTname("czqx");
    /**
     * 角色操作权限表名
     */
    public static String JSCZQXB=TnameU.getTname("jsczqx");
    /**
     * 角色表名
     */
    public static String JSB=TnameU.getTname("jsb");
    /**
     * 人员角色表名  
     */
    public static String JSRYB=TnameU.getTname("jsryb");
    /**
     * 人员单位权限表名
     */
    public static String RYDWQXB=TnameU.getTname("rydwqx");
}
