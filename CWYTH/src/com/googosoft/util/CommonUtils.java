package com.googosoft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;

/**
 * sql语句片段工具类
 * @author master
 */
public class CommonUtils {
	
	private static Logger logger = Logger.getLogger(CommonUtils.class);
	
	/**
	 * 特定格式的json转Sql片段（请换成ToSqlUtil.jsonToSql(json)）
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	public static String jsonToSql(String json){
		return ToSqlUtil.jsonToSql(json);
	}
	/**
	 * 特定格式的json转分组sql片段（请换成ToSqlUtil.jsonToGroupSql(json, removeName)）
	 * @param json
	 * @param removeName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	public static String jsonToGroupSql(String json,String removeName){
		return ToSqlUtil.jsonToGroupSql(json, removeName);
	}
	/**
	 * map封装成实体类
	 * @param map
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "hiding" })
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
	 * in语句自动生成（字符串格式）（请换成ToSqlUtil.getInsql(s)）
	 * @return 返回格式   in(?,?,?,?)或=？
	 */
	public static String getInsql(String s){
		return ToSqlUtil.getInsql(s);
	}
	/**
	 * in语句自动生成(数组格式)（请换成ToSqlUtil.getInsql(arrays)）
	 * @return 返回格式   in(?,?,?,?)或=？
	 */
	public static String getInsql(String[] arrays){
		return ToSqlUtil.getInsql(arrays);
	}
	
	/**
	 * 过滤特殊字符方法(此方法未测试效率，有待优化)（请换成StringUtils.StringFilter(str)）
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str){
		return StringUtils.StringFilter(str);
	}
	/**
	 * 字符串处理（将所有的逗号转变为单引号中放逗号）（请换成ToSqlUtil.pointToSql(val)）
	 * @param val ha,ni,haha   ---->    ha','ni','haha
	 * @return
	 */
	public static String pointToSql(String val){
		return ToSqlUtil.pointToSql(val);
	}
	/**
	 * 获取当前登录人的saas码
	 * @return
	 */
	public static String getSaas(){
		return LUser.getSaas();
	}
	/**
	 * Clob转字符串
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String ClobToString(Clob clob) throws SQLException, IOException { 
        String reString = ""; 
        if(clob.length()>0){
        	Reader is = clob.getCharacterStream();// 得到流 
        	BufferedReader br = new BufferedReader(is); 
        	String s = br.readLine(); 
        	StringBuffer sb = new StringBuffer(); 
        	while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING 
        		sb.append(s); 
        		s = br.readLine(); 
        	} 
        	reString = sb.toString();
        	br.close();
        	is.close();
        }
        return reString; 
    }
    /**
     * Blob转字符串
     * @param blob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String BlobToString(Blob blob){
    	try {
    		if(Validate.noNull(blob)){
    			byte[] byteobj = blob.getBytes(1, (int) blob.length());
    			
    			String content = "";
    			try{
    				content = new String(byteobj,"utf-8");
    			}
    			catch(Exception e){
    				e.printStackTrace();
    			}
    			return content;
    		}
    		return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
    }
	
	/**
	 * 获取当前登陆人的人员编号（请换成LUser.getRybh()）
	 * @return
	 */
	public static String getRybh(){
		return LUser.getRybh();
	}
	/**
	 * 获取当前登陆人的所在单位编号（请换成LUser.getDwbh()）
	 * @return
	 */
	public static String getDwbh(){
		return LUser.getDwbh();
	}
	/**
	 * 获取当前登陆人的姓名（请换成LUser.getRyxm()）
	 * @return
	 */
	public static String getXm(){
		return LUser.getRyxm();
	}
	/**
	 * 获取当前登录人的人员工号（请换成LUser.getRygh()）
	 * @return
	 */
	public static String getRygh(){
		return LUser.getRygh();
	}
}
