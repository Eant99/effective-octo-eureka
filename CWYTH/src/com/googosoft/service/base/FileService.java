package com.googosoft.service.base;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.base.FileDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.pojo.system.ZC_XGWD;
import com.googosoft.util.Const;

/**
 * service基础类
 * @author ii
 *
 */
@Service("fileService")
public class FileService extends BaseService{
	
	@Resource(name="fileDao")
	public FileDao fileDao;
	
	/**
	 * 保存附件信息
	 * @param dwb
	 * @return
	 */
	public int doAdd(String guid,String djbh,String djlx,String filename,String path){
		Subject subject = SecurityUtils.getSubject();
		GX_SYS_RYB ryb = (GX_SYS_RYB) subject.getSession().getAttribute(Const.SESSION_USER);
		ZC_XGWD fj = new ZC_XGWD();
		fj.setRybh(ryb.getRybh());
		fj.setFilename(filename);
		fj.setDbh(djbh);
		fj.setDjlx(djlx);
		fj.setPath(path);
		fj.setGuid(guid);
		int result = fileDao.doAdd(fj);
		if(result>1){
			doAddOplog(OplogFlag.ADD,"附件表",fj.getGuid());
		}
		return result;
	}
	
	public int doAdd(String guid,String djbh,String djlx,String filename,String path,String rybh){
		ZC_XGWD fj = new ZC_XGWD();
		fj.setRybh(rybh);
		fj.setFilename(filename);
		fj.setDbh(djbh);
		fj.setDjlx(djlx);
		fj.setPath(path);
		fj.setGuid(guid);
		int result = fileDao.doAdd(fj);
		if(result>1){
			doAddOplog(OplogFlag.ADD,"附件表",fj.getGuid());
		}
		return result;
	}
	
	/**
	 * 删除附件
	 */
	public int doDelete(String fjid){
		int result = fileDao.doDelete(fjid);
		if(result>1){
			doAddOplog(OplogFlag.DEL,"附件表",fjid);
		}
		return result;
	}
	/**
	 * 查询附件列表
	 */
	public String getFjList(String czbgbh,String mkbh,String realPath) {
		StringBuffer fjView = new StringBuffer();
		StringBuffer fjConfig = new StringBuffer();
		List<Map> list = fileDao.getFjList(czbgbh,mkbh);
		String xnlu_path = ResourceBundle.getBundle("global").getString("FileDataVirturalPath");
		if(list.size()>0){
			for(Map map : list){
				fjView.append("'"+xnlu_path+"/"+map.get("path")+"',");
				fjConfig.append("{caption:\""+map.get("filename")+"\",showDelete:true,key:\""+map.get("path")+"@"+map.get("guid")+"\",size:\""+map.get("djlx")+"\"},");
			}
			fjView.deleteCharAt(fjView.length()-1);
			fjConfig.deleteCharAt(fjConfig.length()-1);
		}
		return fjView+"#"+fjConfig;
	}
	/**
	 * 根据模块编号从图片类型设置表中查单据类型
	 * @param mkbh
	 * @return
	 */
	public List getDjlxList(String mkbh) {
		List list = fileDao.getDjlxList(mkbh);
		return list;
	}
	/**
	 * 根据主键查询附件信息
	 * @param mkbh
	 * @return
	 */
	public List getFjList(String czbgbh) {
		List<Map> list = fileDao.getFjList(czbgbh,"");
		return list;
	}
	/**
	 * 必须上传信息验证
	 * @param mkbh
	 * @param lxbh
	 * @return
	 */
	public boolean dobxscfj(String mkbh,String lxbh){
		return fileDao.dobxscfj(mkbh, lxbh);
	}
	/**
	 * 必须上传文件的类型编号和名称
	 * @param djlx
	 * @return
	 */
	public List dobxscfjlx(String djlx){
		return fileDao.dobxscfjlx(djlx);
	}
	
	/**
	 * 已上传的lxbh
	 * @param dbh
	 * @return
	 */
	public String findLxbh(String dbh){
		return fileDao.findLxbh(dbh);
	}
}
