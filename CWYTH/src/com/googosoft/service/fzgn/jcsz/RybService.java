package com.googosoft.service.fzgn.jcsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.RybDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Const;
import com.googosoft.util.Validate;

/**
 * 人员信息Service方法
 * @author master
 */
@Service("ryxxService")
public class RybService extends BaseService{
	
	@Resource(name="ryxxDao")
	private RybDao ryxxDao;

	/**
	 * 新增
	 * @return
	 */
	public int doAdd(GX_SYS_RYB ryb){
		ryb.setDwbh(CommonUtil.getDwXx(ryb.getDwbh()));
		ryb.setMm(new SimpleHash("SHA-1", Const.SALTKEY, Constant.MRMM).toString().toUpperCase());
		int result = ryxxDao.doAdd(ryb);
		if(result>0){
			doAddOplog(OplogFlag.ADD,"人员基础信息：增加",ryb.getRybh());
		}
		return result;
	}
	/**
	 * 修改
	 * @param ryb
	 * @return
	 */
	public int doUpdate(GX_SYS_RYB ryb){    
		ryb.setDwbh(CommonUtil.getDwXx(ryb.getDwbh()));
		int result = ryxxDao.doUpdate(ryb);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"人员基础信息：修改",ryb.getRybh());
		}
		return result;
	}
	/**
	 * 获取实体
	 * @param rybh
	 * @return
	 */
	public Map<String, Object> getObjectById(String rybh){
		return ryxxDao.getObjectById(rybh);
	}
	/**
	 * 删除
	 * @param rybh
	 * @return
	 */
	public int doDel(String rybh){
		int result = ryxxDao.doDel(rybh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"人员基础信息：删除",rybh);
		}
		return result;
	}
	/**
	 * 删除时，如果该人名下有已处置的资产，则只禁用该人员
	 * @param rybh
	 * @return
	 */
	public int doJyRy(String rybh){
		int result = ryxxDao.doJyRy(rybh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"人员基础信息：禁用",rybh);
		}
		return result;
	}
	/**
	 * 检测人员工号是否已存在
	 * @return
	 */
	public boolean checkRyb(String rygh){
		return ryxxDao.checkRygh(rygh);        
	}

	/**
	 * 删除人员时验证名下是否有资产
	 * @param RYBHS
	 * @return
	 */
	public String validateForZJB(String RYBHS){
		return ryxxDao.validateForZJB(RYBHS);
	}
	
	/**
	 * 人员信息批量赋值
	 * @param ids
	 * @param ziduan
	 * @param zdValue
	 * @return
	 */
	public int doPlfz(String ids,String ziduan,Object zdValue){
		
		int result=ryxxDao.doPlfz(ids, ziduan, zdValue);
		if(result>0)
		{
			doAddOplog(OplogFlag.UPD,"人员基础信息",ids.split(","));
		}
		return result;
	}
	
	/**
	 * 修改他人密码
	 * @param ryb
	 * @return
	 */
	public boolean doUpdate_xgtrmm(GX_SYS_RYB ryb) {
		ryb.setRybh(CommonUtil.getRybh(ryb.getRygh()));
		int result = (int) ryxxDao.doUpdate_xgtrmm(ryb);
		if(result==1){
			doAddOplog(OplogFlag.UPD,"修改他人密码",CommonUtil.getBeginText(ryb.getRygh()));
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 通过(rygh)xm获取该人的所在单位(bmh)mc
	 * @param ryghmc
	 * @return
	 */
	public String getDwxxByRyxx(String ryghmc){
		return ryxxDao.getDwxxByRyxx(ryghmc);
	}
	/**
	 * 通过人员编号查询人员姓名
	 * @param ryxm
	 * @return
	 */
	public String findRybhByRyxm(String ryxm) {
		String rybh="";
		if(Validate.noNull(ryxm)){
			rybh=ryxxDao.findRybhByRyxm(ryxm);
		}
		return rybh;
	}
	/**
	 * 管理权限设置
	 * 通过人员工号查询人员是否存在
	 * @param loginrybh 当前登录人
	 * @param ryxm (rygh)xm格式
	 * @return
	 */
	public String findRybhByRyxm(String loginrybh, String ryxm) {
		if(Validate.isNull(loginrybh)){
			return "";
		}
		String rybh = "";
		if(Validate.noNull(ryxm)){
			rybh = ryxxDao.findRybhByRyxm(loginrybh,ryxm);
		}
		return rybh ;
	}

	/**
	 * 通过rybh查询角色信息
	 * @param rybh
	 * @return
	 */
	public List<Map<String, Object>> getJsxx(String rybh) {
		return ryxxDao.getJsxx(rybh);
	}
	public int doCshmm(String rybh){
		int result = ryxxDao.doCshmm(rybh);
		if(result>0){
			doAddOplog(OplogFlag.CSH,"人员基础信息：密码初始化",rybh.split(","));
		}
		return result;
	}
	
	/**
	 * 启用禁用人员
	 * @param rybh
	 * @param type
	 * @return
	 */
	public int qyjy(String rybh,String type){
		int result = ryxxDao.qyjy(rybh,type);
		String ryzt = "启用";
		if(!type.contains("qy")){
			ryzt = "禁用";
		}
		if(result>0){
			doAddOplog(OplogFlag.UPD,"人员基础信息："+ryzt,rybh.split(","));
		}
		return result;
	}
	/**
	 * 修改密码
	 * @param passwordn
	 * @param rybh
	 * @return
	 */
	public int doUpdPwd(String passwordn,String rybh){
		return ryxxDao.doUpdPwd(passwordn, rybh);
	}
}
