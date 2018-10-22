package com.googosoft.service.xzgl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.xzgl.excel.LzryExportExcel;
import com.googosoft.controller.xzgl.excel.XzimpExcel;
import com.googosoft.controller.xzgl.excel.ZzryExportExcel;
import com.googosoft.dao.xzgl.XzglDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.WindowUtil;

@Service("xzglService")
public class XzglService extends BaseService{

	@Resource(name="xzglDao")
	private XzglDao xzglDao;

	/**
	 * 在职薪资查询
	 * @param pd 
	 * @return
	 */
	public List<Map<String, Object>> getXzList(PageData pd) {
		return xzglDao.getXzList(pd);
	}
	
	/**
	 * 工资维护查询
	 * @param pd 
	 * @return
	 */
	public List<Map<String, Object>> getGzwhList() {
		return xzglDao.getGzwhList();
	}
	
	public List<Map<String, Object>> getList() {
		return xzglDao.getList();
	}
	
	public List<Map<String, Object>> getListy() {
		return xzglDao.getListy();
	}
	
	public int doPlfuzhi(String ids,String ziduan,Object zdValue,String ids2) throws ParseException  {
		if(ziduan.equals("sjdw")){
			zdValue = WindowUtil.getXx(zdValue+"", "D");
		}else if(ziduan.equals("dwld")){
			zdValue = WindowUtil.getXx(zdValue+"", "R");
		}else if(ziduan.equals("jlrq")){
			zdValue = new SimpleDateFormat("yyyy-MM-dd").parse(zdValue+"");
		}
		return xzglDao.doPlfuzhi(ids, ziduan, zdValue,ids2);
		
	}
	public List cxidlist(PageData pd) {
		return xzglDao.cxidlist(pd);
	}
	
	public List cxidlist2(PageData pd) {
		return xzglDao.cxidlist2(pd);
	}
	
	public int doPlfuzhiy(String ids,String ziduan,Object zdValue,String ids2,String gzyf) throws ParseException  {
		if(ziduan.equals("sjdw")){
			zdValue = WindowUtil.getXx(zdValue+"", "D");
		}else if(ziduan.equals("dwld")){
			zdValue = WindowUtil.getXx(zdValue+"", "R");
		}else if(ziduan.equals("jlrq")){
			zdValue = new SimpleDateFormat("yyyy-MM-dd").parse(zdValue+"");
		}
		return xzglDao.doPlfuzhiy(ids, ziduan, zdValue,ids2,gzyf);
		
	}
	
	/**
	 * 清空薪资表
	 * @return
	 */
	public int doDelXz() {
		return xzglDao.doDelXz();
	}
	/**
	 * 在职薪资导入
	 * @param file
	 * @return
	 */
	public String insertXz(String file) {
		return xzglDao.insertXz(file);
	}

	/**
	 * 在职薪资保存
	 * @param list
	 * @return
	 */
	public boolean doSave(List list){
		return xzglDao.doSave(list);
	}

	/**
	 * 离职薪资查询
	 * @param pd 
	 * @return
	 */
	public  List<Map<String, Object>> getLzxzList(PageData pd) {
		return xzglDao.getLzxzList(pd);
	}

	/**
	 *  离职薪资导入
	 * @param file
	 * @return
	 */
	public String insertLzxz(String file) {
		return xzglDao.insertLzxz(file);
	}

	/**
	 * 离职薪资保存
	 * @param list
	 * @return
	 */
	public boolean doSaveLz(List list) {
		return xzglDao.doSaveLz(list);
	}

	/**
	 * 清空离职临时表
	 * @return
	 */
	public int doDelLzxz() {
		return xzglDao.doDelLzxz();
	}

	/**
	 * 在职薪资核算
	 * @return
	 */
	public int doHs(String gzyf,String rybh,String bm) {
		return xzglDao.doHs(gzyf,rybh,bm);
	}
	/**
	 * 在职薪资提交
	 * @return
	 */
	public int doTj(String guid,String gzyf,String rybh,String bm) {
		return xzglDao.doTj(guid,gzyf,rybh,bm);
	}

