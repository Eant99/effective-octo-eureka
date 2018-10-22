<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>图片类型设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="form" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon">类型编号</span> 
					<input type="text" class="form-control" name="lxbh"  value="${tpb.lxbh}" readonly/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon">单据名称</span>
					<select class="form-control select2" name="djlx">
						<c:forEach var="djmclist" items="${djmclist}">
							<option value="${djmclist.MKBH }" <c:if test="${djmclist.MKBH == tpb.DJLX}">selected</c:if>>${djmclist.MKMC }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>图片类型</span>
					<input type="text" class="form-control" name="lxmc" value="${tpb.lxmc}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
				    <span class="input-group-addon">备&emsp;&emsp;注</span>
				    <textarea class="form-control" rows="4" cols="50" name="bz" >${tpb.BZ}</textarea>
			    </div>
		    </div>
		</div>
		<div class="row">
			<div class="col-sm-12">
			    <div class="input-group">
				    <span class="input-group-addon">是否必须上传</span>
					<select class="form-control" name="sfbxsc">
						<option value="1" <c:if test="${tpb.sfbxsc == 1}">selected</c:if>>是</option>
						<option value="0" <c:if test="${tpb.sfbxsc == 0}">selected</c:if>>否</option>
					</select>
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
	//查看页面时处理函数
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	var validate = $('#form').bootstrapValidator({fields: {
          lxmc:{validators:{notEmpty:{message:'不能为空'}}}}
    });

	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"form","${ctx}/tplxsz/saveTplxsz",function(val){//成功
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
			$("#operateType").val("U");
			getIframDoc("${param.pname}").table.ajax.reload();
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