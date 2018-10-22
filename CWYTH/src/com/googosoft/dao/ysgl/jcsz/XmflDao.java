package com.googosoft.dao.ysgl.jcsz;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.lowagie.text.List;

@Repository("nxmflDao")
public class XmflDao extends BaseDao{
	private Logger logger = Logger.getLogger(XmflDao.class);
	/**
	 * 删除单条经济科目与部门对应设置信息
	 * @param guid
	 * @return 
	 * @return
	 */
	public void doDelete(String guid) {
		String sqldelete = "DELETE from CW_XMFLB WHERE guid in ('"+guid+"')";
//		Object[] obj = guid.split(",");
		db.update(sqldelete);
	}
	
	public int doDel(String guid) {
		String sqldelete = "DELETE from CW_XMFLB WHERE guid in ('"+guid+"')";
		return db.update(sqldelete);
	}
	
	/**
	 * 添加经济科目与部门对应设置信息
	 * @param dmb
	 * @return
	 */
	public int doAdd(PageData pd) {
		String sqldelete = "DELETE from CW_XMFLB WHERE guid in ('?')";
		String sqlinsert = "INSERT into CW_XMFLB (GUID,FLMC,YWGLBM,BZ)values(?,?,?,?)";
		int i = 0;
		
		Object []obj = new Object[]{
				pd.getString("guids")
		};
		
		Object []obj1 = new Object[]{
				pd.getString("GUID"),
				CommonUtil.getBeginText(pd.getString("FLMC")),
				CommonUtil.getBeginText(pd.getString("YWGLBM")),
				pd.getString("BZ")
		};
		
		ArrayList list  = new ArrayList();
		list.add(sqldelete);
		list.add(sqlinsert);
		Boolean bool = db.batchUpdate(list,obj,obj1);
		if(bool) {
			i = 1;
		}
		return i;
	}

	/**
	 * 经济科目与部门对应设置表信息实体
	 * @param dwbh
	 * @return
	 */
	public List getObjectById1(String guid) {
		String sql = "select * from cw_jjkmybmdysz where jjkmbh = ?";
		
		return (List) db.queryForList(sql,new Object[]{guid});
	}
	
	
	public int insertTxryxx(Map<String, Object> infoMap,PageData pd,String guid) {
		String sql = "INSERT into CW_XMFLB (GUID,FLMC,YWGLBM,BZ)values(?,?,?,?)";
		Object[] obj = {
				infoMap.get("guid222"),
				CommonUtil.getBeginText(infoMap.get("FLMCS")+""),
				CommonUtil.getBeginText(infoMap.get("YWGLBMMC")+""),
				infoMap.get("bz")
		};
		return db.update(sql,obj);
	}
	public int insertTxrysx(Map<String, Object> infoMap,PageData pd,String guid) {
		String sql = "INSERT into CW_XMFLB (GUID,FLMC,YWGLBM,BZ)values(sys_guid(),?,?,?)";
		Object[] obj1 = {
				CommonUtil.getBeginText(infoMap.get("FLMCS")+""),
				CommonUtil.getBeginText(infoMap.get("YWGLBMMC")+""),
				infoMap.get("bz")
		};
		return db.update(sql,obj1);
	}
	
	public int validateById(String flmcs) {
		return db.update("select count(1) from cw_xmxxb b where b.SSXMFL in ('"+CommonUtil.getBeginText(flmcs)+"')");
	}

}
