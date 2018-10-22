<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>功能科目设置</title>

<%@include file="/static/include/public-manager-css.inc"%> 
<style type="text/css">
	select{
		font-size:12px !important; 
		max-height: 25px !important;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>科目名称</label>
					<input type="text" class="form-control input-radius" name="dmmc"  table="g" placeholder="请输入科目名称">
				</div>
				<div class="form-group">
					<label>科目编号</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="kmbh"  table="g" placeholder="请输入科目编号">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" type="button" id="btn_addtj">增加同级</button>
	                <button class="btn btn-default" type="button" id="btn_addxj">增加下级</button>
	        
			 <!--   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button> -->
			
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="guid" value="${guid}"/>
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>科目编号</th>
						    <th>科目名称</th>
						    <th>所属上级科目</th>
						    <th>所属会计科目</th>						  
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
<%@include file="/static/include/public-list-css.inc"%> 
<script >
$(function(){
	//修改按钮
   $(document).on("click",".btn_upd",function(){
  	 doOperate("${ctx}/kmsz/goEditGnPage","C");
	});
	//保存按钮
	$("#btn_save").click(function(){
   		alert("保存成功");
   	});
	//添加同级按钮
	$("#btn_addtj").click(function(){
   		doOperate("${ctx}/kmsz/goEditGnPage","C");
   	});
	
	$("#btn_addxj").click(function(){
   		doOperate("${ctx}/kmsz/goEditGnPage","C");
   	});
	
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var shzt = checkbox.attr("shzt");
   		if(shzt=="已提交"||shzt=="通过"){
   			alert("已提交或者审核通过的无法修改");
   			return;
   		}
		confirm("确定删除？","{title:提示信息}",function(){
			alert("删除成功");
		});
	});
	/* $(document).on("click",".btn_upd",function(){
		
		//var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/kjhs/kmsz/km_edit.jsp", "C");
	}); */
	
	
	var columns = [
		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" bz="'+full.BZS+'" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "KMBH",defaultContent:""},
	   	{"data": "KMMC",defaultContent:""},
	   	{"data": "SSSJKM",defaultContent:""},
	   	{"data": "SSKJKMBH",defaultContent:""},   	
	   	{"data":"GUID","render":function (data, type, full, meta){
	   		if(full.BZS != "1"){
	   			return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
	   		}else return '';
	   	},orderable:false,"width":90}
	];
   	  table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/kmsz/gnkmsz/gnkmsz_list.json",[2,'asc'],columns,0,1,setGroup);

   //	table = getDataTableByListHj("mydatatables","${ctx}/kmsz/getKjkmPageList?treeDm=${param.dm}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
   	$("#btn_sskj").click(function(){
   		select_commonWin("${ctx}/pzlx/window?controlId=txt_sskj","科目信息","920","630");
    });
	
	
});

</script>
</body>
</html>