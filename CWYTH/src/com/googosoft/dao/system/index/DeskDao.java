package com.googosoft.dao.system.index;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.systemset.qxgl.GlqxbDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Logger;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Repository("deskDao")
public class DeskDao extends BaseDao{
	private Logger logger = Logger.getLogger(DeskDao.class);
	@Resource(name="glqxbDao")
	public GlqxbDao glqxbDao;
	/**
	 * 日常业务
	 * @param rybh 人员编号
	 * @return
	 */
	public List getRcyw(String rybh) {
		String counts_sql = "select count(*) from zc_sys_ywb where rybh=?";
		String counts = db.queryForSingleValue(counts_sql, new Object[]{rybh});
		List list = new ArrayList();
		if("0".equals(counts)){}
		else{
			String sql = "select z.mkbh,z.url,(select t.icon from "+TnameU.MKB+" t where t.mkbh=substr(z.mkbh,0,2)) as icon,nvl(y.mkmc_new,z.mkmc) as mkmcnew "
					+ "from "+TnameU.MKB+" z left join zc_sys_ywb y on z.mkbh=y.mkbh "
					+ "where  y.rybh=? and y.sfzs='1' "
						+ "and z.mkbh in ("
							+ "select mkbh from zc_sys_czqxb where rybh=? "
							+ "union all "
							+ "select mkbh from zc_sys_jsqxb t left join zc_sys_jsryb r on r.jsbh=t.jsbh where r.rybh=? or t.jsbh='00'"
						+ ") "
					+ "order by z.mkbh";
			list = db.queryForList(sql,new Object[]{rybh,rybh,rybh});
		}
		return list;
	}
	/**
	 * 获取柱状图数据
	 * @param 
	 * @return
	 */
	public List getZztData(String sbnd) {
		
		List list = null;
//		    if(sbnd.length()>0) {
//		    	String sql = "select wm_concat(to_char(dw.mc)) mc,\r\n" + 
//		    			"       wm_concat(nvl(sr.dynsrhz, '0')) sr,\r\n" + 
//		    			"       wm_concat(nvl(jb.dynzchz, '0')) jb,\r\n" + 
//		    			"       wm_concat(nvl(xm.dynzchz, '0')) xm\r\n" + 
//		    			"  from gx_sys_dwb dw\r\n" + 
//		    			"  left join cw_srysb sr on sr.sbbm = dw.dwbh\r\n" + 
//		    			"  left join cw_jbzcysb jb on jb.sbbm = dw.dwbh\r\n" + 
//		    			"  left join cw_xmzcysb xm on xm.sbbm = dw.dwbh\r\n" + 
//		    			" where rownum < 10 and to_char(xm.sbnd,'yyyy')=? or to_char(sr.sbnd,'yyyy')=? or to_char(jb.sbnd,'yyyy')=?";
//		    	list = db.queryForList(sql,sbnd,sbnd,sbnd);
//		    }else {
//		    	String sql = "select wm_concat(to_char(dw.mc))mc ,wm_concat(nvl(sr.dynsrhz,'0'))sr,wm_concat(nvl(jb.dynzchz,'0'))jb,wm_concat(nvl(xm.dynzchz,'0'))xm from gx_sys_dwb dw\r\n" + 
//						"left join cw_srysb sr on sr.sbbm=dw.dwbh\r\n" + 
//						"left join cw_jbzcysb jb on jb.sbbm= dw.dwbh\r\n" + 
//						"left join cw_xmzcysb xm on xm.sbbm= dw.dwbh\r\n" + 
//						"where rownum <10";
//		    	list = db.queryForList(sql);
//		    }
			
			
		return list;
	}
	/**
	 * 获取待办事项
	 * @param rybh 人员编号
	 * @return
	 */
	public List getDbsx(String rybh) {
		//使用人确认MenuFlag.ZCBD_ZCDB_SYRQR虽然在审核流程里，但是不在集中审核里，所以这里要排除掉
		String sql = "select k.dbh,'【'||sqmkmc||'】等待【'||mkmc||'】' mc,tjrxm tjr,(select url from "+TnameU.MKB+" m where m.mkbh = k.mkbh) tzlj,mkbh,sqmkbh,mkmc from zc_sys_shcshb k where k.rybh=? and sfdqjd = '1' and mkbh like '"+MenuFlag.SHCD+"%' and mkbh <> '" + MenuFlag.ZCBD_ZCDB_SYRQR + "' order by dbh";
		List list = db.queryForList(sql,new Object[]{rybh});
		return list;
	}
	/**
	 * 获取通知公告
	 * @return
	 */
	public List getTzgg() {
		String sql = "select z.gid,z.title,z.fbsj,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=z.fbr) fbr,z.xx from zc_sys_xtxx z where z.sfzs='1' and rownum<=6 order by fbsj desc";
		List<Map<String,Object>> list = db.queryForList(sql);
		return list;
	}
	/**
	 * 获取个人资产
	 * @param rybh 人员编号
	 * @return
	 */
	public List getGrzc(String rybh) {
		String sql = "select yqbh,yqmc,sykh,gg,xh,to_char(rzrq,'yyyy-mm-dd') rzrq,(select mc from zc_sys_ddb where ddbh=bzxx) bzxx,decode(nvl(z.zzj,0),0,'0.00',to_char(round(z.zzj,2),'fm9999999999999990.00')) zzj from zc_zjb z where syr=? and ztbz = '" + StateManager.ZCJZ_CW_TG + "' and xz not in (select dm from gx_sys_dmb where zl='" + Constant.HXZ + "') order by rzrq";
		return db.queryForList(sql,new Object[]{rybh});
	}
	/**
	 * 获取个人资产的总金额和总数量
	 * @param rybh 人员编号
	 * @return
	 */
	public Map getGrzcHj(String rybh) {
		String sql = "select decode(nvl(sum(zzj),0),0,'0.00',to_char(round(sum(zzj),2),'fm9999999999999990.00')) zzj,count(yqbh) sl from zc_zjb where syr = ? and ztbz = '" + StateManager.ZCJZ_CW_TG + "' and xz not in (select dm from gx_sys_dmb where zl='" + Constant.HXZ + "') order by rzrq";
		Map map;
		try{
			map = db.queryForMap(sql,new Object[]{rybh});
		}
		catch(Exception e){
			e.printStackTrace();
			map = new HashMap();
		}
		if(map.isEmpty()){
			map.put("ZZJ", 0.00);
			map.put("SL", 0);
		}
		return map;
	}
	
