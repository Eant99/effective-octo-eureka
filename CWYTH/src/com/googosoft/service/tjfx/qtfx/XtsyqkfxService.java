package com.googosoft.service.tjfx.qtfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.tjfx.qtfx.XtsyqkfxDao;
import com.googosoft.pojo.tjfx.Zhcx;
import com.googosoft.util.Validate;

/**
 * 系统使用情况分析service
 * @author master
 */
@Service("xtsyqkfxService")
public class XtsyqkfxService {
	@Resource(name="xtsyqkfxDao")
	public XtsyqkfxDao xtsyqkfxDao;
	
	/**
	 * 获取统计分析信息
	 * @return
	 * @throws Exception 
	 */
	public String getXtsyqkfxList(Zhcx obj)  {
		Map<String, List> map = xtsyqkfxDao.getXtsyqkfxList(obj);
		List list = new ArrayList();
		StringBuffer json = new StringBuffer();
		StringBuffer data;
		json.append("{success:true");
		if(map.containsKey("syr")){
			list = map.get("syr");
			json.append(",syroption:{");
				json.append("magicType:true,");
				json.append("dataZoom:{show:false},");
				json.append("grid:{left:55,right:55,bottom:25},");
				json.append("yAxis:[{");
				json.append("name:'使用频次'");
				json.append("}],");
				json.append("xAxis:[{");
					json.append("name:'使用人',");
					json.append("data:[");
						data = new StringBuffer();
						if(list.size() > 0){
							Map rymap;
							for(int i = 0; i < list.size(); i++){
								rymap = (Map)list.get(i);
								json.append("'" + Validate.isNullToDefault(rymap.get("XM"), "") + "',");
								data.append(Validate.isNullToDefault(rymap.get("CNT"), "") + ",");
							}
							rymap = null;
							json.deleteCharAt(json.length() - 1);
							data.deleteCharAt(data.length() - 1);
						}
					json.append("]");
				json.append("}],");
		
				json.append("data:[{");
					json.append("name:'使用人',");
					json.append("markLine:{data:[{type:'average',name:'平均值'}]},");
					json.append("markPoint:{data:[{type:'max',name:'最大值'},{type:'min',name:'最小值'}]},");
					json.append("data:[" + data.toString() + "]");
					data = null;
				json.append("}]");
				
			json.append("}");
			list = null;
		}

		if(map.containsKey("ip")){
			list = map.get("ip");
			json.append(",ipoption:{");
				json.append("magicType:true,");
				json.append("grid:{left:30},");
				json.append("yAxis:[{");
				json.append("name:'使用频次',");
				json.append("}],");
				json.append("xAxis:[{");
				json.append("name:'IP地址',");
				json.append("data:[");
				data = new StringBuffer();
				if(list.size() > 0){
					Map sjmap;
					for(int i = 0; i < list.size(); i++){
						sjmap = (Map)list.get(i);
						json.append("'" + Validate.isNullToDefault(sjmap.get("CZJQ"), "") + "',");
						data.append(Validate.isNullToDefault(sjmap.get("CNT"), "") + ",");
					}
					sjmap = null;
					json.deleteCharAt(json.length() - 1);
					data.deleteCharAt(data.length() - 1);
				}
				json.append("]");
				json.append("}],");
				json.append("data:[{");
					json.append("name:'IP地址',");
					json.append("markLine:{data:[{type:'average',name:'平均值'}]},");
					json.append("markPoint:{data:[{type:'max',name:'最大值'},{type:'min',name:'最小值'}]},");
					json.append("data:[" + data.toString() + "]");
					data = null;
				json.append("}]");
			json.append("}");
			list = null;
		}
		
		
		if(map.containsKey("czlx") || map.containsKey("djlx")){
			String xdata = "";
			data = new StringBuffer();
			StringBuffer czlx_data = new StringBuffer();
			StringBuffer djlx_data = new StringBuffer();
			StringBuffer czlx_xdata = new StringBuffer();
			StringBuffer djlx_xdata = new StringBuffer();
			Map lxmap;
			String rs = "0";
			
			if(map.containsKey("czlx")){
				list = map.get("czlx");
				if(list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						lxmap = (Map)list.get(i);
						rs = Validate.isNullToDefault(lxmap.get("CNT"), "") + "";
						xdata = Validate.isNullToDefault(lxmap.get("CZLX"), "") + ":" + rs + "条";
						//data.append(",'" + xdata + "'");
						czlx_xdata.append("'" + xdata + "',");
						czlx_data.append("{name:'" + xdata + "',value:" + rs + "},");
					}
					czlx_xdata.deleteCharAt(czlx_xdata.length() - 1);
					czlx_data.deleteCharAt(czlx_data.length() - 1);
					lxmap = null;
				}
				list = null;
			}
			
			if(map.containsKey("djlx")){
				list = map.get("djlx");
				if(list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						lxmap = (Map)list.get(i);
						rs = Validate.isNullToDefault(lxmap.get("CNT"), "") + "";
						xdata = Validate.isNullToDefault(lxmap.get("DJLX"), "") + ":" + rs + "条";
						//data.append(",'" + xdata + "'");
						djlx_xdata.append("'" + xdata + "',");
						djlx_data.append("{name:'" + xdata + "',value:" + rs + "},");
					}
					djlx_xdata.deleteCharAt(djlx_xdata.length() - 1);
					djlx_data.deleteCharAt(djlx_data.length() - 1);
					lxmap = null;
				}
				list = null;
			}
			
