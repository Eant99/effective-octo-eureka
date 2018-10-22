package com.googosoft.service.wsbx.bxzf;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.constant.Constant;
import com.googosoft.dao.kjhs.pzxx.PzlrDao;
import com.googosoft.dao.wsbx.bxzf.BxzfDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.kjhs.pzxx.PzlrService;

@Service("bxzfService")
public class BxzfService extends BaseService{

	@Autowired
	BxzfDao bxzfDao;
	@Autowired
	PzlrService pzlrService;
	
	
	/**
	 * 报销支付
	 * @param bxid 报销id
	 * @param bxlx 报销类型
	 * @return
	 */
	@Transactional
	public int doZf(String bxid,String bxlx,String kjzd,HttpSession session) {
		String[] bxid_arr = bxid.split(",");
		String pzlx=pzlrService.getPzlx();
		for (String str : bxid_arr) {
			pzlrService.autoCreatePzlrByBx(str,bxlx,kjzd,Constant.getztid(session),pzlx);
			bxzfDao.insertBxzf(str);
			bxzfDao.updateJfsz(str);
		}
		return bxid_arr.length;
	}
	
	
	
	
	
	
	
	
	
	
	
}
