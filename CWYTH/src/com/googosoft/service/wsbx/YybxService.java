package com.googosoft.service.wsbx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.wsbx.YybxDao;

@Service("yybxService")
public class YybxService {
	@Resource(name = "yybxDao")
	public YybxDao yybxDao;

	public List<Map<String, Object>> getTime() {
		return yybxDao.getTime();
	}
}
