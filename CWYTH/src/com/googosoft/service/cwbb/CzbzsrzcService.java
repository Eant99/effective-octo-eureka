package com.googosoft.service.cwbb;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.CzbzsrzcExcel;
import com.googosoft.controller.cwbb.expExcel.ZcfzExportExcel;
import com.googosoft.dao.cwbb.CzbzsrzcDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

/**
 * 
 * @author wangzhiduo
 * @date 2018-1-2下午7:15:53
 */
@Service("czbzsrzcService")
public class CzbzsrzcService extends BaseService{
	@Resource(name="czbzsrzcbDao")
	private CzbzsrzcDao czbzsrzcDao;
	
	/**
	 * 编制单位信息
	 * @return
	 */
	public Map<String,Object> getBzdw(){
		return czbzsrzcDao.getBzdw();
	}
	/**
	 * 判断是否已经存入数据
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @return
	 */
	public int checkCzbz(String bblx,String sysDate,String sszt,String jzpz){
		return czbzsrzcDao.checkCzbz(bblx, sysDate, sszt,jzpz);
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
		return czbzsrzcDao.getResultListByPro(bblx, sysDate, sszt, jzpz, bzdw,kjzd);
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
		return czbzsrzcDao.getResultList(bblx, sysDate, sszt, jzpz);
	}
	
	/**
	 * 保存
	 * @param list
	 * @return
	 */
	public boolean doSave(List list){
		return czbzsrzcDao.doSave(list);
	}
/**
 * 财政补助收入支出导出
 * @param realfile
 * @param shortfileurl
 * @param searchValue
 * @return
 */
	public Object expExcel(String realfile, String shortfileurl,String searchValue) {
		List<Map<String, Object>> list = this.czbzsrzcDao.getListCzbzsrzc(searchValue);
		String Title = "财政补助收入支出表";
		String[] title = new String[] { "序号", "项目", "本年数", "上年数"  };
		Map<String, Object> dataMap = CzbzsrzcExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	//获取会计制度
		public String getKjzd(String sszt, String sysDate) {
			return czbzsrzcDao.getkjzd(sszt,sysDate);
		}

}
