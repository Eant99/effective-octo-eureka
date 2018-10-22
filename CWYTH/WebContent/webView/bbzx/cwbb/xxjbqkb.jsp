<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学校基本情况表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	table{
		border-collapse:collapse!important;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   	 	border-bottom-width: 0!important;
	}
	.select2-selection{
		border-radius: 4px!important;
	}
	thead th{
		text-align:center!important;
	}
	.allDiv{
		width:100%;
		height:20px;
	}
	.first{
		float:left;
		text-align:left;
		width:33%;
	}
	.second{
		float:left;
		text-align:center;
		width:34%;
	}
	.third{
		float:left;
		text-align:right;
		width:33%;
	}
	.div_bottom{
    	width: 99%;
    	margin-top: 20px;
    	bottom: 20px;
   		background-color: #f3f3f3;
	}
	.bom{
		float:left;
		text-align:left;
		width:33%;
	}
/* 	.td_right{ */
/* 		text-align:right; */
/* 	} */
	td input{
		border:none;
		width:100%!important;
		text-align:right;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>年度</label>
					<select style="width:70px;" class="form-control select2">
						<option value="1">2017</option>
						<option value="2">2016</option>
					</select>
				</div>
				<div class="btn-group pull-right" role="group">
				   <button type="button" class="btn btn-default" id="btn_imp">导入</button>
				   <button type="button" class="btn btn-default" id="btn_tem">下载导入模板</button>
				   <button type="button" class="btn btn-default" id="btn_save">保存</button>
	               <button type="button" class="btn btn-default" id="btn_exp">导出excel</button>
	               <button type="button" class="btn btn-default" id="btn_print">打印预览</button>
				</div>
				<div class="btn-group pull-right" role="group" style="width:10%;margin-top:2px;">
					<input type="file" class="files" id="files" style="width: 152px;"/>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<center><h3>学校基本情况表</h3></center>
			<div class='scrollable-area'> 
				<div class="allDiv">
					<div class="first">
						核算单位：山东国子软件
					</div>
					<div class="second">
						2017年度
					</div>
					<div class="third">
						单位:元
					</div>
				</div>
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				        	<th>项目</th>
				        	<th>年初数</th>
				        	<th>期末数</th>
				        	<th>项目</th>
				        	<th>年初数</th>
				        	<th>期末数</th>
				        </tr>
					</thead>
				    <tbody>
				    	<tr>
				    		<td>一、学校基本情况</td>
				    		<td style="text-align:center;">————</td>
				    		<td style="text-align:center;">————</td>
				    		<td>四、固定资产总值（千元）</td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>（一）班级数（个）</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>（一）房屋及构筑物</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    	</tr>
				    	<tr>
				    		<td>（二）在校学生数（人）</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>（二）专用设备</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    	</tr>
				    	<tr>
				    		<td>其中：1.幼儿</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>（三）通用设备</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    	</tr>
				    	<tr>
				    		<td class="td_right">2.小学学生</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>（四）文物和陈列品</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    	</tr>
				    	<tr>
				    		<td class="td_right">3.初中学生</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>（五）图书、档案</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    	</tr>
				    	<tr>
				    		<td class="td_right">4.高中学生</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>其中：1.一般图书（千元、册）</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    	</tr>
				    	<tr>
				    		<td class="td_right">5.中职学生</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td class="td_right">2.电子图书（千元、册）</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    	</tr>
				    	<tr>
				    		<td class="td_right">6.特殊教育学生</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>（六）家具、用具、装具及动植物</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    	</tr>
				    	<tr>
				    		<td class="td_right">7.其他</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>（三）寄宿生（人）</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>二、教职工基本情况（人）</td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>（一）编制教职工</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>其中：专任教师</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>（二）聘任制教职工</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>　其中：专任教师</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>（三）兼任教师</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>（四）其他</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>三、离退休人员</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>（一）离休人员</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>（二）退休人员</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td rowspan="9" style="text-align:center;vertical-align:middle;">补充资料</td>
				    		<td>1.校园占地面积</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>平方米</td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>
				    		<td>2.车辆数</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>辆，其中：小汽车</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>辆</td>
				    	</tr>
				    	<tr>
				    		<td>3.年末校舍面积</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>平方米，其中：危房面积</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>平方米</td>
				    	</tr>
				    	<tr>
				    		<td>4.本年新建改扩建校舍</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>平方米，金额</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>千元</td>
				    	</tr>
				    	<tr>
				    		<td>5.本年购置学生课桌椅</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>单人套，金额</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>千元</td>
				    	</tr>
				    	<tr>
				    		<td>6.本年购置教学仪器设备</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>台，件，金额</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>千元</td>
				    	</tr>
				    	<tr>
				    		<td>7.本年购置图书资料</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>册，金额</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>千元</td>
				    	</tr>
				    	<tr>
				    		<td class="td_right">其中：（1）一般图书</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>册，金额</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>千元</td>
				    	</tr>
				    	<tr>
				    		<td class="td_right"><span class="zw">占位符</span>（2）电子图书</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>册，金额</td>
				    		<td><input type="text" class="td_input" value=""/></td>
				    		<td>千元</td>
				    	</tr>
				    </tbody>
				</table>
				<div class="div_bottom">
					<div class="bom">单位主要负责人(签字):<br />注：本表反映本单位各项资产及负债情况</div>
					<div class="bom">财务负责人(签字):</div>
					<div class="bom">制表人(签字):</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
  	//打印预览
   	$("#btn_print").click(function(){
   		alert("暂无数据！");
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功！");
   	});
	$("#btn_save").click(function(){
		alert("保存成功！");
	});
	$("#btn_tem").click(function(){
		alert("模板下载成功");
	});
	$("#btn_imp").click(function(){
		var file = $("#files").val();
		var extendName = file.substring(file.lastIndexOf("."));
		if(file==""){
			alert("请选择文件");
			return;
		}
		alert("导入成功！");
	});
});
$(function() {
	$(".td_right").prepend("<span class='zw'>占位符</span>");
	var bg = $(".zw").parents("tr").css("background-color");
	$(".zw").css("color",bg);
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</body>
</html>