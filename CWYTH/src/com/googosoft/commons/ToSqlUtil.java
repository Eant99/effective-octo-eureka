package com.googosoft.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import com.google.gson.Gson;
import com.googosoft.util.DateConverter;
import com.googosoft.util.Logger;
import com.googosoft.util.StringUtils;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

/**
 * sql语句片段工具类
 * @author cyd
 * @since 2017-02-14
 */
public class ToSqlUtil {
	
	private static Logger logger = Logger.getLogger(ToSqlUtil.class);
	
	/**
	 * 特定格式的json转Sql片段
	 * @param json
	 * @return
	 */
	public static String jsonToSql(String json){
		if(Validate.noNull(json)){
			StringBuffer sql = new StringBuffer();
			Gson gson = new Gson();
			ArrayList list = gson.fromJson(json,ArrayList.class);
			for (int i = 0,len=list.size();i < len; i++) {
				Map map = (Map) list.get(i);
				setJsonToSql(map,"",sql);
			}
			logger.debug("综合查询语句："+sql.toString());
			return sql.toString();
		}else{
			return "";
		}
	}
	public static String getLongInSql(String ids, String field){
		return getLongInSql(ids,field,"and",true);
	}
	public static String getLongInSql(String ids, String field, String connector, boolean flag) {
		if(Validate.isNull(ids)){
			return "";
		}
		else{
		    String bh[] = ids.split(",");
		    if(bh.length == 1){
		    	if(flag){
		    		return " " + connector + " " + field + " = '" + ids.replace("'","") + "' ";
		    	}
		    	else{
		    		return " " + connector + " " + field + " = ? ";
		    	}
		    }
		    else{
		    	int count = 1000;
			    int len = bh.length;
			    int size = (int)Math.ceil((float)len/(float)count);
			    
			    StringBuilder builder = new StringBuilder();
			    for (int i = 0; i < size; i++) {
			        int ks = i * count;
			        int js = Math.min(ks + count, len);
			        if (i != 0) {
			            builder.append(" or ");
			        }
			        if(flag){
			        	builder.append(field).append(" in ('" + StringUtils.join(Arrays.copyOfRange(bh,ks,js), "','") + "')");
			        }
			        else{
			        	String[] arr = Arrays.copyOfRange(bh,ks,js);
			        	Arrays.fill(arr,"?");
			        	builder.append(field).append(" in (" + StringUtils.join(arr, ",") + ")");
			        }
			    }
			    if(size == 1){
			    	return " " + connector + " " + builder.toString() + " ";
			    }
			    else{
			    	return " " + connector + " (" + builder.toString() + ") ";
			    }
		    }
		}
	}
	/**
	 * 特定格式的json转分组sql片段
	 * @param json
	 * @param removeName
	 * @return
	 */
	public static String jsonToGroupSql(String json,String removeName){
		if(Validate.noNull(json)){
			StringBuffer sql = new StringBuffer();
			Gson gson = new Gson();
			ArrayList list = gson.fromJson(json,ArrayList.class);
			for (int i = 0,len=list.size();i < len; i++) {
				Map map = (Map) list.get(i);
				String name = StringUtils.StringFilter(map.get("name").toString());
				removeName = "," + removeName + ",";
				if(removeName.indexOf("," + name + ",") < 0){
					setJsonToSql(map,name,sql);
				}
			}
			logger.debug("分组查询语句："+sql.toString());
			return sql.toString();
		}else{
			return "";
		}
	}
	/**
	 * 处理拼接的
	 * @param map 
	 * @param name 拼接分组sql的条件时，需要排除一部分信息，所以name是提前获取，提前处理的，获取列表的条件时，条件不需要另外处理，所以直接传空过来，需要这里获取
	 * @param sql 
	 */
	private static void setJsonToSql(Map map,String name,StringBuffer sql){
		if(Validate.isNull(name)){
			name = StringUtils.StringFilter(map.get("name").toString());
		}
		if("zhcxsql".equals(name)){
			sql.append(map.get("value").toString());
		}
		else{
			String value = StringUtils.StringFilter(map.get("value")+"");
			String table = StringUtils.StringFilter(map.get("table")+"");
			if(Validate.noNull(table)){
				name = table+"."+name;
			}
			String type = StringUtils.StringFilter(map.get("type")+"");
			if(Validate.noNull(type)){
				if("D".equals(type)){//单位
					sql.append(" and "+name+"='"+ WindowUtil.getXx(value, "D")+"'");
				}else if("DQ".equals(type)){//获取单位下所有单位（此时的value是dwbh）
					sql.append(" and " + name + " in (select dwbh from gx_sys_dwb where dwzt = '1' start with dwbh in ('" + pointToSql(value) + "') connect by prior dwbh = sjdw) ");
				}else if("R".equals(type)){//人员
					sql.append(" and "+name+"='"+ WindowUtil.getXx(value, "R")+"'");
				}else if("T".equals(type)){//时间
					sql.append(" and "+name+"='"+ value+"'");
				}else if("TL".equals(type)){//时间范围（小）Time Low
					//有的日期是带时分秒的，如果不进行处理当TL和TH一样时筛选不出
					sql.append(" and to_date(to_char("+name+",'yyyy-MM-dd'),'yyyy-MM-dd')>=to_date('"+ value+"','yyyy-MM-dd')");
				}else if("TH".equals(type)){//时间返回（大）Time Hight
					sql.append(" and to_date(to_char("+name+",'yyyy-MM-dd'),'yyyy-MM-dd')<=to_date('"+ value+"','yyyy-MM-dd')");
				}else if("TL_date".equals(type)){//时间范围（小）Time Low
					//有的日期是带时分秒的，如果不进行处理当TL和TH一样时筛选不出
					sql.append(" and to_date("+name+",'yyyy-MM-dd')>=to_date('"+ value+"','yyyy-MM-dd')");
				}else if("TH_date".equals(type)){//时间返回（大）Time Hight
					sql.append(" and to_date("+name+",'yyyy-MM-dd')<=to_date('"+ value+"','yyyy-MM-dd')");
				}else if("CX1".equals(type)){//时间范围（小）Time Low
					//不需要to_char
					sql.append(" and to_date("+name+",'yyyy-MM-dd')>=to_date('"+ value+"','yyyy-MM-dd')");
				}else if("CX2".equals(type)){//时间返回（大）Time Hight
					sql.append(" and to_date("+name+",'yyyy-MM-dd')<=to_date('"+ value+"','yyyy-MM-dd')");
				}else if("TNL".equals(type)){//年范围（小）Time Low
					//有的日期是带时分秒的，如果不进行处理当TL和TH一样时筛选不出
					sql.append(" and to_date(to_char("+name+",'yyyy'),'yyyy')>=to_date('"+ value+"','yyyy')");
				}else if("TNH".equals(type)){//年返回（大）Time Hight
					sql.append(" and to_date(to_char("+name+",'yyyy'),'yyyy')<=to_date('"+ value+"','yyyy')");
				}else if("TML".equals(type)){//月范围（小）Time Low
					//有的日期是带时分秒的，如果不进行处理当TL和TH一样时筛选不出
					sql.append(" and to_date(to_char("+name+",'yyyy-MM'),'yyyy-MM')>=to_date('"+ value+"','yyyy-MM')");
				}else if("TMH".equals(type)){//月返回（大）Time Hight
					sql.append(" and to_date(to_char("+name+",'yyyy-MM'),'yyyy-MM')<=to_date('"+ value+"','yyyy-MM')");
				}else if("TIL".equals(type)){//带有时分秒的时间范围（小）Time Low
					//有的日期是带时分秒的，如果不进行处理当TL和TH一样时筛选不出
					sql.append(" and to_date(to_char("+name+",'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')>=to_date('"+ value+"','yyyy-mm-dd hh24:mi:ss')");
				}else if("TIH".equals(type)){//带有时分秒的时间返回（大）Time Hight
					sql.append(" and to_date(to_char("+name+",'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')<=to_date('"+ value+"','yyyy-mm-dd hh24:mi:ss')");
				}else if("TILA".equals(type)){//带有时分秒的时间范围（小）Time Low
					//有的日期是带时分秒的，如果不进行处理当TL和TH一样时筛选不出
					sql.append(" and to_date("+name+",'yyyy-mm-dd hh24:mi:ss')>=to_date('"+ value+"','yyyy-mm-dd hh24:mi:ss')");
				}else if("TIHB".equals(type)){//带有时分秒的时间返回（大）Time Hight
					sql.append(" and to_date("+name+",'yyyy-mm-dd hh24:mi:ss')<=to_date('"+ value+"','yyyy-mm-dd hh24:mi:ss')");
				}else if("N".equals(type)){//数字
					sql.append(" and "+name+" = "+ value+"");
				}else if("SFBHWJZ".equals(type)){//是否包含未记账凭证
					sql.append(" and "+name+" in ("+ value+")");
				}else if("NL".equals(type)){//数字范围（小）
					sql.append(" and "+name+">="+ value+"");
				}else if("NH".equals(type)){//数字范围（大）
					sql.append(" and "+name+"<="+ value+"");
				}else if("NHL".equals(type)){//数字范围（小）
					sql.append(" and "+name+">='"+ value+"'");
				}else if("NHH".equals(type)){//数字范围（大）
					sql.append(" and "+name+"<='"+ value+"'");
				}else if("BHL".equals(type)){//科目编号小
					sql.append(" and to_number("+name+")>='"+ value+"'");
				}else if("BHH".equals(type)){//kmbh（大）
					sql.append(" and to_number("+name+")<='"+ value+"'");
				}else if("NFL".equals(type)){//不包括等于的数字范围（小）
					sql.append(" and "+name+">"+ value+"");
				}else if("NFH".equals(type)){//不包括等于的数字范围（大）
					sql.append(" and "+name+"<"+ value+"");
				}else if("E".equals(type)){//强制使用 =
					if(Validate.isNull(value)){
						sql.append(" and "+name+" is null ");
					}
					else{
						sql.append(" and "+name+" = '"+ value+"' ");
					}
				}else if("J".equals(type)){//角色
					sql.append(" and "+name+"='"+WindowUtil.getText(value)+"'");
				}else if("F".equals(type)){//分类号
					sql.append(" and "+name+"='"+WindowUtil.getText(value)+"'");
				}else if("FLIKE".equals(type)){//多个分类号（按模板）
					if(Validate.noNull(value)){
						sql.append(" and (");
						String[] flharr = value.split(",");
						String flh = "";
						for(int j = 0; j < flharr.length; j++){
							flh = flharr[j];
							while(flh.endsWith("00")){
								flh = flh.substring(0,flh.length() - 2);
							}
							
							if(j > 0){
								sql.append(" or ");
							}

							if("04".equals(flh)){
								sql.append("(substr(" + name + ",1," + flh.length() + ") = '" + flh + "' and substr(" + name + ",1,4) <> '0413') ");
							}else if("05".equals(flh)){
								sql.append("(substr(" + name + ",1," + flh.length() + ") = '" + flh + "' and substr(" + name + ",1,4) <> '0571')");										
							}else if("07".equals(flh)){
								sql.append("(substr(" + name + ",1," + flh.length() + ") = '" + flh + "' and substr(" + name + ",1,4) <> '0711')");										
							}else{
								sql.append("substr(" + name + ",1," + flh.length() + ") = '" + flh + "'");
							}
						}
						sql.append(") ");
					}
				}else if("CLIKE".equals(type)){//多个分类号（查询中的公用分类）
					if(Validate.noNull(value)){
						sql.append(" and (");
						String[] flharr = value.split(",");
						String flh = "";
						for(int j = 0; j < flharr.length; j++){
							flh = flharr[j];
							while(flh.endsWith("00")){
								flh = flh.substring(0,flh.length() - 2);
							}
							
							if(j > 0){
								sql.append(" or ");
							}

							if("04".equals(flh)){
								sql.append("(substr(" + name + ",1," + flh.length() + ") = '" + flh + "' and substr(" + name + ",1,4) not in ('0411','0413')) ");
							}else{
								sql.append("substr(" + name + ",1," + flh.length() + ") = '" + flh + "'");
							}
						}
						sql.append(") ");
					}
				}else if("P".equals(type)){//地点
					sql.append(" and "+name+"='"+WindowUtil.getXx(value,"P")+"'");
				}else if("C".equals(type)){//checkbox
					sql.append(" and "+name+" in ('"+pointToSql(value)+"')");
				}else if("W".equals(type)){//checkbox
					sql.append(" and "+name+" ='"+WindowUtil.getXx(value,"P")+"'");
				}else if("GL".equals(type)){//管理员建账特殊处理查询，拼装sql（只使用在管理员建账的列表页面）
					sql.append(" and "+name+" in ('"+pointToSql(value)+"')");
				}else if("year".equals(type)){//查询--公开查询
					sql.append(" and to_char("+name+",'yyyy') ='"+value+"'");
				}else if("nydate".equals(type)){
					sql.append(" and to_char("+name+",'yyyy-mm') ='"+value+"'");
				}else if("ML".equals(type)){//Month Low  表里的字段是文本型的
					sql.append(" and to_date("+name+",'yyyy-mm') >= trunc(to_date('"+value+"','yyyy-mm-dd'),'mm') ");
				}else if("MH".equals(type)){//Month Hight  表里的字段是文本型的
					sql.append(" and to_date("+name+",'yyyy-mm') <= trunc(to_date('"+value+"','yyyy-mm-dd'),'mm') ");
				}else if("TML".equals(type)){//Time Month Low  表里的字段是时间型的
					sql.append(" and trunc("+name+",'mm') >= trunc(to_date('"+value+"','yyyy-mm-dd'),'mm') ");
				}else if("TMH".equals(type)){//Time Month Hight  表里的字段是时间型的
					sql.append(" and trunc("+name+",'mm') <= trunc(to_date('"+value+"','yyyy-mm-dd'),'mm') ");
				}else if("SML".equals(type)){//Time Month Low  表里的字段是文本型的
					sql.append(" and to_date("+name+",'yyyy-mm-dd') >= to_date('"+value+"','yyyy-mm-dd') ");
				}else if("SMH".equals(type)){//Time Month Hight  表里的字段是文本型的
					sql.append(" and to_date("+name+",'yyyy-mm-dd') <= to_date('"+value+"','yyyy-mm-dd') ");
				}else if("month".equals(type)){
					sql.append(" and to_char("+name+",'yyyy-mm') ='"+value+"'");
				}else if("inf".equals(type)&&"shr".equals(name)){
					sql.append(" and "+name+" in (select rybh from gx_sys_ryb where '('||rygh||')'||xm like '%"+value+"%')");
				}else if("ToBhss".equals(type)){
					sql.append(" and "+name+" in ("+WindowUtil.getXx(value,"").replace(",", "','")+")");
				}else if("ToBhs".equals(type)){
					sql.append(" and "+name+" in ("+WindowUtil.getXx(value,"")+")");
					System.err.println(WindowUtil.getXx(value,"").replace(",", "','")+"--------------");
				}else if("NULL".equals(type)){
					
				}else if("XFDD".equals(type)){
					sql.append(" and "+name+" = '"+ value+"'");
				}else if("NLS".equals(type)||"NHS".equals(type)){
//					if("NLS".equals(type)) {
					/*	sql.append(" and  (select count(*) from cw_pzlrmxb m  where m.pzbh = k.guid and (m.jfje >= "+ value+" and ");
//					}
//					if("NHS".equals(type)) {
						sql.append("  m.jfje >= "+ value+")) > 0");
//					}
//					if("NLS".equals(type)) {
						sql.append(" or  (select count(*) from cw_pzlrmxb m  where m.pzbh = k.guid and (m.dfje >= "+ value+" and ");
//					}
//					if("NHS".equals(type)) {
						sql.append("  m.dfje >= "+ value+")) > 0");*/
//					}
				}/*else if("NHS".equals(type)){
					sql.append(" and (select count(*) from cw_pzlrmxb m  where m.pzbh = k.guid and (m.jfje <= "+ value+" or ");
					sql.append("  m.dfje <= "+ value+")) > 0");
				}*/else {
					sql.append(" and "+name+" like'%"+ value+"%'");
				}
			}else{
				sql.append(" and "+name+" like'%"+ value+"%'");
			}
		}
	}
	
