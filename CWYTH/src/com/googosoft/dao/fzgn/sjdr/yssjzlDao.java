package com.googosoft.dao.fzgn.sjdr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.fzgn.sjdr.ZC_ZJB_DRMX;
import com.googosoft.pojo.fzgn.sjdr.sjdr_zjb;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("yssjzlDao")
public class yssjzlDao extends BaseDao{
	private Logger logger = Logger.getLogger(yssjzlDao.class);
	
	/**
	 * 信息保存
	 * @param zjb
	 * @return
	 * @throws ParseException 
	 */
	public boolean doAdd(ZC_ZJB_DRMX zjb) throws ParseException{
		List<sjdr_zjb> list = zjb.getZjbmx();
		List sqllist = new ArrayList();
		List parlist = new ArrayList();
		for(int i=0;i<list.size();i++){
			sjdr_zjb zjbmx = list.get(i);
			String sql = "update ZC_ZJB_DR set "
					+ "yqbh=?,flh=?,flmc=?,yqmc=?,"
					+ "dj=?,jldw=?,zzj=?,dzrrq=?,"
					+ "sydw=?,syr=?,syfx=?,xz=?,"
					+ "jfkm=?,zcly=?,gzrq=?,ysdh=?,"
					+ "dabh=?,jdr=?,ztbz=?,jdrq=?,"
					+ "shr=?,shrq=?,shyj=?,rzrq=?,"
					+ "cz10=?,cz6=?,bmsx=?,bzxx=?,"
					+ "sccj=?,fjzj=?,fjs=?,gbm=?,"
					+ "cch=?,ccrq=?,xss=?,djh=?,"
					+ "xh=?,gg=?,wbzl=?,wbje=?,"
					+ "jkdj=?,jsh=?,gzyq=?,synx=?,"
					+ "htbh=?,cgr=?,kyxm=?,fzr=?,"
					+ "zdsys=?,jcnf=?,sykh=?,zj=?,"
					+ "czrq=?,pzh=?,jzbz=?,gkdw=?,"
					+ "gkry=?,gkyj=?,gkrq=?,xk=?,"
					+ "xklb=?,jzlx=?,xmh=?,cllx=?,"
					+ "clbz=?,ccly=?,syxz=?,bz=?,"
					+ "czr=?,fzrq=?,fgxs=?,gnmj=?,"
					+ "wfmj=?,fwyt=?,symj=?,jsr=?,"
					+ "pgjz=?,djrq=?,djjg=?,zlh=?,"
					+ "pzwh=?,gljg=?,tjsj=?,jzxs=?,"
					+ "glbm=?,ljzj=?,zmjz=?,zjjt=?,"
					+ "cgzzxs=?,cjdfdw=?,czdfdw=?,btzdw=?,"
					+ "jj=?,td_qszm=?,td_qsxz=?,td_fzrq=?,"
					+ "zmmj=?,td_dh=?,td_zymj=?,td_cjmj=?,"
					+ "td_czmj=?,td_jymj=?,td_xzmj=?,td_qtmj=?,"
					+ "zyjz=?,cjjz=?,czjz=?,jyjz=?,"
					+ "xzjz=?,qtjz=?,td_rzkm=?,td_qsnx=?,"
					+ "td_yt=?,td_qdjz=?,td_dymj=?,td_ftmj=?,"
					+ "fw_fgxs=?,cqxs=?,qsxz=?,fw_qszm=?,"
					+ "dxmj=?,dsmj=?,fw_gnmj=?,qsrq=?,"
					+ "fw_fzrq=?,fw_bgmj=?,fw_hymj=?,fw_ckmj=?,"
					+ "fw_stmj=?,fw_pdmj=?,fw_jfmj=?,fw_zymj=?,"
					+ "fw_jymj=?,fw_cjmj=?,fw_czmj=?,fw_xzmj=?,"
					+ "fw_qtmj=?,fw_zrjs=?,pp=?,sb_gl=?,"
					+ "bxjzrq=?,sb_dyjlb=?,jt_bzqk=?,jt_clcd=?,"
					+ "jt_cpxh=?,jt_pql=?,jt_ppxz=?,jt_zcnf=?,"
					+ "jt_cllx=?,jt_clzws=?,jt_syxz=?,jj_bgzylb=?,"
					+ "jj_sflb=?,wx_pgjz=?,wx_djrq=?,wx_djjg=?,"
					+ "wx_zlh=?,wx_pzwh=?,wx_gljg=?,wx_ntxe=?,"
					+ "wx_fmmc=?,zyyt=?,bdbz=?,flgbm=?,"
					+ "gbmid=?,bdzt=?,tph=?,yt=?,"
					+ "rj_ppgsd=?,rj_lx=?,rj_sfzb=?,rj_sqxklx=?,"
					+ "rj_sqxkqx=?,rj_zdsqs=?,wx_kff=?,wx_xcfs=?,"
					+ "czzj=?,fczzj=?,wwmc=?,cqly=?,"
					+ "tddj=? where guid=?";
			Object[] obj = new Object[]{
					zjbmx.getYqbh(),zjbmx.getFlh(),zjbmx.getFlmc(),zjbmx.getYqmc(),
					zjbmx.getDj(),zjbmx.getJldw(),zjbmx.getZzj(),zjbmx.getDzrrq(),	
					zjbmx.getSydw(),zjbmx.getSyr(),zjbmx.getSyfx(),zjbmx.getXz(),
					zjbmx.getJfkm(),zjbmx.getZcly(),zjbmx.getGzrq(),zjbmx.getYsdh(),
					zjbmx.getDabh(),zjbmx.getJdr(),zjbmx.getZtbz(),zjbmx.getJdrq(),
					zjbmx.getShr(),zjbmx.getShrq(),zjbmx.getShyj(),zjbmx.getRzrq(),
					zjbmx.getCz10(),zjbmx.getCz6(),zjbmx.getBmsx(),zjbmx.getBzxx(),
					zjbmx.getSccj(),zjbmx.getFjzj(),zjbmx.getFjs(),zjbmx.getGbm(),
					zjbmx.getCch(),zjbmx.getCcrq(),zjbmx.getXss(),zjbmx.getDjh(),
					zjbmx.getXh(),zjbmx.getGg(),zjbmx.getWbzl(),zjbmx.getWbje(),
					zjbmx.getJkdj(),zjbmx.getJsh(),zjbmx.getGzyq(),zjbmx.getSynx(),
					zjbmx.getHtbh(),zjbmx.getCgr(),zjbmx.getKyxm(),zjbmx.getFzr(),
					zjbmx.getZdsys(),zjbmx.getJcnf(),zjbmx.getSykh(),zjbmx.getZj(),
					zjbmx.getCzrq(),zjbmx.getPzh(),zjbmx.getJzbz(),zjbmx.getGkdw(),
					zjbmx.getGkry(),zjbmx.getGkyj(),zjbmx.getGkrq(),zjbmx.getXk(),
					zjbmx.getXklb(),zjbmx.getJzlx(),zjbmx.getXmh(),zjbmx.getCllx(),
					zjbmx.getClbz(),zjbmx.getCcly(),zjbmx.getSyxz(),zjbmx.getBz(),
					zjbmx.getCzr(),zjbmx.getFzrq(),zjbmx.getFgxs(),zjbmx.getGnmj(),
					zjbmx.getWfmj(),zjbmx.getFwyt(),zjbmx.getSymj(),zjbmx.getJsr(),
					zjbmx.getPgjz(),zjbmx.getDjrq(),zjbmx.getDjjg(),zjbmx.getZlh(),
					zjbmx.getPzwh(),zjbmx.getGljg(),zjbmx.getTjsj(),zjbmx.getJzxs(),
					zjbmx.getGlbm(),zjbmx.getLjzj(),zjbmx.getZmjz(),zjbmx.getZjjt(),
					zjbmx.getCgzzxs(),zjbmx.getCjdfdw(),zjbmx.getCzdfdw(),zjbmx.getBtzdw(),
					zjbmx.getJj(),zjbmx.getTd_qsnx(),zjbmx.getTd_qsxz(),zjbmx.getTd_fzrq(),
					zjbmx.getZmmj(),zjbmx.getTd_dh(),zjbmx.getTd_zymj(),zjbmx.getTd_cjmj(),
					zjbmx.getTd_czmj(),zjbmx.getTd_jymj(),zjbmx.getTd_xzmj(),zjbmx.getTd_qtmj(),
					zjbmx.getZyjz(),zjbmx.getCjjz(),zjbmx.getCzjz(),zjbmx.getJyjz(),
					zjbmx.getXzjz(),zjbmx.getQtjz(),zjbmx.getTd_rzkm(),zjbmx.getTd_qsnx(),
					zjbmx.getTd_yt(),zjbmx.getTd_qdjz(),zjbmx.getTd_dymj(),zjbmx.getTd_ftmj(),
					zjbmx.getFw_fgxs(),zjbmx.getCqxs(),zjbmx.getQsxz(),zjbmx.getFw_qszm(),
					zjbmx.getDxmj(),zjbmx.getDsmj(),zjbmx.getFw_gnmj(),zjbmx.getQsrq(),
					zjbmx.getFw_fzrq(),zjbmx.getFw_bgmj(),zjbmx.getFw_hymj(),zjbmx.getFw_ckmj(),
					zjbmx.getFw_stmj(),zjbmx.getFw_pdmj(),zjbmx.getFw_jfmj(),zjbmx.getFw_zymj(),
					zjbmx.getFw_jymj(),zjbmx.getFw_cjmj(),zjbmx.getFw_czmj(),zjbmx.getFw_xzmj(),
					zjbmx.getFw_qtmj(),zjbmx.getFw_zrjs(),zjbmx.getPp(),zjbmx.getSb_gl(),
					zjbmx.getBxjzrq(),zjbmx.getSb_dyjlb(),zjbmx.getJt_bzqk(),zjbmx.getJt_clcd(),
					zjbmx.getJt_cpxh(),zjbmx.getJt_pql(),zjbmx.getJt_ppxz(),zjbmx.getJt_zcnf(),
					zjbmx.getJt_cllx(),zjbmx.getJt_clzws(),zjbmx.getJt_syxz(),zjbmx.getJj_bgzylb(),
					zjbmx.getJj_sflb(),zjbmx.getWx_pgjz(),zjbmx.getWx_djrq(),zjbmx.getWx_djjg(),
					zjbmx.getWx_zlh(),zjbmx.getWx_pzwh(),zjbmx.getWx_gljg(),zjbmx.getWx_ntxe(),
					zjbmx.getWx_fmmc(),zjbmx.getZyyt(),zjbmx.getBdbz(),zjbmx.getFlgbm(),
					zjbmx.getGbmid(),zjbmx.getBdzt(),zjbmx.getTph(),zjbmx.getYt(),
					zjbmx.getRj_ppgsd(),zjbmx.getRj_lx(),zjbmx.getRj_sfzb(),zjbmx.getRj_sqxklx(),
					zjbmx.getRj_sqxkqx(),zjbmx.getRj_zdsqs(),zjbmx.getWx_kff(),zjbmx.getWx_xcfs(),
					zjbmx.getCzzj(),zjbmx.getFczzj(),zjbmx.getWwmc(),zjbmx.getCqly(),
					zjbmx.getTddj(),zjbmx.getGuid()
			};
			sqllist.add(sql);
			parlist.add(obj);
		}
		return db.batchUpdate(sqllist, parlist);
	}
	/**
	 * 删除
	 * @param 
	 * @return 
	 */
	public int doDelete(String id) {
		String sql = "delete zc_zjb_dr where guid"+CommonUtils.getInsql(id);
		Object[] obj = id.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 清空
	 * @param 
	 * @return 
	 */
	public int doDel() {
		String sql = "delete zc_zjb_dr";
		int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 入账
	 * @param id
	 * @return
	 */
	public boolean DoRz(String id){
		List sqllist = new ArrayList();
		List parlist = new ArrayList();
		String[] arrays =id.split(",");
		for (int i = 0; i < arrays.length; i++) {
			String guid = arrays[i];
			StringBuffer sqlzjb = new StringBuffer();
			sqlzjb.append("insert into zc_zjb(yqbh,flh,flmc,yqmc,dj,jldw,zzj,dzrrq,sydw,syr,syfx,xz,jfkm,zcly,"
						+ "gzrq,ysdh,dabh,jdr,ztbz,jdrq,shr,shrq,shyj,rzrq,cz10,cz6,bmsx,bzxx,sccj,fjzj,fjs,gbm,"
						+ "cch,ccrq,xss,djh,xh,gg,wbzl,wbje,jkdj,jsh,gzyq,synx,htbh,cgr,kyxm,fzr,zdsys,jcnf,sykh,"
						+ "zj,czrq,pzh,jzbz,gkdw,gkry,gkyj,gkrq,xk,xklb,jzlx,xmh,cllx,clbz,ccly,syxz,bz,czr,fzrq,"
						+ "fgxs,gnmj,wfmj,fwyt,symj,jsr,pgjz,djrq,djjg,zlh,pzwh,gljg,tjsj,jzxs,glbm,ljzj,zmjz,zjjt,"
						+ "cgzzxs,cjdfdw,czdfdw,btzdw,jj,td_qszm,td_qsxz,td_fzrq,zmmj,td_dh,td_zymj,td_cjmj,td_czmj,"
						+ "td_jymj,td_xzmj,td_qtmj,zyjz,cjjz,czjz,jyjz,xzjz,qtjz,td_rzkm,td_qsnx,td_yt,td_qdjz,td_dymj,"
						+ "td_ftmj,fw_fgxs,cqxs,qsxz,fw_qszm,dxmj,dsmj,fw_gnmj,qsrq,fw_fzrq,fw_bgmj,fw_hymj,fw_ckmj,"
						+ "fw_stmj,fw_pdmj,fw_jfmj,fw_zymj,fw_jymj,fw_cjmj,fw_czmj,fw_xzmj,fw_qtmj,fw_zrjs,pp,sb_gl,"
						+ "bxjzrq,sb_dyjlb,jt_bzqk,jt_clcd,jt_cpxh,jt_pql,jt_ppxz,jt_zcnf,jt_cllx,jt_clzws,jt_syxz,"
						+ "jj_bgzylb,jj_sflb,wx_pgjz,wx_djrq,wx_djjg,wx_zlh,wx_pzwh,wx_gljg,wx_ntxe,wx_fmmc,zyyt,"
						+ "bdbz,flgbm,gbmid,bdzt,tph,yt,rj_ppgsd,rj_lx,rj_sfzb,rj_sqxklx,rj_sqxkqx,rj_zdsqs,"
						+ "wx_kff,wx_xcfs,czzj,fczzj,wwmc,cqly,tddj)");
			sqlzjb.append("select yqbh,flh,flmc,yqmc,dj,jldw,zzj,to_date(dzrrq,'yyyy-MM-dd'),sydw,syr,syfx,xz,jfkm,zcly,to_date(gzrq,'yyyy-MM-dd'),ysdh,dabh,jdr,"
					+ "'"+StateManager.ZCJZ_CW_TG+"',to_date(jdrq,'yyyy-MM-dd'),'"+LUser.getRybh()+"',to_date('"+Constant.MR_DATE()+"','yyyy-MM-dd'),shyj,to_date(rzrq,'yyyy-MM-dd'),cz10,cz6,bmsx,bzxx,sccj,fjzj,fjs,gbm,cch,to_date(ccrq,'yyyy-MM-dd'),xss,djh,xh,gg,wbzl,"
					+ "wbje,jkdj,jsh,gzyq,synx,htbh,cgr,kyxm,fzr,zdsys,jcnf,sykh,zj,to_date(czrq,'yyyy-MM-dd'),pzh,jzbz,gkdw,gkry,gkyj,to_date(gkrq,'yyyy-MM-dd'),"
					+ "xk,xklb,jzlx,xmh,cllx,clbz,ccly,syxz,bz,czr,to_date(fzrq,'yyyy-MM-dd'),fgxs,gnmj,wfmj,fwyt,symj,jsr,pgjz,to_date(djrq,'yyyy-MM-dd'),djjg,"
					+ "zlh,pzwh,gljg,tjsj,jzxs,glbm,ljzj,zmjz,zjjt,cgzzxs,cjdfdw,czdfdw,btzdw,jj,td_qszm,td_qsxz,"
					+ "to_date(td_fzrq,'yyyy-MM-dd'),zmmj,td_dh,td_zymj,td_cjmj,td_czmj,td_jymj,td_xzmj,td_qtmj,zyjz,cjjz,czjz,jyjz,xzjz,"
					+ "qtjz,td_rzkm,td_qsnx,td_yt,td_qdjz,td_dymj,td_ftmj,fw_fgxs,cqxs,qsxz,fw_qszm,dxmj,dsmj,fw_gnmj,"
					+ "to_date(qsrq,'yyyy-MM-dd'),to_date(fw_fzrq,'yyyy-MM-dd'),fw_bgmj,fw_hymj,fw_ckmj,fw_stmj,fw_pdmj,fw_jfmj,fw_zymj,fw_jymj,fw_cjmj,fw_czmj,"
					+ "fw_xzmj,fw_qtmj,fw_zrjs,pp,sb_gl,to_date(bxjzrq,'yyyy-MM-dd'),sb_dyjlb,jt_bzqk,jt_clcd,jt_cpxh,jt_pql,jt_ppxz,jt_zcnf,"
					+ "jt_cllx,jt_clzws,jt_syxz,jj_bgzylb,jj_sflb,wx_pgjz,to_date(wx_djrq,'yyyy-MM-dd'),wx_djjg,wx_zlh,wx_pzwh,wx_gljg,wx_ntxe,"
					+ "wx_fmmc,zyyt,bdbz,flgbm,gbmid,bdzt,tph,yt,rj_ppgsd,rj_lx,rj_sfzb,rj_sqxklx,rj_sqxkqx,rj_zdsqs,"
					+ "wx_kff,wx_xcfs,czzj,fczzj,wwmc,cqly,tddj from zc_zjb_dr where guid=?");
			sqllist.add(sqlzjb.toString());
			parlist.add(new Object[]{guid});
			String sql = "delete zc_zjb_dr where guid=?";
			sqllist.add(sql);
			parlist.add(new Object[]{guid});
		}
		try {
			return db.batchUpdate(sqllist, parlist);
		} catch (Exception e) {
			return false;
		}
		
	}
	/**
	 * 验证
	 * @param zjb
	 * @return
	 */
	public String docheck(ZC_ZJB_DRMX zjb){
		List<sjdr_zjb> list = zjb.getZjbmx();
		StringBuffer msg = new StringBuffer();
		for(int i=0;i<list.size();i++){
			sjdr_zjb zjbmx = list.get(i);
			String yqbh = zjbmx.getYqbh();
			String flh = zjbmx.getFlh();
			String syr = zjbmx.getSyr();
			String sydw = zjbmx.getSydw();
			String bzxx = zjbmx.getBzxx();
			String sqlsyr = "select count(*) from gx_sys_ryb where '('||rygh||')'||xm=?";
			if("0".equals(db.queryForSingleValue(sqlsyr,new Object[]{syr}))){
				msg.append("使用人编码错误，");
			}
			String sqlsydw = "select count(*) from gx_sys_dwb where '('||bmh||')'||mc=?";
			if("0".equals(db.queryForSingleValue(sqlsydw,new Object[]{sydw}))){
				msg.append("使用单位编码错误，");
			}
			String mbh = CommonUtil.GetMbhByFlh(flh);
			if(Validate.noNull(mbh) && !"01".equals(mbh) && !"02".equals(mbh) && !"03".equals(mbh)){
				String sqlbzxx = "select count(*) from zc_sys_ddb where '('||ddh||')'||mc=?";
				if("0".equals(db.queryForSingleValue(sqlbzxx,new Object[]{bzxx}))){
					msg.append("存放地点编码错误，");
				}
			}
			if(msg.length()>0){
				return "{\"success\":false,\"msg\":\"资产编号："+yqbh+","+msg+""+"请仔细核对！</br>\"}";
			}
			msg = null;
		}
		return "{\"success\":true,\"msg\":\"验证通过！\"}";
	}
	/**
	 * 审核验证
	 * @param zjb
	 * @return
	 */
	public String doShcheck(String type,String id,String tj){
		String value="";
		StringBuffer msg = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("select A.yqbh,A.yqmc,A.flh,A.flmc,A.dj,A.zzj,A.syfx,A.jldw,A.xz,A.jfkm,A.zcly,A.jzlx,A.jzxs,");
		sql.append("sydw, syr,A.sccj,cgr, bzxx,A.czr,A.dzrrq,A.gzrq,A.tph,A.rzrq,A.zjjt,A.ljzj,A.cgzzxs,A.xmh,A.pzh,A.cqly,");
		sql.append("A.ccrq,A.qsrq,A.synx,A.fw_fzrq,A.tddj,A.htbh,A.xss,A.qsxz,A.fgxs,A.cqxs,a.td_qsnx,");
		sql.append("A.fw_qszm,A.jsh,A.zmmj,A.jcnf,A.fwyt,A.zj,A.dsmj,A.dxmj,A.fw_zrjs,A.yt,A.gzyq,A.fjzj,");
		sql.append("A.fw_zymj,A.zyjz,A.xh,A.fw_cjmj,A.wbje,A.fw_czmj,A.czjz,A.fw_gnmj,A.fw_jymj,A.symj,xk,");
		sql.append("A.fw_xzmj,A.xzjz,A.cch,A.fw_qtmj,A.td_qtmj,A.qtjz,A.jkdj,A.zyyt,A.bz,A.xklb,A.td_rzkm,A.td_qszm,");
		sql.append("A.td_qsxz,A.djh,A.td_dymj,A.td_fzrq,A.td_ftmj,A.td_yt,");
		sql.append("A.td_zymj,A.td_czmj,A.td_cjmj,A.cjjz,A.td_jymj,A.jyjz,A.gg,A.fjs,A.wbzl,A.fzr,gbm,");
		sql.append("A.sykh,A.dabh,A.kyxm,A.zdsys,A.bxjzrq,A.pp,A.jt_pql,A.jt_clcd,A.jt_bzqk,A.jt_syxz,A.syxz,A.fzrq,");
		sql.append("A.wx_pgjz,A.wx_djjg,A.wx_djrq,A.wx_zlh,A.wx_pzwh, A.wx_gljg,A.wx_fmmc,A.wx_ntxe,A.sb_gl ");
		sql.append(" from zc_zjb_dr A where 1=1");
		switch(type){
			case "ALL":
				sql.append("");
			break;	
			case "FW":
				sql.append(" and substr(A.flh,1,4) = '0101'");
			break;
			case "GZW":
				sql.append(" and substr(A.flh,1,4) = '0102'");
			break;
			case "TD":
				sql.append(" and substr(A.flh,1,4) = '0201'");
			break;
			case "PLANT":
				sql.append(" and substr(A.flh,1,4) = '0202'");
			break;
			case "CAR":
				sql.append(" and substr(A.flh,1,4) = '0413'");
			break;
			case "PTSB":
				sql.append(" and substr(A.flh,1,2) in ('03','04','05','06','07','08','09','12','14','15')");
				sql.append(" and substr(A.flh,1,4) <> '0413'");
			break;
			case "WW":
				sql.append(" and substr(A.flh,1,2) = '10'");
			break;
			case "BOOKS":
				sql.append(" and substr(A.flh,1,2) = '11'");
			break;
			case "JJ":
				sql.append(" and substr(A.flh,1,2) = '13'");
			break;
			case "ANIMALS":
				sql.append(" and substr(A.flh,1,2) = '16'");
			break;
			case "WXZC":
				sql.append(" and substr(A.flh,1,2) = '17'");
			break;
			default:
                break;
		}
		if(Validate.noNull(id)){
			StringBuffer in = new StringBuffer();
			String[] arrays =id.split(",");
			for (int i = 0; i < arrays.length; i++) {
				in.append(arrays[i]+"','");
			}
			String guid = in.substring(0,in.length()-3);
			sql.append(" and guid in ('"+guid+"')");
		}
		sql.append(" order by yqbh asc,guid asc");
		List list = db.queryForList(sql+"");
		if(list.size()>0){
			String regex="^[a-zA-Z0-9]+$";//数字或字母组成
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map)list.get(i);
				String yqbh = map.get("YQBH")+"";
				String yqmc = map.get("YQMC")+"";
				String flh = map.get("FLH")+"";
				String flmc = map.get("FLMC")+"";
				String syr = map.get("SYR")+"";
				String sydw = map.get("SYDW")+"";
				String syfx = map.get("SYFX")+"";
				String gzrq = map.get("GZRQ")+"";
				String jldw = map.get("JLDW")+"";
				String xz = map.get("XZ")+"";
				String zcly = map.get("ZCLY")+"";
				String jfkm = map.get("JFKM")+"";
				String rzrq = map.get("RZRQ")+"";
				String jzlx = map.get("JZLX")+"";
				String cgzzxs = map.get("CGZZXS")+"";
				//资产编号
				if(Validate.isNull(map.get("YQBH"))){
					msg.append("资产编号不能为空，");
				}else{
					String sqlyqbh = "select count(*) from zc_zjb where yqbh=?";
					if(Validate.noNull(tj)&&"1".equals(tj)){
						String sqltj = "select yqbh from zc_zjb_dr k where k.yqbh=? group by yqbh having count(*)>1";
						if(Validate.noNull(db.queryForSingleValue(sqltj,new Object[]{yqbh}))){
							msg.append("资产编号("+yqbh+")重复，");
						}
					}else{
						if(Integer.valueOf(db.queryForSingleValue(sqlyqbh,new Object[]{yqbh}))>0){
							msg.append("资产编号("+yqbh+")已存在，");
						}
					}
					Pattern pattern = Pattern.compile(regex);
					 Matcher match=pattern.matcher(yqbh);
					//验证yqbh由数字或字母组成
					if(!match.matches()){
						msg.append("资产编号("+yqbh+")必须由数字或字母组成，");
					}
				}
				//资产名称
				if(Validate.isNull(yqmc)){	
					msg.append("资产名称不能为空，");
				}
				//分类号
				if(Validate.isNull(flh)){
					msg.append("分类号不能为空，");
				}else{
					String sqlflh = "select count(*) from zc_flb_jyb where (zc_flb_jyb.flh=?)";
					if(Integer.valueOf(db.queryForSingleValue(sqlflh,new Object[]{flh}))<=0){
						msg.append("分类号("+flh+")不存在，");
					}
				}
				//分类名称
				if(Validate.isNull(map.get("FLMC"))){
					msg.append("分类名称不能为空，");
				}else{
					String sql2 = "select count(1) from ZC_FLB_JYB k where flh=? and flmc='"+flmc+""+"'";
					Integer result =db.queryForObject(sql2, new Object[]{flh+""},Integer.class);
					if(result<=0){
						msg.append("分类号与分类名称("+flmc+")不相符，");
					}
				}
				//价值类型
				if(!"ALL".equals(type)){
					String jzxs = map.get("JZXS")+"";
					//现状
					if(Validate.noNull(jzxs)){
						String sqljzxs = "select count(*) from gx_sys_dmb where zl='"+Constant.JZXS+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqljzxs,new Object[]{jzxs}))<=0){
							msg.append("价值类型("+jzxs+")不存在，");
						}
					}else{
						msg.append("价值类型不能为空，");
					}
				}
				//使用人
				if(Validate.noNull(syr)){
					String sqlsyr = "select count(*) from gx_sys_ryb where (rybh=? or xm=?)";
					if(Integer.valueOf(db.queryForSingleValue(sqlsyr,new Object[]{syr,syr}))<=0){
						msg.append("使用人("+syr+")不存在，");
					}
				}else{
					msg.append("使用人不能为空，");
				}
				//使用单位
				if(Validate.noNull(sydw)){
					String sqlsydw = "select count(*) from gx_sys_dwb where (bmh=? or mc=?)";
					if(Integer.valueOf(db.queryForSingleValue(sqlsydw,new Object[]{sydw,sydw}))<=0){
						msg.append("使用单位("+sydw+")不存在，");
					}
				}else{
					msg.append("使用单位不能为空，");
				}
				//使用方向
				if(Validate.noNull(syfx)){
					String sqlsyfx = "select count(*) from gx_sys_dmb where zl='05' and dm=?";
					if(Integer.valueOf(db.queryForSingleValue(sqlsyfx,new Object[]{syfx}))<=0){
						msg.append("使用方向("+syfx+")不存在，");
					}
				}else{
					msg.append("使用方向不能为空，");
				}
				//存放地点
				String mbh = CommonUtil.GetMbhByFlh(flh);
				if(Validate.noNull(mbh) && !"01".equals(mbh) && !"02".equals(mbh) && !"03".equals(mbh)){
					String bzxx = map.get("BZXX")+"";
					String sqlbzxx = "select count(*) from zc_sys_ddb where (ddh=? or mc=?)";
					if(Validate.isNull(map.get("BZXX")+"")){
						msg.append("存放地点不能为空，");
					}else if(Integer.valueOf(db.queryForSingleValue(sqlbzxx,new Object[]{bzxx,bzxx}))<=0){
						msg.append("存放地点("+bzxx+")不存在，");
					}
				}
				//购置日期
				if(Validate.noNull(gzrq)){
					msg.append(getmsg("gzrq",type,gzrq));
				}else{
					msg.append("购置日期不能为空，");
				}
				//计量单位
				if(Validate.noNull(jldw)){
					String sqljldw = "select count(*) from gx_sys_dmb where zl='"+Constant.JLDW+"' and dm=?";
					if(Integer.valueOf(db.queryForSingleValue(sqljldw,new Object[]{jldw}))<=0){
						msg.append("计量单位("+jldw+")不存在，");
					}
				}else{
					msg.append("计量单位不能为空，");
				}
				//现状
				if(Validate.noNull(xz)){
					String sqlxz = "select count(*) from gx_sys_dmb where zl='"+Constant.XZ+"' and dm=?";
					if(Integer.valueOf(db.queryForSingleValue(sqlxz,new Object[]{xz}))<=0){
						msg.append("现状("+xz+")不存在，");
					}
				}else{
					msg.append("现状不能为空，");
				}
				//资产来源
				if(Validate.noNull(zcly)){
					String sqlzcly = "select count(*) from gx_sys_dmb where zl='"+Constant.ZCLY+"' and dm=?";
					if(Integer.valueOf(db.queryForSingleValue(sqlzcly,new Object[]{zcly}))<=0){
						msg.append("资产来源("+zcly+")不存在，");
					}
				}else{
					msg.append("资产来源不能为空，");
				}
				//经费来源
				if(Validate.noNull(jfkm)){
					String sqljfkm = "select count(*) from gx_sys_dmb where zl='"+Constant.JFKM+"' and dm=?";
					if(Integer.valueOf(db.queryForSingleValue(sqljfkm,new Object[]{jfkm}))<=0){
						msg.append("经费来源("+jfkm+")不存在，");
					}
				}else{
					msg.append("经费来源不能为空，");
				}
				//入账日期
				if(Validate.noNull(rzrq)){
					msg.append(getmsg("rzrq",type,rzrq));
				}else{
					msg.append("入账日期不能为空，");
				}
				//记账类型
				if(Validate.noNull(jzlx)){
					String sqljzlx = "select count(*) from gx_sys_dmb where zl='"+Constant.JZLX+"' and dm=?";
					if(Integer.valueOf(db.queryForSingleValue(sqljzlx,new Object[]{jzlx}))<=0){
						msg.append("记账类型("+jzlx+")不存在，");
					}
				}else{
					msg.append("记账类型不能为空，");
				}
				//采购组织形式
				if(Validate.noNull(cgzzxs)){
					String sqlcgzzxs = "select count(*) from gx_sys_dmb where zl='"+Constant.CGZZXS+"' and dm=?";
					if(Integer.valueOf(db.queryForSingleValue(sqlcgzzxs,new Object[]{cgzzxs}))<=0){
						msg.append("采购组织形式("+cgzzxs+")不存在，");
					}
				}else{
					msg.append("采购组织形式不能为空，");
				}
				
