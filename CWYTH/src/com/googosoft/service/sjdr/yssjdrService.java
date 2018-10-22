package com.googosoft.service.sjdr;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.fzgn.sjdr.yssjdrDao;
import com.googosoft.pojo.fzgn.sjdr.ZC_ZJB_DR;
import com.googosoft.service.base.BaseService;

@Service("yssjdrService")
public class yssjdrService extends BaseService{
	
	@Resource(name="yssjdrDao")
	public yssjdrDao yssjdrDao;
	
	/*** 获取Excel标题*/
	public List getYssjBt(String file) {
		return yssjdrDao.getYssjBt(file);
	}
	/*** 导入数据*/
	public String insertYssjDr(String file,ZC_ZJB_DR zjb){
		return yssjdrDao.insertYssjDr(file, zjb);
	}
}