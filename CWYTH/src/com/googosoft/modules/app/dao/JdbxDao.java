package com.googosoft.modules.app.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.googosoft.JSON.JsonArray;
import com.googosoft.JSON.JsonObject;
import com.googosoft.JSON.KeysValues;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.controller.wsbx.gwjdfbx.gwjdfbxsqExportExcel;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.wsbx.gwjd.GwjdSqDao;
import com.googosoft.dao.wsbx.gwjdfbx.GwjdfbxsqDao;
import com.googosoft.pojo.ImgInfo;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.gwjdf.CW_GWJDFSQ;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.service.wsbx.gwjd.GwjdSqService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.service.wsbx.process.WsbxProcessService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Const;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

/**
*@author 杨超超
*@date   2018年2月1日---下午8:46:23
*/
@Repository("jdbxdao")
public class JdbxDao extends BaseDao{
	@Resource(name = "ywcdao")
	private YwcDao ywcdao;
	@Resource(name="gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;
	@Resource(name = "ProcessService")
	private WsbxProcessService service;
	@Autowired
	private EchoService echoService;
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	@Resource(name="gwjdfbxsqDao")
	private GwjdfbxsqDao gwjdfbxsqDao;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	@Resource(name="pzlrService")
	private PzlrService pzlrService;
	@Resource(name="gwjdsqService")
	private GwjdSqService gwjdsqService;
	@Resource(name="gwjdsqDao")
	public GwjdSqDao gwjdsqDao;
	Gson gson = new Gson();
	//(1) 根据rybh查询ryguid  和 部门编号  bmbh(DWBH)
	public Map getgyryguid(String userId) {
		Map map = null;
		String sql = " SELECT ry.GUID,ry.RYBH,ry.DWBH,ry.XM as ryxm,dw.mc as dwmc FROM gx_sys_ryb ry left join gx_sys_dwb dw on dw.dwbh=ry.dwbh WHERE ry.GUID='" + userId + "' ";
		try {
			map = db.queryForMap(sql);
		} catch (Exception e) {
		}
		return map;
	}
	/***************************13.接待报销申请列表接口*******************************/
	// 接待报销申请列表接口
	public List<Map<String,Object>> getjdbxlist(String userId,int  index,int pageSize){
		Map rymap=getgyryguid(userId);
		String rybh= Validate.isNullToDefaultString(rymap.get("RYBH"),"");
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
		sql.append(" select a.guid,a.djbh,a.jdsy as jdsy,(select '('||RYBH||')'||xm from gx_sys_ryb where rybh = a.sqr) as sqr,(select '('||DWBH||')'||mc from gx_sys_dwb where dwbh = a.jdbm) as jddw ,jdrq,LBDW,JDFD ");
		sql.append(" from CW_GWJDYWSQSPB a where 1=1  ");
//		sql.append(" and ( SQR = '"+rybh+"' or jzbxr = '"+rybh+"' )  and a.guid not in (select sqspbh from cw_gwjdbxdzb) and a.shzt = '06' ");//and a.shzt = '06'
		sql.append("  and a.guid not in (select sqspbh from cw_gwjdbxdzb) and a.shzt = '06'  ");
		sql.append("  ) A  WHERE ROWNUM < "+pageSize*(index)+"  order by jdrq desc )WHERE RN >= "+ pageSize*(index-1)+" ");
		return db.queryForList(sql.toString());
	}
	// 接待报销申请列表搜索接口
	public List<Map<String,Object>> jdbxsqlistss(String userId,int  index,int pageSize,String keyword){
		Map rymap=getgyryguid(userId);
		String rybh= Validate.isNullToDefaultString(rymap.get("RYBH"),"");
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
		sql.append(" select a.guid,a.djbh,a.jdsy as jdsy,(select '('||RYBH||')'||xm from gx_sys_ryb where rybh = a.sqr) as sqr,(select '('||DWBH||')'||mc from gx_sys_dwb where dwbh = a.jdbm) as jddw ,jdrq,LBDW,JDFD ");
		sql.append(" from CW_GWJDYWSQSPB a where 1=1  ");
		sql.append(" and ( SQR = '"+rybh+"' or jzbxr = '"+rybh+"' ) and a.shzt = '06' and a.guid not in (select sqspbh from cw_gwjdbxdzb) and a.jdsy like '%"+keyword+"%'  ");
		sql.append("  ) A  WHERE ROWNUM < "+pageSize*(index)+"  order by jdrq desc )WHERE RN >= "+ pageSize*(index-1)+" ");
		return db.queryForList(sql.toString());
	}
	// 接待报销申请第二步接口
	public Map getjdbxsqmap(String zbid) {
		StringBuffer sql=new StringBuffer();
		sql.append(" select (select mc from gx_sys_dwb where dwbh = t.jdbm) as jdbmmc,t.* from CW_GWJDYWSQSPB t where t.guid='"+zbid+"' ");
		return db.queryForMap(sql.toString());
	}
	//  接待报销申请第二步信息提交接口
	public int jdbxsave(PageData pd,String zbid,String djbh,String bh) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId=Validate.isNullToDefaultString(map.get("userId"),"");
//		String bh=Validate.isNullToDefaultString(map.get("bh"),"");//公务接待事前审批guid
		String bxje=Validate.isNullToDefaultString(map.get("bxje"),"");
		String hdxm=Validate.isNullToDefaultString(map.get("gwhdxm"),"");
		String bxr=Validate.isNullToDefaultString(map.get("bxr"),"");
		String szbm=Validate.isNullToDefaultString(map.get("szbm"),"");
		String jdrq=Validate.isNullToDefaultString(map.get("jdrq"),"");
		String jdbm=Validate.isNullToDefaultString(map.get("jdbm"),"");
		String lbdw=Validate.isNullToDefaultString(map.get("lbdw"),"");
		String jddd=Validate.isNullToDefaultString(map.get("jddd"),"");
		String lbxmrs=Validate.isNullToDefaultString(map.get("lbxx"),"");
		String ptxmrs=Validate.isNullToDefaultString(map.get("zdry"),"");
		String jdsy=Validate.isNullToDefaultString(map.get("jdsy"),"");
	    String lbxm=Validate.isNullToDefaultString(map.get("lbxm"),"");;//来宾姓名
        String lbzw=Validate.isNullToDefaultString(map.get("lbzw"),"");;//来宾职务
        String lxdh=Validate.isNullToDefaultString(map.get("lxdh"),"");;//联系电话
        String txry=Validate.isNullToDefaultString(map.get("txry"),"");;//同行人员
		//报销人guid转换rybh
		Map rymap=getgyryguid(bxr);
		String bxry= Validate.isNullToDefaultString(rymap.get("RYBH"),"");
		StringBuffer sql=new StringBuffer();
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		//保存主表信息
		sql.append("insert into cw_gwjdfbxzb (guid,djbh,bxryid,bxry,szbm,bxje,jdcs,jdrq,lbry,ptry,jdsy,gwhdxm,shzt,czr,czrq,sfxy,sfgwk,sfdgzf,sfdszf,sfcjk,lbdw,jdbm) "
			+ "values('"+zbid+"','"+djbh+"','"+bxr+"','"+bxry+"','"+szbm+"','"+bxje+"','"+lbxmrs+"',to_date('"+jdrq+"','yyyy-mm-dd hh24:mi'),'"+lbxmrs+"','"+ptxmrs+"','"+jdsy+"','"+hdxm+"','0','"+bxr+"','"+datetime+"',(select sfxy from gx_sys_dwb where dwbh = (select dwbh from gx_sys_ryb where guid = '"+bxr+"')),'0','0','0','0','"+lbdw+"','"+jdbm+"') "); 
		List <Map> imglist=(List)map.get("imglist");
		int i = db.update(sql.toString());
		String sqlbxdzb = "insert into cw_gwjdbxdzb (guid,bxbh,sqspbh) values(sys_guid(),'"+zbid+"','"+bh+"')";
		db.update(sqlbxdzb);
		//附件保存方法   图片
		for(int k=0;k<imglist.size();k++ ) {
			Map rymap1=getgyryguid(userId);
			String rybh= Validate.isNullToDefaultString(rymap.get("RYBH"),"");
			String newname=Validate.isNullToDefaultString(imglist.get(k).get("newname"),"");
			String oldname=Validate.isNullToDefaultString(imglist.get(k).get("oldname"),"");
			newname=newname.substring(newname.lastIndexOf("/phone"));
			String delsql2="delete from zc_xgwd where dbh=?";
			db.update(delsql2,new Object[] {zbid});
			String sql2 = "insert into zc_xgwd(guid,djlx,dbh,filename,rybh,path) values(sys_guid(),'000000','"+zbid+"','"+oldname+"','"+rybh+"','"+newname+"')";
			db.update(sql2);
		}
		return i;
	}
	//接待报销申请第三步信息提交接口
	public int jdbxsqsave(PageData pd) {
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
		String sql1=" update cw_gwjdfbxzb set sfdszf='"+sfdszf+"',sfdgzf='"+sfdgzf+"',sfgwk='"+sfgwk+"',sfcjk='"+sfcjk+"' where guid='"+zbid+"' ";
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
			sql2="insert into Cw_DSZFB(guid,zfdh,ryxz,ryxm,klx,dfzh,je,bxlx) VALUES(sys_guid(),'"+zbid+"','"+ryxz+"','"+rygubh+"','"+yhmc+"','"+kh+"','"+je+"','2')";
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
			sql3="insert into CW_DGZFB(guid,zfdh,dfdw,dfdq ,dfyh ,dfzh, je, bxlx ) VALUES(sys_guid(),'"+zbid+"','"+dfdw+"','"+dfdq+"','"+dfyh+"','"+dfzh+"','"+je+"','2')";
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
			sql4="insert into CW_GWKB(guid ,skdh ,szbm ,ryxm ,skrq ,skje ,kh ,bxlx ) VALUES(sys_guid(),'"+zbid+"','','"+rygwkbh+"',to_date(?,'yyyy-MM-dd HH24:mi:ss'),'"+skje+"','"+kh+"','2') ";
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
				+ "VALUES(sys_guid(),'"+zbid+"','"+ryjkkbh+"','"+dwbh+"','"+jkje+"','"+cjkje+"','2','"+djbh+"') ";
			db.update(sql5);
		}
		return a;
	}
	//修改主表状态   更新业务表审核状态
	public int doStatus(String ywid,String zt,String shyj,String userId) {
		String sql = "UPDATE CW_GWJDFBXZB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+userId+"' WHERE guid"+CommonUtils.getInsql(ywid);
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
	//接待报销审批 
	public int jdbxsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("bh"), "");// 主表id
		String spjg = Validate.isNullToDefaultString(map.get("spjg"), "");// 审批结果（同意为T,不同意为F）
		String spyj = Validate.isNullToDefaultString(map.get("spyj"), "");// 审批意见
		String type=Validate.isNullToDefaultString(map.get("type"), "");// 类型 
		String other=Validate.isNullToDefaultString(map.get("rylx"), "");// 
		Map getlcidmap=ywcdao.getlcid(zbid,"CW_GWJDFBXZB");
		String procinstid = Validate.isNullToDefaultString(getlcidmap.get("procinstid"), "");// 流程ID
		String apply=Validate.isNullToDefaultString(map.get("apply"), "");// 退回状态
		DshInfoMap msgMap = new DshInfoMap();
		int a=0;
		Map dwmap=ywcdao.getgyryguid(userId);
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
	//查看详情
	public Map<String, Object> getGwjdmxMapById(String guid){
		String sql = " select guid,djbh,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = bxry) as sqr,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = szbm) as szbm,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = jdbm) as jdbm," + 
				" to_char(jdrq,'yyyy-MM-dd')as jdrq,bxje,lbdw,jdcs,lbry,gwhdxm,ptry,jdsy,sfcjk,sfdgzf,sfdszf,sfgwk,bxryid from cw_gwjdfbxzb where guid = '"+guid+"'";
		return db.queryForMap(sql);
	}
	//查询结算方式
	public List<Map<String, Object>> selectZffs(PageData pd,String table,String zbid){
		String sql;
		switch (table) {
		case "gwk":
			StringBuffer gwk = new StringBuffer();
			gwk.append(" select");
			gwk.append(" to_char(t.skrq,'yyyy-MM-dd')as \"dfdq\",t.kh as \"kh\",");
			gwk.append(" decode(nvl(t.skje,0),0,'0.00',(to_char(round(t.skje,2),'fm99999999999990.00')))as \"je\",");
			gwk.append(" (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.ryxm or r.guid=t.ryxm) as \"ryxm\" ");
			gwk.append(" from cw_gwkb t where 1=1 and t.skdh='" + zbid + "'");
			sql = gwk.toString();
			break;
		case "dszf":
			StringBuffer dszf = new StringBuffer();
			dszf.append(" select");
			dszf.append(" '('||w.rybh||')'||w.xm as \"ryxm\",");
			dszf.append(" t.ryxz as \"ryxz\",t.dfzh as \"kh\",t.klx as \"yhmc\",");
			dszf.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00'))) as \"je\"");
			dszf.append(" from CW_DSZFB t ");
			dszf.append(" left join gx_sys_ryb w on w.rybh=t.ryxm");
			dszf.append(" where 1=1 and t.zfdh='" + zbid + "'");
			sql = dszf.toString();
			break;
		case "dgzf":
			StringBuffer dgzf = new StringBuffer();
			dgzf.append(" select ");
			dgzf.append(" t.dfdw as \"dfdw\",t.dfdq as \"dfdq\",t.dfzh as \"dfzh\",w.dwmc,w.guid,w.wlbh,t.dfyh as \"dfyh\",");
			dgzf.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00'))) as \"je\"");
			dgzf.append(" from CW_DGZFB t");
			dgzf.append(" left join Cw_wldwb w on w.WLBH=t.DFDW");
			dgzf.append(" where 1=1 and t.zfdh='" + zbid + "'");
			sql = dgzf.toString();
			break;
		case "cjk":
			StringBuffer cjk = new StringBuffer();
			cjk.append(" select");
			cjk.append(" distinct(t.guid)guid,d.mc as \"szbm\",decode(nvl(t.cjkje,0),0,'0.00',(to_char(round(t.cjkje,2),'fm99999999999990.00')))as \"cjkje\",'('||r.rybh||')'||r.xm as \"jkr\",t.jkid as \"jkdh\",decode(nvl(cw.jkje,0),0,'0.00',(to_char(round(cw.jkje,2),'fm99999999999990.00'))) as \"jkje\" ");
			cjk.append(" from Cw_cjkb t");
			cjk.append(" left join gx_sys_ryb r on r.guid=t.jkr or r.rybh=t.jkr");
			cjk.append(" left join gx_sys_dwb d on d.dwbh=r.dwbh");
			cjk.append(" left join CW_YZFSQSPB cw on cw.djbh=t.jkid");
			cjk.append(" where 1=1");
			cjk.append(" and jkdh='" + zbid + "'");
			sql = cjk.toString();
			break;
		default:
			sql = "";
			break;
		}
		return db.queryForList(sql);
	}
	//事前审批 详情 
	public Map<String, Object> getObjectById(String guid) {
		String sql = "select guid,djbh,fjzs as lhzs,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=a.sqr),'') sqr,"
				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=a.szbm) szbm,jdrq,"
				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=a.jdbm) jdbm,"
				+ "lbdw,jdfd,lbxmjrs,ptxmjrs,sfyzf,yzfje,jdsy,a.shyj,a.sfjzbx,a.jzbxr,(select xm from gx_sys_ryb where rybh = a.jzbxr) as jzbxrxm,a.lbxm,a.lbzw,a.lxr,a.lxdh,a.txry from CW_GWJDYWSQSPB a where guid=?";
		sql=String.format(sql);
		return db.queryForMap(sql,new Object[]{guid});
	}
	
	//事前审批 提交 
	public int sqsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("bh"), "");// 主表id
		String spjg = Validate.isNullToDefaultString(map.get("spjg"), "");// 审批结果（同意为T,不同意为F）
		String spyj = Validate.isNullToDefaultString(map.get("spyj"), "");// 审批意见
		String ccywguid=Validate.isNullToDefaultString(map.get("ccywguid"), "");// 出差 事前审批 guid
		String appid=Validate.isNullToDefaultString(map.get("appid"), "");// /退回到申请人
		Map getlcidmap=ywcdao.getlcid(zbid,"CW_GWJDYWSQSPB");
		String procinstid = Validate.isNullToDefaultString(getlcidmap.get("procinstid"), "");// 流程ID
	    Map dwmap=ywcdao.getgyryguid(userId);
	    String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
	    GX_SYS_RYB ryb = new GX_SYS_RYB();
		ryb.setDwbh(dwbh);
		ryb.setGuid(userId);
		session.setAttribute(Const.SESSION_USER, ryb);
		DshInfoMap msgMap = new DshInfoMap();
		int a=0;
		if("T".equals(spjg)) {//通过
			a=1;
			gwjdsqService.approveLeaveInfo( session,shyjb, zbid,procinstid,spyj);
			if(Validate.noNull(procinstid)) {
				//从task表中查找流程审核人
				String shr = echoService.getShrGuid(procinstid);
				//如果不是审核通过的单据（通过的流程会在task表被删除）
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg = echoService.getGwjdsqspDshxxMsg(zbid);
					msgMap.get(shr).add(shxxMsg);
				}
			}
		}else if("F".equals(spjg)) {//退回
			a=1;
			gwjdsqService.rejectleaveinfo(session, zbid,procinstid,spyj,appid,shyjb);
			if(Validate.noNull(procinstid)) {
				String shr = echoService.getShrGuid(procinstid);
				//如果不是审核通过的单据（通过的流程会在task表被删除）
				if(Validate.noNull(shr)) {
					if(!msgMap.containsKey(shr)) {
						msgMap.put(shr, new ArrayList<DshInfo>());
					}
					DshInfo shxxMsg = echoService.getGwjdsqspDshxxMsg(zbid);
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
	//公务接待事前申请 提交保存
	public int gwjdsqtjbc(PageData pd, String zbid, String rybh, String dwbh) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String lbdw = Validate.isNullToDefaultString(map.get("lbdw"), ""); // 来宾单位
		String jddd = Validate.isNullToDefaultString(map.get("jddd"), ""); // 接待地点
		String jdrq = Validate.isNullToDefaultString(map.get("jdrq"), ""); // 接待日期
		String jdbm = Validate.isNullToDefaultString(map.get("jdbm"), ""); // 接待部门id
		String jzbx = Validate.isNullToDefaultString(map.get("jzbx"), ""); // 集中报销0：否，1：是
		String bxry = Validate.isNullToDefaultString(map.get("bxry"), ""); // 报销人员id
		String yzf = Validate.isNullToDefaultString(map.get("yzf"), ""); // 预支付02：否，01：是
		String yzje = Validate.isNullToDefaultString(map.get("yzje"), ""); // 预支金额
		String lbxx = Validate.isNullToDefaultString(map.get("lbxx"), ""); // 来宾信息
		String zdry = Validate.isNullToDefaultString(map.get("zdry"), ""); // 招待人员
		String jdsy = Validate.isNullToDefaultString(map.get("jdsy"), ""); // 接待事由
		String lbxm = Validate.isNullToDefaultString(map.get("lbxm"), "");;//来宾姓名
	    String lbzw = Validate.isNullToDefaultString(map.get("lbzw"), "");;//来宾职务
	    String lxdh = Validate.isNullToDefaultString(map.get("lxdh"), "");;//联系电话
	    String txry = Validate.isNullToDefaultString(map.get("txry"), "");;//同行人员
	    String lxr = Validate.isNullToDefaultString(map.get("lxr"), "");;//同行人员
	    String lhzs = Validate.isNullToDefaultString(map.get("lhzs"), "");;//来函张数，即附件张数
		 Map dwmap=ywcdao.getgyryguid(userId);
	    String bxry1=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		List<Map> imglist = (List) map.get("imglist");// 上传文件路径
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		String djbh=GenAutoKey.createKeyforth("cw_gwjdywsqspb", "GW", "djbh");
		String djlx = "000000";
		String sqrId = "select guid from gx_sys_ryb where rybh='"+rybh+"'";
		String czr = db.queryForSingleValue(sqrId);
		String sql = "insert into CW_GWJDYWSQSPB(guid,djbh,sqr,szbm,jdrq,jdbm,lbdw,jdfd,lbxmjrs,ptxmjrs,sfyzf,yzfje,jdsy,shzt,sqrq,sfjzbx,jzbxr,lbxm,lbzw,lxdh,txry,lxr,fjzs,czr) "
			+ "values('"+zbid+"', '"+djbh+"','"+rybh+"' ,'"+dwbh+"' ,'"+jdrq+"' ,'"+jdbm+"' ,'"+lbdw+"' , '"+jddd+"','"+lbxx+"' ,'"+zdry+"' ,'"+yzf+"' ,'"+yzje+"' ,'"+jdsy+"' , '01','"+datetime+"','"+jzbx+"', '"+bxry1+"','"+lbxm+"','"+lbzw+"','"+lxdh+"','"+txry+"','"+lxr+"','"+lhzs+"','"+czr+"' )";
		// 附件保存方法 图片
		for (int k = 0; k < imglist.size(); k++) {
			String newname = Validate.isNullToDefaultString(imglist.get(k).get("newname"), "");
			String oldname = Validate.isNullToDefaultString(imglist.get(k).get("oldname"), "");
			newname = newname.substring(newname.lastIndexOf("/phone"));
			ywcdao.insertimglist(zbid, djlx, rybh, newname, oldname);
		}
		//保存项目list
		List<Map> xmlist = (List) map.get("xmlist");// 项目list
		for(int j=0;j<xmlist.size();j++) {
		  String hdxm=Validate.isNullToDefaultString(xmlist.get(j).get("hdxm"), "");;//活动项目
		  String hdsj=Validate.isNullToDefaultString(xmlist.get(j).get("hdsj"), "");;//活动时间
		  String hddd=Validate.isNullToDefaultString(xmlist.get(j).get("hddd"), "");;//活动地点
		  String ptry=Validate.isNullToDefaultString(xmlist.get(j).get("ptry"), "");;//陪同人员 
		  String xmxxsql="insert into CW_GWJDYWSQSPB_HDXX(GUID,HDXM,HDSJ,HDDD,PTRY,gwjdsqid) values(sys_guid(),'"+hdxm+"',to_date('"+hdsj+"','yyyy-mm-dd'),'"+hddd+"','"+ptry+"','"+zbid+"')";
		  db.update(xmxxsql);
		}
		return db.update(sql);
	}
	//接待报销list 返回
	public List<Map<String,Object>> findxmxxlist(String zbid){
		String sql="select  hdxm as \"hdxm\", to_char(hdsj,'yyyy-mm-dd') as \"hdsj\", hddd as \"hddd\", ptry as \"ptry\" from CW_GWJDYWSQSPB_HDXX where gwjdsqid = '"+zbid+"'";
		return db.queryForList(sql);
	}
}