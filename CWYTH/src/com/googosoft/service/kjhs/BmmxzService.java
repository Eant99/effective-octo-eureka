package com.googosoft.service.kjhs;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.BmmxzDao;
import com.googosoft.service.base.BaseService;

@Service("bmmxzService")
public class BmmxzService extends BaseService{
@Resource(name="bmmxzDao")
private BmmxzDao dao;

public List<Map<String, Object>> getBmMxzList(String treebmbh,String pznd,String startMonth,String endMonth,String pzzt,String kmbh,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
	return dao.getBmMxzList(treebmbh, pznd, startMonth, endMonth,pzzt,kmbh,bmbh,xmbh,jfjel,jfjeh,zy);
}
}
