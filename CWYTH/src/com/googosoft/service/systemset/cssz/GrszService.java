package com.googosoft.service.systemset.cssz;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.GrszDao;
import com.googosoft.service.base.BaseService;
@Service("grszservice")
public class GrszService extends BaseService{
	@Resource(name="grszdao")
	private GrszDao grszdao;
	
	/**
	 * 获取人员基本信息
	 * @param rybh
	 * @return
	 */
	public Map getGrxx(String rybh){
		return grszdao.getGrxx(rybh);
	}
	/**
	 * 保存人员头像信息
	 * @param 
	 * @return
	 */
	public int doUpdRyphoto(String rybh,String path){
		int result = grszdao.doUpdRyphoto(rybh,path);
		if(result>1){
			doAddOplog(OplogFlag.ADD,"人员表",rybh);
		}
		return result;
	}
	
	/**
	 * 获取个人外语水平信息
	 */
	public List getWysp(String rybh) {
		return grszdao.getWysp(rybh); 
	}
	/**
	 * 获取个人论文情况
	 */
	public List getLwqk(String rybh) {
		return grszdao.getLwqk(rybh); 
	}
	/**
	 * 获取个人进修情况
	 */
	public List getJxqk(String rybh) {
		return grszdao.getJxqk(rybh); 
	}
	/**
	 *获取个人著作情况
	 */
	public List getZzqk(String rybh) {
		return grszdao.getZzqk(rybh); 
	}
	/**
	 * 获取个人成果奖励
	 */
	public List getCgjl(String rybh) {
		return grszdao.getCgjl(rybh); 
	}
	/**
	 * 读取密保问题
	 */
	public Map getMmzh(String rybh) {
		return grszdao.getMmzh(rybh);
	}
}
