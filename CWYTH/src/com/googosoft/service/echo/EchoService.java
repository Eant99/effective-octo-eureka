package com.googosoft.service.echo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googosoft.dao.echo.EchoDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.websocket.echo.EchoServer;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;

@Service("echoService")
public class EchoService extends BaseService{

	@Autowired
	private EchoDao echoDao;
	
	/**
	 * 获取待审核信息DshInfo
	 * @param ywguid   业务表guid
	 * @return DshInfo
	 */
	public DshInfo getCcywsqDshxxMsg(String ywguid) {
		return echoDao.getCcsqspDshxxMsg(ywguid);
	}
	public DshInfo getJkDshxxMsg(String ywguid) {
		return echoDao.getJkDshxxMsg(ywguid);
	}
	public DshInfo getGwjdsqspDshxxMsg(String ywguid) {
		return echoDao.getGwjdsqspDshxxMsg(ywguid);
	}
	public DshInfo getXzsbshDshxxMsg(String ywguid) {
		return echoDao.getXzsbshDshxxMsg(ywguid);
	}
	public DshInfo getRcbxDshxxMsg(String ywguid) {
		return echoDao.getRcbxDshxxMsg(ywguid);
	}
	public DshInfo getCcbxDshxxMsg(String ywguid) {
		return echoDao.getCcbxDshxxMsg(ywguid);
	}
	public DshInfo getGwjdbxDshxxMsg(String ywguid) {
		return echoDao.getGwjdbxDshxxMsg(ywguid);
	}
	//
	public String getShrGuid(String procinstid) {
		return echoDao.getShrGuid(procinstid);
	}
	public EchoServer getEchoServerByProId(String procinstid) {
		String shrGuid = getShrGuid(procinstid);
		return EchoUtil.echoMap.get(shrGuid);
	}
}
