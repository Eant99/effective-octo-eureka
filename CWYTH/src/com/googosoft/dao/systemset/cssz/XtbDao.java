package com.googosoft.dao.systemset.cssz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB_EXTEND;

@Repository("xtbDao")
public class XtbDao extends BaseDao {

	/**
	 * 系统参数设置保存
	 * @param xtbextend
	 * @return
	 */
	public int doAdd(ZC_SYS_XTB_EXTEND xtbextend) {
		String sql = "select count(guid) from  %SXTB"; //查询有几条数据
		sql=String.format(sql, SystemSet.sysBz);
		String a = db.queryForObject(sql, String.class); //执行一下sql,返回一个值a
		int i = 0;
		if(a.equals("0")){
			String sql1 = "insert into %SXTB(guid,zzbh,cgbh,"
				+ "td,gzyq,bfrq,bfts,nfcd,nfqd,lscd,lsqd,blcd,blqd,"
				+ "blqs,xxdm,dwlsfrom,dwlsto,bdlsfrom,bdlsto,czlsfrom,"
				+ "czlsto,yshdtd,bdtd,cztd,zxs,xxlx,kpmb,ysdmb,"
				+ "sfmj,mutisearchfirst,sftjzmj,rownums,"
				+ "cxdc,saasdm,ksdh) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			sql1=String.format(sql1, SystemSet.sysBz);
			i = db.update(sql1, new Object[]{xtbextend.getGuid(),xtbextend.getZzbh(),xtbextend.getCgbh(),
					xtbextend.getTd(),xtbextend.getGzyq(),xtbextend.getBfrq(),xtbextend.getBfts(),xtbextend.getNfcd(),xtbextend.getNfqd(),xtbextend.getLscd(),
					xtbextend.getLsqd(),xtbextend.getBlcd(),xtbextend.getBlqd(),xtbextend.getBlqs(),xtbextend.getXxdm(),xtbextend.getDwlsfrom(),xtbextend.getDwlsto(),
					xtbextend.getBdlsfrom(),xtbextend.getBdlsto(),xtbextend.getCzlsfrom(),xtbextend.getCzlsto(),xtbextend.getYshdtd(),
					xtbextend.getBdtd(),xtbextend.getCztd(),xtbextend.getZxs(),xtbextend.getXxlx(),xtbextend.getKpmb(),xtbextend.getYsdmb(),
					xtbextend.getSfmj(),xtbextend.getMutisearchfirst(),xtbextend.getSftjzmj(),
					xtbextend.getRownums(),xtbextend.getCxdc(),xtbextend.getSaasdm(),xtbextend.getKsdh()});
		}else{
			String sql2 = "update %SXTB set guid=?,zzbh=?,cgbh=?,td=?,gzyq=?,"
					+"bfts=?,nfcd=?,nfqd=?,lscd=?,lsqd=?,blcd=?,blqd=?,blqs=?,xxdm=?,dwlsfrom=?,"
					+"dwlsto=?,bdlsfrom=?,bdlsto=?,czlsfrom=?,czlsto=?,yshdtd=?,bdtd=?,cztd=?,"
					+"zxs=?,xxlx=?,kpmb=?,ysdmb=?,sfmj=?,mutisearchfirst=?,sftjzmj=?,"
					+"rownums=?,cxdc=?,saasdm=? ";//只有一条数据不需要根据where条件修改
			sql2=String.format(sql2, SystemSet.sysBz);
			i = db.update(sql2, new Object[]{xtbextend.getGuid(),xtbextend.getZzbh(),xtbextend.getCgbh(),
					xtbextend.getTd(),xtbextend.getGzyq(),xtbextend.getBfts(),xtbextend.getNfcd(),xtbextend.getNfqd(),xtbextend.getLscd(),
					xtbextend.getLsqd(),xtbextend.getBlcd(),xtbextend.getBlqd(),xtbextend.getBlqs(),xtbextend.getXxdm(),xtbextend.getDwlsfrom(),xtbextend.getDwlsto(),
					xtbextend.getBdlsfrom(),xtbextend.getBdlsto(),xtbextend.getCzlsfrom(),xtbextend.getCzlsto(),xtbextend.getYshdtd(),
					xtbextend.getBdtd(),xtbextend.getCztd(),xtbextend.getZxs(),xtbextend.getXxlx(),xtbextend.getKpmb(),xtbextend.getYsdmb(),
					xtbextend.getSfmj(),xtbextend.getMutisearchfirst(),xtbextend.getSftjzmj(),
					xtbextend.getRownums(),xtbextend.getCxdc(),xtbextend.getSaasdm()});
		}
		String sql4="truncate table %SXTSZ";
		sql4=String.format(sql4, SystemSet.sysBz);
		db.execute(sql4);
		
		String[] sql3= new String[13];
		for(int k = 0;k < 13; k++){
			sql3[k]= "insert into %SXTSZ(mvalue,zdvalue,tablename,zdname,zdtype)values(?,?,?,?,?) ";
			sql3[k]=String.format(sql3[k], SystemSet.sysBz);
		}
		List<Object[]> list = new ArrayList<Object[]>(13);
		list.add(new Object[]{xtbextend.getCgzzxs(),"cgzzxs","zc_yshd","采购形式","0"});
		list.add(new Object[]{xtbextend.getCzfs(),"czfs","zc_czb","处置方式","0"});
		list.add(new Object[]{xtbextend.getJfkm(),"jfkm","zc_yshd","经费科目","0"});
		list.add(new Object[]{xtbextend.getJjzlx(),"jjzlx","zc_yshd","建筑类型","0"});
		list.add(new Object[]{xtbextend.getJldw(),"jldw","zc_yshd","计量单位","0"});
		list.add(new Object[]{xtbextend.getJzjg(),"jzjg","zc_yshd","建筑结构","0"});
		list.add(new Object[]{xtbextend.getJzlx(),"jzlx","zc_yshd","记账类型","0"});
		list.add(new Object[]{xtbextend.getSyfx(),"syfx","zc_zjb","使用方向","0"});
		list.add(new Object[]{xtbextend.getWbzl(),"wbzl","zc_yshd","外币种类","0"});
		list.add(new Object[]{xtbextend.getWszk(),"wszk","zc_yshd","完损状况","0"});
		list.add(new Object[]{xtbextend.getXklb(),"xklb","zc_yshd","学科类别","0"});
		list.add(new Object[]{xtbextend.getXz(),"xz","zc_yshd","现状","0"});
		list.add(new Object[]{xtbextend.getZcly(),"zcly","zc_yshd","资产来源","0"});
		boolean b = db.batchUpdate(sql3, list);
		if(b){
			return 1;
		}else {
			return 0;
		}
	}

