package com.googosoft.service.cwbb;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.googosoft.dao.cwbb.ZcfzbjDao;
import com.googosoft.service.base.BaseService;
@Service("zcfzbjService")
public class ZcfzbjService extends BaseService{
	
	@Resource(name="zcfzbjDao")
	public ZcfzbjDao zcfzbjDao;
	public List<Map<String, Object>> getResultListByPro1(String ksyf, String jsyf,
			String saasdm,String sfjz,String ztgid,String jzpz) {
		 return zcfzbjDao.getResultListByPro1(ksyf, jsyf, saasdm, sfjz, ztgid,jzpz);
	} 
	/**
	 * 新的资产负债表
	 * @param bbnd
	 * @param bbyf
	 * @param saasdm
	 * @param sfjz
	 * @param type
	 * @param ztgid
	 * @param rq
	 * @param jzpz
	 * @return
	 */
	public List<Map<String, Object>> getResultListByProNew( String ksyf,String jsyf,
			String saasdm,String sfjz,String type,String ztgid,String jzpz) {
		 return zcfzbjDao.getResultListByProNew( ksyf,jsyf, saasdm, sfjz, type, ztgid, jzpz);
	} 
	
	/**
	 * 保存
	 * @param list
	 * @param sfjz 
	 * @param jzpz 
	 * @return
	 */
	public boolean doSave(List list){
		return zcfzbjDao.doSave(list);
	}
	
	/**
	 * 新的资产负债表保存
	 * @param list
	 * @param jzpz 
	 * @param sfjz 
	 * @return
	 */
	@Transactional
	public boolean doSaveNew(List list){
		return zcfzbjDao.doSaveNew(list);
	}
	public int checkzcfzb(String bblx,String sysDate){
		return zcfzbjDao.checkzcfzb(bblx, sysDate);
	}
	
	/**
	 * 数据集合
	 * 
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @return
	 */
	public List<Map<String, Object>> getResultList(String bblx, String sysDate) {
		return zcfzbjDao.getResultList(bblx, sysDate);
	}
	/**
	 * 新资产负债表获取已保存的数据
	 * @return
	 */
	public List getZcfzbnewList(String ztgid, String ksyf, String jsyf, String jzpz, String sfjz) {
		return zcfzbjDao.getZcfzbnewList(ztgid,ksyf,jsyf,jzpz,sfjz);
	}
}
