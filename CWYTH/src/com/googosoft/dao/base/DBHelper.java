package com.googosoft.dao.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import oracle.jdbc.OracleTypes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.pojo.PageList;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

public class DBHelper extends JdbcTemplate{
	/**
	 * 分页存储过程生成列表页面数据
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public PageList getPageList(final PageList page) throws SQLException{
		String procName = "{CALL PROC_SYS_PAGERLIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		this.execute(procName, new CallableStatementCallback() {
			@Override 
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, page.getTableName());
		        cs.setString(2,page.getStrWhere());
		        cs.setString(3,page.getOrderBy());
		        cs.setString(4,page.getCurPage());
		        cs.setString(5,page.getPage_record());
		        cs.setString(6, page.getPage_length());
		        cs.setString(7,page.getSqlText());
		        cs.setString(8,page.getKeyId());
		        cs.setString(9,page.getHj1());
		        cs.registerOutParameter(10, OracleTypes.VARCHAR);
		        cs.registerOutParameter(11, OracleTypes.CURSOR);
		        cs.registerOutParameter(12, OracleTypes.CURSOR);
		        cs.registerOutParameter(13, OracleTypes.CURSOR);
				cs.execute();
				//获取三个结果集
				String pageNum = cs.getString(10);
				ResultSet rs1 = (ResultSet) cs.getObject(11);
				ResultSet rs2 = (ResultSet) cs.getObject(12);
				ResultSet rs3 = (ResultSet) cs.getObject(13);		
				List list1 = CallableStatementCallbackImp.resultSetToList(rs1);
				List list2 = CallableStatementCallbackImp.resultSetToList(rs2);
				List list3 = CallableStatementCallbackImp.resultSetToList(rs3);
				page.setTotalList(list1);
				page.setHjList(list2);
				page.setContentList(list3);
				page.setPage_length(pageNum);
				return page;
			}
		});
		return page;
	}
	
	/**
	 * 通过存储过程获取结果集(适合于有多个输出参数的方法)
	 * @param proName 存储过程名
	 * @param parInSet 输入参数集合
	 * @param parOutSet 输出参数集合
	 * @return 返回Map<Object,Object>  根据parOutSet不同，返回的类型不同，key的类型跟parOutSet中Entry的key的类型一致
	 * @throws SQLException 
	 */
	public Map<String,Object> queryForMapByProcedure(String proName,Set<Entry<String,String>> parInSet,Set<Entry<String, Integer>> parOutSet) throws SQLException{
		Connection conn = this.getDataSource().getConnection();
		
		int cnt = parInSet.size() + parOutSet.size();
		String s = "";
		if(cnt > 0){
			s = "?";
			for(int i = 1; i < cnt; i++){
				s += ",?";
			}
		}
		List list;
		CallableStatement cstmt = conn.prepareCall("{CALL " + proName + "(" + s + ")}");

		if(Validate.noNull(parInSet)){
			for(Entry<String,String> entry : parInSet){
				cstmt.setObject(entry.getKey(), entry.getValue());
			}
		}
		if(Validate.noNull(parOutSet)){
			for(Entry<String,Integer> entry : parOutSet){
				cstmt.registerOutParameter(entry.getKey(), entry.getValue());
			}
		}
	    Map<String,Object> map = new HashMap<String,Object>();
		try{
	      	cstmt.execute();
	      	if(Validate.noNull(parOutSet)){
		      	ResultSet rs;
		      	for(Entry<String,Integer> entry : parOutSet){
		      		if(entry.getValue() == OracleTypes.CURSOR){
			      		rs = (ResultSet) cstmt.getObject(entry.getKey());
						map.put(entry.getKey(), CallableStatementCallbackImp.resultSetToList(rs));
					}else{
						map.put(entry.getKey(), cstmt.getObject(entry.getKey()));
					}
				}
	      	}
	    }catch(SQLException e) {
	    	logger.error("执行存储过程异常："+e.getMessage());
	    	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    }
      	finally{
      		if(Validate.noNull(cstmt)){
      			cstmt.close();
      		}
      		if(Validate.noNull(conn)){
      			conn.close();
      		}
		}
	    return map;
	}
	