	/**
	 * 获取进度跟踪列表
	 * @param rybh 人员编号
	 * @return
	 */
	public List getJdgz(String rybh){
		return db.queryForList("select bh,mc,lx,jd,css,rq from (" + getJdgzSql(rybh) + ") order by rq desc");
	}
	/**
	 * 获取进度跟踪的sql语句
	 * @param rybh
	 * @return
	 */
	public String getJdgzSql(String rybh){
		StringBuffer sql = new StringBuffer();
		sql.append("select ysdh bh,to_char(yqmc) mc,'资产建账' lx," + StateManager.getZtmcSql(StateManager.CZLX_ZCJZ,"ztbz") + " jd,'text-success' css,jdrq rq from zc_yshd z where jdr = '" + rybh + "' and ztbz <> '" + StateManager.ZCJZ_CW_TG + "'");
		sql.append("union all ");
		sql.append(getZcbdSql(rybh,StateManager.BDXM_XMBD,"1",StateManager.ZCBD_GK_TG));
		sql.append("union all ");
		sql.append(getZcbdSql(rybh,StateManager.BDXM_DJBD,"1",StateManager.ZCBD_CW_TG));
		sql.append("union all ");
		sql.append(getZcbdSql(rybh,StateManager.BDXM_FJZJ,"1",StateManager.ZCBD_CW_TG));
		sql.append("union all ");
		sql.append(getZcbdSql(rybh,StateManager.BDXM_FJCZ,"1","附件处置",StateManager.ZCBD_CW_TG));
		sql.append("union all ");
		sql.append("select bdbgbh bh,'部分调拨' mc,'资产变动' lx," + StateManager.getZtmcSql(StateManager.CZLX_ZCBD,"ztbz") + " jd,'text-primary' css,jdrq rq from zc_zcdbb where jdr = '" + rybh + "' and ztbz <> '" + StateManager.ZCBD_GK_TG + "' ");
		sql.append("union all ");
		sql.append(getZcbdSql(rybh,StateManager.BDXM_BFBF,"1","资产处置",StateManager.ZCBD_CW_TG));
		sql.append("union all ");
		sql.append(getZcbdSql(rybh,StateManager.BDXM_DWNDB,"5",StateManager.ZCBD_GK_TG));
		sql.append("union all ");
		sql.append(getZcbdSql(rybh,StateManager.BDXM_DWJDB,"1",StateManager.ZCBD_GK_TG));
		sql.append("union all ");
		sql.append(getZcbdSql(rybh,StateManager.BDXM_SYRBD,"5",StateManager.ZCBD_GK_TG));
//		sql.append("union all ");
//		sql.append(getZcbdSql(rybh,StateManager.BDXM_CFDDBD,"1",StateManager.ZCBD_GK_TG));
		sql.append("union all ");
		sql.append("select sqbh bh,'资产处置申请单' mc,'资产处置' lx," + StateManager.getZtmcSql(StateManager.CZLX_ZCCZ,"ztbz") + " jd,'text-warning' css,jdrq rq from zc_czsqb where jdr = '" + rybh + "' and ztbz <> '" + StateManager.ZCCZ_GLY_TG + "' ");
		sql.append("union all ");
		sql.append("select czbgbh bh,'资产处置报告单' mc,'资产处置' lx," + StateManager.getZtmcSql(StateManager.CZLX_ZCCZ,"ztbz") + " jd,'text-warning' css,jdrq rq from zc_czbgb where jdr = '" + rybh + "' and ztbz <> '" + StateManager.ZCCZ_CW_TG + "' ");
		sql.append("union all ");
		sql.append("select reportid bh,'资产维修申请单' mc,'资产维修' lx," + StateManager.getZtmcSql(StateManager.CZLX_ZCWX_WXSQ,"ztbz") + " jd,'text-repair' css,replytime rq from zc_wxsqb where replyperson = '" + rybh + "' and ztbz <> '" + StateManager.WXSQ_TG + "' ");
		sql.append("union all ");
		sql.append("select orderid bh,'资产维修报告单' mc,'资产维修' lx," + StateManager.getZtmcSql(StateManager.CZLX_ZCWX_BGDJ,"status") + " jd,'text-repair' css,repairtime rq from zc_wxbgb where status <> '" + StateManager.WXBGDJ_CW_TG + "' " + glqxbDao.getQxsql(rybh, "repaircompany", "D"));
		sql.append("union all ");
		sql.append("select sqbh bh,'维修经费追加申请单' mc,'资产维修' lx," + StateManager.getZtmcSql(StateManager.CZLX_ZCWX_JFZJ,"zjsqzt") + " jd,'text-repair' css,sqrq rq from zc_wxjfzj where sqry = '" + rybh + "' and zjsqzt <> '" + StateManager.WXJFZJ_CW_TG + "' ");
		return sql.toString();
	}
	
