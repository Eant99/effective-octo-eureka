package com.googosoft.service.tubiao;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.Constant;
import com.googosoft.dao.tubiao.JzgjcxxfxDao;
import com.googosoft.pojo.tubiao.Zhcx;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 教职工基础信息分析---服务层
 * @author RC  2016-10-28
 *
 */
@Service("jzgjcxxfxService")
public class JzgjcxxfxService {
	@Resource(name="jzgjcxxfxDao")
	public JzgjcxxfxDao jzgjcxxfxDao;
	
	/**
	 * 
	 * @Title: getJzg_gk 
	 * @Description: 教职工概况分析(在职人员)
	 * @date 2017-5-22 下午4:42:07
	 * @param obj
	 * @return String 
	 *
	 */
	public String getJzg_gk(Zhcx obj){
	    StringBuffer json = new StringBuffer();//需要返回的数据
	    json.append("{ success: true ");
    	    json.append(this.getJzg_gk_hz(obj));   //返回汇总信息
    	    
    	    json.append(this.getJzg_gk_dwrs(obj));  //各系人数信息
    	    
    	    json.append(this.getJzg_gk_ssb(obj));  //各系生师比信息
    	    
    	    json.append(this.getJzg_gk_whcd(obj)); //文化程度分类信息
    	    
    	    json.append(this.getJzg_gk_zc(obj));  //职称分类信息
    	    
    	    json.append(this.getJzg_gk_zzmm(obj)); //职工政治面貌分类信息
    	    
    	    json.append(this.getJzg_gk_bz(obj)); //职工编制类别信息
    	    
    	    json.append(this.getJzg_gk_mz(obj)); //职工民族信息
    	    
    	    json.append(this.getJzg_gk_jg(obj));  //职工籍贯信息
    	    
    	    json.append(this.getJzg_gk_gj(obj));  //职工国籍信息
    	    
    	    json.append(this.getJzg_gk_nl(obj));  //教职工年龄信息
	    json.append("}");
	    return json.toString();
	}
	
	/**
	 * 
	 * @Title: getJzg_mx_map 
	 * @Description: 获取教职工市级信息
	 * @date 2017-6-1 下午4:35:01
	 * @param pd
	 * @return String 
	 *
	 */
	public String getJzg_mx_map(Zhcx obj, PageData pd){
	    StringBuffer json = new StringBuffer();//需要返回的数据
        json.append("{ success: true ");
            json.append(this.getJzg_jgmx(obj, pd));
        json.append("}");
        return json.toString();
	}
	
	
	/**
	 * 
	 * @Title: getJzg_gk_hz 
	 * @Description: 获取教职工概况汇总
	 * @date 2017-5-22 下午4:47:17
	 * @return String 
	 *
	 */
	public String getJzg_gk_hz(Zhcx obj){
	    Map map = this.jzgjcxxfxDao.getJzg_gk_hz(obj);
	    StringBuffer json = new StringBuffer();
	    if(Validate.noNull(map)){
	        json.append("");
	        json.append(",all:{");
                json.append("day:'"+obj.getRq()+"',");
                json.append("zrs:'"+Validate.isNullToDefault(map.get("ZRS"), "0")+"',");
                json.append("boys:'"+Validate.isNullToDefault(map.get("BOYS"), "0")+"',");
                json.append("girls:'"+Validate.isNullToDefault(map.get("GIRLS"), "0")+"'");
            json.append("}");
	    }
	    
	   return json.toString();
	}
	
