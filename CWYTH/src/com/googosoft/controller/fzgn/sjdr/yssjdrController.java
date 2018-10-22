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
import com.googosoft.pojo.fzgn.sjdr.ZC_ZJB_DR;
import com.googosoft.service.sjdr.yssjdrService;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/yssjdr")
public class yssjdrController extends BaseController{
	@Resource(name="yssjdrService")
	private yssjdrService yssjdrService;//单例
	
	/**
	 * 获取数据导入页面
	 * @return
	 */
	@RequestMapping(value="/getPage")
	public ModelAndView getPage(){
    	ModelAndView mv = this.getModelAndView();
    	mv.setViewName("fzgn/sjdr/yssjdr_edit");
    	return mv;
    }
	/**
	 *  导入上传
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
		mv.setViewName("fzgn/sjdr/yssjdr_edit");
		return mv;
	}
	/*** 获取弹窗页面*/
	@RequestMapping(value="/getListPage")
	@ResponseBody
	public ModelAndView getListPage(){
		String file = this.getPageData().getString("file");
		String sclx = this.getPageData().getString("sclx");
		ModelAndView mv = this.getModelAndView();
		List listbt = new ArrayList();
		listbt=yssjdrService.getYssjBt(file);
		if("00".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_all");
		}else if("01".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_fw");
		}else if("02".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_gzw");
		}else if("03".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_td");
		}else if("04".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_ybsb");
		}else if("05".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_jtys");
		}else if("06".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_wwclp");
		}else if("07".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_ts");
		}else if("08".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_jjbf");
		}else if("09".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_zw");
		}else if("10".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_dongwu");
		}else if("11".equals(sclx)){
			mv.setViewName("fzgn/sjdr/sjdz_wxzc");
		}
		mv.addObject("listbt", listbt);
		mv.addObject("file", file);
		return mv;
	}
	/***导入数据*/
	@RequestMapping(value="/insertYssjDr",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object insertYssjGzw(ZC_ZJB_DR zjb){
		String file = this.getPageData().getString("file");
		ModelAndView mv = this.getModelAndView();
		String error = yssjdrService.insertYssjDr(file,zjb);
		mv.addObject("error", Validate.isNullToDefault(error, "导入失败！！"));
		return error;
	}
}