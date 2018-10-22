package com.googosoft.controller.fzgn.sjdr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.sjdr.sjdr_ddb;
import com.googosoft.pojo.fzgn.sjdr.sjdr_dwb;
import com.googosoft.pojo.fzgn.sjdr.sjdr_jcsj;
import com.googosoft.pojo.fzgn.sjdr.sjdr_ryb;
import com.googosoft.service.base.PageService;
import com.googosoft.service.sjdr.jcsjdrService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/jcsjdr")
public class jcsjdrController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="jcsjdrService")
	private jcsjdrService jcsjdrService;//单例
	
	/**
	 * 获取数据导入页面
	 * @return
	 */
	@RequestMapping(value="/getPage")
	public ModelAndView getPage(){
    	ModelAndView mv = this.getModelAndView();
    	mv.setViewName("fzgn/sjdr/jcsudr_edit");
    	return mv;
    }
	/**
	 *  导入上传
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws Exception
	 */
	@RequestMapping(value="/upload")
	public ModelAndView Upload(String sclx,MultipartFile imageFile) throws IllegalStateException, IOException{
		ModelAndView mv = this.getModelAndView();
		//原始文件名称
		String error="";
    	String pictureFile_name =  imageFile.getOriginalFilename();
    	if(Validate.isNull(pictureFile_name)){
    		error = "请选择文件上传！";
    		mv.addObject("error",error);
    	}else{
    		//新文件名称
        	String n = pictureFile_name.trim().substring(pictureFile_name.indexOf("."));
    		if("xlsx".equals(n)){
    			error = "请将.xlsx文件另存为.xls文件!";
    			mv.addObject("error",error);
    		}
    		if(!".xls".equals(n)){
    			error = "请选择.xls格式的文件!";
    			mv.addObject("error",error);
    		}
        	if(Validate.isNull(error)){
        		String newFileName = this.get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
        		String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
        		//上传文件
        		String file = realPath+"WEB-INF/file/excel/"+newFileName;
        		File uploadPic = new File(file);
        		if(!uploadPic.exists()){
        			uploadPic.mkdirs();
        		}
        		//向磁盘写文件
        		imageFile.transferTo(uploadPic);
        		mv.addObject("file", file);
        		mv.addObject("sclx", sclx);
        		mv.addObject("commonWin", "T");//弹窗标志
        	}
    	}
		mv.setViewName("fzgn/sjdr/jcsudr_edit");
		return mv;
	}
	/**
	 * 获取弹窗页面
	 * @return
	 */
	@RequestMapping(value="/getListPage")
	@ResponseBody
	public ModelAndView getListPage(){
		String file = this.getPageData().getString("file");
		String sclx = this.getPageData().getString("sclx");
		ModelAndView mv = this.getModelAndView();
		List listbt = new ArrayList();
		List list = new ArrayList();
		listbt = jcsjdrService.Excelxx(file);
		list = jcsjdrService.insertJcsj(file);
		if("ry".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_ry");
		}else if("dw".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_dw");
		}else if("dd".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_dd");
		}
		mv.addObject("listbt", listbt);
		mv.addObject("list", list);
		mv.addObject("file", file);
		return mv;
	}
	
	/**
	 * 导入数据-地点
	 * @return
	 */
	@RequestMapping(value="/insertJcsj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object insertJcsj(sjdr_jcsj jcsj){
		String file = this.getPageData().getString("file");
		String value = this.getPageData().getString("value");
		String flag = this.getPageData().getString("flag");
		ModelAndView mv = this.getModelAndView();
		String error = jcsjdrService.insertJcsj_jcsj(jcsj,file,value,flag);
		mv.addObject("error", Validate.isNullToDefault(error, "导入失败！！"));
		return error;
	}
}
