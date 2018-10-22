package com.googosoft.service.system.suggest;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.system.suggest.SuggestDao;
import com.googosoft.util.Validate;

@Service("suggestService")
public class SuggestService {
	
	@Resource(name="suggestDao")
	private SuggestDao suggestDao;
	/**
	 * 
	 * @param value 输入框值
	 * @param flag 类型   D：单位   R：人员  。。。。。。\
	 * @param userId 当前登录人
	 * @return
	 */
	public Object getSuggest(String value, String flag, String userId,String sjkm) {
		System.out.println("=============value====="+value);
		System.out.println("=============flag====="+flag);
		String suggestXx = "";
		if("D".equals(flag)){//单位信息（有管理权限限制）
			suggestXx = suggestDao.getDwxx(value, userId);
		}else if("CD".equals(flag)){//单位信息（没有管理权限限制）
			suggestXx = suggestDao.getAllDwxx(value, userId);
		}else if("XMLX".equals(flag)){//项目类型
			suggestXx = suggestDao.getAllXmlx(value, userId);
		}else if("XMMC".equals(flag)) {
			suggestXx = suggestDao.getXmmc(value, userId);
		}
		else if("JJKM".equals(flag)){//经济科目
			suggestXx = suggestDao.getAllJjkm(value, userId);
		}
		else if("BMXM".equals(flag)){
			suggestXx = suggestDao.getAllBmxm(value, userId);
		}
		else if("GNKM".equals(flag)){//功能科目
			suggestXx = suggestDao.getAllGnkm(value, userId);
		}
		else if("KJKM".equals(flag)){//所有会计科目
			suggestXx = suggestDao.getAllKjkm(value, userId);
		}
		else if("YHCK".equals(flag)){//银行存款（1002）下所有会计科目
			suggestXx = suggestDao.getAllKjkm(value, flag);
		}
		else if("KCXJ".equals(flag)){//库存现金（1001）下所有会计科目
			suggestXx = suggestDao.getAllKjkm(value, flag);
		}
		else if("LYE".equals(flag)){//零余额日记账（1011）下所有会计科目
			suggestXx = suggestDao.getAllKjkm(value, flag);
		}
		else if("GNCD".equals(flag)){//所有功能菜单
			suggestXx = suggestDao.getAllGncd(value, userId);
		}
		else if("KJKMEJ".equals(flag)){//会计科目
			suggestXx = suggestDao.getEjKjkm(value, userId ,sjkm);
		}
		else if("ED".equals(flag)){//二级单位（有管理权限限制）
			suggestXx = suggestDao.getEjdw(value, userId);
		}else if("SD".equals(flag)){//卡片的使用单位
			suggestXx = suggestDao.getSyDwxx("%"+value+"%", userId);
		}else if("JFD".equals(flag)){//经费划拨单位信息(当前年度没有经费的)
			suggestXx = suggestDao.getJFDwxx(value, userId);
		}else if("R".equals(flag)){//人员信息
			suggestXx = suggestDao.getRyxx(value, userId);
		}else if("CR".equals(flag)){//没有权限限制的人员信息
			suggestXx = suggestDao.getCgrRyxx(value);
		}else if("DR".equals(flag)){//只能选择本单位的人员（不包括下级单位）
			suggestXx = suggestDao.getDRyxx("%"+value+"%", userId);
//		}else if("ER".equals(flag)){//二级单位人员(这个貌似也没用，ER与R是同一个方法，如果有用到的就改成R)
//			suggestXx = suggestDao.getRyxx(value, userId);
		}else if("XS".equals(flag)){//学生
			suggestXx = suggestDao.getXsRyxx(value);
		}else if("JS".equals(flag)){//老师
			suggestXx = suggestDao.getJsRyxx(value);
		}else if("WXS".equals(flag)){//维修商
			suggestXx = suggestDao.getWxsxx(value, userId);
		}else if("P".equals(flag)){//地点信息
			suggestXx = suggestDao.getDdxx(value);
		}else if("X".equals(flag)){//学科信息
			suggestXx = suggestDao.getXkxx(value);
		}else if("G".equals(flag)){//国别信息
			suggestXx = suggestDao.getGbxx(value);
		}else if("F".equals(flag)){//分类信息（教育部十六大类）
			suggestXx = suggestDao.getJybfl(value);
		}else if("FQ".equals(flag)){//分类信息（教育部十六大类带权限）
			suggestXx = suggestDao.getJybflh(value,userId);
		}else if("FC".equals(flag)){//分类名称（编号加名称）信息（教育部十六大类带权限）
			suggestXx = suggestDao.getJybflmc(value,userId);
		}else if("FH".equals(flag)){//分类名称（编号加名称）信息（教育部十六大类带权限）
			suggestXx = suggestDao.getJybflmcH(value,userId);
		}else if("CF".equals(flag)){//分类信息（财政六大类）
			suggestXx = suggestDao.getCzbfl(value);
		}else if("CQF".equals(flag)){//分类信息（财政六大类、带权限）
			suggestXx = suggestDao.getCzbflqx(value);
		}else if("CFS".equals(flag)){//分类信息（财政十大类）
			suggestXx = suggestDao.getCzbfls(value);
		}else if("CZFLDZ".equals(flag)){//分类信息（财政十大类）财政分类对照
			suggestXx = suggestDao.getCzbCzfldz(value);
		}else if("ZD".equals(flag)){//字典信息
			suggestXx = suggestDao.getZdxx(value, userId);
		}else if("ZY".equals(flag)){//字典信息
			suggestXx = suggestDao.getZYxx(value, userId);
		}else if("BJ".equals(flag)){//字典信息
			suggestXx = suggestDao.getBJxx(value, userId);
		}else if("NJ".equals(flag)){//字典信息
			suggestXx = suggestDao.getNJxx(value, userId);
		}else if("XKML".equals(flag)){//字典信息
			suggestXx = suggestDao.getXkxx(value);
		}else if("FYKMSZ".equals(flag)){//费用科目
			suggestXx = suggestDao.getFykmxx(value);
		}else if("FLMC".equals(flag)){//分类名称
			suggestXx = suggestDao.getFlmcxx(value);
		}else if("ZYFX".equals(flag)){//专业方向
			suggestXx = suggestDao.getZyfxxx(value);
		}else if("WL".equals(flag)){//往来单位
			suggestXx = suggestDao.getWLDW(value);
		}else if("DValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateDwxx(value);
		}else if("XMLXValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateXmlx(value);
		}else if("KJKMValidate".equals(flag)||"YHCKValidate".equals(flag)||"KCXJValidate".equals(flag)||"LYEValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateKjkm(value,userId);
		}else if("GNKMValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateGnkm(value,userId);
		}else if("GNCDValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateGncd(value,userId);
		
		}else if("JJKMValidate".equals(flag)){//经济科目单位信息验证
			suggestXx = suggestDao.validateJjkm(value,userId);
		}
		else if("KJKMEJValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateKjkmej(value);
		}
		else if("CDValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateDwxx(value);
		}else if("EDValidate".equals(flag)){//二级单位（有管理权限限制）
			suggestXx = suggestDao.validateEjdw(value);
		}else if("JFDValidate".equals(flag)){//经费划拨单位信息(当前年度没有经费的)
			suggestXx = suggestDao.validateJFDwxx(value);
		}else if("RValidate".equals(flag)){//人员信息验证
			suggestXx = suggestDao.validateRyxx(value);
		}else if("WXSValidate".equals(flag)){//人员信息验证
			suggestXx = suggestDao.validateWXS(value);
		}else if("PValidate".equals(flag)){//地点信息验证
			suggestXx = suggestDao.validateDdxx(value);
		}else if("XValidate".equals(flag)){//学科信息验证
			suggestXx = suggestDao.validateXkxx(value);
		}else if("GValidate".equals(flag)){//国别信息验证
			suggestXx = suggestDao.validateGbxx(value);
		}else if("FValidate".equals(flag)){//分类信息（教育部十六大类）信息验证
			suggestXx = suggestDao.validateJybfl(value);
		}else if("CRValidate".equals(flag)){//采购人信息验证
			suggestXx = suggestDao.validateRyxx(value);
		}else if("JSValidate".equals(flag)){//采购人信息验证
			suggestXx = suggestDao.validateJsRyxx(value);
		}else if("XSValidate".equals(flag)){//采购人信息验证
			suggestXx = suggestDao.validateXsRyxx(value);
		}else if("FCValidate".equals(flag)){//分类名称（编号加名称）信息（教育部十六大类带权限）
			suggestXx = suggestDao.validateJybflmc(value,userId,true);
		}else if("FQValidate".equals(flag)){//分类名称（编号加名称）信息（教育部十六大类带权限）
			suggestXx = suggestDao.validateJybflmc(value,userId,false);
		}else if("FHValidate".equals(flag)){//分类名称（编号加名称）信息（教育部十六大类带权限）
			suggestXx = suggestDao.validateJybflmc(value,userId,false);
		}else if("CFValidate".equals(flag)){//分类名称（编号加名称）信息（财政六大类）
			suggestXx = suggestDao.validateCzbfl(value);
		}else if("CQFValidate".equals(flag)){//分类信息（财政六大类、带权限）
			suggestXx = suggestDao.validateCzbflqx(value);
		}else if("CFSValidate".equals(flag)){//分类名称（编号加名称）信息（财政十大类）
			suggestXx = suggestDao.validategetCzbfls(value);
		}else if("CZFLDZValidate".equals(flag)){//（财政六大类）财政分类对照
			suggestXx = suggestDao.validategetCzbCzfldz(value);
		}else if("SDValidate".equals(flag)){//卡片的使用单位
			if(Validate.isNull(suggestDao.getSyDwxx(value, userId))){
				suggestXx = "{\"success\":\"false\"}";
			}else{
				suggestXx = "{\"success\":\"true\"}";
			}
		}else if("DRValidate".equals(flag)){//只能选择本单位的人员（不包括下级单位）
			if(Validate.isNull(suggestDao.getDRyxx(value, userId))){
				suggestXx = "{\"success\":\"false\"}";
			}else{
				suggestXx = "{\"success\":\"true\"}";
			}
		}else if("ZDValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateZdxx(value);
		}else if("ZYValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateZYxx(value);
		}else if("BJValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateBJxx(value);
		}else if("NJValidate".equals(flag)){//单位信息验证
			suggestXx = suggestDao.validateNJxx(value);
		}else if("FYKMSZValidate".equals(flag)){//费用科目验证
			suggestXx = suggestDao.validateFyxx(value);
		}else if("WLDW".equals(flag)) {//往来单位联想
			suggestXx = suggestDao.getWldw(value);
		}else if("WLDWValidate".equals(flag)) {
			suggestXx = suggestDao.validategetWldw(value);
		}else if("XMMCValidate".equals(flag)) {
			suggestXx = suggestDao.validategetXmmc(value);
			
		}else if("FLMCValidate".equals(flag)){//分类名称
			suggestXx = suggestDao.validateFlmc(value);
		}else if("ZYFXValidate".equals(flag)){//专业方向
			suggestXx = suggestDao.validateZyfx(value);
		}else if("WLValidate".equals(flag)){//往来单位
			suggestXx = suggestDao.validateWLDW(value);
		}
		return suggestXx;
	}
	
