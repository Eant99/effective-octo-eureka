package com.googosoft.modules.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.googosoft.JSON.JsonArray;
import com.googosoft.JSON.JsonObject;
import com.googosoft.JSON.KeysValues;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.wsbx.CcbxDao;
import com.googosoft.dao.wsbx.gwjdfbx.GwjdfbxsqDao;
import com.googosoft.modules.app.dao.JdbxDao;
import com.googosoft.modules.app.dao.YwcDao;
import com.googosoft.modules.app.pojo.Ccsqspxq;
import com.googosoft.modules.app.pojo.Gwjdsqsp;
import com.googosoft.modules.app.pojo.Jdbxspxq;
import com.googosoft.modules.app.pojo.Jdbxsq;
import com.googosoft.modules.app.pojo.YwcReturnMsg;
import com.googosoft.pojo.ImgInfo;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.wsbx.gwjd.GwjdSqService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.service.wsbx.process.WsbxProcessService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Const;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

/**
*@author 杨超超
*@date   2018年2月1日---下午8:43:09
*/
@Service("jdbxsqservice")
public class JdbxsqService extends BaseService{

	@Resource(name = "jdbxdao")
	private JdbxDao jdbxdao;
	
	@Resource(name = "ywcdao")
	private YwcDao ywcdao;
	
	@Resource(name="gwjdfbxsqDao")
	private GwjdfbxsqDao gwjdfbxsqDao;
	@Resource(name = "ProcessService")
	private WsbxProcessService wsbxprocessservice;
	
	// 流程
	@Resource(name = "ProcessService")
	private WsbxProcessService processservice;
	
