package com.googosoft.service.base;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.googosoft.dao.base.ProvincesDao;
@Service("provincesService")
public class ProvincesService  extends BaseService{
	@Resource(name="provincesDao")
	public ProvincesDao provincesDao;
	
	public List getProvicesList(){
		return provincesDao.getProvicesList();
	}
	
	public List getCitiesByProvince(String provinceid){
		return provincesDao.getCitiesByProvince(provinceid);
	}
}
