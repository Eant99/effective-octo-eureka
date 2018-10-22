<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>人员进修情况</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
	<form class="addLwqk" class="form-horizontal" id="jxqkform" action="" method="post">	        
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<input type="hidden" name="rybh" id="txt_rybh" value="${rybh}">
		<input type="hidden" name="guid" id="txt_guid" value="${guid}">
		<div class="container-fluid dialog-body">
			<div class="row">
    			<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">进修种类</span>
						<select id="txt_jxzl" class="form-control input-radius" name="jxzl" >
                 			<option value="1" <c:if test="${ryjxb.jxzl==1}">selected</c:if>>国内进修</option>
                 			<option value="2" <c:if test="${ryjxb.jxzl==2}">selected</c:if>>国外进修</option>
                    	</select>
              		</div>	
  				</div>
			</div>
			<div class="row">
  				<div class="col-sm-12">
					<div class="input-group">
              			<span class="input-group-addon">进修开始日期</span>
                    	<input type="text" id="txt_jxrq" class="form-control date" name="jxrq" value="<fmt:formatDate value="${ryjxb.jxrq}" pattern="yyyy-MM-dd"/>"  data-format="yyyy-MM-dd" placeholder="进修开始日期">
                  		<span class='input-group-addon'>
                      		<i class="glyphicon glyphicon-calendar"></i>
                  		</span>
            		</div>		
				</div>
   			</div>
      		<div class="row">
       			<div class="col-sm-12">
					<div class="input-group">
						<!-- 带有必填项的label元素 -->
						<span class="input-group-addon">进修时间（月）</span>
						<input type="text" id="txt_jxsj" class="form-control input-radius text-right" onkeyup='this.value=this.value.replace(/\D/gi,"")' name="jxsj" value="${ryjxb.jxsj}" maxlength="10" placeholder="最多输入10个字符"/>
	   				</div>	
  				</div>
			</div>
			<div class="row">
  				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">进修内容</span>
						<select id="txt_jxnr" class="form-control input-radius" name="jxnr">
                 			<c:forEach var="jxnrList" items="${jxnrList}">
                 				<option value="${jxnrList.DM}" <c:if test="${ryjxb.jxnr == jxnrList.DM}">selected</c:if>>${jxnrList.MC}</option>
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
  //人员信息
    $("#tab_ryxx").click(function(){
    	window.location.href = "${ctx}/ryb/goRybListPage";
   	});
  //论文情况
    $("#tab_lwqk").click(function(){
    	window.location.href ="${ctx}/rylwb/goLwqkListPage?rybh=${rybh}";
   	});
  //外语水平
    $("#tab_wysp").click(function(){
    	window.location.href ="${ctx}/rywyspb/goWyspListPage?rybh=${rybh}";
   	});
  //著作情况
    $("#tab_zzqk").click(function(){
    	window.location.href ="${ctx}/ryzzb/goZzqkListPage?rybh=${rybh}";
   	});
  //成果奖励
    $("#tab_cgjl").click(function(){
    	window.location.href ="${ctx}/rycgjlb/goCgjlListPage?rybh=${rybh}";
   	});
  //修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='keyId']").val();
	   	doOperate("${ctx}/ryjxb/goRyjxPage?guid="+guid+"&rybh=${rybh}","U");
   	});
  //单个删除
   	$(document).on("click",".btn_delxx",function(){
   		var guid = $(this).parents("tr").find("[name='keyId']").val();
   		doDel("guid="+guid,"${ctx}/ryjxb/doDelete",function(val){
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
   			doDel("guid="+guid.join(","),"${ctx}/ryjxb/doDelete",function(val){
   	   			table.ajax.reload();
   	   			//....
   	   		},function(val){
   	   			//....
   	   		},guid.length);
   		}else{
   			alert("请选择至少一条信息删除");
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
	   	{"data": "JXZL",defaultContent:""},
	   	{"data": "JXRQ",defaultContent:""},
	   	{"data": "JXSJ",defaultContent:"","class":"text-right"},
	   	{"data": "JXNR",defaultContent:""},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":105}
	];
 	table = getDataTable("mydatatables","${ctx}/ryjxb/getRyjxList?rybh=${rybh}",[4,'asc'],columns);
  //保存按钮
	$("#btn_save").click(function(e){
		doSave(null,"jxqkform","${ctx}/ryjxb/doSave",
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