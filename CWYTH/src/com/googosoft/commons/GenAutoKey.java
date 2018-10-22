package com.googosoft.commons;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.util.Validate;

/**
 * 主键生成工具
 * @author master
 */
@Component
public class GenAutoKey {
	
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
			System.out.println(tableName+"++"+key+"++++++"+year+"++++++"+month);
			result = dao.getCodeNew(tableName, key, "6", year, month);
		}
		return result;
	}
	/**
	 * 主键自动生成工具（6位通用） 年+月+4位流水号
	 * @param tableName
	 * @param key
	 * @return
	 */
	public static String createKeyforth(String tableName,String bz,String key){
		String year = new SimpleDateFormat("yyyy").format(new Date());
		String month = new SimpleDateFormat("MM").format(new Date());
		Date date = new Date();
	      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
	      String time=formatter.format(date);
		String result = "";
		if(Validate.noNull(tableName)&&Validate.noNull(key)){
			result = dao.getCodeNewOnes(tableName, key, "4",bz, time, month);
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
	public static String createRybh(String tableName,String key,String len){
		return dao.getRybhByProcedure(tableName, key, len);
	}
	/**
	 * 根据表名、长度、主键，生成有序的主键
	 * @return 主键 000001
	 */
	public static String makeCkbh(String tableName,String key,String len){
		return dao.getBhByProcedure(tableName, key, len);
	}
	public static String makePzbh(String tableName,String key,String len, String sszt,String pzlx) {
		return dao.getPzbhByProcedure(tableName, key, len,sszt,pzlx);
	}
	/**
	 * 获取32位guid
	 * @return
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().trim().toUpperCase().replaceAll("-", "");
	}

	
}
