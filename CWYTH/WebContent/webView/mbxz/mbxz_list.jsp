<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>模板选择</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scrollHeadInner{
 		width:600px ! important;
	} 
 	table.dataTable{ 
 		width:600px ! important;
 	} 
</style>
</head>
<body >
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-bottom: 2px;padding-top: 8px">
    		<div class="search-simple">
				<div class="form-group">
					<label>帐表名称</label>
					<input type="text"  class="form-control input-radius" id="txt_gjc" name="ZBMC"   placeholder="请输入帐表名称">
					
					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				</div>
<!-- 				<div class="btn-group pull-right"role="group" > -->
<!-- 		                <button type="button" class="btn btn-default" id="btn_add">新增</button> -->
<!-- 						<button type="button" class="btn btn-default" id="btn_dr">导入</button> -->
<!-- 	            	</div> -->
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
				          <th>帐表名称</th>
				          <th>使用模板</th>
  						<th>操作</th>
				    </tr>
				    </thead>
				    <tbody></tbody>
				</table>
            </div>
        </div>
	</div>
	</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
var target = "${ctx}/webView/mbxz";
$(function() {
	//列表数据
	var columns = [
		{"data": "GID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="gid" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"searchable": false,"class":"text-center","width":40},
		{"data": "ZBMC",defaultContent:"","width":160},
		{"data": "MBMC",defaultContent:"", "render": function (data, type, full, meta){
			if(data=="模板一"){
				return '<select name="mbmc" style="width:183px" ><option value="1" selected >模板一</option><option value="2" >模板二</option> </select>';
			}else if (data=="模板二"){
				return '<select name="mbmc" style="width:183px" ><option value="1" >模板一</option><option value="2" selected>模板二</option> </select>';
			}
	       	return '<select name="mbmc" style="width:183px"><option value="1" >模板一</option><option value="2" >模板二</option> </select>';
	    },"width":40},
	    {"data":"",'render':function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_save" guid="' + data + '" >保存</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" xz="'+full.ZBMC+'" xz2="'+full.MBMC+'" guid="' + data + '" >预览</a>';
	      },orderable:false,"width":220,"class":"text-center","width":100}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/mbxz/getPageList",[2,'asc'],columns,0,1,setGroup);

   	
   	
  //预览
	$(document).on("click",".btn_look",function(){
		var xz = $(this).attr("xz");
		var xz2 = $(this).parents("tr").find("[name='mbmc']").val();
		console.log(xz2);
		if(xz=="（旧）收入费用表（年度）" && xz2=="1"){
			window.location.href = target+"/srfybyear.jsp";
		}
		if(xz=="（旧）收入费用表（月度）" && xz2=="1"){
			window.location.href = target+"/srfybmonth.jsp";
		}
		if(xz=="净资产变动表"&& xz2=="1"){
			window.location.href = target+"/jzcbdb.jsp";
		}
		if(xz=="旧资产负债表（年度）"&& xz2=="1"){
			window.location.href = target+"/jzcfzbyear.jsp";
		}
		if(xz=="旧资产负债表（月度）"&& xz2=="1"){
			window.location.href = target+"/jzcfzbmonth.jsp";
		}
		if(xz=="科目余额表"&& xz2=="1"){
			window.location.href = target+"/kmyeb.jsp";
		}
		if(xz=="明细帐"&& xz2=="1"){
			window.location.href = target+"/mxz.jsp";
		}
		if(xz=="收入费用表（年度）"&& xz2=="1"){
			window.location.href = target+"/srfybyear.jsp";
		}
		if(xz=="收入费用表（月度）"&& xz2=="1"){
			window.location.href = target+"/srfybmonth.jsp";
		}
		if(xz=="现金流量表"&& xz2=="1"){
			window.location.href = target+"/xjllb.jsp";
		}
		if(xz=="资产负债表（年度）"&& xz2=="1"){
			window.location.href = target+"/zcfzbyear.jsp";
		}
		if(xz=="资产负债表（月度）"&& xz2=="1"){
			window.location.href = target+"/zcfzbmonth.jsp";
		}
		if(xz=="总账"&& xz2=="1"){
			window.location.href = target+"/zz.jsp";
		}
		
		
		
	});
   	
  //设置启用
	$(document).on("click",".btn_upd",function(){
		var gid = $(this).parents("tr").find("[name='gid']").val();
		 confirm("确定启用该模板？",'',{title:"提示信息"},function(){
			$.ajax({
				url:"${pageContext.request.contextPath}/mbxz/ztsz",
				data:"gid="+gid+"&zt="+'1',
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.success){
						alert("操作成功！");
					}else{
						alert("操作失败！");
					}
				}
			});
			table.ajax.reload();
			return false
		});
		return false 
   	});
  //保存
	$(document).on("click",".btn_save",function(){
		var mb = $(this).parents("tr").find("[name='mbmc']").val();
		var gid = $(this).parents("tr").find("[name='gid']").val();
		confirm("确定使用模板？",{title:"提示信息"},function(){
			$.ajax({
				url:"${pageContext.request.contextPath}/mbxz/doSave",
				data:"mb="+mb+"&gid="+gid,
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.success){
						alert("操作成功！");
					}else{
						alert("操作失败！");
					}
				}
			});
			table.ajax.reload();
		});
   	});
	
	//弹出选择模板框
// 	select_commonWin("${pageContext.request.contextPath}/mbxz/xztc","选择模板","400","250");
		});
</script>
</body>
</html>