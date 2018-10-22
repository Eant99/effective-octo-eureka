package com.googosoft.service.kjhs.pzxx;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.fzgn.jcsz.expExcel.YhxxExportExcel;
import com.googosoft.dao.kjhs.pzxx.PzlrDao;
import com.googosoft.dao.wsbx.bxzf.BxzfDao;
import com.googosoft.pojo.Xtxx;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.kylbx.BxywcxExportExcel;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service("pzlrService")
public class PzlrService extends BaseService{

	@Resource(name="pzlrDao")
	private PzlrDao pzlrDao;
	@Autowired
	BxzfDao bxzfDao;
	
	private Gson gson = new Gson();
	/**
	 * 获取辅助录入交互数据
	 * @param pd
	 * @return
	 */
	public Map<String, Object> getEchoData(PageData pd,HttpSession session){
		String type = pd.getString("type");
		String pzzbguid = pd.getString("pzzbguid");
		Map<String, Object> map = null;
		switch (type) {
		case "kmbh":
			map = pzlrDao.getKjkmInfo(WindowUtil.getText(pd.getString("kmbh")),pd.getString("kjzd"));
			break;
		case "jjfl":
			map = pzlrDao.getJjkmInfo(WindowUtil.getText(pd.getString("jjfl")));
			break;
		case "bmbh":
			map = pzlrDao.getBmInfo(WindowUtil.getText(pd.getString("bmbh")));
			break;
		case "xmbh":
			map = pzlrDao.getXmInfo(WindowUtil.getText(pd.getString("xmbh")),WindowUtil.getText(pd.getString("bmbh")),pzzbguid);
			String syje = Validate.isNullToDefaultString(map.get("SYJE"),"");//剩余金额
			map.put("XMYE", syje);  //要这个值
			break;
		default:
			break;
		}
		return map;
	}
	/**
	 * 联想输入
	 * @param pd
	 * @return
	 */
	public String getSuggestInfo(PageData pd) {
		String val = pd.getString("inputvalue");
		String kjzd=pd.getString("kjzd");
		String menu = pd.getString("menu");
		String result = "";
		switch (menu) {
		case "zy":
			result = pzlrDao.getSgstZy(val);
			break;
		case "kmbh":
			result = pzlrDao.getSgstKmbh(val,kjzd);
			break;
		case "jjfl":
			result = pzlrDao.getSgstJjfl(val);
			break;
		case "bmbh":
			result = pzlrDao.getSgstDwbh(val);
			break;
		case "xmbh":
			String bmbh = pd.getString("bmbh");
			result = pzlrDao.getSgstXmbh(WindowUtil.getText(bmbh), val);
			break;
		case "zyValidate":
			result = pzlrDao.validateZy(val);
			break;
		case "kmbhValidate":
			result = pzlrDao.validateKmbh(val,kjzd);
			break;
		case "jjflValidate":
			result = pzlrDao.validateJjfl(val);
			break;
		case "bmbhValidate":
			result = pzlrDao.validateDwbh(val);
			break;
		case "xmbhValidate":
			bmbh = pd.getString("bmbh");
			result = pzlrDao.validateXmbh(WindowUtil.getText(bmbh),val);
			break;
		default:
			break;
		}
		return result;
	}
	/**
	 * 根据报销单据自动生成凭证
	 * @param bxid
	 * @param type
	 * @param kjzd  会计制度
	 * @param sszt
	 * @return
	 */
	@Transactional
	public int autoCreatePzlrByBx(String bxid,String type,String kjzd,String sszt,String pzlx) {
		// kjzd = 会计制度,sszt = 账套id
		//凭证自动化   pzzdhMap 一些自动生成信息
		Map<String, Object> pzzdhMap = pzlrDao.getZdscpz();
		String zdscpz = Validate.isNullToDefaultString(pzzdhMap.get("zdscpz"), "");
		String zdlrpz = Validate.isNullToDefaultString(pzzdhMap.get("zdlrpz"), "");
		String pzzt = "00";
		int i = 0;
		if("Y".equals(zdscpz)) {
			if("Y".equals(zdlrpz)) {
				pzzt = "02";
			}else{
				pzzdhMap.put("zdr", LUser.getGuid());
				pzzdhMap.put("fhr", "");
				pzzdhMap.put("jzr", "");
			}
			//报销凭证的guid  变量  bxid pzbbid 都是这个值
			String pzzbid = Validate.isNullToDefaultString(bxid, GenAutoKey.get32UUID());
			// kjqjmap 此处获取 当前年度 和 会计期间   即 年 月 值
			Map<String, Object> kjqjMap = getWjzDate(sszt,pzlx);
			String ztnd = kjqjMap.get("ztnd")+"";
			String kjqj = ""+kjqjMap.get("kjqj");
			int year = Integer.parseInt(ztnd);
			int month = Integer.parseInt(kjqj);
			Map<String, Object> pzlxMap = getPzlxmc(pzlx);  //此处 得 pzlxbh=01 pzlxmc=记
			String pzz=pzlxMap.get("pzlxbh")+"";  //pzz=01
			String pzlxmc=pzlxMap.get("pzlxmc")+""; //pzlcmc = ji
			// kjqjmap 此处获取 当前日期   即 日 值
			kjqjMap.put("pzrq", checkDate(year,month));
			
			//获取自动生成的凭证编号
			String autoPzbh;
			String sfbsc =  CheckSfbsc()  ;//是否补充删除凭证号  1是补充  0 是自动追加
			if(sfbsc.equals("1")){
				autoPzbh = GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzlx);
			}else{  //（规则为已存在的最大凭证编号加一）
				autoPzbh= getAutoPzbh(ztnd,kjqj, 4,sszt,pzlx);
			}
			
			switch (type) {
			case "clfbx":
				i += pzlrDao.autoInsertPzzbByClbx(pzzbid, sszt, bxid,pzzt,kjqjMap,pzzdhMap,kjzd,pzz,pzlxmc,autoPzbh);
				i += pzlrDao.autoInsertPzmxByClbx(pzzbid, sszt, bxid,kjzd);
				//向凭证录入报销对照表插入一条信息
				pzlrDao.insertPzlrbxdzb(pzzbid, bxid,type);
				pzlrDao.autoInsertFzlrByClbx(pzzbid, sszt, bxid, kjzd);
				//生成凭证更新业务表状态为9
				pzlrDao.inertClfbxb(bxid);
				if(i>0){
					doAddOplog(OplogFlag.ADD,"增加差旅费报销凭证",pzzbid);
				}
				break;
			case "gwjdbx":
				i += pzlrDao.autoInsertPzzbByGwjdbx(pzzbid, sszt, bxid,pzzt,kjqjMap,pzzdhMap,kjzd,pzz,pzlxmc,autoPzbh);
				i += pzlrDao.autoInsertPzmxByGwjdbx(pzzbid, sszt, bxid,kjzd);
				//向凭证录入报销对照表插入一条信息
				pzlrDao.insertPzlrbxdzb(pzzbid, bxid,type);
				pzlrDao.autoInsertFzlrByGwjdbx(pzzbid, sszt, bxid, kjzd);
				//生成凭证更新业务表状态为9
				pzlrDao.inertGwjdbxb(bxid);
				if(i>0){
					doAddOplog(OplogFlag.ADD,"增加公务接待报销凭证",pzzbid);
				}
				break;
			case "rcbx":
				i += pzlrDao.autoInsertPzzbByRcbx(pzzbid, sszt, bxid,pzzt,kjqjMap,pzzdhMap,kjzd,pzz,pzlxmc,autoPzbh);
				i += pzlrDao.autoInsertPzmxByRcbx(pzzbid, sszt, bxid,kjzd);
				//向凭证录入报销对照表插入一条信息
				pzlrDao.insertPzlrbxdzb(pzzbid, bxid,type);
				pzlrDao.autoInsertFzlrByRcbx(pzzbid, sszt, bxid, kjzd);
				//生成凭证更新业务表状态为9
				pzlrDao.inertRxbxb(bxid);
				if(i>0){
					doAddOplog(OplogFlag.ADD,"增加日常报销凭证",pzzbid);
				}
				break;
			case "srsblr":
				i += pzlrDao.autoInsertPzzbByGrsr(pzzbid, sszt, bxid,pzzt,kjqjMap,pzzdhMap,kjzd,pzz,pzlxmc,autoPzbh);
				i += pzlrDao.autoInsertPzmxByGrsr(pzzbid, sszt, bxid,kjzd);
				//向凭证录入报销对照表插入一条信息
				pzlrDao.insertPzlrbxdzb(pzzbid, bxid,type);
//				pzlrDao.autoInsertFzlrByRcbx(pzzbid, sszt, bxid, kjzd);
				//生成凭证更新业务表状态为9
				pzlrDao.inertSrsbb(bxid);
				if(i>0){
					doAddOplog(OplogFlag.ADD,"增加收入申报凭证",pzzbid);
				}
				break;
			case "jkbx":
				i += pzlrDao.autoInsertPzzbByJkbx(pzzbid, sszt, bxid,pzzt,kjqjMap,pzzdhMap,kjzd,pzz,pzlxmc,autoPzbh);
				i += pzlrDao.autoInsertPzmxByJkbx(pzzbid, sszt, bxid,kjzd);
				//向凭证录入报销对照表插入一条信息
				pzlrDao.insertPzlrbxdzb(pzzbid, bxid,type);
				pzlrDao.autoInsertFzlrByJkbx(pzzbid, sszt, bxid, kjzd);
				//生成凭证更新业务表状态为9
				pzlrDao.inertJkbxb(bxid);
				if(i>0){
					doAddOplog(OplogFlag.ADD,"增加借款报销凭证",pzzbid);
				}
				break;
			case "glf":
				i += pzlrDao.autoInsertPzzbByGlf(pzzbid, sszt, bxid,pzzt,kjqjMap,pzzdhMap,kjzd,pzz,pzlxmc,autoPzbh);
				i += pzlrDao.autoInsertPzmxByGlf(pzzbid, sszt, bxid,kjzd);
				//向凭证录入报销对照表插入一条信息
				pzlrDao.insertPzlrbxdzb(pzzbid, bxid,type);
//				pzlrDao.autoInsertFzlrByJkbx(pzzbid, sszt, bxid, kjzd);
				//生成凭证更新业务表状态为1
				pzlrDao.inertGlf(bxid);
				if(i>0){
					doAddOplog(OplogFlag.ADD,"增加借款报销凭证",pzzbid);
				}
				break;
			default:
				break;
			}
			
		}
		return i;
	}
	/**
	 * 凭证日期核对
	 * @param year 指定年
	 * @param month 制定月
	 * @return 核对后的日期
	 */
	public String checkDate(int year,int month) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		//当前天
		Calendar cal = Calendar.getInstance();
		int dayNow = cal.get(Calendar.DATE);
		//指定月的最后一天
		Calendar cal_max = Calendar.getInstance();
		cal_max.set(Calendar.YEAR,year );
		cal_max.set(Calendar.MONTH, month-1);
		cal_max.set(Calendar.DATE, 1);
		int maxDayOfMonth = cal_max.getActualMaximum(Calendar.DATE);
		//比较
		if(dayNow < maxDayOfMonth) {
			cal_max.set(Calendar.DATE, dayNow);
		}
		return fmt.format(cal_max.getTime());
	}
	/**
	 * 判断 是否是  报销凭证生成
	 */
	@Transactional
	public int doCheckBxpz(String guid){
		int b =pzlrDao.doCheckBxpz(guid);	
		return  b;
	}
	/**
	 * 删除凭证
	 * @param session
	 * @param string 
	 * @param pzly 
	 * @return
	 */
	@Transactional
	public boolean deletePz(HttpSession session, String string, String pzly) {
		String guid = session.getAttribute("pzzbGuid")+"";
		boolean bool= false;
		 bool = pzlrDao.deletePzzbAll(guid,string,pzly);
		 if(bool){
			doAddOplog(OplogFlag.DEL,"删除凭证",guid);
		}
		//加回凭证减去的金额
		//bxzfDao.updateToAddJfsz(guid);
		return bool;
	}
	/**
	 * 新增凭证录入
	 * @param pd
	 * @param type
	 * @return
	 */
	@Transactional
	public int addPzlr(PageData pd,HttpSession session) {
		//凭证自动化
		Map<String, Object> pzzdh = pzlrDao.getZdscpz();
		String zdlrpz = ""+pzzdh.get("zdlrpz");
		String pzzt = "00";
		pd.put("zdr", LUser.getGuid());
		if("Y".equals(zdlrpz)) {
			pzzt = "02";
			pd.put("jzr", pzzdh.get("jzr")+"");
			pd.put("fhr", pzzdh.get("fhr")+"");
			pd.put("zdr", pzzdh.get("zdr")+"");
		}
		int result = 0;
		//插入主表数据
		String zbguid = Validate.isNullToDefaultString(pd.getString("uploadId"), GenAutoKey.get32UUID());
		result += pzlrDao.insertPzlrZb(zbguid,pzzt, pd,session);
		if(result>0){
			doAddOplog(OplogFlag.ADD,"增加凭证",zbguid);
		}
		String mxJson = pd.getString("mxJson");
		String fzJson = pd.getString("fzJson");
		Map<String, Object> mxMap = gson.fromJson(mxJson, new TypeToken<HashMap<String,Object>>(){}.getType());
		Map<String, Object> fzMap = gson.fromJson(fzJson, new TypeToken<HashMap<String,Object>>(){}.getType());
		@SuppressWarnings("unchecked")
		List<Map<String,String>> mxList = (List<Map<String, String>>)mxMap.get("list");
		@SuppressWarnings("unchecked")
		List<Map<String,String>> fzList = (List<Map<String, String>>)fzMap.get("list");
		//第一行是空数据，不用遍历
		for (int i=1;i<mxList.size();i++) {   
			if(!mxList.get(i).isEmpty()&&mxList.get(i).size()>0&&Validate.noNull(mxList.get(i))&&mxList.get(i)!=null){
				String mxGuid = Validate.isNullToDefaultString(mxList.get(i).get("mxid"), GenAutoKey.get32UUID());
				pzlrDao.insertPzlrMxb(mxGuid, zbguid, mxList.get(i),session,i);
				pzlrDao.insertPzlrFzb(mxGuid, fzList.get(i));
				pzlrDao.insertPzlrzsb(mxGuid, fzList.get(i));
//				pzlrDao.updateJe(fzList.get(i), mxList.get(i));
				String zy = mxList.get(i).get("zy");
				boolean yzzy = pzlrDao.selectPzlrzy(zy);
				if(!yzzy) {
					pzlrDao.insertPzlrzy(zy);
				}
				result ++;
			}
		}
		return result;
	}
	/**
	 * 更新凭证录入
	 * @param pd
	 * @param type
	 * @param session
	 * @return
	 */
	@Transactional
	public int updatePzlr(PageData pd,HttpSession session) {
		//凭证自动化
		Map<String, Object> pzzdh = pzlrDao.getZdscpz();
		String zdlrpz = ""+pzzdh.get("zdlrpz");
		String pzzt = "00";
		pd.put("zdr", LUser.getGuid());

		if("Y".equals(zdlrpz)) {
			pzzt = "02";
			pd.put("jzr", pzzdh.get("jzr")+"");
			pd.put("fhr", pzzdh.get("fhr")+"");
			pd.put("zdr", pzzdh.get("zdr")+"");
		}
		int result = 0;
		//插入主表数据
		String zbguid = Validate.isNullToDefaultString(pd.getString("uploadId"), GenAutoKey.get32UUID());
		result += pzlrDao.insertPzlrZbForUpdate(zbguid,pzzt, pd, session);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"修改凭证",zbguid);
		}
		//删除因为删除明细而造成cw_pzlryhmx表中的多余数据
		String mxids = pd.getString("mxids");
		result += pzlrDao.deleteYhmxbyBtn(zbguid,mxids);
		//如果是从报销单据生成的凭证，则向凭证录入报销对照表插入一条数据
