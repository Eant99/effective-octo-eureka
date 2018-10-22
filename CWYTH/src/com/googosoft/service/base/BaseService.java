package com.googosoft.service.base;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googosoft.commons.LUser;
import com.googosoft.dao.systemset.aqgl.OplogDao;
import com.googosoft.pojo.systemset.aqgl.ZC_SYS_OPLOG;
import com.googosoft.util.UserAgentUtils;

/**
 * service基础类
 * @author ii
 *
 */
@Service("baseService")
public class BaseService {
	@Autowired  
	private HttpServletRequest request;
	
	@Resource(name="oplogDao")
	public OplogDao oplogDao;
	
	/**
	 * 日志操作管理
	 * @param czlx：操作类型
	 * @param cznr：操作内容
	 * @param djbh：单据编号
	 */
	public void doAddOplog(String czlx,String cznr,String... djbh){
		ZC_SYS_OPLOG oplog = new ZC_SYS_OPLOG();
		oplog.setCzlx(czlx);
		oplog.setCznr(cznr);
		oplog.setDbh(djbh);
		oplog.setRybh(LUser.getRybh());//当前登录人人员编号
		oplog.setCzjq(UserAgentUtils.getUserIp(request));//操作机器IP	
		oplog.setSyd("1");
		oplog.setLlq(UserAgentUtils.getBrowser(request)+"");
        oplogDao.doAdd(oplog); 
	}
	/**
	 * 日志操作管理(手机端)
	 * @param czlx：操作类型
	 * @param cznr：操作内容
	 * @param djbh：单据编号
	 */
	public void doAddOplogPhone(String czlx,String cznr,String rybh,String... djbh){
		ZC_SYS_OPLOG oplog = new ZC_SYS_OPLOG();
		oplog.setCzlx(czlx);
		oplog.setCznr(cznr);
		oplog.setDbh(djbh);
		oplog.setRybh(rybh);//当前登录人人员编号
		oplog.setCzjq(UserAgentUtils.getUserIp(request));//操作机器IP	
		oplog.setSyd("0");
		oplog.setLlq(UserAgentUtils.getBrowser(request)+"");
        oplogDao.doAdd(oplog); 
	}

}
