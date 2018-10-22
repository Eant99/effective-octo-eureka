package com.googosoft.modules.app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.base.PageDao;
import com.googosoft.dao.system.shenhe.ShenheDao;
import com.googosoft.dao.systemset.qxgl.GlqxbDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.system.ZC_XGWD;
import com.googosoft.pojo.zcjz.ZC_ZJBEXTEND;
import com.googosoft.pojo.zcjz.zc_zjb;
import com.googosoft.service.system.index.DeskService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.Const;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.security.EncryptUtils;


@Repository("phoneDao")
public class PhoneDao extends BaseDao{
	private Logger logger = Logger.getLogger(PhoneDao.class);
	
	@Resource(name="pageDao")
	public PageDao pageDao;
	
	@Resource(name="deskService")
	private DeskService deskService;
	
	@Resource(name="glqxbDao")
	public GlqxbDao glqxbDao;
	
	@Resource(name="shenheDao")
	public ShenheDao shenheDao;
	
	/**
	 * 根据人员工号查询人员信息
	 * @param rygh
	 * @return
	 */
	public Map findUser(String rygh){
		String sql = "select (case r.xb when '1' then '男' when '0' then '女' else ''end)xbmc,r.*,d.bmh,d.mc from (select * from gx_sys_ryb r where rygh = ? or xm=?) r left join gx_sys_dwb d on r.dwbh = d.dwbh";
		Map emptyMm = db.queryForMap(sql,new Object[]{rygh,rygh});
		return emptyMm;
	}
	public List gettzgglistPagelist(int index,int page_length) {
		String sqlstr = "select gid, title, sfzs, (select '(' || rygh || ')' || xm from GX_SYS_ryb where rybh = fbr) AS FBR, " + 
				"         xx, to_char(fbsj, 'yyyy-mm-dd') as fbsj,rownum xh from ZC_SYS_xtxx where 1=1 ";
		String startnum = ((index-1)*page_length)+1+"";
		String endnum = (index*page_length)+"";
		String sql = "select * from (select a1.*, rownum rn from ("+sqlstr+") a1 where rownum <= "+endnum+") where rn >= "+startnum+" ";
		
		return db.queryForList(sql);
	}
	public int checkCzqx(String mkbh, String rybh) {
		String sql = " SELECT count(1) FROM GX_SYS_RYB T WHERE T.RYBH IN(SELECT RYBH FROM ZC_SYS_CZQXB WHERE MKBH='"+mkbh+"')and t.rybh = '"+rybh+"' ";
		return db.queryForInt(sql);
	}
	public List get3tzgglistPagelist() {
		String sqlstr = "select gid, title, sfzs, (select '(' || rygh || ')' || xm from GX_SYS_ryb where rybh = fbr) AS FBR, " + 
				"         xx, to_char(fbsj, 'yyyy-mm-dd') as fbsj,rownum xh from ZC_SYS_xtxx where 1=1 and rownum <4";
		return db.queryForList(sqlstr);
	}
	public List get3xmmclist(String userId) {
//		String sqlstr = "select gid, title, sfzs, (select '(' || rygh || ')' || xm from GX_SYS_ryb where rybh = fbr) AS FBR, " + 
//				"         xx, to_char(fbsj, 'yyyy-mm-dd') as fbsj,rownum xh from ZC_SYS_xtxx where 1=1 and rownum <4";
		String sqlstr = " select  guid,xmbh,xmmc,xmfzr,ysje,syje,to_char((nvl(zcje1,0)+nvl(zcje2,0)),'FM999999999999990.00')as zcje,"
				+ "to_char((nvl(dshje1,0)+nvl(dshje2,0)),'FM999999999999990.00')as dshje  from  (select distinct guid, xmbh,xmmc,(select '('||RYBH||')'|| xm from gx_sys_ryb ry where ry.rybh=A.fzr) as xmfzr,to_char(ysje,'FM999999999990.00') as ysje,"
				+ "to_char(syje,'FM999999999990.00') as syje, (select sum(r.bcbxje) from cw_rcbxxmmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt='8' and b.bxr='"+userId+"')as zcje1, "
				+ "(select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt='8' and b.sqr='"+userId+"')as zcje2, "
				+ "(select sum(r.bcbxje) from cw_rcbxxmmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt not in('8') and b.bxr='"+userId+"')as dshje1,"
				+ " (select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt not in('8') and b.sqr='"+userId+"')as dshje2 from cw_xmjbxxb A where 1=1AND A.fzr=(SELECT r.rybh FROM gx_sys_ryb r WHERE r.guid='"+userId+"'))K where 1=1 and rownum <4"
				+ " order by XMBH asc ";
		return db.queryForList(sqlstr);
	}
	
	public Map getmxxxlistlist(String guid) {
		String sql = " select guid,djbh,SFGWK,SFCJK,SFDGZF,SFDSZF,(case cclx when '01' then '会议出差' when '02' then '培训出差' when '03' then '公务出差' else '' end)cclx,a.sqr rybh,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.guid=a.sqr),'') sqr,"
				+ "nvl((select to_char(r.xm) from GX_SYS_RYB r where r.guid=a.sqr),'') sqrmc,ccrs,ccts,jflx,nvl((select '('||r.xmbh||')'||to_char(r.xmmc) from cw_xmjbxxb r "
				+ "where r.guid=a.xmmc),'') xmmc,a.xmmc as xmguid,(SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBM,"
				+ "(SELECT D.MC FROM GX_SYS_DWB D WHERE D.DWBH=(SELECT DWBH FROM gx_sys_ryb WHERE GUID=A.SQR))AS SZBMMC,"
				+ "(case sfkylbx when '1' then '是' when '0' then '否' else '' end)sfkylbx,bxzje,fjzzs,ccsy,czrq from CW_CLFBXZB a where guid='"+guid+"' ";
		return db.queryForMap(sql);
	}
	
	public List getmxxxlist(String guid) {
		String sql = " select guid,kssj,to_number(to_char(to_date(kssj,'yyyy-mm-dd hh24:mi'),'MM')) ksyf, to_number(to_char(to_date(kssj,'yyyy-mm-dd hh24:mi'),'DD')) ksrq,"
				+ "jssj,to_number(to_char(to_date(jssj,'yyyy-mm-dd hh24:mi'),'MM')) jsyf,  to_number(to_char(to_date(jssj,'yyyy-mm-dd hh24:mi'),'DD')) jsrq,cfdd,mddd,hyfy,"
				+ "pxfy,djbh,fjje,hcje,czcje,qcje,qtfy,lsshbzts,"
				+ "lsshbzje,xsshbzts,xsshbzje,zdfje,ffjs from CW_CLFBXMXB where djbh='"+guid+"' ";
		return db.queryForList(sql);
	}
	
	public List getdgzflist(String guid	) {
		String sql = " select  (select m.khyh from Cw_Wldwmxb m where m.guid=t.dfyh)yhname, t.guid,t.zfdh,t.dfdq,t.dfzh,w.dwmc,w.guid,w.wlbh,t.dfyh, decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je from CW_DGZFB t"
				+ " left join Cw_wldwb w on w.WLBH=t.DFDW where 1=1 and t.zfdh='"+guid+"' ";
		return db.queryForList(sql);
	}
	
	public List getdszflist(String guid	) {
		String sql = " select '('||w.rybh||')'||w.xm as ryxm, t.guid,t.zfdh,(case t.ryxz when '0' then '个人' when '1' then '项目负责人' when '2' then '其他人' else '' end)ryxz,t.dfzh,t.klx, decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je from CW_DSZFB t  "
				+ "left join gx_sys_ryb w on w.rybh=t.ryxm where 1=1 and t.zfdh='"+guid+"' ";
		return db.queryForList(sql);
	}
	
	public List getgwklist(String guid) {
		String sql = " select t.guid,t.skdh,to_char(t.skrq,'yyyy-MM-dd')as skrq,t.kh, decode(nvl(t.skje,0),0,'0.00',(to_char(round(t.skje,2),'fm99999999999990.00')))skje, (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.ryxm or r.guid=t.ryxm)ryxm"
				+ " from cw_gwkb t where 1=1 and t.skdh='"+guid+"' ";
		return db.queryForList(sql);
	}
	
	public List getxmjfzclist(String guid) {
		String sql = " SELECT distinct xm.CCSQID,xm.JFBH,xm.GUID,to_char(xm.bcbxje,'FM999999999999.00')as bcbxje,s.xmmc as xmmc,to_char(s.ye,'FM999999999999.00') as ye FROM CW_CCSQSPB_XM xm "
				+ "left join XMINFOS s on s.guid=xm.jfbh WHERE CCSQID in (select ccywguid from cw_clfbxzb where guid in('"+guid+"')) ";
		return db.queryForList(sql);
	}

	
	public List getccrylist(String guid) {
		String sql = " select guid,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.guid=a.rybh),'') rybh,(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=a.szdw) "
				+ "szdw from CW_TXRYXXB a where txbh='"+guid+"' ";
		return db.queryForList(sql);
	}
	
	public List getcxjklist(String guid) {
		String sql = " select distinct(t.guid)guid,d.mc as dwmc,t.jkid as jkid, (select r.mc from gx_sys_dwb r where r.dwbh = d.dwbh )szbm,decode(nvl(t.cjkje,0),0,'0.00',(to_char(round(t.cjkje,2),'fm99999999999990.00')))cjkje,"
				+ "t.jkr,'('||r.rybh||')'||r.xm as jkrxm,cw.djbh,decode(nvl(t.jkje,0),0,'0.00',(to_char(round(t.jkje,2),'fm99999999999990.00')))jkje from Cw_cjkb t "
				+ "left join gx_sys_ryb r on r.guid=t.jkr or r.rybh=t.jkr left join gx_sys_dwb d on d.dwbh=r.dwbh left join "
				+ "CW_YZFSQSPB cw on cw.djbh=t.jkid where 1=1 and jkdh='"+guid+"' ";
		return db.queryForList(sql);
	}
	
	
	
