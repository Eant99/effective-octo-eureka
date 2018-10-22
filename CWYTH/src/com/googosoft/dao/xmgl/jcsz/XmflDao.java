package com.googosoft.dao.xmgl.jcsz;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;

@Repository("xmflDao")
public class XmflDao extends BaseDao {
	/**
	 * 权限model
	 * @param rybh 人员编号
	 * @return
	 */
	public List GetXmfl(String sjfl) {
//		String sql = "SELECT DWB.DWBH AS DWBH,DWB.BMH AS BMH, DWB.MC AS MC,"
//					+ "(SELECT COUNT(1) FROM %SDWB A WHERE A.SJDW = DWB.DWBH) AS XJCOUNT,DWB.DWZT AS DWZT " 
//					+ "FROM "+TnameU.GLQXB+" T " 
//					+ "INNER JOIN %SDWB DWB ON DWB.DWBH = T.DWBH AND T.RYBH = ? AND EXISTS (SELECT 1 FROM "+TnameU.GLQXB+" WHERE RYBH = ? AND ZL='2') " 
//					+ "UNION " 
//					+ "SELECT DWB.DWBH,BMH,MC,(SELECT COUNT(1) FROM %SDWB A WHERE A.SJDW = DWB.DWBH) AS XJCOUNT,DWB.DWZT " 
//					+ "FROM %SDWB DWB " 
//					+ "INNER JOIN %SRYB RYB ON DWB.DWBH = RYB.DWBH AND RYB.RYBH = ? "
//					+ "AND NOT EXISTS (SELECT 1 FROM "+TnameU.GLQXB+" WHERE RYBH = ? AND ZL='2') ORDER BY BMH ";
//		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz);
		String sql="";
		if(sjfl.equals("root")){
			 sql=" select (SELECT COUNT(1) FROM Cw_xmflszb A WHERE A.SJFL = T.FLBH) AS XJCOUNT,GUID,FLBH,FLMC,SJFL from  Cw_xmflszb  T where sjfl='root' ";	
		}else{
			 sql=" select (SELECT COUNT(1) FROM Cw_xmflszb A WHERE A.SJFL = T.FLBH) AS XJCOUNT,GUID,FLBH,FLMC,SJFL from  Cw_xmflszb  T where sjfl='"+sjfl+"' ";
		}
		
		return db.queryForList(sql,new Object[]{});
	}
	

}
