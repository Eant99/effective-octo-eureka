package com.googosoft.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;
/**
 * 资产折旧相关Dao
 * @author zjy
 */
@Repository("baseZczjDao")
public class BaseZczjDao extends BaseDao{
	/**
	 * 获取系统设置表中关于折旧设置的部分
	 * @return
	 */
	public Map getZjxxBySysXtb(){
		//qyny和yzdny不要nvl，需要根据是否为空判断能否使用
		//折旧年月必须是10位，带着dd的，其实我们用不到dd，但是没有dd的话，存储过程中会报错，这里进行了处理，取到的dd都是01
		//zjny是当前需要折旧的年月，如果sfqy为0说明没有启用折旧，那么这个日期是空（其实这个时候用不到），sfqy为1的时候，如果yzdny是空（虽然开启折旧了，但是还没有折过）那么当前折旧年月就是qyny，如果yzdny不是空（已经进行过折旧），那么当前折旧年月就是yzdny的下个月
		//新增资产折旧时，使用zjny=，而原始资产折旧时，使用add_months(zjny, -1) >=
		//zjff：0按6大类折旧 1按16大类折旧
		//htny：已折到年月是什么回退年月就是什么，不要用nvl替换
		String sql = "select to_char(trunc(qyny,'mm'), 'yyyy-mm-dd') qyny,to_char(trunc(yzdny,'mm'), 'yyyy-mm-dd') yzdny,nvl(sfqy,'0') sfqy,zjff,(case when nvl(sfqy,'0') = '0' then null when yzdny is null then to_char(trunc(qyny,'mm'), 'yyyy-mm-dd') else to_char(trunc(add_months(yzdny, 1),'mm'), 'yyyy-mm-dd') end) zjny,to_char(trunc(yzdny,'mm'),'yyyy-mm-dd') htny from zc_sys_xtb where rownum = 1";
		return db.queryForMap(sql);
	}
	
	/**
	 * 获取折旧方法
	 * @return
	 */
	public List getZjffList(){
		return db.queryForList("select bh,mc,nvl(mrxx,'0') mrxx from zc_zj_zjff where nvl(ztbz,'1') = '1' order by pxxh");
	}
	
	/**
	 * 批量赋值
	 * @return
	 */
	public int doPlfz(String bzdm,String xznr,String content,String flh,String tablename){
		String sql = "update " + tablename + " set " + xznr + " = ? where 1 = 1 ";
		List parlist = new ArrayList();
		parlist.add(content);
		
		if(Validate.noNull(bzdm)){
			Object[] obj = bzdm.split(",",-1);
			sql += " and bzdm" + CommonUtils.getInsql(bzdm) + " ";
			for(Object o : obj){
				parlist.add(o);
			}
		}
		
		if(Validate.noNull(flh)){
			sql += " and flh like ?";
			parlist.add(flh + "%");
		}
		
		Object[] parobj = new Object[parlist.size()];
		for(int i = 0; i < parlist.size(); i++){
			parobj[i] = parlist.get(i);
		}
		
		return db.update(sql,parobj);
	}
}
