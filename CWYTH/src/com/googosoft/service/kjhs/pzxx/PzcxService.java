package com.googosoft.service.kjhs.pzxx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.kjhs.pzxx.PzcxDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Service("pzcxService")
public class PzcxService extends BaseService{
	@Resource(name="pzcxDao")
	public PzcxDao Dao;
	
	@Transactional
	public int doDeal(PageData pd,String type,String guid,HttpSession session) {
		int result=Dao.doDeal(pd,type,guid,session);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"地点基础信息");
		}
		return result;
	}
	
	public String checkIsCx(String guid) {
		String result=Dao.checkIsCx(guid);
		return result;
	}
	
	public String checkIsOrNoCx(String guid){
		return Dao.checkIsOrNoCx(guid);
	}
	
	public String checkIsQxCx(String guid) {
		String result=Dao.checkIsQxCx(guid);
		return result;
	}
	public List getPrintInfoByIds(PageData pd) {
		return Dao.getPrintInfoByIds(pd);
	}
	public String getdwmcByIds(String pzbh) {
		return Dao.getdwmcByIds(pzbh);
	}
	public Map getInfo(PageData pd) {
		return Dao.getInfo(pd);
	}
	
	public int qxcx(String guid1,String pzbh,String sszt) {
		return Dao.qxcx(guid1,pzbh,sszt);
	}
	
	//主表信息
		public Map<String, Object> getPzlrZbInfo(String pznd,String kjqj,String pzbh,String sszt){
			return Dao.selectPzlrzb(pznd,kjqj,pzbh,sszt);
		}
		//凭证录入辅助和明细信息
		public List<Map<String, Object>> getPzlrMxFzInfo(String pznd,String kjqj,String pzbh,String sszt){
			return Dao.selectPzlrmxAndFzlr(pznd,kjqj, pzbh,sszt);
		}
		
		//获取凭证字列表
		public List<Map<String, Object>> getPzzList(String sszt){
			return Dao.getPzzList(sszt);
		}
		//获取凭证编号列表
		public List<String> getPzbhList(String pznd,String kjqj,String sszt){
			return Dao.getPzbhList(pznd,kjqj ,sszt);
		}
		
		//账套信息
		public Map<String, Object> getZtxx(String ztid){
			return Dao.getZtXx(ztid);
		}
		//查询复核人
		public Object getFhr(String pzbh,String pznd,String kjqj,String sszt){
			return Dao.getFhr(pzbh,pznd,kjqj,sszt);
		}
		//查询凭证必有
		public Object getBybwkm(String pzz,String sszt) {
			return Dao.getBybwkm(pzz,sszt);
		}
		
		//获取自动的凭证编号
		public String getAutoPzbh(String pznd,String kjqj,int length,String sszt) {
			String max_pzbh = Validate.isNullToDefaultString(Dao.getMaxPzbh(pznd,kjqj,sszt), "0");
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
						map = Dao.getKjkmInfo(WindowUtil.getText(pd.getString("kmbh")));
						break;
					case "jjfl":
						map = Dao.getJjkmInfo(WindowUtil.getText(pd.getString("jjfl")));
						break;
					case "bmbh":
						map = Dao.getBmInfo(WindowUtil.getText(pd.getString("bmbh")));
						break;
					case "xmbh":
						map = Dao.getXmInfo(WindowUtil.getText(pd.getString("xmbh")),WindowUtil.getText(pd.getString("bmbh")));
						break;
					default:
						break;
					}
					return map;
				}
	
				public String pzbhwfh(String guid){
					return Dao.pzbhwfh(guid);
				}
				
	// 账套信息
	public Map<String, Object> getKjqj(String ztid) {
		return Dao.getKjqj(ztid);
	}
}
