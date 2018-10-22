package com.googosoft.service.kjhs;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.controller.kjhs.PzmbExportExcel;
import com.googosoft.dao.kjhs.PzmbDao;
import com.googosoft.pojo.kjhs.Cw_pzmb;
import com.googosoft.pojo.kjhs.Cw_pzmbmx;

@Service("pzmbService")
public class PzmbService {
    @Resource(name="pzmbDao")	
	public PzmbDao pzmbDao;
	
	/**
	 * 凭证模板增加
	 * @param jbzcys
	 * @return
	 */
	    public int doAddpzmb(Cw_pzmb pzmb) {
		
		int i = pzmbDao.doAddpzmb(pzmb);
		
		return i;
	
       }
	    /**
		 * 凭证模板修改
		 * @param jbzcys
		 * @return
		 */
		    public int doupdatepzmb(Cw_pzmb pzmb) {
			
			int i = pzmbDao.doupdatepzmb(pzmb);
			
			return i;
		
	       }
		    /**
			 * 验证模板编号是否重复
			 */
			public boolean getObjectByMbbh(String guid, String mbbh) {
				return pzmbDao.getObjectByMbbh(guid, mbbh);
			}
		    /**
			 * 凭证模板明细修改
			 * @param jbzcys
			 * @return
			 */
			    public int doupdatepzmbmx(Cw_pzmbmx pzmbmx) {
				
				int i = pzmbDao.doupdatepzmbmx(pzmbmx);
				
				return i;
			
		       }
			    
			    /**
				 * 凭证模板明细删除
				 * @param jbzcys
				 * @return
				 */
				    public int dodeletepzmbmx(String mbbh) {
					
					int i = pzmbDao.dodeletepzmbmx(mbbh);
					
					return i;
				
			       }
	    
	    /**
	     * 凭证模板明细增加
	     * @param pzmbmx
	     * @return
	     */
       public int doAddpzmbmx(Cw_pzmbmx pzmbmx) {
		
		int i = pzmbDao.doAddpzmbmx(pzmbmx);
		
		return i;
	
       }
       /**
   	 * 获取凭证模板编辑信息
   	 * @param
   	 * @return
   	 */
   	public Map<?, ?> getpzmbById(String guid) {
   		return pzmbDao.getpzmbById(guid);
   	}
   	/**
	 * 获取凭证模板明细编辑信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getpzmbmxById(String guid,String kjzd) {
		return pzmbDao.getpzmbmxById(guid,kjzd);
	}
	
	/**
	 * 凭证模板批量删除
	 * @param 
	 * @return
	 */
	@Transactional
	public int doDeletes(String guid) {
		String array[] = guid.split("','");
		for(int i = 0;i<array.length;i++) {
		List list =	pzmbDao.getxx(array[i]);
		for(int j=0;j<list.size();j++) {
			Map map = (Map) list.get(j);
			String guid1 = (String) map.get("guid");
			String sszt = (String) map.get("sszt");
			String mbbh = (String) map.get("mbbh");
			pzmbDao.doDeletes(guid1);
			pzmbDao.dodelete(guid1);
			pzmbDao.updatebh(sszt, mbbh);
			
		}
			
		}
	
		return 1;
	}
	public Object expExcel(String realfile, String shortfileurl,String searchValue, String guid,String sszt) {
		List<Map<String, Object>> dwList = this.pzmbDao.getList(searchValue,guid,sszt);
		String Title = "凭证模板";
		String[] title = new String[] { "序号","模板编号", "模板名称","凭证摘要", "科目编号", "科目名称", "备注"   };
		Map<String, Object> dataMap =PzmbExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}

}
