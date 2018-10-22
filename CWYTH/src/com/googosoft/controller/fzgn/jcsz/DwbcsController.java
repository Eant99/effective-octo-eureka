package com.googosoft.controller.fzgn.jcsz;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.googosoft.controller.base.BaseController;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.DwbService;
import com.googosoft.service.fzgn.jcsz.XsxxService;

/**
 * 单位信息控制类
 * @author master
 */
@Controller
@RequestMapping(value="/dwb")
public class DwbcsController extends BaseController{
	@Resource(name="dwbService")
	private DwbService dwbService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
}
