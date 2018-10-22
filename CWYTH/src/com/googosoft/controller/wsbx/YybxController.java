package com.googosoft.controller.wsbx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.controller.base.BaseController;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.wsbx.YybxService;
import com.googosoft.util.PageData;
@Controller
@RequestMapping(value = "/wsbx/yybx")
public class YybxController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;

	@Resource(name = "dictService")
	private DictService dictService;
	
	@Resource(name = "yybxService")
	private YybxService yybxService;
	/**
	 * 跳转预约报销列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goYybxListPage")
	public ModelAndView goYybxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd(EE)");
		//获取当前天日期
		String sysDate = sdf.format(new Date());
		//获取日历
		Calendar calendar = Calendar.getInstance();
		//给日历赋值当前天
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		String beforeDate = sdf.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		String afterDate = sdf.format(calendar.getTime());
		String sql = "SELECT KSSJ,JSSJ,YXYYRS FROM CW_YYSZMXB T";
		List<Map<String, Object>> list = yybxService.getTime();
		mv.addObject("beforeDate", beforeDate);
		mv.addObject("sysDate", sysDate);
		mv.addObject("afterDate", afterDate);
		mv.addObject("list", list);
		mv.setViewName("wsbx/yybx/yybx");
		return mv;
	}
	
}
