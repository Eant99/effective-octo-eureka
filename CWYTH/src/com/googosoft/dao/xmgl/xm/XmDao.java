package com.googosoft.dao.xmgl.xm;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;
@Repository("xmDao")
public class XmDao  extends BaseDao{
	/**
	 * 获取
	 * @param jb
	 * @return
	 */
	public List xmTree(String sjfl){
		String sql = "";
		if("root".equals(sjfl)){
			sql = "SELECT T.*, T.ROWID FROM CW_XM T WHERE SJ = GUID OR SJ='0' ";
		}else{
			sql = "SELECT T.*, T.ROWID FROM CW_XM T WHERE SJ = '"+sjfl+"' AND SJ<>GUID ";
		}
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 返回true为子节点，返回false为父节点
	 * @param sjfl
	 * @return
	 */
	public boolean checkIsLeaf(String sjfl){
		if("root".equals(sjfl)){
			return false;
		}
		String sql = "select COUNT(0) from CW_XMFLSZB where sjfl='"+sjfl+"' AND GUID<>'"+sjfl+"'";
		String sum = Validate.isNullToDefaultString(db.queryForSingleValue(sql), "0");
		int result = Integer.parseInt(sum);
		if(result>0){
			return true;//是父节点
		}
		return true;
	}
	

}
