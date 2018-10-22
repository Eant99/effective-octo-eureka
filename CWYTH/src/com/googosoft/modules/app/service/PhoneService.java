package com.googosoft.modules.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.faces.flow.SwitchCase;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.googosoft.JSON.JsonArray;
import com.googosoft.JSON.JsonObject;
import com.googosoft.JSON.KeysValues;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.PropertiesUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.base.DictDao;
import com.googosoft.dao.fzgn.tzgg.FbxxDao;
import com.googosoft.dao.system.index.DeskDao;
import com.googosoft.dao.system.shenhe.ShenheDao;
import com.googosoft.modules.app.dao.PhoneDao;
import com.googosoft.modules.app.dao.YwcDao;
import com.googosoft.modules.app.pojo.Syss;
import com.googosoft.pojo.ImgInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.pojo.system.ZC_XGWD;
import com.googosoft.pojo.zcjz.ZC_ZJBEXTEND;
import com.googosoft.pojo.zcjz.zc_zjb;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.Const;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.TwoDimensionCode;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;
import com.googosoft.util.security.EncryptUtils;

@Service("phoneService")
public class PhoneService extends BaseService{
	@Resource(name="phoneDao")
	private PhoneDao phoneDao;
	@Resource(name="shenheDao")
	private ShenheDao shenheDao;
	@Resource(name="deskDao")
	private DeskDao deskDao;
	@Resource(name="dictDao")
	private DictDao dictDao;
	@Resource(name="pageService")
	private PageService pageService;
	@Resource(name="fbxxDao")
	private FbxxDao fbxxDao;
	@Resource(name = "ywcdao")
	private YwcDao ywcdao;
	

	Gson gson = new Gson();
	private int page_length = 10;

	/**
	* 用户登录
	* @return
	*/
	public String logingh(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
	    
	    String username = Validate.isNullToDefaultString(map.get("uname"),"");
	    String password = Validate.isNullToDefaultString(map.get("upwd"),"");
	    Map ryxx_map = getUserByNameAndPwd(username,password);
	    String rybh = Validate.isNullToDefaultString(ryxx_map.get("rybh")+"","");
	    
	    String errCode = ryxx_map.get("errCode").toString();//错误代码
	    if("1".equals(errCode)){
	    	doAddOplogPhone(OplogFlag.LOGIN,"用户登录",rybh);
	    	
	    	//在电脑端登录的时候会放进去的，如果不在电脑端登录，这里放进去也没用
//	    	Session session = SessionMap.sessions.get(rybh);
//	    	if(session == null){
//	    		session = LUser.getSession();
//	    		SessionMap.sessions.put(rybh, session);
//	    	}
	    	
	    	return "{\"success\":\"true\","
	          +"\"msg\":\"登录成功！\","
	    	  +"\"kpts\":\"10\","//验收单录入资产卡片的最大条数
	          +"\"userId\":\"" + rybh + "\","
	          +"\"pname\":\""+Validate.isNullToDefaultString(ryxx_map.get("xm"), "")+"\","
	          +"\"department\":\""+Validate.isNullToDefault(ryxx_map.get("dwbh"), "")+"*"+Validate.isNullToDefault(ryxx_map.get("mc"), "")+"\"}";
	    }
	    else if("3".equals(errCode)){
	      return "{\"success\":\"false\",\"msg\":\"用户名或密码错误！\"}";
	    }
	    else if("6".equals(errCode)){
	      return "{\"success\":\"false\",\"msg\":\"不允许使用初始密码登录，请先修改密码！\",\"userId\":\"" + rybh + "\"}";
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
  
	/**
	* 修改密码
	*/
	public Object xgmm(PageData pd){
	    Map map = gson.fromJson(pd.getString("key"), Map.class);
	    String rybh = map.get("userId").toString();
	    String newmm = Validate.isNullToDefaultString(map.get("newmm"),"");
	    String oldmm = Validate.isNullToDefaultString(map.get("oldmm"),"");
	    if(Validate.isNull(newmm)){
	    	return "{\"success\":\"false\",\"msg\":\"新密码不允许为空！\"}";
	    }
	    else if(newmm.equals(oldmm)){
	    	return "{\"success\":\"false\",\"msg\":\"新密码不允许与旧密码相同！\"}";
	    }
	    else if(newmm.length() > 16 || newmm.length() < 6){
	    	return "{\"success\":\"false\",\"msg\":\"新密码长度需要在6-16位之间！\"}";
	    }
	    else{ 
	    	if(phoneDao.checkPwd(oldmm,rybh)){
		        int r = phoneDao.doUpdPwd(newmm,rybh);
		        if(r == 0){
		        	return "{\"success\":\"false\",\"msg\":\"密码修改失败！\"}";
		        }
		        else if(r == -1){
		        	return "{\"success\":\"false\",\"msg\":\"不能使用初始密码作为新密码！\"}";
		        }
		        else{
		        	doAddOplogPhone(OplogFlag.UPD,"修改密码",rybh);
		        	return "{\"success\":\"true\",\"msg\":\"修改成功！\"}";
		        }
	    	}
	    	else{
	    		return "{\"success\":\"false\",\"msg\":\"原密码录入错误！\"}";
	    	}
	    }
	}

	/**
	* 桌面刷新
	*/
	public Object main(PageData pd){
	    Map map = gson.fromJson(pd.getString("key"), Map.class);
	    String userId = map.get("userId").toString();
//	    int tzggcounts = Integer.parseInt(phoneDao.getWdsl(userId));//未读通知公告
//	    int xtxxcounts = Integer.parseInt(phoneDao.getWdxx(userId));//未读系统消息
//	    int sysl = tzggcounts+xtxxcounts;
	    return "{\"success\":\"true\",\"msg\":\"桌面刷新成功！\"," +
	        "\"items\":[{\"mklx\":\"1\",\"dspsl\":\"0\",\"title\":\"首页\"},"//首页
	        + "{\"mklx\":\"2\",\"dspsl\":\"" +  phoneDao.getYwshCnt(userId,"") + "\",\"title\":\"待办事项\"},"//待办事项
	        + "{\"mklx\":\"3\",\"dspsl\":\"0\",\"title\":\"我的\"}]}";//我的
	}
	public Object sxlogin(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
	    String username = Validate.isNullToDefaultString(map.get("uname"),"");
	    String password = Validate.isNullToDefaultString(map.get("upwd"),"");
	    Map ryxx_map = getUserByNameAndPwd(username,password);
	    List shxx = phoneDao.getShxx(Validate.isNullToDefaultString(username, ""));
	    String num = shxx.size()+"";
	    	return "{\"success\":\"true\",\"msg\":\"登录成功！\", "
	    	  + "\"userId\":\""+Validate.isNullToDefaultString(ryxx_map.get("GUID"), "")+"\","
	          +"\"name\":\"" + Validate.isNullToDefaultString(ryxx_map.get("XM"), "") + "\","
	          +"\"department\":\""+Validate.isNullToDefaultString(ryxx_map.get("BMH"), "")+"*"+Validate.isNullToDefault(ryxx_map.get("MC"), "")+"\","
	          +"\"saas\":\""+Validate.isNullToDefaultString(ryxx_map.get("BMH"), "")+"\","
	          +"\"sex\":\""+Validate.isNullToDefault(ryxx_map.get("XBMC"), "")+"\","
	          +"\"tximg\":\""+Validate.isNullToDefault(ryxx_map.get("URL"), "")+"\","
	          + "\"items\":["
	        		    + "{\"splx\":\"1\",\"dspsl\":\""+num+"\"},"
	      	            + "{\"splx\":\"2\",\"dspsl\":\"0\"},"
	      	            + "{\"splx\":\"3\",\"dspsl\":\"0\"},"
	      	            + "{\"splx\":\"4\",\"dspsl\":\"0\"},"
	      	            + "{\"splx\":\"5\",\"dspsl\":\"0\"},"
	      	            + "{\"splx\":\"6\",\"dspsl\":\"0\"},"
	      	            + "{\"splx\":\"7\",\"dspsl\":\"0\"},"
	      	            + "{\"splx\":\"8\",\"dspsl\":\"0\"},"
	      	            + "{\"splx\":\"9\",\"dspsl\":\"0\"}"
	          + "]}";
	}
	/**
	* 加载首页
	* @return
	*/
	public Object login(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
	    String username = Validate.isNullToDefaultString(map.get("uname"),"");
	    String password = Validate.isNullToDefaultString(map.get("upwd"),"");
	    Map ryxx_map = getUserByNameAndPwd(username,password);
	    String rybh = Validate.isNullToDefaultString(ryxx_map.get("rybh")+"","");
	    String errCode = ryxx_map.get("errCode").toString();//错误代码
	    List shxx = phoneDao.getShxx(Validate.isNullToDefaultString(ryxx_map.get("GUID"), ""));
	    List spxs = deskDao.getShxx(Validate.isNullToDefaultString(ryxx_map.get("GUID"), ""));
	    String num = spxs.size()+"";
	    if("1".equals(errCode)){
	    	doAddOplogPhone(OplogFlag.LOGIN,"用户登录",rybh);
	    	String result = "{\"success\":\"true\",\"msg\":\"登录成功！\", "
	    			+ "\"rybh\":\""+Validate.isNullToDefaultString(rybh, "")+"\","
	    	  + "\"userId\":\""+Validate.isNullToDefaultString(ryxx_map.get("GUID"), "")+"\","
	          +"\"name\":\"" + Validate.isNullToDefaultString(ryxx_map.get("XM"), "") + "\","
	          +"\"department\":\""+Validate.isNullToDefaultString(ryxx_map.get("BMH"), "")+"*"+Validate.isNullToDefault(ryxx_map.get("MC"), "")+"\","
	          +"\"saas\":\""+Validate.isNullToDefaultString(ryxx_map.get("BMH"), "")+"\","
	          +"\"sex\":\""+Validate.isNullToDefault(ryxx_map.get("XBMC"), "")+"\","
	          +"\"tximg\":\""+Validate.isNullToDefault(ryxx_map.get("URL"), "")+"\","
	          + "\"items\":[";
	          int a3 = phoneDao.checkCzqx("110201",rybh);
	          int a4 = phoneDao.checkCzqx("110301",rybh);
	          int a5 = phoneDao.checkCzqx("111101",rybh);
	          int a6 = phoneDao.checkCzqx("130201",rybh);
	          int a7 = phoneDao.checkCzqx("130101",rybh);
	    	result =result + "{\"splx\":\"1\",\"dspsl\":\""+num+"\"},";
	    	result =result +  "{\"splx\":\"2\",\"dspsl\":\"0\"},";
	    	if(a3>0) {
	    		result =result +  "{\"splx\":\"3\",\"dspsl\":\"0\"},";
	    	}
	    	if(a4>0) {
	    		result =result +  "{\"splx\":\"4\",\"dspsl\":\"0\"},";
	    	}
	    	if(a5>0) {
	    		result =result +  "{\"splx\":\"5\",\"dspsl\":\"0\"},";
	    	}
	    	if(a6>0) {
	    		result =result +  "{\"splx\":\"6\",\"dspsl\":\"0\"},";
	    	}
	    	if(a7>0) {
	    		result =result + "{\"splx\":\"7\",\"dspsl\":\"0\"},";
	    	}
			result =result + "{\"splx\":\"8\",\"dspsl\":\"0\"},";
			result =result + "{\"splx\":\"9\",\"dspsl\":\"0\"}";
	      	result = result + "]}";
	    	return result;
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
	 * 首页的接口
	 * @param pd
	 * @return
	 */
	public Object work(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId=Validate.isNullToDefaultString(map.get("userId"), "");
		String time=Validate.isNullToDefaultString(map.get("time"), "");
		String sytime=time.substring(5, 7);
		int pdtime=Integer.parseInt(sytime);
		Map rybhmap=ywcdao.getgyryguid(userId);
		String rybh=Validate.isNullToDefaultString(rybhmap.get("RYBH"), "");
//		List shxx = phoneDao.getShxx(Validate.isNullToDefaultString(map.get("userId"), ""));
		List shxx = phoneDao.get3tzgglistPagelist();
		List xmxx = phoneDao.get3xmmclist(map.get("userId")+"");
//		List xmxx = phoneDao.getXmlist();
		Map shxxmap ;
		Map xmxxmap ;
		String who = "";
		String type = "";
		String result = "";
		int index=1;
		int pageSize=10;
		List<Map<String, Object>> shxxlist = ywcdao.getShxx(userId, "qb",index,pageSize);
			//0、全部；1、出差申请；2、日常报销；3、出差报销；4、公务接待；5、接待报销  
		String str = null;
		if(shxxlist.size() > 0 ) {
			str = shxxlist.get(0).get("state")+"";
			if("1".equals(str)) {
				type = "出差申请";
			}else if("2".equals(str)) {
				type = "日常报销";
			}else if("3".equals(str)) {
				type = "出差报销";
			}else if("4".equals(str)) {
				type = "公务接待";
			}else if("5".equals(str)) {
				type = "接待报销 ";
			}
			who = shxxlist.get(0).get("sqrxm")+"";
		} else {
			who = null;
		}
//		if(shxx.size()>0) {
			int count = 0;
			String notice = "";
			if(Validate.isNull(who)) {
				notice = "";
			}else {
				notice = "您有一条关于"+who+"的"+type+"报销申请需审核";
			}
			result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\",\"notice\":\""+notice+"\"," +
					"\"tzlist\":[";
			for(int i = 0;i<shxx.size();i++) {
				shxxmap = (Map)shxx.get(i);
				String url = phoneDao.getWorkUrl(shxxmap.get("GID")+"");
				result = result + "{\"title\":\""+shxxmap.get("TITLE")+"\",\"time\":\""+shxxmap.get("FBSJ")+"\",\"bh\":\""+shxxmap.get("GID")+"\",\"url\":\""+url+"\"},";
			}
			if(shxx.size()>0) {
				result = result.substring(0,result.length()-1);
			}
			result = result + "], \"xmlist\":[";
			for(int i = 0;i<xmxx.size();i++) {//拼数组xmlist
				xmxxmap = (Map)xmxx.get(i);
				result = result + "{\"title\":\""+xmxxmap.get("XMMC")+"\",\"ysje\":\""+xmxxmap.get("ysje")+"\",\"zcje\":\""+xmxxmap.get("zcje")+"\",\"syje\":\""+xmxxmap.get("syje")+"\",\"bh\":\""+xmxxmap.get("XMBH")+"\"},";
			}
			if(xmxx.size()>0) {
				result = result.substring(0,result.length()-1);
			}
			Map<String,Object> sfhjmap=ywcdao.getsfhjxx(rybh,time);
			String wdxz=Validate.isNullToDefaultString(sfhjmap.get("SFHJ"), "0");
			result = result + "],\"wdxz\":\""+wdxz+"\"";
			result = result + "}";
//		}else {
//			result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\",\"notice\":\"\",\"tzlist\":[],\"xmlist\":[]}";//打头
//		}
		return result;
		
	}
	
	/**
	 * 差旅费审批详情数据获取
	 * @param pd
	 * @return
	 */
	public Object clfbxspxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String bh = Validate.isNullToDefaultString(map.get("bh"),"");
	    String userId = Validate.isNullToDefaultString(map.get("userId"),"");
	    
	    Map shxx = phoneDao.getmxxxlistlist(bh);
	    String result = "";
	    result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\",\"djbh\":\""+Validate.isNullToDefaultString(shxx.get("djbh"),"")+"\"," +
	    		"\"cclx\":\""+Validate.isNullToDefaultString(shxx.get("cclx"),"")+"\",\"sqr\":\""+Validate.isNullToDefaultString(shxx.get("sqr"),"")+"\",\"ccrs\":\""+Validate.isNullToDefaultString(shxx.get("ccrs"),"")+"\","+
	    		"\"ccts\":\""+Validate.isNullToDefaultString(shxx.get("ccts"),"")+"\",\"fjzzs\":\""+Validate.isNullToDefaultString(shxx.get("fjzzs"),"")+"\",\"sfkylbx\":\""+Validate.isNullToDefaultString(shxx.get("sfkylbx"),"")+"\","+
	    		"\"bxzje\":\""+Validate.isNullToDefaultString(shxx.get("bxzje"),"")+"\",\"ccsy\":\""+Validate.isNullToDefaultString(shxx.get("ccsy"),"")+"\","+
				"\"mxxxlist\":[";
	    //mxxxlist--------------------------------------------------------------------------------------
	    Map shxxmap;
	    List list1 = phoneDao.getmxxxlist(bh);
	    for(int i = 0;i<list1.size();i++) {
			shxxmap = (Map)list1.get(i);
			result = result + "{\"kssj\":\""+Validate.isNullToDefaultString(shxxmap.get("kssj"),"")+"\",\"jssj\":\""+Validate.isNullToDefaultString(shxxmap.get("jssj"),"")+"\",\"cfdd\":\""+Validate.isNullToDefaultString(shxxmap.get("cfdd"),"")+"\","
					+ "\"mddd\":\""+Validate.isNullToDefaultString(shxxmap.get("mddd"),"")+"\",\"fjzs\":\""+Validate.isNullToDefaultString(shxxmap.get("ffjs"),"")+"\",\"fjp\":\""+Validate.isNullToDefaultString(shxxmap.get("fjje"),"")+"\",\"hcp\":\""+Validate.isNullToDefaultString(shxxmap.get("hcje"),"")+"\","
					+ "\"czcp\":\""+Validate.isNullToDefaultString(shxxmap.get("czcje"),"")+"\",\"qcp\":\""+Validate.isNullToDefaultString(shxxmap.get("qcje"),"")+"\",\"qtfy\":\""+Validate.isNullToDefaultString(shxxmap.get("qtfy"),"")+"\",\"hyfy\":\""+Validate.isNullToDefaultString(shxxmap.get("hyfy"),"")+"\",\"pxfy\":\""+Validate.isNullToDefaultString(shxxmap.get("pxfy"),"")+"\","
					+ "\"lsshbzts\":\""+Validate.isNullToDefaultString(shxxmap.get("lsshbzts"),"")+"\",\"lsshbzje\":\""+Validate.isNullToDefaultString(shxxmap.get("lsshbzje"),"")+"\",\"xsshbzts\":\""+Validate.isNullToDefaultString(shxxmap.get("xsshbzts"),"")+"\",\"xsshbzje\":\""+Validate.isNullToDefaultString(shxxmap.get("xsshbzje"),"")+"\",\"zsfje\":\""+Validate.isNullToDefaultString(shxxmap.get("zdfje"),"")+"\"},";
		}
		if(list1.size()>0) {
			result = result.substring(0,result.length()-1);
		}
		
		//xmjfzclist-----------------------------------------------------------------------------------
		List xmjfzclist = phoneDao.getxmjfzclist(bh);
		result = result + "],\"xmjfzclist\":[";
		for(int i = 0;i<xmjfzclist.size();i++) {
				shxxmap = (Map)xmjfzclist.get(i);
				result = result + "{\"xmmc\":\""+Validate.isNullToDefaultString(shxxmap.get("xmmc"),"")+"\",\"syje\":\""+Validate.isNullToDefaultString(shxxmap.get("ye"),"")+"\",\"bcbxje\":\""+Validate.isNullToDefaultString(shxxmap.get("bcbxje"),"")+"\"},";
		}
		if(xmjfzclist.size()>0) {
			result = result.substring(0,result.length()-1);
		}
		
		//ccrylist-----------------------------------------------------------------------------------------
		List ccrylist = phoneDao.getccrylist(bh);
		result = result + "],\"ccrylist\":[";
		for(int i = 0;i<ccrylist.size();i++) {
				shxxmap = (Map)ccrylist.get(i);
				result = result + "{\"xm\":\""+Validate.isNullToDefaultString(shxxmap.get("rybh"),"")+"\",\"bm\":\""+Validate.isNullToDefaultString(shxxmap.get("szdw"),"")+"\"},";
		}
		if(ccrylist.size()>0) {
			result = result.substring(0,result.length()-1);
		}
		
		//cxjklist---------------------------------------------------------------------------------------
		List cxjklist = phoneDao.getcxjklist(bh);
		result = result + "],\"cxjklist\":[";
		for(int i = 0;i<cxjklist.size();i++) {
				shxxmap = (Map)cxjklist.get(i);
				result = result + "{\"jkdh\":\""+Validate.isNullToDefaultString(shxxmap.get("jkid"),"")+"\",\"jkr\":\""+Validate.isNullToDefaultString(shxxmap.get("jkrxm"),"")+"\",\"szbm\":\""+Validate.isNullToDefaultString(shxxmap.get("szbm"),"")+"\",\"jkje\":\""+Validate.isNullToDefaultString(shxxmap.get("jkje"),"")+"\",\"cjkje\":\""+Validate.isNullToDefaultString(shxxmap.get("cjkje"),"")+"\"},";
		}
		if(cxjklist.size()>0) {
			result = result.substring(0,result.length()-1);
		}
		
		//dszflist-------------------------------------------------------------------------------------------
		List dszflist = phoneDao.getdszflist(bh);
		result = result + "],\"dszflist\":[";
		for(int i = 0;i<dszflist.size();i++) {
				shxxmap = (Map)dszflist.get(i);
				result = result + "{\"ryxz\":\""+Validate.isNullToDefaultString(shxxmap.get("ryxz"),"")+"\",\"ryxm\":\""+Validate.isNullToDefaultString(shxxmap.get("ryxm"),"")+"\",\"yhmc\":\""+Validate.isNullToDefaultString(shxxmap.get("klx"),"")+"\",\"kh\":\""+Validate.isNullToDefaultString(shxxmap.get("dfzh"),"")+"\",\"je\":\""+Validate.isNullToDefaultString(shxxmap.get("je"),"")+"\"},";
		}
		if(dszflist.size()>0) {
			result = result.substring(0,result.length()-1);
		}
		
		//dgzflist-------------------------------------------------------------------------------------------
		List dgzflist = phoneDao.getdgzflist(bh);
		result = result + "],\"dgzflist\":[";
		for(int i = 0;i<dgzflist.size();i++) {
				shxxmap = (Map)dgzflist.get(i);
				result = result + "{\"dfdw\":\""+Validate.isNullToDefaultString(shxxmap.get("dwmc"),"")+"\",\"dfdq\":\""+Validate.isNullToDefaultString(shxxmap.get("dfdq"),"")+"\",\"dfyh\":\""+Validate.isNullToDefaultString(shxxmap.get("dfyh"),"")+"\",\"dfzh\":\""+Validate.isNullToDefaultString(shxxmap.get("dfzh"),"")+"\",\"je\":\""+Validate.isNullToDefaultString(shxxmap.get("je"),"")+"\"},";
		}
		if(dgzflist.size()>0) {
			result = result.substring(0,result.length()-1);
		}
		
		//gwklist-----------------------------------------------------------------------------------------------------------
		List gwklist = phoneDao.getgwklist(bh);
		result = result + "],\"gwklist\":[";
		for(int i = 0;i<gwklist.size();i++) {
				shxxmap = (Map)gwklist.get(i);
				String url = phoneDao.getWorkUrl(shxxmap.get("GID")+"");
				result = result + "{\"ryxm\":\""+Validate.isNullToDefaultString(shxxmap.get("ryxm"),"")+"\",\"dfdq\":\""+Validate.isNullToDefaultString(shxxmap.get("skrq"),"")+"\",\"kh\":\""+Validate.isNullToDefaultString(shxxmap.get("kh"),"")+"\",\"je\":\""+Validate.isNullToDefaultString(shxxmap.get("skje"),"")+"\"},";
		}
		if(gwklist.size()>0) {
			result = result.substring(0,result.length()-1);
		}
		
		//根据主表ID 查询流程ID
		Map lcmap=ywcdao.getlcid(bh,"CW_CLFBXZB");
		String lcid=Validate.isNullToDefaultString(lcmap.get("PROCINSTID"),"");
		//流程list lclist
		result = result + "],\"lclist\":[";
		List<Map<String,Object>> lclist=ywcdao.getlclist(lcid);
		for(int j=0;j<lclist.size();j++) {
			if(j==0) {
				String time=lclist.get(j).get("STARTTIME")+"";
				String shzt=Validate.isNullToDefaultString(lclist.get(j).get("SHZT"),"");
				String shyj=Validate.isNullToDefaultString(lclist.get(j).get("YJ"),"");
				result = result + "{\"time\":\""+lclist.get(j).get("STARTTIME")+""+"\",\"name\":\""+Validate.isNullToDefaultString(lclist.get(j).get("NAME"), "")+"\",\"yj\":\""+"发起申请"+"\"},";
			}else {
				String time=lclist.get(j).get("STARTTIME")+"";
				String shzt=Validate.isNullToDefaultString(lclist.get(j).get("SHZT"),"");
				String shyj=Validate.isNullToDefaultString(lclist.get(j).get("YJ"),"");
				result = result + ",{\"time\":\""+lclist.get(j).get("STARTTIME")+""+"\",\"name\":\""+Validate.isNullToDefaultString(lclist.get(j).get("NAME"), "")+"\",\"yj\":\""+"("+(shzt)+") "+shyj+"\"},";
			}
			if(lclist.size()>0) {
				result = result.substring(0,result.length()-1);
			}
		}
		
		result = result + "],\"imglist\":[";
		
		//imglist  图片详情路径------------------------------------------------------------------------------------------------
		List<Map<String,Object>> imglist=ywcdao.gettpsclj(bh);
		List imglist1=new ArrayList<>();
		if(imglist.size()>0) {
			result = result + "{";
		}
		for(int t=0;t<imglist.size();t++) {
			Map map2=new HashMap<>();
			String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
			String imgurl="'"+xnlu_path+"/"+imglist.get(t).get("PATH")+"'";
//			map2.put("imgurl", xnlu_path+"/"+imglist.get(t).get("PATH"));
			result = result + "\"imgurl\":\""+imgurl+"\"},{";
			//\"dfdq\":\""+111+"\",\"kh\":\""+111+"\",\"je\":\""+111+"\"},"
		}
		
		if(imglist.size()>0) {
			result = result.substring(0,result.length()-2);
		}
//		if(imglist.size()>0) {
//			result = result + "}";
//		}
		result = result + "]}";
		
	    return result;
		    
	}
	/**
	* 更多模块
	* @return
	*/
	public Object more_yy(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
	    return "{\"success\":\"true\",\"msg\":\"查询信息成功！\"," +
	        "\"items\":["
	        + "{\"splx\":\"16\",\"dspsl\":\"0\",\"title\":\"闲置资产领用\"}," //闲置资产领用
	        + "{\"splx\":\"15\",\"dspsl\":\"0\",\"title\":\"常见问题\"}" //常见问题
	        //+ ",{\"splx\":\"4\",\"dspsl\":\"0\",\"title\":\"资产清查\"}" //资产清查（暂时不要）
	        + "]}";
	}

	/**
	* 待办事项列表展示
	* @return
	*/
	public Object ywsplist(PageData pd){
	    Map map = gson.fromJson(pd.getString("key"), Map.class);
	    int index = (int)Float.parseFloat(map.get("index").toString());
	
	    PageList pagelist = new PageList();
	    pagelist.setSqlText("gid,dbh,tjr,tjrxm,djdw,djdwmc,tjsj,djlx,ywlx,sqmkbh,sqmkmc,mkbh,mkmc");
	    String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
	    if(Validate.noNull(keyword)){
	    	keyword = keyword.replace("'", "");
	    	pagelist.setStrWhere(" and (dbh like '%" + keyword + "%' or djlx like '%" + keyword + "%' or tjrxm like '%" + keyword + "%') ");
	    }
	    StringBuffer sql = new StringBuffer();
	    sql.append("(select gid,dbh,tjr,tjrxm,djdw,djdwmc,to_char(tjsj,'yyyy-mm-dd') tjsj,");
	    sql.append("(case when sqmkbh in ('" + MenuFlag.ZCJZ_LYR + "','" + MenuFlag.ZCJZ_GLY + "') then '资产验收单' ");
	    sql.append("when sqmkbh in ('" + MenuFlag.ZCBD_DWNDB + "','" + MenuFlag.ZCBD_DWJDB + "','" + MenuFlag.ZCBD_SYRBD + "','" + MenuFlag.ZCBD_CFDDBD + "','" + MenuFlag.ZCBD_BFDB + "') then '资产调拨单' ");
	    sql.append("when sqmkbh in ('" + MenuFlag.ZCBD_XMBD + "','" + MenuFlag.ZCBD_DJBD + "','" + MenuFlag.ZCBD_FJZJ + "') then '资产变动报告单' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCBD_BFBF + "' then '部分报废报告单' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCBD_FJCZ + "' then '附件处置报告单' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCCZ_CZSQ + "' then '资产处置申请单' ");
	    sql.append("when sqmkbh in ('" + MenuFlag.ZCCZ_GLYHZ + "','" + MenuFlag.ZCCZ_ZCCZ + "') then '资产处置报告单' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCWX_WXSQ + "' then '资产维修申请单' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCWX_WXBG + "' then '资产维修报告单' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCWX_JFZJ + "' then '维修经费追加申请单' ");
	    sql.append("end) djlx,");
	    sql.append("(case when sqmkbh in ('" + MenuFlag.ZCJZ_LYR + "','" + MenuFlag.ZCJZ_GLY + "') then '2' ");
	    sql.append("when sqmkbh in ('" + MenuFlag.ZCBD_DWNDB + "','" + MenuFlag.ZCBD_DWJDB + "','" + MenuFlag.ZCBD_SYRBD + "','" + MenuFlag.ZCBD_CFDDBD + "','" + MenuFlag.ZCBD_BFDB + "') then '1' ");
	    sql.append("when sqmkbh in ('" + MenuFlag.ZCBD_XMBD + "','" + MenuFlag.ZCBD_DJBD + "','" + MenuFlag.ZCBD_FJZJ + "') then '1' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCBD_BFBF + "' then '3' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCBD_FJCZ + "' then '3' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCCZ_CZSQ + "' then '3' ");
	    sql.append("when sqmkbh in ('" + MenuFlag.ZCCZ_GLYHZ + "','" + MenuFlag.ZCCZ_ZCCZ + "') then '3' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCWX_WXSQ + "' then '4' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCWX_WXBG + "' then '4' ");
	    sql.append("when sqmkbh = '" + MenuFlag.ZCWX_JFZJ + "' then '4' ");
	    sql.append("end) ywlx,");
	    sql.append("sqmkbh,sqmkmc,mkbh,mkmc from zc_sys_shcshb h where sqmkbh in (" + phoneDao.getSqmkbh() + ") and mkbh <> '" + MenuFlag.ZCBD_ZCDB_SYRQR + "' and rybh = '" + map.get("userId").toString() + "' and sfdqjd = '1') h");
	    pagelist.setTableName(sql.toString());
	    
	    pagelist.setOrderBy("order by tjsj desc");
	    
	    List<Map> list = getPageList(pagelist,map,page_length);
	    
	    if(list == null || list.size() == 0){
	    	if(index == 1){
	    		return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的待办事项！\"}";
	    	}
	    	else{
	    		return "{\"success\":\"false\",\"msg\":\"暂无更多待办事项！\"}";
	    	}
	    }
	    JsonArray arr = new JsonArray();
	    Map array;
	    for(Map<String, Object> shmap : list){
    		array = new HashMap();
    		array.put("bh", Validate.isNullToDefaultString(shmap.get("DBH"), ""));
    		array.put("sqsj",Validate.isNullToDefault(shmap.get("TJSJ"), ""));
    		array.put("sqbm",Validate.isNullToDefault(WindowUtil.getEndText(shmap.get("DJDWMC")+""), ""));
    		array.put("zt",Validate.isNullToDefault(shmap.get("MKMC"), ""));
    		array.put("ywsplx",Validate.isNullToDefault(shmap.get("MKBH"),""));
    		array.put("sqr",Validate.isNullToDefault(WindowUtil.getEndText(shmap.get("TJRXM")+""),""));
    		array.put("title",Validate.isNullToDefault(shmap.get("SQMKMC"),""));
	        array.put("djlx", Validate.isNullToDefault(shmap.get("DJLX"),"") + "-" + Validate.isNullToDefaultString(shmap.get("DBH"), ""));
	        array.put("ywlx", Validate.isNullToDefault(shmap.get("YWLX"),""));
	        arr.add(array);
	    }
	    JsonObject json = new JsonObject(
	        new KeysValues("success",true),
	        new KeysValues("msg","数据加载成功！"),
	        new KeysValues("items",arr)
	      );
	    return json.toString();
	}
	/**
	 * 待办事项详情加载
	 * @return
	 */
	public Object ywspxq(PageData pd){
	    Map map = gson.fromJson(pd.getString("key"), Map.class);
	    String rybh = map.get("userId").toString();
	    String djbh = map.get("dbh").toString();//单编号
	    String ywlx = map.get("ywlx").toString();//1:变动 2:验收单 3:处置
	    String ywsplx = map.get("ywsplx").toString();//审核的模块编号
	    
	    Map datamap;
	    JsonArray sharr = new JsonArray();
	    JsonArray array = new JsonArray();
	    JsonArray mxarr = new JsonArray();
	    String shyj = StateManager.SHYJ;
	    if("2".equals(ywlx)){//验收单
	    	datamap = phoneDao.getYsdByYsdh(djbh);//资产建账
	      
	    	if(MenuFlag.ZCJZ_CWSH.equals(ywsplx)){
	    		sharr.add(new JsonObject(
	    				new KeysValues("issubmit",true),
	    				new KeysValues("id","0000001"),
	    				new KeysValues("formtype","1"),
	    				new KeysValues("lable","凭  证  号"),
	    				new KeysValues("name","pzh"),
	    				new KeysValues("btbz","1"),
	    				new KeysValues("source",Validate.isNullToDefault(datamap.get("PZH"), ""))
    				)
		        );
	    		sharr.add(new JsonObject(
		        		new KeysValues("issubmit",true),
		        		new KeysValues("id","0000002"),
		        		new KeysValues("formtype","1"),
		        		new KeysValues("lable","入账日期"),
		        		new KeysValues("name","jzrq"),
		        		new KeysValues("btbz","1"),
		        		new KeysValues("source",Validate.isNullToDefault(datamap.get("JZRQ"), DateUtil.getDay()))
		           )
		        );
	    		sharr.add(new JsonObject(
		        		new KeysValues("issubmit",true),
		        		new KeysValues("id","0000003"),
		        		new KeysValues("formtype","1"),
		        		new KeysValues("lable","预算项目编号"),
		        		new KeysValues("name","xmh"),
		        		new KeysValues("source",Validate.isNullToDefault(datamap.get("XMH"), ""))
		           )
		        );
	    	}
	    	sharr.add(new JsonObject(
	    			new KeysValues("issubmit",true),
	    			new KeysValues("id","0000004"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","审核意见"),
		            new KeysValues("name","shyj"),
		            new KeysValues("btbz","0"),
		            new KeysValues("source",Validate.isNullToDefault(datamap.get("SHYJ"),shyj))
	             )
			);
	      
//	    	array.add(new JsonObject(
//	    			new KeysValues("issubmit",false),
//	    			new KeysValues("id","0000005"),
//	    			new KeysValues("formtype","1"),
//	    			new KeysValues("lable","验收单号"),
//	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("YSDH"), ""))
//		        )
//			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000006"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","资产名称"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("YQMC"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000007"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","分  类  号"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("FLH"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000008"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","分类名称"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("YQBH"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000009"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","数        量"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("SHL"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000010"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","单        价"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("DJ"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000011"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","总        价"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("ZZJ"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000012"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","经  手  人"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("JSR"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000013"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","申购单位"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("SHGDW"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000014"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","型        号"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("XH"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000015"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","规        格"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("GG"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000016"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","购置日期"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("DZRRQ"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000017"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","采  购  人"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("CGR"), ""))
		         )
			);
	    	array.add(new JsonObject(
	    			new KeysValues("issubmit",false),
	    			new KeysValues("id","0000018"),
	    			new KeysValues("formtype","1"),
	    			new KeysValues("lable","验收日期"),
	    			new KeysValues("source",Validate.isNullToDefault(datamap.get("YSHRQ"), ""))
		         )
			);
	    }
	    else if("1".equals(ywlx)){
	    	if(MenuFlag.ZCBD_ZCDB_GLYSH.equals(ywsplx)
	    	 ||MenuFlag.ZCBD_ZCDB_DCDWLD.equals(ywsplx)
	    	 ||MenuFlag.ZCBD_ZCDB_DRDWLD.equals(ywsplx)
	    	 ||MenuFlag.ZCBD_ZCDB_GKSH.equals(ywsplx)){//资产调拨
	    		datamap = phoneDao.getBddByBdbgbh(djbh);
	    		if(MenuFlag.ZCBD_ZCDB_GLYSH.equals(ywsplx)){
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000001"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("GLYSHYJ"),shyj))
		    	           )
					);
	    		}
	    		else if(MenuFlag.ZCBD_ZCDB_DCDWLD.equals(ywsplx)){
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000001"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("DCDWSHYJ"),shyj))
		    	           )
					);
	    		}
	    		else if(MenuFlag.ZCBD_ZCDB_DRDWLD.equals(ywsplx)){
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000001"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("DRDWSHYJ"),shyj))
		    	           )
					);
	    		}
	    		else if(MenuFlag.ZCBD_ZCDB_GKSH.equals(ywsplx)){
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000001"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("GKYJ"),shyj))
		    	           )
					);
	    		}
