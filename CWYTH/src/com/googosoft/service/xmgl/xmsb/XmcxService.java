package com.googosoft.service.xmgl.xmsb;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.xmgl.xmsb.XmcxDao;

@Service("xmcxService")
public class XmcxService{
	@Resource(name="xmcxDao")
	private XmcxDao xmcxDao;
	/**
	 * 项目申报主表的详细信息
	 * @param guid 
	 * @return
	 */
	public Map getMapXmsbByGuid(String guid){
		return xmcxDao.getMapXmsbByGuid(guid);
	}

}
