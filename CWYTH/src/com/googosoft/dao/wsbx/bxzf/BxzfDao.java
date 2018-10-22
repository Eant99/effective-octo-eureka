package com.googosoft.dao.wsbx.bxzf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("bxzfDao")
public class BxzfDao extends BaseDao{
	
	/**
	 * 向cw_bxzfb中插入一条数据
	 * @param bxid
	 * @return
	 */
	public int insertBxzf(String bxid) {
		String sql = "insert into cw_bxzfb (bxid) values(?)";
		Object[] obj = {bxid};
		return db.update(sql,obj);
	}
	
	/**
	 * 减去 个人经费设置和冲借款
	 * @param 
	 * @return
	 */
	public boolean updateJfsz(String guid) {
		ArrayList<String> sqlList = new ArrayList<String>();
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	List<Object[]> objList = new ArrayList<Object[]>();
    	list = getbxzelist(guid);
    	for(int i=0,len=list.size();i<len;i++){
    		Map map = (Map)list.get(i);
    		String bcbxje = Validate.isNullToDefaultString(map.get("BCBXJE"), "0");
    		String xmguid = Validate.isNullToDefaultString(map.get("XMGUID"), "");
    		String sql = "UPDATE CW_JFSZB SET syje=syje-to_number(?) WHERE xmbh=? or guid=?";
    		sqlList.add(sql);
    		objList.add(new Object[]{bcbxje,xmguid,xmguid});
    	}
//		String sql = "UPDATE CW_JFSZB SET syje=syje-to_number('"+bcbxje+"') WHERE xmbh='"+xmguid+"' or guid='"+xmguid+"'";
		boolean bool = false;
		try {  
			bool = db.batchUpdate(sqlList, objList);
			updateQkje(guid);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();
			System.out.println("项目金额异常");
		    return false;
		}
		return bool;
	}
	/**
	 * 加回  个人经费设置和冲借款
	 * @param 
	 * @return
	 */
	public boolean updateToAddJfsz(String guid) {
		ArrayList<String> sqlList = new ArrayList<String>();
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	List<Object[]> objList = new ArrayList<Object[]>();
    	list = getbxzelist(guid);
    	for(int i=0,len=list.size();i<len;i++){
    		Map map = (Map)list.get(i);
    		String bcbxje = Validate.isNullToDefaultString(map.get("BCBXJE"), "0");
    		String xmguid = Validate.isNullToDefaultString(map.get("XMGUID"), "");
    		String sql = "UPDATE CW_JFSZB SET syje=syje+to_number(?) WHERE xmbh=? or guid=?";
    		sqlList.add(sql);
    		objList.add(new Object[]{bcbxje,xmguid,xmguid});
    	}
//		String sql = "UPDATE CW_JFSZB SET syje=syje-to_number('"+bcbxje+"') WHERE xmbh='"+xmguid+"' or guid='"+xmguid+"'";
		boolean bool = false;
		try {  
			bool = db.batchUpdate(sqlList, objList);
			updateQkje(guid);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();
			System.out.println("项目金额异常");
		    return false;
		}
		return bool;
	}
	 /**
	  * 报销项目
	  * @param guid
	  * @return
	  */
	 public List<Map<String,Object>> getbxzelist(String guid) {
	    	StringBuffer sql = new StringBuffer();
	    	sql.append(" select guid,bcbxje bcbxje,jfbh xmguid from CW_CCSQSPB_XM where ccsqid=(select ccywguid from cw_clfbxzb where guid=?) ");
	    	sql.append(" union all");
			sql.append(" select guid,to_char(bcbxje) bcbxje,xmguid xmguid from CW_RCBXXMMXB where zbid=? ");
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = db.queryForList(sql.toString(),new Object[]{guid,guid});
			return list;
	    }
	
	/**
	 * 更新借款表
	 * @param guid
	 */
	public void updateQkje(String guid) {
		String sql = "select sum(nvl(t.cjkje,0))jkje,jkid from cw_cjkb t where t.jkdh=? group by jkid";
		List list = db.queryForList(sql,new Object[]{guid});
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String jkje = Validate.isNullToDefaultString(map.get("JKJE"), "0");
				String jkid = Validate.isNullToDefaultString(map.get("JKID"), "");
				String update = "update CW_YZFSQSPB t set t.qkje=(nvl(t.qkje,t.jkje)-to_number(?)) where t.djbh=?";
				db.update(update,new Object[]{jkje,jkid});
			}
		}
	}
}





















