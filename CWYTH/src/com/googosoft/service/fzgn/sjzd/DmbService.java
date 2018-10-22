package com.googosoft.service.fzgn.sjzd;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.sjzd.DmbDao;
import com.googosoft.pojo.fzgn.sjzd.GX_SYS_DMB;
import com.googosoft.service.base.BaseService;

/**
 * 数据字典Service
 * @author Administrator
 *
 */
@Service("dmbService")
public class DmbService extends BaseService{
	
	@Resource(name="dmbDao")
	private DmbDao dmbDao;

	/**
	 * 数据字典增加操作（保存）
	 * @param dmb
	 * @param operateType
	 * @return
	 */
	public int doAdd(GX_SYS_DMB dmb, String operateType) {
		boolean flag = dmbDao.doCheckDm(dmb, operateType);//保存时验证代码是否重复（增加和修改都要验证）
		if(flag){
			int i = dmbDao.doAdd(dmb);
			if(i>0){
				doAddOplog(OplogFlag.ADD,"数据字典信息：增加",dmb.getDm());
			}
			return i;
		}else{
			return 0;
		}
	}
	/**
	 * 数据字典修改操作
	 * @param dmb
	 * @param operateType
	 * @return
	 */
	public int doUpdate(GX_SYS_DMB dmb, String operateType) {
		boolean flag = dmbDao.doCheckDm(dmb, operateType);//保存时验证代码是否重复（增加和修改都要验证）
		if(flag){
			int i = dmbDao.doUpdate(dmb);
			if(i>0){
				doAddOplog(OplogFlag.UPD,"数据字典信息：修改",dmb.getDm());
			}
			return i;
		}else{
			return 0;
		}
	}
	/**
	 * 根据主键数据字典获取列表详细信息 操作(修改)
	 * @param dm
	 * @return
	 */
	public Map getObjectById(String dmxh) {
		return dmbDao.getObjectById(dmxh);
	}
	
	/**
	 * 根据主键数据字典删除 操作(删除)
	 * @param dmxh
	 * @return
	 */
	public int doDelete(String dmxh) {
		int result = dmbDao.doDelete(dmxh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"数据字典信息","");
		}
			return result;
	}
	/**
	 * 检查代码名称是否重复
	 */
	public int doCheck(GX_SYS_DMB dmb,String mc) {
		int i=0;	
		i=dmbDao.checkMc(dmb,mc);
		return i;
	}
}

