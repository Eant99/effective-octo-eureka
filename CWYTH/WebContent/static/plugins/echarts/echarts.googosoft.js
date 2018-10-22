/**
 * 绘图工具
 * zjy
 */
var theme = "default";
//var theme = "dark";
//var theme = "infographic";
//var theme = "macarons";
//var theme = "roma";
//var theme = "shine";
//var theme = "vintage";
//适合于灵活配置的图表，所有配置项都需要从后台传过来
var setTb = function(dom,opjson,zt){
	var MyChart = echarts.init(dom,zt||theme);
	MyChart.setOption(opjson);
};

//这种只适合于简单折线图表
//var opjson = {titletext:'试试',magicType:true,imgname:'测试图片',legendshow:false,legendorient:'vertical',xdata:['第一天','第二天','第三天','第四天','第五天','第六天'],data:[{name:'测试1',type:'line',areaStyle:{normal:{}},data:[20,12,26,30,38,40]},{data:[25,32,46,35,33,20]}]};
//titletext:可以省略，图表显示的标题，String类型
//magicType:可以省略，是否需要折线和柱状图切换，默认是false，boolean类型，还原按钮是否显示也是用的这个值
//imgname:可以省略，下载之后的图片名称，String类型，这个如果没有的话，保存的图片的名称默认是'折线图'
//legend：可以省略，图例组件配置项，对象类型，注意，设置了图例组件的，以legend开头的设置都不起作用
//legendshow：可以省略，图例是否显示，boolean类型，省略的话，如果有多个系列，则默认显示，如果只有一个系列，则默认不显示
//legendorient:可以省略，图例的排列方向，String类型，只有vertical和horizontal两种，其中horizontal是横向，vertical是纵向，默认是横向
//visualMap：可以省略，视觉映射组件，对象类型，visualMap:[{type:'piecewise',splitNumber:3,color: ['#d94e5d','#eac736','#50a3ba'],min: 0,max:300}]
//			type：可以省略，视觉映射的类型，String类型，默认是分段型
//			calculable：如果需要拖动手柄，则不能省略，拖动手动是否显示，boolean类型，默认不显示
//			splitNumber：可以省略，自动分段的段数，数值类型，默认是3段
//			color：可以省略，符合分段的信息显示的颜色，数组类型，不设置的话按照主题的颜色显示，数组内可以设置任意个颜色，只是第一个是最大的颜色，最后一个是最小的颜色
//			min：可以省略，最小值，数值类型，默认是0
//			max：可以省略，最大值，数值类型，默认是999999
//grid：可以省略，布局（类似于画图中的画布），对象类型，例：grid:{bottom:10}就是距离下边缘10px
//xAxis：可以省略，横坐标配置项，数组类型，注意如果配置了xAxis，则xdata是不起作用的，请将坐标轴的数据拼接到xAxis里
//xname：可以省略，横坐标显示的名称，String类型，注意如果有xAxis这个，则xname不起作用
//xdata：可以省略，横坐标显示的数据，数组类型，注意如果有xAxis这个，则xdata不起作用，数组的个数要跟数据源中数据的个数一致，否则虽然也能显示，但是部分数据的X轴没有值
//如果没有xAxis，也没有xdata，则显示成非类目轴
//yAxis：可以省略，纵坐标的配置项，数组类型，同xAxis
//yname：同xname
//ydata：可以省略，纵坐标的显示的数据，数组类型，同xdata
//data:不能省略，数据源，数组类型，数组中每一项都是一个对象，其中name是系列的名称,可以省略(设置了默认值，默认值是系列+序号)，String类型，type是图形的类型，使用这个方法的一般都是line类型，写成其他的也行，可以省略（有默认值，默认值是line），areaStyle:{normal:{}}加上这句就是这条线下边都用线的颜色填充，data就是每一个系列的数据源，是不能省略的，数组类型
var setLineTb = function(dom,opjson,zt){
	var LineChart = echarts.init(dom,zt||theme);
	var option = {
		title:{
			text:opjson.titletext||'',
			left:'center',
			top:'top'
		},
		tooltip:{
			trigger:'axis'
		},
		legend: {
			show:opjson.legendshow||true,
			orient:opjson.legendorient||'horizontal',
			data: [],
			left:'left'
		},
		toolbox:{
			show:true,
			feature:{
				magicType:{
					show:opjson.magicType || false,
					title : {
						line : '动态类型切换-折线',
						bar : '动态类型切换-柱状'
					},
					type : ['line','bar']
				},
				restore:{
					show:opjson.magicType || false,//具有动态类型切换的才会有还原按钮
				},
				saveAsImage:{
					show : true,
					title : '保存成图片',
					name:opjson.imgname||'折线图',
					type : 'png',
					pixelRatio:2,
					excludeComponents:['toolbox','dataZoom']
				}
			}
		},
		dataZoom:{
			show : true,
			type:'inside',
			filterMode:'filter'
		},
//		xAxis:[{
//			type:'category',
//			boundaryGap:false,
//			data:opjson.xdata
//		}],
//		yAxis : [{
//			type:'value',
//			boundaryGap:false,
//			scale:true
//		}],
		series: []
	};

	//这个一定要在opjson.data循环之前
	if(opjson.legend){
		option.legend = opjson.legend;
	}
	
	for(var i = 0; i < opjson.data.length; i++){
		if(!opjson.data[i].name){
			opjson.data[i].name = '系列' + (i + 1);
		}
		option.legend.data.push(opjson.data[i].name);
		if(!opjson.data[i].type){
			opjson.data[i].type = 'line';
		}
		option.series.push(opjson.data[i]);
	}

	if(opjson.tooltip){
		option.tooltip = opjson.tooltip;
	}
	else if(opjson.data.length == 1){
		option.tooltip.formatter = '{b} : {c}';//这句如果在上边直接写，会显示undefied
	}

	if(opjson.toolbox){
		option.toolbox = opjson.toolbox;
	}
	
	if(opjson.data.length == 1){
		if(!opjson.legend){
			option.legend = {show:false};
		}
	}
	
	if(opjson.visualMap){
		if(!opjson.visualMap[0].type){
			opjson.visualMap[0].type = 'piecewise';
		}
		if(!opjson.visualMap[0].splitNumber){
			opjson.visualMap[0].splitNumber = 3;
		}
		if(!opjson.visualMap[0].min){
			opjson.visualMap[0].min = 0;
		}
		if(!opjson.visualMap[0].max){
			opjson.visualMap[0].max = 999999;
		}
		
		option.visualMap = opjson.visualMap;
	}
	
	if(opjson.grid){
		option.grid = opjson.grid;
	}
	
	if(opjson.xAxis){
		if(!opjson.xAxis.type){
			opjson.xAxis.type = 'category';
		}
		if(!opjson.xAxis.boundaryGap){
			opjson.xAxis.boundaryGap = false;
		}
		option.xAxis = opjson.xAxis;
	}
	else if(opjson.xdata){
		option.xAxis = [{
			name:opjson.xname||'',
			type:'category',
			boundaryGap:false,
			data:opjson.xdata
		}];
	}
	else{
		option.xAxis = [{
			name:opjson.xname||'',
			type:'value',
			boundaryGap:false,
			scale:false
		}];
	}
	
	if(opjson.yAxis){
		if(!opjson.yAxis.type){
			opjson.yAxis.type = 'category';
		}
		if(!opjson.yAxis.boundaryGap){
			opjson.yAxis.boundaryGap = false;
		}
		option.yAxis = opjson.yAxis;
	}
	else if(opjson.ydata){
		option.yAxis = [{
			name:opjson.yname||'',
			type:'category',
			boundaryGap:false,
			data:opjson.ydata
		}];
	}
	else{
		option.yAxis = [{
			name:opjson.yname||'',
			type:'value',
			boundaryGap:false,
			scale:false
		}]
	}

	LineChart.setOption(option);
};

