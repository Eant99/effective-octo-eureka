package com.googosoft.dao.fzgn.sjdr;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.fzgn.jcsz.DdbDao;
import com.googosoft.dao.fzgn.jcsz.DwbDao;
import com.googosoft.dao.fzgn.jcsz.RybDao;
import com.googosoft.pojo.fzgn.sjdr.sjdr_jcsj;
import com.googosoft.util.Const;
import com.googosoft.util.Logger;
import com.googosoft.util.Validate;

@Repository("jcsjdrDao")
public class jcsjdrDao extends BaseDao{
	private Logger logger = Logger.getLogger(jcsjdrDao.class);
	@Resource(name="ryxxDao")
	private RybDao ryxxDao;
	
	@Resource(name="dwbDao")
	public DwbDao dwbDao;
	
	@Resource(name="ddbDao")
	public DdbDao Dao;
	
	/**
	 * 取出excel的表头
	 * @param file
	 * @return
	 */
	public List Excelxx(String file){

		List list = new ArrayList();
		Workbook rwb;
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//获取Excel第一个对象
			Cell[] cell= rs.getRow(0);//获取表格对象的第一行（即表头）
			String str[] = new String[cell.length];//定义一个数组，存储表头内容
			for(int i = 0;i<cell.length;i++){
				Map map = new HashMap();
				if(!"".equals(cell[i].getContents().trim())){
					if(Arrays.asList(str).contains(cell[i].getContents())){//如果表头中有重复的名称，就让重复的内容的后缀加上数字
						map.put("mc", cell[i].getContents()+i);//获取表头中的内容
						list.add(map);
					}else{
						map.put("mc", cell[i].getContents());//获取表头中的内容
						list.add(map);
					}
					str[i]=cell[i].getContents();//存储表头内容
				}
			}
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取导入内容
	 * @param file
	 * @return
	 */
	public List insertJcsj(String file){
		Workbook rwb;
		List list = new ArrayList();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			Cell[] cell= rs.getRow(0);//获取表格对象的第一行（即表头）
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				Map map = new HashMap();
				String str[] = new String[cell.length];//定义一个数组，存储表头内容
				int m=0;
				for(int k = 0;k<cell.length;k++){
					if(!"".equals(cell[k].getContents().trim())){
						if(Arrays.asList(str).contains(cell[k].getContents())){//如果表头中有重复的名称，就让重复的内容的后缀加上数字
							map.put(cell[k].getContents()+k, rs.getCell(m++, i).getContents());
						}else{
							map.put(cell[k].getContents(), rs.getCell(m++, i).getContents());
						}
						str[k]=cell[k].getContents();//存储表头内容
					}
				}
				m=0;
				str=null;
				list.add(map);
			}
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 导入数据--人员
	 * @param file
	 * @return
	 */
	public String insertJcsjRyb(String file,sjdr_jcsj rybdr,String in){
		//修改的条数
		int xz =0;
		//新增的条数
		int xg=0;
		Workbook rwb;
		try {
			//错误信息储存list
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			//错误信息储存list
			LinkedList<String> xx_list = new LinkedList<String>();
			Cell[] cell= rs.getRow(0);//获取表格对象的第一行（即表头）
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				Map map = new HashMap();
				String str[] = new String[cell.length];//定义一个数组，存储表头内容
				int m=0;
				for(int k = 0;k<cell.length;k++){
					if(!"".equals(cell[k].getContents().trim())){
						if(Arrays.asList(str).contains(cell[k].getContents())){//如果表头中有重复的名称，就让重复的内容的后缀加上数字
							map.put(cell[k].getContents()+k, rs.getCell(m++, i).getContents());
						}else{
							map.put(cell[k].getContents(), rs.getCell(m++, i).getContents());
						}
						str[k]=cell[k].getContents();//存储表头内容
					}
				}
				m=0;
				str=null;
				String rygh = map.get(rybdr.getDrp_rybh())+"";
				String passwd = new SimpleHash("SHA-1", Const.SALTKEY, Constant.MRMM).toString();	//默认密码（123456）加密
				//判断人员表中是否存在该人员
				boolean flag1 =ryxxDao.checkRygh(rygh);
				String  Str_xm =ryxxDao.findRyxmByRybh(rygh);
				if(flag1||Validate.noNull(Str_xm)){
					StringBuffer sql = new StringBuffer();
					StringBuffer where = new StringBuffer();
					sql.append("update "+SystemSet.gxBz+"RYB set ");
					if(in.indexOf("ck_xm")<0){
						if(!rybdr.getDrp_xm().equals("null")){
							sql.append("xm='"+map.get(rybdr.getDrp_xm())+"',");
						}
					}else{
						if(!rybdr.getDrp_xm().equals("null")){
							where.append("and xm='"+map.get(rybdr.getDrp_xm())+"'");
						}
					}
					if(in.indexOf("ck_xb")<0){
						if(!rybdr.getDrp_xb().equals("null")){
							sql.append("xb='"+map.get(rybdr.getDrp_xb())+"',");
						}
					}else{
						if(!rybdr.getDrp_xb().equals("null")){
							where.append("and xb='"+map.get(rybdr.getDrp_xb())+"'");
						}
					}
					if(in.indexOf("ck_dwbh")<0){
						if(!rybdr.getDrp_dwbh().equals("null")){
							sql.append("dwbh='"+map.get(rybdr.getDrp_dwbh())+"',");
						}
					}else{
						if(!rybdr.getDrp_dwbh().equals("null")){
							where.append("and dwbh='"+map.get(rybdr.getDrp_dwbh())+"'");
						}
					}
					if(in.indexOf("ck_csrq")<0){
						if(!rybdr.getDrp_csrq().equals("null")){
							sql.append("csrq=to_date('"+map.get(rybdr.getDrp_csrq())+"','yyyy-MM-dd'),");
						}
					}else{
						if(!rybdr.getDrp_csrq().equals("null")){
							where.append("and to_date(to_char(csrq,'yyyy-MM-dd'),'yyyy-MM-dd')=to_date('"+map.get(rybdr.getDrp_csrq())+"','yyyy-MM-dd')");
						}
					}
					if(in.indexOf("ck_whcd")<0){
						if(!rybdr.getDrp_whcd().equals("null")){
							sql.append("whcd='"+map.get(rybdr.getDrp_whcd())+"',");
						}
					}else{
						if(!rybdr.getDrp_whcd().equals("null")){
							where.append("and whcd='"+map.get(rybdr.getDrp_whcd())+"'");
						}
					}
					if(in.indexOf("ck_byrq")<0){
						if(!rybdr.getDrp_byrq().equals("null")){
							sql.append("byrq=to_date('"+map.get(rybdr.getDrp_byrq())+"','yyyy-MM-dd'),");
						}
					}else{
						if(!rybdr.getDrp_byrq().equals("null")){
							where.append("and to_date(to_char(byrq,'yyyy-MM-dd'),'yyyy-MM-dd')=to_date('"+map.get(rybdr.getDrp_byrq())+"','yyyy-MM-dd')");
						}
					}
					if(in.indexOf("ck_sxzy")<0){
						if(!rybdr.getDrp_sxzy().equals("null")){
							sql.append("sxzy='"+map.get(rybdr.getDrp_sxzy())+"',");
						}
					}else{
						if(!rybdr.getDrp_sxzy().equals("null")){
							where.append("and sxzy='"+map.get(rybdr.getDrp_sxzy())+"'");
						}
					}
					if(in.indexOf("ck_gzrq")<0){
						if(!rybdr.getDrp_gzrq().equals("null")){
							sql.append("gzrq=to_date('"+map.get(rybdr.getDrp_gzrq())+"','yyyy-MM-dd'),");
						}
					}else{
						if(!rybdr.getDrp_gzrq().equals("null")){
							where.append("and to_date(to_char(gzrq,'yyyy-MM-dd'),'yyyy-MM-dd')=to_date('"+map.get(rybdr.getDrp_gzrq())+"','yyyy-MM-dd')");
						}
					}
					if(in.indexOf("ck_zyzc")<0){
						if(!rybdr.getDrp_zyzc().equals("null")){
							sql.append("zyzc='"+map.get(rybdr.getDrp_zyzc())+"',");
						}
					}else{
						if(!rybdr.getDrp_zyzc().equals("null")){
							where.append("and zyzc='"+map.get(rybdr.getDrp_zyzc())+"'");
						}
					}
					if(in.indexOf("ck_zygz")<0){
						if(!rybdr.getDrp_zygz().equals("null")){
							sql.append("zygz='"+map.get(rybdr.getDrp_zygz())+"',");
						}
					}else{
						if(!rybdr.getDrp_zygz().equals("null")){
							where.append("and zygz='"+map.get(rybdr.getDrp_zygz())+"'");
						}
					}
					if(in.indexOf("ck_drrq")<0){
						if(!rybdr.getDrp_drrq().equals("null")){
							sql.append("drrq=to_date('"+map.get(rybdr.getDrp_drrq())+"','yyyy-MM-dd'),");
						}
					}else{
						if(!rybdr.getDrp_drrq().equals("null")){
							where.append("and to_date(to_char(drrq,'yyyy-MM-dd'),'yyyy-MM-dd')=to_date('"+map.get(rybdr.getDrp_drrq())+"','yyyy-MM-dd')");
						}
					}
					if(in.indexOf("ck_zzbz")<0){
						if(!rybdr.getDrp_zzbz().equals("null")){
							sql.append("zzbz='"+map.get(rybdr.getDrp_zzbz())+"',");
						}
					}else{
						if(!rybdr.getDrp_zzbz().equals("null")){
							where.append("and zzbz='"+map.get(rybdr.getDrp_zzbz())+"'");
						}
					}
					if(in.indexOf("ck_sysgl")<0){
						if(!rybdr.getDrp_sysgl().equals("null")){
							sql.append("sysgl='"+map.get(rybdr.getDrp_sysgl())+"',");
						}
					}else{
						if(!rybdr.getDrp_sysgl().equals("null")){
							where.append("and sysgl='"+map.get(rybdr.getDrp_sysgl())+"'");
						}
					}
					if(in.indexOf("ck_lxdh")<0){
						if(!rybdr.getDrp_lxdh().equals("null")){
							sql.append("lxdh='"+map.get(rybdr.getDrp_lxdh())+"',");
						}
					}else{
						if(!rybdr.getDrp_lxdh().equals("null")){
							where.append("and lxdh='"+map.get(rybdr.getDrp_lxdh())+"'");
						}
					}
					if(in.indexOf("ck_qq")<0){
						if(!rybdr.getDrp_qq().equals("null")){
							sql.append("qq='"+map.get(rybdr.getDrp_qq())+"',");
						}
					}else{
						if(!rybdr.getDrp_qq().equals("null")){
							where.append("and qq='"+map.get(rybdr.getDrp_qq())+"'");
						}
					}
					if(in.indexOf("ck_mail")<0){
						if(!rybdr.getDrp_mail().equals("null")){
							sql.append("mail='"+map.get(rybdr.getDrp_mail())+"',");
						}
					}else{
						if(!rybdr.getDrp_mail().equals("null")){
							where.append("and mail='"+map.get(rybdr.getDrp_mail())+"'");
						}
					}
					if(in.indexOf("ck_ryzt")<0){
						if(!rybdr.getDrp_ryzt().equals("null")){
							sql.append("ryzt='"+map.get(rybdr.getDrp_ryzt())+"',");
						}
					}else{
						if(!rybdr.getDrp_ryzt().equals("null")){
							where.append("and ryzt='"+map.get(rybdr.getDrp_ryzt())+"'");
						}
					}
					if(in.indexOf("ck_pxxh")<0){
						if(!rybdr.getDrp_pxxh().equals("null")){
							sql.append("pxxh='"+map.get(rybdr.getDrp_pxxh())+"',");
						}
					}else{
						if(!rybdr.getDrp_pxxh().equals("null")){
							where.append("and pxxh='"+map.get(rybdr.getDrp_pxxh())+"'");
						}
					}
					if(in.indexOf("ck_zzzt")<0){
						if(!rybdr.getDrp_pxxh().equals("null")){
							sql.append("zzzt='"+map.get(rybdr.getDrp_pxxh())+"',");
						}
					}else{
						if(!rybdr.getDrp_zzzt().equals("null")){
							where.append("and zzzt='"+map.get(rybdr.getDrp_zzzt())+"'");
						}
					}
					String sqlxx = sql.substring(0,sql.length()-1)+"where 1=1 and rybh='"+rygh+"'"+where;
					int b=0;
					try{
						
						b=db.update(sqlxx);
						xg++;
					}catch(DataAccessException e){
						SQLException sqle = (SQLException) e.getCause();  
					    logger.error("数据库操作失败\n" + sqle);  
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
					}
				}else{
					StringBuffer sql = new StringBuffer();
					sql.append("insert into "+SystemSet.gxBz+"RYB(rybh,rygh,xm,xb,dwbh,csrq,whcd,byrq,sxzy,");
					sql.append("gzrq,zyzc,zygz,drrq,zzbz,sysgl,lxdh,qq,mail,ryzt,pxxh,zzzt,mm)");
					sql.append("values(");
					if(rybdr.getDrp_rybh().equals("null")){
						sql.append("'"+map.get(rybdr.getDrp_rybh())+"','"+map.get(rybdr.getDrp_rybh())+"',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_rybh())+"','"+map.get(rybdr.getDrp_rybh())+"',");
					}
					if(rybdr.getDrp_xm().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_xm())+"',");
					}
					if(rybdr.getDrp_xb().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_xb())+"',");
					}
					if(rybdr.getDrp_dwbh().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_dwbh())+"',");
					}
					if(rybdr.getDrp_csrq().equals("null")){
						sql.append("'',");
					}else{
						sql.append("to_date('"+map.get(rybdr.getDrp_csrq())+"','yyyy-MM-dd'),");
					}
					if(rybdr.getDrp_whcd().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_whcd())+"',");
					}
					if(rybdr.getDrp_byrq().equals("null")){
						sql.append("'',");
					}else{
						sql.append("to_date('"+map.get(rybdr.getDrp_byrq())+"','yyyy-MM-dd'),");
					}
					if(rybdr.getDrp_sxzy().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_sxzy())+"',");
					}
					if(rybdr.getDrp_gzrq().equals("null")){
						sql.append("'',");
					}else{
						sql.append("to_date('"+map.get(rybdr.getDrp_gzrq())+"','yyyy-MM-dd'),");
					}
					if(rybdr.getDrp_zyzc().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_zyzc())+"',");
					}
					if(rybdr.getDrp_zygz().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_zygz())+"',");
					}
					if(rybdr.getDrp_drrq().equals("null")){
						sql.append("'',");
					}else{
						sql.append("to_date('"+map.get(rybdr.getDrp_drrq())+"','yyyy-MM-dd'),");
					}
					if(rybdr.getDrp_zzbz().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_zzbz())+"',");
					}
					if(rybdr.getDrp_sysgl().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_sysgl())+"',");
					}
					if(rybdr.getDrp_lxdh().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_lxdh())+"',");
					}
					if(rybdr.getDrp_qq().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_qq())+"',");
					}
					if(rybdr.getDrp_mail().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_mail())+"',");
					}
					if(rybdr.getDrp_ryzt().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_ryzt())+"',");
					}
					if(rybdr.getDrp_pxxh().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_pxxh())+"',");
					}
					if(rybdr.getDrp_zzzt().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(rybdr.getDrp_zzzt())+"',");
					}
					sql.append("'"+passwd.toUpperCase()+"')");
					int a=0;
					try{
						a=db.update(sql.toString());
						xz++;
					}catch(DataAccessException e){
						SQLException sqle = (SQLException) e.getCause();  
					    logger.error("数据库操作失败\n" + sqle);  
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
					}
				}
			}
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "共新增"+xz+"条，修改"+xg+"条。";
	}
	/**
	 * 导入数据--单位
	 * @param file
	 * @return
	 */
	public String insertJcsjDwb(String file,sjdr_jcsj dwbdr,String value){
		//修改的条数
				int xz =0;
				//新增的条数
				int xg=0;
		Workbook rwb;
		String error = "";//错误或者成功信息保存在此
		try {
			//错误信息储存list
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			//错误信息储存list
			LinkedList<String> xx_list = new LinkedList<String>();
			Cell[] cell= rs.getRow(0);//获取表格对象的第一行（即表头）
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				Map map = new HashMap();
				String str[] = new String[cell.length];//定义一个数组，存储表头内容
				int m=0;
				for(int k = 0;k<cell.length;k++){
					if(!"".equals(cell[k].getContents().trim())){
						if(Arrays.asList(str).contains(cell[k].getContents())){//如果表头中有重复的名称，就让重复的内容的后缀加上数字
							map.put(cell[k].getContents()+k, rs.getCell(m++, i).getContents());
						}else{
							map.put(cell[k].getContents(), rs.getCell(m++, i).getContents());
						}
						str[k]=cell[k].getContents();//存储表头内容
					}
				}
				m=0;
				str=null;
				String bmh = map.get(dwbdr.getDrp_bmh())+"";
				String sjdw = map.get(dwbdr.getDrp_sjdw())+"";
				//判断单位表中是否存在该单位
				boolean flag1 =dwbDao.doCheckDwbh(bmh);
				if(flag1){
					StringBuffer sql = new StringBuffer();
					sql.append("insert into "+SystemSet.gxBz+"DWB(bmh,mc,jc,dz,dwxz,dwld,fgld,sjdw,jlrq,dwjc,dwzt,dwbh)");
					sql.append("values(");
					if(dwbdr.getDrp_bmh().equals("null")){
						sql.append("'"+map.get(dwbdr.getDrp_bmh())+"',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_bmh())+"',");
					}
					if(dwbdr.getDrp_mc().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_mc())+"',");
					}
					if(dwbdr.getDrp_jc().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_jc())+"',");
					}
					if(dwbdr.getDrp_dz().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_dz())+"',");
					}
					if(dwbdr.getDrp_dwxz().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_dwxz())+"',");
					}
					if(dwbdr.getDrp_dwld().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_dwld())+"',");
					}
					if(dwbdr.getDrp_fgld().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_fgld())+"',");
					}
					if(dwbdr.getDrp_sjdw().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_sjdw())+"',");
					}
					if(dwbdr.getDrp_jlrq().equals("null")){
						sql.append("'',");
					}else{
						sql.append("to_date('"+map.get(dwbdr.getDrp_jlrq())+"','yyyy-MM-dd'),");
					}
					sql.append("'"+dwbDao.getDwjc(sjdw)+"',");//添加单位级次
					if(dwbdr.getDrp_dwzt().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(dwbdr.getDrp_dwzt())+"',");
					}
					sql.append("'"+bmh+"')");
					int a = 0;
					try{
						a = db.update(sql.toString());
					}catch(DataAccessException e){
						SQLException sqle = (SQLException) e.getCause();  
						logger.error("数据库操作失败\n" + sqle);  
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
					}
				}else{
					StringBuffer sql = new StringBuffer();
					StringBuffer where = new StringBuffer();
					sql.append("update "+SystemSet.gxBz+"DWB set ");
					if(value.indexOf("ck_mc")<0){
						if(!dwbdr.getDrp_mc().equals("null")){
							sql.append("mc='"+map.get(dwbdr.getDrp_mc())+"',");
						}
					}else{
						if(!dwbdr.getDrp_mc().equals("null")){
							where.append("and mc='"+map.get(dwbdr.getDrp_mc())+"'");
						}
					}
					if(value.indexOf("ck_jc")<0){
						if(!dwbdr.getDrp_jc().equals("null")){
							sql.append("jc='"+map.get(dwbdr.getDrp_jc())+"',");
						}
					}else{
						if(!dwbdr.getDrp_jc().equals("null")){
							where.append("and jc='"+map.get(dwbdr.getDrp_jc())+"'");
						}
					}
					if(value.indexOf("ck_dz")<0){
						if(!dwbdr.getDrp_dz().equals("null")){
							sql.append("dz='"+map.get(dwbdr.getDrp_dz())+"',");
						}
					}else{
						if(!dwbdr.getDrp_dz().equals("null")){
							where.append("and dz='"+map.get(dwbdr.getDrp_dz())+"'");
						}
					}
					if(value.indexOf("ck_dwxz")<0){
						if(!dwbdr.getDrp_dwxz().equals("null")){
							sql.append("dwxz='"+map.get(dwbdr.getDrp_dwxz())+"',");
						}
					}else{
						if(!dwbdr.getDrp_dwxz().equals("null")){
							where.append("and dwxz='"+map.get(dwbdr.getDrp_dwxz())+"'");
						}
					}
					if(value.indexOf("ck_dwld")<0){
						if(!dwbdr.getDrp_dwld().equals("null")){
							sql.append("dwld='"+map.get(dwbdr.getDrp_dwld())+"',");
						}
					}else{
						if(!dwbdr.getDrp_dwld().equals("null")){
							where.append("and dwld='"+map.get(dwbdr.getDrp_dwld())+"'");
						}
					}
					if(value.indexOf("ck_fgld")<0){
						if(!dwbdr.getDrp_fgld().equals("null")){
							sql.append("fgld='"+map.get(dwbdr.getDrp_fgld())+"',");
						}
					}else{
						if(!dwbdr.getDrp_fgld().equals("null")){
							where.append("and fgld='"+map.get(dwbdr.getDrp_fgld())+"'");
						}
					}
					if(value.indexOf("ck_sjdw")<0){
						if(!dwbdr.getDrp_sjdw().equals("null")){
							sql.append("sjdw='"+map.get(dwbdr.getDrp_sjdw())+"',");
						}
					}else{
						if(!dwbdr.getDrp_sjdw().equals("null")){
							where.append("and sjdw='"+map.get(dwbdr.getDrp_sjdw())+"'");
						}
					}
					if(value.indexOf("ck_jlrq")<0){
						if(!dwbdr.getDrp_jlrq().equals("null")){
							sql.append("jlrq=to_date('"+map.get(dwbdr.getDrp_jlrq())+"','yyyy-MM-dd'),");
						}
					}else{
						if(!dwbdr.getDrp_jlrq().equals("null")){
							where.append("and to_date(to_char(jlrq,'yyyy-MM-dd'),'yyyy-MM-dd')=to_date('"+map.get(dwbdr.getDrp_jlrq())+"','yyyy-MM-dd')");
						}
					}
					sql.append("dwjc='"+dwbDao.getDwjc(sjdw)+"',");//添加单位级次
					if(value.indexOf("ck_dwzt")<0){
						if(!dwbdr.getDrp_dwzt().equals("null")){
							sql.append("dwzt='"+map.get(dwbdr.getDrp_dwzt())+"',");
						}
					}else{
						if(!dwbdr.getDrp_dwzt().equals("null")){
							where.append("and dwzt='"+map.get(dwbdr.getDrp_dwzt())+"'");
						}
					}
					String sqlxx = sql.substring(0,sql.length()-1)+"where 1=1 and dwbh=(select dwbh from gx_sys_dwb where bmh='"+bmh+"')"+where;
					int a = 0;
					try{
						a = db.update(sqlxx);
					}catch(DataAccessException e){
						SQLException sqle = (SQLException) e.getCause();  
					    logger.error("数据库操作失败\n" + sqle);  
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
					}
				}
			}
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "共新增"+xz+"条，修改"+xg+"条。";
	}
	/**
	 * 导入数据--地点
	 * @param file
	 * @return
	 */
	public String insertJcsjDdb(String file,sjdr_jcsj ddbdr,String value){
		//修改的条数
		int xz =0;
		//新增的条数
		int xg=0;
		Workbook rwb;
		try {
			//错误信息储存list
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			//错误信息储存list
			LinkedList<String> xx_list = new LinkedList<String>();
			Cell[] cell= rs.getRow(0);//获取表格对象的第一行（即表头）
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				Map map = new HashMap();
				String str[] = new String[cell.length];//定义一个数组，存储表头内容
				int m=0;
				for(int k = 0;k<cell.length;k++){
					if(!"".equals(cell[k].getContents().trim())){
						if(Arrays.asList(str).contains(cell[k].getContents())){//如果表头中有重复的名称，就让重复的内容的后缀加上数字
							map.put(cell[k].getContents()+k, rs.getCell(m++, i).getContents());
						}else{
							map.put(cell[k].getContents(), rs.getCell(m++, i).getContents());
						}
						str[k]=cell[k].getContents();//存储表头内容
					}
				}
				m=0;
				str=null;
				//判断单位表中是否存在该单位
				String ddh = map.get(ddbdr.getDrp_ddh())+"";
				String sjdd = map.get(ddbdr.getDrp_sjdd())+"";
				boolean flag1 =Dao.doCheckDdh(ddh);
				if(flag1){
					StringBuffer sql = new StringBuffer();
					StringBuffer sql_context = new StringBuffer();
					StringBuffer where = new StringBuffer();
					sql.append("update "+SystemSet.sysBz+"DDB set ");
					if(value.indexOf("ck_mc")<0){
						if(!ddbdr.getDrp_mc().equals("null")){
							sql.append("mc='"+map.get(ddbdr.getDrp_mc())+"',");
						}
					}else{
						if(!ddbdr.getDrp_mc().equals("null")){
							where.append("and mc='"+map.get(ddbdr.getDrp_mc())+"'");
						}
						sql_context.append("1");
					}
					if(value.indexOf("ck_dwbh")<0){
						if(!ddbdr.getDrp_dwbh().equals("null")){
							sql.append("dwbh='"+map.get(ddbdr.getDrp_dwbh())+"',");
						}
					}else{
						if(!ddbdr.getDrp_dwbh().equals("null")){
							where.append("and dwbh='"+map.get(ddbdr.getDrp_dwbh())+"'");
						}
						sql_context.append("1");
					}
					if(value.indexOf("ck_sjdd")<0){
						if(!ddbdr.getDrp_sjdd().equals("null")){
							sql.append("sjdd='"+map.get(ddbdr.getDrp_sjdd())+"',");
						}
					}else{
						if(!ddbdr.getDrp_sjdd().equals("null")){
							where.append("and sjdd='"+map.get(ddbdr.getDrp_sjdd())+"'");
						}
						sql_context.append("1");
					}
					sql.append("ddjc='"+Dao.findSjddjc(sjdd)+"',");//添加地点级次
					if(value.indexOf("ck_ddzt")<0){
						if(!ddbdr.getDrp_ddzt().equals("null")){
							sql.append("ddzt='"+map.get(ddbdr.getDrp_ddzt())+"',");
						}
					}else{
						if(!ddbdr.getDrp_ddzt().equals("null")){
							where.append("and ddzt='"+map.get(ddbdr.getDrp_ddzt())+"'");
						}
						sql_context.append("1");
					}
					if(value.indexOf("ck_pxxh")<0){
						if(!ddbdr.getDrp_pxxh().equals("null")){
							sql.append("pxxh='"+map.get(ddbdr.getDrp_pxxh())+"',");
						}
					}else{
						if(!ddbdr.getDrp_pxxh().equals("null")){
							where.append("and pxxh='"+map.get(ddbdr.getDrp_pxxh())+"'");
						}
						sql_context.append("1");
					}
					if(sql_context.length()<=0){
						
					}else{
						String sqlxx = " update "+SystemSet.sysBz+"DDB set ddh='"+ddh+"' where ddh='"+ddh+"'";
					}
					String sqlxx = sql.substring(0,sql.length()-1)+"where 1=1 and ddbh=(select ddbh from zc_sys_ddb where ddh='"+ddh+"')"+where;
					int a = 0;
					try{
						a = db.update(sqlxx);
					}catch(DataAccessException e){
						SQLException sqle = (SQLException) e.getCause();  
					    logger.error("数据库操作失败\n" + sqle);  
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
					}
				}else{
					StringBuffer sql = new StringBuffer();
					sql.append("insert into "+SystemSet.sysBz+"DDB(ddh,mc,dwbh,sjdd,ddjc,ddzt,pxxh,ddbh)");
					sql.append("values(");
					if(ddbdr.getDrp_ddh().equals("null")){
						sql.append("'"+map.get(ddbdr.getDrp_ddh())+"',");
					}else{
						sql.append("'"+map.get(ddbdr.getDrp_ddh())+"',");
					}
					if(ddbdr.getDrp_mc().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(ddbdr.getDrp_mc())+"',");
					}
					if(ddbdr.getDrp_dwbh().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(ddbdr.getDrp_dwbh())+"',");
					}
					if(ddbdr.getDrp_sjdd().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(ddbdr.getDrp_sjdd())+"',");
					}
					sql.append("'"+Dao.findSjddjc(sjdd)+"',");//添加地点级次
					if(ddbdr.getDrp_ddzt().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(ddbdr.getDrp_ddzt())+"',");
					}
					if(ddbdr.getDrp_pxxh().equals("null")){
						sql.append("'',");
					}else{
						sql.append("'"+map.get(ddbdr.getDrp_pxxh())+"',");
					}
					sql.append("'"+ddh+"')");
					int a = 0;
					try{
						a = db.update(sql.toString());
					}catch(DataAccessException e){
						SQLException sqle = (SQLException) e.getCause();  
					    logger.error("数据库操作失败\n" + sqle);  
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
					}
				}
			}
			
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "共新增"+xz+"条，修改"+xg+"条。";
	}
	
	/**
	 * 导入数据
	 * @param file
	 * @return
	 */
	public String insertJcsj_jcsj(String file,sjdr_jcsj ddbdr,String value,String flag){
		Workbook rwb;
		StringBuffer error = new StringBuffer();//错误或者成功信息保存在此
		String msg="";
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			//错误信息储存list
			LinkedList<String> xx_list = new LinkedList<String>();
			Cell[] cell= rs.getRow(0);//获取表格对象的第一行（即表头）
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				Map map = new HashMap();
				String str[] = new String[cell.length];//定义一个数组，存储表头内容
				int m=0;
				for(int k = 0;k<cell.length;k++){
					if(!"".equals(cell[k].getContents().trim())){
						if(Arrays.asList(str).contains(cell[k].getContents())){//如果表头中有重复的名称，就让重复的内容的后缀加上数字
							map.put(cell[k].getContents()+k, rs.getCell(m++, i).getContents());
						}else{
							map.put(cell[k].getContents(), rs.getCell(m++, i).getContents());
						}
						str[k]=cell[k].getContents();//存储表头内容
					}
				}
				m=0;
				str=null;
				//地点
				if("dd".equals(flag)){
					if(!Validate.noNull(map.get(ddbdr.getDrp_ddh()))){
						error.append("地点号不能为空！");
					}else if(Validate.noNull(map.get(ddbdr.getDrp_ddh()))){//地点号，数字+字母
							String regex="^[a-zA-Z0-9]+$";//数字或字母组成
							Pattern pattern = Pattern.compile(regex);
							 Matcher match=pattern.matcher(map.get(ddbdr.getDrp_ddh())+"");
							//验证yqbh由数字或字母组成
							if(!match.matches()){
								error.append("地点号("+map.get(ddbdr.getDrp_ddh())+")必须由数字或字母组成，");
							}else if("000001".equalsIgnoreCase(map.get(ddbdr.getDrp_ddh())+"")){
								error.append("表格中包含顶级地点，不能够导入，请从Excel中删除，");
							}
						}
						//地点名称
						if(!Validate.noNull(map.get(ddbdr.getDrp_mc()))){
							error.append("地点名称不能为空，");
						}
						//所在单位
						String drp_dwbh = map.get(ddbdr.getDrp_dwbh())+"";
						if(Validate.noNull(drp_dwbh)){
							String sqlsydw = "select count(*) from gx_sys_dwb where bmh=? ";
							if(Integer.valueOf(db.queryForSingleValue(sqlsydw,new Object[]{drp_dwbh}))<=0){
								error.append("使用单位("+drp_dwbh+")不存在，");
							}
						}
						//上级地点
						String drp_sjdd = map.get(ddbdr.getDrp_sjdd())+"";
						if(!Validate.noNull(drp_sjdd)){
							error.append("上级地点不能为空，");
						}else{
							String sqlbzxx = "select count(*) from zc_sys_ddb where ddh=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlbzxx,new Object[]{drp_sjdd}))<=0){
								error.append("上级地点("+drp_sjdd+")不存在，");
							} else if (drp_sjdd.equalsIgnoreCase((map.get(ddbdr.getDrp_ddh())+""))){
								error.append("上级地点不能是自己！");
							}
						}
						//地点状态
						String drp_ddzt = map.get(ddbdr.getDrp_ddzt())+"";
						if(Validate.noNull(drp_ddzt)){
							if(!("0".equals(drp_ddzt)||"1".equals(drp_ddzt))){
								error.append("地点状态("+drp_ddzt+")不存在，");
							}
						}
						//排序序号
						String drp_pxxh = map.get(ddbdr.getDrp_pxxh())+"";
						if(Validate.noNull(drp_pxxh)){
							error.append(getmsg("drp_pxxh","dd",drp_pxxh));
						}
						if(error.length()>0){
							msg = msg+"第"+(i)+"行   【地点号："+Validate.isNullToDefaultString(map.get(ddbdr.getDrp_ddh()), "")+"】："+error+"<b style='color:red;'>请仔细核对数据！</b></br>";
							error.delete(0, error.length());
						}
					
				}else if ("dw".equals(flag)){
					if(!Validate.noNull(map.get(ddbdr.getDrp_bmh()))){
						error.append("单位编号不能为空！");
					}
						//单位编号，数字+字母
						if(Validate.noNull(map.get(ddbdr.getDrp_bmh()))){
							String regex="^[a-zA-Z0-9]+$";//数字或字母组成
							Pattern pattern = Pattern.compile(regex);
							 Matcher match=pattern.matcher(map.get(ddbdr.getDrp_bmh())+"");
							//验证yqbh由数字或字母组成
							if(!match.matches()){
								error.append("单位编号("+map.get(ddbdr.getDrp_bmh())+")必须由数字或字母组成，");
							}else if("000001".equalsIgnoreCase(map.get(ddbdr.getDrp_bmh())+"")){
								error.append("表格中包含顶级单位，不能够导入，请从Excel中删除，");
							}
						}
						//单位名称
						if(!Validate.noNull(map.get(ddbdr.getDrp_mc()))){
							error.append("单位名称不能为空，");
						}
						//建立日期
						String drp_jlrq = map.get(ddbdr.getDrp_jlrq())+"";
						if(!Validate.noNull(drp_jlrq)){
							error.append("建立日期不能为空，");
						}else{
							error.append(getmsg("drp_jlrq","dw",drp_jlrq));
						}
						//单位性质
						String drp_dwxz = map.get(ddbdr.getDrp_dwxz())+"";
						if(Validate.noNull(drp_dwxz)){
							String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.DWXZ+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{drp_dwxz}))<=0){
								error.append("单位性质("+drp_dwxz+")不存在，");
							}
						}
						//单位领导
						String drp_dwld = map.get(ddbdr.getDrp_dwld())+"";
						if(Validate.noNull(drp_dwld)){
							String sql_dwld = "select count(*) from gx_sys_ryb where (rybh=?)";
							if(Integer.valueOf(db.queryForSingleValue(sql_dwld,new Object[]{drp_dwld}))<=0){
								error.append("单位领导("+drp_dwld+")不存在，");
							}
						}
						//分管领导
						String drp_fgld = map.get(ddbdr.getDrp_fgld())+"";
						if(Validate.noNull(drp_dwld)){
							String sql_fgld = "select count(*) from gx_sys_ryb where (rybh=?)";
							if(Integer.valueOf(db.queryForSingleValue(sql_fgld,new Object[]{drp_fgld}))<=0){
								error.append("分管领导("+drp_dwld+")不存在，");
							}
						}
						//单位状态
						String drp_dwzt = map.get(ddbdr.getDrp_dwzt())+"";
						if(Validate.noNull(drp_dwzt)){
							if(!("0".equals(drp_dwzt)||"1".equals(drp_dwzt))){
								error.append("单位状态("+drp_dwzt+")不存在，");
							}
						}
						//上级单位
						String drp_sjdw = map.get(ddbdr.getDrp_sjdw())+"";
						if(Validate.noNull(drp_sjdw)){
							String sqlsydw = "select count(*) from gx_sys_dwb where bmh=? ";
							if(Integer.valueOf(db.queryForSingleValue(sqlsydw,new Object[]{drp_sjdw}))<=0){
								error.append("上级单位("+drp_sjdw+")不存在，");
							} else if (drp_sjdw.equalsIgnoreCase((map.get(ddbdr.getDrp_bmh())+""))){
								error.append("上级单位不能是自己！");
							}
						}else{
							error.append("上级单位不能为空，");
						}
						//排序序号
						String drp_pxxh = map.get(ddbdr.getDrp_pxxh())+"";
						if(Validate.noNull(drp_pxxh)){
							error.append(getmsg("drp_pxxh","dd",drp_pxxh));
						}
						//实验室标识
						String drp_sysbz = map.get(ddbdr.getDrp_sysbz())+"";
						if(Validate.noNull(drp_sysbz)){
							if(!("0".equals(drp_sysbz)||"1".equals(drp_sysbz))){
								error.append("实验室标识("+drp_sysbz+")不存在，");
							}
						}
						//实验室类别
						String drp_syslb = map.get(ddbdr.getDrp_syslb())+"";
						if(Validate.noNull(drp_syslb)){
							String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.SYSLB+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{drp_syslb}))<=0){
								error.append("实验室类别("+drp_syslb+")不存在，");
							}
						}
						//实验室面积
						String drp_sysmj= map.get(ddbdr.getDrp_sysmj())+"";
						if(Validate.noNull(drp_sysmj)){
							error.append(getmsg("drp_sysmj","dw",drp_sysmj));
						}
						//实验室级别
						String drp_sysjb = map.get(ddbdr.getDrp_sysjb())+"";
						if(Validate.noNull(drp_sysjb)){
							String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.SYSJB+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{drp_sysjb}))<=0){
								error.append("实验室级别("+drp_sysjb+")不存在，");
							}
						}
						//实验室归属
						String drp_sysgs = map.get(ddbdr.getDrp_sysgs())+"";
						if(Validate.noNull(drp_sysgs)){
							String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.SYSGS+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{drp_sysgs}))<=0){
								error.append("实验室归属("+drp_dwxz+")不存在，");
							}
						}
						if(error.length()>0){
							msg = msg+"第"+(i)+"行   【单位编号："+Validate.isNullToDefaultString(map.get(ddbdr.getDrp_bmh()), "")+"】："+error+"<b style='color:red;'>请仔细核对数据！</b></br>";
							error.delete(0, error.length());
						}
					
				}else if("ry".equals(flag)){
					if(!Validate.noNull(map.get(ddbdr.getDrp_rybh()))){
						error.append("人员编号不能为空，");
					}
						//单位编号，数字+字母
						if(Validate.noNull(map.get(ddbdr.getDrp_rybh()))){
							String regex="^[a-zA-Z0-9]+$";//数字或字母组成
							Pattern pattern = Pattern.compile(regex);
							 Matcher match=pattern.matcher(map.get(ddbdr.getDrp_rybh())+"");
							//验证yqbh由数字或字母组成
							if(!match.matches()){
								error.append("单位编号("+map.get(ddbdr.getDrp_rybh())+")必须由数字或字母组成，");
							}else if("000000".equalsIgnoreCase(map.get(ddbdr.getDrp_rybh())+"")){
								error.append("表格中包含顶超级管理员的编号，不能够导入，请从Excel中删除，");
							}
						}
						//单位名称
						if(!Validate.noNull(map.get(ddbdr.getDrp_xm()))){
							error.append("人员名称不能为空，");
						}
						//所在单位
						String drp_dwbh = map.get(ddbdr.getDrp_dwbh())+"";
						if(Validate.noNull(drp_dwbh)){
							String sqlsydw = "select count(*) from gx_sys_dwb where bmh=? ";
							if(Integer.valueOf(db.queryForSingleValue(sqlsydw,new Object[]{drp_dwbh}))<=0){
								error.append("所在单位("+drp_dwbh+")不存在，");
							}
						}else{
							error.append("所在单位不能为空，");
						}
						//性别
						String drp_xb = map.get(ddbdr.getDrp_xb())+"";
						if(Validate.noNull(drp_xb)){
							if(!("0".equals(drp_xb)||"1".equals(drp_xb))){
								error.append("性别("+drp_xb+")不存在，");
							}
						}
						
						//出生日期
						String drp_csrq = map.get(ddbdr.getDrp_csrq())+"";
						if(Validate.noNull(drp_csrq)){
							error.append(getmsg("drp_csrq","ry",drp_csrq));
						}
						//毕业日期
						String drp_byrq = map.get(ddbdr.getDrp_byrq())+"";
						if(Validate.noNull(drp_byrq)){
							error.append(getmsg("drp_byrq","ry",drp_byrq));
						}
						//文化程度
						String drp_whcd = map.get(ddbdr.getDrp_whcd())+"";
						if(Validate.noNull(drp_whcd)){
							String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.WHCD+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{drp_whcd}))<=0){
								error.append("文化程度("+drp_whcd+")不存在，");
							}
						}
						//工作时间
						String drp_gzrq = map.get(ddbdr.getDrp_gzrq())+"";
						if(Validate.noNull(drp_gzrq)){
							error.append(getmsg("drp_gzrq","ry",drp_gzrq));
						}
						//所学专业
						String drp_sxzy= map.get(ddbdr.getDrp_sxzy())+"";
						if(Validate.noNull(drp_sxzy)){
							String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.SXZY+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{drp_sxzy}))<=0){
								error.append("文化程度("+drp_sxzy+")不存在，");
							}
						}
						//专业职称
						String drp_zyzc = map.get(ddbdr.getDrp_zyzc())+"";
						if(Validate.noNull(drp_zyzc)){
							String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.ZYZC+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{drp_zyzc}))<=0){
								error.append("文化程度("+drp_zyzc+")不存在，");
							}
						}
						//人员类型
						String drp_zzbz = map.get(ddbdr.getDrp_zzbz())+"";
						if(Validate.noNull(drp_zzbz)){
							if(!("0".equals(drp_zzbz)||"1".equals(drp_zzbz))){
								error.append("人员类型("+drp_xb+")不存在，");
							}
						}
						//调入日期
						String drp_drrq = map.get(ddbdr.getDrp_drrq())+"";
						if(Validate.noNull(drp_drrq)){
							error.append(getmsg("drp_drrq","ry",drp_drrq));
						}
						//实验室工龄
						String drp_sysgl = map.get(ddbdr.getDrp_sysgl())+"";
						if(Validate.noNull(drp_sysgl)){
							error.append(getmsg("drp_sysgl","ry",drp_sysgl));
						}
						//主要工作
						String drp_zygz = map.get(ddbdr.getDrp_zygz())+"";
						if(Validate.noNull(drp_zygz)){
							String sqlsyxz= "select count(*) from gx_sys_dmb d where zl='"+Constant.ZYGZ+"' and dm=?";
							if(Integer.valueOf(db.queryForSingleValue(sqlsyxz,new Object[]{drp_zygz}))<=0){
								error.append("主要工作("+drp_zygz+")不存在，");
							}
						}
						//人员状态
						String drp_ryzt = map.get(ddbdr.getDrp_ryzt())+"";
						if(Validate.noNull(drp_ryzt)){
							if(!("0".equals(drp_ryzt)||"1".equals(drp_ryzt))){
								error.append("人员状态("+drp_ryzt+")不存在，");
							}
						}
						//联系电话
						String drp_lxdh = map.get(ddbdr.getDrp_lxdh())+"";
						if(Validate.noNull(drp_lxdh)){
							String regex="^(\\d{2}-?)?\\d{11,12}$";
							Pattern pattern = Pattern.compile(regex);
							 Matcher match=pattern.matcher(map.get(ddbdr.getDrp_lxdh())+"");
							if(!match.matches()){
								error.append("联系电话("+map.get(ddbdr.getDrp_lxdh())+")格式不正确，");
							}
						}
						//QQ号码
						String drp_qq = map.get(ddbdr.getDrp_qq())+"";
						if(Validate.noNull(drp_qq)){
							error.append(getmsg("drp_qq","ry",drp_qq));
						}
						//E-mail地址
						String drp_mail = map.get(ddbdr.getDrp_mail())+"";
						if(Validate.noNull(drp_mail)){
							String regex="^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";
							Pattern pattern = Pattern.compile(regex);
							 Matcher match=pattern.matcher(map.get(ddbdr.getDrp_mail())+"");
							if(!match.matches()){
								error.append("E-mail地址("+map.get(ddbdr.getDrp_mail())+")格式不正确，");
							}
						}
						//在职状态
						String drp_zzzt = map.get(ddbdr.getDrp_zzzt())+"";
						if(Validate.noNull(drp_ryzt)){
							if(!("0".equals(drp_zzzt)||"1".equals(drp_zzzt))){
								error.append("在职状态("+drp_zzzt+")不存在，");
							}
						}
						//排序序号
						String drp_pxxh = map.get(ddbdr.getDrp_pxxh())+"";
						if(Validate.noNull(drp_pxxh)){
							error.append(getmsg("drp_pxxh","dd",drp_pxxh));
						}
						if(error.length()>0){
							msg = msg+"第"+(i)+"行   【人员编号："+Validate.isNullToDefaultString(map.get(ddbdr.getDrp_rybh()), "")+"】："+error+"<b style='color:red;'>请仔细核对数据！</b></br>";
							error.delete(0, error.length());
						}
				}
			}
			rwb.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(msg.length()>0){
			return "{\"success\":false,\"msg\":\""+msg+"\"}";
		}else{
			String ts ="";
			if("dd".equals(flag)){
				ts= insertJcsjDdb(file, ddbdr, value);
			}else if("dw".equals(flag)){
				ts= insertJcsjDwb(file, ddbdr, value);
			}else if("ry".equals(flag)){
				ts= insertJcsjRyb(file, ddbdr, value);
			}
			
			return "{\"success\":true,\"msg\":\""+"导入成功，"+ts+"\"}";
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
