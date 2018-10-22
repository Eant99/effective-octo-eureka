<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>消费地点信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>消费地点</label>
					<input type="text" id="txt_xfddmc" class="form-control" name="xfddmc" table="A" placeholder="请输入消费地点名称">
				</div>
				<div class="form-group">
								<label>所属承包商</label>
								<input type="text" id="txt_cbsmc" class="form-control" name="cbsmc" table="K" placeholder="请输入承包商名称">
							</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>

				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <button type="button" class="btn btn-default" id="btn_plscewm">批量生成二维码</button>
	               <button type="button" class="btn btn-default" id="btn_plxzewm">批量下载二维码</button>
	               
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
				            <th>消费地点编号</th>
				            <th>消费地点名称</th>
				            <th>所属承包商</th><th>业务类型</th>
				            <th>所属校区</th>	
				            <th>负责人</th>	
				            <th>状态</th>            
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
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId"   value="' + data + '" guid = "'+full.GUID+'" ewmurl="'+full.EWMURL+'" xfddmc = "'+full.XFDDMC+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
          	
       {"data": "XFDDBH",defaultContent:""},
       {"data": "XFDDMC",defaultContent:""},
       {"data": "CBSMC",defaultContent:""},
       {"data": "YWLX",'render':function(data,type,full,meta){
    	   if(full.YWLXMC=='undefined'||full.YWLXMC==''||full.YWLXMC==null){
    		   return '';
    	   }else{
         		return '<span>'+full.YWLXMC+'</span>';
         	}
       } },   
       {"data": "ZLWZ",defaultContent:""},
       {"data": "FZR",defaultContent:""},
       {"data": "ZT",defaultContent:""},       
       
       {"data": "GUID",'render':function(data, type, full, meta){
    	   if(full.EWMURL=='0'){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>'+
	   		      '|<a href="javascript:void(0);" class="btn btn-link btn_scewm" guid = "'+full.GUID+'" ywlx= "'+full.YWLX+'">生成二维码</a>';
    	   }else{
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>'+
	   		      '|<a href="javascript:void(0);" class="btn btn-link btn_scewm" guid = "'+full.GUID+'" ywlx= "'+full.YWLX+'">生成二维码</a>|<a href="javascript:void(0);" class="btn btn-link btn_chakan" guid = "'+full.GUID+'" ywlx= "'+full.YWLX+'" ewmurl="'+full.EWMURL+'">查看二维码</a>'+
	   		       '|<a href="javascript:void(0);" class="btn btn-link btn_xiazai" guid = "'+full.GUID+'" ewmurl="'+full.EWMURL+'" xfddmc = "'+full.XFDDMC+'">下载</a>';
    	   }
      },orderable:false,"width":220}
     ];
    	table = getDataTableByListHj("mydatatables","${ctx}/xfddgl/getPageList",[2,'asc'],columns,0,1,setGroup);
 	//添加按钮
 	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/xfddgl/goCbsxxPage","C");
   	});
 	 //编辑
   	 $(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
   		doOperate("${ctx}/xfddgl/goCbsxxPage?guid="+guid,"U");
   	});
   	 //查看
     $(document).on("click",".btn_chakan",function(){
    	 var ewmUrl = $(this).attr("ewmurl");
    	 select_commonWin("${ctx}/webView/fzgn/wxzf/ewmzs.jsp?ewmurl="+ewmUrl,"承包商二维码","300","360");
     });
    $(document).on("click",".btn_scewm",function(){
    	var id = $(this).attr("guid");
    	var ewmUrl='';
    	var success = false;
    	var ywlx = $(this).attr("ywlx");
    	var bh = $(this).parents("tr").find("td").eq(2).text();
    	var dd = $(this).parents("tr").find("td").eq(3).text();
    	var cbs = $(this).parents("tr").find("td").eq(4).text();
    	$.ajax({
    		url:"${ctx}/phone/LoginCreateQRCodeByCbs",
    		data:"QRKey="+id+"&bh="+bh+"&cbs="+cbs+"&dd="+dd+"&ywlx="+ywlx,
    		type:"post",
    		async:"false",    		
    		success:function(val){
    			ewmUrl = val; 
    			$.ajax({
       	   			url:"${ctx}/xfddgl/doUpdateEwm",
       	   			data:"id="+id+"&ewmUrl="+ewmUrl,
       	   			type:"post",
       	   			async:"false",
       	   			success:function(val){   	   				
       	   			    table.ajax.reload();
       	   			    select_commonWin("${ctx}/webView/fzgn/wxzf/ewmzs.jsp?ewmurl="+ewmUrl,"承包商二维码","300","360");
       	   			}
       	   		});
    			
    		}
    	})
    });
    $(document).on("click",".btn_xiazai",function(){
    	var ewmurl = $(this).attr("ewmurl");
    	var houzui = ewmurl.substr(ewmurl.lastIndexOf("."));
    	var xfddmc = $(this).attr("xfddmc")+"二维码"+houzui;
    	FileDownload("${ctx}/xfddgl/fileDownload",xfddmc, ewmurl);
    });
    //批量生成二维码
     $(document).on("click","#btn_plscewm",function(){
     	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
     	var bhs = [];
     	var dds = [];
     	var cbss = [];
     	var ids = [];
     	if(checkbox.length>0){   	   		
   	   		checkbox.each(function(i,v){
	   	   		var bh = $(this).parents("tr").find("td").eq(2).text();
	   	    	var dd = $(this).parents("tr").find("td").eq(3).text();
	   	    	var cbs = $(this).parents("tr").find("td").eq(4).text();
	   	    	var id = $(this).val();
	   	    	$.ajax({
	   	    		url:"${ctx}/phone/LoginCreateQRCodeByCbs",
	   	    		data:"QRKey="+id+"&bh="+bh+"&cbs="+cbs+"&dd="+dd,
	   	    		type:"post",
	   	    		async:"false",    		
	   	    		success:function(val){
	   	    			ewmUrl = val;  
	   	    			$.ajax({
	   	       	   			url:"${ctx}/xfddgl/doUpdateEwm",
	   	       	   			data:"id="+id+"&ewmUrl="+ewmUrl,
	   	       	   			type:"post",
	   	       	   			async:"false",
	   	       	   			success:function(val){   	   				
	   	       	   				if(i+1==checkbox.length){
	   	       	   					alert("操作成功！");
	   	       	   					table.ajax.reload();
	   	       	   				}
// 	   	       	   			    select_commonWin("${ctx}/webView/fzgn/wxzf/ewmzs.jsp?ewmurl="+ewmUrl,"承包商二维码","300","360");
	   	       	   			}
	   	       	   		});
	   	    			
	   	    		}
	   	    	});
   	   		});
   		}else{
   			alert("请选择至少一条信息生成!");
   		}
     });
    $(document).on("click","#btn_plxzewm",function(){
    	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
    	var ewmurl = [];
    	var fileName = [];
    	var flag = false;
    	if(checkbox.length>0){   	   		
   	   		checkbox.each(function(){
   	   			var url = $(this).attr("ewmurl");
   	   			if(url=='0'){
   	   				flag= true;
   	   			}
   	   			else{
   	   		   ewmurl.push($(this).attr("ewmurl"));
   	   		   fileName.push($(this).attr("xfddmc")+url.substr(url.lastIndexOf(".")));
   	   			}
   	   		});
   	   		if(flag==true){
   	   			alert("包含未生成二维码的商家。");
   	   		}else{
   	   	      FileDownload("${ctx}/xfddgl/filesDownload",fileName, ewmurl);
   	   		}
   		}else{
   			alert("请选择至少一条信息下载!");
   		}
    	
    	FileDownload("${ctx}/xfddgl/fileDownload",xfddmc, ewmurl);
    });
    //批量删除按钮
     $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xfddgl/doDelete",
	   	   			data:"guid="+guid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("删除成功");
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
		var guid = $(this).attr("guid");
   		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"${ctx}/xfddgl/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("删除成功");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
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