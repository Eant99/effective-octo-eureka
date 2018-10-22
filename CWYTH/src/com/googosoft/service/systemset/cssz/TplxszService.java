package com.googosoft.service.systemset.cssz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.TplxszDao;
import com.googosoft.pojo.systemset.cssz.ZC_PICTYPESET;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.AutoKey;

/**
 * 图片类型设置 Service 
 * Create by 刘帅  on 2016-10-25 17:30
 */

@Service("tplxszService")
public class TplxszService extends BaseService{

	@Resource(name = "tplxszDao")
	private TplxszDao tplxszDao;

	/**
	 * 图片类型设置（添加）
	 * 
	 * @return
	 */
	public boolean doAdd(ZC_PICTYPESET tpb) {
		tpb.setLxbh(AutoKey.makeCkbh("ZC_PICTYPESET", "LXBH", "6"));
		int i = (int) tplxszDao.doAdd(tpb);
		if (i == 1){
			doAddOplog(OplogFlag.ADD,"图片类型设置",tpb.getLxbh());
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 图片类型设置（修改）：修改角色信息
	 * 
	 * @param ryb
	 * @return
	 */
	public boolean doUpdate(ZC_PICTYPESET tpb) {
		int i = (int) tplxszDao.doUpdate(tpb);
		if (i == 1){
			doAddOplog(OplogFlag.UPD,"图片类型设置",tpb.getLxbh());
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 图片类型设置（查看）：获取图片类型设置详细信息
	 * 
	 * @param lxbh
	 * @return
	 */
	public Map getTplxszxx(String lxbh) {
		return tplxszDao.getTplxszxx(lxbh);
	}

	/**
	 * 图片类型设置（删除）：删除图片类型设置
	 */
	public boolean doDelete(String lxbh) {
		int result = tplxszDao.doDelete(lxbh);
		if (result > 0) {
			String lxbhs [] =  lxbh.split(",");
			for (int i=0;i<lxbhs.length;i++){
				doAddOplog(OplogFlag.DEL,"图片类型设置",lxbhs[i]);
			}
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 图片类型设置：获取单据名称  下拉选数据
	 * @return
	 */
	public List<Map<String, Object>> getDjmc(){
		return tplxszDao.getDjmc();
	}
	/*
	 * 判断要删除的图片类型有没有在用，有，不能删除
	 */
	public boolean doCheck(String lxbh) {
		int result = tplxszDao.doCheck(lxbh);
		if(result >= 1) {
			return true;
		}else{
			return false;
		}
	}
}