//这种只适合于简单柱状图表
///var opjson = {titletext:'试试',magicType:true,imgname:'测试图片',dataZoom:{show:false},legendshow:false,legendorient:'vertical',xdata:['第一天','第二天','第三天','第四天','第五天','第六天'],data:[{name:'测试1',type:'bar',stack:'第一队',data:[20,12,26,30,38,40]},{stack:'第一队',data:[25,32,46,35,33,20]}]};
//titletext:可以省略，图表显示的标题，String类型
//magicType:可以省略，是否需要折线和柱状图切换，默认是false，boolean类型，还原按钮是否显示也是用的这个值
//imgname:下载之后的图片名称，可以省略，String类型，这个如果没有的话，保存的图片的名称默认是'柱状图'
//legend：可以省略，图例组件配置项，对象类型，注意，设置了图例组件的，以legend开头的设置都不起作用
//legendshow：可以省略，图例是否显示，boolean类型，省略的话，如果有多个系列，则默认显示，如果只有一个系列，则默认不显示
//legendorient:可以省略，图例的排列方向，String类型，只有vertical和horizontal两种，其中horizontal是横向，vertical是纵向，默认是纵向
//dataZoom：可以省略，滚动条，对象类型
//visualMap：可以省略，视觉映射组件，对象类型，visualMap:[{type:'piecewise',splitNumber:3,color: ['#d94e5d','#eac736','#50a3ba'],min: 0,max:300}]
//			type：可以省略，视觉映射的类型，String类型，默认是分段型
//			calculable：如果需要拖动手柄，则不能省略，拖动手动是否显示，boolean类型，默认不显示
//			splitNumber：可以省略，自动分段的段数，数值类型，默认是3段
//			color：可以省略，符合分段的信息显示的颜色，数组类型，不设置的话按照主题的颜色显示，数组内可以设置任意个颜色，只是第一个是最大的颜色，最后一个是最小的颜色
//			min：可以省略，最小值，数值类型，默认是0
//			max：可以省略，最大值，数值类型，默认是999999
//grid：可以省略，布局（类似于画图中的画布），对象类型，例：grid:{bottom:10}就是距离下边缘10px
//xAxis：可以省略，横坐标配置项，数组类型，注意如果配置了xAxis，则xdata是不起作用的，请将坐标轴的数据拼接到xAxis里
//xname：可以省略，横坐标显示的名称，String类型，注意如果有xAxis这个，则xname不起作用
//xdata：可以省略，横坐标显示的数据，数组类型，注意如果有xAxis这个，则xdata不起作用，数组的个数要跟数据源中数据的个数一致，否则虽然也能显示，但是部分数据的X轴没有值
//如果没有xAxis，也没有xdata，则显示成非类目轴
//yAxis：可以省略，纵坐标的配置项，数组类型，同xAxis
//yname：同xname
//ydata：可以省略，纵坐标的显示的数据，数组类型，同xdata
//data:不能省略，数据源，数组类型，数组中每一项都是一个对象，其中name是系列的名称,可以省略(设置了默认值，默认值是系列+序号)，String类型，type是图形的类型，使用这个方法的一般都是bar类型，写成其他的也行，可以省略（有默认值，默认值是bar），stack是否把柱型图摞在一起显示，需要摞在一起的，就设置成相同的stack，不需要摞在一起的，不用写，可以写多个不同的stack，data就是每一个系列的数据源，是不能省略的，数组类型
var setBarTb = function(dom,opjson,zt){
	var BarChart = echarts.init(dom,zt||theme);
	var option = {
		title:{
			text:opjson.titletext||'',
			left:'center',
			top:'top'
		},
		tooltip:{
//			show:false,
			trigger:'axis',
			showContent:true,
			axisPointer:{
				type:'shadow'
			}
		},
		legend: {
			show:opjson.legendshow||true,
			orient:opjson.legendorient||'vertical',
			data: [],
			left:'left'
		},
		toolbox:{
			show:true,
			feature:{
				magicType:{
					show:opjson.magicType || false,
					title : {
						line : '动态类型切换-折线',
						bar : '动态类型切换-柱状'
					},
					type : ['line','bar']
				},
				restore:{
					show:opjson.magicType || false,//具有动态类型切换的才会有还原按钮
				},
				saveAsImage:{
					show : true,
					title : '保存成图片',
					name:opjson.imgname||'柱状图',
					type : 'png',
					pixelRatio:2,
					excludeComponents:['toolbox','dataZoom']
				}
			}
		},
		dataZoom:{
			show : true,
			type:'inside',
			filterMode:'filter'
		},
//		xAxis:[{
//			type:'category',
//			boundaryGap:true,
//			data:opjson.xdata
//		}],
//		yAxis : [{
//			type:'value',
//			boundaryGap:true,
//			scale:false
//		}],
		series: []
	};
	//这个一定要在opjson.data循环之前
	if(opjson.legend){
		option.legend = opjson.legend;
	}
	
	if(opjson.tooltip){
		option.tooltip = opjson.tooltip;
	}

	if(opjson.toolbox){
		option.toolbox = opjson.toolbox;
	}
	
	if(opjson.title){
		option.title = opjson.title;
	}
	
	if(opjson.dataZoom){
		option.dataZoom = opjson.dataZoom;
	}
	
	if(opjson.xAxis){
		for(var i = 0; i < opjson.xAxis.length; i++){
			if(!opjson.xAxis[i].type){
				if(opjson.xAxis[i].data){
					opjson.xAxis[i].type = 'category';
				}
				else{
					opjson.xAxis[i].type = 'value';
				}
			}

			if(opjson.xAxis[i].type == 'category' && !opjson.xAxis[i].boundaryGap){
				opjson.xAxis[i].boundaryGap = true;
			}
			
			if(opjson.xAxis[i].type == 'value' && !opjson.xAxis[i].scale){
				opjson.xAxis[i].scale = false;
			}
		}
		
		option.xAxis = opjson.xAxis;
	}
	else if(opjson.xdata){
		option.xAxis = [{
			type:'category',
			name:opjson.xname||'',
			boundaryGap:true,
			data:opjson.xdata
		}];
	}
	else{
		option.xAxis = [{
			name:opjson.xname||'',
			type:'value',
			min:0,
			scale:true
		}];
	}
	
	if(opjson.yAxis){
		for(var i = 0; i < opjson.yAxis.length; i++){
			if(!opjson.yAxis[i].type){
				if(opjson.yAxis[i].data){
					opjson.yAxis[i].type = 'category';
				}
				else{
					opjson.yAxis[i].type = 'value';
				}
			}
			
			if(opjson.yAxis[i].type == 'value' && !opjson.yAxis[i].scale){
				opjson.yAxis[i].scale = false;
			}

			if(opjson.yAxis[i].type == 'category' && !opjson.yAxis[i].boundaryGap){
				opjson.yAxis[i].boundaryGap = true;
			}
		}
		option.yAxis = opjson.yAxis;
	}
	else if(opjson.ydata){
		option.yAxis = [{
			name:opjson.yname||'',
			type:'category',
			boundaryGap:true,
			data:opjson.ydata
		}];
	}
	else{
		option.yAxis = [{
			name:opjson.yname||'',
			type:'value',
			min:0,
			scale:true
		}];
	}
	
	for(var i = 0; i < opjson.data.length; i++){
		if(opjson.data.length == 1){
			if(!opjson.data[i].name){
				opjson.data[i].name = "";
			}
		}
		else{
			if(!opjson.data[i].name){
				opjson.data[i].name = '系列' + (i + 1);
			}
		}
		option.legend.data.push(opjson.data[i].name);
		if(!opjson.data[i].type){
			opjson.data[i].type = 'bar';
		}
//		//柱状图最大宽度
		if(!opjson.data[i].barMaxWidth){
			opjson.data[i].barMaxWidth = '30';
		}
		option.series.push(opjson.data[i]);
	}
	
	if(opjson.data.length == 1){//当只有一个系列的时候，不显示坐标轴指示线，会通过该系列的label设置显示数据
		if(!opjson.legend){
			option.legend = {show:false};
		}
		if(!option.series[0].label){
			option.series[0].label = {
					normal: {
						show: true,
						formatter: function (param) {
							return param.data;
						}
					}
				};
		}
	}
	
	if(opjson.visualMap){
		if(!opjson.visualMap[0].type){
			opjson.visualMap[0].type = 'piecewise';
		}
		if(!opjson.visualMap[0].splitNumber){
			opjson.visualMap[0].splitNumber = 3;
		}
		if(!opjson.visualMap[0].min){
			opjson.visualMap[0].min = 0;
		}
		if(!opjson.visualMap[0].max){
			opjson.visualMap[0].max = 999999;
		}
		
		option.visualMap = opjson.visualMap;
	}
	
	if(opjson.grid){
		option.grid = opjson.grid;
	}
	BarChart.setOption(option);
};

