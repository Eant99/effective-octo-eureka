package com.googosoft.service.bzjgl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.bzjgl.BzxjbzsdDao;
import com.googosoft.dao.bzjgl.XsbzxjlrDao;
import com.googosoft.pojo.bzjgl.CW_XSBZMXB;
import com.googosoft.pojo.bzjgl.CW_XSBZZB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("xsbzxjlrService")
public class XsbzxjlrService extends BaseService {

	@Resource(name="xsbzxjlrDao")
	private XsbzxjlrDao xsbzxjlrDao;
	//查询
	public Map<?, ?> getObjectById(String guid) {
		return xsbzxjlrDao.getObjectById(guid);
	}
	//删除
	public int doDelete(String fabh) {
		return xsbzxjlrDao.doDelete(fabh);
	}
	//批量删除
	public int doDelete2(String guid) {
		return xsbzxjlrDao.doDelete2(guid);
	}
	//添加
	public int doAdd(CW_XSBZZB xsbzzb, String fabh, String ffzje, String xhs,String bzbhs,
			String yhmcs, String yhkhs, String ffjes) {
		return xsbzxjlrDao.doAdd(xsbzzb,fabh,ffzje,xhs,bzbhs,yhmcs,yhkhs,ffjes);
	}
	//查询等级信息
	public List getDjlist() {
		return xsbzxjlrDao.getDjlist();
	}
	//获得学生补助学金录入明细
	public List getBzxjlrmc(String guid) {
		return xsbzxjlrDao.getBzxjlrmc(guid);
	}
	//修改学生补助学金录入信息
	public int doUpdate(CW_XSBZZB xsbzzb, String fabh, String ffzje,
			String xhs, String bzbhs, String yhmcs, String yhkhs, String ffjes) {
		return xsbzxjlrDao.doUpdate(xsbzzb,fabh,ffzje,xhs,bzbhs,yhmcs,yhkhs,ffjes);
	}
	/**
	 * 获取学生个人银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getXxyhk( ){
		return xsbzxjlrDao.getXxyhk();
	}
	/**
	 * 学生银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getXsyhxx(String dqdlrguid) {
		return xsbzxjlrDao.getXsyhxx(dqdlrguid);
	}
}
