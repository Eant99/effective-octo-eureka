package com.googosoft.service.pzxx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.pzjz.PzjzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Service("pzjzService")
public class PzjzService extends BaseService {
	
	@Resource(name="pzjzDao")
	private PzjzDao pzjzDao;
	
	/**
	 * 记账
	 * @param pd
	 * @param type
	 * @param userid
	 * @return
	 */
	@Transactional
	public int doDeal(PageData pd,String type,String userid) {
		int result=pzjzDao.doDeal(pd,type,userid);
		if(result>0){
			doAddOplog(OplogFlag.ADD,"凭证记账");
		}
		return result;
	}
	/**
	 *  打印
	 * @param pd
	 * @return
	 */
	public List getPrintInfoByIds(PageData pd) {
		return pzjzDao.getPrintInfoByIds(pd);
	}
	public String getdwmcByIds(String pzbh) {
		return pzjzDao.getdwmcByIds(pzbh);
	}
	
	public Map getInfo(PageData pd) {
		return pzjzDao.getInfo(pd);
	}
	
	//主表信息
	public Map<String, Object> getPzlrZbInfo(String pznd,String kjqj,String pzbh,String sszt){
		return pzjzDao.selectPzlrzb(pznd,kjqj,pzbh,sszt);
	}
	//凭证录入辅助和明细信息
	public List<Map<String, Object>> getPzlrMxFzInfo(String pznd,String kjqj,String pzbh,String sszt){
		return pzjzDao.selectPzlrmxAndFzlr(pznd,kjqj, pzbh,sszt);
	}
	
	//获取凭证字列表
	public List<Map<String, Object>> getPzzList(String sszt){
		return pzjzDao.getPzzList(sszt);
	}
	//获取凭证编号列表
	public List<String> getPzbhList(String pznd,String kjqj,String sszt){
		return pzjzDao.getPzbhList(pznd,kjqj,sszt);
	}
	
	//账套信息
	public Map<String, Object> getZtxx(String ztid){
		return pzjzDao.getZtXx(ztid);
	}
	//查询复核人
	public Object getFhr(String pzbh,String sszt,String pznd,String kjqj){
		return pzjzDao.getFhr(pzbh,sszt,pznd,kjqj);
	}
	//查询凭证必有
	public Object getBybwkm(String pzz,String sszt,String pznd,String kjqj) {
		return pzjzDao.getBybwkm(pzz,sszt,pznd,kjqj);
	}
	
	//获取自动的凭证编号
	public String getAutoPzbh(String pznd,String kjqj,int length,String sszt) {
		String max_pzbh = Validate.isNullToDefaultString(pzjzDao.getMaxPzbh(pznd,kjqj,sszt), "0");
		String pzbh = "" + (Integer.parseInt(max_pzbh) + 1);
		return autoFill(""+pzbh, length, "0");
	}
	public String autoFill(String s,int length,String c) {
		int s_len = s.length();
		if(s_len < length) {
			int diff = length - s_len;
			for(int i=0;i<diff;i++) {
				s = c+s;
			}
		}
		return s;
	}
	
	//获取交互数据
			public Map<String, Object> getEchoData(PageData pd){
				String type = pd.getString("type");
				Map<String, Object> map = null;
				switch (type) {
				case "kmbh":
					map = pzjzDao.getKjkmInfo(WindowUtil.getText(pd.getString("kmbh")));
					break;
				case "jjfl":
					map = pzjzDao.getJjkmInfo(WindowUtil.getText(pd.getString("jjfl")));
					break;
				case "bmbh":
					map = pzjzDao.getBmInfo(WindowUtil.getText(pd.getString("bmbh")));
					break;
				case "xmbh":
					map = pzjzDao.getXmInfo(WindowUtil.getText(pd.getString("xmbh")),WindowUtil.getText(pd.getString("bmbh")));
					break;
				default:
					break;
				}
				return map;
			}
			
			public String pzbhwfh(String guid){
				return pzjzDao.pzbhwfh(guid);
			}
			
			//账套信息
			public Map<String, Object> getKjqj(String ztid){
				return pzjzDao.getKjqj(ztid);
			}
			/**
			 * 获取当前会计区间
			 * @param sszt
			 * @return
			 */
			public String getDqkjqj(String sszt) {
				return pzjzDao.getDqkjqj(sszt);
			}
			public String getDqkjqjYear(String sszt) {
				return pzjzDao.getDqkjqjYear(sszt);
			}
			
			public Map getDqkjqjMin(String sszt) {
				return pzjzDao.getDqkjqjMin(sszt);
			}
}
