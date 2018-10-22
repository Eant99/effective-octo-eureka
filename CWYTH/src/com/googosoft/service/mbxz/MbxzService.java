package com.googosoft.service.mbxz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.mbxz.MbxzDao;
import com.googosoft.pojo.ysgl.CW_XMBQMXB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("mbxzService")
public class MbxzService extends BaseService{
	@Resource(name="mbxzDao")
	public MbxzDao mbxzDao;

	/**
	 * 获取所有模板
	 * @return
	 */
	public List getMbList() {
		return mbxzDao.getMbList();
	}
	
	public int doDelMxb(String zbid) {	
	    int i = mbxzDao.doDelMxb(zbid);	
		return i;
    }

	public int doSaveMxb(String fybh,String fymc) {
		int i = mbxzDao.doSaveMxb(fybh,fymc);
		return i;
    }
	/**
	 * 修改状态
	 * @param zt
	 * @param gid
	 * @return
	 */
	public int doZtsz(String zt,String gid) {
		int result = mbxzDao.doZtsz(zt,gid);
		return result;
	}
	
	/**
	 * 账表模板保存
	 * @param mb
	 * @param gid
	 * @return
	 */
	public boolean doSave(String mb,String gid) {
		 
		return mbxzDao.doSave(mb,gid);
	}
	
	public List getFybh() {
		return mbxzDao.getFybh();
	}
	public Map getZzzy() {
		return mbxzDao.getZzzy();
	}
    /**
     * 保存 凭证删除
     * @param ms
     * @return 
     */
	public boolean doSavePzsc(String ms, String kmbh,String xmbh,String bmbh,String xxjjhj,String xxjjhd,String xxjjzj,String xxjjzd,String xyjjj,String xyjjd,String glf,String xxxm,String xyxm,String xxxmbmbh,String xyxmbmbh) {
		return mbxzDao.doSavePzsc(ms,kmbh,xmbh,bmbh,xxjjhj,xxjjhd,xxjjzj,xxjjzd,xyjjj,xyjjd,glf,xxxm,xyxm,xxxmbmbh,xyxmbmbh);
	} 
	
	public int doSavePzscscsc(String ms) {
		return mbxzDao.doSavePzscscsc(ms);
	} 
	 /**
     * 保存 转账摘要
     * @param ms
     * @return 
     */
	public int doSaveZzzy(String ms) {
		return mbxzDao.doSaveZzzy(ms);
	} 
	 /**
     * 保存 打印信息
     * @param ms
     * @return 
     */
	public boolean doSaveDyxx(PageData pd) {
		return mbxzDao.doSaveDyxx(pd);
	} 
	/**
	 * 报销类型金额设置
	 * @param map
	 * @return
	 */
	public int doSaveBxlx(Map map) {
		return mbxzDao.doSaveBxlx(map);
	}
	public Map getBxlxInfo(){
		return mbxzDao.getBxlxInfo();
	}
	/**
	 * 获取打印信息参数
	 * @return
	 */
	public List getDyxx() {
		return mbxzDao.getDyxx();
	}
}