	/**
	 * 联想输入（单位内和单位间调拨用到）
	 * @param value
	 * @param flag
	 * @param userId
	 * @param ejdw 二级单位
	 * @param dbflag  单位内和单位间的标志
	 * @return
	 */
	public Object getSuggest(String value, String flag, String userId, String ejdw, String dbflag) {
		String suggestXx = "";
		System.err.println("flag的数值为=================="+flag);
		if("ED".equals(flag)){//二级单位
			suggestXx = suggestDao.getDwxx("%"+value+"%", userId, ejdw, dbflag);
		}else if("ER".equals(flag)){//二级单位人员
			suggestXx = suggestDao.getRyxx("%"+value+"%", userId, ejdw, dbflag);
		}else if("EDValidate".equals(flag)){//二级单位
			if(Validate.isNull(suggestDao.getDwxx(value, userId, ejdw, dbflag))){
				suggestXx = "{\"success\":\"false\"}";
			}
			else{
				suggestXx = "{\"success\":\"true\"}";
			}
		}else if("ERValidate".equals(flag)){//二级单位人员
			if(Validate.isNull(suggestDao.getRyxx(value, userId, ejdw, dbflag))){
				suggestXx = "{\"success\":\"false\"}";
			}
			else{
				suggestXx = "{\"success\":\"true\"}";
			}
		}
		return suggestXx.toString();
	}
}
