<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>人员外语水平</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;"><body>
	<form class="addWysp" class="form-horizontal" id="wyspform" action="" method="post">	        
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<input type="hidden" name="rybh" id="txt_rybh" value="${rybh}">
		<input type="hidden" name="guid" id="txt_guid" value="${guid}">
   		<div class="container-fluid dialog-body">
     		<div class="row">
       			<div class="col-sm-12">
					<div class="input-group">
						<!-- 带有必填项的label元素 -->
						<span class="input-group-addon">语&emsp;&emsp;种</span>
						<select id="txt_wyyz" class="form-control input-radius" name="yz">
	                  		<c:forEach var="wyyzList" items="${wyyzList}">
	                  			<option value="${wyyzList.DM}" <c:if test="${rywyspb.yz == wyyzList.DM}">selected</c:if>>${wyyzList.MC}</option>
	                  		</c:forEach>
	                  	</select>
	  				</div>	
				</div>
			</div>
 			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<!-- 带有必填项的label元素 -->
						<span class="input-group-addon">水&emsp;&emsp;平</span>
							<select id="txt_wysp" class="form-control input-radius" name="sp">
                  				<c:forEach var="wyspList" items="${wyspList}">
                  					<option value="${wyspList.DM}" <c:if test="${rywyspb.sp == wyspList.DM}">selected</c:if>>${wyspList.MC}</option>
                  				</c:forEach>
                       		</select>
				   	</div>	
		   		</div>
        	</div>
        </div>
        <div class='page-bottom clearfix'>
	        <div class="pull-right">
		        <button type="button" class="btn btn-default" id="btn_save"><i class='fa icon-save'></i>保存</button>
				<button type="button" class="btn btn-default btn-without-icon" id="btn_back">返回</button>
	        </div>
    	</div>
	</form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='keyId']").val();
	   	doOperate("${ctx}/rywyspb/goRywyspbPage?guid="+guid+"&rybh=${rybh}","U");
   	});
    $("#tab_ryxx").click(function(){
    	window.location.href = "${ctx}/ryb/goRybListPage";
   	});
    //论文情况
    $("#tab_lwqk").click(function(){
    	window.location.href ="${ctx}/rylwb/goLwqkListPage?rybh=${rybh}";
   	});
  //进修情况
    $("#tab_jxqk").click(function(){
    	window.location.href ="${ctx}/ryjxb/goJxqkListPage?rybh=${rybh}";
   	});
  //著作情况
    $("#tab_zzqk").click(function(){
    	window.location.href ="${ctx}/ryzzb/goZzqkListPage?rybh=${rybh}";
   	});
  //成果奖励
    $("#tab_cgjl").click(function(){
    	window.location.href ="${ctx}/rycgjlb/goCgjlListPage?rybh=${rybh}";
   	});
   	//单个删除
   	$(document).on("click",".btn_delxx",function(){
   		var guid = $(this).parents("tr").find("[name='keyId']").val();
   		doDel("guid="+guid,"${ctx}/rywyspb/doDelete",function(val){
   			table.ajax.reload();
   			//....
   		},function(val){
   			//....
   		},"1");
   	});
   	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
   			checkbox.each(function(){
   				guid.push($(this).val());
   			});
   			doDel("guid="+guid.join(","),"${ctx}/rywyspb/doDelete",function(val){
   	   			table.ajax.reload();
   	   			//....
   	   		},function(val){
   	   			//....
   	   		},guid.length);
   		}else{
   			alert("请选择至少一条信息删除！");
   		}
   	});
  //列表数据
   	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "YZ",defaultContent:""},
	   	{"data": "SP",defaultContent:""},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":105}
	];
 	table = getDataTable("mydatatables","${ctx}/rywyspb/getWyspList?rybh=${rybh}",[2,'asc'],columns);
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(null,"wyspform","${ctx}/rywyspb/doSave",
		function(val){//成功
			$("#operateType").val("U");
			getIframWindow("${param.pname}").table.ajax.reload();
 			var winId = getTopFrame().layer.getFrameIndex(window.name);
 	    	close(winId);
		},function(val){//失败
			
		});
	});
	//返回按钮
	$("#btn_back").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>