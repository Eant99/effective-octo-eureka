package com.googosoft.util.gen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.googosoft.util.Validate;

/**
 * 自动SQL生成工具
 * @author master
 * @version 0.1
 * 说明：目前仅仅实现了单表的sql语句生成，且生成的语句是适用于mybatis的配置文件的格式
 */
public class AutoSql {
	
	public static void main(String[] args) {
		AutoSql au = new AutoSql("CW_JKYWB_beifen","K");
		au.createSelectSql();
		au.createInsertSql();
		au.createUpdateSql();
		au.createPageSql();
	}
	// 数据库连接
		private static final String URL = "jdbc:oracle:thin:@192.168.11.111:1521:SHITAN";
		private static final String NAME = "ngycw";
		private static final String PASS = "ngycw";
		private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	//列名
	private String[] colNames;
	//表名
	private String tableName;
	//别名
	private String anotherName;
	
	/**
	 * 初始化类
	 * @param table
	 * @param another  别名（可缺省）
	 */
	public AutoSql(String table,String another){
		//创建连接
		Connection con = null;
		String sql = "select * from " + table;
		Statement pStemt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, NAME, PASS);
			pStemt = (Statement) con.createStatement();
			ResultSet rs = pStemt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnNum = rsmd.getColumnCount();
			colNames = new String[columnNum];
			for(int i=0;i<columnNum;i++){
				colNames[i] = rsmd.getColumnName(i+1).toLowerCase();
			}
			tableName = table;
			if(Validate.noNull(another)){
				anotherName = another;
			}
		} catch (ClassNotFoundException e) {
			System.out.println("数据库驱动类不存在，导致加载失败");
		} catch (SQLException e) {
			System.out.println("sql语句执行异常");
		}
	}
	/**
	 * 生成insert语句
	 * @return
	 */
	public String createInsertSql(){
		StringBuilder sql = new StringBuilder();
		sql.append("insert into "+tableName+"("+columnSql("")+") values("+columnSqlValue()+") ");
		System.out.println("插入语句："+sql.toString());
		return sql.toString();
	}
	/**
	 * 生成update语句
	 * @return
	 */
	public String createUpdateSql(){
		StringBuilder sql = new StringBuilder();
		sql.append("update "+tableName+" set "+columnUpdateSql());
		System.out.println("更新语句："+sql.toString());
		return sql.toString();
	}
	/**
	 * 生成select语句
	 * @return
	 */
	public String createSelectSql(){
		StringBuilder sql = new StringBuilder();
		sql.append("select "+columnSql(anotherName)+" from "+tableName +" ");
		if(Validate.noNull(anotherName)){
			sql.append(anotherName);
		}
		System.out.println("查询语句："+sql.toString());
		return sql.toString();
	}
	/**
	 * 分页语句
	 * @return
	 */
	public String createPageSql(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM (SELECT p.*, ROWNUM rn FROM ( SELECT ROWNUM,"
				+ columnSql(anotherName)+" FROM "+tableName 
				+" "+Validate.isNullToDefault(anotherName,"")+ " ${ORDER}) p WHERE ROWNUM <=#{LENGTH}) WHERE rn >#{START}");
		System.out.println("分页语句："+sql.toString());
		return "";
	}
	/**
	 * 拼装列名成sql片段
	 * 例子：rybh,xm,rygh
	 * @return
	 */
	public String columnSql(String another){
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < colNames.length; i++) {
			if(Validate.noNull(another)){
				if(i==0){
					sql.append(another+"."+colNames[i]);
				}else{
					sql.append(","+another+"."+colNames[i]);
				}
			}else{
				if(i==0){
					sql.append(colNames[i]);
				}else{
					sql.append(","+colNames[i]);
				}
			}
		}
		return sql.toString();
	}
	/**
	 * 拼装列名成sql插入片段
	 * @return
	 */
	public String columnSqlValue(){
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < colNames.length; i++) {
			if(i==0){
//				sql.append("#{"+colNames[i]+"}");
				sql.append("?");
			}else{
//				sql.append(",#{"+colNames[i]+"}");
				sql.append(",?");
			}
		}
		return sql.toString();
	}
	/**
	 * 更新sql语句片段
	 * @return
	 */
	public String columnUpdateSql(){
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < colNames.length; i++) {
			if(i==0){
//				sql.append("\""+colNames[i]+"\"=#{"+colNames[i]+"}");
				sql.append(""+colNames[i]+"=?");
			}else{
				sql.append(","+colNames[i]+"=?");
			}
		}
		return sql.toString();
	}
	
	
}
