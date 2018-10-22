package com.googosoft.pojo.exp;

import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

/**
 * 地点信息综合查询实体类1
 * @author JiaTong
 */
public class DdxxSearch {
	
	private String mc,
	ddh,
	dwbh,
	sjdd;
	
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getDdh() {
		return ddh;
	}

	public void setDdh(String ddh) {
		this.ddh = ddh;
	}

	public String getDwbh() {
		if(Validate.noNull(dwbh)){
			return "d_"+dwbh;
		}
		return "";
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	public String getSjdd() {
		if(Validate.noNull(sjdd)){
			return "e_"+WindowUtil.getXx(sjdd, "P");
		}
		return sjdd;
	}

	public void setSjdd(String sjdd) {
		this.sjdd = sjdd;
	}
}
