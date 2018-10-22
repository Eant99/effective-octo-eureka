package com.googosoft.service.kjhs.szjz;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.pzxx.PzlrDao;
import com.googosoft.dao.kjhs.szjz.SzjzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("szjzService")
public class SzjzService extends BaseService{
	
	@Resource(name="szjzDao")
	public SzjzDao szjzDao;
	@Resource(name="pzlrDao")
	public PzlrDao pzlrDao;
	
	
	/** 获取凭证模板编辑信息
   	 * @param
   	 * @return
   	 */
   	public List<Map<String, Object>> getJeByKmbh(String zjid,String nd,String pzrq,String sszt,String kjzd,String zrkmbh) {
   		return szjzDao.getJeByKmbh(zjid,nd,pzrq,sszt,kjzd,zrkmbh);
   	}
   	/** 通过zjid获得zrkmbh
   	 * @param
   	 * @return
   	 */
   	public List<Map<String, Object>> getZrkmByid(String guid,String sszt) {
   		return szjzDao.getZrkmByid(guid,sszt);
   	}
   	/** 获取凭证模板明细信息
   	 * @param
   	 * @return
   	 */
   	public List<Map<String, Object>> getpzmbmxByguid(String guid) {
   		return szjzDao.getjzmbmxbyguid(guid);
   	}
   	/** 获取期末结账信息
   	 * @param
   	 * @return
   	 */
   	public Map<String, Object> queryQmjz(String sszt) {
   		return szjzDao.queryQmjz(sszt);
   	}
   	/**
   	 * 凭证录入明细表增加数据
   	 * @param map
   	 * @return
   	 */
   	public int doAddpzlrmx(Map map) {
   		szjzDao.doAddpzlrmx(map);
		return 0;
   		
   	}
   	/**
   	 * 凭证录入明细表增加数据
   	 * @param map
   	 * @return
   	 */
   	public int doAddpzlrmx1(Map map) {
   		szjzDao.doAddpzlrmx1(map);
		return 0;
   		
   	}
   	/**
   	 * 凭证录入明细表增加数据
   	 * @param map
   	 * @return
   	 */
   	public int doAddpzlrmxbyjdfx(Map map) {
   		szjzDao.doAddpzlrmxbyjdfx(map);
		return 0;
   		
   	}
	/**
   	 * 收支结明细表增加数据
   	 * @param map
   	 * @return
   	 */
   	public int addszjzmx(Map map) {
   		szjzDao.addszjzmx(map);
		return 0;
   		
   	}
	/**
   	 * 凭证录入主表表增加数据
   	 * @param map
   	 * @return
   	 */
   	public int doAddpzlrzb(Map map) {
		return szjzDao.doAddpzlrzb(map);
   	}
   	/**
   	 * 凭证编号自动获得
   	 * @return
   	 */
   	/*public int  getAutoPzbh() {
		String pzbh1 = pzlrDao.getMaxPzbh(); 
		int pzbh = Integer.parseInt(pzbh1);
		System.out.println("pzbh==============="+pzbh);
		return pzbh;
	}*/
   	/**
   	 * 凭证录入主表增加金额
   	 * @param map
   	 * @return
   	 */
   	public int addJe(BigDecimal jfjehj,BigDecimal dfjehj,String guid) {
   		szjzDao.addJe(jfjehj,dfjehj,guid);
		return 0;
   		
   	}
 	/**
   	 * 凭证录入明细表sfjz 改为1
   	 * @param map
   	 * @return
   	 */
   	public int updateSfjz(String kmbh) {
   		szjzDao.updateSfjz(kmbh);
		return 0;
   		
   	}
   	/**
   	 * 得到初始余额表中借方金额
   	 */
   	public int getjeinkmye(String kmbh) {
   		return szjzDao.getjeinkmye(kmbh);
   	}
   	/** 得到收支结转的数据
   	 * @param
   	 * @return
   	 */
   	public List<Map<String, Object>> getszjz(String sszt) {
   		return szjzDao.getszjz(sszt);
   	}
	/** 删除主表凭证
   	 * @param
   	 * @return
   	 */
   	public int deletezb(String guid) {
   		return szjzDao.deletezb(guid);
   	}
   	/** 删除明细表数据
   	 * @param
   	 * @return
   	 */
   	public int deletemx(String guid) {
   		return szjzDao.deletemx(guid);
   	}
   	/** 删除明细表数据
   	 * @param
   	 * @return
   	 */
   	public int deleteszjz(String guid) {
   		return szjzDao.deleteszjz(guid);
   	}
   	/**
   	 * 通过凭证号删除数据
   	 */
   	public boolean dodelete(String zbguid,String pzh,String jzyf) {
   		
   		return szjzDao.dodelete(zbguid,pzh,jzyf);
   		
   		
   	}
	/**
   	 * 通过凭证号批量删除数据
   	 */
   	public boolean dopldelete(PageData pd) {
   		
   		return szjzDao.dopldelete(pd);
   		
   		
   	}
	/** 获得自动录入的状态
   	 * @param
   	 * @return
   	 */
   	public Map<String, Object> getzdlrpz() {
   		return szjzDao.getzdlrpz();
   	}

}
