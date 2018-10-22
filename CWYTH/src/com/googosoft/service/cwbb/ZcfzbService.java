package com.googosoft.service.cwbb;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.ZcfzExportExcel;
import com.googosoft.dao.cwbb.ZcfzbDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;
@Service("zcfzbService")
public class ZcfzbService extends BaseService{
	@Resource(name="zcfzbDao")
	public ZcfzbDao zcfzbDao;
	public List<Map<String, Object>> getResultListByPro(String bbnd, String bbyf,
			String saasdm,String sfjz,String type,String ztgid,String rq,String jzpz) {
		 return zcfzbDao.getResultListByPro(bbnd, bbyf, saasdm, sfjz, type, ztgid, rq);
	} 
	
	/**
	 * 编制单位信息
	 * @return
	 */
	public Map<String,Object> getBzdw(){
		return zcfzbDao.getBzdw();
	}
	/**
	 * 保存
	 * @param list
	 * @return
	 */
	public boolean doSave(List list){
		return zcfzbDao.doSave(list);
	}
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "资产负债表(月度)";
		if(Validate.noNull(foo)){
			Title = "资产负债表(年度)";
		}
		String[] title = new String[] { "序号", "资产", "期末余额", "年初余额", "负债和净资产", "期末余额", "年初余额"  };
		Map<String, Object> dataMap = ZcfzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	/**
	 * 收入支出表
	 * @param realfile
	 * @param shortfileurl
	 * @param searchValue
	 * @param foo
	 * @return
	 */
	public Object expExcels(String realfile, String shortfileurl,String searchValue,String foo) {
		List<Map<String, Object>> list = this.zcfzbDao.getListZcfzb(searchValue);
		String Title = "资产负债表(月度)";
		if(Validate.noNull(foo)){
			Title = "资产负债表(年度)";
		}
		String[] title = new String[] { "序号", "资产", "年初余额", "年末余额", "负债和净资产", "年初余额", "年末余额"  };
		Map<String, Object> dataMap = ZcfzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	
	
	/**
	 * 旧收入支出表
	 * @param realfile
	 * @param shortfileurl
	 * @param searchValue
	 * @param list
	 * @param flag
	 * @return
	 */
	public Object expExcel2(String realfile, String shortfileurl,String searchValue,String bblx) {
		List<Map<String, Object>> list = this.zcfzbDao.getListJzcfzb(searchValue);
		String Title = "资产负债表(月度)";
		String[] title = new String[] { "序号", "资产", "期末余额", "期初余额", "负债和净资产", "期末余额", "期初余额"  };
		if(bblx.equals("1")){
			Title = "资产负债表(年度)";
			title = new String[] { "序号", "资产", "期末余额", "年初余额", "负债和净资产", "期末余额", "年初余额"  };
		}
		Map<String, Object> dataMap = ZcfzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	/**
	 * 新资产负债表导出
	 */
	public Object expExcel3(String realfile, String shortfileurl,List zclist) {
		List<Map<String, Object>> list = zclist;
		String Title = "资产负债表";
		String[] title = new String[] { "序号", "资产", "期末余额", "期初余额", "负债和净资产", "期末余额", "期初余额"  };
		Map<String, Object> dataMap = ZcfzExportExcel.exportExcel2(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	
/**
 * 查询月份
 * @return
 */
	public List<Map<String, Object>> getMonth() {
		return this.zcfzbDao.getMonths();
	}
}
