<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预约设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-js.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
﻿<link href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css" />
<style type='text/css'>
body {
	margin-top: 0px;
	text-align: center;
	font-size: 14px;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
}

.fa-angle-right:before {
    content: "";
}
.fa-angle-left:before {
    content: "";
}
.savebt{
	background-color: #00acec;
	color:white;
}
#calendar {
	width:650px;
	margin:0 auto;
	clear:both;
	float: left;
}
.select{
    background: #0E85DC; 
    color: white; 
}

.bttn{
    background-color: #00acec;
    color: white;
    border: 1px solid #ccc;
    border-radius: 4px;
    line-height: 30px;
    padding-left: 6px;

}
.pagination {
width:140px;
float:right;
}
.shili div{

float: left;
width: 20px !important;
height: 20px !important;
    margin-left: 12px;
    margin-right: 12px;
    font-size: 18px;
}
.required{
    color: red;
    padding: 3px;
    font-size: 14px;
}

ul li{
list-style-type:none;
margin-top: 20px;


  }
.shili .rlsl{
	width:72px !important;
	line-height: 25px;
	 margin-left: -3px;
}
.rili{
height:90%;
position: absolute;
margin-left: 6%;
}
.right1{
margin-top:60px;
height: 500px;
width:450px;
float:left;
margin-left:760px;

}
.hh{
height: 28px !important;
}
.form-control{
height: 28px !important;
}
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 28px;
    line-height: 28px;
    padding-left: 6px;
}
input , select{
 border-radius:4px; 
}
</style>

</head>
<body>
<!--          <div class="pull-center"> -->
            
<!-- 			<button type="button" class="btn btn-primary bttn" id="btn_update">预约设置</button> -->
<!-- 			<button type="button" class="btn btn-primary bttn" id="btn_look">查看预约情况</button> -->
<!-- 		</div> -->
<!-- 		<div class="pull-right"> -->
<!-- 			<button class="btn btn-default savebt" id="btn_save">保存</button> -->
<!-- 		</div> -->
<div  class="rili">
		<div id='calendar'></div>
		</div>

