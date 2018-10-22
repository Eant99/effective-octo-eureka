package com.googosoft.dao.kjzdqy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.kylbx.KylbxDao;
import com.googosoft.util.Validate;
/**
 * 会计制度启用
 * @author wangzhiduo
 * @date 2018-1-2上午9:37:06
 */
@Repository("kjzdqyDao")
public class KjzdqyDao extends BaseDao{
	private Logger logger = Logger.getLogger(KylbxDao.class);
	
	/**
	 * 保存会计制度
	 * @param zdm
	 * @param guid
	 * @return
	 */
	public boolean doSave(String zdm, String guid) {
		ArrayList<String> list =new  ArrayList<String>(); 
		String sql = "UPDATE CW_KJZDQY SET ZD = '"+zdm+"' WHERE guid = '" + guid + "'";
		list.add(sql);
		if("0001".equals(zdm)){//旧会计制度，隐去财务报表
			String sql1 = " update zc_sys_mkb set qxbz='1' where mkmc='旧财务报表' ";
			String sql2 = " update zc_sys_mkb set qxbz='0' where mkmc='财务报表' ";
			list.add(sql1);
			list.add(sql2);
		}else if("0002".equals(zdm)){//新会计制度，隐去旧财务报表
			String sql3 = " update zc_sys_mkb set qxbz='1' where mkmc='旧财务报表' ";
			String sql4 = " update zc_sys_mkb set qxbz='0' where mkmc='财 务报表' ";
			list.add(sql3);
			list.add(sql4);
		}
		return db.batchUpdate(list);
	}
	
	/**
	 * 会计制度启用表
	 * @return
	 */
	public List getZd() {
		String sql = "select guid ,zdm from CW_KJZDQY_ZDB ";
		return db.queryForList(sql);
	}
	
	/**
	 * 查询会计制度转换信息
	 * @return
	 */
	public List<Map<String, Object>> getKjzdzh() {
		String sql=" select * from cw_kjkmszb_x ";
		return db.queryForList(sql);
	}

	/**
	 * 删除会计制度转换
	 * @param guid
	 * @return
	 */
	public int doDelKjkm(String guid) {		
		String sql=" DELETE from cw_kjkmszb_x where guid in ('"+guid+"')";
		int result = 0;
		try {
			result = db.update(sql);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 事务回滚
		}
		return result;
	}
/**
 * 修改回显
 * @param guid
 * @return
 */
	public Map<String, Object> getKjzdzhByid(String guid) {
		String sql=" select * from cw_kjkmszb_x where guid ='"+guid+"' ";
		return db.queryForMap(sql);
	}

	/**
	 * 会计制度的编辑保存
	 * @param map
	 * @return
	 */
public int doSaveKjzd(Map<String,Object> map) {
	String operateType = (String) map.get("operateType");
	String ykmbh = (String) map.get("ykmbh");
	String ykmmc = (String) map.get("ykmmc");
	String xkmbh = (String) map.get("xkmbh");
	String xkmmc = (String) map.get("xkmmc");
	String guid = (String) map.get("guid");
	String sql="";
	if("U".equals(operateType)){
		sql = " update cw_kjkmszb_x x  set x.ykmbh='"+ykmbh+"',x.ykmmc='"+ykmmc+"',x.xkmbh='"+xkmbh+"',x.xkmmc='"+xkmmc+"' where x.guid='"+guid+"'  ";
	}else{
		sql = " insert into  cw_kjkmszb_x x (x.guid,x.ykmbh,x.ykmmc,x.xkmbh,x.xkmmc) values(sys_guid(),'"+ykmbh+"','"+ykmmc+"','"+xkmbh+"','"+xkmmc+"')  ";
	}
	return db.update(sql);
}

	/**
	 *执行---- 先将科目余额插入
	 * @return
	 */
	public int doZx1() {
		String sql =" insert into cw_kmyeb  select sys_guid(),t.kmbh ,t.kmmc ,t.sfmj,'0',TO_CHAR(SYSDATE,'yyyy'),t.sszt,'"+LUser.getGuid()+"',sysdate,( select  kmzye from cw_kmyeb b where b.kmbh=t.kmbh and b.kjzd='0001') as kmzye,t.kmsx,t.guid,t.kjzd   from cw_kjkmszb t left join cw_kmyeb b on t.guid=b.kmid where t.kjzd='0002'";
//		String sql =" insert into cw_kmyeb  select sys_guid(),t.kmbh ,t.kmmc ,t.sfmj,'0',TO_CHAR(SYSDATE,'yyyy'),t.sszt,t.czr,sysdate, '',t.kmsx,t.guid,t.kjzd  from cw_kjkmszb t left join cw_kmyeb b on t.guid=b.kmid where b.kmzye='0' or b.kmzye is null ";
		String sql2 = " delete from cw_kmyeb where kjzd='0001' ";
		int i = db.update(sql);
		int j = db.update(sql2);
		int m = i+j;
		return m;
	}

