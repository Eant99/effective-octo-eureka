package com.googosoft.service.fzgn.jcsz;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.DdbDao;
import com.googosoft.pojo.fzgn.jcsz.ZC_SYS_DDB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

/**
 * 单位信息service
 * @author JiaTong
 * 10/28整理
 */
@Service("ddbService")
public class DdbService extends BaseService{

	@Resource(name="ddbDao")
	public DdbDao Dao;

	/**
	 * 通过obj获得下级节点的数量
	 * @return
	 */
	public String getNewStatus(String sjdd) {
		return Dao.getNewStatus(sjdd);
	}
	
	/**
	 * 增加，新增了主键ddbh自动生成，在goEditPage方法里面加了
	 * 
	 */
	public int doAdd(ZC_SYS_DDB ddb){
		
		ddb.setDdbh(ddb.getDdh());//在保存时，地点编号取地点号
		boolean flag =Dao.doCheckDdbh(ddb);
		if(flag){
			ddb.setDwbh(WindowUtil.getXx(ddb.getDwbh(), "D"));
			ddb.setSjdd(WindowUtil.getXx(ddb.getSjdd(), "P"));
			ddb.setDdjc(Dao.findSjddjc(ddb.getSjdd()));//注意findSjddjc是已经+1的
			int result = Dao.doAdd(ddb);
			if(result>0){
				doAddOplog(OplogFlag.ADD,"地点基础信息",ddb.getDdbh());
			}
			return result;
		}else {
			return 0;
		}
	}

	/**
	 * ddbh自动生成
	 */
	public String getDdbh() {
		return AutoKey.makeCkbh("ZC_SYS_DDB", "ddbh", "6");
	}
	
	/**
	 * 修改地点信息
	 * @param ddb
	 * @return
	 */
	public int doUpdate(ZC_SYS_DDB ddb) {
		boolean flag = Dao.doCheckDdh(ddb);
		if(flag){
		ddb.setDwbh(WindowUtil.getXx(ddb.getDwbh(), "D"));
		if(ddb.getSjdd().indexOf("无")==0){
			ddb.setSjdd("000000");
		}else{
			ddb.setSjdd(WindowUtil.getXx(ddb.getSjdd(), "P"));			
		}
		//地点级次根据上级地点确定，注意findSjddjc这个方法已经+1了，无需再次+1
		if(ddb.getSjdd() != null){
			ddb.setDdjc(Dao.findSjddjc(ddb.getSjdd()));	
		}
		int result = Dao.doUpdate(ddb);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"地点基础信息",ddb.getDdbh());
		}
		return result;
		}else{
			return 0;
		}
	}
	
	/**
	 * 删除地点信息
	 * @param ddbh
	 * @return
	 */
	public int doDelete(String ddbh) {
		boolean flag = Dao.doCheckDdbh(ddbh);
		if(flag){
			int result=Dao.doDelete(ddbh);
			if(result>0){
			doAddOplog(OplogFlag.DEL,"地点基础信息",ddbh);
			}
			return result;
		}else{
			return 0;
		}
	}
	
	/**
	 * 获取单条地点信息
	 * @param ddbh
	 * @return
	 */
	public Map getObjectById(String ddbh) {
		return Dao.getObjectById(ddbh);
	}
	
	/**
	 *  批量赋值
	*/
	public int doPlFuzhi(String ddbh,String ziduan,String zdValue) {
		return Dao.doPlFuzhi(ddbh, ziduan, zdValue);
	}
	
	/**
	 * 存放地点设置
	 * 通过地点号(地点名称)查询地点编号
	 * @param ddmc (ddh)mc格式
	 * @return
	 */
	public String findDdbhByDdmc(String ddmc) {
		String dwbh = "";
		if(Validate.noNull(ddmc)){
			dwbh = Dao.findDdbhByDdmc(ddmc);
		}
		return dwbh ;
	}
	
	/**
	 * 存放地点设置
	 * 通过地点号(地点名称)查询地点编号
	 * @param ddmc (ddh)mc格式
	 * @return
	 */
	public String findDdbh(String ddmc) {
		return Dao.findDdbh(ddmc);
	}
}