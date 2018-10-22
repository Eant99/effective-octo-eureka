package com.googosoft.service.fzgn.jcsz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.fzgn.jcsz.expExcel.XsExportExcel;
import com.googosoft.dao.fzgn.jcsz.XsxxDao;
import com.googosoft.pojo.fzgn.jcsz.CW_JSYHZHB;
import com.googosoft.pojo.fzgn.jcsz.CW_XSXXB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("xsxxService")
public class XsxxService extends BaseService{
	@Resource(name = "xsxxDao")
	private XsxxDao xsxxDao;

	/**
	 * 检测学号是否已存在
	 * 
	 * @return
	 */
	public boolean checkRyb(String xh) {
		return xsxxDao.checkXh(xh);
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public int doAdd(CW_XSXXB xsxx) {
		xsxx.setSzyx(CommonUtil.getDwXx(xsxx.getSzyx()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		xsxx.setCzrq(sdf.format(new Date()));
		int result = xsxxDao.doAdd(xsxx);
		if (result > 0) {
			doAddOplog(OplogFlag.ADD, "人员基础信息：增加", xsxx.getXh());
		}
		return result;
	}
	/**
	 * 获取教师银行账号实体类
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getObjectByIdYhzh(String guid) {
		return xsxxDao.getObjectByIdYhzh(guid);
	}
	/**
	 * 教师银行账号删除
	 * @param 
	 * @return
	 */
	public int doDeleteJsyhzh(String jsbh,CW_JSYHZHB jsyhzhb) {
		int result = xsxxDao.doDeleteJsyhzh(jsbh,jsyhzhb);
		return result;
	}
	/**
	 * 教师银行账号增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddJsyhzh(CW_JSYHZHB jsyhzhb) {
		
		int i = xsxxDao.doAddJsyhzh(jsyhzhb);
		
		return i;	
    }
	/**
	 * 查询当前登录人的id
	 * @param rybh
	 * @return
	 */
	public String getLoginIdByRybh(String rybh){
		return xsxxDao.getLoginIdByRybh(rybh);
	}
	public String getRybhByRybh(String rybh){
		return xsxxDao.getRybhByRybh(rybh);
	}
	/**
	 * 获取实体
	 * @param rybh
	 * @return
	 */
		
		public Map<String, Object> getObjectById(String rybh){
			return xsxxDao.getObjectById(rybh);
	}
	/**
	 * 修改
	 * @param ryb
	 * @return
	 */
	public int doUpdate(CW_XSXXB xsxx){    
		xsxx.setSzyx(CommonUtil.getDwXx(xsxx.getSzyx()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		xsxx.setCzrq(sdf.format(new Date()));
		int result = xsxxDao.doUpdate(xsxx);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"学生基础信息：修改",xsxx.getGuid());
		}
		return result;
	}
	/**
	 * 删除
	 * @param rybh
	 * @return
	 */
	public int doDel(String xh){
		int result = xsxxDao.doDel(xh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"学生基础信息：删除",xh);
		}
		return result;
	}
	/**
	 * 单位机构设置
	 * 通过部门号(名称)查询单位编号
	 * @param dwmc (bmh)mc格式
	 * @return
	 */
	public String findZyxx(String dwmc,String dwbh) {
		String  dwbhs = xsxxDao.findZyxx(dwmc,dwbh);
		if(Validate.isNull(dwbhs)){
			dwbh="F";
		}
		return dwbhs ;
	}
	/**
	 * 专业list
	 * @param dwbh
	 * @return
	 */
	public List getZyxxList(String dwbh){
		return xsxxDao.findZyxxList(dwbh);
	}
	public List insertJcsj(String file) {
		return xsxxDao.insertJcsj(file);
	}

	/**
	 * 导出学生信息Excel   wzd
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl, String rybh, String guid,String searchValue) {
		List<Map<String, Object>> xsList = this.xsxxDao.getXsList(guid,searchValue,rybh);
		String Title = "学生信息";
		String[] title = new String[] { "序号", "学号", "姓名", "性别", "出生日期" ,"学生类别", "所在院系","专业","年级","班级","民族" ,"身份证件类型","身份证件号","政治面貌" };
		Map<String, Object> dataMap = XsExportExcel.exportExcel(realfile,shortfileurl, title, Title,xsList );
		return dataMap;
	}
}
