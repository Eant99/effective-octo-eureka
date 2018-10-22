package com.googosoft.controller.fzgn.wxzf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.fzgn.wxzf.CW_XFDDGL;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.wxzf.XfddglService;
import com.googosoft.util.FileUtil;
import com.googosoft.util.PageData;

/**
 * 消费地点管理控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping("/xfddgl")
public class XfddglController extends BaseController{

	@Resource(name="xfddglService")
	private XfddglService objService;
	
	@Resource(name="dictService")
	private DictService dictService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	/**
	 * 跳转到消费地点管理列表页面
	 * @return
	 */
	@RequestMapping(value = "/goPageList")
	public ModelAndView goSecondPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();		
		mv.setViewName("fzgn/wxzf/xfddgl_list");
		return mv;

	}
	
	/**
	 * 获取消费地点列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		
		sql.append(" A.guid,A.xfddbh,A.xfddmc,A.sscbsbh,(select mc from gx_sys_dmb where zl = 'xq' and dm=a.zlwz) zlwz,A.fzr,DECODE(A.zt,'1','正常','0','不正常')zt,k.cbsmc,nvl(A.ewmurl,'0')ewmurl,"
				+ "a.ywlx,(select d.mc from gx_sys_dmb d where d.dm=a.ywlx and zl='ywlx') as ywlxmc ");	
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName(" CW_XFDDGL A left join CW_CBSXX k on A.sscbsbh = k.cbsbh");
		// 主键
		pageList.setKeyId("A.GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();	
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 跳转消费地点编辑页面
	 * @return
	 */
	@RequestMapping(value = "/goCbsxxPage")
	public ModelAndView goCbsxxPage() {
		ModelAndView mv = this.getModelAndView();		
		String operateType = this.getPageData().getString("operateType");	
		List cbsList = objService.getCbsList();
		if (operateType.equals("C")) {
			String guid = GenAutoKey.get32UUID();			
			Map<String, String> map = new HashMap<String, String>();
			map.put("GUID", guid);			
			mv.addObject("map", map);
		} else if (operateType.equals("U") || operateType.equals("L")) {
			Map map = objService.getObjectById(this.getPageData().getString(
					"guid"));
			
			iniFile(mv,this.getPageData().getString("guid"));//查看微信图片
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("GUID", this.getPageData().getString("guid"));
			mv.addObject("map", maps);
			mv.addObject("xfddxx", map);			
			mv.addObject("guid", this.getPageData().getString("guid"));
		}
		mv.addObject("cbsList",cbsList);
		mv.addObject("xqlist", dictService.getDict("xq"));
		mv.addObject("ywlxlist", dictService.getDict("ywlx"));
		mv.setViewName("fzgn/wxzf/xfddgl_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 保存消费地点管理信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(CW_XFDDGL xfddxx) {
		String operateType = this.getPageData().getString("operateType");
		int result = 0;
		if ("C".equals(operateType)) {//新增
			int flag = objService.checkXfddbh(xfddxx);
			if(flag>0) {
				return "{success:'false',msg:'消费地点编号重复！'}";
			}else {
				result = objService.doAdd(xfddxx);
				if (result > 0) {
					return "{success:'true',msg:'信息保存成功！',cbsbh:'" + xfddxx.getXfddbh()
					+ "'}";
				} else {
					return MessageBox.show(false, MessageBox.FAIL_SAVE);
				}
			}
		}else if("U".equals(operateType)){//修改
			int flag = objService.checkXfddbhupdate(xfddxx);
			if(flag>0) {
				return "{success:'false',msg:'消费地点编号重复！'}";
			}else {
				result = objService.doUpp(xfddxx);
				if (result > 0) {
					return "{success:'true',msg:'信息保存成功！',cbsbh:'" + xfddxx.getXfddbh()
					+ "'}";
				} else {
					return MessageBox.show(false, MessageBox.FAIL_SAVE);
				}
			}
		}else{
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		int i = objService.doDel(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	/**
	 * 保存生成二维码
	 * @return
	 */
	@RequestMapping(value = "/doUpdateEwm", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdateEwm() {
		String guid = this.getPageData().getString("id");
		String ewmUrl = this.getPageData().getString("ewmUrl");	
		int i = objService.doUpdateEwm(guid, ewmUrl);
		if (i > 0) {
			return "{success:'true',msg:'信息修改成功！',guid:'" + guid+"'}";
				
		} else {
			return "{success:'true',msg:'信息修改失败！',guid:'" + guid+"'}";
		}
	}
	
	@RequestMapping("/fileDownload")
	@ResponseBody
	public void fileDownload(HttpServletResponse response, String filePath, String fileName){
		String realFilePath = filePath;
		filePath = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");
		filePath += "/"+ResourceBundle.getBundle("global").getString("FileDataTempFlieName");
		filePath += realFilePath.substring(realFilePath.lastIndexOf('/'));
		byte[] data;
		try {
			data = FileUtil.toByteArray2(filePath);
		    fileName = URLEncoder.encode(fileName, "UTF-8");  
		    response.reset();
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
		    response.addHeader("Content-Length", "" + data.length);
		    response.setContentType("application/octet-stream;charset=UTF-8");
		    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
		    outputStream.write(data);
		    outputStream.flush();
		    outputStream.close();
		    response.flushBuffer();
		} catch (IOException e) {
			logger.error("下载文件过程中，文件流异常！！");
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/filesDownload")
	@ResponseBody
	public void filesDownload(HttpServletResponse response, String filePath, String fileName){
		boolean flag = false;  
		 String[] ewmurls = filePath.split(",");  
	     String[] fileNames = fileName.split(","); 
		filePath = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");
		filePath += "/"+ResourceBundle.getBundle("global").getString("FileDataTempFlieName");
        File sourceFile = new File(filePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null; 
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+filePath+"不存在.");  
        }else{  
            try {  
                File zipFile = new File(filePath + "/消费地点.zip");  
                if(zipFile.exists()){  
                    System.out.println(filePath + "目录下存在名字为:消费地点.zip" +"打包文件.");  
                    zipFile.delete();
                } 
//                    File[] sourceFiles = sourceFile.listFiles();  
                    
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<ewmurls.length;i++){
                        	File file = new File(filePath+"/"+ewmurls[i].substring(ewmurls[i].lastIndexOf("/")));
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(fileNames[i]);  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(file);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
                        flag = true;  
                    
                 
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        if(flag==true){
        fileDownload(response,filePath+"/消费地点.zip","消费地点.zip");
        }
	}
	//初始化文件
	public void iniFile(ModelAndView mv,String guid) {
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	}

}
