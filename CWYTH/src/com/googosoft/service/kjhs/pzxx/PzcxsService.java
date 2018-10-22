package com.googosoft.service.kjhs.pzxx;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.pzxx.PzcxsDao;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.FileUtil;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Service("pzcxsService")
public class PzcxsService extends BaseService {
	@Resource(name="pzcxsDao")
	public PzcxsDao pzcxsDao;
	
	//获取凭证字列表
			public List<Map<String, Object>> getPzzList(String sszt){
				return pzcxsDao.getPzzList(sszt);
			}
			//获取凭证编号列表
			public List<String> getPzbhList(String pzz,String sszt){
				return pzcxsDao.getPzbhList(pzz,sszt);
			}
			
			//账套信息
			public Map<String, Object> getZtxx(String ztid){
				return pzcxsDao.getZtXx(ztid);
			}
			//查询复核人
			public Object getFhr(String pzbh,String pzz,String sszt){
				return pzcxsDao.getFhr(pzbh,pzz,sszt);
			}
			//查询凭证必有
			public Object getBybwkm(String pzz,String sszt) {
				return pzcxsDao.getBybwkm(pzz,sszt);
			}
			
			//获取自动的凭证编号
			public String getAutoPzbh(String pzz,int length,String sszt) {
				String max_pzbh = Validate.isNullToDefaultString(pzcxsDao.getMaxPzbh(pzz,sszt), "0");
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
			public String pzbhwfh(String guid){
				return pzcxsDao.pzbhwfh(guid);
			}
			//主表信息
			public Map<String, Object> getPzlrZbInfo(String pzz,String pzbh,String sszt){
				return pzcxsDao.selectPzlrzb(pzz,pzbh,sszt);
			}
			//凭证录入辅助和明细信息
			public List<Map<String, Object>> getPzlrMxFzInfo(String pzz,String pzbh,String sszt){
				return pzcxsDao.selectPzlrmxAndFzlr(pzz, pzbh,sszt);
			}
			/**
			 * 获取凭证类型名称
			 * @return
			 */
			public List<Map<String, Object>> getPzlxmc() {
				return pzcxsDao.getPzlxmc();
			}

	//查询凭证明细list
	public List<Map<String,Object>> getpzmxlist(String pzbh){
		return pzcxsDao.getpzmxlist(pzbh);
	}
	
	public int updatesomef(String guid) {
		return pzcxsDao.updatesomef(guid);
	}
	
	public int updatesomefk(String guid) {
		return pzcxsDao.updatesomefk(guid);
	}
	
	public int fanhuisj(String guid) {
		return pzcxsDao.fanhuisj(guid);
	}
	
	//凭证支付
	public int zfztxgupdate(String guid,String je,String kmbh,String xmbh,String bmbh,String restime,String vchid) {
		return pzcxsDao.zfztxgupdate(guid,je,kmbh,xmbh,bmbh,restime,vchid);
	}
	//批量支付 项目不为空 减钱
	public int plzfjq(String guid,String je,String kmbh,String xmbh,String bmbh,String restime,String vchid) {
		return pzcxsDao.plzfjq(guid,je,kmbh,xmbh,bmbh,restime,vchid);
	}

	/**
	 * 银行支付接口的xml内容
	 * @param guid
	 * @return
	 */
	public String plzfjk(String guid) {
		return pzcxsDao.plzfjk(guid,"","","");
	}
	
	/**
	 *  * 银行对账信息
	 * @param zfsj
	 * @param je
	 * @param khxlh
	 */
//	public List<Map<String, Object>> getPzguid(String zfsj, String je, String khxlh) {
//		return pzcxsDao.getPzguid(zfsj,je,khxlh);
//	}
	
	/**
	 * 生成  生成 支付信息txt  
	 */
	public String ExpZfxxTxt(String sql,String file,List<M_largedata> mlist,String flag,String endStr,HttpServletRequest request,String bankCode,String guid){
		PrintWriter writer = null;
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			
			String hmsj = simpleDateFormat.format(new Date());
			FileUtil.createDir(file);
			String filename = hmsj+ ".txt";
			String fileurl = file + filename;
			List<String> errlist = new ArrayList<String>();
			Map zfxxmap = pzcxsDao.ExpZfxxTxt(sql, fileurl, mlist, errlist, flag, endStr, request,bankCode,guid);
			String status = (String) zfxxmap.get("status");
			String vchid = (String) zfxxmap.get("vchid");
			String restime = (String) zfxxmap.get("restime");
			if(status.equals("COMPLETE")){
				return "{\"success\":true,\"status\":\""+status+"\",\"restime\":\""+restime+"\",\"vchid\":\""+vchid+"\",\"url\":\"" + filename + "\"}";
			}
//			if(zfxxmap.size()>0){
//				return "{\"success\":true,\"status\":\""+status+"\",\"restime\":\""+restime+"\",\"vchid\":\""+vchid+"\",\"url\":\"" + filename + "\"}";
//			}
			else{
				if(errlist.size() == 0){
					return "{\"success\":false,\"msg\":\"未知的原因导致生成txt时失败！\"}";
				}
				else{
					filename = UuidUtil.get32UUID() + ".txt";
					String errorurl = file + filename;
					writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(errorurl),"GBK"));
					for(int i = 0; i < errlist.size(); i++){
						writer.println(errlist.get(i));
					}
					return "{\"success\":false,\"url\":\"" + filename + "\"}";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "{\"success\":false,\"msg\":\"生成txt文件方法 或 银行连接 执行失败！\"}";
		}
		finally{
			if(writer != null){
				writer.close();
			}
		}
	}

}