<div  class="right1">
			
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>预约设置信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
        	
			<hr class="hr-normal">
			<div style="border:1px solid rgb(226, 220, 220);margin-left:15px;margin-right:15px;margin-top:15px; height: 434px">
			<div class="container-fluid box-content" style="margin-top:5px;margin-bottom:5px;">
				<div class="row">
					<div class="col-md-9">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>上午是否可以报销</span>
							<div class="radiodiv">
                            <input type="radio" name="swsfkybx" value="0" checked="checked"> 是&ensp;&ensp;
			                  <input type="radio" name="swsfkybx" value="1"> 否
							</div>
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-9">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>下午是否可以报销</span>
							<div class="radiodiv">
                                <input type="radio" name="xwsfkybx"  value="0" checked="checked"> 是&ensp;&ensp;
			                         <input type="radio" name="xwsfkybx" value="1" > 否
			                </div>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="col-md-9">
						<div class="input-group">
							<span class="input-group-addon hh" ><span class="required">*</span>提前预约天数（天）</span>
                            <input  id="txt_yyts" style="height: 28px;"  class="hh form-control input-radius text-right int" name="yyts" placeholder="当前时间内允许的最大预约人数" value="3"/>
					</div>
				</div>	
				</div>
				<div class="row">
					<div class="col-md-9">
						<div class="input-group">
							<span class="input-group-addon">&ensp;上午报销时段&ensp;&ensp;&ensp;&ensp;&ensp;</span>
             <input type="text" id="txt_kssj" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" class="form-control input-radius" placeholder="上午报销时段开始时间" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="HH:mm:ss"/>"/>       
            <input type="text" id="txt_kssj" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" class="form-control input-radius" placeholder="上午报销时段结束时间" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="HH:mm:ss"/>"/>
           
						</div>
					</div>

				</div>
				<div class="row">
					<div class="col-md-9">
						<div class="input-group">
							<span class="input-group-addon">&ensp;下午报销时段&ensp;&ensp;&ensp;&ensp;&ensp;</span>
          <input type="text" id="txt_jssj" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" class="form-control input-radius" placeholder="下午报销时段开始时间" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="HH:mm:ss"/>"/>
            <input type="text" id="txt_jssj" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" class="form-control input-radius" placeholder="下午报销时段结束时间" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="HH:mm:ss"/>"/>
						</div>
					</div>
				
				</div>
				<div class="row">
				<div class="col-md-9">
						<div class="input-group">
							<span class="input-group-addon">预约时长（分钟）&ensp;&ensp;</span>
							<input style="height: 28px;" id="txt_yysc" class="form-control input-radius" name="yysc" value="">
						</div>
					</div>
				</div>	
				<div class="row">
				<div class="col-md-9">
						<div class="input-group">
							<span class="input-group-addon">允许预约人数 &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span>
							<input style="height: 28px;" id="txt_fddbrh" class="form-control input-radius text-right int" name="fddbrh" value="">
						</div>
					</div>
				</div>	
				<div class="row" style="margin-top: 80px;">
				<div class="col-md-12">
						<div class="input-group" style="width: 80%">
							<button type="button" class="bttn" id="btn_sc">生&ensp;成</button>&ensp;&ensp;&ensp;
			                 <button type="button" class="bttn" id="btn_bc">保&ensp;存</button>
						</div>
					</div>
				</div>	
				</div>	
			</div>	
	</div>
			
			
			
		</div>
		<div class="box" style="margin-top:550px">
		<div class="box-panel">
			<hr class="hr-normal">
				<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>					
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>允许预约人数</th>						
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
		</div>
	</div>	
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery-ui-1.8.23.custom.min.js"></script>
<link href='${pageContext.request.contextPath}/static/ggs/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='${pageContext.request.contextPath}/static/ggs/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<link href='${pageContext.request.contextPath}/static/ggs/fullcalendar/fullcalendar.custom.css' rel='stylesheet' />
<script src='${pageContext.request.contextPath}/static/ggs/fullcalendar/fullcalendar.js' ></script>
<script type="text/javascript">
	$(document).ready(function() {
		//列表数据
		var columns = [
				{
					"data" : "GUID",
					orderable : false,
					"render" : function(data, type, full, meta) {
						return '<input type="checkbox" class="keyId" name="guid" bz="'+full.BZS+'" value="' + data + '">';
					},
					"width" : 10,
					'searchable' : false
				},	
				{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	            	return data;
	            },"width":41,"searchable": false,"class":"text-center"},
				{
					"data" : "KSSJ",
					defaultContent : "",
						"render" : function(data, type, full, meta) {
							return '<input type="hidden"  class="keyId" name="kssj" bz="'+full.BZS+'" value="' + data + '">'+data+'';
						}
				},
				{
					"data" : "JSSJ",
					defaultContent : ""
				},
				{
					"data" : "YXYYRS",						
					defaultContent : "","class":"text-right",
						"render" : function(data, type, full, meta) {
							return '<input type="hidden"  class="keyId" name="yxyyrs" bz="'+full.BZS+'" value="' + data + '">'+data+'';
						},"class":"text-center"
				}
				
				 ];
		table = $('#mydatatables').DataTable({
	        ajax: {
	            url: "${pageContext.request.contextPath}/json/wsbx/jcsz/yysz.json"//获取数据的方法
	        },
	        "lengthMenu":getTopFrame().window.sessionRowNumLength,
	        "order": [ 2, 'asc' ],
	        "serverSide": true,
	        "columns": columns,
	        "language":{
	            //"search":"",
	            //"searchPlaceholder":"请输入种类名称"
	        },
	        "dom":"<'row'<'col-sm-7 col-sm-7 col-xs-7'li><'col-sm-5 col-sm-5 col-xs-5'>>t<'row'<'col-sm-12 col-sm-12 col-xs-12 pull-right'p>>"
	    });
		//保存
		$("#btn_bc").click(function(){
			alert("保存成功");
	     
		});
		$("#btn_sc").click(function(){		
			alert("生成成功！");
		});
		$(".tool-fix").remove();
		var hysbh = "${param.hysbh}";
		var newdate= new Date();
		var calendar = $('#calendar').fullCalendar({
            events:function(start,end,callback){
				var date=$.fullCalendar.formatDate($('#calendar').fullCalendar('getDate'),'yyyy-MM');
				$.ajax({
					url:'${pageContext.request.contextPath}/hysgl/getRc',
					dataType:'json',
					data:{date:date,hysbh:hysbh},
					success:function(data){
						$('.fc-day').each(function() {
							var rqdate = $(this).attr("data-date");
							for(var i=0;i<data.length;i++){
								var rcsj = data[i].RCSJ;
								var rczt = data[i].RCZT;
								if(rqdate==rcsj&&rczt=='0'){
									$(this).find(".fc-day-number").css("background","#0E85DC");
					 				$(this).find(".fc-day-number").css("color","white");
								}else if (rqdate==rcsj&&rczt=='1'){
									$(this).find(".fc-day-number").css("background","#fad60e");
					 				$(this).find(".fc-day-number").css("color","white");
								}else if (rqdate==rcsj&&rczt=='2'){
									var ndate =  $.fullCalendar.formatDate(newdate,"yyyy-MM-dd");
									if(rqdate<ndate){
										$(this).find(".fc-day-number").css("background","#FF0000");
						 				$(this).find(".fc-day-number").css("color","white");
									}else{
										$(this).find(".fc-day-number").css("background","#09A91E");
						 				$(this).find(".fc-day-number").css("color","white");
									}
								}
							}
						});
					}
				});
			},
			/*
			dayClick:function(dayDate,allDay,jsEvent,view){
				var date = $.fullCalendar.formatDate(dayDate,"yyyy-MM-dd");
				var ndate =  $.fullCalendar.formatDate(newdate,"yyyy-MM-dd");
				if(date>=ndate){
					select_commonWin("${pageContext.request.contextPath}/webView/wsbx/jcsz/yysz/yyszxx.jsp","预约详细信息","1020","630");
				}else{
					return false;
				}
			},
			*/
			//设置是否可被单击或者拖动选择
			selectable: true,
			//点击或者拖动选择时，是否显示时间范围的提示信息，该属性只在agenda视图里可用
			selectHelper: true,
			//点击或者拖动选中之后，点击日历外的空白区域是否取消选中状态 true为取消 false为不取消，只有重新选择时才会取消
			unselectAuto: true,
			select: function( startDate, end,allDay){
// 			   $("#btn_update").click(function(){
// 				   //alert(startDate);
// 				   var date = $.fullCalendar.formatDate(startDate,"yyyy-MM-dd");
// 				   //alert(date);
// 				   select_commonWin("${pageContext.request.contextPath}/webView/wsbx/jcsz/yysz/yyszxx.jsp","预约详细信息","1020","630");
				   
// 			   });
// 			   $("#btn_look").click(function(){
// 				   //alert(startDate);
// 				   var date = $.fullCalendar.formatDate(startDate,"yyyy-MM-dd");
// 				   //alert(date);
// 				   select_commonWin("${pageContext.request.contextPath}/webView/wsbx/jcsz/yysz/yyszlog.jsp","预约详细信息","1020","630");
				   
// 			   });
			   
			   
			},
			});
		
		});   
	$(function(){
		$("#fc-dateSelect").delegate("select","change",function(){
			var fcsYear = $("#fcs_date_year").val();
			var fcsMonth = $("#fcs_date_month").val();
			$("#calendar").fullCalendar('gotoDate', fcsYear, fcsMonth);
		});
		$(".fc-content").css("height","100%");
		$(".fc-day-content div").addClass("szheight");
		$("#fc-prev").on("click",function(){
			var date =$('#calendar').fullCalendar('getDate');
			var curMon = date.getMonth();
			var curYear =date.getFullYear();
			var preMonth=curMon-1;
			var preYear =curYear;
			if(preMonth==-1){
				preYear = curYear-1;
				preMonth=11;
			}
			$("#calendar").fullCalendar('gotoDate', preYear, preMonth);
		});	
		$("#fc-next").on("click",function(){
			var date =$('#calendar').fullCalendar('getDate');
			var curMon = date.getMonth();
			var curYear =date.getFullYear();
			var nextMon=curMon+1;
			var nextYear =curYear;
			if(nextMon==12){
				nextYear = curYear+1;
				nextMon=0;
			}

			$("#calendar").fullCalendar('gotoDate', nextYear, nextMon);
		});	
	
	});
</script>
</body>
</html>
