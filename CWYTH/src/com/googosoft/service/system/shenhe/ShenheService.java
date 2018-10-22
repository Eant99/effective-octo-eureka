package com.googosoft.service.system.shenhe;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.system.shenhe.ShenheDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Service("shenheService")
public class ShenheService extends BaseService{
	@Resource(name="shenheDao")
	private ShenheDao shenheDao;
	/**公共提交方法*/
	@Transactional
	public String doSubmit(PageData pd){
		if(shenheDao.doSubmit(pd)){
			String keyid = pd.getString("keyid");
			String[] idarr = keyid.split(",");
			for(int i = 0; i < idarr.length; i++){
				doAddOplog(OplogFlag.SUBMIT, "信息提交", idarr[i]);
			}
		}
		return pd.getString("msg");
	}
	/** 公共撤销方法*/
	@Transactional
	public String doRevoke(PageData pd){
		if(shenheDao.doRevoke(pd)){
			String keyid = pd.getString("keyid");
			String[] idarr = keyid.split(",");
			for(int i = 0; i < idarr.length; i++){
				doAddOplog(OplogFlag.BACK, "信息撤销", idarr[i]);
			}
		}
		return pd.getString("msg");
	}
	/** 公共审核通过方法*/
	@Transactional
	public String doCheck(PageData pd){
		if(shenheDao.doCheck(pd)){
			String keyid = "["+pd.getString("keyid")+"]";
			JSONArray jsar = new JSONArray(keyid);
			for(int i = 0; i < jsar.length(); i++){
				doAddOplog(OplogFlag.CHECK, "审核通过", jsar.getJSONObject(i).get("dbh").toString());
			}
		}
		return pd.getString("msg");
	}
	/** 公共审核退回方法*/
	@Transactional
	public String doBack(PageData pd){
		if(shenheDao.doBack(pd)){
			String keyid = "["+pd.getString("keyid")+"]";
			JSONArray jsar = new JSONArray(keyid);
			for(int i = 0; i < jsar.length(); i++){
				doAddOplog(OplogFlag.RETREAT, "信息退回", jsar.getJSONObject(i).get("dbh").toString());
			}
		}
		return pd.getString("msg");
	}
	/** 公共保存方法*/
	@Transactional
	public boolean doSave(PageData pd){
		return shenheDao.doSave(pd);
	}
	/** 批量赋值 */
	@Transactional
	public int doPlfz(String ids,String ziduan,Object zdValue){
		int result=shenheDao.doPlfz(ids, ziduan, zdValue);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"验收单基础信息",ids.split(","));
		}
		return result;
	}
	/** 批量赋值 */
	@Transactional
	public int doPlfz1(String ids,String ziduan,Object zdValue){
		int result=shenheDao.doPlfz1(ids, ziduan, zdValue);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"验收单基础信息",ids.split(","));
		}
		return result;
	}
	/** 批量赋值*/
	@Transactional
	public int doPlfz2(String ids,String ziduan,Object zdValue){
		int result=shenheDao.doPlfz2(ids, ziduan, zdValue);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"验收单基础信息",ids.split(","));
		}
		return result;
	}
	/** 获取部分报废明细表数据 */
	@Transactional
	public String getBfbfMx(String bdbgbh) {
		StringBuffer xx = new StringBuffer();
		List<Map> list = shenheDao.getBfbfMx(bdbgbh);
		if(list.size()>0){
			for(Map map : list){
				String bdxm = Validate.isNullToDefaultString(map.get("bdxm"), "");
				xx.append("{\"bdbh\":\""+map.get("bdbh")+"\",");
				xx.append("\"bdbgbh\":\""+map.get("bdbgbh")+"\",");
				xx.append("\"yqbh\":\""+Validate.isNullToDefault(map.get("yqbh"), "")+"\",");
				xx.append("\"yqmc\":\""+Validate.isNullToDefault(map.get("yqmc"), "")+"\",");
				xx.append("\"bdxm\":\""+bdxm+"\",");
				if("件数".equals(bdxm)){
					xx.append("\"bdqnr\":\""+Validate.isNullToDefault(map.get("bdqnr"), "")+"\",");
					xx.append("\"bdhnr\":\""+Validate.isNullToDefault(map.get("bdhnr"), "")+"\"");
				}else{
					xx.append("\"bdqnr\":\""+Validate.isNullToDefault(map.get("bdqnrs"), "")+"\",");
					xx.append("\"bdhnr\":\""+Validate.isNullToDefault(map.get("bdhnrs"), "")+"\"");
				}
				xx.append("},");
			}
			xx.deleteCharAt(xx.length()-1);
		}
		return "["+xx+"]";
	}
	/** 验证审核必填项是否已填（列表和详细信息都会用到）*/
	public Object checkSave(PageData pd){
		List list = shenheDao.checkSave(pd);
		String[] zdarr = pd.getString("zdm").split(",");
		Map map;
		for(int i = 0; i < list.size(); i++){
			map = (Map)list.get(i);
			for(int j = 0; j < zdarr.length; j++){
				if(Validate.isNull(map.get(zdarr[j].toUpperCase()))){
					return "{\"success\":false,\"msg\":\"请先完善审核信息！\"}";
				}
			}
		}
		return "{\"success\":true,\"msg\":\"审核验证通过！\"}";
	}
	/** 管理员建账审核，判断获取所选择的验收单号中，分类号是否是末级分类*/
	public boolean checkEndFlh(PageData pageData) {
		return shenheDao.checkEndFlh(pageData);
	}
}