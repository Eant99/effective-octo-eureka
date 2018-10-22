package com.googosoft.dao.wsbx.jkgl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.CW_JKYWB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("jksqDao")
public class JksqDao extends BaseDao {

	public Map<String, Object> getObjectById(String gid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select djbh,(select '('||bmh||')'||mc from gx_sys_dwb a where a.dwbh=k.szbm )szbm,");
		sql.append(" to_char(jksj,'yyyy-MM-dd')jksj,(select '('||rygh||')'||xm from gx_sys_ryb b where b.guid=k.jkr)jkrmc,");
		sql.append(" jkr,TO_CHAR(nvl(JKZJE,0.00),'FM999999990.00')as JKZJE,JKSY,ZFFS,FJZS,PROCINSTID ");
		sql.append(" from CW_JKYWB k WHERE k.gid='" + gid + "'");

		return db.queryForMap(sql.toString());
	}
	public List<Map<String, Object>> selectXmxxListById(String gid) {
		Object[] obj = { gid };
		String sql = "select (select B.mc  from gx_sys_dmb B where B.zl = '"+Constant.JFLX+"'  " +
				"and B.dm = A.jflx) AS JFLXMC,(select '(' || b.rybh || ')' || b.xm  from gx_sys_ryb b  " +
				" where b.rybh = a.fzr) as fzrxm,'('||A.xmbh||')' || a.xmmc as mc,TO_CHAR(nvl(a.ye,0.00),'FM999999999990.00')as ye,a.guid,TO_CHAR(nvl(k.zcje,0.00),'FM999999999990.00')as zcje from xminfos A left join  CW_JKYWB_MXB k on k.jfbh = a.guid " +
				"where 1 = 1  " +
//				"and A.ye <> 0  and ((bsqr = '"+LUser.getGuid()+"' or bsqr = '"+LUser.getRybh()+"') and jflx = '02')  or (jflx = '01' and bmbh = '"+LUser.getDwbh()+"') " +
				"and k.jkid='" + gid + "' ";
		return db.queryForList(sql.toString());
	}
	
