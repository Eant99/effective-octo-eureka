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
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="id" value="${bzxx.ID}" />
    <div class="container-fluid dialog-body">
	<div class="row">
		<div class="col-md-10 col-sm-10">
			<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>目&emsp;&emsp;录</span>
				<c:choose>
					<c:when test="${operateType=='C'}">
						<select id="drp_ywbh" class="form-control input-radius" name="mlbh"  > 
							<option value="">请选择</option> 
							<c:forEach var="Ml_list" items="${Ml_list}">
							<option value="${Ml_list.BH}" <c:if test="${bzxx.MLBH==Ml_list.BH}">selected='selected'</c:if>> ${Ml_list.MC}</option> 
							</c:forEach>
                        </select>
					</c:when>
				    <c:otherwise>
						<c:forEach var="Ml_list" items="${Ml_list}">
							<c:if test="${bzxx.MLBH==Ml_list.BH}">
								<input type="text" class="form-control input-radius" value="${Ml_list.MC}" readonly/>
								<input type="hidden" name="mlbh" value="${bzxx.MLBH}" />
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:if test="${operateType != 'L'}">
		    <div class="col-md-2 col-sm-2">
		        <button class="btn btn-default btn-without-icon" id="btn_bjml" type = "button">
				    编辑目录
		        </button>
		    </div>
		</c:if>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>业务名称</span>
				<input type="text" name="ywmc" id="ywmc" class="form-control input-radius"  value="${bzxx.YWMC}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6">
			<div class="input-group">
				<span class="input-group-addon"><span class="required">*</span>是否展示</span>
				<input type="hidden" name="sfxs" id="sfxs"  value="${bzxx.SFXS}">
                <div class="switch">
                	<div class="onoffswitch">
                    	<input type="checkbox" class="onoffswitch-checkbox" <c:if test="${bzxx.SFXS==1}">checked</c:if> id="btn_sfxs">
                        	<label class="onoffswitch-label" for="btn_sfxs">
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
				<span class="input-group-addon">帮助信息</span>
				<div id="editor">
					<textarea style="width:99%;height:500px;" name="content" id="content">${Content}</textarea>
				</div>
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
		//查看页面时处理函数
		if($("[name='operateType']").val()=='L'){
			$("input,select,textarea").attr("readonly","true");
			$("select").attr("disabled","true");
			$(".onoffswitch-checkbox").attr("id","true");
		}
		var sfxs = "${bzxx.SFXS}";
		if(sfxs==''||sfxs==undefined){
			$("[name='sfxs']").val("1");
		}
		//onoff按扭切换
		$("#btn_sfxs").click(function(){
			if($("[name='sfxs']").val()=='0'){
				$("[name='sfxs']").val("1");
			}else if($("[name='sfxs']").val()=='1'){
				$("[name='sfxs']").val("0");
			}else{
				$("[name='sfxs']").val("1");
			}
		});
		//取消
		$("#btn_cancelw").click(function(){
			getIframWindow("${param.pname}").table.ajax.reload();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		});
		
		//取消
		$("#btn_bjml").click(function(){
			select_commonWin("${ctx}/bzxx/goBzmlPage","目录信息","920","630");
		});
		
		//页面验证
		var validate = $('#myform').bootstrapValidator({fields: {
	            mlbh: {validators: {notEmpty: {message: '不能为空'}}},
	            ywmc: {validators: {notEmpty: {message: '不能为空'}}},}
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
			var mlbh = $("[name='mlbh']").val();
			var ywmc = $("[name='ywmc']").val();
			var id = $("[name='id']").val();
			var sfxs = $("[name='sfxs']").val();
			var operateType = $("[name='operateType']").val();
			$.ajax({
				type:"post",
				url:"${ctx}/bzxx/doSave",
				data:"content="+content+"&mlbh="+mlbh+"&ywmc="+ywmc+"&sfxs="+sfxs+"&operateType="+operateType+"&id="+id,
				dataType:"json",
				success:function(val){
					if(val.success=='true'){
					    alert(val.msg);
					    getIframWindow("${param.pname}").table.ajax.reload();
		 			    var winId = getTopFrame().layer.getFrameIndex(window.name);
		 	    	    close(winId);
					}else{
						alert(val.msg);
					}
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