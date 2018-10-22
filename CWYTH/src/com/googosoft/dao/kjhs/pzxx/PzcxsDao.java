package com.googosoft.dao.kjhs.pzxx;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.googosoft.ccb.TestFileUpload;
import com.googosoft.ccb.TestXMLUpload;
import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.fzgn.jcsz.DwbDao;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.FileUtil;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.controller.base.BaseController;

@Repository("pzcxsDao")
public class PzcxsDao  extends BaseDao{
	private Logger logger = Logger.getLogger(DwbDao.class);	
	/**
	 * 查询凭证录入
	 * */
	public Map<String, Object> selectPzlrzb(String pzz,String pzbh,String sszt){
	//	String sql = "select 	(select mc from gx_sys_dmb where zl = 'pzly' and dm = a.pzly)as pzlymc ," + 
	//			"				(select xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.cn) as cnr," + 
	//			"				guid, pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
	//			+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and a.pzz = '"+pzz+"' and sszt = '"+sszt+"'";
		String sql = "select 	(select mc from gx_sys_dmb where zl = 'pzly' and dm = a.pzly)as pzlymc ," + 
				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.cn) as cnr," + 
				"				guid,sszt,pzzt,pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
				+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and sszt = '"+sszt+"'";
		return db.queryForMap(sql);
	}
	public List<Map<String, Object>> selectPzlrmxAndFzlr(String pzz,String pzbh,String sszt){
		/*String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = b.dfdw) as dfdw,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and "
				+ "pzz = '"+pzz+"' and sszt = '"+sszt+"')";*/
		String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.dfdw) as dfdw,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "(select '('||kmbh||')'||kmmc from CW_GNKMB where  KMBH = b.GNKM) as GNKM,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
				+ " where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"')";
		return db.queryForList(sql);
	}
	
	//获取凭证编号列表
		public List<String> getPzbhList(String pzz,String sszt){
		//	String sql = "select pzbh from cw_pzlrzb where pzz = '"+pzz+"' and sszt = '"+sszt+"' order by pzbh asc";
			String sql = "select pzbh from cw_pzlrzb where sszt = '"+sszt+"' order by pzbh asc";
			return db.queryForList(sql,String.class);
		}
		//获取凭证字列表
		public List<Map<String, Object>> getPzzList(String sszt){
			String sql = "select lxbh,pzz from cw_pzlxb where sszt = '"+sszt+"' order by lxbh";
			return db.queryForList(sql);
		}
		//获取账套会计期间
		public Map<String, Object> getZtXx(String ztid){
			String sql = "select kjnd,qjs from cw_ztxxb where guid = '"+ztid+"'";
			return db.queryForMap(sql);
		}
		
		//查询复核人
		public Object getFhr(String pzbh,String pzz,String sszt){
		//	String sql = "select fhr from cw_pzlrzb where pzbh = '"+pzbh+"' and pzz = '"+pzz+"' and sszt = '"+sszt+"'";
			String sql = "select fhr from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"'";
			return db.queryForObject(sql, String.class);
		}
		//查询借方必有,借方必无，
		public Map<String, Object> getBybwkm(String pzz,String sszt) {
			String sql = "select distinct \r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Jf') as jfbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Df') as dfbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzby') as pzbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzbw') as pzbwkm\r\n" + 
					"from cw_pzlxb t join cw_pzkmdzb s on t.guid = s.pzlxm where t.lxbh = '"+pzz+"' and t.sszt = '"+sszt+"'";
			return db.queryForMap(sql);
		}
		
		//获取最大的凭证编号
		public String getMaxPzbh(String pzz,String sszt) {
		//	String sql = "select max(pzbh) from cw_pzlrzb where pzz = '"+pzz+"' and sszt = '"+sszt+"'";
			String sql = "select max(pzbh) from cw_pzlrzb where sszt = '"+sszt+"'";
			return db.queryForSingleValue(sql);
		}
		public String pzbhwfh(String guid) {
			String sql = "select min(pzbh) from cw_pzlrzb where pzzt = '00'";
			return db.queryForSingleValue(sql);
		}
		/**
		 * 获取凭证类型名称
		 * @return
		 */
		public List<Map<String, Object>> getPzlxmc() {
			String sql = " select * from  cw_pzlxbnew ";
			return db.queryForList(sql);
		}

	//查询凭证明细list
		public List<Map<String,Object>> getpzmxlist(String pzbh){
			List<Map<String,Object>> list=null;
			String sql=" select b.zy,b.kmbh,b.jjfl,(select '(' || jj.kmbh || ')' || jj.kmmc from cw_jjkmb jj where jj.kmbh = b.jjfl ) as jjmc,km.kmmc as kmmc,(select xm.xmmc from cw_xmjbxxb xm where xm.bmbh=b.bmbh and xm.xmbh=b.xmbh )as xmmc,dw.mc as bmmc,b.bmbh,b.xmbh, to_char(round(b.jfje, 2), 'fm9999999999990.00') as jfje, to_char(round(b.dfje, 2), 'fm9999999999990.00')as  dfje from CW_PZLRmxb b  left join cw_kjkmszb km on km.kmbh=b.kmbh left join gx_sys_dwb dw on dw.dwbh=b.bmbh   where b.pzbh='"+pzbh+"' ORDER BY B.JFJE";
			try {
				list=db.queryForList(sql);
			} catch (Exception e) {
				// 异常
			}
			return list;
		}
		
	public int updatesomef(String guid) {
		String sql = " update cw_pzlrzb z set z.dczt = '1' where 1=1 and z.guid in \r\n" + 
				"(select pzbh from cw_pzlrmxb mx where mx.guid in (select mxid from cw_pzlryhmx yh where yh.guid in ('"+guid+"'))) ";
//		if(Validate.noNull(guid)){
//			sql = sql + " and z.guid in ('"+guid+"') ";
//		}
		return db.update(sql);
	}
	
	public int updatesomefk(String guid) {
		String sql1  = " update cw_pzlryhmx z set z.status = '1',dczt='1' where z.guid in ('"+guid+"') ";
		String sql2="update cw_fzlrb fz set fz.zfzt = '1',fz.dczt='1' where fz.guid in ('"+guid+"')";
//		sqls[1] = "update cw_fzlrb  z set z.status = '1' where 1=1 ";
		int i=0;
		try {
			//零余额是联合查询   如果第一个表没有返回行数，那么更新第二个标的数据
			i=db.update(sql1);
			if(i==0) {
				i=db.update(sql2);
			}
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return 1;
	}
	
	public int fanhuisj(String guid) {
		String sql1  = " update cw_pzlryhmx z set z.status = '0' where z.guid in ('"+guid+"') ";
		String sql2="update cw_fzlrb fz set fz.zfzt = '0' where fz.guid in ('"+guid+"') ";
		int i=0;
		try {
			//零余额是联合查询   如果第一个表没有返回行数，那么更新第二个标的数据
			i=db.update(sql1);
			if(i==0) {
				i=db.update(sql2);
			}
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return 1;
	}
	//凭证支付
	public int zfztxgupdate(String guid,String je,String kmbh,String xmbh,String bmbh,String restime,String vchid) {
		String sql1="update cw_pzlryhmx set status='1',RES_TIME='"+restime+"',vchid='"+vchid+"' where guid='"+guid+"' ";
		int a=0;
		a=db.update(sql1);
//		if(Validate.noNull(xmbh)) {
//			String sql2=" update cw_xmjbxxb set syje=syje-to_number('"+je+"') where 1=1 and bmbh='"+bmbh+"' and xmbh='"+xmbh+"' ";
//			db.update(sql2);
//		}
		return a;
	}
	//批量支付 项目不为空 减钱
	public int plzfjq(String guid,String je,String kmbh,String xmbh,String bmbh,String restime,String vchid) {
		String guids [] =guid.split(",");
		int a=0;
		String sql1="";
		for(int j=0;j<guids.length;j++){
			sql1="update cw_pzlryhmx set status='1' ,RES_TIME='"+restime+"',vchid='"+vchid+"' where guid in('"+guids[j]+"') ";
			a=db.update(sql1);
		}
//		if(Validate.noNull(xmbh)) {
//			String kmbhs []=kmbh.split(",");
//			String jes []=je.split(",");
//			String xmbhs []=xmbh.split(",");
//			String bmbhs []=bmbh.split(",");
//			for(int i=0;i<xmbhs.length;i++) {
//				String sql2=" update cw_xmjbxxb set syje=syje-to_number('"+jes[i]+"') where 1=1 and bmbh='"+bmbhs[i]+"' and xmbh='"+xmbhs[i]+"' ";
//				db.update(sql2);
//			}
//		}
		return a;
	}
	/**
	 * 银行支付接口的xml
	 * @param guid
	 * @return
	 */
	public String plzfjk(String guid,String result,String fileMD5,String filename) {
//		System.out.println("%%%"+result);
		int bs = guid.split(",").length;
		String d="";
		String e="";
		//总金额，总笔数
		String money = getSumMoney(guid);
		money = money + ".00";
		//格式化时间戳
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String sysDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String sysMinute = new SimpleDateFormat("HHmmss").format(new Date());
		//获取本机的ip地址
		 InetAddress ia=null;
		//客户序列号   Txn_SN  随机生成18位字符串
		 String xlh = UuidUtil.get32UUID();
		 String khxlh = xlh.substring(1, 19);
		 
		 String sql = " update cw_pzlryhmx set REQ_TIME='"+sysTime+"',TXNSN='"+khxlh+"' where guid in ('"+CommonUtils.pointToSql(guid)+"')";
		 db.execute(sql);
		 
        try {
            ia=ia.getLocalHost();
            String localname=ia.getHostName();
            String localip=ia.getHostAddress();
//            System.out.println("本机名称是："+ localname);
//            System.out.println("本机的ip是 ："+localip);
	    String a="<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
			+ "<Transaction>"
			+ "<Transaction_Header>"
			+ "<SYS_TX_CODE><![CDATA[P1CLP1051]]></SYS_TX_CODE>"
			+ "<SYS_MSG_LEN><![CDATA[]]></SYS_MSG_LEN>"
			+ "<SYS_REQ_TIME><![CDATA["+sysTime+"]]></SYS_REQ_TIME>"
			+ "<SYS_TX_VRSN><![CDATA[01]]></SYS_TX_VRSN>"
			+ "<TXN_DT><![CDATA["+sysDate+"]]></TXN_DT>"
			+ "<TXN_TM><![CDATA["+sysMinute+"]]></TXN_TM>"
			+ "<TXN_STFF_ID><![CDATA[333333]]></TXN_STFF_ID>"
			+ "<MULTI_TENANCY_ID><![CDATA[CN000]]></MULTI_TENANCY_ID>"
			+ "<LNG_ID><![CDATA[zh-cn]]></LNG_ID>"
			+ "<REC_IN_PAGE><![CDATA[]]></REC_IN_PAGE>"
			+ "<PAGE_JUMP><![CDATA[]]></PAGE_JUMP>"
			+ "<STS_TRACE_ID><![CDATA[]]></STS_TRACE_ID>"
			+ "<CHNL_CUST_NO><![CDATA[SD37000009021270501]]></CHNL_CUST_NO>"
			+ "<IttParty_Jrnl_No><![CDATA[]]></IttParty_Jrnl_No>"
			+ "<Txn_Itt_IP_Adr><![CDATA["+localip+"]]></Txn_Itt_IP_Adr>"
			+ "</Transaction_Header>"
			+ "<Transaction_Body>";
	   String b="<request>"
			+"<FILE_LIST_PACK><FILE_NUM><![CDATA["+bs+"]]></FILE_NUM><FILE_INFO><FILE_NAME><![CDATA["+result+"]]></FILE_NAME><Msg_Smy><![CDATA["+fileMD5+"]]></Msg_Smy></FILE_INFO></FILE_LIST_PACK>" 
			   
			   
			+ "<Txn_SN><![CDATA["+khxlh+"]]></Txn_SN>"
			+ "<EBnk_SvAr_ID><![CDATA[SD37000009021270501]]></EBnk_SvAr_ID>"
			+ "<Entrst_Prj_ID><![CDATA[370130066]]></Entrst_Prj_ID>"
			+ "<Prj_Use_ID><![CDATA[zldf00001]]></Prj_Use_ID>"
			+ "<EtrUnt_AccNo><![CDATA[37001618801050149199]]></EtrUnt_AccNo>"
			+ "<TDP_ID><![CDATA[98229385]]></TDP_ID>"
			+ "<TDP_NM><![CDATA[]]></TDP_NM>"
			+ "<CntprtAcc><![CDATA[]]></CntprtAcc>"
			+ "<Cntrprt_AccNm><![CDATA[]]></Cntrprt_AccNm>"
			+ "<IwBk_Brno><![CDATA[]]></IwBk_Brno>"
			+ "<IwBk_BkNm><![CDATA[]]></IwBk_BkNm>"
			+ "<MltltAgrm_No><![CDATA[]]></MltltAgrm_No>"
			+ "<CCY_ID><![CDATA[156]]></CCY_ID>"
			+ "<Orig_File_Nm><![CDATA["+filename+"]]></Orig_File_Nm>"
			+ "<SRP_TxnAmt><![CDATA[]]></SRP_TxnAmt>"
			+ "<SCSP_Smy_Dsc><![CDATA[]]></SCSP_Smy_Dsc>"
			+ "<Rvw_Ind><![CDATA[1]]></Rvw_Ind>"
			+ "<TAmt>"+money+"</TAmt>"
			+ "<TDnum>"+bs+"</TDnum>"
			+ "<VCHR_TP_CODE><![CDATA[1]]></VCHR_TP_CODE>"
			+ "<Lng_Vrsn><![CDATA[1]]></Lng_Vrsn>"
			+ "</request>";
	      d=d+b;
		String c="</Transaction_Body>"
				+ "</Transaction>";
	    e=a+d+c;
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		return e;
	}
	public  String getSumMoney(String guid){
		guid = guid.replace(",", "','");
		String sql = "select sum(nvl(je,0)) from CW_PZLRYHMX where guid in('"+guid+"')";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql), "0");
	}
			
	 public static void main(String[] args) {
	        // TODO Auto-generated method stub
	        InetAddress ia=null;
	        try {
	            ia=ia.getLocalHost();
	             
	            String localname=ia.getHostName();
	            String localip=ia.getHostAddress();
	            System.out.println("本机名称是："+ localname);
	            System.out.println("本机的ip是 ："+localip);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	/**
	 * 列表信息导出txt（可以自定义分隔符）
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param file 导出文件的路径
	 * @param mlist excel的核心组成model的集合
	 * @param errlist 错误信息集合
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 * @param endstr 每个字段结尾的分隔符 默认是空
	 */
	public Map ExpZfxxTxt(String sql, String file, List<M_largedata> mlist, List errlist,String flag,String endstr,HttpServletRequest request,String bankCode,String guid) throws FileNotFoundException, IOException {
		int maxRowNumLast = db.queryForObject("select count(*) from ("+sql+")",Integer.class);
		int expCnt = Integer.parseInt(Validate.isNullToDefault(ResourceBundle.getBundle("global").getString("expCnt_txt"),"10000") + "");
		return txtOutPut(expCnt, maxRowNumLast, sql, file, mlist, errlist, flag, 2, endstr, request,bankCode,guid);
	}

	/**
	 * 生成txt文件方法
	 * @param maxRowNum 每次获取的最大行数
	 * @param maxRowNumLast 获取到的所有数据总行数
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param fileurl 导出文件的路径
	 * @param filename 导出文件的名称
	 * @param mlist txt的核心组成model的集合
	 * @param errlist 错误信息集合
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 * @param hzlx 字符类型  1：一个汉字代表一个字符  2：一个汉字代表两个字符  默认是2
	 * @param endstr 每个字段结尾的分隔符 默认是空
	 */
	public Map txtOutPut(int maxRowNum, int maxRowNumLast, String sql, String fileurl, List<M_largedata> mlist, List errlist,String flag,int hzlx,String endstr,HttpServletRequest request,String bankCode,String guid) throws FileNotFoundException, IOException{		
		System.err.println(fileurl);
		
		long startTimne = System.currentTimeMillis();//获取当前时间
		StringBuffer bodystr;
		int sheets = (int)Math.ceil((float)maxRowNumLast/(float)maxRowNum);//这里必须转成float类型才能出正确结果（否则不会在除不尽时加1）
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileurl),"GBK"));//这里必须是GBK，要不然无法上报
		//String hhf = UserAgentUtils.getLineMark(request);//换行符各个操作系统下不一样
		Map map;
		M_largedata m;
		//查询 转账 支付摘要
		String sqlzy = "select mc from  GX_SYS_DMB t where zl ='zzzy' and dm ='01' ";
		String zfzy = db.queryForSingleValue(sqlzy);
		bodystr = new StringBuffer();
		bodystr.append("\r\n");
		for(int i = 0; i < sheets; i++){//虽然txt没有大小的限制，但是获取到数据在转入list的时候，可能会内存溢出，所以还是分开获取数据比较好
			int s = i * maxRowNum;//每次获取数据开始的条数
			int e = (i + 1) * maxRowNum;//每次获取数据结束的条数
			if(maxRowNumLast < e){
				e = maxRowNumLast;
			}
			
			List list = new ArrayList();
			try{
				list = db.queryForList("select * from (select z.*,rownum rn from (" + sql + ") z where rownum <= " + e + ") where rn > " + s);
			}
			catch(Exception ex){
				logger.error("查询数据库失败，原因是：" + ex.getMessage());
				ex.printStackTrace();
			}
			String bankCodeJsArr []= bankCode.split(","); //js银行标志   bankCode:CCB ||bankCode:CCB,CCB建设银行
			String GuidArr []= guid.split(","); //js银行标志   bankCode:CCB ||bankCode:CCB,CCB建设银行
			//String bankCodeAl ; //ali api的标志
			String khbz; //跨行标志
			int n = list.size();
			for(int j = 0; j < n; j++){
				
				if(j==0){
				//bodystr.append("序号（必填)|账号（必填）|户名（必填)|金额（必填）|跨行标志（必填，建行填“0”，他行填“1”）|人民银行联行号（跨行业务必填）|"
				//		+ "多方协议号（跨行代扣必填）|摘要（选填，显示在账户流水明细中）|备注（选填，显示在处理结果中供业务发起人参考）");
				}
				map = (Map)list.get(j);
				System.out.println("**"+mlist);
				for(int c = 0; c < mlist.size(); c++){
					m = (M_largedata)mlist.get(c);
					String zjzd = "";
					if(Validate.noNull(m.getZjzd())){
						zjzd = Validate.isNullToDefault(map.get(m.getZjzd()),"") + "";
					}
					if(hzlx == 1){
						bodystr.append(CommonUtil.completeStrOne(map.get(m.getName()),m.getWs(),m.getColtype(),errlist,m.getZj(),zjzd,m.getShowname(), flag));
					}
					else{
						bodystr.append(CommonUtil.completeStrTwo(map.get(m.getName()),m.getWs(),m.getColtype(),errlist,m.getZj(),zjzd,m.getShowname(), flag));
					}
				}
                String bankCodeJsStr="";
				for(int h=0;h<GuidArr.length;h++)
				{
					if(map.get("GUID").equals(GuidArr[h])){
						bankCodeJsStr=bankCodeJsArr[h];
						break;
					}
				}
				if(bankCodeJsStr.equals("CCB")){
					khbz = "0";
				}else{
					khbz = "1";
				}
				//银行标志符
				//String zh = (String) map.get("YHZH");//银行账号
				//String URL= " https://ccdcapi.alipay.com/validateAndCacheCardInfo.json";
				//String param = "cardNo="+zh+"&cardBinCheck=true ";
				//String result = SendHttpUtil.sendGet(URL, param);
				//System.out.println("**"+result);
				//JSONObject jsStr = JSONObject.parseObject(result); //api返回
				//{"bank":"ICBC","validated":true,"cardType":"DC","key":"6222005865412565805","messages":[],"stat":"ok"}
				//判断联网是否成功
				//String valid = jsStr.getString("validated");
				//if(valid.equals("true")){
				//	bankCodeAl = jsStr.getString("bank");
				//	if(bankCodeAl.equals("CCB")){
				//		khbz = "0";
				//	}else{
				//		khbz = "1";
				//	}
				//}
				String lhh = Validate.isNullToDefault(map.get("YHLHH"),"") + "";
				bodystr.append(j+1+endstr);//序号
				bodystr.append(map.get("YHZH")+endstr);//账号
				bodystr.append(map.get("XM")+endstr);//姓名
				bodystr.append(map.get("JE").toString()+endstr);//金额
				bodystr.append(khbz+endstr);//跨行标志
				bodystr.append(lhh+endstr);//人民银行联行号
				bodystr.append(""+endstr);//多方协议号
				bodystr.append(zfzy+endstr);//摘要
				bodystr.append("");//备注
				if(j!=(list.size()-1)){
					bodystr.append(""+"\r\n"+"");
				}
				
			}
			if(mlist.size() > 0 && Validate.noNull(endstr)){
				String re = bodystr.toString();
				writer.print(re);
			}
			else{
				String re = bodystr.toString();
				writer.print(re);
			}
//			if(errlist.size() > 0){
//				return false;
//			}
		}
		writer.close();
		logger.info("生成txt文件完成");
		long endTime = System.currentTimeMillis();
		logger.info("用时="+((endTime-startTimne)/1000)+"秒");
		
		File f = new File(fileurl);
		TestFileUpload t = new TestFileUpload();
		String fileName = fileurl.substring(fileurl.lastIndexOf("\\")+1, fileurl.length());
		String result = t.testUpload(fileurl, fileName);
		result = result.substring(result.lastIndexOf("|")+1, result.length());
		
		String fileMD5 = TestXMLUpload.getFileMD5(fileurl).trim();
		
		String xml = plzfjk(guid,result,fileMD5,fileName);
		System.out.println("请求的xml"+xml);
		
		//签到xml
		//格式化时间戳
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String sysDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String sysMinute = new SimpleDateFormat("HHmmss").format(new Date());
		String XMLHeadQD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Transaction><Transaction_Header><SYS_TX_CODE><![CDATA[P1OPME001]]></SYS_TX_CODE><SYS_MSG_LEN><![CDATA[0000001602]]></SYS_MSG_LEN>"
				+ "        <SYS_REQ_TIME><![CDATA["+sysTime+"]]></SYS_REQ_TIME><SYS_TX_VRSN><![CDATA[01]]></SYS_TX_VRSN>"
				+ "        <TXN_DT><![CDATA["+sysDate+"]]></TXN_DT>"
				+ "        <TXN_TM><![CDATA["+sysMinute+"]]></TXN_TM><TXN_STFF_ID><![CDATA[333333]]></TXN_STFF_ID><MULTI_TENANCY_ID><![CDATA[CN000]]></MULTI_TENANCY_ID><LNG_ID><![CDATA[zh-cn]]></LNG_ID><REC_IN_PAGE><![CDATA[]]></REC_IN_PAGE><PAGE_JUMP><![CDATA[]]></PAGE_JUMP><STS_TRACE_ID><![CDATA[]]></STS_TRACE_ID><CHNL_CUST_NO><![CDATA[SD37000009021270501]]></CHNL_CUST_NO><IttParty_Jrnl_No><![CDATA[]]></IttParty_Jrnl_No><Txn_Itt_IP_Adr><![CDATA[60.208.80.195]]></Txn_Itt_IP_Adr></Transaction_Header>";
		String XMLFileReqQD = "<Transaction_Body><request>";
        String XMLBodyQD = "</request></Transaction_Body></Transaction>";
		String qd = XMLHeadQD + XMLFileReqQD + XMLBodyQD;
		String qdxml = TestXMLUpload.testXMLUpload(qd);
		String backXml;
		 Map zfxxMap = new HashMap();
		if(qdxml.length() > 0 ){
            backXml = TestXMLUpload.testXMLUpload(xml);
            //System.out.println(backXml);
            //解析
           zfxxMap = TestXMLUpload.readStringXmlOut(backXml);
    		//将支付的的guid txt 请求报文 响应报文  放到 一个文件夹  zfxx 下
    		zftj(guid,bodystr,xml,backXml);
		}	
		return zfxxMap;
	}
	
	/**
	 * 保存支付信息
	 */
	public boolean zftj(String guids,StringBuffer bodystr,String xml,String backxml){
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\zfxx\\";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		String hmsj = simpleDateFormat.format(new Date());
		FileUtil.createDir(realfile);
		String filename = hmsj+ ".txt";
		String fileurl = realfile + filename;
		try {
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileurl),"GBK"));//这里必须是GBK，要不然无法上报
			StringBuffer txtstr = new StringBuffer();;
			txtstr.append("guid:"+"\r\n"+guids+"\r\n");
			txtstr.append("txt:"+bodystr+"\r\n");
			txtstr.append("请求报文:"+"\r\n"+xml+"\r\n");
			txtstr.append("响应报文:"+"\r\n"+backxml);
			
			writer.print(txtstr);
			writer.close();
			logger.info("生成支付信息txt文件完成");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	private ServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	/**
	 * 银行对账信息
	 * @param zfsj
	 * @param je
	 * @param khxlh
	 * @return
	 */
	/*public List<Map<String, Object>> getPzguid(String zfsj, String je, String khxlh) {
		String sql = " select b.guid, to_char(b.pzrq, 'yyyy-mm-dd') as pzrq,b.pzlxmc, b.pzbh, mx.zy   " 
				+ "  from cw_pzlrzb b left join cw_pzlrmxb mx on mx.pzbh = b.guid"
				+" left join cw_pzlryhmx yh  on yh.mxid = mx.guid "
				+" where yh.res_time = '"+zfsj+"'"
				+" and yh.je = '"+je+"'"
				+" and yh.txnsn = '"+khxlh+"'";
         
		return db.queryForList(sql);
	}*/
	
	
}
