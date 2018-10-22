package com.googosoft.pojo.zf;

import javax.xml.bind.annotation.XmlRootElement;

/**
*@author 杨超超
*@date   2018年2月9日---下午1:53:48
*/
@XmlRootElement
public class Pzzf {
	private String khyh;  
    private String hm;  
    private String zh;  
    private int je;  
      
      
    public Pzzf(String khyh, String hm, String zh, int je) {  
        super();  
        this.khyh = khyh;  
        this.hm = hm;  
        this.zh = zh;  
        this.je = je;  
    }  
    
    public String getKhyh() {
		return khyh;
	}

	public void setKhyh(String khyh) {
		this.khyh = khyh;
	}

	public String getHm() {
		return hm;
	}

	public void setHm(String hm) {
		this.hm = hm;
	}

	public String getZh() {
		return zh;
	}

	public void setZh(String zh) {
		this.zh = zh;
	}

	public int getJe() {
		return je;
	}

	public void setJe(int je) {
		this.je = je;
	}

	public Pzzf() {  
        super();  
    }  
	
}