	/**
	 * 在职薪资导入数据确认
	 * @param file
	 * @return
	 */
	public List<Map<String, Object>> getXzImpeQr(String file) {
		return xzglDao.getXzImpeQr(file);
	}
	/**
	 * 离职薪资导入数据确认
	 * @param file
	 * @return
	 */
	public List<Map<String, Object>> getLzxzImpeQr(String file) {
		return xzglDao.getLzxzImpeQr(file);
	}

	/**
	 * 离职薪资提交
	 */
	public int doLzTj(String guid,String gzyf) {
		return xzglDao.doLzTj(guid,gzyf);
	}
	//查询当前月份是否有 rybh的信息
	public List getcount(String rybh,String txry,String gzyf) {
		
		return xzglDao.getcount(rybh,txry,gzyf);
	}
	/**
	 * 在职薪资--复制上月工资--检查输入框年月是否有工作简历
	 */
	public boolean checkGzyf(String gzyf,String type) {
		return xzglDao.getbooGzny(gzyf,type);
	}
	/**
	 * 在职薪资--复制上月工资
	 * @return
	 */
	public int doFzsygz(String gzyf) {
		return xzglDao.doFzsygz(gzyf);
	}
	/**
	 * 在职薪资--获取上月离职人员
	 * @return
	 */
	public String getLzry(String gzyf) {
		List list = xzglDao.getLzry(gzyf);
		if(list.size()==0||list==null) {
			return "";
		}else {
			String returnString = "";
			Map map = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				map = (Map)list.get(i);
				if(i==(list.size()-1)) {
					returnString+=map.get("RY");
				}else {
					returnString+=(map.get("RY")+",");
				}
		    }
		   return returnString;
	    }
	}
	/**
	 * 离职薪资--复制上月工资
	 * @return
	 */
	public int doFzsygzLz(String gzyf) {
		return xzglDao.doFzsygzLz(gzyf);
	}
	public int addryxx(String rybh,String txry,String gzyf,String xl,String dwbh) {
		return xzglDao.addryxx(rybh, txry,gzyf,xl,dwbh);
	}
	
	
	public String getLastMon(){
		return xzglDao.getLastMon();
	}
	public String getLastMonLz(){
		return xzglDao.getLastMonLz();
	}
	/**
	 * 获取发放月份
	 */
	public Map<String, Object> getffyf() {
		return xzglDao.getffyf();
	}
	/**
	 * 删除
	 */
	public int doDelete(String guid) {
		return xzglDao.doDelete(guid);
	}
	/**
	 * 删除_退休人员薪资生成
	 */
	public int doDelete_txry(String guid) {
		return xzglDao.doDelete_txry(guid);
	}

	/**
	 * 在职薪资数据导出
	 * @param realfile
	 * @param shortfileurl
	 * @param ids
	 * @return
	 */
	public Object ZzryexpExcel(String realfile, String shortfileurl, String guid,PageData pd) {
       List<Map<String, Object>> list = this.xzglDao.getListByid(guid,pd);
//       List<Map<String,Object>> gzwhlist=xzglDao.getZzxzdrmb();
       String name = pd.getString("texts");
       List<Map<String,Object>> gzwhlist=xzglDao.getZzxzdrmbs(name);
       int length=gzwhlist.size()+1;
       String [] newtitle=new String[length];
       String [] newText=new String[length];
       newtitle[0]="序号";
       for(int i=1;i<newtitle.length;i++) {
    	   newtitle[i]=(String) gzwhlist.get(i-1).get("XMMC");
    	   newText[i-1]=(String) gzwhlist.get(i-1).get("XMJC");
       }
//       for(int i=0;i<newtitle.length-1;i++) {
       for(int i=1;i<newtitle.length;i++) {
    	  System.err.println((String) gzwhlist.get(i-1).get("XMMC")+"-----");
//    	  newText[i]=(String) gzwhlist.get(i).get("XMJC");
       }
       String Title = "在职人员薪资";
//       String[] title = new String[] { "序号","人员编号", "姓名", "部门", "人员类别", "人员类型","岗位工资","薪级工资","新住房贴","物业补贴","独生子费" ,
//    		   "基础绩效", "奖励绩效1", "博士补贴", "岗位补贴", "北校区值班补贴","生育补贴","教护补贴","驻济补贴","合同补贴","其他补贴", 
//    		   "补工资", "监考费", "辅助工作量", "招生奖励", "辅导员夜间值班补贴","考核奖","电话费","补贴","租房补贴","应发合计", 
//    		   "讲课费", "补工资扣税", "计税项", "全年计税项", "全年计税项1","全年计税项2","全年计税项3","补基础性绩效工资2014计税基数","北校补贴2014年1到10月计税基数","计税基数", 
//    		   "住房积金", "聘公积金", "医疗保险", "补公积金", "代扣税","本年税额","上年税额","补税","房租","失业金", 
//    		   "暖气费", "暖气费1", "物业费", "社保金", "四季度菜款","扣款","养老金","借款","爱心一日捐","扣款合计",
//    		   "实发合计", "工资月份", "编号", "序号", "交通费","年终奖","年终奖代扣税","工资代扣税","扣税合计","工号",
//    		   "是否在编", "补扣医疗保险", "补扣失业金", "补扣养老金", "补扣社保金","是否党员","入党情况" };
       Map<String, Object> dataMap = ZzryExportExcel.exportExcel(realfile,
    	   shortfileurl, newtitle, Title, list,newText);
       return dataMap;
 }
	/**
	 * 
	 * @author  wangguanghua
	 * @version  2018-9-13下午3:20:45
	 * 数据导出  但是实现下载模板功能
	 */
	public Object ZzryexpExcelnew(String realfile, String shortfileurl, String guid,PageData pd) {
       List<Map<String, Object>> list = this.xzglDao.getListByidnew(guid,pd);
//       List<Map<String,Object>> gzwhlist=xzglDao.getZzxzdrmb();
       String name = pd.getString("texts");
       System.err.println("nameold++++"+name+"");
             String  nameString=name.replace(",", "','");
            System.err.println("namenew++++"+nameString+"");
       List<Map<String,Object>> gzwhlist=xzglDao.getZzxzdrmbs(nameString);
       int length=gzwhlist.size();
       String [] newtitle=new String[length];
       String [] newText=new String[length];
       
       for(int i=0;i<newtitle.length;i++) {
    	   newtitle[i]=(String) gzwhlist.get(i).get("XMMC");
    	   newText[i]=(String) gzwhlist.get(i).get("XMJC");
       }
//       for(int i=0;i<newtitle.length-1;i++) {
       for(int i=1;i<newtitle.length;i++) {
    	  System.err.println((String) gzwhlist.get(i-1).get("XMMC")+"-----");
//    	  newText[i]=(String) gzwhlist.get(i).get("XMJC");
       }
       String Title = "";
//       String[] title = new String[] { "序号","人员编号", "姓名", "部门", "人员类别", "人员类型","岗位工资","薪级工资","新住房贴","物业补贴","独生子费" ,
//    		   "基础绩效", "奖励绩效1", "博士补贴", "岗位补贴", "北校区值班补贴","生育补贴","教护补贴","驻济补贴","合同补贴","其他补贴", 
//    		   "补工资", "监考费", "辅助工作量", "招生奖励", "辅导员夜间值班补贴","考核奖","电话费","补贴","租房补贴","应发合计", 
//    		   "讲课费", "补工资扣税", "计税项", "全年计税项", "全年计税项1","全年计税项2","全年计税项3","补基础性绩效工资2014计税基数","北校补贴2014年1到10月计税基数","计税基数", 
//    		   "住房积金", "聘公积金", "医疗保险", "补公积金", "代扣税","本年税额","上年税额","补税","房租","失业金", 
//    		   "暖气费", "暖气费1", "物业费", "社保金", "四季度菜款","扣款","养老金","借款","爱心一日捐","扣款合计",
//    		   "实发合计", "工资月份", "编号", "序号", "交通费","年终奖","年终奖代扣税","工资代扣税","扣税合计","工号",
//    		   "是否在编", "补扣医疗保险", "补扣失业金", "补扣养老金", "补扣社保金","是否党员","入党情况" };
       Map<String, Object> dataMap = ZzryExportExcel.exportExcelnew(realfile,
    	   shortfileurl, newtitle, Title, list,newText);
       return dataMap;
 }
	/**
	 * 离职薪资数据导出
	 * @param realfile
	 * @param shortfileurl
	 * @param ids
	 * @return
	 */
	public Object LzryexpExcel(String realfile, String shortfileurl, String guid,PageData pd) {
		List<Map<String, Object>> list = this.xzglDao.getLzListByid(guid,pd);
		String name = pd.getString("texts");
		name = name.replace(" ", ""); 
        List<Map<String,Object>> gzwhlist=xzglDao.getZzxzdrmbsBylz(name);
        int length=gzwhlist.size()+1;
        String [] newtitle=new String[length];
        String [] newText=new String[length];
        newtitle[0]="序号";
        for(int i=1;i<newtitle.length;i++) {
    	   newtitle[i]=(String) gzwhlist.get(i-1).get("XMMC");
    	   newText[i-1]=(String) gzwhlist.get(i-1).get("XMJC");
        }
		String Title = "退休人员薪资";
//		String[] title = new String[] { "序号","人员编号", "姓名", "部门", "人员类别", "基本工资","增加离退费","原职务补贴","岗位补贴","新住房贴","回族补贴" ,
//				"退休提高部分", "生活补贴", "新增补贴", "物价补贴", "特需护理","教护补贴","其他补贴","离退补贴","月增补贴","基础绩效", 
//				"交通费", "补贴", "租房补贴", "补工资", "物业补贴","监考费","过节费","电话费","应发合计","房租", 
//				"社保金", "暖气费", "暖气费1", "物业费", "借款","养老金","补公积金","补税","四季度菜款","失业金", 
//				"扣款合计", "实发合计", "工资月份", "编号", "计税项","住房积金","年终奖","扣款" };
		Map<String, Object> dataMap = LzryExportExcel.exportExcel(realfile,
				shortfileurl, newtitle, Title, list,newText);
		return dataMap;
	}
	/**
	 * 离职薪资数据导出
	 * @param realfile
	 * @param shortfileurl
	 * @param ids
	 * @return
	 * xin 
	 */
	public Object LzryexpExcelnew(String realfile, String shortfileurl, String guid,PageData pd) {
		List<Map<String, Object>> list = this.xzglDao.getLzListByidnew(guid,pd);
		String name = pd.getString("texts");
		name = name.replace(",", "','"); 
        List<Map<String,Object>> gzwhlist=xzglDao.getZzxzdrmbsBylz(name);
        int length=gzwhlist.size();
        String [] newtitle=new String[length];
        String [] newText=new String[length];
        
        for(int i=0;i<newtitle.length;i++) {
    	   newtitle[i]=(String) gzwhlist.get(i).get("XMMC");
    	   newText[i]=(String) gzwhlist.get(i).get("XMJC");
        }
		String Title = "";
//		String[] title = new String[] { "序号","人员编号", "姓名", "部门", "人员类别", "基本工资","增加离退费","原职务补贴","岗位补贴","新住房贴","回族补贴" ,
//				"退休提高部分", "生活补贴", "新增补贴", "物价补贴", "特需护理","教护补贴","其他补贴","离退补贴","月增补贴","基础绩效", 
//				"交通费", "补贴", "租房补贴", "补工资", "物业补贴","监考费","过节费","电话费","应发合计","房租", 
//				"社保金", "暖气费", "暖气费1", "物业费", "借款","养老金","补公积金","补税","四季度菜款","失业金", 
//				"扣款合计", "实发合计", "工资月份", "编号", "计税项","住房积金","年终奖","扣款" };
		Map<String, Object> dataMap = LzryExportExcel.exportExcelnew(realfile,
				shortfileurl, newtitle, Title, list,newText);
		return dataMap;
	}
	/**
	 * 在职薪资导入模板生成
	 */
	public Object Zzxzdrmb(String realfile, String shortfileurl) {
		List<Map<String, Object>> list = this.xzglDao.getZzxzdrmb();
		String Title = "薪资导入模板";
		int length = list.size();
		String[] title = new String[length] ;
		String[] xzzd =  new String[length] ;//薪资字段 导入时不需要判断 根据这个字段
		for (int i = 0; i < title.length; i++) {
			title[i]=(String) list.get(i).get("XMMC");
			xzzd[i]=(String) list.get(i).get("xmjc");
		}
		Map<String, Object> dataMap = XzimpExcel.exportExcel(realfile,shortfileurl, title, Title, list,xzzd);
		return dataMap;
	}
	
	public static void main(String[] args) {
		String c = "部     门";
		System.out.println(c.replaceAll(" ",""));
	}
	/**
	 * 薪资管理合计
	 * @param pd
	 * @return
	 */
	public List<Map<String, Object>> getXzHjList(PageData pd) {
		return xzglDao.getXzHjList(pd);
	}
	
	public int count(String tablename,String rybh,String shzt,String gzyf,String xm,String bm){
		return xzglDao.getcount(tablename, rybh, shzt, gzyf,xm,bm);
	}
	