//	    		array.add(new JsonObject(
//			            new KeysValues("issubmit",false),
//			            new KeysValues("id","0000002"),
//			            new KeysValues("formtype","1"),
//			            new KeysValues("lable","调拨编号"),
//			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDBGBH"), ""))
//			          )
//				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000003"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","调  拨  人"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRXM"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000004"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","编报单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("DWMC"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000005"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","调拨日期"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRQ"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000006"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","调拨原因"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDYY"), ""))
			          )
				);
	    		List<Map> list = (List<Map>)datamap.get("mxlist");
	    		JsonArray listarr;
	    		int xh = 0;
	    		for(Map mxmap : list){
	    			listarr = new JsonArray();
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","资产名称"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("FJBH"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","分  类  号"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLH"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","分类名称"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLMC"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","调拨项目"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("BDXM"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "变动前内容"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BDQNR"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "变动后内容"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BDHNR"), ""))
				          )
	    			);
	    			mxarr.add(listarr);
	    		}
	      }
	      else if(MenuFlag.ZCBD_BFDB_GKSH.equals(ywsplx)){
	    	  datamap = phoneDao.getDbdByBdbgbh(djbh);//部分调拨
	    	  
	    	  sharr.add(new JsonObject(
    	            new KeysValues("issubmit",true),
    	            new KeysValues("id","0000001"),
    	            new KeysValues("formtype","1"),
    	            new KeysValues("lable","审核意见"),
		            new KeysValues("name","shyj"),
    	            new KeysValues("btbz","0"),
    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("GKYJ"),shyj))
    	          )
			  );
