package com.googosoft.service.fzgn.jcsz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.XJxxDao;
import com.googosoft.dao.fzgn.jcsz.XsxxDao;
import com.googosoft.pojo.fzgn.jcsz.CW_XJXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_XSXXB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("xjxxService")
public class XjxxService extends BaseService{
	@Resource(name = "xjxxDao")
	private XJxxDao xjxxDao;

	/**
	 * 新增
	 * 
	 * @return
	 */
	public int doAdd(CW_XJXXB xjxx,String zbguid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		xjxx.setRxny(sdf.format(new Date()));
        System.out.println("----------++++++=="+xjxx.getYxsh());
		int result = xjxxDao.doAdd(xjxx,zbguid);
		if (result > 0) {
			doAddOplog(OplogFlag.ADD, "人员基础信息：增加", zbguid);
		}
		return result;
	}
	
	/**
	 * 获取实体
	 * @param rybh
	 * @return
	 */
		
		public Map<String, Object> getObjectById(String zbguid){
			return xjxxDao.getObjectById(zbguid);
	}
	/**
	 * 修改
	 * @param ryb
	 * @return
	 */
	public int doUpdate(CW_XJXXB xjxx,String zbguid){    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		xjxx.setRxny(sdf.format(new Date()));
		int result = xjxxDao.doUpdate(xjxx,zbguid);
		
		return result;
	}
	/**
	 * 删除
	 * @param rybh
	 * @return
	 */
	public int doDel(String guid){
		int result = xjxxDao.doDel(guid);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"学籍信息：删除",guid);
		}
		return result;
	}
	
}
