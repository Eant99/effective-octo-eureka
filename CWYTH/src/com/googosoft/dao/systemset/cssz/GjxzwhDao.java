package com.googosoft.dao.systemset.cssz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_TOOLDOWN;
import com.googosoft.util.CommonUtils;
/**工具下载维护
 * @author sxl
 *
 */
@Repository("gjxzwhDao")
public class GjxzwhDao extends BaseDao {
	// 日志管理
		private Logger logger = Logger.getLogger(GjxzwhDao.class);

		/**保存数据
		 * @param xzwxb
		 * @return
		 */
		public int doAdd(ZC_SYS_TOOLDOWN xzwxb) {
			String sql = "insert into ZC_SYS_TOOLDOWN(guid,wjlx,wjmc,fname,path,wjms,zjr,zjsj)values(?,?,?,?,?,?,?,sysdate)"; 
			Object[] obj = new Object[] {
					xzwxb.getGuid(),
					xzwxb.getWjlx(),
					xzwxb.getWjmc(),
					xzwxb.getFname(),
					xzwxb.getPath(),
					xzwxb.getWjms(),
					xzwxb.getZjr()
				};
			int i = 0;
			try {
				i = db.update(sql, obj);
			} catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				logger.error("数据库操作失败\n" + sqle);
				return -1;
			}
			return i;
		}
		/**查询一条详细信息
		 * @param guid
		 * @return
		 */
		public Map getObjectById(String guid){
			String sql="select Z.WJLX,Z.WJMC,Z.WJMS FROM ZC_SYS_TOOLDOWN Z where guid=?";
			return db.queryForMap(sql,new Object[]{guid});
		}
		/**删除维修工具
		 * @param guid
		 * @return
		 */
		public int doDel(String guid) {
			String sql = "delete ZC_SYS_TOOLDOWN where guid" + CommonUtils.getInsql(guid);
			Object[] obj = guid.split(",");
			int i = 0;
			try {
				i = db.update(sql, obj);
			} catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				logger.error("数据库操作失败\n" + sqle);
				return -1;
			}
			return i;
		}
		/**查看工具上传维护是否重复
		 * @param xzwxb
		 * @return
		 */
		public boolean doCheckGsbh(ZC_SYS_TOOLDOWN xzwxb) {
			String sql = "select count(*) from ZC_SYS_TOOLDOWN where guid=?";
			Object[] obj = new Object[]{xzwxb.getGuid()};
			String a = db.queryForSingleValue(sql, obj);
			if ("0".equals(a)) {
				return true;
			} else {
				return false;
			}
		}
		/**修改工具下载维护信息
		 * @param xzwxb
		 * @return
		 */
		public int doUpdate(ZC_SYS_TOOLDOWN xzwxb) {
			String sql="update ZC_SYS_TOOLDOWN set WJMC=?,WJMS=? where guid=?";
			Object[] obj=new Object[]{
					xzwxb.getWjmc(),
					xzwxb.getWjms(),
					xzwxb.getGuid()
			};
			int i = 0;
			try {
				i = db.update(sql, obj);
			} catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				logger.error("数据库操作失败\n" + sqle);
				return -1;
			}
			return i;
		}
		/**
		 * 从附件表取附件信息
		 * @param czbgbh
		 * @return
		 */
		public List getFjList(String guid) {
			String sql = " SELECT Z.PATH,Z.WJMC,Z.WJMS,Z.GUID,Z.WJLX,Z.FNAME FROM ZC_SYS_TOOLDOWN Z  where GUID=?";
			return db.queryForList(sql,new Object[]{guid});
		}
		
}
