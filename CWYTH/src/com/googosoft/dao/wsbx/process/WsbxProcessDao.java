package com.googosoft.dao.wsbx.process;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.util.Validate;

/**
 * 网上报销流程dao
 * 
 * @author googosoft
 * 
 */
@Repository("ProcessDao")
public class WsbxProcessDao extends BaseDao { 

	/**
	 * 根据guid查询日常报销额项目大类
	 * 
	 * @param guid
	 * @return
	 */
	public String getXmTypeByGuidAndRcbx(String guid) {
		String sql = "SELECT DISTINCT X.XMDL AS XMDL FROM  CW_RCBXMXB MX LEFT JOIN CW_XMJBXXB X ON X.GUID=MX.XMGUID WHERE MX.ZBID=? AND ROWNUM=1";
		return Validate.isNullToDefaultString(
				db.queryForSingleValue(sql, new Object[] { guid }), "");
	}
	/**
	 * 根据guid查询部门是否学院
	 * 
	 * @param guid
	 * @return
	 */
	public String getSfxy(String guid) {
		String sql = "SELECT sfxy from gx_sys_dwb where dwbh = (select x.bmbh FROM  CW_RCBXMXB MX LEFT JOIN CW_XMJBXXB X ON X.GUID=MX.XMGUID where MX.ZBID=? and rownum<2)";
		return Validate.isNullToDefaultString(
				db.queryForSingleValue(sql, new Object[] { guid }), "");
	}
	/**
	 * 根据guid查询个人收入额项目大类
	 * 
	 * @param guid
	 * @return
	 */
	public String getXmTypeByGuidAndGrsr(String guid) {
		String sql = "SELECT DISTINCT X.XMDL AS XMDL FROM  CW_GRSRZB T LEFT JOIN CW_XMJBXXB X ON X.GUID=T.XMBH WHERE T.GUID=? AND ROWNUM=1";
		return Validate.isNullToDefaultString(
				db.queryForSingleValue(sql, new Object[] { guid }), "");
	}
	/**
	 * 根据guid查询日常报销额项目大类
	 * 
	 * @param guid
	 * @return
	 */
	public String getXmTypeByGuidAndClbx(String guid) {
		String sql = "SELECT DISTINCT X.XMDL AS XMDL FROM CW_CLFBXZB T LEFT JOIN CW_CCSQSPB_XM XM ON XM.CCSQID=T.CCYWGUID LEFT JOIN CW_XMJBXXB X ON X.GUID=XM.JFBH " +
				"WHERE T.GUID=? AND ROWNUM=1";
		return Validate.isNullToDefaultString(
				db.queryForSingleValue(sql, new Object[] { guid }), "");
	}

	/**
	 * 更新业务表
	 * 
	 * @param shzt
	 * @param tableName
	 * @param procinstid
	 * @param shyj
	 * @param guid
	 * @return
	 */
	public int updateTableByShzt(String shzt, String tableName,
			String procinstid, String shyj, String guid,String task) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE ").append(tableName).append(" T");
		sql.append(" SET T.SHZT=?,SHYJ=?,PROCINSTID=?,checkshzt=?");
		sql.append(" WHERE T.GUID=?");
		int m = 0;
		m = db.update(sql.toString(), new Object[] {shzt, shyj,
				procinstid, task,guid });
		if(shzt.equals("8")){
			db.update("update CW_JKYWB t set SYJE=SYJE-(select c.cjkje from CW_CJKB c where c.jkid=t.djbh and c.jkdh=?) "
					+ "where t.djbh in (select c.jkid from CW_CJKB c left join " + tableName + " b on b.guid=c.jkdh where b.guid=? and b.shzt='8')",
					new Object[]{guid,guid});
		}
		
