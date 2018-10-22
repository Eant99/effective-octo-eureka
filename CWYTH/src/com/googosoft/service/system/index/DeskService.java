package com.googosoft.service.system.index;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.system.index.DeskDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
   
@Service("deskService")
public class DeskService extends BaseService{
	
	@Resource(name="deskDao")
	private DeskDao deskDao;

	public List getRcyw(String rybh) {
		List<Map<String,Object>> list = deskDao.getRcyw(rybh);
		return list;
	}

	public List getDbsx(String rybh) {
		return deskDao.getDbsx(rybh);
	}

	public List getTzgg() {
		return deskDao.getTzgg();
	}

	public List getGrzc(String rybh) {
		return deskDao.getGrzc(rybh);
	}

	public Map getGrzcHj(String rybh) {
		return deskDao.getGrzcHj(rybh);
	}
	/**
	 * 获取人员的进度跟踪
	 * @param rybh
	 * @return
	 */
	public List getJdgz(String rybh) {
		return deskDao.getJdgz(rybh);
	}

	
	/**
	 * 获取柱状图数据
	 * @param 
	 * @return
	 */
	public List getZztData(String sbnd) {
		return deskDao.getZztData(sbnd);
	}

	/**
	 * 获取进度跟踪的sql语句
	 * @param rybh
	 * @return
	 */
	public String getJdgzSql(String rybh){
		return deskDao.getJdgzSql(rybh);
	}
	/**
	 * 获取可以使用的资产流水号
	 * @return
	 */
	public List getZclsh(){
		return deskDao.getZclsh();
	}

	/**
	 * 根据人员编号获取当前人员有无审核权限
	 * @param rybh
	 * @return
	 */
	public String getShqx(String rybh){
		return deskDao.getShqx(rybh);
	}
	/**
	 * 根据人员编号获取业务审核列表
	 * @param rybh
	 * @return
	 */
	public List getShxx(String rybh){
		return deskDao.getShxx(rybh);
	}
	/**
	 * 根据人员编号获取业务审核数量
	 * @param rybh
	 * @return
	 */
	public String getYwsh(String rybh){
		return deskDao.getYwsh(rybh);
	}
	
	/**
	 * 根据人员编号获取未读通知公告信息
	 * @param rybh
	 * @return
	 */
	public List getWdxx(String rybh){
		return deskDao.getWdxx(rybh);
	}
	/**
	 * 根据人员编号获取未读信息数量
	 * @param rybh
	 * @return
	 */
	public String getWdsl(String rybh){
		return deskDao.getWdsl(rybh);
	}
	/**
	 * 获取我的业务流水
	 * @param rybh  当前登录人编号
	 * @param month 需要获取数据的月份
	 * @return
	 */
	public List getYwls(String rybh,String month){
		return deskDao.getYwls(rybh,month);
	}

	public List getRcywEdit(String rybh) {
		return deskDao.getRcywEdit(rybh);
	}

	public boolean doSaveRcyw(String data, String rybh) {
		return deskDao.doSaveRcyw(data,rybh);
	}
	
	public List getSmzqMenu(String mkbh){
		return deskDao.getSmzqMenu(mkbh);
	}

	public Map getYwsm(String mkbh) {
		return deskDao.getYwsm(mkbh);
	}

//	/**
//	 * 暂时没找到哪里用这个方法，先注释掉
//	**/
//	public boolean doSaveYwsm(String content,String rybh) {
//		return deskDao.doSaveYwsm(content,rybh);
//	}

	public String countYwsm(String mkbh) {
		return deskDao.countYwsm(mkbh);
	}

	public int getAssetsNum(String searchword){		
		return deskDao.getAssetsNum(searchword);
	}

	public String getSpls(String rybh) {
		return deskDao.getSpls(rybh);
	}

	public String getBhls(String rybh) {
		return deskDao.getBhls(rybh);
	}

	public String getCgsl(String rybh) {
		return deskDao.getCgsl(rybh);
	}

//	public String getByjzls(String rybh) {
//		return deskDao.getByjzls(rybh);
//	}

	public String getjzsl(String rybh,String sjfw) {
		return deskDao.getjzsl(rybh,sjfw);
	}
	
	public boolean ryjspd() {
		return deskDao.ryjspd();
	}

//	public String getBmbdsl(String rybh) {
//		return deskDao.getBmbdsl(rybh);
//	}

	public String getbdsl(String rybh,String sjfw) {
		return deskDao.getbdsl(rybh,sjfw);
	}

//	public String getBmczsl(String rybh) {
//		return deskDao.getBmczsl(rybh);
//	}

	public String getczsl(String rybh,String sjfw) {
		return deskDao.getczsl(rybh,sjfw);
	}

	public Map getGlyjzsl(String rybh) {
		return deskDao.getGlyjzsl(rybh);
	}

	public Map getSyrqrsl(String rybh) {
		return deskDao.getSyrqrsl(rybh);
	}
	
	public String getSfgk(String rybh){
		return deskDao.getSfgk(rybh);
	}
	
	public String getInfoByCount(PageData pd){
		return deskDao.getInfoByCount(pd);
	}
	/**
	 * 根据rybh获取角色id
	 * @param rybh
	 * @return
	 */
	public String getJsbh(String rybh){
		return deskDao.getJsbh(rybh);
	}

	//获取培养层次数据
	public List<Map<String, Object>> getPyccfx() {
		List<Map<String, Object>> pycc = deskDao.getPyccfx();
		return pycc;
	}
	/**
	 * 账套信息
	 * @return
	 */
	public List<Map<String, Object>> getZtxx(){
		return deskDao.getZtxx();
	}

	public List getTzxx(String guid) {
		// TODO Auto-generated method stub
		return deskDao.getTzxx(guid);
	}
}
