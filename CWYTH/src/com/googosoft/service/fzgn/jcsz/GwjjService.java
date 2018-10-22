package com.googosoft.service.fzgn.jcsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.GwjjDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_GWJJB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.WindowUtil;

/**
 * 岗位交接service层
 * @author RC 2017-08-30
 */
@Service("gwjjService")
public class GwjjService extends BaseService{
	
	@Resource(name="gwjjDao")
	private GwjjDao gwjjDao;

	/**
	 * 新增岗位交接信息
	 * @param gwjjb
	 * @param jsxx
	 * @return
	 */
	public boolean doAdd(GX_SYS_GWJJB gwjjb, String jsxx){
		gwjjb.setGid(UuidUtil.get32UUID());
		gwjjb.setYwfqr(WindowUtil.getXx(gwjjb.getYwfqr(), "R"));
		gwjjb.setYqxsyr(WindowUtil.getXx(gwjjb.getYqxsyr(), "R"));
		gwjjb.setJgr(WindowUtil.getXx(gwjjb.getJgr(), "R"));
		if(gwjjDao.doAdd(gwjjb, jsxx)){
			doAddOplog(OplogFlag.ADD,"新增岗位交接信息",gwjjb.getGid());
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 获取实体（查看）
	 * @param gid
	 * @return
	 */
	public Map<String, Object> getObjectById(String gid){
		return gwjjDao.getObjectById(gid);
	}
	/**
	 * 通过gid查询角色信息（查看）
	 * @param gid
	 * @return
	 */
	public List<Map<String, Object>> getJsxx(String gid) {
		return gwjjDao.getJsxx(gid);
	}
}
