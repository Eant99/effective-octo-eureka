<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>通知公告</title>
<%@include file="/static/include/public-manager-css.inc"%>
<script src="${ctxStatic}/plugins/ckeditor/ckeditor.js"></script>
<script src="${ctxStatic}/plugins/kindeditor-4.1.2/kindeditor.js"></script>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${xtxx.GID}" />
	<div class="container box-content">
	    <div class="container-fluid dialog-body">
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>标&emsp;&emsp;题</span>
						<input type="text" class="form-control input-radius" name="title" value="${xtxx.TITLE}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>是否展示</span>
						<input type="hidden" name="sfzs" id="sfzs"  value="${xtxx.SFZS}">
		                <div class="switch">
		                    <div class="onoffswitch">
		                        <input type="checkbox" <c:if test="${xtxx.SFZS == '1'}">checked</c:if> class="onoffswitch-checkbox" id="btn_sfzs">
		                        <label class="onoffswitch-label" for="btn_sfzs">
			                        <span class="onoffswitch-inner"></span>
			                        <span class="onoffswitch-switch"></span>
                                </label>
                            </div>
                        </div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon">内&emsp;&emsp;容</span>
						<div id="editor">
							<textarea style="width:99%;height:500px;" name="content" id="content">${Content}</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
	     <div class="pull-right">
			 <button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;"><i class="fa icon-save"></i>保存</button>
			 <button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">取消</button>
	    </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	$(function(){
		//页面验证
		var validate = $('#myform').bootstrapValidator({fields: {
	          title: {validators: {notEmpty: {message: '不能为空'},stringLength:{message:'标题不能超过100个字',max:'100'}}}
	    }});
		editor();
		//查看页面时处理函数
		if($("[name='operateType']").val()=='L'){
			$("input,select,textarea").attr("readonly","true");
			$("select").attr("disabled","true");
		}
		var sfzs = "${xtxx.SFZS}";
		if(sfzs==''||sfzs==undefined){
			$("[name='sfzs']").val("0");
		}
		//onoff按扭切换
		$("#btn_sfzs").click(function(){
			if($("[name='sfzs']").val()=='0'){
				$("[name='sfzs']").val("1");
			}else if($("[name='sfzs']").val()=='1'){
				$("[name='sfzs']").val("0");
			}else{
				$("[name='sfzs']").val("1");
			}
		});
		//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
		//保存按钮
		$("#btn_save").click(function(e){
			var valid;
			if(validate){
				validate.bootstrapValidator("validate");
				valid = $('#myform').data('bootstrapValidator').isValid();
			}
			else{
				valid = true;
			}
			if(valid){
			var content = encodeURIComponent($("#content").val());
			var title = $("[name='title']").val();
			var gid = $("[name='gid']").val();
			var sfzs = $("[name='sfzs']").val();
			var operateType = $("[name='operateType']").val();
			$.ajax({
				type:"post",
				url:"${ctx}/fbxx/doSave",
				data:"content="+content+"&title="+title+"&sfzs="+sfzs+"&operateType="+operateType+"&gid="+gid,
				dataType:"json",
				success:function(val){
					alert(val.msg);
					getIframWindow("${param.pname}").table.ajax.reload();
		 			var winId = getTopFrame().layer.getFrameIndex(window.name);
		 	    	close(winId);
				},
				error:function(){
					alert("系统异常,请联系管理员");
				}
			});
			}
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