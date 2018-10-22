package com.googosoft.pojo.fzgn.jcsz;

/**
* ZC_SYS_DDB 实体类
* 创建时间： 2016-10-14
*@author JiaTong
*/ 


public class ZC_SYS_DDB{
	private String ddbh;
	private String mc;
	private String sjdd;
	private String dwbh;
	private String ddzt;
	private long ddjc;
	private String pxxh;
	private String ddh;
	
	/**赋值地点编号
	 * @param ddbh
	 */
	public void setDdbh(String ddbh){
	this.ddbh=ddbh;
	}
	/**取地点编号
	 * @return
	 */
	public String getDdbh(){
		return ddbh;
	}
	/**赋值名称
	 * @param MC
	 */
	public void setMc(String MC){
	this.mc=MC;
	}
	/**取名称
	 * @return
	 */
	public String getMc(){
		return mc;
	}
	/**赋值上级地点
	 * @param SJDD
	 */
	public void setSjdd(String SJDD){
	this.sjdd=SJDD;
	}
	/**取上级地点
	 * @return
	 */
	public String getSjdd(){
		return sjdd;
	}
	/**赋值所属单位单位编号
	 * @param 
	 */
	public void setDwbh(String DWBH){
	this.dwbh=DWBH;
	}
	/**取所属单位单位编号
	 * @return
	 */
	public String getDwbh(){
		return dwbh;
	}
	/**赋值地点状态正常1禁用0
	 * @param DDZT
	 */
	public void setDdzt(String DDZT){
	this.ddzt=DDZT;
	}
	/**取地点状态正常1禁用0
	 * @return
	 */
	public String getDdzt(){
		return ddzt;
	}

	/**取地点级次
	 * @return
	 */
	public long getDdjc() {
		return ddjc;
	}
	/**赋值地点级次
	 * @param ddjc
	 */
	public void setDdjc(long ddjc) {
		this.ddjc = ddjc;
	}
	/**取排序信息
	 * @return
	 */
	public String getPxxh() {
		return pxxh;
	}
	/**赋值排序信息
	 * @param pxxh
	 */
	public void setPxxh(String pxxh) {
		this.pxxh = pxxh;
	}
	/**赋值地点号
	 * @param DDH
	 */
	public void setDdh(String DDH){
	this.ddh=DDH;
	}
	/**取地点号
	 * @return
	 */
	public String getDdh(){
		return ddh;
	}
	@Override
	public String toString() {
		return "Dwb [ddbh=" + ddbh + ", mc=" + mc + ",sjdd="+sjdd+",dwbh="+dwbh+",ddzt="+ddzt+",ddjc="+ddjc+"," +
				"pxxh="+pxxh+",ddh="+ddh+"]";
	}
}

