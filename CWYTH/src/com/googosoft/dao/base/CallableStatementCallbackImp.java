package com.googosoft.dao.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * 存储过程结果集处理工具类
 * @author master
 */
@SuppressWarnings("rawtypes")
public class CallableStatementCallbackImp{
	
	private static Logger logger = Logger.getLogger(CallableStatementCallbackImp.class);
	
	/**
	 * ResultSet结果集封装成list数据
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public static List resultSetToList(ResultSet rs) throws SQLException{
		List list = new ArrayList();
		Object[] columnName = getColumnName(rs);
		int len = columnName.length;
		while(rs.next()){
			Map map = new HashMap();
			for (int i = 1; i <= len; i++) {
					try {
						map.put(columnName[i-1], rs.getObject(i));
					} catch (SQLException e) {
						logger.debug("获取ResultSet结果异常");
						e.printStackTrace();
					}
			}
			list.add(map);
		}
		return list;
	}
	/**
	 * ResultSet结果集获取列名数组
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Object[] getColumnName(ResultSet rs){
		List<String> list = new ArrayList<String>();
		ResultSetMetaData rm;
		try {
			rm = rs.getMetaData();
			for (int i = 1,len=rm.getColumnCount(); i <= len; i++) {
				list.add(rm.getColumnName(i).toUpperCase());
			}
		} catch (SQLException e) {
			logger.debug("获取ResultSet结果集列名异常");
			e.printStackTrace();
		}
		return list.toArray();
	}
	
	

}