	/**
	 * 通过存储过程获取结果集(适合于有多个输出参数的方法)
	 * @param proName 存储过程名
	 * @param parInList 输入参数集合
	 * @param parOutList 输出参数集合
	 * @return 返回Map<Integer,Object>
	 * @throws SQLException 
	 */
	public Map<Integer,Object> queryForMapByProcedure(String proName,List parInList,List parOutList) throws SQLException{
		Connection conn = this.getDataSource().getConnection();
		
		int cnt = parInList.size() + parOutList.size();
		String s = "";
		if(cnt > 0){
			s = "?";
			for(int i = 1; i < cnt; i++){
				s += ",?";
			}
		}
		
		List list;
		CallableStatement cstmt;
		if(Validate.isNull(s)){
			cstmt = conn.prepareCall("{CALL " + proName + "}");
		}
		else{
			cstmt = conn.prepareCall("{CALL " + proName + "(" + s + ")}");
		}
		
		cnt = parInList.size();
		for(int i = 0; i < cnt; i++){
			cstmt.setObject((i + 1), parInList.get(i));
		}
		for(int i = 0; i < parOutList.size(); i++){
			++cnt;
			cstmt.registerOutParameter(cnt, (int)parOutList.get(i));
		}
	    Map<Integer,Object> map = new HashMap<Integer,Object>();
		try{
	      	cstmt.execute();
	      	cnt = parInList.size();
	      	ResultSet rs;
	      	for(int i = 0; i < parOutList.size(); i++){
		      	++cnt;
		      	if((int)parOutList.get(i) == OracleTypes.CURSOR){
		      		rs = (ResultSet) cstmt.getObject(cnt);
		      		if(rs == null){
		      			map.put(i,new ArrayList());
		      		}
		      		else{
		      			map.put(i, CallableStatementCallbackImp.resultSetToList(rs));
		      		}
		      	}else{
		      		map.put(i, cstmt.getObject(cnt));
		      	}
	      	}
	    }catch(Exception e) {
	    	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    	logger.error("执行存储过程异常："+e.getMessage());
	    }
      	finally{
      		if(Validate.noNull(cstmt)){
      			cstmt.close();
      		}
      		if(Validate.noNull(conn)){
      			conn.close();
      		}
		}
	    return map;
	}

	/**
	 * 执行存储过程(适合于没有参数的方法)
	 * @param proName 存储过程名
	 * @return 执行结果
	 * @throws SQLException 
	 */
	public boolean batchUpdateByProcedure(String proName) throws SQLException{
		return batchUpdateByProcedure(proName,null);
	}
	/**
	 * 执行存储过程(适合于没有输出参数的方法)
	 * @param proName 存储过程名
	 * @param parList 输入参数集合
	 * @return 执行结果
	 * @throws SQLException 
	 */
	public boolean batchUpdateByProcedure(String proName,List<Object> parList) throws SQLException{
		Connection conn = this.getDataSource().getConnection();
		CallableStatement cstmt;
		
		if(Validate.isNull(parList)){
			cstmt = conn.prepareCall("{CALL " + proName + "}");
		}
		else{
			int cnt = parList.size();
			String s = "";
			if(cnt > 0){
				s = "?";
				for(int i = 1; i < cnt; i++){
					s += ",?";
				}
			}
			List list;
			cstmt = conn.prepareCall("{CALL " + proName + "(" + s + ")}");
	
			for(int i = 0; i < cnt; i++){
				if(parList.get(i) instanceof Entry){
					Entry entry = (Entry)parList.get(i);
					String key = (String)entry.getKey();
					if(entry.getValue() instanceof Integer){
						cstmt.setInt(key,(Integer)entry.getValue());
					}
					else if(entry.getValue() instanceof Double){
						cstmt.setDouble(key, (Double)entry.getValue());
					}
					else if(entry.getValue() instanceof Date){
						cstmt.setDate(key, (Date)entry.getValue());
					}
					else if(entry.getValue() instanceof String){
						cstmt.setString(key, (String)entry.getValue());
					}
					else{
						cstmt.setObject(key, (Object)entry.getValue());
					}
				}
				else{
					int xh = i + 1;
					if(parList.get(i) instanceof Integer){
						cstmt.setInt(xh,(Integer)parList.get(i));
					}
					else if(parList.get(i) instanceof Double){
						cstmt.setDouble(xh, (Double)parList.get(i));
					}
					else if(parList.get(i) instanceof Date){
						cstmt.setDate(xh, (Date)parList.get(i));
					}
					else{
						cstmt.setString(xh, (String)parList.get(i));
					}
				}
			}
		}
	    try{
	      	int r = cstmt.executeUpdate();
	      	if(r > 0){
	      		return true;
	      	}
	      	else{
	      		return false;
	      	}
	    }catch(Exception e) {
	    	logger.error("执行存储过程异常："+e.getMessage());
	    	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	  	  	return false;
	    }
      	finally{
      		if(Validate.noNull(cstmt)){
      			cstmt.close();
      		}
      		if(Validate.noNull(conn)){
      			conn.close();
      		}
		}
	}
	
