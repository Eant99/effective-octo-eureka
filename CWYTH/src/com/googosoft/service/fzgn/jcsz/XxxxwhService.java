package com.googosoft.service.fzgn.jcsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.XxxxwhDao;
import com.googosoft.pojo.fzgn.clbx.GX_CLBX;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_XXXXWH;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;

/**
 * 学校信息委会service
 * @author googosoft
 *
 */
@Service("xxxxwhService")
public class XxxxwhService extends BaseService {
	
	@Resource(name="xxxxwhDao")
	private XxxxwhDao xxxxwhDao;
	
	/**
	 * 新增
	 * @return
	 */
	public int doAdd(GX_SYS_XXXXWH xxxxwh)
	{
		xxxxwh.setGuid(UuidUtil.get32UUID());
	    int count = xxxxwhDao.doAdd(xxxxwh);
		if(count>0)
		{
			doAddOplog(OplogFlag.ADD,"学校信息维护",xxxxwh.getCzr());
		}
	    return count;
	}
	
	/**
	 * 学校详情信息
	 * @param guid
	 * @return
	 */
	public Map<?, ?> getObjectById(String guid) {
		return xxxxwhDao.getObjectById(guid);
	}
	/**
	 * 修改
	 * @param xxxxb
	 * @return
	 */
	public int doUpdate(GX_SYS_XXXXWH xxxxwh) {
			
			int result = xxxxwhDao.doUpdate(xxxxwh);
			//if(result>0){
			//	doAddOplog(OplogFlag.UPD,"学校基本信息",xxxxwh.getGuid());
			//}
			return result;
	}
	
	/**
	 * 差旅信息报销标准保存
	 * @param clbx
	 * @param zsbz1
	 * @param zsbz2
	 * @param wj1
	 * @param wj2
	 * @param wj3
	 * @param sfbl1
	 * @param bz
	 * @param jtbz
	 * @param city
	 * @return
	 */
	@Transactional
	public int doclbxSave(GX_CLBX clbx,String[] zsbz1,String[] zsbz2,String[] wj1,String[] wj2,String[] wj3,String[] sfbl1,String[] bz,String[] jtbz,String[] city) {
		int result = xxxxwhDao.doclbxSave(clbx,zsbz1,zsbz2,wj1,wj2,wj3,sfbl1,bz,jtbz,city);
		return result;
	}
	
	/**
	 * 差旅信息报销标准列表
	 * @return
	 */
	public List getClbxlist() {
		return xxxxwhDao.getClbxlist();
	}
}
