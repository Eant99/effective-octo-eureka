package com.googosoft.service.cwbb;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.SrfyjExportExcel;
import com.googosoft.controller.cwbb.expExcel.ZcfzExportExcel;
import com.googosoft.dao.cwbb.SrfyjDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("srfyjService")
public class SrfyjService extends BaseService {
	@Resource(name = "srfyjDao")
	private SrfyjDao srfyDao;
	
	/**
	 * 编制单位信息
	 * @return
	 */
	public Map<String,Object> getBzdw(){
		return srfyDao.getBzdw();
	}
	
	/**
	 * 判断Cw_srfyb是否已经存入数据
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @return
	 */
	public int checkSrfy(String bblx,String sysDate,String sszt,String jzpz){
		return srfyDao.checkSrfy(bblx, sysDate, sszt,jzpz);
	}
	
	/**
	 * 数据集合
	 * 
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @return
	 */
	public List<Map<String, Object>> getResultList(String bblx, String sysDate,
			String sszt,String jzpz) {
		return srfyDao.getResultList(bblx, sysDate, sszt, jzpz);
	}
	
	/**
	 * 调用存储过程返回list
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @param jzpz
	 * @param bzdw
	 * @return
	 */
	public List<Map<String, Object>> getResultListByPro(String bblx, String sysDate,
			String sszt,String jzpz,String bzdw,String kjzd) {
		return srfyDao.getResultListByPro(bblx, sysDate, sszt, jzpz, bzdw,kjzd);
	}
	/**
	 * 保存
	 * @param list
	 * @return
	 */
	public boolean doSave(List list){
		return srfyDao.doSave(list);
	}

	public Object expExcel(String realfile, String shortfileurl,String searchValue, String sql,String flag) {
		List<Map<String, Object>> dwList = this.srfyDao.getList(searchValue,sql);
		String Title = "收入支出月度";
		String[] title = new String[] { "序号", "项目","本月数" ,"本年累积数" };
		if(Validate.noNull(flag)){
			Title = "收入支出年度";
		 title = new String[] { "序号", "项目","上年数" ,"本年数" };
		}
		Map<String, Object> dataMap = SrfyjExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}

	//获取会计制度
	public String getKjzd(String sszt, String sysDate) {
		return srfyDao.getkjzd(sszt,sysDate);
	}
	/**
	 * 通过存储过程获得收入支出决算总表
	 * @param jsyf
	 * @param sfjz
	 * @param ztgid
	 * @param jzpz
	 * @return
	 */
	public List getSrzcjszbListByPro(String jsyf, String sfjz, String ztgid, String jzpz) {
		return srfyDao.getSrzcjszbListByPro(jsyf,sfjz,ztgid,jzpz);
	}

	public boolean doSaveSrzcjszb(List list, String sfjz, String jzpz) {
		return srfyDao.doSaveSrzcjszb(list,sfjz,jzpz);
	}
/**
 * 从上一次保存的位置获取收入支出决算总表数据
 * @param sfjz 
 * @param jzpz 
 * @param jsyf 
 * @return
 */
	public List getSrzcjszbList(String jsyf, String jzpz, String sfjz) {
		return srfyDao.getSrzcjszbList(jsyf,jzpz,sfjz);
	}
}
