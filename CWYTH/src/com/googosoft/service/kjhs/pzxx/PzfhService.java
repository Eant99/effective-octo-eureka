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
import com.googosoft.dao.kjhs.pzxx.PzfhDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Service("pzfhService")
public class PzfhService {

	@Resource(name="pzfhDao")
	PzfhDao pzfhDao;
	
	//获取交互数据
		public Map<String, Object> getEchoData(PageData pd){
			String type = pd.getString("type");
			Map<String, Object> map = null;
			switch (type) {
			case "kmbh":
				map = pzfhDao.getKjkmInfo(WindowUtil.getText(pd.getString("kmbh")));
				break;
			case "jjfl":
				map = pzfhDao.getJjkmInfo(WindowUtil.getText(pd.getString("jjfl")));
				break;
			case "bmbh":
				map = pzfhDao.getBmInfo(WindowUtil.getText(pd.getString("bmbh")));
				break;
			case "xmbh":
				map = pzfhDao.getXmInfo(WindowUtil.getText(pd.getString("xmbh")),WindowUtil.getText(pd.getString("bmbh")));
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
				result = pzfhDao.getSgstZy(val);
				break;
			case "kmbh":
				result = pzfhDao.getSgstKmbh(val);
				break;
			case "jjfl":
				result = pzfhDao.getSgstJjfl(val);
				break;
			case "bmbh":
				result = pzfhDao.getSgstDwbh(val);
				break;
			case "xmbh":
				String bmbh = pd.getString("bmbh");
				result = pzfhDao.getSgstXmbh(WindowUtil.getText(bmbh), val);
				break;
			case "zyValidate":
				result = pzfhDao.validateZy(val);
				break;
			case "kmbhValidate":
				result = pzfhDao.validateKmbh(val);
				break;
			case "jjflValidate":
				result = pzfhDao.validateJjfl(val);
				break;
			case "bmbhValidate":
				result = pzfhDao.validateDwbh(val);
				break;
			case "xmbhValidate":
				bmbh = pd.getString("bmbh");
				result = pzfhDao.validateXmbh(WindowUtil.getText(bmbh),val);
				break;
			default:
				break;
			}
			return result;
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
				result += pzfhDao.insertPzlrZb(zbguid, pd);
			}else {
				result += pzfhDao.updatePzlrZb(zbguid, pd);
			}
			//如果是从报销单据生成的凭证，则向凭证录入报销对照表插入一条数据
			String bxid = pd.getString("bxid");
			if(!Validate.isNullOrEmpty(bxid)) {
				pzfhDao.insertPzlrbxdzb(zbguid, bxid);
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
				pzfhDao.insertPzlrMxb(kmbh, zbguid, mxList.get(i),sszt);
				pzfhDao.insertPzlrFzb(kmbh, fzList.get(i));
				pzfhDao.insertPzlrzsb(kmbh, fzList.get(i));
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
			pzfhDao.deletePzzb(pzbh, pzz, sszt);
			return addPzlr(pd,"");
		}
		//获取自动的凭证编号
		public String getAutoPzbh(String pznd,String kjqj,int length,String sszt) {
			String max_pzbh = Validate.isNullToDefaultString(pzfhDao.getMaxPzbh(pznd,kjqj,sszt), "0");
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
			return pzfhDao.getPzzList(sszt);
		}
		//获取凭证编号列表
		public List<String> getPzbhList(String pznd,String kjqj,String sszt){
			return pzfhDao.getPzbhList(pznd, kjqj, sszt);
		}
		//查询复核人
		public Object getFhr(String pzbh,String pzz,String sszt,String pznd,String kjqj){
			return pzfhDao.getFhr(pzbh,pzz,sszt,pznd,kjqj);
		}
		//查询凭证必有
		public Object getBybwkm(String pzz,String sszt,String pznd,String kjqj) {
			return pzfhDao.getBybwkm(pzz,sszt,pznd,kjqj);
		}
		//主表信息
		public Map<String, Object> getPzlrZbInfo(String pzz,String pzbh,String sszt,String pznd,String kjqj){
			return pzfhDao.selectPzlrzb(pzz,pzbh,sszt, pznd, kjqj);
		}
		//凭证录入辅助和明细信息
		public List<Map<String, Object>> getPzlrMxFzInfo(String pzz,String pzbh,String sszt,String pznd,String kjqj){
			return pzfhDao.selectPzlrmxAndFzlr(pzz, pzbh,sszt,pznd,kjqj);
		}
		/**
		 * 账套信息
		 * @param ztid
		 * @return
		 */
		public Map<String, Object> getZtxx(String ztid){
			return pzfhDao.getZtXx(ztid);
		}
		/**
		 * 根据报销信息生成凭证信息
		 * @param type
		 * @param guid
		 * @return
		 */
		public List<Map<String, Object>> getPzJsonByBx(String type,String guid){
			if("rcbx".equals(type)) {
				return pzfhDao.getPzListByRcbx(guid);
			}else if("clfbx".equals(type)) {
				return pzfhDao.getPzListByClbx(guid);
			}else if("gwjdfbx".equals(type)) {
				return pzfhDao.getPzListByGwjdbx(guid);
			}
			return pzfhDao.getPzListByGwjdbx(guid);
		}
		/**
		 *  复核
		 * @param guid
		 * @return
		 */
		@Transactional
		public int updateFh(String guid ,String pzzt,String fhr) {
			return pzfhDao.saveFh(guid,pzzt,fhr);
		}
		
		/**
		 * 取消复核
		 * @param guid
		 * @param pzzt
		 * @param fhr
		 * @return
		 */
		public int updateFhqx(String guid ,String pzzt,String fhr) {
			return pzfhDao.saveFhqx(guid,pzzt,fhr);
		}
		
		public int qxfh(String guid ,String pzzt,String fhyj) {
			return pzfhDao.qxfh(guid,pzzt,fhyj);
		}
		@Transactional
		public int plfh(String guid ,String pzzt,String qspdh,String jspdh) {
			return pzfhDao.plfh(guid,pzzt,qspdh,jspdh);
		}
		@Transactional
		public int plfhzdr(String guid ,String pzzt,String qspdh,String jspdh,String zdr) {
			return pzfhDao.plfhzdr(guid,pzzt,qspdh,jspdh,zdr);
		}
		@Transactional
		public int plfhrq(String guid ,String pzzt,String qsrq,String jsrq) {
			return pzfhDao.plfhrq(guid,pzzt,qsrq,jsrq);
		}
		@Transactional
		public int plfhrqzdr(String guid ,String pzzt,String qsrq,String jsrq,String zdr) {
			return pzfhDao.plfhrqzdr(guid,pzzt,qsrq,jsrq,zdr);
		}
		
		public List getPzlrZbList(String pzzt,String pzz,String sszt) {
			return pzfhDao.getPzlrZbList(pzzt,pzz,sszt);
		}
		
		public List getZdrList(String pzzt,String sszt) {
			return pzfhDao.getZdrList(pzzt,sszt);
		}
		
		public String pzbhwfh(String pznd,String kjqj,String sszt){
			return pzfhDao.pzbhwfh(pznd,kjqj,sszt);
		}
		//账套信息
		public Map<String, Object> getKjqj(String ztid){
			return pzfhDao.getKjqj(ztid);
		}
		public List<Map<String, Object>> getPzlrMain(String qspdh,String jspdh, String qsrq, String jsrq, String zdr,String pzzt, String sszt, String kjzd) {
			// TODO Auto-generated method stub
			return pzfhDao.getPzlrMain(qspdh,jspdh,qsrq,jsrq,zdr,pzzt, sszt, kjzd);
		}
}
