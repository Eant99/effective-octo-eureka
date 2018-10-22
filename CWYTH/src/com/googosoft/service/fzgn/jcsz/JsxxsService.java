package com.googosoft.service.fzgn.jcsz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.fzgn.jcsz.expExcel.JsxxsExportExcel;
import com.googosoft.dao.fzgn.jcsz.CW_JZGKZXXB;
import com.googosoft.dao.fzgn.jcsz.JsxxsDao;
import com.googosoft.pojo.fzgn.jcsz.CW_JSXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_JSYHZHB;
import com.googosoft.service.base.BaseService;

/**
 * 教师信息service
 * @author googosoft
 *
 */
@Service("jsxxsService")
public class JsxxsService extends BaseService {
	@Resource(name = "jsxxsDao")
	private JsxxsDao jsxxsDao;

	/**
	 * 检测学号是否已存在
	 * 
	 * @return
	 */
	public boolean checkRyb(String xh) {
		return jsxxsDao.checkXh(xh);
	}
	/**
	 * 检测排序序号是否已存在
	 * 
	 * @return
	 */
	public boolean checkPxxh(String pxxh,String guid) {
		return jsxxsDao.checkPxxh(pxxh,guid);
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	@Transactional
	public int doAdd(CW_JSXXB jsxx) {
		jsxx.setSzdw(CommonUtil.getDwXx(jsxx.getSzdw()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jsxx.setCzrq(sdf.format(new Date()));
		int result = jsxxsDao.doAdd(jsxx);
		if (result > 0) {
			doAddOplog(OplogFlag.ADD, "人员基础信息：增加", jsxx.getXh());
		}
		return result;
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public int doAdd1(CW_JZGKZXXB jsxx) {
		int result = jsxxsDao.doAdd1(jsxx);
		if (result > 0) {
			doAddOplog(OplogFlag.ADD, "人员基础信息：增加", jsxx.getXh());
		}
		return result;
	}
	/**
	 * 教师银行账号删除
	 * @param 
	 * @return
	 */
	public int doDeleteJsyhzh(String jsbh,CW_JSYHZHB jsyhzhb) {
		int result = jsxxsDao.doDeleteJsyhzh(jsbh,jsyhzhb);
		return result;
	}
	/**
	 * 教师银行账号增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddJsyhzh(CW_JSYHZHB jsyhzhb) {
		
		int i = jsxxsDao.doAddJsyhzh(jsyhzhb);
		
		return i;	
    }
	/**
	 * 获取教师银行账号实体类
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getObjectByIdYhzh(String guid) {
		return jsxxsDao.getObjectByIdYhzh(guid);
	}
	/**
	 * 查询当前登录人的id
	 * 
	 * @param rybh
	 * @return
	 */
	public String getLoginIdByRybh(String rybh) {
		return jsxxsDao.getLoginIdByRybh(rybh);
	}
	
	/**
	 * 获取教职工guid
	 * @param guid
	 * @return
	 */
	public String getLoginIdByKz(String guid) {
		return jsxxsDao.getLoginIdByKz(guid);
	}

	/**
	 * 获取实体
	 * 
	 * @param rybh
	 * @return
	 */

	public Map<String, Object> getObjectById(String rybh) {
		return jsxxsDao.getObjectById(rybh);
	}
	
	/**
	 * 获取实体类
	 * 
	 * @param rybh
	 * @return
	 */
	public Map<String, Object> getJzgkzxxById(String guid) {
		return jsxxsDao.getJzgkzxxById(guid);
	}

	/**
	 * 修改
	 * 
	 * @param ryb
	 * @return
	 */
	public int doUpdate(CW_JSXXB jsxx) {
		jsxx.setSzdw(CommonUtil.getDwXx(jsxx.getSzdw()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jsxx.setCzrq(sdf.format(new Date()));
		int result = jsxxsDao.doUpdate(jsxx);
		if (result > 0) {
			doAddOplog(OplogFlag.UPD, "教师基础信息：修改", jsxx.getGuid());
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param rybh
	 * @return
	 */
	public int doDel(String guid) {
		int result = jsxxsDao.doDel(guid);
		if (result > 0) {
			doAddOplog(OplogFlag.DEL, "教师基础信息：删除", guid);
		}
		return result;
	}
	
	/**
	 * 教职工信息导入
	 * @param file
	 * @return
	 */
	public List insertJcsj(String file) {
		return jsxxsDao.insertJcsj(file);
	}


	
	/**
	 * 导出教师信息Excel   wzd
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl, String guid,String searchValue, String rybh, String s1, String s2, String s3,
			String s4, String s5, String s6, String s7, String s8, String s9,
			String s10,String dwbh) {
		List<Map<String, Object>> dwList = this.jsxxsDao.getJsList(guid,searchValue,rybh,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,dwbh);
		String Title = "教师信息";
		String[] title = new String[] { "序号", "工号", "姓名", "性别", "所在单位" ,"职务", "职称","文化程度","来校时间","出生日期","民族","身份证件类型","身份证件号","婚姻状况","政治面貌","联系方式","邮箱","在职类型","在职人员来源","参加工作时间","工龄" };
		Map<String, Object> dataMap = JsxxsExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
}
