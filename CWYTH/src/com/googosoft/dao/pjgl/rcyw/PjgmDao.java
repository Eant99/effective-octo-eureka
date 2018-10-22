package com.googosoft.dao.pjgl.rcyw;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.kylbx.KylbxDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
/**
 * 票据购买Dao
 * @author 张春燕
 * @param session
 * @return
 */
@Repository("pjgmDao")
public class PjgmDao extends BaseDao{
	private Logger logger = Logger.getLogger(PjgmDao.class);
	/**
	 * 向票据购买总表中插入数据
	 * @author 张春燕
	 * @param session
	 * @return
	 */
	public int doAdd(PageData pd, String loginId, String rybh) {
		
		int i = 0;
		String gmgid =GenAutoKey.get32UUID();;
		String sql = "insert into CW_PJGM (gid,ztgid,kmbh,kmmc,gmrq,gmr,pjzh,pjqz,pjqh,qjzh,pjzs,pjlx) "
				+ "values('"+gmgid+"','"+pd.getString("ztgid")+"','"+pd.getString("kmbh")+"','"+pd.getString("kmmc")+"',"
				+ "to_date('"+pd.getString("gmrq")+"','yyyy-MM-dd'),'"+pd.getString("gmr")+"','"+pd.getString("pjzhgid")+"','"+pd.getString("pjqz")+"','"+pd.getString("pjqh")+"',"
				+ "'"+pd.getString("qjzh")+"','"+pd.getString("pjzs")+"','"+pd.getString("pjlx")+"')";
		try {  
			i = db.update(sql);
			i = insertPJ(pd,gmgid);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 向票据表中插入数据
	 * @author 张春燕
	 * @param session
	 * @return
	 */
	private int insertPJ(PageData pd,String gmgid) {
		int pjzs = Integer.parseInt(Validate.isNullToDefaultString(pd.getString("pjzs"), ""));
		int i=0;
		//注意pjzh应该是32位的代码。
		for(int k = 0;k<pjzs;k++){
			int pjqh = Integer.parseInt(Validate.isNullToDefaultString(pd.getString("pjqh"),"0"));
			int pjh = pjqh + k;
			String pjhstr = pd.getString("pjqz") + String.valueOf(pjh);
			try {  
				i = db.update( " insert into CW_PJ(gid,ztgid,pjzh,gmgid,pjh,sfdy,zt,pjlx)"
						+ " values(sys_guid(),'"+pd.getString("ztgid")+"','"+pd.getString("pjzhgid")+"','"+gmgid+"','"+pjhstr+"','00','00','"+pd.getString("pjlx")+"')");
			} catch (DataAccessException e) {
			    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
			    return -1;
			}
	}
		return i;
}
	
	public List getxx(String guid) {
		String sql1 = "select * from CW_PJGM where gid in ('"+guid+"')";
		return db.queryForList(sql1);
	}
	/**
	 * 删除票据购买中的数据（包括主表和明细表）
	 * @author jiatong 2018年3月25日12:56:53
	 * @param session
	 * @return
	 */
	public boolean doDelete(String guid) {
		int i = 0;
		String sql1 = "delete from CW_PJGM where gid in ('"+guid+"') ";
		String sql2 = "delete from CW_PJ where gmgid in ('"+guid+"') ";
		i = db.update(sql1);
		i = db.update(sql2);
		if(i == 1){
			return true;
		}
		return false;
	}
	/**
	 * 检查票据表中的数据是否被使用（如果使用则不允许删除，如果未使用则允许删除）
	 * @author 贾彤 2018年3月25日12:05:40
	 * @param session
	 * @return
	 */
	public boolean doCheckused(String guid) {
		String count ="";
		int a = 1;
		String sql = "select COUNT(*) from CW_PJ where GMGID in ('"+guid+"') AND ZT <>'00'";
		count = db.queryForSingleValue(sql);
		try {
		    a = Integer.valueOf(count).intValue();
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		};
		if(a==0){
			return true;
		};
		return false;
	}
	
	
	
}
