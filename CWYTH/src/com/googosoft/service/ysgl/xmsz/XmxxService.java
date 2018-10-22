package com.googosoft.service.ysgl.xmsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.ysgl.xmsz.XmxxDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("xmxxService")
public class XmxxService extends BaseService{

	@Resource(name="xmxxDao")
	private XmxxDao xmxxDao;
	
	public List<Map<String, Object>> getXmxxList(){
		return xmxxDao.selectXmxxList();
	}
	public Map<String, Object> getXmxxMapById(String guid){
		return xmxxDao.selectXmxxMapById(guid);
	}
	public int editXmxx(PageData pd) {
		return xmxxDao.updateXmxx(pd);
	}
	public int addXmxx(PageData pd) {
		if(xmxxDao.insertXmjbxx(pd) > 0) {
			return xmxxDao.insertXmkzxx(pd);
		}else {
			return 0;
		}
	}
	public int deleteXmxx(PageData pd) {
		return xmxxDao.deleteXmxx(pd);
	}
	public boolean checkXmbhExist(PageData pd) {
		return xmxxDao.checkXmbhExist(pd);
	}
	public boolean doCheckXmmc(String bmh) {
		boolean result = xmxxDao.doCheckXmmc(bmh);
		return result;
	}
	public boolean doCheckXmmcU(String bmh) {
		boolean result = xmxxDao.doCheckXmmcU(bmh);
		return result;
	}
	public int doAdd(PageData pd,String rybh,HttpSession session,String zfcgje,String fzfcgje){
		int result = xmxxDao.doAdd(pd,rybh,session,zfcgje,fzfcgje);
		return result;
	}
	public int doUpdate(PageData pd,String dwbh,String fzrmc) {
		int result = xmxxDao.doUpdate(pd,dwbh,fzrmc);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"单位基础信息");
		}
		return result;
	}
	public int doChangeFzr(String xmid,String newfzr) {
		//添加负责人变更记录
		int result = xmxxDao.doAddFzrbgjl(xmid,newfzr);
		if(result>0){
			int rel = xmxxDao.doChangeFzr(xmid,newfzr);
			if(rel>0){
				doAddOplog(OplogFlag.UPD,"项目负责人");
			}
		}
		return result;
	}
	
	@Transactional
	public int doDelete(String dwbh) {
		String array[] = dwbh.split("','");
		for(int i=0;i<array.length;i++) {
			List list = xmxxDao.getxx(array[i]);
			for(int j = 0;j<list.size();j++) {
				Map map = (Map) list.get(j);
				String sszt = (String) map.get("sszt");
				String xmbh = (String) map.get("xmbh");
				String guid = (String) map.get("guid");
				xmxxDao.doDeletejbxx(guid);
				xmxxDao.doDeletejjfl(guid);
				xmxxDao.doDeletekz(guid);
				xmxxDao.doDeletesr(guid);
				xmxxDao.doDeletezc(guid);
//				xmxxDao.updatebh(sszt, xmbh);
			}
		}
		return 1;
	}
	/**
	 *  验证项目信息是否正被使用
	 */
	public boolean selectXmmc(String guid) {
		return xmxxDao.selectXmmc(guid);
	}
	/**
	 * 得到收入科目
	 * @param 
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getsrkm(String xmlxbh,String kjzd) {
		return xmxxDao.getsrkm(xmlxbh,kjzd);
	}
	/**
	 * 联想得到收入科目
	 * @param 
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getsrkm1(String xmbh,String sszt,String kjzd) {
		return xmxxDao.getsrkm1(xmbh,sszt,kjzd);
	}
	/**
	 * 得到项目扩展信息
	 * @param 
	 * @param
	 * @return
	 */
	public Map getxmlxxx(String guid) {
		return xmxxDao.getxmlxxx(guid);
	}
	/**
	 * 联想得到项目扩展信息
	 * @param 
	 * @param
	 * @return
	 */
	public Map getxmlxxx1(String xmbh,String sszt) {
		return xmxxDao.getxmlxxx1(xmbh,sszt);
	}
	/**
	 * 得到支出科目
	 * @param 
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getzckm(String xmlxbh,String kjzd) {
		return xmxxDao.getzckm(xmlxbh,kjzd);
	}
	/**
	 * 联想得到支出科目
	 * @param 
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getzckm1(String xmbh,String sszt,String kjzd) {
		return xmxxDao.getzckm1(xmbh,sszt,kjzd);
	}
	/**
	 * 得到收经济分类科目
	 * @param 
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getjjflkm(String xmlxbh) {
		return xmxxDao.getjjflkm(xmlxbh);
	}
	/**
	 * 联想得到收经济分类科目
	 * @param 
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getjjflkm1(String xmbh,String sszt) {
		return xmxxDao.getjjflkm1(xmbh,sszt);
	}
	/**
	 * 增加收入
	 * @param session
	 * @param map
	 * @return
	 */
	public Object addsr(HttpSession session,String srkmbhs,String srkmmcs,String yslxs) {
		return xmxxDao.addsr(session,srkmbhs,srkmmcs,yslxs);
	}
	/**
	 * 修改收入
	 * @param session
	 * @param map
	 * @return
	 */
	public Object updatesr(HttpSession session,String srkmbhs,String srkmmcs,String yslxs) {
		return xmxxDao.updatesr(session,srkmbhs,srkmmcs,yslxs);
	}
	/**
	 * 增加支出
	 * @param session
	 * @param map
	 * @return
	 */
	public Object addzc(HttpSession session,Map map) {
		xmxxDao.addzc(session,map);
		return map;
		
	}
	/**
	 * 增加经济分类
	 * @param session
	 * @param map
	 * @return
	 */
	public Object addjjfl(HttpSession session,Map map) {
		xmxxDao.addjjfl(session,map);
		return map;
		
	}
	/**
	 * 获取收入科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getsrkmById(String guid,String kjzd) {
		return xmxxDao.getsrkmById(guid,kjzd);
	}
	/**
	 * 获取支出科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getzckmById(String guid,String kjzd) {
		return xmxxDao.getszckmById(guid,kjzd);
	}
	/**
	 * 获取经济分类科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getjjflkmById(String guid) {
		return xmxxDao.getjjflkmById(guid);
	}
	/**
	 * 删除sr表
	 */
	public int deletesr(String xmlxbh) {
		return xmxxDao.deletesr(xmlxbh);
		
	}
	/**
	 * 删除zc表
	 */
	public int deletezc(String xmlxbh) {
		return xmxxDao.deletezc(xmlxbh);
		
	}
	/**
	 * 删除jjfl表
	 */
	public int deletejjfl(String xmlxbh) {
		return xmxxDao.deletejjfl(xmlxbh);
		
	}
}