//这种只适合于简单饼图
//var opjson = {titletext:'试试',imgname:'测试图片',legendorient:'vertical',xdata:['第一天','第二天','第三天','第四天','第五天','第六天'],data:[{name:'我是系列1',roseType:'radius',radius:['20%','70%'],center:['30%','50%'],data:[{value:12,name:'第二天'},{value:26,name:'第三天'},{value:30,name:'第四天'},{value:38,name:'第五天'},{value:40,name:'第六天'}]},{name:'我是系列2',roseType:'area',radius:['60%','90%'],center:['70%','50%'],data:[{value:12,name:'第一天'},{value:26,name:'第二天'},{value:19,name:'第三天'},{value:52,name:'第四天'},{value:34,name:'第五天'},{value:29,name:'第六天'}]},{name:'我是系列3',radius:['0','50%'],center:['70%','50%'],label:{normal: {position: 'inner'}},data:[{value:23,name:'第一天'},{value:8,name:'第二天'},{value:15,name:'第三天'},{value:29,name:'第四天'},{value:20,name:'第五天'},{value:17,name:'第六天'}]}]};
//title：可以省略，标题的配置项，数组类型，数组类型里边是对象，每一个对象是一个饼图的标题，注意设置了title的，所以以title开头的设置都不起作用
//titletext：可以省略，图表显示的标题，String类型
//imgname:下载之后的图片名称，可以省略，String类型，这个如果没有的话，保存的图片的名称默认是'饼图'
//legend：可以省略，图例组件配置项，对象类型，注意，设置了图例组件的，以legend开头的设置都不起作用
//legendorient:可以省略，图例的排列方向，String类型，只有vertical和horizontal两种，其中horizontal是横向，vertical是纵向，默认是横向
//xdata:不能省略，类目显示的数据，数组类型
//color:可以省略，饼图颜色，数组类型，如果一个饼图中有5块，但是这个数组只有3个颜色，那么会循环使用这三种颜色，不设置的话，使用主题中的颜色
//data:不能省略，数据源，数组类型，数组内是对象
//		name:可以省略，系列名，String类型，默认显示系列+序号
//	 	roseType:可以省略，String类型，不写是普通饼图，写的话只有两种，一种是radius，另一种是area，会被设置成南丁格尔饼图
//		radius:可以省略，饼图的半径，数组类型，默认显示内半径是0，外半径是90%（['0','90%']），内半径是0的就是饼图，不是0 的就是环形图
//		center:可以省略，图形位于容器中的位置，数组类型，默认显示在中间（['50%','50%']），其中第一个值是相对于左右的位置，第二个值是相对于上下的位置
//		label:{normal: {position: 'inner'}}：可以省略，注释显示的设置，像示例上系列3这种设置是指将注释显示在饼图里边
//		data:不能省略，数据源，数组类型，数组内是对象
//			 name：不能省略，必须跟xdata中的数据对应，String类型
//			 value：不能省略，数据，数值类型
//上边这个例子是三个饼图，第一个系列中是个南丁格尔环形图，第二个系列是另一种南丁格尔环形图，第三个系列是普通饼图，其中第二个跟第三个是嵌套在一起的，只要把第二个和第三个的data中的center设置成一样的即可
var setPieTb = function(dom,opjson,zt){
	var PieChart = echarts.init(dom,zt||theme);
	var option = {
			title : {
		        text: opjson.titletext||'',
		        left:opjson.left||'center',
				top:opjson.top||'top'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
			legend:{
				show:true,
				orient:opjson.legendorient||'horizontal',
				left:'left',
				//加上这一段可以把图例带上数值，但是目前没有想到怎么做成通用的，注意，这种目前只适合于一个饼图的情况，写在service里会提示找不到option
/*				formatter:function(name){
		        	var oa = option.series[0].data;
		        	var num = 700;//总额
		        	for(var i = 0; i < oa.length; i++){
	                    if(name == oa[i].name){
	                    	console.log(name + '     ' + oa[i].value + '     ' + (oa[i].value/num * 100).toFixed(2) + '%');
	                    	return name + '     ' + oa[i].value + '     ' + (oa[i].value/num * 100).toFixed(2) + '%';
	                    }
		        	}
		        },*/
				data:opjson.xdata
			},
			toolbox:{
				show:true,
				//orient:'horizontal',
				right:1,
				top:1,
				feature:{
					saveAsImage:{
						show : true,
						title : '保存成图片',
						name:opjson.imgname||'饼图',
						type : 'png',
						pixelRatio:2,
						excludeComponents:['toolbox']
					}
				}
			},
			series:[]
	}

	if(opjson.color){
		option.color = opjson.color;
	}
	
	if(opjson.roseType){
		option.series[0].roseType = opjson.roseType;
	}

	if(opjson.title){
		option.title = opjson.title;
	}

	if(opjson.toolbox){
		option.toolbox = opjson.toolbox;
	}

	if(opjson.tooltip){
		option.tooltip = opjson.tooltip;
	}

	//这个一定要在opjson.data循环之前
	if(opjson.legend){
		option.legend = opjson.legend;
	}
	
	for(var i = 0; i < opjson.data.length; i++){
		if(opjson.data.length == 1){
			if(!opjson.data[i].name){
				opjson.data[i].name = "";
			}
		}
		else{
			if(!opjson.data[i].name){
				opjson.data[i].name = "系列" + (i + 1);
			}
		}
		if(!opjson.data[i].type){
			opjson.data[i].type = "pie";
		}
		if(!opjson.data[i].radius){
			opjson.data[i].radius = ['0','75%'];//第一个值代表中心圆的大小，一般为0，第二个为外层饼图的大小，可适当调节
		}
		if(!opjson.data[i].center){
			opjson.data[i].center = ['50%','50%'];
		}

		if(!opjson.data[i].label){
			opjson.data[i].label = {
				normal: {
					show: true,
					//formatter: '{b}:{c}({d}%)'//饼图展示名称+值+百分比；文化学院：18(5.2%)
					formatter: '{c}({d}%)'//饼图只展示值+百分比；18(5.2%)
				}
			};
		}
		
		option.series.push(opjson.data[i]);
	}
	
	if(opjson.legendformatternum){
		option.legend.formatter = function(name){
			var oa = option.series[0].data;
        	var num = opjson.legendformatternum;
        	for(var i = 0; i < oa.length; i++){
                if(name == oa[i].name){
                	return name + ':' + oa[i].value + opjson.legendformatterdw + '(' + (oa[i].value/num * 100).toFixed(2) + '%)';
                }
        	}
		}
	}

	PieChart.setOption(option);
}

//这种只适合于简单的散点图
//设置x轴的例子：var opjson = {titletext:'试试',imgname:'测试图片',legendorient:'vertical',xdata:['第一天','第二天','第三天','第四天','第五天','第六天'],data:[{name:'我是第一个系列',data:[['第一天',20],['第二天',12],['第三天',26],['第四天',30],['第五天',38],['第六天',40]]},{name:'我是第2个系列',symbolSize:30,data:[['第一天',30],['第二天',42],['第三天',25],['第四天',10],['第五天',28],['第六天',30]]}]};
//不设置X轴的例子：var opjson = {titletext:'试试',imgname:'测试图片',legendorient:'vertical',data:[{name:'我是第一个系列',data:[[12,20],[26,12],[5,26],[18,30],[34,38],[9,40]]},{name:'我是第2个系列',symbolSize:30,data:[[27,30],[20,42],[32,25],[19,10],[7,28],[25,30]]}]};
//titletext:可以省略，图表显示的标题，String类型
//imgname:下载之后的图片名称，可以省略，String类型，这个如果没有的话，保存的图片的名称默认是'散点图'
//legendorient:可以省略，图例的排列方向，String类型，只有vertical和horizontal两种，其中horizontal是横向，vertical是纵向，默认是纵向
//polar：可以省略，省略的话就是普通散点图，带上这个key就是极坐标系，可以直接给个{}，对象类型
//xdata:可以省略（有polar这个时，这个xdata不起任何作用，没有polar这个时最好省略，数据源中data代表x轴和y轴的一般是数值类型），x轴显示的数据，数组类型，注意一般情况下散点图的x轴和y轴都是非类目的，也就是说这个xdata一般都是要省略的，不省略的话，就失去了散点图的意义
//ydata：可以省略（有polar这个时，这个ydata不起任何作用，没有polar这个时，一般使用非类目轴），y轴显示的数据，数组类型
//radiusAxis：只有在具有polar时，才起作用，表示当前这个点在当前这跟轴上的哪一段，数据源data[i].data数组中的第一个参数
//angleAxis：只有在具有polar时，才起作用，表示当前这个图片有几根轴线，数据源data[i].data数组中的第二个参数
//data:不能省略，数据源，数组类型，数组内是对象
//		data[i].name:系列的名称,可以省略(设置了默认值，默认值是系列+序号)，String类型
//		data[i].type：图形的类型，可以省略，使用这个方法的一般都是scatter类型
//		data[i].symbolSize：设置圆点的大小，可以省略，默认值是用y轴的大小开方再乘以5，也可以直接设置成数值
//		data[i].data：每一个系列的数据源，不能省略，数组类型，这个数组里边也是数组，注意：内部数组至少需要两个值（[x轴数值,y轴数值...] or [radiusAxis轴数值，angleAxis轴数值]），第一个值是x/radiusAxis轴对应的值，第二个值是y/angleAxis轴对应的值，如果前边设置了xdata，那么这里的第一个值必须能跟xdata里的值对应起来才能显示，如果没有设置xdata，那么这两个值必须是数值类型。内部数组里还能放更多的值，后边没要求，看实际情况添加
var setScatterTb = function(dom,opjson,zt){
	var ScatterChart = echarts.init(dom,zt||theme);
	var option = {
			title:{
				text:opjson.titletext||'',
				left:'center',
				top:'top'
			},
			legend: {
				orient:opjson.legendorient||'vertical',
				data: [],
				left:'left'
			},
			tooltip: {
				show:false
			},
			toolbox:{
				show:true,
				right:1,
				top:1,
				feature:{
					saveAsImage:{
						show : true,
						title : '保存成图片',
						name:opjson.imgname||'散点图',
						type : 'png',
						pixelRatio:2,
						excludeComponents:['toolbox','dataZoom']
					}
				}
			},
			dataZoom: [{
				type: 'inside'
			},{
				type: 'inside'
			}],
			series: []
	}
	
	if(opjson.polar){
		option.polar = opjson.polar;
		if(opjson.radiusAxis){//影响没跟轴线上的分段
			option.radiusAxis = {
				type: 'category',
				data:opjson.radiusAxis,
		        axisLabel: {//注释的旋转角度
		            rotate: 0
		        },
				splitLine: {
					show: false
				},
		        axisLabel:{
		        	show:false
		        },
		        axisTick:{
		        	show:false
		        }
			}
		}
		else{
			option.radiusAxis = {
				type: 'value',
				min: 'dataMin',
				max: 'dataMax',
				splitLine: {//不显示分割线
					show: false
				},
		        axisLabel:{//不显示坐标轴上的字
		        	show:false
		        },
		        axisTick:{//不显示刻度
		        	show:false
		        }
			}
		}

		if(opjson.angleAxis){//影响有几根轴线
			option.angleAxis = {
				type: 'category',
				data:opjson.angleAxis,
		        boundaryGap: false,//类目轴中，默认true，圆点会在轴线之间，不在轴线上，false就显示在轴线上
		        splitLine: {//轴线，设置为false轴线就不显示了
		            show: true,
		            lineStyle: {
		                color: '#999',
		                type: 'dashed'
		            }
		        },
		        axisLine: {//如果开启，外边会有个圆环
		            show: false
		        }
			}
		}
		else{
			option.angleAxis = {
				type: 'value',
				min: 'dataMin',
				max: 'dataMax',
				splitLine: {
					show: true
				}
			}
		}
	}
	else{
		if(opjson.xAxis){
			option.xAxis = opjson.xAxis;
		}
		else{
			if(opjson.xdata){
				option.xAxis = {
					type: 'category',
					data:opjson.xdata,
					axisLabel: {
						interval:'auto'
					},
					splitLine: {
						show: true
					}
				}
			}
			else{
				option.xAxis = {
					type: 'value',
					min: 'dataMin',
					max: 'dataMax',
					splitLine: {
						show: true
					}
				}
			}
		}
		
		if(opjson.yAxis){
			option.yAxis = opjson.yAxis;
		}
		else{
			if(opjson.ydata){
				option.yAxis = {
					type: 'category',
					data:opjson.ydata,
					splitLine: {
						show: true
					}
				}
			}
			else{
				option.yAxis = {
					type: 'value',
					min: 'dataMin',
					max: 'dataMax',
					splitLine: {
						show: true
					}
				}
			}
		}
	};

	if(opjson.toolbox){
		option.toolbox = opjson.toolbox;
	}

	if(opjson.tooltip){
		option.tooltip = opjson.tooltip;
	}
	
	for(var i = 0; i < opjson.data.length; i++){
		if(!opjson.data[i].name){
			opjson.data[i].name = '系列' + (i + 1);
		}
		option.legend.data.push(opjson.data[i].name);
		if(!opjson.data[i].type){
			opjson.data[i].type = 'scatter';
		}
		if(!opjson.data[i].label){
			opjson.data[i].label = {
				emphasis: {
					show: true,
					position: 'top'
				}
			};
		}
		if(!opjson.data[i].symbolSize){
			opjson.data[i].symbolSize = 20;
//			if(opjson.polar){
//				opjson.data[i].symbolSize = function(data){
//					return Math.sqrt(data[0]) * 5;
//				};
//			}
//			else{
////				opjson.data[i].symbolSize = 20;
//				opjson.data[i].symbolSize = function(data){
//					return Math.sqrt(data[1]) * 5;
//				};
//			}
		}
		
		if(opjson.polar){///这里很重要，每一个数据源里没有coordinateSystem，就出不来
			if(!opjson.data[i].coordinateSystem){
				opjson.data[i].coordinateSystem = 'polar';
			}
		}
		option.series.push(opjson.data[i]);
	}
	
	if(opjson.data.length < 2){
		if(!opjson.legend){
			option.legend = {show:false};
		}
	}
	
	ScatterChart.setOption(option);
};

//这种只适合于一个地图的图表
//有nameMap的示例：var opjson = {titletext:'试试',imgname:'测试图片',legendorient:'vertical',nameMap:{'山东':'37','内蒙古':'15','北京':'11','新疆':'65','台湾':'71','海南':'46'},data:[{map:'china',data:[{value:20,name:'37'},{value:12,name:'46'},{value:26,name:'11'},{value:30,name:'15'},{value:38,name:'71'},{value:40,name:'65'}]},{data:[{value:50,name:'37'},{value:32,name:'46'},{value:16,name:'11'},{value:20,name:'15'},{value:28,name:'71'},{value:33,name:'65'}]}]};
//没有nameMap的示例：var opjson = {titletext:'试试',imgname:'测试图片',map:'china',visualMap:[{show:true,max:100,calculable:true,text:['学生人数最多','学生人数最少']}],data:[{name:'女生人数',data:[{value:20,name:'山东'},{value:12,name:'海南'},{value:26,name:'北京'},{value:30,name:'内蒙古'},{value:38,name:'台湾'},{value:40,name:'新疆'}]},{name:'学生人数',data:[{value:60,name:'山东'},{value:22,name:'海南'},{value:36,name:'北京'},{value:10,name:'内蒙古'},{value:28,name:'台湾'},{value:34,name:'新疆'}]}]};
//backgroundColor:可以省略，背景色，默认是透明，String类型
//titletext:可以省略，图表显示的标题，String类型
//imgname:下载之后的图片名称，可以省略，String类型，这个如果没有的话，保存的图片的名称默认是'地图'
//legendorient:可以省略，图例的排列方向，String类型，只有vertical和horizontal两种，其中horizontal是横向，vertical是纵向，默认是横向
//legend：可以省略，图例组件，对象类型，注意legend跟legendleft、legendtop、legendorient这种以legend开头的设置冲突，legend的优先级高，也就是说只要设置了legend，那些以legend开头的设置都不起作用
//////dataRangetext：可以省略，数据显示说明中最大和最小显示的信息，数组类型，注意在echarts3中不建议使用，可以通过visualMap设置
//toolbox：可以省略，工具组件，对象类型，注意toolbox下所有的设置，都不如toolbox的优先级高
//map:可以省略，显示地图的类型，String类型，默认显示中国地图，注意：这里设置的不是js的名称，而是地图js中最后一句echarts.registerMap方法的第一个参数
//nameMap：可以省略，自定义地区的名称映射（类似于地图上显示信息的枚举），对象类型，每项中用冒号分隔，冒号前是原地图上显示的信息，冒号后是希望地图上显示的信息，比如中国地图，原本显示的是“山东”,“内蒙古”这样的字，设置nameMap:{'山东':'37','内蒙古':'15'}，那么在山东和内蒙古的地方显示37和15，在最里边的data中name（比如{value:20,name:'山东'}这一部分里的山东）也要使用转换之后的信息，注意：只要一个系列用到了nameMap，其他的系列data中的name（比如{value:20,name:'山东'}这一部分里的山东）也都需要使用转换之后的信息
//visualMap：可以省略，视觉映射组件，分为continuous（连续型），piecewise（分段型）两种，地图上一般使用连续型，默认就是连续型，数组类型，一般数组里只有一个对象，默认显示视觉映射
//			show：可以省略，是否显示，默认显示，boolean类型
//			min：可以省略，最小值，默认是0，数值类型
//			max：如需显示视觉映射不能省略，最大值，数值类型，可以参考学生人数分析中的生源省区，省略的话，默认显示999999（这个方法里设置的max去掉的话，最大值默认是200）
//			calculable：可以省略，是否显示拖动手柄，boolean类型，默认不显示
//			orient：可以省略，同图例中的orient，String类型，默认是纵向
//			inverse：可以省略，是否反转 visualMap 组件，boolean类型，默认false，默认状态下：当visualMap.orient是纵向时，数据上大下小，当visualMap.orient是横向时，数据右大左小
//			text：可以省略，最大值和最小值显示的文字，数组类型，默认显示['最多','最少']
//data：不能省略，数据源，数组类型，数组内是对象
//		name：可以省略，系列名，String类型
//		data:不能省略，数据源，数组类型，数组内是对象，注意这个数组内的对象里的name，必须是地图上有的内容，例如中国地图，只能是山东、上海等，不能是山东省，如果设置了nameMap，则这里的name要跟nameMap中每一项冒号后边的内容保持一致
var setMapTb = function(dom,opjson,zt){
	var MapChart = echarts.init(dom,zt||theme);
	var option = {
			backgroundColor: opjson.bgcolor||'transparent',
			title:{
				text:opjson.titletext||'',
				left:'center',
				top:'top'
			},
			tooltip:{
				trigger:'item',
		        formatter: function(params){
		        	return params.name + ":" + params.data.value;
		        }
			},
			toolbox: {
				show: true,
				orient : 'vertical',
				feature : {
					saveAsImage:{
						show : true,
						title : '保存成图片',
						name:opjson.imgname||'地图',
						type : 'png',
						pixelRatio:2,
						excludeComponents:['toolbox','dataZoom']
					}
				}
			},
			legend:{
				left:'left',
				orient:opjson.legendorient||'horizontal',
				data:[]
			},
//			visualMap: [{//数据显示说明，左下角所显示的内容
////				min: 0,//数据最小值
////				max: 20000,//数据最大值
//				left: 'left',
//				bottom: 'bottom',
//				text:opjson.visualMaptext||['最多','最少'],
//				color: ['#ff6700', '#e50707', '#000000'],//设置地图的颜色				
//				calculable:true//最大值和最小值是否显示
//			}],
			series:[]//这里的某一项如果不想用visualMap控制，可以在这一项里加上visualMap: false，例如：[{value:20,name:'山东'},{value:12,name:'海南',visualMap:false}]，海南就不会受visualMap控制
	}
	
	var mapcnt = opjson.data.length;
	for(var i = 0; i < mapcnt; i++){
		if(!opjson.data[i].name){
			opjson.data[i].name = '系列' + (i + 1);
		}
		option.legend.data.push(opjson.data[i].name);
		if(!opjson.data[i].type){
			opjson.data[i].type = 'map';
		}
		if(opjson.map){
			opjson.data[i].map = opjson.map;
		}
		else{
			opjson.data[i].map = 'china';
		}
		
		if(!opjson.data[i].bottom){
			opjson.data[i].bottom = 25;
		}
		if(!opjson.data[i].top){
			opjson.data[i].top = 25;
		}
		if(!opjson.data[i].left){
			opjson.data[i].left = 10;
		}
		if(!opjson.data[i].right){
			opjson.data[i].right = 10;
		}
		
		if(!opjson.data[i].itemStyle){
			opjson.data[i].itemStyle = {
				normal:{
//					borderWidth:2,//地图中各省边框的宽度
//					borderColor:'lightgreen',//地图中各省边框的颜色
					label:{
						show:true//各省市名称默认是否显示，true默认显示，false默认不显示
					}
				}
			};
		}
		if(opjson.nameMap){
			opjson.data[i].nameMap = opjson.nameMap;
		}
		option.series.push(opjson.data[i]);
	}
	
	if(mapcnt > 1){
		option.tooltip.formatter = function(params){
			var res = params.name + "<br/>";
			for(var i = 0; i < option.series.length; i++){
				var dataArr = option.series[i].data;
		        for(var j = 0;j < option.series[i].data.length; j++){
		          if(dataArr[j].name == params.name){
		             res += option.series[i].name + ':' + dataArr[j].value + '<br/>';
		          }
		        }
			}
			return res;
		}
	}
	
	if(opjson.visualMap){
		if(opjson.visualMap.length == 0){
			opjson.visualMap = [{}];
		}
	}
	else{
		opjson.visualMap = [{}];
		
	}
	
	for(var i = 0; i < opjson.visualMap.length; i++){
		if(!opjson.visualMap[i].min){
			opjson.visualMap[i].min = 0;
		}
		if(!opjson.visualMap[i].max){
			opjson.visualMap[i].max = 999999;
		}
		if(!opjson.visualMap[i].left && !opjson.visualMap[i].right){
			opjson.visualMap[i].left = 'left';
		}
		if(!opjson.visualMap[i].bottom && !opjson.visualMap[i].top){
			opjson.visualMap[i].bottom = 'bottom';
		}
		if(!opjson.visualMap[i].text){
			opjson.visualMap[i].text = ['最多','最少'];
		}
	}
	
	option.visualMap = opjson.visualMap;
	
	if(opjson.legend){
		option.legend = opjson.legend;
	}
	if(opjson.toolbox){
		option.toolbox = opjson.toolbox;
	}
	
	MapChart.setOption(option);
}

//这种只适合于简单漏斗图（最好不要超过5个系列，超过5个透明度可能会有问题）
//var opjson = {titletext:'试试',titleleft:'left',titletop:'top',imgname:'测试图片',legendtop:'top',legendleft:'left',xdata:['第一天','第二天','第三天'],data:[{name:'测试1',type:'funnel',sort:'ascending',data:[{value:8,name:'第一天'},{value:12,name:'第二天'},{value:26,name:'第三天'}]},{maxSize:'60%',data:[{value:2,name:'第一天'},{value:10,name:'第二天'},{value:18,name:'第三天'}]}]};
//titletext:可以省略，图表显示的标题，String类型
//titleleft：可以省略，标题横向位置，String类型，默认是left
//titletop:可以省略，标题纵向位置，String类型，默认是top
//imgname:可以省略，下载之后的图片名称，String类型，这个如果没有的话，保存的图片的名称默认是'漏斗图'
//legendorient:可以省略，图例的排列方向，String类型，只有vertical和horizontal两种，其中horizontal是横向，vertical是纵向，默认是横向
//legendtop：可以省略，图例组件纵向的位置，String类型，默认是top
//legendleft：可以省略，图例组件横向位置，String类型，默认是left
//legend：可以省略，图例组件，对象类型，注意legend跟legendleft、legendtop、legendorient这种以legend开头的设置冲突，legend的优先级高，也就是说只要设置了legend，那些以legend开头的设置都不起作用
//xdata:不能省略，图例显示的数据，数组类型
//data:不能省略，数据源，数组类型，数组中每一项都是一个对象
//		name：可以省略(设置了默认值，默认值是系列+序号)，系列的名称，String类型
//		type：可以省略（有默认值，默认值是funnel），图形的类型，String类型
//		sort：可以省略，漏斗朝向，不写是上边大下边小，写成ascending是下边大上边小
//		data：不能省略，每一个系列的数据源，数组类型
var setFunnelTb = function(dom,opjson,zt){
	var FunnelChart = echarts.init(dom,zt||theme);
	var option = {
		    title: {
		        text: opjson.titletext||'',
				left: opjson.titleleft||'left',
				top: opjson.titletop|'top'
		    },
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    toolbox: {
		        feature: {
		        	saveAsImage:{
						show : true,
						title : '保存成图片',
						name:opjson.imgname||'漏斗图',
						type : 'png',
						pixelRatio:2,
						excludeComponents:['toolbox','dataZoom']
					}
		        }
		    },
		    legend: {
				top:opjson.legendtop||'top',
				left:opjson.legendleft||'center',
				orient:opjson.legendorient||'horizontal',
				data:opjson.xdata
		    },
		    series: []
		};
	
	for(var i = 0; i < opjson.data.length; i++){
		if(!opjson.data[i].name){
			opjson.data[i].name = '系列' + (i + 1);
		}
		if(!opjson.data[i].type){
			opjson.data[i].type = 'funnel';
		}
		
		if(!opjson.data[i].maxSize){//数据最大值映射的宽度。
			opjson.data[i].maxSize = '80%';
		}
		
		if(!opjson.data[i].bottom){
			opjson.data[i].bottom = 0;
		}
		
		if(!opjson.data[i].label){
			if(i == 0){
				opjson.data[i].label = {
	                normal: {//鼠标没指上去时显示的样子
	                	position:'outside',
	                    formatter: '{b}'
	                },
	                emphasis:{
	                	show:false
	                }
	            };
			}
			else{
				opjson.data[i].label = {
					normal:{
	                    show:false
					},
	                emphasis:{
	                	show:false
	                }
	            };
			}
		}
		
//		if(!opjson.data[i].labelLine){
//			opjson.data[i].labelLine = {
//              normal: {
//                  show: false
//              }
//          };
//		}
		
		if(!opjson.data[i].itemStyle){//设置透明度
			var tmd = (opjson.data.length - i) * 0.2;
			if(tmd >= 1){
				tmd = 1;
			}
			else{
				tmd = 1 - tmd;
			}
			//这边这三句只能这么写，直接写最后一句的话，会报错
			opjson.data[i].itemStyle = {};
			opjson.data[i].itemStyle.normal = {};
			opjson.data[i].itemStyle.normal.opacity = tmd;
		}
		
		option.series.push(opjson.data[i]);
	}
	
	if(opjson.legend){
		option.legend = opjson.legend;
	}

	if(opjson.toolbox){
		option.toolbox = opjson.toolbox;
	}
	FunnelChart.setOption(option);
};

//这种只适合于简单雷达图
//var opjson = {
//				    titletext:'试试',
//					imgname:'测试图片',
//					legend:{
//						show:true,
//						orient:'vertical',
//						data: ['专业一','专业一','专业三','专业四','专业五','专业六'],
//						left:'left'
//					},
//					visualMap:[
//						{name:'学院一',type:'continuous',calculable:true,max:60,text:['学院一最大值','学院一最小值'],right:160},
//						{name:'学院二',type:'continuous',calculable:true,max:110,text:['学院二最大值','学院二最小值'],right:80},
//						{name:'学院三',type:'continuous',calculable:true,max:90,text:['学院三最大值','学院三最小值'],right:0}
//					],
//					radar:{
//						indicator:[
//							{text:'男生人数'},
//							{text:'女生人数'},
//							{text:'学生总数'}
//						],
//						center: ['50%', '50%'],
//						radius: '90%',
//						startAngle: 90,
//						splitNumber:10,
//						shape: 'circle',
//						name: {
//							formatter:'【{value}】',
//							textStyle: {
//								color:'#72ACD1'
//							}
//						}
//					},
//					data:[{
//						name:'学院一',
//						type: "radar",
//						data:[
//							{value:[20,12,32],name:'专业一'},
//							{value:[30,22,52],name:'专业二'},
//							{value:[40,36,76],name:'专业三'},
//							{value:[10,16,26],name:'专业四'},
//							{value:[16,22,38],name:'专业五'},
//							{value:[20,22,42],name:'专业六'}
//						]
//					},{
//						name:'学院二',
//						type: "radar",
//						data:[{value:[55,48,103],name:'专业四'}]
//					},{
//						name:'学院三',
//						type: "radar",
//						data:[
//							{value:[35,48,83],name:'专业四'},
//							{value:[35,28,63],name:'专业五'}
//						]
//					}]
//				}
//示例是获取的每个学院下每个专业中的男生数、女生数、学生总数
//titletext:可以省略，图表显示的标题，String类型
//imgname:可以省略，下载之后的图片名称，String类型，这个如果没有的话，保存的图片的名称默认是'雷达图'
//tooltip：可以省略，提示框组件，对象类型，默认显示，如不想显示，可以设置成tooltip:{show:false}
//legend：可以省略，图例组件，对象类型
//		  show：可以省略，是否显示，boolean类型
//		  orient：可以省略，图例的排列方向，String类型，只有vertical和horizontal两种，其中horizontal是横向，vertical是纵向，默认是横向
//		  data：如果需要显示图例则不能省略，图例数据源，数组类型
//		  left：可以省略，距离左边缘的距离，String类型，默认居中，left居左
//visualMap：可以省略，视觉映射组件，数组类型，数组中是对象，visualMap:[{type:'continuous',calculable:true,min: 0,max:300}]
//			type：可以省略，视觉映射的类型，String类型，默认是连续型
//			calculable：如果需要拖动手柄，则不能省略，拖动手动是否显示，boolean类型，默认不显示
//			color：可以省略，符合分段的信息显示的颜色，数组类型，不设置的话按照主题的颜色显示，数组内可以设置任意个颜色，只是第一个是最大的颜色，最后一个是最小的颜色
//			min：可以省略，最小值，数值类型，默认是0
//			max：可以省略，最大值，数值类型，默认是999999
//radar:不能省略，坐标轴显示的数据，对象类型
//		indicator：不能省略，用来指定雷达图的多个系列（设置每个坐标轴显示信息的），数组类型，数组里边是对象，这里边有几个对象，则显示几条轴
//					text：不能省略，每个坐标轴显示信息，String类型
//					max：可以省略，最大值，数值类型，设置这个值之后，如果真实的数据中有比这个最大值大的，则会显示在图形之外
//		center：可以省略，图形的位置，数组类型，默认['50%', '50%']（[控制左右，控制上下]）
//		radius：可以省略，图形直径占容器的大小，String类型
//		startAngle：可以省略，开始节点旋转的角度，数值类型
//		splitNumber：可以省略，划分背景的区域数，数值类型
//		shape：可以省略，背景显示的形状，String类型，支持'circle'（圆形）和'polygon'（多边形），默认显示多边形
//		name：可以省略，坐标轴显示信息的配置项，对象类型
//			  formatter：可以省略，格式，String类型，例：'【{value}】',
//			  textStyle：可以省略，设置样式，对象类型
//data:不能省略，数据源设置，数组类型，数组中每一项都是一个对象
//		name：可以省略，用来区分不同的大系列，String类型
//		type：可以省略，图形的类型，String类型，默认是radar
//		data：不能省略，数据源，数组类型，数组里边是对象
//			  name：不能省略，小系列的名称，需要跟图例中的数据对应，String类型
//			  value：不能省略，数据源，数组类型，数组中数据的顺序，需要跟radar.indicator中的顺序保持一致
var setRadarTb = function(dom,opjson,zt){
	var RadarChart = echarts.init(dom,zt||theme);
	var option = {
		title:{
			text:opjson.titletext||'',
			left:'center',
			top:'top'
		},
		tooltip:{},
		toolbox:{
			show:true,
			right:1,
			top:1,
			feature:{
				saveAsImage:{
					show : true,
					title : '保存成图片',
					name:opjson.imgname||'雷达图',
					type : 'png',
					pixelRatio:2,
					excludeComponents:['toolbox']
				}
			}
		},
		series: []
	};
	
	for(var i = 0; i < opjson.data.length; i++){
		if(!opjson.data[i].type){
			opjson.data[i].type = 'radar';
		}
		option.series.push(opjson.data[i]);
	}
	
	if(opjson.visualMap){
		if(!opjson.visualMap[0].min){
			opjson.visualMap[0].min = 0;
		}
		if(!opjson.visualMap[0].max){
			opjson.visualMap[0].max = 999999;
		}
		
		option.visualMap = opjson.visualMap;
	}
	
	if(opjson.tooltip){
		option.tooltip = opjson.tooltip;
	}

	if(opjson.toolbox){
		option.toolbox = opjson.toolbox;
	}
	
	if(opjson.legend){
		if(!opjson.legend.orient){
			opjson.legend.orient = 'vertical';
		}
		if(!opjson.legend.left && !opjson.legend.right){
			opjson.legend.left = 'left';
		}
		option.legend = opjson.legend;
	}
	
	option.radar = opjson.radar;
	RadarChart.setOption(option);
};