//	    	  array.add(new JsonObject(
//		            new KeysValues("issubmit",false),
//		            new KeysValues("id","0000002"),
//		            new KeysValues("formtype","1"),
//		            new KeysValues("lable","调拨编号"),
//		            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDBGBH"), ""))
//		          )
//			  );
	    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000003"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","调  拨  人"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("JDRMC"), ""))
			          )
				  );
	    	  array.add(new JsonObject(
		            new KeysValues("issubmit",false),
		            new KeysValues("id","0000004"),
		            new KeysValues("formtype","1"),
		            new KeysValues("lable","编报单位"),
		            new KeysValues("source",Validate.isNullToDefault(datamap.get("DWMC"), ""))
		          )
			  );
	    	  array.add(new JsonObject(
		            new KeysValues("issubmit",false),
		            new KeysValues("id","0000005"),
		            new KeysValues("formtype","1"),
		            new KeysValues("lable","拆分数量"),
		            new KeysValues("source",Validate.isNullToDefault(datamap.get("CFSHL"), ""))
		          )
			  );
	    	  array.add(new JsonObject(
		            new KeysValues("issubmit",false),
		            new KeysValues("id","0000006"),
		            new KeysValues("formtype","1"),
		            new KeysValues("lable","拆分总金额"),
		            new KeysValues("source",Validate.isNullToDefault(datamap.get("CFJE"), ""))
		          )
			  );
	    	  array.add(new JsonObject(
		            new KeysValues("issubmit",false),
		            new KeysValues("id","0000007"),
		            new KeysValues("formtype","1"),
		            new KeysValues("lable","调拨日期"),
		            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRQ"), ""))
		          )
			  );
	    	  array.add(new JsonObject(
		            new KeysValues("issubmit",false),
		            new KeysValues("id","0000008"),
		            new KeysValues("formtype","1"),
		            new KeysValues("lable","调拨原因"),
		            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDYY"), ""))
		          )
			  );

	    	  List<Map> list = (List<Map>)datamap.get("mxlist");
	    	  JsonArray listarr;
	    	  int xh = 0;
	    	  for(Map mxmap : list){
	    			listarr = new JsonArray();
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","资产名称"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("NEWYQBH"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","数        量"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("SL"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","金        额"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("JE"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","使  用  人"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("SYRMC"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "使用单位"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SYDWMC"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "存放地点"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("CFDDMC"), ""))
				          )
	    			);
	    			mxarr.add(listarr);
	    	  }
	      }
	      else if(MenuFlag.ZCBD_XMBD_GKSH.equals(ywsplx)
	             ||MenuFlag.ZCBD_JZBD_GKSH.equals(ywsplx)
	             ||MenuFlag.ZCBD_JZBD_CWSH.equals(ywsplx)){
	    	  datamap = phoneDao.getBddByBdbgbh(djbh);//项目变动、价值变动、附件增加
	    	  String djbz = Validate.isNullToDefaultString(datamap.get("DJBZ"), "");
	    	  List<Map> list = (List<Map>)datamap.get("mxlist");
	    	  
	    	  if(MenuFlag.ZCBD_JZBD_CWSH.equals(ywsplx)){
	    		  sharr.add(new JsonObject(
			            new KeysValues("issubmit",true),
			            new KeysValues("id","0000001"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","凭  证  号"),
			            new KeysValues("name","pzh"),
			            new KeysValues("btbz","1"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("PZH"), ""))
			           )
			        );
	    		  sharr.add(new JsonObject(
			            new KeysValues("issubmit",true),
			            new KeysValues("id","0000002"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","入账日期"),
			            new KeysValues("name","jzrq"),
			            new KeysValues("btbz","1"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("JZRQ"), ""))
			           )
			        );
		    	    sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("SHYJ"),shyj))
		    	           )
					);
	    	  }
	    	  else{
	    		  sharr.add(new JsonObject(
	    	            new KeysValues("issubmit",true),
	    	            new KeysValues("id","0000003"),
	    	            new KeysValues("formtype","1"),
	    	            new KeysValues("lable","审核意见"),
			            new KeysValues("name","shyj"),
	    	            new KeysValues("btbz","0"),
	    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("GKYJ"),shyj))
	    	           )
				  );
	    	  }
//	    	  array.add(new JsonObject(
//			            new KeysValues("issubmit",false),
//			            new KeysValues("id","0000004"),
//			            new KeysValues("formtype","1"),
//			            new KeysValues("lable","变动编号"),
//			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDBGBH"), ""))
//			          )
//			  );
	    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000005"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","编报单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("DWMC"), ""))
			          )
			  );
	    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000006"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","变  动  人"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRXM"), ""))
			          )
			  );
	    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000007"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","变动日期"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRQ"), ""))
			          )
			  );
	    	  if(StateManager.BDXM_FJZJ.equals(djbz)){
		    		array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000008"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","增加附件数"),
				            new KeysValues("source",list.size())
				          )
					);
		    		array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000009"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","变动后资产总价"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("FJZJS"), ""))
				          )
					);
	    	  }
	    	  else{
	    		  if(StateManager.BDXM_DJBD.equals(djbz)){
	    			  array.add(new JsonObject(
					            new KeysValues("issubmit",false),
					            new KeysValues("id","0000009"),
					            new KeysValues("formtype","1"),
					            new KeysValues("lable","变动金额"),
					            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDDJS"), ""))
					          )
						);
		    	  }
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000010"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","变动原因"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDYY"), ""))
				          )
				  );
	    	  }
	    		
	    	  JsonArray listarr;
	    	  int xh = 0;
	    	  for(Map mxmap : list){
	    			listarr = new JsonArray();
	    			if(StateManager.BDXM_FJZJ.equals(djbz)){
	    				listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","附件名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("FJBH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","分  类  号"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","分类名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLMC"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","单        价"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FJDJ"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "规        格"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("FJGG"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "型        号"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("FJXH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "销  售  商"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("XSS"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "生厂厂家"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SCCJ"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "描        述"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SFJRZJ"), ""))
					          )
		    			);
	    			}
	    			else{
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","资产名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("FJBH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","分  类  号"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","分类名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLMC"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","变动项目"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("BDXM"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "变动前内容"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BDQNR"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "变动后内容"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BDHNR"), ""))
					          )
		    			);
	    			}
	    			mxarr.add(listarr);
	    		}
	      	}
	    }
	    else if("3".equals(ywlx)){
	    	if(MenuFlag.ZCBD_BFBF_GKSH.equals(ywsplx)
	    	 ||MenuFlag.ZCBD_BFBF_CWSH.equals(ywsplx)){
	    		datamap = phoneDao.getBddByBdbgbh(djbh);//部分报废
	    		List<Map> list = (List<Map>)datamap.get("mxlist");
	    		
	    		if(MenuFlag.ZCBD_BFBF_CWSH.equals(ywsplx)){
	    			sharr.add(new JsonObject(
			            new KeysValues("issubmit",true),
			            new KeysValues("id","0000001"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","凭  证  号"),
			            new KeysValues("name","pzh"),
			            new KeysValues("btbz","1"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("PZH"), ""))
			           )
			        );
	    			sharr.add(new JsonObject(
			            new KeysValues("issubmit",true),
			            new KeysValues("id","0000002"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","入账日期"),
			            new KeysValues("name","jzrq"),
			            new KeysValues("btbz","1"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("JZRQ"), ""))
			           )
			        );
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("SHYJ"),shyj))
		    	           )
					);
		    	}
	    		else{
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("GKYJ"),shyj))
		    	           )
					);
	    		}
	    		
//	    		array.add(new JsonObject(
//			            new KeysValues("issubmit",false),
//			            new KeysValues("id",4),
//			            new KeysValues("formtype","1"),
//			            new KeysValues("lable","处置编号"),
//			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDBGBH"), ""))
//			          )
//				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id",5),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","编报单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("DWMC"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id",6),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处  置  人"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRXM"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id",7),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置日期"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRQ"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id",8),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置数量"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("SH"), ""))
			          )
				);
    			array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id",9),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置金额"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDDJS"), ""))
			          )
				);
    			array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id",10),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置方式"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("HXZMC"), ""))
			          )
				);
    			array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id",11),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置收益"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("CZSYS"), ""))
			          )
				);
    			array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id",12),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置原因"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDYY"), ""))
			          )
				);
    			
    			int xh = 13;
	    		for(Map mxmap : list){
	    			if(xh == 13){
	    				array.add(new JsonObject(
					            new KeysValues("issubmit",false),
					            new KeysValues("id",xh++),
					            new KeysValues("lable","资产编号"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FJBH"), ""))
					          )
		    			);
	    				array.add(new JsonObject(
					            new KeysValues("issubmit",false),
					            new KeysValues("id",xh++),
					            new KeysValues("lable","资产名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), ""))
					          )
		    			);
	    				array.add(new JsonObject(
					            new KeysValues("issubmit",false),
					            new KeysValues("id",xh++),
					            new KeysValues("lable","分  类  号"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLH"), ""))
					          )
		    			);
	    				array.add(new JsonObject(
					            new KeysValues("issubmit",false),
					            new KeysValues("id",xh++),
					            new KeysValues("lable","分类名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLMC"), ""))
					          )
		    			);
	    			}
	    			String bdxm = Validate.isNullToDefaultString(mxmap.get("BDXM"), "");
	    			array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id",xh++),
				            new KeysValues("lable", "报废前" + bdxm),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BDQNR"), ""))
				          )
	    			);
	    			array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id",xh++),
				            new KeysValues("lable", "报废后" + bdxm),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BDHNR"), ""))
				          )
	    			);
	    		}
	    	}
	    	else if(MenuFlag.ZCBD_FJCZ_GKSH.equals(ywsplx)
	    	 ||MenuFlag.ZCBD_FJCZ_CWSH.equals(ywsplx)){
	    		datamap = phoneDao.getBddByBdbgbh(djbh);//附件处置
	    		List<Map> list = (List<Map>)datamap.get("mxlist");
	    		
	    		if(MenuFlag.ZCBD_FJCZ_CWSH.equals(ywsplx)){
	    			sharr.add(new JsonObject(
			            new KeysValues("issubmit",true),
			            new KeysValues("id","0000001"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","凭  证  号"),
			            new KeysValues("name","pzh"),
			            new KeysValues("btbz","1"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("PZH"), ""))
			           )
			        );
	    			sharr.add(new JsonObject(
			            new KeysValues("issubmit",true),
			            new KeysValues("id","0000002"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","入账日期"),
			            new KeysValues("name","jzrq"),
			            new KeysValues("btbz","1"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("JZRQ"), ""))
			           )
			        );
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("SHYJ"),shyj))
		    	           )
					);
		    	}
	    		else{
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("GKYJ"),shyj))
		    	           )
					);
	    		}
	    		
//	    		array.add(new JsonObject(
//			            new KeysValues("issubmit",false),
//			            new KeysValues("id","0000004"),
//			            new KeysValues("formtype","1"),
//			            new KeysValues("lable","处置编号"),
//			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDBGBH"), ""))
//			          )
//				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000005"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","编报单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("DWMC"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000006"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处  置  人"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRXM"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000007"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置日期"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("BDRQ"), ""))
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000008"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置附件数"),
			            new KeysValues("source",list.size())
			          )
				);
	    		array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000009"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置后资产总价"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("FJCZS"), ""))
			          )
				);
	    		
	    		JsonArray listarr;
	    		int xh = 0;
	    		for(Map mxmap : list){
	    			listarr = new JsonArray();
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","附件名称"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("FJBH"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","分  类  号"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLH"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","分类名称"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLMC"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","单        价"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FJDJ"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "规        格"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("FJGG"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "型        号"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("FJXH"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "销  售  商"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("XSS"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "生厂厂家"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SCCJ"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "描        述"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SFJRZJ"), ""))
				          )
	    			);
	    			mxarr.add(listarr);
	    		}
	    	}
	    	else if(MenuFlag.ZCCZ_GLYSH.equals(ywsplx)){
	    		datamap = phoneDao.getCzdBySqbh(djbh);//处置申请
		    	List<Map> list = (List<Map>)datamap.get("mxlist");
		    	  
		    	sharr.add(new JsonObject(
	    	            new KeysValues("issubmit",true),
	    	            new KeysValues("id","0000001"),
	    	            new KeysValues("formtype","1"),
	    	            new KeysValues("lable","审核意见"),
			            new KeysValues("name","shyj"),
	    	            new KeysValues("btbz","0"),
	    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("SHYJ"),shyj))
	    	          )
				  );
//		    	  array.add(new JsonObject(
//			            new KeysValues("issubmit",false),
//			            new KeysValues("id","0000002"),
//			            new KeysValues("formtype","1"),
//			            new KeysValues("lable","申请编号"),
//			            new KeysValues("source",Validate.isNullToDefault(datamap.get("SQBH"), ""))
//			          )
//				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000003"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","申  请  人"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("SQR"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000004"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","申请单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("SQDW"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000005"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","申请日期"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("SQRQ"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000006"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","处置金额"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("ZJE"), ""))
				          )
					  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000007"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置资产数"),
			            new KeysValues("source",list.size())
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000008"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置原因"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("CZYY"), ""))
			          )
				  );

		    	  JsonArray listarr;
		    	  int xh = 0;
		    	  for(Map mxmap : list){
		    			listarr = new JsonArray();
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","资产名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("YQBH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","分  类  号"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","分类名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLMC"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","总        价"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("ZZJ"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","使  用  人"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("SYRMC"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "使用单位"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SYDWMC"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "存放地点"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BZXXMC"), ""))
					          )
		    			);
		    			mxarr.add(listarr);
		    	  }
	    	}
	    	else if(MenuFlag.ZCCZ_GKSH.equals(ywsplx)
	    		   ||MenuFlag.ZCCZ_CWSH.equals(ywsplx)){
	    		datamap = phoneDao.getCzdByCzbgbh(djbh);//处置报告
		    	List<Map> list = (List<Map>)datamap.get("mxlist");
		    	
		    	if(MenuFlag.ZCCZ_CWSH.equals(ywsplx)){
		    		sharr.add(new JsonObject(
    						new KeysValues("issubmit",true),
    						new KeysValues("id","0000001"),
    						new KeysValues("formtype","1"),
    						new KeysValues("lable","凭  证  号"),
    						new KeysValues("name","pzh"),
    						new KeysValues("btbz","1"),
    						new KeysValues("source",Validate.isNullToDefault(datamap.get("PZH"), ""))
			           )
			        );
		    		sharr.add(new JsonObject(
			        		new KeysValues("issubmit",true),
			        		new KeysValues("id","0000002"),
			        		new KeysValues("formtype","1"),
			        		new KeysValues("lable","入账日期"),
			        		new KeysValues("name","jzrq"),
			        		new KeysValues("btbz","1"),
			        		new KeysValues("source",Validate.isNullToDefault(datamap.get("JZRQ"), ""))
			           )
			        );
			    	sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("SHYJ"),shyj))
	    	           )
					);
		    	}
		    	else{
			    	sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("GKYJ"),shyj))
	    	           )
					);
		    	}
//	    	  	array.add(new JsonObject(
//				            new KeysValues("issubmit",false),
//				            new KeysValues("id","0000004"),
//				            new KeysValues("formtype","1"),
//				            new KeysValues("lable","处置编号"),
//				            new KeysValues("source",Validate.isNullToDefault(datamap.get("CZBGBH"), ""))
//			          )
//	  			);
	    	  	array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000005"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处  置  人"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("CZRXM"), ""))
    	  			)
	  			);
	    	  	array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000006"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("DWMC"), ""))
    	  			)
	  			);
	    	  	array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000007"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","账面原值"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("ZMYZ"), ""))
    	  			)
	  			);
	    	  	array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000008"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","评估价值"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("PGJZ"), ""))
    	  			)
	  			);
	    	  	array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000009"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置价值"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("CZJZ"), ""))
    	  			)
	  			);
	    	  	array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000010"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置方式"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("HXZMC"), ""))
    	  			)
	  			);
	    	  	array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000011"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","处置原因"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("CZYY"), ""))
    	  			)
	  			);
	    	  	array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000012"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","技术鉴定意见"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("JDYJ"), ""))
    	  			)
	  			);
	    	  	JsonArray listarr;
	    	  	int xh = 0;
	    	  	for(Map mxmap : list){
	    	  		listarr = new JsonArray();
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","资产名称"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("YQBH"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","分  类  号"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLH"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","分类名称"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLMC"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","总        价"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("ZZJ"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable","使  用  人"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("SYRMC"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "使用单位"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SYDWMC"), ""))
				          )
	    			);
	    			listarr.add(new JsonObject(
    			            new KeysValues("issubmit",false),
    			            new KeysValues("id",xh++),
				            new KeysValues("lable", "存放地点"),
				            new KeysValues("formtype","1"),
				            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BZXXMC"), ""))
				          )
	    			);
	    			mxarr.add(listarr);
	    	  	}
	    	}
	    }
	    else if("4".equals(ywlx)){
	    	if(MenuFlag.ZCWX_WXSQSH.equals(ywsplx)){
	    		datamap = phoneDao.getWxdBySqbh(djbh);//设备维修申请
		    	List<Map> list = (List<Map>)datamap.get("mxlist");

//		    	sharr.add(new JsonObject(
//	    	            new KeysValues("issubmit",true),
//	    	            new KeysValues("id","0000001"),
//	    	            new KeysValues("formtype","1"),
//	    	            new KeysValues("lable","审核日期"),
//			            new KeysValues("name","jzrq"),
//	    	            new KeysValues("btbz","1"),
//	    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("CHECKTIME"),DateUtil.getDay()))
//	    	          )
//				);
		    	sharr.add(new JsonObject(
	    	            new KeysValues("issubmit",true),
	    	            new KeysValues("id","0000002"),
	    	            new KeysValues("formtype","1"),
	    	            new KeysValues("lable","审核意见"),
			            new KeysValues("name","shyj"),
	    	            new KeysValues("btbz","0"),
	    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("CHECKADVICE"),shyj))
	    	          )
				  );
//		    	  array.add(new JsonObject(
//			            new KeysValues("issubmit",false),
//			            new KeysValues("id","0000003"),
//			            new KeysValues("formtype","1"),
//			            new KeysValues("lable","申请编号"),
//			            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPORTID"), ""))
//			          )
//				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000004"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","申  请  人"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPLYPERSON"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000005"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","申请单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPLYCOMPANY"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000006"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","申请日期"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPLYTIME"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000007"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","维  修  商"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("WXSMC"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000008"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","预估维修费用"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("ABOUTMONEY"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000009"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","故障描述"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("QUESTIONREMARK"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000010"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","维修内容"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPLYCONTENT"), ""))
				          )
				  );

		    	  JsonArray listarr;
		    	  int xh = 0;
		    	  for(Map mxmap : list){
		    			listarr = new JsonArray();
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","资产名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("YQBH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","分  类  号"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","分类名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FLMC"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","现        状"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("XZ"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","使  用  人"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FZR"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "使用单位"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SYDW"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "存放地点"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("CFDD"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "购置日期"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("GZRQ"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "保修截止日期"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BXJZRQ"), ""))
					          )
		    			);
		    			mxarr.add(listarr);
		    	  }
	    	}
	    	else if(MenuFlag.ZCWX_WXBG_SH.equals(ywsplx)
	    		   ||MenuFlag.ZCWX_WXBG_CWSH.equals(ywsplx)){
	    		datamap = phoneDao.getWxdByBgbh(djbh);//设备维修报告
		    	List<Map> list = (List<Map>)datamap.get("mxlist");
		    	
		    	if(MenuFlag.ZCWX_WXBG_SH.equals(ywsplx)){
		    		sharr.add(new JsonObject(
			        		new KeysValues("issubmit",true),
			        		new KeysValues("id","0000001"),
			        		new KeysValues("formtype","1"),
			        		new KeysValues("lable","验收日期"),
			        		new KeysValues("name","jzrq"),///这个key用的就是jzrq，但字段是ysrq
			        		new KeysValues("source",Validate.isNullToDefault(datamap.get("YSRQ"), ""))
			           )
			        );
			    	sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("CHECKADVICE"),shyj))
		    	          )
	    			);
		    	}
		    	else if(MenuFlag.ZCWX_WXBG_CWSH.equals(ywsplx)){
		    		sharr.add(new JsonObject(
    						new KeysValues("issubmit",true),
    						new KeysValues("id","0000001"),
    						new KeysValues("formtype","1"),
    						new KeysValues("lable","凭  证  号"),
    						new KeysValues("name","pzh"),
    						new KeysValues("btbz","1"),
    						new KeysValues("source",Validate.isNullToDefault(datamap.get("PZH"), ""))
			           )
			        );
		    		sharr.add(new JsonObject(
			        		new KeysValues("issubmit",true),
			        		new KeysValues("id","0000002"),
			        		new KeysValues("formtype","1"),
			        		new KeysValues("lable","入账日期"),
			        		new KeysValues("name","jzrq"),//这个就是存到jzrq里了
			        		new KeysValues("btbz","1"),
			        		new KeysValues("source",Validate.isNullToDefault(datamap.get("JZRQ"), ""))
			           )
			        );
			    	sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000003"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("CWYJ"),shyj))
		    	          )
	    			);
		    	}
