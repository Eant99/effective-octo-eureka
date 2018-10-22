package com.googosoft.controller.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.LUser;
import com.googosoft.service.base.FileService;
import com.googosoft.util.FileUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
/**
 * 文件上传下载类
 * @author master
 * @version 0.1
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController{
	
	private Logger logger = Logger.getLogger(FileController.class);
	
	@Resource(name="fileService")
	private FileService fileService;//单例
	
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
		filePath = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\"+filePath;
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
	public String fileUpload(HttpServletRequest request) throws IllegalStateException, IOException{
		String id = request.getParameter("id");
		String djlx = request.getParameter("djlx");
		String fold = request.getParameter("fold");
		String dayFold = new SimpleDateFormat("yyyyMMdd").format(new Date());
		System.out.println(id+"@@@@"+djlx+"!!!!"+fold);
//		String contextPath = request.getContextPath();//img请求用的根路径(项目名称)
//		String realPath = request.getServletContext().getRealPath("/");//文件保存时候的根路径(工作空间)
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
            	String pictureFile_name =  multipartFile.getOriginalFilename();
            	//新文件名称
            	String newFileName = get32UUID() + pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
            	//虚拟路径
            	String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
            	//物理路径
            	String wllu_path = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");
            	//上传图片
//            	File uploadPic = new File(realPath+"imgFile/"+fold+"/"+dayFold+"/"+newFileName);
            	File uploadPic = new File(wllu_path+"/"+fold+"/"+dayFold+"/"+newFileName);
            	if(!uploadPic.exists()){
            		uploadPic.mkdirs();
            	}
            	//向磁盘写文件
            	multipartFile.transferTo(uploadPic);
//            	String urlpath = "imgFile/"+fold+"/"+dayFold+"/"+newFileName;
            	String urlpath = fold+"/"+dayFold+"/"+newFileName;
            	String guid = get32UUID();
            	fileService.doAdd(guid,id,djlx,pictureFile_name,urlpath);
            	return "{\"error\":\"\",\"msg\":\"success\","
            			+ "\"initialPreviewConfig\":[{\"caption\":\""+pictureFile_name+"\",\"width\":\"120px\",\"key\":\"imgFile/"+fold+"/"+dayFold+"/"+newFileName+"@"+guid+"\",\"size\":\""+djlx+"\"}],"
            			+ "\"initialPreview\":[\""+xnlu_path+"/"+fold+"/"+dayFold+"/"+newFileName+"\"]}";
            }
        }
        return "{\"msg\":\"success\"}";
	}

	@RequestMapping("/fileUploads")
	@ResponseBody
	public String fileUploads(HttpServletRequest request) throws IllegalStateException, IOException{
		String id = request.getParameter("id");
		String djlx = request.getParameter("djlx");
		String fold = request.getParameter("fold");
		String rybh = request.getParameter("rybh");
		String dayFold = new SimpleDateFormat("yyyyMMdd").format(new Date());
		System.out.println(id+"@@@@"+djlx+"!!!!"+fold);
//		String contextPath = request.getContextPath();//img请求用的根路径(项目名称)
//		String realPath = request.getServletContext().getRealPath("/");//文件保存时候的根路径(工作空间)
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
            	String pictureFile_name =  multipartFile.getOriginalFilename();
            	//新文件名称
            	String newFileName = get32UUID() + pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
            	//虚拟路径
            	String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
            	//物理路径
            	String wllu_path = ResourceBundle.getBundle("global").getString("FileDataPhysicalPath");
            	//上传图片
//            	File uploadPic = new File(realPath+"imgFile/"+fold+"/"+dayFold+"/"+newFileName);
            	File uploadPic = new File(wllu_path+"/"+fold+"/"+dayFold+"/"+newFileName);
            	if(!uploadPic.exists()){
            		uploadPic.mkdirs();
            	}
            	//向磁盘写文件
            	multipartFile.transferTo(uploadPic);
//            	String urlpath = "imgFile/"+fold+"/"+dayFold+"/"+newFileName;
            	String urlpath = fold+"/"+dayFold+"/"+newFileName;
            	String guid = get32UUID();
            	fileService.doAdd(guid,id,djlx,pictureFile_name,urlpath,rybh);
            	return "{\"error\":\"\",\"msg\":\"success\","
            			+ "\"initialPreviewConfig\":[{\"caption\":\""+pictureFile_name+"\",\"width\":\"120px\",\"key\":\"imgFile/"+fold+"/"+dayFold+"/"+newFileName+"@"+guid+"\",\"size\":\""+djlx+"\"}],"
            			+ "\"initialPreview\":[\""+wllu_path+"/"+fold+"/"+dayFold+"/"+newFileName+"\"]}";
            }
        }
        return "{\"msg\":\"success\"}";
	}
	
	/**
	 * fileinput控件刷新时获取图片
	 */
	@RequestMapping(value="/getFilesByMkbhDbh",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getFilesByMkbhDbh(){
		PageData pd = this.getPageData();
		String dbh = pd.getString("dbh");
		String mkbh = pd.getString("mkbh");
		ModelAndView mv = this.getModelAndView();
		HttpServletRequest request=this.getRequest();
		String realPath = request.getContextPath();//img请求用的根路径(项目名称)
		String[] fjxx = fileService.getFjList(dbh,mkbh,realPath).split("#",-1);
		System.err.println("---------------=========!!!!!!!!!!!!"+fjxx[0]+"#"+fjxx[1]);
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		return "{\"success\":true,\"fjView\":[" + fjxx[0] + "],\"fjConfig\":[" + fjxx[1] + "]}";
//		return mv;
		
	}
	

	/**
	 * 只有文件上传，不做其他操作
	 * @param request
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @return 返回文件路径
	 */
	@RequestMapping("/onlyFileUpload")
	@ResponseBody
	public String onlyFileUpload(HttpServletRequest request) throws IllegalStateException, IOException{
		String fold = request.getParameter("fold");
		String rybh = request.getParameter("rybh");
		if(Validate.isNull(rybh)){
			rybh = LUser.getRybh();
		}
		String oldurl = Validate.isNullToDefault(request.getParameter("oldurl"),"") + "";
		String dayFold = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String contextPath = request.getContextPath();//img请求用的根路径
		String realPath = request.getServletContext().getRealPath("/");//文件保存时候的根路径
		// 转换为文件类型的request
		MultipartHttpServletRequest multipartRequest = null;
		try{
			multipartRequest = (MultipartHttpServletRequest) request;
		}catch(Exception e){
			e.printStackTrace();
			return "{\"success\":false,\"msg\":\"上传文件转换失败！\",\"url\":\"" + oldurl + "\",\"rybh\":\"" + rybh + "\"}";
		}
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
            	String pictureFile_name =  multipartFile.getOriginalFilename();
            	//新文件名称
            	String newFileName = get32UUID() + pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
            	//返回的路径
            	String urlpath = "imgFile/"+fold+"/"+dayFold+"/"+newFileName;
            	//上传图片
            	File uploadPic = new File(realPath+urlpath);
            	
            	if(!uploadPic.exists()){
            		uploadPic.mkdirs();
            	}
            	//向磁盘写文件
            	multipartFile.transferTo(uploadPic);
            	
            	return "{\"success\":true,\"msg\":\"上传成功！\",\"url\":\"" + realPath + urlpath + "\",\"rybh\":\"" + rybh + "\",\"filename\":\"" + pictureFile_name + "\"}";
            }
        }
        return "{\"success\":false,\"msg\":\"没有获取到需要上传的文件！\",\"url\":\"" + oldurl + "\",\"rybh\":\"" + rybh + "\"}";
	}
	
	/**
	 * 文件删除类
	 * @param key 文件路径
	 */
	@RequestMapping("/fileDelete")
	@ResponseBody
	public Object fileDelete(HttpServletRequest request,String key){
		String[] keys = key.split("@",-1);
		StringBuffer sb =  new StringBuffer();
		sb.append(ResourceBundle.getBundle("global").getString("FileDataPhysicalPath"));//E:\\pic
		sb.append("\\");
		sb.append(keys[0].replaceAll("//", "\\"));
		FileUtil.delFile(sb.toString());//从物理路径删除，就是从本地删除实际的文件
		fileService.doDelete(keys[1]);//从数据库中删除
		return "{}";
	}
	
	/**
	 * 不需要上传数据库的方法
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/commonFileUpload")
	@ResponseBody
	public String commonFileUpload(HttpServletRequest request) throws IllegalStateException, IOException{
		String dayFold = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String contextPath = request.getContextPath();//img请求用的根路径
		String realPath = request.getServletContext().getRealPath("/");//文件保存时候的根路径
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
            	String pictureFile_name =  multipartFile.getOriginalFilename();
            	//新文件名称
            	String newFileName = get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
            	//上传图片
            	File uploadPic = new File(realPath+"imgFile/commonFile/"+dayFold+"/"+newFileName);
            	if(!uploadPic.exists()){
            		uploadPic.mkdirs();
            	}
            	//向磁盘写文件
            	multipartFile.transferTo(uploadPic);
            	return "";
            }
        }
		return "";
	}
	@RequestMapping(value= "/xml",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object test(){
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version = \"1.0\" encoding = \"UTF-8\"?>");
		xml.append("<bocb2e version=\"100\" security=\"true\" lang=\"chs\"><head><termid>E127000000001</termid>");
		xml.append("<trnid>20060704001</trnid><custid>12345678</custid><cusopr>BOC</cusopr><trncod>b2e0002</trncod>");
		xml.append("<token>9TTQALYGH1</token></head><trans><trn-b2e0002-rs><status><rspcod>B002</rspcod>");
		xml.append("<errmsg>OK</errmsg></status><serverdt>20020615102045</serverdt></trn-b2e0002-rs></trans></bocb2e>");
		return xml.toString();
	}
	
	/**
	 * 检查附件是否全部上传完
	 */
	@RequestMapping(value="/dosfscfjlx",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public String dosfscfjlx(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String mkbh = this.getPageData().getString("mkbh");
		String keyid = this.getPageData().getString("dbh");
		String[] dbharr = keyid.split(",");
		List list = fileService.dobxscfjlx(mkbh);//必须上传的类型
		StringBuffer msg = new StringBuffer();//存放错误信息
		Map map;
		int cnt = 0;
		for(int i = 0; i < dbharr.length; i++){
			String dbh = dbharr[i];
			String lxbh = fileService.findLxbh(dbh);//已经上传的类型
			
			for(int j = 0; j < list.size(); j++){
				map = (Map)list.get(j);
				if((lxbh).indexOf(map.get("LXBH")+"") < 0){
					++cnt;
					if(cnt == 10){
						msg.append("...");
						return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
					}
					else{
						msg.append("单据号为：" + dbh + "的单据缺少【" + map.get("LXMC") + "】类型的图片<br/>");
					}
				}
			}
		}
		
		if(msg.length() > 0){
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
		else{
			return "{\"success\":true,\"msg\":\"必传的图片均已上传\"}";
		}
	}
}
