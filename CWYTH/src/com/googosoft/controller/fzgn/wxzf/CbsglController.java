package com.googosoft.controller.fzgn.wxzf;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
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
import com.googosoft.pojo.fzgn.wxzf.CW_CBSXX;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.wxzf.CbsglService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
/**
 * 承包商信息控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping("/cbsgl")
public class CbsglController extends BaseController{
	@Resource(name="cbsglService")
	private CbsglService objService;
	
	@Resource(name="dictService")
	private DictService dictService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	/**
	 * 跳转承包商信息列表页面
	 * @return
	 */
	@RequestMapping(value = "/goPageList")
	public ModelAndView goSecondPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();		
		mv.setViewName("fzgn/wxzf/cbsxx_list");
		return mv;

	}
	/**
	 * 获取承包商列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" ( select   A.GUID,");
		sql.append(" A.CBSBH,A.CBSMC,A.CBSWXH,A.LXR,A.LXDH,TO_CHAR(A.CBKSRQ,'YYYY-MM-DD')CBKSRQ,TO_CHAR(A.CBJSRQ,'YYYY-MM-DD')CBJSRQ,"
				+ "decode(a.sftj,'1','是','0','否') as sftjmc from CW_CBSXX A ) K");	
		pageList.setSqlText(" * ");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("GUID");
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
	 * 跳转承包商信息编辑页面
	 * @return
	 */
	@RequestMapping(value = "/goCbsxxPage")
	public ModelAndView goCbsxxPage() {
		ModelAndView mv = this.getModelAndView();		
		String operateType = this.getPageData().getString("operateType");		
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
			mv.addObject("cbsxx", map);			
			mv.addObject("guid", this.getPageData().getString("guid"));
		}
		mv.setViewName("fzgn/wxzf/cbsxx_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 保存承包商信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(CW_CBSXX cbsxx) {
		String operateType = this.getPageData().getString("operateType");
		String sftj = Validate.isNullToDefaultString(cbsxx.getSftj(), "0");
		cbsxx.setSftj(sftj);
		int result = 0;
		if ("C".equals(operateType)) {
			// 判断承包商编号是否重复		
				boolean checkrygh = objService.checkCbsbh(cbsxx.getCbsbh());
				if (checkrygh) {
					return MessageBox.show(false, "该承包商对应的编号已经存在，请重新输入！");
				}
			
			result = objService.doAdd(cbsxx);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！',cbsbh:'" + cbsxx.getCbsbh()
						+ "'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else if("U".equals(operateType)){
			result = objService.doUpp(cbsxx);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！',cbsbh:'" + cbsxx.getCbsbh()
						+ "'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
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
	//初始化文件
	public void iniFile(ModelAndView mv,String guid) {
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	}
}