//		    	array.add(new JsonObject(
//			            new KeysValues("issubmit",false),
//			            new KeysValues("id","0000004"),
//			            new KeysValues("formtype","1"),
//			            new KeysValues("lable","报告编号"),
//			            new KeysValues("source",Validate.isNullToDefault(datamap.get("ORDERID"), ""))
//			          )
//    			);
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000005"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","维修申请单号"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPORTID"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000006"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","申请单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPAIRCOMPANYMC"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000007"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","维  修  商"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("WXCJ"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000008"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","维修商联系方式"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPAIRCONTACT"), ""))
				          )
					  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000009"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","维  修  人"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPAIRPERSON"), ""))
				          )
					  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000010"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","维修日期"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPAIRTIME"), ""))
				          )
					  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000011"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","维修金额"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPAIRMONEY"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000012"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","验  收  人"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("ACCEPTPERSONMC"), ""))
				          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000013"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","验收意见"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("ACCEPTADVICE"), ""))
				          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000014"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","维修项目及更换配件"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("REPAIRRECORD"), ""))
				          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000015"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","维修后仪器设备状态"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("WXHSBZT"), ""))
				          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000016"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","备        注"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("REMARK"), ""))
				          )
				  );

		    	  JsonArray listarr;
		    	  int xh = 0;
		    	  for(Map mxmap : list){
		    			listarr = new JsonArray();
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","资产名称"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("YQMC"), "") + "-" + Validate.isNullToDefaultString(mxmap.get("YQBH"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable","使  用  人"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source",Validate.isNullToDefaultString(mxmap.get("FZR"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "使用单位"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("SYDW"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "存放地点"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("CFDD"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "购置日期"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("GZRQ"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "保修截止日期"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("BXJZRQ"), ""))
					          )
		    			);
		    			listarr.add(new JsonObject(
	    			            new KeysValues("issubmit",false),
	    			            new KeysValues("id",xh++),
					            new KeysValues("lable", "维修费用"),
					            new KeysValues("formtype","1"),
					            new KeysValues("source", Validate.isNullToDefaultString(mxmap.get("WXFY"), ""))
					          )
		    			);
		    			mxarr.add(listarr);
		    	  }
	    	}
	    	else if(MenuFlag.ZCWX_JFZJ_DWLDSH.equals(ywsplx)
	    			||MenuFlag.ZCWX_JFZJ_CWSH.equals(ywsplx)){
	    		datamap = phoneDao.getWxjfzjBySqbh(djbh);//设备维修经费追加
	    		if(MenuFlag.ZCWX_JFZJ_DWLDSH.equals(ywsplx)){
		    		sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000001"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("DWYJ"),shyj))
		    	          )
    				);
	    		}
	    		else if(MenuFlag.ZCWX_JFZJ_CWSH.equals(ywsplx)){
	    			sharr.add(new JsonObject(
		    	            new KeysValues("issubmit",true),
		    	            new KeysValues("id","0000001"),
		    	            new KeysValues("formtype","1"),
		    	            new KeysValues("lable","审核意见"),
				            new KeysValues("name","shyj"),
		    	            new KeysValues("btbz","0"),
		    	            new KeysValues("source",Validate.isNullToDefault(datamap.get("ZCCYJ"),shyj))
		    	          )
    				);
	    		}
//		    	  array.add(new JsonObject(
//			            new KeysValues("issubmit",false),
//			            new KeysValues("id","0000002"),
//			            new KeysValues("formtype","1"),
//			            new KeysValues("lable","申请编号"),
//			            new KeysValues("source",Validate.isNullToDefault(datamap.get("SQBH"), ""))
//			          )
//				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000003"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","申  请  人"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("SQRY"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000004"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","使用单位"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("SYDW"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000005"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","申请日期"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("SQRQ"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000006"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","金        额"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("JE"), ""))
				          )
					  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000007"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","年        度"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("ND"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
			            new KeysValues("issubmit",false),
			            new KeysValues("id","0000008"),
			            new KeysValues("formtype","1"),
			            new KeysValues("lable","申请原因"),
			            new KeysValues("source",Validate.isNullToDefault(datamap.get("SQYY"), ""))
			          )
				  );
		    	  array.add(new JsonObject(
				            new KeysValues("issubmit",false),
				            new KeysValues("id","0000009"),
				            new KeysValues("formtype","1"),
				            new KeysValues("lable","备        注"),
				            new KeysValues("source",Validate.isNullToDefault(datamap.get("BZ"), ""))
				          )
				  );
	    	}
	    }
	    
	    JsonObject json = new JsonObject(
	      new KeysValues("success",true),
	      new KeysValues("msg","数据加载成功！"),
	      new KeysValues("dbh",djbh),
	      new KeysValues("ywsplx",ywsplx),
	      new KeysValues("shxx",sharr),
	      new KeysValues("items",array),
	      new KeysValues("mxlist",mxarr)
	    );
	    return json.toString();
	}
	/**
	 * 待办事项审核
	 * @param pd
	 * @return
	 */
	public Object ywsptj(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String rybh = map.get("userId").toString();
	    String djbh = map.get("dbh").toString();//单编号
	    pd.put("keyid", "{\"dbh\":\"" + djbh + "\",\"shyj\":\"" + Validate.isNullToDefault(map.get("shyj"),"") + "\"}");//审核的时候用到了
	    pd.put("mkbh", map.get("ywsplx"));
	    
	    if(doShenHe(pd,rybh,"0")){
	    	String state = map.get("state").toString();
	    	if("1".equals(state)){//审核通过
	        	if(MenuFlag.ZCJZ_GLYSH.equals(map.get("ywsplx"))){
	        		if(phoneDao.checkEndFlh(djbh)){
	        			doShenHe(pd,rybh,"2");
			    		return pd.get("msg");
	        		}
	        		else{
	        			return "{\"success\":\"false\",\"msg\":\"该单据不是末级分类，请至电脑端修改分类后审核！\"}";
	        		}
	        	}
	        	else{
		    		doShenHe(pd,rybh,"2");
		    		return pd.get("msg");
	        	}
	    	}
	    	else if("0".equals(state)){//审核退回
	    		doShenHe(pd,rybh,"3");
	    		return pd.get("msg");
	    	}
	    	return "{\"success\":\"false\",\"msg\":\"传入参数错误，审核失败！\"}";
	    }
	    else{
	    	return "{\"success\":\"false\",\"msg\":\"保存审核信息失败！\"}";
	    }
	}
	
	/**
	 * 个人信息接口
	 */
	public Object grxx(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"", "");
		Map rymap = phoneDao.RyxxByGuid(userId);
		return "{\"success\":\"true\",\"msg\":\"数据加载成功\"," +
				"\"grtx\":\""+Validate.isNullToDefaultString(rymap.get("url"),"")+"\"," +
				"\"sfzh\":\""+Validate.isNullToDefaultString(rymap.get("sfzh"),"")+"\"," +
				"\"csny\":\""+Validate.isNullToDefaultString(rymap.get("csrq"),"")+"\"," +
				"\"whcd\":\""+Validate.isNullToDefaultString(rymap.get("whcd"),"")+"\"," +
				"\"sxzy\":\""+Validate.isNullToDefaultString(rymap.get("sxzy"),"")+"\"," +
				"\"zyzc\":\""+Validate.isNullToDefaultString(rymap.get("zyzc"),"")+"\"," +
				"\"gzrq\":\""+Validate.isNullToDefaultString(rymap.get("gzrq"),"")+"\"," +
				"\"zygz\":\""+Validate.isNullToDefaultString(rymap.get("zygz"),"")+"\"," +
				"\"qqhm\":\""+Validate.isNullToDefaultString(rymap.get("qq"),"")+"\"," +
				"\"lxfs\":\""+Validate.isNullToDefaultString(rymap.get("LXDH"),"")+"\"," +
				"\"gryx\":\""+Validate.isNullToDefaultString(rymap.get("LXDH"),"")+"\"," +
				"\"sclj\":\""+Validate.isNullToDefaultString(Const.TXSJCJ,"")+"\"}";
		
	}
	
	/**
	 * 日常报销列表搜索
	 * @param pd
	 * @return
	 */
	public Object rcbxlistsearch(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"", "");
		int index = Integer.parseInt(Validate.isNullToDefaultString(map.get("index")+"", ""));
		String keyword = Validate.isNullToDefaultString(map.get("keyword")+"", "");
		String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!!\",\"items\":[";
		String result1="{\"success\":\"false\",\"msg\":\"未搜索到您要搜索的信息!!\",\"items\":[";
		List<Map<String,Object>> lclist=phoneDao.getrcbxlist(userId,index,keyword,page_length);
		for(int j=0;j<lclist.size();j++) {
			Map objmap = (Map)lclist.get(j);
			result = result + "{\"xmid\":\""+Validate.isNullToDefaultString(objmap.get("xmbh"),"")+"\",\"xmmc\":\""+Validate.isNullToDefaultString(objmap.get("xmmc"),"")+"\",\"xmlx\":\""+Validate.isNullToDefaultString(objmap.get("jflx"),"")+"\",\"xmfzr\":\""+Validate.isNullToDefaultString(objmap.get("fzr"),"")+"\",\"ysje\":\""+Validate.isNullToDefaultString(objmap.get("ysje"),"")+"\",\"ye\":\""+Validate.isNullToDefaultString(objmap.get("ye"),"")+"\"},";
		}
		if(lclist.size()>0) {
			result = result.substring(0,result.length()-1);
		}
		if(lclist.size()>0) {
			result = result + "]}";
		}else {
			result = result1 + "]}";
		}
		
		return result;
	}
	
	
	
	/**
	 * 头像上传接口
	 * @param pd
	 * @return
	 */
	public Object txsc(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
//		String sclj = Validate.isNullToDefaultString(map.get("sclj")+"", "");
		String guid = Validate.isNullToDefaultString(map.get("guid")+"", "");
//		Map rymap = phoneDao.RyxxByGuid(sclj);
		List<Map<String,Object>> imglist = (List) map.get("sclj");//图片数据集合
		for(int k=0;k<imglist.size();k++ ) {
			String newname=Validate.isNullToDefaultString(imglist.get(k).get("newname"),"");
			String oldname=Validate.isNullToDefaultString(imglist.get(k).get("oldname"),"");
			newname=newname.substring(newname.lastIndexOf("/phone"));
			phoneDao.uploadimg(guid,newname,oldname);
		}
		String sclj=Const.SJCJ2;//上传路径
		return sclj;
	}
	
	
	/**
	 * s首页搜索
	 * @param pd
	 * @return
	 */
	public Object dshlistsearch(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Syss syss = new Syss();
		String userId = Validate.isNullToDefaultString(map.get("userId")+"", "");
		String ywlx = Validate.isNullToDefaultString(map.get("ywlx")+"", "");//业务类型：0、全部；1、出差申请；2、日常报销；3、出差报销；4、公务接待；5、接待报销
		int index = Integer.parseInt(Validate.isNullToDefaultString(map.get("index")+"", ""));
		String keyword = Validate.isNullToDefaultString(map.get("keyword")+"", "");
		syss.setUserId(userId);
		syss.setYwlx(ywlx);
		syss.setIndex(index);
		syss.setKeyword(keyword);
		String cxywlx = "";
//		111
		try {
			List list = new ArrayList();
			if ("0".equals(ywlx)) {// 查询全部
				cxywlx = "qb";
				List<Map<String, Object>> shxxlist = phoneDao.getShxx(userId, cxywlx,index,page_length,keyword);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", shxxlist.get(i).get("SHMC"));
					map1.put("time", shxxlist.get(i).get("TIME"));
					map1.put("bh", shxxlist.get(i).get("GUID"));
					map1.put("sqr", shxxlist.get(i).get("SQRXM"));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(Validate.isNull(shxxlist)) {
					syss.setMsg("未找到您要查询的信息!!");
					syss.setSuccess(false);
				}else {
					syss.setMsg("数据加载成功!!");
					syss.setSuccess(true);
					syss.setItems(list);
				}
			} else if ("1".equals(ywlx)) {
				cxywlx = "ccsq";
				List<Map<String, Object>> shxxlist = phoneDao.getShxx(userId, cxywlx,index,page_length,keyword);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", shxxlist.get(i).get("SHMC"));
					map1.put("time", shxxlist.get(i).get("TIME"));
					map1.put("bh", shxxlist.get(i).get("GUID"));
					map1.put("sqr", shxxlist.get(i).get("SQRXM"));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(Validate.isNull(shxxlist)) {
					syss.setMsg("未找到您要查询的信息!!");
					syss.setSuccess(false);
				}else {
					syss.setMsg("数据加载成功!!");
					syss.setSuccess(true);
					syss.setItems(list);
				}
			} else if ("2".equals(ywlx)) {
				cxywlx = "rcbx";
				List<Map<String, Object>> shxxlist = phoneDao.getShxx(userId, cxywlx,index,page_length,keyword);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("SHMC"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("sqr", Validate.isNullToDefaultString(shxxlist.get(i).get("SQRXM"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(Validate.isNull(shxxlist)) {
					syss.setMsg("未找到您要查询的信息!!");
					syss.setSuccess(false);
				}else {
					syss.setMsg("数据加载成功!!");
					syss.setSuccess(true);
					syss.setItems(list);
				}
			} else if ("3".equals(ywlx)) {
				cxywlx = "ccbx";
				List<Map<String, Object>> shxxlist = phoneDao.getShxx(userId, cxywlx,index,page_length,keyword);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("SHMC"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("sqr", Validate.isNullToDefaultString(shxxlist.get(i).get("SQRXM"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(Validate.isNull(shxxlist)) {
					syss.setMsg("未找到您要查询的信息!!");
					syss.setSuccess(false);
				}else {
					syss.setMsg("数据加载成功!!");
					syss.setSuccess(true);
					syss.setItems(list);
				}
			} else if ("4".equals(ywlx)) {
				cxywlx = "gwjd";
				List<Map<String, Object>> shxxlist = phoneDao.getShxx(userId, cxywlx,index,page_length,keyword);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("SHMC"),""));
					map1.put("time", Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("bh", Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("sqr", Validate.isNullToDefaultString(shxxlist.get(i).get("SQRXM"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(Validate.isNull(shxxlist)) {
					syss.setMsg("未找到您要查询的信息!!");
					syss.setSuccess(false);
				}else {
					syss.setMsg("数据加载成功!!");
					syss.setSuccess(true);
					syss.setItems(list);
				}
			} else if ("5".equals(ywlx)) {
				cxywlx = "jdbx";
				List<Map<String, Object>> shxxlist = phoneDao.getShxx(userId, cxywlx,index,page_length,keyword);// 审核信息
				for (int i = 0; i < shxxlist.size(); i++) {
					Map map1 = new HashMap<>();
					map1.put("title", Validate.isNullToDefaultString(shxxlist.get(i).get("SHMC"),""));
					map1.put("time",  Validate.isNullToDefaultString(shxxlist.get(i).get("TIME"),""));
					map1.put("bh",    Validate.isNullToDefaultString(shxxlist.get(i).get("GUID"),""));
					map1.put("sqr",   Validate.isNullToDefaultString(shxxlist.get(i).get("SQRXM"),""));
					map1.put("state", Validate.isNullToDefaultString(shxxlist.get(i).get("STATE"),""));
					list.add(map1);
				}
				if(Validate.isNull(shxxlist)) {
					syss.setMsg("未找到您要查询的信息!!");
					syss.setSuccess(false);
				}else {
					syss.setMsg("数据加载成功!!");
					syss.setSuccess(true);
					syss.setItems(list);
				}
			}
		}catch (Exception e) {
			syss.setMsg("数据加载失败!!");
			syss.setSuccess(false);
		}
		return new Gson().toJson(syss);
	}
	
	
	/**
	 * 个人信息修改接口
	 * @param pd
	 * @return
	 */
	public Object grxxxg(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId"),"");
		String sfzh = Validate.isNullToDefaultString(map.get("sfzh"),"");
		String csny = Validate.isNullToDefaultString(map.get("csny"),"");
		String whcd = Validate.isNullToDefaultString(map.get("whcd"),"");
		String lxfs = Validate.isNullToDefaultString(map.get("lxfs"),"");
		String qqhm = Validate.isNullToDefaultString(map.get("qqhm"),"");
		String gryx = Validate.isNullToDefaultString(map.get("gryx"),"");
		String grtx = Validate.isNullToDefaultString(map.get("grtx"),"");
		int a =phoneDao.getgrxxxg(userId,sfzh,csny,whcd,lxfs,qqhm,gryx);
		String newname=grtx;
//		newname=newname.substring(newname.lastIndexOf("/imgFile"));
		int i =ywcdao.inserttximglist(userId,newname);
		if(i>0&&a>0) {
			return "{\"success\":\"true\",\"msg\":\"true\"}";
		}else {
			return "{\"success\":\"false\",\"msg\":\"false\"}";
		}
	}

	/**
	 * 加载版本说明
	 */
	public Object versions(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		
		PageList pagelist = new PageList();
		pagelist.setSqlText("gid,bbh,gxry,to_char(gxrq,'yyyy-mm-dd') gxrq,gxnr");
		String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
		String where = "";
		if(Validate.noNull(keyword)){
			keyword = keyword.replace("'", "");
			where = " and gxnr like '%" + keyword + "%' ";
		}
		pagelist.setStrWhere(where);
		pagelist.setTableName("zc_app_bbsm");
		
		pagelist.setOrderBy("order by gxrq desc");
		
		List list = getPageList(pagelist,map,page_length);

		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到版本说明！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多版本说明！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for (int i = 0; i < list.size(); i++) {
				Map xxmap = (Map)list.get(i);
				array.add(new JsonObject(
						new KeysValues("gxrq", Validate.isNullToDefault(xxmap.get("GXRQ"), "")),
						new KeysValues("bbh", Validate.isNullToDefault(xxmap.get("BBH"), "")),
						new KeysValues("gxnr",xxmap.get("GXNR"))
						)
				);
			}
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	
	/**
	 * 获取通讯录
	 * @return
	 */
	public Object hqtxl(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		String rybh = map.get("userId").toString();
		String keyword = Validate.isNullToDefaultString(map.get("keyword"), "");
		List<Map<String, Object>> list = phoneDao.hqtxldw(rybh,keyword);
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多通讯录信息！\"}";
			}
		}
		else{
			int start = (index - 1) * page_length;
			int end = (index * page_length);
			if(list.size() < end){
				end = list.size();
			}
			
			JsonArray dwarr = new JsonArray();
			JsonArray ryarr;
			Map<String, Object> dwmap;
			for(int i = start; i < end; i++){
				dwmap = (Map)list.get(i);
				List<Map<String, Object>> listry = phoneDao.hqtxlry(dwmap.get("DWBH").toString(),keyword);
				ryarr = new JsonArray();
				for(Map<String, Object> mapry : listry){
					ryarr.add(new JsonObject(
									new KeysValues("name",mapry.get("XM")),
							  		new KeysValues("bgdh",Validate.isNullToDefault(mapry.get("BDDH"),"")),
							  		new KeysValues("detail",Validate.isNullToDefault(mapry.get("MOBLIE"),""))
							 )
					);
				}
				dwarr.add(new JsonObject(
								new KeysValues("departmentname",dwmap.get("MC")),
								new KeysValues("items",ryarr)
						 )
				);
			}
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","查询成功！"),
					new KeysValues("departments",dwarr)
			);
			return json.toString();
		}
	}
	
	/**
	 * 我的名下资产列表展示
	 * @return
	 */
	public Object mxzclist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		String rybh = map.get("userId").toString();
		
		PageList pagelist = new PageList();
		List<Map> list = setZjbListPar(pagelist,map,page_length," and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') and syr = '" + rybh + "' and ztbz = '" + StateManager.ZCJZ_CW_TG + "' ");
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的数据！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多名下资产！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map zcmap : list){
				array.add(new JsonObject(
						new KeysValues("zcbh", Validate.isNullToDefault(zcmap.get("YQBH"), "")),
						new KeysValues("zcmc", Validate.isNullToDefault(zcmap.get("YQMC"), "")),
						new KeysValues("sydw", Validate.isNullToDefault(zcmap.get("SYDWMC"), "")),
						new KeysValues("cfdd", Validate.isNullToDefault(zcmap.get("BZXXMC"), "")),
						new KeysValues("gzrq",Validate.isNullToDefault(zcmap.get("GZRQ"), "")),
						new KeysValues("gg",Validate.isNullToDefault(zcmap.get("GG"), "")),
						new KeysValues("xh",Validate.isNullToDefault(zcmap.get("XH"), ""))
						)
				);
			}
			
			pagelist.setHj1(" sum(nvl(sykh,1)) slhj," +
					" decode(nvl(sum(dj),0),0,'0.00',(to_char(round(sum(dj),2),'fm99999999999990.00'))) djs,"+ 
					" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj");
			List zjlist = pageService.getPageZjList(pagelist);
			//如果list能查到数据，那么zjlist肯定有
			Map zcmap = (Map)zjlist.get(0);
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("zczsl",zcmap.get("SLHJ").toString()),
					new KeysValues("zczjz",zcmap.get("ZZJHJ").toString()),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 名下资产详情加载
	 * @return
	 */
	public Object mxzcxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String rybh = map.get("userId").toString();
		String zcbh = map.get("bh").toString();
		Map zcxxxqMap = phoneDao.zcxxxq(zcbh," and syr = '" + rybh + "' and ztbz = '" + StateManager.ZCJZ_CW_TG + "' and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') ");
		if(zcxxxqMap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"暂无该资产信息或该资产不在您名下！\"}";
		}
		else{
			JsonArray array = new JsonArray();
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111111"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("YQBH"), "")),
					new KeysValues("lable", "资产编号")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111112"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("YQMC"), "")),
					new KeysValues("lable", "资产名称")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111113"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("FLH"), "")),
					new KeysValues("lable", "分  类  号")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111114"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("FLMC"), "")),
					new KeysValues("lable", "分类名称")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111115"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYDWMC"), "")),
					new KeysValues("lable", "使用单位")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111116"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYFXMC"), "")),
					new KeysValues("lable", "使用方向")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111117"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("BZXXMC"), "")),
					new KeysValues("lable", "存放地点")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111118"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("DJ"), "")),
					new KeysValues("lable", "单        价")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111119"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYKH"), "")),
					new KeysValues("lable", "套 (件) 数")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111120"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("ZZJ"), "")),
					new KeysValues("lable", "总        价")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111121"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("GG"), "")),
					new KeysValues("lable", "规        格")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111122"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("XH"), "")),
					new KeysValues("lable", "型        号")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111123"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SCCJ"), "")),
					new KeysValues("lable", "生产厂家")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111124"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("JLDWMC"), "")),
					new KeysValues("lable", "计量单位")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111125"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("XZMC"), "")),
					new KeysValues("lable", "现        状")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111126"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("GZRQ"),"")),
					new KeysValues("lable", "购置日期")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111127"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("RZRQ"), "")),
					new KeysValues("lable", "入账日期")
					));
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			
			return json.toString();
		}
	}
	
	/**
	 * 通知公告列表展示
	 * @return
	 */
	public Object tzgglist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");//用户名GID
		int index = (int)Float.parseFloat(map.get("index").toString());//分页
