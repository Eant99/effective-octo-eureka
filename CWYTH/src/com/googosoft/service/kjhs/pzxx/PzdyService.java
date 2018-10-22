package com.googosoft.service.kjhs.pzxx;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.dao.kjhs.pzxx.PzdyDao;

import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Service("pzdyService")
public class PzdyService {

	@Resource(name="pzdyDao")
	PzdyDao pzdyDao;
	
	//获取交互数据
		public Map<String, Object> getEchoData(PageData pd){
			String type = pd.getString("type");
			Map<String, Object> map = null;
			switch (type) {
			case "kmbh":
				map = pzdyDao.getKjkmInfo(WindowUtil.getText(pd.getString("kmbh")));
				break;
			case "jjfl":
				map = pzdyDao.getJjkmInfo(WindowUtil.getText(pd.getString("jjfl")));
				break;
			case "bmbh":
				map = pzdyDao.getBmInfo(WindowUtil.getText(pd.getString("bmbh")));
				break;
			case "xmbh":
				map = pzdyDao.getXmInfo(WindowUtil.getText(pd.getString("xmbh")),WindowUtil.getText(pd.getString("bmbh")));
				break;
			default:
				break;
			}
			return map;
		}
		//联想输入
		public String getSuggestInfo(PageData pd) {
			String val = pd.getString("inputvalue");
			String menu = pd.getString("menu");
			String result = "";
			switch (menu) {
			case "zy":
				result = pzdyDao.getSgstZy(val);
				break;
			case "kmbh":
				result = pzdyDao.getSgstKmbh(val);
				break;
			case "jjfl":
				result = pzdyDao.getSgstJjfl(val);
				break;
			case "bmbh":
				result = pzdyDao.getSgstDwbh(val);
				break;
			case "xmbh":
				String bmbh = pd.getString("bmbh");
				result = pzdyDao.getSgstXmbh(WindowUtil.getText(bmbh), val);
				break;
			case "zyValidate":
				result = pzdyDao.validateZy(val);
				break;
			case "kmbhValidate":
				result = pzdyDao.validateKmbh(val);
				break;
			case "jjflValidate":
				result = pzdyDao.validateJjfl(val);
				break;
			case "bmbhValidate":
				result = pzdyDao.validateDwbh(val);
				break;
			case "xmbhValidate":
				bmbh = pd.getString("bmbh");
				result = pzdyDao.validateXmbh(WindowUtil.getText(bmbh),val);
				break;
			default:
				break;
			}
			return result;
		}
		/**
		 * 查询主表信息
		 * @param pznd	年
		 * @param kjqj	月
		 * @param pzbh
		 * @param sszt
		 * @return
		 */
		public Map<String, Object> getPzlrMain(String pznd,String kjqj,String pzbh,String sszt,String pzlx){
			return pzdyDao.getPzlrMain(pznd,kjqj,pzbh,sszt,pzlx);
		}
		public String getminPzbh(String sszt,String pznd,String kjqj,String pzz) {
			return pzdyDao.getminPzbh(sszt, pznd, kjqj, pzz);
		}
		public String getPzlx(String sszt,String pznd,String kjqj){
			return pzdyDao.getPzlx(sszt, pznd, kjqj);
		}
		/**
		 * 获取凭证录入详细信息
		 * @param pznd
		 * @param kjqj
		 * @param pzbh
		 * @param sszt
		 * @return
		 */
		public List<Map<String, Object>> getPzlrDetail(String pznd,String kjqj,String pzbh,String sszt,String pzlx){
			return pzdyDao.getPzlrDetail(pznd, kjqj, pzbh, sszt,pzlx);
		}
		/**
		 * 获取凭证编号列表
		 * @param pznd
		 * @param kjqj
		 * @param sszt
		 * @return
		 */
		public List<String> getPzbhList(String pznd,String kjqj,String sszt,String pzlx){
			return pzdyDao.getPzbhList(pznd, kjqj, sszt,pzlx);
		}
		//新增凭证录入
		@Transactional
		public int addPzlr(PageData pd,String type) {
			Gson gson = new Gson();
			int result = 0;
			String sszt = pd.getString("sszt");
			//插入主表数据
			String zbguid = GenAutoKey.get32UUID();
			if("ADD".equals(type)) {
				result += pzdyDao.insertPzlrZb(zbguid, pd);
			}else {
				result += pzdyDao.updatePzlrZb(zbguid, pd);
			}
			//如果是从报销单据生成的凭证，则向凭证录入报销对照表插入一条数据
			String bxid = pd.getString("bxid");
			if(!Validate.isNullOrEmpty(bxid)) {
				pzdyDao.insertPzlrbxdzb(zbguid, bxid);
			}
			//
			String mxJson = pd.getString("mxJson");
			String fzJson = pd.getString("fzJson");
			Map<String, Object> mxMap = gson.fromJson(mxJson, new TypeToken<HashMap<String,Object>>(){}.getType());
			Map<String, Object> fzMap = gson.fromJson(fzJson, new TypeToken<HashMap<String,Object>>(){}.getType());
			List<Map<String,String>> mxList = (List<Map<String, String>>)mxMap.get("list");
			List<Map<String,String>> fzList = (List<Map<String, String>>)fzMap.get("list");
			for (int i=1;i<mxList.size();i++) {
				String kmbh = GenAutoKey.get32UUID();
				pzdyDao.insertPzlrMxb(kmbh, zbguid, mxList.get(i),sszt);
				pzdyDao.insertPzlrFzb(kmbh, fzList.get(i));
				pzdyDao.insertPzlrzsb(kmbh, fzList.get(i));
				result ++;
			}
			return result;
		}
		//更新凭证录入
		@Transactional
		public int updatePzlr(PageData pd) {
			String sszt = pd.getString("sszt");
			String pzbh = pd.getString("pzbh");
			String pzz = pd.getString("pzz");
			pzdyDao.deletePzzb(pzbh, pzz, sszt);
			return addPzlr(pd,"");
		}
		//获取自动的凭证编号
		public String getAutoPzbh(String pznd,String kjqj,int length,String sszt) {
			String max_pzbh = Validate.isNullToDefaultString(pzdyDao.getMaxPzbh(pznd,kjqj,sszt), "0");
			String pzbh = "" + (Integer.parseInt(max_pzbh) + 1);
			return autoFill(""+pzbh, length, "0");
		}
		
