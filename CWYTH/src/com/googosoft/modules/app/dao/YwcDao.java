package com.googosoft.modules.app.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.apache.poi.hssf.record.formula.functions.Replace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.wsbx.CcbxDao;
import com.googosoft.dao.wsbx.RcbxDao;
import com.googosoft.dao.wsbx.ccyw.CcywshDao;
import com.googosoft.modules.app.pojo.Cctj;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.wsbx.CcbxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.ccyw.CcywshService;
import com.googosoft.service.wsbx.jkgl.JksqService;
import com.googosoft.service.wsbx.process.WsbxProcessService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Const;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;
/**
*@author 杨超超
*@date   2018年1月31日---下午1:24:37
*/
@Repository("ywcdao")
public class YwcDao extends BaseDao {
	@Resource(name="ccywshService")
	CcywshService ccywshService;
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	@Resource(name="ccywshDao")
	private CcywshDao ccywshDao;
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	@Resource(name = "rcbxDao")
	private RcbxDao rcbxDao;
	@Resource(name = "ccbxDao")
	private CcbxDao ccbxDao;
	@Resource(name="jksqService")
	private JksqService jksqService;
	@Resource(name = "ccbxService")
	private CcbxService ccbxService;
	@Resource(name = "ProcessService")
	private WsbxProcessService service;
	@Autowired
	EchoService echoService;
	Gson gson = new Gson();
	/*************************** 公用查询 *******************************/
	//(1) 根据rybh查询ryguid  和 部门编号  bmbh(DWBH)
	public Map getgyryguid(String userId) {
		Map map = null;
		String sql = " SELECT ry.GUID,ry.RYBH,ry.DWBH,ry.XM as ryxm,dw.mc as dwmc FROM gx_sys_ryb ry left join gx_sys_dwb dw on dw.dwbh=ry.dwbh WHERE ry.GUID='" + userId + "' ";
		try {
			map = db.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	public Map getgyryrybh(String rybh) {
		Map map = null;
		String sql = " SELECT ry.GUID,ry.RYBH,ry.DWBH,ry.XM as ryxm,dw.mc as dwmc FROM gx_sys_ryb ry left join gx_sys_dwb dw on dw.dwbh=ry.dwbh WHERE ry.rybh='" + rybh + "' ";
		try {
			map = db.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	//(2)获取对公支付 单位
	public List<Map<String,Object>> getWldwList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.dwmc,t.dwdz,t.guid,t.wlbh");
		sql.append(" from CW_WLDWB t where 1=1");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	//往来银行 查询
	public List<Map<String,Object>> getWldwYh(String wlbh) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,khyh,khyhzh");
		sql.append(" from Cw_wldwmxb t where 1=1");
		sql.append(" and t.wlbh=(select guid from CW_WLDWB where wlbh='"+wlbh+"')");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	//省份list
	public List<Map<String,Object>> getProvicesList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select provinceid, province from cw_provinces ");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	//根据省份查询市区
	public List<Map<String,Object>> getCitiesByProvince(String sfbh) {
		return db.queryForList("select provinceid, cityid, city from cw_cities  where provinceid='"+sfbh+"'  ", new Object[]{});
	}
	//根据项目guid 查询项目负责人  
	public List<Map<String,Object>> getxmfzrlist(String xmguid){
		String sql=" select wm_concat(fzr) as fzr from XMINFOS where guid in ('"+xmguid+"') ";
		return db.queryForList(sql);
	}
	//项目负责人 所在部门
	public List<Map<String,Object>> xmfzrszbmlist(String fzrrybh){
		String fzrybhs [] =fzrrybh.split(",");
		String fzrybh1="";
		String sql="";
		List<Map<String,Object>> list=null;
		for(int i=0;i<fzrybhs.length;i++) {
			fzrybh1=fzrybhs[i];
		    sql=" select dw.dwbh as dwbh,dw.mc as dwmc from gx_sys_ryb b left join gx_sys_dwb dw on dw.dwbh=b.dwbh where b.rybh in ('"+fzrybh1+"') ";
		    list=db.queryForList(sql);
		}
		return list;
	}
	//项目负责人 银行信息
	public List<Map<String,Object>> getyhxxlist(String fzrguid){
		String sql=" select guid,jsbh,khyh,khyhzh from cw_jsyhzhb where jsbh='"+fzrguid+"' ";
		return db.queryForList(sql);
	}
	//根据主表id获取流程id  出差申请审批表
	public Map getlcid(String bh,String bm) {
		Map map=null;
		String sql=" select PROCINSTID from "+bm+" where guid='"+bh+"' ";
		try {
			map=db.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	//根据主表ID获取所有字段
	public Map getsyzd(String bh,String bm) {
		Map map=null;
		String sql=" select * from "+bm+" where guid='"+bh+"' ";
		try {
			map=db.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	//根据主表id获取流程id  出差申请审批表
	public Map getlcidgid(String bh,String bm) {
		Map map=null;
		String sql=" select PROCINSTID from "+bm+" where gid='"+bh+"' ";
		try {
			map=db.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	/*************************** 6.出差申请接口 *******************************/
	// （1）、获取人员基本信息

	/**
	 * 部门基本信息 单位
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getbmlist() {
		List<Map<String, Object>> list = null;
		String sql = "SELECT DWBH , MC FROM GX_SYS_DWB ";
		try {
			list = db.queryForList(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//查询部门
	public List<Map<String, Object>> getrybmlist() {
		List<Map<String, Object>> list = null;
		String sql = "select k.dwbh, k.mc from gx_sys_dwb k  where 1 = 1 and  k.dwbh='000001' and nvl(dwzt, '1') = '1'  ";
		try {
			list = db.queryForList(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//递归
	public List<Map<String, Object>> getdgryxxlist() {
		List<Map<String, Object>> list = null;
		String sql = "  select m.dwbh as \"personelId\", m.mc as \"personelName\" from gx_sys_dwb m  where 1=1 and nvl(dwzt, '1') = '1'   ";
		try {
			list = db.queryForList(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	/**
	 * 人员基本信息 saas==bmbh
	 * 
	 * @param object
	 * @return
	 */
	public List<Map<String, Object>> getryxxlist(Object object,String userId) {
		List<Map<String, Object>> list = null;
		String sql = " SELECT GUID as \"personelId\" , XM as \"personelName\" FROM GX_SYS_RYB WHERE DWBH=? and guid not in '"+userId+"' ";
		try {
			list = db.queryForList(sql, new Object[] { object });
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	// （2）、获取项目的基本信息
	public List<Map<String, Object>> getxmlist(String userId,String saas,String rybh) {
		List<Map<String, Object>> list = null;
		StringBuffer sql = new StringBuffer();
			sql.append(" select (select B.mc from gx_sys_dmb B where B.zl = 'jflx' and B.dm = A.jflx) AS JFLXMC,(select B.mc from gx_sys_dmb B where B.zl = '250' and B.dm = A.xmdl) AS XMDLMC,(select '('||b.rybh||')'||b.xm from gx_sys_ryb b where b.rybh = a.fzr) as fzrxm,A.* from XMINFOS A where 1=1  ");
			sql.append(" and A.ye<> 0 and ((bsqr='"+userId+"' or bsqr='"+rybh+"') and jflx = '02') or (jflx='01' and bmbh='"+saas+"') and A.xmdl not in ('1', '2')");
			list = db.queryForList(sql.toString());
		return list;
	}

	// （3）出差申请提交
	public int ccsave(Cctj cctj) {
		// String sql="";
		String sql = "insert into cw_ccsqspb (guid,djbh,sqr,szbm,cclx,jfbh,sfyzf,yzfje,ccts,ccrs,ccnr,shzt,czr,czrq,sxfy,sqrxm,sqrq) " + "values(?,?,?,?,?,?,?,?,"
			+ "?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'))";
		Object[] obj = {};
		return db.update(sql);
	}
	
	public int insertCcywsq(PageData pd,String guid) {
		 Map map = gson.fromJson(pd.getString("key"), Map.class);
		 String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		 Map rymap=getgyryguid(userId);
		 String szbm = Validate.isNullToDefaultString("("+(rymap.get("DWBH")+")"+rymap.get("DWMC")),"");//所在部门
		 String sqrxm= Validate.isNullToDefaultString("("+(rymap.get("RYBH")+")"+rymap.get("RYXM")),"");//申请人姓名
		 
		 String ccts = Validate.isNullToDefaultString(map.get("ccts"), "");// 出差天数
		 String ccrs = Validate.isNullToDefaultString(map.get("ccrs"), "");// 出差人数
		 String cclx = Validate.isNullToDefaultString(map.get("cclx"), "");// 出差类型
		 if("会议出差".equals(cclx)) {
			 cclx="01";
		 }else if("培训出差".equals(cclx)) {
			 cclx="02";
		 }else  {
			 cclx="03";
		 }
		 String fy = Validate.isNullToDefaultString(map.get("fy"), "");// 费用
		 String sfyzf = Validate.isNullToDefaultString(map.get("sfyzf"), "");// 是否预支付
		 if("是".equals(sfyzf)) {
			 sfyzf="1";
		 }else {
			 sfyzf="0";
		 }
		 String yzfje = Validate.isNullToDefaultString(map.get("yzfje"), "");// 预支付金额
		 String ccnr = Validate.isNullToDefaultString(map.get("ccnr"), "");// 出差内容
		 String xmid = Validate.isNullToDefaultString(map.get("xmid"), "");// 项目id
		 String ccry = Validate.isNullToDefaultString(map.get("ccry"), "");// 出差人员
		 String imglist = Validate.isNullToDefaultString(map.get("imglist"), "");// 上传图片 list
		 
		 String sql = "insert into cw_ccsqspb (guid,djbh,sqr,szbm,cclx,jfbh,sfyzf,yzfje,ccts,ccrs,ccnr,shzt,czr,czrq,sxfy,sqrxm,sqrq) "
				+ "values(?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'))";
		Object[] obj = {
				guid,
				GenAutoKey.createKeyforth("cw_ccsqspb", "CC", "djbh"),
				userId,
				szbm,
				cclx,
				pd.getString("jfbh"),//废弃 多个项目经费  保存到另一个表
				sfyzf,
				yzfje,
				ccts,
				ccrs,
				ccnr,
				"01",
				userId,
				DateUtil.getTime(),
				fy,//所需费用
				sqrxm,//mv.addObject("ryxm","("+LUser.getRybh()+")"+LUser.getRyxm());
				DateUtil.getTime()
		};
//		       String czr = LUser.getGuid();
		       db.update("insert into cw_sys_ywjlb(ywid,kmlx,sszt,guid,czid,czr,czsj) select a.guid,a.xmbh,a.sszt,sys_guid(),'"+guid+"','"+userId+"',sysdate from Cw_xmjbxxb a where a.guid='"+pd.getString("jfbh")+"' ");
		       return db.update(sql,obj);
	}
	//同行人员
	public int insertTxryxx(String ccrysz,String guid) {
		String sql = "insert into cw_txryxxb(guid,txbh,rybh) values(sys_guid(),?,?)";
		Object[] obj = {
				guid,
				ccrysz
		};
		return db.update(sql,obj);
	}
	//出差项目
	public int insertXmxx(String xmguidsz,String guid) {
		String sql = "insert into CW_CCSQSPB_XM(guid,ccsqid,jfbh) values(sys_guid(),?,?)";
		Object[] obj = {
				guid,
				xmguidsz
		};
		return db.update(sql,obj);
	}
	//图片信息 
	public int insertimglist(String zbid,String djlx,String rybh,String newname, String oldname) {
		String sql = "insert into zc_xgwd(guid,djlx,dbh,filename,rybh,path) values(sys_guid(),'"+djlx+"','"+zbid+"','"+oldname+"','"+rybh+"','"+newname+"')";
		return db.update(sql);
	}
	//头像上传  
	public int inserttximglist(String zbid,String newname) {
		String sql = "update GX_SYS_RYB set url='"+newname+"' where guid='"+zbid+"'";
		return db.update(sql);
	}
	//行程信息保存
	public int insertxcxx(PageData pd,String guid) {
		 Map map = gson.fromJson(pd.getString("key"), Map.class);
		 String kssj = Validate.isNullToDefaultString(map.get("kssj"), "");// 开始时间
		 String jssj = Validate.isNullToDefaultString(map.get("jssj"), "");// 结束时间
		 String cfdd = Validate.isNullToDefaultString(map.get("cfdd"), "");// 出发地点
		 String mddd = Validate.isNullToDefaultString(map.get("xxdd"), "");// 目的地点
		 String jtgj = Validate.isNullToDefaultString(map.get("jtgj"), "");// 交通工具
		 String provinceid=Validate.isNullToDefaultString(map.get("provinceid"), "");// 省份id  provinceid
		 String cityid=Validate.isNullToDefaultString(map.get("cityid"), "");// 城市id
		String sql = "insert into cw_xcxxb (guid,spbh,kssj,jssj,cfdd,mddd,jtgj,provinceid,cityid) values(sys_guid(),'"+guid+"',to_date('"+kssj+"','yyyy-mm-dd  HH24:mi'),to_date('"+jssj+"','yyyy-mm-dd  HH24:mi'),'"+cfdd+"','"+mddd+"','"+jtgj+"','"+provinceid+"','"+cityid+"')";
		return db.update(sql);
	}
	/**
	 * 查询当前登录人的单位领导
	 * @return
	 */
	public Map<String,Object> getLoginLd(String dwbh){
		String sql = " select (select guid from gx_sys_ryb where rybh = (select dwld from gx_sys_dwb where dwbh='"+dwbh+"')) as dwld, "
				+" (select guid from gx_sys_ryb where rybh = (select fgld from gx_sys_dwb where dwbh='"+dwbh+"')) as fgld from dual ";
		return db.queryForMap(sql);
	}
	/*************************** 7.待办业务接口 *******************************/
	// （1）、列表接口
	// 根据传过来的userId 查询人员guid userId为rybh
	public Map getryguid(String userId) {
		Map map = null;
		String sql = " SELECT GUID,DWBH,rybh FROM gx_sys_ryb WHERE GUID='" + userId + "' ";
		try {
			map = db.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	/**
	 * 获取业务审核列表信息
	 */
	public List<Map<String, Object>> getShxx(String guid, String cxywlx,int index,int pageSize) {
		//业务类型 0、全部；1、出差申请；2、日常报销；3、出差报销；4、公务接待；5、接待报销  
		StringBuffer sql = new StringBuffer();
		if ("ccsq".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,'ccsq' as ywlx ,'1' as state,to_char(a.czrq,'yyyy-mm-dd HH24:MI:SS') as time,a.djbh,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_ccsqspb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '130202' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  and b.name_ not in ('报销人员')  order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("rcbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,to_char(a.czrq,'yyyy-mm-dd HH24:MI:SS') as time,'2' as state,'rcbx' as ywlx,a.djbh,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_rcbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_");
			sql.append(" left join gx_sys_ryb c on a.bxr = c.guid left join zc_sys_mkb d on d.mkbh = '110203' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  and b.name_ not in ('报销人员')  order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("ccbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,a.czrq as time,'ccbx' as ywlx,'3' as state,a.djbh,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_clfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '110302' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and b.name_ not in ('报销人员')  order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("gwjd".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,a.sqrq as time,'gwjd' as ywlx,a.djbh,'4' as state,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdywsqspb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.rybh left join zc_sys_mkb d on d.mkbh = '130102' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and b.name_ not in ('报销人员') order by time desc  ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("jdbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,to_char(a.czrq,'yyyy-mm-dd HH24:MI:SS') as time,'5' as state,'jdbx' as ywlx,a.djbh,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(	" left join gx_sys_ryb c on a.bxryid = c.guid left join zc_sys_mkb d on d.mkbh = '111102' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and b.name_ not in ('报销人员')  order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,'ccsq' as ywlx ,to_char(a.czrq,'yyyy-mm-dd HH24:MI:SS') as time,a.djbh,to_char(c.xm) as sqrxm,'1' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_ccsqspb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '130202' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and b.name_ not in ('报销人员')   ");
			sql.append(" union ");
			sql.append(	" select a.guid,a.procinstid,'gwjd' as ywlx,a.sqrq as time,a.djbh,to_char(c.xm) as sqrxm,'4' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdywsqspb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.rybh left join zc_sys_mkb d on d.mkbh = '130102' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  and b.name_ not in ('报销人员')  ");
			sql.append(" union ");
			sql.append(	" select a.guid,a.procinstid,'rcbx' as ywlx,to_char(a.czrq,'yyyy-mm-dd HH24:MI:SS') as time,a.djbh,to_char(c.xm) as sqrxm,'2' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_rcbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_");
			sql.append(" left join gx_sys_ryb c on a.bxr = c.guid left join zc_sys_mkb d on d.mkbh = '110203' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  and b.name_ not in ('报销人员')   ");
			sql.append(" union ");
			sql.append(	" select a.guid,a.procinstid,'ccbx' as ywlx,a.czrq as time,a.djbh,to_char(c.xm) as sqrxm,'3' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_clfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '110302' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  and b.name_ not in ('报销人员')   ");
			sql.append(" union ");
			sql.append(	" select a.guid,a.procinstid,'jdbx' as ywlx,to_char(a.czrq,'yyyy-mm-dd HH24:MI:SS') as time,a.djbh,to_char(c.xm) as sqrxm,'5' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(	" left join gx_sys_ryb c on a.bxryid = c.guid left join zc_sys_mkb d on d.mkbh = '111102' where b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and b.name_ not in ('报销人员')  order by time desc  ");
			sql.append("  ) A  WHERE ROWNUM < "+pageSize*(index)+"  order by a.time desc )WHERE RN >= "+ pageSize*(index-1)+" ");
		}
		return db.queryForList(sql.toString());
	}

	/*************************** 8.我发起列表 *******************************/
	// （1）、我发起列表
	/**
	 * 获取业务审核列表信息
	 */
	public List<Map<String, Object>> getWfqShxx(String guid, String cxywlx,String rybh,int index,int pageSize) {
		//业务类型 0、全部；1、出差申请审批；2、日常报销审批；3、差旅费报销审批；4、公务接待申请审批；5、公务接待报销审批  
		StringBuffer sql = new StringBuffer();
		int a = (index - 1) * pageSize;// 开始条数
		int b = (index * pageSize) - 1;// 结束条数
		if ("ccsq".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(" select to_char(c.czrq,'yyyy-mm-dd') as time ,'1' as state,c.guid as guid,c.djbh as bh ,c.sqr as sqr, (SELECT T.MC  FROM GX_SYS_DMB t  where zl = '11033' AND T.DM = c.shzt) zt,");
			sql.append(" C.CCNR as title from CW_CCSQSPB c where c.sqr='"+guid+"' order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} 
		else if ("rcbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(" select to_char(r.czrq,'yyyy-mm-dd') as time,'2' as state,r.guid as guid ,r.djbh as bh,r.bxr as sqr,(SELECT T.MC  FROM GX_SYS_DMB t  where zl = 'lcsh' AND T.DM = r.shzt) zt, ");
			sql.append(" r.bxsy as title from CW_RCBXZB r where r.bxr='"+guid+"' order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("ccbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(" select clf.czrq as time,'3' as state,clf.guid as guid,clf.djbh as bh,clf.sqr as sqr,(SELECT T.MC  FROM GX_SYS_DMB t  where zl = 'lcsh' AND T.DM = clf.shzt) zt,");
			sql.append(" clf.ccsy as title   from CW_CLFBXZB clf where clf.sqr='"+guid+"' order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("gwjd".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(" select g.sqrq as time,'4' as state,g.guid as guid,g.djbh as bh  ,g.sqr as sqr,(SELECT T.MC  FROM GX_SYS_DMB t  where zl = 'shztdmgw' AND T.DM = G.shzt) zt,");
			sql.append(" g.jdsy as title from CW_GWJDYWSQSPB G where g.sqr='"+rybh+"' order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("jdbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(" select to_char(gw.czrq,'yyyy-mm-dd') as time,gw.guid as guid,gw.djbh as bh,'5' as state,gw.bxry as sqr,(SELECT T.MC  FROM GX_SYS_DMB t  where zl = 'lcsh' AND T.DM = gw.shzt) zt,");
			sql.append(" gw.jdsy as title from CW_GWJDFBXZB GW where gw.bxry='"+rybh+"' order by time desc  ");
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			//1.出差申请审批
			sql.append(" select to_char(c.czrq,'yyyy-mm-dd') as time ,c.guid as guid,c.djbh as bh,'1' as state ,c.sqr as sqr, (SELECT T.MC  FROM GX_SYS_DMB t  where zl = '11033' AND T.DM = c.shzt) zt,");
			sql.append(" C.CCNR as title from CW_CCSQSPB c   ");
			sql.append(" where c.sqr='"+guid+"' ");
			//2.日常报销审批
			sql.append(" union ");
			sql.append(" select to_char(r.czrq,'yyyy-mm-dd') as time,r.guid as guid ,r.djbh as bh,'2' as state,r.bxr as sqr,(SELECT T.MC  FROM GX_SYS_DMB t  where zl = 'lcsh' AND T.DM = r.shzt) zt, ");
			sql.append(" r.bxsy as title from CW_RCBXZB r  ");
			sql.append(" where r.bxr='"+guid+"' ");
			//3.差旅费报销审批
			sql.append(" union ");
			sql.append(" select clf.czrq as time,clf.guid as guid,clf.djbh as bh,'3' as state,clf.sqr as sqr,(SELECT T.MC  FROM GX_SYS_DMB t  where zl = 'lcsh' AND T.DM = clf.shzt) zt,");
			sql.append(" clf.ccsy as title   from CW_CLFBXZB clf ");
			sql.append(" where clf.sqr='"+guid+"' ");
			//4.公务接待申请审批
			sql.append(" union ");
			sql.append(" select g.sqrq as time,g.guid as guid,g.djbh as bh ,'4' as state ,g.sqr as sqr,(SELECT T.MC  FROM GX_SYS_DMB t  where zl = 'shztdmgw' AND T.DM = G.shzt) zt,");
			sql.append(" g.jdsy as title from CW_GWJDYWSQSPB G ");
			sql.append(" where g.sqr='"+rybh+"'  ");
			//5.公务接待报销审批 
			sql.append(" union ");
			sql.append(" select to_char(gw.czrq,'yyyy-mm-dd') as time,gw.guid as guid,gw.djbh as bh,'5' as state,gw.bxry as sqr,(SELECT T.MC  FROM GX_SYS_DMB t  where zl = 'lcsh' AND T.DM = gw.shzt) zt,");
			sql.append(" gw.jdsy as title from CW_GWJDFBXZB GW  ");
			sql.append(" where gw.bxry='"+rybh+"' order by time desc ");
			sql.append("  ) A  WHERE ROWNUM < "+pageSize*(index)+"  )WHERE RN >= "+pageSize*(index-1)+" ");

		}
		return db.queryForList(sql.toString());
	}
	/***************************9.日常报销申请*******************************/
	//（1）日常报销申请列表
	public List<Map<String, Object>> getrcbxxmlist(String ryguid,String rybh,String dwbh,int index,int pageSize) {
		//业务类型 0、全部；1、出差申请；2、日常报销；3、出差报销；4、公务接待；5、接待报销  
		StringBuffer sql = new StringBuffer();
			sql.append(" SELECT * FROM(SELECT R.*, ROWNUM RN FROM (  ");
			sql.append(" (select distinct a.guid,a.xmbh,a.xmmc,decode(nvl(ye,'0'),'0','0.00',to_char(round(YE,2),'fm9999999999999999999999999900.00'))ye,jzsj," +
				"(SELECT D.MC FROM GX_SYS_DMB D WHERE D.ZL = '251' AND D.DM = A.XMLB) XMLBMC," +
				"(select B.mc from gx_sys_dmb B where B.zl = '"+Constant.XMDL+"' and B.dm = A.xmdl) AS XMDLMC," +
				"(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=a.fzr)as fzr," +
				"(SELECT decode(nvl(YSJE,'0'),'0','0.00',to_char(round(YSJE,2),'fm9999999999999999999999999999900.00')) FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS YSJE," +
				" DECODE(NVL(zfcgsyje, '0'),'0','0.00',TO_CHAR(ROUND(zfcgsyje, 2),'fm9999999999999999999999999900.00')) zfcgsyje," +
				" DECODE(NVL(fzfcgsyje, '0'),'0','0.00',TO_CHAR(ROUND(fzfcgsyje, 2),'fm9999999999999999999999999900.00')) fzfcgsyje," +
				"decode(a.jflx,'01','部门经费','个人经费')jflx,a.jflx as jflxdm " +
				"from  XMINFOS A  " +
				"where 1=1 and a.ye<>0 and ( ((bsqr='"+ryguid+"' or bsqr='"+rybh+"') and jflx = '02') or (jflx='01' and a.bmbh='"+dwbh+"'))"
						+ "and ((A.xmdl='"+Constant.XMDL_KYJF+"' and (a.fzr='"+rybh+"' or a.xmbh in (select t.xmbh from CW_XMSQB t where t.bsqr='"+rybh+"') ))"
								+ "or (A.xmdl<>'"+Constant.XMDL_KYJF+"' and a.bmbh='"+dwbh+"')))"
						);
			sql.append("  ) R  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
			System.err.println("日常报123销申请列表sql==>"+sql);
			return db.queryForList(sql.toString());
	}
	//(2) 报销明细 费用修改接口详情
	public List<Map<String,Object>> getFyList(String xmguid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select guid,j.kmnd,j.KMBH,j.KMMC,KMJC,L,K,SM,DECODE(QYF,'1','是','否')QYF from CW_JJKMB j where 1=1 and sszt ='B9BA12A24DBE4EA89763AFDE76B8C61A'   and qyf = '1'     order by KMBH asc");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	//（3）表单数据保存提交
	public int rcbxfyxgtj(PageData pd,String djbh,String zbid) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String fjzzs = Validate.isNullToDefaultString(map.get("fjzzs"), "");// 附件总张数
		String bxzje = Validate.isNullToDefaultString(map.get("bxzje"), "");// 报销总金额
		String sfkylbx = Validate.isNullToDefaultString(map.get("sfkylbx"), "0");// 是否科研类报销（传是或者否）
		if("是".equals(sfkylbx)) {
			sfkylbx="1";
		}else {
			sfkylbx="0";
		}
		String bxsy = Validate.isNullToDefaultString(map.get("bxsy"), "");// 报销事由
		String fysm = Validate.isNullToDefaultString(map.get("fysm"), "");// 费用说明
		List<Map> xmxxlist = (List) map.get("xmxxlist");//项目信息数据集合
		List<Map> bxxxlist = (List) map.get("bxxxlist");//报销明细信息集合
		List<Map> fplist=(List)map.get("fplist");//发票list
		//报修人guid  bxr
		Map ryidmap =getryguid(userId);
		String ryguid = Validate.isNullToDefaultString(ryidmap.get("GUID"), "");
		//单位编号 (szbm) dwbh
		String szbm=Validate.isNullToDefaultString(ryidmap.get("DWBH"), "");
		//保存信息 到 主表 
		int a1=0;
		//新增之前先删除 避免重复
		String delsql1="delete from Cw_rcbxzb where guid='"+zbid+"' ";
		db.update(delsql1);
		String sql1=" insert into Cw_rcbxzb(guid,djbh,bxr,szbm,fjzzs,bxzje,sfkylbx,bxsy,bz,czr,czrq) values('"+zbid+"','"+djbh+"','"+ryguid+"','"+szbm+"','"+fjzzs+"','"+bxzje+"','"+sfkylbx+"','"+bxsy+"','"+fysm+"','"+ryguid+"',sysdate) ";
		a1=db.update(sql1);
		//项目信息遍历
//		int a2=0;
//		String sql2="";
//		//新增之前先删除 避免重复
//		String delsql2="delete from CW_RCBXXMMXB where zbid='"+zbid+"' ";
//		db.update(delsql2);
//		for(int j=0;j<xmxxlist.size();j++) {
//			String xmmc= Validate.isNullToDefaultString(xmxxlist.get(j).get("xmmc"),"");
//			String xmid= Validate.isNullToDefaultString(xmxxlist.get(j).get("xmid"),"");
//			String syje= Validate.isNullToDefaultString(xmxxlist.get(j).get("syje"),"");
//			String bcbxje= Validate.isNullToDefaultString(xmxxlist.get(j).get("bcbxje"),"");
//			sql2="insert into CW_RCBXXMMXB (guid,syje,bcbxje,xmmc,zbid,xmguid) values (sys_guid(),'"+syje+"','"+bcbxje+"','"+xmmc+"','"+zbid+"','"+xmid+"')";
//			a2=db.update(sql2);
//		}
		//报销明细遍历
		int a3=0;
		String sql3="";
		//新增之前先删除 避免重复
		String delsql3="delete from CW_RCBXMXB where zbid='"+zbid+"' ";
		db.update(delsql3);
		for(int k=0;k<bxxxlist.size();k++) {
			String fymc= Validate.isNullToDefaultString(bxxxlist.get(k).get("fymc"),"");
			String fyid= Validate.isNullToDefaultString(bxxxlist.get(k).get("fyid"),"");
			String je= Validate.isNullToDefaultString(bxxxlist.get(k).get("je"),"");
			String pjzs= Validate.isNullToDefaultString(bxxxlist.get(k).get("pjzs"),"");
			String bz= Validate.isNullToDefaultString(bxxxlist.get(k).get("bz"),"");
			String xmid= Validate.isNullToDefaultString(bxxxlist.get(k).get("xmid"),"");
			String zfcgje= Validate.isNullToDefaultString(bxxxlist.get(k).get("zfcgje"),"");
			String fzfcgje= Validate.isNullToDefaultString(bxxxlist.get(k).get("fzfcgje"),"");
			sql3=" insert into CW_RCBXMXB(guid,fymc,bz,fjzs,bxje,zbid,xmguid,zfcgje,fzfcgje) values(sys_guid(),'"+fyid+"','"+bz+"','"+pjzs+"','"+je+"','"+zbid+"','"+xmid+"','"+zfcgje+"','"+fzfcgje+"')";
			a3=db.update(sql3);
		}
		//发票list fplist
		int a4=0;
		String sql4="";
		//新增之前先删除 避免重复
		String delsql4="delete from CW_FPXXB where zbid='"+zbid+"' ";
		db.update(delsql4);
		for(int q=0;q<fplist.size();q++) {
			String fph1=Validate.isNullToDefaultString(fplist.get(q).get("one"),"");
			String fph2=Validate.isNullToDefaultString(fplist.get(q).get("two"),"");
			String fph3=Validate.isNullToDefaultString(fplist.get(q).get("three"),"");
			String fph4=Validate.isNullToDefaultString(fplist.get(q).get("four"),"");
			String fph5=Validate.isNullToDefaultString(fplist.get(q).get("five"),"");
			sql4=" insert into CW_FPXXB (guid,zbid,fph1,fph2,fph3,fph4,fph5) values(sys_guid(),'"+zbid+"','"+fph1+"','"+fph2+"','"+fph3+"','"+fph4+"','"+fph5+"') ";
			a4=db.update(sql4);
		}
		return a1+a3+a4;
	}
	//（5）结算方式数据提交
		public int rcbxjsfstj(PageData pd) {
			Map map = gson.fromJson(pd.getString("key"), Map.class);
			String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
			String zbid = Validate.isNullToDefaultString(map.get("zbid"), "");// 主表id
			String djbh = Validate.isNullToDefaultString(map.get("djbh"), "");// 单据编号
			String sfcjk = Validate.isNullToDefaultString(map.get("sfcjk"), "");// 是否冲借款
			String sfdszf = Validate.isNullToDefaultString(map.get("sfdszf"), "");// 是否对公支付
			String sfdgzf = Validate.isNullToDefaultString(map.get("sfdgzf"), "");// 是否对私支付
			String sfgwk = Validate.isNullToDefaultString(map.get("sfgwk"), "");// 是否公务卡
			//更新主表信息
			String sql1=" update Cw_rcbxzb set sfdszf='"+sfdszf+"',sfdgzf='"+sfdgzf+"',sfgwk='"+sfgwk+"',sfcjk='"+sfcjk+"' where guid='"+zbid+"' ";
			int a = db.update(sql1);
			List<Map> dszflist = (List) map.get("dszflist");//对私支付list
			List<Map> dgzflist = (List) map.get("dgzflist");//对公支付list
			List<Map> gwklist = (List) map.get("gwklist");//公务卡list
			List<Map> cjklist = (List) map.get("cjklist");//冲借款list
			//对私支付 list遍历保存方法
			String sql2="";
			String delsql2=" delete from Cw_DSZFB where zfdh ='"+zbid+"' ";
			db.update(delsql2);
			for(int i=0;i<dszflist.size();i++) {
				String ryxz=Validate.isNullToDefaultString(dszflist.get(i).get("ryxz"), "");// 1.本人 2.项目负责人 3.其他人
				String ryid=Validate.isNullToDefaultString(dszflist.get(i).get("ryid"), "");// 人员id  
				String yhmc=Validate.isNullToDefaultString(dszflist.get(i).get("yhkh"), "");// 卡类型 (银行名称)
				String kh=Validate.isNullToDefaultString(dszflist.get(i).get("yhkh"), "");// 对方卡号
				String je=Validate.isNullToDefaultString(dszflist.get(i).get("je"), "");// 金额
				Map dwmap=getgyryguid(ryid);
			    String rygubh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
				sql2="insert into Cw_DSZFB(guid,zfdh,ryxz,ryxm,klx,dfzh,je,bxlx) VALUES(sys_guid(),'"+zbid+"','"+ryxz+"','"+rygubh+"','"+yhmc+"','"+kh+"','"+je+"','1')";
				db.update(sql2);
			}
			//对公支付 list遍历保存方法
			String sql3="";
			String delsql3=" delete from CW_DGZFB where zfdh ='"+zbid+"' ";
			db.update(delsql3);
			for(int j=0;j<dgzflist.size();j++) {
				String dfdq=Validate.isNullToDefaultString(dgzflist.get(j).get("dfdq"), "");// 地方地区
				String wlbh=Validate.isNullToDefaultString(dgzflist.get(j).get("wlbh"), "");// 对方单位 对应往来编号
				String dfyh=Validate.isNullToDefaultString(dgzflist.get(j).get("dfyh"), "");// 对方银行
				String dfzh=Validate.isNullToDefaultString(dgzflist.get(j).get("dfzh"), "");// 对方账号
				String je=Validate.isNullToDefaultString(dgzflist.get(j).get("je"), "");// 金额
				String dfdw=Validate.isNullToDefaultString(dgzflist.get(j).get("dfdw"), "");// wlbh
				sql3="insert into CW_DGZFB(guid,zfdh,dfdw,dfdq ,dfyh ,dfzh, je, bxlx ) VALUES(sys_guid(),'"+zbid+"','"+dfdw+"','"+dfdq+"','"+dfyh+"','"+dfzh+"','"+je+"','1')";
				db.update(sql3);
			}
			//公务卡 list遍历保存方法
			String delsql4=" delete from CW_GWKB where skdh ='"+zbid+"' ";
			db.update(delsql4);
			for(int k=0;k<gwklist.size();k++) {
				String skje=Validate.isNullToDefaultString(gwklist.get(k).get("skje"), "");// 刷卡金额
				String kh=Validate.isNullToDefaultString(gwklist.get(k).get("kh"), "");// 卡号
				String skrq=Validate.isNullToDefaultString(gwklist.get(k).get("skri"), "");// 刷卡日期
				Map dwmap=getgyryguid(userId);
			    String rygubh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
				String sql4="insert into CW_GWKB(guid ,skdh ,szbm ,ryxm ,skrq ,skje ,kh ,bxlx ) VALUES(sys_guid(),'"+zbid+"','','"+rygubh+"',to_date('"+skrq+"','yyyy-MM-dd HH24:mi'),'"+skje+"','"+kh+"','1') ";
				db.update(sql4);
			}
			//冲借款 list保存方法
			String delsql5=" delete from CW_CJKB where jkdh ='"+zbid+"' ";
			db.update(delsql5);
			Map dwmap=getgyryguid(userId);
			 String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
			for(int c=0;c<cjklist.size();c++) {
				String jkje=Validate.isNullToDefaultString(cjklist.get(c).get("jkje"), "");//
				String cjkje=Validate.isNullToDefaultString(cjklist.get(c).get("cjkje"), "");//
			    String ryjkkbh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
				String sql5 = "insert into CW_CJKB(guid,jkdh,jkr,szbm,jkje,cjkje,bxlx,jkid)"
					+ "VALUES(sys_guid(),'"+zbid+"','"+ryjkkbh+"','"+dwbh+"','"+jkje+"','"+cjkje+"','0','"+djbh+"') ";
				db.update(sql5);
			}
			return a;
		}
	/***************************10.、审批数据详情页面*******************************/
	//(1）、出差申请 ①审批详情接口
	public Map<String, Object> getCcywxq(String guid){
		Object[] obj = {guid};
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT B.GUID,sf.province as CCSF ,cs.city as CCSQ,B.YZFJE AS YZFJE,B.DJBH as DJBH,B.SQR AS SQR,ry.xm as sqrxm,B.SZBM AS SZBM,B.CCTS AS CCTS ,B.CCRS AS CCRS ,(select dmb.mc from gx_sys_dmb dmb where dmb.zl='cclx' and dmb.dm=b.cclx)AS CCLX,B.SXFY AS PXFY,DECODE(B.SFYZF, '1','是','0','否')as SFYZF,B.CCNR,to_char(XC.KSSJ,'yyyy-mm-dd hh:mm:ss') as KSSJ,to_char(XC.JSSJ,'yyyy-mm-dd hh:mm:ss') as JSSJ,XC.CFDD,XC.MDDD,xc.jtgj,(select mc from gx_sys_dmb d where d.zl='jtgj' and d.dm=xc.jtgj ) as jtgj,xc.jtgj as jtgjid FROM CW_CCSQSPB B " ) ;
		sql.append(" left join gx_sys_ryb ry on ry.guid=b.sqr LEFT JOIN CW_XCXXB XC ON XC.SPBH=B.GUID ");
		sql.append(" left join  cw_provinces sf on sf.provinceid=XC.PROVINCEID left join  cw_cities cs on cs.cityid=xc.cityid ");
		sql.append(" WHERE 1=1 and b.guid=?");
		return db.queryForMap(sql.toString(),obj);
	}
	//获取交通工具
	public Map<String, Object> getJtgjmap(String jtgjc,String bh){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT B.GUID,sf.province as CCSF ,cs.city as CCSQ,B.YZFJE AS YZFJE,B.DJBH as DJBH,B.SQR AS SQR,ry.xm as sqrxm,B.SZBM AS SZBM,B.CCTS AS CCTS ,B.CCRS AS CCRS ,(select dmb.mc from gx_sys_dmb dmb where dmb.zl='cclx' and dmb.dm=b.cclx)AS CCLX,B.SXFY AS PXFY,DECODE(B.SFYZF, '1','是','0','否')as SFYZF,B.CCNR,to_char(XC.KSSJ,'yyyy-mm-dd hh:mm:ss') as KSSJ,to_char(XC.JSSJ,'yyyy-mm-dd hh:mm:ss') as JSSJ,XC.CFDD,XC.MDDD,xc.jtgj,(select wm_concat(to_char(mc)) from gx_sys_dmb d where d.zl = 'jtgj' and d.dm in ('"+jtgjc+"'))as jtgj,xc.jtgj as jtgjid FROM CW_CCSQSPB B " ) ;
		sql.append(" left join gx_sys_ryb ry on ry.guid=b.sqr LEFT JOIN CW_XCXXB XC ON XC.SPBH=B.GUID ");
		sql.append(" left join  cw_provinces sf on sf.provinceid=XC.PROVINCEID left join  cw_cities cs on cs.cityid=xc.cityid ");
		sql.append(" WHERE 1=1 and b.guid='"+bh+"'");
		return db.queryForMap(sql.toString());
	}
	//获取出差申请详情同行人员
	public List<Map<String,Object>> gettxrylist(String guid){
		List<Map<String,Object>> list = null;
		String sql="select ry.xm as \"xm\",dw.mc as \"bm\" from CW_TXRYXXB t left join gx_sys_ryb ry on ry.guid=t.rybh left join gx_sys_dwb dw on dw.dwbh=ry.dwbh where t.txbh=?";
		try {
			list=db.queryForList(sql,new Object[] { guid });
		} catch (Exception e) {
			
		}
		return list;
	}
	//获取图片路径
	public List<Map<String,Object>> gettpsclj(String zbid){
		List<Map<String,Object>> list = null;
		String sql="select * from zc_xgwd where dbh='"+zbid+"' ";
		try {
			list=db.queryForList(sql);
		} catch (Exception e) {
			
		}
		return list;
	}
	//获取 出差申请详情 办理记录 流程list
	public List<Map<String,Object>> getlclist(String guid){
		List<Map<String,Object>> list = null;
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT T.NAME_ AS TASKNAME,r.xm as name,s.SHYJ as yj,to_char(T.START_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS starttime,to_char(T.END_TIME_, 'yyyy-MM-dd hh24:mi:ss') AS ENDTIME, ");
		sql.append(" (case s.shzt when '1' then  '通过'  when '2' then '不通过' end) shzt  ");
		sql.append("  FROM ACT_HI_TASKINST T LEFT JOIN GX_SYS_RYB r  ON T.ASSIGNEE_ = r.GUID left join oa_shyjb s  on T.id_ = s.taskid WHERE PROC_INST_ID_ = ?  order by T.START_TIME_ asc");
		try {
			list=db.queryForList(sql.toString(),new Object[] { guid });
		} catch (Exception e) {
			
		}
		return list;
	}
	//②、出差申请审批提交接口
	public int ccsqsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("bh"), "");// 主表id
		String spjg = Validate.isNullToDefaultString(map.get("spjg"), "");// 审批结果（同意为T,不同意为F）
		String spyj = Validate.isNullToDefaultString(map.get("spyj"), "");// 审批意见
		String apply=Validate.isNullToDefaultString(map.get("apply"), "");// 退回第一步
		Map getlcidmap=getlcid(zbid,"CW_CCSQSPB");
		String procinstid = Validate.isNullToDefaultString(getlcidmap.get("procinstid"), "");// 流程ID
		DshInfoMap msgMap = new DshInfoMap();
		int a=0;
		 Map dwmap=getgyryguid(userId);
	     String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
		 GX_SYS_RYB ryb = new GX_SYS_RYB();
		 ryb.setDwbh(dwbh);
		 ryb.setGuid(userId);
		 session.setAttribute(Const.SESSION_USER, ryb);
		if("T".equals(spjg)) {//通过
			ccywshService.approveLeaveInfo( session,shyjb, zbid,procinstid,spyj);
			if(Validate.noNull(procinstid)) {
				//从task表中查找流程审核人
				String shr = echoService.getShrGuid(procinstid);
				//如果审核人存在
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(zbid);
					msgMap.get(shr).add(shxxMsg);
				}
			}
			a=1;
		}else if("F".equals(spjg)) {//退回
				ccywshService.rejectleaveinfo(session, shyjb,zbid,procinstid,spyj,apply);
				if(Validate.noNull(procinstid)) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstid);
					//如果不是审核通过的单据（通过的流程会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(zbid);
						msgMap.get(shr).add(shxxMsg);
					}
				}
				a=1;
		}
		String guids[] = zbid.split(",");
		//向当前操作人发送消息
		EchoUtil.sendMessage(new YshMessage(LUser.getGuid(),MessageType.YSHXX, guids));
		//向办理人发送消息
		EchoUtil.batchSendDshxxMsg(msgMap);
		return a;
	}
	//审核通过 方法
	/**
	 * 审批通过
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfo(OA_SHYJB shyjb,String guid,String procinstid,String shyj,String userId) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(userId, procinstid);
		String taskDefKey = ccywshDao.getTaskNodeId(task.getId());
		if("dwldsh".equals(taskDefKey)){
			doStatus(guid, "03", shyj,userId);//部门负责任审核通过，提交给分管领导审核
		}
		if("fgld".equals(taskDefKey)){
			doStatus(guid, "06", shyj,userId);//分管领导审核通过
			insertYzfsqspb(guid);
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		doAddShyj(shyjb,userId);
	}
	//更新业务表
	public int doStatus(String ywid,String zt,String shyj,String userId) {
		String sql = "UPDATE CW_CCSQSPB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+userId+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
	public int insertYzfsqspb(String guid) {
		String sql = "insert into cw_yzfsqspb(guid,djbh,jkr,szbm,jkje,sqrq,czr,czrq,sqspdh,qkje) select sys_guid() as guid,djbh,(select rybh from gx_sys_ryb where guid = sqr) as jkr," + 
				"(select dwbh from gx_sys_ryb where guid = sqr) as szbm,yzfje as jkje,sqrq,czr,czrq,guid,yzfje as qkje from cw_ccsqspb where guid = ?";
		Object[] obj = {guid};
		return db.update(sql,obj); 
	}
	/**
	 * 插入审核意见信息
	 * @param ddbh
	 * @return
	 */
	public int doAddShyj(OA_SHYJB shyjb,String userId) {
		String sql = "insert into OA_SHYJB (gid, rybh, procinstid, shyj, taskid, shzt,jdsj) values(sys_guid(), ?, ?, ?, ?, ?,to_char(sysdate,'yyyy-mm-dd hh24:Mi:ss'))";
		Object[] obj = new Object[]{
				userId, 
				shyjb.getProcinstid(), 
				shyjb.getShyj(), 
				shyjb.getTaskid(), 
				shyjb.getShzt()
		};
		int i = db.update(sql, obj);
		return i;
	}
	//审核退回方法
	@Transactional
	public void rejectleaveinfo(OA_SHYJB shyjb,String guid,String procinstid, String shyj,String userId) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(userId, procinstid);
		String taskDefKey = ccywshDao.getTaskNodeId(task.getId());
		Map<String, Object> var = runtimeService.getVariables(procinstid);
		boolean role = (boolean) var.get("role");
		variables.put("pass", false);
		variables.put("role", role);
		workflowService.completeTask(task, variables);
		if ("dwldsh".equals(taskDefKey)) {
			doStatus(guid, "04", shyj,userId);// 部门负责任审核退回
		}
		if ("fgld".equals(taskDefKey)) {
			doStatus(guid, "05", shyj,userId);//分管领导审核通过
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		doAddShyj(shyjb,userId);
	}
	//（2）日常报销审批详情  ①审批详情接口
	//查询报销主表信息 map
	public Map<String, Object> getBxzbById(String zbid) {
		StringBuffer sql= new StringBuffer();
		sql.append("select t.guid,t.djbh,ry.xm as bxr,dw.mc as szbm,t.fjzzs,t.bxzje,decode(t.sfkylbx,'1','是','0','否')as sfkylbx,t.bxsy,t.bz from cw_rcbxzb t left join gx_sys_dwb dw on dw.dwbh=t.szbm left join gx_sys_ryb ry on ry.guid=t.bxr where t.guid=? ");
		return db.queryForMap(sql.toString(),new Object[] {zbid});
	}
	//日常报销 项目明细信息
	 public List<Map<String,Object>> getXmhxList(String zbid) {
		StringBuffer sql = new StringBuffer();//fymc":"日常报销","pjzs":"1","je":"9999.00","bz":"1"
		sql.append(" select fymc as \"fymc\",bz as \"bz\",fjzs as \"pjzs\",bxje as \"je\",zfcgje as \"zfcgje\",fzfcgje as \"fzfcgje\" from CW_RCBXMXB where zbid='"+zbid+"' ");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	//对公支付list 
	 public List<Map<String,Object>> getdgzflist(String zbid){
	 StringBuffer sql = new StringBuffer();
		sql.append(" select  (select m.khyh from Cw_Wldwmxb m where m.guid=t.dfyh)yhname,");
		sql.append(" t.guid,t.zfdh,t.dfdq,t.dfzh,w.dwmc,w.guid,w.wlbh,t.dfyh,");
		sql.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je");
		sql.append(" from CW_DGZFB t");
		sql.append(" left join Cw_wldwb w on w.WLBH=t.DFDW");
		sql.append(" where 1=1 and t.zfdh='" + zbid + "'");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	 }
    //对私支付list
	 public List<Map<String,Object>> getDsList(String zbid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" '('||w.rybh||')'||w.xm as \"ryxm\" ,");
		sql.append(" t.ryxz as \"ryxz\",t.klx as \"yhmc1\",'中国建设银行' as \"yhmc\",t.dfzh as \"kh\",");
		sql.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00'))) as \"je\" ");
		sql.append(" from CW_DSZFB t ");
		sql.append(" left join gx_sys_ryb w on w.rybh=t.ryxm");
		sql.append(" where 1=1 and t.zfdh='" + zbid + "'");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
   //公务卡 list 
	 public List<Map<String,Object>> getGekList(String zbid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.ryxm or r.guid=t.ryxm) as \"ryxm\",to_char(t.skrq,'yyyy-MM-dd')as \"dfdq\",t.kh as \"kh\",");
		sql.append(" decode(nvl(t.skje,0),0,'0.00',(to_char(round(t.skje,2),'fm99999999999990.00'))) as \"je\" ");
		sql.append(" from cw_gwkb t where 1=1 and t.skdh='" + zbid + "'");
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	 //
	//②、日常报销审批提交接口
	public int rcbxsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("bh"), "");// 主表id
		String spjg = Validate.isNullToDefaultString(map.get("spjg"), "");// 审批结果（同意为T,不同意为F）
		String spyj = Validate.isNullToDefaultString(map.get("spyj"), "");// 审批意见
		String other = Validate.isNullToDefaultString(map.get("rylx"), "");// 人员类型  弹窗选择的人员类型		 yxsj 二级院系书记   yzfzr 二级院系院长或行政负责人
		String type = Validate.isNullToDefaultString(map.get("type"), "");// 审批 类型 rcbx
		String apply = Validate.isNullToDefaultString(map.get("apply"), "");
		Map getlcidmap=getlcid(zbid,"Cw_rcbxzb");
		Map dwmap=getgyryguid(userId);
		String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
		String procinstid = Validate.isNullToDefaultString(getlcidmap.get("procinstid"), "");// 流程ID
		DshInfoMap msgMap = new DshInfoMap();
		int a=0;
		GX_SYS_RYB ryb = new GX_SYS_RYB();
		ryb.setDwbh(dwbh);
		ryb.setGuid(userId);
		session.setAttribute(Const.SESSION_USER, ryb);
		if("T".equals(spjg)) {//通过
			a=1;
			service.approveLeaveInfo(session, shyjb, zbid, procinstid, spyj, other, type);
			if(Validate.noNull(procinstid)) {
				//从task表中查找流程审核人
				String shr = echoService.getShrGuid(procinstid);
				//如果不是审核通过的单据（通过的流程会在task表被删除）
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg;
					if("gwjdfbx".equals(type)){
						shxxMsg = echoService.getGwjdbxDshxxMsg(zbid);
					}else if("rcbx".equals(type)){
						shxxMsg = echoService.getRcbxDshxxMsg(zbid);
					}else{
						shxxMsg = echoService.getCcbxDshxxMsg(zbid);
					}
					
					msgMap.get(shr).add(shxxMsg);
				}
			}
		}else if("F".equals(spjg)) {//退回
			a=1;
			service.rejectleaveinfo(shyjb,session, zbid,procinstid,spyj,type,apply);
			if(Validate.noNull(procinstid)) {
				//从task表中查找流程审核人
				String shr = echoService.getShrGuid(procinstid);
				//如果不是审核通过的单据（通过的流程会在task表被删除）
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg;
					if("gwjdfbx".equals(type)){
						shxxMsg = echoService.getGwjdbxDshxxMsg(zbid);
					}else if("rcbx".equals(type)){
						shxxMsg = echoService.getRcbxDshxxMsg(zbid);
					}else{
						shxxMsg = echoService.getCcbxDshxxMsg(zbid);
					}
					msgMap.get(shr).add(shxxMsg);
				}
			}
		}
		String guids[] = zbid.split(",");
		//向当前操作人发送消息
		EchoUtil.sendMessage(new YshMessage(LUser.getGuid(),MessageType.YSHXX, guids));
		//向办理人发送消息
		EchoUtil.batchSendDshxxMsg(msgMap);
		return a;
	}
	//日常报销审核通过
	/**
	 * 审批通过
	 * @param leave
	 */
	@Transactional
	 public void approveLeaveInfo(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj,String userId) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(userId, procinstid);
		String taskDefKey=rcbxDao.getTaskNodeId(task.getId());
		String shbz=rcbxDao.getNextTaskNodeId(task.getId());//通过任务id获取
		String syr="";
		String sfbj="";
		String blr="";
		Map rcbxmap=rcbxDao.getRcbxById(guid);
		List<Map<String,Object>> bxzelist=rcbxDao.getbxzelist(guid);
		Map<String, Object> variabless = runtimeService
				.getVariables(procinstid);
		String sqr= variabless.get("applyUser")+"";
		//非科研
		String fky = variabless.get("fky")+"";
		//公务接待
		String gwjd = variabless.get("gwjd")+"";
		//科研
		String ky = variabless.get("ky")+"";
		List list=new ArrayList();
		String status = "";
		if("cwys".equals(taskDefKey)){
			if("true".equals(ky)&&!"true".equals(fky)&&!"true".equals(gwjd)){
				int result = rcdoStatus(guid,"2",shyj,userId);//修改主表审核状态科研处负责人审核
				status = "2";
			}
			if("true".equals(fky)&&!"true".equals(ky)&&!"true".equals(gwjd)){
				int result = rcdoStatus(guid,"3",shyj,userId);//修改主表审核状态部门负责人审核
				status = "3";
			}
			if("true".equals(gwjd)&&!"true".equals(fky)&&!"true".equals(ky)){
				int result = rcdoStatus(guid,"16",shyj,userId);//修改主表审核状态办公室负责人审核
				status = "16";
			}
		}
		
		if("bgsfzr".equals(taskDefKey)||"bmfzrsh".equals(taskDefKey)||"kycfzrsh".equals(taskDefKey)){
			
				int result = rcdoStatus(guid,"4",shyj,userId);//修改主表审核状态办公室负责人审核
				status = "4";
		}
		if("cwfzrsh".equals(taskDefKey)){
				int result = rcdoStatus(guid,"5",shyj,userId);//财务负责人审核通过，提交给部门分管领导
				status = "5";
		}
		if("bmfgldsh".equals(taskDefKey)){
			int result = rcdoStatus(guid,"6",shyj,userId);//部门分管领导审核通过，提交给财务分管领导
			status = "6";
	    }
		if("cwfgldsh".equals(taskDefKey)){
			int result = rcdoStatus(guid,"7",shyj,userId);//财务分管领导审核通过，提交给校长
			status = "7";
	    }
		
		if("xzsh".equals(taskDefKey)){
//			int result = rcbxDao.doStatus(guid,"8",shyj);//校长审核通过
//			status = "8";
//			pzlrService.autoCreatePzlrByRcBx(guid);
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		if(Validate.isNull(rcbxDao.getFinId(procinstid))){
			int result = rcdoStatus(guid,"8",shyj,userId);//审核通过
			if(result>0){
				String xmbh = Validate.isNullToDefaultString(rcbxmap.get("XMMC"), "");
				String bxzje = Validate.isNullToDefaultString(rcbxmap.get("BXZJE"), "0");
				if(bxzelist.size()>0) {
					for(int i=0;i<bxzelist.size();i++) {
						Object xmguid= bxzelist.get(i).get("xmguid");
						Object bcbxje= bxzelist.get(i).get("bcbxje");
						rcbxDao.updateJfsz(xmguid, bcbxje);
					}
				}
				rcbxDao.updateQkje(guid);
			}
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(status);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		rcdoAddShyj(shyjb,userId);
	}
	//日程更新业务表审核状态
	public int rcdoStatus(String ywid,String zt,String shyj,String userId) {
		String sql = "UPDATE CW_RCBXZB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+userId+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
	public int rcdoAddShyj(OA_SHYJB shyjb,String userId) {
		String sql = "insert into OA_SHYJB (gid, rybh, procinstid, shyj, taskid, shzt,jdsj) values(sys_guid(), ?, ?, ?, ?, ?,to_char(sysdate,'yyyy-mm-dd hh24:Mi:ss'))";
		Object[] obj = new Object[]{
				userId, 
				shyjb.getProcinstid(), 
				shyjb.getShyj(), 
				shyjb.getTaskid(), 
				shyjb.getShzt()
		};
		int i = db.update(sql, obj);
		return i;
	}
	/**
	 * 日常报销审核 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfo(HttpSession session, String guid,
			String procinstid, String shyj,String userId,OA_SHYJB shyjb) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(userId, procinstid);
		String taskDefKey = rcbxDao.getTaskNodeId(task.getId());
		variables.put("pass", false);
		String status = "";
		workflowService.completeTask(task, variables);
		if ("cwys".equals(taskDefKey)) {
			int result = rcdoStatus(guid, "9", shyj,userId);// 财务预审
			status="9";
		}
		if ("bmfzrsh".equals(taskDefKey)) {
			int result = rcdoStatus(guid, "11", shyj,userId);//部门负责人
			status="11";
		}
		if ("bgsfzr".equals(taskDefKey)) {
			int result = rcdoStatus(guid, "17", shyj,userId);// 办公室负责人
			status="17";
		}
		if ("kycfzrsh".equals(taskDefKey)) {
			int result = rcdoStatus(guid, "10", shyj,userId);// 财务预审
			status="10";
		}
		if ("cwfzrsh".equals(taskDefKey)) {
			int result = rcdoStatus(guid, "12", shyj,userId);// 财务负责人
			status="12";
		}
		if ("bmfgldsh".equals(taskDefKey)) {
			int result = rcdoStatus(guid, "13", shyj,userId);// 部门分管领导
			status="13";
		}
		if ("cwfgldsh".equals(taskDefKey)) {
			int result = rcdoStatus(guid, "14", shyj,userId);// 财务分管领导
			status="14";
		}
		if ("xzsh".equals(taskDefKey)) {
			int result = rcdoStatus(guid, "15", shyj,userId);// 校长
			status="15";
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(status);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		rcdoAddShyj(shyjb,userId);
	}
	/***************************11.公务接待申请*******************************/
	//（1）、获取接待部门信息
	public List<Map<String,Object>> getjdbmlist(){
		StringBuffer sql=new StringBuffer();
		sql.append(" select k.dwbh as \"departmentId\",k.mc as \"departmentName\" from gx_sys_dwb k where  1 = 1 and nvl(dwzt, '1') = '1' ");
		List<Map<String,Object>> list=null;
		try {
			list=db.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	/***************************12.差旅费申请*******************************/
	//（1）、获取出差业务
	public List<Map<String,Object>> getccywlist(int pageSize,int index,String userId){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM ( ");
		sql.append(" select sqrq as \"sqsj\",guid as \"bh\",ccts as \"ccts\", xmmc as \"xmmc\",ccrs as \"ccrs\"  from ");
		sql.append(" (select distinct TO_CHAR(A.SQRQ, 'YYYY-MM-DD') SQRQ, A.GUID,A.SQRXM, A.SZBM, A.DJBH, A.CCTS, A.CCRS, A.PROCINSTID, A.SHZT AS SHZTDM,");
		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = 'cclx'  AND C.DM = A.CCLX) AS CCLX,");
		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = 'jflx'  AND C.DM = A.SHZT) AS SHZT,");
		sql.append(" (SELECT WM_CONCAT(B.XMMC) FROM CW_CCSQSPB_XM S LEFT JOIN CW_JFSZB B  ON S.JFBH = B.guid  WHERE S.CCSQID = A.GUID) AS xmmc");
		sql.append(" from CW_CCSQSPB A LEFT JOIN CW_CCSQSPB_XM M ON A.GUID = M.CCSQID LEFT JOIN CW_JFSZB B  ON A.GUID = M.CCSQID");
		sql.append(" where 1 = 1  and A.SQR = '"+userId+"' and A.SHZT IN ('06')  and nvl(b.syje, 0) > 0 and not exists");
		sql.append(" (select 1 from cw_clfbxzb cw where cw.ccywguid = a.guid)) K where 1 = 1 order by SQRXM asc ");
		sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"'");
		List<Map<String,Object>> list=null;
		try {
			list=db.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//（2）、搜索 出差业务
	public List<Map<String,Object>> getssccywlist(String keyword,String userId){
		StringBuffer sql=new StringBuffer();
		sql.append(" select sqrq as \"sqsj\",guid as \"bh\",ccts as \"ccts\", xmmc as \"xmmc\"  from ");
		sql.append(" (select distinct TO_CHAR(A.SQRQ, 'YYYY-MM-DD') SQRQ, A.GUID,A.SQRXM, A.SZBM, A.DJBH, A.CCTS, A.CCRS, A.PROCINSTID, A.SHZT AS SHZTDM,");
		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = 'cclx'  AND C.DM = A.CCLX) AS CCLX,");
		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = 'jflx'  AND C.DM = A.SHZT) AS SHZT,");
		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = 'jflx'  AND C.DM = B.JFLX) AS JFLX,");
		sql.append(" (SELECT WM_CONCAT(B.XMMC) FROM CW_CCSQSPB_XM S LEFT JOIN CW_JFSZB B  ON S.JFBH = B.guid  WHERE S.CCSQID = A.GUID) AS xmmc");
		sql.append(" from CW_CCSQSPB A LEFT JOIN CW_CCSQSPB_XM M ON A.GUID = M.CCSQID LEFT JOIN CW_JFSZB B  ON A.GUID = M.CCSQID");
		sql.append(" where 1 = 1  and A.SQR = '"+userId+"' and xmmc like '%"+keyword+"%' and A.SHZT IN ('06')  and nvl(b.syje, 0) > 0 and not exists");
		sql.append(" (select 1 from cw_clfbxzb cw where cw.ccywguid = a.guid)) K   ");
		sql.append(" order by SQRXM asc ");
		List<Map<String,Object>> list=null;
		try {
			list=db.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//(3)
	public Map<String,Object> getssxxccywlist(String userId,String bh){
		StringBuffer sql=new StringBuffer();
		sql.append(" select sqrq as \"sqsj\",guid as \"bh\",ccts as \"ccts\" ,ccrs as \"ccrs\", xmmc as \"xmmc\"  from ");
		sql.append(" (select distinct TO_CHAR(A.SQRQ, 'YYYY-MM-DD') SQRQ, A.GUID,A.SQRXM, A.SZBM, A.DJBH, A.CCTS, A.CCRS, A.PROCINSTID, A.SHZT AS SHZTDM,");
		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = 'cclx'  AND C.DM = A.CCLX) AS CCLX,");
		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = 'jflx'  AND C.DM = A.SHZT) AS SHZT,");
//		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = 'jflx'  AND C.DM = B.JFLX) AS JFLX,");
		sql.append(" (SELECT WM_CONCAT(B.XMMC) FROM CW_CCSQSPB_XM S LEFT JOIN CW_JFSZB B  ON S.JFBH = B.guid  WHERE S.CCSQID = A.GUID) AS xmmc");
		sql.append(" from CW_CCSQSPB A LEFT JOIN CW_CCSQSPB_XM M ON A.GUID = M.CCSQID LEFT JOIN CW_JFSZB B  ON A.GUID = M.CCSQID");
		sql.append(" where 1 = 1  and A.SQR = '"+userId+"' and A.SHZT IN ('06')  and nvl(b.syje, 0) > 0 and not exists");
		sql.append(" (select 1 from cw_clfbxzb cw where cw.ccywguid = a.guid)) K where 1 = 1   ");
		if(Validate.noNull(bh)) {
			sql.append(" and guid='"+bh+"' ");
		}
		sql.append(" order by SQRXM asc ");
		Map<String,Object> map=null;
		try {
			map=db.queryForMap(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	//(4) 查询项目list
	public List getxmxxlist(String ccywguid) {
		String sql = "SELECT distinct xm.JFBH as \"xmid\",s.xmmc as \"xmmc\",to_char(s.ye,'FM999999999999.00') as \"syje\" FROM CW_CCSQSPB_XM xm left join XMINFOS s on s.guid=xm.jfbh WHERE CCSQID in ('"+ccywguid+"')";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}
	//(5) 保存 出差报销 第二布
//	public int ccbxbc(PageData pd,String zbid,String djbh) {
//		Map map = gson.fromJson(pd.getString("key"), Map.class);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String sysDate = sdf.format(new Date());
//		String userId=Validate.isNullToDefaultString(map.get("userId"), "");// userId
//		String ccywguid=Validate.isNullToDefaultString(map.get("ccywguid"), "");// 事前审批单 guid
//		String bxzje=Validate.isNullToDefaultString(map.get("bxzje"), "");// 报销总金额
//		String cclx=Validate.isNullToDefaultString(map.get("cclx"), "");// 出差类型
//		String ccrs=Validate.isNullToDefaultString(map.get("ccrs"), "");// 出差人数
//		String ccts=Validate.isNullToDefaultString(map.get("ccts"), "");// 出差天数
//		String ccrys=Validate.isNullToDefaultString(map.get("ccry"), "");// 出差人员 字符串多个逗号隔开
//		String ccry [] = ccrys.split(",");
//		String ccsy=Validate.isNullToDefaultString(map.get("ccsy"), "");// 出差事由
//		String fjzzs=Validate.isNullToDefaultString(map.get("fjzzs"), "");// 附件总张数
//		String kybx = Validate.isNullToDefaultString(map.get("kybx"), "");// 科研报销
//		if("是".equals(kybx)) {
//			kybx="1";
//		}else {
//			kybx="0";
//		}
//		List<Map> mxxxlist = (List) map.get("mxxxlist");//明细信息项
//		List<Map> xmjfxxlist = (List) map.get("xmjfxxlist");//项目经费信息list
//		String bh = Validate.isNullToDefaultString(map.get("bh"), "");// 事前审批报销单 guid
//		//保存主表信息
//		String sql = "insert into CW_CLFBXZB(guid,djbh,cclx,sqr,ccrs,ccts,jflx,xmmc,sfkylbx,bxzje,fjzzs,ccsy,sfxy,czrq,ccywguid)"
//			+ "VALUES('"+zbid+"','"+djbh+"','"+cclx+"','"+userId+"','"+ccrs+"','"+ccts+"','','','"+kybx+"','"+bxzje+"','"+fjzzs+"','"+ccsy+"','0','"+sysDate+"','"+ccywguid+"') ";
//		int a=0;
//		a=db.update(sql);
//		if(a>0) {
//			//明细信息项
//			for(int i=0;i<mxxxlist.size();i++) {
//				String kssj=Validate.isNullToDefaultString(mxxxlist.get(i).get("kssj"), "");//开始时间
//			    String jssj=Validate.isNullToDefaultString(mxxxlist.get(i).get("jssj"), "");//结束时间
//			    String cfdd=Validate.isNullToDefaultString(mxxxlist.get(i).get("cfdd"), "");//出发地点
//			    String mddd=Validate.isNullToDefaultString(mxxxlist.get(i).get("mddd"), "");//目的地点
//			    String fjje=Validate.isNullToDefaultString(mxxxlist.get(i).get("fjje"), "");//飞机金额
//			    String hcje=Validate.isNullToDefaultString(mxxxlist.get(i).get("hcje"), "");//火车金额
//			    String czje=Validate.isNullToDefaultString(mxxxlist.get(i).get("czje"), "");//出租金额
//			    String qcje=Validate.isNullToDefaultString(mxxxlist.get(i).get("qcje"), "");//汽车金额
//			    String qtfy=Validate.isNullToDefaultString(mxxxlist.get(i).get("qtfy"), "");//其他费用
//			    String hyfy=Validate.isNullToDefaultString(mxxxlist.get(i).get("hyfy"), "");//会议费用
//			    String pxfy=Validate.isNullToDefaultString(mxxxlist.get(i).get("pxfy"), "");//培训费用
//			    String zsje=Validate.isNullToDefaultString(mxxxlist.get(i).get("zsje"), "");//住宿金额
//			    String lsbzts=Validate.isNullToDefaultString(mxxxlist.get(i).get("lsbzts"), "");//老师补助天数
//			    String lsbzje=Validate.isNullToDefaultString(mxxxlist.get(i).get("lsbzje"), "");//老师补助金额
//			    String xsbzts=Validate.isNullToDefaultString(mxxxlist.get(i).get("xsbzts"), "");//学生补助天数
//			    String xsbzje=Validate.isNullToDefaultString(mxxxlist.get(i).get("xsbzje"), "");//学生补助金额
//			    String fjzs=Validate.isNullToDefaultString(mxxxlist.get(i).get("fjzs"), "");//附件张数
//			    String sql2 = "insert into CW_CLFBXMXB(guid,kssj,jssj,cfdd,mddd,djbh,fjje,hcje,czcje,qcje,qtfy,lsshbzts,lsshbzje,xsshbzts,xsshbzje,zdfje,ffjs,hyfy,pxfy,xzxmguid)"
//					+ "VALUES(sys_guid(),'"+kssj+"','"+jssj+"','"+cfdd+"','"+mddd+"','"+zbid+"','"+fjje+"','"+hcje+"','"+czje+"','"+qcje+"','"+qtfy+"','"+lsbzts+"','"+lsbzje+"','"+xsbzts+"','"+xsbzje+"','"+zsje+"','"+fjzs+"','"+hyfy+"','"+pxfy+"','') ";
//			    db.update(sql2);
//			}
//			//项目经费信息list
//			for(int j=0;j<xmjfxxlist.size();j++) {
//				String bcbxje=Validate.isNullToDefaultString(xmjfxxlist.get(j).get("bcbxje"), "");//本次报销金额
//				String xmid=Validate.isNullToDefaultString(xmjfxxlist.get(j).get("xmid"), "");//项目ID
//				String sql3="update CW_CCSQSPB_XM set BCBXJE='"+bcbxje+"' where CCSQID='"+ccywguid+"' and jfbh='"+xmid+"' ";
//				db.update(sql3);
//			}
//			//出差人员
//			for(int k=0;k<ccry.length;k++) {
//				 String rybh=Validate.isNullToDefaultString(ccry[k], "");
//				 Map dwmap=getgyryguid(rybh);
//				 String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
//				 String sql4 = "insert into CW_TXRYXXB(guid,txbh,rybh,szdw)"
//					+ "VALUES(sys_guid(),'"+zbid+"','"+rybh+"','"+dwbh+"') ";
//				 db.update(sql4);
//			}
//		}
//		return a;
//	}
	
	//(5) 保存 出差报销 第二布
		public int ccbxbc(PageData pd,String zbid,String djbh) {
			Map map = gson.fromJson(pd.getString("key"), Map.class);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sysDate = sdf.format(new Date());
			String userId=Validate.isNullToDefaultString(map.get("userId"), "");// userId
			String ccywguid=Validate.isNullToDefaultString(map.get("ccywguid"), "");// 事前审批单 guid
			String bxzje=Validate.isNullToDefaultString(map.get("bxzje"), "");// 报销总金额
			String cclx=Validate.isNullToDefaultString(map.get("cclx"), "");// 出差类型
			String ccrs=Validate.isNullToDefaultString(map.get("ccrs"), "");// 出差人数
			String ccts=Validate.isNullToDefaultString(map.get("ccts"), "");// 出差天数
			String ccrys=Validate.isNullToDefaultString(map.get("ccry"), "");// 出差人员 字符串多个逗号隔开
			String ccry [] = ccrys.split(",");
			String ccsy=Validate.isNullToDefaultString(map.get("ccsy"), "");// 出差事由
			String fjzzs=Validate.isNullToDefaultString(map.get("fjzzs"), "");// 附件总张数
			String kybx = Validate.isNullToDefaultString(map.get("kybx"), "");// 科研报销
			if("是".equals(kybx)) {
				kybx="1";
			}else {
				kybx="0";
			}
			List<Map> mxxxlist = (List) map.get("mxxxlist");//明细信息项
			List<Map> xmjfxxlist = (List) map.get("xmjfxxlist");//项目经费信息list
			List<Map> fplist = (List) map.get("fplist");//发票信息
			String bh = Validate.isNullToDefaultString(map.get("bh"), "");// 事前审批报销单 guid
			//保存主表信息
			String sql = "insert into CW_CLFBXZB(guid,djbh,cclx,sqr,ccrs,ccts,jflx,xmmc,sfkylbx,bxzje,fjzzs,ccsy,sfxy,czrq,ccywguid)"
				+ "VALUES('"+zbid+"','"+djbh+"','"+cclx+"','"+userId+"','"+ccrs+"','"+ccts+"','','','"+kybx+"','"+bxzje+"','"+fjzzs+"','"+ccsy+"','0','"+sysDate+"','"+ccywguid+"') ";
			int a=0;
			a=db.update(sql);
			System.err.println("________________________________"+a);
			if(a>0) {
				//明细信息项
				for(int i=0;i<mxxxlist.size();i++) {
					String rylx=Validate.isNullToDefaultString(mxxxlist.get(i).get("rylx"), "");//人员类型--
					String ryid=Validate.isNullToDefaultString(mxxxlist.get(i).get("ryid"), "");//人员姓名id
					String bxjb=Validate.isNullToDefaultString(mxxxlist.get(i).get("bxjb"), "");//报销级别
					
					String mdsf=Validate.isNullToDefaultString(mxxxlist.get(i).get("mdsf"), "");//目的省份
					String mdsq=Validate.isNullToDefaultString(mxxxlist.get(i).get("mdsf"), "");//目的市区
					
					String kssj=Validate.isNullToDefaultString(mxxxlist.get(i).get("kssj"), "");//开始时间
				    String jssj=Validate.isNullToDefaultString(mxxxlist.get(i).get("jssj"), "");//结束时间
				    String cfdd=Validate.isNullToDefaultString(mxxxlist.get(i).get("cfdd"), "");//出发地点
				    String mddd=Validate.isNullToDefaultString(mxxxlist.get(i).get("mddd"), "");//目的地点
				    String fjje=Validate.isNullToDefaultString(mxxxlist.get(i).get("fjje"), "");//飞机金额
				    String hcje=Validate.isNullToDefaultString(mxxxlist.get(i).get("hcje"), "");//火车金额
				    String czje=Validate.isNullToDefaultString(mxxxlist.get(i).get("czje"), "");//出租金额
				    String qcje=Validate.isNullToDefaultString(mxxxlist.get(i).get("qcje"), "");//汽车金额
				    String qtfy=Validate.isNullToDefaultString(mxxxlist.get(i).get("qtfy"), "");//其他费用
				    String hyfy=Validate.isNullToDefaultString(mxxxlist.get(i).get("hyfy"), "");//会议费用
				    String pxfy=Validate.isNullToDefaultString(mxxxlist.get(i).get("pxfy"), "");//培训费用
				    String zsje=Validate.isNullToDefaultString(mxxxlist.get(i).get("zsje"), "");//住宿金额
				    String lsbzts=Validate.isNullToDefaultString(mxxxlist.get(i).get("lsbzts"), "");//老师补助天数
				    String lsbzje=Validate.isNullToDefaultString(mxxxlist.get(i).get("lsbzje"), "");//老师补助金额
				    String xsbzts=Validate.isNullToDefaultString(mxxxlist.get(i).get("xsbzts"), "");//学生补助天数
				    String xsbzje=Validate.isNullToDefaultString(mxxxlist.get(i).get("xsbzje"), "");//学生补助金额
				    String fjzs=Validate.isNullToDefaultString(mxxxlist.get(i).get("fjzs"), "");//附件张数
				    
				    String zje=Validate.isNullToDefaultString(mxxxlist.get(i).get("zje"), "");
				    String jtbzfy=Validate.isNullToDefaultString(mxxxlist.get(i).get("jtbzfy"), "");//交通补助费用 SNJTBZF
				    String jsbzbz=Validate.isNullToDefaultString(mxxxlist.get(i).get("jsbzbz"), "");//教师补助标准 JSBZBZ
				    String jtbzbz=Validate.isNullToDefaultString(mxxxlist.get(i).get("jtbzbz"), "");//交通补助标准 SNJTBZBZ
				    String zsfbz=Validate.isNullToDefaultString(mxxxlist.get(i).get("zsfbz"), "");//住宿费标准 ZSFBZBZ
				    String hjje=Validate.isNullToDefaultString(mxxxlist.get(i).get("hjje"), "");//合计金额
				    
				    String sql2 = "insert into CW_CLFBXMXB(guid,rylx,xm,bxjb,PROVINCEID,"
				    		+ "CITYID,kssj,jssj,cfdd,mddd,"
				    		+ "djbh,fjp,hcp,qcp,qtfy,"
				    		+ "xsshbzts,hyfy,pxfy,hjje,SNJTBZF,JSBZBZ,SNJTBZBZ,ZSFBZBZ)"
						+ "VALUES(sys_guid(),'"+rylx+"','"+ryid+"','"+bxjb+"','"+mdsf+"',"
								+ "'"+mdsq+"','"+kssj+"','"+jssj+"','"+cfdd+"','"+mddd+"',"
								+ "'"+zbid+"','"+fjje+"','"+hcje+"','"+qcje+"','"+qtfy+"',"
								+ "'"+xsbzts+"','"+hyfy+"','"+pxfy+"','"+hjje+"','"+jtbzfy+"','"+jsbzbz+"','"+jtbzbz+"','"+zsfbz+"') ";
				    db.update(sql2);
				}
				//项目经费信息list
				for(int j=0;j<xmjfxxlist.size();j++) {
					String bcbxje=Validate.isNullToDefaultString(xmjfxxlist.get(j).get("bcbxje"), "");//本次报销金额
					String xmid=Validate.isNullToDefaultString(xmjfxxlist.get(j).get("xmid"), "");//项目ID
					String xmmc=Validate.isNullToDefaultString(xmjfxxlist.get(j).get("xmmc"), "");//项目名称
					String syje=Validate.isNullToDefaultString(xmjfxxlist.get(j).get("syje"), "");//剩余金额
					String sql3="update CW_CCSQSPB_XM set BCBXJE='"+bcbxje+"' where CCSQID='"+ccywguid+"' and jfbh='"+xmid+"' ";
					db.update(sql3);
				}
				//出差人员
//				for(int k=0;k<ccry.length;k++) {
//					 String rybh=Validate.isNullToDefaultString(ccry[k], "");
//					 Map dwmap=getgyryguid(rybh);
//					 String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
//					 String sql4 = "insert into CW_TXRYXXB(guid,txbh,rybh,szdw)"
//						+ "VALUES(sys_guid(),'"+zbid+"','"+rybh+"','"+dwbh+"') ";
//					 db.update(sql4);
//				}
				//发票信息
//				List<Fpxx_Bean> fplist = new ArrayList<>();//发票信息
				for(int l = 0;l<fplist.size();l++) {
					String one=Validate.isNullToDefaultString(fplist.get(l).get("one"), "");
					String two=Validate.isNullToDefaultString(fplist.get(l).get("two"), "");
					String three=Validate.isNullToDefaultString(fplist.get(l).get("three"), "");
					String four=Validate.isNullToDefaultString(fplist.get(l).get("four"), "");
					String five=Validate.isNullToDefaultString(fplist.get(l).get("five"), "");
					System.err.println("______________"+one+"_____"+two+"_______"+three+"___"+four+"___"+five);
					String sqlfp = " insert into cw_fpxxb (GUID,ZBID,FPH1,FPH2,FPH3,FPH4,FPH5) values (sys_guid(),'"+ccywguid+"','"+one+"','"+two+"','"+three+"','"+four+"','"+five+"') ";
					db.update(sqlfp);
				}
				
				
			}
			return a;
		}
	
	//出差报销审批提交
	public int ccbxsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("bh"), "");// 主表id
		String spjg = Validate.isNullToDefaultString(map.get("spjg"), "");// 审批结果（同意为T,不同意为F）
		String spyj = Validate.isNullToDefaultString(map.get("spyj"), "");// 审批意见
		String ccywguid=Validate.isNullToDefaultString(map.get("ccywguid"), "");// 出差 事前审批 guid
		String apply=Validate.isNullToDefaultString(map.get("apply"), "");// 退回字段 
		String other = Validate.isNullToDefaultString(map.get("rylx"), "");// 人员类型  弹窗选择的人员类型		 yxsj 二级院系书记   yzfzr 二级院系院长或行政负责人
		String type = Validate.isNullToDefaultString(map.get("type"), "");// 审批 类型 clfbx
		Map getlcidmap=getlcid(zbid,"CW_CLFBXZB");
		String procinstid = Validate.isNullToDefaultString(getlcidmap.get("procinstid"), "");// 流程ID
		DshInfoMap msgMap = new DshInfoMap();
		int a=0;
		Map dwmap=getgyryguid(userId);
		String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
		GX_SYS_RYB ryb = new GX_SYS_RYB();
		ryb.setDwbh(dwbh);
		ryb.setGuid(userId);
		session.setAttribute(Const.SESSION_USER, ryb);
		if("T".equals(spjg)) {//通过
			a=1;
			service.approveLeaveInfo(session, shyjb, zbid, procinstid, spyj, other, type);
			if(Validate.noNull(procinstid)) {
				//从task表中查找流程审核人
				String shr = echoService.getShrGuid(procinstid);
				//如果不是审核通过的单据（通过的流程会在task表被删除）
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg;
					if("gwjdfbx".equals(type)){
						shxxMsg = echoService.getGwjdbxDshxxMsg(zbid);
					}else if("rcbx".equals(type)){
						shxxMsg = echoService.getRcbxDshxxMsg(zbid);
					}else{
						shxxMsg = echoService.getCcbxDshxxMsg(zbid);
					}
					
					msgMap.get(shr).add(shxxMsg);
				}
			}
		}else if("F".equals(spjg)) {//退回
				a=1;
			service.rejectleaveinfo(shyjb,session, zbid,procinstid,spyj,type,apply);
			if(Validate.noNull(procinstid)) {
				//从task表中查找流程审核人
				String shr = echoService.getShrGuid(procinstid);
				//如果不是审核通过的单据（通过的流程会在task表被删除）
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg;
					if("gwjdfbx".equals(type)){
						shxxMsg = echoService.getGwjdbxDshxxMsg(zbid);
					}else if("rcbx".equals(type)){
						shxxMsg = echoService.getRcbxDshxxMsg(zbid);
					}else{
						shxxMsg = echoService.getCcbxDshxxMsg(zbid);
					}
					msgMap.get(shr).add(shxxMsg);
				}
			}
		}
		String guids[] = zbid.split(",");
		//向当前操作人发送消息
		EchoUtil.sendMessage(new YshMessage(userId,MessageType.YSHXX, guids));
		//向办理人发送消息
		EchoUtil.batchSendDshxxMsg(msgMap);
		return a;
	}
	//出差报销审核 通过
	@Transactional
	 public void ccbxshapproveLeaveInfo(HttpSession session, OA_SHYJB shyjb,String guid,
			String procinstid,String shyj,String ccywguid,String userId) {
		shyj = Validate.isNullToDefaultString(shyj, "同意");
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(userId, procinstid);
		String taskDefKey=ccbxDao.getTaskNodeId(task.getId());
		String shbz=ccbxDao.getNextTaskNodeId(task.getId());//通过任务id获取
		String syr="";
		String sfbj="";
		String blr="";
		Map<String, Object> variabless = runtimeService
				.getVariables(procinstid);
		String sqr= variabless.get("applyUser")+"";
		//非科研
		String fky = variabless.get("fky")+"";
		//公务接待
		String gwjd = variabless.get("gwjd")+"";
		//科研
		String ky = variabless.get("ky")+"";
		List list=new ArrayList();
		if("cwys".equals(taskDefKey)){
			if("true".equals(ky)&&!"true".equals(fky)&&!"true".equals(gwjd)){
				int result = ccbxshdoStatus(guid,"2",shyj,userId);//修改主表审核状态科研处负责人审核
			}
			if("true".equals(fky)&&!"true".equals(ky)&&!"true".equals(gwjd)){
				int result = ccbxshdoStatus(guid,"3",shyj,userId);//修改主表审核状态部门负责人审核
			}
			if("true".equals(gwjd)&&!"true".equals(fky)&&!"true".equals(ky)){
				int result = ccbxshdoStatus(guid,"16",shyj,userId);//修改主表审核状态办公室负责人审核
			}
		}
		
		if("bgsfzr".equals(taskDefKey)||"bmfzrsh".equals(taskDefKey)||"kycfzrsh".equals(taskDefKey)){
			
				int result = ccbxshdoStatus(guid,"4",shyj,userId);//修改主表审核状态办公室负责人审核
		}
		if("cwfzrsh".equals(taskDefKey)){
				int result = ccbxshdoStatus(guid,"5",shyj,userId);//财务负责人审核通过，提交给部门分管领导
		}
		if("bmfgldsh".equals(taskDefKey)){
			int result = ccbxshdoStatus(guid,"6",shyj,userId);//部门分管领导审核通过，提交给财务分管领导
	    }
		if("cwfgldsh".equals(taskDefKey)){
			int result = ccbxshdoStatus(guid,"7",shyj,userId);//财务分管领导审核通过，提交给校长
	    }
		
		if("xzsh".equals(taskDefKey)){
			int result = ccbxshdoStatus(guid,"8",shyj,userId);//校长审核通过
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		Map ccbxmap=ccbxDao.getRcbxById(guid);
		List<Map<String,Object>> bxzelist=getshbxzelist(ccywguid);
		if(Validate.isNull(ccbxDao.getFinId(procinstid))){
			int result = ccbxshdoStatus(guid,"8",shyj,userId);//审核通过
			if(result>0){
				String xmbh = Validate.isNullToDefaultString(ccbxmap.get("XMMC"), "");
				String bxzje = Validate.isNullToDefaultString(ccbxmap.get("BXZJE"), "0");
				if(bxzelist.size()>0) {
					for(int i=0;i<bxzelist.size();i++) {
						Object JFBH= bxzelist.get(i).get("JFBH");
						Object BCBXJE= bxzelist.get(i).get("BCBXJE");
						ccbxDao.updateJfsz(JFBH, BCBXJE);
					}
				}
				ccbxDao.updateQkje(guid);
			}
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		ccbxshdoAddShyj(shyjb,userId);
	}
	public List<Map<String,Object>> getshbxzelist(String guid) {
    	StringBuffer sql = new StringBuffer();
		sql.append(" select guid,jfbh,ccsqid,bcbxje from CW_CCSQSPB_XM where ccsqid='"+guid+"' ");
		List<Map<String,Object>> list = null;
		list = db.queryForList(sql.toString());
		return list;
    }
	//出差报销审核 退回
	@Transactional
	public void ccbxshrejectleaveinfo(HttpSession session, String guid,
			String procinstid, String shyj,String userId,OA_SHYJB shyjb) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(
			userId, procinstid);
		String taskDefKey = ccbxDao.getTaskNodeId(task.getId());
		variables.put("pass", false);
		workflowService.completeTask(task, variables);
		if ("cwys".equals(taskDefKey)) {
			int result = ccbxshdoStatus(guid, "9", shyj,userId);// 财务预审
		}
		if ("bmfzrsh".equals(taskDefKey)) {
			int result = ccbxshdoStatus(guid, "11", shyj,userId);//部门负责人
		}
		if ("bgsfzr".equals(taskDefKey)) {
			int result = ccbxshdoStatus(guid, "17", shyj,userId);// 办公室负责人
		}
		if ("kycfzrsh".equals(taskDefKey)) {
			int result = ccbxshdoStatus(guid, "10", shyj,userId);// 财务预审
		}
		if ("cwfzrsh".equals(taskDefKey)) {
			int result = ccbxshdoStatus(guid, "12", shyj,userId);// 财务负责人
		}
		if ("bmfgldsh".equals(taskDefKey)) {
			int result = ccbxshdoStatus(guid, "13", shyj,userId);// 部门分管领导
		}
		if ("cwfgldsh".equals(taskDefKey)) {
			int result = ccbxshdoStatus(guid, "14", shyj,userId);// 财务分管领导
		}
		if ("xzsh".equals(taskDefKey)) {
			int result = ccbxshdoStatus(guid, "15", shyj,userId);// 校长
		}
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		shyjb.setShyj(shyj);
		ccbxshdoAddShyj(shyjb,userId);
	}
	//更新 出差报销 审核 业务表审核状态
	public int ccbxshdoStatus(String ywid,String zt,String shyj,String userId) {
		String sql = "UPDATE CW_CLFBXZB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+userId+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
	//插入审核意见信息
	public int ccbxshdoAddShyj(OA_SHYJB shyjb,String userId) {
		String sql = "insert into OA_SHYJB (gid, rybh, procinstid, shyj, taskid, shzt,jdsj) values(sys_guid(), ?, ?, ?, ?, ?,to_char(sysdate,'yyyy-mm-dd hh24:Mi:ss'))";
		Object[] obj = new Object[]{
				userId, 
				shyjb.getProcinstid(), 
				shyjb.getShyj(), 
				shyjb.getTaskid(), 
				shyjb.getShzt()
		};
		int i = db.update(sql, obj);
		return i;
	}
	//冲借款

	public List getCxjkListHx(String userId,String rybh) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append("   ry.xm as \"jkr\",a.djbh as \"djbh\", TO_CHAR(ROUND(A.JKzJE, 2), 'fm999999999999999999990.00') AS \"JKJE\", ");
		sql.append("  (select '(' || dwbh || ')' || mc from gx_sys_dwb where dwbh = a.szbm) as \"szbm\" from CW_JKYWB a  ");
		sql.append(" left join gx_sys_ryb ry on ry.guid=a.jkr   ");
		sql.append(" where 1 = 1 and (a.jkr = '"+userId+"' or a.jkr = '"+rybh+"' ) and JKzJE > 0 and SHZT = '8' order by DJBH desc ");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		System.err.println("sql=="+sql);
		return list;
	}
	//出差报销 保存信息 并开启流程
	public int ccbxthreetj(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("zbid"), "");// 主表id
		String sqspguid = Validate.isNullToDefaultString(map.get("bh"), "");// 事前审批guid
		String djbh = Validate.isNullToDefaultString(map.get("djbh"), "");// 单据编号
		String sfcjk = Validate.isNullToDefaultString(map.get("sfcjk"), "");// 是否冲借款
		String sfdszf = Validate.isNullToDefaultString(map.get("sfdszf"), "");// 是否对公支付
		String sfdgzf = Validate.isNullToDefaultString(map.get("sfdgzf"), "");// 是否对私支付
		String sfgwk = Validate.isNullToDefaultString(map.get("sfgwk"), "");// 是否公务卡
		int a =0;
		//更新主表信息
		String sql1=" update CW_CLFBXZB set sfdszf='"+sfdszf+"',sfdgzf='"+sfdgzf+"',sfgwk='"+sfgwk+"',sfcjk='"+sfcjk+"' where guid='"+zbid+"' ";
		a=db.update(sql1);
		List<Map> cjklist = (List) map.get("cjklist");//冲借款list
		List<Map> dszflist = (List) map.get("dszflist");//对私支付list
		List<Map> dgzflist = (List) map.get("dgzflist");//对公支付list
		List<Map> gwklist = (List) map.get("gwklist");//公务卡list
		//对私支付 list遍历保存方法
		String sql2="";
		String delsql2=" delete from Cw_DSZFB where zfdh ='"+zbid+"' ";
		db.update(delsql2);
		for(int i=0;i<dszflist.size();i++) {
			String ryxz=Validate.isNullToDefaultString(dszflist.get(i).get("rylx"), "");// 1.本人 0.项目负责人 2.其他人
			String ryid=Validate.isNullToDefaultString(dszflist.get(i).get("ryid"), "");// 人员id  
			Map dwmap=getgyryguid(ryid);
		    String rygubh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
			String yhmc=Validate.isNullToDefaultString(dszflist.get(i).get("yhmc"), "");// 卡类型 (银行名称)
			String kh=Validate.isNullToDefaultString(dszflist.get(i).get("yhkh"), "");// 对方卡号
			String je=Validate.isNullToDefaultString(dszflist.get(i).get("je"), "");// 金额
			sql2="insert into Cw_DSZFB(guid,zfdh,ryxz,ryxm,klx,dfzh,je,bxlx) VALUES(sys_guid(),'"+zbid+"','"+ryxz+"','"+rygubh+"','"+yhmc+"','"+kh+"','"+je+"','0')";
			db.update(sql2);
		}
		//对公支付 list遍历保存方法
		String sql3="";
		String delsql3=" delete from CW_DGZFB where zfdh ='"+zbid+"' ";
		db.update(delsql3);
		for(int j=0;j<dgzflist.size();j++) {
			String dfdq=Validate.isNullToDefaultString(dgzflist.get(j).get("dfdq"), "");// 地方地区
			String wlbh=Validate.isNullToDefaultString(dgzflist.get(j).get("wlbh"), "");// 对方单位 对应往来编号
			String dfyh=Validate.isNullToDefaultString(dgzflist.get(j).get("dfyh"), "");// 对方银行
			String dfzh=Validate.isNullToDefaultString(dgzflist.get(j).get("dfzh"), "");// 对方账号
			String dfdw=Validate.isNullToDefaultString(dgzflist.get(j).get("dfdw"), "");// wlbh
			String je=Validate.isNullToDefaultString(dgzflist.get(j).get("je"), "");// 金额
			sql3="insert into CW_DGZFB(guid,zfdh,dfdw,dfdq ,dfyh ,dfzh, je, bxlx ) VALUES(sys_guid(),'"+zbid+"','"+dfdw+"','"+dfdq+"','"+dfyh+"','"+dfzh+"','"+je+"','0')";
			db.update(sql3);
		}
		//公务卡 list遍历保存方法
		String sql4="";
		String delsql4=" delete from CW_GWKB where skdh ='"+zbid+"' ";
		db.update(delsql4);
		for(int k=0;k<gwklist.size();k++) {
			String skje=Validate.isNullToDefaultString(gwklist.get(k).get("skje"), "");// 刷卡金额
			String kh=Validate.isNullToDefaultString(gwklist.get(k).get("kh"), "");// 卡号
			String skri=Validate.isNullToDefaultString(gwklist.get(k).get("skri"), "");// skri
			Map dwmap=getgyryguid(userId);
			String rygwkbh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
			sql4="insert into CW_GWKB(guid ,skdh ,szbm ,ryxm ,skrq ,skje ,kh ,bxlx ) VALUES(sys_guid(),'"+zbid+"','','"+rygwkbh+"',to_date(?,'yyyy-MM-dd HH24:mi'),'"+skje+"','"+kh+"','0') ";
			db.update(sql4,new Object[] {skri});
		}
		//冲借款 list保存方法
		Map dwmap=getgyryguid(userId);
		String delsql5=" delete from CW_CJKB where jkdh ='"+zbid+"' ";
		db.update(delsql5);
		 String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
		for(int c=0;c<cjklist.size();c++) {
			String jkje=Validate.isNullToDefaultString(cjklist.get(c).get("jkje"), "");//
			String cjkje=Validate.isNullToDefaultString(cjklist.get(c).get("cjkje"), "");//
		    String ryjkkbh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
			String sql5 = "insert into CW_CJKB(guid,jkdh,jkr,szbm,jkje,cjkje,bxlx,jkid)"
				+ "VALUES(sys_guid(),'"+zbid+"','"+ryjkkbh+"','"+dwbh+"','"+jkje+"','"+cjkje+"','0','"+djbh+"') ";
			db.update(sql5);
		}
		return a;
	}
	//更新业务表审核状态  出差报销 lc
	public int ccbxlcdoStatus(String userId,String ywid,String zt,String shyj) {
		String sql = "UPDATE CW_CLFBXZB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+userId+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
	/***************************14.借款流程 申请*******************************/
	//对公支付  单位信息获取
	public List<Map<String,Object>> dwxxlist(){
		String sql=" select  t.dwmc,dwdz,t.guid,t.lxr,t.bgdh,t.wlbh from  cw_wldwb t  where 1=1   order by DWMC desc ";
		return db.queryForList(sql);
	}
	//银行信息获取 对公支付
	public List<Map<String,Object>> yhxxhqlist(Object object){
		StringBuffer sql=new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,khyh,khyhzh");
		sql.append(" from Cw_wldwmxb t where 1=1");
		sql.append(" and t.wlbh=(select guid from CW_WLDWB where wlbh='"+object+"')");
		return db.queryForList(sql.toString());
	}
	//借款申请提交 保存
	public int insertjksq(PageData pd,String zbid,String djbh) {
		Map map=gson.fromJson(pd.getString("key"), Map.class);
		String userId=Validate.isNullToDefaultString(map.get("userId"), "");//当前登录人guid
		String jkr=Validate.isNullToDefaultString(map.get("jkr"), "");// 借款人  存的guid
		String dwbh=Validate.isNullToDefaultString(map.get("dwbh"), "");// 所在部门  dwbh
		String jksj=Validate.isNullToDefaultString(map.get("jksj"), "");//借款时间
		String jkzje=Validate.isNullToDefaultString(map.get("jkzje"), "");//借款总金额
		String jksy=Validate.isNullToDefaultString(map.get("jksy"), "");//借款事由
		String zffs=Validate.isNullToDefaultString(map.get("zffs"), "");//支付方式  0.对私 1.对公
		String sql="";//主表sql
		String sql1="";//对私支付sql
		String sql2="";//对公支付sql
		String sql3="";//项目信息list
		sql=" insert into CW_JKYWB(gid,djbh,jkr,szbm,jksj,jkzje,jksy,shzt,zffs) values('"+zbid+"','"+djbh+"','"+jkr+"','"+CommonUtil.getBeginText(dwbh)+"',to_date('"+jksj+"','yyyy-MM-dd'),'"+jkzje+"','"+jksy+"','02','"+zffs+"') ";
		if("0".equals(zffs)) {
			List<Map> dszflist = (List) map.get("dszflist");//对私支付list
			for(int i=0;i<dszflist.size();i++) {
				String ryxz=Validate.isNullToDefaultString(dszflist.get(i).get("rylx"), "");// 1.本人 0.项目负责人 2.其他人
				String ryid=Validate.isNullToDefaultString(dszflist.get(i).get("ryid"), "");// 人员id  
				Map dwmap=getgyryguid(ryid);
			    String rygubh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
				String yhmc=Validate.isNullToDefaultString(dszflist.get(i).get("yhmc"), "");// 卡类型 (银行名称)
				String kh=Validate.isNullToDefaultString(dszflist.get(i).get("yhkh"), "");// 对方卡号
				String je=Validate.isNullToDefaultString(dszflist.get(i).get("je"), "");// 金额
				String delsql1=" delete from Cw_DSZFB where zfdh ='"+zbid+"' ";
				db.update(delsql1);
				sql1="insert into Cw_DSZFB(guid,zfdh,ryxz,ryxm,klx,dfzh,je,bxlx) VALUES(sys_guid(),'"+zbid+"','"+ryxz+"','"+rygubh+"','"+yhmc+"','"+kh+"','"+je+"','0')";
				db.update(sql1);
			}
		}else {
			List<Map> dgzflist = (List) map.get("dgzflist");//对公支付list
			for(int j=0;j<dgzflist.size();j++) {
				String dfdq=Validate.isNullToDefaultString(dgzflist.get(j).get("dfdq"), "");// 地方地区
				String wlbh=Validate.isNullToDefaultString(dgzflist.get(j).get("wlbh"), "");// 对方单位 对应往来编号
				String dfyh=Validate.isNullToDefaultString(dgzflist.get(j).get("dfyh"), "");// 对方银行
				String dfzh=Validate.isNullToDefaultString(dgzflist.get(j).get("dfzh"), "");// 对方账号
				String dfdw=Validate.isNullToDefaultString(dgzflist.get(j).get("dfdw"), "");// wlbh
				String je=Validate.isNullToDefaultString(dgzflist.get(j).get("je"), "");// 金额
				String delsql2=" delete from CW_DGZFB where zfdh ='"+zbid+"' ";
				db.update(delsql2);
				sql2="insert into CW_DGZFB(guid,zfdh,dfdw,dfdq ,dfyh ,dfzh, je, bxlx ) VALUES(sys_guid(),'"+zbid+"','"+dfdw+"','"+dfdq+"','"+dfyh+"','"+dfzh+"','"+je+"','0')";
				db.update(sql2);
			}
		}
		List<Map> xmxxlist=(List)map.get("xmxxlist");//项目信息list  
		for(int k=0;k<xmxxlist.size();k++) {
			String jfbh=Validate.isNullToDefaultString(xmxxlist.get(k).get("jfbh"), "");//经费编号 项目的guid 
			String zcje=Validate.isNullToDefaultString(xmxxlist.get(k).get("zcje"), "");//支付金额
			String delsql3=" delete from CW_JKYWB_MXB where jkid='"+zbid+"' ";
			db.update(delsql3);
			sql3="insert into CW_JKYWB_MXB(gid,jkid,jfbh,zcje) values(sys_guid(),'"+zbid+"','"+jfbh+"','"+zcje+"')" ;
			db.update(sql3);
		}
		return db.update(sql);
	}
	//借款审批提交
	public int jksptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("zbid"), "");// 主表id
		String spjg = Validate.isNullToDefaultString(map.get("spjg"), "");// 审批结果（同意为T,不同意为F）
		String spyj = Validate.isNullToDefaultString(map.get("spyj"), "");// 审批意见
		String apply=Validate.isNullToDefaultString(map.get("apply"), "");// 
		String procinstid =  jksqService.sjsubmitBySqr(zbid);
	    Map dwmap=getgyryguid(userId);
	    String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
	    GX_SYS_RYB ryb = new GX_SYS_RYB();
		ryb.setDwbh(dwbh);
		ryb.setGuid(userId);
		session.setAttribute(Const.SESSION_USER, ryb);
		DshInfoMap msgMap = new DshInfoMap();
		int a=0;
		if("T".equals(spjg)) {//通过
			a=1;
			jksqService.approveLeaveInfo( session,shyjb, zbid,procinstid,spyj);
			if(Validate.noNull(procinstid)) {
				//从task表中查找流程审核人
				String shr = echoService.getShrGuid(procinstid);
				//如果审核人存在
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(zbid);
					msgMap.get(shr).add(shxxMsg);
				}
			}
		}else if("F".equals(spjg)) {//退回
			a=1;
			jksqService.rejectleaveinfo(session, shyjb,zbid,procinstid,spyj,apply);
			if(Validate.noNull(procinstid)) {
				//从task表中查找流程审核人
				String shr = echoService.getShrGuid(procinstid);
				//如果不是审核通过的单据（通过的流程会在task表被删除）
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(zbid);
					msgMap.get(shr).add(shxxMsg);
				}
			}
		}
		String guids[] = zbid.split(",");
		//向当前操作人发送消息
		EchoUtil.sendMessage(new YshMessage(userId,MessageType.YSHXX, guids));
		//向办理人发送消息
		EchoUtil.batchSendDshxxMsg(msgMap);
		return a;
	}
	//首页实发合计 
	public Map<String,Object> getsfhjxx(String rybh,String time){
		time=time.replace("-", ".");
//		String sql="select SFHJ FROM CW_XZB WHERE gh='"+rybh+"' and gzyf='"+time+"' ";
		String sql="select SFHJ FROM CW_XZB WHERE gh='"+rybh+"' and gzyf='"+time+"' and shzt ='2' ";

		System.err.println("sql==>"+sql);
		return db.queryForMap(sql);
	}
	/*********************15.我的薪酬**************************/
	//(1)我的薪酬
	public Map<String,Object> wdxcmap(String rybh,String time){
		StringBuffer sql=new StringBuffer();
		String sytime=time.substring(0, 7);
		System.err.println("sytime==>"+sytime);
		sql.append(" select T.GUID,T.RYBH,T.XM,T.BM,T.RYLB,T.RYLX,DECODE(T.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回') AS SHZT, " +
			"TO_CHAR(T.GWGZ,'FM9999990.00') AS GWGZ,TO_CHAR(T.XJGZ,'FM9999990.00') AS XJGZ,TO_CHAR(T.XZFT,'FM9999990.00') AS XZFT," +
			"TO_CHAR(T.WYBT,'FM9999990.00') AS WYBT,TO_CHAR(T.DSZF,'FM9999990.00') AS DSZF,TO_CHAR(T.JCJX,'FM9999990.00') AS JCJX," +
			"TO_CHAR(T.JLJX1,'FM9999990.00') AS JLJX1,TO_CHAR(T.BSBT,'FM9999990.00') AS BSBT," +
			"TO_CHAR(T.GWBT,'FM9999990.00') AS GWBT,TO_CHAR(T.BXQZBBT,'FM9999990.00') AS BXQZBBT,TO_CHAR(T.SYBT,'FM9999990.00') AS SYBT," +
			"TO_CHAR(T.JHBT,'FM9999990.00') AS JHBT,TO_CHAR(T.ZJBT,'FM9999990.00') AS ZJBT,TO_CHAR(T.HTBT,'FM9999990.00') AS HTBT," +
			"TO_CHAR(T.QTBT,'FM9999990.00') AS QTBT," +
			"TO_CHAR(T.BGZ,'FM9999990.00') AS BGZ,TO_CHAR(T.JKF,'FM9999990.00') AS JKF,TO_CHAR(T.FZGZL,'FM9999990.00') AS FZGZL," +
			"TO_CHAR(T.ZSJL,'FM9999990.00') AS ZSJL,TO_CHAR(T.FDYYJZBBT,'FM9999990.00') AS FDYYJZBBT,TO_CHAR(T.KHJ,'FM9999990.00') AS KHJ," +
			"TO_CHAR(T.DHF,'FM9999990.00') AS DHF,TO_CHAR(T.BT,'FM9999990.00') AS BT,TO_CHAR(T.ZFBT,'FM9999990.00') AS ZFBT," +
			"TO_CHAR(T.YFHJ,'FM999999.00') AS YFHJ," +
			"TO_CHAR(T.JIANGKEF,'FM9999990.00') AS JIANGKEF,TO_CHAR(T.BGZKS,'FM9999990.00') AS BGZKS,TO_CHAR(T.JSX,'FM9999990.00') AS JSX," +
			"TO_CHAR(T.QNJSX,'FM9999990.00') AS QNJSX,TO_CHAR(T.QNJSX1,'FM9999990.00') AS QNJSX1,TO_CHAR(T.QNJSX2,'FM9999990.00') AS QNJSX2," +
			"TO_CHAR(T.QNJSX3,'FM9999990.00') AS QNJSX3,TO_CHAR(T.BJCXJXGZ2014JSJS,'FM9999990.00') AS BJCXJXGZ2014JSJS,TO_CHAR(T.BXBT2014N1D10YJSJS,'FM9999990.00') AS BXBT2014N1D10YJSJS," +
			"TO_CHAR(T.JSJS,'FM9999990.00') AS JSJS," +
			"TO_CHAR(T.ZFJJ,'FM9999990.00') AS ZFJJ,TO_CHAR(T.PGJJ,'FM9999990.00') AS PGJJ,TO_CHAR(T.YLBX,'FM9999990.00') AS YLBX," +
			"TO_CHAR(T.BGJJ,'FM9999990.00') AS BGJJ,TO_CHAR(T.DKS,'FM9999990.00') AS DKS,TO_CHAR(T.BNSE,'FM9999990.00') AS BNSE," +
			"TO_CHAR(T.SNSE,'FM9999990.00') AS SNSE,TO_CHAR(T.BS,'FM9999990.00') AS BS,TO_CHAR(T.FZ,'FM9999990.00') AS FZ," +
			"TO_CHAR(T.SYJ,'FM9999990.00') AS SYJ," +
			"TO_CHAR(T.NQF,'FM9999990.00') AS NQF,TO_CHAR(T.NQF1,'FM9999990.00') AS NQF1,TO_CHAR(T.WYF,'FM9999990.00') AS WYF," +
			"TO_CHAR(T.SBJ,'FM9999990.00') AS SBJ,TO_CHAR(T.SJDCK,'FM9999990.00') AS SJDCK,TO_CHAR(T.KK,'FM9999990.00') AS KK," +
			"TO_CHAR(T.YLJ,'FM9999990.00') AS YLJ,TO_CHAR(T.JK,'FM9999990.00') AS JK,TO_CHAR(T.AXYRJ,'FM9999990.00') AS AXYRJ," +
			"TO_CHAR(T.KKHJ,'FM9999990.00') AS KKHJ," +
			"TO_CHAR(T.SFHJ,'FM9999990.00') AS SFHJ,T.GZYF,T.BH," +
			"T.XH,TO_CHAR(T.JTF,'FM9999990.00') AS JTF,TO_CHAR(T.NZJ,'FM9999990.00') AS NZJ," +
			"TO_CHAR(T.NZJDKS,'FM9999990.00') AS NZJDKS,TO_CHAR(T.GZDKS,'FM9999990.00') AS GZDKS,TO_CHAR(T.KSHJ,'FM9999990.00') AS KSHJ," +
			"T.GH,T.SFZB,TO_CHAR(T.BKYLBX,'FM9999990.00') AS BKYLBX," +
			"TO_CHAR(T.BKSYJ,'FM9999990.00') AS BKSYJ,TO_CHAR(T.BKYLJ,'FM9999990.00') AS BKYLJ,TO_CHAR(T.BKSBJ,'FM9999990.00') AS BKSBJ," +
			"T.SFDY,T.RDQK  from CW_XZB T where 1 = 1 and t.shzt like '%2%' and t.gzyf like '%"+sytime+"%'  and t.gh='"+rybh+"' order by XM asc");
		System.err.println("查询我的薪酬sql="+sql);
		return db.queryForMap(sql.toString());
	}
	//查询发票信息list
	public List<Map<String,Object>> getFpxxlist(String zbid){
			String sql = "select fph1 as \"one\" ,fph2 as \"two\",fph3 as \"three\" ,fph4 as \"four\",fph5 as \"five\" from cw_fpxxb where zbid='"+zbid+"'";
			return db.queryForList(sql);
	}
	public String getprocinstid(String zbid,String bm) {
		String sql="select PROCINSTID from "+bm+" where guid='"+zbid+"' ";
		return db.queryForSingleValue(sql);
	}
}
