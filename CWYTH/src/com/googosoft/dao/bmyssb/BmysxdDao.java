package com.googosoft.dao.bmyssb;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.ysgl.CW_BMYSXDB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("bmysxdDao")
public class BmysxdDao extends BaseDao{
	private Logger logger = Logger.getLogger(BmysxdDao.class);
	
	/**
	 * 先删除
	 * @param dmb
	 * @return
	 */
	public int doDeleteAll(String guid) {
		String sql = "DELETE CW_bmysxdb WHERE guid"+CommonUtils.getInsql(guid);
		Object[] obj = guid.split(",");
		
		int i = 0;
		try {  
			i = db.update(sql,obj);
		} catch (DataAccessException e) {  
		   
		    return -1;
		}
		return i;
	}
	/**
	 * 添加银行账号
	 * @param dmb
	 * @return
	 */
	public int doAdd(CW_BMYSXDB bmysxd) {
		String sql = "insert into cw_bmysxdb(guid, jyje)values(?, ?)";
		int i = 0;
		try{
			i = db.update(sql,new Object[]{bmysxd.getGuid(), bmysxd.getJyje()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 保存下达
	 * @param params
	 * @return
	 */
	public boolean doSaveXd(String[] params,String sszt){
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		for(int i=0;i<params.length;i++){
			String param = Validate.isNullToDefaultString(params[i], "");
			String data[] = param.split("=");
			if(data.length>5){
				String tbbm = Validate.isNullToDefaultString(data[0],"");
				String nd = Validate.isNullToDefaultString(data[1],"");
				String xmid = Validate.isNullToDefaultString(data[2],"");
				String jflx = Validate.isNullToDefaultString(data[3],"");
				String zcyshz = Validate.isNullToDefaultString(data[4],"");
				String jyje = Validate.isNullToDefaultString(data[5],"0");
				String zbid = "";
				if(xmid.length()>20){
					zbid = xmid.substring(0,20)+tbbm+nd;
				}else{
					zbid = tbbm+nd;
				}
				Float money = Float.parseFloat(jyje)*10000;
				//删除下达标
				StringBuffer deleteXd = new StringBuffer();
				deleteXd.append(" delete from CW_BMYSXDB where bmbh=? and sbnd=? and xmid=?");
				sqlList.add(deleteXd.toString());
				objList.add(new Object[]{tbbm,nd,xmid});
				//新增到下达表
				StringBuffer insertXd = new StringBuffer();
				insertXd.append(" insert into CW_BMYSXDB(guid,bmbh,sbnd,zcyshz,jyje,zbid,xmmc,xmid)");
				insertXd.append(" values");
				insertXd.append(" (sys_guid(),?,?,?,?,?,(select xmmc from CW_XMJBXXB where guid=?),?)");
				sqlList.add(insertXd.toString());
				objList.add(new Object[]{tbbm,nd,zcyshz,jyje,zbid,xmid,xmid});
				if("部门经费".equals(jflx)){
					//删除部门经费
					StringBuffer deleteBm = new StringBuffer();
					deleteBm.append(" delete from CW_XMJBXXB where bmbh=? and sszt=? and xmsx=?");
					sqlList.add(deleteBm.toString());
					objList.add(new Object[]{tbbm,sszt,"01"});
					//新增部门经费
					StringBuffer insertBm = new StringBuffer();
					insertBm.append(" insert into CW_XMJBXXB(guid,bmbh,XMSX,SSZT,YSJE,SYJE,XMMC,xmbh,GKBM,FZR)");
					insertBm.append(" values");
					insertBm.append(" (sys_guid(),?,?,?,?,?,?,?,?,(SELECT DWLD FROM GX_SYS_DWB WHERE DWBH=?))");
					sqlList.add(insertBm.toString());
					objList.add(new Object[]{tbbm,"01",sszt,money,money,"日常公用支出费用",GenAutoKey.makeCkbh("Cw_xmjbxxb", "xmbh", "2"),tbbm,tbbm});
				}
				if("个人经费".equals(jflx)){
					//修改个人经费
					StringBuffer updateXM = new StringBuffer();
					updateXM.append(" UPDATE CW_XMJBXXB t");
					updateXM.append(" set t.xmsx=?,t.ysje=?,t.syje=?");
					updateXM.append(" where guid=?");
					sqlList.add(updateXM.toString());
					objList.add(new Object[]{"02",money,money,xmid});
				}
			}
		}
		bool = db.batchUpdate(sqlList, objList);
		return bool;
	}

}
