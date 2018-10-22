package com.googosoft.service.sqspcx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.sqspcx.sqspcxExportExcel;
import com.googosoft.controller.wsbx.gwjdf.gwjdfsqExportExcel;
import com.googosoft.dao.sqspcx.SqspcxDao;
import com.googosoft.service.base.BaseService;

/**
 * 单位信息service
 * @author master
 */
@Service("sqspcxService")
public class SqspcxService extends BaseService{
	
	@Resource(name="sqspcxDao")
	public SqspcxDao sqspcxzDao;
	
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getObjectById1(String guid) {
		return sqspcxzDao.getObjectById1(guid);
	}
//	/**
//	 * 新增
//	 * @param dwb
//	 * @return
//	 */
//	public int doAdd(PageData pd){
//		int result = grjfszDao.doAdd(pd);
//		//if(result>0){
//			//doAddOplog(OplogFlag.ADD,"单位基础信息",dwb.getDwbh());
//		//}
//		return result;
//	}
//	/**
//	 * 获取实体类
//	 * @param dwbh
//	 * @return
//	 */
//	public Map<?, ?> getObjectById1(String guid) {
//		return wldwszDao.getObjectById(guid);
//	}
//	/**
//	 * 获取实体类
//	 * @param dwbh
//	 * @return
//	 */
//	public Map<?, ?> getObjectById(String dwbh) {
//		return grjfszDao.getObjectById(dwbh);
//	}
//	/**
//	 * 修改
//	 * @param dwb
//	 * @return
//	 */
//	public int doUpdate(PageData pd,String dwbh) {
//		int result = grjfszDao.doUpdate(pd,dwbh);
//		if(result>0){
//			doAddOplog(OplogFlag.UPD,"单位基础信息");
//		}
//		return result;
//	}
//	/**
//	 * 复核
//	 */
////	public int goFhPage(PageData pd,String dwbh) {
////		int result = grjfszDao.goFhPage(pd,dwbh);
////		if(result>0){
////			doAddOplog(OplogFlag.UPD,"单位基础信息");
////		}
////		return result;
////	}	
//	public boolean goFhPage(PageData pd,String dwbh){
//		return grjfszDao.goFhPage(pd,dwbh);
//	}
//	/**
//	 * 删除
//	 * @param dwb
//	 * @return
//	 */
//	public int doDelete(String dwbh) {
//		int result = grjfszDao.doDelete(dwbh);
//		if(result>0){
//			doAddOplog(OplogFlag.DEL,"单位基础信息",dwbh);
//		}
//		return result;
//	}
//	
//	public List insertJcsj(String file) {
//		return grjfszDao.insertJcsj(file);
//	}
	
	/**
	 * 判断部门号的重复性
	 * @param dwb
	 * @return
	 *//*
	public boolean doCheckDwbh(String bmh) {
		boolean result = dwbDao.doCheckDwbh(bmh);
		return result;
	}
	
	*//**
	 * 删除时，如果单位下有已处置的资产，则禁用该单位
	 * @param dwbh
	 * @return
	 *//*
	public int jydW(String dwbh){
		int i = dwbDao.jyDw(dwbh);
		if(i>0){
			doAddOplog(OplogFlag.DEL,"单位基础信息：禁用",dwbh);
		}
		return i;
	}
	*//**
	 * 删除单位时验证该单位下是否有人员或下级单位或资产
	 * @param DWBHS
	 * @return
	 *//*
	public String validateForRyOrXjdwOrZc(String DWBHS){
		return dwbDao.validateForRyOrXjdwOrZc(DWBHS);
	}

	*//**
	 * 单位信息批量赋值
	 * @param ids
	 * @param ziduan
	 * @param zdValue
	 * @return
	 * @throws ParseException
	 *//*
	public int doPlfuzhi(String ids,String ziduan,Object zdValue) throws ParseException  {
		if(ziduan.equals("sjdw")){
			zdValue = WindowUtil.getXx(zdValue+"", "D");
		}else if(ziduan.equals("dwld")){
			zdValue = WindowUtil.getXx(zdValue+"", "R");
		}else if(ziduan.equals("jlrq")){
			zdValue = new SimpleDateFormat("yyyy-MM-dd").parse(zdValue+"");
		}
		return dwbDao.doPlfuzhi(ids, ziduan, zdValue);
		
	}
	
	*//**
	 * 单位机构设置
	 * 通过部门号(名称)查询单位编号
	 * @param dwmc (bmh)mc格式
	 * @return
	 *//*
	public String findDwbhByDwmc(String dwmc) {
		String  dwbh = dwbDao.findDwbhByDwmc(dwmc);
		if(Validate.isNull(dwbh)){
			dwbh="F";
		}
		return dwbh ;
	}
	*//**
	 * 通过dbwh获取（bmh）mc格式
	 * @param dwbh
	 * @return
	 *//*
	public String getDwxx(String dwbh){
		return dwbDao.getDwxx(dwbh);
	}
	*//**
	 * 通过obj获得下级节点的数量
	 * @return
	 *//*
	public String getNewStatus(String dwbh) {
		return dwbDao.getNewStatus(dwbh);
	}
	*/
	/**
	 * 导出教师信息Excel   wzd
	 * @return
	 */
	public Object expExcel1(String realfile, String shortfileurl, String guid,String searchValue, String sql,Object dwbh2) {
		List<Map<String, Object>> dwList = this.sqspcxzDao.getJsList(sql,dwbh2);
		String Title = "事前审批查询";
		String[] title = new String[] { "序号", "审批状态", "单据编号","申请人", "所在部门","事前审批类型","申请日期"};
		Map<String, Object> dataMap = sqspcxExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	
}
