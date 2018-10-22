package com.googosoft.service.systemset.cssz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.systemset.cssz.DlxxDao;
import com.googosoft.pojo.systemset.cssz.SYS_XTCSB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;
//import com.googosoft.pojo.systemset.cssz.SYS_XTCSB;
//import com.googosoft.constant.MkConstant;

/**
 * 登录信息设置
 * @author master
 */
@Service("dlxxService")
public class DlxxService extends BaseService{

	@Resource(name="dlxxDao")
	private DlxxDao dlxxDao;
	
	/**
	 * 获取登录信息
	 * @return
	 */
	public Map getDlxx() {
		return dlxxDao.getDlxx();
	}
	
	/**
	 * 获取背景图及logo图
	 * @return
	 */
	public List getImgFile() {
		return dlxxDao.getImgFile();
	}
	
	/**
	 * 登录信息设置保存
	 * @param xtcsb
	 * @param content
	 * @return
	 */
	@Transactional
	public boolean doSave(SYS_XTCSB xtcsb,String content) {
		boolean bool = dlxxDao.doSave(xtcsb,content);
		return bool;
	}

	/**
	 * 根据登录方式编号返回登录方式中文名称
	 * @param bh
	 * @return
	 */
	public String getDlfsmc(String bh){
		String mc = "";
		if("1".equals(bh)){
			mc = "姓名";
		}else if("2".equals(bh)){
			mc = "工号";
		}else if("3".equals(bh)){
			mc = "身份证号";
		}else if("4".equals(bh)){
			mc = "手机号";
		}
		return mc;
	}
	/**
	 * 登录页面--获取登录方式
	 * @return
	 */
	public String getDlfs() {
		Map<?, ?> dlxx= dlxxDao.getDlxx();
		String dlfs = Validate.isNullToDefault(dlxx.get("DLFS"), "")+"";
		String dlfsmc = "";
		if(Validate.noNull(dlfs)){
			String[] dlfss = dlfs.split(",",-1);
			for(int i=0;i<dlfss.length;i++){
				dlfsmc = dlfsmc+getDlfsmc(dlfss[i])+"/";
			}
			dlfsmc = dlfsmc.substring(0, dlfsmc.length()-1);
		}
		return dlfsmc;
	}
	
	/**
	 * 获取系统参数
	 * @return
	 */
	public Map<String, Object> getXtcs() {
		Map<String,Object> map = dlxxDao.getXtcs(); 
		List<Map> bglist = dlxxDao.getLoginImg("bgImg");
		map.put("bglist", bglist.size()==0?"":bglist);
		List<Map> logo = dlxxDao.getLoginImg("logoImg");
		map.put("logoPath",logo.size()==0?"":logo.get(0).get("PATH"));
		return map;
	}
	
	/**
	 * 获取系统参数
	 * @return
	 */
	public Map<String, Object> getTp() {
		Map<String,Object> map = dlxxDao.getTp(); 
		return map;
	}
	/**
	 * 获取注意事项
	 * @return
	 */
	public String getZysx() {
		return dlxxDao.getZysx();
	}

	
	

}
