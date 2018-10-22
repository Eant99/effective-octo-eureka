package com.googosoft.modules.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.wsbx.process.WsbxProcessController;
import com.googosoft.dao.wsbx.CcbxDao;
import com.googosoft.dao.wsbx.RcbxDao;
import com.googosoft.dao.wsbx.ccyw.CcywsqDao;
import com.googosoft.modules.app.dao.PhoneDao;
import com.googosoft.modules.app.dao.YwcDao;
import com.googosoft.modules.app.pojo.Ccsqbx;
import com.googosoft.modules.app.pojo.Ccsqspxq;
import com.googosoft.modules.app.pojo.Gwjdsqsp;
import com.googosoft.modules.app.pojo.Jkspxq;
import com.googosoft.modules.app.pojo.Login;
import com.googosoft.modules.app.pojo.Rcbxxq;
import com.googosoft.modules.app.pojo.Wdxc;
import com.googosoft.modules.app.pojo.YwcReturnMsg;
import com.googosoft.pojo.ImgInfo;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.system.index.DeskService;
import com.googosoft.service.wsbx.CcbxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.ccyw.CcywsqService;
import com.googosoft.service.wsbx.jkgl.JksqService;
import com.googosoft.service.wsbx.process.WsbxProcessService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Const;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.security.EncryptUtils;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
/**
*@author 杨超超
*@date   2018年1月31日---下午1:24:37
*/
@Service("ywcservice")
public class YwcService extends BaseService {

