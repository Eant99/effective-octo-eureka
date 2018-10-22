package com.googosoft.service.kjzdqy;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.kjzdqy.KjzdqyDao;
import com.googosoft.service.base.BaseService;

/**
 * 会计制度启用
 * 
 * @author wangzhiduo
 * @date 2018-1-2上午9:37:06
 */
@Service("kjzdqyService")
public class KjzdqyService extends BaseService {
	@Resource(name = "kjzdqyDao")
	public KjzdqyDao kjzdqyDao;
	
	/**
	 * 会计制度启用设置保存按钮
	 * @param zdm
	 * @param guid
	 * @return
	 */
	public boolean doSave(String zdm, String guid) {
		return kjzdqyDao.doSave(zdm, guid);
	}
	
	/**
	 * 查询会计制度
	 * @return
	 */
	public List getZd() {
		return kjzdqyDao.getZd();
	}

	/**
	 * 会计制度转换
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getKjzdzh() {
		return kjzdqyDao.getKjzdzh();
	}

	/**
	 * 删除会计制度转换
	 * 
	 * @param guid
	 * @return
	 */
	public int doDelKjkm(String guid) {
		int result = kjzdqyDao.doDelKjkm(guid);
		if (result > 0) {
			doAddOplog(OplogFlag.DEL, "操作成功！", guid);
		}
		return result;
	}

	/**
	 * 修改回显
	 * 
	 * @param guid
	 * @return
	 */
	public Map<String, Object> getKjzdzhByid(String guid) {
		return kjzdqyDao.getKjzdzhByid(guid);
	}

	/**
	 * 会计制度的编辑保存
	 * 
	 * @param map
	 * @return
	 */
	public int doSaveKjzd(Map<String, Object> map) {
		return kjzdqyDao.doSaveKjzd(map);
	}

	/**
	 * 执行----先将科目插入
	 * @return
	 */
	public int doZx1() {
		return kjzdqyDao.doZx1();
	}

	/**
	 * 执行----根据替换数据转换金额
	 * @return
	 */
	public boolean doZx2() {
		return kjzdqyDao.doZx2();
	}
	/**
	 * 执行----将会计制度启用为新版
	 * @return
	 */
	public int doZx3(String ztid) {
		return kjzdqyDao.doZx3(ztid);
	}
	/**
	 * 执行----修改财务报表和旧财务报表的权限
	 * @return
	 */
	public int doZx4() {
		return kjzdqyDao.doZx4();
	}

	/**
	 * 
	 * @param value 输入框值
	 * @param flag 类型   D：单位   R：人员  。。。。。。\
	 * @param userId 当前登录人
	 * @return
	 */
	public Object getSuggest(String value, String flag) {
		String suggestXx = "";
		if("KJKMZH".equals(flag)){//原科目编号 
			suggestXx = kjzdqyDao.getKjkmzh(value,flag );
		}
		else if("KJKMZH2".equals(flag)){//现科目编号
			suggestXx = kjzdqyDao.getKjkmzh(value,flag);
		}
		else if("KJKMZHValidate".equals(flag)){//信息验证
			suggestXx = kjzdqyDao.validateKjkmzh(value,flag);
		}
		else if("KJKMZH2Validate".equals(flag)){//信息验证
			suggestXx = kjzdqyDao.validateKjkmzh(value,flag);
		}
		return suggestXx;
	}
/**
 * 获取原科目名称
 * @param ykmbh
 * @return
 */
	public String getKmmc(String ykmbh,String xkmbh) {
		return kjzdqyDao.getKmmc(ykmbh,xkmbh);
	}

}
