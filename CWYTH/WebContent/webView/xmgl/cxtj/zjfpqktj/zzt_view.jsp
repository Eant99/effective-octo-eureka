<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
*{
	margin:0px;
	padding:0px;
}
html,body{
	height:100%;
}
</style>
<script src="../extJs/echarts.js"></script>
<script src="../extJs/jquery-1.5.1.js"></script>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="btn-group pull-right" role="group">
	             <div class="row">
					<div class="col-sm-4">
					<div class="input-group">
						<select class="form-control input-radius" id="sel" style="width:100px;">
							<option value="bar">柱状图</option>
							<option value="lb">列表</option>
							<option value="pie">饼状图</option>
						</select>
				</div>
   			</div>
				</div>
		</form>
	</div>
	<div id="main" style="height:400px;margin: auto;"></div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	//视图切换
	$("#sel").change(function(){
		var view = $(this).val();
		if(view=="bar"){
			return;
		}
		if(view=="pie"){
			location.href="${ctx}/webView/xmgl/cxtj/zjfpqktj/pzt_view.jsp";
			return;
		}
		if(view=="lb"){
			location.href="${ctx}/webView/xmgl/cxtj/zjfpqktj/zjfpqktj_list.jsp";
			return;
		}
	});
});
$(function() {	
	 // 基于准备好的dom，初始化echarts实例
    function setOption(){
    	var type = "bar";
    	var option = {};
        var myChart = echarts.init(document.getElementById('main'));
   		var itemStyle1 = {
   			normal: {color: '#2F7ED8'}
		};
   		var itemStyle2 = {
   			normal: {color: '#E0522F'}
		};
   		
        // 指定图表的配置项和数据
        if(type == 'bar'){
        	option = {
    	            title: {
    	                text: '资金分配情况统计'
    	            },
    	            tooltip : {
    	            	trigger : 'axis',
    					axisPointer : {
    						type : 'shadow'
    					},
    					backgroundColor : '#fff',
    					textStyle : {
    						color : '#666666'
    					}
    	            },
    	            legend: {
    	                data:['山东大学','济南大学']
    	            },
    	            xAxis: {
    	                data: ["资金分配情况统计"]
    	            },
    	            yAxis: {},
            	  	series: [{
    	                name: '山东大学',
    	                type: 'bar',
    	                barWidth : 60,
    	                itemStyle: itemStyle1,
    	                data: ['1000.00']
          		}, {name: '济南大学',
                    type: 'bar',
                    barWidth : 60,
                    itemStyle: itemStyle2,
                    data: ['102.00']
          		}]
    	               
    	     };
        }else if(type =='pie'){
        	option = {
            	    title : {
            	        text: '资金分配情况统计',
            	        subtext: '资金',
            	        x:'center'
            	    },
            	    tooltip : {
            	        trigger: 'item',
            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
            	    },
            	    legend: {
            	        orient: 'vertical',
            	        left: 'left',
            	        data: ['山东大学','济南大学']
            	    },
            	    series : [
            	        {
            	            name: '山东大学',
            	            type: 'pie',
            	            radius : '55%',
            	            center: ['50%', '60%'],
            	            data:[
            	                {value:'1000.00', name:'山东大学'},
            	                {value:'102.00', name:'济南大学'},
            	               
            	            ],
            	            itemStyle: {
            	                emphasis: {
            	                    shadowBlur: 10,
            	                    shadowOffsetX: 0,
            	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
            	                }
            	            }
            	        }
            	    ]
            	};
        }else{
        	var insertText = "	<div ><table  class='gridtable'><caption align='top'><h2>资金分配情况分析</h2></caption> "+
			"<tr >   <td orderbyName='JSMC'style='width: 100px;'>山东大学</td> <td orderbyName='JXJH' style='width: 200px;text-align:center'>山东大学</td> <td orderbyName='JXJH' style='width: 200px;text-align:center'>济南大学</td> "
			+"</tr> "
		+ "	<tr  >  <td style='width: 100px;'>数目</td> <td style='width: 200px;text-align:right'>4</td> <td style='width: 200px;text-align:right'>2</td> </tr></div>";
        	document.getElementById("main1").innerHTML = document.getElementById("main1").innerHTML+insertText;
        }
        myChart.setOption(option);
    }
    setOption();
});
</script>
</body>
</html>