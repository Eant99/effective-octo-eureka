package com.googosoft.service.fzgn.jcsz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.LzryzcyjDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.WindowUtil;

/**
 * 离职人员资产移交service层
 * @author RC 2017-08-31
 */
@Service("lzryzcyjService")
public class LzryzcyjService extends BaseService{
	
	@Resource(name="lzryzcyjDao")
	private LzryzcyjDao lzryzcyjDao;

	/**
	 * 保存离职人员信息
	 * @param lzry 离职人员
	 * @param jsr 接收人员
	 * @param yqbhs 部分移交时选择的部分资产
	 * @return
	 */
	public boolean doSave(String lzry, String jsr, String yqbhs){
		if(lzryzcyjDao.doSave(lzry, WindowUtil.getXx(jsr, "R"), yqbhs)){
			doAddOplog(OplogFlag.ADD,"保存离职人员资产移交信息",lzry);
			return true;
		}
		else{
			return false;
		}
	}
}
