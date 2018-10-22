package com.googosoft.service.tubiao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.tubiao.JzgcwxqDao;
import com.googosoft.pojo.tubiao.Zhcx;
import com.googosoft.util.Validate;

/**
 * 教职工财务校情分析
 * @author Administrator
 *
 */
@Service("jzgcwxqService")
public class JzgcwxqService {
	@Resource(name="jzgcwxqDao")
	public JzgcwxqDao jzgcwxqDao;
	
	
	public String getJzggzzc(Zhcx obj){
	    List<Map<String, Object>> list = this.jzgcwxqDao.getJzggzzc(obj);
        StringBuffer json = new StringBuffer();
        json.append("{success : true,");
        if(list.size() > 0){
            StringBuffer gzdata = new StringBuffer();
            json.append("gzzcoption:{");
                json.append("magicType:true,");
                json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
                json.append("xdata:[");
                    
                Map map;
                for(int i = 0; i < list.size(); i++){
                    map = (Map)list.get(i);
                    json.append("'"+Validate.isNullToDefault(map.get("YDDM"), "") + "月',");
                    gzdata.append(Validate.isNullToDefault(map.get("GZZC"), "0.00")+"," );
                }
                json.deleteCharAt(json.length() - 1);
                gzdata.deleteCharAt(gzdata.length() - 1);
                
                json.append("],");
                json.append("yAxis:[{name:'工资支出(元)'}],");//Y轴内容
                json.append("data:[{");
                    json.append("name:'工资支出曲线',type:'line',smooth:true,data:[" + gzdata.toString() + "]");
                json.append("},{");
                    json.append("name:'工资月度支出',label:{normal:{show:true,position:'top'}},data:[" + gzdata.toString() + "]");
                json.append("}]");
            json.append("}");
        }
        json.append("}");
        
        return json.toString();
        
	}
	
	
	
	
	/**
	 * 获取统计分析信息
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public String getJzgcwxq(Zhcx obj)  {
		@SuppressWarnings("unchecked")
		Map<String, List> map = jzgcwxqDao.getJzggzfb(obj);
		List list;
		StringBuffer json = new StringBuffer();//需要返回的数据
		StringBuffer data1;
		if(map.isEmpty()){
			return "{success:true,msg:'没有查询到数据'}";
		}
		json.append("{success:true");
		if(map.containsKey("gzfb")){
			list = map.get("gzfb");
			data1 = new StringBuffer();
			json.append(",gzfboption:{");
				json.append("magicType:true,");
				json.append("legendorient:'horizontal',");
				json.append("xdata:[");
					if(list.size() > 0){
						Map rsmap;
						for(int i = 0; i < list.size(); i++){
							rsmap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(rsmap.get("LB"), "") + "',");
							data1.append("{name:'" + Validate.isNullToDefault(rsmap.get("LB"), "") + "',value:"+Validate.isNullToDefault(rsmap.get("JE"), "0") + "},");
						}
						json.deleteCharAt(json.length() - 1);
						data1.deleteCharAt(data1.length() - 1);
					}
				json.append("],");
				json.append("data:[{name:'金额',");
					json.append("radius:['0','60%'],");//圆环
					json.append("center:['50%','60%'],");
					json.append("data:["+data1.toString()+"]");
				json.append("}]");
			json.append("}");
			
			list = null;
			data1 = null;
		}
		if(map.containsKey("gzbh")){
			list = map.get("gzbh");
			data1 = new StringBuffer();
			StringBuffer qcData = new StringBuffer();
			if(list.size() > 0){
    			json.append(",gzbhoption:{");
        			json.append("magicType:true,");//折线图和柱状图切换
        			json.append("legendorient:'horizontal',");//图例horizontal横向排列，vertical纵向排列
        			json.append("ydata:[");
        			Map rsmap;
        			for(int i = 0; i < list.size(); i++){
        				rsmap = (Map)list.get(i);
        				json.append("'" + Validate.isNullToDefault(rsmap.get("YDDM"), "") + "月',");
        				qcData.append(Validate.isNullToDefault(rsmap.get("sfgz"), "0") + ",");
        				
        			}
        			json.deleteCharAt(json.length() - 1);
        			qcData.deleteCharAt(qcData.length() - 1);
        			json.append("],");//ydata闭合
        			json.append("xAxis:[{ name:'（元）' }],");//Y轴内容
        			json.append("data:[{");
        				json.append("name:'月份',");
        				json.append("label:{normal:{show:true,position:'right'}},");
        				json.append("data:[" + qcData.toString() + "]");
        			json.append("}]");//data闭合
    			json.append("}");//sltjoption闭合
			}
			
			list = null;
			data1 = null;
		}
		json.append("}");//json闭合
		return json.toString();
	}
}
