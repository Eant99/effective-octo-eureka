<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支出预算修正</title>
<%@include file="/static/include/public-list-css.inc"%>

</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh" table="K" placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>单位名称</label>
					<input type="text" id="txt_dwmc" class="form-control" name="dwmc"  table="K" placeholder="请输入单位名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_save">保存</button>
	               <button type="button" class="btn btn-default" id="btn_check">复核</button>
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>单据编号</th>
				            <th>单位名称</th>
				            <th>编制年度</th>
				             <th>项目分类</th>
				            <th>项目类型</th>
				            <th>项目名称</th>
				            <th>上年预算总额（万元）</th>
				            <th>上年支出总额（万元）</th>
				            <th>本年建议总额（万元）</th>
				            <th>差额（万元）</th>
				            <th>修正人</th>
				            <th>修正时间</th>				            
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var d = new Date();
	var date = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
	
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false,defaultContent:"001", 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "DJBH",defaultContent:"001"},
       {"data": "DWMC",defaultContent:"测试"},
       {"data": "BZND",defaultContent:"2017","class":"text-center"},
       {"data": "XMFL",defaultContent:"一级类"},
       {"data": "XMLX",defaultContent:"出差类"},
       {"data": "XMMC",defaultContent:"测试项目"},
       {"data": "SNYSZE",defaultContent:"50.0000","class":"text-right"},
       {"data": "SNZCZE",defaultContent:"100.00",'render': function (data, type, full, meta){
           return '<input type="type" name="snzcze" class="text-right number1" value="100.0000" style="width:100%;border:none;"  guid = "'+full.SNZCZE+'">';
         },"width":200},
       {"data": "BNJYZE",defaultContent:"100.00",'render': function (data, type, full, meta){
           return '<input type="type" name="bnjyze" class="text-right number1 checkval" style="width:100%;border:none;"  guid = "'+full.BNJYZE+'">';
         },"width":200},
       {"data": "CE",defaultContent:"100.00",'render': function (data, type, full, meta){
         return '<input type="type" name="ce" class="text-right number1" style="width:100%;border:none;"  guid = "'+full.CE+'">';
       },"width":200},
       {"data": "XZR",defaultContent:"",'render': function (data, type, full, meta){
         return '<input type="type" name="LHH" style="width:100%;border:none;" value="' + data + '" guid = "'+full.XZR+'">';
       },"width":400},  
       {"data": "XZSJ",defaultContent:"",'render': function (data, type, full, meta){
         return '<input type="type" name="LHH" class="date" style="text-align: center;width:100%;border:none;" value="' + date + '"  guid = "'+full.XZSJ+'">';
       },"width":400}
      
      
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/onesrysxz/getZcysxzPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//保存按钮
   	$("#btn_save").click(function(){
   		alert("保存成功！");
   	});
  	//复核
  	$("#btn_check").click(function(){
		   alert("操作成功");
	   });
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/jsxxs/expExcel?treedwbh=${dwbh}","收入预算修正信息","${ctx}",guid.join(","));
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	doOperate("${ctx}/jsxxs/goJsxxPage?guid="+guid,"U");
   	});
	//
  	$(document).on("blur", ".number1", function(e){
	$(this).Num(4,true,false,e);
	return false;
   });
  //差额计算
    var i=0;
 	$(document).on("blur",".checkval",function(){
    		if(true){
    			i++;
    		}
    		$(this).parent().prev().find("input:first").attr("id","snzcze"+i+"");
    		$(this).parent().next().find("input:first").attr("id","ce"+i+"");
    		var snzcze=Number($("#snzcze"+i+"").val());  		
    		var val1=$(this).val();
    		if(val1.length==0 ){
    			
    		}else{
    			$("#ce"+i+"").val(snzcze-val1);
    			$("#ce"+i+"").focus();
    		
    		}
    			
    		
 	});
   
	
	
});
$(function() {	
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