//		String url = phoneDao.gettzgglistUrl();
		//带分页的sql语句
		List list = phoneDao.gettzgglistPagelist(index,page_length);
		Map shxxmap;
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的信息！\"}";
			}else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多通知公告！\"}";
			}
		}else{
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
			        "\"tzlist\":[";//打头
			for(int i = 0;i<list.size();i++) {//拼数组tzlist
				int j = i+1;
				shxxmap = (Map)list.get(i);
				Map maptemp = fbxxDao.getObjectById(shxxmap.get("GID")+"");
				String url = phoneDao.getWorkUrl(shxxmap.get("GID")+"");
				String str1 = "\"";
				String str2 = "\\\"";
				result = result + "{\"title\":\""+shxxmap.get("TITLE")+"\",\"time\":\""+shxxmap.get("FBSJ")+"\",\"bh\":\""+(j++)+"\",\"url\":\""+url+"\",\"nr\":\""+(maptemp.get("XX")+"").replace(str1,str2 )+"\"},";
//				result = result + "{\"title\":\""+shxxmap.get("TITLE")+"\",\"time\":\""+shxxmap.get("FBSJ")+"\",\"bh\":\""+(j++)+"\",\"url\":\""+url+"\",\"nr\":\""+111+"\"},";
			}
			if(list.size()>0) {//如若不空则去尾
				result = result.substring(0,result.length()-1);
			}
			result = result + "]}";//收尾
			return result;
		}
	}
	
	/**
	 * 通知搜索接口
	 * @param pd
	 * @return
	 */
	public Object tzggsearch(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		String keyword = Validate.isNullToDefaultString(map.get("keyword"), "");
		List list = phoneDao.gettzggsearchPagelist(keyword);
		Map shxxmap;
		String url = "";
		if(list.size() > 0) {
			Map infomap = (Map)list.get(0);
			url = phoneDao.gettzggsearchlistUrl(infomap.get("GID")+"");
		}
		
		if(list == null || list.size() == 0){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"tzlist\":[";//打头
			for(int i = 0;i<list.size();i++) {//拼数组tzlist
				int j = i+1;
				shxxmap = (Map)list.get(i);
				Map maptemp = fbxxDao.getObjectById(shxxmap.get("GID")+"");
				String str1 = "\"";
				String str2 = "\\\"";
				result = result + "{\"title\":\""+shxxmap.get("TITLE")+"\",\"time\":\""+shxxmap.get("FBSJ")+"\",\"bh\":\""+(j++)+"\",\"url\":\""+url+"\",\"nr\":\""+(maptemp.get("XX")+"").replace(str1,str2 )+"\"},";
			}
			if(list.size()>0) {//如若不空则去尾
				result = result.substring(0,result.length()-1);
			}
			result = result + "]}";//收尾
			return result;
		}
		
	}
	
	/**
	 * 通讯录接口
	 * @param pd
	 * @return
	 */
	public Object txl(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		
		//带分页的sql语句
		List dwlist = phoneDao.getDwPagelist();
		Map shxxmap;
		Map rymap;
		if(dwlist == null || dwlist.size() == 0){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"items\":[";//打头
			for(int i = 0;i<dwlist.size();i++) {
				shxxmap = (Map)dwlist.get(i);
				List rylist = phoneDao.getRyPagelist(shxxmap.get("DWBH")+"");
				result = result + "{\"schoolname\":\""+shxxmap.get("MC")+"\",\"schoolid\":\""+shxxmap.get("DWBH")+"\",\"childnum\":\""+rylist.size()+"\",";
				result = result + "\"items\":[";
				for(int j = 0;j<rylist.size();j++) {
					rymap = (Map)rylist.get(j);	
					result = result + "{\"name\":\""+rymap.get("XM")+"\",\"zw\":\""+Validate.isNullToDefaultString(rymap.get("ZWMC"), "普通职工")+"\",\"phone\":\""+Validate.isNullToDefaultString(rymap.get("LXDH"), "")+"\",\"tximg\":\""+rymap.get("URL")+"\"},";
				}
				if(rylist.size()>0) {//如若不空则去尾
					result = result.substring(0,result.length()-1);
				}
				result = result + "]},";//收尾
			}
			if(dwlist.size()>0) {//如若不空则去尾
				result = result.substring(0,result.length()-1);
			}
			result = result + "]}";//收尾
			return result;
		}
		
	}
	
	/**
	 * 通讯录搜索
	 * @param pd
	 * @return
	 */
	public Object txlsearch(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		String keyword = Validate.isNullToDefaultString(map.get("keyword")+"","");
		//带分页的sql语句
		List rylist = phoneDao.getRyPagelistBykeyword(keyword);
		Map shxxmap;
		Map rymap;
		if(rylist == null || rylist.size() == 0){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"items\":[";//打头
			for(int i = 0;i<rylist.size();i++) {
				rymap = (Map)rylist.get(i);
				result = result + "{\"name\":\""+rymap.get("XM")+"\",\"zw\":\""+Validate.isNullToDefaultString(rymap.get("ZWMC"),"普通职员")+"\",\"phone\":\""+rymap.get("LXFS")+"\",\"tximg\":\""+rymap.get("URL")+"\"},";
			}
			if(rylist.size()>0) {//如若不空则去尾
				result = result.substring(0,result.length()-1);
			}
			result = result + "]}";//收尾
			return result;
		}
		
	}
	
	/**
	 * 我的薪酬接口
	 * @param pd
	 * @return
	 */
	public Object wdxc(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		String time = Validate.isNullToDefaultString(map.get("time")+"","");
		//根据用户id和时间查询查询我的薪酬
//		List rylist = phoneDao.getWdxcPagelistBytime(time);
		Map shxxmap;
		Map rymap;
		if(Validate.isNull(userId)){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"sfgz\":\"9700.00\","+
					"\"jbgz\":\"5120.00\","+
					"\"jxgz\":\"2400.50\","+
					"\"btgz\":\"3520.00\","+
					"\"dkgz\":\"3600.20\","+
					"\"yfgz\":\"3500.00\"}";
			return result;
		}
		
	}
	
	/**
	 * 我的项目列表接口
	 * @param pd
	 * @return
	 */
	public Object wdxm(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		int index = Integer.parseInt(Validate.isNullToDefaultString(map.get("index")+"",""));
		//带分页的sql语句
		List wdxmlist = phoneDao.getWdxmPagelist(userId,index,page_length);
		Map rymap;
		if(wdxmlist == null || wdxmlist.size() == 0){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"items\":[";//打头
			for(int i = 0;i<wdxmlist.size();i++) {
				rymap = (Map)wdxmlist.get(i);
				result = result + "{\"bh\":\""+rymap.get("XMBH")+"\",\"title\":\""+rymap.get("XMMC")+"\",\"ysje\":\""+rymap.get("YSJE")+"\",\"zcje\":\""+rymap.get("ZCJE")+"\",\"syje\":\""+rymap.get("SYJE")+"\"},";
			}
			if(wdxmlist.size()>0) {//如若不空则去尾
				result = result.substring(0,result.length()-1);
			}
			result = result + "]}";//收尾
			return result;
		}
		
	}
	
	/**
	 * 我的项目搜索
	 * @param pd
	 * @return
	 */
	public Object wdxmsearch(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		String keyword = Validate.isNullToDefaultString(map.get("keyword")+"","");
		//带分页的sql语句
		List wdxmlist = phoneDao.getWdxmSearchPagelist(userId,keyword);
		Map rymap;
		if(wdxmlist == null || wdxmlist.size() == 0){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"items\":[";//打头
			for(int i = 0;i<wdxmlist.size();i++) {
				rymap = (Map)wdxmlist.get(i);
				result = result + "{\"bh\":\""+rymap.get("XMBH")+"\",\"title\":\""+rymap.get("XMMC")+"\",\"ysje\":\""+rymap.get("YSJE")+"\",\"zcje\":\""+rymap.get("ZCJE")+"\",\"syje\":\""+rymap.get("SYJE")+"\"},";
			}
			if(wdxmlist.size()>0) {//如若不空则去尾
				result = result.substring(0,result.length()-1);
			}
			result = result + "]}";//收尾
			return result;
		}
		
	}
	
	
	/**
	 * 我的项目加载详情
	 * @param pd
	 * @return
	 */
	public Object wdxmxq(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		String bh = Validate.isNullToDefaultString(map.get("bh")+"","");
		//带分页的sql语句
		Map infomap = phoneDao.getWdxmxqPagelist(userId,bh);
		int count = 1;
		if(Validate.isNull(infomap.get("XMBH"))){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"items\":[";//打头
			result = result + "{\"issubmit\":\"false\",\"id\":\"01\",\"name\":\"标签1\",\"formtype\":\"1\",\"source\":\""+infomap.get("XMBH")+"\",\"lable\":\"项目编号\"},";
			result = result + "{\"issubmit\":\"false\",\"id\":\"02\",\"name\":\"标签1\",\"formtype\":\"1\",\"source\":\""+infomap.get("XMMC")+"\",\"lable\":\"项目名称\"},";
			result = result + "{\"issubmit\":\"false\",\"id\":\"03\",\"name\":\"标签1\",\"formtype\":\"1\",\"source\":\""+infomap.get("XMFZR")+"\",\"lable\":\"负  责  人\"},";
			result = result + "{\"issubmit\":\"false\",\"id\":\"04\",\"name\":\"标签1\",\"formtype\":\"1\",\"source\":\""+infomap.get("YSJE")+"\",\"lable\":\"预算金额\"},";
			result = result + "{\"issubmit\":\"false\",\"id\":\"05\",\"name\":\"标签1\",\"formtype\":\"1\",\"source\":\""+infomap.get("ZCJE")+"\",\"lable\":\"支出金额\"},";
			result = result + "{\"issubmit\":\"false\",\"id\":\"06\",\"name\":\"标签1\",\"formtype\":\"1\",\"source\":\""+infomap.get("DSHJE")+"\",\"lable\":\"待审金额\"},";
			result = result + "{\"issubmit\":\"false\",\"id\":\"07\",\"name\":\"标签1\",\"formtype\":\"1\",\"source\":\""+infomap.get("SYJE")+"\",\"lable\":\"剩余金额\"},";
			result = result.substring(0,result.length()-1);
			result = result + "]}";//收尾
			return result;
		}
		
	}
	
	/**
	 * 我的银行卡
	 * @param pd
	 * @return
	 */
	public Object wdyhk(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		int index = Integer.parseInt(Validate.isNullToDefaultString(map.get("index")+"",""));
		//带分页的sql语句
		List wdxmlist = phoneDao.getWdyhkPagelist(userId,index,page_length);
		Map infomap;
		if(wdxmlist == null || wdxmlist.size() == 0){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"items\":[";//打头
			for(int i = 0;i<wdxmlist.size();i++) {
				infomap = (Map)wdxmlist.get(i);
				int count = i+1;
				result = result + "{\"bh\":\""+count+"\",\"yhmc\":\""+infomap.get("khyh")+"\",\"klb\":\"储蓄卡\",\"kh\":\""+infomap.get("khyhzh")+"\"},";
			}
			if(wdxmlist.size()>0) {//如若不空则去尾
				result = result.substring(0,result.length()-1);
			}
			result = result + "]}";//收尾
			return result;
		}
		
	}
	
	
	/**
	 * 我的公务卡
	 * @param pd
	 * @return
	 */
	public Object wdgwk(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		int index = Integer.parseInt(Validate.isNullToDefaultString(map.get(
				"index")+"",""));
		//带分页的sql语句
//		List wdxmlist = phoneDao.getWdyhkPagelist(userId,index,page_length);
//		Map infomap;
//		if(wdxmlist == null || wdxmlist.size() == 0){
//			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
//		}else {
			String result = "{\"success\":\"true\",\"msg\":\"数据加载成功!\"," +
					"\"items\":[";//打头
//			for(int i = 0;i<wdxmlist.size();i++) {
//				infomap = (Map)wdxmlist.get(i);
				result = result + "{\"bh\":\"000001\",\"yhmc\":\"中国建设银行\",\"yhmc\":\"储蓄卡\",\"yhmc\":\"6217 0022 7000 1654 765\"},";
//			}
//			if(wdxmlist.size()>0) {//如若不空则去尾
				result = result.substring(0,result.length()-1);
//			}
			result = result + "]}";//收尾
			return result;
//		}
		
	}
	
	/**
	 * 密码修改接口
	 * @param pd
	 * @return
	 */
	public Object mmxg(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String userId = Validate.isNullToDefaultString(map.get("userId")+"","");
		String oldpwd = Validate.isNullToDefaultString(map.get("oldpwd")+"","");
		String newpwd = Validate.isNullToDefaultString(map.get("newpwd")+"","");
		String username = phoneDao.getrybhByguid(userId);
		//检测旧密码对不对
		Map ryxx_map = getUserByNameAndPwd(username,oldpwd);
		if("1".equals(ryxx_map.get("errCode")) || "6".equals(ryxx_map.get("errCode"))) {//检测
			if(phoneDao.mmxg(userId,newpwd)>0) {//修改密码
				return "{\"success\":\"true\",\"msg\":\"密码修改成功！\"}";
			}else {
				return "{\"success\":\"false\",\"msg\":\"密码修改失败！\"}";
			}
		}else {
			return "{\"success\":\"false\",\"msg\":\"原始密码输入有误！\"}";
		}
	}
	/**
	 * 通知公告详情加载
	 * @return
	 */
	public Object tzggxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String rybh = map.get("userId").toString();
		String id = Validate.isNullToDefaultString(map.get("bh"),"");
		Map tzggMap = phoneDao.tzggxq(id,rybh);
		if(tzggMap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"暂无通知公告信息！\"}";
		}
		else{
			JsonArray array = new JsonArray();
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111111"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(tzggMap.get("TITLE"), "")),
					new KeysValues("lable", "标题")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111112"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(tzggMap.get("FBRMC"), "")),
					new KeysValues("lable", "发布人")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111113"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(tzggMap.get("FBSJ"), "")),
					new KeysValues("lable", "发布时间")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111114"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefaultString(tzggMap.get("XX"), "")),
					new KeysValues("lable", "信息")
					));
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			
			return json.toString();
		}
	}
	
	/**
	 * 系统消息列表展示
	 * @return
	 */
	public Object xtxxlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		String rybh = map.get("userId").toString();
		String lx = Validate.isNullToDefaultString(map.get("lx"), "0");
		String where = "";
		if("0".equals(lx)){
			where = " and sfck = '未读' ";
		}
		else{
			where = " and sfck = '已读' ";
		}

		PageList pagelist = new PageList();
		pagelist.setSqlText("gid,title,fbsj,fbrmc,sfck");
		String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
		if(Validate.noNull(keyword)){
			keyword = keyword.replace("'", "");
			where += " and (fbrmc like '%" + keyword + "%' or title like '%" + keyword + "%') ";
		}
		pagelist.setStrWhere(where);
		pagelist.setTableName("(select gid,title,to_char(fbsj,'yyyy-mm-dd') fbsj,fbr,(select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = x.fbr) fbrmc,(case (select count(*) from zc_tzxx_view where bh = x.gid and rybh = '" + map.get("userId") + "') when 0 then '未读' else '已读' end) sfck from zc_sys_tzxx x where sfzs = '1' and (nvl(dwbh,'$') = '$' or ((select dwbh from gx_sys_ryb r where r.rybh = '" + rybh + "') in (select dwbh from gx_sys_dwb d start with d.dwbh = x.dwbh connect by prior dwbh = sjdw)))) x");
		pagelist.setOrderBy("order by fbsj desc");
		
		List list = getPageList(pagelist,map,page_length);
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多系统消息！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for (int i = 0; i < list.size(); i++) {
				Map xxmap = (Map)list.get(i);
				array.add(new JsonObject(new KeysValues("title", Validate.isNullToDefault(xxmap.get("TITLE"), "")),
						new KeysValues("issuer", Validate.isNullToDefault(xxmap.get("FBRMC"), "")),
						new KeysValues("lb",xxmap.get("GID")),
						new KeysValues("fbsj",Validate.isNullToDefault(xxmap.get("FBSJ"), "")),
						new KeysValues("state",Validate.isNullToDefault(xxmap.get("SFCK"), "")),
						new KeysValues("fbsj",Validate.isNullToDefault(xxmap.get("FBSJ"), "")))
						);
			}
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 系统消息详情加载
	 * @return
	 */
	public Object xtxxxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String rybh = Validate.isNullToDefaultString(map.get("userId"),"");
		String id = Validate.isNullToDefaultString(map.get("bh"),"");
		Map tzggMap = phoneDao.xtxxxq(id,rybh);
		if(tzggMap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"暂无系统消息信息！\"}";
		}
		else{
			JsonArray array = new JsonArray();
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111111"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(tzggMap.get("TITLE"), "")),
					new KeysValues("lable", "标题")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111112"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(tzggMap.get("FBRMC"), "")),
					new KeysValues("lable", "发布人")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111113"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(tzggMap.get("FBSJ"), "")),
					new KeysValues("lable", "发布时间")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111114"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefaultString(tzggMap.get("XX"), "")),
					new KeysValues("lable", "信息")
					));
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			
			return json.toString();
		}
	}
	
	/**
	 * 资产查询列表展示
	 * @return
	 */
	public Object zccxlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		String rybh = map.get("userId").toString();
		
		PageList pagelist = new PageList();
		List<Map> list = setZjbListPar(pagelist,map,page_length," and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') and sydw in (select dwbh from gx_sys_dwb where dwzt = '1' start with dwbh = (select dwbh from gx_sys_ryb where rybh = '" + rybh + "') connect by prior dwbh = sjdw) and ztbz = '" + StateManager.ZCJZ_CW_TG + "' ");
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的资产！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多资产！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map zcmap : list){
				array.add(new JsonObject(
								new KeysValues("zcbh", Validate.isNullToDefault(zcmap.get("YQBH"), "")),
								new KeysValues("zcmc", Validate.isNullToDefault(zcmap.get("YQMC"), "")),
								new KeysValues("syr",Validate.isNullToDefault(zcmap.get("SYRMC"), "")),
								new KeysValues("sydw",Validate.isNullToDefault(zcmap.get("SYDWMC"), ""))
						 )
				);
			}
			
			pagelist.setHj1(" sum(nvl(sykh,1)) slhj," +
					" decode(nvl(sum(dj),0),0,'0.00',(to_char(round(sum(dj),2),'fm99999999999990.00'))) djs,"+ 
					" decode(nvl(sum(zzj),0),0,'0.00',(to_char(round(sum(zzj),2),'fm99999999999990.00'))) zzjhj");
			List zjlist = pageService.getPageZjList(pagelist);
			//如果list能查到数据，那么zjlist肯定有
			Map zcmap = (Map)zjlist.get(0);
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("zczsl",zcmap.get("SLHJ").toString()),
					new KeysValues("zczjz",zcmap.get("ZZJHJ").toString()),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 资产查询加载详情
	 * @return
	 */
	public Object zccxxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String rybh = map.get("userId").toString();
		//Map zcxxxqMap = phoneDao.zcxxxq(map.get("bh").toString()," and sydw in (select dwbh from gx_sys_dwb where dwzt = '1' start with dwbh = (select dwbh from gx_sys_ryb where rybh = '" + rybh + "') connect by prior dwbh = sjdw) and ztbz = '" + StateManager.ZCJZ_CW_TG + "' and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') ");
		//所有的查看资产详情都是走的这个方法，这里如果加上权限闲置资产领用查看资产详情就有可能查不到信息
		Map zcxxxqMap = phoneDao.zcxxxq(map.get("bh").toString(),"");
		if(zcxxxqMap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"没有查询到该资产信息或无权查看该资产信息！\"}";
		}
		else{
			JsonArray array = new JsonArray();
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000001"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("YQBH"), "")),
					new KeysValues("lable", "资产编号")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000002"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("YQMC"), "")),
					new KeysValues("lable", "资产名称")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000003"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("FLH"), "")),
					new KeysValues("lable", "分  类  号")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000004"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("FLMC"), "")),
					new KeysValues("lable", "分类名称")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000005"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYDWMC"), "")),
					new KeysValues("lable", "使用单位")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000006"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYFXMC"), "")),
					new KeysValues("lable", "使用方向")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000007"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYRMC"), "")),
					new KeysValues("lable", "使  用  人")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000008"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("BZXXMC"), "")),
					new KeysValues("lable", "存放地点")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000009"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("GZRQ"),"")),
					new KeysValues("lable", "购置日期")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000010"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("DJ"), "")),
					new KeysValues("lable", "单        价")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000011"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYKH"), "")),
					new KeysValues("lable", "套 (件) 数")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000012"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("ZZJ"), "")),
					new KeysValues("lable", "总        价")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000013"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("JLDWMC"), "")),
					new KeysValues("lable", "计量单位")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000014"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("XZMC"), "")),
					new KeysValues("lable", "现        状")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "0000015"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("RZRQ"), "")),
					new KeysValues("lable", "入账日期")
					));
	
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	
	/**
	 * 进度跟踪列表展示
	 */
	public Object jdgzlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		
		PageList pagelist = new PageList();
		pagelist.setSqlText("bh||mc||lx||to_char(rq,'yyyymmddhhmiss')||jd id,bh,mc,lx,jd,css,rq");
		String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
		if(Validate.noNull(keyword)){
			keyword = keyword.replace("'", "");
			pagelist.setStrWhere(" and (bh like '%" + keyword + "%' or mc like '%" + keyword + "%' or lx like '%" + keyword + "%' or jd like '%" + keyword + "%') ");
		}
		pagelist.setTableName("(" + deskDao.getJdgzSql(map.get("userId").toString()) + ")");
		pagelist.setOrderBy("order by rq desc");
		
		List<Map<String, Object>> list = getPageList(pagelist,map,page_length);
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的数据！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多进度跟踪信息！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map<String, Object> jdmap : list){
				array.add(new JsonObject(new KeysValues("ywlx", jdmap.get("lx")),
								new KeysValues("bh", jdmap.get("bh")),
								new KeysValues("mc",jdmap.get("mc")),
								new KeysValues("dqjd",jdmap.get("jd"))
								)
						);
			}
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	
	/**
	 * 资产图片维护获取资产列表
	 * @return
	 */
	public Object zctpwhlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		String rybh = map.get("userId").toString();
		
		PageList pagelist = new PageList();
		List<Map> list = setZjbListPar(pagelist,map,page_length," and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') and syr = '" + rybh + "' ");
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的资产！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多资产！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map zcmap : list){
				array.add(new JsonObject(
						new KeysValues("zcbh", Validate.isNullToDefault(zcmap.get("YQBH"), "")),
						new KeysValues("zcmc", Validate.isNullToDefault(zcmap.get("YQMC"), "")),
						new KeysValues("sydw", Validate.isNullToDefault(zcmap.get("SYDWMC"), "")),
						new KeysValues("cfdd", Validate.isNullToDefault(zcmap.get("BZXXMC"), "")),
						new KeysValues("gzrq",Validate.isNullToDefault(zcmap.get("GZRQ"), "")),
						new KeysValues("gg",Validate.isNullToDefault(zcmap.get("GG"), "")),
						new KeysValues("xh",Validate.isNullToDefault(zcmap.get("XH"), ""))
					 )
				);
			}
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 资产图片维护获取资产信息
	 * @return
	 */
	public Object zctpwhxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		Map zcxxxqMap = phoneDao.zcxxxq(Validate.isNullToDefaultString(map.get("zcbh"),"")," and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') and syr = '" + map.get("userId").toString() + "' ");
		if(zcxxxqMap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"暂无该资产信息或该资产不在您名下！\"}";
		}
		else{
			JsonArray array = new JsonArray();
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111111"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("YQBH"), "")),
					new KeysValues("lable", "资产编号")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111112"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("YQMC"), "")),
					new KeysValues("lable", "资产名称")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111113"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("FLH"), "")),
					new KeysValues("lable", "分  类  号")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111114"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("FLMC"), "")),
					new KeysValues("lable", "分类名称")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111115"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYDWMC"), "")),
					new KeysValues("lable", "使用单位")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111116"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYFXMC"), "")),
					new KeysValues("lable", "使用方向")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111117"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("BZXXMC"), "")),
					new KeysValues("lable", "存放地点")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111118"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("DJ"), "")),
					new KeysValues("lable", "单        价")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111119"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYKH"), "")),
					new KeysValues("lable", "套 (件) 数")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111120"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("ZZJ"), "")),
					new KeysValues("lable", "总        价")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111121"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("GG"), "")),
					new KeysValues("lable", "规        格")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111122"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("XH"), "")),
					new KeysValues("lable", "型        号")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111123"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SCCJ"), "")),
					new KeysValues("lable", "生产厂家")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111124"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("JLDWMC"), "")),
					new KeysValues("lable", "计量单位")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "11111125"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("XZMC"), "")),
					new KeysValues("lable", "现        状")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111126"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("GZRQ"),"")),
					new KeysValues("lable", "购置日期")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111127"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("RZRQ"), "")),
					new KeysValues("lable", "入账日期")
					));
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			
			return json.toString();
		}
	}
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
				
				String pcfilepath = wllu_path + "\\phone\\" + name;//pc端存放地址
				File uploadPic = new File(wllu_path + "\\phone\\");
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
	/**
	* 资产图片维护提交
	* @return
	*/
	public String zctpwhtj(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		if(phoneDao.zctpwhtj(map)){
			doAddOplogPhone(OplogFlag.SUBMIT,"提交图片信息",map.get("userId").toString(),Validate.isNullToDefaultString(map.get("zcbh"),""));
			return "{\"success\":\"true\",\"msg\":\"保存成功！\"}";
		}
		else{
			return "{\"success\":\"false\",\"msg\":\"保存失败！\"}";
		}
	}
	
	/**
	 * 获取资产清查状态
	 * @return
	 */
	public Object zcqczt(){
		String qcbh = phoneDao.getqcbh();
		if(Validate.isNull(qcbh)){
			return "{\"success\":\"false\",\"msg\":\"没有开启清查业务！\"}";
			
		}else{
			JsonArray array = new JsonArray();
			array.add(new JsonObject(
					new KeysValues("dm","1"),
					new KeysValues("mc","√")
					));
			array.add(new JsonObject(
					new KeysValues("dm","2"),
					new KeysValues("mc","盈")
					));
			array.add(new JsonObject(
					new KeysValues("dm","3"),
					new KeysValues("mc","亏")
					));
			
			JsonObject json = new JsonObject(
				new KeysValues("success",true),
				new KeysValues("msg","加载成功！"),
				new KeysValues("qczt",array),
				new KeysValues("qcxz",getDictList(Constant.SYZK)),
				new KeysValues("pkyy",getDictList(Constant.PKYY)),
				new KeysValues("pyyy",getDictList(Constant.PYYY))
			);
			
			return json.toString();
		}
	}
	/**
	 * 资产清查详情加载
	 * @return
	 */
	public Object zcqcgetinfoqc(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String zcbh = Validate.isNullToDefaultString(map.get("zcbh"),"");
		String rybh = map.get("userId").toString();
		Map zcxxxqMap = phoneDao.zcqcgetinfo(rybh,zcbh);
		if(zcxxxqMap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"暂无该资产信息或该资产不在您管理权限下！\"}";
		}
		else{
			JsonArray array = new JsonArray();
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111111"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("YQBH"), "")),
					new KeysValues("lable", "资产编号")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111112"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("YQMC"), "")),
					new KeysValues("lable", "资产名称")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111113"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("FLH"), "")),
					new KeysValues("lable", "分  类  号")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111114"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("FLMC"), "")),
					new KeysValues("lable", "分类名称")
					));	
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111115"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("BZXXMC"), "")),
					new KeysValues("lable", "存放地点")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111116"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYRMC"), "")),
					new KeysValues("lable", "使  用  人")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111117"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("DJ"), "")),
					new KeysValues("lable", "单        价")
					));
			array.add(new JsonObject(
					new KeysValues("issubmit", false),
					new KeysValues("id", "1111118"),
					new KeysValues("formtype", "1"),
					new KeysValues("source", Validate.isNullToDefault(zcxxxqMap.get("SYDWMC"), "")),
					new KeysValues("lable", "使用单位")
					));
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("qcbh",Validate.isNullToDefault(zcxxxqMap.get("QCBH"), "")),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 提交清查数据
	 * @return
	 * @throws ParseException 
	 */
	public Object zcqctj(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		if(phoneDao.zcqctj(map)){
			doAddOplogPhone(OplogFlag.SUBMIT,"提交资产清查信息",map.get("userId").toString(),Validate.isNullToDefaultString(map.get("zcbh"),""));
			return "{\"success\":\"true\",\"msg\":\"提交成功！\"}";
		}
		else{
			return "{\"success\":\"false\",\"msg\":\"提交失败！\"}";
		}
	}
	
	/**
	 * 获取资产自查列表
	 * @return
	 * @throws ParseException 
	 */
	public Object zczclist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());

		String zcbh = phoneDao.getzcbh();
		if(Validate.isNull(zcbh)){
			return "{\"success\":\"false\",\"msg\":\"没有开启自查业务！\"}";
		}
		else{
			PageList pagelist = new PageList();
			pagelist.setSqlText("qcbh,yqbh,yqmc,flh,flmc,xzbh,xz,syzkdm,syzk,sydwbh,sydw,syrbh,syr,cfddbh,bzxx,gzrq,decode(nvl(k.dj, 0),0,'0.00',(to_char(round(k.dj, 2), 'fm99999999999990.00'))) dj,qcqk,qczt,qcztmc,bfyy,shr,shsj,shyj");
			
			pagelist.setTableName("(select k.qcbh,k.yqbh,k.yqmc,k.flh,k.flmc,k.xz xzbh,(select mc from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = k.xz) xz,k.syzk syzkdm,(select mc from gx_sys_dmb where zl = '" + Constant.SYZK + "' and dm = k.syzk) syzk,k.sydw sydwbh,(select '('||bmh||')'||mc from gx_sys_dwb where dwbh = k.sydw) sydw,k.syr syrbh,(select '('||rygh||')'||xm from gx_sys_ryb where rybh = k.syr) syr,k.bzxx cfddbh,(select '('||ddh||')'||mc from zc_sys_ddb d where d.ddbh = k.bzxx) bzxx,to_char(k.gzrq,'yyyy-mm-dd') gzrq,decode(nvl(k.dj, 0),0,'0.00',(to_char(round(k.dj, 2), 'fm99999999999990.00'))) dj,k.qcqk,k.qczt," + StateManager.getZtmcSql(StateManager.CZLX_ZCZC, "k.qczt") + " qcztmc,bfyy,k.shr,k.shsj,k.shyj from zc_zczcb k where k.qcbh in (select qcbh from zc_zczczt where zt = '1') and k.syr = '" + map.get("userId") + "' and k.qczt in ('" + StateManager.ZCZC_LR + "','" + StateManager.ZCZC_WTG + "')) k");
			String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
			if(Validate.noNull(keyword)){
				keyword = keyword.replace("'", "");
				pagelist.setStrWhere(" and (yqbh like '%" + keyword + "%' or yqmc like '%" + keyword + "%') ");
			}
			pagelist.setOrderBy("order by yqbh");
			
			List<Map<String, Object>> list = getPageList(pagelist,map,page_length);
			if(list == null || list.size() == 0){
				if(index == 1){
					return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的数据！\"}";
				}
				else{
					return "{\"success\":\"false\",\"msg\":\"暂无更多待自查资产！\"}";
				}
			}
			else{
				JsonArray zcztarr = new JsonArray();
				zcztarr.add(new JsonObject(
						new KeysValues("dm","1"),
						new KeysValues("mc","相符")
						));
				zcztarr.add(new JsonObject(
						new KeysValues("dm","2"),
						new KeysValues("mc","不符")
						));
				
				JsonArray array = new JsonArray();
				for(Map zcmap : list){
					array.add(new JsonObject(
									new KeysValues("zcbh", Validate.isNullToDefault(zcmap.get("YQBH"), "")),
									new KeysValues("zcmc", Validate.isNullToDefault(zcmap.get("YQMC"), "")),
									new KeysValues("syr", Validate.isNullToDefault(zcmap.get("SYR"), "")),
									new KeysValues("sydw",Validate.isNullToDefault(zcmap.get("SYDW"), "")),
									new KeysValues("cfdd",Validate.isNullToDefault(zcmap.get("BZXX"), "")),
									new KeysValues("gzrq",Validate.isNullToDefault(zcmap.get("GZRQ"), ""))
							 )
					);
				}
				
				JsonObject json = new JsonObject(
						new KeysValues("success",true),
						new KeysValues("msg","数据加载成功！"),
						new KeysValues("items",array),
						new KeysValues("zczt",zcztarr)
					);
				return json.toString();
			}
		}
	}
	/**
	 * 保存或提交资产自查
	 * @return
	 */
	public String zczctj(PageData pd) {
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		if(phoneDao.zczctj(map,pd)){
			String op = Validate.isNullToDefaultString(map.get("caozuo"), "0");
			String yqbh = Validate.isNullToDefaultString(map.get("zcbh"),"");
			if("0".equals(op)){//保存
				doAddOplogPhone(OplogFlag.UPD,"保存资产自查信息",map.get("userId").toString(),yqbh);
			}
			else{//提交
				doAddOplogPhone(OplogFlag.SUBMIT,"提交资产自查信息",map.get("userId").toString(),yqbh);
			}
			
			return "{\"success\":\"true\",\"msg\":\"" + map.get("errmsg") + "\"}";
		}
		else{
			return "{\"success\":\"false\",\"msg\":\"" + map.get("errmsg") + "\"}";
		}
	}
	
	/**
	 * 获取常见问题列表
	 * @return
	 * @throws ParseException 
	 */
	public Object cjwtlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		
		PageList pagelist = new PageList();
		pagelist.setSqlText("guid,title,xx,to_char(upddate,'yyyy-mm-dd hh24:mi') upddate,jdr,jdrq");
		pagelist.setTableName("zc_cjwtb");
		String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
		if(Validate.noNull(keyword)){
			keyword = keyword.replace("'", "");
			pagelist.setStrWhere(" and (title like '%" + keyword + "%' or xx like '%" + keyword + "%') ");
		}
		pagelist.setOrderBy("order by upddate desc");
		
		List<Map<String, Object>> list = getPageList(pagelist,map,page_length);
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的常见问题！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多常见问题！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map datamap : list){
				array.add(new JsonObject(
								new KeysValues("wtbh", Validate.isNullToDefault(datamap.get("GUID"), "")),
								new KeysValues("wtnr", Validate.isNullToDefault(datamap.get("TITLE"), "")),
								new KeysValues("wtsj", Validate.isNullToDefault(datamap.get("UPDDATE"), ""))
						 )
				);
			};
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","常见问题加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 获取常见问题详情
	 * @return
	 * @throws ParseException 
	 */
	public Object cjwtlbxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		
		Map datamap = phoneDao.cjwtlbxq(Validate.isNullToDefaultString(map.get("wtbh"), ""));
		if(datamap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的常见问题！\"}";
		}
		else{
			return new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","常见问题加载成功！"),
					new KeysValues("wt",Validate.isNullToDefault(datamap.get("TITLE"), "")),
					new KeysValues("hd",Validate.isNullToDefault(datamap.get("XX"), ""))
				).toString();
		}
	}
	
	/**
	 * 获取业务说明列表
	 * @return
	 * @throws ParseException 
	 */
	public Object ywsmlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		
		PageList pagelist = new PageList();
		pagelist.setSqlText("id,mkbh,mkmc,mklx");
		pagelist.setTableName("(select id,mkbh,mkmc,(select mkmc from zc_sys_mkb where mkbh = k.mkbh) mklx from zc_ywsm k) k");
		String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
		if(Validate.noNull(keyword)){
			keyword = keyword.replace("'", "");
			pagelist.setStrWhere(" and mkmc like '%" + keyword + "%' ");
		}
		pagelist.setOrderBy("order by mkbh");
		
		List<Map<String, Object>> list = getPageList(pagelist,map,page_length);
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的业务说明！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多业务说明！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map datamap : list){
				array.add(new JsonObject(
								new KeysValues("ywbh", Validate.isNullToDefault(datamap.get("ID"), "")),
								new KeysValues("ywlx", Validate.isNullToDefault(datamap.get("MKLX"), "")),
								new KeysValues("ywmc", Validate.isNullToDefault(datamap.get("MKMC"), ""))
						 )
				);
			};
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","业务说明加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 获取业务说明详情
	 * @return
	 */
	public Object ywsmxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		
		Map datamap = phoneDao.ywsmxq(Validate.isNullToDefaultString(map.get("ywbh"), ""));
		if(datamap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的业务说明！\"}";
		}
		else{
			return new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","业务说明加载成功！"),
					new KeysValues("content",Validate.isNullToDefault(datamap.get("YWSM"), ""))
				).toString();
		}
	}

	/**
	 * 获取存放地点变动资产列表
	 * @return
	 * @throws ParseException 
	 */
	public Object cfddbdlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		
		PageList pagelist = new PageList();
		List<Map> list = setZjbListPar(pagelist,map,page_length," and substr(flh,0,4) not in ('0101','0102','0201') and nvl(bdzt,'0') = '0' and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') and ztbz = '" + StateManager.ZCJZ_CW_TG + "' and syr = '" + map.get("userId").toString() + "' ");
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的资产！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多资产！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map datamap : list){
				array.add(new JsonObject(
								new KeysValues("zcbh", Validate.isNullToDefault(datamap.get("YQBH"), "")),
								new KeysValues("zcmc", Validate.isNullToDefault(datamap.get("YQMC"), "")),
								new KeysValues("sydw", Validate.isNullToDefault(datamap.get("SYDWMC"), "")),
								new KeysValues("gg", Validate.isNullToDefault(datamap.get("GG"), "")),
								new KeysValues("xh", Validate.isNullToDefault(datamap.get("XH"), "")),
								new KeysValues("cfdd", Validate.isNullToDefault(datamap.get("BZXXMC"), ""))
						 )
				);
			};
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 提交存放地点变动信息
	 * @return
	 * @throws ParseException 
	 */
	public Object cfddbdtj(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		
		if(phoneDao.cfddbdtj(pd,map)){
			doAddOplogPhone(OplogFlag.SUBMIT,"提交存放地点变动信息",map.get("userId").toString(),map.get("bdbgbh").toString());
			return "{\"success\":\"true\",\"msg\":\"" + map.get("errmsg") + "\"}";
		}
		else{
			return "{\"success\":\"false\",\"msg\":\"" + map.get("errmsg") + "\"}";
		}
	}

	/**
	 * 获取使用人变动资产列表
	 * @return
	 * @throws ParseException 
	 */
	public Object syrbdlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		
		PageList pagelist = new PageList();
		List<Map> list = setZjbListPar(pagelist,map,page_length," and nvl(bdzt,'0') = '0' and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') and ztbz = '" + StateManager.ZCJZ_CW_TG + "' and syr = '" + map.get("userId").toString() + "' ");
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的资产！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多资产！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map datamap : list){
				array.add(new JsonObject(
								new KeysValues("zcbh", Validate.isNullToDefault(datamap.get("YQBH"), "")),
								new KeysValues("zcmc", Validate.isNullToDefault(datamap.get("YQMC"), "")),
								new KeysValues("sydw", Validate.isNullToDefault(datamap.get("SYDWMC"), "")),
								new KeysValues("gg", Validate.isNullToDefault(datamap.get("GG"), "")),
								new KeysValues("xh", Validate.isNullToDefault(datamap.get("XH"), "")),
								new KeysValues("cfdd", Validate.isNullToDefault(datamap.get("BZXXMC"), ""))
						 )
				);
			};
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}

	/**
	* 提交使用人变动信息
	* @return
	*/
	public String syrbdtj(PageData pd){
	  Map map = gson.fromJson(pd.getString("key"), Map.class);
	  if(phoneDao.syrbdtj(pd,map)){
		  pd.put("mkbh", MenuFlag.ZCBD_SYRBD);
		  if(doShenHe(pd,map.get("userId").toString(),"1")){
			  doAddOplogPhone(OplogFlag.SUBMIT,"提交使用人变动信息",map.get("userId").toString(),pd.getString("keyid"));
		  }
		  else{
			  phoneDao.delsyrbd(pd.getString("keyid"));
		  }
		  return pd.getString("msg");
	  }
	  else{
		  return "{\"success\":\"false\",\"msg\":\"" + Validate.isNullToDefault(map.get("errmsg"), "提交使用人变动信息失败！") + "\"}";
	  }
	}

	/**
	 * 获取资产认领（领用人确认）信息
	 * @return
	 */
	public Object zcrllist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());

		PageList pagelist = new PageList();
		pagelist.setSqlText("bdbgbh,dwmc,djbz,djbzmc,bdrxm,bdrq,bdyy");
		pagelist.setTableName("(select b.bdbgbh,b.dwmc,b.djbz," + StateManager.getBdxm("b.djbz") + " djbzmc,b.bdrxm,to_char(b.bdrq,'yyyy-mm-dd') bdrq,b.bdyy from (select dbh,tjr,tjrxm from zc_sys_shcshb where rybh = '" + map.get("userId") + "' and mkbh = '" + MenuFlag.ZCBD_ZCDB_SYRQR + "' and sfdqjd = '1') h inner join zc_bdbgb b on h.dbh = b.bdbgbh) k");
		String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
		if(Validate.noNull(keyword)){
			keyword = keyword.replace("'", "");
			pagelist.setStrWhere(" and (bdbgbh like '%" + keyword + "%' or dwmc like '%" + keyword + "%') ");
		}
		pagelist.setOrderBy("order by bdbgbh");
		
		List<Map<String, Object>> list = getPageList(pagelist,map,page_length);
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多待认领资产！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map datamap : list){
				array.add(new JsonObject(
								new KeysValues("bddh", Validate.isNullToDefault(datamap.get("BDBGBH"), "")),
								new KeysValues("bbdw", Validate.isNullToDefault(datamap.get("DWMC"), "")),
								new KeysValues("bdxm", Validate.isNullToDefault(datamap.get("DJBZMC"), "")),
								new KeysValues("bdr", Validate.isNullToDefault(datamap.get("BDRXM"), "")),
								new KeysValues("bdrq", Validate.isNullToDefault(datamap.get("BDRQ"), "")),
								new KeysValues("bdyy", Validate.isNullToDefault(datamap.get("BDYY"), ""))
						 )
				);
			};
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 获取资产认领（领用人确认）详情
	 * @return
	 */
	public Object zcrlxq(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		
		Map datamap = phoneDao.zcrlxq(map.get("bddh").toString());
		if(datamap.isEmpty()){
			return "{\"success\":\"false\",\"msg\":\"没有查询到信息！\"}";
		}
		else{
			List<Map> list = (List)datamap.get("list");
			JsonArray array = new JsonArray();
			for(Map bdmap : list){
				array.add(new JsonObject(
								new KeysValues("zcbh", Validate.isNullToDefault(bdmap.get("FJBH"), "")),
								new KeysValues("zcmc", Validate.isNullToDefault(bdmap.get("YQMC"), "")),
								new KeysValues("zj", Validate.isNullToDefault(bdmap.get("ZZJ"), "")),
								new KeysValues("cfdd", Validate.isNullToDefault(bdmap.get("BZXXMC"), "")),
								new KeysValues("gg", Validate.isNullToDefault(bdmap.get("FJGG"), "")),
								new KeysValues("xh", Validate.isNullToDefault(bdmap.get("FJXH"), ""))
						 )
				);
			};
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("bddbh",datamap.get("BDBGBH")),
					new KeysValues("bdhsyr",Validate.isNullToDefault(datamap.get("HRYBHMC"),"")),
					new KeysValues("bdhsydw",Validate.isNullToDefault(datamap.get("HDWBHMC"),"")),
					new KeysValues("bdhcfdd",Validate.isNullToDefault(datamap.get("HCFDDMC"),"")),
					new KeysValues("bdyy",Validate.isNullToDefault(datamap.get("BDYY"),"")),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 资产认领（领用人确认）提交
	 * @return
	 */
	public Object zcrlyjtj(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		
		String shyj = Validate.isNullToDefaultString("qryj", "");
		pd.put("keyid", "{\"dbh\":\"" + map.get("bddbh").toString() + "\",\"shyj\":\"" + shyj + "\"}");//审核的时候用到了
	    pd.put("shyj", shyj);
		pd.put("mkbh", MenuFlag.ZCBD_ZCDB_SYRQR);
		if("0".equals(map.get("yj"))){
			if(Validate.isNull(shyj)){
				pd.put("msg","{\"success\":\"false\",\"msg\":\"请录入意见！\"}");
			}
			else{
				doShenHe(pd, map.get("userId").toString(), "3");
			}
			doAddOplogPhone(OplogFlag.BACK,"资产认领退回",map.get("userId").toString(),pd.getString("keyid"));
		}
		else{
			doShenHe(pd, map.get("userId").toString(), "2");
			doAddOplogPhone(OplogFlag.CHECK,"资产认领通过",map.get("userId").toString(),pd.getString("keyid"));
		}
		return pd.get("msg");
	}

	/**
	 * 资产处置申请列表
	 * @return
	 */
	public Object czsqlist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());
		
		PageList pagelist = new PageList();
		List<Map> list = setZjbListPar(pagelist,map,page_length," and nvl(bdzt,'0') = '0' and xz not in (select dm from gx_sys_dmb d where zl = '" + Constant.HXZ + "') and ztbz = '" + StateManager.ZCJZ_CW_TG + "' and syr = '" + map.get("userId").toString() + "' ");
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的资产！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多资产！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map zcmap : list){
				array.add(new JsonObject(
						new KeysValues("zcbh", Validate.isNullToDefault(zcmap.get("YQBH"), "")),
						new KeysValues("zcmc", Validate.isNullToDefault(zcmap.get("YQMC"), "")),
						new KeysValues("sydw", Validate.isNullToDefault(zcmap.get("SYDWMC"), "")),
						new KeysValues("cfdd", Validate.isNullToDefault(zcmap.get("BZXXMC"), "")),
						new KeysValues("gzrq",Validate.isNullToDefault(zcmap.get("GZRQ"), "")),
						new KeysValues("gg",Validate.isNullToDefault(zcmap.get("GG"), "")),
						new KeysValues("xh",Validate.isNullToDefault(zcmap.get("XH"), ""))
						)
				);
			};
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	/**
	 * 提交处置申请
	 * @return
	 * @throws ParseException 
	 */
	public Object scczsqd(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		if(phoneDao.scczsqd(pd,map)){
			pd.put("mkbh", MenuFlag.ZCCZ_CZSQ);
			if(doShenHe(pd,map.get("userId").toString(),"1")){
				doAddOplogPhone(OplogFlag.SUBMIT,"提交处置申请",map.get("userId").toString(),pd.getString("keyid"));
			}
			else{
				phoneDao.delczsq(pd.getString("keyid"));
			}
			return pd.getString("msg");
	    }
	    else{
	    	return "{\"success\":\"false\",\"msg\":\"" + Validate.isNullToDefault(map.get("errmsg"), "提交处置申请信息失败！") + "\"}";
	    }
	}

	/**
	 * 获取闲置领用申请的资产列表
	 * @return
	 */
	public Object xzzclylist(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		int index = (int)Float.parseFloat(map.get("index").toString());

		PageList pagelist = new PageList();
		pagelist.setSqlText("yqbh,yqmc,sydwmc,syrmc,sqmxbh,bzxxmc,gg,xh");
		pagelist.setTableName("(select g.*," + ToSqlUtil.getSqlByZjbXh("z", "") + " xh," + ToSqlUtil.getSqlByZjbGg() + " gg,(case when substr(z.flh,1,4) in ('0101','0102','0201') then '' else (select to_char(mc) from zc_sys_ddb d where d.ddbh = z.bzxx) end) bzxxmc from (select yqbh,yqmc,syr,(select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = g.syr) syrmc,sydw,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = g.sydw) sydwmc,syfx,(select mc from gx_sys_dmb d where zl = '' and dm = g.syfx) syfxmc,dj,decode(nvl(zzj,0),0,'0.00',(to_char(round(zzj,2),'fm99999999999990.00'))) zzj,sfgs,gsry,to_char(gsrq,'yyyy-mm-dd') gsrq,crje,sqmxbh,to_char(gzrq,'yyyy-mm-dd') gzrq from zc_xztj_gsb g where sfgs = '0') g left join zc_zjb z on g.yqbh = z.yqbh) k");
		String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
		if(Validate.noNull(keyword)){
			keyword = keyword.replace("'", "");
			pagelist.setStrWhere(" and (yqbh like '%" + keyword + "%' or yqmc like '%" + keyword + "%') ");
		}
		pagelist.setOrderBy("order by yqbh");
		
		List<Map<String, Object>> list = getPageList(pagelist,map,page_length);
		if(list == null || list.size() == 0){
			if(index == 1){
				return "{\"success\":\"false\",\"msg\":\"没有查询到符合条件的信息！\"}";
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"暂无更多闲置资产！\"}";
			}
		}
		else{
			JsonArray array = new JsonArray();
			for(Map datamap : list){
				array.add(new JsonObject(
								new KeysValues("zcbh", Validate.isNullToDefault(datamap.get("YQBH"), "")),
								new KeysValues("zcmc", Validate.isNullToDefault(datamap.get("YQMC"), "")),
								new KeysValues("sydw", Validate.isNullToDefault(datamap.get("SYDWMC"), "")),
								new KeysValues("syr", Validate.isNullToDefault(datamap.get("SYRMC"), "")),
								new KeysValues("gg", Validate.isNullToDefault(datamap.get("GG"), "")),
								new KeysValues("xh", Validate.isNullToDefault(datamap.get("XH"), "")),
								new KeysValues("cfdd", Validate.isNullToDefault(datamap.get("BZXXMC"), "")),
								new KeysValues("sqmxbh", Validate.isNullToDefault(datamap.get("SQMXBH"), ""))
						 )
				);
			};
			
			JsonObject json = new JsonObject(
					new KeysValues("success",true),
					new KeysValues("msg","数据加载成功！"),
					new KeysValues("items",array)
				);
			return json.toString();
		}
	}
	

	/**
	 * 提交设备入账卡片信息
	 * @return
	 */
	public Object sbjzzctj(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String ysdh = map.get("dbh").toString();
		String rybh = map.get("userId").toString();
		List<Map> list = (List)map.get("zclist");
		
		zc_zjb zjb;
		List<zc_zjb> zclist = new ArrayList<zc_zjb>();
		for(Map zjbmap : list){
			String yqbh = Validate.isNullToDefaultString(zjbmap.get("zcbh"), "");
			if(Validate.isNull(yqbh)){
				return "{\"success\":\"false\",\"msg\":\"没有获取到资产编号，提交失败！\"}";
			}
			
			zjb = new zc_zjb();
			zjb.setYsdh(ysdh);
			zjb.setYqbh(yqbh);
//			zjb.setFjs(0.00);
//			zjb.setFjzj(0.00);
//			zjb.setJdr(rybh);
//			zjb.setJsr(rybh);
			String syr = Validate.isNullToDefaultString(zjbmap.get("syr"),"");
			if(Validate.isNull(syr)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有选择使用人，提交失败！\"}";
			}
			else{
				zjb.setSyr(syr);
			}
			String sydw = Validate.isNullToDefaultString(zjbmap.get("sydw"),"");
			if(Validate.isNull(sydw)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有选择使用部门，提交失败！\"}";
			}
			else{
				zjb.setSydw(sydw);
			}
			String syfx = Validate.isNullToDefaultString(zjbmap.get("syfx"),"");
			if(Validate.isNull(syfx)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有选择使用方向，提交失败！\"}";
			}
			else{
				zjb.setSyfx(syfx);
			}
			String cfdd = Validate.isNullToDefaultString(zjbmap.get("cfdd"),"");
			if(Validate.isNull(cfdd)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有选择存放地点，提交失败！\"}";
			}
			else{
				zjb.setBzxx(cfdd);
			}
			String jsh = Validate.isNullToDefaultString(zjbmap.get("jsh"),"");
			if(Validate.isNull(jsh)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有输入机身号，提交失败！\"}";
			}
			else{
				zjb.setJsh(jsh);
			}
			String cch = Validate.isNullToDefaultString(zjbmap.get("cch"),"");
			if(Validate.isNull(cch)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有输入出厂编号，提交失败！\"}";
			}
			else{
				zjb.setCch(cch);
			}
			zclist.add(zjb);
		}
		ZC_ZJBEXTEND ysd = new ZC_ZJBEXTEND();
		ysd.setYsdh(ysdh);
		ysd.setJdr(rybh);
		ysd.setZjbmx(zclist);
		
		if(phoneDao.sbjzzcsave(ysd)){
			if(phoneDao.zcjzbtx(ysdh)){
				pd.put("mkbh", MenuFlag.ZCJZ_LYR);
				pd.put("keyid", ysdh);
				doShenHe(pd,rybh,"1");
				doAddOplogPhone(OplogFlag.SUBMIT,"提交设备类资产建账",ysd.getJdr(),ysd.getYsdh());
				return pd.getString("msg");
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"存在未录入的必填项，不允许提交！\"}";
			}
		}
		else{
			return "{\"success\":\"false\",\"msg\":\"" + ysd.getErrmsg() + "\"}";
		}
	}

	/**
	 * 提交家具入账卡片信息
	 * @return
	 */
	public Object jjjzzctj(PageData pd){
		Map map = gson.fromJson(pd.getString("key"), Map.class);
		String ysdh = map.get("dbh").toString();
		String rybh = map.get("userId").toString();
		List<Map> list = (List)map.get("zclist");
		
		zc_zjb zjb;
		List<zc_zjb> zclist = new ArrayList<zc_zjb>();
		for(Map zjbmap : list){
			String yqbh = Validate.isNullToDefaultString(zjbmap.get("zcbh"), "");
			if(Validate.isNull(yqbh)){
				return "{\"success\":\"false\",\"msg\":\"没有获取到资产编号，提交失败！\"}";
			}
			
			zjb = new zc_zjb();
			zjb.setYsdh(ysdh);
			zjb.setYqbh(yqbh);
//			zjb.setFjs(0.00);
//			zjb.setFjzj(0.00);
//			zjb.setJdr(rybh);
//			zjb.setJsr(rybh);
			String syr = Validate.isNullToDefaultString(zjbmap.get("syr"),"");
			if(Validate.isNull(syr)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有选择使用人，提交失败！\"}";
			}
			else{
				zjb.setSyr(syr);
			}
			String sydw = Validate.isNullToDefaultString(zjbmap.get("sydw"),"");
			if(Validate.isNull(sydw)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有选择使用部门，提交失败！\"}";
			}
			else{
				zjb.setSydw(sydw);
			}
			String syfx = Validate.isNullToDefaultString(zjbmap.get("syfx"),"");
			if(Validate.isNull(syfx)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有选择使用方向，提交失败！\"}";
			}
			else{
				zjb.setSyfx(syfx);
			}
			String cfdd = Validate.isNullToDefaultString(zjbmap.get("cfdd"),"");
			if(Validate.isNull(cfdd)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有选择存放地点，提交失败！\"}";
			}
			else{
				zjb.setBzxx(cfdd);
			}
			String sykh = Validate.isNullToDefaultString(zjbmap.get("sykh"),"");
			if(Validate.isNull(sykh)){
				return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "没有输入套（件）数，提交失败！\"}";
			}
			else{
				int tjs = 0;
				try{
					tjs = Integer.parseInt(sykh);
					if(tjs <= 0){
						return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "输入的套（件）数必须是大于0的整数，提交失败！\"}";
					}
				}
				catch(Exception e){
					return "{\"success\":\"false\",\"msg\":\"资产" + yqbh + "输入的套（件）数必须是大于0的整数，提交失败！\"}";
				}
				zjb.setSykh(sykh);
			}
			zclist.add(zjb);
		}
		ZC_ZJBEXTEND ysd = new ZC_ZJBEXTEND();
		ysd.setYsdh(ysdh);
		ysd.setJdr(rybh);
		ysd.setZjbmx(zclist);
		
		if(phoneDao.jjjzzcsave(ysd)){
			if(phoneDao.zcjzbtx(ysdh)){
				pd.put("mkbh", MenuFlag.ZCJZ_LYR);
				pd.put("keyid", ysdh);
				doShenHe(pd,rybh,"1");
				doAddOplogPhone(OplogFlag.SUBMIT,"提交家具类资产建账",ysd.getJdr(),ysd.getYsdh());
				return pd.getString("msg");
			}
			else{
				return "{\"success\":\"false\",\"msg\":\"存在未录入的必填项，不允许提交！\"}";
			}
		}
		else{
			return "{\"success\":\"false\",\"msg\":\"" + ysd.getErrmsg() + "\"}";
		}
	}
  
  
  
  
  