	/**
	 * 通过存储过程获取结果集(只适合于只有一个输出参数的方法)
	 * @param proName 存储过程名
	 * @param parInList 参数集合（这里只需要输入参数即可）
	 * @return 返回结果集
	 * @throws SQLException 
	 */
	public List queryForListByProcedure(String proName,List parInList) throws SQLException{
		List<Integer> parOutList = new ArrayList();
		parOutList.add(OracleTypes.CURSOR);
		Map map = queryForMapByProcedure(proName,parInList,parOutList);
		if(map.isEmpty()){
			return new ArrayList();
		}
		else{
			return (List)map.get(0);
		}
	}
	
	/**
	 * 通过存储过程获取结果集(只适合于只有一个输出参数的方法，并且输出游标的名称必须是my_cursor)
	 * @param proName 存储过程名
	 * @param parInSet 输入参数集合（这里只需要输入参数即可）
	 * @return 返回结果集
	 * @throws SQLException 
	 */
	public List queryForListByProcedure(String proName,Set<Entry<String,String>> parInSet) throws SQLException{
		Map<String,Integer> parOutMap = new HashMap<String,Integer>();
		parOutMap.put("my_cursor", OracleTypes.CURSOR);
		Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
		Map map = queryForMapByProcedure(proName,parInSet,parOutSet);
		if(map.isEmpty()){
			return new ArrayList();
		}else{
			return (List)map.get("my_cursor");
		}
	}
	