	/**
	 * 执行----根据替换数据转换金额
	 * @return
	 */
	public boolean doZx2() {
	    List<String> sqllist = new ArrayList<>();
		List<Map<String, Object>> list = getKjzdzh();
		for (Map<String, Object> map : list) {
			String ykmbh = (String) map.get("ykmbh");
			String ykmmc = (String) map.get("ykmmc");
			String xkmbh = (String) map.get("xkmbh");
			String xkmmc = (String) map.get("xkmmc");
			sqllist.add("  update cw_kmyeb set kmzye = (nvl(kmzye,0)+nvl((select kmzye from cw_kmyeb where  kmbh='"+ykmbh+"' and kmmc='"+ykmmc+"' and kjzd='0001' ),0) )   where kmbh='"+xkmbh+"' and kmmc='"+xkmmc+"' and kjzd='0002'  ");
		}
//		for (String string : sqllist) {
//			System.err.println(string);
//		}
		 return db.batchUpdate(sqllist);
	}

	/**
	 *执行---- 将会计制度启用为新版
	 * @return
	 */
	public int doZx3(String ztid) {
		String sql = "UPDATE CW_KJZDQY SET ZD = '0002' WHERE guid = '" + ztid + "'";
		return db.update(sql);
	}
	/**
	 *执行----修改财务报表和旧财务报表的权限
	 * @return
	 */
	public int doZx4() {
		String sql1 = " update zc_sys_mkb set qxbz='0' where mkmc='旧财务报表' ";
		String sql2 = " update zc_sys_mkb set qxbz='1' where mkmc='财务报表' ";
		int i= db.update(sql1);
		int j= db.update(sql2);
		int m = i+j;
		return m;
	}
	/**
	 * 手输联想获取会计科目---会计科目转换使用
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getKjkmzh(String value,String flag) {
		value = "%" + value + "%";
		// String sql =
		// "select '('||d.kmbh||')'||d.kmmc as xmlxxx from CW_KJKMSZB d   where '('||d.kmbh||')'||d.kmmc like ?  and rownum<=10 order by d.kmbh";
		String sql = new String();
		if("KJKMZH".equals(flag)){//原科目编号 
			sql= "select d.kmbh as xmlxxx from CW_KJKMSZB d   where '('||d.kmbh||')'||d.kmmc like ?  and rownum<=10 and d.kjzd='0001' order by d.kmbh";
		}else if("KJKMZH2".equals(flag)){//现科目编号
			sql= "select d.kmbh as xmlxxx from CW_KJKMSZB d   where '('||d.kmbh||')'||d.kmmc like ?  and rownum<=10 and d.kjzd='0002' order by d.kmbh";
		}
		List list = db.queryForList(sql, new Object[] { value });
		StringBuffer suggestXx = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (i < list.size() - 1) {
				suggestXx.append(map.get("xmlxxx") + ";;");
			} else {
				suggestXx.append(map.get("xmlxxx"));
			}
		}
		return suggestXx.toString();
	}

	/**
	 * 联想输入的验证
	 * @param value
	 * @return
	 */
	public String validateKjkmzh(String value,String flag) {
		String sql = new String();
		if("KJKMZHValidate".equals(flag)){//原科目编号 
			sql = "select count(d.kmbh) as counts from CW_KJKMSZB d where d.kmbh = ? and d.kjzd='0001' ";
		}else if("KJKMZH2Validate".equals(flag)){//现科目编号
			sql = "select count(d.kmbh) as counts from CW_KJKMSZB d where d.kmbh = ? and d.kjzd='0002' ";
		}
		String counts = db.queryForObject(sql, new Object[] { value },
				String.class);
		if (counts.equals("1")) {
			return "{\"success\":\"true\"}";
		} else {
			return "{\"success\":\"false\"}";
		}
	}

	/**
	 * 获取原科目名称
	 * @param ykmbh
	 * @return
	 */
	public String getKmmc(String ykmbh,String xkmbh) {
		String sql = new String();
		if(Validate.isNull(xkmbh) && Validate.noNull(ykmbh)){
			sql =" select b.kmmc from cw_kjkmszb b where b.kmbh='"+ykmbh+"' and b.kjzd='0001' ";
		}else if(Validate.isNull(ykmbh) && Validate.noNull(xkmbh)){
			sql =" select b.kmmc from cw_kjkmszb b where b.kmbh='"+xkmbh+"' and b.kjzd='0002' ";
		}
		return db.queryForSingleValue(sql);
	}

}