	/**对公转账list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getDgList(String gid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select (select m.khyh from Cw_Wldwmxb m where m.guid=t.dfyh)yhname,");
		sql.append(" t.guid,t.zfdh,t.dfdq,t.dfzh,w.dwmc,w.guid,w.wlbh,t.dfyh,");
		sql.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je");
		sql.append(" from CW_DGZFB t");
		sql.append(" left join Cw_wldwb w on w.WLBH=t.DFDW");
		sql.append(" where 1=1 and t.zfdh='" + gid + "'");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	
	public String getXmFzr(String gid){
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" '('||r.rybh||')'||r.xm as xmfzr");
		sql.append(" from CW_JFSZB t");
		sql.append(" left join gx_sys_ryb r on r.rybh=t.fzr");
		sql.append(" where 1=1");
		sql.append(" and t.xmbh='"+gid+"' or t.guid='"+gid+"'");
		String xmfzr = Validate.isNullToDefaultString(db.queryForSingleValue(sql.toString()), "");
		return xmfzr;
	}
	/**对私转账list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getDsList(String gid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" '('||w.rybh||')'||w.xm as ryxm,");
		sql.append(" t.guid,t.zfdh,t.ryxz,t.dfzh,t.klx,");
		sql.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je");
		sql.append(" from CW_DSZFB t ");
		sql.append(" left join gx_sys_ryb w on w.rybh=t.ryxm");
		sql.append(" where 1=1 and t.zfdh='" + gid + "'");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	public boolean doDelete(String gid) {
		List<String> strlist = new ArrayList<String>();
		List<Object[]> sqllist = new ArrayList<Object[]>();
		String sql = "DELETE CW_JKYWB WHERE GID" + CommonUtils.getInsql(gid);
		Object[] obj = gid.split(",");
		String sql1 = "DELETE CW_JKYWB_MXB WHERE JKID" + CommonUtils.getInsql(gid);
		Object[] obj1 = gid.split(",");
		String sql2 = "DELETE cw_dszfb WHERE zfdh" + CommonUtils.getInsql(gid);
		Object[] obj2 = gid.split(",");
		String sql3 = "DELETE cw_dgzfb WHERE zfdh" + CommonUtils.getInsql(gid);
		Object[] obj3 = gid.split(",");
		strlist.add(sql);
		strlist.add(sql1);
		strlist.add(sql2);
		strlist.add(sql3);
		sqllist.add(obj);
		sqllist.add(obj1);
		sqllist.add(obj1);
		sqllist.add(obj1);
		return db.batchUpdate(strlist, sqllist);
	}
	/**
	 * 增加
	 * @param rkgl
	 * @return
	 */
	public int doAdd(PageData pd,String gid) {
		String rybh =LUser.getGuid();
		String sql = "insert into CW_JKYWB(gid,djbh,jkr,jbr,szbm,jksj,jkzje,jksy,shzt,zffs,fjzs,syje) values('"+gid+"','"+GenAutoKey.createKeyforth("CW_JKYWB","JK", "djbh")+"','"+pd.getString("jkr")+"','"+rybh+"','"+CommonUtil.getBeginText(pd.getString("szbm"))+"',to_date('"+pd.getString("jksj")+"','yyyy-MM-dd'),'"+pd.getString("jkzje")+"','"+pd.getString("jksy")+"','01','"+pd.getString("zffs")+"','"+pd.getString("fjzs")+"','"+pd.getString("jkzje")+"')";
		return db.update(sql);
	}
	public int doAddMx(Map<String, Object> map,String gid) {
		String sql = "insert into CW_JKYWB_MXB(gid,jkid,jfbh,zcje) values(sys_guid(),'"+gid+"',?,?)";
		Object[] ojb = {
				map.get("jfbh"),
				map.get("zcje"),
			};
		return db.update(sql,ojb);
	}
	public int doAddDs(Map<String, Object> map,String gid) {
		if(Validate.noNull(map.get("ryxm"))){
			String sql = "insert into cw_dszfb(guid,zfdh,ryxz,ryxm,klx,dfzh,je,bxlx) values (sys_guid(),'"+gid+"',?,?,?,?,?,5)";
			Object[] ojb = {
					map.get("ryxz"),
					CommonUtil.getBeginText(map.get("ryxm")+""),
					map.get("klx"),
					map.get("dsdfzh"),
					map.get("dsje")
			};
			return db.update(sql,ojb);
		}
		return 0;
	}
	public int doAddDg(Map<String, Object> map,String gid) {
		if(Validate.noNull(map.get("dfdw"))){
			String sql = "insert into cw_dgzfb (guid,zfdh,dfdw,dfdq,dfyh,dfzh,je,bxlx) values(sys_guid(),'"+gid+"',?,?,?,?,?,5)";
			Object[] ojb = {
					map.get("dfdw"),
					map.get("dfdz"),
					map.get("dfyh"),
					map.get("dgdfzh"),
					map.get("dgje")
			};
			return db.update(sql,ojb);
		}
		return 0;
	}
	
	public int doUpdate(PageData pd,String gid) {
		String sql = "update CW_JKYWB set jkr='"+pd.getString("jkr")+"',szbm='"+CommonUtil.getBeginText(pd.getString("szbm"))+"',jksj=to_date('"+pd.getString("jksj")+"','yyyy-MM-dd'),jkzje='"+pd.getString("jkzje")+"',jksy='"+pd.getString("jksy")+"',zffs='"+pd.getString("zffs")+"',fjzs='"+pd.getString("fjzs")+"',syje='"+pd.getString("jkzje")+"' where gid='"+gid+"' ";
		return db.update(sql);
	}
	public int deleteMx(PageData pd,String gid) {
		String sql = "delete CW_JKYWB_MXB where jkid='"+gid+"'";
		return db.update(sql);
	}
	
	public int deleteDg(PageData pd,String gid) {
		String sql = "delete cw_dgzfb where zfdh=?";
		Object[] obj = {
			pd.getString("gid")
		};
		return db.update(sql,obj);
	}
	public int deleteDs(PageData pd,String gid) {
		String sql = "delete cw_dszfb where zfdh=?";
		Object[] obj = {
			pd.getString("gid")
		};
		return db.update(sql,obj);
	}
	
	public int doSubmit(String gid) {
		String sql="update CW_JKYWB set shzt='02' where gid"+CommonUtils.getInsql(gid);
		Object[] obj = gid.split(",");
		int i = 0;
		i = db.update(sql,obj);
		return i;
	}
	