	@Resource(name = "ywcdao")
	private YwcDao ywcdao;
	@Resource(name = "deskService")
	private DeskService deskService;
	@Resource(name = "ccbxService")
	private CcbxService ccbxService;
	@Resource(name="ccywsqDao")
	private CcywsqDao ccywsqDao;
	@Resource(name="ccywsqService")
	CcywsqService ccywsqService;
	@Autowired
	private EchoService echoService;
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	@Resource(name="identityService")
	IdentityService identityService;
	@Resource(name="workflowService")
	WorkflowService workflowService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name = "rcbxDao")
	private RcbxDao rcbxDao;
	@Resource(name = "ccbxDao")
	private CcbxDao ccbxDao;
	@Resource(name="jksqService")
	private JksqService jksqService;
	@Resource(name="phoneDao")
	private PhoneDao phoneDao;
	@Resource(name = "ProcessService")
	private WsbxProcessService wsbxprocessservice;
	// 流程
	@Resource(name = "ProcessService")
	private WsbxProcessService processservice;
	Gson gson = new Gson();
	//定义
	private int pageSize = 10;//分页 每页显示个数
	private double pluginVersion=1.1;//插件版本

	/*************************** 上传图片 *******************************/
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	public String tpscgetinfo(MultipartHttpServletRequest request) {
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
            	String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
            	//物理路径
            	String wllu_path = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");

            	String filename = file.getOriginalFilename();
            	String kzm = filename.substring(filename.lastIndexOf(".")).toLowerCase();//带有.
            	String name = UuidUtil.get32UUID() + kzm;
				String appfilepath = xnlu_path + "/phone/" + name;//回传手机端的访问路径
				
				imgInfo.setOldname(filename);
				imgInfo.setNewname(appfilepath);
				
				String pcfilepath = wllu_path + "/phone/" + name;//pc端存放地址
				File uploadPic = new File(wllu_path + "/phone/");
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
	/*************************** 重写登录借口 *******************************/
	/**
	* 加载首页
	* @return
	*/
	public Object login(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
	    String username = Validate.isNullToDefaultString(map.get("uname"),"");
	    String password = Validate.isNullToDefaultString(map.get("upwd"),"");
	    Login login=new Login();
	    Map ryxx_map = getUserByNameAndPwd(username,password);
	    String rybh = Validate.isNullToDefaultString(ryxx_map.get("rybh")+"","");
	    String errCode = ryxx_map.get("errCode").toString();//错误代码
	    List shxx = phoneDao.getShxx(Validate.isNullToDefaultString(ryxx_map.get("GUID"), ""));
	    String num = shxx.size()+"";
	    if("1".equals(errCode)){
	    	doAddOplogPhone(OplogFlag.LOGIN,"用户登录",rybh);
	          login.setMsg("登录成功!");
	    	  login.setSuccess(true);
	          login.setRybh(Validate.isNullToDefaultString(ryxx_map.get("GUID"), ""));
	    	  login.setUserId(Validate.isNullToDefaultString(ryxx_map.get("GUID"), ""));
	    	  login.setName(Validate.isNullToDefaultString(ryxx_map.get("XM"), ""));
	    	  login.setDepartment(Validate.isNullToDefaultString(ryxx_map.get("BMH"), "")+"*"+Validate.isNullToDefault(ryxx_map.get("MC"), ""));
	    	  login.setSaas(Validate.isNullToDefaultString(ryxx_map.get("BMH"), ""));
	    	  login.setSex(Validate.isNullToDefaultString(ryxx_map.get("XBMC"), ""));
	    	  login.setTximg(Validate.isNullToDefaultString(ryxx_map.get("URL"), ""));
	          int a3 = phoneDao.checkCzqx("110201",rybh);
	          int a4 = phoneDao.checkCzqx("110301",rybh);
	          int a5 = phoneDao.checkCzqx("111101",rybh);
	          int a6 = phoneDao.checkCzqx("130201",rybh);
	          int a7 = phoneDao.checkCzqx("130101",rybh);
	          List list=new ArrayList<>();
	          //代办业务 1
	          Map  dbmap=new HashMap<>();
	          dbmap.put("mId", "daibanyewu");//模块Id
	          dbmap.put("mName", "待办业务");//模块名称
	          dbmap.put("dspsl", "0");//待审批数量
	          dbmap.put("mPath", "/app/image/dbyw.png");//模块图片路径
	          dbmap.put("isPlugin", "false");//是否是插件
	          dbmap.put("pLuginName", "daibanyewu");//插件名称
	          dbmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(dbmap);

	          //我的申请 2
	          Map wdsqmap=new HashMap<>();
	          wdsqmap.put("mId", "wodeshenqing");//模块Id
	          wdsqmap.put("mName", "我的申请");//模块名称
	          wdsqmap.put("dspsl", "0");//待审批数量
	          wdsqmap.put("mPath", "/app/image/wdsq.png");//模块图片路径
	          wdsqmap.put("isPlugin", "false");//是否是插件
	          wdsqmap.put("pLuginName", "wodeshenqing");//插件名称
	          wdsqmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(wdsqmap);
	          if(a3>0) {
	          //日常报销 3
	          Map rcbxmap=new HashMap<>();
	          rcbxmap.put("mId", "richangbaoxiao");//模块Id
	          rcbxmap.put("mName", "日常报销");//模块名称
	          rcbxmap.put("dspsl", "0");//待审批数量
	          rcbxmap.put("mPath", "/app/image/rcbx.png");//模块图片路径
	          rcbxmap.put("isPlugin", "true");//是否是插件
	          rcbxmap.put("pLuginName", "richangbaoxiao");//插件名称
	          rcbxmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(rcbxmap);
	          }
	          if(a4>0) {
	          //出差报销 4
	          Map ccbxmap=new HashMap<>();
	          ccbxmap.put("mId", "chuchaibaoxiao");//模块Id
	          ccbxmap.put("mName", "出差报销");//模块名称
	          ccbxmap.put("dspsl", "0");//待审批数量
	          ccbxmap.put("mPath", "/app/image/ccbx.png");//模块图片路径
	          ccbxmap.put("isPlugin", "false");//是否是插件
	          ccbxmap.put("pLuginName", "chuchaibaoxiao");//插件名称
	          ccbxmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(ccbxmap);
	          }
	          if(a5>0) {
	          //接待报销 5
	          Map jdbxmap=new HashMap<>();
	          jdbxmap.put("mId", "jiedaibaoxiao");//模块Id
	          jdbxmap.put("mName", "接待报销");//模块名称
	          jdbxmap.put("dspsl", "0");//待审批数量
	          jdbxmap.put("mPath", "/app/image/jdbx.png");//模块图片路径
	          jdbxmap.put("isPlugin", "false");//是否是插件
	          jdbxmap.put("pLuginName", "jiedaibaoxiao");//插件名称
	          jdbxmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(jdbxmap);
	          }
	          if(a6>0) {
	          //出差申请 6
	          Map ccsqmap=new HashMap<>();
	          ccsqmap.put("mId", "chuchaishenqing");//模块Id
	          ccsqmap.put("mName", "出差申请");//模块名称
	          ccsqmap.put("dspsl", "0");//待审批数量
	          ccsqmap.put("mPath", "/app/image/ccsq.png");//模块图片路径
	          ccsqmap.put("isPlugin", "false");//是否是插件
	          ccsqmap.put("pLuginName", "chuchaishenqing");//插件名称
	          ccsqmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(ccsqmap);
	          }
	          if(a7>0) {
	          //接待申请 7
	          Map jdsqmap=new HashMap<>();
	          jdsqmap.put("mId", "jiedaishenqing");//模块Id
	          jdsqmap.put("mName", "接待申请");//模块名称
	          jdsqmap.put("dspsl", "0");//待审批数量
	          jdsqmap.put("mPath", "/app/image/jdsq.png");//模块图片路径
	          jdsqmap.put("isPlugin", "false");//是否是插件
	          jdsqmap.put("pLuginName", "jiedaishenqing");//插件名称
	          jdsqmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(jdsqmap);
	          }
	          //我的薪酬 8
	          Map wdxcmap=new HashMap<>();
	          wdxcmap.put("mId", "wodexinchou");//模块Id
	          wdxcmap.put("mName", "我的薪酬");//模块名称
	          wdxcmap.put("dspsl", "0");//待审批数量
	          wdxcmap.put("mPath", "/app/image/wdxc.png");//模块图片路径
	          wdxcmap.put("isPlugin", "false");//是否是插件
	          wdxcmap.put("pLuginName", "wodexinchou");//插件名称
	          wdxcmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(wdxcmap);
	          
	          //我的项目 9
	          Map wdxmmap=new HashMap<>();
	          wdxmmap.put("mId", "wodexiangmu");//模块Id
	          wdxmmap.put("mName", "我的项目");//模块名称
	          wdxmmap.put("dspsl", "0");//待审批数量
	          wdxmmap.put("mPath", "/app/image/wdxm.png");//模块图片路径
	          wdxmmap.put("isPlugin", "false");//是否是插件
	          wdxmmap.put("pLuginName", "wodexiangmu");//插件名称
	          wdxmmap.put("pluginVersion", pluginVersion);//插件版本
	          list.add(wdxmmap);
	          login.setItems(list);
	          return new Gson().toJson(login);
	    }else if("3".equals(errCode)){
	      return "{\"success\":\"false\",\"msg\":\"用户名或密码错误！\"}";
	    }
	    else if("6".equals(errCode)){
	      return "{\"success\":\"false\",\"msg\":\"不允许使用初始密码登录，请先修改密码！\",\"userId\":\"" + Validate.isNullToDefaultString(ryxx_map.get("GUID"), "") + "\"}";
	    }
	    else{
	      return "{\"success\":\"false\",\"msg\":\"抱歉，系统出现未知的错误！\"}";
	    }
	}
	
	/**
	* 获取用户信息
	* @param username
	* @param password
	* @return
	*/
	private Map getUserByNameAndPwd(String username,String password)  {
		Map rymap = phoneDao.findUser(username);
	    if(rymap.isEmpty()){
	    	rymap.put("errCode", "3");//密码错误
	    }else{
	    	String mm = Validate.isNullToDefaultString(rymap.get("MM"), "");
	        String csmm = phoneDao.getCsmm();
	        if(Validate.isNull(mm)){
	        	if(csmm.equals(password)){
	        		rymap.put("errCode", "6");//初始密码
	        	}else{
	        		rymap.put("errCode", "3");//密码错误
	        	}
	        }else{
		        password = EncryptUtils.encryptToSHA(password,Const.SALTKEY);
		        if(mm.equals(password)){
		        	csmm = EncryptUtils.encryptToSHA(csmm,Const.SALTKEY);
		        	if(mm.equals(csmm)){
		        		rymap.put("errCode", "6");
		        	}else{
		        		rymap.put("errCode", "1");
		        	}
		        }else{
		        	rymap.put("errCode", "3");//密码错误
		        }
	        }
	    }
	    return rymap;
	}
	/*************************** 6.出差申请接口 *******************************/
	// （1）、获取人员基本信息
	@ResponseBody
	public String ccsq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String saas = Validate.isNullToDefaultString(map.get("saas"), "");// 部门编号
		 Map dwmap=ywcdao.getgyryguid(userId);
		 String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		// 查询所有部门信息 单位
		List<Map<String, Object>> bmlist = ywcdao.getbmlist();
		List list = new ArrayList();
		List list1 = new ArrayList();
		try {
			for (int i = 0; i < bmlist.size(); i++) {
				Map map1 = new HashMap();
				map1.put("departmentId", bmlist.get(i).get("DWBH"));
				map1.put("departmentName", bmlist.get(i).get("MC"));
				// 根据传过来的 部门编号 查询 部门下所有的人员信息
				List<Map<String, Object>> rylist = ywcdao.getryxxlist(bmlist.get(i).get("DWBH"),userId);
				map1.put("list", rylist);
				list.add(map1);
			}
			//项目list
			List xmxxlist=new ArrayList<>();
			List<Map<String, Object>> xmlist = ywcdao.getxmlist(userId,saas,rybh);
			for (int i = 0; i < xmlist.size(); i++) {
				Map map1 = new HashMap();
				map1.put("projectId", xmlist.get(i).get("GUID"));
				map1.put("projectName", xmlist.get(i).get("XMMC"));
				map1.put("projectType", xmlist.get(i).get("JFLXMC"));
				map1.put("projectMoney", xmlist.get(i).get("YE"));
				xmxxlist.add(map1);
			}
			//出差方式list  交通工具集合
			List<Map<String, Object>> ccfslist=dictService.getDict(Constant.JTGJ);
			for(int j=0;j<ccfslist.size();j++) {
				Map map2=new HashMap<>();
				if("1".equals(ccfslist.get(j).get("MS"))) {
					map2.put("id",Validate.isNullToDefaultString(ccfslist.get(j).get("DM"), "") );
					map2.put("name",Validate.isNullToDefaultString(ccfslist.get(j).get("MC"), "") );
					list1.add(map2);
				}
			}
			//省份list
			List<Map<String, Object>> provincelist = ywcdao.getProvicesList();
			List sflist=new ArrayList<>();
			for(int k=0;k<provincelist.size();k++) {
				Map map3=new HashMap<>();
				map3.put("id",Validate.isNullToDefaultString(provincelist.get(k).get("PROVINCEID"), "") );
				map3.put("province",Validate.isNullToDefaultString(provincelist.get(k).get("PROVINCE"), "") );
				sflist.add(map3);
			}
			msg.setSuccess(true);
			msg.setMsg("数据加载成功!!");
			msg.setSclj(Const.SJCJ);
			msg.setItems(list);
			msg.setXmlist(xmxxlist);
			msg.setTransportList(list1);//交通工具list
			msg.setProvincelist(sflist);
		} catch (Exception e) {
			msg.setMsg("暂无数据!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}

	// （2）、获取项目的基本信息
	@ResponseBody
	public String xmhq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String saas = Validate.isNullToDefaultString(map.get("saas"), "");// 部门编号
		String xmid = Validate.isNullToDefaultString(map.get("xmid"), "");// 项目id 如果xmid为空，项目传全部的，如果xmid有值，剔除这些项目，项目有多的话，用逗号隔开
		 Map dwmap=ywcdao.getgyryguid(userId);
		 String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		try {
			List list = new ArrayList();
			// 根据传过来的xmid 查询项目
			List<Map<String, Object>> xmlist = ywcdao.getxmlist(userId,saas,rybh);
			if (Validate.noNull(xmlist)) {
				for (int i = 0; i < xmlist.size(); i++) {
					Map map1 = new HashMap();
					map1.put("xmid", xmlist.get(i).get("GUID"));
					map1.put("xmmc", xmlist.get(i).get("XMMC"));
					map1.put("xmdl", xmlist.get(i).get("XMDLMC"));
					map1.put("xmfzr", xmlist.get(i).get("FZRXM"));
					map1.put("jflx", xmlist.get(i).get("JFLXMC"));
					map1.put("syje", xmlist.get(i).get("YE"));
					list.add(map1);
				}
				if(xmlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else{
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}

	// （3）出差申请提交
	@ResponseBody
	public String ccsqtj(PageData pd,HttpSession session) {
		YwcReturnMsg msg = new YwcReturnMsg();
		 Map map1 = gson.fromJson(pd.getString("key"), Map.class);
		 String ccry = Validate.isNullToDefaultString(map1.get("ccry"), "");// 出差人员
		 String xmguid = Validate.isNullToDefaultString(map1.get("xmid"), "");// 项目GUID
		 String userId = Validate.isNullToDefaultString(map1.get("userId"), "");// 用户guid
		 List<Map> imglist = (List) map1.get("imglist");//图片数据集合
		 Map dwmap=ywcdao.getgyryguid(userId);
		 String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		 String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
		 String djlx="000000"; 
		 String ccrysz []=ccry.split(",");
		 String xmguidsz []=xmguid.split(",");
		//生成主表出差申请主表id zbid
		String zbid=GenAutoKey.get32UUID();
		//生成单据编号
		String djbh = GenAutoKey.createKeyforth("cw_ccsqspb", "CC", "djbh");
		int m = 0;
		DshInfoMap msgMap = new DshInfoMap();
		try {
			int a=ywcdao.insertCcywsq(pd,zbid);
			if (a>0) {
					for (int i=0;i<ccrysz.length;i++) {//多个出差人员保存方法
						if(ccrysz[i]==userId) {
						}else {
							ywcdao.insertTxryxx(ccrysz[i],zbid);
						}
					}
					for (int j=0;j<xmguidsz.length;j++) {//多个项目经费保存方法
						ywcdao.insertXmxx(xmguidsz[j],zbid);
					}
					//行程信息保存
					ywcdao.insertxcxx(pd,zbid);
					//附件保存方法   图片
					for(int k=0;k<imglist.size();k++ ) {
						String newname=Validate.isNullToDefaultString(imglist.get(k).get("newname"),"");
						String oldname=Validate.isNullToDefaultString(imglist.get(k).get("oldname"),"");
						newname=newname.substring(newname.lastIndexOf("phone"));
						ywcdao.insertimglist(zbid,djlx,rybh,newname,oldname);
					}
					GX_SYS_RYB ryb = new GX_SYS_RYB();
					ryb.setDwbh(dwbh);
					ryb.setGuid(userId);
					session.setAttribute(Const.SESSION_USER, ryb);
				//开启提交 工作流程
				if(Validate.noNull(zbid)){
					String procinstid =  ccywsqService.submitBySqr(zbid);
					if(Validate.noNull(procinstid)) {
						m++;
						//从task表中查找流程审核人
						String shr = echoService.getShrGuid(procinstid);
						//如果不是审核通过的单据（通过的会在task表被删除）
						if(Validate.noNull(shr)) {
							if(!msgMap.containsKey(shr)) {
								msgMap.put(shr, new ArrayList<DshInfo>());
							}
							DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(zbid);
							msgMap.get(shr).add(shxxMsg);
						}
					}
				}
				msg.setMsg("提交成功!!");
				msg.setSuccess(true);
				msg.setYwdh(djbh);
				msg.setZbid(zbid);
			}
		} catch (Exception e) {
			msg.setMsg("提交失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//(4)根据省份查询市区
	@ResponseBody
	public String cityhq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String id = Validate.isNullToDefaultString(map.get("id"), "");// 省份id
		try {
			List list=new ArrayList<>();
			List<Map<String, Object>> citylist = ywcdao.getCitiesByProvince(id);
			for (int i = 0; i < citylist.size(); i++) {
				Map map1 = new HashMap();
				map1.put("id", Validate.isNullToDefault(citylist.get(i).get("CITYID"),""));
				map1.put("city", Validate.isNullToDefault(citylist.get(i).get("CITY"),""));
				list.add(map1);
			}
			if(citylist.size()>0) {
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
				msg.setItems(list);
			}else{
				msg.setMsg("暂无数据!!");
				msg.setSuccess(false);
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}

	
	/*************************** 7.待办业务接口 *******************************/
	// （1）、列表接口
	@ResponseBody
	public String dsh(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String ywlx = Validate.isNullToDefaultString(map.get("ywlx"), "");// 业务类型 // 0、全部；1、出差申请；2、日常报销；3、出差报销；4、公务接待；5、接待报销
		String index1 = Validate.isNullToDefaultString(map.get("index"), "1");// 分页
		int index=Integer.parseInt(index1);
		// 根据传过来的userId 查询人员guid userId为rybh
		Map ryidmap = ywcdao.getryguid(userId);
		String ryguid = Validate.isNullToDefaultString(ryidmap.get("GUID"), "");
		String cxywlx = "";
		try {
			List list = new ArrayList();
			// 'ccsq' as ywlx 'gwjd' as ywlx 'rcbx' as ywlx 'ccbx' as ywlx
			// 'jdbx' as ywlx
			if ("0".equals(ywlx)) {// 查询全部
				cxywlx = "qb";
				List<Map<String, Object>> shxxlist = ywcdao.getShxx(userId, cxywlx,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", shxxlist.get(i).get("SHMC"));
					map1.put("time", shxxlist.get(i).get("TIME"));
					map1.put("bh", shxxlist.get(i).get("GUID"));
					map1.put("sqr", shxxlist.get(i).get("SQRXM"));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else{
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			} else if ("1".equals(ywlx)) {
				cxywlx = "ccsq";
				List<Map<String, Object>> shxxlist = ywcdao.getShxx(userId, cxywlx,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", shxxlist.get(i).get("SHMC"));
					map1.put("time", shxxlist.get(i).get("TIME"));
					map1.put("bh", shxxlist.get(i).get("GUID"));
					map1.put("sqr", shxxlist.get(i).get("SQRXM"));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else{
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}

			} else if ("2".equals(ywlx)) {
				cxywlx = "rcbx";
				List<Map<String, Object>> shxxlist = ywcdao.getShxx(userId, cxywlx,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("SHMC"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("sqr", Validate.isNullToDefaultString(shxxlist.get(i).get("SQRXM"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else{
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			} else if ("3".equals(ywlx)) {
				cxywlx = "ccbx";
				List<Map<String, Object>> shxxlist = ywcdao.getShxx(userId, cxywlx,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("SHMC"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("sqr", Validate.isNullToDefaultString(shxxlist.get(i).get("SQRXM"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else{
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			} else if ("4".equals(ywlx)) {
				cxywlx = "gwjd";
				List<Map<String, Object>> shxxlist = ywcdao.getShxx(userId, cxywlx,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("SHMC"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("sqr", Validate.isNullToDefaultString(shxxlist.get(i).get("SQRXM"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else{
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			} else if ("5".equals(ywlx)) {
				cxywlx = "jdbx";
				List<Map<String, Object>> shxxlist = ywcdao.getShxx(userId, cxywlx,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("SHMC"),""));
					map1.put("time",  Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("bh",    Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("sqr",   Validate.isNullToDefaultString(shxxlist.get(i).get("SQRXM"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else{
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}

	/*************************** 8.我发起列表 *******************************/
	// （1）、我发起列表
	@ResponseBody
	public String wfq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String ywlx = Validate.isNullToDefaultString(map.get("ywlx"), "");// 业务类型 // 0、全部；1、出差申请；2、日常报销；3、出差报销；4、公务接待；5、接待报销
		String index1 = Validate.isNullToDefaultString(map.get("index"), "1");// 分页
		int index=Integer.parseInt(index1);
		// 根据传过来的userId 查询人员guid userId为rybh
		Map ryidmap = ywcdao.getryguid(userId);
		String ryguid = Validate.isNullToDefaultString(ryidmap.get("GUID"), "");
		String rybh=Validate.isNullToDefaultString(ryidmap.get("RYBH"), "");
		String cxywlx = "";
		try {
			List list = new ArrayList();
			// 'ccsq' as ywlx 'gwjd' as ywlx 'rcbx' as ywlx 'ccbx' as ywlx  'jdbx' as ywlx
			if ("0".equals(ywlx)) {// 查询全部
				cxywlx = "qb";
				List<Map<String, Object>> shxxlist = ywcdao.getWfqShxx(ryguid, cxywlx,rybh,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map2 = new HashMap<>();
					map2.put("bh",    Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map2.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("TITLE"),""));
					map2.put("time",  Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map2.put("zt",    Validate.isNullToDefaultString(shxxlist.get(i).get("ZT"),""));
					map2.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map2);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else {
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			} else if ("1".equals(ywlx)) {  
				cxywlx = "ccsq";
				List<Map<String, Object>> shxxlist = ywcdao.getWfqShxx(ryguid, cxywlx,rybh,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("TITLE"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("zt", Validate.isNullToDefaultString(shxxlist.get(i).get("ZT"),""));
					map1.put("state",    Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else {
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}

			} else if ("2".equals(ywlx)) {
				cxywlx = "rcbx";
				List<Map<String, Object>> shxxlist = ywcdao.getWfqShxx(ryguid, cxywlx,rybh,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("TITLE"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("zt", Validate.isNullToDefaultString(shxxlist.get(i).get("ZT"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
					
				}else {
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			} else if ("3".equals(ywlx)) {
				cxywlx = "ccbx";
				List<Map<String, Object>> shxxlist = ywcdao.getWfqShxx(ryguid, cxywlx,rybh,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("TITLE"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("zt", Validate.isNullToDefaultString(shxxlist.get(i).get("ZT"),""));
					map1.put("state",  Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else {
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			} else if ("4".equals(ywlx)) {
				cxywlx = "gwjd";
				List<Map<String, Object>> shxxlist = ywcdao.getWfqShxx(ryguid, cxywlx,rybh,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("TITLE"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("zt", Validate.isNullToDefaultString(shxxlist.get(i).get("ZT"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else {
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			} else if ("5".equals(ywlx)) {
				cxywlx = "jdbx";
				List<Map<String, Object>> shxxlist = ywcdao.getWfqShxx(ryguid, cxywlx,rybh,index,pageSize);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("TITLE"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("zt", Validate.isNullToDefaultString(shxxlist.get(i).get("ZT"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(shxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else {
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	/***************************9.日常报销申请*******************************/
	//（1）日常报销申请列表
	@ResponseBody
	public String rcbxsqlist(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String index1 = Validate.isNullToDefaultString(map.get("index"), "1");// 分页
		// 根据传过来的userId 查询人员guid userId为rybh
		Map ryidmap = ywcdao.getryguid(userId);
		String ryguid = Validate.isNullToDefaultString(ryidmap.get("GUID"), "");
		String rybh = Validate.isNullToDefaultString(ryidmap.get("RYBH"), "");
		String dwbh = Validate.isNullToDefaultString(ryidmap.get("DWBH"), "");
		int index=Integer.parseInt(index1);
		try {
			List list = new ArrayList();
			// 根据传过来的userId 查询日常报销 项目
			List<Map<String, Object>> xmlist = ywcdao.getrcbxxmlist(userId,rybh,dwbh,index,pageSize);
			if (Validate.noNull(xmlist)) {
				for (int i = 0; i < xmlist.size(); i++) {
					Map map1 = new HashMap();
					map1.put("xmid", Validate.isNullToDefaultString(xmlist.get(i).get("GUID"),""));
					map1.put("xmmc", Validate.isNullToDefaultString(xmlist.get(i).get("XMMC"),""));
					map1.put("xmlx", Validate.isNullToDefaultString(xmlist.get(i).get("JFLX"),""));
					map1.put("xmdl", Validate.isNullToDefaultString(xmlist.get(i).get("XMDLMC"),""));
					map1.put("xmfzr", Validate.isNullToDefaultString(xmlist.get(i).get("FZR"),""));
					map1.put("ysje", Validate.isNullToDefaultString(xmlist.get(i).get("YSJE"),""));
					map1.put("ye", Validate.isNullToDefaultString(xmlist.get(i).get("YE"),""));
					map1.put("zfcgsyje", Validate.isNullToDefaultString(xmlist.get(i).get("ZFCGSYJE"),""));
					map1.put("fzfcgsyje", Validate.isNullToDefaultString(xmlist.get(i).get("FZFCGSYJE"),""));
					list.add(map1);
				}
				if(xmlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSuccess(true);
					msg.setItems(list);
				}else{
					msg.setMsg("暂无数据!!");
					msg.setSuccess(false);
				}
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//（2）报销明细费用修改接口详情
	@ResponseBody
	public String rcbxfyxg(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String index1 = Validate.isNullToDefaultString(map.get("index"), "1");// 分页
		String zbid = Validate.isNullToDefaultString(map.get("zbid"), GenAutoKey.get32UUID()); 
		String sclj=Const.SJCJ;
		try {
			List list = new ArrayList();
			// 报销明细信息
			List<Map<String,Object>> bxmxxxlist = ywcdao.getFyList(zbid);
			if (Validate.noNull(bxmxxxlist)) {
				for (int i = 0; i < bxmxxxlist.size(); i++) {
					Map map1 = new HashMap();
					map1.put("fymc", Validate.isNullToDefaultString(bxmxxxlist.get(i).get("KMMC"),""));
					map1.put("fyid", Validate.isNullToDefaultString(bxmxxxlist.get(i).get("KMBH"),""));
					list.add(map1);
				}
//				if(bxmxxxlist.size()>0) {
					msg.setMsg("数据加载成功!!");
					msg.setSclj(sclj);
					msg.setSuccess(true);
					msg.setItems(list);
//				}else{
//					msg.setMsg("暂无数据!!");
//					msg.setSuccess(false);
//				}
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//（3）表单数据保存提交
	@ResponseBody
	public String rcbxfyxgtj(PageData pd) {
		YwcReturnMsg msg = new YwcReturnMsg();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		 List<Map> imglist = (List) map.get("imglist");//图片数据集合
		 String djlx="000000";
		 Map dwmap=ywcdao.getgyryguid(userId);
		 String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		//生成主表日常报销主表id zbid
		String zbid=GenAutoKey.get32UUID();
		//生成单据编号
		String djbh = GenAutoKey.createKeyforth("CW_RCBXZB","RC", "djbh"); 
		int i = 0;
		DshInfoMap msgMap = new DshInfoMap();
		try {
			int a=ywcdao.rcbxfyxgtj(pd,djbh,zbid);
			if (a>0) {
				//附件保存方法   图片
				for(int k=0;k<imglist.size();k++ ) {
					String newname=Validate.isNullToDefaultString(imglist.get(k).get("newname"),"");
					String oldname=Validate.isNullToDefaultString(imglist.get(k).get("oldname"),"");
					newname=newname.substring(newname.lastIndexOf("phone"));
					ywcdao.insertimglist(zbid,djlx,rybh,newname,oldname);
				}
				msg.setMsg("提交成功!!");
				msg.setSuccess(true);
				msg.setYwdh(djbh);
				msg.setZbid(zbid);
			}
		} catch (Exception e) {
			msg.setMsg("提交失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//（4）结算方式信息获取
	@ResponseBody
	public String rcbxjsfs(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		try {
			List list = new ArrayList();
			//获取对公支付 对方单位
			List<Map<String,Object>> dgdfdw=ywcdao.getWldwList();
			for(int i=0;i<dgdfdw.size();i++) {
				List yhwllist=new ArrayList();
				Map  dfyhmap=new HashMap();
				String dwmc=Validate.isNullToDefaultString(dgdfdw.get(i).get("DWMC"), "");// 单位名称
				String dwid=Validate.isNullToDefaultString(dgdfdw.get(i).get("GUID"), "");// 单位ID
				String wlbh=Validate.isNullToDefaultString(dgdfdw.get(i).get("WLBH"), "");// 往来编号 作为条件查询 对方银行
				String dwdz=Validate.isNullToDefaultString(dgdfdw.get(i).get("DWDZ"), "");// 单位地址
				//获取 对方银行 根据往来编号  
				List<Map<String,Object>> wlyhlist = ywcdao.getWldwYh(wlbh);
				for(int j=0;j<wlyhlist.size();j++) {
					String yhmc=Validate.isNullToDefaultString(wlyhlist.get(j).get("KHYH"), "");// 银行名称
					String yhkh=Validate.isNullToDefaultString(wlyhlist.get(j).get("KHYHZH"), "");// 银行卡号
					Map yhmap=new HashMap();
					yhmap.put("yhmc", yhmc);
					yhmap.put("yhkh", yhkh);
					yhwllist.add(yhmap);
				}
				dfyhmap.put("dwmc", dwmc);
				dfyhmap.put("dwid", wlbh);
				dfyhmap.put("dwdz", dwdz);
				dfyhmap.put("dfyh", yhwllist);
				list.add(dfyhmap);
			}
			if(dgdfdw.size()>0) {
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
				msg.setItems(list);
			}else{
				msg.setMsg("暂无数据!!");
				msg.setSuccess(false);
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//（5）结算方式数据提交
	@ResponseBody
	public String rcbxjsfstj(PageData pd,HttpSession session) {
		YwcReturnMsg msg = new YwcReturnMsg();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("zbid"), "");// 主表id
		String type = Validate.isNullToDefaultString(map.get("type"), "");
		String xz = Validate.isNullToDefaultString(map.get("rylx"), "");// 弹窗页面 返回人员类型 选择的第几个 yxsj 二级院系书记  yzfzr 二级院系院长或行政负责人
		DshInfoMap msgMap = new DshInfoMap();
		Map dwmap=ywcdao.getgyryguid(userId);
		String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
		String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"), "");
		try {
			int a=ywcdao.rcbxjsfstj(pd);
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
					msg.setMsg("提交成功!");
					msg.setSuccess(true);
				}else {
					msg.setMsg("提交失败!");
					msg.setSuccess(false);
				}
			}
		} catch (Exception e) {
			msg.setMsg("提交失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//(6)日常报销 结算方式 项目负责人获取
	@ResponseBody
	public String rcbxryxz(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String lx = Validate.isNullToDefaultString(map.get("lx"), "");// 类型  lx传的是0或者2    0代表项目负责人   2代表其他人
		String xmguid = Validate.isNullToDefaultString(map.get("xmguid"), "");// 项目guid 多个项目逗号隔开 ','
		try {
			List bmlist=new ArrayList<>();
			//lx  0.项目负责人 1.个人 2.其他人
			if("0".equals(lx)) {
			//业务逻辑 查询项目负责人 根据xmguid
				List<Map<String,Object>> xmfzrlist=ywcdao.getxmfzrlist(xmguid);
					//新建项目负责人集合 将所有的项目负责人放入一个字段  通过','分割开
					String fzrrybh="";
					for(int i=0;i<xmfzrlist.size();i++) {
					    fzrrybh=Validate.isNullToDefaultString(xmfzrlist.get(i).get("fzr"), "");
					}
					//根据项目负责人 查询项目负责人所在的部门
					List<Map<String,Object>> xmfzrszbm=ywcdao.xmfzrszbmlist(fzrrybh);
					for(int j=0;j<xmfzrszbm.size();j++) {
						Map bmmap=new HashMap<>();
						String departmentId=Validate.isNullToDefaultString(xmfzrszbm.get(j).get("dwbh"), "");
						String departmentName=Validate.isNullToDefaultString(xmfzrszbm.get(j).get("dwmc"), "");
						//根据项目负责人 查询负责人银行卡信息
						String fzrrybhs []=fzrrybh.split(",");
						List fzrlist=new ArrayList<>();
						for(int k=0;k<fzrrybhs.length;k++) {//有几个项目负责人循环几次  每个项目负责人下边放 项目负责人的银行信息
							Map fzrmap=new HashMap<>();
							String fzrbh=fzrrybhs[k];
							Map ryguidmap=ywcdao.getgyryrybh(fzrbh);
							String fzrguid=Validate.isNullToDefaultString(ryguidmap.get("GUID"), "");
							String personelName=Validate.isNullToDefaultString(ryguidmap.get("RYXM"), "");
							List<Map<String,Object>> yhxxlist=ywcdao.getyhxxlist(fzrguid);
							List yhlist=new ArrayList<>();//银行list
							for(int z=0;z<yhxxlist.size();z++) {
								Map yhmap=new HashMap<>();
								yhmap.put("id", Validate.isNullToDefaultString(yhxxlist.get(z).get("guid"), ""));//银行表 guid
								yhmap.put("yhkh", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyhzh"), ""));//银行卡号
								yhmap.put("yhmc", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyh"), ""));//银行名称
								yhlist.add(yhmap);
							}
							fzrmap.put("personelId", fzrguid);
							fzrmap.put("personelName", personelName);
							fzrmap.put("yhxxlist", yhlist);
							fzrlist.add(fzrmap);
						}
						bmmap.put("departmentId", departmentId);
						bmmap.put("departmentName", departmentName);
						bmmap.put("list", fzrlist);
						bmlist.add(bmmap);
					}
						msg.setItems(bmlist);
						msg.setMsg("数据加载成功!!");
						msg.setSuccess(true);
			}else if("1".equals(lx)) {//个人
					List<Map<String,Object>> yhxxlist=ywcdao.getyhxxlist(userId);
					List yhlist=new ArrayList<>();//银行list
					for(int z=0;z<yhxxlist.size();z++) {
						Map yhmap=new HashMap<>();
						yhmap.put("id", Validate.isNullToDefaultString(yhxxlist.get(z).get("guid"), ""));//银行表 guid
						yhmap.put("yhkh", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyhzh"), ""));//银行卡号
						yhmap.put("yhmc", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyh"), ""));//银行名称
						yhlist.add(yhmap);
					}
					if(yhxxlist.size()>0) {
						msg.setItems(yhlist);
						msg.setMsg("数据加载成功!!");
						msg.setSuccess(true);
					}else{
						msg.setMsg("暂无数据!!");
						msg.setSuccess(false);
					}
			}else if("2".equals(lx)) {//其他人
				List<Map<String, Object>> sybmlist = ywcdao.getbmlist();
				List list = new ArrayList();
				for (int s = 0; s < sybmlist.size(); s++) {
					Map map1 = new HashMap();
					map1.put("departmentId", sybmlist.get(s).get("DWBH"));
					map1.put("departmentName", sybmlist.get(s).get("MC"));
					// 根据传过来的 部门编号 查询 部门下所有的人员信息
					List<Map<String, Object>> rylist = ywcdao.getryxxlist(sybmlist.get(s).get("DWBH"),userId);
					List newrylist=new ArrayList<>();
					for(int y=0;y<rylist.size();y++) {
						Map rymap=new HashMap<>();
						rymap.put("personelId", Validate.isNullToDefault(rylist.get(y).get("PERSONELID"), ""));
						rymap.put("personelName", Validate.isNullToDefault(rylist.get(y).get("PERSONELNAME"), ""));
						String personelid= (String) Validate.isNullToDefault(rylist.get(y).get("PERSONELID"), "");
						List yhxxlist=new ArrayList<>();
						rymap.put("yhxxlist", yhxxlist);
						newrylist.add(rymap);
					}
					map1.put("list", newrylist);
					list.add(map1);
				}
						msg.setItems(list);
						msg.setMsg("数据加载成功!!");
						msg.setSuccess(true);
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//查询银行卡
	@ResponseBody
	public String yhklist(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("ryid"), "");// 用户guid
		try {
			List<Map<String,Object>> yhxxlist=ywcdao.getyhxxlist(userId);
			List yhlist=new ArrayList<>();//银行list
			for(int z=0;z<yhxxlist.size();z++) {
				Map yhmap=new HashMap<>();
				yhmap.put("id", Validate.isNullToDefaultString(yhxxlist.get(z).get("guid"), ""));//银行表 guid
				yhmap.put("yhkh", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyhzh"), ""));//银行卡号
				yhmap.put("yhmc", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyh"), ""));//银行名称
				yhlist.add(yhmap);
			}
			msg.setItems(yhlist);
			msg.setMsg("数据加载成功!!");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//根据部门查询人员  
	@ResponseBody
	public String choosepeople(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String bmid = Validate.isNullToDefaultString(map.get("bmid"), "");// 部门ID
		try {
			// 根据传过来的 部门编号 查询 部门下所有的人员信息
			List<Map<String, Object>> rylist = ywcdao.getryxxlist(bmid,userId);
			List newrylist=new ArrayList<>();
			for(int y=0;y<rylist.size();y++) {
				Map rymap=new HashMap<>();
				rymap.put("personelid", Validate.isNullToDefault(rylist.get(y).get("PERSONELID"), ""));
				rymap.put("personelname", Validate.isNullToDefault(rylist.get(y).get("PERSONELNAME"), ""));
				String personelid= (String) Validate.isNullToDefault(rylist.get(y).get("PERSONELID"), "");
				List<Map<String,Object>> yhxxlist=ywcdao.getyhxxlist(personelid);
				List yhlist=new ArrayList<>();//银行list
				for(int z=0;z<yhxxlist.size();z++) {
					Map yhmap=new HashMap<>();
					yhmap.put("id", Validate.isNullToDefaultString(yhxxlist.get(z).get("guid"), ""));//银行表 guid
					yhmap.put("yhkh", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyhzh"), ""));//银行卡号
					yhmap.put("yhmc", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyh"), ""));//银行名称
					yhlist.add(yhmap);
				}
				rymap.put("yhlist", yhlist);
				newrylist.add(rymap);
			}
				msg.setItems(newrylist);
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	/***************************10.、审批数据详情页面*******************************/
	//(1）、出差申请 ①审批详情接口
	@ResponseBody
	public String ccsqxq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Ccsqspxq ccsqspxq=new Ccsqspxq();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String bh = Validate.isNullToDefaultString(map.get("bh"), "");// bh  主表id
		//根据主表ID 查询流程ID
		Map lcmap=ywcdao.getlcid(bh,"CW_CCSQSPB");
		String lcid=Validate.isNullToDefaultString(lcmap.get("PROCINSTID"),"");
		try {
			List list = new ArrayList();
			Map<String, Object> ccywsq = ywcdao.getCcywxq(bh);
			Map ccmap=new HashMap();
			String djbh= Validate.isNullToDefaultString(ccywsq.get("DJBH"), "");//单据编号
			String sqr= Validate.isNullToDefaultString(ccywsq.get("SQRXM"), "");//申请人
			String szbm= Validate.isNullToDefaultString(ccywsq.get("SZBM"), "");//所在部门
			String ccts= Validate.isNullToDefaultString(ccywsq.get("CCTS"), "");//出差天数
			String ccrs= Validate.isNullToDefaultString(ccywsq.get("CCRS"), "");//出差人数
			String cclx= Validate.isNullToDefaultString(ccywsq.get("CCLX"), "");//出差类型
			String pxfy= Validate.isNullToDefaultString(ccywsq.get("PXFY"), "");//培训费用
			String sfyzf= Validate.isNullToDefaultString(ccywsq.get("SFYZF"),"");//是否预支付（传是或者是否）
			String yzfje= Validate.isNullToDefaultString(ccywsq.get("YZFJE"),"");//预支付金额
			String ccnr= Validate.isNullToDefaultString(ccywsq.get("CCNR"), "");//出差内容
			String kssj= Validate.isNullToDefaultString(ccywsq.get("KSSJ"), "");//开始时间
			String jssj= Validate.isNullToDefaultString(ccywsq.get("JSSJ"), "");//结束时间
			String cfdd= Validate.isNullToDefaultString(ccywsq.get("CFDD"), "");//出发地点
			String mddd= Validate.isNullToDefaultString(ccywsq.get("MDDD"), "");//目的地点
			String jtgj1= Validate.isNullToDefaultString(ccywsq.get("JTGJID"), "");//交通工具（多个交通工具用逗号隔开）  只是id,id,id
			String ccsf= Validate.isNullToDefaultString(ccywsq.get("CCSF"), "");//出差省份
			String ccsq= Validate.isNullToDefaultString(ccywsq.get("CCSQ"), "");//市区
			//将交通工具id 用逗号分隔开 转换成名称
			jtgj1=jtgj1.replace(",","','");
			Map<String, Object> jtgjmap = ywcdao.getJtgjmap(jtgj1,bh);
			String jtgj=Validate.isNullToDefaultString(jtgjmap.get("JTGJ"), "");
			ccsqspxq.setMsg("数据加载成功!!");
			ccsqspxq.setSuccess(true);
			ccsqspxq.setDjbh(djbh);
			ccsqspxq.setSqr(sqr);
			ccsqspxq.setSzbm(szbm);
			ccsqspxq.setCcts(ccts);
			ccsqspxq.setCcrs(ccrs);
			ccsqspxq.setCclx(cclx);
			ccsqspxq.setPxfy(pxfy);
			ccsqspxq.setSfyzf(sfyzf);
			ccsqspxq.setYzfje(yzfje);
			ccsqspxq.setCcnr(ccnr);
			ccsqspxq.setKssj(kssj);
			ccsqspxq.setJssj(jssj);
			ccsqspxq.setCfdd(cfdd);
			ccsqspxq.setMddd(mddd);
			ccsqspxq.setJtgj(jtgj);
			ccsqspxq.setCcsf(ccsf);
			ccsqspxq.setCcsq(ccsq);
			//项目数据集合
			List<Map<String,Object>> xmxxlist=ccywsqDao.selectXmxxListById(bh);
			List xmjhlist=new ArrayList<>();
			for(int i=0;i<xmxxlist.size();i++) {
				Map map1=new HashMap<>();
				map1.put("xmmc",  Validate.isNullToDefaultString(xmxxlist.get(i).get("MC"), ""));
				map1.put("jflx",  Validate.isNullToDefaultString(xmxxlist.get(i).get("JFLXMC"), ""));
				map1.put("syje",  Validate.isNullToDefaultString(xmxxlist.get(i).get("YE"), ""));
				xmjhlist.add(map1);
			}
			ccsqspxq.setXmlist(xmjhlist);
			//rylist 人员list集合
			List<Map<String,Object>> rylist = ywcdao.gettxrylist(bh);
			ccsqspxq.setRylist(rylist);
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
			ccsqspxq.setImglist(imglist1);
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
			ccsqspxq.setLclist(lclist1);
		} catch (Exception e) {
			ccsqspxq.setMsg("数据加载失败!!");
			ccsqspxq.setSuccess(false);
		}
		return new Gson().toJson(ccsqspxq);
	}
	//②、出差申请审批提交接口
	@ResponseBody
	public String ccsqsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		YwcReturnMsg msg = new YwcReturnMsg();
		try {
			int a=ywcdao.ccsqsptj(session,pd,shyjb);
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
	//（2）日常报销审批详情  ①审批详情接口
	@ResponseBody
	public String rcbxxq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Rcbxxq rcbxxq=new Rcbxxq();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("bh"), "");// bh  主表id
		//根据主表ID 查询流程ID
		Map lcmap=ywcdao.getlcid(zbid,"CW_RCBXZB");
		String lcid=Validate.isNullToDefaultString(lcmap.get("PROCINSTID"),"");
		try {
			List list = new ArrayList();
			Map<String, Object> rcbxxqmap = ywcdao.getBxzbById(zbid);
			Map ccmap=new HashMap();
			String djbh= Validate.isNullToDefaultString(rcbxxqmap.get("DJBH"), "");//单据编号
			String bxr=  Validate.isNullToDefaultString(rcbxxqmap.get("BXR"), "");//报销人
			String szbm= Validate.isNullToDefaultString(rcbxxqmap.get("SZBM"), "");//所在部门
			String fjzzs= Validate.isNullToDefaultString(rcbxxqmap.get("FJZZS"), "");//附件总张数
			String bxzje= Validate.isNullToDefaultString(rcbxxqmap.get("BXZJE"), "");//报销总金额
			String sfkylbx= Validate.isNullToDefaultString(rcbxxqmap.get("SFKYLBX"), "");//是否科研类报销(传是或否)
			String bxsy= Validate.isNullToDefaultString(rcbxxqmap.get("BXSY"), "");//报销事由
			String bz=Validate.isNullToDefaultString(rcbxxqmap.get("BZ"),"");//备注
			rcbxxq.setMsg("数据加载成功!!");
			rcbxxq.setSuccess(true);
			rcbxxq.setDjbh(djbh);
			rcbxxq.setBxr(bxr);
			rcbxxq.setSzbm(szbm);
			rcbxxq.setFjzzs(fjzzs);
			rcbxxq.setBxzje(bxzje);
			rcbxxq.setSfkylbx(sfkylbx);
			rcbxxq.setBxsy(bxsy);
			rcbxxq.setBz(bz);
			List xmjhlist=new ArrayList<>();
			//项目数据集合
			List<Map<String,Object>> xmxxlist=ywcdao.getXmhxList(zbid);
			rcbxxq.setXmxxlist(xmxxlist);
			//日程报销信息list
			List<Map<String,Object>> bxmxxxlist = ywcdao.getFyList(zbid);
			List bxmxlist=new ArrayList<>();
			if (Validate.noNull(bxmxxxlist)) {
				for (int i = 0; i < bxmxxxlist.size(); i++) {
					if(Validate.noNull(bxmxxxlist.get(i).get("BXJE"))&&Validate.noNull(bxmxxxlist.get(i).get("FJZS"))) {
						Map map1 = new HashMap();
						map1.put("fymc", bxmxxxlist.get(i).get("FYMC"));
						map1.put("fyid", bxmxxxlist.get(i).get("GUID"));
						map1.put("je", bxmxxxlist.get(i).get("BXJE"));
						map1.put("pjzs", bxmxxxlist.get(i).get("FJZS"));
						map1.put("bz", bxmxxxlist.get(i).get("BZ"));
						map1.put("zfcgje", bxmxxxlist.get(i).get("ZFCGJE"));
						map1.put("fzfcgje", bxmxxxlist.get(i).get("FZFCGJE"));
						bxmxlist.add(map1);
					}
				}
			}
			rcbxxq.setBxxxlist(xmxxlist);
			//imglist  图片详情路径
			List<Map<String,Object>> imglist=ywcdao.gettpsclj(zbid);
			List imglist1=new ArrayList<>();
			for(int t=0;t<imglist.size();t++) {
				Map map2=new HashMap<>();
				String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
				String imgurl="'"+xnlu_path+"/"+imglist.get(t).get("PATH")+"'";
				map2.put("imgurl", xnlu_path+"/"+imglist.get(t).get("PATH"));
				imglist1.add(map2);
			}
			rcbxxq.setImglist(imglist1);
			//对公支付 dgzflist
			List<Map<String,Object>> dgzflist=ywcdao.getdgzflist(zbid);
			List dgzflist1=new ArrayList();
			for(int b=0;b<dgzflist.size();b++) {
				Map dgzfmap=new HashMap();
				dgzfmap.put("dfdw",  Validate.isNullToDefaultString(dgzflist.get(b).get("DWMC"), ""));
				dgzfmap.put("dfdq",  Validate.isNullToDefaultString(dgzflist.get(b).get("DFDQ"), ""));
				dgzfmap.put("dfyh",  Validate.isNullToDefaultString(dgzflist.get(b).get("DFYH"), ""));
				dgzfmap.put("dfzh",  Validate.isNullToDefaultString(dgzflist.get(b).get("DFZH"), ""));
				dgzfmap.put("je",  Validate.isNullToDefaultString(dgzflist.get(b).get("JE"), ""));
				dgzflist1.add(dgzfmap);
			}
			rcbxxq.setDgzflist(dgzflist1);
			//对私支付 
			List<Map<String,Object>> dszflist=ywcdao.getDsList(zbid);
			rcbxxq.setDszflist(dszflist);
			//公务卡list  gwklist
			List<Map<String,Object>> gwklist=ywcdao.getGekList(zbid);
			rcbxxq.setGwklist(gwklist);
			//冲借款list
			List cxjklist = phoneDao.getcxjklist(zbid);
			Map shxxmap = new HashMap<>();
			List cjkList = new ArrayList<>();
			for(int i = 0;i<cxjklist.size();i++) {
				shxxmap = (Map)cxjklist.get(i);
				Map cjkMap = new HashMap<>();
				cjkMap.put("jkdh", Validate.isNullToDefaultString(shxxmap.get("jkid"),""));
				cjkMap.put("jkr", Validate.isNullToDefaultString(shxxmap.get("jkrxm"),""));
				cjkMap.put("szbm", Validate.isNullToDefaultString(shxxmap.get("szbm"),""));
				cjkMap.put("jkje", Validate.isNullToDefaultString(shxxmap.get("jkje"),""));
				cjkMap.put("cjkje", Validate.isNullToDefaultString(shxxmap.get("cjkje"),""));
				cjkList.add(cjkMap);
			}
			rcbxxq.setCjklist(cjkList);
			//发票信息list
			List<Map<String,Object>> fpxxlist=ywcdao.getFpxxlist(zbid);
			rcbxxq.setFplist(fpxxlist);
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
			rcbxxq.setLclist(lclist1);
		} catch (Exception e) {
			rcbxxq.setMsg("数据加载失败!!");
			rcbxxq.setSuccess(false);
		}
		return new Gson().toJson(rcbxxq);
	}
	//②、日常报销审批提交接口
	@ResponseBody
	public String rcbxsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		YwcReturnMsg msg = new YwcReturnMsg();
		try {
			int a=ywcdao.rcbxsptj(session,pd,shyjb);
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
	/***************************11.公务接待申请*******************************/
	//（1）、获取接待部门信息
	@ResponseBody
	public String jdsq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String bmid = Validate.isNullToDefaultString(map.get("saas"), "");// 部门id
		String sclj=Const.SJCJ;//上传路径
		try {
			//接待部门list
			List<Map<String,Object>> jdbmlist=ywcdao.getjdbmlist();
			//接待部门list下面 list
			msg.setMsg("数据加载成功!!");
			msg.setSuccess(true);
			msg.setSclj(sclj);
			msg.setJdbmlist(jdbmlist);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	/***************************12.差旅费申请*******************************/
	//（1）、获取出差业务
	@ResponseBody
	public String ccbxsqlist(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String index1 = Validate.isNullToDefaultString(map.get("index"), "1");// 分页
		int index=Integer.parseInt(index1);
		try {
			//出差业务list  事前审批单
			List<Map<String,Object>> ccywlist=ywcdao.getccywlist(pageSize,index,userId);
			for(int i=0;i<ccywlist.size();i++) {
				Map ccyemap=new HashMap<>();
				ccyemap.put("", Validate.isNullToDefault(ccywlist.get(i).get("XMMC"), ""));
				ccyemap.put("", Validate.isNullToDefault(ccywlist.get(i).get("SQSJ"), ""));
				ccyemap.put("", Validate.isNullToDefault(ccywlist.get(i).get("CCTS"), ""));
				ccyemap.put("", Validate.isNullToDefault(ccywlist.get(i).get("XMGUID"), ""));
				ccyemap.put("", Validate.isNullToDefault(ccywlist.get(i).get("SYJE"), ""));
				ccyemap.put("", Validate.isNullToDefault(ccywlist.get(i).get("BXJE"), ""));
			}
			if(ccywlist.size()>0) {
				msg.setItems(ccywlist);
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
			}else{
				msg.setMsg("暂无数据!!");
				msg.setSuccess(false);
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//（2）、搜索 出差业务
	@ResponseBody
	public String ccbxsearch(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String keyword = Validate.isNullToDefaultString(map.get("keyword"), "");// 分页
		try {
			//出差业务list
			List<Map<String,Object>> ssccywlist=ywcdao.getssccywlist(keyword,userId);
			if(ssccywlist.size()>0) {
				msg.setItems(ssccywlist);
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
			}else {
				msg.setMsg("没有您要查找的信息!!");
				msg.setSuccess(false);
			}
			
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//（3）出差业务报销详情填写
	@ResponseBody
	public String ccsqbxxqtx(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Ccsqbx msg = new Ccsqbx();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String bh = Validate.isNullToDefaultString(map.get("bh"), "");// 事前审批报销单 guid
		try {
			Map<String,Object> ssccywmap=ywcdao.getssxxccywlist(userId,bh);
			String ccrs=Validate.isNullToDefaultString(ssccywmap.get("ccrs"), "");//出差人数
			String ccts=Validate.isNullToDefaultString(ssccywmap.get("ccts"), "");//出差天数
			//出差类型
			List<Map<String, Object>> cclx = dictService.getDict("cclx");
			List cclxlist=new ArrayList<>();
			for(int i=0;i<cclx.size();i++) {
				Map cclxmap=new HashMap<>();
				cclxmap.put("id", Validate.isNullToDefaultString(cclx.get(i).get("DM"), ""));
				cclxmap.put("name", Validate.isNullToDefaultString(cclx.get(i).get("MC"), ""));
				cclxlist.add(cclxmap);
			}
			msg.setCclxlist(cclxlist);
			//部门人员list 
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
			//省份list
			List<Map<String, Object>> provincelist = ywcdao.getProvicesList();
			List sflist=new ArrayList<>();
			for(int k=0;k<provincelist.size();k++) {
				Map map3=new HashMap<>();
				List sqlist=new ArrayList<>();
				List<Map<String, Object>> citylist = ywcdao.getCitiesByProvince(Validate.isNullToDefaultString(provincelist.get(k).get("PROVINCEID"), ""));
				for (int i = 0; i < citylist.size(); i++) {
					Map map1 = new HashMap();
					map1.put("sqbh", Validate.isNullToDefault(citylist.get(i).get("CITYID"),""));
					map1.put("sqmc", Validate.isNullToDefault(citylist.get(i).get("CITY"),""));
					sqlist.add(map1);
				}
				map3.put("sfbh",Validate.isNullToDefaultString(provincelist.get(k).get("PROVINCEID"), "") );
				map3.put("sfmc",Validate.isNullToDefaultString(provincelist.get(k).get("PROVINCE"), "") );
				map3.put("sqlist", sqlist);
				sflist.add(map3);
			}
			msg.setSflist(sflist);
			//项目信息list
			List xmlist=ywcdao.getxmxxlist(bh);
			msg.setXmxxlist(xmlist);
			msg.setItems(list);
			msg.setSclj(Const.SJCJ);
			msg.setMsg("数据加载成功!!");
			msg.setCcts(ccts);
			msg.setCcrs(ccrs);
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//（3）出差业务报销  保存
	@ResponseBody
	public String ccbxtwotj(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Ccsqbx msg = new Ccsqbx();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String bh = Validate.isNullToDefaultString(map.get("bh"), "");// 事前审批报销单 guid
		List<Map> imglist = (List) map.get("imglist");//图片数据集合
		//生成主表出差申请主表id zbid
		String zbid=GenAutoKey.get32UUID();
		//生成单据编号
		String djbh =GenAutoKey.createKeyforth("CW_CLFBXZB","CL", "djbh");
		String djlx="000000";
		Map dwmap=ywcdao.getgyryguid(userId);
		 String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		try {
			int a = ywcdao.ccbxbc(pd,zbid,djbh);
			if(a>0) {
				//附件保存方法   图片
				for(int k=0;k<imglist.size();k++ ) {
					String newname=Validate.isNullToDefaultString(imglist.get(k).get("newname"),"");
					String oldname=Validate.isNullToDefaultString(imglist.get(k).get("oldname"),"");
					newname=newname.substring(newname.lastIndexOf("phone"));
					ywcdao.insertimglist(zbid,djlx,rybh,newname,oldname);
				}
			}
			msg.setZbid(zbid);
			msg.setDjbh(djbh);
			msg.setMsg("数据加载成功!!");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	
	
	//出差报销审核 提交
	@ResponseBody
	public String clfbxsptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		YwcReturnMsg msg = new YwcReturnMsg();
		try {
			int a=ywcdao.ccbxsptj(session,pd,shyjb);
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
	//出差报销 结算方式 获取
	@ResponseBody
	public String ccbxthree(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		 Map dwmap=ywcdao.getgyryguid(userId);
		 String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		try {
			//冲借款
			List cjknewlist=new ArrayList<>();
			List<Map> cjkList = ywcdao.getCxjkListHx(userId,rybh);
			for(int z=0;z<cjkList.size();z++) {
				Map cjkmap=new HashMap<>();
				cjkmap.put("jkr", Validate.isNullToDefault(cjkList.get(z).get("JKR"), ""));
				cjkmap.put("jkdh", Validate.isNullToDefault(cjkList.get(z).get("DJBH"), ""));
				cjkmap.put("jkje", Validate.isNullToDefault(cjkList.get(z).get("JKJE"), ""));
				cjkmap.put("szbm", Validate.isNullToDefault(cjkList.get(z).get("SZBM"), ""));
				cjknewlist.add(cjkmap);
			}
			msg.setCjklist(cjknewlist);
			List<Map<String,Object>> yhxxlist=ywcdao.getyhxxlist(userId);
			List yhlist=new ArrayList<>();//银行list
			for(int z=0;z<yhxxlist.size();z++) {
				Map yhmap=new HashMap<>();
				yhmap.put("id", Validate.isNullToDefaultString(yhxxlist.get(z).get("guid"), ""));//银行表 guid
				yhmap.put("yhkh", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyhzh"), ""));//银行卡号
				yhmap.put("yhmc", Validate.isNullToDefaultString(yhxxlist.get(z).get("khyh"), ""));//银行名称
				yhlist.add(yhmap);
			}
			msg.setYhkhlist(yhlist);
			//其他人list
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
			msg.setQtrlist(list);
			//获取对公支付 对方单位
			List<Map<String,Object>> dgdfdw=ywcdao.getWldwList();
			List dglist=new ArrayList<>();
			for(int i=0;i<dgdfdw.size();i++) {
				List yhwllist=new ArrayList();
				Map  dfyhmap=new HashMap();
				String dfdw=Validate.isNullToDefaultString(dgdfdw.get(i).get("DWMC"), "");// 单位名称
				String dfdwbh=Validate.isNullToDefaultString(dgdfdw.get(i).get("GUID"), "");// 单位ID
				String wlbh=Validate.isNullToDefaultString(dgdfdw.get(i).get("WLBH"), "");// 往来编号 作为条件查询 对方银行
				String dfdq=Validate.isNullToDefaultString(dgdfdw.get(i).get("DWDZ"), "");// 单位地址
				//获取 对方银行 根据往来编号  
				List<Map<String,Object>> wlyhlist = ywcdao.getWldwYh(wlbh);
				for(int j=0;j<wlyhlist.size();j++) {
					String guid=Validate.isNullToDefaultString(wlyhlist.get(j).get("GUID"), "");// 银行名称
					String yhmc=Validate.isNullToDefaultString(wlyhlist.get(j).get("KHYH"), "");// 银行名称
					String yhkh=Validate.isNullToDefaultString(wlyhlist.get(j).get("KHYHZH"), "");// 银行卡号
					Map yhmap=new HashMap();
					yhmap.put("id", guid);
					yhmap.put("name", yhmc);
					yhmap.put("yhzh", yhkh);
					yhwllist.add(yhmap);
				}
				dfyhmap.put("dfdw", dfdw);
				dfyhmap.put("dfdwbh", wlbh);
				dfyhmap.put("dfdq", dfdq);
				dfyhmap.put("dfyhlist", yhwllist);
				dglist.add(dfyhmap);
			}
			msg.setDgzflist(dglist);
			msg.setMsg("数据加载成功!!");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//出差报销 保存信息 并开启流程
	@ResponseBody
	public String ccbxthreetj(PageData pd,HttpSession session) {
		YwcReturnMsg msg = new YwcReturnMsg();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("zbid"), "");// 主表id
		String type = Validate.isNullToDefaultString(map.get("type"), "");
		String xz = Validate.isNullToDefaultString(map.get("rylx"), "");// 弹窗页面 返回人员类型 选择的第几个 yxsj 二级院系书记  yzfzr 二级院系院长或行政负责人
		Map dwmap=ywcdao.getgyryguid(userId);
		String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
		String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		DshInfoMap msgMap = new DshInfoMap();
		try {
			int a=ywcdao.ccbxthreetj(pd);
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
					msg.setMsg("提交成功!");
					msg.setSuccess(true);
				}else {
					msg.setSuccess(false);
					msg.setMsg("提交失败!");
				}
			}
		} catch (Exception e) {
			msg.setMsg("提交失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//出差报销申请 开启流程\
	public String ccbxlcsubmit(String guid, String shyj, String shzt,String key,String userId) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Map ccbxmap=ccbxDao.getRcbxById(guid);
		float fyje=Float.valueOf(ccbxmap.get("BXZJE")+"");
		String sfxy=ccbxmap.get("sfxy")+"";
		identityService.setAuthenticatedUserId(userId);
		variables.put("fyje", fyje);//费用报销金额
		if(fyje>3000&&sfxy.equals("1")||fyje>2000&&sfxy.equals("0")){
			variables.put("fyjelx", true);//判断是否经由部门分管领导审核
		}else{
			variables.put("fyjelx", false);//判断是否经由部门分管领导审核
		}
		//财务预审后的类型判断：科研，非科研，公务接待费，日常报销只需要考虑是否科研
		String sfkyl = Validate.isNullToDefaultString(ccbxmap.get("SFKYLBX"), "0");
		if("0".equals(sfkyl)){
			variables.put("fky", true);// 非科研
		}else{
			variables.put("ky", true);// 科研
		}
		ProcessInstance ps=null;
	    ps = workflowService.startProcess(variables,"bxlc");
		Task task = workflowService.queryUserTaskByInstanceId(userId, ps.getId());
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		ccbxDao.doUpdateProcinstId(guid, ps.getId());//往业务表里添加流程id
		int result = ywcdao.ccbxlcdoStatus(userId,guid,"1","");//修改主表审核状态
		return ps.getId();
	}
	/***************************14.借款流程 申请*******************************/
	//对公支付  单位信息获取
	@ResponseBody
	public String jksqdgzfhq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		try {
			//获取单位信息 list 
			List dwlist=new ArrayList<>();
			List<Map<String,Object>> dwxxlist=ywcdao.dwxxlist();
			for(int i=0;i<dwxxlist.size();i++) {
				Map dwxxmap=new HashMap<>();
				dwxxmap.put("dwid", Validate.isNullToDefault(dwxxlist.get(i).get("guid"), ""));
				dwxxmap.put("dwmc", Validate.isNullToDefault(dwxxlist.get(i).get("dwmc"), ""));
				dwxxmap.put("dwdz", Validate.isNullToDefault(dwxxlist.get(i).get("dwdz"), ""));
				dwxxmap.put("wlbh", Validate.isNullToDefault(dwxxlist.get(i).get("wlbh"), ""));
				//根据wlbh查询单位的银行卡信息
				List<Map<String,Object>> yhkxxlist=ywcdao.yhxxhqlist(dwxxlist.get(i).get("wlbh"));
				List yhlist=new ArrayList<>();//银行list
				for(int z=0;z<yhkxxlist.size();z++) {
					Map yhmap=new HashMap<>();
					yhmap.put("id", Validate.isNullToDefaultString(yhkxxlist.get(z).get("guid"), ""));//银行表 guid
					yhmap.put("yhkh", Validate.isNullToDefaultString(yhkxxlist.get(z).get("khyhzh"), ""));//银行卡号
					yhmap.put("yhmc", Validate.isNullToDefaultString(yhkxxlist.get(z).get("khyh"), ""));//银行名称
					yhlist.add(yhmap);
				}
				dwxxmap.put("yhlist",yhlist);
				dwlist.add(dwxxmap);
			}
			if(dwlist.size()>0) {
				msg.setItems(dwlist);
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
			}else {
				msg.setMsg("暂无数据!!");
				msg.setSuccess(false);
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//借款申请提交
	@ResponseBody
	public String jksqtj(PageData pd,HttpSession session) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		YwcReturnMsg msg = new YwcReturnMsg();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 当前登录人guid
		//根据userId 查询dwbh
		Map dwmap=ywcdao.getgyryguid(userId);
		String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"), "");
		//生成主表日常报销主表id zbid
		String zbid=GenAutoKey.get32UUID();
		//生成单据编号
		String djbh = GenAutoKey.createKeyforth("CW_JKYWB","JK", "djbh");
		try {
			int a=ywcdao.insertjksq(pd,zbid,djbh);
			if(a>0) {
				GX_SYS_RYB ryb = new GX_SYS_RYB();
				ryb.setDwbh(dwbh);
				ryb.setGuid(userId);
				session.setAttribute(Const.SESSION_USER, ryb);
				//开启流程
				String procinstid =  jksqService.sjsubmitBySqr(zbid);
				DshInfoMap msgMap = new DshInfoMap();
				if(Validate.noNull(procinstid)) {
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstid);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(zbid);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
			List list = new ArrayList();
			msg.setMsg("提交成功!!");
			msg.setSuccess(true);
			msg.setItems(list);
		} catch (Exception e) {
			msg.setMsg("提交失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//借款审批详情页面
	@ResponseBody
	public String jkspxq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Jkspxq  jkspxq=new Jkspxq();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("zbid"), "");// bh  主表id
		//根据主表ID 查询流程ID
		Map lcmap=ywcdao.getlcidgid(zbid,"CW_JKYWB");
		String lcid=Validate.isNullToDefaultString(lcmap.get("PROCINSTID"),"");
		try {
			List list = new ArrayList();
			//借款明细 主表
			Map jkzbmap = jksqService.getObjectById(zbid);
			String djbh=Validate.isNullToDefaultString(jkzbmap.get("DJBH"), "");//单据编号
			String jkrmc=Validate.isNullToDefaultString(jkzbmap.get("JKRMC"), "");//借款人名称
			String szbm=Validate.isNullToDefaultString(jkzbmap.get("SZBM"), "");//所在部门
			String jksj=Validate.isNullToDefaultString(jkzbmap.get("JKSJ"), "");//借款时间
			String jkzje=Validate.isNullToDefaultString(jkzbmap.get("JKZJE"), "");//借款总金额
			String jksy=Validate.isNullToDefaultString(jkzbmap.get("JKSY"), "");//借款事由
			String zffs=Validate.isNullToDefaultString(jkzbmap.get("ZFFS"), "");//支付方式 0.对私 1.对公
			jkspxq.setDjbh(djbh);
			jkspxq.setJkrmc(jkrmc);
			jkspxq.setSzbm(szbm);
			jkspxq.setJksj(jksj);
			jkspxq.setJkzje(jkzje);
			jkspxq.setJksy(jksy);
			jkspxq.setZffs(zffs);
			//项目信息list
			List<Map<String,Object>> xmxxlist=jksqService.getXmxxListById(zbid);
			List xmlist=new ArrayList<>();
			for(int i=0;i<xmxxlist.size();i++) {
				Map map1 = new HashMap<>();
				map1.put("xmmc", Validate.isNullToDefaultString(xmxxlist.get(i).get("mc"), ""));//项目名称
				map1.put("ye", Validate.isNullToDefaultString(xmxxlist.get(i).get("ye"), ""));//余额
				map1.put("zcje", Validate.isNullToDefaultString(xmxxlist.get(i).get("zcje"), ""));//支出金额
				xmlist.add(map1);
			}
			jkspxq.setXmxxlist(xmlist);
			//对私支付 
			List<Map<String,Object>> dszflist=ywcdao.getDsList(zbid);
			jkspxq.setDslist(dszflist);
			//对公支付
			List<Map<String,Object>> dgzflist=ywcdao.getdgzflist(zbid);
			List dgzflist1=new ArrayList();
			for(int b=0;b<dgzflist.size();b++) {
				Map dgzfmap=new HashMap();
				dgzfmap.put("dfdw",  Validate.isNullToDefaultString(dgzflist.get(b).get("DWMC"), ""));
				dgzfmap.put("dfdq",  Validate.isNullToDefaultString(dgzflist.get(b).get("DFDQ"), ""));
				dgzfmap.put("dfyh",  Validate.isNullToDefaultString(dgzflist.get(b).get("DFYH"), ""));
				dgzfmap.put("dfzh",  Validate.isNullToDefaultString(dgzflist.get(b).get("DFZH"), ""));
				dgzfmap.put("je",  Validate.isNullToDefaultString(dgzflist.get(b).get("JE"), ""));
				dgzflist1.add(dgzfmap);
			}
			jkspxq.setDglist(dgzflist1);
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
			if(lclist1.size()>0) {
				jkspxq.setLclist(lclist1);
				jkspxq.setMsg("数据加载成功!!");
				jkspxq.setSuccess(true);
			}else {
				jkspxq.setMsg("暂无数据!!");
				jkspxq.setSuccess(false);
			}
		} catch (Exception e) {
			jkspxq.setMsg("数据加载失败!!");
			jkspxq.setSuccess(false);
		}
		return new Gson().toJson(jkspxq);
	}
	//借款审批提交
	@ResponseBody
	public String jksptj(HttpSession session,PageData pd,OA_SHYJB shyjb) {
		YwcReturnMsg msg = new YwcReturnMsg();
		try {
			int a=ywcdao.jksptj(session,pd,shyjb);
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
	/*********************15.我的薪酬**************************/
	//(1)我的薪酬
	
	@ResponseBody
	public String wdxc(PageData pd ) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Wdxc msg = new Wdxc();
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 当前登录人guid
		String time = Validate.isNullToDefaultString(map.get("time"), "");// 当前登录人guid
		time=time.replace("-", ".");
		//根据userId 查询rybh
		Map dwmap=ywcdao.getgyryguid(userId);
		String rybh=Validate.isNullToDefaultString(dwmap.get("RYBH"), "");
		Map<String,Object> wdxcmap=ywcdao.wdxcmap(rybh,time);
		 String gwgz=Validate.isNullToDefaultString(wdxcmap.get("GWGZ"), "0.00");//岗位工资
	     String xjgz=Validate.isNullToDefaultString(wdxcmap.get("XJGZ"), "0.00");//薪级工资
	     String xzft=Validate.isNullToDefaultString(wdxcmap.get("XZFT"), "0.00");//新住房帖
	     String wybt=Validate.isNullToDefaultString(wdxcmap.get("WYBT"), "0.00");//物业补贴
	     String dh=Validate.isNullToDefaultString(wdxcmap.get("DSZF"), "0.00");//独回
	     String jcjx=Validate.isNullToDefaultString(wdxcmap.get("JCJX"), "0.00");//基础绩效
	     String jljx=Validate.isNullToDefaultString(wdxcmap.get("JLJX1"), "0.00");//奖励绩效
	     String bsbt=Validate.isNullToDefaultString(wdxcmap.get("BSBT"), "0.00");//博士补助
	     String gwbt=Validate.isNullToDefaultString(wdxcmap.get("GWBT"), "0.00");//岗位补贴
	     String bgz=Validate.isNullToDefaultString(wdxcmap.get("BGZ"), "0.00");//补工资
	     String yfhj=Validate.isNullToDefaultString(wdxcmap.get("YFHJ"), "0.00");//应发合计
	     String zfjj=Validate.isNullToDefaultString(wdxcmap.get("ZFJJ"), "0.00");//住房积金
	     String ylbx=Validate.isNullToDefaultString(wdxcmap.get("YLBX"), "0.00");//医疗报销
	     String dks=Validate.isNullToDefaultString(wdxcmap.get("DKS"), "0.00");//代扣税
	     String fz=Validate.isNullToDefaultString(wdxcmap.get("FZ"), "0.00");//房租
	     String syj=Validate.isNullToDefaultString(wdxcmap.get("SYJ"), "0.00");//失业金
	     String sbj=Validate.isNullToDefaultString(wdxcmap.get("SBJ"), "0.00");//社保金
	     String ylj=Validate.isNullToDefaultString(wdxcmap.get("YLJ"), "0.00");//养老金
	     String kk=Validate.isNullToDefaultString(wdxcmap.get("KK"), "0.00");//扣款
	     String kkhj=Validate.isNullToDefaultString(wdxcmap.get("KKHJ"), "0.00");//扣款合计
	     String sfhj=Validate.isNullToDefaultString(wdxcmap.get("SFHJ"), "0.00");//实发合计
	     msg.setGwgz(gwgz);
	     msg.setXjgz(xjgz);
	     msg.setXzft(xzft);
	     msg.setWybt(wybt);
	     msg.setDh(dh);
	     msg.setJcjx(jcjx);
	     msg.setJljx(jljx);
	     msg.setBsbt(bsbt);
	     msg.setGwbt(gwbt);
	     msg.setBgz(bgz);
	     msg.setYfhj(yfhj);
	     msg.setZfjj(zfjj);
	     msg.setYlbx(ylbx);
	     msg.setDks(dks);
	     msg.setFz(fz);
	     msg.setSyj(syj);
	     msg.setSbj(sbj);
	     msg.setYlj(ylj);
	     msg.setKk(kk);
	     msg.setKkhj(kkhj);
	     msg.setSfhj(sfhj);
		try {
			if(wdxcmap.size()>0) {
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
			}else {
				msg.setMsg("暂无数据!!");
				msg.setSuccess(false);
			}
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//流程接口新写
	@ResponseBody
	public String xjshr(PageData pd,HttpSession session) {
		YwcReturnMsg msg = new YwcReturnMsg();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("zbid"), "");// 主表id
		String type = Validate.isNullToDefaultString(map.get("type"), "");//提交类型 rcbx clfbx
	    Map dwmap=ywcdao.getgyryguid(userId);
	    String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
	    String rybh = Validate.isNullToDefaultString(dwmap.get("RYBH"),"");
		try {
				GX_SYS_RYB ryb = new GX_SYS_RYB();
				ryb.setDwbh(dwbh);
				ryb.setGuid(userId);
				ryb.setRybh(rybh);
				session.setAttribute(Const.SESSION_USER, ryb);
				Map fhmap=processservice.checkWhoSh( zbid, type);
				String XZ=Validate.isNullToDefaultString(fhmap.get("XZ"), "");
				if("submit".equals(XZ)) {
					msg.setLctjzszt("2");//1.展示 弹窗 2.不展示弹窗
				}else if("hq".equals(XZ)) {
					msg.setLctjzszt("2");
				}else {
					msg.setLctjzszt("1");
				}
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
	//下级审核人 弹窗判断 
	@ResponseBody
	public String xjshrsh(PageData pd,HttpSession session) {
		YwcReturnMsg msg = new YwcReturnMsg();
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"), "");// 用户guid
		String zbid = Validate.isNullToDefaultString(map.get("zbid"), "");// 主表id
		String type = Validate.isNullToDefaultString(map.get("type"), "");//提交类型 rcbx 
		String spcxzb=Validate.isNullToDefaultString(map.get("spcxzb"), "");//审批查询主表   
	    Map dwmap=ywcdao.getgyryguid(userId);
	    String dwbh=Validate.isNullToDefaultString(dwmap.get("DWBH"),"");
	    //查询判断条件
	    Map getlcidmap=ywcdao.getsyzd(zbid,spcxzb);
	    String money=Validate.isNullToDefaultString(getlcidmap.get("BXZJE"), "0.00");
	    double bxzje=Double.parseDouble(money);
	    String shzt=Validate.isNullToDefaultString(getlcidmap.get("SHZT"), "");
		try {
				GX_SYS_RYB ryb = new GX_SYS_RYB();
				ryb.setDwbh(dwbh);
				ryb.setGuid(userId);
				session.setAttribute(Const.SESSION_USER, ryb);
				String fhjg=processservice.selectWho( zbid, type);
				String bool = "2";
				if(bxzje<=5000&&!"2".equals(bool)&&("01".equals(shzt)||"04".equals(shzt))) {
					if ("1".equals(fhjg)) {// 1.弹窗 2.不弹窗
						bool = "1";
					}
				}
				msg.setLctjzszt(bool);
				msg.setMsg("数据加载成功!!");
				msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("数据加载失败!!");
			msg.setSuccess(false);
		}
		return new Gson().toJson(msg);
	}
}