/**
 * 
 * @author 作者：PR
 * @date 2018-9-13  下午16:51:55
 */
	public List<Map<String,Object>> getExcelData(String sql){
		List list = new ArrayList<>();
		List list1 = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RYBH", "人员编号");
		map.put("XM", "姓名");
		map.put("BM", "部门");
		map.put("RYLB","人员类别");
		map.put("RYLX","人员类型");
		map.put("XH", "排序序号");
		map.put("GWGZ", "岗位工资");
		map.put("XJGZ", "薪级工资");
		map.put("XZFT", "新住房贴");
		map.put("WYBT", "物业补贴");
		map.put("DSZF", "独/回");
		map.put("JCJX", "基础绩效");
		map.put("JLJX1", "奖励绩效1");
		map.put("BSBT", "博士补贴");
		map.put("GWBT", "岗位补贴");
		map.put("BGZ", "补工资");
		map.put("YFHJ", "应发合计");
		map.put("ZFJJ", "住房积金");
		map.put("YLBX", "医疗保险");
		map.put("DKS", "代扣税");
		map.put("FZ", "房租");
		map.put("SYJ", "失业金");
		map.put("SBJ", "社保金");
		map.put("YLJ", "养老金");
		map.put("KK", "扣款");
		map.put("KKHJ", "扣款合计");
		map.put("SFHJ", "实发合计");

		list = xzglDao.getExcelData(sql);
		int a = 0;
		for(int i = 0;i<list.size();i++){
			list1.add(list.get(i));
			if(i<list.size()-1){
				list1.add(map);
			}
		}
		return list1;
	}

	/**
	 * @Description: 描述:验证是否为在职人员
	 * @author 作者:李烁
	 * @date 创建时间:2018-9-14下午1:28:25
	 * @param rybh
	 * @return    
	 */
	public int zzryCheck(String rybh) {
		return xzglDao.zzryCheck(rybh);
	}

	/**
	 * @Description: 描述:判断该人员是否为退休人员
	 * @author 作者:李烁
	 * @date 创建时间:2018-9-14下午1:52:26
	 * @param rybh
	 * @return    
	 */
	public int txryCheck(String rybh) {
		return xzglDao.txryCheck(rybh);
	}
   			           
}
