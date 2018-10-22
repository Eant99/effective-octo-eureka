package com.googosoft.service.fzgn.wxzf;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.wxzf.CbsglDao;
import com.googosoft.pojo.fzgn.wxzf.CW_CBSXX;
import com.googosoft.service.base.BaseService;

/**
 * 承包商信息维护service
 * @author googosoft
 *
 */
@Service("cbsglService")
public class CbsglService extends BaseService{
	@Resource(name="cbsglDao")
	private CbsglDao dao;
	
	public Map<String, Object> getObjectById(String guid) {
		return dao.getObjectById(guid);
	}
	
	/**
	 * 判断承包商信编号是否重复
	 * @param cbsbh
	 * @return
	 */
	public boolean checkCbsbh( String cbsbh) {
		return dao.checkCbsbh(cbsbh);
	}
	
	/**
	 * 保存承包商信息
	 * @param cbsxx
	 * @return
	 */
	public int doAdd(CW_CBSXX cbsxx){
		return dao.doAdd(cbsxx);
	}
	
	/**
	 * 删除承包商信息
	 * @param guid
	 * @return
	 */
	public int doDel(String guid) {
		int result = dao.doDel(guid);
		if (result > 0) {
			doAddOplog(OplogFlag.DEL, "承办商信息：删除", guid);
		}
		return result;
	}
	
	/**
	 * 修改承包商信息
	 * @param cbsxx
	 * @return
	 */
	public int doUpp(CW_CBSXX cbsxx){
		return dao.doUpp(cbsxx);
	}
}
