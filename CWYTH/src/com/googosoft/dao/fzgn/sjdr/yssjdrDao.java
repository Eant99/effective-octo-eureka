package com.googosoft.dao.fzgn.sjdr;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.shiro.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.sjdr.ZC_ZJB_DR;
import com.googosoft.util.Logger;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Repository("yssjdrDao")
public class yssjdrDao extends BaseDao{
	private Logger logger = Logger.getLogger(yssjdrDao.class);
	/**
	 * 获取Excel标题
	 */
	public List getYssjBt(String file){
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
	/** 导入数据 */
	public String insertYssjDr(String file,ZC_ZJB_DR zjb){
		Workbook rwb;
		String error = "";//错误或者成功信息保存在此
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
				StringBuffer sql = new StringBuffer();
				sql.append("insert into zc_zjb_dr(yqbh,flh,yqmc,flmc,jzxs,dj,xz,jldw,jfkm,zcly,syfx,sydw,syr,cgzzxs,jzlx,xmh,");
				sql.append("pzh,rzrq,qsrq,gzrq,ccrq,dzrrq,synx,czr,fjs,sccj,tddj,htbh,xss,qsxz,fgxs,cqxs,cqly,fw_qszm,jsh,zmmj,");
				sql.append("fw_fzrq,jcnf,fwyt,zj,dsmj,dxmj,fw_zrjs,zmjz,gzyq,fjzj,gg,fw_zymj,zyjz,xh,fw_cjmj,cjjz,wbje,fw_czmj,");
				sql.append("czjz,fw_gnmj,fw_jymj,jyjz,symj,fw_xzmj,xzjz,cch,fw_qtmj,qtjz,jkdj,zyyt,bz,zjjt,ljzj,");
				sql.append("zzj,dabh,bzxx,gbm,djh,wbzl,cgr,kyxm,fzr,sykh,xk,xklb,fzrq,jj,td_qszm,td_qsxz,td_fzrq,td_dh,td_zymj,");
				sql.append("td_czmj,td_jymj,td_xzmj,td_qtmj,td_rzkm,td_qsnx,td_yt,td_dymj,td_ftmj,pp,sb_gl,bxjzrq,jt_cpxh,jt_pql,"); 
				sql.append("td_cjmj,wx_pgjz,wx_djrq,wx_djjg,wx_zlh,wx_pzwh,wx_gljg,wx_ntxe,wx_fmmc,tph,yt,jdr,jdrq,guid)");
				sql.append("values(");
				//1:
				if(Validate.isNull(zjb.getYqbh()) || "null".equals(zjb.getYqbh())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getYqbh())+"',");
				}
				//2:
				if(Validate.isNull(zjb.getFlh()) || zjb.getFlh().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFlh())+"',");
				}
				//3:
				if(Validate.isNull(zjb.getYqmc()) || zjb.getYqmc().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getYqmc())+"',");
				}
				//4:
				if(Validate.isNull(zjb.getFlmc()) || zjb.getFlmc().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFlmc())+"',");
				}
				//5:
				if(Validate.isNull(zjb.getJzxs()) || zjb.getJzxs().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJzxs())+"',");
				}
				//6:
				if(Validate.isNull(zjb.getDj()) || zjb.getDj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getDj())+"',");
				}
				//7:
				if(Validate.isNull(zjb.getXz()) || zjb.getXz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getXz())+"',");
				}
				//8:
				if(Validate.isNull(zjb.getJldw()) || zjb.getJldw().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJldw())+"',");
				}
				//9:
				if(Validate.isNull(zjb.getJfkm()) || zjb.getJfkm().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJfkm())+"',");
				}
				//10:
				if(Validate.isNull(zjb.getZcly()) || zjb.getZcly().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getZcly())+"',");
				}
				//11:
				if(Validate.isNull(zjb.getSyfx()) || zjb.getSyfx().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getSyfx())+"',");
				}
				//12:
				if(Validate.isNull(zjb.getSydw()) || zjb.getSydw().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getSydw())+"',");
				}
				//13:
				if(Validate.isNull(zjb.getSyr()) || zjb.getSyr().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getSyr())+"',");
				}
				//14:
				if(Validate.isNull(zjb.getCgzzxs()) || zjb.getCgzzxs().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCgzzxs())+"',");
				}
				//15:
				if(Validate.isNull(zjb.getJzlx()) || zjb.getJzlx().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJzlx())+"',");
				}
				//16:
				if(Validate.isNull(zjb.getXmh()) || zjb.getXmh().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getXmh())+"',");
				}
				//17:
				if(Validate.isNull(zjb.getPzh()) || zjb.getPzh().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getPzh())+"',");
				}
				//18:
				if(Validate.isNull(zjb.getRzrq()) || zjb.getRzrq().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getRzrq())+"',");
				}
				//19:
				if(Validate.isNull(zjb.getQsrq()) || zjb.getQsrq().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getQsrq())+"',");
				}
				//20:
				if(Validate.isNull(zjb.getGzrq()) || zjb.getGzrq().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getGzrq())+"',");
				}
				//21:
				if(Validate.isNull(zjb.getCcrq()) || zjb.getCcrq().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCcrq())+"',");
				}
				//22:
				if(Validate.isNull(zjb.getDzrrq()) || zjb.getDzrrq().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getDzrrq())+"',");
				}
				//23:
				if(Validate.isNull(zjb.getSynx()) || zjb.getSynx().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getSynx())+"',");
				}
				//24:
				if(Validate.isNull(zjb.getCzr()) || zjb.getCzr().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCzr())+"',");
				}
				//25:
				if(Validate.isNull(zjb.getFjs()) || zjb.getFjs().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFjs())+"',");
				}
				//26:
				if(Validate.isNull(zjb.getSccj()) || zjb.getSccj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getSccj())+"',");
				}
				//27:
				if(Validate.isNull(zjb.getTddj()) || zjb.getTddj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTddj())+"',");
				}
				//28:
				if(Validate.isNull(zjb.getHtbh()) || zjb.getHtbh().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getHtbh())+"',");
				}
				//29:
				if(Validate.isNull(zjb.getXss()) || zjb.getXss().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getXss())+"',");
				}
				//30:
				if(Validate.isNull(zjb.getQsxz()) || zjb.getQsxz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getQsxz())+"',");
				}
				//31:
				if(Validate.isNull(zjb.getFgxs()) || zjb.getFgxs().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFgxs())+"',");
				}
				//32:
				if(Validate.isNull(zjb.getCqxs()) || zjb.getCqxs().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCqxs())+"',");
				}
				//33:
				if(Validate.isNull(zjb.getCqly()) || zjb.getCqly().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCqly())+"',");
				}
				//34:
				if(Validate.isNull(zjb.getFw_qszm()) || zjb.getFw_qszm().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_qszm())+"',");
				}
				//35:
				if(Validate.isNull(zjb.getJsh()) || zjb.getJsh().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJsh())+"',");
				}
				//36:
				if(Validate.isNull(zjb.getZmmj()) || zjb.getZmmj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getZmmj())+"',");
				}
				//37:
				if(Validate.isNull(zjb.getFw_fzrq()) || zjb.getFw_fzrq().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_fzrq())+"',");
				}
				//38:
				if(Validate.isNull(zjb.getJcnf()) || zjb.getJcnf().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJcnf())+"',");
				}
				//39:
				if(Validate.isNull(zjb.getFwyt()) || zjb.getFwyt().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFwyt())+"',");
				}
				//40:
				if(Validate.isNull(zjb.getZj()) || zjb.getZj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getZj())+"',");
				}
				//41:
				if(Validate.isNull(zjb.getDsmj()) || zjb.getDsmj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getDsmj())+"',");
				}
				//42:
				if(Validate.isNull(zjb.getDxmj()) || zjb.getDxmj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getDxmj())+"',");
				}
				//43:
				if(Validate.isNull(zjb.getFw_zrjs()) || zjb.getFw_zrjs().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_zrjs())+"',");
				}
				//44:
				if(Validate.isNull(zjb.getZmjz()) || zjb.getZmjz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getZmjz())+"',");
				}
				//45:
				if(Validate.isNull(zjb.getGzyq()) || zjb.getGzyq().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getGzyq())+"',");
				}
				//46:
				if(Validate.isNull(zjb.getFjzj()) || zjb.getFjzj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFjzj())+"',");
				}
				//47:
				if(Validate.isNull(zjb.getGg()) || zjb.getGg().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getGg())+"',");
				}
				//48:
				if(Validate.isNull(zjb.getFw_zymj()) || zjb.getFw_zymj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_zymj())+"',");
				}
				//49:
				if(Validate.isNull(zjb.getZyjz()) || zjb.getZyjz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getZyjz())+"',");
				}
				//50:
				if(Validate.isNull(zjb.getXh()) || zjb.getXh().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getXh())+"',");
				}
				//51:
				if(Validate.isNull(zjb.getFw_cjmj()) || zjb.getFw_cjmj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_cjmj())+"',");
				}
				//52:
				if(Validate.isNull(zjb.getCjjz()) || zjb.getCjjz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCjjz())+"',");
				}
				//53:
				if(Validate.isNull(zjb.getWbje()) || zjb.getWbje().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWbje())+"',");
				}
				//54:
				if(Validate.isNull(zjb.getFw_czmj()) || zjb.getFw_czmj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_czmj())+"',");
				}
				//55:
				if(Validate.isNull(zjb.getCzjz()) || zjb.getCzjz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCzjz())+"',");
				}
				//56:
				if(Validate.isNull(zjb.getFw_gnmj()) || zjb.getFw_gnmj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_gnmj())+"',");
				}
				//57:
				if(Validate.isNull(zjb.getFw_jymj()) || zjb.getFw_jymj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_jymj())+"',");
				}
				//58:
				if(Validate.isNull(zjb.getJyjz()) || zjb.getJyjz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJyjz())+"',");
				}
				//59:
				if(Validate.isNull(zjb.getSymj()) || zjb.getSymj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getSymj())+"',");
				}
				//60:
				if(Validate.isNull(zjb.getFw_xzmj()) || zjb.getFw_xzmj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_xzmj())+"',");
				}
				//61:
				if(Validate.isNull(zjb.getXzjz()) || zjb.getXzjz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getXzjz())+"',");
				}
				//62:
				if(Validate.isNull(zjb.getCch()) || zjb.getCch().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCch())+"',");
				}
				//63:
				if(Validate.isNull(zjb.getFw_qtmj()) || zjb.getFw_qtmj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFw_qtmj())+"',");
				}
				//64:
				if(Validate.isNull(zjb.getQtjz()) || zjb.getQtjz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getQtjz())+"',");
				}
				//65:
				if(Validate.isNull(zjb.getJkdj()) || zjb.getJkdj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJkdj())+"',");
				}
				//66:
				if(Validate.isNull(zjb.getZyyt()) || zjb.getZyyt().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getZyyt())+"',");
				}
				//67:
				if(Validate.isNull(zjb.getBz()) || zjb.getBz().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getBz())+"',");
				}
				//68:
				if(Validate.isNull(zjb.getZjjt()) || zjb.getZjjt().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getZjjt())+"',");
				}
				//69:
				if(Validate.isNull(zjb.getLjzj()) || zjb.getLjzj().equals("null")){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getLjzj())+"',");
				}
				//70:
				if(Validate.isNull(zjb.getZzj()) || "null".equals(zjb.getZzj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getZzj())+"',");
				}
				//71:
				if(Validate.isNull(zjb.getDabh()) || "null".equals(zjb.getDabh())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getDabh())+"',");
				}
				//72:
				if(Validate.isNull(zjb.getBzxx()) || "null".equals(zjb.getBzxx())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getBzxx())+"',");
				}
				//73:
				if(Validate.isNull(zjb.getGbm()) || "null".equals(zjb.getGbm())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getGbm())+"',");
				}
				//74:
				if(Validate.isNull(zjb.getDjh()) || "null".equals(zjb.getDjh())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getDjh())+"',");
				}
				//75:
				if(Validate.isNull(zjb.getWbzl()) || "null".equals(zjb.getWbzl())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWbzl())+"',");
				}
				//76:
				if(Validate.isNull(zjb.getCgr()) || "null".equals(zjb.getCgr())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getCgr())+"',");
				}
				//77:
				if(Validate.isNull(zjb.getKyxm()) || "null".equals(zjb.getKyxm())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getKyxm())+"',");
				}
				//78:
				if(Validate.isNull(zjb.getFzr()) || "null".equals(zjb.getFzr())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFzr())+"',");
				}
				//79:
				if(Validate.isNull(zjb.getSykh()) || "null".equals(zjb.getSykh())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getSykh())+"',");
				}
				//81:
				if(Validate.isNull(zjb.getXk()) || "null".equals(zjb.getXk())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getXk())+"',");
				}
				//82:
				if(Validate.isNull(zjb.getXklb()) || "null".equals(zjb.getXklb())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getXklb())+"',");
				}
				//83:
				if(Validate.isNull(zjb.getFzrq()) || "null".equals(zjb.getFzrq())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getFzrq())+"',");
				}
				//84:
				if(Validate.isNull(zjb.getJj()) || "null".equals(zjb.getJj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJj())+"',");
				}
				//85:
				if(Validate.isNull(zjb.getTd_qszm()) || "null".equals(zjb.getTd_qszm())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_qszm())+"',");
				}
				//86:
				if(Validate.isNull(zjb.getTd_qsxz()) || "null".equals(zjb.getTd_qsxz())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_qsxz())+"',");
				}
				//87:
				if(Validate.isNull(zjb.getTd_fzrq()) || "null".equals(zjb.getTd_fzrq())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_fzrq())+"',");
				}
				//88:
				if(Validate.isNull(zjb.getTd_dh()) || "null".equals(zjb.getTd_dh())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_dh())+"',");
				}
				//89:
				if(Validate.isNull(zjb.getTd_zymj()) || "null".equals(zjb.getTd_zymj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_zymj())+"',");
				}
				//90:
				if(Validate.isNull(zjb.getTd_czmj()) || "null".equals(zjb.getTd_czmj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_czmj())+"',");
				}
				//91:
				if(Validate.isNull(zjb.getTd_jymj()) || "null".equals(zjb.getTd_jymj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_jymj())+"',");
				}
				//92:
				if(Validate.isNull(zjb.getTd_xzmj()) || "null".equals(zjb.getTd_xzmj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_xzmj())+"',");
				}
				//93:
				if(Validate.isNull(zjb.getTd_qtmj()) || "null".equals(zjb.getTd_qtmj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_qtmj())+"',");
				}
				//94:
				if(Validate.isNull(zjb.getTd_rzkm()) || "null".equals(zjb.getTd_rzkm())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_rzkm())+"',");
				}
				//95
				if(Validate.isNull(zjb.getTd_qsnx()) || "null".equals(zjb.getTd_qsnx())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_qsnx())+"',");
				}
				//96:
				if(Validate.isNull(zjb.getTd_yt()) || "null".equals(zjb.getTd_yt())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_yt())+"',");
				}
				//97:
				if(Validate.isNull(zjb.getTd_dymj()) || "null".equals(zjb.getTd_dymj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_dymj())+"',");
				}
				//98:
				if(Validate.isNull(zjb.getTd_ftmj()) || "null".equals(zjb.getTd_ftmj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_ftmj())+"',");
				}
				//99:
				if(Validate.isNull(zjb.getPp()) || "null".equals(zjb.getPp())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getPp())+"',");
				}
				//100:
				if(Validate.isNull(zjb.getSb_gl()) || "null".equals(zjb.getSb_gl())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getSb_gl())+"',");
				}
				//101:
				if(Validate.isNull(zjb.getBxjzrq()) || "null".equals(zjb.getBxjzrq())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getBxjzrq())+"',");
				}
				//102:
				if(Validate.isNull(zjb.getJt_cpxh()) || "null".equals(zjb.getJt_cpxh())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJt_cpxh())+"',");
				}
				//103:
				if(Validate.isNull(zjb.getJt_pql()) || "null".equals(zjb.getJt_pql())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getJt_pql())+"',");
				}
				//104:
				if(Validate.isNull(zjb.getTd_cjmj()) || "null".equals(zjb.getTd_cjmj())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTd_cjmj())+"',");
				}
				//105:
				if(Validate.isNull(zjb.getWx_pgjz()) || "null".equals(zjb.getWx_pgjz())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWx_pgjz())+"',");
				}
				//106:
				if(Validate.isNull(zjb.getWx_djrq()) || "null".equals(zjb.getWx_djrq())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWx_djrq())+"',");
				}
				//107:
				if(Validate.isNull(zjb.getWx_djjg()) || "null".equals(zjb.getWx_djjg())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWx_djjg())+"',");
				}
				//108:
				if(Validate.isNull(zjb.getWx_zlh()) || "null".equals(zjb.getWx_zlh())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWx_zlh())+"',");
				}
				//109:
				if(Validate.isNull(zjb.getWx_pzwh()) || "null".equals(zjb.getWx_pzwh())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWx_pzwh())+"',");
				}
				//110:
				if(Validate.isNull(zjb.getWx_gljg()) || "null".equals(zjb.getWx_gljg())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWx_gljg())+"',");
				}
				//111:
				if(Validate.isNull(zjb.getWx_ntxe()) || "null".equals(zjb.getWx_ntxe())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWx_ntxe())+"',");
				}
				//112:
				if(Validate.isNull(zjb.getWx_fmmc()) || "null".equals(zjb.getWx_fmmc())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getWx_fmmc())+"',");
				}
				//113:
				if(Validate.isNull(zjb.getTph()) || "null".equals(zjb.getTph())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getTph())+"',");
				}
				//114:
				if(Validate.isNull(zjb.getYt()) || "null".equals(zjb.getYt())){
					sql.append("'',");
				}else{
					sql.append("'"+map.get(zjb.getYt())+"',");
				}
				//建档人
				sql.append("'"+LUser.getRybh()+"',");
				//建档日期
				sql.append("'"+Constant.MR_DATE()+"',");
				//115:
				sql.append("'"+UuidUtil.get32UUID()+"')");
				int a = 0;
				try{
					a = db.update(sql.toString());
				}catch(DataAccessException e){
					SQLException sqle = (SQLException) e.getCause();  
				    logger.error("数据库操作失败\n" + sqle);  
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
				}
				if(a==0){
					xx_list.add(i+"");
				}
			}
			if(xx_list.size()>0){
				error += "第";
            	for(int i=0,len=xx_list.size();i<len;i++){
            		if(i==0){
            			error +=xx_list.get(i);
            		}else{
            			error += "、"+xx_list.get(i);
            		}
            		//控制错误信息的数量，不能让他数量爆炸的全部输出
            		if(i>20){
            			break;
            		}
            	}
            	error += "行数据导入失败！！<br/>";
			}else{
				error="导入成功"+(rows-1)+"行！";
			}
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return error;
	}
}