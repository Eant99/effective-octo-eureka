package com.googosoft.dao.ysgl.xmsz;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.CW_JSXXB;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;

@Repository("xmxx2Dao")
public class Xmxx2Dao extends BaseDao{
	
	
	/**
	 * 获取实体类
	 * 
	 * @param rybh
	 * @return
	 */
	public int getObjectById(PageData pd) {
		String sql = "select count(1) from cw_xmxxb b where b.xmmc = '"+pd.getString("xmmc")+"'";
		return db.queryForInt(sql);
	}
	
	public List getXmfl(){
		return db.queryForList("select ");
	}
	
	
	
	/*
	 * 编辑项目详细信息的sql语句 lsq
	 * 
	 * */
	public int updateXmxx(PageData pd) {
		String sql ="UPDATE cw_xmxxb b set b.xmmc=?,b.ssxmfl=?,b.sjxm=(select b.guid from cw_xmxxb b where b.xmmc=? ),b.ywsx=?,b.sxje=?,b.sfyxcz=?,b.czbl=?,b.txbfb=?,b.xmnd=?,b.ssxb=?,b.xmxz=?,b.sfqd=?,b.qysj=to_date(?,'yyyy-mm-dd'),b.wcsj=to_date(?,'yyyy-mm-dd'),b.kzfw=? where b.guid=?";
		Object[] obj = {
				pd.getString("xmmc"),
				pd.getString("ssxmfl"),
				pd.getString("sjxm"),
				pd.getString("ywsx"),
				pd.getString("sxje"),
				pd.getString("sfyxcz"),
				pd.getString("czbl"),
				pd.getString("txbfb"),
				pd.getString("xmnd"),
				pd.getString("ssxb"),
				pd.getString("xmxz"),
				pd.getString("sfqd"),
				pd.getString("qysj"),
				pd.getString("wcsj"),
				pd.getString("kzfw"),
				pd.getString("guid"),
				
				
		};
		System.err.println("gui======"+pd.getString("guid"));
		return db.update(sql, obj);
	}
	
	/**
	 * 删除
	 * @param guid   
	 * @return 
	 */
	public int doDelete(PageData pd) {
		String guid = pd.getString("dwbh");
		String sql = "delete from cw_xmxxb where guid in ('"+guid+"')";
	
		int i = 0;
		try {  
			i = db.update(sql);
		
		} catch (Exception e) {
		    return -1;
		}
		return i;
	}
	public int deleteXmxx(PageData pd) {
		String sql = "delete from cw_xmjbxxb where guid in (?)";
		Object guid = pd.getString("guid");
		return db.update(sql, guid);
	}
	public boolean checkXmbhExist(PageData pd) {
		String xmbh = pd.getString("xmbh");
		String slq = "select * from cw_xmjbxxb where xmbh = ?";
		if(db.queryForList(slq,xmbh).size() > 0) {
			return true;
		}
		return false;
	}


	/**
	 * 新增
	 * 
	 * @param ryb
	 * @return
	 */

	public int doAdd(PageData pd) {
//		String xmlxbh = AutoKey.createKey("cw_xmxxb", "guid", "4");
//		System.err.println("________"+xmlxbh);
		String sql = "insert into cw_xmxxb values(sys_guid(),?,?,?, (select b.guid from cw_xmxxb b where b.xmmc=? ) ,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?)";
		Object[] obj = {
				GenAutoKey.createKeyforth("cw_xmxxb", "", "xmbh"),
				pd.getString("xmmc"),
				pd.getString("ssxmfl"),
				pd.getString("sjxm"),
				pd.getString("ywsx"),
				pd.getString("sxje"),
				pd.getString("sfyxcz"),
				pd.getString("czbl"),
				pd.getString("txbfb"),
				pd.getString("xmnd"),
//				CommonUtil.getEndText(pd.getString("ssxb")),
				pd.getString("ssxb"),
				pd.getString("xmxz"),
				pd.getString("sfqd"),
				pd.getString("qysj"),
				pd.getString("wcsj"),
				pd.getString("kzfw")
		};
		return db.update(sql,obj);
	}

	public Map<?, ?> getObjectById(String guid) {
		String sql = "select *\r\n" + 
				"  from (select b.guid,\r\n" + 
				"               b.xmbh,\r\n" + 
				"               b.xmmc,\r\n" + 
				"               b.ssxmfl,\r\n" + 
				"               (select a.xmmc from cw_xmxxb a where b.sjxm=a.guid) sjxm,\r\n" + 
				"               b.ywsx,\r\n" + 
				"               b.sxje,\r\n" + 
				"               b.sfyxcz,\r\n" + 
				"               b.czbl,\r\n" + 
				"               b.txbfb,\r\n" + 
				"               b.xmnd,\r\n" + 
				"               b.ssxb,\r\n" + 
				"               b.xmxz,\r\n" + 
				"               b.sfqd,\r\n" + 
				"               b.qysj,\r\n" + 
				"               b.wcsj,\r\n" + 
				"               b.kzfw\r\n" + 
				"          from cw_xmxxb b) k\r\n" + 
				" where k.guid='"+guid+"'" ; 
				
				

		return db.queryForMap(sql);
	}
}


















