package com.googosoft.service.ysgl.jcsz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;

import com.googosoft.dao.ysgl.jcsz.JjkmybmdyszDao;
import com.googosoft.pojo.ysgl.jjkmybmdysz;
import com.googosoft.service.base.BaseService;
import com.lowagie.text.List;
/**
 * 经济科目与部门对应设置Service
 * @author Administrator
 *
 */
@Service("jjkmybmdyszService")
public class JjkmybmdyszService extends BaseService{
	@Resource(name="jjkmybmdyszDao")
	private JjkmybmdyszDao jjkmybmdyszDao;

	/**
	 * 根据主键经济科目与部门对应设置删除操作
	 * @param dmxh
	 * @return
	 */
	public int doDelete(String guid) {
		int result = jjkmybmdyszDao.doDelete(guid);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"经济科目与部门对应设置信息",guid);
		}
			return result;
	}
	/**
	 * 收入经济科目与部门对应设置增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd1(jjkmybmdysz bmxx) {
		
		int i = jjkmybmdyszDao.doAdd1(bmxx);
		
		return i;
	
}
	/**
	 * 获取经济科目与部门编号对应设置实体类
	 * @param 
	 * @return
	 */
	public List getObjectById1(String guid) {
		return jjkmybmdyszDao.getObjectById1(guid);
	}





}
