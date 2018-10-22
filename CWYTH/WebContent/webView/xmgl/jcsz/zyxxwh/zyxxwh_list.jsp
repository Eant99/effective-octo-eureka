<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>专业信息维护</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>专业编号</label>
					<input type="text" id="txt_zybh" class="form-control" name="zybh" table="k" placeholder="请输入专业编号">
				</div>
				<div class="form-group">
					<label>专业名称</label>
					<input type="text" id="txt_zymc" class="form-control" name="zymc"  table="k" placeholder="请输入专业名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>专业编号</label>
							<div class="input-group">
								<input type="text" id="txt_zybh" class="form-control input-radius" name="zybh" value=""  table="k" placeholder="请输入专业编号">         	
							</div>
						</div>
						<div class="form-group">
							<label>专业名称</label>
							<input type="text" id="txt_zymc" class="form-control input-radius" name="zymc" value=""  table="k" placeholder="请输入专业名称">         	
						</div>
						<div class="form-group">
							<label>所属院系</label>
							<div class="input-group">
								<input type="text" id="txt_ssyx" class="form-control input-radius" name="ssyx" value=""  table="k" placeholder="请选择所属院系">
								<span class="input-group-btn"><button type="button" id="btn_ssyx" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>就业方向</label>
							<div class="input-group">
								<input type="text" id="txt_zyfx" class="form-control input-radius" name="zyfx" value=""  table="k" placeholder="请选择就业方向">
								<span class="input-group-btn"><button type="button" id="btn_zyfx" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>学&emsp;&emsp;制</label>
	                       <select id="drp_xz" class="form-control input-radius select2" name="xz" table="k"> 							
	                        		<option value="" >未选择</option>
	                        	
	                        		<option value="三年" >三年</option>   
	                        		<option value="四年" >四年</option>
	                        		<option value="五年" >五年</option>                     		                   
							</select>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	                <button type="button" class="btn btn-default" id="btn_enable">批量启用</button>	               
	                <button type="button" class="btn btn-default" id="btn_disable">批量禁用</button>
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
				            <th>专业编号</th>
				            <th>专业名称</th>
				            <th>所属院系</th>
				            <th>学制</th>
				            <th>专业状态</th>	           
				            <th>操作</th>
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
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "ZYBH",defaultContent:""},
       {"data": "ZYMC",defaultContent:""},
       {"data": "SSYX",defaultContent:""},
       {"data": "XZ",defaultContent:""},
       {"data": "ZYZT",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);"  class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_qyxx">启用</a>|<a href="javascript:void(0);" class="btn btn-link btn_jyxx">禁用</a>';
      },orderable:false,"width":220,"class":"text-center"}
     ];
   table = getDataTableByListHj("mydatatables","${ctx}/zyxxwh/getPageList?treeid=${dwbh}",[2,'asc'],columns,0,1,setGroup);
	
  
	$("#btn_add").click(function(){
   		doOperate("${ctx}/zyxxwh/gozyxxtjPage?qc=${qc}");
   		
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/zyxxwh/expExcel?treedwbh=${dwbh}","专业信息","${ctx}",guid.join(","));

   	});
    //修改
  	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/zyxxwh/gozyxxbjPage?guid="+guid);
   	}); 
    //联想输入提示
	$("#txt_ssyx").bindChange("${ctx}/suggest/getXx","D");
	//单位弹窗 
	$("#btn_ssyx").click(function(){
		
		select_commonWin("${ctx}/window/dwpage?controlId=txt_ssyx","单位信息","920","630");
    });
    $("#btn_zyfx").click(function(){
		
		select_commonWin("${ctx}/window/dwpage?controlId=txt_zyfx","单位信息","920","630");
    });
    //禁用按钮
   	$("#btn_disable").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		guid.push($(this).val());
   	   		});
   	   	confirm("确定禁用？", {title:"提示"}, function(z){
   	  	$.ajax({
   			url:"${ctx}/zyxxwh/updZt?zt=0",
   			data:"guid="+guid.join("','"),
   			type:"post",
   			async:"false",
   			success:function(val){
   			    var result = JSON.getJson(val);
			        if(result.success){
			   alert("禁用成功");  	   					
   				   table.ajax.reload();
				     }
   			}
   		});
   		}); 
   		}else{
   			alert("请选择至少一条信息禁用!");
   		}
   	});
    //启用按钮
   	$("#btn_enable").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		guid.push($(this).val());
   	   		});
   	   	confirm("确定启用？", {title:"提示"}, function(z){
   	   	$.ajax({
	   			url:"${ctx}/zyxxwh/updZt?zt=1",
	   			data:"guid="+guid.join("','"),
	   			type:"post",
	   			async:"false",
	   			success:function(val){
	   			    var result = JSON.getJson(val);
  			        if(result.success){
				    alert("启用成功！");  	   					
	   				   table.ajax.reload();
 				     }
	   			}
	   		});
   		}); 
   		}else{
   			alert("请选择至少一条信息启用!");
   		}
   	});
	  //批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/zyxxwh/doDelete",
	   	   			data:"guid="+guid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   			    /* var result = JSON.getJson(val);
   	   			        if(result.success){
						   alert("删除成功！");  	   					
	   	   				   table.ajax.reload();
  	   				     } */
	   	   			alert(val.replace("\"","").replace("\"",""));
   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
	  
		var guid = $(this).parents("tr").find("[name='guid']").val();
		console.log("___"+guid);
   		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"${ctx}/zyxxwh/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
//    	   			    var result = JSON.getJson(val);
//    	   			    if(result.success){
						alert(val.replace("\"","").replace("\"","")); 					
	   	   				table.ajax.reload();
//   	   				}
   	   			}
   	   		});
   		}); 
	});
	
	$(document).on("click",".btn_qyxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("是否启用？","",function(){
			$.ajax({
				url:"${ctx}/zyxxwh/doqy",
				data:"guid="+guid,
				type:"post",
				dateType:"json",
				async:"false",
				success:function(val){
// 					var result = JSON.getJson(val);
// 					if(result.success){
						alert("启用成功！");
// 					}
					table.ajax.reload();
				}
			});
		});
	});
	
	$(document).on("click",".btn_jyxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("是否禁用？","",function(){
			$.ajax({
				url:"${ctx}/zyxxwh/dojy",
				data:"guid="+guid,
				type:"post",
				asunc:"false",
				success:function(val){
					var result = JSON.getJson(val);
					if(result.success){
						alert("禁用成功!");
					}
					table.ajax.reload();
				}
				
			});
		});
	});
	
	//弹窗
	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwbh","单位信息","920","630");
    });
	$("#btn_rybh").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_rybh","人员信息","920","630");
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