	public int doUpdate(CW_JKYWB jksq,String flag) {
		String[] gid = jksq.getGid().split(",");
		String sql="";
		if(flag.equals("TG")){
			sql = "UPDATE CW_JKYWB SET shyj=?,shzt='08' WHERE GID=?";	
		}else if(flag.equals("TH")){
			sql = "UPDATE CW_JKYWB SET SHYJ=?,shzt='04' WHERE GID=?";
		}
		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i<gid.length; i++) {
			list.add(new Object[]{jksq.getShyj(), gid[i]});
		}
		return db.batchUpdate(sql, list).length;
	}
	
	/**
	 * 查询当前登录人的单位领导
	 * @return
	 */
	public Map<String,Object> getLoginLd(){
		String sql = " select (select guid from gx_sys_ryb where rybh = (select dwld from gx_sys_dwb where dwbh='"+LUser.getDwbh()+"')) as dwld, "
				+" (select guid from gx_sys_ryb where rybh = (select fgld from gx_sys_dwb where dwbh='"+LUser.getDwbh()+"')) as fgld from dual ";
		return db.queryForMap(sql);
	}
	
	/**
	 * 更新业务表的信息
	 * @param table
	 * @param guid
	 * @param procinstid
	 * @param val
	 * @return
	 */
	public int doUpdateByProcinstId(String table,String guid,String procinstid,String val,String shyj){
		String sql = "update "+table+" t set t.shzt='"+val+"',t.procinstid='"+procinstid+"',shyj='"+shyj+"' where t.gid='"+guid+"'";
		int r = db.update(sql);
		return r;
	}
	
	public Map<String,Object> getcwfzrSql(String jsbh){
		Map<String,Object> map=null;
		String sql = "select distinct(r.guid)as guid from ZC_SYS_JSRYB t left join gx_sys_ryb r on r.rybh=t.rybh where t.jsbh='"+jsbh+"' and rownum=1";
		map=db.queryForMap(sql);
		return map;
	}
	
	
	/**
	 * 更新业务表审核状态
	 * @param 
	 * @return
	 */
	public int doStatus(String ywid,String zt,String shyj) {
		String sql = "UPDATE CW_JKYWB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+LUser.getGuid()+"' WHERE gid"+CommonUtils.getInsql(ywid);
		Object[] obj = ywid.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 插入审核意见信息
	 * @param ddbh
	 * @return
	 */
	public int doAddShyj(OA_SHYJB shyjb) {
		String sql = "insert into OA_SHYJB (gid, rybh, procinstid, shyj, taskid, shzt,jdsj) values(sys_guid(), ?, ?, ?, ?, ?,to_char(sysdate,'yyyy-mm-dd hh24:Mi:ss'))";
		Object[] obj = new Object[]{
				LUser.getGuid(), 
				shyjb.getProcinstid(), 
				shyjb.getShyj(), 
				shyjb.getTaskid(), 
				shyjb.getShzt()
		};
		int i = db.update(sql, obj);
		return i;
	}
	/**
	 * 对私登录人 银行
	 * @param wlbh
	 * @return
	 */
	/*public List getdlrYh(String dqdlrguid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,khyh,khyhzh");
		sql.append(" from cw_jsyhzhb t where t.jsbh=? and rownum<2 ");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString(),new Object[] {dqdlrguid});
		return list;
	}*/
	public int doUpdateJK(String fjzs, String jksy, String gid) {
		String sql = " update CW_JKYWB set fjzs=?,jksy=? where gid= ? ";
		Object[] obj = {fjzs,jksy,gid} ;
		return db.update(sql, obj);
	}
	 /**
     * 凭证退回  借款 复核
     * @param fjzs
     * @param jdsy
     * @param guid
     * @return
     */
	public int JKbxSubmit(String gid) {
		String sql = " update CW_JKYWB set shzt='8' where gid= ? ";
		Object[] obj = {gid} ;
		return db.update(sql, obj);
	}
	/**
	 * 判断是否 冲过 这个 借款
	 */
	public int checksfcjk(String gid) {
		//判断 有没有冲过 这个 借款 jkdh 关联cw_rcbxzb-guid ,Cw_Gwjdfbxzb-guid,cw_clfbxzb-guid
	    // bxlx 0差旅费，1日常报销, 2,3公务接待报销,
	    String cjk = "select jkdh,bxlx,jkid,cjkje from CW_CjKB where  jkid = (select djbh from CW_JKYWB where gid = '"+gid+"')";
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    list = db.queryForList(cjk);
	    int n = list.size();
	    //相应操作 暂时没用
	    Map<String,Object> bxMap = new HashMap<String,Object>();
	    for(int i=0; i<n; i++){
	    	bxMap = list.get(i);
	    	String bxlx = (String) bxMap.get("bxlx");
	    	if("0".equals(bxlx)){
	    		
	    	}else if("1".equals(bxlx)){
	    		
	    	}else{
	    		
	    	}
	    }
	    return n;
	}
}
