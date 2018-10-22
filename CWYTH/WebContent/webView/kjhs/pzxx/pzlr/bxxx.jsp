<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>网上报销信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.btn-link{
	padding: 0px!important;
	margin: 0px!important;
}
 .dataTables_scrollBody{
    height:425px !important;
 }
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>单据类型</label>
					<select id="sel_djlx" class="form-control">
					<option value="all">全部</option>
					<option value="00">日常报销</option>
					<option value="01">差旅费报销</option>
					<option value="02">公务接待费报销</option>
					<option value="03">借款</option>
					<option value="04">个人收入发放</option>
<!-- 						<option value="0" selected>报销信息</option> -->
<!-- 						<option value="1">薪资信息</option> -->
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control"  placeholder="请输入单据编号" onkeydown="if(event.keyCode==13)return false;">
				</div>
				<div class="form-group">
					<label>申请人</label>
					<input type="text" id="txt_sqr" class="form-control"  placeholder="请输入申请人" onkeydown="if(event.keyCode==13)return false;">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search1"><i class="fa icon-chaxun"></i>查询</button>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<div class="alert alert-info">
		          	<strong>提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息。或者选中一条或多条数据点击
		          	<button type="button" class="btn btn-primary" id="btn_submit">确定</button>
	        </div>
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr id="head">
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>单据编号</th>
				            <th>申请人</th>
				            <th>申请日期</th>
				            <th>所在部门</th>
				            <th>报销类型</th>
				            <th>报销金额（元）</th>
				            <th>报销项目</th>
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
var target = "${ctx}/kjhs/pzxx/pzlr";
var pzlx = "${param.pzlx}";
var pzbh = "${param.pzbh}";
var winId = top.layer.getFrameIndex(window.name);
//加载列表数据
var columns = [
   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	   return '<input type="checkbox" name="guid" class="keyId" data-bxlx="'+full.BXLX+'" value="'+data+'">';
   },"width":10,'searchable': false},
   {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
      	return data;},"width":41,"searchable":false,"class":"text-center"},
   {"data": "DJBH",defaultContent:"","class":"text-center"},
   {"data": "SQR",defaultContent:""},
   {"data": "SQRQ",defaultContent:"","class":"text-center"},
   {"data": "SZBM",defaultContent:""},
   {"data": "BXLX",defaultContent:""},
   {"data": "BXJE",defaultContent:"","width":80,'render':function(data,type,full,meta){
	   return parseFloat(data).toFixed(2);
   },"class":"text-right"},
   {"data": "BXXM",defaultContent:""}
 ];
table = getDataTableByListHj("mydatatables",target+"/getBxxxData?bxlx="+$("#sel_djlx").val()+"&djbh="+$("#txt_djbh").val(),[2,'asc'],columns,0,1);
$(function () {
	$("#sel_djlx").change(function(){
// 		if($(this).val() == "1")
// 			pageName = "xzxx";
		var bxlx = $("#sel_djlx").val();
		var djbh = $("#txt_djbh").val();
		table.ajax.url("${ctx}/kjhs/pzxx/pzlr/getBxxxData?bxlx="+bxlx+"&djbh="+djbh);
		table.ajax.reload();
	});
	$(document).on("click","#btn_search1",function(){
		var pageName = "bxxx";
// 		if($(this).val() == "1")
// 			pageName = "xzxx";
		var bxlx = $("#sel_djlx").val();
		var djbh = $("#txt_djbh").val();
		var sqr = $("#txt_sqr").val();
		table.ajax.url("${ctx}/kjhs/pzxx/pzlr/getBxxxData?bxlx="+bxlx+"&djbh="+djbh+"&sqr="+sqr);
		table.ajax.reload();
	});
	$("#btn_submit").click(function(){
		var checkbox = $("#mydatatables").find(".keyId").filter(":checked");
		var data = [];
		if(checkbox.length > 0){
			checkbox.each(function(){
				var type = $(this).data("bxlx");
				if(type == "日常报销"){
					  type = "rcbx";
				  }else if(type == "差旅费报销"){
					  type = "clfbx";
				  }else if(type == "公务接待费报销"){
					  type = "gwjdbx";
				  }else if(type == "借款"){
					  type = "jkbx";
				  }else if(type == "个人收入发放"){
					  type = "srsblr";
				  }
				var gid = $(this).val();
				data.push({"gid":gid,"type":type});
			});
			console.log(JSON.stringify(data));
			$.ajax({
				  type:"post",
				  url:target+"/doadd",
				  data:"pzlx=${param.pzlx}&json="+JSON.stringify(data),
				  dataType:"json",
				  success:function(data){
					  	 getIframWindow("${param.pname}").pageSkip(getBasePath(),"pzlr&type=self&pzlx=${param.pzlx}");
			        	 close(winId);
				  },
				  error:function(){
					  alert("抱歉，系统出现错误！");
				  }
			  });
		}else{
			alert("请至少选择一条数据");
		}
		
	});
  $(document).on("dblclick","tr:not(#head)",function(){
	  var gid = $(this).find(".keyId").val();
	  if(gid == null||gid == ""){
		  alert("没有可选的数据");
		  return;
	  }
	  var type = $(this).find(".keyId").data("bxlx");
	  if(type == "日常报销"){
		  type = "rcbx";
	  }else if(type == "差旅费报销"){
		  type = "clfbx";
	  }else if(type == "公务接待费报销"){
		  type = "gwjdbx";
	  }else if(type == "借款"){
		  type = "jkbx";
	  }else if(type == "个人收入发放"){
		  type = "srsblr";
	  }
	  var data = [{"gid":gid,"type":type}];
	  $.ajax({
		  type:"post",
		  url:target+"/doadd",
		  data:"pzlx=${param.pzlx}&json="+JSON.stringify(data),
		  dataType:"json",
		  success:function(data){
			  close(index);
			  getIframWindow("${param.pname}").pageSkip(getBasePath(),"pzlr&type=self&pzlx=${param.pzlx}");
	          close(winId);
		  },
		  error:function(){
			  alert("抱歉，系统出现错误！");
		  },
		beforeSend:function(){
			index = loading(2);
		}
	  });
  })
});
</script>
</body>
</html>