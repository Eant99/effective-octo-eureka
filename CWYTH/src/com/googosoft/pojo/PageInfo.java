package com.googosoft.pojo;

import com.googosoft.util.Validate;

/**
 * 列表数据实体类
 * @author  master
 * @version 0.1
 */
/**
 * @author Administrator
 *
 */
public class PageInfo {
	
	public PageInfo(){
	}
	public PageInfo(String _draw,String _recordsTotal,String _recordsFiltered,String _data){
		this.draw = _draw;
		this.recordsTotal = _recordsTotal;
		this.recordsFiltered = _recordsFiltered;
		this.data = _data;
	}
	/**
	 * 带分组
	 * @param _draw
	 * @param _recordsTotal
	 * @param _recordsFiltered
	 * @param _data
	 * @param _groupJson
	 */
	public PageInfo(String _draw,String _recordsTotal,String _recordsFiltered,String _data,String _groupJson){
		this.draw = _draw;
		this.recordsTotal = _recordsTotal;
		this.recordsFiltered = _recordsFiltered;
		this.data = _data;
		this.groupJson = _groupJson;
	}
	/**
	 * 带总计和当前页合计
	 * @param _draw
	 * @param _recordsTotal
	 * @param _recordsFiltered
	 * @param _data
	 * @param _zjJson
	 * @param _hjJson
	 */
	public PageInfo(String _draw,String _recordsTotal,String _recordsFiltered,String _data,String _zjJson,String _hjJson){
		this.draw = _draw;
		this.recordsTotal = _recordsTotal;
		this.recordsFiltered = _recordsFiltered;
		this.data = _data;
		this.zjJson = _zjJson;
		this.hjJson = _hjJson;
	}
	/**
	 * 带分组和总计、当前页合计
	 * @param _draw
	 * @param _recordsTotal
	 * @param _recordsFiltered
	 * @param _data
	 * @param _groupJson
	 * @param _zjJson
	 * @param _hjJson
	 */
	public PageInfo(String _draw,String _recordsTotal,String _recordsFiltered,String _data,String _groupJson,String _zjJson,String _hjJson){
		this.draw = _draw;
		this.recordsTotal = _recordsTotal;
		this.recordsFiltered = _recordsFiltered;
		this.data = _data;
		this.groupJson = _groupJson;
		this.zjJson = _zjJson;
		this.hjJson = _hjJson;
	}
	
	private String draw;//请求次数
	private String recordsTotal;//总记录数
	private String recordsFiltered;//过滤后的总记录数
	private String data;//数据
	private String groupJson;//分组信息
	private String hjJson;//当前页合计信息
	private String zjJson;//总计信息
	public String getZjJson() {
		return zjJson;
	}
	public void setZjJson(String zjJson) {
		this.zjJson = zjJson;
	}
	public String getHjJson() {
		return hjJson;
	}
	public void setHjJson(String hjJson) {
		this.hjJson = hjJson;
	}
	public String getGroupJson() {
		return groupJson;
	}
	public void setGroupJson(String groupJson) {
		this.groupJson = groupJson;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public String getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(String recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public String getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(String recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String toJson(){
		StringBuffer stru = new StringBuffer();
		stru.append("{\"draw\":\""+this.draw+"\",\"recordsTotal\":\""+this.recordsTotal+"\",\"recordsFiltered\":\""+this.recordsFiltered+"\",\"data\":"+this.data);
		if(Validate.noNull(this.getGroupJson())){
			stru.append(",\"groupJson\":"+groupJson);
		}
		if(Validate.noNull(this.getHjJson())){
			stru.append(",\"hjJson\":"+hjJson);
		}
		if(Validate.noNull(this.getZjJson())){
			stru.append(",\"zjJson\":"+zjJson);
		}
		stru.append("}");
		return stru.toString();
//		if(Validate.noNull(this.getHjJson())&&Validate.noNull(this.getZjJson())&&Validate.noNull(this.getGroupJson())){
//			return "{\"draw\":\""+this.draw+"\",\"recordsTotal\":\""+this.recordsTotal+"\",\"recordsFiltered\":\""+this.recordsFiltered+
//					"\",\"data\":"+this.data+",\"groupJson\":"+groupJson+",\"zjJson\":"+zjJson+",\"hjJson\":"+hjJson+"}";
//		}else if(Validate.noNull(this.getGroupJson())){
//			return "{\"draw\":\""+this.draw+"\",\"recordsTotal\":\""+this.recordsTotal+"\",\"recordsFiltered\":\""+this.recordsFiltered+
//					"\",\"data\":"+this.data+",\"groupJson\":"+groupJson+"}";
//		}else if(Validate.noNull(this.getHjJson())&&Validate.noNull(this.getZjJson())){
//			return "{\"draw\":\""+this.draw+"\",\"recordsTotal\":\""+this.recordsTotal+"\",\"recordsFiltered\":\""+this.recordsFiltered+
//					"\",\"data\":"+this.data+",\"zjJson\":"+zjJson+",\"hjJson\":"+hjJson+"}";
//		}else{
//			return "{\"draw\":\""+this.draw+"\",\"recordsTotal\":\""+this.recordsTotal+"\",\"recordsFiltered\":\""+this.recordsFiltered+
//					"\",\"data\":"+this.data+"}";
//		}
	}
}
