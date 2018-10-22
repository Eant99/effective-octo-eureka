package com.googosoft.controller.systemset.cssz;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.system.user.UserService;
import com.googosoft.service.systemset.cssz.GrszService;

@Controller
@RequestMapping(value="/grsz")
public class GrszController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;
	@Resource(name="grszservice")
	private GrszService grszservice;
	@Resource(name="dictService")
	private DictService dictService;
	@Resource(name="userService")
	private UserService userService;

	/**
	 * 首页获取个人详细信息
	 * @return
	 */
	@RequestMapping(value="/goPersonSetPage")
	public ModelAndView goPersonSetPage(){
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>>  whcd = dictService.getDict(Constant.WHCD);//文化程度
		mv.addObject("whcdList",whcd);
		Map<?, ?> map = userService.getObjectById(LUser.getRybh());
		mv.addObject("ryb",map);
		mv.addObject("rybh",LUser.getRybh());
		mv.setViewName("systemset/cssz/grxx_Edit");
		return mv;
	}
	/**
	 * 修改个人信息
	 */
	@RequestMapping(value="/doSavePersonInfo",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSavePersonInfo(GX_SYS_RYB ryb){
		int result;
		result = userService.doSavePersonInfo(ryb);
		if(result==1){
			return "{success:true,msg:'信息保存成功！'}";
		}else{
			return  "{success:false,msg:'信息保存失败！'}";
		}
	}
	
	/**
	 * 跳转个人选项设置设置页面
	 * @return
	 */
	@RequestMapping(value="/goGrxxPage")
	public ModelAndView goGrxxPage(){
		ModelAndView mv = this.getModelAndView();
		Map map = grszservice.getGrxx(LUser.getRybh());
		String rybh = this.getPageData().getString("rybh");
		mv.addObject("map",map);
		mv.addObject("rybh",rybh); 
		//读取密保问题
		Map mapMmzh = grszservice.getMmzh(LUser.getRybh());
		mv.addObject("mapMmzh",mapMmzh); 
		mv.setViewName("systemset/cssz/grxx_main");
		return mv;
	}
	/**
	 * 获取个人外语水平页面
	 */
	@RequestMapping(value="/goGrWyspPage")
	public ModelAndView goGrWyspPage(){
		ModelAndView mv = this.getModelAndView();
		Map map = grszservice.getGrxx(LUser.getRybh());
		mv.addObject("map",map);
		List wysp =grszservice.getWysp(LUser.getRybh());//获取个人外语水平信息
		mv.addObject("wysp",wysp);
		mv.setViewName("systemset/cssz/grxx_wysp");
		return mv;
	}
	/**
	 * 获取个人论文情况页面
	 */
	@RequestMapping(value="/goGrLwqkPage")
	public ModelAndView goGrLwqkPage(){
		ModelAndView mv = this.getModelAndView();
		Map map = grszservice.getGrxx(LUser.getRybh());
		mv.addObject("map",map);
		List lwqk =grszservice.getLwqk(LUser.getRybh());//获取个人论文情况
		mv.addObject("lwqk",lwqk);
		mv.setViewName("systemset/cssz/grxx_lwqk");
		return mv;
	}
	/**
	 * 获取个人进修情况页面
	 */
	@RequestMapping(value="/goGrJxqkPage")
	public ModelAndView goGrJxqkPage(){
		ModelAndView mv = this.getModelAndView();
		Map map = grszservice.getGrxx(LUser.getRybh());
		mv.addObject("map",map);
		List jxqk =grszservice.getJxqk(LUser.getRybh());//获取个人进修情况
		mv.addObject("jxqk",jxqk);
		mv.setViewName("systemset/cssz/grxx_jxqk");
		return mv;
	}
	/**
	 * 获取个人著作情况页面
	 */
	@RequestMapping(value="/goGrZzqkPage")
	public ModelAndView goGrZzqkPage(){
		ModelAndView mv = this.getModelAndView();
		Map map = grszservice.getGrxx(LUser.getRybh());
		mv.addObject("map",map);
		List zzqk =grszservice.getZzqk(LUser.getRybh());//获取个人著作情况
		mv.addObject("zzqk",zzqk);
		mv.setViewName("systemset/cssz/grxx_zzqk");
		return mv;
	}
	/**
	 * 获取个人成果奖励页面
	 */
	@RequestMapping(value="/goGrCgjlPage")
	public ModelAndView goGrCgjlPage(){
		ModelAndView mv = this.getModelAndView();
		Map map = grszservice.getGrxx(LUser.getRybh());
		mv.addObject("map",map);
		List cgjl =grszservice.getCgjl(LUser.getRybh());//获取个人成果奖励信息
		mv.addObject("cgjl",cgjl);
		mv.setViewName("systemset/cssz/grxx_cgjl");
		return mv;
	}
	/**
	 * 人员头像上传
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/PerPhotoUpload")
	@ResponseBody
	public String PerPhotoUpload(HttpServletRequest request) throws IllegalStateException, IOException{
		String id = request.getParameter("id");
		String fold = request.getParameter("fold");
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
            	File uploadPic = new File(realPath+"imgFile/"+fold+"/"+newFileName);
            	if(!uploadPic.exists()){
            		uploadPic.mkdirs();
            	}
            	//向磁盘写文件
            	multipartFile.transferTo(uploadPic);
            	String urlpath = "imgFile/"+fold+"/"+newFileName;
            	grszservice.doUpdRyphoto(id,urlpath);
            	return "{\"error\":\"\",\"msg\":\"success\","
            			+ "\"initialPreviewConfig\":[{\"caption\":\""+newFileName+"\",\"width\":\"120px\",\"key\":\"imgFile/"+fold+"/"+newFileName+"\",\"size\":\"\"}],"
            			+ "\"initialPreview\":[\""+contextPath+"/imgFile/"+fold+"/"+newFileName+"\"]}";
            }
        }
        return "{\"error\":\"fail\",\"msg\":\"\"}";
	}
}
