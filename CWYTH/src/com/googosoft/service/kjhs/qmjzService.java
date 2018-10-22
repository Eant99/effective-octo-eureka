package com.googosoft.service.kjhs;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.qmjzDao;
import com.googosoft.pojo.kjhs.CW_QMJZB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWSZ;
import com.googosoft.service.base.BaseService;

@Service("qmjzService")
public class qmjzService extends BaseService{
	@Resource(name="qmjzDao")
	private qmjzDao qmjzDao;
	
	/**
	 *期末结账本月结账操作
	 * @param 
	 * @param
	 * @return
	 */
	@Transactional
	public int doEdit(CW_QMJZB qmjzb,String jzyf,String jzcs,String ztnd,String kjzd,String sszt) {
	
		int nd = Integer.parseInt(ztnd)+1;//上一年+1
		    

			 qmjzDao.doEdit(qmjzb);
			 if("12".equals(jzyf) && "0".equals(jzcs)) {
				 qmjzDao.insertKmye(nd,ztnd,kjzd);
				 qmjzDao.insertKmyemx(nd,sszt,ztnd,kjzd);
				 qmjzDao.updateKmye(nd,sszt);//修改第一行复制过去的科目总余额
				 qmjzDao.insertXmncyeb(nd);
			 }else if("12".equals(jzyf) && !"0".equals(jzcs)){
				 //如果反结装张之后，删除明细表，重新添加，
				 qmjzDao.deleteKmyemx(nd,sszt);
				 qmjzDao.insertKmyemx(nd,sszt,ztnd,kjzd);
				 qmjzDao.updateKmye(nd,sszt);
				 qmjzDao.insertXmncyeb(nd);
			 }
			
			return 1;
		
	}
	/**
	 * 期末结账增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd(CW_QMJZB qmjzb) {
		
			int i = qmjzDao.doAdd(qmjzb);
			
			return i;
		
	}
	
	public List getSsxt() {
		return qmjzDao.getSsxt();
	}
	
	
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public  List<Map<String, Object>>  getObjectById(String kjqj) {
		return qmjzDao.getObjectById(kjqj);
	}
	
	/**
	 * 验证凭证编号是否断号
	 * @param kjqj
	 * @param ztnd
	 * @return
	 */
	public boolean checkPzbh(String kjqj,String ztnd){
		return qmjzDao.checkPzbh(kjqj, ztnd);
	}
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getObjectByIdJz(String kjqj,String ztnd) {
		return qmjzDao.getObjectByIdJz(kjqj,ztnd);
	}
	/**
	 *反结账
	 * @param 
	 * @param
	 * @return
	 */
	public int doEditFjz(CW_QMJZB qmjzb) {
			int i = qmjzDao.doEditFjz(qmjzb);
			//根据guid获取tznd,kjqj
			Map<String, Object> qmjzMap=qmjzDao.getQmjzxxByGud(qmjzb.getGuid());
			qmjzDao.deleteXmNcyeb(qmjzMap);
			return i;
	}
	/**
	 * 获取期末结账信息
	 */
	public Map<String, Object> getQmjzxxByGud(String guid) {
		return qmjzDao.getQmjzxxByGud(guid);
	}
}
