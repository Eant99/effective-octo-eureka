package com.googosoft.service.kjhs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.LUser;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.fzgn.tzgg.TxlDao;
import com.googosoft.dao.kjhs.KjkmszDao;
import com.googosoft.dao.kjhs.KmszDao;
import com.googosoft.pojo.kjhs.KMSZ;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("kjkmszService")
public class KjkmszService extends BaseService{
	@Resource(name="kjkmszDao")
	public KjkmszDao Dao;
	
	
	
	/**
	 * 获取会计科目设置树
	 */
	public Object getKmszNode(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"1".equals(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = Dao.kmszMenu(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                count=Integer.parseInt(map.get("COUNT")+"");
                if(count<=0) {
    	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target,icon));
                }else {
    				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
                }
                }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	/**
	 * 获取会计科目设置树
	 */
	public Object getKmszNodezj(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String icon1 = rootPath+"/static/plugins/ext/resources/images/default/s.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"1".equals(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list=null;
		
		if(jb.length()>2) {
			
			list=Dao.kmszMenuzj1(jb);
//			String datas = SendHttpUtil.sendPost(SystemSet.address+"/commonTree/kjknsztree/Menuzj1", "jb="+jb);
//			Gson gson = new Gson();  
//		   list = gson.fromJson(datas, new TypeToken<List>(){}.getType());  

		}else {
			 list = Dao.kmszMenuzj(jb);
//			String datas = SendHttpUtil.sendPost(SystemSet.address+"/commonTree/kjknsztree/Menuzj", "jb="+jb);
//			Gson gson = new Gson();  
//		   list = gson.fromJson(datas, new TypeToken<List>(){}.getType());  
		}
	//	List list = Dao.kmszMenuzj(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			String isleaf="";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                isleaf=Validate.isNullToDefault(map.get("ISLEAF"), "").toString();           
                if(isleaf.equals("1")) {
    	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target));
                }else {   
    				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
                }
              
               }
			  		
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	/**
	 * 获取单条科目信息
	 * 
	 */
	public Map getkmszObjectById(String id) {
		return Dao.getkmszObjectById(id);
	}
	/**
	 * 根据主键获取列表详细信息 操作(修改)
	 * @param dm
	 * @return
	 */
	public Map getObjectById(String dmxh) {
		return Dao.getObjectById(dmxh);
	}
	public Map getinfoById(String dmxh) {
		return Dao.getinfoById(dmxh);
	}
	/**
	 * 获取经济科目设置树
	 */
	public Object getjjkmNode(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"1".equals(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = Dao.jjszMenu(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
               mc = map.get("MC").toString();
               jb = map.get("JB").toString();
               count=Integer.parseInt(map.get("COUNT")+"");
               if(count<=0) {
   	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target,icon));
               }else {
   				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
               }
               }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	/**
	 * 获取经济科目设置树
	 */
	public Object getjjkmNodezj(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"1".equals(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&kmbh=";
		} else {
			href = href + "?kmbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = Dao.jjszMenuzj(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String kmbh = "",kmmc = "",kmjc="",l="",k="";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				kmbh = Validate.isNullToDefault(map.get("KMBH"), "").toString();
				kmmc = map.get("KMMC").toString();
                kmjc = map.get("KMJC").toString();
                count= Dao.getCount(kmbh);
                l = map.get("L").toString();
                k = map.get("K").toString();
               if(count>0) {
          	      children.add(new ExtTreeNode(kmbh, kmmc, false , true, false, href.length() > 0 ? href + kmbh +"&kmjc="+kmjc+"&l="+l+"&k="+k: href, target,icon));

               }else {
   					children.add(new ExtTreeNode(kmbh, kmmc, true , true, false, href.length() > 0 ? href + kmbh + "&kmjc="+kmjc+"&l="+l+"&k="+k: href, target));
               }
            }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	//
	public Object getjjkmNodezjff(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = Dao.jjszMenuzj(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
               mc = map.get("MC").toString();
               jb = map.get("JB").toString();
               count=Integer.parseInt(map.get("COUNT")+"");
               if(count<=0) {
            	   if(jb.length()==3) {
          	            children.add(new ExtTreeNode(dm, mc, true , true, false,"", target));

            	   }else {
          	            children.add(new ExtTreeNode(dm, mc, false , true, false,"", target));

            	   }
               }else {
   					children.add(new ExtTreeNode(dm, mc, false , true, false,"", target,icon));
               }
               }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	/**
	 * 获取功能科目设置树
	 */
	public Object getgnkmNode(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"1".equals(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = Dao.gnszMenu(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
               mc = map.get("MC").toString();
               jb = map.get("JB").toString();
               count=Integer.parseInt(map.get("COUNT")+"");
               if(count<=0) {
   	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target,icon));
               }else {
   				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
               }
               }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	/**
	 * 获取功能科目设置树
	 */
	public Object getgnkmNodezj(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"1".equals(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list=Dao.gnszMenuzj(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                count=Integer.parseInt(map.get("COUNT")+"");
                if(count<=0) {
            	   if(dm.length()==7) {
          	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target));
                    	   }else {
          	            children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm : href, target,icon));
            	   }
               }else {
   				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
               }
               }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();	
	}

	public boolean doCheckZjf(String zjf) {
		boolean result = Dao.doCheckZjf(zjf);
		return result;
	}
	//修改时的判断  功能科目设置
	public boolean doCheckZjf2(String zjf,String guid) {
		boolean result = Dao.doCheckZjf2(zjf,guid);
		return result;
	}
	//科目余额初始 判断zjf是否重复
	public boolean doCheckkjZjf2(String zjf,String kjzd) {
		boolean result = Dao.doCheckkjZjf2(zjf,kjzd);
		return result;
	}
	//科目余额初始 判断zjf是否重复  修改
	public boolean doCheckZjf3(String zjf,String dwbh,String kjzd) {
		boolean result = Dao.doCheckZjf3(zjf,dwbh,kjzd);
		return result;
	}
	public boolean doCheckmmc(String kmmc) {
		boolean result = Dao.doCheckKmmc(kmmc);
		return result;
	}
	public boolean doCheckmmc1(String kmmc,String kjzd) {
		boolean result = Dao.doCheckKmmc1(kmmc,kjzd);
		return result;
	}
	public boolean doCheckmmc2(String kmmc,String kjzd,String guid) {
		boolean result = Dao.doCheckKmmc2(kmmc,kjzd,guid);
		return result;
	}
	//修改时的判断  功能科目设置
	public boolean doCheckmmc2(String kmmc,String guid) {
		boolean result = Dao.doCheckKmmc2(kmmc,guid);
		return result;
	}
	public boolean doCheckKmbh(String kmbh) {
		boolean result = Dao.doCheckXmbh(kmbh);
		return result;
	}
	//修改时的判断  功能科目设置
	public boolean doCheckKmbh2(String kmbh,String guid) {
		boolean result = Dao.doCheckXmbh2(kmbh,guid);
		return result;
	}
	public Map<String, Object> getObjectByIdByKmsz(String guid) {
		
		return Dao.getObjectByIdByKmsz(guid);
	}
	public boolean doUpdate(PageData pd,String dwbh,String sszt){
		return Dao.doUpdate(pd,dwbh,sszt);
	}
	/**
	 * 
	 * @param pd
	 * @param dm
	 * @param session
	 * @return
	 */
	public int doUpdategnkm(PageData pd,String dwbh){
		return Dao.doUpdategnkm(pd,dwbh);
	}
	public boolean doAdd(PageData pd,String dm,HttpSession session){
		return Dao.doAdd(pd,dm,session);
	}
	public int doAddgnkmsz(PageData pd,String dm,HttpSession session){
		return Dao.doAddgnkmsz(pd,dm,session);
	}
	public boolean doAddxj(PageData pd,HttpSession session){
		return Dao.doAddxj(pd,session);
	}
	public int doAddxjkmsz(PageData pd,HttpSession session){
		return Dao.doAddxjkmsz(pd,session);
	}
	public boolean doDelete(String dwbh,String sjfl,String kmbh,String kmmc,String kjzd,String mkbh,String sszt) {
		
		
		return  Dao.doDelete(dwbh,sjfl,kmbh,kmmc,kjzd,mkbh,sszt);
	}
	public int doDeletekmsz(String dwbh) {
		int result = Dao.doDeletekmsz(dwbh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"单位基础信息",dwbh);
		}
		return result;
	}
	public int getCountByKmbh(String kmbh) {
		
		return Dao.getCountByKmbh(kmbh);
	}
	public int getCountByGuid(String guid) {
		
		return Dao.getCountByGuid(guid);
	}
	public boolean doCheck(String kmguid,String type) {
		return Dao.doCheck(kmguid,type);
	}
	/**
	 * 得到科目余额
	 */
	public String doCheckye(String guid,String kmnd,String sszt,String kjzd) {
		return Dao.doCheckye(guid,kmnd,sszt,kjzd);
	}
	/**
	 * 得到变动金额
	 */
	public String getbdje(String sszt,String kmbh) {
		return Dao.getbdje(sszt,kmbh);
	}
	public List getzd() {
		return Dao.getzd();
	}
	/**
	 * 生成编号
	 */
	public String scbh(String kmbh,String kjzd) {
		
		return Dao.scbh(kmbh,kjzd);
	}
	/**
	 * 校验科目编号
	 * @param kmbh
	 * @return
	 */
	public boolean doCheckKmbh1(String kmbh,String kjzd,String sszt) {
		boolean result = Dao.doCheckXmbh1(kmbh,kjzd,sszt);
		return result;
	}
	/**
	 * 生成下级编号
	 */
	public String scxjbh(String kmbh,String kjzd) {
		return Dao.scxjbh(kmbh, kjzd);
	}
}
