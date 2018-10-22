//支出和收入的切换
$(document).on("click","[id^=click_]",function(){
	var id = $(this).attr("id");
	if(id=="click_zc"){
		$("#yssr").addClass("ycc");
		xs();
	}else{
		$("#yssr").removeClass("ycc");
		$("#yszc").css("display","none");
	}
	$(".bg").removeClass("bg");
	$(".sbg").removeClass("sbg");
	$(this).addClass("bg");
	$(this).find("span").addClass("sbg");
});
//控制办事大厅分页
$(document).on("click","[id^=switch_]",function(){
	var id = $(this).attr("id");
	var box = $(this).parents(".box");
	var clw = $(this).attr("class");
	if(clw.indexOf("hello")>0){
		return;
	}
	if("switch_left"==id){
		$("#left").removeClass("yc");
		$("#right").addClass("yc");
		box.css("padding-bottom", "6px").find("[id='left']").slideDown("fast");
	}else{
		$("#left").addClass("yc");
		$("#right").removeClass("yc");
		box.css("padding-bottom", "6px").find("[id='right']").slideDown("fast");
	}
	$(".toBtn").css("color","gray").removeClass("hello");
	$(this).css("color","#eae9e9").addClass("hello");
	$(this).parents(".box").find("#faaaa").removeClass("fa-angle-down").addClass("fa-angle-up");
});
//控制办事大厅的显示
$(".box .newbox-fold").click(function(){
	var $this = $(this);
	var box = $this.parents(".box");
	if(box.find("#left:visible").length > 0 && box.find("#right:hidden").length > 0) {
		box.css("padding-bottom", "6px").find("[id='left']").slideUp("fast");
		$this.find(".faw").removeClass("fa-angle-up").addClass("fa-angle-down");
	} else if(box.find("#left:hidden").length > 0 && box.find("#right:hidden").length > 0){
		var cla = box.find("#left:hidden").attr("class");
		if(cla.indexOf("yc")<0){
			box.css("padding-bottom", "6px").find("[id='left']").slideDown("fast");
			$this.find(".faw").removeClass("fa-angle-down").addClass("fa-angle-up");
		}else{
			box.css("padding-bottom", "6px").find("[id='right']").slideDown("fast");
			$this.find(".faw").removeClass("fa-angle-down").addClass("fa-angle-up");
		}
	}else if(box.find("#left:hidden").length > 0 && box.find("#right:visible").length > 0){
		box.css("padding-bottom", "6px").find("[id='right']").slideUp("fast");
		$this.find(".faw").removeClass("fa-angle-up").addClass("fa-angle-down");
	}
});




//从这里开始，显示所有的echart图标

$(function(){
	 var myChart = echarts.init(document.getElementById('xsrsfx')); 
	 option = {
			    tooltip: {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['专科生','本科生','研究生','博士生'],
			        /*selectedMode: 'single'*/
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    toolbox: {
			        feature: {
			            saveAsImage: {}
			        }
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: ['2010年','2011年','2012年','2013年','2014年','2015年','2016年','2017年']
			    },
			    yAxis: {
			    	name: '单位（人）',
			        type: 'value',
//			        axisLabel: {
//			            formatter: '{value}'			            	
//			        }
			    },
			    color:['#3EEDE7','#358AEB','#F9B654','red','#E8E8E8'],
			    series: [
			        {
			            name:'专科生',
			            type:'line',
			            data:[25203, 24865,24935, 21458, 23548, 22548, 21536, 22321]
			        },
			        {
			            name:'本科生',
			            type:'line',
			            data:[46875,46258, 47583, 48526, 47524, 49635, 45231, 48742]
			        },
			        {
			            name:'研究生',
			            type:'line',
			            data:[19861, 21026,21004, 19916, 19817, 21094, 21100, 21031]
			        },
			        {
			            name:'博士生',
			            type:'line',
			            data:[10256,11587,10236,16944,14544,13564,14526,15200]
			        },
			       
			    ]
			};
	 myChart.setOption(option);
});
$(function(){
	 var myChart = echarts.init(document.getElementById('jsxlfx')); 
	 option = {
	     tooltip : {
	         trigger: 'axis',
	         axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	             type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	         }
	     },
	     legend: {
	         data: ['女性教师', '男性教师']
	     },
	     grid: {
	         left: '3%',
	         right: '4%',
	         bottom: '3%',
	         containLabel: true
	     },
	     xAxis:  {
	         type: 'value'
	     },
	     yAxis: {
	         type: 'category',
	         data: ['其他','博士', '研究','本科','专科']
	     },
	     series: [
	         {
	             name:  '女性教师',
	             type: 'bar',
	             stack: '总量',
	             label: {
	                 normal: {
	                     show: true,
	                     position: 'insideRight'
	                 }
	             },
	             data: [205,356,664, 532, 306]
	         },
	         {
	             name: '男性教师',
	             type: 'bar',
	             stack: '总量',
	             label: {
	                 normal: {
	                     show: true,
	                     position: 'insideRight'
	                 }
	             },
	             data: [305,458, 836, 768, 407]
	         },
	    
	     ]
	 };
	 myChart.setOption(option);
	});

$(function(){
	var myChart = echarts.init(document.getElementById('xmzs'));
	var itemStyle0 = {
			normal:{color:'#FF8483'}
	};
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
//		        orient: 'vertical',
//		        x: 'left',
//		        data:['学生总数']
		    },
		    title:{
		        text:'学生总数',
		        x:'center',
		        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                fontSize: 14,
	            },
		      },
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            itemStyle:itemStyle0,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center',
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:72294, name:72294},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});

