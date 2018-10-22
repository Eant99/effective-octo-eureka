package com.googosoft.pojo;

import java.util.List;
import java.util.Map;

import com.googosoft.util.Validate;

/**
 * 分页方法实体类
 * 
 * @author master
 */
@SuppressWarnings("rawtypes")
public class PageList {

	private String tableName;
	private String strWhere;
	private String orderBy;
	private String orderDir;
	private String curPage;
	private String page_record;
	private String page_length;
	private String sqlText;
	private String keyId;
	private String hj1;
	private String hjWhere; // 合计的专有条件
	private String recordCnt; // 总条数
	private String groupSql; // 分组的sql语句
	private List totalList;
	private List hjList;
	private List contentList;
	private List groupList;

	public List getGroupList() {
		return groupList;
	}

	public void setGroupList(List groupList) {
		this.groupList = groupList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getStrWhere() {
		if (Validate.isNull(strWhere)) {
			return "";
		} else {
			return strWhere;
		}
	}

	public void setStrWhere(String strWhere) {
		this.strWhere = strWhere;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}

	public String getPage_length() {
		return page_length;
	}

	public void setPage_length(String page_length) {
		this.page_length = page_length;
	}

	public String getPage_record() {
		return page_record;
	}

	public void setPage_record(String page_record) {
		this.page_record = page_record;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getHj1() {
		return hj1;
	}

	public void setHj1(String hj1) {
		this.hj1 = hj1;
	}

	public String getHjWhere() {
		return hjWhere;
	}

	public void setHjWhere(String hjWhere) {
		this.hjWhere = hjWhere;
	}

	/**
	 * 获取总条数
	 * 
	 * @return
	 */
	public String getRecordCnt() {
		return recordCnt;
	}

	/**
	 * 设置总条数
	 * 
	 * @return
	 */
	public void setRecordCnt(String recordCnt) {
		this.recordCnt = recordCnt;
	}

	/**
	 * 获取分组的sql语句
	 */
	public String getGroupSql() {
		if (Validate.isNull(groupSql)) {
			return "";
		} else {
			return groupSql;
		}
	}

	/**
	 * 设置分组的sql语句
	 */
	public void setGroupSql(String groupSql) {
		this.groupSql = groupSql;
	}

	public List<Map> getTotalList() {
		return totalList;
	}

	public void setTotalList(List totalList) {
		this.totalList = totalList;
	}

	public List<Map> getHjList() {
		return hjList;
	}

	public void setHjList(List hjList) {
		this.hjList = hjList;
	}

	public List getContentList() {
		return contentList;
	}

	public void setContentList(List contentList) {
		this.contentList = contentList;
	}

	@Override
	public String toString() {
		return "PageList [tableName=" + tableName + ", strWhere=" + strWhere + ", orderBy=" + orderBy + ", curPage="
				+ curPage + ", page_record=" + page_record + ", sqlText=" + sqlText + ", keyId=" + keyId + ", hj1="
				+ hj1 + "]";
	}
}
