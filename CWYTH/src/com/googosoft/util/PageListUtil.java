package com.googosoft.util;

import com.googosoft.commons.ToSqlUtil;
import com.googosoft.pojo.PageList;

/**
 * 分页工具类
 * @author master
 * @version 1.0 2016-10-20
 */
public class PageListUtil {
	private static Logger logger = Logger.getLogger(PageListUtil.class);
	
	/**
	 * 普通列表页面查询方法
	 * @param pd
	 * @param pagelist
	 * @return
	 */
	public static PageList setPgxxList(PageData pd,PageList pagelist){
		//请求的次数
		String draw = Validate.isNullToDefault(pd.get("draw"), "1")+"";//默认为1
	    //数据起始位置
	    String start = Validate.isNullToDefault(pd.getString("start"), "0")+"";
	    //每页数据长度
	    String length = pd.getString("length");
	    if(Validate.isNull(length) || "0".equals(length)){
	    	length = "10";
	    	pd.put("length", "10");
	    }
	    //本页结束位置
	    String end = (Integer.parseInt(length)+Integer.parseInt(start))+"";
	    //排序列名
	    String orderColumn = Validate.isNullToDefault(pd.getString("order[0][column]"), "0")+"";
	    orderColumn = pd.getString("columns["+orderColumn+"][data]");
	    //排序方式
	    String orderDir = Validate.isNullToDefault(pd.getString("order[0][dir]"), "asc")+"";
	    //获取用户过滤框里的字符
	    String searchValue = pd.getString("search[value]");
	    if(Validate.noNull(searchValue)&&searchValue.equals("[]")){
	    	searchValue="";
	    }
	    //封装用户的查询条件为sql语句（想要替换生成综合查询语句条件的方法，可以在这操作）
		String searchSql = "";
		if(Validate.noNull(searchValue)){
			if(searchValue.indexOf("{")>0){
				searchSql = ToSqlUtil.jsonToSql(searchValue);//综合查询的语句在这个方法里处理过了name
			}else{
				searchSql = searchValue;
				
				String zhcxsql = Validate.isNullToDefaultString(pd.getString("zhcxsql"),"");
				if(Validate.noNull(zhcxsql)){
					searchSql += zhcxsql;
				}
			}
		}
		if(Validate.noNull(pagelist.getStrWhere())){
			pagelist.setStrWhere(pagelist.getStrWhere()+" "+searchSql+" ");//查询条件语句
		}else{
			pagelist.setStrWhere(searchSql+" ");//查询条件语句
		}
		String pxzh = pd.getString("pxzh");
		String asc = pd.getString("asc");
		if("pxzh".equals(pxzh)){
			pagelist.setOrderBy("order by "+asc+"");
		}else{
			if(Validate.noNull(orderColumn) && orderColumn.indexOf(",")>0){
				String[] orders = orderColumn.split(",");
				StringBuffer orderSql = new StringBuffer();
				orderSql.append(" order by ");
				for (int i = 0; i < orders.length; i++) {
					orderSql.append(orders[i]+" "+orderDir+",");
				}
				pagelist.setOrderBy(orderSql.substring(0,orderSql.length()-1));//排序语句
			}else{
				pagelist.setOrderBy(" order by "+orderColumn+" "+orderDir);//排序语句
			}
		}
		//特殊模块的查询 多条件  《凭证查询》  凭证编号 asc 凭证日期 desc
		String mkmc = pd.getString("mkmc");
		if("pzcx".equals(mkmc)){
//			if(orderColumn.equals("PZBH")){
//				pagelist.setOrderBy(" order by "+orderColumn+" "+orderDir+" ,PZRQ desc");
//			}else if(orderColumn.equals("PZRQ")){
				pagelist.setOrderBy(" order by "+orderColumn+" "+orderDir+" ,PZBH asc");
//			}else{
//				pagelist.setOrderBy(" order by "+orderColumn+" "+orderDir+" ,PZRQ desc,PZBH asc");
//			}
		}
		
		pagelist.setCurPage(start);//起始位置
		pagelist.setPage_record(end);//结束位置
		pagelist.setPage_length(length);//列表页面每页长度
		if(Validate.noNull(pagelist.getHj1())){
			pagelist.setHj1("count(*) as num,"+pagelist.getHj1());//合计查询语句
		}else{
			pagelist.setHj1("count(*) as num");
		}
		logger.debug("合计信息查询语句：select "+pagelist.getHj1() +" from "+pagelist.getTableName()+" where 1=1 "+ pagelist.getStrWhere());
		logger.debug("信息查询语句：select "+pagelist.getSqlText()+" from "+pagelist.getTableName()+" where 1=1 "+pagelist.getStrWhere()+" "+pagelist.getOrderBy() );
		System.err.println("pagelist="+pagelist);
		return pagelist;
	}
	public static PageList setPgxxListA(PageData pd,PageList pagelist){
		//请求的次数
		String draw = Validate.isNullToDefault(pd.get("draw"), "1")+"";//默认为1
		//数据起始位置
		String start = Validate.isNullToDefault(pd.getString("start"), "0")+"";
		//每页数据长度
		String length = pd.getString("length");
		if(Validate.isNull(length) || "0".equals(length)){
			length = "10";
			pd.put("length", "10");
		}
		//本页结束位置
		String end = (Integer.parseInt(length)+Integer.parseInt(start))+"";
	/*	//排序列名
		String orderColumn = Validate.isNullToDefault(pd.getString("order[0][column]"), "0")+"";
		orderColumn = pd.getString("columns["+orderColumn+"][data]");
		//排序方式
		String orderDir = Validate.isNullToDefault(pd.getString("order[0][dir]"), "asc")+"";*/
		//获取用户过滤框里的字符
		String searchValue = pd.getString("search[value]");
		if(Validate.noNull(searchValue)&&searchValue.equals("[]")){
			searchValue="";
		}
		//封装用户的查询条件为sql语句（想要替换生成综合查询语句条件的方法，可以在这操作）
		String searchSql = "";
		if(Validate.noNull(searchValue)){
			if(searchValue.indexOf("{")>0){
				searchSql = ToSqlUtil.jsonToSql(searchValue);//综合查询的语句在这个方法里处理过了name
			}else{
				searchSql = searchValue;
				
				String zhcxsql = Validate.isNullToDefaultString(pd.getString("zhcxsql"),"");
				if(Validate.noNull(zhcxsql)){
					searchSql += zhcxsql;
				}
			}
		}
		if(Validate.noNull(pagelist.getStrWhere())){
			pagelist.setStrWhere(pagelist.getStrWhere()+" "+searchSql+" ");//查询条件语句
		}else{
			pagelist.setStrWhere(searchSql+" ");//查询条件语句
		}
		String pxzh = pd.getString("pxzh");
		String asc = pd.getString("asc");
	/*	if("pxzh".equals(pxzh)){
			pagelist.setOrderBy("order by "+asc+"");
		}else{
			if(Validate.noNull(orderColumn) && orderColumn.indexOf(",")>0){
				String[] orders = orderColumn.split(",");
				StringBuffer orderSql = new StringBuffer();
				orderSql.append(" order by ");
				for (int i = 0; i < orders.length; i++) {
					orderSql.append(orders[i]+" "+orderDir+",");
				}
				pagelist.setOrderBy(orderSql.substring(0,orderSql.length()-1));//排序语句
			}else{
				pagelist.setOrderBy(" order by "+orderColumn+" "+orderDir);//排序语句
			}
		}*/
		pagelist.setCurPage(start);//起始位置
		pagelist.setPage_record(end);//结束位置
		pagelist.setPage_length(length);//列表页面每页长度
		if(Validate.noNull(pagelist.getHj1())){
			pagelist.setHj1("count(*) as num,"+pagelist.getHj1());//合计查询语句
		}else{
			pagelist.setHj1("count(*) as num");
		}
		pagelist.setOrderBy(" ");
		logger.debug("合计信息查询语句：select "+pagelist.getHj1() +" from "+pagelist.getTableName()+" where 1=1 "+ pagelist.getStrWhere());
		logger.debug("信息查询语句：select "+pagelist.getSqlText()+" from "+pagelist.getTableName()+" where 1=1 "+pagelist.getStrWhere()+" "+pagelist.getOrderBy() );
		System.err.println("pagelist="+pagelist);
		return pagelist;
	}
	
	
	public static PageList setPgxxList1(PageData pd,PageList pagelist){
		//请求的次数
		String draw = Validate.isNullToDefault(pd.get("draw"), "1")+"";//默认为1
		//数据起始位置
		String start = Validate.isNullToDefault(pd.getString("start"),"0")+"";
		//每页数据长度
		String length = Validate.isNullToDefault(pd.getString("length"), "10")+"";
		//本页结束位置
		String end = (Integer.parseInt(length)+Integer.parseInt(start))+"";
		//排序列名
		String orderColumn = Validate.isNullToDefault(pd.getString("order[0][column]"), "0")+"";
		orderColumn = pd.getString("columns["+orderColumn+"][data]");
		//排序方式
		String orderDir = Validate.isNullToDefault(pd.getString("order[0][dir]"), "asc")+"";
		//获取用户过滤框里的字符
		String searchValue = CommonUtils.StringFilter(pd.getString("search[value]"));
		//封装用户的查询条件为sql语句（想要替换生成综合查询语句条件的方法，可以在这操作）
		String searchSql = "";
		if(Validate.noNull(searchValue)){
			if("D".equals("D")){
				searchSql = " AND (A.RYBH LIKE'%".concat(searchValue).concat("%'").concat(" OR A.XM LIKE '%").concat(searchValue).concat("%')");
			}
			
		}
		if(Validate.noNull(pagelist.getStrWhere())){
			pagelist.setStrWhere(pagelist.getStrWhere()+" "+searchSql+" ");//查询条件语句
		}else{
			pagelist.setStrWhere(searchSql+" ");//查询条件语句
		}
		pagelist.setOrderBy(" order by "+orderColumn+" "+orderDir);//排序语句
		pagelist.setCurPage(start);//起始位置
		pagelist.setPage_record(end);//结束位置
		pagelist.setPage_length(length);//列表页面每页长度
		if(Validate.noNull(pagelist.getHj1())){
			pagelist.setHj1("count(*) as num,"+pagelist.getHj1());//合计查询语句
		}else{
			pagelist.setHj1("count(*) as num");
		}
		logger.debug("合计信息查询语句：select "+pagelist.getHj1() +" from "+pagelist.getTableName()+" where 1=1 "+ pagelist.getStrWhere());
		logger.debug("信息查询语句：select "+pagelist.getSqlText()+" from "+pagelist.getTableName()+" where 1=1 "+pagelist.getStrWhere());
		return pagelist;
	}
	
	
	
	
	
	
	/**
	 * 根据页面信息拼接where条件
	 * @param pd
	 * @param removename
	 * @return
	 */
	public static String setPgxxWhere(PageData pd){
		return setPgxxWhere(pd,"");
	}
	/**
	 * 根据页面信息拼接where条件
	 * @param pd
	 * @param removename
	 * @return
	 */
	public static String setPgxxWhere(PageData pd,String removeName){
		String searchSql = "";
		
	    String searchValue = pd.getString("search[value]");
	    if("[]".equals(searchValue)){
	    	searchValue = "";
	    }
		if(Validate.noNull(searchValue)){
			if(Validate.isNull(removeName)){
				searchSql = ToSqlUtil.jsonToSql(searchValue);
			}
			else{
				searchSql = ToSqlUtil.jsonToGroupSql(searchValue,removeName);
			}
		}
		return searchSql;
	}
	/**
	 * 列表页面拼接起始位置、结束位置、排序字段等信息(不拼接where条件)
	 * @param pd
	 * @param pagelist
	 * @return
	 */
	public static PageList setSpecialPgxxList(PageData pd,PageList pagelist){
		//请求的次数
		String draw = Validate.isNullToDefault(pd.get("draw"), "1")+"";//默认为1
	    //数据起始位置
	    String start = Validate.isNullToDefault(pd.getString("start"), "0")+"";
	    //每页数据长度
	    String length = pd.getString("length");
	    if(Validate.isNull(length) || "0".equals(length)){
	    	length = "10";
	    	pd.put("length", "10");
	    }
	    //本页结束位置
	    String end = (Integer.parseInt(length)+Integer.parseInt(start))+"";
	    //排序列名
	    String orderColumn = Validate.isNullToDefault(pd.getString("order[0][column]"), "0")+"";
	    orderColumn = pd.getString("columns["+orderColumn+"][data]");
	    //排序方式
	    String orderDir = Validate.isNullToDefault(pd.getString("order[0][dir]"), "asc")+"";
	    
		String pxzh = pd.getString("pxzh");
		String asc = pd.getString("asc");
		if("pxzh".equals(pxzh)){
			pagelist.setOrderBy("order by "+asc+"");
		}else{
			if(Validate.noNull(orderColumn) && orderColumn.indexOf(",")>0){
				String[] orders = orderColumn.split(",");
				StringBuffer orderSql = new StringBuffer();
				orderSql.append(" order by ");
				for (int i = 0; i < orders.length; i++) {
					orderSql.append(orders[i]+" "+orderDir+",");
				}
				pagelist.setOrderBy(orderSql.substring(0,orderSql.length()-1));//排序语句
			}else{
				pagelist.setOrderBy(" order by "+orderColumn+" "+orderDir);//排序语句
			}
		}
		pagelist.setCurPage(start);//起始位置
		pagelist.setPage_record(end);//结束位置
		pagelist.setPage_length(length);//列表页面每页长度
		if(Validate.noNull(pagelist.getHj1())){
			pagelist.setHj1("count(*) num,"+pagelist.getHj1());//合计查询语句
			logger.debug("合计信息查询语句：select "+pagelist.getHj1() +" from "+pagelist.getTableName()+" where 1=1 "+ pagelist.getStrWhere());
		}else{
			pagelist.setHj1("count(*) num");
		}
		logger.debug("信息查询语句：select "+pagelist.getSqlText()+" from "+pagelist.getTableName()+" where 1=1 "+pagelist.getStrWhere()+" "+pagelist.getOrderBy());
		return pagelist;
	}
	/**
	 * 弹窗列表页面查询方法
	 * @param pd
	 * @param pagelist
	 * @return
	 */
	public static PageList setPgxxList(PageData pd,PageList pagelist,String flag){
		//请求的次数
		String draw = Validate.isNullToDefault(pd.get("draw"), "1")+"";//默认为1
		//数据起始位置
		String start = Validate.isNullToDefault(pd.getString("start"),"0")+"";
		//每页数据长度
		String length = Validate.isNullToDefault(pd.getString("length"), "10")+"";
		//本页结束位置
		String end = (Integer.parseInt(length)+Integer.parseInt(start))+"";
		//排序列名
		String orderColumn = Validate.isNullToDefault(pd.getString("order[0][column]"), "0")+"";
		orderColumn = pd.getString("columns["+orderColumn+"][data]");
		//排序方式
		String orderDir = Validate.isNullToDefault(pd.getString("order[0][dir]"), "asc")+"";
		//获取用户过滤框里的字符
		String searchValue = CommonUtils.StringFilter(pd.getString("search[value]"));
		//封装用户的查询条件为sql语句（想要替换生成综合查询语句条件的方法，可以在这操作）
		String searchSql = "";
		if(Validate.noNull(searchValue)){
			if("D".equals(flag)){
				searchSql = " AND (K.DWBH LIKE'%".concat(searchValue).concat("%'").concat(" OR K.MC LIKE '%").concat(searchValue).concat("%')");
			}else if("R".equals(flag)){
				searchSql = " AND (A.RYGH LIKE'%".concat(searchValue).concat("%'").concat(" OR A.XM LIKE '%").concat(searchValue).concat("%')");
			}else if("P".equals(flag)){
				searchSql = " AND (D.DDH LIKE'%".concat(searchValue).concat("%'").concat(" OR D.MC LIKE '%").concat(searchValue).concat("%')");
			}else if("F".equals(flag)){
				searchSql = " AND (T.FLH LIKE'%".concat(searchValue).concat("%'").concat(" OR T.FLMC LIKE '%").concat(searchValue).concat("%')");
			}else if("FZ".equals(flag)){
				searchSql = " and (flh like'%".concat(searchValue).concat("%'").concat(" or yqmc like '%").concat(searchValue).concat("%')");
			}else if("T".equals(flag)){
				searchSql = " and (t.dm like'%".concat(searchValue).concat("%'").concat(" or t.mc like '%").concat(searchValue).concat("%')");
			}else if("T1".equals(flag)){
				searchSql = " and (t.xmlxbh like'%".concat(searchValue).concat("%'").concat(" or t.srkmbh like '%").concat(searchValue).concat("%')");
			}
			else if("J".equals(flag)){//首页-进度跟踪-更多
				searchSql = " and (j.bh like '%".concat(searchValue).concat("%'").concat(" or j.mc like '%").concat(searchValue).concat("%'").concat(" or j.lx like '%").concat(searchValue).concat("%'").concat(" or j.jd like '%").concat(searchValue).concat("%')");
			}else if("W".equals(flag)){
				searchSql = " and (t.yqbh like '%".concat(searchValue).concat("%'").concat(" or t.yqmc like '%").concat(searchValue).concat("%'").concat(" or t.gzrq like '%").concat(searchValue).concat("%'").concat(" or t.bzxx like '%").concat(searchValue).concat("%'").concat(" or t.flh like '%").concat(searchValue).concat("%'").concat(" or t.flmc like '%").concat(searchValue).concat("%')");
			}else if("WXS".equals(flag)){
				searchSql = " and ('('||d.gsbh||')'||d.mc like '%".concat(searchValue).concat("%'").concat(" or d.fr like '%").concat(searchValue).concat("%'").concat(" or d.phone like '%").concat(searchValue).concat("%'").concat(" or d.address like '%").concat(searchValue).concat("%'").concat(" or d.lxr like '%").concat(searchValue).concat("%')");
			}else if("WXSQ".equals(flag)){
				searchSql = " and (w.reportid like '%".concat(searchValue).concat("%'").concat(" or w.wxsmc like '%").concat(searchValue).concat("%'").concat(" or w.replypersonmc like '%").concat(searchValue).concat("%'").concat(" or w.replycompanymc like '%").concat(searchValue).concat("%')");
			}else if("DBZX".equals(flag)){//首页-待办事项-更多
				searchSql = " and (k.dbh like '%".concat(searchValue).concat("%'").concat(" or k.mc like '%").concat(searchValue).concat("%'").concat(" or k.tjr like '%").concat(searchValue).concat("%'").concat(" or k.mkmc like '%").concat(searchValue).concat("%')");
			}else if("CZT".equals(flag)){//财政十分类查询语句
				searchSql = " AND (T.ZCDM LIKE'%".concat(searchValue).concat("%'").concat(" OR T.MC LIKE '%").concat(searchValue).concat("%')");
			}else if("TZGG".equals(flag)){
				searchSql = " AND (T.TITLE LIKE'%".concat(searchValue).concat("%'").concat(" OR T.FBRMC LIKE '%").concat(searchValue).concat("%')");
			}else if("DBSX".equals(flag)){
				searchSql = " AND (K.MC LIKE'%".concat(searchValue).concat("%'").concat(" OR K.DBH LIKE '%").concat(searchValue).concat("%')");
			}else if("ZCXX".equals(flag)){//首页-我的名下资产-更多
				searchSql = " AND (T.YQBH LIKE'%".concat(searchValue).concat("%'").concat(" OR T.YQMC LIKE '%").concat(searchValue).concat("%')");
			}else if("ZY".equals(flag)){//专业
				searchSql = " AND (A.ZYBH LIKE'%".concat(searchValue).concat("%'").concat(" OR A.ZYMC LIKE '%").concat(searchValue).concat("%')");
			}else if("NJ".equals(flag)){//专业
				searchSql = " AND (A.NJBH LIKE'%".concat(searchValue).concat("%'").concat(" OR A.NJMC LIKE '%").concat(searchValue).concat("%')");
			}else if("BJ".equals(flag)){//专业
				searchSql = " AND (A.BJBH LIKE'%".concat(searchValue).concat("%'").concat(" OR A.BJMC LIKE '%").concat(searchValue).concat("%')");
			}else if("ZD".equals(flag)){//专业
				searchSql = " AND (K.dm LIKE'%".concat(searchValue).concat("%'").concat(" OR K.MC LIKE '%").concat(searchValue).concat("%')");
			}else if("WLDW".equals(flag)) {
				searchSql = " AND (K.wlbh LIKE'%".concat(searchValue).concat("%')");
			}else if("WWW".equals(flag)) {
				//会计科目
				searchSql ="AND (k.kmbh like '%".concat(searchValue).concat("%'").concat(" OR k.KMMC LIKE '%").concat(searchValue).concat("%')");
			}else if("JJKM".equals(flag)) {
				//经济科目
				searchSql ="AND (j.kmbh like '%".concat(searchValue).concat("%'").concat(" OR j.KMMC LIKE '%").concat(searchValue).concat("%')");
			}else if("FYKM".equals(flag)) {
				//费用科目与部门对应设置
				searchSql ="AND (K.FYFL like '%".concat(searchValue).concat("%'").concat(" OR K.FYMC LIKE '%").concat(searchValue).concat("%')");
			}else if("XMLX".equals(flag)) {
				//费用科目与部门对应设置
				searchSql ="AND (K.XMLXBH like '%".concat(searchValue).concat("%'").concat(" OR K.XMLXMC LIKE '%").concat(searchValue).concat("%')");
			}else if("JFB".equals(flag)) {
				searchSql ="AND (A.XMMC like '%".concat(searchValue).concat("%'").concat(" OR A.XMBH LIKE '%").concat(searchValue).concat("%')");
			}else if("CGML".equals(flag)) {
				//采购目录设置
				searchSql ="AND (K.MLDM like '%".concat(searchValue).concat("%'").concat(" OR K.MLMC LIKE '%").concat(searchValue).concat("%')");
			}else if("WLDWS".equals(flag)) {
				//采购目录设置
				searchSql ="AND (A.WLBH like '%".concat(searchValue).concat("%'").concat(" OR A.DWMC LIKE '%").concat(searchValue).concat("%')");
			}else if("TZXX".equals(flag)){
				searchSql ="AND (FBRXM like '%".concat(searchValue).concat("%'").concat(" or ZT like '%").concat(searchValue).concat("%'").concat(" or XXNR like '%").concat(searchValue).concat("%'").concat(" OR FBSJ LIKE '%").concat(searchValue).concat("%')");
			}else if("CBSA".equals(flag)){
				searchSql ="AND (T.CBSBH like '%".concat(searchValue).concat("%'").concat(" OR T.CBSMC LIKE '%").concat(searchValue).concat("%')");
			}else if("XFDDA".equals(flag)){
				searchSql ="AND (T.XFDDBH like '%".concat(searchValue).concat("%'").concat(" OR T.XFDDMC LIKE '%").concat(searchValue).concat("%')");
			}//复制的Gxzcpt的
			else if("GZ".equals(flag)){
	            searchSql = " AND ( XM LIKE '%".concat(searchValue).concat("%'").concat(" OR JGH LIKE '%").concat(searchValue).concat("%')");
	        }
			
			
		}
		if(Validate.noNull(pagelist.getStrWhere())){
			pagelist.setStrWhere(pagelist.getStrWhere()+" "+searchSql+" ");//查询条件语句
		}else{
			pagelist.setStrWhere(searchSql+" ");//查询条件语句
		}
		pagelist.setOrderBy(" order by "+orderColumn+" "+orderDir);//排序语句
		pagelist.setCurPage(start);//起始位置
		pagelist.setPage_record(end);//结束位置
		pagelist.setPage_length(length);//列表页面每页长度
		if(Validate.noNull(pagelist.getHj1())){
			pagelist.setHj1("count(*) as num,"+pagelist.getHj1());//合计查询语句
		}else{
			pagelist.setHj1("count(*) as num");
		}
		logger.debug("合计信息查询语句：select "+pagelist.getHj1() +" from "+pagelist.getTableName()+" where 1=1 "+ pagelist.getStrWhere());
		logger.debug("信息查询语句：select "+pagelist.getSqlText()+" from "+pagelist.getTableName()+" where 1=1 "+pagelist.getStrWhere());
		return pagelist;
	}
}
