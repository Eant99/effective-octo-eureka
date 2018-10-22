<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目类型</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
#btns{
	margin-top: 10px !important;	
	}
</style>

<body>
<div class="fullscreen">
    <div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>购买人</label>
					<input type="text" id="txt_gmr" class="form-control" name="gmr" table="K" placeholder="请输入购买人姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">购买</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
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
				            <th style="text-align:center">序号</th>
				            <th style="text-align:center" >银行科目</th>
				            <th style="text-align:center" >购买日期</th>
				            <th style="text-align:center" >购买人</th>
				            <th style="text-align:center" >票据账户</th>
				            <th style="text-align:center" >票据前缀</th>
				            <th style="text-align:center" >票据张数</th>
				            <th style="text-align:center" >票据起号</th>
				            <th style="text-align:center" >票据止号</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
            </div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-checked.js"></script>
<script>
$(function () {
	var columns = [
	       {"data": "GID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="gid" class="keyId " value="' + data + '" gid = "'+full.GID+'">';
	       },"width":10,'searchable': false,"class":"text-center"},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":20,"searchable":false,"class":"text-center"},
	       {"data": "KMMC",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "GMRQ",defaultContent:"","width":40,"class":"text-center",},
	       {"data": "GMR",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "PJZH",defaultContent:"","class":"text-left","width":30},
	       {"data": "PJQZ",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "PJZS",defaultContent:"","width":40,"class":"text-right",},
	       {"data": "PJQH",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "QJZH",defaultContent:"","width":40,"class":"text-left",}
	     ];
	    table = getDataTableByListHj("mydatatables","${ctx}/pjgm/getPjgmList",[2,'asc'],columns,0,1,setGroup);

   	$("#btn_add").click(function(){
   		select_commonWin("${ctx}/webView/pjgl/rcyw/pjgm/pjgm_add.jsp","购买详情页","800","300");
   	});
   	
   	$(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='gid']").filter(":checked");
   			if(checkbox.length>0){
   	   			var guid = [];
   	   			checkbox.each(function(){
   	   				guid.push($(this).val());
   	   			});
	   	   		$.ajax({
		   			url:"${ctx}/pjgm/doCheckused",
		   			data:"guid="+guid.join("','"),
		   			type:"post",
		   			dataType:"json",
		   			async:"false",
		   			success:function(val){
		   				if(val){
		   					confirm("确定要删除所选中的这"+checkbox.length+"条信息吗？","",function(){
	   						$.ajax({
		    		   			url:"${ctx}/pjgm/doDelete",
		    		   			data:"guid="+guid.join("','"),
		    		   			type:"post",
		    		   			dataType:"json",
		    		   			async:"false",
		    		   			success:function(val){
		    		   				if(val.success){
		    		   					alert("删除成功！");
		    		   				}
		    		   				table.ajax.reload();
		    		   			},
		    		   			error:function(){
		    		   				alert("抱歉，系统出现问题！");
		    		   			}
		    		   		});	
						});	
		   				}else{
		   					alert("抱歉，票据已经被使用，不能删除！");
		   				}
		   			},
		   			error:function(){
		   				alert("抱歉，系统出现问题!");
		   			}
   				});	
   			}else{
   	   			alert("请选择至少一条信息删除!");
   	   		}
   	});		
});
   	

</script>
</html>