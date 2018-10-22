package com.googosoft.service.jzmb;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.fzgn.jcsz.expExcel.JsxxsExportExcel;
import com.googosoft.controller.jzmb.JzmbExportExcel;
import com.googosoft.dao.jzmb.JzmbDao;
import com.googosoft.dao.ysgl.grjfsz.GrjfszDao;
import com.googosoft.pojo.kjhs.Cw_jzmbmx;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSMXB;
import com.googosoft.util.PageData;

@Service("jzmbService")
public class JzmbService extends BaseService{
	
	@Resource(name="jzmbDao")
	public JzmbDao jzmbDao;

	/**
	 * 删除
	 * @param pd
	 * @return 
	 */
	@Transactional
	public int doDelete(PageData pd) {
		String guid = pd.getString("guid");
		String array[] = guid.split("','");
		for(int i=0;i<array.length;i++) {
			List list = jzmbDao.getxx(array[i]);
			for(int j=0;j<list.size();j++) {
				Map map = (Map) list.get(j);
				String guid1 = (String) map.get("guid");
				String mbbh = (String) map.get("mbbh");
				String sszt = (String) map.get("sszt");
				jzmbDao.doDelete(guid1);
				jzmbDao.dodeletejzmbmx(guid1);
				jzmbDao.updatebh(sszt, mbbh);
			}
			
		}		
		return 1;	
	}
	
	/**
	 * 查找凭证类型列表
	 * @param guid   
	 * @return 
	 */
	public List getSrxmMapById(String guid){
		return jzmbDao.selectSrxmMapById(guid);
	}
	
	/**
	 * 查找摘要列表（与数据字典表的项匹配）
	 * @param guid   
	 * @return 
	 */
	public List getjzmbzyMapById(String guid){
		return jzmbDao.selectjzmbzyMapById(guid);
	}
	
	public List getPzlxMapById(){
		return jzmbDao.getPzlxMapById();
	}
	
	/**
	 * 修改
	 * @param pd   
	 * @return 
	 */
	public int editJzmb(PageData pd) {
		return jzmbDao.updateJzmb(pd);
	}
	
	/**
	 * 增加
	 * @param pd   
	 * @return 
	 */
	public int addJzmb(PageData pd,HttpSession session) {
		return jzmbDao.insertJzmb(pd,session);
	}
	
	/**
	 * 结转信息（查看）：获取结转的详细信息
	 * 
	 * @param jzbh
	 * @return
	 */
	public Map<String, Object> getjzmbMapById(String guid){
		return jzmbDao.selectjzmbMapById(guid);
	}
	/**
	 * 结转模板明细增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddmx(Cw_jzmbmx srysmxb) {
		
		int i = jzmbDao.doAddmx(srysmxb);
		
		return i;
	
    }
	/**
	 * 获取结转模板明细编辑信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getjzmbmxById(String guid) {
		return jzmbDao.getjzmbmxById(guid);
	}
	 /**
	 * 结转模板明细删除
	 * @param jbzcys
	 * @return
	 */
	    public int dodeletejzmbmx(String guid) {
		
		int i = jzmbDao.dodeletejzmbmx(guid);
		
		return i;
	
       }
		/**
		 * 判断项目类型编号的重复性
		 * @param 项目类型编号
		 * @return
		 */
		public boolean doCheckDwbh(String mbbh) {
			boolean result = jzmbDao.doCheckDwbh(mbbh);
			return result;
		}
		/**
		 * 业务记录表细增加操作
		 * @param 
		 * @param
		 * @return
		 */
		public int doAddjwjl(Map map) {
			
			int i = jzmbDao.doAddjwjl(map);
			
			return i;
		
	    }
		
		/**
		 * 导出教师信息Excel   wzd
		 * @return
		 */
		public Object expExcel(String realfile, String shortfileurl, String guid,String searchValue, String rybh, String s1) {
			List<Map<String, Object>> dwList = this.jzmbDao.getJsList(guid,searchValue,rybh,s1);
			String Title = "结转模板";
			String[] title = new String[] { "序号", "模板编号", "模板名称", "凭证摘要", "转出科目" ,"转入科目" };
			Map<String, Object> dataMap = JzmbExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
			return dataMap;
		}
}
