<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>业务说明信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="id" value="${ywsmb.ID}" />
    <div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>业务类型</span>
					<c:choose>
						<c:when test="${operateType=='C'}">
							<select id="drp_ywbh" class="form-control  input-radius  " name="mkbh"  > 
								<option value="">请选择</option> 
								<c:forEach var="Mk_list" items="${Mk_list}">
									<option value="${Mk_list.mkbh}" <c:if test="${ywsmb.MKBH==Mk_list.mkbh}">selected='selected'</c:if>> ${Mk_list.mkmc}</option> 
								</c:forEach>
                                   </select>
						</c:when>
						<c:otherwise>
							<c:forEach var="Mk_list" items="${Mk_list}">
								<c:if test="${ywsmb.MKBH==Mk_list.mkbh}">
									<input type="text" class="form-control input-radius" value="${ywsmb.MKMC}" readonly/>
									<input type="hidden" name="mkbh" value="${ywsmb.MKBH}" />
								</c:if>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class= "row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>业务名称</span>
					<input type="text" class="form-control input-radius" name="mkmc" value="${ywsmb.MKMC}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>业务说明</span>
					<div id="editor">
						<textarea style="width:99%;height:500px;" name="content" id="content">${Content}</textarea>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
		<div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
			 	<i class="fa icon-save"></i>
					保存
			</button>
			<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
					取消
			</button>
	    </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function(){
		editor();
		//查看页面时处理函数
		if($("[name='operateType']").val()=='L'){
			$("input,select,textarea").attr("readonly","true");
			$("select").attr("disabled","true");
		}
		//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
		//页面验证
		var validate = $('#myform').bootstrapValidator({fields: {
		    mkbh: {validators: {notEmpty: {message: '不能为空'}}},
		    mkmc: {validators: {notEmpty: {message: '不能为空'}}}}
	    });
		//保存按钮
		$("#btn_save").click(function(e){
			var content = encodeURIComponent($("#content").val());
			if(content ==""){
				alert("业务说明不能为空！");
			}else{
			var valid;
			if(validate){
				validate.bootstrapValidator("validate");
				valid = $('#myform').data('bootstrapValidator').isValid();
			}else{
				valid = true;
			}
			if(valid){
			var ywlx = $("[name='mkbh']").val();
			var ywmc = $("[name='mkmc']").val();
			var id = $("[name='id']").val();
			var operateType = $("[name='operateType']").val();
			$.ajax({
				type:"post",
				url:"${ctx}/ywsm/doSave",
				data:"content="+content+"&ywlx="+ywlx+"&ywmc="+ywmc+"&operateType="+operateType+"&id="+id,
				dataType:"json",
				success:function(val){
					alert(val.msg);
					getIframWindow("${param.pname}").table.ajax.reload();
		 			var winId = getTopFrame().layer.getFrameIndex(window.name);
		 	    	close(winId);
				}
			});
			}}
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