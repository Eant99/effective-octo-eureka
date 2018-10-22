package com.googosoft.service.zffs;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.controller.jzmb.JzmbExportExcel;
import com.googosoft.controller.zffs.zffsdyszExportExcel;
import com.googosoft.dao.wsbx.FykmdyszDao;
import com.googosoft.dao.zffs.ZffsdyszDao;
import com.googosoft.pojo.wsbx.jcsz.Cw_zffsdzb;

@Service("zffsdysz")
public class zffsdyszService {
	
	@Resource(name="zffsdyszDao")
	private ZffsdyszDao zffsdyszDao;
	
	
	
	/**
	 * 获取实体类
	 * @param Cw_fykmdzb
	 * @return
	 */
	public Map<?, ?> getObjectById(String guid,String sszt,String kjzd) {
		return zffsdyszDao.getObjectByGuId(guid,sszt,kjzd);
	}
	/**
	 * 增加支付方式对照表
	 */
	@Transactional
    public int doAdd(Cw_zffsdzb zffsb) {
    	return zffsdyszDao.doAdd(zffsb);
    	
    }
	/**
	 * 
	 * @param zffs
	 * @param kjzd
	 * @param sszt
	 * @param guid
	 * @return
	 */
	public boolean checkZffs(String zffs,String kjzd,String sszt,String guid){
		return zffsdyszDao.checkZffs(zffs, kjzd, sszt, guid);
	}
    /**
	 * 增加支付方式对照表
	 */
    public int doupdate(Cw_zffsdzb zffsb) {
    	return zffsdyszDao.doupdate(zffsb);
    	
    }
    /**
	 * 删除
	 * 
	 * @param Cw_fykmdzb
	 * @return
	 */
	public int doDel(String guid) {
		int result = zffsdyszDao.doDel(guid);

		return result;
	}
	
	/**
	 * 导出教师信息Excel   wzd
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl, String guid,String searchValue, String rybh, String s1) {
		List<Map<String, Object>> dwList = this.zffsdyszDao.getJsList(guid,searchValue,rybh,s1);
		String Title = "支付方式科目应对设置";
		String[] title = new String[] { "序号", "支付方式", "科目名称", "借贷方向" };
		Map<String, Object> dataMap = zffsdyszExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
}