	public List gettzggsearchPagelist(String keyword) {
		String sql = " select * from ZC_SYS_xtxx where 1=1 ";
		if(Validate.noNull(keyword)) {
			sql = sql + " and title like '%"+keyword+"%' ";
		}
		return db.queryForList(sql);
	}
	public List getDwPagelist() {
		return db.queryForList(" select d.* from gx_sys_dwb d  ");
	}
	public List getRyPagelist(String dwbh) {
		return db.queryForList(" select t.*,j.zw,(select d.mc from gx_sys_dmb d where zl='112' and d.dm=j.zw) zwmc,t.url,j.lxfs "
				+ "from gx_sys_ryb t left join CW_JZGXXB j on j.xh=t.rybh where t.dwbh = '"+dwbh+"' ");
	}
	public List getRyPagelistBykeyword(String keyword) {
		return db.queryForList(" select t.*,j.zw,(select d.mc from gx_sys_dmb d where zl='112' and d.dm=j.zw) zwmc,t.url,j.lxfs "
				+ "from gx_sys_ryb t left join CW_JZGXXB j on j.xh=t.rybh where t.xm like '%"+keyword+"%' ");
	}
	public List getWdxcPagelistBytime(String time) {
		return db.queryForList(" select t.*,j.zw,(select d.mc from gx_sys_dmb d where zl='112' and d.dm=j.zw) zwmc,t.url "
				+ "from gx_sys_ryb t left join CW_JZGXXB j on j.xh=t.rybh where t.xm like '%"+time+"%' ");
	}
	public List getWdxmPagelist(String userId,int index,int page_length) {
		String sqlstr = " select  guid    ,xmbh,xmmc,xmfzr,ysje,syje,to_char((nvl(zcje1,0)+nvl(zcje2,0)),'FM999999999999990.00')as zcje,"
				+ "to_char((nvl(dshje1,0)+nvl(dshje2,0)),'FM999999999999990.00')as dshje  from  (select distinct guid, xmbh,xmmc,(select '('||RYBH||')'|| xm from gx_sys_ryb ry where ry.rybh=A.fzr) as xmfzr,to_char(ysje,'FM999999999990.00') as ysje,"
				+ "to_char(syje,'FM999999999990.00') as syje, (select sum(r.bcbxje) from cw_rcbxxmmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt='8' and b.bxr='"+userId+"')as zcje1, "
				+ "(select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt='8' and b.sqr='"+userId+"')as zcje2, "
				+ "(select sum(r.bcbxje) from cw_rcbxxmmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt not in('8') and b.bxr='"+userId+"')as dshje1,"
				+ " (select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt not in('8') and b.sqr='"+userId+"')as dshje2 from cw_xmjbxxb A where 1=1 and A.FZR=(select d.rybh from gx_sys_ryb d where d.guid='"+userId+"')  and a.sszt='B9BA12A24DBE4EA89763AFDE76B8C61A')K where 1=1 "
				+ " order by XMBH asc ";
		String startnum = ((index-1)*page_length)+1+"";
		String endnum = (index*page_length)+"";
		String sql = "select * from (select a1.*, rownum rn from ("+sqlstr+") a1 where rownum <= "+endnum+") where rn >= "+startnum+" ";
		
		return db.queryForList(sql);
	}
	
	public List getWdxmSearchPagelist(String userId,String keyword) {
		String sqlstr = " select guid,xmbh,  xmmc,xmfzr,ysje,syje,to_char((nvl(zcje1,0)+nvl(zcje2,0)),'FM999999999999990.00')as zcje,"
				+ "to_char((nvl(dshje1,0)+nvl(dshje2,0)),'FM999999999999990.00')as dshje  from  (select distinct guid, xmbh,xmmc,(select '('||RYBH||')'|| xm from gx_sys_ryb ry where ry.rybh=A.fzr) as xmfzr,to_char(ysje,'FM999999999990.00') as ysje,"
				+ "to_char(syje,'FM999999999990.00') as syje, (select sum(r.bcbxje) from cw_rcbxxmmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt='8' and b.bxr='"+userId+"')as zcje1, "
				+ "(select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt='8' and b.sqr='"+userId+"')as zcje2, "
				+ "(select sum(r.bcbxje) from cw_rcbxxmmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt not in('8') and b.bxr='"+userId+"')as dshje1,"
				+ " (select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt not in('8') and b.sqr='"+userId+"')as dshje2 from cw_xmjbxxb A where 1=1 and a.sszt='B9BA12A24DBE4EA89763AFDE76B8C61A')K where 1=1 ";
		if(Validate.noNull(keyword)) {
			sqlstr = sqlstr + " and k.xmmc like '%"+keyword+"%' ";
		}
		sqlstr = sqlstr + " order by XMBH asc ";
		
		return db.queryForList(sqlstr);
	}
	
	public Map getWdxmxqPagelist(String userId,String bh) {
		String sqlstr = " select  guid,xmbh,xmmc,xmfzr,ysje,syje,to_char((nvl(zcje1,0)+nvl(zcje2,0)),'FM999999999999990.00')as zcje,"
				+ "to_char((nvl(dshje1,0)+nvl(dshje2,0)),'FM999999999999990.00')as dshje  from  (select distinct guid, xmbh,xmmc,(select '('||RYBH||')'|| xm from gx_sys_ryb ry where ry.rybh=A.fzr) as xmfzr,to_char(ysje,'FM999999999990.00') as ysje,"
				+ "to_char(syje,'FM999999999990.00') as syje, (select sum(r.bcbxje) from cw_rcbxxmmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt='8' and b.bxr='"+userId+"')as zcje1, "
				+ "(select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt='8' and b.sqr='"+userId+"')as zcje2, "
				+ "(select sum(r.bcbxje) from cw_rcbxxmmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt not in('8') and b.bxr='"+userId+"')as dshje1,"
				+ " (select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt not in('8') and b.sqr='"+userId+"')as dshje2 from cw_xmjbxxb A where 1=1 and a.FZR=(select r.RYBH from gx_sys_ryb r where r.guid = '"+userId+"') and a.sszt='B9BA12A24DBE4EA89763AFDE76B8C61A'";
		if(Validate.noNull(bh)) {
			sqlstr = sqlstr + " and xmbh = '"+bh+"' ";
		}
		sqlstr = sqlstr + ")K where 1=1 order by XMBH asc ";
		return db.queryForMap(sqlstr);
	}
	public List getWdyhkPagelist(String userId,int index,int page_length) {
		String sqlstr = " select c.guid,c.jsbh,c.khyh,c.khyhzh,c.yhlhh from cw_jsyhzhb c where c.jsbh='"+userId+"'";
		String startnum = ((index-1)*page_length)+1+"";
		String endnum = (index*page_length)+"";
		String sql = "select * from (select a1.*, rownum rn from ("+sqlstr+") a1 where rownum <= "+endnum+") where rn >= "+startnum+" ";
		
		return db.queryForList(sql);
	}
	public String getrybhByguid(String guid) {
		return db.queryForSingleValue(" select r.rybh from gx_sys_ryb r where r.guid = '"+guid+"'");
	}
	