	public Map getXtcs() {
		String sql = "select A.guid,A.zzbh,A.cgbh,A.tmcd,A.td,A.gzyq,A.bfrq,"
				+"A.bfts,A.nfcd,A.nfqd,A.lscd,A.lsqd,A.blcd,A.blqd,A.blqs,A.xxdm,"
				+"A.dwlsfrom,A.dwlsto,A.bdlsfrom,A.bdlsto,A.czlsfrom,A.czlsto,A.yshdtd,"
				+"A.zjbtd,A.bdtd,A.cztd,A.zxs,A.xxlx,A.kpmb,A.ysdmb,A.tmmb,A.sfmj,"
				+"A.mutisearchfirst,A.sftjzmj,A.dwcd,A.ddcd,A.rycd,A.rownums,A.yzdny,A.qyny,"
				+"A.cxdc,A.saasdm,A.czsbqybz,A.zjff,A.sfqy,A.bfmc,A.ksdh ,A.yjts from %Sxtb A ";
		 sql=String.format(sql, SystemSet.sysBz);
		return db.queryForMap(sql);
	}

	public List getXtsz() {
		String sql = "SELECT * FROM %SXTSZ";
		sql=String.format(sql, SystemSet.sysBz);
		return db.queryForList(sql);
	}

	public List getXtsz(String tname) {
		String sql = "SELECT * FROM %SXTSZ where tablename = ?";
		sql=String.format(sql, SystemSet.sysBz);
		return db.queryForList(sql,new Object[]{tname});
	}
//	/**
//	 * 获取验收单切换地址（这个地址放在了人员表里）
//	 * @return
//	 */
//	public String findysdUrl(){
//		String sql = "select ysdurl from %Sxtb";
//		sql=String.format(sql, SystemSet.sysBz);
//		return db.queryForSingleValue(sql, null);
//	}
	/**
	 * 领用人建账点击切换列表时更换验收单地址
	 * @param url
	 * @return
	 */
	public int UpdUrl(String url){
		String sql = "update gx_sys_ryb set ysdurl = ? where rybh = ?";
		return db.update(sql, new Object[]{url,LUser.getRybh()});
	}
	/**
	 * 是否启用折旧
	 * @return
	 */
	public String sfqy(){
		String sql = "select nvl(sfqy,'0') sfqy from %Sxtb";
		sql=String.format(sql, SystemSet.sysBz);
		return db.queryForSingleValue(sql, null);
	}

	public int doUpdate(String ksdh) {
		String sql="update ZC_SYS_XTB set ksdh='"+ksdh+"'";
		int i=0;
		try{
			i=db.update(sql);
		}catch(DataAccessException e){
			return -1;
		}
		return i;
	}
}