	/**
     * 
     * @Title: getJzg_gk_dwrs 
     * @Description: 获取教职工各系人数情况
     * @date 2017-5-22 下午5:20:54
     * @return List 
     *
     */
	public String getJzg_gk_dwrs(Zhcx obj){
	    List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_dwrs(obj);
	    StringBuffer json = new StringBuffer();
	    if(Validate.noNull(list)){
	        StringBuffer jzgdata = new StringBuffer();
	        StringBuffer boydata = new StringBuffer();
	        StringBuffer girldata = new StringBuffer();
	        json.append(",dwoption:{");
                json.append("magicType:true,");
                json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
                json.append("xdata:[");
                    if(list.size() > 0){
                        Map rsmap;
                        for(int i = 0; i < list.size(); i++){
                            rsmap = (Map)list.get(i);
                            json.append("'");
                            json.append(Validate.isNullToDefault(rsmap.get("SZX"), "") + "',");
                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("ZRS"), "0")+"")){
                                jzgdata.append(Validate.isNullToDefault(rsmap.get("ZRS"), "") + "");
                            }
                            jzgdata.append(",");
                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("BOYS"), "0")+"")){
                                boydata.append(Validate.isNullToDefault(rsmap.get("BOYS"), "") + "");
                            }
                            boydata.append(",");
                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("GIRLS"), "0")+"")){
                                girldata.append(Validate.isNullToDefault(rsmap.get("GIRLS"), "") + "");
                            }
                            girldata.append(",");
                        }
                        json.deleteCharAt(json.length() - 1);
                        jzgdata.deleteCharAt(jzgdata.length() - 1);
                        boydata.deleteCharAt(boydata.length() - 1);
                        girldata.deleteCharAt(girldata.length() - 1);
                    }
                json.append("],");
                json.append("yAxis:[");
                json.append("{");
                    json.append("name:'人数（人）'");
                json.append("}");
                json.append("],");//Y轴内容
                json.append("data:[{");
                    json.append("name:'教职工总人数',label:{normal:{show:true,position:'top'}},data:[" + jzgdata.toString() + "]");
                json.append("},{");
                    json.append("name:'男性教职工人数',label:{normal:{show:true,position:'right'}},stack:'职工',barWidth:10,data:[" + boydata.toString() + "]");
                json.append("},{");
                    json.append("name:'女性教职工人数',label:{normal:{show:true,position:'top'}},stack:'职工',data:[" + girldata.toString() + "]");
                json.append("}]");
            json.append("}");
	    }
	    
	    return json.toString();
	}
	
	/**
	 * 
	 * @Title: getJzg_gk_ssb 
	 * @Description: 获取生师比信息
	 * @date 2017-5-24 下午2:47:29
	 * @return String 
	 *
	 */
	public String getJzg_gk_ssb(Zhcx obj){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_ssb(obj);
        StringBuffer json = new StringBuffer();
        if(list.size() > 0){
            StringBuffer jzgdata = new StringBuffer();
            StringBuffer boydata = new StringBuffer();
            StringBuffer girldata = new StringBuffer();
            json.append(",ssboption:{");
                json.append("magicType:true,");
                json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
                json.append("xdata:[");
                    
                Map rsmap;
                for(int i = 0; i < list.size(); i++){
                    rsmap = (Map)list.get(i);
                    json.append("'"+Validate.isNullToDefault(rsmap.get("SZX"), "") + "',");
                    jzgdata.append(Validate.isNullToDefault(rsmap.get("JSRS"), "0") + ",");
                    boydata.append(Validate.isNullToDefault(rsmap.get("XSRS"), "0") + ",");
                    girldata.append(Validate.isNullToDefault(rsmap.get("SSB"), "0.00") + ",");
                }
                json.deleteCharAt(json.length() - 1);
                jzgdata.deleteCharAt(jzgdata.length() - 1);
                boydata.deleteCharAt(boydata.length() - 1);
                girldata.deleteCharAt(girldata.length() - 1);
                
                json.append("],");
                json.append("yAxis:[{");
                    json.append("name:'人数(人)',");
                json.append("},{");
                    json.append("name:'生师比',");
                json.append("}],");//Y轴内容
                json.append("data:[{");
                    json.append("name:'教职工人数',label:{normal:{show:true,position:'top'}},data:[" + jzgdata.toString() + "]");
                json.append("},{");
                    json.append("name:'学生人数',label:{normal:{show:true,position:'top'}},data:[" + boydata.toString() + "]");
                json.append("},{");
                    json.append("name:'生师比',type:'line',smooth:true,");
                    json.append("yAxisIndex: 1,");
                    json.append("label:{normal:{show:true,position:'top'}},data:[" + girldata.toString() + "]");
                json.append("}]");
            json.append("}");
        }
        
        return json.toString();
	}
	
	
	
	/**
	 * 
	 * @Title: getJzg_gk_whcd 
	 * @Description: 获取教职工文化程度信息
	 * @date 2017-5-23 上午9:58:00
	 * @return String 
	 *
	 */
	public String getJzg_gk_whcd(Zhcx obj){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_whcd(obj);
        StringBuffer json = new StringBuffer();
        if(Validate.noNull(list)){
            StringBuffer jzgdata = new StringBuffer();
            StringBuffer boydata = new StringBuffer();
            StringBuffer girldata = new StringBuffer();
            json.append(",whcdoption:{");
                json.append("magicType:true,");
                json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
                json.append("xAxis:[{name:'人数(人)'}],");
                json.append("ydata:[");
                    if(list.size() > 0){
                        Map rsmap;
                        for(int i = 0; i < list.size(); i++){
                            rsmap = (Map)list.get(i);
                            json.append("'" + Validate.isNullToDefault(rsmap.get("WHCD"), "") + "',");
//                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("ZRS"), "0")+"")){
                                jzgdata.append(Validate.isNullToDefault(rsmap.get("ZRS"), "") + "");
//                            }
                            jzgdata.append(",");
//                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("BOYS"), "0")+"")){
                                boydata.append(Validate.isNullToDefault(rsmap.get("BOYS"), "") + "");
//                            }
                            boydata.append(",");
//                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("GIRLS"), "0")+"")){
                                girldata.append(Validate.isNullToDefault(rsmap.get("GIRLS"), "") + "");
//                            }
                            girldata.append(",");
                        }
                        json.deleteCharAt(json.length() - 1);
                        jzgdata.deleteCharAt(jzgdata.length() - 1);
                        boydata.deleteCharAt(boydata.length() - 1);
                        girldata.deleteCharAt(girldata.length() - 1);
                    }
                json.append("],");
                json.append("data:[");
                    json.append("{name:'教职工总人数',label:{normal:{show:true,position:'right'}},data:[" + jzgdata.toString() + "]},");
                    json.append("{name:'男性教职工人数',label:{normal:{show:true,position:'inside'}},stack:'职工',data:[" + boydata.toString() + "]},");
                    json.append("{name:'女性教职工人数',label:{normal:{show:true,position:'right'}},stack:'职工',data:[" + girldata.toString() + "]}");
                json.append("]");
            json.append("}");
        }
        
        return json.toString();
	}
	
	/**
	 * 
	 * @Title: getJzg_gk_zc 
	 * @Description: 获取教职工职称分类信息
	 * @date 2017-5-23 上午10:43:48
	 * @return String 
	 *
	 */
	public String getJzg_gk_zc(Zhcx obj){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_zc(obj);
        StringBuffer json = new StringBuffer();
        if(Validate.noNull(list)){
            StringBuffer jzgdata = new StringBuffer();
            json.append(",zcoption:{");
                json.append("magicType:true,");
                json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
                json.append("xdata:[");
                    if(list.size() > 0){
                        Map rsmap;
                        for(int i = 0; i < list.size(); i++){
                            rsmap = (Map)list.get(i);
                            json.append("'" + Validate.isNullToDefault(rsmap.get("ZCMC"), "") + "',");
                            jzgdata.append("{value:"+Validate.isNullToDefault(rsmap.get("ZRS"), "0")+",name:'" + Validate.isNullToDefault(rsmap.get("ZCMC"), "") + "'}" + ",");
                        }
                        json.deleteCharAt(json.length() - 1);
                        jzgdata.deleteCharAt(jzgdata.length() - 1);
                    }
                json.append("],");
                json.append("data:[{");
                    json.append("name:'职称',");
                    json.append("radius:['0','70%'],");
                    json.append("center:['50%','60%'],");
                    json.append("data:[" + jzgdata.toString() + "]");
                json.append("}]");
            json.append("}");
        }
        
        return json.toString();
	}
	
	/**
	 * 
	 * @Title: getJzg_gk_zzmm 
	 * @Description: 获取教职工政治面貌
	 * @date 2017-5-23 上午10:56:09
	 * @return String 
	 *
	 */
	public String getJzg_gk_zzmm(Zhcx obj){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_zzmm(obj);
        StringBuffer json = new StringBuffer();
        if(list.size() > 0){
            StringBuffer jzgdata = new StringBuffer();
            StringBuffer boydata = new StringBuffer();
            StringBuffer girldata = new StringBuffer();
            json.append(",zzmmoption:{");
                json.append("legend:{");
                    json.append("orient:'vertical',");   //图例horizontal横向排列，vertical纵向排列
                    json.append("data: ['教职工总数','男性教职工数','女性教职工数']");
                json.append("},");
                json.append("radar: {");
                    json.append("name:{textStyle:{color:'#333'}},");
                    json.append("indicator: [");
                Map rsmap;
                for(int i = 0; i < list.size(); i++){
                    rsmap = (Map) list.get(i);
                    json.append("{name:'"+rsmap.get("ZZMM")+"',max:40},");
                    jzgdata.append(""+rsmap.get("ZRS")+",");
                    boydata.append(""+rsmap.get("BOYS")+",");
                    girldata.append(""+rsmap.get("GIRLS")+",");
                }
                json.deleteCharAt(json.length() - 1);
                jzgdata.deleteCharAt(jzgdata.length() - 1);
                boydata.deleteCharAt(boydata.length() - 1);
                girldata.deleteCharAt(girldata.length() - 1);
                    json.append("]},");
                
                json.append("data:[{type:'radar',");
                    json.append("data:[{");
                        json.append("name:'教职工总数',value:[" + jzgdata.toString() + "]");
                    json.append("},{");
                        json.append("name:'男性教职工数',value:[" + boydata.toString() + "]");
                    json.append("},{");
                        json.append("name:'女性教职工数',value:[" + girldata.toString() + "]");
                    json.append("}]");
                json.append("}]");
            json.append("}");
        }
        
        return json.toString();
	}
	
	/**
	 * 
	 * @Title: getJzg_gk_bz 
	 * @Description: 获取职工编制信息
	 * @date 2017-5-23 上午11:05:19
	 * @return String 
	 *
	 */
	public String getJzg_gk_bz(Zhcx obj){
	    List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_bz(obj);
        StringBuffer json = new StringBuffer();
        if(Validate.noNull(list)){
            json.append(",bzlboption:{");
                json.append("title:[{text:'在编',left:'20%',top:'15%'},{text:'不在编',left:'70%',top:'15%'}],");
                json.append("legend:{");
                    json.append(" orient:'vertical',");   //图例horizontal横向排列，vertical纵向排列
                    json.append(" x: 'left', ");
                    json.append("data: ['男性','女性']");
                json.append("},");
                StringBuffer zbData = new StringBuffer();
                StringBuffer bzbData = new StringBuffer();
                for (Map<String, Object> map : list) {
                    if("在编".equals(map.get("BZLB"))){
                        zbData.append("{value:"+Validate.isNullToDefault(map.get("BOYS"), "0")+",name:'男性'},");
                        zbData.append("{value:"+Validate.isNullToDefault(map.get("GIRLS"), "0")+",name:'女性'}");
                    }else{
                        bzbData.append("{value:"+Validate.isNullToDefault(map.get("BOYS"), "0")+",name:'男性'},");
                        bzbData.append("{value:"+Validate.isNullToDefault(map.get("GIRLS"), "0")+",name:'女性'}");
                    }
                }
                json.append("data:[{");
                    json.append("name:'在编职工',");
                    json.append("radius:['35%','60%'],");
                    json.append("center:['25%','65%'],");
                    json.append(" label: {  ");
                    json.append(" normal: { show: true, position: 'inside' }, ");
//                    json.append(" emphasis: { show: true, textStyle: { fontSize: '30', fontWeight: 'bold'}} ");
                    json.append(" }, ");
                    json.append(" labelLine: { normal: { show: false } }, ");
                    json.append("data:["+zbData.toString()+"]");//填充数据
                json.append("},{");
                    json.append("name:'不在编职工',");
                    json.append("radius:['35%','60%'],");
                    json.append("center:['75%','65%'],");
                    json.append(" label: {  ");
                    json.append(" normal: { show: true, position: 'inside' }, ");
//                    json.append(" emphasis: { show: true, textStyle: { fontSize: '30', fontWeight: 'bold'}} ");
                    json.append(" }, ");
                    json.append(" labelLine: { normal: { show: false } }, ");
                    json.append("data:["+bzbData.toString()+"]");//填充数据
                json.append("}]");//series闭合
            json.append("}");
        }
        
        return json.toString();
    }
	
	/**
	 * 
	 * @Title: getJzg_gk_mz 
	 * @Description: 获取教职工民族信息
	 * @date 2017-5-23 上午11:17:39
	 * @return String 
	 *
	 */
	public String getJzg_gk_mz(Zhcx obj){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_mz(obj);
        StringBuffer json = new StringBuffer();
        if(Validate.noNull(list)){
            StringBuffer jzgdata = new StringBuffer();
            StringBuffer boydata = new StringBuffer();
            StringBuffer girldata = new StringBuffer();
            json.append(",mzoption:{");
                json.append("title:[{text:'所有教职工民族分布情况',x:'12%',top:'bottom'},{text:'男教职工民族分布情况',x:'42%',top:'bottom'},{text:'女教职工民族分布情况',x:'73%',top:'bottom'}],");
                json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
                json.append("xdata : [");
                    Map ndmap;
                    for(int i = 0; i < list.size(); i++){
                        ndmap = (Map)list.get(i);
                        json.append("'" + Validate.isNullToDefault(ndmap.get("MZ"), "") + "',");
                        jzgdata.append("{value:"+Validate.isNullToDefault(ndmap.get("ZRS"), "0")+",name:'" + Validate.isNullToDefault(ndmap.get("MZ"), "") + "'}" + ",");
                        boydata.append("{value:"+Validate.isNullToDefault(ndmap.get("BOYS"), "0")+",name:'" + Validate.isNullToDefault(ndmap.get("MZ"), "") + "'}" + ",");
                        girldata.append("{value:"+Validate.isNullToDefault(ndmap.get("GIRLS"), "0")+",name:'" + Validate.isNullToDefault(ndmap.get("MZ"), "") + "'}" + ",");
                    }
                    ndmap = null;
                    json.deleteCharAt(json.length() - 1);
                    jzgdata.deleteCharAt(jzgdata.length() - 1);
                    boydata.deleteCharAt(boydata.length() - 1);
                    girldata.deleteCharAt(girldata.length() - 1);
                json.append("],");//xdata闭合
                //设置数据
                json.append("data:[{");
                        json.append("name:'职工总人数',");
                        json.append("roseType:'radius',");
                        json.append("radius:['30%','75%'],");
                        json.append("center:['20%','50%'],");
                        json.append(" label: { normal: { show: true, position: 'outside' }}, ");
                        json.append("data:["+jzgdata.toString()+"]");//填充数据
                    json.append("},{");
                        json.append("name:'男性职工人数',");
                        json.append("roseType:'radius',");
                        json.append("radius:['30%','75%'],");
                        json.append("center:['50%','50%'],");
                        json.append(" label: { normal: { show: true, position: 'outside' }}, ");
                        json.append("data:["+boydata.toString()+"]");//填充数据
                    json.append("},{");
                        json.append("name:'女性职工人数',");
                        json.append("roseType:'radius',");
                        json.append("radius:['30%','75%'],");
                        json.append("center:['80%','50%'],");
                        json.append(" label: { normal: { show: true, position: 'outside' }}, ");
                        json.append("data:["+girldata.toString()+"]");//填充数据
               json.append("}]");//series闭合
            json.append("}");//mzoption闭合
        }
        
        return json.toString();
	}
	
	/**
	 * 
	 * @Title: getJzg_gk_jg 
	 * @Description: 获取教职工籍贯信息
	 * @date 2017-5-23 下午1:32:38
	 * @return String 
	 *
	 */
	public String getJzg_gk_jg(Zhcx obj){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_jg(obj);
        StringBuffer json = new StringBuffer();
        if(Validate.noNull(list)){
            StringBuffer jzgdata = new StringBuffer();
            StringBuffer boydata = new StringBuffer();
            StringBuffer girldata = new StringBuffer();
            json.append(",jgoption:{"); 
                json.append("magicType:true,");
                json.append("map:'china',");//代表地图类型
                json.append("legendorient:'vertical',");//图例horizontal横向排列，vertical纵向排列
                json.append("visualMap: [{max: 10,inRange: {color: ['#e0ffff', 'lightskyblue']}}],");
            Map rsmap;
            for(int i = 0; i < list.size(); i++){
                rsmap = (Map)list.get(i);
                String xdata = Validate.isNullToDefault(rsmap.get("JGS"), "") + "";
                if(!"0".equals(Validate.isNullToDefault(rsmap.get("ZRS"), "0"))){
                    jzgdata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("ZRS"), "0") + "},");
                }
                if(!"0".equals(Validate.isNullToDefault(rsmap.get("BOYS"), "0"))){
                    boydata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("BOYS"), "0") + "},");
                }
                if(!"0".equals(Validate.isNullToDefault(rsmap.get("GIRLS"), "0"))){
                    girldata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("GIRLS"), "0") + "},");
                }
            }
            jzgdata.deleteCharAt(jzgdata.length() - 1);
            boydata.deleteCharAt(boydata.length() - 1);
            girldata.deleteCharAt(girldata.length() - 1);
                json.append("data:[{");
                    json.append("name:'教职工总人数',data:[" + jzgdata.toString() + "]");
                json.append("},{");
                    json.append("name:'男性职工人数',data:[" + boydata.toString() + "]");
                json.append("},{");
                    json.append("name:'女性职工人数',data:[" + girldata.toString() + "]");
                json.append("}]");
            json.append("}");//gjoption闭合
        }
        
        return json.toString();
	}
	
	/**
	 * 
	 * @Title: getJzg_gk_gj 
	 * @Description: 获取教职工国籍信息
	 * @date 2017-5-23 下午1:34:15
	 * @return String 
	 *
	 */
	public String getJzg_gk_gj(Zhcx obj){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_gj(obj);
        StringBuffer json = new StringBuffer();
        if(Validate.noNull(list)){
            StringBuffer jzgdata = new StringBuffer();
            StringBuffer boydata = new StringBuffer();
            StringBuffer girldata = new StringBuffer();
            json.append(",gjoption:{"); 
                json.append("magicType:true,");
                json.append("map:'world',");//代表地图类型
                json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
                json.append("visualMap: [{max: 100}],");
            Map rsmap;
            for(int i = 0; i < list.size(); i++){
                rsmap = (Map)list.get(i);
                String xdata = Validate.isNullToDefault(rsmap.get("GJ"), "") + "";
                if(!"0".equals(Validate.isNullToDefault(rsmap.get("ZRS"), "0"))){
                    jzgdata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("ZRS"), "0") + "},");
                }
                if(!"0".equals(Validate.isNullToDefault(rsmap.get("BOYS"), "0"))){
                    boydata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("BOYS"), "0") + "},");
                }
                if(!"0".equals(Validate.isNullToDefault(rsmap.get("GIRLS"), "0"))){
                    girldata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("GIRLS"), "0") + "},");
                }
            }
            jzgdata.deleteCharAt(jzgdata.length() - 1);
            boydata.deleteCharAt(boydata.length() - 1);
            girldata.deleteCharAt(girldata.length() - 1);
                json.append("data:[{");
                    json.append("name:'教职工总人数',itemStyle:{normal:{label:{show:false}}},data:[" + jzgdata.toString() + "]");
                json.append("},{");
                    json.append("name:'男性职工人数',data:[" + boydata.toString() + "]");
                json.append("},{");
                    json.append("name:'女性职工人数',data:[" + girldata.toString() + "]");
                json.append("}]");
            json.append("}");//gjoption闭合
        }
        
        return json.toString();
    }
	
	/**
	 * 
	 * @Title: getJzg_gk_nl 
	 * @Description: 获取教职工年龄信息
	 * @date 2017-5-23 下午2:36:34
	 * @return String 
	 *
	 */
	public String getJzg_gk_nl(Zhcx obj){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_gk_nl(obj);
        StringBuffer json = new StringBuffer();
        if(Validate.noNull(list)){
            StringBuffer jzgdata = new StringBuffer();
            StringBuffer boydata = new StringBuffer();
            StringBuffer girldata = new StringBuffer();
            json.append(",nloption:{");
                json.append("magicType:true,");
                json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
                json.append("xdata:[");
                    if(list.size() > 0){
                        Map rsmap;
                        for(int i = 0; i < list.size(); i++){
                            rsmap = (Map)list.get(i);
                            json.append("'"+Validate.isNullToDefault(rsmap.get("NLD"), "") + "',");
                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("ZRS"), "0")+"")){
                                jzgdata.append(Validate.isNullToDefault(rsmap.get("ZRS"), "") + "");
                            }
                            jzgdata.append(",");
                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("BOYS"), "0")+"")){
                                boydata.append(Validate.isNullToDefault(rsmap.get("BOYS"), "") + "");
                            }
                            boydata.append(",");
                            if(!"0".equals(Validate.isNullToDefault(rsmap.get("GIRLS"), "0")+"")){
                                girldata.append(Validate.isNullToDefault(rsmap.get("GIRLS"), "") + "");
                            }
                            girldata.append(",");
                        }
                        json.deleteCharAt(json.length() - 1);
                        jzgdata.deleteCharAt(jzgdata.length() - 1);
                        boydata.deleteCharAt(boydata.length() - 1);
                        girldata.deleteCharAt(girldata.length() - 1);
                    }
                json.append("],");
                json.append("yAxis:[");
                json.append("{");
                    json.append("name:'人数(人)'");
                json.append("}");
                json.append("],");//Y轴内容
                json.append("data:[{");
                    json.append("name:'教职工总人数',label:{normal:{show:true,position:'top'}},data:[" + jzgdata.toString() + "]");
                json.append("},{");
                    json.append("name:'男性职工人数',label:{normal:{show:true,position:'right'}},stack:'职工',barWidth:10,data:[" + boydata.toString() + "]");
                json.append("},{");
                    json.append("name:'女性职工人数',label:{normal:{show:true,position:'top'}},stack:'职工',data:[" + girldata.toString() + "]");
                json.append("}]");
            json.append("}");
        }
        
        return json.toString();
	}
	
	/**
	 * 
	 * @Title: getJzg_jgmx 
	 * @Description: 
	 * @date 2017-6-1 下午3:20:56
	 * @param pd
	 * @return String 
	 * @throws 
	 *
	 */
	public String getJzg_jgmx(Zhcx obj, PageData pd){
        List<Map<String, Object>> list = this.jzgjcxxfxDao.getJzg_jgmx(obj, pd);
        StringBuffer json = new StringBuffer();
        if(list.size()>0){
            StringBuffer jzgdata = new StringBuffer();
            StringBuffer boydata = new StringBuffer();
            StringBuffer girldata = new StringBuffer();
            json.append(",msoption:{"); 
                json.append("magicType:true,");
                json.append("map:'"+pd.getString("jgs")+"',");//代表地图类型
                json.append("legendorient:'vertical',");//图例horizontal横向排列，vertical纵向排列
                json.append("visualMap: [{top: 100, max: 5,inRange: {color: ['#e0ffff', 'lightskyblue']}}],");
            Map rsmap;
            for(int i = 0; i < list.size(); i++){
                rsmap = (Map)list.get(i);
                String xdata = Validate.isNullToDefault(rsmap.get("JGMS"), "") + "";
                jzgdata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("ZRS"), "0") + "},");
                boydata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("BOYS"), "0") + "},");
                girldata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("GIRLS"), "0") + "},");
            }
            jzgdata.deleteCharAt(jzgdata.length() - 1);
            boydata.deleteCharAt(boydata.length() - 1);
            girldata.deleteCharAt(girldata.length() - 1);
                json.append("data:[{");
                    json.append("name:'教职工总人数',data:[" + jzgdata.toString() + "]");
                json.append("},{");
                    json.append("name:'男性职工人数',data:[" + boydata.toString() + "]");
                json.append("},{");
                    json.append("name:'女性职工人数',data:[" + girldata.toString() + "]");
                json.append("}]");
            json.append("}");//gjoption闭合
        }
        
        return json.toString();
    }
	
	
	/************************************************新旧方法分界线*****************************************************/
	
	
	/**
	 * 获取教职工基础信息分析数据
	 * @return
	 */
	public String getJzgjcxxfx(Zhcx obj){
		Map<String,List> map = jzgjcxxfxDao.getJzgjcxxfx(obj);
		if(map.isEmpty()){
			return "{success:false,msg:'没有查询到数据！'}";
		}
		List list;
		StringBuffer json = new StringBuffer();//需要返回的数据
		StringBuffer jzgdata;//存放教职工人数
		StringBuffer boydata;//存放男性教职工人数
		StringBuffer girldata;//存放女性教职工人数
		json.append("{success:true");
		//当前人数分析
		if(map.containsKey("dq")){
			Map map1 = (Map) map.get("dq");
			json.append(",all:{");
				json.append("year:'"+DateUtil.getYear()+"',");
				json.append("zrs:'"+Validate.isNullToDefault(map1.get("ZRS"), "0")+"',");
				json.append("boys:'"+Validate.isNullToDefault(map1.get("BOYS"), "0")+"',");
				json.append("girls:'"+Validate.isNullToDefault(map1.get("GIRLS"), "0")+"'");
			json.append("}");
		}
		//单位分析
		if(map.containsKey("dw")){
			list = map.get("dw");
			jzgdata = new StringBuffer();
			boydata = new StringBuffer();
			girldata = new StringBuffer();
			json.append(",dwoption:{");
				json.append("magicType:true,");
				json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
				json.append("xdata:[");
					if(list.size() > 0){
						Map rsmap;
						for(int i = 0; i < list.size(); i++){
							rsmap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(rsmap.get("XDATA"), "") + "',");
							jzgdata.append(Validate.isNullToDefault(rsmap.get("JZGRS"), "0") + ",");
							boydata.append(Validate.isNullToDefault(rsmap.get("BOYS"), "0") + ",");
							girldata.append(Validate.isNullToDefault(rsmap.get("GIRLS"), "0") + ",");
						}
						json.deleteCharAt(json.length() - 1);
						jzgdata.deleteCharAt(jzgdata.length() - 1);
						boydata.deleteCharAt(boydata.length() - 1);
						girldata.deleteCharAt(girldata.length() - 1);
					}
				json.append("],");
				json.append("yAxis:[");
		        json.append("{");
		            json.append("name:'人数（人）'");
		        json.append("}");
		        json.append("],");//Y轴内容
				json.append("data:[{");
					json.append("name:'教职工总人数',label:{normal:{show:true,position:'top'}},data:[" + jzgdata.toString() + "]");
				json.append("},{");
					json.append("name:'男性教职工人数',label:{normal:{show:true,position:'top'}},data:[" + boydata.toString() + "]");
				json.append("},{");
					json.append("name:'女性教职工人数',label:{normal:{show:true,position:'top'}},data:[" + girldata.toString() + "]");
				json.append("}]");
			json.append("}");
			
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		//文化程度分析
		if(map.containsKey("whcd")){
			list = map.get("whcd");
			jzgdata = new StringBuffer();
			boydata = new StringBuffer();
			girldata = new StringBuffer();
			json.append(",whcdoption:{");
				json.append("magicType:true,");
				json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
				json.append("ydata:[");
					if(list.size() > 0){
						Map rsmap;
						for(int i = 0; i < list.size(); i++){
							rsmap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(rsmap.get("XDATA"), "") + "',");
							jzgdata.append(Validate.isNullToDefault(rsmap.get("JZGRS"), "0") + ",");
							boydata.append(Validate.isNullToDefault(rsmap.get("BOYS"), "0") + ",");
							girldata.append(Validate.isNullToDefault(rsmap.get("GIRLS"), "0") + ",");
						}
						json.deleteCharAt(json.length() - 1);
						jzgdata.deleteCharAt(jzgdata.length() - 1);
						boydata.deleteCharAt(boydata.length() - 1);
						girldata.deleteCharAt(girldata.length() - 1);
					}
				json.append("],");
				json.append("data:[");
					json.append("{name:'教职工总人数',label:{normal:{show:true,position:'right'}},data:[" + jzgdata.toString() + "]},");
					json.append("{name:'男性教职工人数',label:{normal:{show:true,position:'right'}},data:[" + boydata.toString() + "]},");
					json.append("{name:'女性教职工人数',label:{normal:{show:true,position:'right'}},data:[" + girldata.toString() + "]}");
				json.append("]");
			json.append("}");
			
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		
		//编制类别分析（散点图）
		if(map.containsKey("bzlb")){
			list = map.get("bzlb");
			jzgdata = new StringBuffer();
			boydata = new StringBuffer();
			girldata = new StringBuffer();
			json.append(",bzlboption:{");
			json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
				json.append("xdata : [");
					Map ndmap;
					for(int i = 0; i < list.size(); i++){
						ndmap = (Map)list.get(i);
						json.append("'" + Validate.isNullToDefault(ndmap.get("XDATA"), "") + "',");
						jzgdata.append("['" + Validate.isNullToDefault(ndmap.get("XDATA"), "") + "',"+Validate.isNullToDefault(ndmap.get("JZGRS"), "0")+"],");
						boydata.append("['" + Validate.isNullToDefault(ndmap.get("XDATA"), "") + "',"+Validate.isNullToDefault(ndmap.get("BOYS"), "0")+"],");
						girldata.append("['" + Validate.isNullToDefault(ndmap.get("XDATA"), "") + "',"+Validate.isNullToDefault(ndmap.get("GIRLS"), "0")+"],");
					}
					ndmap = null;
					json.deleteCharAt(json.length() - 1);
					jzgdata.deleteCharAt(jzgdata.length() - 1);
					boydata.deleteCharAt(boydata.length() - 1);
					girldata.deleteCharAt(girldata.length() - 1);
				json.append("],");//xdata闭合
				//设置数据
				json.append("yAxis:[");
		        json.append("{");
		            json.append("name:'人数（人）'");
		        json.append("}");
		        json.append("],");//Y轴内容
				json.append("data:[");
					json.append("{");
						json.append("name:'教职工总人数',");
						json.append("symbolSize:35,");
						json.append("data:["+jzgdata.toString()+"]");//填充数据
					json.append("}");
					json.append(",{");
						json.append("name:'男性教职工人数',");
						json.append("symbolSize:30,");
						json.append("data:["+boydata.toString()+"]");//填充数据
					json.append("}");
					json.append(",{");
						json.append("name:'女性教职工人数',");
						json.append("symbolSize:25,");
						json.append("data:["+girldata.toString()+"]");//填充数据
					json.append("}");
				json.append("]");//series闭合
			json.append("}");//bzlboption闭合
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		//职称分析
		if(map.containsKey("zc")){
			list = map.get("zc");
			jzgdata = new StringBuffer();
//			boydata = new StringBuffer();
//			girldata = new StringBuffer();
			json.append(",zcoption:{");
			json.append("title:{text:'教职工职称分布情况',left:'center',top:'bottom'},");
				json.append("magicType:true,");
				json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
				json.append("xdata:[");
					if(list.size() > 0){
						Map rsmap;
						for(int i = 0; i < list.size(); i++){
							rsmap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(rsmap.get("XDATA"), "") + "',");
							jzgdata.append("{value:"+Validate.isNullToDefault(rsmap.get("JZGRS"), "0")+",name:'" + Validate.isNullToDefault(rsmap.get("XDATA"), "") + "'}" + ",");
						}
						json.deleteCharAt(json.length() - 1);
						jzgdata.deleteCharAt(jzgdata.length() - 1);
					}
				json.append("],");
				json.append("data:[{");
					json.append("name:'职称',");
					json.append("radius:['0','70%'],");
					json.append("center:['50%','50%'],");
					json.append("data:[" + jzgdata.toString() + "]");
				json.append("}]");
			json.append("}");
			
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		//政治面貌分析
		if(map.containsKey("zzmm")){
			list = map.get("zzmm");
			jzgdata = new StringBuffer();
			boydata = new StringBuffer();
			girldata = new StringBuffer();
			json.append(",zzmmoption:{");
				json.append("magicType:true,");
				json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
				json.append("xdata:[");
					if(list.size() > 0){
						Map rsmap;
						for(int i = 0; i < list.size(); i++){
							rsmap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(rsmap.get("XDATA"), "") + "',");
							jzgdata.append(Validate.isNullToDefault(rsmap.get("JZGRS"), "0") + ",");
							boydata.append(Validate.isNullToDefault(rsmap.get("BOYS"), "0") + ",");
							girldata.append(Validate.isNullToDefault(rsmap.get("GIRLS"), "0") + ",");
						}
						json.deleteCharAt(json.length() - 1);
						jzgdata.deleteCharAt(jzgdata.length() - 1);
						boydata.deleteCharAt(boydata.length() - 1);
						girldata.deleteCharAt(girldata.length() - 1);
					}
				json.append("],");
				json.append("yAxis:[");
		        json.append("{");
		            json.append("name:'人数（人）'");
		        json.append("}");
		        json.append("],");//Y轴内容
				json.append("data:[{");
					json.append("name:'教职工总人数',label:{normal:{show:true,position:'top'}},data:[" + jzgdata.toString() + "]");
				json.append("},{");
					json.append("name:'男性教职工人数',label:{normal:{show:true,position:'top'}},data:[" + boydata.toString() + "]");
				json.append("},{");
					json.append("name:'女性教职工人数',label:{normal:{show:true,position:'top'}},data:[" + girldata.toString() + "]");
				json.append("}]");
			json.append("}");
			
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
	
		
		//民族（饼状图）
		if(map.containsKey("mz")){
			list = map.get("mz");
			jzgdata = new StringBuffer();
			boydata = new StringBuffer();
			girldata = new StringBuffer();
			json.append(",mzoption:{");
			json.append("title:[{text:'所有教职工民族分布情况分析',x:'10%',top:'bottom'},{text:'男教职工民族分布情况分析',x:'40%',top:'bottom'},{text:'女教职工民族分布情况分析',x:'70%',top:'bottom'}],");
				json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
				//设置坐标轴
				json.append("xdata : [");
					Map ndmap;
					for(int i = 0; i < list.size(); i++){
						ndmap = (Map)list.get(i);
						json.append("'" + Validate.isNullToDefault(ndmap.get("XDATA"), "") + "',");
						jzgdata.append("{value:"+Validate.isNullToDefault(ndmap.get("JZGRS"), "0")+",name:'" + Validate.isNullToDefault(ndmap.get("XDATA"), "") + "'}" + ",");
						boydata.append("{value:"+Validate.isNullToDefault(ndmap.get("BOYS"), "0")+",name:'" + Validate.isNullToDefault(ndmap.get("XDATA"), "") + "'}" + ",");
						girldata.append("{value:"+Validate.isNullToDefault(ndmap.get("GIRLS"), "0")+",name:'" + Validate.isNullToDefault(ndmap.get("XDATA"), "") + "'}" + ",");
					}
					ndmap = null;
					json.deleteCharAt(json.length() - 1);
					jzgdata.deleteCharAt(jzgdata.length() - 1);
					boydata.deleteCharAt(boydata.length() - 1);
					girldata.deleteCharAt(girldata.length() - 1);
				json.append("],");//xdata闭合
				//设置数据
				json.append("data:[");
					json.append("{");
						json.append("name:'教职工总人数',");
						json.append("roseType:'radius',");
						json.append("radius:['30%','75%'],");
						json.append("center:['20%','50%'],");
//						json.append("label:{normal: {position: 'inner'}},");
						json.append("data:["+jzgdata.toString()+"]");//填充数据
					json.append("}");
					json.append(",{");
						json.append("name:'男性教职工人数',");
						json.append("roseType:'radius',");
						json.append("radius:['30%','75%'],");
						json.append("center:['50%','50%'],");
//						json.append("label:{normal: {position: 'inner'}},");
						json.append("data:["+boydata.toString()+"]");//填充数据
					json.append("}");
					json.append(",{");
						json.append("name:'女性教职工人数',");
						json.append("roseType:'radius',");
						json.append("radius:['30%','75%'],");
						json.append("center:['80%','50%'],");
//						json.append("label:{normal: {position: 'inner'}},");
						json.append("data:["+girldata.toString()+"]");//填充数据
					json.append("}");
				json.append("]");//series闭合
			json.append("}");//mzoption闭合
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		//年龄分析
		if(map.containsKey("age")){
			Map map2 = (Map) map.get("age");
			jzgdata = new StringBuffer();
			boydata = new StringBuffer();
			girldata = new StringBuffer();
			json.append(",nloption:{");
				json.append("magicType:true,");
				json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
				json.append("xdata:[");
					if(Validate.noNull(map2)){
						int minage = Integer.parseInt(Validate.isNullToDefault(obj.getAge1()+"", Validate.isNullToDefault(map2.get("MINAGE"), Constant.MR_QSNL))+"");
						int maxage = Integer.parseInt(Validate.isNullToDefault(obj.getAge2()+"", Validate.isNullToDefault(map2.get("MAXAGE"), Constant.MR_JSNL))+"");
						int index = Integer.parseInt(Validate.isNullToDefault(obj.getAgen()+"", Constant.MR_NLJG)+"");
						for(int i = minage; i <= maxage; i+=index){//在最小年龄和最大年龄之间，每隔5年分个年龄段
							json.append("'" + i + "',");
							Map ageMap = jzgjcxxfxDao.getAges(i, i+index);
							jzgdata.append(Validate.isNullToDefault(ageMap.get("ZRS"), "0") + ",");
							boydata.append(Validate.isNullToDefault(ageMap.get("BOYS"), "0") + ",");
							girldata.append(Validate.isNullToDefault(ageMap.get("GIRLS"), "0") + ",");
						}
						json.deleteCharAt(json.length() - 1);
						jzgdata.deleteCharAt(jzgdata.length() - 1);
						boydata.deleteCharAt(boydata.length() - 1);
						girldata.deleteCharAt(girldata.length() - 1);
					}
				json.append("],");
				json.append("yAxis:[");
		        json.append("{");
		            json.append("name:'人数（人）'");
		        json.append("}");
		        json.append("],");//Y轴内容
				json.append("data:[{");
					json.append("name:'教职工总人数',label:{normal:{show:true,position:'top'}},data:[" + jzgdata.toString() + "]");
				json.append("},{");
					json.append("name:'男性教职工人数',label:{normal:{show:true,position:'top'}},data:[" + boydata.toString() + "]");
				json.append("},{");
					json.append("name:'女性教职工人数',label:{normal:{show:true,position:'top'}},data:[" + girldata.toString() + "]");
				json.append("}]");
			json.append("}");
			
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		//国家分析
		if(map.containsKey("gjs")){
			list = map.get("gj");
			jzgdata = new StringBuffer();
			boydata = new StringBuffer();
			girldata = new StringBuffer();
			json.append(",gjoption:{");
				json.append("xdata:[");
					if(list.size() > 0){
						Map rsmap;
						for(int i = 0; i < list.size(); i++){
							rsmap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(rsmap.get("XDATA"), "") + "',");
							jzgdata.append(Validate.isNullToDefault(rsmap.get("JZGRS"), "0") + ",");
							boydata.append(Validate.isNullToDefault(rsmap.get("BOYS"), "0") + ",");
							girldata.append(Validate.isNullToDefault(rsmap.get("GIRLS"), "0") + ",");
						}
						json.deleteCharAt(json.length() - 1);
						jzgdata.deleteCharAt(jzgdata.length() - 1);
						boydata.deleteCharAt(boydata.length() - 1);
						girldata.deleteCharAt(girldata.length() - 1);
					}
				json.append("],");
				
				json.append("data:[{");
					json.append("name:'教职工总人数',data:[" + jzgdata.toString() + "]");
				json.append("},{");
					json.append("name:'男性教职工人数',data:[" + boydata.toString() + "]");
				json.append("},{");
					json.append("name:'女性教职工人数',data:[" + girldata.toString() + "]");
				json.append("}]");
			json.append("}");
			
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		//国籍分析（中国地图）
		//{map:'china',data:[{name:'女生人数',data:[{value:20,name:'山东'},{value:12,name:'海南'},{value:26,name:'北京'},{value:30,name:'内蒙古'},{value:38,name:'台湾'},{value:40,name:'新疆'}]},{name:'学生人数',data:[{value:60,name:'山东'},{value:22,name:'海南'},{value:36,name:'北京'},{value:10,name:'内蒙古'},{value:28,name:'台湾'},{value:34,name:'新疆'}]}]};
		if(map.containsKey("gj")){
			list = map.get("gj");
			json.append(",gjoption:{");	
				jzgdata = new StringBuffer();
				boydata = new StringBuffer();
				girldata = new StringBuffer();
				if(list.size() > 0){
					Map rsmap;
					for(int i = 0; i < list.size(); i++){
						rsmap = (Map)list.get(i);String xdata = Validate.isNullToDefault(rsmap.get("XDATA"), "") + "";
						jzgdata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("JZGRS"), "0") + "},");
						boydata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("BOYS"), "0") + "},");
						girldata.append("{name:'" + xdata + "',value:" + Validate.isNullToDefault(rsmap.get("GIRLS"), "0") + "},");
					}
					jzgdata.deleteCharAt(jzgdata.length() - 1);
					boydata.deleteCharAt(boydata.length() - 1);
					girldata.deleteCharAt(girldata.length() - 1);
				}
				json.append("magicType:true,");
				json.append("map:'world',");//代表世界地图
				json.append("data:[{");
					json.append("name:'教职工总人数',itemStyle:{normal:{label:{show:false}}},data:[" + jzgdata.toString() + "]");
				json.append("},{");
					json.append("name:'男性教职工人数',data:[" + boydata.toString() + "]");
				json.append("},{");
					json.append("name:'女性教职工人数',data:[" + girldata.toString() + "]");
				json.append("}]");
			json.append("}");//gjoption闭合
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		//籍贯分析
		if(map.containsKey("jg")){
			list = map.get("jg");
			json.append(",jgoption:{");
				json.append("data:[");
					json.append("{");
						json.append("name:'教职工总人数',");
						//填充数据
						json.append("data:[");
							//获取数据
							if(list.size() > 0){
								Map sjmap;
								for(int i = 0; i < list.size(); i++){
									sjmap = (Map)list.get(i);
									json.append("{name:'" + Validate.isNullToDefault(sjmap.get("XDATA"), "") + "',value:"+Validate.isNullToDefault(sjmap.get("JZGRS"), "") + "},");
								}
								json.deleteCharAt(json.length() - 1);
								sjmap = null;
							}
						json.append("]");//data闭合
					json.append("},");
					json.append("{");
						json.append("name:'男性教职工人数',");
						//填充数据
						json.append("data:[");
							//获取数据
							if(list.size() > 0){
								Map sjmap;
								for(int i = 0; i < list.size(); i++){
									sjmap = (Map)list.get(i);
									json.append("{name:'" + Validate.isNullToDefault(sjmap.get("XDATA"), "") + "',value:"+Validate.isNullToDefault(sjmap.get("BOYS"), "") + "},");
								}
								json.deleteCharAt(json.length() - 1);
								sjmap = null;
							}
						json.append("]");//data闭合
					json.append("},");
					json.append("{");
						json.append("name:'女性教职工人数',");
						//填充数据
						json.append("data:[");
							//获取数据
							if(list.size() > 0){
								Map sjmap;
								for(int i = 0; i < list.size(); i++){
									sjmap = (Map)list.get(i);
									json.append("{name:'" + Validate.isNullToDefault(sjmap.get("XDATA"), "") + "',value:"+Validate.isNullToDefault(sjmap.get("GIRLS"), "") + "},");
								}
								json.deleteCharAt(json.length() - 1);
								sjmap = null;
							}
						json.append("]");//data闭合
					json.append("}");
				json.append("]");
			json.append("}");//jgoption闭合
			list = null;
		}
		//师生比分析
		if(map.containsKey("ssb")){
			list = map.get("ssb");
			Integer max=0;
			jzgdata = new StringBuffer();
			boydata = new StringBuffer();
			girldata = new StringBuffer();
			json.append(",ssboption:{");
				json.append("magicType:true,");
				json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
				json.append("xdata:[");
					if(list.size() > 0){
						Map rsmap;
						for(int i = 0; i < list.size(); i++){
							rsmap = (Map)list.get(i);
							int rs = Integer.parseInt(Validate.isNullToDefault(rsmap.get("XSRS"), "0") + "");					
							if(rs > max){
								max = rs;
							}
							json.append("'"+Validate.isNullToDefault(rsmap.get("YXMC"), "") + "',");
							jzgdata.append(Validate.isNullToDefault(rsmap.get("JZGRS"), "0") + ",");
							boydata.append(Validate.isNullToDefault(rsmap.get("XSRS"), "0") + ",");
							girldata.append(Validate.isNullToDefault(rsmap.get("SSB"), "0.00") + ",");
						}
						json.deleteCharAt(json.length() - 1);
						jzgdata.deleteCharAt(jzgdata.length() - 1);
						boydata.deleteCharAt(boydata.length() - 1);
						girldata.deleteCharAt(girldata.length() - 1);
					}
				json.append("],");
				json.append("yAxis:[");
				json.append("{");
					json.append("name:'人数（人）',");
					json.append("min: 0,max: "+max+",");
					json.append("interval: "+new DecimalFormat("0").format(max/10)+",");
					json.append("axisLabel: {formatter: '{value}'}");
				json.append("},{");
					json.append("name:'师生比（%）',");
					json.append("min: 0,max: 100,");
					json.append("interval: 10,");
					json.append("axisLabel: {formatter: '{value} %'}");
				json.append("}");
				json.append("],");//Y轴内容
				json.append("data:[{");
					json.append("name:'教职工人数',label:{normal:{show:true,position:'top'}},data:[" + jzgdata.toString() + "]");
				json.append("},{");
					json.append("name:'学生人数',label:{normal:{show:true,position:'top'}},data:[" + boydata.toString() + "]");
				json.append("},{");
					json.append("name:'师生比',type:'line',smooth:true,");
					//json.append("markLine : {data : [{type : 'average', name: '平均值'}]},");
					json.append("yAxisIndex: 1,");
					json.append("label:{normal:{show:true,position:'top'}},data:[" + girldata.toString() + "]");
				json.append("}]");
			json.append("}");
			
			list = null;
			jzgdata = null;
			boydata = null;
			girldata = null;
		}
		json.append("}");
		return json.toString();
	}

}
