package com.googosoft.service.fzgn.wxzf;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.wxzf.XfddglDao;
import com.googosoft.pojo.fzgn.wxzf.CW_XFDDGL;
import com.googosoft.service.base.BaseService;

/**
 * 消费地点信息service
 * @author googosoft
 *
 */
@Service("xfddglService")
public class XfddglService extends BaseService{
	
	@Resource(name="xfddglDao")
	private XfddglDao dao;
	
	public Map<String, Object> getObjectById(String guid) {
		return dao.getObjectById(guid);
	}
	
	public boolean checkCbsbh( String cbsbh) {
		return dao.checkCbsbh(cbsbh);
	}
	
	/**
	 * 新增保存消费地点信息
	 * @param xfddxx
	 * @return
	 */
	public int doAdd(CW_XFDDGL xfddxx){
		return dao.doAdd(xfddxx);
	}
	
	public int checkXfddbh(CW_XFDDGL xfddxx){
		return dao.checkXfddbh(xfddxx);
	}
	
	public int checkXfddbhupdate(CW_XFDDGL xfddxx){
		return dao.checkXfddbhupdate(xfddxx);
	}
	/**
	 * 删除消费地点信息
	 * @param guid
	 * @return
	 */
	public int doDel(String guid) {
		int result = dao.doDel(guid);
		if (result > 0) {
			doAddOplog(OplogFlag.DEL, "消费地点信息：删除", guid);
		}
		return result;
	}
	
	/**
	 * 修改保存消费地点信息
	 * @param xfddxx
	 * @return
	 */
	public int doUpp(CW_XFDDGL xfddxx){
		return dao.doUpp(xfddxx);
	}
	
	/**
	 * 获取所属承包商下拉列表信息
	 * @return
	 */
	public List getCbsList() {
		return dao.getCbsList();
	}
	
	/**
	 * 更新二维码信息
	 * @param guid
	 * @param ewmUrl
	 * @return
	 */
	public int doUpdateEwm(String guid,String ewmUrl) {
		return dao.doUpdateEwm(guid, ewmUrl);
	}
}
