package com.googosoft.controller.systemset.cssz;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_TOOLDOWN;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cssz.GjxzwhService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.FileUtil;
import com.googosoft.util.PageData;
/**工具下载维护
 * @author sxl
 */
@Controller
@RequestMapping(value="/xzwhb")
public class GjxzwhController extends BaseController {
@Resource(name="gjxzwhService")
private GjxzwhService gjxzwhService;
@Resource(name="pageService")
private PageService pageService;

/**
 * 跳转到工具下载维护页面
 * @return
 */
@RequestMapping(value="/goGjxzwh_listPage")
public ModelAndView goXgtrmmPage(){
	ModelAndView mv = this.getModelAndView();
	mv.setViewName("systemset/cssz/gjxzwh/gjxzwh_list");
	return mv;
}
/**获取工具下载维护页面
 * @return
 */
@RequestMapping(value="/getPageList")
@ResponseBody
public Object getPageList(){
	//将获取的信息
	PageData pd = this.getPageData();
	pd.put("length", "999999");
	StringBuffer sqltext = new StringBuffer();
	sqltext.append("Z.WJLX,Z.WJMC,Z.WJMS,Z.GUID,Z.FNAME,Z.PATH,Z.XTBZ");
	PageList pageList = new PageList();
	pageList.setSqlText(sqltext.toString());
	pageList.setKeyId("Z.GUID");
	pageList.setStrWhere("");
	pageList.setTableName("ZC_SYS_TOOLDOWN Z");
	pageList.setHj1("");
	pageList = pageService.findPageList(pd,pageList);
	Gson gson = new Gson();
	//draw;//请求次数 recordsTotal;//没过滤  总记录数 recordsFiltered;//过滤后的总记录数 data;//数据
	PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
	return pageInfo.toJson();
}
/**获取工具下载页面（快捷方式）
 * @return
 */
@RequestMapping("/goGjxzPage")
public ModelAndView goGjxzPage(){
	ModelAndView mv = this.getModelAndView();
	mv.setViewName("systemset/cssz/gjxzwh/gjxz");
	return mv;
}
/**进入添加工具下载维护页面
 * @return
 */
@RequestMapping(value = "/goXzwhPage")
public ModelAndView goXzwhPage() {
	String operateType=this.getPageData().getString("operateType");
	ModelAndView mv = this.getModelAndView();
	if(operateType.equals("C")){
		String guid = AutoKey.makeCkbh("ZC_SYS_TOOLDOWN", "guid", "6");
		Map<String, String> map = new HashMap<String, String>();
		map.put("guid", guid);
		mv.addObject("xzwhb", map);
	}else if(operateType.equals("U")){
		String guid = this.getPageData().getString("guid"); 
		String realPath = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
		String[] fjxx = gjxzwhService.getFjList(guid,realPath).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		Map map = gjxzwhService.getObjectById(this.getPageData().getString("guid"));
		mv.addObject("guid",this.getPageData().getString("guid"));
		mv.addObject("xzwhb",map);
	}
	// 跳转页面
	mv.addObject("operateType",operateType);
	mv.setViewName("systemset/cssz/gjxzwh/gjxzwh_edit");
	return mv;
}
/**
 * 文件上传类（为上传图片和附件准备的通用方法）
 * @param response
 * @param filePath
 * @param fileName
 * @throws IOException 
 * @throws IllegalStateException 
 * @throws Exception
 */
@RequestMapping("/fileUpload")
@ResponseBody
public Object fileUpload(HttpServletRequest request) throws IllegalStateException, IOException{
	ZC_SYS_TOOLDOWN xzwxb = new ZC_SYS_TOOLDOWN();
	String fold = request.getParameter("fold");
	String dayFold = new SimpleDateFormat("yyyyMMdd").format(new Date());
	//虚拟路径
	String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
	//物理路径
	String wllu_path = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");
	
	// 转换为文件类型的request
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    // 获取对应file对象
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    Iterator<String> fileIterator = multipartRequest.getFileNames();
    while (fileIterator.hasNext()) {
        String fileKey = fileIterator.next();
        logger.debug("文件名为：" + fileKey);
        // 获取对应文件
        MultipartFile multipartFile = fileMap.get(fileKey);
        if (multipartFile.getSize() != 0L) {
        	//原始文件名称
        	String pictureFile_name = multipartFile.getOriginalFilename();
        	String wjlx = pictureFile_name.substring(pictureFile_name.lastIndexOf(".") + 1);
        	//新文件名称
        	String wjmc_url = get32UUID()+"."+wjlx;
        	String path = "/"+fold+"/"+dayFold+"/"+wjmc_url;
        	//上传图片
        	File uploadPic = new File(wllu_path+path);
        	if(!uploadPic.exists()){
        		uploadPic.mkdirs();
        	}
        	//向磁盘写文件
        	multipartFile.transferTo(uploadPic);
        	String guid = AutoKey.makeCkbh("ZC_SYS_TOOLDOWN", "guid", "6");
        	xzwxb.setPath(path);
        	xzwxb.setGuid(guid);
        	String wjmc = request.getParameter("wjmc");
        	String wjms = request.getParameter("wjms");
        	String operateType=request.getParameter("operateType");
        	xzwxb.setWjms(wjms);
        	xzwxb.setWjmc(wjmc);
        	xzwxb.setFname(pictureFile_name);
        	xzwxb.setWjlx(wjlx);
        	if(operateType.equals("C")){
        	int i = gjxzwhService.doAdd(xzwxb);
        	}else if(operateType.equals("U")){
        		int i=gjxzwhService.doUpdate(xzwxb);
        	}
        	return "{\"error\":\"\",\"msg\":\"success\","
        			+ "\"initialPreviewConfig\":[{\"caption\":\""+wjmc_url+"\",\"width\":\"120px\",\"key\":\""+path+"@"+guid+"\"}],"
        			+ "\"initialPreview\":[\""+xnlu_path+path+"\"]}";
        }
    }
    return "{\"error\":\"fail\",\"msg\":\"success\"}";
}

/**
 * 文件下载类
 * @param response
 * @param filePath
 * @param fileName
 * @throws Exception
 */
@RequestMapping("/fileDownload")
@ResponseBody
public void fileDownload(HttpServletResponse response, String filePath, String fileName){
	filePath = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath")+filePath;
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


/**
 * 文件下载类,(通讯录，基础数据导入，资产处置数据导入模板下载)
 */
@RequestMapping("/fileDownloadImpo")
@ResponseBody
public void fileDownloadImpo(HttpServletResponse response, String filePath, String fileName){
	filePath = this.getRequest().getServletContext().getRealPath("\\")+filePath;
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

/**删除下载工具数据
 * @return
 */
@RequestMapping(value="/delectGjxz",produces = "text/html;charset=UTF-8")
@ResponseBody
public Object delectGjxz(HttpServletRequest request){
	String guid=this.getPageData().getString("guid");
	String path=this.getPageData().getString("path");
	//String realPath = request.getServletContext().getRealPath("/");
	String realPath = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");
	FileUtil.delFile(realPath+path);
	int i=gjxzwhService.doDel(guid);
	if(i>0){
		return "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
	}else{
		return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
	}
 }

/**检查文件是否存在
 */
@RequestMapping(value="/doCheckWj",produces = "text/html;charset=UTF-8")
@ResponseBody
public Object doCheckWj(){
	String filePath=this.getPageData().getString("filePath");
	filePath = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath")+filePath;
	File file = new File(filePath);
	if(file.exists()){
		return "{\"success\":\"true\",\"msg\":\"文件存在！\"}";
	}else{
		return "{\"success\":\"false\",\"msg\":\"该文件不存在！\"}";
	}
}
}
