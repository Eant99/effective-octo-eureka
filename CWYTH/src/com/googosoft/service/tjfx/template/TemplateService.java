package com.googosoft.service.tjfx.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.tjfx.template.TemplateDao;
import com.googosoft.pojo.tjfx.Zhcx;
import com.googosoft.util.Validate;

/**
 * 系统使用情况分析service
 * @author zjy
 */
@Service("templateService")
public class TemplateService {
	@Resource(name="templateDao")
	public TemplateDao templateDao;
	
	/**
	 * 获取统计分析信息
	 * @return
	 * @throws Exception 
	 */
	public String getTemplateList(Zhcx obj)  {
		Map<String, List> map = templateDao.getTemplateList(obj);
		List list = new ArrayList();
		StringBuffer json = new StringBuffer();
		StringBuffer data;
		json.append("{success:true");
		if(map.containsKey("syr")){
			list = map.get("syr");
			json.append(",syroption:{");
				json.append("title:{");//标题
					json.append("text:'使用人分布情况',");//主标题
					json.append("subtext:'日志分析',");//副标题
					json.append("left:'center',");//标题的横向位置(相对于标题区域)  'left', 'center', 'right'  也可以是数值或者百分数
					json.append("top:'top'");//标题的纵向位置(相对于标题区域)  'top', 'middle', 'bottom'  也可以是数值或者百分数
				json.append("},");
				json.append("tooltip:{");//提示信息
					json.append("trigger:'axis'");//提示信息的处罚形式，axis:坐标轴触发，一般用在柱状、折线这种具有坐标轴的图形中，item:数据项触发，一般用在饼状、散点这种图形中
//					json.append("axisPointer:{");// 坐标轴指示器（鼠标移动到坐标上时显示的标记线），坐标轴触发有效
//						json.append("type:'shadow'");        //默认为直线，可选为：'line' | 'cross' | 'shadow'
//					json.append("}");
					//折线（区域）图、柱状（条形）图、K线图 : {a}（系列名称），{b}（类目值），{c}（数值）, {d}（无）
					//散点图（气泡）图 : {a}（系列名称），{b}（数据名称），{c}（数值数组）, {d}（无）
					//地图 : {a}（系列名称），{b}（区域名称），{c}（合并数值）, {d}（无）
					//饼图、仪表盘、漏斗图: {a}（系列名称），{b}（数据项名称），{c}（数值）, {d}（百分比）
//					json.append("formatter:'{a}<br/>{b0}:{c0}<br/>{b1}:{c1}'");
				json.append("},");
				json.append("toolbox:{");//工具栏
					json.append("show:true,");//是否显示
					json.append("orient:'horizontal',");//排列方向  vertical横向  horizontal纵向
					json.append("itemSize:15,");//图标大小，单位px
					json.append("itemGap:10,");//图标间隔
					json.append("showTitle:true,");//是否显示每个按钮的提示信息
					json.append("right:1,");//右边距，默认'auto'
					json.append("top:1,");//上边距
					//json.append("left:'auto',");//左边距
					//json.append("bottom:'auto',");//下边距
					json.append("feature:{");//工具配置项
						json.append("magicType:{");//动态类型切换
							json.append("show:true,");
							json.append("title : {");
//								json.append("line : '动态类型切换-折线',");
//								json.append("bar : '动态类型切换-柱状',");
								json.append("stack : '动态类型切换-堆积',");
								json.append("tiled : '动态类型切换-平铺'");
							json.append("},");
							json.append("type : ['stack', 'tiled']");
							//json.append("type : ['line','bar','stack', 'tiled']");
						json.append("},");
						json.append("saveAsImage:{");//保存按钮
							json.append("show : true,");
							json.append("title : '保存成图片',");//按钮提示信息
							json.append("type : 'png',");//支持png和jpeg
							json.append("pixelRatio:2,");//1是跟容器分辨率一致，高于1的都是大于容器分辨率的
							json.append("excludeComponents:['toolbox','dataZoom']");//保存时排除的配置项
						json.append("}");
					json.append("}");
				json.append("},");
				json.append("legend:{");//图例组件
					json.append("show:true,");
					json.append("left:'left',");
					json.append("top:'top',");
					json.append("formatter:'{name}',");//显示样式
					//json.append("formatter:'图例：{name}',");
					json.append("data:[{");//图例显示的内容
						json.append("name:'使用人',");
//						json.append("textStyle:{");
//							json.append("color:'red'");
//						json.append("},");
						json.append("icon:'circle'");
					json.append("},{");
						json.append("name:'使用单位',");
//						json.append("textStyle:{");
//							json.append("color:'blue'");
//						json.append("},");
						json.append("icon:'circle'");
					json.append("}]");//
					//json.append("data:['使用人','使用单位']");//
					//json.append("data:['使用人']");
				json.append("},");
				json.append("dataZoom:{");//区域缩放
					json.append("show : true,");
					json.append("type:'slider',");//slider：滑动条型  inside：内置型
					json.append("orient:'vertical',");
					json.append("width:24,");
					json.append("z:3,");//层级，数越大，层级越高
					//json.append("height:'60%',");
					//不写xAxisIndex和yAxisIndex时控制全部
					//json.append("orient:'horizontal',");//这是horizontal时使用xAxisIndex
					//json.append("xAxisIndex:[0,2],");//控制第一个和第三个xAxis
					//json.append("xAxisIndex:3,");//控制第四个xAxis
					//json.append("orient:'vertical',");//这是vertical时使用yAxisIndex
					//json.append("yAxisIndex:[0,2],");//控制第一个和第三个yAxis
					//json.append("yAxisIndex:0,");//控制第1个yAxis
					//json.append("start:0,");//数据范围的起始百分比
					//json.append("end:100,");//数据范围的结束百分比
					//json.append("startValue:'',");//数据窗口范围的起始数值，跟start冲突，start优先级更高,可以是数据集合的索引，也可以是数据本身
					//json.append("endValue:'',");//数据窗口范围的结束数值，跟end冲突，end优先级更高,可以是数据集合的索引，也可以是数据本身
					//json.append("dataBackground:");//阴影样式，注意type:'inside'时没有这个属性
					//json.append("labelFormatter:'aaaa {value} bbbb',");//显示提示的样式，注意type:'inside'时没有这个属性
					json.append("right:10,");//右边距，默认'auto'
					json.append("top:60,");//上边距
					//json.append("left:'left',");//左边距
					json.append("bottom:40,");//下边距
					json.append("filterMode:'filter',");//filter:窗口外的数据被过滤掉，影响其他轴的数据范围   empty:窗口外的数据设置为空，不影响其他轴的数据范围
					json.append("zoomLock:false,");//设置为true，则锁定区域大小，只能平移，不能缩放
					json.append("backgroundColor:'rgba(47,69,84,0)',");//组件的背景色，注意type:'inside'时没有这个属性
					json.append("fillerColor:'rgba(167,183,204,0.4)',");//选中范围的填充样是，注意type:'inside'时没有这个属性
					json.append("showDetail:true,");//拖动时是否显示详细信息，注意type:'inside'时没有这个属性
					json.append("showDataShadow:'auto',");//组件中显示数据阴影，注意type:'inside'时没有这个属性
					json.append("realtime : true");//拖动时是否实时更新，注意type:'inside'时没有这个属性
				json.append("},");
				json.append("grid:{left:55,right:45,bottom:25},");//直角坐标系内网格（类似于绘图的画布）
				json.append("yAxis:[{");
					json.append("type:'category',");//类目轴  value:非类目轴（数值轴）
					//json.append("name:'我是x轴',");//坐标轴的名称
					//json.append("nameLocation:'middle',");//坐标轴名称的位置  start：起始（左）    end：结束（右）
					//json.append("nameGap:0,");//坐标轴名称与轴线之间的距离，默认15，单位px
					json.append("position:'left',");
					json.append("boundaryGap:true,");//柱形图会把中间点对准Y轴，所以一定要是true，但是换成折线图true的话，第一个点就不会在Y轴上了
					json.append("scale:false,");//true不会强制包含0刻度，只在type:'value'时有效，设置 min 和 max 后无效
					json.append("axisLine: {");//轴线
						json.append("onZero: true");//设置为true，则强制将轴线设置到另一个轴线的0刻度上
					json.append("},");
					json.append("axisLabel: {");//坐标轴刻度标签的相关设置。
						json.append("interval:'auto'");//坐标轴刻度标签的显示间隔，只在类目轴中有效。0强制显示所有标签,不设置则自动显示不重叠的标签
					json.append("},");
					json.append("splitLine: {");//显示区域内的背景线设置
						json.append("show:true,");//是否显示
						json.append("lineStyle: {");//线的样式
							json.append("type:'dashed'");//虚线
						json.append("}");
					json.append("},");
					json.append("data:[");
					data = new StringBuffer();
					if(list.size() > 0){
						Map rymap;
						for(int i = 0; i < list.size(); i++){
							rymap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(rymap.get("RYBH"), "") + "',");
							data.append(Validate.isNullToDefault(rymap.get("CNT"), "") + ",");
						}
						rymap = null;
						json.deleteCharAt(json.length() - 1);
						data.deleteCharAt(data.length() - 1);
					}
					json.append("]");
				json.append("}],");
				json.append("xAxis : [{");
					json.append("type:'value',");//非类目轴
					//json.append("name:'我是y轴',");//坐标轴的名称
					//json.append("nameLocation:'middle',");//坐标轴名称的位置  start：起始（下）    end：结束（上）
					//json.append("nameGap:20,");//坐标轴名称与轴线之间的距离，默认15，单位px
//					json.append("boundaryGap:['20%','20%'],");//注意这里的boundaryGap跟类目轴不一样，只能是这种数组形式
					//json.append("data:[],");//非类目轴不用设置data
					json.append("axisLabel: {");
						//json.append("formatter:'{value} 次',");//坐标轴显示的样式
						json.append("formatter:function(value, index){");
							//json.append("return 'X轴：' + value;");
							json.append("return value;");
						json.append("},");//y轴显示的样式
						json.append("interval:0");
					json.append("},");
					json.append("splitLine: {");
						json.append("show:true,");
						json.append("lineStyle: {");
							json.append("type:'dashed'");
						json.append("}");
					json.append("}");
				json.append("}],");
				
//				json.append("visualMap:[{");//视觉映射组件(就是每个区域的数值、颜色等信息)
//					json.append("type:'piecewise',");//continuous:连续型  piecewise:分段型
//					//json.append("range:[],");//最小手柄和最大手柄的位置，默认是[min,max]，不设置range的时候，range虽min和max改变，但设置了range之后，再改变min或max，range不会改变
//					json.append("top:35,");//该组件相对于容器的上边距
//					json.append("right:1,");//该组件相对于容器的右边距
//					//json.append("left:1,");//该组件相对于容器的右边距
//					//json.append("bottom:1,");//该组件相对于容器的右边距，注意这四个位置不能同时设置
//					json.append("outOfRange:{color: '#999'},");//不在分段中的部分，显示的颜色
////					json.append("splitNumber:6");//自动分段数，默认是5，如果是自动分段，不需要下边这部分
//					json.append("pieces:[");
//						json.append("{lte: 200,color: '#096',label:'最小区域'},");//lt:小于  gt:大于  lte:小于等于  gte:大于等于
//						json.append("{gt:200,lte: 400,color: '#ffde33'},");
//						json.append("{gt:400,lte: 600,color: '#ff9933'},");
//						json.append("{gt:600,lte: 800,color: '#cc0033'},");
//						json.append("{gt:800,lte: 1000,color: '#660099'},");
//						json.append("{gt:1000,color: '#7e0023'}");
//					json.append("]");
//				json.append("}],");
				
				json.append("series:[{");
					json.append("name:'使用人',");//注意，这里要跟legend.data下的name保持一致
					json.append("type:'bar',");//图标类型
//					json.append("markLine:{");//分隔标记线（如果不需要标记线，那么需要去掉这部分和visualMap）
//						json.append("data:[");
//							json.append("{yAxis: 200},");
//							json.append("{yAxis: 400},");
//							json.append("{yAxis: 600},");
//							json.append("{yAxis: 800},");
//							json.append("{yAxis: 1000}");
//						json.append("],");
//						json.append("silent:true");//是否响应和触发鼠标事件
//					json.append("},");//分割线
					json.append("data:[" + data.toString() + "]");
				json.append("},{");
					json.append("name:'使用单位',");
					json.append("type:'line',");
//					json.append("markLine:{");//分隔标记线（如果不需要标记线，那么需要去掉这部分和visualMap）
//						json.append("data:[");
//							json.append("{yAxis: 200},");
//							json.append("{yAxis: 400},");
//							json.append("{yAxis: 600},");
//							json.append("{yAxis: 800},");
//							json.append("{yAxis: 1000}");
//						json.append("],");
//						json.append("silent:true");//是否响应和触发鼠标事件
//					json.append("},");//分割线  
					json.append("data:[" + data.toString() + "]");
					data = null;
				json.append("}]");
			json.append("}");
			list = null;
		}
		if(map.containsKey("czrq")){
			list = map.get("czrq");
			json.append(",sjoption:{");
				json.append("title:{");//标题
					json.append("text:'操作时间分布情况',");
					json.append("left:'center',");
					json.append("top:'top'");
				json.append("},");
				json.append("tooltip:{");
					json.append("trigger:'axis'");
				json.append("},");
				json.append("toolbox:{");
					json.append("show:true,");
					json.append("right:1,");
					json.append("top:1,");
					json.append("feature:{");
						json.append("saveAsImage:{");
							json.append("show : true,");
							json.append("title : '保存成图片',");
							json.append("type : 'png',");
							json.append("pixelRatio:2,");
							json.append("excludeComponents:['toolbox','dataZoom']");
						json.append("}");
					json.append("}");
				json.append("},");
				json.append("legend:{");//图例组件
					json.append("show:false");
				json.append("},");
				json.append("dataZoom:{");
					json.append("show : true,");
					json.append("type:'inside',");
					json.append("realtime : true");
				json.append("},");
				//json.append("grid:{left:'10%',right:0},");
				json.append("grid:{left:'30',right:0},");
				json.append("xAxis:[{");
					json.append("type:'category',");
					json.append("boundaryGap:false,");
					json.append("data:[");
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
					json.append("]");
				json.append("}],");
				json.append("yAxis : [{");
					json.append("type:'value',");
					json.append("boundaryGap:false,");
					json.append("formatter: '{value} 次',");
					json.append("scale:true");
				json.append("}],");
				json.append("series:[{");
					json.append("name:'操作时间',");
					json.append("type:'line',");
					json.append("data:[" + data.toString() + "]");
					data = null;
				json.append("}]");
			json.append("}");
			list = null;
		}
		if(map.containsKey("djlx")){
			list = map.get("djlx");
			json.append(",djlxoption:{");
				json.append("title:{");
					//json.append("text:'单据类型分布情况',");
					json.append("left:'center',");
					json.append("top:'top'");
				json.append("},");
				json.append("tooltip:{");
					json.append("trigger:'item',");
					json.append("formatter:'{a} <br/>{b} : {c} ({d}%)'");
				json.append("},");
				json.append("toolbox:{");
					json.append("show:true,");
					json.append("orient:'horizontal',");
					json.append("right:1,");
					json.append("top:1,");
					json.append("feature:{");
						json.append("saveAsImage:{");
							json.append("show : true,");
							json.append("title : '保存成图片',");
							json.append("type : 'png',");
							json.append("pixelRatio:2,");
							json.append("excludeComponents:['toolbox']");
						json.append("}");
					json.append("}");
				json.append("},");
				json.append("legend:{");
					json.append("show:true,");
					json.append("orient:'vertical',");
					json.append("left:'left',");
					json.append("data:[");
					data = new StringBuffer();
					if(list.size() > 0){
						Map lxmap;
						for(int i = 0; i < list.size(); i++){
							lxmap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(lxmap.get("DJLX"), "") + "',");
							data.append("{value:" + Validate.isNullToDefault(lxmap.get("CNT"), "") + ",name:'" + Validate.isNullToDefault(lxmap.get("DJLX"), "") + "'},");
						}
						lxmap = null;
						json.deleteCharAt(json.length() - 1);
						data.deleteCharAt(data.length() - 1);
					}
					json.append("]");
				json.append("},");
				json.append("series:[{");
					json.append("name:'单据类型',");
					//json.append("roseType:'radius',");//是否显示成南丁格尔图
					//json.append("roseType:'area',");
					json.append("radius:['0','60%'],");//半径 [内环半径，外环半径]
					json.append("center:['60%','50%'],");//中心点位于容器中的位置，[左右,上下]
					json.append("type:'pie',");
					json.append("data:[" + data.toString() + "]");
					data = null;
				json.append("}]");
			json.append("}");
			list = null;
		}
		if(map.containsKey("ip")){
			list = map.get("ip");
			json.append(",ipoption:{");
				json.append("title:{");
					json.append("text:'IP地址分布情况',");
					json.append("left:'center',");
					json.append("top:'top'");
				json.append("},");
				json.append("tooltip:{");
					json.append("trigger:'axis'");
				json.append("},");
				json.append("toolbox:{");
					json.append("show:true,");
					json.append("right:1,");
					json.append("top:1,");
					json.append("feature:{");
						json.append("saveAsImage:{");
							json.append("show : true,");
							json.append("title : '保存成图片',");
							json.append("type : 'png',");
							json.append("pixelRatio:2,");
							json.append("excludeComponents:['toolbox','dataZoom']");
						json.append("}");
					json.append("}");
				json.append("},");
				json.append("legend:{");
					json.append("show:false");
				json.append("},");
				json.append("dataZoom:{");
					json.append("show : true,");
					json.append("type:'inside',");
					json.append("realtime : true");
				json.append("},");
				json.append("grid:{left:45,right:140},");
				json.append("xAxis:[{");
					json.append("type:'category',");
					json.append("boundaryGap:true,");
					json.append("axisLabel:{rotate:35},");//标签旋转，35是百分比 可以是-数
					json.append("data:[");
					data = new StringBuffer();
					int maxrs = 0;
					int minrs = 0;
					if(list.size() > 0){
						Map sjmap;
						for(int i = 0; i < list.size(); i++){
							sjmap = (Map)list.get(i);
							json.append("'" + Validate.isNullToDefault(sjmap.get("CZJQ"), "") + "',");
							int rs = Integer.parseInt(Validate.isNullToDefault(sjmap.get("CNT"), "") + "");
							maxrs = maxrs + rs;
							if(i == 0){
								minrs = rs;
							}
							else if(rs < minrs){
								minrs = rs;
							}
							data.append(rs + ",");
						}
						sjmap = null;
						json.deleteCharAt(json.length() - 1);
						data.deleteCharAt(data.length() - 1);
					}
					json.append("]");
				json.append("}],");
				json.append("yAxis : [{");
					json.append("type:'value',");
					json.append("boundaryGap:false,");
					json.append("scale:true");
				json.append("}],");
				json.append("visualMap:[{");
					json.append("type:'piecewise',");
					json.append("top:35,");
					json.append("right:1,");
					//json.append("precision:0,");//自动分段时没有作用，表示精度
					json.append("outOfRange:{color: '#999'},");
					json.append("color: ['#d94e5d','#eac736','#50a3ba'],");
					json.append("splitNumber:5,");
					json.append("min: " + minrs + ",");
					json.append("max:" + maxrs);
//					json.append("pieces:[");
//						json.append("{lte: 200,color: '#096',label:'最小区域'},");
//						json.append("{gt:200,lte: 400,color: '#ffde33'},");
//						json.append("{gt:400,lte: 600,color: '#ff9933'},");
//						json.append("{gt:600,lte: 800,color: '#cc0033'},");
//						json.append("{gt:800,lte: 1000,color: '#660099'},");
//						json.append("{gt:1000,color: '#7e0023'}");
//					json.append("]");
				json.append("}],");
				json.append("series:[{");
					json.append("name:'IP地址',");
					json.append("type:'bar',");
					json.append("markLine:{");
						json.append("data:[");
							json.append("{type:'average',name:'平均人数'}");
//							json.append("{yAxis: 200},");
//							json.append("{yAxis: 400},");
//							json.append("{yAxis: 600},");
//							json.append("{yAxis: 800},");
//							json.append("{yAxis: 1000}");
						json.append("],");
						json.append("silent:true");
					json.append("},");
					json.append("markPoint:{data:[{type:'max',name:'最大值'},{type:'min',name:'最小值'}]},");
					json.append("data:[" + data.toString() + "]");
					data = null;
				json.append("}]");
			json.append("}");
			list = null;
		}
		if(map.containsKey("ryip")){
			list = map.get("ryip");
			List rylist = map.get("ryip_ry");
			List iplist = map.get("ryip_ip");
			Map ryipmap;
			data = new StringBuffer();
			if(list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					ryipmap = (Map)list.get(i);
					data.append("'" + Validate.isNullToDefault(ryipmap.get("CZRQ"), "") + "',");
				}
				ryipmap = null;
				data.deleteCharAt(data.length() - 1);
			}
			StringBuffer ipdata = new StringBuffer();
			if(iplist.size() > 0){
				for(int i = 0; i < iplist.size(); i++){
					ryipmap = (Map)iplist.get(i);
					ipdata.append("['" + Validate.isNullToDefault(ryipmap.get("CZRQ"), "") + "'," + Validate.isNullToDefault(ryipmap.get("CNT"), "") + "],");
				}
				ryipmap = null;
				ipdata.deleteCharAt(ipdata.length() - 1);
			}
			StringBuffer rydata = new StringBuffer();
			if(rylist.size() > 0){
				for(int i = 0; i < rylist.size(); i++){
					ryipmap = (Map)rylist.get(i);
					rydata.append("['" + Validate.isNullToDefault(ryipmap.get("CZRQ"), "") + "'," + Validate.isNullToDefault(ryipmap.get("CNT"), "") + "],");
				}
				ryipmap = null;
				rydata.deleteCharAt(rydata.length() - 1);
			}
			
			json.append(",ryipoption:{");
				json.append("title:{");//标题
					json.append("text:'人员IP分布情况',");
					json.append("left:'center',");
					json.append("top:'top'");
				json.append("},");
				json.append("animation: false,");
				json.append("legend: {");
					json.append("data: ['使用人','人员','IP地址','IP'],");
					json.append("left:'left'");
				json.append("},");
				json.append("tooltip: {");
					json.append("show:false");
				json.append("},");
				json.append("toolbox:{");
					json.append("show:true,");
					json.append("right:1,");
					json.append("top:1,");
					json.append("feature:{");
						json.append("saveAsImage:{");
							json.append("show : true,");
							json.append("title : '保存成图片',");
							json.append("type : 'png',");
							json.append("pixelRatio:2,");
							json.append("excludeComponents:['toolbox','dataZoom']");
						json.append("}");
					json.append("}");
				json.append("},");
				json.append("xAxis: {");
					json.append("type: 'category',");
					json.append("data:[" + data.toString() + "],");
					json.append("axisLabel: {");
						json.append("interval:'auto'");
					json.append("},");
					json.append("splitLine: {");
						json.append("show: true");
					json.append("}");
				json.append("},");
				json.append("yAxis: {");
					json.append("type: 'value',");
					json.append("min: 'dataMin',");
					json.append("max: 'dataMax',");
					json.append("splitLine: {");
						json.append("show: true");
					json.append("}");
				json.append("},");
				json.append("dataZoom: [{");
					json.append("type: 'inside',");
					json.append("xAxisIndex: [0]");
				json.append("},{");
					json.append("type: 'inside',");
					json.append("yAxisIndex: [0]");
				json.append("}],");
				json.append("series: [{");
					json.append("name:'使用人',");
					json.append("type:'bar',");
					json.append("label: {");
						json.append("emphasis: {");
							json.append("show: true,");
							json.append("formatter: function (param) {");
								json.append("return '人员:' + param.data[0] + ':' + param.data[1];");
							json.append("},");
							json.append("position: 'top'");
						json.append("}");
					json.append("},");
					json.append("data:[" + rydata.toString() + "]");
				json.append("},{");
					json.append("name: '人员',");
					json.append("type: 'scatter',");
					json.append("label: {");
						json.append("emphasis: {");
							json.append("show: true,");
							json.append("formatter: function (param) {");
								json.append("return '人员:' + param.data[0] + ':' + param.data[1];");
							json.append("},");
							json.append("position: 'top'");
						json.append("}");
					json.append("},");
					json.append("itemStyle: {");
						json.append("normal: {");
							json.append("opacity: 0.8");//透明度 0时不显示
						json.append("}");
					json.append("},");
					//json.append("symbolSize:10,");
					json.append("symbolSize:function (data) {");
						//json.append("return Math.sqrt(data[1]) * 5;");
						json.append("return 20;");
					json.append("},");
					json.append("data: [" + rydata.toString() + "]");
				json.append("},{");
					json.append("name:'IP地址',");
					json.append("type:'line',");
					json.append("label: {");
					json.append("emphasis: {");
						json.append("show: true,");
						json.append("formatter: function (param) {");
							json.append("return 'IP:' + param.data[0] + ':' + param.data[1];");
						json.append("},");
						json.append("position: 'top'");
					json.append("}");
				json.append("},");
					json.append("data:[" + ipdata.toString() + "]");
				json.append("},{");
					json.append("name: 'IP',");
					json.append("type: 'scatter',");
					json.append("label: {");
						json.append("emphasis: {");
							json.append("show: true,");
							json.append("formatter: function (param) {");
								json.append("return 'IP:' + param.data[0] + ':' + param.data[1];");
							json.append("},");
							json.append("position: 'top'");
						json.append("}");
					json.append("},");
					json.append("itemStyle: {");
						json.append("normal: {");
							json.append("opacity: 0.8");
						json.append("}");
					json.append("},");
					json.append("symbolSize:10,");
