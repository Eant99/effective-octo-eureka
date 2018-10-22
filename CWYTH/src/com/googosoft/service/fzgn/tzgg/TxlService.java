package com.googosoft.service.fzgn.tzgg;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.DdbDao;
import com.googosoft.dao.fzgn.tzgg.TxlDao;
import com.googosoft.pojo.fzgn.jcsz.ZC_SYS_DDB;
import com.googosoft.pojo.fzgn.tzgg.ZC_TXL;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

/**
 * 通讯录service
 * 
 */
@Service("txlService")
public class TxlService extends BaseService{

	@Resource(name="txlDao")
	public TxlDao Dao;

	/**
	 * 增加
	 * 
	 */
	public int doAdd(ZC_TXL txl){
			txl.setGid(UuidUtil.get32UUID());
			txl.setXm(WindowUtil.getEndText(txl.getRybh()));
			txl.setRybh(WindowUtil.getText(txl.getRybh()));
			int result = Dao.doAdd(txl);
			if(result>0){
				doAddOplog(OplogFlag.ADD,"通讯录信息",txl.getGid());
			}
			return result;
		
	}
	
	/**
	 * 修改通讯录信息
	 * @param ddb
	 * @return
	 */
	public int doUpdate(ZC_TXL txl) {
		txl.setXm(WindowUtil.getEndText(txl.getRybh()));
		txl.setRybh(WindowUtil.ryghTorybh(WindowUtil.getText(txl.getRybh())));
		int result = Dao.doUpdate(txl);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"通讯录信息",txl.getGid());
		}
		return result;
		
	}
	
	/**
	 * 删除通讯录信息
	 * @param gid
	 * @return
	 */
	public int doDelete(String gid) {
			int result=Dao.doDelete(gid);
			if(result>0){
				doAddOplog(OplogFlag.DEL,"通讯录信息",gid);
			}
			return result;
	}
	
	/**
	 * 获取单条通讯录信息
	 * @param ddbh
	 * @return
	 */
	public Map getObjectById(String id) {
		return Dao.getObjectById(id);
	}
	

	public List insertJcsj(String file) {
		return Dao.insertJcsj(file);
	}
	
}