//		String bxid = pd.getString("bxid");
//		if(!Validate.isNullOrEmpty(bxid)) {
//			//获取报销类型
//			Map bxmap = pzlrDao.getBxlx(bxid);
//			String bxlx = (String) bxmap.get("bxlx");
//			pzlrDao.insertPzlrbxdzb(zbguid, bxid,bxlx);
//		}
		
		String mxJson = pd.getString("mxJson");
		String fzJson = pd.getString("fzJson");
		Map<String, Object> mxMap = gson.fromJson(mxJson, new TypeToken<HashMap<String,Object>>(){}.getType());
		Map<String, Object> fzMap = gson.fromJson(fzJson, new TypeToken<HashMap<String,Object>>(){}.getType());
		@SuppressWarnings("unchecked")
		List<Map<String,String>> mxList = (List<Map<String, String>>)mxMap.get("list");
		@SuppressWarnings("unchecked")
		List<Map<String,String>> fzList = (List<Map<String, String>>)fzMap.get("list");
		//删除 原有数据 
		pzlrDao.updatePzlrMxb(zbguid);
		pzlrDao.updatePzlrFzb(zbguid);
		pzlrDao.updatePzlrzsb(zbguid);
		//第一行是空数据，不用遍历
		for (int i=1;i<mxList.size();i++) {   
			if(!mxList.get(i).isEmpty()&&mxList.get(i).size()>0&&Validate.noNull(mxList.get(i))&&mxList.get(i)!=null){
				String mxGuid = Validate.isNullToDefaultString(mxList.get(i).get("mxid"), GenAutoKey.get32UUID());
				pzlrDao.insertPzlrMxb(mxGuid, zbguid, mxList.get(i),session,i);
				pzlrDao.insertPzlrFzb(mxGuid, fzList.get(i));
				pzlrDao.insertPzlrzsb(mxGuid, fzList.get(i));
				String zy = mxList.get(i).get("zy");
				boolean yzzy = pzlrDao.selectPzlrzy(zy);
				if(!yzzy) {
					pzlrDao.insertPzlrzy(zy);
				}
				result ++;
			}
		}
		return result;
	}
	/**
	 * 获取自动的凭证编号
	 * @param pznd
	 * @param kjqj
	 * @param length
	 * @param sszt
	 * @return
	 */
	public String getAutoPzbh(String pznd,String kjqj,int length,String sszt,String pzlx) {
		String max_pzbh = Validate.isNullToDefaultString(pzlrDao.getMaxPzbh(pznd,kjqj,sszt,pzlx), "0000");
		String pzbh = "" + (Integer.parseInt(max_pzbh) + 1);
		return autoFill(""+pzbh, length, "0");
	}
	/**
	 * 向前自动填充字符串
	 * @param s	要补全的字符串
	 * @param length 补全后的长度
	 * @param c	填充字符串
	 * @return 补全后的字符串
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
	/**
	 * 获取凭证编号列表
	 * @param pznd
	 * @param kjqj
	 * @param sszt
	 * @return
	 */
	public List<String> getPzbhList(String pznd,String kjqj,String sszt,String pzlx){
		return pzlrDao.getPzbhList(pznd, kjqj, sszt,pzlx);
	}
	/**
	 * 获取凭证类型列表
	 * @return
	 */
	public List<Map<String, Object>> getPzlxList(){
		return pzlrDao.getPzlxList();
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
		return pzlrDao.getPzlrMain(pznd,kjqj,pzbh,sszt,pzlx);
	}
	public Map<String, Object> getPzlrMain(String guid){
		return pzlrDao.getPzlrMain(guid);
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
		return pzlrDao.getPzlrDetail(pznd, kjqj, pzbh, sszt,pzlx);
	}
	public List<Map<String, Object>> getPzlrDetail(String guid){
		return pzlrDao.getPzlrDetail(guid);
	}
	public List<Map<String, Object>> getPzlrDetailByMb(String guid){
		return pzlrDao.getPzlrDetailByMb(guid);
	}
	/**
	 * 获取凭证录入明细
	 * @param guid
	 * @param kjzd
	 * @return
	 */
	public List<Map<String, Object>> getPzlrMx(String guid,String kjzd){
		return pzlrDao.getPzlrMx(guid, kjzd);
	}
	/**
	 * 获取期末结账表中没有结账的日期
	 * @param ztid
	 * @return
	 */
	public Map<String, Object> getWjzDate(String ztid,String pzlx){
		return pzlrDao.getWjzDate(ztid);
	}
	/**
	 * 根据报销信息获取凭证信息
	 * @param type
	 * @param guid
	 * @param sszt
	 * @param kjzd
	 * @return	json
	 */
	public List<Map<String, Object>> getPzJsonByBx(String type,String guid,String sszt,String kjzd){
		if("rcbx".equals(type)) {
			return pzlrDao.getPzListByRcbx(guid,sszt,kjzd);
		}else if("clfbx".equals(type)) {
			return pzlrDao.getPzListByClbx(guid,sszt,kjzd);
		}else if("gwjdfbx".equals(type)) {
			return pzlrDao.getPzListByGwjdbx(guid,sszt,kjzd);
		}
		return pzlrDao.getPzListByGwjdbx(guid,sszt,kjzd);
	}
	/**
	 * 保存报销生成凭证  插入相关表格 数据
	 * @param list
	 * @param kjzd
	 * @param sszt
	 * @param pzlx
	 * @return
	 */
	@Transactional
	public int doZf(List list,String kjzd,String sszt,String pzlx) {
		// list = checkbook的guid+报销类型（4种;） kjzd =会计制度, sszt = 账套id; pzlx = 01; (支出？)
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			autoCreatePzlrByBx(map.get("gid")+"",map.get("type")+"",kjzd,sszt,pzlx);
			bxzfDao.insertBxzf(map.get("gid")+"");
			
		}
		return list.size();
	}
	/**
	 * 根据凭证模板获取凭证信息
	 * @param guid
	 * @return	json
	 */
	public List<Map<String, Object>> getPzJsonByMb(String guid){
		return pzlrDao.getPzListByMb(guid);
	}
	/**
	 * 获取凭证状态
	 * @param sszt
	 * @param pznd
	 * @param kjqj
	 * @param pzbh
	 * @return
	 */
	public String getPzzt(String sszt,String pznd,String kjqj,String pzbh){
		return pzlrDao.getPzzt(sszt, pznd, kjqj, pzbh);
	}
	/**
	 * 获取第一个凭证
	 * @return
	 */
	public String getPzlx(){
		return pzlrDao.getPzlx();
	}
	public String getPzlx(String sszt,String pznd,String kjqj){
		return pzlrDao.getPzlx(sszt, pznd, kjqj);
	}
	/**
	 * 查询凭证类型
	 * @return
	 */
	public Map<String, Object> getPzlxmc(String pzlx){
		return pzlrDao.getPzlxmc(pzlx);
	}
	/**
	 * 
	 * @param sszt
	 * @param pznd
	 * @param kjqj
	 * @param pzz
	 * @return
	 */
	public String getPzbh(String sszt,String pznd,String kjqj,String pzz) {
		return pzlrDao.getPzbh(sszt, pznd, kjqj, pzz);
	}
	
	public String getminPzbh(String sszt,String pznd,String kjqj,String pzz) {
		return pzlrDao.getminPzbh(sszt, pznd, kjqj, pzz);
	}
	
	/**
	 * 根据主键id查询凭证是否存在
	 * @param guid
	 * @return
	 */
	public int selectPzInfo(String guid){
		return pzlrDao.selectPzInfo(guid);
	}
	
	/**
	 * 复制凭证信息
	 * @param guid
	 * @return
	 */
	@Transactional
	public String insertCopyPzInfoByGuid(String guid,String pzrq,HttpSession session,String dqnd,String dqqj,String autoPzbh){
		String result =  pzlrDao.insertCopyPzInfoByGuid(guid,pzrq,session,dqnd,dqqj,autoPzbh);
		if(result.equals("")){
			doAddOplog(OplogFlag.ADD,"增加复制凭证",guid);
		}
		return result;
	}
	
	/**
	 * 查询凭证类型
	 * @param guid
	 * @return
	 */
	public String getPzzByGuid(String guid){
		return pzlrDao.getPzzByGuid(guid);
	}
	
	/**
	 * 获取常用摘要的kmbh
	 * @param session
	 * @param zy
	 * @return
	 */
	public String getKmbhZy(HttpSession session,String zy){
		return pzlrDao.getKmbhZy(session, zy);
	}
	
	/**
	 * 复制凭证信息存为模板
	 * @param guid
	 * @return
	 */
	public boolean insertCopyPzInfoByGuidToMb(String guid,String param){
		boolean bool =pzlrDao.insertCopyPzInfoByGuidToMb(guid, param);
		if(bool){
		  doAddOplog(OplogFlag.ADD,"创建凭证模板",guid);
		}
		return bool;
	}
	public List getzd() {
		return pzlrDao.getzd();
	}
	/**
	 * 验证科目与辅助信息的关系
	 * @param session
	 * @param kmbh
	 * @return
	 */
	public Map getKmFzInfoByKmbh(HttpSession session,String kmbh){
		return pzlrDao.getKmFzInfoByKmbh(session, kmbh);
	}
	/**
	 * 获取项目 可用金额
	 */
	public BigDecimal getXmkyje(String xmbh,String bmbh,HttpSession session){
		return pzlrDao.getXmkyje(xmbh, bmbh,session);
	}
	
	/**
	 * 单据是否必传
	 * @param guid
	 * @return
	 */
	public boolean checkDj(String guid){
		return pzlrDao.checkDj(guid);
	}
	
	/**
	 * 查询开户银行信息
	 * @param type
	 * @param hm
	 * @return
	 */
	public List getKhyh(String type,String hm){
		return pzlrDao.getKhyh(type, hm);
	}
	/**
	 * 结算单号
	 * @param type
	 * @param hm
	 * @return
	 */
	public List getJsdh(String type,String hm,String pzid){
		return pzlrDao.getJsdh(type, hm,pzid);
	}
	
	/**
	 * 检查还款金额是否超出借款金额
	 * @param wldc
	 * @param money
	 * @return
	 */
	public boolean checkWldc(String wldc,String money,String pzid){
		return pzlrDao.checkWldc(wldc, money,pzid);
	}
	
	/**
	 * 检查借款单号
	 * @param zclx
	 * @param pzid
	 * @return
	 */
	public boolean checkJkdh(String zclx,String pzid){
		return pzlrDao.checkJkdh(zclx, pzid);
	}
	/**
	 * 验证经济科目是否末级
	 * @param kmbh
	 * @return
	 */
	public Map<?, ?> getObjectByIdSfmj(String kmbh) {
		return pzlrDao.getObjectByIdSfmj(kmbh);
	}
	
	/**
	 * 查询银行录入明细信息
	 * @param zbid
	 * @param mxid
	 * @return
	 */
	public List getYhmxIdByZBidAndMxId(String zbid,String mxid){
		return pzlrDao.getYhmxIdByZBidAndMxId(zbid, mxid);
	}
	
	/**
	 * 查询银行录入明细信息
	 * @param zbid
	 * @param mxid
	 * @return
	 */
	public List getYhmxIdByZBidAndMxIdView(String zbid,String mxid){
		return pzlrDao.getYhmxIdByZBidAndMxIdView(zbid, mxid);
	}
	

	/**
	 * 删除银行明细
	 * @param zbid
	 * @param mxid
	 */
	public void deleteYhmxByZbidAndMxId(String zbid,String mxid){
		int i = pzlrDao.deleteYhmxByZbidAndMxId(zbid, mxid);
		if(i>0){
			doAddOplog(OplogFlag.DEL,"删除银行明细",zbid);
		}
	}
	
	/**
	 * 保存银行明细
	 * @param map
	 * @param zbid
	 * @param mxid
	 * @return
	 */
	@Transactional
	public int saveYhmx(Map map,String zbid,String mxid,int i){
		if(i==0){
			pzlrDao.deleteYhmxByZbidAndMxId(zbid, mxid);
		}
		int n = pzlrDao.saveYhmx(map, zbid, mxid);
		if(n>0){
			doAddOplog(OplogFlag.ADD,"增加银行明细",zbid);
		}
		return n;
	}
	
	public List insertJcsj(String file,String zbid,String mxid) {
		return pzlrDao.insertJcsj(file,zbid,mxid);
	}
	
	public Object expExcel(String realfile, String shortfileurl, String zbid,String mxid) {
		List<Map<String, Object>> dwList = this.pzlrDao.getYhmxIdByZBidAndMxIdByExcel(zbid, mxid);
		String Title = "银行信息";
		String[] title = new String[] { "姓名", "银行名称", "银行账号", "金额","支付状态"};
		Map<String, Object> dataMap = YhxxExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	public String docheck(String rylx, String gzyf, String pzlx, String kjzd, String sszt) {
		// TODO Auto-generated method stub
		String tablename = rylx.equals("0")?"cw_lzxzb":"cw_xzb";
		if(pzlrDao.docheck2(gzyf, tablename))
			return "{\"success\":false,\"msg\":\"不存在本月薪资信息！\"}";
		if(!this.pzlrDao.docheck(rylx, gzyf, pzlx, kjzd, sszt))
			return "{\"success\":false,\"msg\":\"本月薪资已生成凭证，不可重复生成！\"}";
		return null;
	}
	/**
	 *  增加 薪资 凭证
	 * @param rylx
	 * @param gzyf
	 * @param pzlx
	 * @param kjzd
	 * @param sszt
	 */
	public void autoCreatePzlrByXz(String rylx, String gzyf, String pzlx,String kjzd, String sszt) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();//存放数据
		Map<String, Object> kjqjMap = getWjzDate(sszt,pzlx);//期末日期
		
		String ztnd = kjqjMap.get("ztnd")+"";
		String kjqj = ""+kjqjMap.get("kjqj");
		
		int year = Integer.parseInt(ztnd);
		int month = Integer.parseInt(kjqj);
		Map<String, Object> pzlxMap = pzlrDao.getPzlxmc(pzlx);//凭证类型
		String pzz=pzlxMap.get("pzlxbh")+"";
		String pzlxmc=pzlxMap.get("pzlxmc")+"";
		
		map.put("pzrq", checkDate(year,month));
		map.put("ztnd", year);
		map.put("kjqj", month);
		map.put("pzlxbh", pzz);
		map.put("pzlxmc", pzlxmc);
		map.put("rylx", rylx);
		map.put("gzyf", gzyf);
		map.put("kjzd", kjzd);
		map.put("sszt", sszt);
//		if(rylx.equals("1")&&pzlx.equals(Constant.PZLX_ZZ)){
//			pzlrDao.autoInsertPzmxByXz1(map);
//		}else if(rylx.equals("1")&&pzlx.equals(Constant.PZLX_FZ)){
//			pzlrDao.autoInsertPzmxByXz2(map);
//		}else if(rylx.equals("0")&&pzlx.equals(Constant.PZLX_FZ)){
//			pzlrDao.autoInsertPzmxByXz3(map);
//		}
		//获取自动生成的凭证编号
		String autoPzbh;
		String sfbsc =  CheckSfbsc()  ;//是否补充删除凭证号  1是补充  0 是自动追加
		if("1".equals(rylx)){
			for(int i=0;i<2;i++){
				if(sfbsc.equals("1")){
					autoPzbh = GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzlx);
				}else{  //（规则为已存在的最大凭证编号加一）
					autoPzbh= getAutoPzbh(ztnd,kjqj, 4,sszt,pzlx);
				}
				map.put("pzbh", autoPzbh);
				String pzzbid = GenAutoKey.get32UUID();
				map.put("pzzbid", pzzbid);
				if(i==0){
					pzlrDao.autoInsertPzmxByXz1(map);//生成转
				}else{
					pzlrDao.autoInsertPzmxByXz2(map);//生成付
				}
				int n = pzlrDao.autoInsertPzzbByXz(map);
			    if(n>0){
					doAddOplog(OplogFlag.ADD,"增加在职人员薪资凭证",pzzbid);
				}
			}
		}else{
			if(sfbsc.equals("1")){
				autoPzbh = GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzlx);
			}else{  //（规则为已存在的最大凭证编号加一）
				autoPzbh= getAutoPzbh(ztnd,kjqj, 4,sszt,pzlx);
			}
			map.put("pzbh", autoPzbh);
			String pzzbid = GenAutoKey.get32UUID();
			map.put("pzzbid", pzzbid);
			pzlrDao.autoInsertPzmxByXz3(map);
			int n = pzlrDao.autoInsertPzzbByXz(map);
			if(n>0){
				doAddOplog(OplogFlag.ADD,"增加离职人员薪资凭证",pzzbid);
			}
		}
	}
	public boolean doUpdateOfPrint(String guid) {
		// TODO Auto-generated method stub
		return pzlrDao.doUpdateOfPrint(guid);
	}
	public boolean dosave(Xtxx obj) {
		// TODO Auto-generated method stub
		return pzlrDao.dosave(obj);
	}
	public boolean docheck(String pzbh, String pzlx, String kjzd, String sszt) {
		// TODO Auto-generated method stub
		return pzlrDao.docheck( pzbh,  pzlx,  kjzd,  sszt);
	}
	public String CheckSfbsc() {
		return pzlrDao.CheckSfbsc();
	}
	/** 
	 * 获取  CW_PZLRBXDZB中的bxid  type
	 * @param guid
	 * @return
	 */
	public Map getBxpzxx(String guid) {
		return pzlrDao.getBxpzxx(guid);
	}
	/**
	 * 修改差旅费报销凭证 状态
	 * @param bxid
	 */
	public int updateCLbxpz(String bxid) {
		return pzlrDao.updateCLbxpz(bxid);		
	}
	/**
	 * 修改 公务接待 报销凭证 状态
	 * @param bxid
	 */
	public int updateJDbxpz(String bxid) {
		return pzlrDao.updateJDbxpz(bxid);
	}
	/**
	 * 修改  日常 报销凭证 状态
	 * @param bxid
	 */
	public int updateRCbxpz(String bxid) {
		return pzlrDao.updateRCbxpz(bxid);		
	}
	/**
	 * 修改 借款 报销凭证 状态
	 * @param bxid
	 */
	public int updateJKbxpz(String bxid) {
		return pzlrDao.updateJKbxpz(bxid);		
	}
	/**
	 * 修改 收入 报销凭证 状态
	 * @param bxid
	 */
	public int updateSRbxpz(String bxid) {
		return pzlrDao.updateSRbxpz(bxid);		
	}
	/**
	 *  获取当前分录 的银行 明细 的  总金额
	 */
	public String getSumMxje(String mxid) {
		return pzlrDao.getSumMxje(mxid);
	}
	/**
	 * 获取 这个科目  的 余额方向
	 * @return
	 */
	public String getKmYefx(String kmbh) {
		return pzlrDao.getKmYefx(kmbh);
	}
	public String insertGlf(String file) {
		return pzlrDao.insertGlf(file);
	}
	/**
	 * 管理费新增
	 * @author BiMing
	 * @Time 2018年10月12日下午5:27:13
	 */
	public int insertGlf(List list) {
		return pzlrDao.insertGlf(list);
	}
	/**
	 * 删除管理费 费
	 * @author BiMing
	 * @Time 2018年10月12日下午5:27:13
	 */
	public int DeleteKyglxm(String guids) {
		return pzlrDao.DeleteKyglxm(guids);
	}
	/**
	 * 导出管理费信息
	 * @author BiMing
	 * @Time 2018年10月12日下午5:14:56
	 */
	public Object expKyglxm(String realfile, String shortfileurl, String sql) {
		List<Map<String, Object>> dcglfList = pzlrDao.getListForKyglxm(sql);
		String Title = "科研管理费信息";
		String[] title = new String[] { "序号", "项目名称","项目类型","项目负责人","所属部门" ,"学校科研基金（元）", "学院科研基金（元）"};
		Map<String, Object> dataMap = exportExcelForGlf(realfile,shortfileurl, title, Title,dcglfList );
		return dataMap;
	}
	/**
	 * 导出管理费信息
	 */
	public static Map<String, Object> exportExcelForGlf(String fileurl,String shortfileurl,String[] title,String TITLE, List<Map<String, Object>> listContent) {  
		String result="系统提示：Excel文件导出成功！"; 
		boolean flag=true;
		//以下开始输出到EXCEL  
		try {      
		   /** **********创建工作簿************ */  
			File file = new File(fileurl.substring(0, fileurl.lastIndexOf(".")));
			if(!file.exists()){
				file.mkdirs();
			}
			WritableWorkbook workbook = Workbook.createWorkbook(new File(fileurl));
		   /** **********创建工作表************ */  
		   WritableSheet sheet = workbook.createSheet("科研管理费信息",0);
		   sheet.getSettings().setVerticalFreeze(2);
		   for(int i=1;i<title.length;i++){
			   //sheet.setColumnView(i,30); 
			   if(i==4){
				   sheet.setColumnView(i,80);   //摘要列
			   }else{
				   sheet.setColumnView(i,30); 
			   }
		   }
		   /** ************设置单元格字体************** */  
		   WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);  
		   WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 12,WritableFont.BOLD);  
		   WritableFont font = new WritableFont(WritableFont.createFont("楷体"),15, WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE); 
		   /** ************以下设置三种单元格样式，灵活备用************ */  
		   //表头
		   WritableCellFormat title_style = new WritableCellFormat(font);  
		   title_style.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
		   title_style.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
		   title_style.setAlignment(Alignment.CENTRE); // 文字水平对齐  
		   title_style.setWrap(false); // 文字是否换行  
		   // 用于标题居中  
		   WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);  
		   wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
		   wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
		   wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐  
		   wcf_center.setWrap(false); // 文字是否换行  
		   wcf_center.setBackground(Colour.GRAY_25);
		   // 用于正文居左  
		   WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);  
		   wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条  
		   wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
		   wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐  
		   wcf_left.setWrap(false); // 文字是否换行    
		   /*****************以下是EXCEL开头大标题*********************/  
		   sheet.mergeCells(0, 0, title.length-1, 0); 
		   sheet.setRowView(0, 650, false); //设置行高
		   sheet.setRowView(1, 400, false); 
		   sheet.addCell(new Label(0, 0, TITLE, title_style));  
		   /** ***************以下是EXCEL第一行列标题********************* */  
		   for (int i = 0; i < title.length; i++) {  
			   sheet.addCell(new Label(i, 1,title[i],wcf_center));  
		   } 
		   /*****************以下是EXCEL正文数据**********************/ 
		   /**********学生数据加载*************/
		   int i = 2;
		   for (Map<String, Object> drow : listContent) {
			    sheet.setRowView(i, 300, false); 
				sheet.addCell(new Label(0, i, Validate.isNullToDefaultString(i-1, ""), wcf_left));
				sheet.addCell(new Label(1, i, Validate.isNullToDefaultString(drow.get("XMMC"), ""), wcf_left));
				sheet.addCell(new Label(2, i, Validate.isNullToDefaultString(drow.get("XMLX"), ""), wcf_left));
				sheet.addCell(new Label(3, i, Validate.isNullToDefaultString(drow.get("XM"), ""), wcf_left));
				sheet.addCell(new Label(4, i, Validate.isNullToDefaultString(drow.get("MC"), ""), wcf_left));
				sheet.addCell(new Label(5, i, Validate.isNullToDefaultString(drow.get("XXJJ"), ""), wcf_left));
				sheet.addCell(new Label(6, i, Validate.isNullToDefaultString(drow.get("XYJJ"), ""), wcf_left));
				i++;
		    }
		   /************将以上缓存中的内容写到EXCEL文件中*********/  
		   workbook.write();  
		   /***********关闭文件**************/  
		   workbook.close();     
		   } catch (Exception e) {  
			   result="系统提示：Excel文件导出失败，原因："+ e.toString(); 
			   flag=false;
			   e.printStackTrace();  
		   }  
		   Map<String, Object> map=new HashMap<String, Object>();
		   map.put("flag", flag);
		   map.put("msg", result);
		   map.put("url", shortfileurl);
		   return map;  
		}
	/**
	 * 检查 是否 =已 增加  了项目
	 * @author BiMing
	 * @Time 2018年10月12日下午5:14:56
	 */
	public int CheckKyglxm(String xmguids) {
		return pzlrDao.CheckKyglxm(xmguids);
	}
	
	
	
	
	
}