			json.append(",lxoption:{");
				json.append("title:{");
					json.append("subtext:'外部环形图、右侧图例显示操作类型分析；内部饼状图、左侧图例显示单据类型分析',");
					json.append("left:'center',");
					json.append("bottom:1");
				json.append("},");
				json.append("grid:{bottom:0,containLabel:true},");
				json.append("toolbox:{feature:{saveAsImage:{show:false}}},");
				json.append("tooltip:{trigger: 'item',formatter:'{a} <br/>{b}({d}%)'},");
				//json.append("xdata:[" + data.toString().substring(1) + "],");
				json.append("legend:[{");
					json.append("show:true,");
					json.append("right:1,");
					json.append("orient:'vertical',");
					json.append("data:[" + czlx_xdata.toString() + "]");
				json.append("},{");
					json.append("show:true,");
					json.append("left:1,");
					json.append("orient:'vertical',");
					json.append("data:[" + djlx_xdata.toString() + "]");
				json.append("}],");
				json.append("data:[{");
					json.append("name:'操作类型',");
					json.append("label:{normal:{show:true,position:'inside',formatter:'{d}%'}},");
					//json.append("roseType:'radius',");
					//json.append("roseType:'area',");
					json.append("radius:['45%','70%'],");
					json.append("center:['50%','60%'],");
					json.append("data:[" + czlx_data.toString() + "]");
				json.append("},{");
					json.append("name:'单据类型',");
					json.append("label:{normal:{show:true,position:'inside',formatter:'{d}%'}},");
					//json.append("roseType:'radius',");
					//json.append("roseType:'area',");
					json.append("radius:['0','40%'],");
					json.append("center:['50%','60%'],");
					json.append("data:[" + djlx_data.toString() + "]");
				json.append("}]");
			json.append("}");
			
			data = null;
			czlx_xdata = null;
			djlx_xdata = null;
			czlx_data = null;
			djlx_data = null;
		}
		
		if(map.containsKey("sjd")){
			list = map.get("sjd");
			json.append(",sjdoption:{");
				json.append("magicType:true,");
				json.append("grid:{left:30},");
				json.append("xdata:[");
				data = new StringBuffer();
				if(list.size() > 0){
					Map sjmap;
					for(int i = 0; i < list.size(); i++){
						sjmap = (Map)list.get(i);
						json.append("'" + Validate.isNullToDefault(sjmap.get("CZRQ"), "") + "',");
						data.append(Validate.isNullToDefault(sjmap.get("CNT"), "") + ",");
					}
					sjmap = null;
					json.deleteCharAt(json.length() - 1);
					data.deleteCharAt(data.length() - 1);
				}
				json.append("],");
				json.append("data:[{");
					json.append("name:'时间点',");
					json.append("markLine:{data:[{type:'average',name:'平均值'}]},");
					json.append("markPoint:{data:[{type:'max',name:'最大值'},{type:'min',name:'最小值'}]},");
					json.append("data:[" + data.toString() + "]");
					data = null;
				json.append("}]");
			json.append("}");
			list = null;
		}
		
		json.append("}");
		return json.toString();
	}
}