				if(!"ALL".equals(type)){
					//单价
					String dj = map.get("DJ")+"";
					if(Validate.noNull(dj)){
						msg.append(getmsg("dj",type,dj));
					}
					//总价
					String zzj = map.get("ZZJ")+"";
					if(Validate.noNull(zzj)){
						msg.append(getmsg("zzj",type,zzj));
					}
					//折旧状态
					String zjjt = map.get("ZJJT")+"";
					if(Validate.noNull(zjjt)){
						String sqlzjjt = "select count(*) from gx_sys_dmb where zl='"+Constant.ZJJT+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlzjjt,new Object[]{zjjt}))<=0){
							msg.append("折旧状态("+zjjt+")不存在，");
						}
					}
					//累计折旧
					String Ljzj = map.get("LJZJ")+"";
					if(Validate.noNull(Ljzj)){
						msg.append(getmsg("Ljzj",type,Ljzj));
					}
					//调转入日期
					String dzrrq = map.get("DZRRQ")+"";
					if(Validate.noNull(dzrrq)){
						msg.append(getmsg("dzrrq",type,dzrrq));
					}
					//竣工日期,出版日期,出厂日期
					String ccrq = map.get("CCRQ")+"";
					if(Validate.noNull(ccrq)){
						msg.append(getmsg("ccrq",type,ccrq));
					}
					//投入使用日期
					String qsrq = map.get("QSRQ")+"";
					if(Validate.noNull(qsrq)){
						msg.append(getmsg("qsrq",type,qsrq));
					}
					//预计使用年限,预计寿命
					String synx = map.get("SYNX")+"";
					if(Validate.noNull(synx)){
						msg.append(getmsg("synx",type,synx));
					}
					//年龄，平面图数
					String tph = map.get("TPH")+"";
					if(Validate.noNull(tph)){
						msg.append(getmsg("tph",type,tph));
					}
					//完损状况
					String tddj = map.get("TDDJ")+"";
					if(Validate.noNull(tddj)&&("FW".equals(type))){
						String sqltddj = "select count(*) from gx_sys_dmb where zl='"+Constant.TDDJ+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqltddj,new Object[]{tddj}))<=0){
							msg.append("完损状况("+tddj+")不存在，");
						}
					}
					//权属性质
					String qsxz = map.get("QSXZ")+"";
					if(Validate.noNull(qsxz)&&("FW".equals(type)||"GZW".equals(type))){
						String sqlqsxz = "select count(*) from gx_sys_dmb where zl='"+Constant.QSXZ+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlqsxz,new Object[]{qsxz}))<=0){
							msg.append("权属性质("+qsxz+")不存在，");
						}
					}
					//房管形式
					String fgxs = map.get("FGXS")+"";
					if(Validate.noNull(fgxs)&&("FW".equals(type)||"GZW".equals(type))){
						String sqlfgxs = "select count(*) from gx_sys_dmb where zl='"+Constant.FGXS+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlfgxs,new Object[]{fgxs}))<=0){
							msg.append("房管形式("+fgxs+")不存在，");
						}
					}
					//产权形式
					String cqxs = map.get("CQXS")+"";
					if(Validate.noNull(cqxs)&&("FW".equals(type)||"GZW".equals(type))){
						String sqlcqxs = "select count(*) from gx_sys_dmb where zl='"+Constant.CQXS+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlcqxs,new Object[]{cqxs}))<=0){
							msg.append("产权形式("+cqxs+")不存在，");
						}
					}
					//发证日期
					String fw_fzrq = map.get("FW_FZRQ")+"";
					if(Validate.noNull(fw_fzrq)){
						msg.append(getmsg("fw_fzrq",type,fw_fzrq));
					}
					//证载面积,使用权面积
					String zmmj = map.get("ZMMJ")+"";
					if(Validate.noNull(zmmj)){
						msg.append(getmsg("zmmj",type,zmmj));
					}
					//建成年份,栽种年份,出生年份
					String jcnf = map.get("JCNF")+"";
					if(Validate.noNull(jcnf)){
						msg.append(getmsg("jcnf",type,jcnf));
					}
					//建筑类型
					String zj = map.get("ZJ")+"";
					if(Validate.noNull(zj)&&("FW".equals(type)||"GZW".equals(type))){
						String sqlzj = "select count(*) from gx_sys_dmb where zl='"+Constant.ZJ+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlzj,new Object[]{zj}))<=0){
							msg.append("建筑类型("+zj+")不存在，");
						}
					}
					//地上面积
					String dsmj = map.get("DSMJ")+"";
					if(Validate.noNull(dsmj)){
						msg.append(getmsg("dsmj",type,dsmj));
					}
					//地下面积
					String dxmj = map.get("DXMJ")+"";
					if(Validate.noNull(dxmj)){
						msg.append(getmsg("dxmj",type,dxmj));
					}
					//自然间数
					String fw_zrjs = map.get("FW_ZRJS")+"";
					if(Validate.noNull(fw_zrjs)){
						msg.append(getmsg("fw_zrjs",type,fw_zrjs));
					}
					//建筑面积,实际面积
					String fjzj = map.get("FJZJ")+"";
					if(Validate.noNull(fjzj)){
						msg.append(getmsg("fjzj",type,fjzj));
					}
					//自用面积
					String fw_zymj = map.get("FW_ZYMJ")+"";
					if(Validate.noNull(fw_zymj)){
						msg.append(getmsg("fw_zymj",type,fw_zymj));
					}
					
					//自用价值
					String zyjz = map.get("ZYJZ")+"";
					if(Validate.noNull(zyjz)){
						msg.append(getmsg("zyjz",type,zyjz));
					}
					
					//型号，建筑结构，藏品年代
					String xh = map.get("XH")+"";
					if(Validate.noNull(xh)){
						if("FW".equals(type)){
							String sqlxh = "select count(*) from gx_sys_dmb where zl='"+Constant.XH+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlxh,new Object[]{xh}))<=0){
								msg.append("建筑结构("+xh+")不存在，");
							}
						}else if("WW".equals(type)){
							msg.append(getmsg("xh",type,xh));
						}
					}
					//出借面积,
					String fw_cjmj = map.get("FW_CJMJ")+"";
					if(Validate.noNull(fw_cjmj)){
						msg.append(getmsg("fw_cjmj",type,fw_cjmj));
					}
					//外币单价,占地面积,行车里程
					String wbje = map.get("WBJE")+"";
					if(Validate.noNull(wbje)){
						msg.append(getmsg("wbje",type,wbje));
					}
					//出租面积
					String fw_czmj = map.get("FW_CZMJ")+"";
					if(Validate.noNull(fw_czmj)){
						msg.append(getmsg("fw_czmj",type,fw_czmj));
					}
					//出租价值
					String czjz = map.get("CZJZ")+"";
					if(Validate.noNull(czjz)){
						msg.append(getmsg("czjz",type,czjz));
					}
					//供暖面积
					String fw_gnmj = map.get("FW_GNMJ")+"";
					if(Validate.noNull(fw_gnmj)){
						msg.append(getmsg("fw_gnmj",type,fw_gnmj));
					}
					//对外投资面积
					String fw_jymj = map.get("FW_JYMJ")+"";
					if(Validate.noNull(fw_jymj)){
						msg.append(getmsg("fw_jymj",type,fw_jymj));
					}
					//使用面积
					String symj = map.get("SYMJ")+"";
					if(Validate.noNull(symj)){
						msg.append(getmsg("symj",type,symj));
					}
					//担保面积
					String fw_xzmj = map.get("FW_XZMJ")+"";
					if(Validate.noNull(fw_xzmj)){
						msg.append(getmsg("fw_xzmj",type,fw_xzmj));
					}
					//担保价值
					String xzjz = map.get("XZJZ")+"";
					if(Validate.noNull(xzjz)){
						msg.append(getmsg("xzjz",type,xzjz));
					}
					//其他面积
					String fw_qtmj = map.get("FW_QTMJ")+"";
					if(Validate.noNull(fw_qtmj)){
						msg.append(getmsg("fw_qtmj",type,fw_qtmj));
					}
					String td_qtmj = map.get("TD_QTMJ")+"";
					if(Validate.noNull(td_qtmj)){
						msg.append(getmsg("td_qtmj",type,td_qtmj));
					}
					//其他价值
					String qtjz = map.get("QTJZ")+"";
					if(Validate.noNull(qtjz)){
						msg.append(getmsg("qtjz",type,qtjz));
					}
					//进口总价
					String jkdj = map.get("jkdj")+"";
					if(Validate.noNull(jkdj)){
						msg.append(getmsg("jkdj",type,jkdj));
					}
					//学科类别
					String xklb = map.get("XKLB")+"";
					if(Validate.noNull(xklb)){
						String sqlxklb= "select count(*) from gx_sys_dmb where zl='"+Constant.XKLB+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlxklb,new Object[]{xklb}))<=0){
							msg.append("学科类别("+xklb+")不存在，");
						}
					}
					//学科
					String xk = map.get("XK")+"";
					if(Validate.noNull(xk)){
						String sqlxk = "select count(*) from gx_sys_dmb d where zl='"+Constant.XKML+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlxk,new Object[]{xk}))<=0){
							msg.append("学科("+xk+")不存在，");
						}
					}
					//入账科目
					String td_rzkm = map.get("TD_RZKM")+"";
					if(Validate.noNull(td_rzkm)){
						String sqltd_rzkm = "select count(*) from gx_sys_dmb d where zl='"+Constant.TD_RZKM+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqltd_rzkm,new Object[]{td_rzkm}))<=0){
							msg.append("入账科目("+td_rzkm+")不存在，");
						}
					}
					//权属年限
					String td_qsnx = map.get("TD_QSNX")+"";
					if(Validate.noNull(td_qsnx)){
						msg.append(getmsg("td_qsnx",type,td_qsnx));
					}
					//权属性质
					String td_qsxz = map.get("TD_QSXZ")+"";
					if(Validate.noNull(td_qsxz)){
						String sqltd_qsxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.TD_QSXZ+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqltd_qsxz,new Object[]{td_qsxz}))<=0){
							msg.append("权属性质("+td_qsxz+")不存在，");
						}
					}
					//独用面积
					String td_dymj = map.get("TD_DYMJ")+"";
					if(Validate.noNull(td_dymj)){
						msg.append(getmsg("td_dymj",type,td_dymj));
					}
					//发证日期
					String td_fzrq = map.get("TD_FZRQ")+"";
					if(Validate.noNull(td_fzrq)){
						msg.append(getmsg("td_fzrq",type,td_fzrq));
					}
					//分摊面积
					String td_ftmj = map.get("TD_FTMJ")+"";
					if(Validate.noNull(td_ftmj)){
						msg.append(getmsg("td_ftmj",type,td_ftmj));
					}
					//自用面积
					String td_zymj = map.get("TD_ZYMJ")+"";
					if(Validate.noNull(td_zymj)){
						msg.append(getmsg("td_zymj",type,td_zymj));
					}
					//出租面积
					String td_czmj = map.get("TD_CZMJ")+"";
					if(Validate.noNull(td_czmj)){
						msg.append(getmsg("td_czmj",type,td_czmj));
					}
					//出借面积
					String td_cjmj = map.get("TD_CJMJ")+"";
					if(Validate.noNull(td_cjmj)){
						msg.append(getmsg("td_cjmj",type,td_cjmj));
					}
					//出借价值
					String cjjz = map.get("CJJZ")+"";
					if(Validate.noNull(cjjz)){
						msg.append(getmsg("cjjz",type,cjjz));
					}
					//对外投资面积
					String td_jymj = map.get("TD_JYMJ")+"";
					if(Validate.noNull(td_jymj)){
						msg.append(getmsg("td_jymj",type,td_jymj));
					}
					//对外投资价值
					String jyjz = map.get("JYJZ")+"";
					if(Validate.noNull(jyjz)){
						msg.append(getmsg("jyjz",type,jyjz));
					}
					//栽种树龄
					String fjs = map.get("FJS")+"";
					if(Validate.noNull(fjs)){
						msg.append(getmsg("fjs",type,fjs));
					}
					//外币种类
					String wbzl = map.get("WBZL")+"";
					if(Validate.noNull(wbzl)){
						String sqlwbzl= "select count(*) from gx_sys_dmb d where zl='"+Constant.WBZL+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlwbzl,new Object[]{wbzl}))<=0){
							msg.append("外币种类("+wbzl+")不存在，");
						}
					}
					//采购人
					String cgr = map.get("CGR")+"";
					if(Validate.noNull(cgr)){
						String sqlcgr = "select count(*) from gx_sys_ryb where (rybh=? or xm=?)";
						if(Integer.valueOf(db.queryForSingleValue(sqlcgr,new Object[]{cgr,cgr}))<=0){
							msg.append("采购人("+cgr+")不存在，");
						}
					}
					//图书册数
					String sykh = map.get("SYKH")+"";
					if(Validate.noNull(sykh)){
						msg.append(getmsg("sykh",type,sykh));
					}
					//国别码
					String gbm = map.get("GBM")+"";
					if(Validate.noNull(gbm)){
						String sqlgbm= "select count(*) from gx_sys_dmb d where zl='"+Constant.GB+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlgbm,new Object[]{gbm}))<=0){
							msg.append("国别码("+gbm+")不存在，");
						}
					}
					//保修截止日期
					String bxjzrq = map.get("BXJZRQ")+"";
					if(Validate.noNull(bxjzrq)){
						msg.append(getmsg("bxjzrq",type,bxjzrq));
					}
					//排气量
					String jt_pql = map.get("JT_PQL")+"";
					if(Validate.noNull(jt_pql)){
						msg.append(getmsg("jt_pql",type,jt_pql));
					}
					//编制情况
					String jt_clbz = map.get("JT_CLBZ")+"";
					if(Validate.noNull(jt_clbz)){
						String sqljt_clbz= "select count(*) from gx_sys_dmb d where zl='"+Constant.CLBZ+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqljt_clbz,new Object[]{jt_clbz}))<=0){
							msg.append("编制情况("+jt_clbz+")不存在，");
						}
					}
					//使用性质
					String jt_syxz = map.get("JT_SYXZ")+"";
					if(Validate.noNull(jt_syxz)){
						String sqljt_syxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.JT_SYXZ+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqljt_syxz,new Object[]{jt_syxz}))<=0){
							msg.append("使用性质("+jt_syxz+")不存在，");
						}
					}
					//车辆用途
					String syxz = map.get("SYXZ")+"";
					if(Validate.noNull(syxz)){
						String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.SYXZ+"' and dm=?";
						if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{syxz}))<=0){
							msg.append("车辆用途("+syxz+")不存在，");
						}
					}
					//持证日期,授权公告日
					String fzrq = map.get("FZRQ")+"";
					if(Validate.noNull(fzrq)){
						msg.append(getmsg("fzrq",type,fzrq));
					}
					//评估价值
					String wx_pgjz = map.get("WX_PGJZ")+"";
					if(Validate.noNull(wx_pgjz)){
						msg.append(getmsg("wx_pgjz",type,wx_pgjz));
					}
					//注册登记日期
					String wx_djrq = map.get("WX_DJRQ")+"";
					if(Validate.noNull(wx_djrq)){
						msg.append(getmsg("wx_djrq",type,wx_djrq));
					}
					//管理机构
					String wx_gljg = map.get("WX_GLJG")+"";
					if(Validate.noNull(wx_gljg)){
						String sqlwx_gljg = "select count(*) from gx_sys_dwb where (bmh=? or mc=?)";
						if(Integer.valueOf(db.queryForSingleValue(sqlwx_gljg,new Object[]{wx_gljg,wx_gljg}))<=0){
							msg.append("管理机构("+wx_gljg+")不存在，");
						}
					}
					//年摊销额
					String wx_ntxe = map.get("WX_NTXE")+"";
					if(Validate.noNull(wx_ntxe)){
						msg.append(getmsg("wx_ntxe",type,wx_ntxe));
					}
					//功率
					String sb_gl = map.get("SB_GL")+"";
					if(Validate.noNull(sb_gl)){
						msg.append(getmsg("sb_gl",type,sb_gl));
					}
				}
				if(msg.length()>0){
					value = value+"第"+(i+1)+"行   【资产编号："+yqbh+"】："+msg+"<b style='color:red;'>请仔细核对Excel表格数据！</b></br>";
					msg.delete(0, msg.length());
				}
			}
		}
		if(value.length()>0){
			return "{\"success\":false,\"msg\":\""+value+"\"}";
		}else{
			return "{\"success\":true,\"msg\":\"验证通过！\"}";
		}
	}
	public String getmsg(String name,String type,String value){
		String sql = "select fieldtype,fieldms,fieldlength from zc_importcheck where checkrule='0'"
				+ " and lxtype=? and fieldname=?";
		Map map = db.queryForMap(sql,new Object[]{type,name});
		StringBuffer msg = new StringBuffer();
		if(map !=null && map.size()>0){
			String dtype = map.get("FIELDTYPE")+"";
			String dms = map.get("FIELDMS")+"";
			if("varchar".equals(dtype)){
				int dlength = (int) map.get("FIELDLENGTH");
				if(value.length()>dlength){
					msg.append(""+dms+"("+value+")字节长度大于"+dlength+",");
				}
			}else if("int".equals(dtype)){
				if(!isIntric(value)){
					msg.append(""+dms+"("+value+")数据格式错误,");
				}
			}else if("double".equals(dtype)){
				if(!isNumeric(value)){
					msg.append(""+dms+"("+value+")数据格式错误,");
				}
			}else if("datetime".equals(dtype)){
				if(!isValidDate(value)){
					msg.append(""+dms+"("+value+")数据格式错误,");
				}
			}else if("year".equals(dtype)){
				if(!isValidYear(value)){
					msg.append(""+dms+"("+value+")数据格式错误,");
				}
			}
		}
		
		return msg+"";
	}
	//验证纯数字
	public static boolean isIntric(String str){ 
		 Pattern pattern = Pattern.compile("[\\d]+"); 
		 return pattern.matcher(str).matches(); 
	} 
	//验证数字 
	public static boolean isNumeric(String str){ 
		Pattern pattern = Pattern.compile("^([1-9]\\d*|0)(\\.\\d{1,})?$");
		return pattern.matcher(str).matches(); 
	}
	//验证日期
	public static boolean isValidDate(String s){
		 try{
			 new SimpleDateFormat("yyyy-MM-dd").parse(s);
			 return true;
		 }catch (Exception e){
			 return false;
		 }
	 }
	//验证年份
	public static boolean isValidYear(String s){
		Pattern pattern = Pattern.compile("^(19|20)\\d{2}$");
		return pattern.matcher(s).matches(); 
	 }
}