package com.googosoft.pojo.exp;

import com.googosoft.util.Validate;

public class M_largedata {
	
	private String showname;//表头显示的信息
	private String name;//字段名称
	private String coltype;//字段类型 (注意：设置的时候首字母一定要大写，否则会报错) String Number
	private int ws;//字段位数
	private String zj;// 错误信息中显示的主键名称
	private String zjzd;//错误信息中需要获取的主键字段
	private String colstyle;//单元格的样式，注意这里一定是表头上已经存在的
	private String titlestyle;//表头单元格样式
	private boolean ismerge;//是否合并单元格
	private int erow;
	private int ecol;
	private int scol;
	private int sindex;//合并行时，被合并和下一个单元格开始列的序号（第一列是1）
	
	/**
	 * 表头显示的信息
	 * @return
	 */
	public String getShowname() {
		return showname;
	}
	/**
	 * 如果getIsmerge是true，这里是合并单元格开始的列数（第一列是0），如果getIsmerge是false，这里怎么写都不起作用
	 * @return
	 */
	public int getScol() {
		return scol;
	}

	public void setScol(int scol) {
		this.scol = scol;
	}

	/**
	 * 表头显示的信息
	 * @return
	 */
	public void setShowname(String showname) {
		this.showname = showname;
	}
	/**
	 * 字段名称
	 * @return
	 */
	public String getName() {
		return name.toUpperCase();
	}
	/**
	 * 字段名称（不区分大小写，最终get的时候会统一转成大写，注意如果要显示序号，只需要设置成rn即可，sql语句中不需要添加字段）
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 字段类型 String Number
	 * @return
	 */
	public String getColtype() {
		return Validate.isNullToDefault(coltype,"String") + "";
	}
	/**
	 * 字段类型(注意：设置的时候首字母一定要大写，否则会报错) String Number
	 * 可以不写，默认是String
	 * @return
	 */
	public void setColtype(String coltype) {
		this.coltype = coltype;
	}
	/**
	 * 字段的位数（导出txt时要按照要求的位数显示，没有位数要求的，可以不设置，这里就是0）
	 * @return
	 */
	public int getWs() {
		return (Validate.isNull(ws) ? 0 : ws);
	}
	/**
	 * 字段的位数（导出txt时要按照要求的位数显示，没有位数要求的，可以不设置，这里就是0）
	 * @return
	 */
	public void setWs(int ws) {
		this.ws = (Validate.isNull(ws) ? 0 : ws);
	}
	/**
	 * 错误信息中显示的主键名称
	 * @return
	 */
	public String getZj() {
		return Validate.isNullToDefault(zj,"") + "";
	}
	/**
	 * 错误信息中显示的主键名称
	 * @return
	 */
	public void setZj(String zj) {
		this.zj = zj;
	}
	/**
	 * 错误信息中需要获取的主键字段
	 * @return
	 */
	public String getZjzd() {
		return (Validate.isNull(zjzd) ? "" : zjzd.toUpperCase());
	}
	/**
	 * 错误信息中需要获取的主键字段
	 * @return
	 */
	public void setZjzd(String zjzd) {
		this.zjzd = zjzd;
	}
	/**
	 * 单元格的样式，注意这里一定是表头上已经存在的
	 * @return
	 */
	public String getColstyle() {
		return Validate.isNullToDefault(colstyle,"Default") + "";
	}
	/**
	 * 单元格的样式，注意这里一定是表头上已经存在的
	 * @return
	 */
	public void setColstyle(String colstyle) {
		this.colstyle = colstyle;
	}
	/**
	 * 表头单元格的样式
	 * @return
	 */
	public String getTitlestyle() {
		return Validate.isNullToDefault(titlestyle,"title") + "";
	}
	/**
	 * 表头单元格的样式
	 * @return
	 */
	public void setTitlestyle(String titlestyle) {
		this.titlestyle = titlestyle;
	}
	/**
	 * 是否合并单元格  true false
	 * @return
	 */
	public boolean getIsmerge() {
		return ismerge;
	}
	/**
	 * 是否合并单元格  true false 
	 * @return
	 */
	public void setIsmerge(boolean ismerge) {
		this.ismerge = ismerge;
	}
	/**
	 * 如果getIsmerge是true，这里是合并单元格的行数（需要合并的行数-1，比如合并2行，这里就是1），如果getIsmerge是false，这里怎么写都不起作用
	 * 注意：想合并行必须在下一行的下一个单元格（只需要在这一个单元格设置就好，再遇到合并行的列前边，其他的单元格就不用设置了）设置sindex，否则生成的文件打不开
	 * 例如：第一行的第二个单元格和第六个单元格设置和合并2行，那么在第二行的第三个单元格，需要设置sindex为3，第七个单元格，需要设置sindex为7，其他的都不需要设置
	 * @return
	 */
	public int getErow() {
		return erow;
	}
	/**
	 * 如果getIsmerge是true，这里是合并单元格的行数（需要合并的行数-1，比如合并2行，这里就是1），如果getIsmerge是false，这里怎么写都不起作用
	 * 注意：想合并行必须在下一行的下一个单元格（只需要在这一个单元格设置就好，再遇到合并行的列前边，其他的单元格就不用设置了）设置sindex，否则生成的文件打不开
	 * 例如：第一行的第二个单元格和第六个单元格设置和合并2行，那么在第二行的第三个单元格，需要设置sindex为3，第七个单元格，需要设置sindex为7，其他的都不需要设置
	 * @return
	 */
	public void setErow(int erow) {
		this.erow = erow;
	}
	/**
	 * 如果getIsmerge是true，这里是合并单元格的列数（需要合并的列数-1，比如合并2列，这里就是1），如果getIsmerge是false，这里怎么写都不起作用
	 * @return
	 */
	public int getEcol() {
		return ecol;
	}
	/**
	 * 如果getIsmerge是true，这里是合并单元格的列数（需要合并的列数-1，比如合并2列，这里就是1），如果getIsmerge是false，这里怎么写都不起作用
	 * @return
	 */
	public void setEcol(int ecol) {
		this.ecol = ecol;
	}
	/**
	 * 这里是当前单元格开始的列数（第一列是1），一般用在合并单元行时，也就是说erow不是0时
	 * 例如：第一行的D列和G列设置erow是1（合并2行），那么在第二行的E列，需要设置成5，H列需要设置成8，其他的都不需要设置
	 * 该字段跟getIsmerge没关系
	 * @return
	 */
	public int getSindex() {
		return sindex;
	}
	/**
	 * 这里是当前单元格开始的列数（第一列是1），一般用在合并单元行时，也就是说erow不是0时
	 * 例如：第一行的D列和G列设置erow是1（合并2行），那么在第二行的E列，需要设置成5，H列需要设置成8，其他的都不需要设置
	 * 该字段跟getIsmerge没关系
	 * @return
	 */
	public void setSindex(int sindex) {
		this.sindex = sindex;
	}
}
