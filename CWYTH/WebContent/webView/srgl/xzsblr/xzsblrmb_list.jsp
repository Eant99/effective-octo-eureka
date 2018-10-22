<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>薪资上报录入列表页</title>
<%@include file="/static/include/public-list-css.inc"%>
<%--表头样式--%>
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
/*     	border-bottom-width: 0px; */
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
	.search-simple .select2-container--default .select2-selection--single {
   		background-color: #fff;
   		border: 1px solid #ccc;
    	border-radius: 4px 4px 4px 4px;
	}

</style>
</head>
<body>
<div class="fullscreen">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_getmb">提取模版</button>
	               <button type="button" class="btn btn-default" id="btn_delmb">删除模版</button>
	               <button type="button" class="btn btn-default" id="btn_back">返回</button>
				</div>
		</form>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <c:if test="${param.type!='model'}">
				             <th>审核状态</th>
				             <th>流水号</th>
				            </c:if>
				            <th>录入日期</th>
				            <th>发放方式</th>
				            <th>发放方案</th>
				            <th>摘要</th>
				            <th>财务项目</th>
				            <th>人员类型</th>
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
	var type="${param.type}";
	//列表数据
	if(type =='model'){
	    var columns = [
	       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="guid" shzt="'+full.CHECKSHZT+'" rylx="'+full.RYLXDM+'" class="keyId"  ffry="'+full.FFRY+'"   value="' + data + '" guid = "'+full.GUID+'">';
	       },"width":10,'searchable': false},
	       {"data": "_XH",defaultContent:"","class":"text-center","width":20},
	       {"data": "LRSJ",defaultContent:""},
	       {"data": "FFFS",defaultContent:""},
	       {"data": "FFFA",defaultContent:""},
	       {"data": "ZY",defaultContent:""},
	       {"data": "XMMC",defaultContent:""},
	       {"data": "RYLX",defaultContent:""}
	     ];
	}else{
	    var columns = [
	       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="guid" shzt="'+full.CHECKSHZT+'" rylx="'+full.RYLXDM+'" class="keyId"  ffry="'+full.FFRY+'"   value="' + data + '" guid = "'+full.GUID+'">';
	       },"width":10,'searchable': false},
	       {"data": "_XH",defaultContent:"","class":"text-center","width":20},
	       {"data": "SHZTDM",defaultContent:"","class":"text-center"},
	       {"data": "FFLSH",defaultContent:""},
	       {"data": "LRSJ",defaultContent:""},
	       {"data": "FFFS",defaultContent:""},
	       {"data": "FFFA",defaultContent:""},
	       {"data": "ZY",defaultContent:""},
	       {"data": "XMMC",defaultContent:""},
	       {"data": "RYLX",defaultContent:""}
	     ];
	}
  table = getDataTableByListHj("mydatatables","${ctx}/xzsblr/getPageList?ymbz=mb",[2,'desc'],columns,0,1,setGroup);
   	//删除模版
   	$(document).on("click","#btn_back",function(){
   		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
   	});
   	//提取模版
  	$(document).on("click","#btn_getmb",function(){
    	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked"); //选中模版
    	if(checkbox.length > 0){
    	var rylx = checkbox.attr("rylx");
    	var guid = checkbox.val();
    	var vNow = new Date();
    	var lsh = "";
  		lsh += String(vNow.getFullYear());
  		lsh += String(vNow.getMonth() + 1);
  		lsh += String(vNow.getDate());
  		lsh += String(vNow.getHours());
  		lsh += String(vNow.getMinutes());
  		lsh += String(vNow.getSeconds());
  		lsh += String(vNow.getMilliseconds());
  		$(top.document).contents().find("#"+"${param.pname}").attr("src","${pageContext.request.contextPath}/xzsblr/goEditPage?rylx="+rylx+"&guid="+guid+"&lsh="+lsh+"&operateType=T");
   		var winId = getTopFrame().layer.getFrameIndex(window.name);
		close(winId);
    	}else{
    		alert("请至少选择一条模版!");
    	}
    });
	//删除模版
	$(document).on("click","#btn_delmb",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked"); //选中模版
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
	   				guid.push($(this).val());
   	   		});
	   	   	confirm("确定删除模版？","",function(){
	   			$.ajax({
	   				url:"${ctx}/xzsblr/doDelmb",
	   	   			data:"guid="+guid.join("','"),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   					alert(checkbox.length+"条数据删除成功!");
	   					table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息!");
   		}
	});
	
});
</script>
</body>
</html>