		return m;
	}
	public int update(String procinstid) {
		//政府采购剩余金额
		String zfcgsyjeSql = "select zfcgsyje from CW_RCBXMXB where zbid = (select guid from CW_RCBXZB where PROCINSTID ='"+procinstid+"')";
		String zfcgsyje = db.queryForSingleValue(zfcgsyjeSql);
		String zfcgjeSql = "select zfcgje from CW_RCBXMXB where zbid = (select guid from CW_RCBXZB where PROCINSTID ='"+procinstid+"')";
		String zfcgje = db.queryForSingleValue(zfcgjeSql);
		BigDecimal b1 = new BigDecimal(Validate.isNullToDefault(zfcgsyje, 0).toString());   
		BigDecimal b2 = new BigDecimal(Validate.isNullToDefault(zfcgje, 0).toString());   
		String  zfsyje= b1.subtract(b2).toString();   
		//非政府采购剩余金额
		String fzfcgsyjeSql = "select fzfcgsyje from CW_RCBXMXB where zbid = (select guid from CW_RCBXZB where PROCINSTID ='"+procinstid+"')";
		String fzfcgsyje = db.queryForSingleValue(fzfcgsyjeSql);
		String fzfcgjeSql = "select fzfcgje from CW_RCBXMXB where zbid = (select guid from CW_RCBXZB where PROCINSTID ='"+procinstid+"')";
		String fzfcgje = db.queryForSingleValue(fzfcgjeSql);
		BigDecimal b3 = new BigDecimal(Validate.isNullToDefault(fzfcgsyje, 0).toString());   
		BigDecimal b4 = new BigDecimal(Validate.isNullToDefault(fzfcgje, 0).toString());   
		String  fzfsyje= b3.subtract(b4).toString();   
		String  sql = "update CW_XMJBXXB set zfcgsyje='"+zfsyje+"',fzfcgsyje='"+fzfsyje+"' where guid = (select DISTINCT xmguid from CW_RCBXMXB where zbid = (select guid from CW_RCBXZB where PROCINSTID ='"+procinstid+"') )";
		int m = 0;
		m = db.update(sql);
		return m;
	}

	/**
	 * 日常报销信息
	 * 
	 * @param guid
	 * @return
	 */
	public Map getMapInfoByRcbxAndGuid(String guid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM CW_RCBXZB WHERE GUID=?");
		String sqlProc_inst = "update cw_rcbxzb set procinstid = '' where guid = ?";
		db.update(sqlProc_inst,new Object[] {guid}); //退回单据重新编辑，项目换了，流程重新提交需要将流程字段置空，重新启动流程
		Map map = db.queryForMap(sql.toString(), new Object[] { guid });
		return map;
	}

	/**
	 * 差旅费报销信息
	 * 
	 * @param guid
	 * @return
	 */
	public Map getMapInfoByClfbxAndGuid(String guid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM CW_CLFBXZB WHERE GUID=?");
		Map map = db.queryForMap(sql.toString(), new Object[] { guid });
		return map;
	}

	/**
	 * 公务接待费报销信息
	 * 
	 * @param guid
	 * @return
	 */
	public Map getMapInfoByGwjdfAndGuid(String guid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM CW_GWJDFBXZB WHERE GUID=?");
		Map map = db.queryForMap(sql.toString(), new Object[] { guid });
		return map;
	}
	/**
	 * 个人收入信息
	 * 
	 * @param guid
	 * @return
	 */
	public Map getMapInfoByGrsrfAndGuid(String guid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T.*,T.FFZJE AS  FROM CW_GRSRZB T WHERE T.GUID=?");
		Map map = db.queryForMap(sql.toString(), new Object[] { guid });
		return map;
	}

	/**
	 * 查询角色
	 * 
	 * @param jsbh
	 * @return
	 */
	public String getJsryId(String jsbh) {
		String sql = "SELECT DISTINCT(R.GUID)AS GUID FROM ZC_SYS_JSRYB T LEFT JOIN GX_SYS_RYB R ON R.RYBH=T.RYBH WHERE T.JSBH=? AND ROWNUM=1";
		return Validate.isNullToDefaultString(
				db.queryForSingleValue(sql, new Object[] { jsbh }), "");
	}
	
	/**
	 * 查询财务预审
	 * 
	 * @param jsbh
	 * @return
	 */
	public List getCwysList(String jsbh) {
		String sql = "SELECT DISTINCT(R.GUID)AS GUID FROM ZC_SYS_JSRYB T LEFT JOIN GX_SYS_RYB R ON R.RYBH=T.RYBH WHERE T.JSBH=? and t.rybh<>?";
		List list = db.queryForList(sql, new Object[] { jsbh,LUser.getRybh()});
		List people = new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for(int i=0,len=list.size();i<len;i++){
				Map map = (Map)list.get(i);
				people.add(Validate.isNullToDefault(map.get("GUID"),""));
			}
		}
		return people;
	}
	
	/**
	 * 查询单位领导
	 * @return
	 */
	public Map getDwldSql(String guid){
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" r1.guid as dwld,");
		sql.append(" r2.guid as dwfgld,");
		sql.append(" r3.guid as SJ");
		sql.append(" from gx_sys_dwb d");
		sql.append(" left join gx_sys_ryb r1 on r1.rybh=d.DWLD");
		sql.append(" left join gx_sys_ryb r2 on r2.rybh=d.FGLD");
		sql.append(" left join gx_sys_ryb r3 on r3.rybh=d.SJ");