	@Resource(name="identityService")
	private IdentityService identityService;
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	@Autowired
	private EchoService echoService;
	@Resource(name="gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;
	
	@Resource(name="gwjdsqService")
	private GwjdSqService gwjdsqService;
	
	Gson gson = new Gson();
	private int pageSize = 10;//分页 每页显示个数
	
	/***************************13.接待报销申请列表接口*******************************/
	// 接待报销申请列表接口
	@ResponseBody
	public String jdbxsqlistss(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		int index = Integer.parseInt(Validate.isNullToDefaultString(map.get("index"), "1"));// 分页
		String keyword=Validate.isNullToDefaultString(map.get("keyword"), "");// 用户guid
		try {
			//页面list
			List<Map<String,Object>> list=jdbxdao.jdbxsqlistss(userId,index,pageSize,keyword);
			List jdbxlist=new ArrayList<>();
			for(int i=0;i<list.size();i++) {
				Map map1=new HashMap<>();
				map1.put("jdbt", Validate.isNullToDefaultString(list.get(i).get("jdsy"), ""));
				map1.put("jdlx",  "");
				map1.put("jdsj", Validate.isNullToDefaultString(list.get(i).get("jdrq"), ""));
				map1.put("jddd", Validate.isNullToDefaultString(list.get(i).get("jdfd"), ""));
				map1.put("jdbh", Validate.isNullToDefaultString(list.get(i).get("guid"), ""));
				jdbxlist.add(map1);
			}
			if(list.size()>0) {
				msg.setItems(jdbxlist);
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
			}else {
				msg.setMsg("未搜索到您要搜索的信息!!");
				msg.setSuccess(false);
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	// 接待报销申请列表搜索接口
	@ResponseBody
	public String jdbxsqlist(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		int index = Integer.parseInt(Validate.isNullToDefaultString(map.get("index"), "1"));// 分页
		try {
			//页面list
			List<Map<String,Object>> list=jdbxdao.getjdbxlist(userId,index,pageSize);
			List jdbxlist=new ArrayList<>();
			for(int i=0;i<list.size();i++) {
				Map map1=new HashMap<>();
				map1.put("jdbt", Validate.isNullToDefaultString(list.get(i).get("jdsy"), ""));
				map1.put("jdlx",  "");
				map1.put("jdsj", Validate.isNullToDefaultString(list.get(i).get("jdrq"), ""));
				map1.put("jddd", Validate.isNullToDefaultString(list.get(i).get("jdfd"), ""));
				map1.put("jdbh", Validate.isNullToDefaultString(list.get(i).get("guid"), ""));
				jdbxlist.add(map1);
			}
			if(list.size()>0) {
				msg.setItems(jdbxlist);
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
			}else {
				msg.setSuccess(false);
				msg.setMsg("暂无数据!!");
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	// 接待报销申请第二步接口
	@ResponseBody
	public String jdbxsqtwo(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Jdbxsq jdbxsq=new Jdbxsq();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid=Validate.isNullToDefaultString(map.get("bh"), "");// 主表id
		try {
			Map jdbxsqmap=jdbxdao.getjdbxsqmap(zbid);
			jdbxsq.setBxry("");
			jdbxsq.setSzbm("");
			jdbxsq.setJdrq(Validate.isNullToDefaultString(jdbxsqmap.get("JDRQ"), ""));//接待日期
			jdbxsq.setJdbm(Validate.isNullToDefaultString(jdbxsqmap.get("JDBM"), ""));//接待部门
			jdbxsq.setJdbmmc(Validate.isNullToDefaultString(jdbxsqmap.get("JDBMMC"), ""));//接待部门
			jdbxsq.setLkdw(Validate.isNullToDefaultString(jdbxsqmap.get("LBDW"), ""));//来客单位
			jdbxsq.setLbryjh(Validate.isNullToDefaultString(jdbxsqmap.get("LBXMJRS"), ""));//来宾人员及人数
			jdbxsq.setPhryjh(Validate.isNullToDefaultString(jdbxsqmap.get("PTXMJRS"), ""));//陪同人员及人数
			jdbxsq.setJdsy(Validate.isNullToDefaultString(jdbxsqmap.get("JDSY"), ""));//接待事由
			jdbxsq.setJddd(Validate.isNullToDefaultString(jdbxsqmap.get("JDFD"), ""));//接待地点 饭店
			// 查询所有部门信息 单位
			List<Map<String, Object>> bmlist = ywcdao.getbmlist();
			List list = new ArrayList();
			for (int i = 0; i < bmlist.size(); i++) {
				Map map1 = new HashMap();
				map1.put("departmentId", bmlist.get(i).get("DWBH"));
				map1.put("departmentName", bmlist.get(i).get("MC"));
				// 根据传过来的 部门编号 查询 部门下所有的人员信息
				List<Map<String, Object>> rylist = ywcdao.getryxxlist(bmlist.get(i).get("DWBH"),userId);
				map1.put("list", rylist);
				list.add(map1);
			}
			jdbxsq.setSclj(Const.SJCJ);
			if(bmlist.size()>0) {
				jdbxsq.setRylist(list);
				jdbxsq.setMsg("数据加载成功!!");
				jdbxsq.setSuccess(true);
			}else {
				jdbxsq.setMsg("暂无数据!!");
				jdbxsq.setSuccess(false);
			}
		} catch (Exception e) {
			jdbxsq.setMsg("数据加载失败!!");
			jdbxsq.setSuccess(false);
		}
		return new Gson().toJson(jdbxsq);
	}
	// 接待报销申请第二步信息提交接口
	@ResponseBody
	public String jdbxsqtwotj(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Jdbxsq jdbxsq=new Jdbxsq();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String bh=Validate.isNullToDefaultString(map.get("bh"), "");// 公务接待事前审批guid
		//生成主表出差申请主表id zbid
		String zbid=GenAutoKey.get32UUID();
		String djbh=GenAutoKey.createKeyforth("cw_gwjdfbxzb", "JD", "djbh");
		try {
			int a=jdbxdao.jdbxsave(pd,zbid,djbh,bh);
			jdbxsq.setZbid(zbid);
			jdbxsq.setDjbh(djbh);
			jdbxsq.setMsg("数据提交成功!!");
			jdbxsq.setSuccess(true);
		} catch (Exception e) {
			jdbxsq.setMsg("数据提交失败!!");
			jdbxsq.setSuccess(false);
		}
		return new Gson().toJson(jdbxsq);
	}
	
	//接待报销申请第三步信息提交接口
	@ResponseBody
	public String jdbxsqthreetj(PageData pd,HttpSession session) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Jdbxsq jdbxsq=new Jdbxsq();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid=Validate.isNullToDefaultString(map.get("zbid"), "");// 公务接待报销主表id
		String bh=Validate.isNullToDefaultString(map.get("bh"), "");// 公务接待事前审批guid
		String xz = Validate.isNullToDefaultString(map.get("rylx"), "");//
		String type = Validate.isNullToDefaultString(map.get("type"), "");//
	    Map dwmap=ywcdao.getgyryguid(userId);
	    String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
	    String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		DshInfoMap msgMap = new DshInfoMap();
		try {
			int a=jdbxdao.jdbxsqsave(pd);//保存信息 
			if (a>0) {
			    GX_SYS_RYB ryb = new GX_SYS_RYB();
				ryb.setDwbh(dwbh);
				ryb.setGuid(userId);
				ryb.setRybh(rybh);
				session.setAttribute(Const.SESSION_USER, ryb);
				//提交流程
				int m = 0;
				xz = Validate.isNullToDefaultString(xz, "");
				if("undefined".equals(xz)){
					xz = "";
				}
				String process = "";
				if (Validate.noNull(zbid)) {
						String id = Validate.isNullToDefaultString(zbid, "");
						m += processservice.getProcessType(id, type,xz);
				}
				if(m>0) {
					jdbxsq.setMsg("提交成功!");
					jdbxsq.setSuccess(true);
				}else {
					jdbxsq.setMsg("提交失败!");
					jdbxsq.setSuccess(false);
				}
			}
		} catch (Exception e) {
			jdbxsq.setMsg("数据提交失败!!");
			jdbxsq.setSuccess(false);
		}
		return new Gson().toJson(jdbxsq);
	}

	//接待报销审批提交接口
	@ResponseBody
	public String jdbxsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		YwcReturnMsg msg = new YwcReturnMsg();
		try {
			int a=jdbxdao.jdbxsptj(session,pd,shyjb);
			if (a>0) {
				msg.setMsg("审批成功!!");
				msg.setSuccess(true);
			}
		} catch (Exception e) {
			msg.setMsg("审批失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//接待报销审批详情页面
	@ResponseBody
	public String jdbxxq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Jdbxspxq jdbxspxq=new Jdbxspxq();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String bh = Validate.isNullToDefaultString(map.get("bh"), "");// bh  主表id
		//根据主表ID 查询流程ID
		Map lcmap=ywcdao.getlcid(bh,"CW_GWJDFBXZB");
		String lcid=Validate.isNullToDefaultString(lcmap.get("PROCINSTID"),"");
		try {
			List list = new ArrayList();
			Map<String, Object> gwjdmx = jdbxdao.getGwjdmxMapById(bh);
			String djbh =Validate.isNullToDefaultString(gwjdmx.get("djbh"), "");  //单据编号
			String bxry =Validate.isNullToDefaultString(gwjdmx.get("sqr"), "");  //报销人员
			String szbm =Validate.isNullToDefaultString(gwjdmx.get("szbm"), "");  //所在部门
			String jdrq =Validate.isNullToDefaultString(gwjdmx.get("jdrq"), "");  //接待日期
			String jdbm =Validate.isNullToDefaultString(gwjdmx.get("jdbm"), "");  //接待部门
			String lbdw =Validate.isNullToDefaultString(gwjdmx.get("lbdw"), "");  //来宾单位
			String jddd =Validate.isNullToDefaultString(gwjdmx.get("jdcs"), "");  //接待地点
			String bxje =Validate.isNullToDefaultString(gwjdmx.get("bxje"), "");  //报销金额
			String gwhdxm=Validate.isNullToDefaultString(gwjdmx.get("gwhdxm"), ""); //公务活动项目
			String lbxmjrs=Validate.isNullToDefaultString(gwjdmx.get("lbry"), "");//来宾姓名及人数
			String ptxmjrs=Validate.isNullToDefaultString(gwjdmx.get("ptry"), "");//陪同姓名及人数
			String jdsy=Validate.isNullToDefaultString(gwjdmx.get("jdsy"), "");//陪同姓名及人数
			jdbxspxq.setDjbh(djbh);
			jdbxspxq.setBxry(bxry);
			jdbxspxq.setSzbm(szbm);
			jdbxspxq.setJdrq(jdrq);
			jdbxspxq.setJdbm(jdbm);
			jdbxspxq.setLbdw(lbdw);
			jdbxspxq.setJddd(jddd);
			jdbxspxq.setBxje(bxje);
			jdbxspxq.setJdsy(jdsy);
			jdbxspxq.setGwhdxm(gwhdxm);
			jdbxspxq.setLbxmjrs(lbxmjrs);
			jdbxspxq.setPtxmjrs(ptxmjrs);
			//冲销借款集合
			List<Map<String,Object>> cjkList=jdbxdao.selectZffs(pd, "cjk",bh);
			jdbxspxq.setCxjklist(cjkList);
			//对私支付集合
			List<Map<String,Object>> dszflist=jdbxdao.selectZffs(pd, "dszf",bh);
			jdbxspxq.setDszflist(dszflist);
			//对公支付集合
			List<Map<String,Object>> dgzflist=jdbxdao.selectZffs(pd, "dgzf",bh);
			jdbxspxq.setDgzflist(dgzflist);
			//公务卡集合
			List<Map<String,Object>> gwklist=jdbxdao.selectZffs(pd, "gwk",bh);
			jdbxspxq.setGwklist(gwklist);
			//imglist  图片详情路径
			List<Map<String,Object>> imglist=ywcdao.gettpsclj(bh);
			List imglist1=new ArrayList<>();
			for(int t=0;t<imglist.size();t++) {
				Map map2=new HashMap<>();
				String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
				String imgurl="'"+xnlu_path+"/"+imglist.get(t).get("PATH")+"'";
				map2.put("imgurl", xnlu_path+"/"+imglist.get(t).get("PATH"));
				imglist1.add(map2);
			}
			jdbxspxq.setImglist(imglist1);
			//流程list lclist
			List<Map<String,Object>> lclist=ywcdao.getlclist(lcid);
			List lclist1=new ArrayList<>();
			for(int j=0;j<lclist.size();j++) {
				if(j==0) {
					Map map1=new HashMap<>();
					String time=lclist.get(j).get("STARTTIME")+"";
					map1.put("time", time);
					map1.put("name",  Validate.isNullToDefaultString(lclist.get(j).get("NAME"), ""));
					String shzt=Validate.isNullToDefaultString(lclist.get(j).get("SHZT"),"");
					String shyj=Validate.isNullToDefaultString(lclist.get(j).get("YJ"),"");
					map1.put("yj","发起申请");
					lclist1.add(map1);
				}else {
					Map map1=new HashMap<>();
					String time=lclist.get(j).get("STARTTIME")+"";
					map1.put("time", time);
					map1.put("name",  Validate.isNullToDefaultString(lclist.get(j).get("NAME"), ""));
					String shzt=Validate.isNullToDefaultString(lclist.get(j).get("SHZT"),"");
					String shyj=Validate.isNullToDefaultString(lclist.get(j).get("YJ"),"");
					map1.put("yj","("+shzt+") "+shyj);
					lclist1.add(map1);
				}
				
			}
			jdbxspxq.setLclist(lclist1);
			jdbxspxq.setMsg("数据加载成功!!");
			jdbxspxq.setSuccess(true);
		} catch (Exception e) {
			jdbxspxq.setMsg("数据加载失败!!");
			jdbxspxq.setSuccess(false);
		}
		return new Gson().toJson(jdbxspxq);
	}
	//公务接待 事前审批详情 
	@ResponseBody
	public String gwjdspxq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Gwjdsqsp gwjdsqsp=new Gwjdsqsp();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String bh = Validate.isNullToDefaultString(map.get("bh"), "");// bh  主表id
		//根据主表ID 查询流程ID
		Map lcmap=ywcdao.getlcid(bh,"CW_GWJDYWSQSPB");
		String lcid=Validate.isNullToDefaultString(lcmap.get("PROCINSTID"),"");
		try {
			List list = new ArrayList();
			Map<String, Object> gwjdmx = jdbxdao.getGwjdmxMapById(bh);
			Map<String, Object> gwjdsqspxqmap = jdbxdao.getObjectById(bh);
			String djbh= Validate.isNullToDefaultString(gwjdsqspxqmap.get("djbh"), "");//单据编号
			String sqr= Validate.isNullToDefaultString(gwjdsqspxqmap.get("sqr"), "");//申请人
			String szbm= Validate.isNullToDefaultString(gwjdsqspxqmap.get("szbm"), "");//所在部门
			String jdrq= Validate.isNullToDefaultString(gwjdsqspxqmap.get("jdrq"), "");//接待日期
			String jdbm= Validate.isNullToDefaultString(gwjdsqspxqmap.get("jdbm"), "");//接待部门
			String lbdw= Validate.isNullToDefaultString(gwjdsqspxqmap.get("lbdw"), "");//来宾单位
			String jddd= Validate.isNullToDefaultString(gwjdsqspxqmap.get("jdfd"), "");//接待地点
			String sfyzf= Validate.isNullToDefaultString(gwjdsqspxqmap.get("sfyzf"), "");//是否预支付
			String yzfje= Validate.isNullToDefaultString(gwjdsqspxqmap.get("yzfje"), "");//预支付金额
			String lbxmjrs= Validate.isNullToDefaultString(gwjdsqspxqmap.get("lbxmjrs"), "");//来宾姓名及人数
			String ptxmjrs= Validate.isNullToDefaultString(gwjdsqspxqmap.get("ptxmjrs"), "");//陪同姓名及人数
			String sfjzbx= Validate.isNullToDefaultString(gwjdsqspxqmap.get("sfjzbx"), "");//是否集中报销
			String jzbxr= Validate.isNullToDefaultString(gwjdsqspxqmap.get("jzbxrxm"), "");//集中报销人
			String jdsy= Validate.isNullToDefaultString(gwjdsqspxqmap.get("jdsy"), "");//接待事由
	   	    String  lbxm= Validate.isNullToDefaultString(gwjdsqspxqmap.get("lbxm"), "");//来宾姓名
	        String  lbzw= Validate.isNullToDefaultString(gwjdsqspxqmap.get("lbzw"), "");//来宾职务
	        String  lxr= Validate.isNullToDefaultString(gwjdsqspxqmap.get("lxr"), "");//联系人
	        String  lxdh= Validate.isNullToDefaultString(gwjdsqspxqmap.get("lxdh"), "");//联系电话
	        String  txry= Validate.isNullToDefaultString(gwjdsqspxqmap.get("txry"), "");//同行人员
	        String  lhzs= Validate.isNullToDefaultString(gwjdsqspxqmap.get("lhzs"), "");//附件张数，即来函张数
			gwjdsqsp.setDjbh(djbh);
			gwjdsqsp.setSqr(sqr);
			gwjdsqsp.setSzbm(szbm);
			gwjdsqsp.setJdrq(jdrq);
			gwjdsqsp.setJdbm(jdbm);
			gwjdsqsp.setLbdw(lbdw);
			gwjdsqsp.setJddd(jddd);
			gwjdsqsp.setSfyzf(sfyzf);
			gwjdsqsp.setYzfje(yzfje);
			gwjdsqsp.setLbxmjrs(lbxmjrs);
			gwjdsqsp.setPtxmjrs(ptxmjrs);
			gwjdsqsp.setSfjzbx(sfjzbx);
			gwjdsqsp.setJzbxr(jzbxr);
			gwjdsqsp.setJdsy(jdsy);
			gwjdsqsp.setLbxm(lbxm);
			gwjdsqsp.setLbzw(lbzw);
			gwjdsqsp.setLxr(lxr);
			gwjdsqsp.setLxdh(lxdh);
			gwjdsqsp.setTxry(txry);
			gwjdsqsp.setLhzs(lhzs);
			
			//图片list
			List<Map<String,Object>> imglist=ywcdao.gettpsclj(bh);
			List imglist1=new ArrayList<>();
			for(int t=0;t<imglist.size();t++) {
				Map map2=new HashMap<>();
				String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
				String imgurl="'"+xnlu_path+"/"+imglist.get(t).get("PATH")+"'";
				map2.put("imgurl", xnlu_path+"/"+imglist.get(t).get("PATH"));
				imglist1.add(map2);
			}
			gwjdsqsp.setImglist(imglist1);
			//流程list lclist
			List<Map<String,Object>> lclist=ywcdao.getlclist(lcid);
			List lclist1=new ArrayList<>();
			for(int j=0;j<lclist.size();j++) {
				if(j==0) {
					Map map1=new HashMap<>();
					String time=lclist.get(j).get("STARTTIME")+"";
					map1.put("time", time);
					map1.put("name",  Validate.isNullToDefaultString(lclist.get(j).get("NAME"), ""));
					String shzt=Validate.isNullToDefaultString(lclist.get(j).get("SHZT"),"");
					String shyj=Validate.isNullToDefaultString(lclist.get(j).get("YJ"),"");
					map1.put("yj","发起申请");
					lclist1.add(map1);
				}else {
					Map map1=new HashMap<>();
					String time=lclist.get(j).get("STARTTIME")+"";
					map1.put("time", time);
					map1.put("name",  Validate.isNullToDefaultString(lclist.get(j).get("NAME"), ""));
					String shzt=Validate.isNullToDefaultString(lclist.get(j).get("SHZT"),"");
					String shyj=Validate.isNullToDefaultString(lclist.get(j).get("YJ"),"");
					map1.put("yj","("+shzt+") "+shyj);
					lclist1.add(map1);
				}
				
			}
			//返回项目list
			List<Map<String,Object>> xmxxlist=jdbxdao.findxmxxlist(bh);
			gwjdsqsp.setXmlist(xmxxlist);
			if(lclist1.size()>0) {
				gwjdsqsp.setLclist(lclist1);
				gwjdsqsp.setMsg("数据加载成功!!");
				gwjdsqsp.setSuccess(true);
			}else {
				gwjdsqsp.setMsg("暂无数据!!");
				gwjdsqsp.setSuccess(false);
			}
		} catch (Exception e) {
			gwjdsqsp.setMsg("数据加载失败!!");
			gwjdsqsp.setSuccess(false);
		}
		return new Gson().toJson(gwjdsqsp);
	}
	//事前审批 审批 提交
	@ResponseBody
	public String gwjdsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		YwcReturnMsg msg = new YwcReturnMsg();
		try {
			int a=jdbxdao.sqsptj(session,pd,shyjb);
			if (a>0) {
				msg.setMsg("审批成功!!");
				msg.setSuccess(true);
			}
		} catch (Exception e) {
			msg.setMsg("审批失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//公务接待事前审批 获取人员\
	@ResponseBody
	public String jdsqbxry(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		// 查询所有部门信息 单位
		List<Map<String, Object>> bmlist = ywcdao.getbmlist();
		List list = new ArrayList();
		for (int i = 0; i < bmlist.size(); i++) {
			Map map1 = new HashMap();
			map1.put("departmentId", bmlist.get(i).get("DWBH"));
			map1.put("departmentName", bmlist.get(i).get("MC"));
			// 根据传过来的 部门编号 查询 部门下所有的人员信息
			List<Map<String, Object>> rylist = ywcdao.getryxxlist(bmlist.get(i).get("DWBH"),userId);
			map1.put("list", rylist);
			list.add(map1);
		}
		if (bmlist.size() > 0) {
			msg.setMsg("数据加载成功!!");
			msg.setSuccess(true);
			msg.setItems(list);
		} else {
			msg.setMsg("暂无数据!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//公务接待 事前审批 获取部门 递归
	@ResponseBody
	public String jdsqjdbm(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		// 查询所有部门信息 单位
		List list = new ArrayList();
		try {
			Map map1 = new HashMap();
			map1.put("departmentId","");
			map1.put("departmentName","");
			// 根据传过来的 部门编号 查询 部门下所有的人员信息
			List<Map<String, Object>> rylist = ywcdao.getdgryxxlist();
			map1.put("list", rylist);
			list.add(map1);
			msg.setSclj(Const.SJCJ);
			msg.setMsg("数据加载成功!!");
			msg.setSuccess(true);
			msg.setItems(list);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//公务接待事前申请提交保存  
	@ResponseBody
	public String gwjdsqtj(PageData pd,HttpSession session) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Jdbxsq jdbxsq=new Jdbxsq();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		//生成主表出差申请主表id zbid
		String zbid=GenAutoKey.get32UUID();
	    Map dwmap=ywcdao.getgyryguid(userId);
	    String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
	    String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		DshInfoMap msgMap = new DshInfoMap();
		try {
			int a=jdbxdao.gwjdsqtjbc(pd,zbid,rybh,dwbh);//保存信息 
			if(a>0) {//开启提交工作流程
				GX_SYS_RYB ryb = new GX_SYS_RYB();
				ryb.setDwbh(dwbh);
				ryb.setGuid(userId);
				session.setAttribute(Const.SESSION_USER, ryb);
				String procinstid = gwjdsqService.submitBySqr(zbid);
				if(Validate.noNull(procinstid)) {
					String shr = echoService.getShrGuid(procinstid);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>()); 
						}
						DshInfo shxxMsg = echoService.getGwjdsqspDshxxMsg(zbid);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
			jdbxsq.setMsg("数据提交成功!!");
			jdbxsq.setSuccess(true);
		} catch (Exception e) {
			jdbxsq.setMsg("数据提交失败!!");
			jdbxsq.setSuccess(false);
		}
		return new Gson().toJson(jdbxsq);
	}
	/*************************** 上传图片 *******************************/
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	public String txsczyff(MultipartHttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		MultiValueMap<String, MultipartFile> multiMap = request.getMultiFileMap();
		Set<String> keys = multiMap.keySet();
		List<ImgInfo> imgInfos = new ArrayList<ImgInfo>();
		for (String key : keys) {
			ImgInfo imgInfo = new ImgInfo();
			List<MultipartFile> mutiFile = multiMap.get(key);
			imgInfo = writeFileToDisk(mutiFile, request);
			if(Validate.isNull(imgInfo.getErrmsg())){
				imgInfos.add(imgInfo);
			}
		}
		int size = imgInfos.size();
		if (size > 0) {
			String text = "";
			if(multiMap.size() == size){
				text = "上传成功！";
			}
			else{
				text = size + "张图片上传成功！";
			}
			
			JsonArray array = new JsonArray();
			for (int i = 0; i < size; i++) {
				array.add(new JsonObject(
						new KeysValues("oldname", imgInfos.get(i).getOldname()),
						new KeysValues("newname", imgInfos.get(i).getNewname())
						));
			}
			
			JsonObject json = new JsonObject(
				new KeysValues("success",true),
				new KeysValues("msg",text),
				new KeysValues("names",array)
			);
			return json.toString();
		}
		else{
			return "{\"success\":\"false\",\"msg\":\"上传失败！\"}";
		}
	}
	/**
	 * 上传图片
	 * @return
	 * @throws Exception 
	 */
	/* 读取文件流 */
	private ImgInfo writeFileToDisk(List<MultipartFile> fileList, MultipartHttpServletRequest request) {
		ImgInfo imgInfo = new ImgInfo();
		for (MultipartFile file : fileList) {
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				is = file.getInputStream();
            	//虚拟路径
//            	String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
//            	//物理路径
//            	String wllu_path = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");

            	String realPath = request.getServletContext().getRealPath("/");//文件保存时候的根路径
            	
            	String filename = file.getOriginalFilename();
            	String kzm = filename.substring(filename.lastIndexOf(".")).toLowerCase();//带有.
            	String name = UuidUtil.get32UUID() + kzm;
				String appfilepath =  "imgFile/" + name;//回传手机端的访问路径
				
				imgInfo.setOldname(filename);
				imgInfo.setNewname(appfilepath);
				
				String pcfilepath = request.getServletContext().getRealPath("/") + "/imgFile/" + name;//pc端存放地址
				File uploadPic = new File(realPath + "/imgFile/");
            	if(!uploadPic.exists()){
            		uploadPic.mkdirs();
            	}
				fos = new FileOutputStream(pcfilepath);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				fos.flush();
			} catch (IOException e) {
				imgInfo.setErrmsg(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return imgInfo;
	}
}
