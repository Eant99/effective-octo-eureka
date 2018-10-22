package com.googosoft.service.sjdr;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.fzgn.sjdr.jcsjdrDao;
import com.googosoft.pojo.fzgn.sjdr.sjdr_ddb;
import com.googosoft.pojo.fzgn.sjdr.sjdr_dwb;
import com.googosoft.pojo.fzgn.sjdr.sjdr_jcsj;
import com.googosoft.pojo.fzgn.sjdr.sjdr_ryb;
import com.googosoft.service.base.BaseService;

@Service("jcsjdrService")
public class jcsjdrService extends BaseService{

	@Resource(name="jcsjdrDao")
	public jcsjdrDao jcsjdrDao;
	
	public List insertJcsj(String file){
		return jcsjdrDao.insertJcsj(file);
	}
	public List Excelxx(String file){
		return jcsjdrDao.Excelxx(file);
	}
	public String insertJcsj_jcsj(sjdr_jcsj ddb,String file,String value,String flag){
		return jcsjdrDao.insertJcsj_jcsj(file,ddb,value,flag);
	}
}
