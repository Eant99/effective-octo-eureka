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
/*function xs(){
	$("#yszc").css("display","block");
	$("#yszc").css("height","193px");
	 var myChart = echarts.init(document.getElementById('yszc'));
	 option = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        x: 'right',
			        data:['学校建设支出','教学支出','后勤支出','经营支出','其他支出']
			    },
			    series: [
			        {
			            name:'',
			            type:'pie',
			            radius: ['50%', '70%'],   
			            center:['40%','50%'],
			            avoidLabelOverlap: false,
			            label: {
			                normal: {
			                    show: false,
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
			                {value:119, name:'学校建设支出'},
			                {value:105, name:'教学支出'},
			                {value:234, name:'后勤支出'},
			                {value:235, name:'经营支出'},
			                {value:85, name:'其他支出'},
			            ]
			        }
			    ]
			};
	 myChart.setOption(option);
}*/
myChart();
function myChart(){
	var myChart = echarts.init(document.getElementById('yssr'));
	 $.ajax({
			url:"${ctx}/index/getData",
			type:"POST",
			data:"qrzt=123",
			dataType:"JSON",
			async:false,
			success:function(result){
				 option = {
					     tooltip : {
					         trigger: 'axis',
					         axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					             type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					         }
					     },
					     legend: {
					         data:['收入预算','基本支出预算','项目支出预算']
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
					             data : []
					         }
					     ],
					     yAxis : [
					         {
					             type : 'value'
					         }
					     ],
					     series : [      
					         {
					             name:'收入预算',
					             type:'bar',
					             stack: '广告',
					             data:[]
					         },
					         {
					             name:'基本支出预算',
					             type:'bar',
					             stack: '广告',
					             data:[]
					         },
					         {
					             name:'项目支出预算',
					             type:'bar',
					             stack: '广告',
					             data:[]
					         },
					    ]
					 };
				 myChart.setOption(option);
			}
	             
		});
	 
}
/*$(function(){
	
 var myChart = echarts.init(document.getElementById('yssr'));*/

// app.title = '堆叠柱状图';
/* option = {
     tooltip : {
         trigger: 'axis',
         axisPointer : {            // 坐标轴指示器，坐标轴触发有效
             type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
         }
     },
     legend: {
         data:['收入预算','基本支出预算','项目支出预算']
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
             data : ['办公室','保卫处','财务处','工会','教务处','科研处','图书信息中心','院领导','招生就业处','总务处','组织人事处']
         }
     ],
     yAxis : [
         {
             type : 'value'
         }
     ],
     series : [      
         {
             name:'收入预算',
             type:'bar',
             stack: '广告',
             data:[120, 132, 101, 134, 90, 230, 210,234,354,124,234]
         },
         {
             name:'基本支出预算',
             type:'bar',
             stack: '广告',
             data:[220, 182, 191, 234, 290, 330, 310,145,321,221,214]
         },
         {
             name:'项目支出预算',
             type:'bar',
             stack: '广告',
             data:[150, 232, 201, 154, 190, 330, 410,264,234,211,152]
         },
    ]
 };
 myChart.setOption(option);*/
     
/* $.ajax({
		url:"${ctx}/index/getData",
		type:"POST",
	//	data:"qrzt="+sel,
		dataType:"JSON",
		async:false,
		success:function(result){
			 option = {
				     tooltip : {
				         trigger: 'axis',
				         axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				             type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				         }
				     },
				     legend: {
				         data:['收入预算','基本支出预算','项目支出预算']
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
				             data : []
				         }
				     ],
				     yAxis : [
				         {
				             type : 'value'
				         }
				     ],
				     series : [      
				         {
				             name:'收入预算',
				             type:'bar',
				             stack: '广告',
				             data:[]
				         },
				         {
				             name:'基本支出预算',
				             type:'bar',
				             stack: '广告',
				             data:[]
				         },
				         {
				             name:'项目支出预算',
				             type:'bar',
				             stack: '广告',
				             data:[]
				         },
				    ]
				 };
			 myChart.setOption(option);
		}
             
	});*/
 
 
	
		
/*});*/


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
		        orient: 'vertical',
		        x: 'left',
		        data:['项目总数57项']
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
		                {value:57, name:'项目总数57项'},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
//获取公告通知数据
$(function(){
	var myChart = echarts.init(document.getElementById('xmzs'));
	alert("aaaaaa");
	var columns = [
		{"data": "GID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="gid" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		{"data": "TITLE","render":function (data, type, full, meta){
		   	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link">'+ data +'</a></div>';
	    }},
	   	{"data": "FBR",defaultContent:""},
	   	{"data": "FBSJ",defaultContent:""},
	   	
	   	
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/index/getggList",[4,'desc'],columns,0,1,setGroup);
	 });
$(function(){
	var myChart = echarts.init(document.getElementById('xmjf'));
	var itemStyle1 = {
   			normal: {color: '#06BAB1'}
		};
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:['项目经费8953万元']
		    },
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            itemStyle:itemStyle1,
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
		                {value:8953, name:'项目经费8953万元'},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('jgxm'));
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:['竣工项目23项','']
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
		                {value:23, name:'竣工项目23项'},
		                {value:34, name:''},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('jxxm'));
	option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:['进行项目24项','']
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
		                {value:23, name:'进行项目24项'},
		                {value:34, name:''},
		            ]
		        }
		    ]
		};
	 myChart.setOption(option);
});
$(function(){
	var myChart = echarts.init(document.getElementById('xmqkfx'));
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
		           { text: '省部级项目', max: 400},
		           { text: '国家级项目', max: 400},
		           { text: '其他项目', max: 400},
		           { text: '横向课题', max: 400},
		           { text: '校级项目', max: 400},
		           { text: '纵向课题', max: 400},
		           { text: '市级项目', max: 400}
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
