package com.googosoft.service.sjdr;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.fzgn.sjdr.yssjzlDao;
import com.googosoft.pojo.fzgn.sjdr.ZC_ZJB_DRMX;
import com.googosoft.pojo.fzgn.sjdr.sjdr_zjb;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Service("yssjzlService")
public class yssjzlService extends BaseService{
	
	@Resource(name="yssjzlDao")
	public yssjzlDao yssjzlDao;
	
	/**
	 * 信息保存
	 * @param zjb
	 * @return
	 * @throws ParseException 
	 */
	public boolean doAdd(ZC_ZJB_DRMX zjb) throws ParseException{
		List<sjdr_zjb> list = zjb.getZjbmx();
		if(Validate.noNull(list)){
			for(sjdr_zjb zjbmx : list){
				zjbmx.setWx_gljg(WindowUtil.getXx(zjbmx.getWx_gljg(), "D"));//管理机构（无形资产用到了）
				zjbmx.setJsr(WindowUtil.getXx(zjbmx.getJsr(), "R"));//经手人
				zjbmx.setCgr(WindowUtil.getXx(zjbmx.getCgr(), "R"));//采购人
				zjbmx.setXk(WindowUtil.getText(zjbmx.getXk()));//学科
				zjbmx.setGbm(WindowUtil.getText(zjbmx.getGbm()));//国别
				zjbmx.setSyr(WindowUtil.getXx(zjbmx.getSyr(), "R"));//使用人
				zjbmx.setSydw(WindowUtil.getXx(zjbmx.getSydw(), "D"));//使用单位
				zjbmx.setBzxx(WindowUtil.getXx(zjbmx.getBzxx(), "P"));//存放地点
			}
		}
		boolean b = yssjzlDao.doAdd(zjb);
//		if(b){
//			doAddOplog(OplogFlag.UPD, "验收单信息修改",);
//		}
		return b;
	}
	/**
	 * 删除
	 * @param 
	 * @return 
	 */
	public int doDelete(String id) {
		return yssjzlDao.doDelete(id);
	}
	/**
	 * 清空
	 * @param 
	 * @return 
	 */
	public int doDel() {
		return yssjzlDao.doDel();
	}
	/**
	 * 入账
	 * @param id
	 * @return
	 */
	public boolean DoRz(String id){
		return yssjzlDao.DoRz(id);
	}
	/**
	 * 验证
	 * @param zjb
	 * @return
	 */
	public String docheck(ZC_ZJB_DRMX zjb){
		return yssjzlDao.docheck(zjb);
	}
	/**
	 * 审核验证
	 * @param zjb
	 * @return
	 */
	public String doShcheck(String type,String id,String tj){
		return yssjzlDao.doShcheck(type,id,tj);
	}
}