//		sql.append(" where d.dwbh='"+LUser.getDwbh()+"'");
		sql.append(" where d.dwbh=(select dwbh from gx_sys_ryb where guid =(select bxr from cw_rcbxzb where guid = ?))");
		return db.queryForMap(sql.toString(),new Object[] {guid});
	}
	
	
	/**
	 * 查询项目负责人
	 * @param guid
	 * @return
	 */
	public String getXmfzrByRcbxAndGuid(String guid){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT GUID FROM GX_SYS_RYB WHERE RYBH IN(");
		sql.append(" SELECT DISTINCT X.FZR FROM  CW_RCBXMXB MX");
		sql.append(" LEFT JOIN CW_XMJBXXB X ON X.GUID=MX.XMGUID WHERE MX.ZBID=? AND X.FZR IS NOT NULL) AND ROWNUM=1");
	
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql.toString(),new Object[]{guid}), "");
	}
	
	/**
	 * 查询项目负责人姓名
	 * @param guid
	 * @return
	 */
	public String getXmfzrXmByRcbxAndGuid(String guid){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT XM FROM GX_SYS_RYB WHERE RYBH IN(");
		sql.append(" SELECT DISTINCT X.FZR FROM  CW_RCBXMXB MX");
		sql.append(" LEFT JOIN CW_XMJBXXB X ON X.GUID=MX.XMGUID WHERE MX.ZBID=? AND X.FZR IS NOT NULL) AND ROWNUM=1");
	
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql.toString(),new Object[]{guid}), "");
	}
	
	/**
	 * 查询某些固定部门的单位领导
	 * @param dwbh
	 * @return
	 */
	public String getDwldByGdbm(String dwbh){
		String sql = "select guid from gx_sys_ryb where rybh in(select dwld from gx_sys_dwb where dwbh=?)";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql, new Object[]{dwbh}),"");
	}
	
	/**
	 * 查询某些固定部门的单位分管领导
	 * @param dwbh
	 * @return
	 */
	public String getFgldByGdbm(String dwbh){
		String sql = "select guid from gx_sys_ryb where rybh in(select fgld from gx_sys_dwb where dwbh=?)";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql, new Object[]{dwbh}),"");
	}
	
	/**
	 * 查询校长
	 * @return
	 */
	public String getXz(){
		String sql = "select xzxm from CW_XXXXB t";
		String xzxm = Validate.isNullToDefaultString(db.queryForSingleValue(sql),"");
		if(xzxm.contains(")")&&xzxm.length()>2){
			xzxm = xzxm.substring(1,xzxm.indexOf(")"));
		}
		String sqls = "select r.guid as guid from gx_sys_ryb r where r.rybh='"+xzxm+"'";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sqls),"");
	}
	
	/**
	 * 查询费用名称
	 * @param guid
	 * @return
	 */
	public int getFymcByRcbxAndGuid(String guid){
		String sql = "SELECT DISTINCT FYMC AS FYMC FROM CW_RCBXMXB WHERE ZBID=?";
		List list = db.queryForList(sql,new Object[]{guid});
		if(list==null||list.size()==0){
			return 0;
		}
		int m = 0;
		for(int i=0,len=list.size();i<len;i++){
			Map map = (Map)list.get(i);
			String fybh = Validate.isNullToDefaultString(map.get("FYMC"), "");
			String search = "select count(0) from CW_JJKMPZB where kmbh='"+fybh+"' ";
			if(m==0){
				m = db.queryForInt(search);
			}
		}
		return m;
	}
	
	/**
	 * 获取流程节点
	 * @param procinstid
	 * @return
	 */
	public String getTaskNodeId(String procinstid) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ FROM (SELECT  TASK_DEF_KEY_ from ACT_RU_TASK t  ");
		buffer.append(" where PROC_INST_ID_ ='" + procinstid + "' ORDER BY CREATE_TIME_ DESC) WHERE ROWNUM = 1  ");
//		buffer.append(" SELECT  distinct TASK_DEF_KEY_ from ACT_RU_TASK t  ");
//		buffer.append(" where PROC_INST_ID_ ='" + procinstid + "' ");
		String nodeId = null;
		try {
			nodeId = db.queryForSingleValue(buffer.toString()) + "";
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
		}
		buffer=null;
		return nodeId;
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
	 * 判断会签是否结束
	 * @param procinstid
	 * @param taskDefKey
	 * @return
	 */
	public int checkHq(String procinstid,String taskDefKey){
		String sql = "select count(0) from ACT_RU_TASK where PROC_INST_ID_=? and TASK_DEF_KEY_=?";
		return db.queryForInt(sql,new Object[]{procinstid,taskDefKey});
	}
	
	/**
	 * 根据人员编号或者id查询姓名
	 * @param param
	 * @return
	 */
	public Map getPeopleXm(String param){
		String sql = "select xm from gx_sys_ryb where (guid=? or rybh=? or xm=?) and rownum=1";
		return db.queryForMap(sql,new Object[]{param,param,param});
	}
	
	/**
	 * 部门会签人员的姓名
	 * @return
	 */
	public String getHqshr(){
		String sql = "select wm_concat(distinct to_char(xm))xm from gx_sys_ryb t left join gx_sys_dwb d on d.dwld=t.rybh or d.sj=t.rybh where d.dwbh=?";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{LUser.getDwbh()}),"");
	}
	
	/**
	 * 部门领导人员的姓名
	 * @return
	 */
	public String getDwldXm(){
		String sql = "select wm_concat(distinct to_char(xm))xm from gx_sys_ryb t left join gx_sys_dwb d on d.dwld=t.rybh where d.dwbh=?";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{LUser.getDwbh()}),"");
	}
	
	/**
	 * 查询本单位的报账员
	 * @return
	 */
	public String getBmbzy(){
		String sql = "select wm_concat(distinct to_char(r.xm))xm from zc_sys_jsryb t left join gx_sys_ryb r on r.rybh=t.rybh where r.dwbh=? and t.jsbh=?";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{LUser.getDwbh(),"13"}),"");
	}
	
	/**
	 * 查询财务预审会签人员姓名
	 * 
	 * @param jsbh
	 * @return
	 */
	public String getCwysWmConcat(String jsbh) {
		String sql = "SELECT wm_concat(DISTINCT to_char(R.xm))AS xm FROM ZC_SYS_JSRYB T LEFT JOIN GX_SYS_RYB R ON R.RYBH=T.RYBH WHERE T.JSBH=? and t.rybh<>?";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{jsbh,LUser.getRybh()}),"");
	}
	
	/**
	 * 判断人员是不是该角色
	 * @param jsbh
	 * @return
	 */
	public boolean checkLoginIsJs(String rybh,String jsbh){
		String sql = "select rybh from zc_sys_jsryb where rybh=? and jsbh=? and rownum=1";
		String js = Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{rybh,jsbh}),"");
		if(Validate.noNull(js)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是不是科技处
	 * @return
	 */
	public boolean checkLoginKjc(){
		if(LUser.getDwbh().equals(Constant.KJC)){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除跳过的节点
	 * @param procinstid
	 */
	public void deleteActinstByProcinstid(String procinstid){
		String delSql1 = "delete FROM ACT_HI_TASKINST t WHERE 1=1 AND TASK_DEF_KEY_<>? and not exists(select 1 from OA_SHYJB where PROCINSTID=? and taskid=t.id_) and proc_inst_id_ = ? and DELETE_REASON_ is not null";
//		String delSql2 = "delete FROM ACT_HI_ACTINST t WHERE ACT_TYPE_=? AND act_id_<>? and not exists(select 1 from OA_SHYJB where PROCINSTID=?  and taskid=t.task_id_) and proc_inst_id_=? and END_TIME_ is not null";
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		sqlList.add(delSql1);
		objList.add(new Object[]{"sqr",procinstid,procinstid});
//		sqlList.add(delSql2);
//		objList.add(new Object[]{"userTask","sqr",procinstid,procinstid});
		db.batchUpdate(sqlList,objList);
	}
	/**
	 * 报销类型 的 金额 额度
	 * @return
	 */
	public Map getBxlxInfo(){
		String sql="select to_char(zyje1,'fm999999990.00')zyje1,to_char(bmje1,'fm999999990.00')bmje1,to_char(kyje1,'fm999999990.00')kyje1," +
				"to_char(kyje2,'fm999999990.00')kyje2,to_char(kyje3,'fm999999990.00')kyje3 from bxlxje where rownum=1";
		return db.queryForMap(sql);
	}
	/**
	 * 如果是财务处员工，自动跳过部门负责人审核，到了财务处长审核，由于财务预审到财务处长审核是jump，没有连线，流程图不能加complete>0,故加此sql
	 * @param procinstid
	 */
	public void deletetaskid(String procinstid) {
		String sql = "delete act_ru_task where proc_inst_id_='"+procinstid+"' and task_def_key_='cwys'";
		db.execute(sql);
	}
	/**
	 * 如果是财务预审，可以报销事由和修改费用名称。
	 * @author 作者：BiMing
	 * @version 创建时间:2018-4-20下午3:36:38
	 */
	public void updatebxsyAndfymc(String bxsy,String fyid,String guid,String xmguid) {
		String fyids[] = fyid.split(",");
		String xmguids[] = xmguid.split(",");
		List list = new ArrayList<>();
		String sqlz = "update cw_rcbxzb set bxsy='"+bxsy+"' where guid = '"+guid+"'";
		list.add(sqlz);
		for (int i = 0; i < xmguids.length; i++) {
			String sqlm = "update cw_rcbxmxb set fymc = '"+fyids[i]+"' where zbid='"+guid+"'and guid='"+xmguids[i]+"'";
			list.add(sqlm);
		}
		try {
			db.batchUpdate(list);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
		}
	}
	/**
	 * 查询平台系统设置部门余额。
	 * @return
	 */
	public String getBmye() {
		String Bmjesql = "select bmje1 from bxlxje";
		String bmje = db.queryForSingleValue(Bmjesql);
		return bmje;
	}
}
