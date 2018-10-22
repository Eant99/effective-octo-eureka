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
    height:630px  !important;
 }
</style>
</head>
<body>
<div class="fullscreen">
	<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;margin:3px 11px;">
		<div class="search-simple">
			<div class="form-group">
				<label>单据类型</label>
				<select id="sel_djlx" class="form-control">
					<option value="0">未生成凭证</option>
					<option value="1">已生成凭证</option>
				</select>
			</div>
			<div class="pull-right">
				<button type="button" class="btn btn-primary" id="btn_glfadd">增加</button>
				<button type="button" class="btn btn-primary" id="btn_glfplsc">批量删除</button>
				<button type="button" class="btn btn-primary" id="btn_glfimp">导入Excel</button>
				<button type="button" class="btn btn-primary" id="btn_glfexp">导出Excel</button>
			</div>
		</div>
	</form>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<div class="alert alert-info" style="padding:7px;margin:0 -8px;">
		          	<strong>温馨提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息生成凭证
	        	</div>
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr id="head">
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>项目名称</th>
				            <th>项目类型</th>
				            <th>项目负责人</th>
				            <th>所属部门</th>
				            <th>学校科研基金（元）</th>
				            <th>学院科研基金（元）</th>
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
	   return '<input type="checkbox" name="guid" class="keyId" value="'+data+'">';
   },"width":10,'searchable': false},
   {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
      	return data;},"width":41,"searchable":false,"class":"text-center"},
   {"data": "XMMC",defaultContent:"","class":"text-center"},
   {"data": "XMLX",defaultContent:"","class":"text-center"},
   {"data": "XM",defaultContent:"","class":"text-center"},
   {"data": "MC",defaultContent:"","class":"text-center"},
   {"data": "XXJJ",defaultContent:"","class":"text-right"},
   {"data": "XYJJ",defaultContent:"","width":80,'render':function(data,type,full,meta){
	   return parseFloat(data).toFixed(2);
   },"class":"text-right"},
 ];
table = getDataTableByListHj("mydatatables",target+"/getGlfData",[2,'asc'],columns,0,1);
$(function () {
	$("#sel_djlx").change(function(){
		var bxlx = $("#sel_djlx").val();
		table.ajax.url("${ctx}/kjhs/pzxx/pzlr/getGlfData?bxlx="+bxlx);
		table.ajax.reload();
	});
	//数据导入
  	$(document).on("click","#btn_glfimp",function(){
		select_commonWin("${pageContext.request.contextPath}/webView/kjhs/pzxx/pzlr/txl_glfimp.jsp", "导入管理费信息", 450,350);
		return false;
	});
  //数据导出
  	$(document).on("click","#btn_glfexp",function(){
  		var id = [];
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		var bxlx = $("#sel_djlx").val();
   		doExp(bxlx,"${ctx}/kjhs/pzxx/pzlr/expKyglxm","科研管理费信息","${pageContext.request.contextPath}",id);

	});
	//数据增加
  	$(document).on("click","#btn_glfadd",function(){
		select_commonWin("${pageContext.request.contextPath}/webView/kjhs/pzxx/pzlr/glfadd.jsp", "增加管理费",950,600);
		return false;
	});
	//删除
	$(document).on("click","#btn_glfplsc",function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			confirm("确定要删除选中的这"+checkbox.length+"条信息吗？","",function(){
	 			var guid = [];
	 			checkbox.each(function(){
	 				guid.push($(this).val());
	 			});
				$.ajax({
		   			url:"${ctx}/kjhs/pzxx/pzlr/DeleteKyglxm",
		   			data:"guid="+guid.join("','"),
		   			type:"post",
		   			dataType:"json",
		   			async:"false",
		   			success:function(val){
			   			if(val.success){
			   				alert("删除成功");
			   				table.ajax.reload();
			   			}else{
			   				alert("删除失败");
			   			}	
			   		},
			   		error:function(){
			   			alert("抱歉，系统出现问题！");
			   		}
		   		});
   			});
		}else{
   	   		alert("请选择至少一条信息删除!");
   	   	}
	});
	$(document).on("click","#btn_search1",function(){
		var pageName = "bxxx";
		var bxlx = $("#sel_djlx").val();
		var djbh = $("#txt_djbh").val();
		var sqr = $("#txt_sqr").val();
		table.ajax.url("${ctx}/kjhs/pzxx/pzlr/getBxxxData?bxlx="+bxlx+"&djbh="+djbh+"&sqr="+sqr);
		table.ajax.reload();
	});
	//生成 凭证
  $(document).on("dblclick","tr:not(#head)",function(){
	  var gid = $(this).find(".keyId").val();
	  if(gid == null||gid == ""){
		  alert("没有可选的数据");
		  return;
	  }
	  var type="glf";
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