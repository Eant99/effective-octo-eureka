<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>人员信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body style="background-color: white;">
	<form id="form" class="form-horizontal" action="" method="post" >
		 <div class="box" style="top:36px;margin-left: 9px;margin-right: 9px;">
         <div class="box-content">
             <div class="row">
             	<div class="col-md-12">
             		<table class="table table-bordered">
             			<thead>
             				<tr>
             					<th class="col-md-2">人员编号</th>
             					<th class="col-md-2">人员名称</th>
             				</tr>
             			</thead>
                          <tbody>
              					<c:forEach var="item" items="${rymcList}">
              					<tr>
              					<td>${item.RYBH}</td>
              					<td>${item.MC}</td>
              					</tr>
              					</c:forEach>
              		</tbody>
	                  		</table>

                 </div>
             </div>
         </div>
     </div>
	   
		
	</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var validate = $('#form').bootstrapValidator({fields: {
        jsmc:{validators:{notEmpty:{message:'不能为空'}}}}
    });
	//查看页面时处理函数
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"form","${ctx}/jsxx/doSave",
		function(val){//成功
			$("#operateType").val("U");
			getIframWindow("${param.pname}").$('#mydatatables').DataTable().ajax.reload();
 			var winId = getTopFrame().layer.getFrameIndex(window.name);
 	    	close(winId);
		},function(val){//失败
			
		});
	}); 
	//取消
	$("#btn_cancelw").click(function(){
		//getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
</body>
</html>