	private String getZcbdSql(String jdr,String bdxm,String lb,String wczt){
		return getZcbdSql(jdr,bdxm,lb,"资产变动",wczt);
	}
	
	private String getZcbdSql(String jdr,String bdxm,String lb,String lx,String wczt){
		return " select bdbgbh bh," + StateManager.getZtmcSql(StateManager.CZLX_ZCBD, "djbz", "", "4") + " mc,'" + lx + "' lx," + StateManager.getZtmcSql(StateManager.CZLX_ZCBD,"ztbz","",lb) + " jd,'text-primary' css,jdrq rq from zc_bdbgb where jdr = '" + jdr + "' and xmbz = '" + bdxm + "' and ztbz <> '" + wczt + "' ";
	}
	
	/**
	 * 获取业务流水信息
	 * @param rybh 人员编号
	 * @param month 获取的月份
	 * @return
	 */
	public List getYwls(String rybh, String month){
		Map<String,String> parInMap = new HashMap();
		parInMap.put("p_rybh", rybh);
		parInMap.put("p_month", month);
		parInMap.put("p_where", "");
		parInMap.put("p_pagestart", "0");
		parInMap.put("p_pageend", "10");
		parInMap.put("p_order", "");
		Set<Entry<String,String>> parInSet = parInMap.entrySet();
		parInMap = null;
		
		Map<String,Integer> parOutMap = new HashMap();
		parOutMap.put("my_num",OracleTypes.INTEGER);
		parOutMap.put("my_cursor", OracleTypes.CURSOR);
		Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
		parOutMap = null;
		
		try{
			Map map = db.queryForMapByProcedure("pro_getYwls", parInSet, parOutSet);
			if(map.isEmpty()){
				return new ArrayList();
			}
			else{
				List list = (List)map.get("my_cursor");
				return list;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取日常业务设置页面的展示信息
	 * @param rybh 人员编号
	 * @return
	 */
	public List getRcywEdit(String rybh) {
		String sql = "select z.mkbh,z.mkmc,nvl(y.mkmc_new,'') as mkmcnew,case nvl(y.sfzs,'1') when '0' then '0' when '1' then '1' end sfzs "
				+ " from "+TnameU.MKB+" z left join zc_sys_ywb y on z.mkbh=y.mkbh and y.rybh=? "
				+ " where z.mkbh in (" + MenuFlag.MKBH_RCYW + ") order by z.mkbh ";
		List list = db.queryForList(sql,new Object[]{rybh});
		return list;
	}
	/**
	 * 
	 * @param data
	 * @param rybh
	 * @return
	 */
	public boolean doSaveRcyw(String data, String rybh) {
		String[] sql = new String[2];
		sql[0] = "delete zc_sys_ywb where rybh=?";
		List<Object[]> list0 = new ArrayList<Object[]>();
		list0.add(new Object[]{rybh});
		sql[1] = "insert into zc_sys_ywb(rybh,mkbh,mkmc_new,sfzs) values(?,?,?,?)";
		List<Object[]> list = new ArrayList<Object[]>();
		if(Validate.noNull(data)){
			String[] datas = data.split(",");
			for (int j = 0; j < datas.length; j++) {
				String[] ss = datas[j].split("##");
				Object[] obj = new Object[]{rybh,ss[0],ss[1],ss[2]};
				list.add(obj);
			}
		}
		try{
			return db.batchUpdate(sql, list0, list);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取生命周期中的模块信息
	 * @param mkbh
	 * @return
	 */
	public List getSmzqMenu(String mkbh){
		String sql = "select mkbh, mkmc, url, xh, qxbz, icon, xtbz, czqx, ywsm, tplx  from "+TnameU.MKB+" M where mkbh='"+mkbh+"' and (MKBH IN (SELECT MKBH  FROM "+TnameU.JSCZQXB+"  WHERE JSBH = '00')" +
				" OR MKBH IN (select mkbh from "+TnameU.getTname("czqx")+" where rybh = '"+LUser.getRybh()+"' and xtbz = '01'))";
		return db.queryForList(sql);
	}
	/**
	 * 获取某个模块编号下的业务说明
	 * @param mkbh 模块编号
	 * @return
	 */
	public Map getYwsm(String mkbh) {
		String sql = "select ywsm,jdr,jdrq from zc_ywsm where mkbh = ?";
		try {
			Connection conn = db.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,mkbh);
			ResultSet rs = ps.executeQuery();
			Blob blob = null;
			String jdr = "";
			String jdrq = "";
			Map<String,Object> map = new HashMap<String,Object>();
			while(rs.next()){
				map.put("YWSM", CommonUtils.BlobToString(rs.getBlob("YWSM")));
				map.put("JDR", WindowUtil.getRyxx(rs.getString("JDR")));
				map.put("JDRQ", rs.getDate("JDRQ"));
			}
			conn.close();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	/**
//	 * 保存业务说明  没有找到哪里用这个方法，先注释掉
//	 * @param content 内容
//	 * @param rybh 人员编号
//	 * @return
//	 * @throws SQLException 
//	 * @throws UnsupportedEncodingException 
//	 */
//	public boolean doSaveYwsm(String content, String rybh){
//		Connection conn;
//		try {
//			conn = db.getDataSource().getConnection();
//			String sql ="insert into zc_ywsm(id,mkbh,mkmc,ywsm,jdr,jdrq) values(sys_guid(),?,?,?,?,?)";
//			PreparedStatement st = null;
//			Date date = new Date();
//			byte[] b = content.getBytes("utf-8");
//			Blob blob = new SerialBlob(b);
//			st = conn.prepareStatement(sql);
//			st.setString(1, "000000");
//			st.setString(2, "测试模块名称");
//			st.setBinaryStream(3, blob.getBinaryStream(),b.length);
//			st.setString(4,rybh);
//			st.setObject(5, new java.sql.Date(date.getTime()));
//			st.execute();
//			conn.close();
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		} 
//	}
	/**
	 * 检查此mkbh下是否存在业务说明信息
	 * @param mkbh 模块编号
	 * @return
	 */
	public String countYwsm(String mkbh) {
		String sql = "select count(mkbh) from zc_ywsm where mkbh=?";
		String count = db.queryForSingleValue(sql, new Object[]{mkbh});
		return count;
	}

	/**
	 * 获取未读通知数量
	 */
	public String getWdsl(String rybh) {
		String sql = "select count(1) sl from zc_sys_xtxx where sfzs='1' and gid not in (select bh from zc_xtxx_view where rybh = ?) ";
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	/**
	 * 获取未读通知公告信息
	 */
	public List getWdxx(String rybh){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.gid,a.title,to_char(fbsj,'yyyy-mm-dd hh24:mi') as fbsj,a.fbr,(sysdate-fbsj) as fbts,");
		sql.append("trunc(sysdate-fbsj) ts,trunc((sysdate-fbsj)*24)xs,trunc((sysdate-fbsj)*1440) fz ");
		sql.append("from zc_sys_xtxx a where a.sfzs='1' and gid not in (select bh from zc_xtxx_view   where rybh = ?) order by a.fbsj desc");
		return db.queryForList(sql.toString(),rybh);
	}
	/**
	 * 获取此人的业务审核数量
	 * @param rybh 人员编号
	 * @return
	 */
	public String getYwsh(String rybh) {
		String sql = "select count(*) from zc_sys_shcshb where rybh = ? and sfdqjd = '1' and (substr(mkbh,1,2) = '" + MenuFlag.SHCD + "')";//如果管理员审核与管理员建账合并到一个模块里的话，管理员审核菜单就不在05下边了，去掉了，现在都在05下
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	/**
	 * 根据人员编号获取当前人员有无审核权限
	 * @param rybh
	 * @return
	 */
	public String getShqx(String rybh){
		String sql = "select (case count(1) when 0 then 0 else 1 end) from (select mkbh from zc_sys_mkb where qxbz = '1' and mklx = '"+Constant.MKLX_SHQX+"') m inner join (select mkbh from zc_sys_jsqxb where jsbh in (select jsbh from zc_sys_jsryb where rybh = ?) union all select mkbh from zc_sys_czqxb where rybh = ?) q on m.mkbh = q.mkbh";
		return db.queryForSingleValue(sql, new Object[]{rybh,rybh});
	}
	/**
	 * 获取业务审核列表信息
	 */
	public List getShxx(String guid) {
//		String sql = "select k.dbh,'【'||sqmkmc||'】等待【'||mkmc||'】' mc,tjrxm tjr,(select url from "+TnameU.MKB+" m where m.mkbh = k.mkbh) tzlj,mkbh,sqmkbh,mkmc from zc_sys_shcshb k where k.rybh=? and sfdqjd = '1' and (substr(mkbh,1,2) = '" + MenuFlag.SHCD + "') order by dbh";
//		List list = db.queryForList(sql,new Object[]{rybh});
//		return list;
		String sql = " select b.task_def_key_,a.shzt,a.guid as guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_ccsqspb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '130202' where b.assignee_ = ? and b.task_def_key_ <> 'sqr'"
				+" union "
				+" select b.task_def_key_,a.shzt,a.guid as guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdywsqspb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.rybh left join zc_sys_mkb d on d.mkbh = '130102' where b.assignee_ = ? and b.task_def_key_ <> 'sqr'"
				+" union "
				+" select b.task_def_key_,a.shzt,a.guid as guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_rcbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_"
				+ " left join gx_sys_ryb c on a.bxr = c.guid left join zc_sys_mkb d on d.mkbh = '110203' where b.assignee_ = ? and b.task_def_key_ <> 'sqr'"
				+" union "
				+" select b.task_def_key_,a.shzt,a.guid as guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_clfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '110302' where b.assignee_ = ? and b.task_def_key_ <> 'sqr' "
				+" union "
				+" select b.task_def_key_,a.shzt,a.guid as guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.czr = c.guid left join zc_sys_mkb d on d.mkbh = '111102' where b.assignee_ = ? and b.task_def_key_ <> 'sqr'"
				+" union "
				+" select b.task_def_key_,a.shzt,a.gid as guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc, mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from CW_JKYWB a left join  act_ru_task b on a.procinstid = b.proc_inst_id_  "
				+ "  left join gx_sys_ryb c on a.jkr = c.guid left join zc_sys_mkb d on d.mkbh = '111702' where b.assignee_ = ? and b.task_def_key_ <> 'sqr' ";
		Object[] obj = {guid,guid,guid,guid,guid,guid};
		return db.queryForList(sql,obj);
	}
	/**
	 * 首页搜索
	 * @param keyword
	 * @return
	 */
	public int getAssetsNum(String keyword){
		String sql="select count(*) from zc_zjb where ztbz = '" + StateManager.ZCJZ_CW_TG + "' and xz not in (select dm from gx_sys_dmb where zl = '" + Constant.HXZ + "') and yqmc like '%"+keyword+"%'";
			return db.queryForObject(sql,Integer.class);
	}
	public String getSpls(String rybh) {
		String sql = "select count(*) from zc_sys_shjlb where shr = ? and shmkbh is not null";
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	public String getBhls(String rybh) {
		String sql = "select count(*) from zc_sys_shjlb where dbh in (select dbh from zc_sys_shcshb where tjr = ?) and shbz like '%退回%' and sxsj is null";
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	
	/**
	 * 获取业务草稿数量
	 * @param rybh 当前登录人的id
	 * @return
	 */
	public String getCgsl(String rybh) {
		Map<String,String> parInMap = new HashMap();
		parInMap.put("p_rybh", rybh);
		Set<Entry<String,String>> parInSet = parInMap.entrySet();
		parInMap = null;
		
		Map<String,Integer> parOutMap = new HashMap();
		parOutMap.put("zsl",OracleTypes.INTEGER);
		parOutMap.put("my_cursor", OracleTypes.CURSOR);
		Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
		parOutMap = null;
		
		try{
			Map map = db.queryForMapByProcedure("proc_cg", parInSet, parOutSet);
			if(map.isEmpty()){
				return "";
			}
			else{
				return Validate.isNullToDefaultString(map.get("zsl"),"");
			}
		}catch(SQLException e){
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 判断当前用户角色
	 * @return flag 如果用户属于归口、财务、管理员角色，返回true,否则返回false。
	 */
	public boolean ryjspd(){
		String sql = "SELECT count(1) FROM %SJSRYB where jsbh in('"+MenuFlag.ZCGLY+"','"+MenuFlag.GKGLY+"','"+MenuFlag.CWGLY+"') and rybh=?";
		sql = String.format(sql, SystemSet.sysBz);
		int cnt = Integer.parseInt(db.queryForSingleValue(sql, new Object[]{LUser.getRybh()}));
		if(cnt>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 获取建账数量
	 * @param rybh 当前登录人的id
	 * @param sjfw 数据范围，例如："yyyy"：获取本年  "yyyy-mm"：获取本月
	 */
	public String getjzsl(String rybh,String sjfw) {
		boolean flag = ryjspd();//如果是用户不属于归口、财务、管理员角色，取自己的数据。如果是取管理权限下的数据。
		String sql = "";
		if(flag){
			sql = "select count(*) from zc_yshd where 1 = 1 " 
					+ glqxbDao.getQxsql(rybh, "shgdw", "D") 
					+ glqxbDao.getQxsql(rybh, "flh", "F") 
					+ "and ztbz = '" + StateManager.ZCJZ_CW_TG + "' "
					+ "and to_char(jdrq,?) = to_char(sysdate,?)";
			return db.queryForSingleValue(sql,new Object[]{sjfw,sjfw});
		}else{
			sql = "select count(*) from zc_yshd where 1 = 1 " 
					+ "and jdr =? "
					+ "and ztbz = '" + StateManager.ZCJZ_CW_TG + "' "
					+ "and to_char(jdrq,?) = to_char(sysdate,?)";
			return db.queryForSingleValue(sql,new Object[]{rybh,sjfw,sjfw});
		}
	}

	/**
	 * 获取变动数量
	 * @param rybh 当前登录人的id
	 * @param sjfw 数据范围，例如："yyyy"：获取本年  "yyyy-mm"：获取本月
	 */
	public String getbdsl(String rybh,String sjfw) {
		boolean flag = ryjspd();//如果是用户不属于归口、财务、管理员角色，取自己的数据。如果是取管理权限下的数据。
		if(flag){
			String sql = "select count(*) from zc_bdbgb where 1 = 1 "
					+ glqxbDao.getQxsql(rybh, "DWBH", "D")
					+" and ("
						+ "(djbz in ('" + StateManager.BDXM_DJBD + "','" + StateManager.BDXM_FJZJ + "','" + StateManager.BDXM_FJCZ + "','" + StateManager.BDXM_BFBF + "') and ztbz = '" + StateManager.ZCBD_CW_TG + "' and to_char(shrq,?) = to_char(sysdate,?)) "
						+ "or (djbz = '" + StateManager.BDXM_DWNDB + "' and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(glyshrq,?) = to_char(sysdate,?)) "
						+ "or (djbz in ('" + StateManager.BDXM_XMBD + "','" + StateManager.BDXM_DWJDB + "','" + StateManager.BDXM_CFDDBD + "') and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(gkrq,?)=to_char(sysdate,?)) "
						+ "or (djbz = '" + StateManager.BDXM_SYRBD + "' and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(lyrrq,?) = to_char(sysdate,?)) "
					+ ")";
			return db.queryForSingleValue(sql,new Object[]{sjfw,sjfw,sjfw,sjfw,sjfw,sjfw,sjfw,sjfw});
		}else{
			String sql = "select count(*) from zc_bdbgb where 1 = 1 "
					+" and ("
						+ "(djbz in ('" + StateManager.BDXM_DJBD + "','" + StateManager.BDXM_FJZJ + "','" + StateManager.BDXM_FJCZ + "','" + StateManager.BDXM_BFBF + "') and ztbz = '" + StateManager.ZCBD_CW_TG + "' and to_char(shrq,?) = to_char(sysdate,?)) "
						+ "or (djbz = '" + StateManager.BDXM_DWNDB + "' and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(glyshrq,?) = to_char(sysdate,?)) "
						+ "or (djbz in ('" + StateManager.BDXM_XMBD + "','" + StateManager.BDXM_DWJDB + "','" + StateManager.BDXM_CFDDBD + "') and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(gkrq,?)=to_char(sysdate,?)) "
						+ "or (djbz = '" + StateManager.BDXM_SYRBD + "' and ztbz = '" + StateManager.ZCBD_GK_TG + "' and to_char(lyrrq,?) = to_char(sysdate,?)) "
					+ ")  and jdr = '"+rybh+"'";
			return db.queryForSingleValue(sql,new Object[]{sjfw,sjfw,sjfw,sjfw,sjfw,sjfw,sjfw,sjfw});
		}
	}

	public String getczsl(String rybh,String sjfw) {
		boolean flag = ryjspd();//如果是用户不属于归口、财务、管理员角色，取自己的数据。如果是取管理权限下的数据。
		if(flag){
			String sql = "select count(*) from zc_czbgb where 1 = 1 and ztbz =? "
					+ glqxbDao.getQxsql(rybh, "DWBH", "D")
					+ "and to_char(shrq,?) = to_char(sysdate,?)  and czbgbh in (select distinct czbgbh from zc_czsqmxb where czbgbh is not null) and jdr = '"+LUser.getRybh()+"'";
			return db.queryForSingleValue(sql,new Object[]{StateManager.ZCCZ_CW_TG,sjfw,sjfw});
		}else{
			String sql = "select count(*) from zc_czbgb where 1 = 1 and ztbz = ?" 
					+" and jdr = ?"
					+" and to_char(shrq,?) = to_char(sysdate,?)  and czbgbh in (select distinct czbgbh from zc_czsqmxb where czbgbh is not null) and jdr = '"+LUser.getRybh()+"'";
			return db.queryForSingleValue(sql,new Object[]{StateManager.ZCCZ_CW_TG,LUser.getRybh(),sjfw,sjfw});
		}
	}
	
	public Map getGlyjzsl(String rybh) {
		String sql = "select (select count(*) from zc_yshd t where 1 = 1 and jzrlx = '" + StateManager.JZRLX_GLY + "' and ztbz = '" + StateManager.ZCJZ_TJ + "' " + glqxbDao.getQxsql(rybh, "shgdw", "D") + glqxbDao.getQxsql(rybh, "flh", "F") +") as sl, url, mkbh, mkmc from " + SystemSet.sysBz + "mkb m where m.mkbh = '" + MenuFlag.ZCJZ_GLY +"' "
				+ "  and m.mkbh in (select mkbh from ( select j.rybh,q.mkbh from " + TnameU.JSRYB + " j left join " + TnameU.JSCZQXB + " q on q.jsbh=j.jsbh union all  select t.rybh,t.mkbh from " + TnameU.CZQXB + " t  ) where rybh='"+ rybh +"')";
		List list = db.queryForList(sql);
		Map map = new HashMap();
		if(list == null || list.size() == 0)
			map.put("SL", "0");
		else
			map = (Map)list.get(0);
		return map;
	}
	
	public Map getSyrqrsl(String rybh) {
		String sql = "select (select count(*) num from zc_bdbgb b inner join (select dbh,sqmkbh from zc_sys_shcshb where rybh = '" + rybh + "' and mkbh = '" + MenuFlag.ZCBD_ZCDB_SYRQR +"' and sfdqjd = '1') s on s.dbh = b.bdbgbh) sl,mkbh,mkmc,url from zc_sys_mkb where mkbh = '" + MenuFlag.ZCBD_ZCDB_SYRQR +"' "
				+ " and mkbh in (select mkbh from (select j.rybh,q.mkbh from " + TnameU.JSRYB + " j left join " + TnameU.JSCZQXB + " q on q.jsbh=j.jsbh union all select t.rybh,t.mkbh from " + TnameU.CZQXB + " t) where rybh='"+ rybh +"')";
		List list = db.queryForList(sql);
		Map map = new HashMap();
		if(list == null || list.size() == 0)
			map.put("SL", "0");
		else
			map = (Map)list.get(0);
		return map;
	}
	/**
	 * 获取可以使用的流水号
	 * @return
	 */
	public List getZclsh(){
		List parInList = new ArrayList();
		parInList.add("nfcd");
		parInList.add("nfqd");
		parInList.add("substr(yqbh,5,4)");
		parInList.add("zc_zjb");
		parInList.add("and substr(yqbh,1,4) = to_char(sysdate,'yyyy')");
		parInList.add(4);
		
		try{
			return db.queryForListByProcedure("proc_getlsh", parInList);
		}catch(SQLException e){
			e.printStackTrace();
			return new ArrayList();
		}
	}
	
	public String getSfgk(String rybh){
		return db.queryForSingleValue("select (case nvl((select count(*) from zc_sys_jsryb where rybh = ? and jsbh = ?),0) when 0 then '0' else '1' end) from dual", new Object[]{rybh,Constant.GKJS});
	}
	
	public String getInfoByCount(PageData pd){
		String tj = pd.getString("cxtj");
		String dbh = pd.getString("dbh");
		String sql = "";
		if("tmdy".equals(tj)){
			sql = "select count(*) from zc_zjb z where yqbh" + ToSqlUtil.getInsql(dbh) + " and ztbz='99' and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') " + glqxbDao.getQxsql(LUser.getRybh(), "sydw", "D") + glqxbDao.getQxsql(LUser.getRybh(), "flh", "F") ;
			return db.queryForSingleValue(sql, dbh.split(","));
		}
		else if("zccx".equals(tj)){
			sql = "select count(*) from zc_zjb z where yqbh = ? and ztbz='99' and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') " + glqxbDao.getQxsql(LUser.getRybh(), "sydw", "D") + glqxbDao.getQxsql(LUser.getRybh(), "flh", "F") ;
			return db.queryForSingleValue(sql, new Object[]{dbh});
		}
		else if("ysdcx".equals(tj)){
			sql = "select count(*) from zc_yshd where ysdh = ? and ztbz='99' " + glqxbDao.getQxsql(LUser.getRybh(), "shgdw", "D") + glqxbDao.getQxsql(LUser.getRybh(), "flh", "F");
			return db.queryForSingleValue(sql, new Object[]{dbh});
		}
		else if("bddcx".equals(tj)){
			sql = "select count(*) from zc_bdbgb where bdbgbh = ? and ztbz='99' " + glqxbDao.getQxsql(LUser.getRybh(), "dwbh", "D");
			return db.queryForSingleValue(sql, new Object[]{dbh});
		}
		else if("czdcx".equals(tj)){
			sql = "select count(*) from zc_czbgb c where czbgbh = ? and ztbz='99' " + glqxbDao.getQxsql(LUser.getRybh(), "dwbh", "D");
			return db.queryForSingleValue(sql, new Object[]{dbh});
		}
		else if("txlcx".equals(tj)){
			sql = "select count(*) from zc_txl where nvl(zt,'1') = '1' and (rybh like ? or xm like ?)";
			return db.queryForSingleValue(sql, new Object[]{"%" + dbh + "%","%" + dbh + "%"});
		}
		else{
			return "";
		}
	}
	/**
	 * 根据人员编号查询角色
	 * @param rybh
	 * @return
	 */
	public String getJsbh(String rybh){
		StringBuffer sql=new StringBuffer();
		sql.append(" select wm_concat(d.jsbh)jsbh from ZC_SYS_JSRYB D ");
		sql.append(" left join ZC_SYS_JSB Z ");
		sql.append(" on D.JSBH = Z.JSBH ");
		sql.append("  left join GX_SYS_RYB G ");
		sql.append("   on D.RYBH = G.RYBH ");
		sql.append("  left join GX_SYS_DWB DW ");
		sql.append(" on G.DWBH = DW.DWBH ");
		sql.append(" where 1 = 1 and  D.ryBH =?");
		return db.queryForSingleValue(sql+"", new Object[]{rybh});
	}
	//获取培养层次数据
	public List<Map<String, Object>> getPyccfx() {
		StringBuffer sql = new StringBuffer();
		sql.append("select mc,count(mc) as rs  from ( SELECT MC FROM GX_SYS_DMB WHERE ZL = '13' AND DM =  (select xslb  from CW_XJXXB b  left join CW_XSXXB a on a.xh=b.xh))  group by mc");
		List<Map<String, Object>> map = db.queryForList(sql.toString());
		return map;
	}
	/**
	 * 账套信息
	 * @return
	 */
	public List<Map<String, Object>> getZtxx(){
		String sql = "select * from cw_ztxxb";
		return db.queryForList(sql);
	}
	public List getTzxx(String guid) {
		// TODO Auto-generated method stub
		return db.queryForList("select t.guid,t.fbr,to_char(t.fbsj,'yyyy-mm-dd') as fbsj,t.jsr,t.jssj,t.sjid,t.xxnr,t.xxzt,"
				+ "(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.guid=t.fbr) as fbrxm "
				+ "from CW_XTXX t where nvl(t.xxzt,'0')='0' and t.jsr=?",
				new Object[]{guid} );
	}
	
}
