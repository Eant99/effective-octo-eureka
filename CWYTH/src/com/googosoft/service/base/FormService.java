package com.googosoft.service.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.base.FormDao;
import com.googosoft.pojo.Form;


@Service
public class FormService {
	@Autowired
	FormDao formDao;
	public List<Form> findForm(){
		return formDao.findForm();
	}
	@Transactional
	public boolean saveFormInfo(Form form) {
		return formDao.saveFormInfo(form);
	}
	public Map<String, String> process(Form model, Map<String, Object> datas){
		
		return formDao.process(model, datas);
	}
	public Form findFormInfoById(String id) {
		return formDao.findFormInfoById(id);
	}
	public boolean updateFormInfo(Form form) throws SerialException, SQLException {
		return formDao.updateFormInfo(form);
	}
	public boolean deleteFormInfo(String id) {
		return formDao.deleteFormInfo(id);
	}
	public boolean updateFormByid(Form form) {
		return formDao.updateFormByid(form);
	}
	/**
	 * 绑定流程根据名称查询表单
	 * @param name
	 * @return
	 */
	public String findFormByName(String name){
		
		return formDao.findFormContent(name);
	}
	
	
}