//					json.append("symbolSize:function (data) {");
//						json.append("return Math.sqrt(data[1]) * 5;");
//					json.append("},");
					json.append("data: [" + ipdata.toString() + "]");
				json.append("}]");
			json.append("}");
			data = null;
			rydata = null;
			ipdata = null;
		}
		//区域分布图
		if(map.containsKey("qyfb")){
			list = map.get("qyfb");
			json.append(",dtoption:{");
				json.append("title:{");//区域分布图标题
					json.append("text:'学生生源地分布',");//标题
					json.append("x:'center',");
					json.append("y:'top'");
				json.append("},");
				json.append("tooltip:{");//提示信息，鼠标悬浮提示
					json.append("trigger:'item'");
				json.append("},");
				json.append("toolbox: {");//工具，右侧显示的内容
					json.append("show: true,");//是否显示
					json.append("orient : 'vertical',");//排列方向  vertical横向  horizontal纵向
					json.append("x: 'right',");
					json.append("y: 'center',");
					json.append("feature : {");
						json.append("mark : {");
							json.append("show: false");
						json.append("},");
						json.append("saveAsImage : {");//保存为图片
							json.append("show: true");
						json.append("}");
					json.append("}");
				json.append("},");
				json.append("legend:{");//图例组件，颜色代表说明，左上角所显示的内容
					json.append("x:'left',");
					json.append("data:[");//图例显示的内容
						json.append("'学生生源地'");//必须和series中的name相同
					json.append("]");
				json.append("},");
				
				json.append("dataRange: {");//数据显示说明，左下角所显示的内容
					json.append("min: 0,");//数据最小值
					json.append("max: 20000,");//数据最大值
					json.append("x: 'left',");
					json.append("y: 'bottom',");
					json.append("text:['高','低'],");// 文本，默认为数值文本
					json.append("calculable : true");//最大值和最小值是否显示
				json.append("},");
				
				json.append("series:[{");
					json.append("name:'学生生源地',");
					json.append("type:'map',");
					json.append("map:'china',");//代表中国地图
					
					json.append("itemStyle:{");
						json.append("normal:{");
							json.append("borderWidth:2,");//地图中各省边框的宽度
							json.append("borderColor:'lightgreen',");//地图中各省边框的颜色
							json.append("label:{");
								json.append("show:true");//各省市名称默认是否显示，true默认显示，false默认不显示
							json.append("}");
						json.append("}");
					json.append("},");
					
					//填充数据
					json.append("data:[");
						//获取数据
						if(list.size() > 0){
							Map sjmap;
							for(int i = 0; i < list.size(); i++){
								sjmap = (Map)list.get(i);
								json.append("{name:'" + Validate.isNullToDefault(sjmap.get("DQ"), "") + "',value:"+Validate.isNullToDefault(sjmap.get("CNT"), "") + "},");
							}
							json.deleteCharAt(json.length() - 1);
							sjmap = null;
						}
					json.append("]");
				json.append("}]");
			json.append("}");
			list = null;
		}
		
		json.append("}");
		return json.toString();
	}
}