//***********************************************************************公用方法汇总  start********************************************************************************
  /**
   * 根据条件获取主基表中的信息
   * @param pagelist
   * @param map
   * @param where
   */
	private List setZjbListPar(PageList pagelist,Map map,int len,String where){
	    String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
	    if(Validate.noNull(keyword)){
	    	keyword = keyword.replace("'", "");
	    	where += " and (yqbh like '%" + keyword + "%' or yqmc like '%" + keyword + "%') ";
	    	pagelist.setHjWhere(" and (yqbh like '%" + keyword + "%' or yqmc like '%" + keyword + "%') ");
	    }
	    
	    pagelist.setSqlText("yqbh,yqmc,flh,flmc,syr,syrmc,sydw,sydwmc,bzxx,bzxxmc,decode(nvl(zzj,0),0,'0.00',(to_char(round(zzj,2),'fm99999999999990.00'))) zzj,decode(nvl(dj,0),0,'0.00',(to_char(round(dj,2),'fm99999999999990.00'))) dj,to_char(rzrq,'yyyy-mm-dd') rzrq,to_char(gzrq,'yyyy-mm-dd') gzrq,nvl(sykh,1) sykh,ysdh,gg,xh");
	    pagelist.setTableName("(select yqbh,yqmc,flh,flmc,gbmid,flgbm,cz6,cz10,gzrq,dj,zzj,rzrq," + ToSqlUtil.getSqlByZjbXh() + " xh," + ToSqlUtil.getSqlByZjbGg() + " gg," + ToSqlUtil.getSqlByZjbSccj() + " sccj," + ToSqlUtil.getSqlByZjbXss() + " xss,syr,(select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = z.syr) syrmc,sydw,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = z.sydw) sydwmc,bzxx,(select '('||ddh||')'||mc from zc_sys_ddb d where d.ddbh = z.bzxx) bzxxmc,syfx,xz,ztbz,sykh,nvl(bdzt,'0') bdzt,ysdh from zc_zjb z) z");
	    pagelist.setStrWhere(where);
	    pagelist.setOrderBy("order by yqbh");
	    
	    return getPageList(pagelist,map,len);
	}
  
  /**
   * 获取人员信息
   * @param lx  1：获取管理权限下的人员 2：获取当前登录人所在单位及下级单位中的人员 3：获取所在单位下的人员 0或其他都是不限制范围
   */
  public JsonArray getRyList(Map map,String lx){
  	String rybh = map.get("userId").toString();
  	String where = "";
  	if("1".equals(lx)){//管理权限下的单位中的人员
  		where = pageService.getQxsql(rybh, "dwbh", "D");
  	}
  	else if("2".equals(lx)){//所在单位及下级单位中的人员
  		where = " and dwbh in (select dwbh from gx_sys_dwb d start with dwbh = (select dwbh from gx_sys_ryb r where r.rybh = '" + rybh + "') and dwzt = '1' connect by prior dwbh = sjdw) ";
  	}
  	else if("3".equals(lx)){//所在单位的人员
  		where = " and dwbh = (select dwbh from gx_sys_ryb r where rybh = '" + rybh + "') ";
  	}
  	
  	PageList pagelist = new PageList();
  	List<Map> list = setRybListPar(pagelist,map,where);
  	JsonArray array = new JsonArray();
  	if(list == null || list.size() == 0){
  		
  	}
  	else{
  		for(Map datamap : list){
  			array.add(new JsonObject(
  							new KeysValues("dm", Validate.isNullToDefault(datamap.get("RYBH"), "")),
  							new KeysValues("mc", Validate.isNullToDefault(datamap.get("RYXM"), "")),
  							new KeysValues("szdw", Validate.isNullToDefault(datamap.get("DWMC"), ""))
  					 )
  			);
  		};
  	}
  	return array;
  }
  /**
   * 根据条件获取人员表中的信息
   * @param pagelist
   * @param map
   * @param where
   */
  private List setRybListPar(PageList pagelist,Map map,String where){
    String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
    if(Validate.noNull(keyword)){
    	keyword = keyword.replace("'", "");
    	where += " and ryxm like '%" + keyword + "%' ";
    }
    
    pagelist.setSqlText("rybh,rygh,xm,ryxm,dwbh,dwmc,xb,xbmc");
    pagelist.setTableName("(select rybh,rygh,xm,'('||rygh||')'||xm ryxm,dwbh,(select mc from gx_sys_dwb d where d.dwbh = r.dwbh) dwmc,xb,(select mc from gx_sys_dmb d where d.zl = '" + Constant.SEX + "' and d.dm = r.xb) xbmc from gx_sys_ryb r where ryzt = '1' and rybh <> '000000') r");
    pagelist.setStrWhere(where);
    pagelist.setOrderBy("order by rygh");
    
    return getPageList(pagelist,map,page_length);
  }