$(function(){
	var myChart = echarts.init(document.getElementById('zks'));
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
//		        orient: 'vertical',
//		        x: 'left',
//		        data:['专科生']
		    },
		    title:{
		        text:'专科生',
		        x:'center',
		        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                fontSize: 14,
	            },
		      },
		    color:['#3EEDE7','#E8E8E8'],
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:22321, name:22321},
		                {value:72294, name:''},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('bks'));
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    title:{
		        text:'本科生',
		        x:'center',
		        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                fontSize: 14,
	            },
		      },
		    legend: {
//		        orient: 'vertical',
//		        x: 'left',
//		        data:['本科生']
		    },
		    color:['#358AEB','#E8E8E8'],
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:48742, name:45152},
		                {value:72294, name:''},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('yjs'));
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
//		        orient: 'vertical',
//		        x: 'left',
//		        data:['研究生']
		    },
		    title:{
		        text:'研究生',
		        x:'center',
		        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                fontSize: 14,
	            },
		      },
		    color:['#F9B654','#E8E8E8'],
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:21031, name:21031},
		                {value:72294, name:''},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('bss'));
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
//		        orient: 'vertical',
//		        x: 'left',
//		        data:['博士生']
		    },
		    grid:{
                y:-110,
            },
		    title:{
		        text:'博士生',
		        x:'center',
		        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                fontSize: 14,
	            },
		      },
		    color:['red','#E8E8E8'],
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:15200, name:15200},
		                {value:72294, name:''},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});


$(function(){
	var myChart = echarts.init(document.getElementById('jsnlfx'));
	option = {
		    
		    tooltip: {
		        trigger: 'item',
		        backgroundColor : 'rgba(0,0,250,0.2)'
		    },
		    visualMap: {
		        top: 'middle',
		        right: 10,
		        color: ['red', 'yellow'],
		        calculable: false,
		        show:false,
		    },
		    radar: {
		       indicator : [
		           { text: '35岁以下', max: 400},
		           { text: '36-40岁', max: 400},
		           { text: '41-45岁', max: 400},
		           { text: '46-50岁', max: 400},
		           { text: '51-55岁', max: 400},
		           { text: '56-60岁', max: 400},
		           { text: '61-65岁', max: 400}
		        ]
		    },
		    series : (function (){
		        var series = [];
		        for (var i = 1; i <= 28; i++) {
		            series.push({
		                name:'',
		                type: 'radar',
		                symbol: 'none',
		                itemStyle: {
		                    normal: {
		                        lineStyle: {
		                          width:1
		                        }
		                    },
		                    emphasis : {
		                        areaStyle: {color:'rgba(0,250,0,0.3)'}
		                    }
		                },
		                data:[
		                  {
		                    value:[
									(40 - i) * 10,
									(38 - i) * 4 + 60,
									i * 5 + 10,
									i * 9,
									i * i /2
		                    ],
		                    name: i + 2000 + ''
		                  }
		                ]
		            });
		        }
		        return series;
		    })()
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('jszs'));
	var itemStyle0 = {
			normal:{color:'#3EEDE7'}
	};
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
//		        orient: 'vertical',
//		        x: 'left',
//		        data:['教师总数']
		    }, title:{
		        text:'教师总数',
		        x:'center',
		        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                fontSize: 14,
	            },
		      },
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            itemStyle:itemStyle0,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center',
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:4837, name:'4837'},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('zrjs'));
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
//		        orient: 'vertical',
//		        x: 'left',
//		        data:['专任教师人数']
		    }, title:{
		        text:'专任教师人数',
		        x:'center',
		        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                fontSize: 14,
	                
	            },
		      },
		    color:['#358AEB','#E8E8E8'],
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:[
		                {value:3756, name:'3756'},
		                {value:4837, name:''},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('jsblfx'));
	option = {
	    color: ['#3398DB'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : ['2010年', '2011年', '2012年', '2013年', '2014年', '2015年', '2016年','2017年'],
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel: {
		            formatter: '{value}'
		        }
	        }
	    ],
	    series : [
	        {
	            name:'生师比例',
	            type:'bar',
	            barWidth: '60%',
	            data:[14.9, 15.3,13.2, 14.5, 12.9, 13.5, 14.7,13.9]
	        }
	    ]
	};

	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('ryjffx'));
option = {
		   
	    tooltip: {
	        trigger: 'axis'
	    },
	   

	    toolbox: {
	        show: true,
	        feature: {
	            dataZoom: {
	                yAxisIndex: 'none'
	            },
	            dataView: {readOnly: false},
	            magicType: {type: ['line', 'bar']},
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    xAxis:  {
	    	//name:'单位（年）',
	        type: 'category',
	        boundaryGap: false,
	        data: ['2010年','2011年','2012年','2013年','2014年','2015年','2016年','2017年']
	    },
	    yAxis: {
	    	name:'单位（万元）',
	        type: 'value',
	        axisLabel: {
	            formatter: '{value}'
	        }
	    },
	    series: [
	        {
	            name:'经费',
	            type:'line',
	            data:[6945.2064, 7856.6450, 5412.5304, 8234.7409, 6987.6135, 5698.7003, 7541.5806,8541.6345],
	            markPoint: {
	               
	            },
	           
	        },
	       
	    ]
	};
myChart.setOption(option);
});
$(function(){
	
});

$(function(){
	
});

$(function(){

});