	/**
	 * 通过存储过程获取结果集(只适合于只有一个输出参数的方法)
	 * @param proName 存储过程名
	 * @param parInSet 输入参数集合（这里只需要输入参数即可）
	 * @param cursorName 输出游标的名称
	 * @return 返回结果集
	 * @throws SQLException 
	 */
	public List queryForListByProcedure(String proName,Set<Entry<String,String>> parInSet,String cursorName) throws SQLException{
		Map<String,Integer> parOutMap = new HashMap<String,Integer>();
		parOutMap.put(cursorName, OracleTypes.CURSOR);
		Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
		Map map = queryForMapByProcedure(proName,parInSet,parOutSet);
		if(map.isEmpty()){
			return new ArrayList();
		}else{
			return (List)map.get(cursorName);
		}
	}
	public List queryForListByProcedure(String proName,List parInList,String s) throws SQLException{
		List<Integer> parOutList = new ArrayList();
		parOutList.add(OracleTypes.CURSOR);
		Map map = queryForMapByProcedure(proName,parInList,parOutList,s);
		if(map.isEmpty()){
			return new ArrayList();
		}
		else{
			return (List)map.get(0);
		}
	}
	/**
	 * 通过存储过程获取结果集(适合于有多个输出参数的方法)
	 * @param proName 存储过程名
	 * @param parInList 参数集合
	 * @param parOutList 输出参数集合
	 * @param s       有多少个参数，就传多少个?，中间以,分隔，注意：这里的参数包括输入参数和输出参数两种
	 * @return 返回Map<Integer,Object>
	 * @throws SQLException 
	 */
	public Map<Integer,Object> queryForMapByProcedure(String proName,List parInList,List parOutList,String s) throws SQLException{
		Connection conn = this.getDataSource().getConnection();
		List list;
		int cnt = parInList.size();
		CallableStatement cstmt = conn.prepareCall("{CALL " + proName + "(" + s + ")}");
		for(int i = 0; i < cnt; i++){
			cstmt.setObject((i + 1), parInList.get(i));
		}
		for(int i = 0; i < parOutList.size(); i++){
			++cnt;
			cstmt.registerOutParameter(cnt, (int)parOutList.get(i));
		}
	    Map<Integer,Object> map = new HashMap();
		try{
	      	cstmt.execute();
	      	cnt = parInList.size();
	      	ResultSet rs;
	      	for(int i = 0; i < parOutList.size(); i++){
		      	++cnt;
		      	if((int)parOutList.get(i) == OracleTypes.CURSOR){
		      		rs = (ResultSet) cstmt.getObject(cnt);
			      	map.put(i, CallableStatementCallbackImp.resultSetToList(rs));
			      	rs.close();
		      	}
		      	else{
		      		map.put(i, cstmt.getObject(cnt));
		      	}
	      	}
	      	cstmt.close();
	    }catch(SQLException e) {
	  	  	e.printStackTrace();
	    }
      	finally{
      		conn.close();
		}
	    return map;
	}
	/**
	 * 单位内和单位间调拨、项目变动、附件增加、单价变动
	 * 归口人员存储过程取归口人员信息
	 * @param mkbh 模块编号
	 * @param gkry 已选择的归口人员
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getGuikouList(final String mkbh, final String gkry){
		String procName = "{CALL PROC_ZC_GKRYFL(?,?,?)}";
		List list = this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, mkbh);
				cs.setString(2, gkry);
		        cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.execute();
				//获取三个结果集
				ResultSet rs = (ResultSet) cs.getObject(3);		
				List list = CallableStatementCallbackImp.resultSetToList(rs);
				return list;
			}
		});
		return list;
	}
	/**
	 * 返回list
	 * @param mkbh
	 * @param gkry
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getListByPro(final List proInList, String proName,List proOutList){
		List list = this.execute(proName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				for(int i=0,len=proInList.size();i<len;i++){
					cs.setString(i+1, Validate.isNullToDefaultString(proInList.get(i), ""));
				}
		        cs.registerOutParameter(proInList.size()+1, OracleTypes.CURSOR);
				cs.execute();
				//获取结果集
				ResultSet rs = (ResultSet) cs.getObject(proInList.size()+1);		
				List list = CallableStatementCallbackImp.resultSetToList(rs);
				return list;
			}
		});
		return list;
	}
	/**
	 * r_tname 表名, r_keyid 生成编号字段,r_len 流水编号长度,r_year  年,r_month 月
	 * @param tableName
	 * @param id
	 * @param len
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCodeNew(final String tableName,final String id,final String len,final String year,final String month){
		String procName = "{CALL PROC_SYS_MAKEDBHNEW(?,?,?,?,?,?,?,?)}";
		String primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, tableName);
				cs.setString(2, id);
				cs.setString(3, len);
				cs.setString(4, year);
				cs.setString(5, month);
				cs.setString(6, "1");
				cs.registerOutParameter(7,OracleTypes.VARCHAR);
				cs.registerOutParameter(8,OracleTypes.CURSOR);
				cs.execute();
				return cs.getString(7);
			}
		});
		return primaryKey;
	}
	
	/**
	 * r_tname 表名, r_keyid 生成编号字段,r_len 流水编号长度,r_year  年,r_month 月
	 * @param tableName
	 * @param id
	 * @param len
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCodeNewOne(final String tableName,final String id,final String len,final String bz,final String year,final String month){
		String procName = "{CALL PROC_SYS_MAKEDBHNEW(?,?,?,?,?,?,?,?,?)}";
		String primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, tableName);
				cs.setString(2, id);
				cs.setString(3, len);
				cs.setString(4, bz);
				cs.setString(5, year);
				cs.setString(6, month);
				cs.setString(7, "1");
				cs.registerOutParameter(8,OracleTypes.VARCHAR);
				cs.registerOutParameter(9,OracleTypes.CURSOR);
				cs.execute();
				return cs.getString(8);
			}
		});
		return primaryKey;
	}
	/**
	 * r_tname 表名, r_keyid 生成编号字段,r_len 流水编号长度,r_year  年,r_month 月
	 * @param tableName
	 * @param id
	 * @param len
	 * @param year
	 * @param month
	 * @return
	 */
	
