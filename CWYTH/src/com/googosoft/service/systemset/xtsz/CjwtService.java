package com.googosoft.service.systemset.xtsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.systemset.xtsz.CjwtDao;
import com.googosoft.pojo.systemset.xtsz.ZC_CJWT;

@Service("cjwtService")
public class CjwtService {
	
	@Resource(name="cjwtdao")
	private CjwtDao cjwtdao;
	
	/**
	 * 保存
	 * @param bzxx
	 * @param content
	 * @return
	 */
	public boolean doSave(ZC_CJWT cjwt) {
		return cjwtdao.doSave(cjwt);
	}
	
	public Map getObjectById(String id) {
		return cjwtdao.getObjectById(id);
	}

	public boolean doDelete(String id) {
		// TODO Auto-generated method stub
		return cjwtdao.doDelete(id);
	}
	
	public List getList(){
		return cjwtdao.getList();
	}
	
}