	/**
	 * map封装成实体类
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(Map map, Class<T> clazz) {
		try {
			/*
			 * 1. 通过参数clazz创建实例
			 * 2. 使用BeanUtils.populate把map的数据封闭到bean中
			 */
			T bean = clazz.newInstance();
			ConvertUtils.register(new DateConverter(), java.util.Date.class);
			BeanUtils.populate(bean, map);
			return bean;
		} catch(Exception e) {
			logger.error("表单数据map封装到实体对象异常");
			throw new RuntimeException(e);
		}
	}
	/**
	 * in语句自动生成（字符串格式）
	 * @return 返回格式   in(?,?,?,?)或=？
	 */
	public static String getInsql(String s){
		StringBuffer inSql = new StringBuffer();
		String[] arrays = s.split(",");
		int len = arrays.length;
		//判断，如果长度为1，则表示直接用=就可以了
		inSql.append(len==1?"=":" in(");
		//连接?语句
 		for(int i=0;i<len;i++){
 			inSql.append("?,");
 		}
 		s = inSql.substring(0,inSql.length()-1);
		return len==1?s:s.concat(")");
	}
	/**
	 * in语句自动生成(数组格式)
	 * @return 返回格式   in(?,?,?,?)或=？
	 */
	public static String getInsql(String[] arrays){
		StringBuffer inSql = new StringBuffer();
		int len = arrays.length;
		//判断，如果长度为1，则表示直接用=就可以了
		inSql.append(len==1?"=":" in(");
		//连接?语句
		for(int i=0;i<len;i++){
			inSql.append("?,");
		}
		String s = inSql.substring(0,inSql.length()-1);
		return len==1?s:s.concat(")");
	}
	/**
	 * 字符串处理（将所有的逗号转变为单引号中放逗号）
	 * @param val ha,ni,haha   ---->    ha','ni','haha
	 * @return
	 */
	public static String pointToSql(String val){
		val = val.replaceAll(",", "','");
		return val;
	}
	
	/**
	 * 获取主基表中xh的sql
	 * @return
	 */
	public static String getSqlByZjbXh(){
		return getSqlByZjbXh("","");
	}
	/**
	 * 获取主基表中xh的sql
	 * @param tbm 表别名
	 * @param zdm 型号字段查询之后的名称
	 * @return
	 */
	public static String getSqlByZjbXh(String tbm,String zdm){
		if(Validate.noNull(tbm)){
			tbm = tbm + ".";
		}
		return "(case when substr(" + tbm + "flh,1,2) in ('03','04','05','06','07','08','11','12','13','14','15') and substr(" + tbm + "flh,1,4) not in ('0413','0711') then to_char(" + tbm + "xh) else '' end) " + zdm + " ";
	}
	/**
	 * 获取主基表中gg的sql
	 * @return
	 */
	public static String getSqlByZjbGg(){
		return getSqlByZjbGg("","");
	}
	/**
	 * 获取主基表中gg的sql
	 * @param tbm 表别名
	 * @param zdm 规格字段查询之后的名称
	 * @return
	 */
	public static String getSqlByZjbGg(String tbm,String zdm){
		if(Validate.noNull(tbm)){
			tbm = tbm + ".";
		}
		return "(case when substr(" + tbm + "flh,1,2) in ('03','04','05','06','07','08','11','12','13','14','15') and substr(" + tbm + "flh,1,4) not in ('0413','0711') then to_char(" + tbm + "gg) else '' end) " + zdm + " ";
	}
	/**
	 * 获取主基表中xss的sql
	 * @return
	 */
	public static String getSqlByZjbXss(){
		return getSqlByZjbXss("","");
	}
	/**
	 * 获取主基表中xss的sql
	 * @param tbm 表别名
	 * @param zdm 销售商字段查询之后的名称
	 * @return
	 */
	public static String getSqlByZjbXss(String tbm,String zdm){
		if(Validate.noNull(tbm)){
			tbm = tbm + ".";
		}
		return "(case when substr(" + tbm + "flh,1,2) in ('03','04','05','06','07','08','11','12','13','14','15','16') then to_char(" + tbm + "xss) else '' end) " + zdm + " ";
	}
	/**
	 * 获取主基表中htbh的sql
	 * @return
	 */
	public static String getSqlByZjbHtbh(){
		return getSqlByZjbHtbh("","");
	}
	/**
	 * 获取主基表中htbh的sql
	 * @param tbm 表别名
	 * @param zdm 合同编号字段查询之后的名称
	 * @return
	 */
	public static String getSqlByZjbHtbh(String tbm,String zdm){
		if(Validate.noNull(tbm)){
			tbm = tbm + ".";
		}
		return "(case when substr(" + tbm + "flh,1,2) in ('03','04','05','06','07','08','11','12','13','14','15','16') then to_char(" + tbm + "htbh) else '' end) " + zdm + " ";
	}
	/**
	 * 获取主基表中sykh的sql
	 * @return 构筑物时，默认显示房间数量
	 */
	public static String getSqlByZjbSykh(){
		return getSqlByZjbSykh("","",true);
	}
	/**
	 * 获取主基表中sykh的sql
	 * @param flag 标记构筑物时显示的信息，true：显示房间数量 false：显示套件数（1）
	 * @return
	 */
	public static String getSqlByZjbSykh(boolean flag){
		return getSqlByZjbSykh("","",flag);
	}
	/**
	 * 获取主基表中sykh的sql
	 * @param tbm 表别名
	 * @param zdm 套件数字段查询之后的名称
	 * @param flag 标记构筑物时显示的信息，true：显示房间数量 false：显示套件数（1）
	 * @return
	 */
	public static String getSqlByZjbSykh(String tbm,String zdm,boolean flag){
		if(Validate.noNull(tbm)){
			tbm = tbm + ".";
		}
		return "(case when substr(" + tbm + "flh,1,4) = '0102' then " + (flag ? "nvl(sykh,0)" : "1") + " else nvl(sykh,1) end) " + zdm + " ";
	}
	/**
	 * 获取主基表中sccj的sql
	 * @return
	 */
	public static String getSqlByZjbSccj(){
		return getSqlByZjbSccj("","");
	}
	/**
	 * 获取主基表中sccj的sql
	 * @param tbm 表别名
	 * @param zdm 生产厂家字段查询之后的名称
	 * @return
	 */
	public static String getSqlByZjbSccj(String tbm,String zdm){
		if(Validate.noNull(tbm)){
			tbm = tbm + ".";
		}
		return "(case when substr(" + tbm + "flh,1,2) in ('03','04','05','06','07','08','12','13','14','15') then to_char(" + tbm + "sccj) else '' end) " + zdm + " ";
	}
	/**
	 * 获取主基表中jsh的sql
	 * @return
	 */
	public static String getSqlByZjbJsh(){
		return getSqlByZjbJsh("","");
	}
	/**
	 * 获取主基表中jsh的sql
	 * @param tbm 表别名
	 * @param zdm 生产厂家字段查询之后的名称
	 * @return
	 */
	public static String getSqlByZjbJsh(String tbm,String zdm){
		if(Validate.noNull(tbm)){
			tbm = tbm + ".";
		}
		return "(case when substr(" + tbm + "flh,1,2) in ('03','04','05','06','07','08','12','14') and substr(" + tbm + "flh,1,4) <> '0571' then to_char(" + tbm + "jsh) else '' end) " + zdm + " ";
	}
	/**
	 * 获取主基表中fjs的sql
	 * @return
	 */
	public static String getSqlByZjbFjs(){
		return getSqlByZjbFjs("","");
	}
	/**
	 * 获取主基表中fjs的sql
	 * @param tbm 表别名
	 * @param zdm 规格字段查询之后的名称
	 * @return
	 */
	public static String getSqlByZjbFjs(String tbm,String zdm){
		if(Validate.noNull(tbm)){
			tbm = tbm + ".";
		}
		return "(case when substr(" + tbm + "flh,1,2) in ('03','04','05','06','07','08','09','10','12','13','14','15') and substr(" + tbm + "flh,1,4) <> '0571' then to_char(" + tbm + "fjs) else '' end) " + zdm + " ";
	}
}
