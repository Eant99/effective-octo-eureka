package com.googosoft.service.systemset.cssz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.XtbDao;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB_EXTEND;
import com.googosoft.service.base.BaseService;

@Service("xtbService")
public class XtbService extends BaseService{
	
	@Resource(name="xtbDao")
	private XtbDao xtbDao;	
	//保存数据
	public int doAdd(ZC_SYS_XTB_EXTEND xtbextend) {
		int result = xtbDao.doAdd(xtbextend);
		if(result>0){
			doAddOplog(OplogFlag.ADD,"系统运行参数设置",xtbextend.getGuid());
		}
		return result;
	}

	public Map getXtcs() {
		return xtbDao.getXtcs();
	}

	public Map getXtsz() {
		List<Map> list = xtbDao.getXtsz();
		Map<String,String> xtsz_map = new HashMap<String,String>();
		for (Map map : list) {
			xtsz_map.put(map.get("ZDVALUE").toString().toLowerCase(), map.get("MVALUE")+"");
		}
		return xtsz_map;
	}

	public Map getXtsz(String tname) {
		List<Map> list = xtbDao.getXtsz(tname);
		Map<String,String> xtsz_map = new HashMap<String,String>();
		for (Map map : list) {
			xtsz_map.put(map.get("ZDVALUE").toString().toUpperCase(), map.get("MVALUE")+"");
		}
		return xtsz_map;
	}
//	/**
//	 * 获取验收单切换地址（这个地址放在了人员表里）
//	 * @return
//	 */
//	public String findysdUrl(){
//		return xtbDao.findysdUrl();
//	}
	/**
	 * 领用人建账点击切换列表时更换验收单地址
	 * @param url
	 * @return
	 */
	public int UpdUrl(String url){
		return xtbDao.UpdUrl(url);
	}
	/**
	 * 是否启用折旧
	 * @return
	 */
	public String sfqy(){
		return xtbDao.sfqy();
	}

	public int doUpdate(String ksdh) {
		int i=xtbDao.doUpdate(ksdh);
		if(i>0){
			doAddOplog(OplogFlag.UPD,"系统运行参数设置","");
		}
		return i;
	}
}
