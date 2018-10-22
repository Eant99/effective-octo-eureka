package com.googosoft.util;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.googosoft.dao.base.DBHelper;

/**
 * 主键生成工具
 * @author master
 */
@Component
public class AutoKey {
	
	@Resource(name="jdbcTemplate")
	private DBHelper cacheDao;

	private static DBHelper dao;
	
	@PostConstruct
	public void init(){
		this.dao = cacheDao;
	}
	
	/**
	 * 主键自动生成工具（6位通用） 年+月+6位流水号
	 * @param tableName
	 * @param key
	 * @return
	 */
	public static String createKey(String tableName,String key){
		String year = new SimpleDateFormat("yyyy").format(new Date());
		String month = new SimpleDateFormat("MM").format(new Date());
		String result = "";
		if(Validate.noNull(tableName)&&Validate.noNull(key)){
			result = dao.getCodeNew(tableName, key, "6", year, month);
		}
		return result;
	}
	/**
	 * 主键自动生成工具（n位通用）     年+月+n位流水号
	 * @param tableName 表名
	 * @param key  主键
	 * @param num   位数
	 * @return
	 */
	public static String createKey(String tableName,String key,String num){
		String year = new SimpleDateFormat("yyyy").format(new Date());
		String month = new SimpleDateFormat("MM").format(new Date());
		String result = "";
		if(Validate.noNull(tableName)&&Validate.noNull(key)&&Validate.noNull(num)){
			result = dao.getCodeNew(tableName, key, num, year, month);
		}else{
			//这里应该对于所有的参数进行验证非空，如果存在空值，则进行默认的生成。
			result = dao.getCodeNew(tableName, key, "6", year, month);
		}
		return result;
	}
	/**
	 * 包含保留位的yqbh
	 * @param flh
	 * @param num
	 * @return
	 */
	public static List createYqbh(String flh,int shl){
		String year = new SimpleDateFormat("yyyy").format(new Date());
		List list = new ArrayList();
		if(Validate.noNull(year)&&Validate.noNull(flh)&&Validate.noNull(shl)){
			list = dao.getYqbhNew(flh, shl, year);
		}
		return list;
	}
	
	/**
	 * 主键自动生成工具（n位通用）    年+n位流水号
	 * @param tableName 表名
	 * @param key  主键
	 * @param num   位数
	 * @return
	 */
	public static String createYear(String tableName,String key,String num){
		String year = new SimpleDateFormat("yyyy").format(new Date());
		String result = "";
		if(Validate.noNull(tableName)&&Validate.noNull(key)&&Validate.noNull(num)){
			result = dao.getCodeNew(tableName, key, num, year, "");
		}else{
			//这里应该对于所有的参数进行验证非空，如果存在空值，则进行默认的生成。
			result = dao.getCodeNew(tableName, key, "6", year, "");
		}
		return result;
	}
	/**
	 * 主键自动生成工具（n位通用）    年+n位流水号
	 * @param tableName 表名
	 * @param key  主键
	 * @param num   位数
	 * @return
	 */
	public static List createYear(String tableName,String key,String num, String sl){
		String year = new SimpleDateFormat("yyyy").format(new Date());
		List list;
		if(Validate.noNull(tableName)&&Validate.noNull(key)&&Validate.noNull(num)){
			list = dao.getCodeNew(tableName, key, num, year, "", sl);
		}else{
			//这里应该对于所有的参数进行验证非空，如果存在空值，则进行默认的生成。
			list = dao.getCodeNew(tableName, key, "6", year, "", sl);
		}
		return list;
	}
	/**
	 * 单位编号主键自动生成工具
	 * @param tableName 表名
	 * @param key  主键
	 * @param num   位数
	 * @return
	 */
	public static String createDwbh(String tableName,String key,String num){
		String result = "";
		if(Validate.noNull(tableName)&&Validate.noNull(key)&&Validate.noNull(num)){
			result = dao.getDWbhByProcedure(tableName, key, num,"");
		}
		return result;
	}
	/**
	 * 根据表名、长度、主键，生成有序的主键
	 * @return 主键 000001
	 */
	public static String makeCkbh(String tableName,String key,String len){
		return dao.getBhByProcedure(tableName, key, len);
	}
	
	/**
	 * 单位内和单位间调拨、项目变动、附件增加、单价变动
	 * 根据模块编号，取归口人员
	 * @param mkbh
	 * @param gkry 已选择的归口人员
	 * @return
	 */
	public static List getGuikouRy(String mkbh, String gkry){
		List list = new ArrayList();
		if(Validate.noNull(mkbh)){
			list = dao.getGuikouList(mkbh, gkry);
		}
		return list;
	}
	/**
	 * 折旧信息回退
	 * @param mkbh
	 * @return
	 */
	public static void getZjxxht(String date){
		dao.getZjxxhtProcedure(date);
	}
}