/**
 * 获取单位信息
 * @param map
 * @param lx  是否限制管理权限，1：获取管理权限下的单位，其他都是不限制
 * @param mj  是否限制必须是末级，1：根据系统设置，2：必须是末级，其他都是不限制
 */
public JsonArray getDwList(Map map,String lx,String mj){
	//0：所有单位 1：管理权限下的单位
	String where = "";
	if("1".equals(lx)){//管理权限下的单位
		where = pageService.getQxsql(map.get("userId").toString(), "dwbh", "D");
	}
	
	if("2".equals(mj)){
		where += " and (select count(*) from gx_sys_dwb where sjdw = d.dwbh) = 0 ";
	}
	else if("1".equals(mj)){
		String sfmj = pageService.getSingleValue("select sftjzmj from zc_sys_xtb where rownum = 1");
		//因为手机端没有用到房屋、土地的设置，所以这里暂时不处理这一种情况，如果以后用到了，需要修改这里的代码，切记
		if("1".equals(sfmj)){
			where += " and (select count(*) from gx_sys_dwb where sjdw = d.dwbh) = 0 ";
		}
	}
	
	PageList pagelist = new PageList();
	List<Map> list = setDwbListPar(pagelist,map,where);
	JsonArray array = new JsonArray();
	if(list == null || list.size() == 0){
		
	}
	else{
		for(Map datamap : list){
			array.add(new JsonObject(
							new KeysValues("dwbh", Validate.isNullToDefault(datamap.get("DWBH"), "")),
							new KeysValues("dwmc", Validate.isNullToDefault(datamap.get("DWMC"), "")),
							new KeysValues("sjdw", Validate.isNullToDefault(datamap.get("SJDWMC"), ""))
					 )
			);
		};
	}
	return array;
}
  /**
   * 根据条件获取单位表中的信息
   * @param pagelist
   * @param map
   * @param where
   */
