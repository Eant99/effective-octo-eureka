package com.googosoft.service.kjhs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.kjhs.KmszExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.fzgn.tzgg.TxlDao;
import com.googosoft.dao.kjhs.KmszDao;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.Cw_kmyemxb;
import com.googosoft.pojo.kjhs.KMSZ;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("kmszService")
public class KmszService {
	@Resource(name="kmszDao")
	public KmszDao Dao;
	
	
	
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
	
	
	public List getSsxt() {
		return Dao.getSsxt();
	}
	
	
	public String getTime() {
		return Dao.getTime();
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
		}else {
			 list = Dao.kmszMenuzj(jb);
		}
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
		//String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		String isAll = Validate.isNullToDefaultString(pd.getString("isAll"), "");
		boolean bool = false;
		if(Validate.isNull(jb)){
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
				System.err.println("isall==="+isAll);
				System.err.println("kmbh==="+kmbh);
				if((isAll.equals("0") && kmbh.equals("301"))||(isAll.equals("0") && kmbh.equals("309")))
				{
					System.err.println("AAAAAAAAA==="+kmbh);
				}
				else
				{
					kmmc = Validate.isNullToDefaultString(map.get("KMMC"), "");
	                kmjc = Validate.isNullToDefaultString(map.get("KMJC"),"");
	                count= Dao.getCount(kmbh);
	                l = Validate.isNullToDefaultString(map.get("L"),"");
	                k = Validate.isNullToDefaultString(map.get("K"),"");
	               if(count>0) {
	          	      children.add(new ExtTreeNode(kmbh, kmmc, false , true, false, href.length() > 0 ? href + kmbh +"&kmjc="+kmjc+"&l="+l+"&k="+k+"&kmnd="+map.get("KMND"): href, target));
	
	               }else {
	   					children.add(new ExtTreeNode(kmbh, kmmc, true , true, false, href.length() > 0 ? href + kmbh + "&kmjc="+kmjc+"&l="+l+"&k="+k+"&kmnd="+map.get("KMND"): href, target));
	               }             
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
		//String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
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
			String kmjc = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
                mc = map.get("KMMC").toString();
                jb = map.get("KMBH").toString();
                kmjc = map.get("KMJC").toString();
                
                count=Integer.parseInt(map.get("XJCOUNT")+"");
                if(count<=0) {
            		  children.add(new ExtTreeNode(jb, mc, true , true, false, href.length() > 0 ? href + kmjc + "&kmbh="+jb+ "&kmjc="+kmjc+"&kmnd="+map.get("KMND")+"&yefx="+map.get("YEFX")+"&kmsx="+map.get("KMSX"): href, target));
               }else {
            	   children.add(new ExtTreeNode(jb, mc, false , true, false, href.length() > 0 ? href + kmjc + "&kmbh="+jb+ "&kmjc="+kmjc+"&kmnd="+map.get("KMND")+"&yefx="+map.get("YEFX")+"&kmsx="+map.get("KMSX"): href, target));
               }
               }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();	
	}
	@Transactional
	public int updatenczye(String guid,String kmzye,String sszt) {
		int i=Dao.updatenczye( guid, kmzye,sszt);
		return i;
	}
	public int dodeletekmyemx(String guid,String nd) {
		return Dao.dodeletekmyemx(guid,nd);
	}
	/**
	 * 增加科目余额明细
	 */
	public int doAddkmyemx(Cw_kmyemxb kmyemx) {
		return Dao.doAddkmyemx(kmyemx);
		
		
	}
	public Object getPowerDwNode(PageData pd, String loginrybh,String rootPath,String sjfyfl,HttpSession session) {
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dm=";
		} else {
			Href = Href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = Dao.GetFykmdysz(sjfyfl,session);
		Map map=new HashMap();
		if(dList.size()>0){
			String id="",kmbh="",fymc="",sfmj="",kmsx="",kmjc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				id = Validate.isNullToDefault(map.get("guid"), "")+"";
                fymc = map.get("KMMC")+"";
                String jb = (String) map.get("jb");
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                sfmj=(String) map.get("sfmj");
                kmsx = (String) map.get("kmsx");
                kmjc= (String) map.get("kmjc");
                kmbh=Validate.isNullToDefaultString(map.get("KMBH"), "");
                if(xjcount<=0) {
				     children.add(new ExtTreeNode( kmbh,  fymc, true, true, false, Href.length() > 0 ? Href + map.get("sjfl").toString()+ "&jb="+map.get("JB")+"&sfmj="+sfmj+"&id="+id+"&kmsx="+kmsx+"&kmjc="+kmjc+"&kmbh="+kmbh+"&clicks=yes&kmmc="+fymc+"&kmnd="+map.get("KMND")+"&kjzd="+map.get("KJZD")+"&yefx="+map.get("YEFX") : Href, Target));
                }else{
                	children.add(new ExtTreeNode(kmbh, fymc, false, true, false, Href.length() > 0 ? Href + map.get("sjfl").toString()+ "&jb="+map.get("JB")+"&sfmj="+sfmj+"&id="+id+"&kmsx="+kmsx+"&kmjc="+kmjc+"&kmbh="+kmbh+"&clicks=yes&kmmc="+fymc+"&kmnd="+map.get("KMND")+"&kjzd="+map.get("KJZD")+"&yefx="+map.get("YEFX")  : Href, Target));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 删除
	 * @param pd
	 * @return
	 */
	public int deleteSrxm(PageData pd) {
		return Dao.deleteSrxm(pd);
	}
		public Map<String, Object> getObjectByIdByKmsz(String guid) {
				
				return Dao.getObjectByIdByKmsz(guid);
			}
		public int getCountByKmbh(String kmbh) {
			
			return Dao.getCountByKmbh(kmbh);
		}
		
		public int doDel(String kmbh) {
			int result = Dao.doDel(kmbh);
			return result;
		}
		public boolean doCheckKmbh(String kmbh) {
			boolean result = Dao.doCheckKmbh(kmbh);
			return result;
		}
		public int doAdd(KMSZ kmsz,HttpSession session){
			return Dao.doAdd(kmsz,session);
		}
		public int doUpdate(KMSZ kmsz){
			return Dao.doUpdate(kmsz);
		}
		public int drkm(String kjzd) {
			return Dao.drkm( kjzd);
		}
		public List<Map<String, Object>> getkm(String nd,String zkmsx,String sszt,String kjzd){
			
			return Dao.getkm(nd,zkmsx,sszt,kjzd);
			
		}
		public Map getfz(String kmyeguid) {
			
			return Dao.getfz(kmyeguid);
		}
		
		
			public String getTime1() {
			
			return Dao.getTime();
		}
		
		
		public List getcountkmye() {
			return Dao.getcountkmye();
		}
	public boolean doCheck(String kmguid) {
		return Dao.doCheck(kmguid);
	}

	// 科目余额明细导入数据确认
	public List<Map<String, Object>> insertKmyemx(String file) {
		return Dao.insertKmyemx(file);
	}

	// 科目余额明细导入
	public String doInsert(String file, String sszt, String kmyebh, String kmmc) throws Exception {
		return Dao.doInsert(file, sszt, kmyebh, kmmc);
	}
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String nd,String zkmsx) {
		List<Map<String, Object>> dwList = this.Dao.getList(searchValue,nd,zkmsx);
		String Title = "科目余额初始";
		String[] title = new String[] { "序号", "科目编号", "科目名称", "方向", "年初余额"  };
		Map<String, Object> dataMap = KmszExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}

}