	public String getCodeNewOnes(final String tableName,final String id,final String len,String bz,final String time,final String month){
		bz=bz+time; //前缀
		String sql = "select count(1) from "+tableName+" where "+id+" like '%"+bz+"%'   ";
		String count = super.queryForObject(sql,new Object[]{}, String.class);
		String djbh="";
		if("0".equals(count)){
			  djbh=bz+"0001";
			return djbh;
			
		}else{
			String selectsql="select max(substr("+id+",10,14)) from "+tableName+" where "+id+" like '%"+bz+"%'";
			String h=super.queryForObject(selectsql, new Object[]{},String.class);
			DecimalFormat df = new DecimalFormat("0000");
			 djbh =bz+ df.format(1 + Integer.parseInt(h));
			return djbh;
			
		}


		
	}
	/**
	 * r_tname 表名, r_keyid 生成编号字段,r_len 流水编号长度,r_year  年,r_month 月
	 * @param tableName
	 * @param id
	 * @param len
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getCodeNew(final String tableName,final String id,final String len,final String year,final String month,final String sl){
		String procName = "{CALL PROC_SYS_MAKEDBHNEW(?,?,?,?,?,?,?,?)}";
		List primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, tableName);
				cs.setString(2, id);
				cs.setString(3, len);
				cs.setString(4, year);
				cs.setString(5, month);
				cs.setString(6, sl);
				cs.registerOutParameter(7,OracleTypes.VARCHAR);
				cs.registerOutParameter(8,OracleTypes.CURSOR);
				cs.execute();
				//获取三个结果集
				ResultSet rs = (ResultSet) cs.getObject(8);
				List list = CallableStatementCallbackImp.resultSetToList(rs);
				return list;
			}
		});
		return primaryKey;
	}
	/**
	 * 包含保留位的yqbh
	 * @param flh
	 * @param shl
	 * @param year
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getYqbhNew(final String flh,final int shl,final String year){
		String procName = "{CALL PROC_ZC_MakeCardCode(?,?,?,?)}";
		List primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, year);
				cs.setString(2, flh);
				cs.setInt(3, shl);
				cs.registerOutParameter(4,OracleTypes.CURSOR);
				cs.execute();
				ResultSet rs = (ResultSet) cs.getObject(4);
				return CallableStatementCallbackImp.resultSetToList(rs);
			}
		});
		return primaryKey;
	}
	
	public List getCgls(final String rybh) {
		// TODO Auto-generated method stub
		String procName = "{CALL proc_cg(?,?,?)}";
		@SuppressWarnings("unchecked")
		List list = this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, rybh);
				cs.registerOutParameter(2, OracleTypes.VARCHAR);
		        cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.execute();
				ResultSet rs = (ResultSet) cs.getObject(3);
				List list = CallableStatementCallbackImp.resultSetToList(rs);
				return list;
			}
		});
		return list;
	}
	
	public List getBhls(final String rybh) {
		// TODO Auto-generated method stub
		String procName = "{CALL proc_bh(?,?,?)}";
		@SuppressWarnings("unchecked")
		List list = this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, rybh);
				cs.registerOutParameter(2, OracleTypes.VARCHAR);
		        cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.execute();
				ResultSet rs = (ResultSet) cs.getObject(3);
				List list = CallableStatementCallbackImp.resultSetToList(rs);
				return list;
			}
		});
		return list;
	}
	/**
	 * 获取驳回数量
	 * @param rybh
	 * @return sl
	 */
	public String getBhsl(final String rybh) {
		// TODO Auto-generated method stub
		String procName = "{CALL proc_bh(?,?,?)}";
		@SuppressWarnings("unchecked")
		String sl = this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, rybh);
				cs.registerOutParameter(2, OracleTypes.VARCHAR);
		        cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.execute();
				ResultSet rs = (ResultSet) cs.getObject(3);
				List list = CallableStatementCallbackImp.resultSetToList(rs);
				return cs.getString(2);
			}
		});
		return sl;
	}
	/**
	 * 人员编号
	 * @param tableName
	 * @param id
	 * @param len
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getRybhByProcedure(final String tableName,final String id,final String len){
		String procName = "{CALL PROC_SYS_MAKERYBH(?,?,?,?)}";
		String primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, tableName);
				cs.setString(2, id);
				cs.setString(3, len);
				cs.registerOutParameter(4,OracleTypes.VARCHAR);
				cs.execute();
				return cs.getString(4);
			}
		});
		return primaryKey;       
	}
	/**
	 * 单位编号
	 * @param tableName 表名
	 * @param id 主键
	 * @param len 长度
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getDWbhByProcedure(final String tableName,final String id,final String len,final String year){
		String procName = "{CALL PROC_SYS_MAKEDBH(?,?,?,?,?)}";
		String primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, tableName);
				cs.setString(2, id);
				cs.setString(3, len);
				cs.setString(4, year);
				cs.registerOutParameter(5,OracleTypes.VARCHAR);
				cs.execute();
				return cs.getString(5);
			}
		});
		return primaryKey;            
	}
	/**
	 * 有序位数的主键编号
	 * @param tableName 表名
	 * @param id 主键
	 * @param len 长度
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getBhByProcedure(final String tableName,final String id,final String len){
		String procName = "{CALL PROC_SYS_MAKECKBH(?,?,?,?,?,?)}";
		String primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, tableName);
				cs.setString(2, id);
				cs.setString(3, len);
				cs.setString(4, "");
				cs.setString(5, "");
				cs.registerOutParameter(6,OracleTypes.VARCHAR);
				cs.execute();
				return cs.getString(6);
			}
		});
		return primaryKey;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPzbhByProcedure(final String tableName,final String id,final String len,final String sszt,final String pzlx){
		String procName = "{CALL PROC_SYS_MAKECKBH(?,?,?,?,?,?)}";
		String primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, tableName);
				cs.setString(2, id);
				cs.setString(3, len);
				cs.setString(4, sszt);
				cs.setString(5, pzlx);
				cs.registerOutParameter(6,OracleTypes.VARCHAR);
				cs.execute();
				return cs.getString(6);
			}
		});
		return primaryKey;
	}
	
	/**
	 * 获取打印列表数据
	 * @param ysdhs
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getPrintYsdlb(final String ysdhs){
		String procName = "{CALL PROC_PrintYshdList(?,?)}";
		List primaryKey =  this.execute(procName, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setString(1, ysdhs);
				cs.registerOutParameter(2,OracleTypes.CURSOR);
				cs.execute();
				ResultSet rs = (ResultSet) cs.getObject(2);
				return CallableStatementCallbackImp.resultSetToList(rs);
			}
		});
		return primaryKey;
	}

	/**
	 * 业务审批
	 * @param rybh
	 */
	public void getYwshProcedure(final String rybh){
		String procName = "{CALL PROC_GET_DBSX('"+rybh+"')}";
		this.execute(procName);
	}
	/**
	 * 折旧信息回退
	 * @param rybh
	 */
	public void getZjxxhtProcedure(final String date){
		String procName = "{CALL ZC_ZCZJ_ZJXXHT('"+date+"')}";
		this.execute(procName);
	}	
	/**
	 * 更新人员的单位权限
	 * @param rybh 人员编号
	 * @return
	 */
	public void insertRydwqx(String rybh){
		String procName = "{CALL PROC_ZC_DWBQX('"+rybh+"')}";
		this.execute(procName);
	}

	/**
	 * 批量执行的方法
	 * @param sqls 模板sql语句数组
	 * @param lists 数据组list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean batchUpdate(String[] sqls,List<Object[]>... list){
		for (int i = 0,sqllen = sqls.length; i < sqllen; i++) {
			try{
				super.batchUpdate(sqls[i], list[i]);
			}catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				logger.error("批量执行sql语句异常："+e.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * 批量执行的方法
	 * @param sqls 模板sql语句数组
	 * @param lists 数据组list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean batchUpdate(String[] sqls,Object[]... objs){
		for (int i = 0,sqllen = sqls.length; i < sqllen; i++) {
			try{
				preparSql(sqls[i],objs[i]);
				super.update(sqls[i], objs[i]);
			}catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				logger.error("批量执行sql语句异常："+e.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * 批量执行的方法
	 * @param sqls 模板sql语句数组
	 * @param lists 数据组list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean batchUpdate(List sqls,Object[]... objs){
		List list = new ArrayList();
		for (int i = 0,sqllen = sqls.size(); i < sqllen; i++) {
			list.add(objs[i]);
		}
		try{
			return batchUpdate(sqls,list);
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("批量执行sql语句异常："+e.getMessage());
			return false;
		}
	}

	/**
	 * 批量执行的方法
	 * @param sqls 模板sql语句数组
	 * @param lists 数据组list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean batchUpdate(List<String> sqls,List<Object[]> list){
		for (int i = 0,sqllen = sqls.size(); i < sqllen; i++) {
			try{
				String sql = (String)sqls.get(i);
				if(Validate.isNull(list.get(i))){
					logger.debug("*****执行的sql语句为:".concat(sql).concat("******"));
					super.update(sql);
				}
				else{
					preparSql(sql,list.get(i));
					super.update(sql, (Object[])list.get(i));
				}
			}catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				logger.error("批量执行sql语句异常，" + e.getMessage());
				return false;
			}
		}
		return true;
	}
	/**
	 * 批量执行的方法
	 * @param sqls 模板sql语句数组
	 * @param list 数据list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean batchUpdate(String[] sqls,List<Object[]> list){
		for (int i = 0,sqllen = sqls.length; i < sqllen; i++) {
			preparSql(sqls[i],list.get(i));
			try{
				super.update(sqls[i], list.get(i));
			}catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				logger.error("批量执行sql语句异常："+e.getMessage());
				return false;
			}
		}
		return true;
	}
		
	/**
	 * 批量执行的方法
	 * @param sqls 模板sql语句数组
	 * @param lists 数据组list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean batchUpdate(String[] sqls,Object[] parobj){
		for (int i = 0,sqllen = sqls.length; i < sqllen; i++) {
			preparSql(sqls[i],parobj);
			try{
				super.update(sqls[i], parobj);
			}catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				logger.error("批量执行sql语句异常："+e.getMessage());
				return false;
			}
		}
		return true;
	}
	/**
	 * 批量执行的方法
	 * @param sqls 模板sql语句数组
	 * @param lists 数据组list
	 * @return
	 */
	public boolean batchUpdate(List<String> sqls){
		boolean b = true;
		String[] ss = new String[sqls.size()];
		for (int i = 0,len=sqls.size(); i < len; i++) {
			ss[i] = sqls.get(i);
		}
		if(sqls.size()>0){
			int[] i = super.batchUpdate(ss);
			for (int j = 0; j < i.length; j++) {
				if(i[j]<0){
					b = false;
				}
			}
		}
		return b;
	}
	/**
	 * 返回单个值，如果查询到空，返回空串
	 * @param sql 模板语句                                                                                                                                                                                                                                                                                                                                                                                                                   
	 * @param objs
	 * @return
	 */
	public String queryForSingleValue(String sql,Object[] objs){
		List<Map<String, Object>> list = super.queryForList(sql,objs);
		if(list.size()==1){
			Map<String, Object> map = list.get(0);
			Iterator<String> it = map.keySet().iterator();
			String s = it.next()+"";
			return map.get(s)+"";
		}else{
			return "";
		}
	}
	/**
	 * 返回单个值，如果查询到空，返回空串
	 * @param sql 模板语句                                                                                                                                                                                                                                                                                                                                                                                                                   
	 * @param objs
	 * @return
	 */
	public String queryForSingleValue(String sql){
		List<Map<String, Object>> list = super.queryForList(sql);
		if(list.size()==1){
			Map<String, Object> map = list.get(0);
			Iterator<String> it = map.keySet().iterator();
			String s = it.next()+"";
			return map.get(s)+"";
		}else{
			return "";
		}
	}
    @Override
	public <T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse)
			throws DataAccessException {
    	preparSql(sql,args);
		return super.query(sql, args, argTypes, rse);
	}

	@Override
	public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) throws DataAccessException {
		preparSql(sql,args);
		return super.query(sql, args, rse);
	}

	@Override
	public void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException {
		preparSql(sql,args);
		super.query(sql, args, argTypes, rch);
	}

	@Override
	public void query(String sql, Object[] args, RowCallbackHandler rch) throws DataAccessException {
		preparSql(sql,args);
		super.query(sql, args, rch);
	}

	@Override
	public <T> List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws DataAccessException {
		preparSql(sql,args);
		return super.query(sql, args, argTypes, rowMapper);
	}

	@Override
	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
		preparSql(sql,args);
		return super.query(sql, args, rowMapper);
	}
	@Override
	public Map<String, Object> queryForMap(String sql){
		preparSql(sql,null);
		try{
			return super.queryForMap(sql);
		}
		catch(Exception e){
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
	}
	@Override
	public Map<String, Object> queryForMap(String sql, Object... args){
		preparSql(sql,args);
		try{
			return super.queryForMap(sql, args);
		}
		catch(Exception e){
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
	}
	/**
	 * 获取带有blob型字段的map
	 * @param sql 拼接好的sql语句，目前不支持参数
	 * @param colname 结果集中blob类型字段的名称，有多个字段的，可以用英文逗号隔开，不区分大小写
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql,String colname){
		preparSql(sql,null);
		Map map = new HashMap<String, Object>();
		Connection conn = null;
		try {
			conn = super.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			String name = "";
			String[] cname = colname.split(",");
			boolean b = true;
			while(rs.next()){
				for(int i = 0; i < cnt; i++){
					name = rsmd.getColumnName(i+1).toUpperCase();
					b = true;
					for(int j = 0; j < cname.length; j++){
						if(name.equalsIgnoreCase(cname[j])){
							b = false;
							map.put(name, CommonUtils.BlobToString(rs.getBlob(name)));
						}
					}
					
					if(b){
						map.put(name, rs.getString(name));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if(conn != null){
				try{
					conn.close();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql){
		preparSql(sql,null);
		try{
			return super.queryForList(sql);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ArrayList<Map<String,Object>>();
		}
	}

	@Override
	public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		preparSql(sql,args);
		try {  
			return super.update(sql, args, argTypes);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
	}

	@Override
	public int update(String sql, Object... args) throws DataAccessException {
		preparSql(sql,args);
		try {  
			return super.update(sql, args);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) throws DataAccessException {
		for (int i = 0,len = batchArgs.size(); i < len ; i++) {
			preparSql(sql,batchArgs.get(i));
		}
		return super.batchUpdate(sql, batchArgs);
	}

	public String listToString(List list){
        StringBuffer s = new StringBuffer();
        for (int i = 0,len = list.size(); i < len ; i++) {
            if(i==0){
                s.append(list.get(i));
            }else{
                s.append(","+list.get(i));
            }
        }
        return s.toString();
    }
	
	/**
	 * preparStatement语句人工合成  
	 * 用于开发过程检测sql语句排错	
	 * @param sql sql语句模板
	 * @param objs 参数
	 */
	public void preparSql(String sql,Object[] objs){
		if(Validate.noNull(objs)){
			for (int i = 0,len=objs.length; i < len; i++) {
				if(objs[i]  instanceof Date) {
					sql = sql.replaceFirst("[?]", "to_date('"+objs[i]+"','yyyy-MM-dd')");
				}else{
					sql = sql.replaceFirst("[?]", "'"+objs[i]+"'");
				}
			}
		}
		logger.debug("执行的sql语句为:".concat(sql));
	}
	/**
	 * preparStatement语句人工合成  
	 * 用于开发过程检测sql语句排错	
	 * @param sql sql语句模板
	 */
	public void preparSql(List sqls){
		for (int i = 0,len=sqls.size(); i < len; i++) {
			logger.debug("执行的sql语句为:".concat(sqls.get(i).toString()));
		}
	}
	/**
	 * preparStatement语句人工合成  
	 * 用于开发过程检测sql语句排错	
	 * @param sql sql语句模板
	 */
	public void preparSql(String[] sqls){
		for (int i = 0,len = sqls.length; i < len; i++) {
			logger.debug("执行的sql语句为:".concat(sqls[i]));
		}
	}
	
	//避免sql注入  
    public void dbUpdate(String sql,PreparedStatement ps) {  
        this.update(sql,   
        		new PreparedStatementSetter(){  
                	@Override  
                   public void setValues(PreparedStatement ps) throws SQLException { 
                }
        });
    }
    /**
     * 单条数据的增加、修改、删除  ：：：：： 防注入式
     * @param sql
     * @param params
     * @param tClass
     * @return
     */
    public  int addOrUpdateOrDelete(String sql, final Object[] params) {
        int num = 0;
        if (params == null || params.length == 0)
            num = super.update(sql);
        else
            num = super.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    for (int i = 0; i < params.length; i++)
                        ps.setObject(i + 1, params[i]);
                }
            });
        return num;

    }
    
    /**
     * 多条数据的增加、修改、删除 ：：：：： 防注入式
     * @param sql
     * @param params
     * @param tClass
     * @return
     */
    public  int bacthAddOrUpdateOrDelete(String sql, final List<Object[]> batchArgs) {
    	int[] count = super.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps,int index) throws SQLException {
            	Object[] params=batchArgs.get(index);
                for (int i = 0; i < params.length; i++)
                    ps.setObject(i + 1, params[i]);
            }
            public int getBatchSize() {
                return batchArgs.size();
            }
        });
    	return count.length;//返回执行的的SQL数量，非影响的行数（因为有bug，一直返回-2）
    }
    /**
     * @param sql
     * @param args
     * @param classT 注意该参数，jdbcTemplate.queryForObject传入的不能是自定义的classType，
     *               如果是自定义的，需要经过new BeanPropertyRowMapper<T>(classT)转换，默认支持的只有比如String，int等类型
     * @param <T>
     * @return
     */
    public <T> T findForObject(String sql, Object[] args, Class<T> classT) {
        if (sql == null || sql.length() <= 0) {
            return null;
        }
        if (args == null || args.length <= 0) {
            return super.queryForObject(sql, new BeanPropertyRowMapper<T>(classT));
        }
        return super.queryForObject(sql, args, new BeanPropertyRowMapper<T>(classT));
    }

    public Map<String, Object> find(String sql, Object[] params) {
        return super.queryForMap(sql,params);
    }
    public List<Map<String, Object>> queryList(String sql, Object[] params) {
        return super.queryForList(sql,params);
    }
}
