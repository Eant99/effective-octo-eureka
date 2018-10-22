package com.googosoft.service.cwbb;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.SrfyExportExcel;
import com.googosoft.dao.cwbb.SrfyDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("srfyService")
public class SrfyService extends BaseService {
	@Resource(name = "srfyDao")
	private SrfyDao srfyDao;
	
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
			String sszt,String jzpz,String bzdw) {
		return srfyDao.getResultListByPro(bblx, sysDate, sszt, jzpz, bzdw);
	} 
	
	/**
	 * 保存
	 * @param list
	 * @return
	 */
	public boolean doSave(List list){
		return srfyDao.doSave(list);
	}

	public Object expExcel(String realfile, String shortfileurl,String searchValue,String flag) {
		List<Map<String, Object>> dwList = this.srfyDao.getList();
		String Title = "";
		String[] title =new String[] { "序号", "项目","本月数","本年累积数"  };
		if(Validate.noNull(flag)){
			Title = "收入费用月度";
			 title = new String[] { "序号", "项目","本月数","本年累积数"  };
		}else{
			Title = "收入费用年度";
			title = new String[] { "序号", "项目","本年数","上年累积数"  };
		}
		Map<String, Object> dataMap = SrfyExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
}
