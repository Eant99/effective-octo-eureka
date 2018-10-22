package com.googosoft.dao.ysgl.xmsz;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;

@Repository("srxmDao")
public class SrxmDao extends BaseDao{
	
	public List<Map<String, Object>> selectSrxmList(){
		String sql = "SELECT A.*,ROWNUM AS \"_XH\",(SELECT '('||B.DM||')'||B.MC FROM GX_SYS_DMB B WHERE B.ZL = '"+Constant.XMFL+"' AND B.DM = A.XMFL) AS XMFLMC FROM CW_SRXMB A order by \"_XH\"";
		return db.queryForList(sql);
	}
	public Map<String, Object> selectSrxmMapById(String guid){
		Object[] obj = {guid};
		String sql = "select * from cw_srxmb where guid = ?";
		return db.queryForMap(sql,obj);
	}
	
	public int updateSrxm(PageData pd) {
		String sql = "UPDATE CW_SRXMB SET "
				+ "SRXMBH = ?,SRXMMC = ?,XMFL = ?,SRBZ = ?,BZ = ?,CZR = ?,CZRQ = to_date(?,'yy-mm-dd hh24:mi:ss') where guid = ?";
		Object[] obj = {
				pd.getString("srxmbh"),
				pd.getString("srxmmc"),
				pd.getString("xmfl"),
				pd.getString("srbz"),
				pd.getString("bz"),
				pd.getString("czr"),
				DateUtil.getTime(),
				pd.getString("guid")
		};
		return db.update(sql, obj);
	}
	public int insertSrxm(PageData pd) {
		String sql = "insert into cw_srxmb values(sys_guid(),?,?,?,?,?,?,to_date(?,'yy-mm-dd hh24:mi:ss'))";
		Object[] obj = {
				pd.getString("srxmbh"),
				pd.getString("srxmmc"),
				pd.getString("xmfl"),
				pd.getString("srbz"),
				pd.getString("bz"),
				pd.getString("czr"),
				DateUtil.getTime()
		};
		return db.update(sql,obj);
	}
	public int deleteSrxm(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "delete from cw_srxmb where guid in ('"+guid+"')";
		return db.update(sql);
	}
	public boolean checkSrxmbhExist(PageData pd) {
		String srxmbh = pd.getString("srxmbh");
		String slq = "select * from cw_srxmb where srxmbh = ?";
		if(db.queryForList(slq,srxmbh).size() > 0) {
			return true;
		}
		return false;
	}
}





















