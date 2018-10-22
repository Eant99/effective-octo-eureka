package com.googosoft.service.yskjbb;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.yskjbb.YssrzcbDao;

@Service("SryszcbService")
public class SryszcbService {
	@Resource(name="YssrzcbDao")
	private YssrzcbDao YssrzcbDao;
	public List getZcList(){
		return YssrzcbDao.getZcList();
	}
	public List getYsList(){
		return YssrzcbDao.getYsList();
	}
	public List getCzList(){
		return YssrzcbDao.getCzList();
	}
}
