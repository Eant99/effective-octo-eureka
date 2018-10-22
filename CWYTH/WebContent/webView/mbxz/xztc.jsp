<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助信息设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
<style type="text/css">
	
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="id" value="${bzxx.ID}" />
    <div class="container-fluid dialog-body">
	<div class="row">
		<div class="col-md-6">
			<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>模块名称</span>
				<select id="drp_jldw" class="form-control input-radius" name="jldw"> 
					<c:forEach var="jldw" items="${jldwList}">
	                       <option value="${mb.gid}" <c:if test="${mb.gid == mbxz.gid}">selected</c:if>>${mb.mbmc}</option>
	                </c:forEach>
				</select>
			</div>
		</div>
	</div>

</div>
<div class='page-bottom clearfix'>
	<div class="pull-right">
		<button class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
			 <i class="fa icon-save"></i>
				保存
		</button>
		<button class="btn btn-default btn-without-icon" id="btn_cancelw">
				取消
		</button>
	 </div>
</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function(){
  
		editor();
		//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});

		//页面验证
		var validate = $('#myform').bootstrapValidator({fields: {
	            mc: {validators: {notEmpty: {message: '模块名称不能为空'}}},
				}
	    });
		
	$("#btn_save").click(function(){
		doSave1(validate,"myform","http://192.168.10.155/apis/app/zysxAdd",function(val){
			alert(val);
			if(val){
				alert(val);
				parent.location.reload(true);
			}
		});
	});


	});
	function editor(){
		KindEditor.ready(function(K) {
	        editor = K.create('#content',{
	        	afterBlur : function() {
	    			this.sync();
	    		}
	        });
		});
	}
</script>
</body>
</html>