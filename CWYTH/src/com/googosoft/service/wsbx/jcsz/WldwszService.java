package com.googosoft.service.wsbx.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.net.aso.b;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.kjhs.WldwExportExcel;
import com.googosoft.dao.wsbx.jcsz.WldwszDao;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWMXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWSZ;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 往来单位设置Service
 * @author Administrator
 *
 */
@Service("wldwszService")
public class WldwszService extends BaseService{
	@Resource(name="wldwszDao")
	private WldwszDao wldwszDao;
	/**
	 * 往来单位设置增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd(CW_WLDWSZ wldwsz) {
		
			int i = wldwszDao.doAdd(wldwsz);
			
			return i;
		
	}
	/**
	 * 往来单位明细增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd1(CW_WLDWMXB wldwmxb) {
		
		int i = wldwszDao.doAdd1(wldwmxb);
		
		return i;	
    }
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getObjectById(String guid) {
		return wldwszDao.getObjectById(guid);
	}
	/**
	 * 获取往来单位设置制明细实体类
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getObjectById1(String guid) {
		return wldwszDao.getObjectById1(guid);
	}
	/**
	 *往来单位设置编辑操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doEdit(CW_WLDWSZ wldwsz) {
		
			int i = wldwszDao.doEdit(wldwsz);
			
			return i;
		
	}
	/**
	 * 往来单位明细删除
	 * @param 
	 * @return
	 */
	public int doDeleteWldwmx(String guid,CW_WLDWMXB wldwmxb) {
		int result = wldwszDao.doDeleteWldwmx(guid,wldwmxb);
		return result;
	}
	/**
	 * 先删后增
	 */
	public int delYhzh(String wlbh) {
		int result = wldwszDao.delYhzh(wlbh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"往来单位信息",wlbh);
		}
			return result;
	}
	
	
	/**
	 * 根据主键经济科目与部门对应设置删除操作
	 * @param dmxh
	 * @return
	 */
	public int doDelete(String guid) {
		int result = wldwszDao.doDelete(guid);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"往来单位信息",guid);
		}
			return result;
	}
	public boolean  checkWldwbhExist(PageData pd) {
		if(wldwszDao.selectListByWldwbh(pd).size() > 0) {
			return true;
		}else {
			return false;
		}
	}

	//加上银行卡，合并导出（wzd）
	public Object expExcel2(String realfile, String shortfileurl, String guid, String searchValue) {
		List<Map<String, Object>> dwList = this.wldwszDao.getList1(guid,searchValue);
//		List<Map<String, Object>> yhkList =  wldwszDao.getList2(guid);
		String Title = "往来单位设置信息";
		
//		for(Map<String, Object> map:dwList){
//			List<Map<String, Object>> yhk =new ArrayList<Map<String, Object>>();
//			for(Map<String, Object> map1:yhkList){
//				if(map.get("GUID").equals(map1.get("WLBH"))){
//					yhk.add(map1);
//				}
//			}
//			map.put("data",yhk);
//		}
		String[] title = new String[] { "序号", "单位编号", "户名", "单位简称", "单位类型", "单位地址","联系人" };
//		String[] title = new String[] { "序号", "单位编号", "单位名称", "单位简称", "单位类型", "单位地址","联系人","统一社会信用代码","办公电话","传真","开户银行名称","开户银行账号" };//银行卡暂时无法实现
		Map<String, Object> dataMap = WldwExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	/**
	 * 批量删除往来单位
	 */
	public int doDeletes(String guid) {
		int result = wldwszDao.doDeletes(guid);
		return result;
	}
}