	public int mmxg(String userId,String newpwd) {
		newpwd = EncryptUtils.encryptToSHA(newpwd,Const.SALTKEY);
		String sql = " update gx_sys_ryb r set r.mm = '"+newpwd+"' where r.guid='"+userId+"' ";
		return db.update(sql);
	}
	/**
	 * 获取初始密码
	 * @param rygh
	 * @param mm
	 * @return
	 */
	public String getCsmm(){
		String sql = "select csmm from "+SystemSet.sysBz+"login t where rownum = 1";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 检查密码输入是否正确（设置选项页面）
	 */
	public boolean checkPwd(String passwordo,String rybh){
		String sql = "select count(*) from gx_sys_ryb where rybh = ? and (mm = ? or (mm is null and (select csmm from zc_sys_login t where rownum = 1) = ?))";
		String passwd = EncryptUtils.encryptToSHA(passwordo, Const.SALTKEY);//密码加密
		String count = db.queryForObject(sql,new Object[]{rybh,passwd,passwordo}, String.class);
		if("1".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 修改密码
	 */
	public int doUpdPwd(String newmm,String rybh){
		String csmm = getCsmm();
		if(newmm.equals(csmm)){
			return -1;
		}
		else{
			String sql="update gx_sys_ryb set mm = ? where rybh = ?";
			String passwd = EncryptUtils.encryptToSHA(newmm, Const.SALTKEY);//密码加密
			return db.update(sql,new Object[]{passwd,rybh});
		}
	}
	/**
	 * 待审核数量
	 * @param rybh
	 * @return
	 */
	public String getYwshCnt(String rybh,String mkbh) {
		String sql = "select count(*) from zc_sys_shcshb where rybh = ? and sfdqjd = '1' and sqmkbh in (" + getSqmkbh() + ") ";
		if(Validate.isNull(mkbh)){
			sql += " and mkbh <> '" + MenuFlag.ZCBD_ZCDB_SYRQR + "'";
		}else{
			sql += " and mkbh = '" + mkbh + "'";
		}
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	/**
	 * 获取待资产自查数量
	 */
	public String getZczcCnt(String rybh) {
		String sql = "select count(*) from zc_zczcb k where k.qcbh in (select qcbh from zc_zczczt where zt = '1') and k.syr = ? and k.qczt in ('" + StateManager.ZCZC_LR + "','" + StateManager.ZCZC_WTG + "')";
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	/**
	 * 获取未读通知数量
	 */
	public String getWdsl(String rybh) {
		String sql = "select count(*) sl from zc_sys_xtxx where gid not in (select bh from zc_xtxx_view where rybh = ? and sfzs='1') ";
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	/**
	 * 获取未读系统消息
	 */
	public String getWdxx(String rybh) {
		String sql = "select count(*) sl from zc_sys_tzxx x where gid not in (select bh from zc_tzxx_view where rybh = ? and sfzs='1') and (nvl(dwbh,'$') = '$' or (select dwbh from gx_sys_ryb r where r.rybh = ?) in (select dwbh from gx_sys_dwb d start with d.dwbh = x.dwbh connect by prior dwbh = sjdw)) ";
		return db.queryForSingleValue(sql, new Object[]{rybh,rybh});
	}
	/**
	 * 待办事项-获取验收单信息
	 * @param zcbh
	 * @return
	 */
	public Map getYsdByYsdh(String djbh) {
		String sql = "select ysdh,yqbh,nvl(shl,1) shl,flh,tph,yqmc,fph,pzh,xmh,to_char(jzrq,'yyyy-mm-dd') jzrq,to_char(dzrrq,'yyyy-mm-dd') dzrrq,to_char(hdrq,'yyyy-mm-dd') hdrq,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh = a.shgdw) shgdw," +
				"(select mc from gx_sys_dmb d where zl = '" + Constant.ZCLY + "' and d.dm = a.zcly) zcly,(select mc from gx_sys_dmb d where zl = '" + Constant.JFKM + "' and d.dm = a.jfkm) jfkm,(select mc from gx_sys_dmb d where zl = '" + Constant.JLDW + "' and d.dm = a.jldw) jldw," + 
				"(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh = a.jsr) jsr,to_char(yshrq,'yyyy-mm-dd') yshrq," + ToSqlUtil.getSqlByZjbXh() + " xh," + ToSqlUtil.getSqlByZjbGg() + " gg," +
				"(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh = a.cgr) cgr,decode(nvl(a.dj,0),0,'0.00',(to_char(round(a.dj,2),'fm99999999999990.00'))) dj,(select mc from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = a.xz) xzmc," +
				"(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh = a.syr) syr,decode(nvl(a.zzj,0),0,'0.00',(to_char(round(a.zzj, 2),'fm99999999999990.00'))) zzj,shyj from zc_yshd a where ysdh = ?";
		return db.queryForMap(sql,new Object[]{djbh});
	}
	/**
	 * 待办事项-获取变动单信息
	 * @param zcbh
	 * @return
	 */
	public Map getBddByBdbgbh(String djbh) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.bdbgbh,to_char(b.bdrq,'yyyy-mm-dd') bdrq,(select '('||a.rygh||')'||a.xm from gx_sys_ryb a where a.rybh = b.rybh) rybhmc,b.rybh,b.zi,b.hao,b.dwbh,");
		sql.append("(select '('||bmh||')'||mc from gx_sys_dwb a where a.dwbh = b.dwbh) dwmc,");
		sql.append("b.hdwbh,(select '('||bmh||')'||mc from gx_sys_dwb a where a.dwbh = b.hdwbh) hdwbhmc,");
		sql.append("b.hrybh,(select '('||a.rygh||')'||a.xm from gx_sys_ryb a where a.rybh = b.hrybh) hrybhmc,");
		sql.append("b.hcfdd,(select '('||ddh||')'||mc from zc_sys_ddb a where a.ddbh = b.hcfdd) hcfddmc,");
		sql.append("b.bddj,decode(nvl(b.bddj,0),0,'0.00',to_char(round(b.bddj,2),'fm9999999999999990.00')) bddjs,");
		sql.append("b.czsy,decode(nvl(b.czsy,0),0,'0.00',to_char(round(b.czsy,2),'fm9999999999999990.00')) czsys,");
		sql.append("b.hxz,(select mc from gx_sys_dmb d where d.zl in ('" + Constant.XZ + "','" + Constant.HXZ + "') and d.dm = b.hxz) hxzmc,b.bdyy,b.sh,b.hcs,b.bdrxm,b.pzh,to_char(b.jzrq,'yyyy-mm-dd') jzrq,b.jzbz,b.gkdw,b.ztbz,b.hmj,b.hzrjs,b.xmbz,b.djbz,");
		sql.append(StateManager.getBdxm("b.djbz") + " djbzmc,b.hfsss,b.htdmj,b.jdr,b.jdrq,b.gkry,");
		sql.append("(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = b.gkry) gk,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = b.lyr) lyr,");
		sql.append("to_char(b.lyrrq,'yyyy-mm-dd') lyrrq,b.lyryj,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = b.gly) gly,");
		sql.append("to_char(b.glyshrq,'yyyy-mm-dd') glyshrq,b.glyshyj,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = b.dcdwry) dcdwry,");
		sql.append("to_char(b.dcdwshrq,'yyyy-mm-dd') dcdwshrq,b.dcdwshyj,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = b.drdwry) drdwry,");
		sql.append("to_char(b.drdwshrq,'yyyy-mm-dd') drdwshrq,b.drdwshyj,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = b.gkshry) gkshry,");
		sql.append("to_char(b.gkrq,'yyyy-mm-dd') gkrq,b.gkyj,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = b.shr) shr,");
		sql.append("to_char(b.shrq,'yyyy-mm-dd') shrq,b.shyj,");
		sql.append("decode(nvl(b.zzj, 0),0,'0.00',to_char(round(b.zzj, 2),'fm9999999999999990.00')) zzjs,");
		sql.append("decode((b.zzj + b.fjzj),0,'0.00',to_char(round((b.zzj + b.fjzj), 2),'fm9999999999999990.00')) fjzjs,");
		sql.append("decode((b.zzj - b.fjzj),0,'0.00',to_char(round((b.zzj - b.fjzj), 2),'fm9999999999999990.00')) fjczs ");
		sql.append("from (select b.*,nvl((select sum(zzj) from zc_bdb where bdbgbh = b.bdbgbh and bz = '1'),0) zzj,nvl((select sum(dj) from zc_bdb where bdbgbh = b.bdbgbh and bz = '2'),0) fjzj from zc_bdbgb b where b.bdbgbh = ?) b");
		Map map = db.queryForMap(sql.toString(),new Object[]{djbh});
		
		sql = new StringBuffer();
		if(StateManager.BDXM_FJZJ.equals(map.get("djbz")) || StateManager.BDXM_FJCZ.equals(map.get("djbz"))){
			sql.append("select fjbh,yqmc,flh,flmc,bdxm,decode(nvl(fjdj,0),0,'0.00',to_char(round(fjdj,2),'fm9999999999999990.00')) fjdj,xss,sccj,fjgg,fjxh,(case sfjrzj when '0' then '不计入主件' else '计入主件' end) sfjrzj from zc_bdb where bdbgbh = ? and bz = '2' order by fjbh,bdxm");
		}
		else{
			sql.append("select fjbh,yqmc,flh,flmc,bdxm,");
			sql.append("(case bdxm ");
				sql.append("when '使用人' then (FUN_GETBDMC('R',b.bdqnr,b.bdqnrmc)) ");
				sql.append("when '使用单位' then (FUN_GETBDMC('D',b.bdqnr,b.bdqnrmc)) ");
				sql.append("when '存放地点' then (FUN_GETBDMC('F',b.bdqnr,b.bdqnrmc)) ");
				sql.append("when '单价' then (decode(nvl(to_number(b.bdqnr),0),0,'0.00',to_char(round(to_number(b.bdqnr),2),'fm9999999999999990.00'))) ");
				sql.append("when '总价' then (decode(nvl(to_number(b.bdqnr),0),0,'0.00',to_char(round(to_number(b.bdqnr),2),'fm9999999999999990.00'))) ");
				sql.append("when '现状' then (select to_char(mc) from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = b.bdqnr) ");
				sql.append("when '使用方向' then (select to_char(mc) from gx_sys_dmb where zl = '" + Constant.SYFX + "' and dm = b.bdqnr) ");
				sql.append("when '建筑面积' then (decode(nvl(to_number(b.bdqnr),0),0,'0.00',to_char(round(to_number(b.bdqnr),2),'fm9999999999999990.00'))) ");
				sql.append("when '土地面积' then (decode(nvl(to_number(b.bdqnr),0),0,'0.00',to_char(round(to_number(b.bdqnr),2),'fm9999999999999990.00'))) ");
				sql.append("else bdqnr end) bdqnr,");
			sql.append("(case bdxm ");
				sql.append("when '使用人' then (FUN_GETBDMC('R',b.bdhnr,b.bdhnrmc)) ");
				sql.append("when '使用单位' then (FUN_GETBDMC('D',b.bdhnr,b.bdhnrmc)) ");
				sql.append("when '存放地点' then (FUN_GETBDMC('F',b.bdhnr,b.bdhnrmc)) ");
				sql.append("when '单价' then (decode(nvl(to_number(b.bdhnr),0),0,'0.00',to_char(round(to_number(b.bdhnr),2),'fm9999999999999990.00'))) ");
				sql.append("when '总价' then (decode(nvl(to_number(b.bdhnr),0),0,'0.00',to_char(round(to_number(b.bdhnr),2),'fm9999999999999990.00'))) ");
				sql.append("when '现状' then (select to_char(mc) from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = b.bdhnr) ");
				sql.append("when '使用方向' then (select to_char(mc) from gx_sys_dmb where zl = '" + Constant.SYFX + "' and dm = b.bdhnr) ");
				sql.append("when '建筑面积' then (decode(nvl(to_number(b.bdhnr),0),0,'0.00',to_char(round(to_number(b.bdhnr),2),'fm9999999999999990.00'))) ");
				sql.append("when '土地面积' then (decode(nvl(to_number(b.bdhnr),0),0,'0.00',to_char(round(to_number(b.bdhnr),2),'fm9999999999999990.00'))) ");
				sql.append("else bdhnr end) bdhnr");
			sql.append(" from (select bdbgbh,to_char(bdqnr) bdqnr,to_char(bdhnr) bdhnr,bz,fjbh,yqmc,flh,flmc,to_char(bdxm) bdxm from zc_bdb where bdbgbh = ? and bz = '1') b order by fjbh,bdxm");
		}
		map.put("mxlist", db.queryForList(sql.toString(), new Object[]{djbh}));
		return map;
	}
	/**
	 * 待办事项-获取部分调拨信息
	 * @param zcbh
	 * @return
	 */
	public Map getDbdByBdbgbh(String djbh) {
		String sql = "select bdbgbh,to_char(b.bdrq,'yyyy-mm-dd') bdrq,cfshl,decode(nvl(b.cfje, 0),0,'0.00',to_char(round(b.cfje, 2),'fm9999999999999990.00')) cfje,b.dwbh,(select '('||bmh||')'||mc from gx_sys_dwb a where a.dwbh = b.dwbh) dwmc,(select '('||rygh||')'||to_char(xm) from gx_sys_ryb r where r.rybh = b.jdr) jdrmc,bdyy,gkyj from (select b.*,nvl((select sum(je) from zc_zcdbmxb m where m.bdbgbh = b.bdbgbh),0.00) cfje from zc_zcdbb b where bdbgbh = ?) b";
		Map map = db.queryForMap(sql,new Object[]{djbh});
		sql = "select guid,newyqbh,(select yqmc from zc_zjb z where z.yqbh = b.yqbh) yqmc,sl,decode(nvl(je,0),0,'0.00',to_char(round(je,2),'fm9999999999999990.00')) je,sydw,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = b.sydw) sydwmc,syr,(select '('||rygh||')'||to_char(xm) from gx_sys_ryb r where r.rybh = b.syr) syrmc,cfdd,(select '('||ddh||')'||to_char(mc) from zc_sys_ddb d where d.ddbh = b.cfdd) cfddmc from zc_zcdbmxb b where bdbgbh = ? order by newyqbh";
		map.put("mxlist", db.queryForList(sql.toString(), new Object[]{djbh}));
		return map;
	}
	/**
	 * 待办事项-获取资产处置申请详情
	 */
	public Map getCzdBySqbh(String djbh) {
		String sql="select sqbh,to_char(sqrq,'yyyy-mm-dd') sqrq,ztbz,(select '('||rybh||')'||xm from gx_sys_ryb b where b.rybh = a.sqr) sqr,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = a.sqdw) sqdw,decode(nvl(zje, 0),0,'0.00',to_char(round(zje, 2),'fm9999999999999990.00')) zje,czyy,shyj from zc_czsqb a where sqbh = ?";
		Map map = db.queryForMap(sql,new Object[]{djbh});
		sql = "select sqmxbh,yqbh,sqbh,yqmc,sydw,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = s.sydw) sydwmc,syr,(select '('||rygh||')'||to_char(xm) from gx_sys_ryb r where r.rybh = s.syr) syrmc,flh,flmc,decode(nvl(zzj, 0),0,'0.00',to_char(round(zzj, 2),'fm9999999999999990.00')) zzj,bzxx,(select '('||ddh||')'||to_char(mc) from zc_sys_ddb d where d.ddbh = s.bzxx) bzxxmc,gg,xh,to_char(gzrq,'yyyy-mm-dd') gzrq,to_char(rzrq,'yyyy-mm-dd') rzrq from zc_czsqmxb s where sqbh = ? order by yqbh";
		map.put("mxlist", db.queryForList(sql.toString(), new Object[]{djbh}));
		return map;
	}
	/**
	 * 待办事项-获取资产处置报告详情
	 */
	public Map getCzdByCzbgbh(String djbh) {
		String sql="select czbgbh,zi,hao,decode(nvl(zmyz,0),0,'0.00',to_char(round(zmyz,2),'fm9999999999999990.00')) zmyz,decode(nvl(pgjz, 0),0,'0.00',to_char(round(pgjz, 2), 'fm9999999999999990.00')) pgjz,decode(nvl(czjz, 0),0,'0.00',to_char(round(czjz, 2), 'fm9999999999999990.00')) czjz,(select mc from gx_sys_dmb d where d.dm = c.hxz and zl = '" + Constant.HXZ + "') hxzmc,czyy,jdyj,ztbz,pzh,to_char(czrq,'yyyy-mm-dd') czrq,to_char(jzrq,'yyyy-mm-dd') jzrq,gkdw,shyj,gkshry,gkyj,gkrq,(select xm from gx_sys_ryb r where r.rybh = c.gkry) gkry,jdr,jdrq,rybh,dwbh,czrxm,dwmc from zc_czbgb c where czbgbh = ?";
		Map map = db.queryForMap(sql,new Object[]{djbh});
		sql = "select c.yqbh,(select mc from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = c.bdqnr) bdqnr,z.yqmc,z.flh,z.flmc,z.syr,(select '('||rygh||')'||to_char(xm) from gx_sys_ryb r where r.rybh = z.syr) syrmc,z.sydw,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = z.sydw) sydwmc,z.bzxx,(select '('||ddh||')'||to_char(mc) from zc_sys_ddb d where d.ddbh = z.bzxx) bzxxmc,(select mc from gx_sys_dmb where zl = '" + Constant.SYFX + "' and dm = z.syfx) syfx,decode(nvl(to_number(z.dj),0),0,'0.00',to_char(round(to_number(z.dj),2),'fm9999999999999990.00')) dj,decode(nvl(to_number(z.zzj),0),0,'0.00',to_char(round(to_number(z.zzj),2),'fm9999999999999990.00')) zzj,to_char(z.gzrq,'yyyy-mm-dd') gzrq,to_char(z.rzrq,'yyyy-mm-dd') rzrq from (select * from zc_czb c where czbgbh = ?) c left join zc_zjb z on c.yqbh = z.yqbh order by c.yqbh";
		map.put("mxlist", db.queryForList(sql.toString(), new Object[]{djbh}));
		return map;
	}
	/**
	 * 待办事项-获取维修申请详情
	 */
	public Map getWxdBySqbh(String djbh) {
		String sql="select reportid,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh = w.replycompany) replycompany,(select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = w.replyperson) replyperson,to_char(replytime,'yyyy-mm-dd') replytime,assetid,assetname,questionremark,replycontent,decode(nvl(aboutmoney,0),0,'0.00',(to_char(round(aboutmoney,2),'fm99999999999990.00'))) aboutmoney,remark,checkperson,checkadvice,to_char(checktime,'yyyy-mm-dd') checktime,ztbz,replystatus,flag,wxsadvice,wxsbh,wxsmc,fjinfo from zc_wxsqb w where reportid = ? ";
		Map map = db.queryForMap(sql,new Object[]{djbh});
		sql = "select yqbh,yqmc,flh,flmc,(select mc from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = w.xz) xz,to_char(gzrq,'yyyy-mm-dd') gzrq,(select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = w.fzr) fzr,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh = w.sydw) sydw,(select '('||ddh||')'||to_char(mc) from zc_sys_ddb d where d.ddbh = w.cfdd) cfdd,decode(nvl(dj,0),0,'0.00',(to_char(round(dj,2),'fm99999999999990.00'))) dj,decode(nvl(zzj,0),0,'0.00',(to_char(round(zzj,2),'fm99999999999990.00'))) zzj,gg,xh,to_char(bxjzrq,'yyyy-mm-dd') bxjzrq from zc_wxsqmxb w where sqbh = ? order by yqbh";
		map.put("mxlist", db.queryForList(sql.toString(), new Object[]{djbh}));
		return map;
	}
	/**
	 * 待办事项-获取维修报告详情
	 */
	public Map getWxdByBgbh(String djbh) {
		String sql="select orderid,reportid,repaircompany,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = w.repaircompany) repaircompanymc,repairperson,repaircontact,to_char(repairtime,'yyyy-mm-dd') repairtime,repairrecord,decode(nvl(w.repairmoney,0),0,'0.00',(to_char(round(w.repairmoney,2),'fm99999999999990.00'))) repairmoney,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh = w.acceptperson) acceptpersonmc,acceptadvice,remark,pzh,to_char(jzrq,'yyyy-mm-dd') jzrq,to_char(ysrq,'yyyy-mm-dd') ysrq,wxcj,wxhsbzt,fpinfo,wxcjbh,checkadvice,cwyj from zc_wxbgb w where orderid = ? ";
		Map map = db.queryForMap(sql,new Object[]{djbh});
		sql = "select yqbh,yqmc,flh,flmc,(select mc from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = w.xz) xz,to_char(gzrq,'yyyy-mm-dd') gzrq,(select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = w.fzr) fzr,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh = w.sydw) sydw,(select '('||ddh||')'||to_char(mc) from zc_sys_ddb d where d.ddbh = w.cfdd) cfdd,decode(nvl(dj,0),0,'0.00',(to_char(round(dj,2),'fm99999999999990.00'))) dj,decode(nvl(zzj,0),0,'0.00',(to_char(round(zzj,2),'fm99999999999990.00'))) zzj,gg,xh,to_char(bxjzrq,'yyyy-mm-dd') bxjzrq,decode(nvl(wxfy,0),0,'0.00',(to_char(round(wxfy,2),'fm99999999999990.00'))) wxfy from zc_wxsqmxb w where sqbh = (select reportid from zc_wxbgb where orderid = ?) order by yqbh";
		map.put("mxlist", db.queryForList(sql.toString(), new Object[]{djbh}));
		return map;
	}
	/**
	 * 待办事项-获取维修经费追加详情
	 */
	public Map getWxjfzjBySqbh(String djbh) {
		String sql = "select sqbh,(select '('||b.rygh||')'||b.xm from gx_sys_ryb b where b.rybh = j.sqry) sqry," +
				"to_char(sqrq,'yyyy-mm-dd') sqrq,(select '('||c.dwbh||')'||c.mc from gx_sys_dwb c where c.dwbh = j.sydw) sydw," +
				"sqyy,decode(nvl(je,0),0,'0.00',(to_char(round(je,2),'fm99999999999990.00'))) je,nd,bz,zjsqzt,(select '('||b.rygh||')'||b.xm from gx_sys_ryb b where b.rybh = j.dwry) dwry," +
				"dwyj,(select '('||b.rygh||')'||b.xm from gx_sys_ryb b where b.rybh = j.zccry) zccry," +
				"zccyj,to_char(dwshrq,'yyyy-mm-dd') dwshrq,to_char(zccshrq,'yyyy-mm-dd') zccshrq from zc_wxjfzj j where sqbh = ?";
		return db.queryForMap(sql,new Object[]{djbh});
	}
	/**
	 * 判断是否末级分类
	 * @param djbh
	 * @return
	 */
	public boolean checkEndFlh(String djbh){
		String sql = "select flh from zc_yshd where ysdh = ?";
		String flh = db.queryForObject(sql, String.class, new Object[]{djbh});
		if(Validate.isNull(flh)){
			return false;
		}
		else{
			String sfmj = "1";
			sql = "select sfmj from zc_sys_xtb x where rownum = 1";
			sfmj = db.queryForSingleValue(sql);
			if("0".equals(sfmj)){
				return true;
			}
			else{
				sql = "select count(*) from (select bzdm,flh,flmc,jldw,dmjc,(case sjdm when bzdm then '000000' else sjdm end) sjdm,zcdm,zjff,synx,jczl,zgzl,ygzl,ffldm,fflmc,sfmj,sfwxzc,billtype from zc_flb_jyb) where sjdm in (select bzdm from zc_flb_jyb where flh = ?)";
				sfmj = db.queryForSingleValue(sql,new Object[]{flh});
				return "0".equals(sfmj);
			}
		}
	}
	
	/**
	 * 根据人员编号查询人员信息(xm字段不要加rygh，获取姓名的都是走的这个方法)
	 * @param rybh
	 * @return
	 */
	public Map RyxxByRybh(String rybh){
		String sql = "select rybh,xm,'('||rygh||')'||xm ryxm,dwbh,xb,(select mc from gx_sys_dmb d where d.zl = '" + Constant.SEX + "' and d.dm = r.xb) xbmc,lxdh,(select mc from gx_sys_dmb a where zl = '"+Constant.ZYGZ+"' and a.dm=r.zygz) zygz from gx_sys_ryb r where rybh='"+rybh+"'";
		return db.queryForMap(sql);
	}
	
	
	public Map RyxxByGuid(String guid){
		String sql = " select t.rybh,t.dwbh,t.xm,(select mc from gx_sys_dmb where zl='20' and dm=t.xb) as xb, to_char(t.csrq,'yyyy-mm-dd') as csrq,"
				+ " to_char(t.byrq,'yyyy-mm-dd') as byrq, to_char(t.gzrq,'yyyy-mm-dd') as gzrq, to_char(t.drrq,'yyyy-mm-dd') as drrq, to_char(t.txrq,'yyyy-mm-dd') as txrq, "
				+ " t.bz,(case t.ryzt when '1' then '正常' else '禁用' end) as ryzt, (case t.zzbz when '1' then '实验室专职人员' else '非实验室专职人员' end) as zzbz, t.sysgl,t.pxxh,"
				+ " t.sfzh,t.rygh,t.rownums,t.url,t.cssclass,t.lxdh,t.qq,t.mail,t.mm, (select mc from  gx_sys_dmb  where zl = '102' and  dm=t.zzzt) as zzzt, "
				+ " (select mc from  gx_sys_dmb  where zl = '27' and  dm=t.whcd ) as whcd, (select mc from  gx_sys_dmb  where zl = '01' and  dm=t.sxzy ) as sxzy,"
				+ " (select mc from  gx_sys_dmb  where zl = '28' and  dm=t.zyzc ) as zyzc, (select mc from  gx_sys_dmb  where zl = '26' and  dm=t.zygz ) as zygz,"
				+ " (select '('||d.bmh||')'||d.mc  from  gx_sys_dwb d where dwbh=t.dwbh) as dwmc, "
				+ " (select ld.xm from  gx_sys_ryb ld where rybh=(select dwld from gx_sys_dwb where dwbh=t.dwbh) ) as ldxm  from gx_sys_ryb t where t.guid='"+guid+"' ";
		return db.queryForMap(sql);
	}
	
	/**
	 * 
	 * @param guid 
	 * @param newname 保存的文件路径
	 * @param oldname 上传的文件路径
	 * @return
	 */
	public int uploadimg(String guid,String newname, String oldname) {
		String sql = "update gx_sys_ryb r set r.url='"+oldname+"' where r.guid = '"+guid+"' ";
		return db.update(sql);
	}
	
	public int getgrxxxg(String userId,String sfzh,String csny,String whcd,String lxfs,String qqhm,String gryx) {
		String sql = " update gx_sys_ryb r set r.sfzh='"+sfzh+"',r.csrq=to_date('"+csny+"','yyyy-mm-dd'),r.whcd='"+whcd+"',r.lxdh='"+lxfs+"',r.qq='"+qqhm+"',r.mail='"+gryx+"' where r.guid='"+userId+"' ";
		return db.update(sql);
	}
	
	public List getrcbxlist(String userId,int index,String keyword,int page_length) {
		String sqlstr = " select guid,xmbh,xmmc,decode(nvl(ye,'0'),'0','0.00',to_char(round(YE,2),'fm9999999999999999999999999900.00'))ye,jzsj,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=a.fzr)as fzr,"
				+ " (SELECT decode(nvl(YSJE,'0'),'0','0.00',to_char(round(YSJE,2),'fm9999999999999999999999999999900.00')) FROM CW_XMJBXXB B WHERE B.GUID=A.GUID) AS YSJE,decode(a.jflx,'01','部门经费','个人经费')jflx"
				+ " from XMINFOS A where 1 = 1 and a.ye <> 0  ";
		sqlstr = sqlstr + " and a.ye<>0 and ( ((bsqr='"+userId+"' or bsqr=(select rybh from gx_sys_ryb r where r.guid = '"+userId+"')) and jflx = '02') or (jflx='01' and bmbh=(select r.dwbh from gx_sys_ryb r where r.guid = '"+userId+"'))) ";
		sqlstr = sqlstr + " and xmmc like '%"+keyword+"%' ";
		String startnum = ((index-1)*page_length)+1+"";
		String endnum = (index*page_length)+"";
		String sql = "select * from (select a1.*, rownum rn from ("+sqlstr+") a1 where rownum <= "+endnum+") where rn >= "+startnum+" ";
		return db.queryForList(sql);
	}
	
	public List<Map<String, Object>> getShxx(String guid, String cxywlx,int index,int pageSize,String keyword) {
		StringBuffer sql = new StringBuffer();
		if ("ccsq".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,'ccsq' as ywlx ,'1' as state,to_char(a.czrq,'yyyy-mm-dd') as time,a.djbh,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_ccsqspb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '130202' where  d.mkmc like '%"+keyword+"%' and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  ");//
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("rcbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,to_char(a.czrq,'yyyy-mm-dd') as time,'2' as state,'rcbx' as ywlx,a.djbh,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_rcbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_");
			sql.append(" left join gx_sys_ryb c on a.bxr = c.guid left join zc_sys_mkb d on d.mkbh = '110203' where  d.mkmc like '%"+keyword+"%'  and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'   ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("ccbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,a.czrq as time,'ccbx' as ywlx,'3' as state,a.djbh,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_clfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '110302' where  d.mkmc like '%"+keyword+"%' and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'   ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("gwjd".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,a.sqrq as time,'gwjd' as ywlx,a.djbh,'4' as state,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdywsqspb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.rybh left join zc_sys_mkb d on d.mkbh = '130102' where  d.mkmc like '%"+keyword+"%'  and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else if ("jdbx".equals(cxywlx)) {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,to_char(a.czrq,'yyyy-mm-dd') as time,'5' as state,'jdbx' as ywlx,a.djbh,to_char(c.xm) as sqrxm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(	" left join gx_sys_ryb c on a.bxryid = c.guid left join zc_sys_mkb d on d.mkbh = '111102' where  d.mkmc like '%"+keyword+"%'  and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and
			sql.append("  ) A  WHERE ROWNUM < '"+pageSize*(index)+"'  )WHERE RN >= '"+pageSize*(index-1)+"' ");
		} else {
			sql.append(" SELECT * FROM(SELECT A.*, ROWNUM RN FROM (  ");
			sql.append(	" select a.guid,a.procinstid,'ccsq' as ywlx ,to_char(a.czrq,'yyyy-mm-dd') as time,a.djbh,to_char(c.xm) as sqrxm,'1' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_ccsqspb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '130202' where  d.mkmc like '%"+keyword+"%' and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'   ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and
			sql.append(" union ");
			sql.append(	" select a.guid,a.procinstid,'gwjd' as ywlx,a.sqrq as time,a.djbh,to_char(c.xm) as sqrxm,'4' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdywsqspb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.rybh left join zc_sys_mkb d on d.mkbh = '130102' where d.mkmc like '%"+keyword+"%'  and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and 
			sql.append(" union ");
			sql.append(	" select a.guid,a.procinstid,'rcbx' as ywlx,to_char(a.czrq,'yyyy-mm-dd') as time,a.djbh,to_char(c.xm) as sqrxm,'2' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_rcbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_");
			sql.append(" left join gx_sys_ryb c on a.bxr = c.guid left join zc_sys_mkb d on d.mkbh = '110203' where  d.mkmc like '%"+keyword+"%'  and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and
			sql.append(" union ");
			sql.append(	" select a.guid,a.procinstid,'ccbx' as ywlx,a.czrq as time,a.djbh,to_char(c.xm) as sqrxm,'3' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_clfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(" left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '110302' where  d.mkmc like '%"+keyword+"%' and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and
			sql.append(" union ");
			sql.append(	" select a.guid,a.procinstid,'jdbx' as ywlx,to_char(a.czrq,'yyyy-mm-dd') as time,a.djbh,to_char(c.xm) as sqrxm,'5' as state,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh from cw_gwjdfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ ");
			sql.append(	" left join gx_sys_ryb c on a.bxryid = c.guid left join zc_sys_mkb d on d.mkbh = '111102' where  d.mkmc like '%"+keyword+"%'  and b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr'  ");//b.assignee_ = '" + guid + "' and b.task_def_key_ <> 'sqr' and
			sql.append("  ) A  WHERE ROWNUM < "+pageSize*(index)+"  order by a.time desc )WHERE RN >= "+ pageSize*(index-1)+" ");
		}
		return db.queryForList(sql.toString());
	}
	/**
	 * 获取通讯录人员所在单位
	 * @return
	 */
	public List<Map<String, Object>> hqtxldw(String rybh,String keyword) {
		StringBuffer sql =  new StringBuffer();
		sql.append("select dwbh,mc from gx_sys_dwb ");
		sql.append("where dwbh in (select (select dwbh from gx_sys_ryb r where rybh = t.rybh) from zc_txl t");
		if(Validate.noNull(keyword)){
			sql.append(" where xm like '%" + keyword + "%'");
		}
		sql.append(") order by bmh");
		return db.queryForList(sql.toString());
	}
	/**
	 * 通讯录中的获取单位下人员
	 * @param dwbh
	 * @param keyword
	 * @return
	 */
	public List<Map<String, Object>> hqtxlry(String dwbh,String keyword) {
		StringBuffer sql = new StringBuffer();
		sql.append("select xm,moblie,bddh from zc_txl a where a.rybh in (select rybh from gx_sys_ryb where dwbh = ?)");
		if(Validate.noNull(keyword)){
			sql.append(" and xm like '%" + keyword + "%'");
		}
		sql.append(" order by nvl(pxxh,0),xm");
		return db.queryForList(sql.toString(),new Object[]{dwbh});
	}
	/**
	 * 资产详细信息
	 * @param zcbh
	 * @return
	 */
	public Map zcxxxq(String zcbh,String where) {
		String sql = "select flh,flmc,yqbh,yqmc,nvl(sykh,1) sykh,to_char(gzrq,'yyyy-mm-dd') gzrq,sydw,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh = a.sydw) sydwmc,decode(nvl(a.zzj, 0),0, '0.00', (to_char(round(a.zzj, 2), 'fm99999999999990.00'))) zzj," +
				"decode(nvl(a.dj, 0),0,'0.00',(to_char(round(a.dj, 2),'fm99999999999990.00'))) dj,(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh = a.syr) syrmc,syr,(select mc from gx_sys_dmb where zl = '" + Constant.JLDW + "' and dm = a.jldw) jldwmc,to_char(a.rzrq,'yyyy-mm-dd') rzrq," +
				"(select '('||d.ddh||')'||d.mc from zc_sys_ddb d where d.ddh = a.bzxx) bzxxmc,bzxx,(select mc from gx_sys_dmb where zl = '"+Constant.XZ+"' and dm = a.xz) xzmc,a.tph,(select mc from gx_sys_dmb where zl = '" + Constant.SYFX + "' and dm = a.syfx) syfxmc," + ToSqlUtil.getSqlByZjbXss() + " xss," + ToSqlUtil.getSqlByZjbXh() + " xh," + ToSqlUtil.getSqlByZjbGg() + " gg," + ToSqlUtil.getSqlByZjbSccj() + " sccj from zc_zjb a where yqbh = ?" + where;
		return db.queryForMap(sql,new Object[]{zcbh});
	}
	/**
	 * 获取通知公告详细信息
	 * @param pjxqbh
	 * @return
	 */
	public Map tzggxq(String id,String rybh) {
		String sql = "select gid,title,sfzs,fbr,(select xm from " + SystemSet.gxBz + "ryb where rybh = t.fbr) fbrmc,xx,to_char(fbsj,'yyyy-mm-dd') fbsj from %Sxtxx t where gid = '"+id+"'";
		sql = String.format(sql, SystemSet.sysBz);
		Map<String,Object> map = db.queryForMap(sql, "xx");
			
		//如果以前该人员没有查看过这个通知，则在查看表里插入已查看信息
		sql = "insert into zc_xtxx_view(gid,bh,rybh,rq) select sys_guid(),?,?,sysdate from dual where (select count(*) from zc_xtxx_view where bh = ? and rybh = ?) = 0";
		db.update(sql, new Object[]{id,rybh,id,rybh});
		
		return map;
	}
	/**
	 * 获取系统消息的详细信息
	 * @param id
	 * @param rybh
	 * @return
	 */
	public Map xtxxxq(String id,String rybh) {
		String sql = "select gid,title,sfzs,fbr,(select xm from "+SystemSet.gxBz+"ryb where rybh = t.fbr) fbrmc,xx,to_char(fbsj,'yyyy-mm-dd') fbsj from %Stzxx t where gid = '"+id+"'";
		sql=String.format(sql, SystemSet.sysBz);
		Map<String,Object> map = db.queryForMap(sql, "xx");
		
		//如果以前该人员没有查看过这个通知，则在查看表里插入已查看信息
		sql = "insert into zc_tzxx_view(gid,bh,rybh,rq) select sys_guid(),?,?,sysdate from dual where (select count(*) from zc_tzxx_view where bh = ? and rybh = ?) = 0";
		db.update(sql, new Object[]{id,rybh,id,rybh});
		
		return map;
	}
	
	/**
	 * 资产图片维护提交
	 * @param fj
	 * @return
	 */
	public boolean zctpwhtj(Map map){
		String sql = " insert into zc_xgwd(guid,djlx,dbh,filename,rybh,scsj,path) values (sys_guid(),?,?,?,?,sysdate,?)";
		String rybh = Validate.isNullToDefaultString(map.get("userId"),"");
		String zcbh = Validate.isNullToDefaultString(map.get("zcbh"),"");
		List list = (List)map.get("photos");
		List sqllist = new ArrayList();
		List parlist = new ArrayList();
		
		Map tpmap;
		String fileurl = "";
		String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
		for(int i = 0; i < list.size(); i++){
			tpmap = (Map)list.get(i);
			fileurl = Validate.isNullToDefaultString(tpmap.get("fileUrl"),"").replace(xnlu_path + "/", "");
			parlist.add(new Object[]{"000000",zcbh,Validate.isNullToDefault(tpmap.get("filename"), ""),rybh,fileurl});
			sqllist.add(sql);
		}
		
		return db.batchUpdate(sqllist, parlist);
	}
	
	/**
	 * 获取清查编号
	 * @return
	 */
	public String getqcbh(){
		return db.queryForSingleValue("select qcbh from zc_zcqczt where zt = '1'");
	}
	/**
	 * 资产清查获取资产信息
	 * @param zcbh
	 * @return
	 */
	public Map zcqcgetinfo(String rybh,String zcbh) {
		String sql="select k.qcbh,k.yqbh,k.yqmc,k.flh,k.flmc,k.qcqk,k.xz,(select mc from gx_sys_dmb where zl = '" + Constant.XZ + "' and dm = k.xz) xzmc,"+
	      "k.pyyy,k.pkyy,k.syzk,k.sydw,(select '('||bmh||')'||mc from gx_sys_dwb where dwbh = k.sydw) sydwmc,k.syr,(select '('||rygh||')'||xm from gx_sys_ryb where rybh = k.syr) syrmc,k.bzxx,(select '('||ddh||')'||mc from zc_sys_ddb d where d.ddbh = k.bzxx) bzxxmc,"+
	      "to_char(k.gzrq,'yyyy-MM-dd') gzrq,decode(nvl(k.dj, 0),0,'0.00',(to_char(round(k.dj, 2),'fm99999999999990.00'))) dj from zc_zcqcb k "+
	      " where k.qcbh in (select qcbh from zc_zcqczt where zt = '1')" + glqxbDao.getQxsql(rybh, "k.sydw", "D") + " and k.yqbh = ? order by yqbh";
		return db.queryForMap(sql,new Object[]{zcbh});
	}
	/**
	 * 资产清查数据提交
	 * @param 
	 * @return
	 */
	public boolean zcqctj(Map map) {
		String sql = "update zc_zcqcb set (qcqk,syzk,pyyy,pkyy) = (select ?,?,?,? from dual) where qcbh = ? and yqbh = ?";
		return db.update(sql, new Object[]{Validate.isNullToDefault(map.get("qcqk"), ""),
											Validate.isNullToDefault(map.get("qcxz"), ""),
											Validate.isNullToDefault(map.get("pyyy"), ""),
											Validate.isNullToDefault(map.get("pkyy"), ""),
											Validate.isNullToDefault(map.get("qcbh"), ""),
											Validate.isNullToDefault(map.get("zcbh"), "")}) > 0;
	}
	/**
	 * 获取自查编号
	 * @return
	 */
	public String getzcbh(){
		return db.queryForSingleValue("select qcbh from zc_zczczt where zt = '1'");
	}
	/**
	 * 保存或提交资产自查
	 * @return
	 */
	public boolean zczctj(Map map,PageData pd) {
		String sql = "";
		String op = Validate.isNullToDefaultString(map.get("caozuo"), "0");
		String qcqk = Validate.isNullToDefaultString(map.get("zczt"), "");
		String bfyy = Validate.isNullToDefaultString(map.get("bfyy"), "");
		if("1".equals(op) && "2".equals(qcqk) && Validate.isNull(bfyy)){//提交时清查情况是不符，并且没有录入不符原因
			map.put("errmsg", "请录入不符原因！");
			return false;
		}
		else{
			String zcbh = Validate.isNullToDefaultString(map.get("zcbh"), "");
			sql = "update zc_zczcb set (qcqk,bfyy) = (select ?,? from dual) where qcbh in (select qcbh from zc_zczczt where zt = '1') and yqbh = ?";
			int i = db.update(sql, new Object[]{qcqk,bfyy,zcbh});
			if(i > 0){
				if("1".equals(op)){
					String rybh = map.get("userId").toString();
					pd.put("rybh", rybh);
					pd.put("xm",RyxxByRybh(rybh).get("XM"));
					pd.put("mkbh", MenuFlag.ZCQC_ZCZC);
					pd.put("keyid", zcbh);
					pd.put("sjdjk","sjdjk");
					if(shenheDao.doSubmit(pd)){
						map.put("errmsg", "提交成功！");
						return true;
					}
					else{
						map.put("errmsg", "提交失败！");
						return false;
					}
				}
				else{
					map.put("errmsg", "保存成功！");
					return true;
				}
			}
			else{
				map.put("errmsg", "保存失败！");
				return false;
			}
		}
	}
	/**
	 * 获取常见问题的详细信息
	 * @param wtbh
	 * @return
	 */
	public Map cjwtlbxq(String wtbh){
		return db.queryForMap("select guid,title,xx,to_char(upddate,'yyyy-mm-dd hh24:mi') upddate,jdr,jdrq from zc_cjwtb where guid = ?",new Object[]{wtbh});
	}
	/**
	 * 获取业务说明的详细信息
	 * @param wtbh
	 * @return
	 */
	public Map ywsmxq(String ywbh){
		return db.queryForMap("select ywsm from zc_ywsm where id = '" + ywbh + "'","ywsm");
	}
	/**
	 * 提交存放地点变动信息
	 * @return
	 */
	public boolean cfddbdtj(PageData pd,Map map){
		List<String> sqllist = new ArrayList<String>();
		List<Object[]> parlist = new ArrayList<Object[]>();
		String rybh = map.get("userId").toString();
		String cfdd = Validate.isNullToDefaultString(map.get("cfdd"), "");
		String bdyy = Validate.isNullToDefaultString(map.get("bdyy"), "");
		if(Validate.isNull(cfdd)){
			map.put("errmsg", "请填写变动后存放地点！");
			return false;
		}
//		else if(Validate.isNull(bdyy)){
//			map.put("errmsg", "请填写变动原因！");
//			return false;
//		}
		else{
			String bdbgbh = AutoKey.createKey("ZC_BDBGB","bdbgbh","4");
			map.put("bdbgbh", bdbgbh);
			sqllist.add("insert into zc_bdbgb(bdbgbh,bdrq,hcfdd,bdyy,rybh,dwbh,zi,hao,jdr,jdrq,bdrxm,dwmc,gkry,ztbz,djbz) select ?,sysdate,?,?,?,r.dwbh,?,?,?,sysdate,r.ryxm,r.dwmc,?,?,? from dual,(select xm,dwbh,(select mc from gx_sys_dwb d where d.dwbh = r.dwbh) dwmc,'('||rygh||')'||xm ryxm from gx_sys_ryb r where rybh = ?) r ");
			parlist.add(new Object[]{bdbgbh,cfdd,bdyy,rybh,"变",bdbgbh,rybh,"",StateManager.ZCBD_GK_TG,StateManager.BDXM_CFDDBD,rybh});
			
			List zclist = (List)map.get("bdzcbhlist");
			for(int i = 0; i < zclist.size(); i++){
				String yqbh = (String)zclist.get(i);
				
				if(db.queryForObject("select count(*) from zc_zjb z where z.yqbh = ?",new Object[]{yqbh},Integer.class) == 0){
					map.put("errmsg", "资产信息有误，请核实后提交！");
					return false;
				}
				else{
					sqllist.add("insert into zc_bdb(bdbh,bz,fjbh,bdxm,yqmc,bdbz,bdrq,sydw,flh,flmc,bdbgbh,syr,dj,zzj,gzrq,syfx,bzxx,xz,sykh,jldw,sccj,xss,fjgg,fjxh,pzh,jdr,jdrq,xmbz,bdqnr,bdhnr,bdyy,jzrq) select sys_guid(),?,yqbh,?,yqmc,?,sysdate,sydw,flh,flmc,?,syr,dj,zzj,gzrq,syfx,bzxx,xz,sykh,jldw,sccj,xss,gg,xh,pzh,?,sysdate,?,bzxx,?,?,sysdate from zc_zjb where yqbh = ?");
					parlist.add(new Object[]{Constant.BDBZ_ZJ,"存放地点",StateManager.ZCBD_GK_TG,bdbgbh,rybh,StateManager.BDXM_CFDDBD,cfdd,bdyy,yqbh});
					
					sqllist.add("update zc_zjb set bzxx = ? where yqbh = ?");
					parlist.add(new Object[]{cfdd,yqbh});
				}
			}
			if(db.batchUpdate(sqllist, parlist)){
				map.put("errmsg", "操作成功！");
				return true;
			}
			else{
				map.put("errmsg", "操作失败！");
				return false;
			}
		}
	}

	/**
	 * 提交使用人变动信息
	 * @return
	 */
	public boolean syrbdtj(PageData pd,Map map){
		List<String> sqllist = new ArrayList<String>();
		List<Object[]> parlist = new ArrayList<Object[]>();
		String rybh = map.get("userId").toString();
		String syr = Validate.isNullToDefaultString(map.get("bdhsyr"), "");
		String bdyy = Validate.isNullToDefaultString(map.get("bdyy"), "");
		List zclist = (List)map.get("bdzcbhlist");
		int zccnt = zclist.size();
		if(zccnt == 0){
			map.put("errmsg", "请先选择资产！");
			return false;
		}
		else if(Validate.isNull(syr)){
			map.put("errmsg", "请填写变动后使用人！");
			return false;
		}
		else if(Validate.isNull(bdyy)){
			map.put("errmsg", "请填写变动原因！");
			return false;
		}
		else{
			String bdbgbh = AutoKey.createKey("ZC_BDBGB","bdbgbh","4");
			pd.put("keyid", bdbgbh);
			
			sqllist.add("insert into zc_bdbgb(bdbgbh,bdrq,hrybh,bdyy,rybh,dwbh,zi,hao,jdr,jdrq,bdrxm,dwmc,gkry,ztbz,djbz) select ?,sysdate,?,?,?,r.dwbh,?,?,?,sysdate,r.ryxm,r.dwmc,?,?,? from dual,(select xm,dwbh,(select mc from gx_sys_dwb d where d.dwbh = r.dwbh) dwmc,'('||rygh||')'||xm ryxm from gx_sys_ryb r where rybh = ?) r ");
			parlist.add(new Object[]{bdbgbh,syr,bdyy,rybh,"变",bdbgbh,rybh,"",StateManager.ZCBD_LR,StateManager.BDXM_SYRBD,rybh});
			
			String[] zcarr = new String[zccnt];
			for(int i = 0; i < zccnt; i++){
				zcarr[i] = (String)zclist.get(i);
			}
			String zcbh = ToSqlUtil.getInsql(zcarr);
					
			if(db.queryForObject("select count(*) from zc_zjb z where z.yqbh"+zcbh,zcarr,Integer.class) != zccnt){
				map.put("errmsg", "资产信息有误，请核实后提交！");
				return false;
			}
			else{
				String[] bdarr = new String[zccnt + 7];
				bdarr[0] = Constant.BDBZ_ZJ;
				bdarr[1] = StateManager.ZCBD_LR;
				bdarr[2] = bdbgbh;
				bdarr[3] = rybh;
				bdarr[4] = StateManager.BDXM_SYRBD;
				bdarr[5] = syr;
				bdarr[6] = bdyy;
				System.arraycopy(zcarr, 0, bdarr, 7, zcarr.length);
				
				sqllist.add("insert into zc_bdb(bdbh,bz,fjbh,bdxm,yqmc,bdbz,bdrq,sydw,flh,flmc,bdbgbh,syr,dj,zzj,gzrq,syfx,bzxx,xz,sykh,jldw,sccj,xss,fjgg,fjxh,pzh,jdr,jdrq,xmbz,bdqnr,bdhnr,bdyy,jzrq) select sys_guid(),?,yqbh,'使用人',yqmc,?,sysdate,sydw,flh,flmc,?,syr,dj,zzj,gzrq,syfx,bzxx,xz,sykh,jldw,sccj,xss,gg,xh,pzh,?,sysdate,?,syr,?,?,sysdate from zc_zjb where yqbh" + zcbh);
				parlist.add(bdarr);

				sqllist.add("update zc_zjb set bdzt = '" + StateManager.BDZT_XMBD + "' where yqbh" + zcbh);
				parlist.add(zcarr);
				
				return db.batchUpdate(sqllist, parlist);
			}
		}
	}
	/**
	 * 删除使用人变动信息
	 * @param bdbgbh
	 * @return
	 */
	public boolean delsyrbd(String bdbgbh){
		List<String> sqllist = new ArrayList<String>();
		List<Object[]> parlist = new ArrayList<Object[]>();
		
		sqllist.add("update zc_zjb set bdzt = ? where yqbh in (select fjbh from zc_bdb where bdbgbh = ?)");
		parlist.add(new Object[]{StateManager.BDZT_Normal,bdbgbh});
		sqllist.add("delete from zc_bdb where bdbgbh = ?");
		parlist.add(new Object[]{bdbgbh});
		sqllist.add("delete from zc_bdbgb where bdbgbh = ?");
		parlist.add(new Object[]{bdbgbh});
		
		return db.batchUpdate(sqllist, parlist);
	}
	
	/**
	 * 获取资产认领（领用人确认）详情
	 * @return
	 */
	public Map zcrlxq(String bdbgbh){
		Map map = new HashMap();
		map = db.queryForMap("select bdbgbh,to_char(bdrq,'yyyy-mm-dd') bdrq,hdwbh,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = b.hdwbh) hdwbhmc,hrybh,(select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = b.hrybh) hrybhmc,hsyfx,(select mc from gx_sys_dmb d where zl = '" + Constant.SYFX + "' and d.dm = b.hsyfx) hsyfxmc,hcfdd,(select '('||ddh||')'||mc from zc_sys_ddb d where d.ddbh = b.hcfdd) hcfddmc,hxz,(select mc from gx_sys_dmb d where zl in ('" + Constant.XZ + "','" + Constant.HXZ + "') and d.dm = b.hxz) hxzmc,bdyy from zc_bdbgb b where bdbgbh = ?", new Object[]{bdbgbh});
		String sql = "select fjbh,yqmc,decode(nvl(zzj, 0),0,'0.00',(to_char(round(zzj, 2),'fm99999999999990.00'))) zzj,bzxx,(select '('||ddh||')'||mc from zc_sys_ddb d where d.ddbh = b.bzxx) bzxxmc,(case when substr(flh,1,2) in ('03','04','05','06','07','08','11','12','13','14','15') and substr(flh,1,4) not in ('0413','0711') then to_char(fjgg) else '' end) fjgg,(case when substr(flh,1,2) in ('03','04','05','06','07','08','11','12','13','14','15') and substr(flh,1,4) not in ('0413','0711') then to_char(fjxh) else '' end) fjxh from zc_bdb b where bdbgbh = ? and bz = ? group by fjbh,yqmc,flh,zzj,bzxx,fjgg,fjxh order by fjbh";
		List list = db.queryForList(sql, new Object[]{bdbgbh,Constant.BDBZ_ZJ});
		map.put("list", list);
		return map;
	}

	/**
	 * 提交处置申请信息
	 * @return
	 */
	public boolean scczsqd(PageData pd,Map map){
		String rybh = map.get("userId").toString();
		String czyy = Validate.isNullToDefaultString(map.get("czyy"), "");
		List zclist = (List)map.get("zclb");
		int zccnt = zclist.size();
		if(zccnt == 0){
			map.put("errmsg", "请先选择资产！");
			return false;
		}
		else if(Validate.isNull(czyy)){
			map.put("errmsg", "请填写处置原因！");
			return false;
		}
		else{
			String sqbh = AutoKey.createKey("zc_czsqb", "sqbh", "4");
			pd.put("keyid", sqbh);

			List<String> sqllist = new ArrayList<String>();
			List<Object[]> parlist = new ArrayList<Object[]>();
			sqllist.add("insert into zc_czsqb(sqbh,ztbz,sqdw,sqr,sqrq,czyy,jdr,jdrq) values(?,?,(select dwbh from gx_sys_ryb where rybh = ?),?,sysdate,?,?,sysdate)");
			parlist.add(new Object[]{sqbh,StateManager.ZCCZ_LR,rybh,rybh,czyy,rybh});
			
			String[] zcarr = new String[zccnt];
			for(int i = 0; i < zccnt; i++){
				zcarr[i] = (String)zclist.get(i);
			}
			String zcbh = ToSqlUtil.getInsql(zcarr);
			
			if(db.queryForObject("select count(*) from zc_zjb z where z.yqbh"+zcbh,zcarr,Integer.class) != zccnt){
				map.put("errmsg", "资产信息有误，请核实后提交！");
				return false;
			}
			else{
				sqllist.add("insert into zc_czsqmxb(sqmxbh,sqbh,yqbh,yqmc,flh,flmc,gzrq,rzrq,syr,sydw,bzxx,xh,gg,zzj,ztbz,jdr,jdrq,sfczd) " +
						"select sys_guid(),'" + sqbh + "',yqbh,yqmc,flh,flmc,gzrq,rzrq,syr,sydw,bzxx,xh,gg,zzj,'" + StateManager.ZCCZ_LR + "','" + rybh + "',sysdate,'0' from zc_zjb where yqbh"+zcbh);
				parlist.add(zcarr);
				sqllist.add("update zc_zjb set bdzt = '" + StateManager.BDZT_CZ + "' where yqbh"+zcbh);
				parlist.add(zcarr);
				sqllist.add("update zc_czsqb set zje = (select sum(zzj) from zc_czsqmxb where sqbh = ?) where sqbh = ?");
				parlist.add(new Object[]{sqbh,sqbh});
				
				return db.batchUpdate(sqllist, parlist);
			}
		}
	}
	/**
	 * 删除处置申请
	 * @param sqbh
	 * @return
	 */
	public boolean delczsq(String sqbh){
		List<String> sqllist = new ArrayList<String>();
		List<Object[]> parlist = new ArrayList<Object[]>();
		
		sqllist.add("update zc_zjb set bdzt = ? where yqbh in (select yqbh from zc_czsqmxb where sqbh = ?)");
		parlist.add(new Object[]{StateManager.BDZT_Normal,sqbh});
		sqllist.add("delete from zc_czsqmxb where sqbh = ?");
		parlist.add(new Object[]{sqbh});
		sqllist.add("delete from zc_czsqb where sqbh = ?");
		parlist.add(new Object[]{sqbh});
		
		return db.batchUpdate(sqllist, parlist);
	}

	/**
	 * 提交闲置资产领用信息
	 * @return
	 */
	public boolean xzzclytj(PageData pd,Map map){
		String rybh = map.get("userId").toString();
		String sqyy = Validate.isNullToDefaultString(map.get("sqyy"), "");
//		if(Validate.isNull(sqyy)){
//			map.put("errmsg", "请填写申请原因！");
//			return false;
//		}
//		else{
			String sqbh = AutoKey.createKey("ZC_XZTJ_LYSQB", "SQBH", "4");
			pd.put("keyid", sqbh);

			List<String> sqllist = new ArrayList<String>();
			List<Object[]> parlist = new ArrayList<Object[]>();
			sqllist.add("insert into zc_xztj_lysqb(sqbh,sqry,sqdw,sqrq,sqyy,ztbz) values(?,?,(select dwbh from gx_sys_ryb r where rybh = ?),sysdate,?,?)");
			parlist.add(new Object[]{sqbh,rybh,rybh,sqyy,StateManager.LYSQ_LR});
			
			String sql = "insert into zc_xztj_lysqmxb(guid,sqbh,yqbh,yqmc,dj,zzj,cjje,syfx,syr,sydw,ztbz,sqmxbh) " +
					"select sys_guid(),?,yqbh,yqmc,dj,zzj,crje,syfx,syr,sydw,?,sqmxbh from zc_xztj_gsb where yqbh = ? and sqmxbh = ?";
			List zclist = (List)map.get("zclist");
			Map zcmap;
			for(int i = 0; i < zclist.size(); i++){
				zcmap = (Map)zclist.get(i);
				String yqbh = Validate.isNullToDefaultString(zcmap.get("zcbh"),"");
				String sqmxbh = Validate.isNullToDefaultString(zcmap.get("sqmxbh"),"");
				
				sqllist.add(sql);
				parlist.add(new Object[]{sqbh,StateManager.LYSQ_LR,yqbh,sqmxbh});
				
				sqllist.add("update zc_xztj_gsb set sfgs = '1' where yqbh = ? and sqmxbh = ?");
				parlist.add(new Object[]{yqbh,sqmxbh});

				sqllist.add("update zc_zjb set bdzt = ? where yqbh = ?");
				parlist.add(new Object[]{StateManager.BDZT_XZLY,yqbh});
			}
			
			return db.batchUpdate(sqllist, parlist);
//		}
	}
	
	/**
	 * 根据教育部十六大类获取财政分类
	 * @param flh
	 * @param flmc
	 * @return
	 */
	public Map getCzflByJyfl(String flh,String flmc){
		String sql = "select (select bzdm from zc_flb_czbn c where c.flh = f.ffldm) bzdm,ffldm flh,fflmc flmc from zc_flb_jyb f where flh = ? and flmc = ?";
		return db.queryForMap(sql, new Object[]{flh,flmc});
	}
	/**
	 * 提交设备入账卡片信息
	 * @return
	 */
	public boolean sbjzzcsave(ZC_ZJBEXTEND ysd){
		List<zc_zjb> zclist = ysd.getZjbmx();
		List sqllist = new ArrayList();
		List parlist = new ArrayList();
		for(zc_zjb zjb : zclist){
			sqllist.add("update zc_zjb set (syr,sydw,syfx,bzxx,jsh,cch)  = (select ?,?,?,?,?,? from dual) where yqbh = ?");
			parlist.add(new Object[]{zjb.getSyr(),zjb.getSydw(),zjb.getSyfx(),zjb.getBzxx(),zjb.getJsh(),zjb.getCch(),zjb.getYqbh()});
		}
		return db.batchUpdate(sqllist, parlist);
	}

	/**
	 * 提交家具入账卡片信息
	 * @return
	 */
	public boolean jjjzzcsave(ZC_ZJBEXTEND ysd){
		List<zc_zjb> zclist = ysd.getZjbmx();
		List sqllist = new ArrayList();
		List parlist = new ArrayList();
		for(zc_zjb zjb : zclist){
			sqllist.add("update zc_zjb set (syr,sydw,syfx,bzxx,sykh)  = (select ?,?,?,?,? from dual) where yqbh = ?");
			parlist.add(new Object[]{zjb.getSyr(),zjb.getSydw(),zjb.getSyfx(),zjb.getBzxx(),zjb.getSykh(),zjb.getYqbh()});
		}
		return db.batchUpdate(sqllist, parlist);
	}
	
	/**
	 * 获取业务审核列表信息
	 */
	public List getShxx(String guid) {
//		String sql = "select k.dbh,'【'||sqmkmc||'】等待【'||mkmc||'】' mc,tjrxm tjr,(select url from "+TnameU.MKB+" m where m.mkbh = k.mkbh) tzlj,mkbh,sqmkbh,mkmc from zc_sys_shcshb k where k.rybh=? and sfdqjd = '1' and (substr(mkbh,1,2) = '" + MenuFlag.SHCD + "') order by dbh";
//		List list = db.queryForList(sql,new Object[]{rybh});
//		return list;
		String sql = "select * from ( select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh,to_char(b.create_time_,'yyyy-mm-dd') time ,b.create_time_,'出差业务审核' type "
				+ "from cw_ccsqspb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '130202' where b.assignee_ = ? and b.task_def_key_ <> 'sqr'"
				+" union "
				+" select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh,to_char(b.create_time_,'yyyy-mm-dd') time,b.create_time_, '公务接待审批' type "
				+ "from cw_gwjdywsqspb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.rybh left join zc_sys_mkb d on d.mkbh = '130102' where b.assignee_ = ? and b.task_def_key_ <> 'sqr'"
				+" union "
				+" select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh,to_char(b.create_time_,'yyyy-mm-dd') time,b.create_time_, '日常' type "
				+ "from cw_rcbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_"
				+ " left join gx_sys_ryb c on a.bxr = c.guid left join zc_sys_mkb d on d.mkbh = '110203' where b.assignee_ = ? and b.task_def_key_ <> 'sqr'"
				+" union "
				+" select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh,to_char(b.create_time_,'yyyy-mm-dd') time,b.create_time_, '差旅费' type "
				+ "from cw_clfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '110302' where b.assignee_ = ? and b.task_def_key_ <> 'sqr' "
				+" union "
				+" select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,mkbh,substr(mkbh,0,4) as sjmkbh,substr(mkbh,0,2) as rootmkbh,to_char(b.create_time_,'yyyy-mm-dd') time,b.create_time_, '公务接待' type "
				+ "from cw_gwjdfbxzb a left join  act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.bxryid = c.guid left join zc_sys_mkb d on d.mkbh = '111102' where b.assignee_ = ? and b.task_def_key_ <> 'sqr') where rownum <4 order by create_time_ desc";
		Object[] obj = {guid,guid,guid,guid,guid};
		return db.queryForList(sql,obj);
	}
	public List getXmlist() {
		String str = " select  *  from  ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbh,"
				+ " x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc, x.xmlb,"
				+ "(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc, (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,"
				+ "(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc, x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) "
				+ "fzrmc,x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc, x.gkbm,"
				+ "('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc, x.sfqy,"
				+ "(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc  from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh "
				+ "left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh "
				+ "left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh ) k where 1=1   order by XMBH asc ";
		return db.queryForList(str);
	}
	public String getWorkUrl(String guid,String procinstid,String type) {
		String ifrurl = Const.furl+"";
		String suffix = "&guid="+guid+"&procinstid="+procinstid;
		switch(type) {
			case "出差业务审核": ifrurl += "/wsbx/ccyw/ccywsq/goCcywsqViewPage?action=sh&link=sh";
				break;
			case "公务接待审核":
				ifrurl += "/gwjdfsh/goEditPage?operateType=U";
				break;
			case "日常":
				ifrurl += "/wsbx/rcbx/operate?operate=CK";
				break;
			case "差旅费":
				ifrurl += "/wsbx/ccbx/goViewJsp?type=sh";
				break;
			case "公务接待":
				ifrurl += "/wsbx/gwjdfbx/gwjdfbxsq/pageSkip?pageName=gwjdmx_view&action=sh&link=sh";
				break;
			default:
				break;
			}
		ifrurl += suffix;
		return ifrurl;
	}
	public String getWorkUrl(String xh) {
		String ifrurl = Const.furl+"";
		String suffix = "?xh="+xh;
		ifrurl += "/phone/goMain";
		ifrurl += suffix;
		return ifrurl;
	}
	
	public String gettzgglistUrl() {
		String ifrurl = Const.furl+"";
		String suffix = "";
		ifrurl += "/index/goMain";
		ifrurl += suffix;
		return ifrurl;
	}
	public String gettzggsearchlistUrl(String guid) {
		String ifrurl = Const.furl+"";
		String suffix = "?xh="+guid;
		ifrurl += "/phone/goMain";
		ifrurl += suffix;
		return ifrurl;
	}
	
	/**
	 * 判断必填项是否都已填写
	 * @param ysdh
	 * @return
	 */
	public boolean zcjzbtx(String ysdh){
		String sql = "select sum(cnt) from (" +
				"select count(*) cnt from zc_yshd where (yqmc is null or flh is null or yqbh is null or gbmid is null or flgbm is null or dj is null or shl is null or zzj is null or shgdw is null or dzrrq is null or yshrq is null) and ysdh = ?" +
				" union all " +
				"select count(*) cnt from zc_zjb where (yqbh is null or yqmc is null or syr is null or sydw is null or syfx is null or bzxx is null or dj is null or zzj is null) and ysdh = ? group by ysdh" +
				")";
		String cnt = db.queryForSingleValue(sql,new Object[]{ysdh,ysdh});
		return "0".equals(cnt);
	}
	
/***************************************************************************公共部分  start**********************************************************************************/
	/**
	 * 手机端显示的审核流程中的申请模块编号
	 */
	public String getSqmkbh(){
		return "'" + MenuFlag.ZCJZ_LYR + "','" + MenuFlag.ZCJZ_GLY + "','" + MenuFlag.ZCBD_DWNDB + "','" + MenuFlag.ZCBD_DWJDB + "','" + MenuFlag.ZCBD_SYRBD + "','" + MenuFlag.ZCBD_CFDDBD + "','" + MenuFlag.ZCBD_BFDB + "','" + MenuFlag.ZCBD_XMBD + "','" + MenuFlag.ZCBD_DJBD + "','" + MenuFlag.ZCBD_FJZJ + "','" + MenuFlag.ZCCZ_CZSQ + "','" + MenuFlag.ZCCZ_GLYHZ + "','" + MenuFlag.ZCCZ_ZCCZ + "','" + MenuFlag.ZCBD_BFBF + "','" + MenuFlag.ZCBD_FJCZ + "','" + MenuFlag.ZCWX_WXSQ + "','" + MenuFlag.ZCWX_WXBG + "','" + MenuFlag.ZCWX_JFZJ + "'";
	}
/***************************************************************************公共部分  end**********************************************************************************/
	
	
	/*****************************扫描二维码上传图片 start***********************************/
	/**
	 * 扫码上传图片
	 * @param fj
	 * @return
	 */
	public boolean doUploadSmsc(ZC_XGWD fj){
		String sql = "insert into zc_xgwd(guid,djlx,dbh,filename,rybh,scsj,path) values (?,?,?,?,?,sysdate,?) ";
		Object[] obj = new Object[]{
				fj.getGuid(),
				fj.getDjlx(),
				fj.getDbh(),
				fj.getFilename(),
				fj.getRybh(),
				fj.getPath()
		};
		return db.update(sql, obj) > 0;
	}
	/**
	 * 删除扫码上传的图片
	 * @param tpid
	 * @return
	 */
	public boolean doDelSmsc(String id) {
		return db.update("delete from zc_xgwd where guid = ?", new Object[]{id}) > 0;
	}
	/*****************************扫描二维码上传图片 end***********************************/
	
	/**
	 * 根据消费地点查询承包商的guid
	 * @param guid
	 * @return
	 */
	public String getCbsGuidByXfddGuid(String guid){
		String sql = "select guid from cw_cbsxx where cbsbh in(select SSCBSBH from cw_xfddgl where guid=?)";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{guid}),"");
	}
}