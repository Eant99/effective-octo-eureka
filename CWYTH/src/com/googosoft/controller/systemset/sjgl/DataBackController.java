package com.googosoft.controller.systemset.sjgl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.PropertiesUtil;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.systemset.cssz.XtbService;
import com.googosoft.service.systemset.sjgl.DataBackService;
import com.googosoft.util.MacUtils;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/dataBack")
public class DataBackController extends BaseController{
	@Resource(name="dataBackService")
	private DataBackService dataBackService;//单例
	@Resource(name="xtbService")
	private XtbService xtbService;//单例
	
	Runtime runtime = Runtime.getRuntime(); 
    Process process = null ; 
    boolean isSuccess = false ; 
    
    /**
     * 跳转到数据备份页面
     */
    @RequestMapping(value="/findData")
	public ModelAndView findData(){
    	Map map = xtbService.getXtcs();//获取上一次数据备份日期
    	ModelAndView mv = this.getModelAndView();
    	mv.addObject("xtb", map);
    	String xtlx = Validate.isNullToDefaultString( MacUtils.getOSName(), "未检测到");//检测当前系统操作类型
    	mv.addObject("xtlx", xtlx);
    	mv.addObject("databasedz", PropertiesUtil.getGlobalValueByKey("dbserver_url"));
    	
    	mv.setViewName("systemset/sjgl/databaseback_frm");
    	return mv;
    }
    @RequestMapping(value="/findDatahf")
	public ModelAndView findDatahf(){
    	Map map = xtbService.getXtcs();//获取上一次数据备份名称
    	ModelAndView mv = this.getModelAndView();
    	mv.addObject("xtb", map);
    	mv.setViewName("systemset/sjgl/databasehf_frm");
    	return mv;
    }

   /** 
     * 备份 oracle 数据库的方法 
     * @param cmdStr 备份命令 ( 即导出 )
     */
	@RequestMapping(value="/backupOracleDB")
	@ResponseBody
	   public Object backupOracleDB() { 
		   try {
	    	 String desk = PropertiesUtil.getGlobalValueByKey("dbserver_url");
	    	 String xtlx = Validate.isNullToDefaultString( MacUtils.getOSName(), "未检测到");//检测当前系统操作类型
	    	 String pt="";
	    	 if(xtlx.indexOf("windows")>-1){
	    		 pt = PropertiesUtil.getGlobalValueByKey("window_system");
	    	 }else if(xtlx.indexOf("Linux")>-1){
	    		 pt = PropertiesUtil.getGlobalValueByKey("linux_system");
	    	 }else{
	    		 pt = PropertiesUtil.getGlobalValueByKey("window_system");
	    	 }
	    	 String username=PropertiesUtil.getGGSValueByKey("username_db").trim();
	    	 String password=PropertiesUtil.getGGSValueByKey("password_db").trim();
	    	 String url=PropertiesUtil.getGGSValueByKey("dataBack").trim();	    	 
	    	 String cmdStr = "";
	    	 Date date = new Date();
	    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SS毫秒");
	    	 String fileName=sdf.format(date);
	    	 String deskAddr = desk+pt+fileName+".dmp";
	    	 //System.err.println("deskAddr:"+deskAddr);
	    	 //判断文件是否存在，不存在创建文件
	    	 File file=new File(desk+"DataBak");
	    	 if(!file.exists()){
	    		 file.mkdirs();//如果文件夹不存在则创建
	    	 }
	    	 try {
	    		 cmdStr = "exp "+username+"/"+password+url+" file="+deskAddr;
	    		 //System.err.println("cmdStr:"+cmdStr);
		         process = runtime.exec(cmdStr); 
	 			 final InputStream in =  process.getInputStream();
	 			 new Thread(new IORunnable(in)).start();
	 			 final InputStream es =  process.getErrorStream();
	 			 new Thread(new IORunnable(es)).start();
	 			 process.waitFor();
	 			 String bfmc = fileName+".dmp";
	 			 dataBackService.getBfmc(bfmc);
			 } catch (InterruptedException e) {
				 e.printStackTrace();
			 }
	         isSuccess = true;
	         if(isSuccess){
	        	
	         }
	      }  catch (IOException e) { 
	         e.printStackTrace(); 
	      } catch (Exception e) {
			 e.printStackTrace();
		  }
		   return "{\"success\":\"true\",\"msg\":\"保存成功！\"}"; 	     
	   } 
	 /** 
     * 恢复 oracle 数据库的方法 
     * @param 
     */
	@RequestMapping(value="/backOracleDB")
	@ResponseBody
   public Object backOracleDB() { 
	   String fileName = this.getPageData().getString("fileName");
	   try {
    	 String desk = PropertiesUtil.getGlobalValueByKey("db_url");
    	 String pt = PropertiesUtil.getGlobalValueByKey("window_pt");;
    	 String username=PropertiesUtil.getGGSValueByKey("username").trim();
    	 String password=PropertiesUtil.getGGSValueByKey("password").trim();
    	 String url=PropertiesUtil.getGGSValueByKey("dataBack").trim();
    	 String cmdStr = "";
    	 String deskAddr = desk+"DataBak"+pt+fileName;
    	 //System.out.println("deskAddr====="+deskAddr);
    	 try {
    		 cmdStr = "imp "+username+"/"+password+url+" file="+deskAddr;
    		 //System.out.println("cmdStr==================="+cmdStr);
	         process = runtime.exec(cmdStr); 
 			 final InputStream in =  process.getInputStream();
 			 new Thread(new IORunnable(in)).start();
 			 final InputStream es =  process.getErrorStream();
 			 new Thread(new IORunnable(es)).start();
 			  process.waitFor();
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 }
         isSuccess = true;
         if(isSuccess){
        	
         }
      }  catch (IOException e) { 
         e.printStackTrace(); 
      } catch (Exception e) {
		 e.printStackTrace();
	  }
	   return "{\"success\":\"true\",\"msg\":\"恢复成功！\"}"; 	     
   } 
	   /**
	    * @author admin
	    */
	   private class IORunnable implements Runnable {

			private InputStream in;
			
			public IORunnable(java.io.InputStream in2){
				this.in = in2;
			}
			
			private void flushInputStream (){
				try {
					if(in == null){
						return ;
					}
					while((in.read()) != -1){
						//此处不做任何处理
					}
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void run() {
				this.flushInputStream();
			}
			
		}
		@RequestMapping(value="/getZxbf")
		@ResponseBody
		
		public Object getZxbf(){
			int i = dataBackService.getZxbf();
			if(i>0){
				return "true" ;
			}else{
				return "false";
			}
		}	
		
		
}