private List setDwbListPar(PageList pagelist,Map map,String where){
    String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
    if(Validate.noNull(keyword)){
      keyword = keyword.replace("'", "");
      where += " and dwmc like '%" + keyword + "%' ";
    }
    
    pagelist.setSqlText("dwbh,mc,dwmc,bmh,dwxz,dwxzmc,dwld,dwldmc,fgld,fgldmc,sjdw,sjdwmc");
    pagelist.setTableName("(select dwbh,mc,bmh,'('||bmh||')'||mc dwmc,dwxz,(select mc from gx_sys_dmb m where m.zl = '" + Constant.DWXZ + "' and m.dm = d.dwxz) dwxzmc,dwld,(select '('||rygh||')'||xm from gx_sys_ryb r where d.dwld = r.rybh) dwldmc,fgld,(select '('||rygh||')'||xm from gx_sys_ryb r where d.fgld = r.rybh) fgldmc,sjdw,(select '('||bmh||')'||mc from gx_sys_dwb w where w.dwbh = d.sjdw) sjdwmc from gx_sys_dwb d where dwzt = '1') d");
    pagelist.setStrWhere(where);
    pagelist.setOrderBy("order by bmh");
    
    return getPageList(pagelist,map,page_length);
  }

/**
 * 获取教育部十六大类信息
 * @param map
 * @param lx  sb：只获取设备类分类 jj：只获取家具类分类
 * @param qx  是否限制管理权限，1：控制管理权限，其他都是不限制
 * @param mj  是否限制必须是末级，1：根据系统设置，2：必须是末级，其他都是不限制
 */
public JsonArray getFlList(Map map,String lx,String qx,String mj){
	String where = ("1".equals(qx) ? pageService.getQxsql(map.get("userId").toString(), "flh", "F") : "");
	if("sb".equals(lx)){//管理权限下的单位
		where += " and (substr(flh,1,2) in ('03','04','05','06','07','08','12','14') and substr(flh,1,4) not in ('0413','0571','0711')) ";
	}
	else if("jj".equals(lx)){
		where += " and substr(flh,1,2) = '13' ";
	}
	
	if("2".equals(mj)){
		where += " and nvl(sfmj,'0') = '1' ";
	}
	else if("3".equals(mj)){
		String sfmj = pageService.getSingleValue("select sfmj from zc_sys_xtb where rownum = 1");
		if("1".equals(sfmj)){
			where += " and nvl(sfmj,'0') = '1' ";
		}
	}
	
	PageList pagelist = new PageList();
	List<Map> list = setFlbListPar(pagelist,map,"zc_flb_jyb",where);
	JsonArray array = new JsonArray();
	if(list == null || list.size() == 0){
		
	}
	else{
		for(Map datamap : list){
			array.add(new JsonObject(
						new KeysValues("bzdm", Validate.isNullToDefault(datamap.get("BZDM"), "")),
						new KeysValues("flh", Validate.isNullToDefault(datamap.get("FLH"), "")),
						new KeysValues("flmc", Validate.isNullToDefault(datamap.get("FLMC"), "")),
						new KeysValues("czfl", Validate.isNullToDefault(datamap.get("FFLDM"), "")),
						new KeysValues("czflmc", "(" + Validate.isNullToDefault(datamap.get("FFLDM"), "") + ")" + Validate.isNullToDefault(datamap.get("FFLMC"), ""))
				 )
			);
		};
	}
	return array;
}
/**
 * 获取财政分类
 * @param map
 * @return
 */
public JsonArray getCzflList(Map map){
	PageList pagelist = new PageList();
	List<Map> list = setFlbListPar(pagelist,map,"zc_flb_czbn","");
	JsonArray array = new JsonArray();
	if(list == null || list.size() == 0){
		
	}
	else{
		for(Map datamap : list){
			array.add(new JsonObject(
							new KeysValues("bzdm", Validate.isNullToDefault(datamap.get("BZDM"), "")),
							new KeysValues("flh", Validate.isNullToDefault(datamap.get("FLH"), "")),
							new KeysValues("flmc", "(" + Validate.isNullToDefault(datamap.get("FLH"), "") + ")" + Validate.isNullToDefault(datamap.get("FLMC"), ""))
					 )
			);
		};
	}
	return array;
}
  /**
   * 根据条件获取分类表中的信息
   * @param pagelist
   * @param map
   * @param tname  表名，可以获取教育部分类，也可以获取财政分类
   * @param where
   */
private List setFlbListPar(PageList pagelist,Map map,String tname,String where){
    String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
    if(Validate.noNull(keyword)){
      keyword = keyword.replace("'", "");
      where += " and (flh like '%" + keyword + "%' or flmc like '%" + keyword + "%') ";
    }
    
    pagelist.setSqlText("bzdm,flh,flmc,ffldm,fflmc");
    pagelist.setTableName(tname + " f");
    pagelist.setStrWhere(where);
    pagelist.setOrderBy("order by bzdm");
    
    return getPageList(pagelist,map,page_length);
  }

  /**
   * 获取存放地点信息
   * @param map
   */
  public JsonArray getCfddList(Map map){
  	PageList pagelist = new PageList();
  	List<Map> list = setCfddListPar(pagelist,map,"");
  	JsonArray array = new JsonArray();
  	if(list == null || list.size() == 0){
  		
  	}
  	else{
  		for(Map datamap : list){
  			array.add(new JsonObject(
						new KeysValues("dm", Validate.isNullToDefault(datamap.get("DDBH"), "")),
						new KeysValues("mc", Validate.isNullToDefault(datamap.get("DDMC"), "")),
						new KeysValues("sjddmc", Validate.isNullToDefault(datamap.get("SJDDMC"), ""))
				 )
  			);
  		};
  	}
  	return array;
  }
  /**
   * 获取存放地点列表
   * @return
   * @throws ParseException 
   */
  private List setCfddListPar(PageList pagelist,Map map,String where){
    String keyword = Validate.isNullToDefaultString(map.get("keyword"),"");
    if(Validate.noNull(keyword)){
      keyword = keyword.replace("'", "");
      where += " and ddmc like '%" + keyword + "%' ";
    }
    
    pagelist.setSqlText("ddbh,ddh,ddmc,mc");
    pagelist.setTableName("(select ddbh,ddh,mc,'('||ddh||')'||mc ddmc,(select '('||s.ddh||')'||s.mc from zc_sys_ddb s where s.ddbh = d.sjdd) sjddmc from zc_sys_ddb d where nvl(ddzt,'1') = '1') d");
    pagelist.setStrWhere(where);
    pagelist.setOrderBy("order by ddh");
    
    return getPageList(pagelist,map,page_length);
  }
  
  /**
   * 获取列表信息
   * @param pagelist
   * @param map
   * @return
   */
  private List getPageList(PageList pagelist,Map map,int len){
    int index = (int)Float.parseFloat(map.get("index").toString());
    
    pagelist.setPage_record(String.valueOf(index*len));
    pagelist.setCurPage(String.valueOf((index - 1)*len));
    pagelist.setPage_length(String.valueOf(len));
    
    return pageService.getPageList(pagelist);
  }

  /**
   * 获取字典表中的信息
   * @param map
   * @return
   */
  public JsonArray getDictList(String zl){
	List<Map<String, Object>> list = dictDao.getDict(zl);
	JsonArray array = new JsonArray();
	if(list == null || list.size() == 0){
			
	}
	else{
		for(Map datamap : list){
			array.add(new JsonObject(
						new KeysValues("dm", Validate.isNullToDefault(datamap.get("DM"), "")),
						new KeysValues("mc", Validate.isNullToDefault(datamap.get("MC"), ""))
				)
			);
		};
	}
	return array;
  }
  
  /**
   * 手机端审核相关公共方法
   * @param pd
   * @param rybh
   * @param flag  1：提交 2：审核通过 3：审核退回 4：撤销 0或其他都是审核保存
   * @return
   */
	public boolean doShenHe(PageData pd,String rybh,String flag){
		pd.put("sjdjk","sjdjk");
	    pd.put("rybh",rybh);
	    pd.put("xm",phoneDao.RyxxByRybh(rybh).get("RYXM"));
	    
	    if("1".equals(flag)){
	    	return shenheDao.doSubmit(pd);
	    }
	    else if("2".equals(flag)){
	    	return shenheDao.doCheck(pd);
	    }
	    else if("3".equals(flag)){
	    	return shenheDao.doBack(pd);
	    }
	    else if("4".equals(flag)){
	    	return shenheDao.doRevoke(pd);
	    }
	    else{
	    	Map map = gson.fromJson(pd.getString("key"), Map.class);
	    	map.put("mkbh", map.get("ywsplx"));
	    	if(shenheDao.doSavePhone(map)){
	    		pd.put("msg", "{\"success\":\"true\",\"msg\":\"保存成功！\"}");
		        return true;
	    	}
	    	else{
		        pd.put("msg", "{\"success\":\"false\",\"msg\":\"保存失败！\"}");
		        return false;
	    	}
	    }
	}
	
	/**
	 * 通用生成二维码
	 * @return
	 */
	public String createQRCode(String QRCodeContent){
		String filename = UuidUtil.get32UUID();
		String wjlj = PropertiesUtil.getGlobalValueByKey("FileDataPhysicalPath");
		String lswjj = PropertiesUtil.getGlobalValueByKey("FileDataTempFlieName");
		String QRCodeUrl = wjlj + "/" + lswjj + "/" + filename + ".png";
		
		try{
			TwoDimensionCode.encoderQRCode(QRCodeContent, QRCodeUrl, "png");
			return PropertiesUtil.getGlobalValueByKey("FileDataVirturalPath") + "/" + lswjj + "/" + filename + ".png";
		}
		catch(Exception e){
			return "";
		}
	}
//***********************************************************************公用方法汇总  end********************************************************************************

	/*****************************扫描二维码上传图片 start***********************************/
	/**
	 * 生成扫描二维码上传图片的二维码
	 * @return
	 */
	public Object ImgUploadCreateQRCode(String QRCodeContent){
		JsonObject json;

		String QRCodeUrl = createQRCode(QRCodeContent);
		
		if(Validate.isNull(QRCodeUrl)){
			json = new JsonObject(
		        new KeysValues("success",false),
		        new KeysValues("msg","生成二维码失败！")
			);
		}
		else{
			json = new JsonObject(
		        new KeysValues("success",true),
		        new KeysValues("QRCodeUrl",QRCodeUrl),
		        new KeysValues("QRCodeTime",PropertiesUtil.getGlobalValueByKey("QRCodeTime")),
		        new KeysValues("msg","生成二维码成功！")
			);
		}
		return json.toString();
	}
	
	/**
	 * 扫码上传图片
	 * @param dwb
	 * @return
	 */
	public boolean doUploadSmsc(String guid,String djbh,String djlx,String filename,String path,String rybh){
		ZC_XGWD fj = new ZC_XGWD();
		fj.setRybh(rybh);
		fj.setFilename(filename);
		fj.setDbh(djbh);
		fj.setDjlx(djlx);
		fj.setPath(path);
		fj.setGuid(guid);
		if(phoneDao.doUploadSmsc(fj)){
			doAddOplogPhone(OplogFlag.ADD,"扫码上传图片",rybh,djbh);
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * 删除附件
	 */
	public boolean doDelSmsc(String tpid,String rybh){
		if(phoneDao.doDelSmsc(tpid)){
			doAddOplogPhone(OplogFlag.DEL,"删除扫码上传的图片",rybh,tpid);
			return true;
		}
		else{
			return false;
		}
	}
	/*****************************扫描二维码上传图片 end***********************************/
	
	/**
	 * 根据消费地点查询承包商的guid
	 * @param guid
	 * @return
	 */
	public String getCbsGuidByXfddGuid(String guid){
		return phoneDao.getCbsGuidByXfddGuid(guid);
	}
}