package com.googosoft.service.system.index;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.system.index.IndexDao;
import com.googosoft.service.base.BaseService;

@Service("indexService")
public class IndexService extends BaseService{
	
	@Resource(name="indexDao")
	private IndexDao indexDao;

	public int getAssetsNum(String searchword){		
		return indexDao.getAssetsNum(searchword);
	}
	
	public  String getBzdm(){
		return indexDao.getBzdm();
	}
	/**
	 * 得到通知公告表
	 * 
	 */
	public List<Map<String, Object>> getTzgg(String js) {
		
		List list = indexDao.getTzgg(js);
		
		return list;
		
		
	}
	/**
	 * 得到今日报销
	 * 
	 */
	public Map getjrbx() {
		
		Map map = indexDao.getjrbx();
		System.out.println("111111111111111111"+map);
		return map;
		
		
	}
	/**
	 * 得到我的报销
	 * 
	 */
	public Map getwdbx() {
		
		Map map = indexDao.getwdbx();
		return map;
		
		
	}
	/**
	 * 得到我的项目信息
	 * 
	 */
	public Map getxm() {
		
		Map map = indexDao.getxm();
		return map;
		
		
	}
	
	public List getXzList(String rybh){
		return indexDao.getXzList(rybh);
	}
	/**
	 * 得到当前登陆人模块操作权限
	 * 
	 */
	public List<Map<String, Object>> getCzqx(String rybh) {
		
		List list = indexDao.getCzqx(rybh);
		
		return list;
		
		
	}

	/**
	 * 得到所有一级菜单
	 * @param rybh
	 * @return
	 */
	public List<Map<String, Object>> getYjmk(String filter) {
		List list = indexDao.getYjmk(filter);

		return list;
	}
	/**
	 * 查询事前审批是否启用
	 * @return
	 */
	public boolean getSqspSfqy() {
		return "0".equals(indexDao.getSqspmk()) ? false:true;
	}
	/**
	 * 
	 * @return
	 */
	public String getWsbx(String sjqj){
        Map map = indexDao.getWsbx(sjqj);
        System.err.println("*************"+"{\"cl\":\""+map.get("cl")+"\",\"gw\":\""+map.get("gw")+"\",\"rc\":\""+map.get("rc")+"\",\"clsjs\":\""+map.get("clsjs")+"\",\"gwsjs\":\""+map.get("gwsjs")+"\",\"rcsjs\":\""+map.get("rcsjs")+"\"}");
       
        
        return "{\"cl\":\""+map.get("cl")+"\",\"gw\":\""+map.get("gw")+"\",\"rc\":\""+map.get("rc")+"\",\"clsjs\":\""+map.get("clsjs")+"\",\"gwsjs\":\""+map.get("gwsjs")+"\",\"rcsjs\":\""+map.get("rcsjs")+"\"}";
        
    }
	public List<Map<String, Object>> getBsdtMkList(String rybh,String filter){
		return indexDao.getBsdtMkList(rybh, filter);
	}
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getDscpz() {
		return indexDao.getDscpz();
	}

	public boolean doUpdateTzxx(String guid) {
		// TODO Auto-generated method stub
		return indexDao.doUpdateTzxx(guid);
	}
    /**
     * 首页  我的项目
     * @param rygh
     * @return
     */
	public Object getWdxmList(String rybh) {
		return indexDao.getWdxmList(rybh);
	}
}