		/**
		 * 自动补充  生成length位数
		 * @param s
		 * @param length
		 * @param c
		 * @return
		 */
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
		//获取凭证字列表
		public List<Map<String, Object>> getPzzList(String sszt){
			return pzdyDao.getPzzList(sszt);
		}
		//获取凭证编号列表
		public List<String> getPzbhList(String pznd,String kjqj,String sszt){
			return pzdyDao.getPzbhList(pznd, kjqj, sszt);
		}
		//查询复核人
		public Object getFhr(String pzbh,String pzz,String sszt,String pznd,String kjqj){
			return pzdyDao.getFhr(pzbh,pzz,sszt,pznd,kjqj);
		}
		//查询凭证必有
		public Object getBybwkm(String pzz,String sszt,String pznd,String kjqj) {
			return pzdyDao.getBybwkm(pzz,sszt,pznd,kjqj);
		}
		//主表信息
		public Map<String, Object> getPzlrZbInfo(String pzz,String pzbh,String sszt,String pznd,String kjqj){
			return pzdyDao.selectPzlrzb(pzz,pzbh,sszt, pznd, kjqj);
		}
		//凭证录入辅助和明细信息
		public List<Map<String, Object>> getPzlrMxFzInfo(String pzz,String pzbh,String sszt,String pznd,String kjqj){
			return pzdyDao.selectPzlrmxAndFzlr(pzz, pzbh,sszt,pznd,kjqj);
		}
		/**
		 * 账套信息
		 * @param ztid
		 * @return
		 */
		public Map<String, Object> getZtxx(String ztid){
			return pzdyDao.getZtXx(ztid);
		}
		/**
		 * 根据报销信息生成凭证信息
		 * @param type
		 * @param guid
		 * @return
		 */
		public List<Map<String, Object>> getPzJsonByBx(String type,String guid){
			if("rcbx".equals(type)) {
				return pzdyDao.getPzListByRcbx(guid);
			}else if("clfbx".equals(type)) {
				return pzdyDao.getPzListByClbx(guid);
			}else if("gwjdfbx".equals(type)) {
				return pzdyDao.getPzListByGwjdbx(guid);
			}
			return pzdyDao.getPzListByGwjdbx(guid);
		}
		/**
		 *  复核
		 * @param guid
		 * @return
		 */
		@Transactional
		public int updateFh(String guid ,String pzzt,String fhr) {
			return pzdyDao.saveFh(guid,pzzt,fhr);
		}
		
		/**
		 * 取消复核
		 * @param guid
		 * @param pzzt
		 * @param fhr
		 * @return
		 */
		public int updateFhqx(String guid ,String pzzt,String fhr) {
			return pzdyDao.saveFhqx(guid,pzzt,fhr);
		}
		
		public int qxfh(String guid ,String pzzt,String fhyj) {
			return pzdyDao.qxfh(guid,pzzt,fhyj);
		}
		@Transactional
		public int plfh(String guid ,String pzzt,String qspdh,String jspdh) {
			return pzdyDao.plfh(guid,pzzt,qspdh,jspdh);
		}
		@Transactional
		public int plfhzdr(String guid ,String pzzt,String qspdh,String jspdh,String zdr) {
			return pzdyDao.plfhzdr(guid,pzzt,qspdh,jspdh,zdr);
		}
		@Transactional
		public int plfhrq(String guid ,String pzzt,String qsrq,String jsrq) {
			return pzdyDao.plfhrq(guid,pzzt,qsrq,jsrq);
		}
		@Transactional
		public int plfhrqzdr(String guid ,String pzzt,String qsrq,String jsrq,String zdr) {
			return pzdyDao.plfhrqzdr(guid,pzzt,qsrq,jsrq,zdr);
		}
		
		public List getPzlrZbList(String pzzt,String pzz,String sszt) {
			return pzdyDao.getPzlrZbList(pzzt,pzz,sszt);
		}
		
		public List getZdrList(String pzzt,String sszt) {
			return pzdyDao.getZdrList(pzzt,sszt);
		}
		
		public String pzbhwfh(String pznd,String kjqj,String sszt){
			return pzdyDao.pzbhwfh(pznd,kjqj,sszt);
		}
		//账套信息
		public Map<String, Object> getKjqj(String ztid){
			return pzdyDao.getKjqj(ztid);
		}
		public List<Map<String, Object>> getPzlrMain(String qspdh,String jspdh, String qsrq, String jsrq, String zdr,String pzzt, String sszt, String kjzd) {
			// TODO Auto-generated method stub
			return pzdyDao.getPzlrMain(qspdh,jspdh,qsrq,jsrq,zdr,pzzt, sszt, kjzd);
		}
}
