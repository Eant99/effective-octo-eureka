<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>事前审批维护列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
	.dataTables_scroll{
		width: 50%!important;
	}
  	.onoffswitch{  
  		margin: auto; 
  	}   
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>模块名称</label>
					<input type="text" id="txt_mkmc" class="form-control" name="mkmc"  placeholder="请输入事前审批名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
		               <button type="button" class="btn btn-primary" id="btn_save">保存</button>
				</div>
			</div>
		</form>
	</div>
	<form id="myform_2">
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>事前审批名称</th>
				            <th>是否启用</th>
				            <th>是否停止新增</th>
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
	</form>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
var target = "${ctx}/sqspwh";

//加载列表数据
   var columns = [
      {"data": "MKBH",orderable:false, 'render': function (data, type, full, meta){
   	   return '<input type="checkbox" name="guid" class="keyId" xmguid="'+full.XMGUID+'" value="' + data + '">'
   	   			+'<input type="hidden" name="mkbh" value="'+data+'">';
      },"width":10,'searchable': false},
      {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
         	return data;},"width":41,"searchable":false,"class":"text-center"},
      {"data": "MKMC",defaultContent:""},
      {"data": "SFQY",defaultContent:"","orderable":false,'render':function(data,type,full){
   	   var sfqy = "";
   	   var xh = full._XH;
   	   //默认为启用
   	   if(data == null||data == "1"){
   		 	sfqy = "checked";
   	   }
   	   return  '<div class="switch">'+
				'<div class="onoffswitch">'+
				'<input type="checkbox" name="sfqy'+xh+'" id="sfqy'+xh+'" class="onoffswitch-checkbox" '+sfqy+' data-sqspmc="'+full.MKMC+'" value="1"/>'+
					 '<label class="onoffswitch-label" for="sfqy'+xh+'">'+
	               		'<span class="onoffswitch-inner"></span>'+
	               		'<span class="onoffswitch-switch"></span>'+
	               	 '</label>'+
				'</div>'+
	            '</div>';
      }},
      {"data": "SFTZXZ",defaultContent:"","orderable":false,'render':function(data,type,full){
   	   var sftzxz = "";
   	   var xh = full._XH;
   	   if(data == "1"){
   		   sftzxz = "checked";
   	   }
   	   return  '<div class="switch">'+
				'<div class="onoffswitch">'+
				'<input type="checkbox" name="sftzxz'+xh+'" id="sftzxz'+xh+'" class="onoffswitch-checkbox" '+sftzxz+' data-sqspmc="'+full.MKMC+'" value="1"/>'+
					 '<label class="onoffswitch-label" for="sftzxz'+xh+'">'+
	               		'<span class="onoffswitch-inner"></span>'+
	               		'<span class="onoffswitch-switch"></span>'+
	               	 '</label>'+
				'</div>'+
	            '</div>'+
	            '<input type="hidden" name="end" />';
      }}
    ];
   table = getDataTableByListHj("mydatatables",target+"/getListData",[2,'desc'],columns,0,1,setGroup);
   
//查询是否有未审批完成的单据   
function querySqspSfsy(sqspmc){
	var tag = false;
	if(isNull(sqspmc)){
		return tag;
	}
	var type = "";
	switch (sqspmc) {
	case "出差业务":
		type = "cc";
		break;
	case "公务接待业务":
		type = "gwjd";
		break;
	default:
		break;
	}
	var url_ = target+"/getSqspSfsy";
	$.ajax({
		type:"post",
		url:url_,
		data:"type="+type,
		dataType:"json",
		async:false,
		success:function(data){
			if(data.success){
				tag = true;
			}
		},
		error:function(){
			alert("抱歉，系统出现错误！");
		}
	});
	return tag;
}   

   
$(function () {
	//当禁用模块时
	$(document).on("click","[name^=sfqy]:not(:checked)",function(){
		var $sftzxz = $(this).parents("tr").find("[name^=sftzxz]:not(:checked)");
		var $sfqy = $(this).parents("tr").find("[name^='sfqy']:not(:checked)");
		var tag = $sftzxz.length > 0;
		var content = tag ? "是否停止新增？" : "";
		var sqspmc = $(this).data("sqspmc");
		if(querySqspSfsy(sqspmc)){
			confirm("该事前审批模块正在使用中，无法禁用！"+content,"W",{title:"提示"},function(){
				$("a.layui-layer-btn1",top.document)[0].click();
				$sfqy.click();
				if(tag){
					$sftzxz.click();
				}
			});
		}
	});
	$().click(function(){
		$(this).click();
	})
	$("#btn_save").click(function(){
		var json = $("#myform_2").serializeObject2("mkbh","end","^sfqy<>^sftzxz","sfqy<>sftzxz");
		var jsonStr = JSON.stringify(json);
		$.ajax({
			type:"post",
			data:"json="+jsonStr,
			url:target+"/doSave",
			dataType:"json",
			success:function(data){
				if(data.success){
					alert("保存成功！");
					location.reload();
				}else{
					alert("保存失败，请稍后重试！");
				}
			},
			error:function(){
				alert("抱歉，系统出现异常！");
			}
		});
	})
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