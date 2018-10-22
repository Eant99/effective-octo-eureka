package com.googosoft.service.kjhs;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.YhzhglDao;
import com.googosoft.pojo.kjhs.Cw_yhzhgl;
import com.googosoft.service.base.BaseService;

@Service("yhzhglService")
public class YhzhglService extends BaseService{

	@Resource(name="yhzhglDao")
	private YhzhglDao yhzhglDao;
	
	/**
	 * 银行账号管理增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd(Cw_yhzhgl yhzhb){
		
		int i = yhzhglDao.doAdd(yhzhb);
		
		return i;	
    }
	/**
	 * 银行账号全删除
	 */
	public int doDeleteAll(){
		return yhzhglDao.doDeleteAll();
	}

}
