package com.googosoft.service.kjhs.zffs;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.zffs.ZffsDao;

@Service("zffsService")
public class ZffsService {
	@Resource(name="zffsDao")
	public ZffsDao Dao;
	public List<Map<String, Object>> getZffs(){
		return Dao.getZffs();
	}

}
